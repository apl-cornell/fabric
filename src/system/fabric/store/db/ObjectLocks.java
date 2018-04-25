package fabric.store.db;

import java.util.LinkedList;
import java.util.List;

import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Oid;
import fabric.lang.security.Principal;

/**
 * Read/write lock information for a single object.
 *
 * Supports a "soft" write lock, for updates that should exclude writers but not
 * readers.  This kind of lock is intended to support updates of information
 * which does not invalidate readers, like an extension of an expiry in the
 * warranties work.
 */
final class ObjectLocks {
  /**
   * The TID for the holder of the "hard" write lock.
   */
  Long writeLock;

  /**
   * The TID for the holder of the "soft" write lock.
   */
  Long softWriteLock;

  /**
   * Maps TIDs for the holders of read locks to the principal OIDs of the
   * workers in the transaction that have read locks on the object.
   */
  LongKeyMap<List<Oid>> readLocks;

  ObjectLocks() {
    this.writeLock = null;
    this.softWriteLock = null;
    this.readLocks = new LongKeyHashMap<>();
  }

  /**
   * @return true iff the object is locked (whether for a read or for a write).
   */
  synchronized boolean isLocked() {
    return writeLock != null || softWriteLock != null || !readLocks.isEmpty();
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
    if (writeLock != null && writeLock != tid) {
      // Conflicting write lock.
      throw new UnableToLockException();
    }

    // We already have the write lock, don't bother with anything.
    if (writeLock != null && writeLock == tid) return;

    if (softWriteLock != null && softWriteLock != tid) {
      // Conflicting with a softWriteLock
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
   * Registers a soft write lock for the given TID.
   *
   * @throws UnableToLockException
   *          when a conflicting lock is held. Soft write locks are always
   *          considered conflicting with other write locks (hard or soft).
   */
  synchronized void lockForSoftWrite(long tid) throws UnableToLockException {
    if (writeLock != null && writeLock != tid) {
      // Conflicting write lock.
      throw new UnableToLockException();
    }

    if (softWriteLock != null && softWriteLock != tid) {
      // Conflicting with a softWriteLock
      throw new UnableToLockException();
    }

    softWriteLock = tid;
  }

  /**
   * Removes a soft write lock for the given TID.
   *
   * @return true iff a lock was found for the given TID.
   */
  synchronized boolean unlockForSoftWrite(long tid) {
    if (softWriteLock != null && softWriteLock == tid) {
      softWriteLock = null;
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
    if (writeLock != null && writeLock != tid) {
      // Conflicting write lock.
      throw new UnableToLockException();
    }

    // We already have the write lock, don't bother with a read lock.
    if (writeLock != null && writeLock == tid) return;

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
