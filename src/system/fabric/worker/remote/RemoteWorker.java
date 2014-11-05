package fabric.worker.remote;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import fabric.common.ClassRef;
import fabric.common.ObjectGroup;
import fabric.common.SerializedObjectAndTokens;
import fabric.common.TransactionID;
import fabric.common.WarrantyGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.net.SubSocket;
import fabric.common.net.SubSocketFactory;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.WarrantyGlob;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Principal;
import fabric.messages.AbortTransactionMessage;
import fabric.messages.CommitTransactionMessage;
import fabric.messages.DirtyReadMessage;
import fabric.messages.InterWorkerStalenessMessage;
import fabric.messages.Message;
import fabric.messages.Message.NoException;
import fabric.messages.ObjectUpdateMessage;
import fabric.messages.PrepareTransactionReadsMessage;
import fabric.messages.PrepareTransactionWritesMessage;
import fabric.messages.RemoteCallMessage;
import fabric.messages.TakeOwnershipMessage;
import fabric.messages.WarrantyRefreshMessage;
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
public class RemoteWorker extends RemoteNode<RemoteWorker> {

  private transient final SubSocketFactory<RemoteWorker> subSocketFactory;

  /**
   * This should only be called by fabric.worker.Worker. If you want a
   * RemoteWorker, use fabric.worker.Worker.getWorker() instead.
   */
  public RemoteWorker(String name) {
    this(name, Worker.getWorker().authToWorker);
  }

  RemoteWorker(Worker worker) {
    this(worker.getName(), worker.authToWorker);
  }

  private RemoteWorker(String name,
      SubSocketFactory<RemoteWorker> subSocketFactory) {
    super(name);
    this.subSocketFactory = subSocketFactory;
  }

  public Object issueRemoteCall(_Proxy receiver, String methodName,
      Class<?>[] parameterTypes, Object[] args)
      throws UnreachableNodeException, RemoteCallException {
    TransactionManager tm = TransactionManager.getInstance();
    tm.registerRemoteCall(this);

    TransactionID tid = tm.getCurrentTid();
    WriterMap writerMap = tm.getWriterMap();

    Class<? extends fabric.lang.Object> receiverClass =
        (Class<? extends fabric.lang.Object>) receiver.fetch().$getProxy()
            .getClass().getEnclosingClass();
    ClassRef receiverClassRef = ClassRef.makeRef(receiverClass);

    RemoteCallMessage.Response response =
        send(new RemoteCallMessage(tid, writerMap, receiverClassRef, receiver,
            methodName, parameterTypes, args));

    // Commit any outstanding subtransactions that occurred as a result of the
    // remote call.
    Log innermost = TransactionRegistry.getInnermostLog(tid.topTid);
    tm.associateAndSyncLog(innermost, tid);

    // Merge in the writer map we got.
    if (response.writerMap != null)
      tm.getWriterMap().putAll(response.writerMap);

    return response.result;
  }

  public long prepareTransactionWrites(long tid)
      throws UnreachableNodeException, TransactionPrepareFailedException {
    PrepareTransactionWritesMessage.Response response =
        send(new PrepareTransactionWritesMessage(tid));
    return response.result.commitTime;
  }

  public void prepareTransactionReads(long tid, long commitTime)
      throws UnreachableNodeException, TransactionPrepareFailedException {
    send(new PrepareTransactionReadsMessage(tid, commitTime));
  }

  public void commitTransaction(long tid, long commitTime)
      throws UnreachableNodeException, TransactionCommitFailedException {
    send(new CommitTransactionMessage(tid, commitTime));
  }

  /**
   * Informs the remote worker that a transaction is aborting.
   *
   * @param tid
   *          the tid for the transaction that is aborting.
   */
  public void abortTransaction(TransactionID tid) throws AccessException,
      UnreachableNodeException {
    send(new AbortTransactionMessage(tid));
  }

  /**
   * Reads the given object from the remote worker, updating the object's state.
   *
   * @param tid
   *          the tid for the current transaction.
   */
  public void readObject(TransactionID tid, _Impl obj) {
    Pair<Store, SerializedObjectAndTokens> remoteSerializedObj;
    try {
      remoteSerializedObj = readObject(tid, obj.$getStore(), obj.$getOnum());
    } catch (AccessException e) {
      throw new InternalError("Inter-worker object read failed.", e);
    }

    if (remoteSerializedObj == null)
      throw new InternalError("Inter-worker object read failed.");

    SerializedObjectAndTokens serialized = remoteSerializedObj.second;
    _Impl remoteObj =
        serialized.getDeserializedObject(remoteSerializedObj.first);
    obj.$copyAppStateFrom(remoteObj);
  }

  public Pair<Store, SerializedObjectAndTokens> readObject(TransactionID tid,
      Store store, long onum) throws AccessException {
    DirtyReadMessage.Response response =
        send(new DirtyReadMessage(tid, store, onum));
    if (response.obj == null) return null;
    return new Pair<>(response.store, response.obj);
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
    } catch (TakeOwnershipFailedException e) {
      throw new InternalError(e);
    }
  }

  /**
   * @return the principal associated with the remote worker.
   */
  @Override
  public Principal getPrincipal() {
    try {
      SubSocket<RemoteWorker> socket = getSocket(subSocketFactory);
      Principal principal = socket.getPrincipal();
      recycle(subSocketFactory, socket);
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
  public List<Long> notifyObjectUpdates(String store,
      LongKeyMap<ObjectGlob> updates) {
    ObjectUpdateMessage.Response response;
    try {
      response = send(new ObjectUpdateMessage(store, updates));
    } catch (NoException e) {
      // This is not possible.
      throw new InternalError(e);
    }
    return response.resubscriptions;
  }

  /**
   * Notifies the worker that a set of objects has been updated.
   *
   * @return whether the node is resubscribing to the object.
   */
  public List<Long> notifyObjectUpdates(List<Long> updatedOnums,
      List<ObjectGroup> updates) {
    ObjectUpdateMessage.Response response;
    try {
      response = send(new ObjectUpdateMessage(updatedOnums, updates));
    } catch (NoException e) {
      // This is not possible.
      throw new InternalError(e);
    }
    return response.resubscriptions;
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

  /**
   * Notifies the worker that a list of warranties has been updated.
   */
  public List<Long> notifyWarrantyRefresh(WarrantyGroup warranties) {
    WarrantyRefreshMessage.Response response;
    try {
      response = send(new WarrantyRefreshMessage(warranties));
    } catch (NoException e) {
      // Not possible.
      throw new InternalError(e);
    }
    return response.resubscriptions;
  }

  /**
   * Notifies the dissemination node at the given worker that a list of warranty
   * groups has been updated.
   */
  public List<Long> notifyWarrantyRefresh(String store,
      LongKeyMap<WarrantyGlob> updates) {
    WarrantyRefreshMessage.Response response;
    try {
      response = send(new WarrantyRefreshMessage(store, updates));
    } catch (NoException e) {
      // Not possible.
      throw new InternalError(e);
    }
    return response.resubscriptions;
  }

  // ////////////////////////////////
  // Java custom-serialization gunk
  // ////////////////////////////////

  private Object writeReplace() {
    return new SerializationProxy(name);
  }

  static final class SerializationProxy implements Serializable {
    private final String workerName;

    public SerializationProxy(String workerName) {
      this.workerName = workerName;
    }

    java.lang.Object readResolve() {
      return Worker.getWorker().getWorker(workerName);
    }
  }
}
