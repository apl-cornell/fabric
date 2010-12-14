package fabric.worker.remote;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import fabric.common.AuthorizationUtil;
import fabric.common.TransactionID;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.net.SubServerSocket;
import fabric.common.net.SubServerSocketFactory;
import fabric.common.net.handshake.HandshakeImpl;
import fabric.common.net.handshake.HandshakeProtocol;
import fabric.common.net.naming.NameService;
import fabric.common.net.naming.PropertyNameService;
import fabric.common.net.naming.PropertyNameService.PortType;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Label;
import fabric.lang.security.NodePrincipal;
import fabric.messages.*;
import fabric.messages.GetPrincipalMessage.Response;
import fabric.worker.TransactionAtomicityViolationException;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
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
  private final Worker worker;
  
  /**
   * @param name the worker's DNS hostname.
   */
  public RemoteCallManager(Worker worker) {
    super(worker.name);
    this.worker = worker;
    
    try {
      HandshakeProtocol handshake = new HandshakeImpl();
      NameService nameService = new PropertyNameService(PortType.WORKER);
      this.factory = new SubServerSocketFactory(handshake, nameService);
    } catch (IOException e) {
      throw new InternalError("Failed to initialize RemoteCallManager", e);
    }
  }

  @Override
  protected SubServerSocket createServerSocket() {
    return factory.createServerSocket();
  }
  @Override
  public RemoteCallMessage.Response handle(final NodePrincipal p,
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

      // Merge in the update map we got.
      tm.getUpdateMap().putAll(remoteCallMessage.updateMap);
    }

    try {
      // Execute the requested method.
      Object result = Worker.runInSubTransaction(new Worker.Code<Object>() {
        public Object run() {
          // This is ugly. Wrap all exceptions that can be thrown with a runtime
          // exception and do the actual handling below.
          try {
            // Ensure the receiver and arguments have the right dynamic types.
            fabric.lang.Object receiver =
                remoteCallMessage.receiver.fetch().$getProxy();
            Object[] args = new Object[remoteCallMessage.args.length + 1];
            args[0] = p;
            for (int i = 0; i < remoteCallMessage.args.length; i++) {
              Object arg = remoteCallMessage.args[i];
              if (arg instanceof fabric.lang.Object) {
                arg = ((fabric.lang.Object) arg).fetch().$getProxy();
              }
              args[i + 1] = arg;
            }

            return remoteCallMessage.getMethod().invoke(receiver, args);
          } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
          } catch (SecurityException e) {
            throw new RuntimeException(e);
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
          } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
          } catch (RuntimeException e) {
            throw new RuntimeException(e);
          }
        }
      });

      // Return the result.
      UpdateMap updateMap = TransactionManager.getInstance().getUpdateMap();
      return new RemoteCallMessage.Response(result, updateMap);
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

  /**
   * In each message handler, we maintain the invariant that upon exit, the
   * worker's TransactionManager is associated with a null log.
   */
  @Override
  public AbortTransactionMessage.Response handle(NodePrincipal p, AbortTransactionMessage abortTransactionMessage) {
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
  public PrepareTransactionMessage.Response handle(NodePrincipal p,
                                                   PrepareTransactionMessage prepareTransactionMessage)
  throws TransactionPrepareFailedException
  {
    // XXX TODO Security checks.
    Log log =
        TransactionRegistry.getInnermostLog(prepareTransactionMessage.tid);
    if (log == null)
      throw new TransactionPrepareFailedException("No such transaction");

    // Commit up to the top level.
    TransactionManager tm = TransactionManager.getInstance();
    TransactionID topTid = log.getTid();
    while (topTid.depth > 0) topTid = topTid.parent;
    tm.associateAndSyncLog(log, topTid);

    try {
      tm.sendPrepareMessages(prepareTransactionMessage.commitTime);
    } finally {
      tm.associateLog(null);
    }


    try {
      tm.sendPrepareMessages(prepareTransactionMessage.commitTime);
    } finally {
      tm.associateLog(null);
    }

    return new PrepareTransactionMessage.Response();
  }

  /**
   * In each message handler, we maintain the invariant that upon exit, the
   * worker's TransactionManager is associated with a null log.
   */
  @Override
  public CommitTransactionMessage.Response handle(NodePrincipal p,
                                                  CommitTransactionMessage commitTransactionMessage)
  throws TransactionCommitFailedException {
    // XXX TODO Security checks.
    Log log =
        TransactionRegistry
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
  public DirtyReadMessage.Response handle(NodePrincipal p, DirtyReadMessage readMessage) {
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
    Label label = obj.get$label();
    if (!AuthorizationUtil.isReadPermitted(p, label
        .$getStore(), label.$getOnum())) {
      obj = null;
    }

    tm.associateLog(null);

    return new DirtyReadMessage.Response(obj);
  }

  @Override
  public TakeOwnershipMessage.Response handle(NodePrincipal p, TakeOwnershipMessage msg)
  throws TakeOwnershipFailedException {
    Log log =
        TransactionRegistry.getInnermostLog(msg.tid.topTid);
    if (log == null) 
      throw new TakeOwnershipFailedException(MessageFormat.format(
          "Object fab://{0}/{1} is not owned by {2} in transaction {3}",
          msg.store.name(), msg.onum, null, msg.tid));

    _Impl obj =
        new _Proxy(msg.store, msg.onum)
            .fetch();

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
      Label label = obj.get$label();
      boolean authorized =
        AuthorizationUtil.isWritePermitted(p, label
            .$getStore(), label.$getOnum());

      tm.associateLog(null);

      if (!authorized)
        throw new TakeOwnershipFailedException(MessageFormat.format(
            "{0} is not authorized to own fab://{1}/{2}", p.$getStore() + "/"
                + p.$getOnum(), msg.store.name(), msg.onum));
      
      // Relinquish ownership.
      obj.$isOwned = false;
      return new TakeOwnershipMessage.Response();
    }
  }

  @Override
  public Response handle(NodePrincipal p, GetPrincipalMessage getPrincipalMessage) {
    return new GetPrincipalMessage.Response(worker.getPrincipal());
  }

  @Override
  public ObjectUpdateMessage.Response handle(NodePrincipal p, ObjectUpdateMessage objectUpdateMessage) {
    if (objectUpdateMessage.group == null) {
      // TODO
      //RemoteStore store = worker.getStore(objectUpdateMessage.store);
      //objectUpdateMessage.glob.verifySignature(store.getPublicKey());

      //response =
      //    worker.updateDissemCaches(store, objectUpdateMessage.onum,
      //        objectUpdateMessage.glob);
      throw new NotImplementedException();
    } else {
      // TODO
      // RemoteStore store = worker.getStore(session.remoteNodeName);
      // objectUpdateMessage.glob.verifySignature(store.getPublicKey());

      // response = worker.updateCache(store, objectUpdateMessage.group);
      throw new NotImplementedException();
    }

    // TODO
    // return new ObjectUpdateMessage.Response(response);
  }

  @Override
  public InterWorkerStalenessMessage.Response handle(NodePrincipal p, InterWorkerStalenessMessage stalenessCheckMessage) {

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
