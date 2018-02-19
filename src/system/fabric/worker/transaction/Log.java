package fabric.worker.transaction;

import static fabric.common.Logging.HOTOS_LOGGER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.SerializedObject;
import fabric.common.SysUtil;
import fabric.common.Timing;
import fabric.common.TransactionID;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Oid;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.common.util.WeakReferenceArrayList;
import fabric.lang.Object._Impl;
import fabric.lang.security.LabelCache;
import fabric.lang.security.SecurityCache;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.MetricContract;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.store.DelayedExtension;
import fabric.worker.FabricSoftRef;
import fabric.worker.RemoteStore;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.WriterMap;

/**
 * Stores per-transaction information. Records the objects that are created,
 * read, and written during a single nested transaction.
 */
public final class Log {
  public static final Log NO_READER = new Log((Log) null);

  /**
   * The transaction ID for this log.
   */
  TransactionID tid;

  /**
   * The log for the parent transaction, or null if there is none. A null value
   * here does not necessarily mean that this is the top-level transaction. The
   * tid should be checked to determine whether this transaction is top-level.
   */
  final Log parent;

  /**
   * A map indicating where to fetch objects from.
   */
  WriterMap writerMap;

  /**
   * The sub-transaction.
   */
  private Log child;

  /**
   * The thread that is running this transaction.
   */
  Thread thread;

  /**
   * A flag indicating whether this transaction should abort or be retried. This
   * flag should be checked before each operation. This flag is set when it's
   * non-null and indicates the transaction in the stack that is to be retried;
   * all child transactions are to be aborted.
   */
  volatile TransactionID retrySignal;

  /**
   * Maps OIDs to <code>readMap</code> entries for objects read in this
   * transaction or completed sub-transactions. Reads from running or aborted
   * sub-transactions don't count here.
   */
  // Proxy objects aren't used for keys here because doing so would result in
  // calls to hashcode() and equals() on such objects, resulting in fetching the
  // corresponding Impls from the store.
  protected final OidKeyHashMap<ReadMap.Entry> reads;

  /**
   * Reads on objects that have been read by an ancestor transaction.
   */
  protected final List<ReadMap.Entry> readsReadByParent;

  /**
   * A collection of all objects created in this transaction or completed
   * sub-transactions. Objects created in running or aborted sub-transactions
   * don't count here. To keep them from being pinned, objects on local store
   * are not tracked here.
   */
  protected final List<_Impl> creates;

  /**
   * Tracks objects created on local store. See <code>creates</code>.
   */
  protected final WeakReferenceArrayList<_Impl> localStoreCreates;

  /**
   * A collection of all objects modified in this transaction or completed
   * sub-transactions. Objects modified in running or aborted sub-transactions
   * don't count here. To keep them from being pinned, objects on local store
   * are not tracked here.
   */
  protected final List<_Impl> writes;

  /**
   * Collection of {@link Subjects} in this transaction that need to be/have
   * been observed by {@link Observer}s before the transaction commits.
   */
  protected final OidKeyHashMap<Subject> unobservedSamples;

  /**
   * Flag indicating if we're in the middle of resolving observations (to avoid
   * infinite loop).
   */
  protected boolean resolving = false;

  /**
   * A collection of {@link Contract}s that are extended by this transaction
   */
  protected final OidKeyHashMap<_Impl> extendedContracts;

  /**
   * A collection of {@link Contract}s that are retracted by this transaction
   */
  protected final OidKeyHashMap<_Impl> retractedContracts;

  /**
   * A collection of {@link Contract}s that should be extended after this
   * transaction
   */
  protected final OidKeyHashMap<Long> delayedExtensions;

  /**
   * A map from RemoteStores to maps from onums to contracts that were longer on
   * the store, to update after we commit this transaction.
   */
  public Map<RemoteStore, LongKeyMap<SerializedObject>> longerContracts;

  /**
   * Tracks objects on local store that have been modified. See
   * <code>writes</code>.
   */
  protected final WeakReferenceArrayList<_Impl> localStoreWrites;

  /**
   * The set of workers called by this transaction and completed
   * sub-transactions.
   */
  public final List<RemoteWorker> workersCalled;

  /**
   * Indicates the state of commit for the top-level transaction.
   */
  public final CommitState commitState;

  protected boolean coordinated = false;

  public static class CommitState {
    public static enum Values {
      /**
       * Signifies a transaction before it has been prepared or aborted.
       */
      UNPREPARED,
      /**
       * Signifies a transaction that is currently being prepared.
       */
      PREPARING,
      /**
       * Signifies a transaction that has successfully prepared, but has not yet
       * been committed.
       */
      PREPARED,
      /**
       * Signifies a transaction that has failed to prepare, but has not yet
       * been rolled back.
       */
      PREPARE_FAILED,
      /**
       * Signifies a transaction that is currently being committed.
       */
      COMMITTING,
      /**
       * Signifies a transaction that has been committed.
       */
      COMMITTED,
      /**
       * Signifies a transaction that is currently being aborted.
       */
      ABORTING,
      /**
       * Signifies a transaction that has been aborted.
       */
      ABORTED
    }

    public Values value = Values.UNPREPARED;
  }

  public final AbstractSecurityCache securityCache;

  /**
   * The time at which this subtransaction was started.
   */
  public final long startTime;

  /**
   * Earliest expiry used in this transaction.
   */
  private long expiryToCheck = Long.MAX_VALUE;

  /**
   * If a transaction T's log appears in this set, then this transaction is
   * waiting for a lock that is held by transaction T.
   */
  private final Set<Log> waitsFor;

  /**
   * Locks that will be acquired if this transaction commits successfully.
   */
  protected final OidKeyHashMap<Boolean> pendingAcquires =
      new OidKeyHashMap<>();

  /**
   * Locks that will be released if this transaction commits successfully.
   */
  protected final OidKeyHashMap<Boolean> pendingReleases =
      new OidKeyHashMap<>();

  /**
   * Locks that were created in this transaction.
   */
  protected final OidKeyHashMap<Boolean> locksCreated =
      new OidKeyHashMap<>();

  /**
   * Creates a new log with the given parent and the given transaction ID. The
   * TID for the parent and the given TID are assumed to be consistent. If the
   * given TID is null, a random tid is generated for the subtransaction.
   */
  Log(Log parent, TransactionID tid) {
    this.parent = parent;
    if (tid == null) {
      if (parent == null) {
        this.tid = new TransactionID();
      } else {
        this.tid = new TransactionID(parent.tid);
      }
    } else {
      this.tid = tid;
    }

    this.child = null;
    this.thread = Thread.currentThread();
    this.retrySignal = parent == null ? null : parent.retrySignal;
    this.reads = new OidKeyHashMap<>();
    this.readsReadByParent = new ArrayList<>();
    this.creates = new ArrayList<>();
    this.localStoreCreates = new WeakReferenceArrayList<>();
    this.writes = new ArrayList<>();
    this.unobservedSamples = new OidKeyHashMap<>();
    this.extendedContracts = new OidKeyHashMap<>();
    this.retractedContracts = new OidKeyHashMap<>();
    this.delayedExtensions = new OidKeyHashMap<>();
    this.localStoreWrites = new WeakReferenceArrayList<>();
    this.workersCalled = new ArrayList<>();
    this.startTime = System.currentTimeMillis();
    this.waitsFor = new HashSet<>();

    if (parent != null) {
      this.unobservedSamples.putAll(parent.unobservedSamples);
      try {
        Timing.SUBTX.begin();
        this.writerMap = new WriterMap(parent.writerMap);
        synchronized (parent) {
          parent.child = this;
        }

        commitState = parent.commitState;
        this.securityCache =
            new SecurityCache((SecurityCache) parent.securityCache);
      } finally {
        Timing.SUBTX.end();
      }
      this.resolving = parent.resolving;
      this.locksCreated.putAll(parent.locksCreated);
      this.pendingAcquires.putAll(parent.pendingAcquires);
      this.pendingReleases.putAll(parent.pendingReleases);
    } else {
      this.writerMap = new WriterMap(this.tid.topTid);
      commitState = new CommitState();

      LabelCache labelCache = Worker.getWorker().labelCache;
      this.securityCache = new SecurityCache(labelCache);

      // New top-level frame. Register it in the transaction registry.
      TransactionRegistry.register(this);
    }
  }

  /**
   * Creates a nested transaction whose parent is the transaction with the given
   * log. The created transaction log is added to the parent's children.
   *
   * @param parent
   *          the log for the parent transaction or null if creating the log for
   *          a top-level transaction.
   */
  Log(Log parent) {
    this(parent, null);
  }

  /**
   * Creates a log with the given transaction ID.
   */
  public Log(TransactionID tid) {
    this(null, tid);
  }

  /**
   * Returns true iff the given Log is in the ancestry of (or is the same as)
   * this log.
   */
  boolean isDescendantOf(Log log) {
    return tid.isDescendantOf(log.tid);
  }

  /**
   * @return true if the transaction is not distributed and neither creates nor
   *         modifies objects on remote stores.
   */
  public boolean isReadOnly() {
    return writes.isEmpty() && creates.isEmpty() && workersCalled.isEmpty();
  }

  /**
   * Returns a set of stores affected by this transaction. This is the set of
   * stores to contact when preparing and committing a transaction.
   */
  Set<Store> storesToContact() {
    Set<Store> result = new HashSet<>();

    result.addAll(reads.storeSet());

    for (_Impl obj : writes) {
      if (obj.$isOwned) result.add(obj.$getStore());
    }

    for (_Impl obj : creates) {
      if (obj.$isOwned) result.add(obj.$getStore());
    }

    if (!localStoreWrites.isEmpty() || !localStoreCreates.isEmpty()) {
      result.add(Worker.getWorker().getLocalStore());
    }

    return result;
  }

  /**
   * @return a set of stores to contact when checking for object freshness.
   */
  Set<Store> storesToCheckFreshness() {
    Set<Store> result = new HashSet<>();
    result.addAll(reads.storeSet());
    for (ReadMap.Entry entry : readsReadByParent) {
      result.add(entry.getStore());
    }

    return result;
  }

  /**
   * Returns a map from onums to version numbers of objects read at the given
   * store. Reads on created objects are never included.
   *
   * @param includeModified
   *          whether to include reads on modified objects.
   */
  LongKeyMap<Pair<Integer, Long>> getReadsForStore(Store store,
      boolean includeModified) {
    LongKeyMap<Pair<Integer, Long>> result = new LongKeyHashMap<>();
    LongKeyMap<ReadMap.Entry> submap = reads.get(store);
    if (submap == null) return result;

    for (LongKeyMap.Entry<ReadMap.Entry> entry : submap.entrySet()) {
      result.put(entry.getKey(), new Pair<>(entry.getValue().getVersionNumber(),
          entry.getValue().getExpiry()));
    }

    if (parent != null) {
      for (ReadMap.Entry entry : readsReadByParent) {
        FabricSoftRef entryRef = entry.getRef();
        if (store.equals(entryRef.store)) {
          result.put(entryRef.onum,
              new Pair<>(entry.getVersionNumber(), entry.getExpiry()));
        }
      }
    }

    Log curLog = this;
    while (curLog != null) {
      if (store.isLocalStore()) {
        Iterable<_Impl> writesToExclude =
            includeModified ? Collections.<_Impl> emptyList()
                : curLog.localStoreWrites;
        Iterable<_Impl> chain =
            SysUtil.chain(writesToExclude, curLog.localStoreCreates);
        for (_Impl write : chain)
          result.remove(write.$getOnum());
      } else {
        Iterable<_Impl> writesToExclude =
            includeModified ? Collections.<_Impl> emptyList() : curLog.writes;
        Iterable<_Impl> chain = SysUtil.chain(writesToExclude, curLog.creates);
        for (_Impl write : chain)
          if (write.$getStore() == store) result.remove(write.$getOnum());
      }
      curLog = curLog.parent;
    }

    return result;
  }

  /**
   * Returns a collection of objects modified at the given store. Writes on
   * created objects are not included.
   */
  Collection<Pair<_Impl, Boolean>> getWritesForStore(Store store) {
    // This should be a Set of _Impl, but we have a map indexed by OID to
    // avoid calling hashCode and equals on the _Impls.
    LongKeyMap<Pair<_Impl, Boolean>> result = new LongKeyHashMap<>();

    if (store.isLocalStore()) {
      for (_Impl obj : localStoreWrites) {
        result.put(obj.$getOnum(),
            new Pair<>(obj, ((obj instanceof MetricContract)
                && extendedContracts.containsKey(obj))));
      }

      for (_Impl create : localStoreCreates) {
        result.remove(create.$getOnum());
      }
    } else {
      for (_Impl obj : writes) {
        if (obj.$getStore() == store && obj.$isOwned) {
          result.put(obj.$getOnum(),
              new Pair<>(obj, ((obj instanceof MetricContract)
                  && extendedContracts.containsKey(obj))));
        }
      }

      for (_Impl create : creates)
        if (create.$getStore() == store) result.remove(create.$getOnum());
    }

    return result.values();
  }

  /**
   * Returns a collection of objects created at the given store.
   */
  Collection<_Impl> getCreatesForStore(Store store) {
    // This should be a Set of _Impl, but to avoid calling methods on the
    // _Impls, we instead use a map keyed on OID.
    LongKeyMap<_Impl> result = new LongKeyHashMap<>();

    if (store.isLocalStore()) {
      for (_Impl obj : localStoreCreates) {
        result.put(obj.$getOnum(), obj);
      }
    } else {
      for (_Impl obj : creates)
        if (obj.$getStore() == store && obj.$isOwned)
          result.put(obj.$getOnum(), obj);
    }

    return result.values();
  }

  /**
   * Sets the retry flag on this and the logs of all sub-transactions.
   */
  public void flagRetry() {
    Queue<Log> toFlag = new LinkedList<>();
    toFlag.add(this);
    while (!toFlag.isEmpty()) {
      Log log = toFlag.remove();
      synchronized (log) {
        if (log.child != null) toFlag.add(log.child);
        if (log.retrySignal == null || log.retrySignal.isDescendantOf(tid)) {
          log.retrySignal = tid;
          // XXX This was here to unblock a thread that may have been waiting on
          // XXX a lock. Commented out because it was causing a bunch of
          // XXX InterruptedExceptions and ClosedByInterruptExceptions that
          // XXX weren't being handled properly. This includes improper or
          // XXX insufficient handling by Java code in the examples (e.g.,
          // XXX Jetty).
          // XXX Instead, when a thread is blocked waiting on an object lock, we
          // XXX spin once a second to check the retry signal.

          // log.thread.interrupt();
        }
      }
    }
  }

  /**
   * Updates logs and data structures in <code>_Impl</code>s to abort this
   * transaction. All locks held by this transaction are released.
   */
  void abort() {
    Store localStore = Worker.getWorker().getLocalStore();
    Set<Store> stores = storesToContact();
    // Note what we were trying to do before we aborted.
    Logging.log(HOTOS_LOGGER, Level.FINE,
        "aborted tid {0} ({1} stores, {2} retractions, {3} extensions, {4} delayed extensions)",
        tid, stores.size() - (stores.contains(localStore) ? 1 : 0),
        retractedContracts.size(), extendedContracts.size(),
        delayedExtensions.size());
    // Release read locks.
    for (LongKeyMap<ReadMap.Entry> submap : reads) {
      for (ReadMap.Entry entry : submap.values()) {
        entry.releaseLock(this);
      }
    }

    for (ReadMap.Entry entry : readsReadByParent)
      entry.releaseLock(this);

    // Roll back writes and release write locks.
    Iterable<_Impl> chain = SysUtil.chain(writes, localStoreWrites);
    for (_Impl write : chain) {
      synchronized (write) {
        write.$copyStateFrom(write.$history);

        // Signal any waiting readers/writers.
        if (write.$numWaiting > 0) write.notifyAll();
      }
    }

    if (parent != null && parent.tid.equals(tid.parent)) {
      // The parent frame represents the parent transaction. Null out its child.
      synchronized (parent) {
        parent.child = null;
      }
    } else {
      // This frame will be reused to represent the parent transaction. Clear
      // out the log data structures.
      reads.clear();
      readsReadByParent.clear();
      creates.clear();
      localStoreCreates.clear();
      writes.clear();
      unobservedSamples.clear();
      extendedContracts.clear();
      retractedContracts.clear();
      delayedExtensions.clear();
      localStoreWrites.clear();
      workersCalled.clear();
      securityCache.reset();
      longerContracts = null;
      pendingAcquires.clear();
      pendingReleases.clear();
      locksCreated.clear();
      coordinated = false;

      if (parent != null) {
        writerMap = new WriterMap(parent.writerMap);
      } else {
        writerMap = new WriterMap(tid.topTid);
      }

      if (retrySignal != null) {
        synchronized (this) {
          if (retrySignal.equals(tid)) retrySignal = null;
        }
      }
    }
  }

  /**
   * Resolve unobserved subjects, either before attempting to commit at the top
   * level or before using a {@link Contract}.
   */
  public void resolveObservations() {
    // Skip if there's nothing to handle or if this was called in the middle of
    // an already ongoing resolve.
    if (!resolving && !unobservedSamples.isEmpty()) {
      Logging.METRICS_LOGGER.finer("PROCESSING SAMPLES IN " + tid);
      try {
        resolving = true;
        LinkedList<Subject> q = new LinkedList<>();
        for (Subject s : unobservedSamples.values()) {
          q.add(s);
        }
        AbstractSubject._Impl.processSamples(q);
        unobservedSamples.clear();
      } finally {
        resolving = false;
      }
    }
  }

  /**
   * Update the earliest expiry we have to check against.
   */
  protected synchronized void updateExpiry(long exp) {
    expiryToCheck = Math.min(expiryToCheck, exp);
  }

  /**
   * @return the earliest expiry used by this transaction.
   */
  protected long expiry() {
    return expiryToCheck;
  }

  /**
   * Updates logs and data structures in <code>_Impl</code>s to commit this
   * transaction. Assumes there is a parent transaction. This transaction log is
   * merged into the parent's log and any locks held are transferred to the
   * parent.
   */
  void commitNested() {
    // TODO See if lazy merging of logs helps performance.

    if (parent == null || !parent.tid.equals(tid.parent)) {
      // Reuse this frame for the parent transaction.
      return;
    }

    // Merge reads and transfer read locks.
    for (LongKeyMap<ReadMap.Entry> submap : reads) {
      for (ReadMap.Entry entry : submap.values()) {
        parent.transferReadLock(this, entry);
      }
    }

    for (ReadMap.Entry entry : readsReadByParent) {
      entry.releaseLock(this);
    }

    // Merge writes and transfer write locks.
    List<_Impl> parentWrites = parent.writes;
    for (_Impl obj : writes) {
      synchronized (obj) {
        if (obj.$history.$writeLockHolder == parent) {
          // The parent transaction already wrote to the object. Discard one
          // layer of history. In doing so, we also end up releasing this
          // transaction's write lock.
          obj.$history = obj.$history.$history;
        } else {
          // The parent transaction didn't write to the object. Add write to
          // parent and transfer our write lock.
          synchronized (parentWrites) {
            parentWrites.add(obj);
          }
        }
        obj.$writer = null;
        obj.$writeLockHolder = parent;

        // Signal any readers/writers.
        if (obj.$numWaiting > 0) obj.notifyAll();
      }
    }

    synchronized (parent.unobservedSamples) {
      parent.unobservedSamples.clear();
      parent.unobservedSamples.putAll(unobservedSamples);
    }

    for (_Impl obs : retractedContracts.values()) {
      synchronized (parent.retractedContracts) {
        if (!parent.retractedContracts.containsKey(obs))
          parent.retractedContracts.put(obs, obs);
      }
      synchronized (parent.extendedContracts) {
        if (parent.extendedContracts.containsKey(obs))
          parent.extendedContracts.remove(obs);
      }
      synchronized (parent.delayedExtensions) {
        if (parent.delayedExtensions.containsKey(obs))
          parent.delayedExtensions.remove(obs);
      }
    }

    for (_Impl obs : extendedContracts.values()) {
      synchronized (parent.delayedExtensions) {
        if (parent.delayedExtensions.containsKey(obs))
          parent.delayedExtensions.remove(obs);
      }
      synchronized (parent.retractedContracts) {
        if (parent.retractedContracts.containsKey(obs)) continue;
      }
      synchronized (parent.extendedContracts) {
        if (!parent.extendedContracts.containsKey(obs))
          parent.extendedContracts.put(obs, obs);
      }
    }

    for (Store s : delayedExtensions.storeSet()) {
      for (LongKeyMap.Entry<Long> e : delayedExtensions.get(s).entrySet()) {
        long onum = e.getKey();
        long expiry = e.getValue();
        synchronized (parent.retractedContracts) {
          if (parent.retractedContracts.containsKey(s, onum)) continue;
        }
        synchronized (parent.extendedContracts) {
          if (parent.extendedContracts.containsKey(s, onum)) continue;
        }
        synchronized (parent.delayedExtensions) {
          if (!parent.delayedExtensions.containsKey(s, onum))
            parent.delayedExtensions.put(s, onum, expiry);
        }
      }
    }

    WeakReferenceArrayList<_Impl> parentLocalStoreWrites =
        parent.localStoreWrites;
    for (_Impl obj : localStoreWrites) {
      synchronized (obj) {
        if (obj.$history.$writeLockHolder == parent) {
          // The parent transaction already wrote to the object. Discard one
          // layer of history. In doing so, we also end up releasing this
          // transaction's write lock.
          obj.$history = obj.$history.$history;
        } else {
          // The parent transaction didn't write to the object. Add write to
          // parent and transfer our write lock.
          synchronized (parentLocalStoreWrites) {
            parentLocalStoreWrites.add(obj);
          }
        }
        obj.$writer = null;
        obj.$writeLockHolder = parent;

        // Signal any readers/writers.
        if (obj.$numWaiting > 0) obj.notifyAll();
      }
    }

    // Merge creates and transfer write locks.
    List<_Impl> parentCreates = parent.creates;
    synchronized (parentCreates) {
      for (_Impl obj : creates) {
        parentCreates.add(obj);
        obj.$writeLockHolder = parent;
      }
    }

    WeakReferenceArrayList<_Impl> parentLocalStoreCreates =
        parent.localStoreCreates;
    synchronized (parentLocalStoreCreates) {
      for (_Impl obj : localStoreCreates) {
        parentLocalStoreCreates.add(obj);
        obj.$writeLockHolder = parent;
      }
    }

    // Merge the set of workers that have been called.
    synchronized (parent.workersCalled) {
      for (RemoteWorker worker : workersCalled) {
        if (!parent.workersCalled.contains(worker))
          parent.workersCalled.add(worker);
      }
    }

    // Replace the parent's security cache with the current cache.
    parent.securityCache.set((SecurityCache) securityCache);

    // Merge the writer map.
    synchronized (parent.writerMap) {
      parent.writerMap.putAll(writerMap);
    }

    // Merge pessimistic lock pending operations.
    // Acquires
    synchronized (parent.pendingAcquires) {
      parent.pendingAcquires.putAll(pendingAcquires);
    }
    // Releases
    synchronized (parent.pendingReleases) {
      parent.pendingReleases.putAll(pendingReleases);
    }
    // Creates
    synchronized (parent.locksCreated) {
      parent.locksCreated.putAll(locksCreated);
    }

    // Update the expiry time and drop this child.
    synchronized (parent) {
      parent.expiryToCheck = Math.min(expiryToCheck, parent.expiryToCheck);
      parent.child = null;
    }

    // Update the coordination flag.
    if (coordinated) {
      synchronized (parent) {
        parent.coordinated = true;
      }
    }
  }

  // Time before expiration runs out to process pending extensions
  public static final long EXTENSION_WINDOW = 1000;

  /**
   * Updates logs and data structures in <code>_Impl</code>s to commit this
   * transaction. Assumes this is a top-level transaction. All locks held by
   * this transaction are released.
   */
  void commitTopLevel() {
    // Release read locks.
    for (LongKeyMap<ReadMap.Entry> submap : reads) {
      for (ReadMap.Entry entry : submap.values()) {
        entry.releaseLock(this);
      }
    }

    // sanity check
    if (!readsReadByParent.isEmpty())
      throw new InternalError("something was read by a non-existent parent");

    // Release write locks and ownerships; update version numbers.
    Iterable<_Impl> chain = SysUtil.chain(writes, localStoreWrites);
    for (_Impl obj : chain) {
      if (!obj.$isOwned) {
        // The cached object is out-of-date. Evict it.
        obj.$ref.evict();
        continue;
      }

      synchronized (obj) {
        obj.$writer = null;
        obj.$writeLockHolder = null;
        obj.$writeLockStackTrace = null;
        // Don't increment the version if it's an extended metric contract
        if (!((obj instanceof MetricContract)
            && (extendedContracts.containsKey(obj)))) {
          obj.$version++;
          obj.$readMapEntry.incrementVersionAndUpdateExpiry(obj.$expiry);
        } else {
          obj.$readMapEntry.extendExpiry(obj.$expiry);
        }
        obj.$isOwned = false;

        // Discard one layer of history.
        obj.$history = obj.$history.$history;

        // Signal any waiting readers/writers.
        if (obj.$numWaiting > 0) obj.notifyAll();
      }

      // Note writes that committed so they can be flushed at the caller.
      TransactionManager tm = TransactionManager.getInstance();
      if (tm.committedWrites != null)
        tm.committedWrites.put(new Oid(obj), obj.$version);
    }

    // Release write locks on created objects and set version numbers.
    Iterable<_Impl> chain2 = SysUtil.chain(creates, localStoreCreates);
    for (_Impl obj : chain2) {
      if (!obj.$isOwned) {
        // The cached object is out-of-date. Evict it.
        obj.$ref.evict();
        continue;
      }

      obj.$writer = null;
      obj.$writeLockHolder = null;
      obj.$writeLockStackTrace = null;
      obj.$version = 1;
      obj.$readMapEntry.incrementVersionAndUpdateExpiry(obj.$expiry);
      obj.$isOwned = false;
    }

    // Update the local copies of contracts now that we've released the lock and
    // won't be aborted by this.
    for (Map.Entry<RemoteStore, LongKeyMap<SerializedObject>> e : longerContracts
        .entrySet()) {
      RemoteStore s = e.getKey();
      for (SerializedObject obj : e.getValue().values()) {
        s.updateCache(obj);
      }
    }

    // Queue up extension transactions
    Map<Store, List<DelayedExtension>> extensionsToSend = new HashMap<>();
    for (Store s : delayedExtensions.storeSet()) {
      List<DelayedExtension> l =
          new ArrayList<>(delayedExtensions.get(s).size());
      for (LongKeyMap.Entry<Long> e : delayedExtensions.get(s).entrySet()) {
        DelayedExtension d =
            new DelayedExtension(e.getValue() - EXTENSION_WINDOW, e.getKey());
        if (!l.contains(d)) l.add(d);
      }
      extensionsToSend.put(s, l);
    }

    for (Map.Entry<Store, List<DelayedExtension>> entry : extensionsToSend
        .entrySet()) {
      entry.getKey().sendExtensions(entry.getValue());
    }

    // Update this thread's lock state in the TransactionManager.
    TransactionManager tm = TransactionManager.getInstance();
    tm.updateLockState(pendingAcquires, pendingReleases);

    // Merge the security cache into the top-level label cache.
    securityCache.mergeWithTopLevel();

    // Log if there was a coordination.
    if (coordinated)
      Logging.METRICS_LOGGER.info("COORDINATED IN " + this);
  }

  /**
   * Transfers a read lock from a child transaction.
   */
  private void transferReadLock(Log child, ReadMap.Entry readMapEntry) {
    // If we already have a read lock, return; otherwise, register a read lock.
    Boolean lockedByAncestor = readMapEntry.transferLockToParent(child);
    if (lockedByAncestor == null) {
      // We already had a lock; nothing to do.
      return;
    }

    // Only record the read in this transaction if none of our ancestors have
    // read this object.
    if (!lockedByAncestor) {
      FabricSoftRef ref = readMapEntry.getRef();
      synchronized (reads) {
        reads.put(ref.store, ref.onum, readMapEntry);
      }
    } else {
      readsReadByParent.add(readMapEntry);
    }

    // Signal any readers/writers and clear the $reader stamp.
    readMapEntry.signalObject();
  }

  /**
   * Grabs a read lock for the given object.
   */
  void acquireReadLock(_Impl obj) {
    // If we already have a read lock, return; otherwise, register a read
    // lock.
    ReadMap.Entry readMapEntry = obj.$readMapEntry;
    boolean lockedByAncestor = false;
    synchronized (readMapEntry) {
      Set<Log> readers = readMapEntry.getReaders();
      if (readers.contains(this)) {
        // We already have a lock; nothing to do.
        return;
      }

      // Check if any of our ancestors already has a read lock.
      Log curAncestor = parent;
      while (curAncestor != null) {
        if (readers.contains(curAncestor)) {
          lockedByAncestor = true;
          break;
        }

        curAncestor = curAncestor.parent;
      }

      readMapEntry.addLock(this);
    }

    if (obj.$writer != this) {
      // Clear the object's write stamp -- the writer's write condition no
      // longer holds.
      obj.$writer = null;
    }

    // Only record the read in this transaction if none of our ancestors have
    // read this object.
    if (!lockedByAncestor) {
      synchronized (reads) {
        reads.put(obj.$ref.store, obj.$ref.onum, readMapEntry);
      }
    } else {
      readsReadByParent.add(readMapEntry);
    }
  }

  /**
   * Blocks until all threads in <code>threads</code> are finished.
   */
  void waitForThreads() {
  }

  public TransactionID getTid() {
    return tid;
  }

  public Log getChild() {
    return child;
  }

  /**
   * @return the log of the outermost ancestor transaction available at this
   *     worker. This will be different from the top-level transaction if the
   *     top-level was started at a different worker.
   */
  public Log getOutermost() {
    Log result = this;
    while (result.parent != null) {
      result = result.parent;
    }
    return result;
  }

  /**
   * Changes the waitsFor set to a singleton set containing the given log.
   */
  public void setWaitsFor(Log waitsFor) {
    synchronized (this.waitsFor) {
      this.waitsFor.clear();
      this.waitsFor.add(waitsFor);
    }
  }

  /**
   * Changes the waitsFor set to contain exactly the elements of the given set.
   */
  public void setWaitsFor(Set<Log> waitsFor) {
    synchronized (this.waitsFor) {
      this.waitsFor.clear();
      this.waitsFor.addAll(waitsFor);
    }
  }

  /**
   * Empties the waitsFor set.
   */
  public void clearWaitsFor() {
    synchronized (this.waitsFor) {
      this.waitsFor.clear();
    }
  }

  /**
   * Returns a copy of the waitsFor set.
   */
  public Set<Log> getWaitsFor() {
    synchronized (this.waitsFor) {
      return new HashSet<>(this.waitsFor);
    }
  }

  /**
   * Goes through this transaction log and performs an onum renumbering. This is
   * used by fabric.worker.TransactionRegistery.renumberObject. Do not call this
   * unless if you really know what you are doing.
   *
   * @deprecated
   */
  @Deprecated
  public void renumberObject(Store store, long onum, long newOnum) {
    ReadMap.Entry entry = reads.remove(store, onum);
    if (entry != null) {
      reads.put(store, newOnum, entry);
    }

    if (child != null) child.renumberObject(store, onum, newOnum);
  }

  @Override
  public String toString() {
    return "[" + tid + "]";
  }
}
