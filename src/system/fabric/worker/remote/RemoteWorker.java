package fabric.worker.remote;

import fabric.common.ObjectGroup;
import fabric.common.TransactionID;
import fabric.common.exceptions.FetchException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NotImplementedException;
import fabric.dissemination.Glob;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.security.NodePrincipal;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.DirtyReadMessage;
import fabric.messages.GetPrincipalMessage;
import fabric.messages.ObjectUpdateMessage;
import fabric.messages.PrepareTransactionMessage;
import fabric.messages.RemoteCallMessage;
import fabric.messages.TakeOwnershipMessage;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;
import fabric.worker.Store;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.transaction.Log;
import fabric.worker.transaction.TakeOwnershipFailedException;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.transaction.TransactionRegistry;

/**
 * Encapsulates a remote worker. This class maintains the connection to the
 * remote worker and manages all communication. RemoteWorkers can only be
 * obtained through the <code>RemoteCallManager.getWorker()</code> interface.
 * For each remote worker, there should be at most one <code>RemoteWorker</code>
 * object representing that worker.
 */
public final class RemoteWorker extends RemoteNode {

  /**
   * This should only be called by fabric.worker.Worker. If you want a
   * RemoteWorker, use fabric.worker.Worker.getWorker() instead.
   */
  public RemoteWorker(String name) {
    super(name);
  }

  public Object issueRemoteCall(_Proxy receiver, String methodName,
      Class<?>[] parameterTypes, Object[] args)
      throws UnreachableNodeException, RemoteCallException {
    TransactionManager tm = TransactionManager.getInstance();
    tm.registerRemoteCall(this);
 
    TransactionID tid = tm.getCurrentTid();
    UpdateMap updateMap = tm.getUpdateMap();

    Class<?> receiverClass =
        receiver.fetch().$getProxy().getClass().getEnclosingClass();

    RemoteCallMessage.Response response =
        send(new RemoteCallMessage(tid, updateMap, receiverClass, receiver,
            methodName, parameterTypes, args));

    // Commit any outstanding subtransactions that occurred as a result of the
    // remote call.
    Log innermost = TransactionRegistry.getInnermostLog(tid.topTid);
    tm.associateLog(innermost);
    for (int i = innermost.getTid().depth; i > tid.depth; i--)
      tm.commitTransaction();

    // Merge in the update map we got.
    if (response.updateMap != null)
      tm.getUpdateMap().putAll(response.updateMap);

    return response.result;
  }

  public void prepareTransaction(long tid, long commitTime)
      throws UnreachableNodeException, TransactionPrepareFailedException {
    PrepareTransactionMessage.Response response =
        send(new PrepareTransactionMessage(tid, commitTime));
  }

  public void commitTransaction(long tid) throws UnreachableNodeException,
      TransactionCommitFailedException {
    CommitTransactionMessage.Response response =
        send(new CommitTransactionMessage(tid));
  }

  /**
   * Informs the remote worker that a transaction is aborting.
   * 
   * @param tid
   *          the tid for the transaction that is aborting.
   */
  public void abortTransaction(TransactionID tid)
      throws UnreachableNodeException {
    send(new AbortTransactionMessage(tid));
  }

  /**
   * Reads the given object from the remote worker, updating the object's state.
   * 
   * @param tid
   *          the tid for the current transaction.
   */
  public void readObject(TransactionID tid, _Impl obj) {
    _Impl remoteObj = readObject(tid, obj.$getStore(), obj.$getOnum());

    if (remoteObj == null)
      throw new InternalError("Inter-worker object read failed.");
    obj.$copyAppStateFrom(remoteObj);
  }

  public _Impl readObject(TransactionID tid, Store store, long onum) {
    try {
      DirtyReadMessage.Response response = send(new DirtyReadMessage(tid, store, onum));
      return response.obj;
    } catch(FetchException e) {
      throw new NotImplementedException();
    }
  }

  /**
   * Unsets the ownership bit for the given object at the remote worker.
   * 
   * @param tid
   *          the tid for the current transaction.
   */
  public void takeOwnership(TransactionID tid, Store store, long onum) {
    try {
      TakeOwnershipMessage.Response response = send(new TakeOwnershipMessage(tid, store, onum));
    } catch(TakeOwnershipFailedException e) {
      throw new InternalError(e);
    }
  }

  /**
   * @return the principal associated with the remote worker.
   */
  public NodePrincipal getPrincipal() {
    GetPrincipalMessage.Response response =
        send(new GetPrincipalMessage());
    final NodePrincipal principal = response.principal;
    final String expectedPrincipalName;
    try {
      // Note: this check may not make sense anymore.  -mdg
      expectedPrincipalName = "cn=" + name;
    } catch (IllegalStateException e) {
      throw new InternalError(e);
    }

    boolean authenticated =
        Worker.runInTransaction(null, new Worker.Code<Boolean>() {
          public Boolean run() {
            return principal.name().equals(expectedPrincipalName);
          }
        });

    if (authenticated)
      return principal;
    else return null;
  }

  @Override
  public String toString() {
    return name;
  }

  /**
   * Notifies the dissemination node at the given worker that an object has been
   * updated.
   * 
   * @return whether the node is resubscribing to the object.
   */
  public boolean notifyObjectUpdate(String store, long onum, Glob glob) {
    ObjectUpdateMessage.Response response =
        send(new ObjectUpdateMessage(store, onum, glob));
    return response.resubscribe;
  }
  
  /**
   * Notifies the worker that an object has been updated.
   * 
   * @return whether the node is resubscribing to the object.
   */
  public boolean notifyObjectUpdate(long onum, ObjectGroup group) {
    ObjectUpdateMessage.Response response =
      send(new ObjectUpdateMessage(onum, group));
  return response.resubscribe;
  }

}
