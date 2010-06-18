package fabric.worker.remote;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import fabric.common.AuthorizationUtil;
import fabric.common.TransactionID;
import fabric.common.Threading.NamedRunnable;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.exceptions.ProtocolError;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Label;
import fabric.lang.security.NodePrincipal;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.DirtyReadMessage;
import fabric.messages.GetPrincipalMessage;
import fabric.messages.MessageToWorkerHandler;
import fabric.messages.ObjectUpdateMessage;
import fabric.messages.PrepareTransactionMessage;
import fabric.messages.RemoteCallMessage;
import fabric.messages.TakeOwnershipMessage;
import fabric.messages.GetPrincipalMessage.Response;
import fabric.net.RemoteNode;
import fabric.worker.RemoteStore;
import fabric.worker.TransactionAtomicityViolationException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.transaction.Log;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.transaction.TransactionRegistry;

public class MessageHandlerThread
     extends NamedRunnable
  implements MessageToWorkerHandler 
{

  public MessageHandlerThread(String name) {
    super(name);
    // TODO Auto-generated constructor stub
    throw new NotImplementedException();
  }

  private final SessionAttributes session;  
  
  private final Worker worker;

  public RemoteCallMessage.Response handle(
      NodePrincipal p, final RemoteCallMessage remoteCallMessage) throws RemoteCallException,
      ProtocolError {
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

  public PrepareTransactionMessage.Response handle(
      NodePrincipal p, PrepareTransactionMessage prepareTransactionMessage) {
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
      NodePrincipal p, CommitTransactionMessage commitTransactionMessage) {
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

  public DirtyReadMessage.Response handle(NodePrincipal p, DirtyReadMessage readMessage)
      throws ProtocolError {
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
    if (!AuthorizationUtil.isReadPermitted(session.remotePrincipal, label
        .$getStore(), label.$getOnum())) {
      obj = null;
    }

    tm.associateLog(null);

    return new DirtyReadMessage.Response(obj);
  }

  public TakeOwnershipMessage.Response handle(
      NodePrincipal p, TakeOwnershipMessage takeOwnershipMessage) throws ProtocolError {
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

  public Response handle(NodePrincipal p, GetPrincipalMessage getPrincipalMessage) {
    return new GetPrincipalMessage.Response(worker.getPrincipal());
  }

  public ObjectUpdateMessage.Response handle(NodePrincipal p, ObjectUpdateMessage objectUpdateMessage) {
    boolean response;
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
  protected void runImpl() {
    // TODO Auto-generated method stub
    throw new NotImplementedException();
  }
}
