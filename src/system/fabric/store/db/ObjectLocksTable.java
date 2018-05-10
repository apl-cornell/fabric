package fabric.store.db;

import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.lang.security.Principal;

/**
 * A table of read/write locks, with proper memory management.
 * <p>
 * Supports a "soft" write lock, for updates that should exclude writers but not
 * readers.  This kind of lock is intended to support updates of information
 * which does not invalidate readers, like an extension of an expiry in the
 * warranties work.
 * <p>
 * Locks are "memory managed" so that there is an entry only when there is a
 * user of the lock.  Furthermore, encapsulating the table here ensures that
 * multiple ObjectLocks aren't being used for the same onum at any time.
 * <p>
 * All locks are associated with a tid and worker.  Lock acquires will wait if
 * the only conflicting locks are by the same worker.  Conflicting acquires by
 * the same worker should only occur when the worker has already sent messages
 * for the second phase of 2PC for the transaction holding the conflicting lock
 * and the waiting transaction is a prepare by the same worker for a later
 * transaction operating on the resulting state.  Therefore the lock will soon
 * be freed with the store's state in a consistent state.
 * <p>
 * <strong>NOTE:</strong> It is critical that the following invariants hold in
 * the system for these waits to be safe:
 * <ul>
 *      <li> prepares are only sent to the store when the worker is not in phase
 *      1 of the commit protocol for any conflicting operations.
 *      <li> the store does <strong>not</strong> use these locks for operations
 *      outside of the prepare phase of the commit protocol.
 * </ul>
 * <p>
 * A violation of these invariants would be something like allowing multiple
 * conflicting optimistic operations to send prepares to the store from the same
 * worker or changing the store to use these locks to quickly perform local,
 * in-place updates.
 */
final class ObjectLocksTable {
  private final ConcurrentLongKeyMap<ObjectLocks> table =
      new ConcurrentLongKeyHashMap<>();

  /**
   * Locks are created if they do not already exist.  Locks will not be removed
   * until the last user of the lock has released it.
   */
  private ObjectLocks getLocks(long onum) {
    ObjectLocks lock = new ObjectLocks(onum);
    ObjectLocks existing = table.putIfAbsent(onum, lock);
    return existing == null ? lock : existing;
  }

  /**
   * @return true iff the onum is locked by anyone.
   */
  public boolean isLocked(long onum) {
    ObjectLocks locks;
    synchronized (locks = getLocks(onum)) {
      try {
        return locks.isLocked();
      } finally {
        if (!locks.inUse()) table.remove(onum, locks);
      }
    }
  }

  /**
   * Acquire a write lock.
   * @param onum the onum of the object being written.
   * @param tid the id of the writing transaction.
   * @param worker the {@link Principal} of the worker performing this write in
   * the transaction.
   */
  public void acquireWriteLock(long onum, long tid, Principal worker)
      throws UnableToLockException {
    ObjectLocks locks;
    synchronized (locks = getLocks(onum)) {
      try {
        locks.lockForWrite(tid, worker);
      } finally {
        if (!locks.inUse()) table.remove(onum, locks);
      }
    }
  }

  /**
   * Release a write lock.
   * @param onum the onum of the object that was being written.
   * @param tid the id of the writing transaction.
   * @param worker the {@link Principal} of the worker that was performing this
   * write in the transaction.
   */
  public void releaseWriteLock(long onum, long tid, Principal worker) {
    ObjectLocks locks;
    synchronized (locks = getLocks(onum)) {
      try {
        locks.unlockForWrite(tid, worker);
      } finally {
        if (!locks.inUse()) table.remove(onum, locks);
      }
    }
  }

  /**
   * Acquire a soft write lock.
   * @param onum the onum of the object being written softly.
   * @param tid the id of the softly writing transaction.
   * @param worker the {@link Principal} of the worker performing this soft
   * write in the transaction.
   */
  public void acquireSoftWriteLock(long onum, long tid, Principal worker)
      throws UnableToLockException {
    ObjectLocks locks;
    synchronized (locks = getLocks(onum)) {
      try {
        locks.lockForSoftWrite(tid, worker);
      } finally {
        if (!locks.inUse()) table.remove(onum, locks);
      }
    }
  }

  /**
   * Release a soft write lock.
   * @param onum the onum of the object that was being written softly.
   * @param tid the id of the softly writing transaction.
   * @param worker the {@link Principal} of the worker that was performing this
   * soft write in the transaction.
   */
  public void releaseSoftWriteLock(long onum, long tid, Principal worker) {
    ObjectLocks locks;
    synchronized (locks = getLocks(onum)) {
      try {
        locks.unlockForSoftWrite(tid, worker);
      } finally {
        if (!locks.inUse()) table.remove(onum, locks);
      }
    }
  }

  /**
   * Acquire a read lock.
   * @param onum the onum of the object being read.
   * @param tid the id of the reading transaction.
   * @param worker the {@link Principal} of the worker performing this read in
   * the transaction.
   */
  public void acquireReadLock(long onum, long tid, Principal worker)
      throws UnableToLockException {
    ObjectLocks locks;
    synchronized (locks = getLocks(onum)) {
      try {
        locks.lockForRead(tid, worker);
      } finally {
        if (!locks.inUse()) table.remove(onum, locks);
      }
    }
  }

  /**
   * Release a read lock.
   * @param onum the onum of the object that was being read.
   * @param tid the id of the reading transaction.
   * @param worker the {@link Principal} of the worker that was performing this
   * read in the transaction.
   */
  public void releaseReadLock(long onum, long tid, Principal worker) {
    ObjectLocks locks;
    synchronized (locks = getLocks(onum)) {
      try {
        locks.unlockForRead(tid, worker);
      } finally {
        if (!locks.inUse()) table.remove(onum, locks);
      }
    }
  }
}
