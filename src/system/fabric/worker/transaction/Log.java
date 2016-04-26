package fabric.worker.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import fabric.common.SysUtil;
import fabric.common.Timing;
import fabric.common.TransactionID;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.common.util.WeakReferenceArrayList;
import fabric.lang.Object._Impl;
import fabric.lang.security.LabelCache;
import fabric.lang.security.SecurityCache;
import fabric.worker.FabricSoftRef;
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
   * transaction or completed sub-transactions, but have not been staged and
   * have not been read by an ancestor transaction. Reads from running or
   * aborted sub-transactions don't count here.
   */
  // Proxy objects aren't used for keys here because doing so would result in
  // calls to hashcode() and equals() on such objects, resulting in fetching the
  // corresponding Impls from the store.
  protected final OidKeyHashMap<ReadMap.Entry> unstagedReads;

  /**
   * Maps OIDs to <code>readMap</code> entries for objects read in this
   * transaction or completed sub-transactions, that have been staged, but have
   * not been read by an ancestor transaction. Reads from running or aborted
   * sub-transactions don't count here.
   */
  // Proxy objects aren't used for keys here because doing so would result in
  // calls to hashcode() and equals() on such objects, resulting in fetching the
  // corresponding Impls from the store.
  protected final OidKeyHashMap<ReadMap.Entry> stagedReads;

  /**
   * Reads on objects that have been read by this transaction and an ancestor
   * transaction. Both staged and unstaged objects are included here.
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
   * A collection of unstaged objects modified in this transaction or completed
   * sub-transactions. Objects modified in running or aborted sub-transactions
   * don't count here, nor do objects that were also modified by an ancestor
   * transaction. To keep them from being pinned, objects on local store
   * are not tracked here, either.
   */
  protected final List<_Impl> unstagedWritesx;

  /**
   * A collection of objects that have been write-staged in this transaction, a
   * completed sub-transaction, or an aborted sub-transaction. Objects
   * write-staged in running sub-transactions don't count here, nor do objects
   * that were also modified by an ancestor transaction. To keep them from being
   * pinned, objects on local store are not tracked here, either.
   */
  protected final List<_Impl> stagedWritesx;

  /**
   * A collection of objects modified in this transaction or completed
   * sub-transactions, that were also modified by an ancestor transaction.
   * Objects modified in running or aborted sub-transactions don't count here.
   * To keep them from being pinned, objects on local store are not tracked here
   * either.
   */
  protected final List<_Impl> writesWrittenByParent;

  /**
   * The set of OIDs for all non-local-store objects that were written by an
   * ancestor transaction.
   */
  protected final OidKeyHashMap<Void> parentWrites;

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
    this.unstagedReads = new OidKeyHashMap<>();
    this.stagedReads = new OidKeyHashMap<>();
    this.readsReadByParent = new ArrayList<>();
    this.creates = new ArrayList<>();
    this.localStoreCreates = new WeakReferenceArrayList<>();
    this.unstagedWritesx = new ArrayList<>();
    this.stagedWritesx = new ArrayList<>();
    this.writesWrittenByParent = new ArrayList<>();
    this.parentWrites = new OidKeyHashMap<>();
    this.localStoreWrites = new WeakReferenceArrayList<>();
    this.workersCalled = new ArrayList<>();
    this.startTime = System.currentTimeMillis();

    if (parent != null) {
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
    return unstagedWritesx.isEmpty() && stagedWritesx.isEmpty()
        && writesWrittenByParent.isEmpty() && creates.isEmpty()
        && workersCalled.isEmpty();
  }

  /**
   * Returns the set of stores to contact when staging the transaction.
   */
  Set<Store> storesToStage() {
    // Start with the stores needed to stage the parent transaction.
    Set<Store> result =
        parent == null ? new HashSet<Store>() : parent.storesToStage();

    // Add stores on which we have unstaged reads and writes.
    result.addAll(unstagedReads.storeSet());

    for (_Impl obj : unstagedWritesx) {
      if (obj.$isOwned) result.add(obj.$getStore());
    }

    return result;
  }

  /**
   * Returns the set of stores to contact when preparing/committing the
   * transaction. Assumes {@code this} represents a top-level transaction.
   */
  Set<Store> storesToCommit() {
    Set<Store> result = new HashSet<>();

    result.addAll(unstagedReads.storeSet());
    result.addAll(stagedReads.storeSet());

    for (_Impl obj : unstagedWritesx) {
      if (obj.$isOwned) result.add(obj.$getStore());
    }

    for (_Impl obj : stagedWritesx) {
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
    Set<Store> result =
        parent == null ? new HashSet<Store>() : parent.storesToCheckFreshness();
    result.addAll(unstagedReads.storeSet());

    return result;
  }

  /**
   * Returns a map from onums to version numbers of objects read at the given
   * store. Reads on created objects are never included.
   *
   * @param includeModified
   *          whether to include reads on modified objects.
   */
  LongKeyMap<Integer> getUnstagedReadsForStore(Store store,
      boolean includeModified) {
    // Walk up the log stack and collect all unstaged reads.
    LongKeyMap<Integer> result = new LongKeyHashMap<>();
    for (Log curLog = this; curLog != null; curLog = curLog.parent) {
      LongKeyMap<ReadMap.Entry> submap = curLog.unstagedReads.get(store);
      if (submap == null) continue;

      for (LongKeyMap.Entry<ReadMap.Entry> entry : submap.entrySet()) {
        result.put(entry.getKey(), entry.getValue().getVersionNumber());
      }
    }

    // Walk up the log stack again, this time filtering out created objects (and
    // modified objects, if desired).
    for (Log curLog = this; curLog != null; curLog = curLog.parent) {
      if (store.isLocalStore()) {
        Iterable<_Impl> writesToExclude = includeModified
            ? Collections.<_Impl> emptyList() : curLog.localStoreWrites;
        Iterable<_Impl> chain =
            SysUtil.chain(writesToExclude, curLog.localStoreCreates);
        for (_Impl write : chain)
          result.remove(write.$getOnum());
      } else {
        Iterable<_Impl> writesToExclude;
        if (includeModified) {
          writesToExclude = Collections.<_Impl> emptyList();
        } else {
          writesToExclude =
              SysUtil.chain(curLog.stagedWritesx, curLog.unstagedWritesx);
        }
        Iterable<_Impl> chain = SysUtil.chain(writesToExclude, curLog.creates);
        for (_Impl write : chain)
          if (write.$getStore() == store) result.remove(write.$getOnum());
      }
    }

    return result;
  }

  /**
   * Returns a collection of objects modified at the given store. Writes on
   * created objects are not included. Assumes {@code this} represents a
   * top-level transaction.
   */
  Collection<_Impl> getWritesForStore(Store store) {
    // This should be a Set of _Impl, but we have a map indexed by OID to
    // avoid calling hashCode and equals on the _Impls.
    LongKeyMap<_Impl> result = new LongKeyHashMap<>();

    if (store.isLocalStore()) {
      for (_Impl obj : localStoreWrites) {
        result.put(obj.$getOnum(), obj);
      }

      for (_Impl create : localStoreCreates) {
        result.remove(create.$getOnum());
      }
    } else {
      for (_Impl obj : SysUtil.chain(unstagedWritesx, stagedWritesx))
        if (obj.$getStore() == store && obj.$isOwned)
          result.put(obj.$getOnum(), obj);

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
   * transaction. All locks on unstaged objects held by this transaction are
   * released.
   */
  void abort() {
    // Release locks on unstaged reads.
    for (LongKeyMap<ReadMap.Entry> submap : unstagedReads) {
      for (ReadMap.Entry entry : submap.values()) {
        entry.releaseLock(this);
      }
    }

    // Release read locks for objects read by parent. Doesn't matter if the
    // the objects have been staged -- the parent should already have a lock.
    for (ReadMap.Entry entry : readsReadByParent)
      entry.releaseLock(this);

    // Transfer locks on staged reads to the parent.
    for (LongKeyMap<ReadMap.Entry> submap : stagedReads) {
      for (ReadMap.Entry entry : submap.values()) {
        parent.transferReadLock(this, entry, true);
      }
    }

    // Ensure the parent has write locks on staged writes.
    for (_Impl obj : stagedWritesx) {
      synchronized (obj) {
        // Create a copy of the backup object and insert it into the object
        // history.
        _Impl newHist = obj.$history.clone();
        newHist.$history = obj.$history;
        obj.$history = newHist;

        // On the parent's behalf, obtain a write lock on the new backup.
        newHist.$writeLockHolder = parent;
        if (TransactionManager.TRACE_WRITE_LOCKS)
          newHist.$writeLockStackTrace = Thread.currentThread().getStackTrace();

        // Add the object to the list of the parent's staged writes.
        synchronized (parent.stagedWritesx) {
          parent.stagedWritesx.add(obj);
        }
      }
    }

    // Roll back writes.
    Iterable<_Impl> chain = SysUtil.chain(unstagedWritesx, stagedWritesx,
        writesWrittenByParent, localStoreWrites);
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
      // out the log data structures. Staged reads and staged writes are
      // preserved, however, so that we don't lose track of what we've locked on
      // the store.
      unstagedReads.clear();
      readsReadByParent.clear();
      creates.clear();
      localStoreCreates.clear();
      unstagedWritesx.clear();
      writesWrittenByParent.clear();
      localStoreWrites.clear();
      workersCalled.clear();
      securityCache.reset();

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
    for (LongKeyMap<ReadMap.Entry> submap : unstagedReads) {
      for (ReadMap.Entry entry : submap.values()) {
        parent.transferReadLock(this, entry, false);
      }
    }

    for (LongKeyMap<ReadMap.Entry> submap : stagedReads) {
      for (ReadMap.Entry entry : submap.values()) {
        parent.transferReadLock(this, entry, true);
      }
    }

    for (ReadMap.Entry entry : readsReadByParent) {
      entry.releaseLock(this);
    }

    // Merge writes and transfer write locks.
    // Some grossness to allow for code reuse.
    Pair<List<_Impl>, List<_Impl>> staged =
        new Pair<>(parent.stagedWritesx, stagedWritesx);
    Pair<List<_Impl>, List<_Impl>> unstaged =
        new Pair<>(parent.unstagedWritesx, unstagedWritesx);
    Pair<List<_Impl>, List<_Impl>> ancestor =
        new Pair<>(parent.writesWrittenByParent, writesWrittenByParent);
    for (Pair<List<_Impl>, List<_Impl>> pair : new Pair[] { staged, unstaged,
        ancestor }) {
      List<_Impl> parentWrites = pair.first;
      List<_Impl> myWrites = pair.second;
      for (_Impl obj : myWrites) {
        synchronized (obj) {
          if (obj.$history.$writeLockHolder == parent) {
            // The parent transaction already wrote to the object. (This should
            // only happen in the "ancestor" iteration of the outer loop.)
            // Discard one layer of history. In doing so, we also end up
            // releasing this transaction's write lock.
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

    synchronized (parent) {
      parent.child = null;
    }
  }

  /**
   * Updates logs and data structures in <code>_Impl</code>s to commit this
   * transaction. Assumes this is a top-level transaction. All locks held by
   * this transaction are released.
   */
  void commitTopLevel() {
    // Release read locks.
    for (LongKeyMap<ReadMap.Entry> submap : stagedReads) {
      for (ReadMap.Entry entry : submap.values()) {
        entry.releaseLock(this);
      }
    }

    // sanity check
    if (!readsReadByParent.isEmpty())
      throw new InternalError("something was read by a non-existent parent");

    // Another sanity check: there should be never be unstaged reads in the
    // top-level transaction.
    if (!unstagedReads.isEmpty()) {
      throw new InternalError("Invariant violation: found an unstaged read in "
          + "a top-level transaction.");
    }

    // There should also never be unstaged writes in the top-level transaction.
    if (!unstagedWritesx.isEmpty()) {
      throw new InternalError("Invariant violation: found an unstaged write in "
          + "a top-level transaction.");
    }

    // In a top-level transaction, there should never be writes written by a
    // parent transaction.
    if (!writesWrittenByParent.isEmpty())
      throw new InternalError("something was written by a non-existent parent");

    // Release write locks and ownerships; update version numbers.
    Iterable<_Impl> chain = SysUtil.chain(stagedWritesx, localStoreWrites);
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
        obj.$version++;
        obj.$readMapEntry.incrementVersion();
        obj.$isOwned = false;

        // Discard one layer of history.
        obj.$history = obj.$history.$history;

        // Signal any waiting readers/writers.
        if (obj.$numWaiting > 0) obj.notifyAll();
      }
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
      obj.$readMapEntry.incrementVersion();
      obj.$isOwned = false;
    }

    // Merge the security cache into the top-level label cache.
    securityCache.mergeWithTopLevel();
  }

  /**
   * Transfers a read lock from a child transaction.
   *
   * @param staged whether the lock is for a staged read.
   */
  private void transferReadLock(Log child, ReadMap.Entry readMapEntry,
      boolean staged) {
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
      OidKeyHashMap<ReadMap.Entry> reads = staged ? stagedReads : unstagedReads;
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
      synchronized (unstagedReads) {
        unstagedReads.put(obj.$ref.store, obj.$ref.onum, readMapEntry);
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
   * Goes through this transaction log and performs an onum renumbering. This is
   * used by fabric.worker.TransactionRegistery.renumberObject. Do not call this
   * unless if you really know what you are doing.
   *
   * @deprecated
   */
  @Deprecated
  public void renumberObject(Store store, long onum, long newOnum) {
    ReadMap.Entry entry = unstagedReads.remove(store, onum);
    if (entry != null) {
      unstagedReads.put(store, newOnum, entry);
    }

    entry = stagedReads.remove(store, onum);
    if (entry != null) {
      stagedReads.put(store, newOnum, entry);
    }

    if (child != null) child.renumberObject(store, onum, newOnum);
  }

  @Override
  public String toString() {
    return "[" + tid + "]";
  }
}
