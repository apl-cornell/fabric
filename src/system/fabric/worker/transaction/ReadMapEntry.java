package fabric.worker.transaction;

import java.util.ArrayList;
import java.util.List;

import fabric.common.VersionWarranty;
import fabric.lang.Object._Impl;
import fabric.worker.FabricSoftRef;
import fabric.worker.ObjectCache;

/***
 * See {@link TransactionManager#readMap}.
 */
public final class ReadMapEntry {
  /**
   * The object to which this entry corresponds.
   */
  public FabricSoftRef obj;

  /**
   * The object's version number.
   */
  int versionNumber;

  /**
   * A list of logs for transactions that have read this version of the object
   * (as specified by obj and versionNumber).
   */
  List<Log> readLocks;

  VersionWarranty warranty;

  /**
   * The number of _Impls that have a reference to this object.
   */
  int pinCount;

  ReadMapEntry(_Impl obj, VersionWarranty expiry) {
    this.obj = obj.$ref;
    this.readLocks = new ArrayList<Log>();
    this.versionNumber = obj.$version;
    this.warranty = expiry;
    this.pinCount = 1;
  }

  public synchronized VersionWarranty warranty() {
    return warranty;
  }

  /**
   * Removes the lock owned by the given transaction log.
   */
  void releaseLock(Log lockHolder) {
    synchronized (this) {
      readLocks.remove(lockHolder);
      unpin();
    }

    signalObject();
  }

  /**
   * Does garbage collection when pin count is 0.
   * 
   * @return whether garbage collection was performed.
   */
  private boolean unpin() {
    if (readLocks.isEmpty() && pinCount == 0) {
      // There are no read locks and no references to this entry. Garbage
      // collect.
      synchronized (TransactionManager.readMap) {
        TransactionManager.readMap.remove(obj.store, obj.onum);
        return true;
      }
    }
    return false;
  }

  /**
   * Decrements pin count by 1 and does garbage collection if possible.
   * 
   * @return whether the entry was removed from the read map.
   */
  public synchronized boolean depin() {
    pinCount--;
    return unpin();
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
        this.obj = obj.$ref;
      }
    }

    synchronized (obj) {
      if (obj.$numWaiting > 0) obj.notifyAll();
      obj.$reader = Log.NO_READER;
    }
  }
}
