package fabric.store.db;

import java.util.LinkedList;
import java.util.List;

import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Oid;
import fabric.lang.security.Principal;

/**
 * Read/write lock information for a single object.
 */
final class ObjectLocks {
  /**
   * The TID for the holder of the write lock.
   */
  Long writeLock;

  /**
   * Maps TIDs for the holders of read locks to the principal OIDs of the
   * workers in the transaction that have read locks on the object.
   */
  LongKeyMap<List<Oid>> readLocks;

  ObjectLocks() {
    this.writeLock = null;
    this.readLocks = new LongKeyHashMap<>();
  }

  /**
   * @return true iff the object is locked (whether for a read or for a write).
   */
  synchronized boolean isLocked() {
    return writeLock != null || !readLocks.isEmpty();
  }

  /**
   * Registers a write lock for the given TID.
   *
   * @throws UnableToLockException
   *          when a conflicting lock is held. Write locks are always considered
   *          conflicting. Read locks are considered conflicting if they are
   *          held by transactions whose TID is different from the one
   *          requesting the lock.
   */
  synchronized void lockForWrite(long tid) throws UnableToLockException {
    if (writeLock != null) {
      // Conflicting write lock.
      throw new UnableToLockException();
    }

    int numReadLocks = readLocks.size();
    if (numReadLocks > 1 || numReadLocks == 1 && !readLocks.containsKey(tid)) {
      // Read-locked by another transaction.
      throw new UnableToLockException();
    }

    writeLock = tid;
  }

  /**
   * Removes a write lock for the given TID.
   *
   * @return true iff a lock was found for the given TID.
   */
  synchronized boolean unlockForWrite(long tid) {
    if (writeLock != null && writeLock == tid) {
      writeLock = null;
      return true;
    }

    return false;
  }

  /**
   * Registers a read lock for the given TID and worker.
   *
   * @throws UnableToLockException
   *          when a conflicting lock is held. Write locks are always
   *          conflicting. Read locks never conflict.
   */
  synchronized void lockForRead(long tid, Principal worker)
      throws UnableToLockException {
    if (writeLock != null) {
      // Conflicting write lock.
      throw new UnableToLockException();
    }

    List<Oid> pins = readLocks.get(tid);
    if (pins == null) {
      pins = new LinkedList<>();
      readLocks.put(tid, pins);
    }

    pins.add(new Oid(worker.$getStore(), worker.$getOnum()));
  }

  /**
   * Removes a read lock for the given TID and worker.
   *
   * @return true iff a lock was found for the given TID and worker.
   */
  synchronized boolean unlockForRead(long tid, Principal worker) {
    List<Oid> pins = readLocks.get(tid);
    if (pins != null) {
      pins.remove(new Oid(worker.$getStore(), worker.$getOnum()));
      if (pins.isEmpty()) {
        readLocks.remove(tid);
        return true;
      }
    }

    return false;
  }
}
