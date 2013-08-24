package fabric.worker.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fabric.common.VersionWarranty;
import fabric.common.util.ConcurrentOidKeyHashMap;
import fabric.lang.Object._Impl;
import fabric.worker.FabricSoftRef;
import fabric.worker.ObjectCache;
import fabric.worker.Store;

/**
 * A map from OIDs to Entry objects. An object's Entry records its version
 * number and a list of logs for transactions that have acquired a read lock on
 * that the object. Entries are removed from the map when they are no longer
 * read-locked.
 */
public final class ReadMap {
  /**
   * The backing map.
   */
  private final ConcurrentOidKeyHashMap<Entry> map;

  public static final class Entry {
    private final ReadMap outer;

    private boolean defunct;

    /**
     * A soft reference to the object represented by this entry.
     */
    private FabricSoftRef obj;

    /**
     * The list of transaction logs that have read locks on the object
     * represented by this entry.
     */
    private final List<Log> readLocks;

    /**
     * The version number on the object represented by this entry. 
     */
    private int versionNumber;

    /**
     * A warranty on this version of the object.
     */
    private VersionWarranty warranty;

    /**
     * Number of _Impls that have a reference to this entry. This is usually 1,
     * but can be more in certain transient states.
     */
    private int pinCount;

    private Entry(ReadMap outer, _Impl obj, VersionWarranty expiry) {
      this.defunct = false;
      this.outer = outer;
      this.obj = obj.$ref;
      this.readLocks = new ArrayList<>();
      this.versionNumber = obj.$version;
      this.warranty = expiry;
      this.pinCount = 1;
    }

    /**
     * @return a FabricSoftRef for the object represented by this entry.
     */
    FabricSoftRef getRef() {
      return obj;
    }

    /**
     * @return the store for the object represented by this entry.
     */
    Store getStore() {
      return obj.store;
    }

    synchronized int getVersionNumber() {
      return versionNumber;
    }

    public synchronized void incrementVersion() {
      versionNumber++;
    }

    public synchronized VersionWarranty getWarranty() {
      return warranty;
    }

    /**
     * Updates the warranty for the object.
     * 
     * @return true if successful; false if we already had a longer warranty.
     */
    public synchronized boolean updateWarranty(VersionWarranty warranty) {
      if (warranty.expiresAfter(this.warranty)) {
        this.warranty = warranty;
        return true;
      }

      return false;
    }

    /**
     * Determines whether any transaction has read the object represented by
     * this entry.
     */
    synchronized boolean haveReaders() {
      return !readLocks.isEmpty();
    }

    synchronized List<Log> getReaders() {
      return Collections.unmodifiableList(readLocks);
    }

    /**
     * Adds a lock for the given reader.
     */
    synchronized void addLock(Log reader) {
      readLocks.add(reader);
    }

    /**
     * Releases a lock for the given reader.
     */
    void releaseLock(Log reader) {
      synchronized (this) {
        readLocks.remove(reader);
        attemptGC();
      }

      signalObject();
    }

    /**
     * Transfers a lock from a reader to its parent.
     * 
     * @return <ul>
     *          <li>null, if the reader's parent already has a lock;
     *          <li>true, if an ancestor of the reader's parent already has a
     *          lock;
     *          <li>false, otherwise.
     *          </ul> 
     */
    synchronized Boolean transferLockToParent(Log reader) {
      final Log child = reader;
      final Log parent = child.parent;
      boolean lockedByAncestor = false;

      // Release child's read lock.
      readLocks.remove(child);

      // Scan for an existing read lock. At the same time, check if any of the
      // parent's ancestors already has a read lock.
      for (Log cur : readLocks) {
        if (cur == parent) {
          // Parent already has a lock. Nothing to do.
          return null;
        }

        lockedByAncestor |= parent.isDescendantOf(cur);
      }

      readLocks.add(parent);
      return lockedByAncestor;
    }

    /**
     * Aborts all transactions that have this entry in their logs.
     */
    synchronized void abortReaders() {
      for (Log reader : readLocks)
        reader.flagRetry();
    }

    /**
     * Decrements pin count and attempts garbage collection.
     * 
     * @return true iff this entry was removed from the read map.
     */
    public synchronized boolean depin() {
      pinCount--;
      return attemptGC();
    }

    /**
     * Attempts to update this entry's soft reference with the given impl. When
     * the impl's version number is the same as that of this entry, the update
     * succeeds. Otherwise, the update fails, and any transaction that has read
     * the old version of the object is aborted.
     * 
     * @return true iff successful.
     */
    private boolean updateImpl(_Impl impl) {
      synchronized (impl) {
        synchronized (this) {
          if (defunct) return false;

          if (versionNumber != impl.$version) {
            // Version numbers don't match. Retry all other transactions.
            // XXX What if we were given an older copy of the object?
            abortReaders();
            defunct = true;
            return false;
          }

          this.obj = impl.$ref;
          return true;
        }
      }
    }

    /**
     * Attempts to garbage-collect this entry. Garbage collection is performed
     * when the pin count is 0 and there are no read locks.
     * 
     * @return true iff garbage collection was performed.
     */
    private synchronized boolean attemptGC() {
      if (!readLocks.isEmpty() || pinCount > 0) {
        return false;
      }

      // Garbage collect.
      outer.map.remove(obj.store, obj.onum, this);
      return true;
    }

    /**
     * Signals the object corresponding to this entry (if the object is resident
     * in memory). After signalling, this method clears the $reader stamp of the
     * object. 
     */
    void signalObject() {
      _Impl obj = this.obj.get();
      if (obj == null) {
        // Object evicted from cache.

        // If object was a local-store object, it doesn't exist anymore.
        if (this.obj.store.isLocalStore()) return;

        ObjectCache.Entry entry = this.obj.store.readFromCache(this.obj.onum);
        if (entry == null) return;

        obj = entry.getImpl(false);
        if (obj == null) return;

        synchronized (this) {
          if (obj.$readMapEntry != this) return;
          this.obj = obj.$ref;
        }
      }

      synchronized (obj) {
        if (obj.$numWaiting > 0) obj.notifyAll();
        obj.$reader = Log.NO_READER;
      }
    }
  }

  ReadMap() {
    this.map = new ConcurrentOidKeyHashMap<>();
  }

  /**
   * Determines whether an object is read-locked.
   */
  boolean haveReaders(Store store, long onum) {
    Entry entry = map.get(store, onum);
    return entry != null && !entry.haveReaders();
  }

  /**
   * Sends abort signals to all transactions that have read the given OID.
   */
  void abortReaders(Store store, long onum) {
    Entry entry = map.get(store, onum);
    if (entry != null) entry.abortReaders();
  }

  /**
   * Obtains an Entry object for the given _Impl. If there is an existing Entry
   * for an older version of the _Impl, then all readers of the old version are
   * aborted, and the existing Entry is replaced with a new Entry.
   */
  Entry getEntry(_Impl impl, VersionWarranty warranty) {
    FabricSoftRef ref = impl.$ref;
    Entry newEntry = new Entry(this, impl, warranty);

    // Optimization: if the impl lives on the local store, it will never be
    // evicted, so no need to store the entry in the read map.
    if (ref.store.isLocalStore()) return newEntry;

    while (true) {
      Entry existing = map.putIfAbsent(ref.store, ref.onum, newEntry);
      if (existing == null) return newEntry;

      if (existing.updateImpl(impl)) return existing;

      if (map.replace(ref.store, ref.onum, existing, newEntry))
        return newEntry;
    }
  }
}