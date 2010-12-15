package fabric.worker.remote;

import java.io.IOException;

import fabric.common.ObjectGroup;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.net.SubSocket;
import fabric.common.net.SubSocketFactory;
import fabric.common.net.handshake.BogusAuthenticatedHandshake;
import fabric.common.net.handshake.HandshakeProtocol;
import fabric.common.net.naming.DefaultNameService;
import fabric.common.net.naming.DefaultNameService.PortType;
import fabric.common.net.naming.NameService;
import fabric.dissemination.Glob;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Principal;
import fabric.messages.*;
import fabric.messages.Message.NoException;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;
import fabric.worker.Store;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
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
  
  private transient final SubSocketFactory subSocketFactory;

  /**
   * This should only be called by fabric.worker.Worker. If you want a
   * RemoteWorker, use fabric.worker.Worker.getWorker() instead.
   */
  public RemoteWorker(String name) {
    super(name);
    
    try {
      HandshakeProtocol protocol = new BogusAuthenticatedHandshake();
      NameService nameService = new DefaultNameService(PortType.WORKER);
      this.subSocketFactory = new SubSocketFactory(protocol, nameService);
    } catch (IOException e) {
      throw new InternalError(e);
    }
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
    tm.associateAndSyncLog(innermost, tid);

    // Merge in the update map we got.
    if (response.updateMap != null)
      tm.getUpdateMap().putAll(response.updateMap);

    return response.result;
  }

  public void prepareTransaction(long tid, long commitTime)
       throws UnreachableNodeException,
              TransactionPrepareFailedException {
    send(new PrepareTransactionMessage(tid, commitTime));
  }

  public void commitTransaction(long tid) throws UnreachableNodeException,
      TransactionCommitFailedException {
    send(new CommitTransactionMessage(tid));
  }

  /**
   * Informs the remote worker that a transaction is aborting.
   * 
   * @param tid
   *          the tid for the transaction that is aborting.
   */
  public void abortTransaction(TransactionID tid)
      throws AccessException, UnreachableNodeException {
    send(new AbortTransactionMessage(tid));
  }

  /**
   * Reads the given object from the remote worker, updating the object's state.
   * 
   * @param tid
   *          the tid for the current transaction.
   */
  public void readObject(TransactionID tid, _Impl obj) {
    _Impl remoteObj;
    try {
      remoteObj = readObject(tid, obj.$getStore(), obj.$getOnum());
    } catch (AccessException e) {
      throw new InternalError("Inter-worker object read failed.", e);
    }

    if (remoteObj == null)
      throw new InternalError("Inter-worker object read failed.");
    obj.$copyAppStateFrom(remoteObj);
  }

  public _Impl readObject(TransactionID tid, Store store, long onum)
      throws AccessException {
    DirtyReadMessage.Response response = send(new DirtyReadMessage(tid, store, onum));
    return response.obj;
  }

  /**
   * Unsets the ownership bit for the given object at the remote worker.
   * 
   * @param tid
   *          the tid for the current transaction.
   */
  public void takeOwnership(TransactionID tid, Store store, long onum) {
    try {
      send(new TakeOwnershipMessage(tid, store, onum));
    } catch(TakeOwnershipFailedException e) {
      throw new InternalError(e);
    }
  }

  /**
   * @return the principal associated with the remote worker.
   */
  public Principal getPrincipal() {
    try {
      SubSocket socket = subSocketFactory.createSocket(name);
      Principal principal = socket.getPrincipal();
      socket.close();
      return principal;
    } catch (IOException e) {
      throw new NotImplementedException(e);
    }
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
    ObjectUpdateMessage.Response response;
    try {
      response = send(new ObjectUpdateMessage(store, onum, glob));
    } catch (NoException e) {
      // This is not possible.
      throw new InternalError(e);
    }
    return response.resubscribe;
  }

  /**
   * Notifies the worker that an object has been updated.
   * 
   * @return whether the node is resubscribing to the object.
   */
  public boolean notifyObjectUpdate(long onum, ObjectGroup group) {
    ObjectUpdateMessage.Response response;
    try {
      response = send(new ObjectUpdateMessage(onum, group));
    } catch (NoException e) {
      // This is not possible.
      throw new InternalError(e);
    }
    return response.resubscribe;
  }

  /**
   * Asks the worker to check that the objects used in a given transaction are
   * up-to-date.
   */
  public boolean checkForStaleObjects(TransactionID tid) {
    InterWorkerStalenessMessage.Response response;
    try {
      response = send(new InterWorkerStalenessMessage(tid));
    } catch (NoException e) {
      // This is not possible.
      throw new InternalError(e);
    }
    return response.result;
  }
  
  private <R extends Message.Response, E extends FabricException> R send(
      Message<R, E> message) throws E {
    return send(subSocketFactory, message);
  }
}
