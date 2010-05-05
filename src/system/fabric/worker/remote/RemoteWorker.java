package fabric.worker.remote;

import java.io.IOException;
import java.net.UnknownHostException;

import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.remote.messages.GetPrincipalMessage;
import fabric.worker.remote.messages.ReadMessage;
import fabric.worker.remote.messages.RemoteCallMessage;
import fabric.worker.remote.messages.TakeOwnershipMessage;
import fabric.worker.transaction.Log;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.transaction.TransactionRegistry;
import fabric.common.ObjectGroup;
import fabric.common.TransactionID;
import fabric.common.exceptions.InternalError;
import fabric.common.net.naming.SocketAddress;
import fabric.dissemination.Glob;
import fabric.lang.security.NodePrincipal;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.ObjectUpdateMessage;
import fabric.messages.PrepareTransactionMessage;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;

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
    super(name, true);
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
        new RemoteCallMessage(tid, updateMap, receiverClass, receiver,
            methodName, parameterTypes, args).send(this);

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
        new PrepareTransactionMessage(tid, commitTime).send(this);
    if (!response.success)
      throw new TransactionPrepareFailedException(response.message);
  }

  public void commitTransaction(long tid) throws UnreachableNodeException,
      TransactionCommitFailedException {
    CommitTransactionMessage.Response response =
        new CommitTransactionMessage(tid).send(this);
    if (!response.success)
      throw new TransactionCommitFailedException(response.message);
  }

  /**
   * Informs the remote worker that a transaction is aborting.
   * 
   * @param tid
   *          the tid for the transaction that is aborting.
   */
  public void abortTransaction(TransactionID tid)
      throws UnreachableNodeException {
    new AbortTransactionMessage(tid).send(this);
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
    ReadMessage.Response response = new ReadMessage(tid, store, onum).send(this);
    return response.obj;
  }

  /**
   * Unsets the ownership bit for the given object at the remote worker.
   * 
   * @param tid
   *          the tid for the current transaction.
   */
  public void takeOwnership(TransactionID tid, Store store, long onum) {
    TakeOwnershipMessage.Response response =
        new TakeOwnershipMessage(tid, store, onum).send(this);
    if (!response.success) {
      throw new InternalError("Unable to take ownership of object fab://"
          + store.name() + "/" + onum + " from " + name + " -- either " + name
          + " doesn't own the object or authorization has failed.");
    }
  }

  /**
   * @return the principal associated with the remote worker.
   */
  public NodePrincipal getPrincipal() {
    GetPrincipalMessage.Response response =
        new GetPrincipalMessage().send(this);
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
        new ObjectUpdateMessage(store, onum, glob).send(this);
    return response.resubscribe;
  }
  
  /**
   * Notifies the worker that an object has been updated.
   * 
   * @return whether the node is resubscribing to the object.
   */
  public boolean notifyObjectUpdate(long onum, ObjectGroup group) {
    ObjectUpdateMessage.Response response =
      new ObjectUpdateMessage(onum, group).send(this);
  return response.resubscribe;
  }

  @Override
  protected SocketAddress lookup() throws IOException {
    return Worker.getWorker().workerNameService.resolve(name);
  }
}
