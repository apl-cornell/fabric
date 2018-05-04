package fabric.worker.remote;

import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Principal;
import fabric.net.UnreachableNodeException;
import fabric.store.InProcessStore;
import fabric.worker.RemoteStore;
import fabric.worker.Store;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.TransactionRestartingException;
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
    //
    // Tom (9/24/2015) Yes it does: run a program which does a remote call where
    // the worker specified is the local worker.  This isn't too hard to imagine
    // if you write a program that should run *anywhere* but always runs a call
    // at the same exact node.
    throw new NotImplementedException();
  }

  @Override
  public long prepareTransaction(long tid)
      throws UnreachableNodeException, TransactionPrepareFailedException {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public void commitTransaction(long tid)
      throws UnreachableNodeException, TransactionCommitFailedException {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public void abortTransaction(TransactionID tid)
      throws AccessException, UnreachableNodeException {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public void readObject(TransactionID tid, _Impl obj) {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public Pair<Store, SerializedObject> readObject(TransactionID tid,
      Store store, long onum) throws AccessException {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public void takeOwnership(TransactionID tid, Store store, long onum) {
    // XXX Does this actually happen?
    // Tom (1/23/18): Yes, due to a race condition between cache eviction and
    // writer locking.  This has been changed to abort the transaction, since
    // the condition in which it occurs suggests that a now evicted value was
    // used earlier in the transaction.
    //throw new NotImplementedException();
    throw new TransactionRestartingException(new TransactionID(tid.topTid));
  }

  @Override
  public Principal getPrincipal() {
    return worker.getPrincipal();
  }

  @Override
  public List<Long> notifyObjectUpdates(String storeName,
      LongKeyMap<ObjectGlob> updates, LongKeyMap<LongSet> associatedOnums) {
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
      List<ObjectGroup> updates, LongKeyMap<LongSet> associatedOnums) {
    return notifyObjectUpdates(inProcessStore, updatedOnums, updates,
        associatedOnums);
  }

  List<Long> notifyObjectUpdates(RemoteStore store, List<Long> updatedOnums,
      List<ObjectGroup> updates, LongKeyMap<LongSet> associatedOnums) {
    LongKeyMap<ObjectGroup> gMap = new LongKeyHashMap<>();
    for (ObjectGroup group : updates) {
      for (LongIterator iter = group.objects().keySet().iterator(); iter
          .hasNext();) {
        gMap.put(iter.next(), group);
      }
      worker.updateCache(store, group);
    }

    List<Long> updated = worker.findOnumsInCache(store, updatedOnums);
    for (Long onum : updated) {
      for (LongIterator iter = associatedOnums.get(onum).iterator(); iter
          .hasNext();) {
        ObjectGroup group = gMap.get(iter.next());
        for (SerializedObject obj : group.objects().values()) {
          store.forceCache(obj);
        }
      }
    }
    return updated;
  }

  @Override
  public boolean checkForStaleObjects(TransactionID tid) {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  private Object writeReplace() {
    return new SerializationProxy(name);
  }
}
