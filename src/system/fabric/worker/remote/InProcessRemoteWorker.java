package fabric.worker.remote;

import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.Collection;
import java.util.Map;

import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.Threading;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.util.LongHashSet;
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
  public void notifyObjectUpdates(final String storeName,
      final Map<ObjectGlob, LongSet> updates, final LongSet updatedOnums,
      final Collection<ObjectGroup> groups,
      final LongKeyMap<LongSet> associatedOnums) {
    Threading.getPool().submit(new Runnable() {
      @Override
      public void run() {
        notifyObjectUpdatesSync(storeName, updates, updatedOnums, groups,
            associatedOnums);
      }
    });
  }

  /**
   * Run an update notification in the current thread.  This is intended to
   * avoid an unnecessary Thread fork when handling an ObjectUpdateMessage from
   * the network, which already creates a separate thread.
   */
  public void notifyObjectUpdatesSync(final String storeName,
      final Map<ObjectGlob, LongSet> updates, final LongSet updatedOnums,
      final Collection<ObjectGroup> groups,
      final LongKeyMap<LongSet> associatedOnums) {
    LongSet response = new LongHashSet();
    for (LongSet value : updates.values()) {
      response.addAll(value);
    }
    response.addAll(updatedOnums);

    LongSet processed = new LongHashSet();
    RemoteStore store = worker.getStore(storeName);
    PublicKey storeKey = store.getPublicKey();
    // Map from onums to globs, to use to force associated onum updates.
    LongKeyMap<ObjectGlob> gMap = new LongKeyHashMap<>();
    for (ObjectGlob glob : updates.keySet()) {
      LongSet onums = updates.get(glob);
      // Skip over elements we've already managed to handle.
      boolean needsProcessing = false;
      for (LongIterator iter = onums.iterator(); iter.hasNext();) {
        long onum = iter.next();
        if (!processed.contains(onum)) {
          needsProcessing = true;
        }
        gMap.put(onum, glob);
      }
      if (needsProcessing) {
        try {
          glob.verifySignature(storeKey);

          if (worker.updateCaches(store, onums, glob)) {
            processed.addAll(onums);
          }
        } catch (InvalidKeyException e) {
          e.printStackTrace();
        } catch (SignatureException e) {
          e.printStackTrace();
        }
      }
    }

    // Go back and make sure associated globs are forced into cache.
    for (LongIterator iter = processed.iterator(); iter.hasNext();) {
      long onum = iter.next();
      if (associatedOnums.containsKey(onum)) {
        for (LongIterator iter2 = associatedOnums.get(onum).iterator(); iter2
            .hasNext();) {
          long associated = iter2.next();
          if (processed.contains(associated)) continue;
          ObjectGlob associatedGlob = gMap.get(associated);
          try {
            associatedGlob.verifySignature(storeKey);
            // TODO: Force dissem cache too.
            for (SerializedObject obj : associatedGlob.decrypt().objects()
                .values()) {
              store.forceCache(obj);
            }
            processed.addAll(updates.get(associatedGlob));
          } catch (InvalidKeyException e) {
            e.printStackTrace();
          } catch (SignatureException e) {
            e.printStackTrace();
          }
        }
      }
    }

    response.removeAll(processed);
    response.removeAll(
        notifyObjectUpdates(store, updatedOnums, groups, associatedOnums));
    if (!response.isEmpty()) {
      store.unsubscribe(response);
    }
  }

  LongSet notifyObjectUpdates(RemoteStore store, LongSet updatedOnums,
      Collection<ObjectGroup> updates, LongKeyMap<LongSet> associatedOnums) {
    LongKeyMap<ObjectGroup> gMap = new LongKeyHashMap<>();
    for (ObjectGroup group : updates) {
      for (LongIterator iter = group.objects().keySet().iterator(); iter
          .hasNext();) {
        gMap.put(iter.next(), group);
      }
      worker.updateCache(store, group);
    }

    LongSet updated = worker.findOnumsInCache(store, updatedOnums);
    LongSet result = new LongHashSet(updated);
    for (LongIterator iter1 = updated.iterator(); iter1.hasNext();) {
      long onum = iter1.next();
      if (associatedOnums.containsKey(onum)) {
        for (LongIterator iter = associatedOnums.get(onum).iterator(); iter
            .hasNext();) {
          long associated = iter.next();
          if (result.contains(associated)) continue;
          ObjectGroup group = gMap.get(associated);
          for (SerializedObject obj : group.objects().values()) {
            store.forceCache(obj);
          }
          result.add(associated);
        }
      }
    }
    return result;
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
