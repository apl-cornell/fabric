package fabric.client.remote;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Logger;

import jif.lang.Label;
import fabric.client.Client;
import fabric.client.RemoteNode;
import fabric.client.TransactionAtomicityViolationException;
import fabric.client.TransactionPrepareFailedException;
import fabric.client.remote.messages.GetPrincipalMessage;
import fabric.client.remote.messages.ReadMessage;
import fabric.client.remote.messages.RemoteCallMessage;
import fabric.client.remote.messages.TakeOwnershipMessage;
import fabric.client.remote.messages.GetPrincipalMessage.Response;
import fabric.client.transaction.Log;
import fabric.client.transaction.TransactionManager;
import fabric.client.transaction.TransactionRegistry;
import fabric.common.AbstractWorkerThread;
import fabric.common.AuthorizationUtil;
import fabric.common.TransactionID;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.PrepareTransactionMessage;

public class Worker extends AbstractWorkerThread<SessionAttributes, Worker> {

  static final Logger logger = Logger.getLogger("fabric.client.remote.Worker");

  private final RemoteCallManager rcm;

  /**
   * A factory for creating Worker instances. This is used by WorkerThread.Pool.
   */
  static class Factory implements AbstractWorkerThread.Factory<Worker> {
    private final RemoteCallManager rcm;

    Factory(RemoteCallManager rcm) {
      this.rcm = rcm;
    }

    public Worker createWorker(
        fabric.common.AbstractWorkerThread.Pool<Worker> pool) {
      return new Worker(rcm, pool);
    }
  }

  /**
   * Instantiates a new worker thread and starts it running.
   */
  public Worker(RemoteCallManager rcm, Pool<Worker> pool) {
    super("RCM worker", pool);
    this.rcm = rcm;
    TransactionManager.startThread(this);
  }

  @Override
  protected boolean shuttingDown() {
    return rcm.shuttingDown;
  }

  @Override
  protected Logger getLogger() {
    return logger;
  }

  public RemoteCallMessage.Response handle(
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
      Object result = Client.runInSubTransaction(new Client.Code<Object>() {
        public Object run() {
          // This is ugly. Wrap all exceptions that can be thrown with a runtime
          // exception and do the actual handling below.
          try {
            // Ensure the receiver and arguments have the right dynamic types.
            fabric.lang.Object receiver =
                remoteCallMessage.receiver.fetch().$getProxy();
            Object[] args = new Object[remoteCallMessage.args.length + 1];
            args[0] = session.client;
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
  public void handle(AbortTransactionMessage abortTransactionMessage) {
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
      PrepareTransactionMessage prepareTransactionMessage) {
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
      CommitTransactionMessage commitTransactionMessage) {
    // XXX TODO Security checks.
    Log log =
        TransactionRegistry
            .getInnermostLog(commitTransactionMessage.transactionID);
    if (log == null) {
      // If no log exists, assume that another client in the transaction has
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

  public ReadMessage.Response handle(ReadMessage readMessage) {
    Log log = TransactionRegistry.getInnermostLog(readMessage.tid.topTid);
    if (log == null) return new ReadMessage.Response(null);

    _Impl obj = new _Proxy(readMessage.core, readMessage.onum).fetch();

    // Ensure this client owns the object.
    synchronized (obj) {
      if (!obj.$isOwned) {
        return new ReadMessage.Response(null);
      }
    }

    // Run the authorization in the remote client's transaction.
    TransactionManager tm = TransactionManager.getInstance();
    tm.associateAndSyncLog(log, readMessage.tid);

    // Ensure that the remote client is allowed to read the object.
    Label label = obj.get$label();
    if (!AuthorizationUtil.isReadPermitted(session.client, label.$getCore(),
        label.$getOnum())) {
      obj = null;
    }

    tm.associateLog(null);

    return new ReadMessage.Response(obj);
  }

  public TakeOwnershipMessage.Response handle(
      TakeOwnershipMessage takeOwnershipMessage) {
    Log log =
        TransactionRegistry.getInnermostLog(takeOwnershipMessage.tid.topTid);
    if (log == null) return new TakeOwnershipMessage.Response(false);

    _Impl obj =
        new _Proxy(takeOwnershipMessage.core, takeOwnershipMessage.onum)
            .fetch();

    // Ensure this client owns the object.
    synchronized (obj) {
      if (!obj.$isOwned) {
        return new TakeOwnershipMessage.Response(false);
      }
    }

    // Run the authorization in the remote client transaction.
    TransactionManager tm = TransactionManager.getInstance();
    tm.associateAndSyncLog(log, takeOwnershipMessage.tid);

    // Ensure that the remote client is allowed to write the object.
    Label label = obj.get$label();
    boolean authorized =
        AuthorizationUtil.isWritePermitted(session.client, label.$getCore(),
            label.$getOnum());

    tm.associateLog(null);

    if (authorized) {
      // Relinquish ownership.
      obj.$isOwned = false;
      return new TakeOwnershipMessage.Response(true);
    }

    return new TakeOwnershipMessage.Response(false);
  }

  public Response handle(GetPrincipalMessage getPrincipalMessage) {
    return new GetPrincipalMessage.Response(Client.getClient().getPrincipal());
  }
}
