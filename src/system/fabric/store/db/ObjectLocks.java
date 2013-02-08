package fabric.store.db;

import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.MutableInteger;

/**
 * Read/write lock information for a single object.
 */
final class ObjectLocks {
  /**
   * The TID for the holder of the write lock.
   */
  Long writeLock;

  /**
   * Maps TIDs for the holders of read locks to the number of workers in the
   * transaction that have read locks on the object.
   */
  LongKeyMap<MutableInteger> readLocks;

  ObjectLocks() {
    this.writeLock = null;
    this.readLocks = new LongKeyHashMap<MutableInteger>();
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
   * Registers a read lock for the given TID.
   * 
   * @throws UnableToLockException
   *          when a conflicting lock is held. Write locks are always
   *          conflicting. Read locks never conflict.
   */
  synchronized void lockForRead(long tid) throws UnableToLockException {
    if (writeLock != null) {
      // Conflicting write lock.
      throw new UnableToLockException();
    }

    MutableInteger pinCount = readLocks.get(tid);
    if (pinCount == null) {
      pinCount = new MutableInteger(1);
      readLocks.put(tid, pinCount);
    } else {
      pinCount.value++;
    }
  }
}
