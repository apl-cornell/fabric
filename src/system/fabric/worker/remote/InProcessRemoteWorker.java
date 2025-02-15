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
import fabric.messages.StoreCommittedMessage;
import fabric.messages.StorePrepareFailedMessage;
import fabric.messages.StorePrepareSuccessMessage;
import fabric.messages.WorkerCommittedMessage;
import fabric.messages.WorkerPrepareFailedMessage;
import fabric.messages.WorkerPrepareSuccessMessage;
import fabric.net.UnreachableNodeException;
import fabric.store.InProcessStore;
import fabric.worker.RemoteStore;
import fabric.worker.Store;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.TransactionRestartingException;
import fabric.worker.Worker;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.transaction.TransactionPrepare;

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
  public void notifyStorePrepareFailed(long tid,
      TransactionPrepareFailedException e) {
    // Don't bother with another thread, a thread would have been created for
    // the prepare processing already.
    TransactionManager.pendingPrepares.get(tid).markFail(inProcessStore.name(),
        new StorePrepareFailedMessage(tid, e));
  }

  @Override
  public void notifyStorePrepareSuccess(long tid) {
    // Don't bother with another thread, a thread would have been created for
    // the prepare processing already.
    TransactionManager.pendingPrepares.get(tid).markSuccess(
        inProcessStore.name(), new StorePrepareSuccessMessage(tid));
  }

  @Override
  public void notifyStoreCommitted(long tid) {
    // Don't bother with another thread, a thread would have been created for
    // the commit processing already.
    TransactionPrepare p = TransactionManager.outstandingCommits.get(tid);
    if (p != null) {
      p.markCommitted(inProcessStore.name(), new StoreCommittedMessage(tid));
    }
  }

  @Override
  public void notifyWorkerPrepareFailed(long tid,
      TransactionPrepareFailedException e) {
    // Don't bother with another thread, a thread would have been created for
    // the prepare processing already.
    TransactionManager.pendingPrepares.get(tid).markFail(Worker.getWorkerName(),
        new WorkerPrepareFailedMessage(tid, e));
  }

  @Override
  public void notifyWorkerPrepareSuccess(long tid) {
    // Don't bother with another thread, a thread would have been created for
    // the prepare processing already.
    TransactionManager.pendingPrepares.get(tid).markSuccess(
        Worker.getWorkerName(), new WorkerPrepareSuccessMessage(tid));
  }

  @Override
  public void notifyWorkerCommitted(long tid) {
    // Don't bother with another thread, a thread would have been created for
    // the commit processing already.
    TransactionPrepare p = TransactionManager.outstandingCommits.get(tid);
    if (p != null) {
      p.markCommitted(Worker.getWorkerName(), new WorkerCommittedMessage(tid));
    }
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
  public void prepareTransaction(long tid) throws UnreachableNodeException {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public void commitTransaction(long tid) {
    // XXX Does this actually happen?
    throw new NotImplementedException();
  }

  @Override
  public void abortTransaction(TransactionID tid) {
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
      final Collection<ObjectGroup> groups) {
    Threading.getPool().submit(new Runnable() {
      @Override
      public void run() {
        notifyObjectUpdatesSync(storeName, updates, updatedOnums, groups);
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
      final Collection<ObjectGroup> groups) {
    LongSet response = new LongHashSet();
    for (LongSet value : updates.values()) {
      response.addAll(value);
    }
    response.addAll(updatedOnums);

    RemoteStore store = worker.getStore(storeName);
    PublicKey storeKey = store.getPublicKey();
    for (ObjectGlob glob : updates.keySet()) {
      LongSet onums = updates.get(glob);
      // Skip over elements we've already managed to handle.
      for (LongIterator iter = onums.iterator(); iter.hasNext();) {
        if (response.contains(iter.next())) {
          // We haven't handled it already.
          try {
            glob.verifySignature(storeKey);

            if (worker.updateCaches(store, onums, glob)) {
              response.removeAll(onums);
            }
          } catch (InvalidKeyException e) {
            e.printStackTrace();
          } catch (SignatureException e) {
            e.printStackTrace();
          }
          break;
        }
      }
    }

    response.removeAll(notifyObjectUpdates(store, updatedOnums, groups));
    if (!response.isEmpty()) {
      store.unsubscribe(response);
    }
  }

  LongSet notifyObjectUpdates(RemoteStore store, LongSet updatedOnums,
      Collection<ObjectGroup> updates) {
    LongSet result = new LongHashSet(updatedOnums);
    LongKeyMap<ObjectGroup> gMap = new LongKeyHashMap<>();
    for (ObjectGroup group : updates) {
      for (LongIterator iter = group.objects().keySet().iterator(); iter
          .hasNext();) {
        gMap.put(iter.next(), group);
      }
      if (!worker.updateCache(store, group))
        result.removeAll(group.objects().keySet());
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
