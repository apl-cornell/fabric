package fabric.worker.remote;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import fabric.common.AuthorizationUtil;
import fabric.common.TransactionID;
import fabric.common.exceptions.ProtocolError;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Label;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.ObjectUpdateMessage;
import fabric.messages.PrepareTransactionMessage;
import fabric.net.AbstractMessageHandlerThread;
import fabric.net.RemoteNode;
import fabric.worker.RemoteStore;
import fabric.worker.TransactionAtomicityViolationException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.remote.messages.GetPrincipalMessage;
import fabric.worker.remote.messages.ReadMessage;
import fabric.worker.remote.messages.RemoteCallMessage;
import fabric.worker.remote.messages.TakeOwnershipMessage;
import fabric.worker.remote.messages.GetPrincipalMessage.Response;
import fabric.worker.transaction.Log;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.transaction.TransactionRegistry;

public class MessageHandlerThread extends
    AbstractMessageHandlerThread<SessionAttributes, MessageHandlerThread> {

  private final RemoteCallManager rcm;

  private final Worker worker;

  /**
   * A factory for creating MessageHandlerThread instances. This is used by
   * AbstractMessageHandlerThread.Pool.
   */
  static class Factory implements
      AbstractMessageHandlerThread.Factory<MessageHandlerThread> {
    private final RemoteCallManager rcm;

    Factory(RemoteCallManager rcm) {
      this.rcm = rcm;
    }

    public MessageHandlerThread createMessageHandler(
        fabric.net.AbstractMessageHandlerThread.Pool<MessageHandlerThread> pool) {
      return new MessageHandlerThread(rcm, pool);
    }
  }

  /**
   * Instantiates a new message-handler thread and starts it running.
   */
  public MessageHandlerThread(RemoteCallManager rcm,
      Pool<MessageHandlerThread> pool) {
    super("RCM message handler", pool);
    this.rcm = rcm;
    this.worker = Worker.getWorker();
    TransactionManager.startThread(this);
  }

  @Override
  protected boolean shuttingDown() {
    return rcm.shuttingDown;
  }

  public RemoteCallMessage.Response handle(
      final RemoteCallMessage remoteCallMessage) throws RemoteCallException,
      ProtocolError {
    if (session.isDissemConnection)
      throw new ProtocolError("Message not supported.");

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
            args[0] = session.remotePrincipal;
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
          || cause instanceof NoSuchMethodException)
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
  public void handle(AbortTransactionMessage abortTransactionMessage)
      throws ProtocolError {
    if (session.isDissemConnection)
      throw new ProtocolError("Message not supported.");

    // XXX TODO Security checks.
    Log log =
        TransactionRegistry.getInnermostLog(abortTransactionMessage.tid.topTid);
    if (log == null) return;

    TransactionManager tm = TransactionManager.getInstance();
    tm.associateAndSyncLog(log, abortTransactionMessage.tid);
    tm.abortTransaction();
    tm.associateLog(null);
  }

  public PrepareTransactionMessage.Response handle(
      PrepareTransactionMessage prepareTransactionMessage) throws ProtocolError {
    if (session.isDissemConnection)
      throw new ProtocolError("Message not supported.");

    // XXX TODO Security checks.
    Log log =
        TransactionRegistry.getInnermostLog(prepareTransactionMessage.tid);
    if (log == null)
      return new PrepareTransactionMessage.Response("No such transaction");

    TransactionManager tm = TransactionManager.getInstance();
    tm.associateLog(log);

    // Commit up to the top level.
    for (int i = 0; i < log.getTid().depth; i++)
      tm.commitTransactionAt(prepareTransactionMessage.commitTime);

    Map<RemoteNode, TransactionPrepareFailedException> failures =
        tm.sendPrepareMessages(prepareTransactionMessage.commitTime);

    tm.associateLog(null);

    if (failures.isEmpty())
      return new PrepareTransactionMessage.Response();
    else return new PrepareTransactionMessage.Response(
        "Transaction prepare failed.");
  }

  /**
   * In each message handler, we maintain the invariant that upon exit, the
   * worker's TransactionManager is associated with a null log.
   */
  public CommitTransactionMessage.Response handle(
      CommitTransactionMessage commitTransactionMessage) throws ProtocolError {
    if (session.isDissemConnection)
      throw new ProtocolError("Message not supported.");

    // XXX TODO Security checks.
    Log log =
        TransactionRegistry
            .getInnermostLog(commitTransactionMessage.transactionID);
    if (log == null) {
      // If no log exists, assume that another worker in the transaction has
      // already committed the requested transaction.
      return new CommitTransactionMessage.Response(true);
    }

    TransactionManager tm = TransactionManager.getInstance();
    tm.associateLog(log);
    try {
      tm.sendCommitMessagesAndCleanUp();
    } catch (TransactionAtomicityViolationException e) {
      tm.associateLog(null);
      return new CommitTransactionMessage.Response(false);
    }

    return new CommitTransactionMessage.Response(true);
  }

  public ReadMessage.Response handle(ReadMessage readMessage)
      throws ProtocolError {
    if (session.isDissemConnection)
      throw new ProtocolError("Message not supported.");

    Log log = TransactionRegistry.getInnermostLog(readMessage.tid.topTid);
    if (log == null) return new ReadMessage.Response(null);

    _Impl obj = new _Proxy(readMessage.store, readMessage.onum).fetch();

    // Ensure this worker owns the object.
    synchronized (obj) {
      if (!obj.$isOwned) {
        return new ReadMessage.Response(null);
      }
    }

    // Run the authorization in the remote worker's transaction.
    TransactionManager tm = TransactionManager.getInstance();
    tm.associateAndSyncLog(log, readMessage.tid);

    // Ensure that the remote worker is allowed to read the object.
    Label label = obj.get$label();
    if (!AuthorizationUtil.isReadPermitted(session.remotePrincipal, label
        .$getStore(), label.$getOnum())) {
      obj = null;
    }

    tm.associateLog(null);

    return new ReadMessage.Response(obj);
  }

  public TakeOwnershipMessage.Response handle(
      TakeOwnershipMessage takeOwnershipMessage) throws ProtocolError {
    if (session.isDissemConnection)
      throw new ProtocolError("Message not supported.");

    Log log =
        TransactionRegistry.getInnermostLog(takeOwnershipMessage.tid.topTid);
    if (log == null) return new TakeOwnershipMessage.Response(false);

    _Impl obj =
        new _Proxy(takeOwnershipMessage.store, takeOwnershipMessage.onum)
            .fetch();

    // Ensure this worker owns the object.
    synchronized (obj) {
      if (!obj.$isOwned) {
        return new TakeOwnershipMessage.Response(false);
      }
    }

    // Run the authorization in the remote worker transaction.
    TransactionManager tm = TransactionManager.getInstance();
    tm.associateAndSyncLog(log, takeOwnershipMessage.tid);

    // Ensure that the remote worker is allowed to write the object.
    Label label = obj.get$label();
    boolean authorized =
        AuthorizationUtil.isWritePermitted(session.remotePrincipal, label
            .$getStore(), label.$getOnum());

    tm.associateLog(null);

    if (authorized) {
      // Relinquish ownership.
      obj.$isOwned = false;
      return new TakeOwnershipMessage.Response(true);
    }

    return new TakeOwnershipMessage.Response(false);
  }

  public Response handle(GetPrincipalMessage getPrincipalMessage) {
    return new GetPrincipalMessage.Response(worker.getPrincipal());
  }

  public ObjectUpdateMessage.Response handle(
      ObjectUpdateMessage objectUpdateMessage) {
    boolean response;
    if (objectUpdateMessage.group == null) {
      RemoteStore store = worker.getStore(objectUpdateMessage.store);
      response =
          worker.updateDissemCaches(store, objectUpdateMessage.onum,
              objectUpdateMessage.glob);
    } else {
      RemoteStore store = worker.getStore(session.remoteNodeName);
      response = worker.updateCache(store, objectUpdateMessage.group);
    }

    return new ObjectUpdateMessage.Response(response);
  }
}
