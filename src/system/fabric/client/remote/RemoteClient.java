package fabric.client.remote;

import fabric.client.*;
import fabric.client.remote.messages.GetPrincipalMessage;
import fabric.client.remote.messages.ReadMessage;
import fabric.client.remote.messages.RemoteCallMessage;
import fabric.client.remote.messages.TakeOwnershipMessage;
import fabric.client.transaction.Log;
import fabric.client.transaction.TransactionManager;
import fabric.client.transaction.TransactionRegistry;
import fabric.common.TransactionID;
import fabric.common.exceptions.InternalError;
import fabric.lang.NodePrincipal;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.PrepareTransactionMessage;

/**
 * Encapsulates a remote client. This class maintains the connection to the
 * remote client and manages all communication. RemoteClients can only be
 * obtained through the <code>RemoteCallManager.getClient()</code> interface.
 * For each remote client, there should be at most one <code>RemoteClient</code>
 * object representing that client.
 */
public final class RemoteClient extends RemoteNode {

  /**
   * This should only be called by fabric.client.Client. If you want a
   * RemoteClient, use fabric.client.Client.getClient() instead.
   */
  public RemoteClient(String name) {
    super(name);
  }

  @Override
  protected boolean supportsUnencrypted() {
    return false;
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

  public void prepareTransaction(long tid, long commitTime) throws UnreachableNodeException,
      TransactionPrepareFailedException {
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
   * Informs the remote client that a transaction is aborting.
   * 
   * @param tid
   *          the tid for the transaction that is aborting.
   */
  public void abortTransaction(TransactionID tid)
      throws UnreachableNodeException {
    new AbortTransactionMessage(tid).send(this);
  }

  /**
   * Reads the given object from the remote client, updating the object's state.
   * 
   * @param tid
   *          the tid for the current transaction.
   */
  public void readObject(TransactionID tid, _Impl obj) {
    _Impl remoteObj = readObject(tid, obj.$getCore(), obj.$getOnum());

    if (remoteObj == null)
      throw new InternalError("Inter-client object read failed.");
    obj.$copyAppStateFrom(remoteObj);
  }

  public _Impl readObject(TransactionID tid, Core core, long onum) {
    ReadMessage.Response response = new ReadMessage(tid, core, onum).send(this);
    return response.obj;
  }

  /**
   * Unsets the ownership bit for the given object at the remote client.
   * 
   * @param tid
   *          the tid for the current transaction.
   */
  public void takeOwnership(TransactionID tid, Core core, long onum) {
    TakeOwnershipMessage.Response response =
        new TakeOwnershipMessage(tid, core, onum).send(this);
    if (!response.success) {
      throw new InternalError(
          "Unable to take ownership of object fab://" + core.name()
              + "/" + onum + " from " + name + " -- either " + name
              + " doesn't own the object or authorization has failed.");
    }
  }

  @Override
  public String name() {
    return name;
  }

  /**
   * @return the principal associated with the remote client.
   */
  public NodePrincipal getPrincipal() {
    GetPrincipalMessage.Response response =
        new GetPrincipalMessage().send(this);
    final NodePrincipal principal = response.principal;

    boolean authenticated =
        Client.runInTransaction(null, new Client.Code<Boolean>() {
          public Boolean run() {
            return principal.name().equals(name);
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
}
