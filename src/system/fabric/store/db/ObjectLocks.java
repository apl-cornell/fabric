package fabric.store.db;

import static fabric.common.Logging.STORE_TRANSACTION_LOGGER;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Oid;
import fabric.lang.security.Principal;
import fabric.store.db.ObjectDB.PendingTransaction;

/**
 * Read/write lock information for a single object.
 * <p>
 * Supports a "soft" write lock, for updates that should exclude writers but not
 * readers.  This kind of lock is intended to support updates of information
 * which does not invalidate readers, like an extension of an expiry in the
 * warranties work.
 * <p>
 * All locks are associated with a tid and worker.  Lock acquires will wait if
 * the only conflicting locks are by the same worker, which should only happen
 * if the worker has already sent messages for the second phase of 2PC and the
 * waiter is a later transaction operating on the resulting state.  Therefore
 * the lock will soon be freed with the store's state in a consistent state.
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
final class ObjectLocks {

  /** The onum this lock protects on the store. */
  final long onum;

  /**
   * The TID for the holder of the "hard" write lock.
   */
  Long writeLock;

  /**
   * The TID for the holder of the "soft" write lock.
   */
  Long softWriteLock;

  /**
   * The oid of the worker holding the write lock (of either kind).  This allows
   * for transactions to wait for a lock to be freed if held by the same worker,
   * which usually means that the lock is held by a transaction that has been
   * aborted at the worker already.
   */
  Oid writer;

  /**
   * Maps TIDs for the holders of read locks to the principal OIDs of the
   * workers in the transaction that have read locks on the object.
   */
  LongKeyMap<List<Oid>> readLocks;

  /**
   * Count of the number of waiting threads, hoping to wait out a transaction
   * that should be done soon from the same worker.  Used to check if the lock
   * is still being used before removing it in {@link ObjectDB}.
   */
  private int waiters;

  ObjectLocks(long onum) {
    this.onum = onum;
    this.writeLock = null;
    this.softWriteLock = null;
    this.writer = null;
    this.readLocks = new LongKeyHashMap<>();
    this.waiters = 0;
  }

  /**
   * @return true iff the object is locked (whether for a read or for a write).
   */
  synchronized boolean isLocked() {
    return writeLock != null || softWriteLock != null || !readLocks.isEmpty();
  }

  /**
   * @return true iff the object is locked (whether for a read or for a write).
   */
  synchronized boolean inUse() {
    return writeLock != null || softWriteLock != null || !readLocks.isEmpty()
        || waiters > 0;
  }

  /**
   * @return true iff the object is write locked by a tid other than the one
   * given.
   */
  private synchronized boolean writeLockedByAnotherTid(long tid) {
    return (writeLock != null && !writeLock.equals(tid))
        || (softWriteLock != null && !softWriteLock.equals(tid));
  }

  /**
   * @return true iff the object is write locked by a worker other than the one
   * given.
   */
  private synchronized boolean writeLockedByAnotherWorker(Principal worker) {
    Oid workerOid = new Oid(worker.$getStore(), worker.$getOnum());
    return writer != null && !writer.equals(workerOid);
  }

  /**
   * @return true iff the object is read or write locked by a tid other than the
   * one given.
   */
  private synchronized boolean lockedByAnotherTid(long tid) {
    return writeLockedByAnotherTid(tid) || (!readLocks.isEmpty()
        && (readLocks.size() > 1 || !readLocks.containsKey(tid)));
  }

  /**
   * @return true iff the object is read or write locked by a worker other than
   * the one given.
   */
  private synchronized boolean lockedByAnotherWorker(Principal worker) {
    Oid workerOid = new Oid(worker.$getStore(), worker.$getOnum());
    if (writeLockedByAnotherWorker(worker)) return true;
    for (List<Oid> readLock : readLocks.values()) {
      if (readLock.size() > 1
          || (readLock.size() == 1 && !readLock.contains(workerOid)))
        return true;
    }
    return false;
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
  synchronized void lockForWrite(PendingTransaction tx)
      throws UnableToLockException {
    long tid = tx.tid;
    Principal worker = tx.owner;
    Oid workerOid = new Oid(worker.$getStore(), worker.$getOnum());
    while (!lockedByAnotherWorker(worker) && lockedByAnotherTid(tid)
        && tx.state != PendingTransaction.State.ABORTING) {
      waiters++;
      try {
        if (STORE_TRANSACTION_LOGGER.isLoggable(Level.FINEST)) {
          STORE_TRANSACTION_LOGGER.log(Level.FINEST,
              "{1} waiting for write lock of {0}",
              new Object[] { this, Long.toHexString(tid) });
        }
        // See if we can just wait for the other transaction from the same
        // worker to abort/commit.
        tx.setWaitsFor(this);
        wait();
        tx.clearWaitsFor();
      } catch (InterruptedException e) {
        Logging.logIgnoredInterruptedException(e);
      }
      waiters--;
    }

    if (tx.state == PendingTransaction.State.ABORTING) {
      // Aborted while waiting.
      throw new UnableToLockException();
    }

    if (writeLock != null && !writeLock.equals(tid)
        && !writer.equals(workerOid)) {
      // Conflicting write lock.
      throw new UnableToLockException();
    }

    // We already have the write lock, don't bother with anything.
    if (writeLock != null && writeLock.equals(tid)) return;

    if (softWriteLock != null && !softWriteLock.equals(tid)
        && !writer.equals(workerOid)) {
      // Conflicting with a softWriteLock
      throw new UnableToLockException();
    }

    int numReadLocks = readLocks.size();
    if (numReadLocks > 1 || numReadLocks == 1 && !readLocks.containsKey(tid)) {
      // Read-locked by another transaction.
      throw new UnableToLockException();
    }

    writeLock = tid;
    writer = workerOid;
    notifyAll(); // Notify others that there's a new lock holder.
    if (STORE_TRANSACTION_LOGGER.isLoggable(Level.FINEST)) {
      STORE_TRANSACTION_LOGGER.log(Level.FINEST, "{0} write locked by {1}",
          new Object[] { this, Long.toHexString(tid) });
    }
  }

  /**
   * Removes a write lock for the given TID.
   *
   * @return true iff a lock was removed.
   */
  synchronized boolean unlockForWrite(PendingTransaction tx) {
    long tid = tx.tid;
    Principal worker = tx.owner;
    Oid workerOid = new Oid(worker.$getStore(), worker.$getOnum());
    if (writeLock != null && writeLock.equals(tid) && writer != null
        && writer.equals(workerOid)) {
      writeLock = null;
      writer = null;
      notifyAll(); // Notify waiters there's one less holder.
      if (STORE_TRANSACTION_LOGGER.isLoggable(Level.FINEST)) {
        STORE_TRANSACTION_LOGGER.log(Level.FINEST, "{0} write unlocked by {1}",
            new Object[] { this, Long.toHexString(tid) });
      }
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
  synchronized void lockForSoftWrite(PendingTransaction tx)
      throws UnableToLockException {
    long tid = tx.tid;
    Principal worker = tx.owner;
    Oid workerOid = new Oid(worker.$getStore(), worker.$getOnum());
    while (!writeLockedByAnotherWorker(worker) && lockedByAnotherTid(tid)
        && tx.state != PendingTransaction.State.ABORTING) {
      waiters++;
      try {
        if (STORE_TRANSACTION_LOGGER.isLoggable(Level.FINEST)) {
          STORE_TRANSACTION_LOGGER.log(Level.FINEST,
              "{1} waiting for soft write lock of {0}",
              new Object[] { this, Long.toHexString(tid) });
        }
        // See if we can just wait for the other transaction from the same
        // worker to abort/commit.
        tx.setWaitsFor(this);
        wait();
        tx.clearWaitsFor();
      } catch (InterruptedException e) {
        Logging.logIgnoredInterruptedException(e);
      }
      waiters--;
    }

    if (tx.state == PendingTransaction.State.ABORTING) {
      // Aborted while waiting.
      throw new UnableToLockException();
    }

    if (writeLock != null && !writeLock.equals(tid)) {
      // Conflicting write lock.
      throw new UnableToLockException();
    }

    if (softWriteLock != null && !softWriteLock.equals(tid)) {
      // Conflicting with a softWriteLock
      throw new UnableToLockException();
    }

    softWriteLock = tid;
    writer = workerOid;
    notifyAll(); // Notify others that there's a new lock holder.
    if (STORE_TRANSACTION_LOGGER.isLoggable(Level.FINEST)) {
      STORE_TRANSACTION_LOGGER.log(Level.FINEST, "{0} soft write locked by {1}",
          new Object[] { this, Long.toHexString(tid) });
    }
  }

  /**
   * Removes a soft write lock for the given TID.
   *
   * @return true iff the lock was removed.
   */
  synchronized boolean unlockForSoftWrite(PendingTransaction tx) {
    long tid = tx.tid;
    Principal worker = tx.owner;
    Oid workerOid = new Oid(worker.$getStore(), worker.$getOnum());
    if (softWriteLock != null && softWriteLock.equals(tid) && writer != null
        && writer.equals(workerOid)) {
      softWriteLock = null;
      writer = null;
      notifyAll(); // Notify waiters there's one less holder.
      if (STORE_TRANSACTION_LOGGER.isLoggable(Level.FINEST)) {
        STORE_TRANSACTION_LOGGER.log(Level.FINEST,
            "{0} soft write unlocked by {1}",
            new Object[] { this, Long.toHexString(tid) });
      }
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
  synchronized void lockForRead(PendingTransaction tx)
      throws UnableToLockException {
    long tid = tx.tid;
    Principal worker = tx.owner;
    while (!writeLockedByAnotherWorker(worker) && writeLockedByAnotherTid(tid)
        && tx.state != PendingTransaction.State.ABORTING) {
      waiters++;
      try {
        if (STORE_TRANSACTION_LOGGER.isLoggable(Level.FINEST)) {
          STORE_TRANSACTION_LOGGER.log(Level.FINEST,
              "{1} waiting for read lock of {0}",
              new Object[] { this, Long.toHexString(tid) });
        }
        // See if we can just wait for the other transaction from the same
        // worker to abort/commit.
        tx.setWaitsFor(this);
        wait();
        tx.clearWaitsFor();
      } catch (InterruptedException e) {
        Logging.logIgnoredInterruptedException(e);
      }
      waiters--;
    }

    if (tx.state == PendingTransaction.State.ABORTING) {
      // Aborted while waiting.
      throw new UnableToLockException();
    }

    if (writeLock != null && !writeLock.equals(tid)) {
      // Conflicting write lock.
      throw new UnableToLockException();
    }

    // We already have the write lock, don't bother with a read lock.
    if (writeLock != null && writeLock.equals(tid)) return;

    List<Oid> pins = readLocks.get(tid);
    if (pins == null) {
      pins = new LinkedList<>();
      readLocks.put(tid, pins);
    }

    pins.add(new Oid(worker.$getStore(), worker.$getOnum()));
    notifyAll(); // Notify others that there's a new lock holder.
    if (STORE_TRANSACTION_LOGGER.isLoggable(Level.FINEST)) {
      STORE_TRANSACTION_LOGGER.log(Level.FINEST, "{0} read locked by {1}",
          new Object[] { this, Long.toHexString(tid) });
    }
  }

  /**
   * Removes a read lock for the given TID and worker.
   *
   * @return true iff the read lock was removed.
   */
  synchronized boolean unlockForRead(PendingTransaction tx) {
    long tid = tx.tid;
    Principal worker = tx.owner;
    List<Oid> pins = readLocks.get(tid);
    if (pins != null) {
      if (pins.remove(new Oid(worker.$getStore(), worker.$getOnum())))
        notifyAll(); // Notify waiters there's one less holder.
      if (pins.isEmpty()) {
        readLocks.remove(tid);
        if (STORE_TRANSACTION_LOGGER.isLoggable(Level.FINEST)) {
          STORE_TRANSACTION_LOGGER.log(Level.FINEST, "{0} read unlocked by {1}",
              new Object[] { this, Long.toHexString(tid) });
        }
        return true;
      }
    }

    return false;
  }

  @Override
  public String toString() {
    return "ObjectLocks for " + onum;
  }
}
