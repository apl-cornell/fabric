package fabric.worker.remote;

import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.TransactionID;
import fabric.common.VersionWarranty;
import fabric.common.WarrantyGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.WarrantyGlob;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Principal;
import fabric.net.UnreachableNodeException;
import fabric.store.InProcessStore;
import fabric.worker.RemoteStore;
import fabric.worker.Store;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;

/**
 * In-process implementation of RemoteWorker. This is so that a store or
 * dissemination node can contact its colocated worker using the same interface
 * as a worker that is genuinely remote.
 */
public class InProcessRemoteWorker extends RemoteWorker {

  private transient final Worker worker;

  /**
   * The local in-process store. This is null if there is no colocated store.
   */
  private transient final InProcessStore inProcessStore;

  public InProcessRemoteWorker(Worker worker) {
    super(worker);
    this.worker = worker;

    RemoteStore store = worker.getStore(name);
    this.inProcessStore =
        store instanceof InProcessStore ? (InProcessStore) store : null;
  }

  @Override
  public Object issueRemoteCall(_Proxy receiver, String methodName,
      Class<?>[] parameterTypes, Object[] args)
          throws UnreachableNodeException, RemoteCallException {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public long prepareTransactionWrites(long tid)
      throws UnreachableNodeException, TransactionPrepareFailedException {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public void prepareTransactionReads(long tid, long commitTime)
      throws UnreachableNodeException, TransactionPrepareFailedException {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public void commitTransaction(long tid, long commitTime)
      throws UnreachableNodeException, TransactionCommitFailedException {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public void abortTransaction(TransactionID tid) throws AccessException,
  UnreachableNodeException {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public void readObject(TransactionID tid, _Impl obj) {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public Pair<Store, Pair<SerializedObject, VersionWarranty>> readObject(
      TransactionID tid, Store store, long onum) throws AccessException {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public void takeOwnership(TransactionID tid, Store store, long onum) {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public Principal getPrincipal() {
    return worker.getPrincipal();
  }

  @Override
  public List<Long> notifyObjectUpdates(String storeName,
      LongKeyMap<ObjectGlob> updates) {
    List<Long> response = new ArrayList<>();

    RemoteStore store = worker.getStore(storeName);
    PublicKey storeKey = store.getPublicKey();
    for (LongKeyMap.Entry<ObjectGlob> entry : updates.entrySet()) {
      long onum = entry.getKey();
      ObjectGlob glob = entry.getValue();
      try {
        glob.verifySignature(storeKey);

        if (worker.updateCaches(store, onum, glob)) {
          response.add(onum);
        }
      } catch (InvalidKeyException e) {
        e.printStackTrace();
      } catch (SignatureException e) {
        e.printStackTrace();
      }
    }

    return response;
  }

  @Override
  public List<Long> notifyObjectUpdates(List<Long> updatedOnums,
      List<ObjectGroup> updates) {
    return notifyObjectUpdates(inProcessStore, updatedOnums, updates);
  }

  List<Long> notifyObjectUpdates(RemoteStore store, List<Long> updatedOnums,
      List<ObjectGroup> updates) {
    for (ObjectGroup group : updates) {
      worker.updateCache(store, group);
    }

    return worker.findOnumsInCache(store, updatedOnums);
  }

  @Override
  public boolean checkForStaleObjects(TransactionID tid) {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public List<Long> notifyWarrantyRefresh(WarrantyGroup warranties) {
    return notifyWarrantyRefresh(inProcessStore, warranties);
  }

  List<Long> notifyWarrantyRefresh(RemoteStore store, WarrantyGroup warranties) {
    return store.updateWarranties(warranties);
  }

  @Override
  public List<Long> notifyWarrantyRefresh(String storeName,
      LongKeyMap<WarrantyGlob> updates) {
    RemoteStore store = worker.getStore(storeName);
    List<Long> result = new ArrayList<Long>();

    for (LongKeyMap.Entry<WarrantyGlob> entry : updates.entrySet()) {
      long onum = entry.getKey();
      WarrantyGlob glob = entry.getValue();

      try {
        glob.verifySignature(store.getPublicKey());

        if (worker.updateCaches(store, onum, glob)) {
          result.add(onum);
        }
      } catch (InvalidKeyException e) {
        e.printStackTrace();
      } catch (SignatureException e) {
        e.printStackTrace();
      }
    }

    return result;
  }

  private Object writeReplace() {
    return new SerializationProxy(name);
  }
}
