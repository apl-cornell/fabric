package fabric.worker.transaction;

import java.util.*;

import fabric.common.TransactionID;
import fabric.common.Util;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.WeakReferenceArrayList;
import fabric.lang.Object._Impl;
import fabric.lang.security.SecurityCache;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.debug.Timing;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.UpdateMap;

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
  UpdateMap updateMap;

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
   * non-null and indicates the transaction in the stack that is
   * to be retried; all child transactions are to be aborted.
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
  protected final OidKeyHashMap<ReadMapEntry> reads;

  /**
   * Reads on objects that have been read by an ancestor transaction.
   */
  protected final List<ReadMapEntry> readsReadByParent;

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
      UNPREPARED, PREPARING, PREPARED, PREPARE_FAILED, COMMITTING, COMMITTED, ABORTING, ABORTED
    }

    public Values value = Values.UNPREPARED;
  }

  public final AbstractSecurityCache securityCache;

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
    this.reads = new OidKeyHashMap<ReadMapEntry>();
    this.readsReadByParent = new ArrayList<ReadMapEntry>();
    this.creates = new ArrayList<_Impl>();
    this.localStoreCreates = new WeakReferenceArrayList<_Impl>();
    this.writes = new ArrayList<_Impl>();
    this.localStoreWrites = new WeakReferenceArrayList<_Impl>();
    this.workersCalled = new ArrayList<RemoteWorker>();

    if (parent != null) {
      try {
        Timing.SUBTX.begin();
        this.updateMap = new UpdateMap(parent.updateMap);
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
      this.updateMap = new UpdateMap(this.tid.topTid);
      commitState = new CommitState();
      this.securityCache = new SecurityCache(null);

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
   * Returns a set of stores affected by this transaction. This is the set of
   * stores to contact when preparing and committing a transaction.
   */
  Set<Store> storesToContact() {
    Set<Store> result = new HashSet<Store>();

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
    Set<Store> result = new HashSet<Store>();
    result.addAll(reads.storeSet());
    for (ReadMapEntry entry : readsReadByParent) {
      result.add(entry.obj.store);
    }
    
    return result;
  }

  /**
   * Returns a map from onums to version numbers of objects read at the given
   * store. Reads on created and modified objects are not included.
   */
  @SuppressWarnings("unchecked")
  LongKeyMap<Integer> getReadsForStore(Store store) {
    LongKeyMap<Integer> result = new LongKeyHashMap<Integer>();
    LongKeyMap<ReadMapEntry> submap = reads.get(store);
    if (submap == null) return result;

    for (LongKeyMap.Entry<ReadMapEntry> entry : submap.entrySet()) {
      result.put(entry.getKey(), entry.getValue().versionNumber);
    }
    
    if (parent != null) {
      for (ReadMapEntry entry : readsReadByParent) {
        result.put(entry.obj.onum, entry.versionNumber);
      }
    }

    if (store.isLocalStore()) {
      for (_Impl write : Util.chain(localStoreWrites, localStoreCreates))
        result.remove(write.$getOnum());
    } else {
      for (_Impl write : Util.chain(writes, creates))
        if (write.$getStore() == store) result.remove(write.$getOnum());
    }

    return result;
  }

  /**
   * Returns a collection of objects modified at the given store. Writes on
   * created objects are not included.
   */
  Collection<_Impl> getWritesForStore(Store store) {
    // This should be a Set of _Impl, but we have a map indexed by OID to
    // avoid calling hashCode and equals on the _Impls.
    LongKeyMap<_Impl> result = new LongKeyHashMap<_Impl>();

    if (store.isLocalStore()) {
      for (_Impl obj : localStoreWrites) {
        result.put(obj.$getOnum(), obj);
      }

      for (_Impl create : localStoreCreates) {
        result.remove(create.$getOnum());
      }
    } else {
      for (_Impl obj : writes)
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
    LongKeyMap<_Impl> result = new LongKeyHashMap<_Impl>();

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
    Queue<Log> toFlag = new LinkedList<Log>();
    toFlag.add(this);
    while (!toFlag.isEmpty()) {
      Log log = toFlag.remove();
      synchronized (log) {
        if (log.child != null) toFlag.add(log.child);
        if (log.retrySignal == null || log.retrySignal.isDescendantOf(tid))
          log.retrySignal = tid;
        // XXX This was here to unblock a thread that may have been waiting on a
        // XXX lock. Commented out because it was causing a bunch of
        // XXX InterruptedExceptions and ClosedByInterruptExceptions that
        // XXX weren't being handled properly.

        // log.thread.interrupt();
      }
    }
  }

  /**
   * Updates logs and data structures in <code>_Impl</code>s to abort this
   * transaction. All locks held by this transaction are released.
   */
  @SuppressWarnings("unchecked")
  void abort() {
    // Release read locks.
    for (LongKeyMap<ReadMapEntry> submap : reads) {
      for (ReadMapEntry entry : submap.values()) {
        entry.releaseLock(this);
      }
    }

    for (ReadMapEntry entry : readsReadByParent)
      entry.releaseLock(this);

    // Roll back writes and release write locks.
    for (_Impl write : Util.chain(writes, localStoreWrites)) {
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
      localStoreWrites.clear();
      workersCalled.clear();
      securityCache.reset();

      if (parent != null) {
        updateMap = new UpdateMap(parent.updateMap);
      } else {
        updateMap = new UpdateMap(tid.topTid);
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
    for (LongKeyMap<ReadMapEntry> submap : reads) {
      for (ReadMapEntry entry : submap.values()) {
        parent.transferReadLock(this, entry);
      }
    }

    for (ReadMapEntry entry : readsReadByParent) {
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

    // Merge the update map.
    synchronized (parent.updateMap) {
      parent.updateMap.putAll(updateMap);
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
  @SuppressWarnings("unchecked")
  void commitTopLevel() {
    // Release read locks.
    for (LongKeyMap<ReadMapEntry> submap : reads) {
      for (ReadMapEntry entry : submap.values()) {
        entry.releaseLock(this);
      }
    }

    // sanity check
    if (!readsReadByParent.isEmpty())
      throw new InternalError("something was read by a non-existent parent");

    // Release write locks and ownerships; update version numbers.
    for (_Impl obj : Util.chain(writes, localStoreWrites)) {
      if (!obj.$isOwned) {
        // The cached object is out-of-date. Evict it.
        obj.$ref.evict();
        continue;
      }

      synchronized (obj) {
        obj.$writer = null;
        obj.$writeLockHolder = null;
        obj.$version++;
        obj.$readMapEntry.versionNumber++;
        obj.$isOwned = false;

        // Discard one layer of history.
        obj.$history = obj.$history.$history;

        // Signal any waiting readers/writers.
        if (obj.$numWaiting > 0) obj.notifyAll();
      }
    }

    // Release write locks on created objects and set version numbers.
    for (_Impl obj : Util.chain(creates, localStoreCreates)) {
      if (!obj.$isOwned) {
        // The cached object is out-of-date. Evict it.
        obj.$ref.evict();
        continue;
      }

      obj.$writer = null;
      obj.$writeLockHolder = null;
      obj.$version = 1;
      obj.$readMapEntry.versionNumber = 1;
      obj.$isOwned = false;
    }
  }

  /**
   * Transfers a read lock from a child transaction.
   */
  private void transferReadLock(Log child, ReadMapEntry readMapEntry) {
    // If we already have a read lock, return; otherwise, register a read lock.
    boolean lockedByAncestor = false;
    synchronized (readMapEntry) {
      // Release child's read lock.
      readMapEntry.readLocks.remove(child);

      // Scan the list for an existing read lock. At the same time, check if
      // any of our ancestors already has a read lock.
      for (Log cur : readMapEntry.readLocks) {
        if (cur == this) {
          // We already have a lock; nothing to do.
          return;
        }

        if (!lockedByAncestor && isDescendantOf(cur)) lockedByAncestor = true;
      }

      readMapEntry.readLocks.add(this);
    }

    // Only record the read in this transaction if none of our ancestors have
    // read this object.
    if (!lockedByAncestor) {
      synchronized (reads) {
        reads.put(readMapEntry.obj.store, readMapEntry.obj.onum, readMapEntry);
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
    ReadMapEntry readMapEntry = obj.$readMapEntry;
    boolean lockedByAncestor = false;
    synchronized (readMapEntry) {
      // Scan the list for an existing read lock. At the same time, check if
      // any of our ancestors already has a read lock.
      for (Log cur : readMapEntry.readLocks) {
        if (cur == this) {
          // We already have a lock; nothing to do.
          return;
        }

        if (!lockedByAncestor && isDescendantOf(cur)) lockedByAncestor = true;
      }

      readMapEntry.readLocks.add(this);
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

  void removePromisedReads(long commitTime) {
    // Generics. Ugh.

    Iterator<LongKeyMap<ReadMapEntry>> outer = reads.iterator();
    while (outer.hasNext()) {
      Collection<ReadMapEntry> values = outer.next().values();

      Iterator<ReadMapEntry> inner = values.iterator();
      while (inner.hasNext()) {
        ReadMapEntry entry = inner.next();

        if (entry.promise > commitTime) {
          entry.releaseLock(this);
          inner.remove();
        }
      }

      if (values.isEmpty()) outer.remove();
    }

    // sanity check
    if (!readsReadByParent.isEmpty())
      throw new InternalError("something was read by a non-existent parent");
  }

  /**
   * Goes through this transaction log and performs an onum renumbering. This is
   * used by fabric.worker.TransactionRegistery.renumberObject. Do not call this
   * unless if you really know what you are doing.
   * 
   * @deprecated
   */
  public void renumberObject(Store store, long onum, long newOnum) {
    ReadMapEntry entry = reads.remove(store, onum);
    if (entry != null) {
      reads.put(store, newOnum, entry);
    }

    if (child != null) child.renumberObject(store, onum, newOnum);
  }
}
