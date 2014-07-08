/**
 * Copyright (C) 2010 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.worker.transaction;

import java.util.ArrayList;
import java.util.List;

import fabric.worker.FabricSoftRef;
import fabric.lang.Object._Impl;

public final class ReadMapEntry {
  FabricSoftRef obj;
  List<Log> readLocks;
  int  versionNumber;
  long promise;
  int pinCount;

  ReadMapEntry(_Impl obj, long expiry) {
    this.obj = obj.$ref;
    this.readLocks = new ArrayList<Log>();
    this.versionNumber = obj.$version;
    this.promise  = expiry;
    this.pinCount = 1;
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
    _Impl obj = this.obj.get();
    if (obj == null) {
      // Object evicted from cache.
      
      // If object was a local-store object, it doesn't exist anymore.
      if (this.obj.store.isLocalStore()) return;
      
      obj = this.obj.store.readObjectFromCache(this.obj.onum);
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
