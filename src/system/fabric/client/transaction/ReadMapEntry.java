package fabric.client.transaction;

import fabric.client.FabricSoftRef;
import fabric.lang.Object.$Impl;

public final class ReadMapEntry {
  FabricSoftRef obj;
  LockList<Log> readLocks;
  int versionNumber;
  int pinCount;

  ReadMapEntry($Impl obj) {
    this.obj = obj.$ref;
    this.readLocks = new LockList<Log>();
    this.versionNumber = obj.$version;
    this.pinCount = 1;
  }
  
  /**
   * Removes the lock owned by the given transaction log.
   */
  void releaseLock(LockList.Node<Log> lockNode) {
    synchronized (this) {
      readLocks.remove(lockNode);
      unpin();
    }

    signalObject();
  }
  
  /**
   * Does garbage collection when pin count is 0.
   * @return whether garbage collection was performed.
   */
  private boolean unpin() {
    if (readLocks.isEmpty() && pinCount == 0) {
      // There are no read locks and no references to this entry. Garbage
      // collect.
      synchronized (TransactionManager.readMap) {
        TransactionManager.readMap.remove(obj.core, obj.onum);
        return true;
      }
    }
    return false;
  }
  
  /**
   * Decrements pin count by 1 and does garbage collection if possible.
   * @return whether the entry was removed from the read map.
   */
  public synchronized boolean depin() {
    pinCount--;
    return unpin();
  }

  /**
   * Signals the object corresponding to this entry (if the object is resident
   * in memory).
   * 
   * After signalling, this method clears the $reader stamp of the object.
   */
  void signalObject() {
    $Impl obj = this.obj.get();
    if (obj == null) {
      obj = this.obj.core.readObjectFromCache(this.obj.onum);
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
