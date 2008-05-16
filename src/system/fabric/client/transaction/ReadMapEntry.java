package fabric.client.transaction;

import java.lang.ref.SoftReference;

import fabric.client.Core;
import fabric.lang.Object.$Impl;

public final class ReadMapEntry {
  Core core;
  long onum;
  SoftReference<$Impl> obj;
  LockList<Log> readLocks;
  int versionNumber;
  int pinCount;

  ReadMapEntry($Impl obj) {
    this.core = obj.$getCore();
    this.onum = obj.$getOnum();
    this.obj = new SoftReference<$Impl>(obj);
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

      if (readLocks.isEmpty() && pinCount == 0) {
        // There are no read locks and no references to this entry. Garbage
        // collect.
        synchronized (TransactionManager.readMap) {
          TransactionManager.readMap.remove(core, onum);
        }
      }
    }

    signalObject();
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
      obj = core.readObjectFromCache(onum);
      if (obj == null) return;

      synchronized (this) {
        this.obj = new SoftReference<$Impl>(obj);
      }
    }

    synchronized (obj) {
      if (obj.$numWaiting > 0) obj.notifyAll();
      obj.$reader = null;
    }
  }
}
