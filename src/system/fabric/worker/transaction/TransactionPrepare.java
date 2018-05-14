package fabric.worker.transaction;

import static fabric.common.Logging.WORKER_TRANSACTION_LOGGER;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.SerializedObject;
import fabric.common.SysUtil;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Oid;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.lang.Object._Impl;
import fabric.messages.StoreCommittedMessage;
import fabric.messages.StorePrepareFailedMessage;
import fabric.messages.StorePrepareSuccessMessage;
import fabric.messages.WorkerCommittedMessage;
import fabric.messages.WorkerPrepareFailedMessage;
import fabric.messages.WorkerPrepareSuccessMessage;
import fabric.worker.RemoteStore;
import fabric.worker.Store;
import fabric.worker.TransactionRestartingException;
import fabric.worker.Worker;
import fabric.worker.metrics.ExpiryExtension;
import fabric.worker.remote.RemoteWorker;

/**
 * Worker representation of a transaction prepare.
 */
public class TransactionPrepare {
  private enum Status {
    PREPARING, ABORTING, PREPARED, COMMITTING, COMMITTED;
  }

  private Status currentStatus = Status.PREPARING;

  private final Map<RemoteWorker, Boolean> outstandingWorkers;
  private final Set<RemoteWorker> respondedWorkers = new HashSet<>();

  private final Map<Store, Boolean> outstandingStores;
  private final Set<Store> respondedStores = new HashSet<>();

  private final RemoteWorker coordinator;
  private final Log txnLog;
  private final boolean singleStore;
  private final boolean readOnly;
  // time at which all prepares were finished.
  private long commitTime;
  // All longer contracts to forward to the coordinator.
  private OidKeyHashMap<Long> longerContracts;

  public TransactionPrepare(RemoteWorker coordinator, Log txnLog,
      boolean singleStore, boolean readOnly, Collection<Store> stores,
      Collection<RemoteWorker> workers) {
    this.coordinator = coordinator;
    this.txnLog = txnLog;
    this.singleStore = singleStore;
    this.readOnly = readOnly;
    this.outstandingStores = new HashMap<>(stores.size());
    for (Store store : stores) {
      this.outstandingStores.put(store, false);
    }
    this.outstandingWorkers = new HashMap<>(workers.size());
    for (RemoteWorker worker : workers) {
      this.outstandingWorkers.put(worker, false);
    }
    this.commitTime = 0;
    this.longerContracts = new OidKeyHashMap<>();
    if (TransactionManager.pendingPrepares.putIfAbsent(txnLog.tid.topTid,
        this) != null) {
      // This shouldn't be possible, somehow we're trying to prepare an already
      // preparing transaction id.
      throw new InternalError("Preparing an already preparing tid!");
    }
  }

  /**
   * Mark a store as having finished committing.
   */
  public synchronized void markCommitted(String name, StoreCommittedMessage m) {
    Store s = name.equals("local") ? Worker.getWorker().getLocalStore()
        : Worker.getWorker().getStore(name);
    WORKER_TRANSACTION_LOGGER.log(Level.FINER, "{0} finished committing at {1}",
        new Object[] { txnLog, s });
    respondedStores.remove(s);
    if (respondedStores.isEmpty() && respondedWorkers.isEmpty()) {
      if (coordinator != null)
        coordinator.notifyWorkerCommitted(txnLog.tid.topTid);
      TransactionManager.outstandingCommits.remove(txnLog.tid.topTid, this);
      synchronized (TransactionManager.outstandingCommits) {
        TransactionManager.outstandingCommits.notifyAll();
      }
      currentStatus = Status.COMMITTED;
      notifyAll();
    }
  }

  /**
   * Mark a successful prepare for a store.
   */
  public synchronized void markSuccess(String name,
      StorePrepareSuccessMessage m) {
    // XXX: This is really brittle for local store handling...
    Store s = name.equals("local") ? Worker.getWorker().getLocalStore()
        : Worker.getWorker().getStore(name);
    WORKER_TRANSACTION_LOGGER.log(Level.FINER,
        "{0} successfully prepared at {1}", new Object[] { txnLog, s });
    outstandingStores.remove(s);
    respondedStores.add(s);
    commitTime = Math.max(commitTime, m.time);
    for (Store store : m.longerContracts.storeSet()) {
      for (LongKeyMap.Entry<Long> entry : m.longerContracts.get(store)
          .entrySet()) {
        long onum = entry.getKey();
        long expiry = entry.getValue();
        store.readFromCache(onum).setExpiry(expiry);
        this.longerContracts.put(store, onum, expiry);
      }
    }
    cleanUp();
    if (currentStatus == Status.PREPARING && outstandingWorkers.isEmpty()
        && outstandingStores.isEmpty()) {
      if (commitTime > txnLog.expiry() && !singleStore && !readOnly) {
        abortForTimeout();
      } else {
        currentStatus = Status.PREPARED;
        notifyAll();
      }
    }
  }

  /**
   * Mark a failed prepare for a store.
   */
  public synchronized void markFail(String name, StorePrepareFailedMessage m) {
    // XXX: This is really brittle for local store handling...
    Store s = name.equals("local") ? Worker.getWorker().getLocalStore()
        : Worker.getWorker().getStore(name);
    WORKER_TRANSACTION_LOGGER.log(Level.FINER,
        "{0} failed to prepare at {1}: {2}", new Object[] { txnLog, s, m });
    outstandingStores.remove(s);
    respondedStores.add(s);
    this.longerContracts.putAll(m.longerContracts);
    for (Store store : m.longerContracts.storeSet()) {
      for (LongKeyMap.Entry<Long> entry : m.longerContracts.get(store)
          .entrySet()) {
        long onum = entry.getKey();
        long expiry = entry.getValue();
        store.readFromCache(onum).setExpiry(expiry);
      }
    }
    abort(s);
    if (s instanceof RemoteStore) {
      // Remove old objects from our cache.
      RemoteStore store = (RemoteStore) s;
      OidKeyHashMap<SerializedObject> versionConflicts = m.conflicts;
      String conflictsString = "";
      for (SerializedObject obj : versionConflicts.values()) {
        store.updateCache(obj);
        if (!conflictsString.equals("")) {
          conflictsString += " ";
        }
        conflictsString +=
            obj.getClassName() + "@" + store.name() + "#" + obj.getOnum();
      }
      txnLog.stats.addConflicts(conflictsString);
    }
    cleanUp();
  }

  /**
   * Mark a worker as having finished committing.
   */
  public synchronized void markCommitted(String name,
      WorkerCommittedMessage m) {
    RemoteWorker w = Worker.getWorker().getWorker(name);
    WORKER_TRANSACTION_LOGGER.log(Level.FINER, "{0} finished committing at {1}",
        new Object[] { txnLog, w });
    respondedWorkers.remove(w);
    if (respondedStores.isEmpty() && respondedWorkers.isEmpty()) {
      if (coordinator != null)
        coordinator.notifyWorkerCommitted(txnLog.tid.topTid);
      TransactionManager.outstandingCommits.remove(txnLog.tid.topTid, this);
      synchronized (TransactionManager.outstandingCommits) {
        TransactionManager.outstandingCommits.notifyAll();
      }
      currentStatus = Status.COMMITTED;
      notifyAll();
    }
  }

  /**
   * Mark a successful prepare for a worker.
   */
  public synchronized void markSuccess(String name,
      WorkerPrepareSuccessMessage m) {
    RemoteWorker w = Worker.getWorker().getWorker(name);
    WORKER_TRANSACTION_LOGGER.log(Level.FINER,
        "{0} successfully prepared at {1}", new Object[] { txnLog, w });
    outstandingWorkers.remove(w);
    respondedWorkers.add(w);
    commitTime = Math.max(commitTime, m.time);
    this.longerContracts.putAll(m.longerContracts);
    for (Store store : m.longerContracts.storeSet()) {
      for (LongKeyMap.Entry<Long> entry : m.longerContracts.get(store)
          .entrySet()) {
        long onum = entry.getKey();
        long expiry = entry.getValue();
        store.readFromCache(onum).setExpiry(expiry);
      }
    }
    cleanUp();
    if (currentStatus == Status.PREPARING && outstandingWorkers.isEmpty()
        && outstandingStores.isEmpty()) {
      if (commitTime > txnLog.expiry() && !singleStore && !readOnly) {
        abortForTimeout();
      } else {
        currentStatus = Status.PREPARED;
        notifyAll();
      }
    }
  }

  /**
   * Mark a failed prepare for a worker.
   */
  public synchronized void markFail(String name, WorkerPrepareFailedMessage m) {
    RemoteWorker w = Worker.getWorker().getWorker(name);
    WORKER_TRANSACTION_LOGGER.log(Level.FINER,
        "{0} failed to prepare at {1}: {2}", new Object[] { txnLog, w, m });
    outstandingWorkers.remove(w);
    respondedWorkers.add(w);
    this.longerContracts.putAll(m.longerContracts);
    for (Store store : m.longerContracts.storeSet()) {
      for (LongKeyMap.Entry<Long> entry : m.longerContracts.get(store)
          .entrySet()) {
        long onum = entry.getKey();
        long expiry = entry.getValue();
        store.readFromCache(onum).setExpiry(expiry);
      }
    }
    abort(w);
    // TODO: handle conflicts?
    cleanUp();
  }

  public synchronized void prepare() throws TransactionRestartingException {
    WORKER_TRANSACTION_LOGGER.log(Level.FINER, "{0} running prepare", txnLog);
    // Make sure we weren't aborted already.
    txnLog.checkRetrySignal();

    // If we literally don't have anyone to prepare, we just move on.
    if (currentStatus == Status.PREPARING && outstandingStores.isEmpty()
        && outstandingWorkers.isEmpty()) {
      currentStatus = Status.PREPARED;
      return;
    }

    // Send prepares to workers.
    for (RemoteWorker w : outstandingWorkers.keySet()) {
      w.prepareTransaction(txnLog.tid.topTid);
      WORKER_TRANSACTION_LOGGER.log(Level.FINER, "{0} sending prepare to {1}",
          new Object[] { txnLog, w });
      outstandingWorkers.put(w, true);
    }

    // Send prepares to stores.
    for (Store store : outstandingStores.keySet()) {
      Collection<_Impl> creates = txnLog.getCreatesForStore(store);
      LongKeyMap<Pair<Integer, Long>> reads =
          txnLog.getReadsForStore(store, false);
      Collection<_Impl> writes = txnLog.getWritesForStore(store);
      Collection<ExpiryExtension> extensions =
          txnLog.getExtensionsForStore(store);
      LongKeyMap<Set<Oid>> extensionsTriggered =
          txnLog.getTriggeredExtensionsForStore(store);
      LongSet delayedExtensions = txnLog.getDelayedExtensionsForStore(store);
      store.prepareTransaction(txnLog.tid.topTid, singleStore, readOnly,
          txnLog.expiry(), creates, reads, writes, extensions,
          extensionsTriggered, delayedExtensions);
      WORKER_TRANSACTION_LOGGER.log(Level.FINER, "{0} sending prepare to {1}",
          new Object[] { txnLog, store });
      outstandingStores.put(store, true);
    }

    try {
      // Wait for success or abort.
      while (currentStatus == Status.PREPARING) {
        txnLog.checkRetrySignal();
        try {
          txnLog.setWaitsFor(this);
          wait();
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
          // TODO: more?
        }
      }
    } finally {
      txnLog.clearWaitsFor();
    }
    if (currentStatus == Status.ABORTING) {
      // Check if we're done due to abort.
      txnLog.checkRetrySignal();
    }
  }

  public synchronized void commit() {
    if (currentStatus == Status.PREPARED) {
      WORKER_TRANSACTION_LOGGER.log(Level.FINER, "{0} running commit", txnLog);
      if (!readOnly && !singleStore
          && (!respondedStores.isEmpty() || !respondedWorkers.isEmpty())) {
        // Make sure worker waits for this to be handled at stores.
        TransactionManager.outstandingCommits.put(txnLog.tid.topTid, this);
        currentStatus = Status.COMMITTING;

        // Tell workers to commit.
        for (RemoteWorker w : respondedWorkers) {
          w.commitTransaction(txnLog.tid.topTid);
          WORKER_TRANSACTION_LOGGER.log(Level.FINER,
              "{0} sending commit to {1}", new Object[] { txnLog, w });
        }

        // Tell stores to commit.
        for (Store s : respondedStores) {
          s.commitTransaction(txnLog.tid.topTid);
          WORKER_TRANSACTION_LOGGER.log(Level.FINER,
              "{0} sending commit to {1}", new Object[] { txnLog, s });
        }

        // TODO: figure out how to make it safe to pipeline the next transaction
        // and make it configurable whether the worker should block waiting for
        // a response before continuing.
        // (What's remaining appears to be proper handling of creates, which
        // aren't visible until the creating transaction is committed).
        while (currentStatus == Status.COMMITTING) {
          try {
            wait();
          } catch (InterruptedException e) {
            Logging.logIgnoredInterruptedException(e);
          }
        }
      } else {
        // Mark this as committed.
        currentStatus = Status.COMMITTED;
        if (coordinator != null)
          coordinator.notifyWorkerCommitted(txnLog.tid.topTid);
      }
    }
    // TODO: is it possible to be here before we've prepared?
  }

  /**
   * Initiate an abort due to some problem on the coordinator side.
   */
  public synchronized void abort() {
    if (currentStatus != Status.ABORTING) {
      WORKER_TRANSACTION_LOGGER.log(Level.FINE,
          "{0} aborted during prepare by external actor", txnLog);
      runAbort();
    }
    cleanUp();
  }

  /**
   * Initiate an abort due to contract timeout.
   */
  public synchronized void abortForTimeout() {
    if (currentStatus != Status.ABORTING) {
      WORKER_TRANSACTION_LOGGER.log(Level.FINE,
          "{0} aborted during prepare due to expiries expiring", txnLog);
      runAbort();
    }
    cleanUp();
  }

  /**
   * Initiate an abort due to the RemoteWorker cause indicating a problem.
   * @param cause the failed worker that initiated the abort.
   */
  private synchronized void abort(RemoteWorker cause) {
    if (currentStatus != Status.ABORTING) {
      WORKER_TRANSACTION_LOGGER.log(Level.FINE,
          "{0} aborted during prepare by {1}", new Object[] { txnLog, cause });
      runAbort();
    }
  }

  /**
   * Initiate an abort due to the store cause indicating a problem.
   * @param cause the failed store that initiated the abort.
   */
  private synchronized void abort(Store cause) {
    if (currentStatus != Status.ABORTING) {
      WORKER_TRANSACTION_LOGGER.log(Level.FINE,
          "{0} aborted during prepare by {1}", new Object[] { txnLog, cause });
      runAbort();
    }
  }

  /**
   * Actually perform abort round, updating state and sending messages.
   */
  private synchronized void runAbort() {
    currentStatus = Status.ABORTING;

    // Clear out nodes that we didn't contact and shouldn't contact.
    for (RemoteWorker w : new HashSet<>(outstandingWorkers.keySet())) {
      if (!outstandingWorkers.get(w)) outstandingWorkers.remove(w);
    }
    for (Store s : new HashSet<>(outstandingStores.keySet())) {
      if (!outstandingStores.get(s)) outstandingStores.remove(s);
    }

    // Abort the rest.
    for (RemoteWorker w : SysUtil.chain(outstandingWorkers.keySet(),
        respondedWorkers)) {
      w.abortTransaction(txnLog.tid);
      WORKER_TRANSACTION_LOGGER.log(Level.FINER, "{0} sending abort to {1}",
          new Object[] { txnLog, w });
    }
    for (Store s : SysUtil.chain(outstandingStores.keySet(), respondedStores)) {
      s.abortTransaction(txnLog.tid);
      WORKER_TRANSACTION_LOGGER.log(Level.FINER, "{0} sending abort to {1}",
          new Object[] { txnLog, s });
    }

    // Flag that local locks should be released.
    txnLog.flagRetry();
    txnLog.prepare = null;
  }

  /**
   * Make sure this is dropped from the pendingPrepares table once every node
   * has replied and, if applicable, the coordinator has initiated abort/commit.
   */
  private synchronized void cleanUp() {
    if (outstandingWorkers.isEmpty() && outstandingStores.isEmpty()) {
      TransactionManager.pendingPrepares.remove(txnLog.tid.topTid, this);
    }
  }

  /**
   * @return the commitTime
   */
  public synchronized long getCommitTime() {
    return commitTime;
  }

  /**
   * @return the longerContracts
   */
  public synchronized OidKeyHashMap<Long> getLongerContracts() {
    return longerContracts;
  }
}
