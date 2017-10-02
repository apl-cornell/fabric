package fabric.worker.remote;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import fabric.common.AuthorizationUtil;
import fabric.common.SerializedObject;
import fabric.common.TransactionID;
import fabric.common.net.RemoteIdentity;
import fabric.common.net.SubServerSocket;
import fabric.common.net.SubServerSocketFactory;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.AsyncCallMessage;
import fabric.messages.CallMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.DirtyReadMessage;
import fabric.messages.InterWorkerStalenessMessage;
import fabric.messages.MessageToWorkerHandler;
import fabric.messages.ObjectUpdateMessage;
import fabric.messages.PrepareTransactionMessage;
import fabric.messages.RemoteCallMessage;
import fabric.messages.TakeOwnershipMessage;
import fabric.worker.RemoteStore;
import fabric.worker.RetryException;
import fabric.worker.TransactionAtomicityViolationException;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.TransactionRestartingException;
import fabric.worker.Worker;
import fabric.worker.transaction.Log;
import fabric.worker.transaction.TakeOwnershipFailedException;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.transaction.TransactionRegistry;

/**
 * A thread that handles incoming requests from other workers.
 */
public class RemoteCallManager extends MessageToWorkerHandler {

  private final SubServerSocketFactory factory;
  private final InProcessRemoteWorker inProcessRemoteWorker;

  public RemoteCallManager(Worker worker) {
    super(worker.config.name);

    this.factory = worker.authFromAll;
    this.inProcessRemoteWorker = worker.inProcessRemoteWorker;
  }

  @Override
  protected SubServerSocket createServerSocket() {
    return factory.createServerSocket();
  }

  private static Object runRemoteCall(final RemoteIdentity<RemoteWorker> client,
      final CallMessage remoteCallMessage) {
    try {
      // Ensure the receiver and arguments have the right dynamic types.
      fabric.lang.Object receiver =
          remoteCallMessage.getReceiver().fetch().$getProxy();
      Object[] args = new Object[remoteCallMessage.getArgs().length + 1];
      args[0] = client.principal;
      for (int i = 0; i < remoteCallMessage.getArgs().length; i++) {
        Object arg = remoteCallMessage.getArgs()[i];
        if (arg instanceof fabric.lang.Object) {
          arg = ((fabric.lang.Object) arg).fetch().$getProxy();
        }
        args[i + 1] = arg;
      }

      Object result = remoteCallMessage.getMethod().invoke(receiver, args);
      TransactionManager.getInstance().resolveObservations();
      return result;
    } catch (IllegalArgumentException e) {
      throw new RuntimeException(e);
    } catch (SecurityException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      Throwable cause = e.getCause();
      /* e's cause might be a specific runtime exception for
       * restart/abort/retry signalling (eg. RetryException).  These
       * exceptions shouldn't be wrapped as this causes the transaction
       * loop to miss them and give up on the transaction prematurely.
       */
      if (cause instanceof TransactionRestartingException
          || cause instanceof RetryException)
        throw (RuntimeException) cause;
      // TODO: should we remove the invocation target exception layer
      // before wrapping?
      throw new RuntimeException(e);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    } catch (TransactionRestartingException e) {
      throw e;
    } catch (RetryException e) {
      throw e;
    } catch (RuntimeException e) {
      /* TODO This should probably be wrapped in an exception type that
       * better signals it was the code setting up the reflection rather
       * than the reflected invocation itself that was the issue.
       */
      throw new RuntimeException(e);
    }
  }

  @Override
  public RemoteCallMessage.Response handle(
      final RemoteIdentity<RemoteWorker> client,
      final RemoteCallMessage remoteCallMessage) throws RemoteCallException {
    // We assume that this thread's transaction manager is free (i.e., it's not
    // managing any tranaction's log) at the start of the method and ensure that
    // it will be free at the end of the method.

    // XXX TODO Security checks.

    TransactionID tid = remoteCallMessage.tid;
    TransactionManager tm = TransactionManager.getInstance();
    if (tid != null) {
      Log log = TransactionRegistry.getOrCreateInnermostLog(tid);
      tm.associateAndSyncLog(log, tid);

      // Merge in the writer map we got.
      tm.getWriterMap().putAll(remoteCallMessage.writerMap);
    } else {
      throw new InternalError(
          "RemoteCallMessage must only be sent in the context of a transaction!");
    }

    try {
      // Execute the requested method.
      Object result = Worker.runInSubTransaction(new Worker.Code<Object>() {
        @Override
        public Object run() {
          return runRemoteCall(client, remoteCallMessage);
        }
      });

      // Return the result.
      WriterMap writerMap = TransactionManager.getInstance().getWriterMap();
      return new RemoteCallMessage.Response(result, writerMap);
    } catch (RuntimeException e) {
      Throwable cause = e.getCause();
      if (cause instanceof IllegalArgumentException
          || cause instanceof SecurityException
          || cause instanceof IllegalAccessException
          || cause instanceof InvocationTargetException
          || cause instanceof NoSuchMethodException
          || cause instanceof RuntimeException)
        throw new RemoteCallException(cause);

      throw e;
    } finally {
      tm.associateLog(null);
    }
  }

  @Override
  public AsyncCallMessage.Response handle(
      final RemoteIdentity<RemoteWorker> client,
      final AsyncCallMessage remoteCallMessage) throws RemoteCallException {
    // We assume that this thread's transaction manager is free (i.e., it's not
    // managing any tranaction's log) at the start of the method and ensure that
    // it will be free at the end of the method.

    // XXX TODO Security checks.

    try {
      TransactionManager.getInstance().startAsyncCall();

      // Execute the requested method.
      Object result = runRemoteCall(client, remoteCallMessage);

      // Return the result.
      OidKeyHashMap<Integer> remoteWrites =
          TransactionManager.getInstance().finishAsyncCall();
      return new AsyncCallMessage.Response(result, remoteWrites);
    } catch (RuntimeException e) {
      Throwable cause = e.getCause();
      if (cause instanceof IllegalArgumentException
          || cause instanceof SecurityException
          || cause instanceof IllegalAccessException
          || cause instanceof InvocationTargetException
          || cause instanceof NoSuchMethodException
          || cause instanceof RuntimeException)
        throw new RemoteCallException(cause);

      throw e;
    } finally {
      TransactionManager.getInstance().associateLog(null);
    }
  }

  /**
   * In each message handler, we maintain the invariant that upon exit, the
   * worker's TransactionManager is associated with a null log.
   */
  @Override
  public AbortTransactionMessage.Response handle(
      RemoteIdentity<RemoteWorker> client,
      AbortTransactionMessage abortTransactionMessage) {
    // XXX TODO Security checks.
    Log log =
        TransactionRegistry.getInnermostLog(abortTransactionMessage.tid.topTid);
    if (log != null) {
      TransactionManager tm = TransactionManager.getInstance();
      tm.associateAndSyncLog(log, abortTransactionMessage.tid);
      tm.abortTransaction();
      tm.associateLog(null);
    }

    return new AbortTransactionMessage.Response();
  }

  @Override
  public PrepareTransactionMessage.Response handle(
      RemoteIdentity<RemoteWorker> client,
      PrepareTransactionMessage prepareTransactionMessage)
      throws TransactionPrepareFailedException {
    // XXX TODO Security checks.
    Log log =
        TransactionRegistry.getInnermostLog(prepareTransactionMessage.tid);
    if (log == null)
      throw new TransactionPrepareFailedException("No such transaction");

    // Commit up to the top level.
    TransactionManager tm = TransactionManager.getInstance();
    TransactionID topTid = log.getTid();
    while (topTid.depth > 0)
      topTid = topTid.parent;
    tm.associateAndSyncLog(log, topTid);

    Pair<Map<RemoteStore, LongKeyMap<SerializedObject>>, Long> p = null;
    try {
      p = tm.sendPrepareMessages();
      tm.getCurrentLog().longerContracts = p.first;
    } catch (TransactionRestartingException e) {
      throw new TransactionPrepareFailedException(e);
    } finally {
      tm.associateLog(null);
    }

    return new PrepareTransactionMessage.Response(p.second,
        new LongKeyHashMap<SerializedObject>());
  }

  /**
   * In each message handler, we maintain the invariant that upon exit, the
   * worker's TransactionManager is associated with a null log.
   */
  @Override
  public CommitTransactionMessage.Response handle(
      RemoteIdentity<RemoteWorker> client,
      CommitTransactionMessage commitTransactionMessage)
      throws TransactionCommitFailedException {
    // XXX TODO Security checks.
    Log log = TransactionRegistry
        .getInnermostLog(commitTransactionMessage.transactionID);
    if (log == null) {
      // If no log exists, assume that another worker in the transaction has
      // already committed the requested transaction.
      return new CommitTransactionMessage.Response();
    }

    TransactionManager tm = TransactionManager.getInstance();
    tm.associateLog(log);
    try {
      tm.sendCommitMessagesAndCleanUp();
    } catch (TransactionAtomicityViolationException e) {
      tm.associateLog(null);
      throw new TransactionCommitFailedException("Atomicity violation");
    }

    return new CommitTransactionMessage.Response();
  }

  @Override
  public DirtyReadMessage.Response handle(RemoteIdentity<RemoteWorker> client,
      DirtyReadMessage readMessage) {
    Log log = TransactionRegistry.getInnermostLog(readMessage.tid.topTid);
    if (log == null) return new DirtyReadMessage.Response(null);

    _Impl obj = new _Proxy(readMessage.store, readMessage.onum).fetch();

    // Ensure this worker owns the object.
    synchronized (obj) {
      if (!obj.$isOwned) {
        return new DirtyReadMessage.Response(null);
      }
    }

    // Run the authorization in the remote worker's transaction.
    TransactionManager tm = TransactionManager.getInstance();
    tm.associateAndSyncLog(log, readMessage.tid);

    // Ensure that the remote worker is allowed to read the object.
    Label label = obj.get$$updateLabel();
    if (!AuthorizationUtil.isReadPermitted(client.principal, label.$getStore(),
        label.$getOnum())) {
      obj = null;
    }

    tm.associateLog(null);

    return new DirtyReadMessage.Response(obj);
  }

  @Override
  public TakeOwnershipMessage.Response handle(
      RemoteIdentity<RemoteWorker> client, TakeOwnershipMessage msg)
      throws TakeOwnershipFailedException {
    Log log = TransactionRegistry.getInnermostLog(msg.tid.topTid);
    if (log == null) throw new TakeOwnershipFailedException(MessageFormat
        .format("Object fab://{0}/{1} is not owned by {2} in transaction {3}",
            msg.store.name(), msg.onum, null, msg.tid));

    _Impl obj = new _Proxy(msg.store, msg.onum).fetch();

    // Ensure this worker owns the object.
    synchronized (obj) {
      if (!obj.$isOwned) {
        throw new TakeOwnershipFailedException(MessageFormat.format(
            "Object fab://{0}/{1} is not owned by {2} in transaction {3}",
            msg.store.name(), msg.onum, null, msg.tid));
      }

      // Run the authorization in the remote worker transaction.
      TransactionManager tm = TransactionManager.getInstance();
      tm.associateAndSyncLog(log, msg.tid);

      // Ensure that the remote worker is allowed to write the object.
      Label label = obj.get$$updateLabel();
      boolean authorized = AuthorizationUtil.isReadAndWritePermitted(
          client.principal, label.$getStore(), label.$getOnum());

      tm.associateLog(null);

      if (!authorized) {
        Principal p = client.principal;
        throw new TakeOwnershipFailedException(MessageFormat.format(
            "{0} is not authorized to own fab://{1}/{2}",
            p.$getStore() + "/" + p.$getOnum(), msg.store.name(), msg.onum));
      }

      // Relinquish ownership.
      obj.$isOwned = false;
      return new TakeOwnershipMessage.Response();
    }
  }

  @Override
  public ObjectUpdateMessage.Response handle(
      RemoteIdentity<RemoteWorker> client,
      ObjectUpdateMessage objectUpdateMessage) {

    Worker worker = Worker.getWorker();
    final List<Long> response;

    if (objectUpdateMessage.groups == null) {
      response = inProcessRemoteWorker.notifyObjectUpdates(
          objectUpdateMessage.store, objectUpdateMessage.globs);
    } else {
      RemoteStore store = worker.getStore(client.node.name);
      response = inProcessRemoteWorker.notifyObjectUpdates(store,
          objectUpdateMessage.onums, objectUpdateMessage.groups);
    }

    return new ObjectUpdateMessage.Response(response);
  }

  @Override
  public InterWorkerStalenessMessage.Response handle(
      RemoteIdentity<RemoteWorker> client,
      InterWorkerStalenessMessage stalenessCheckMessage) {

    TransactionID tid = stalenessCheckMessage.tid;
    if (tid == null) return new InterWorkerStalenessMessage.Response(false);

    TransactionManager tm = TransactionManager.getInstance();
    Log log = TransactionRegistry.getOrCreateInnermostLog(tid);
    tm.associateAndSyncLog(log, tid);

    boolean result = tm.checkForStaleObjects();

    tm.associateLog(null);
    return new InterWorkerStalenessMessage.Response(result);
  }
}
