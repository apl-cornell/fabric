package fabric.worker.transaction;

import static fabric.common.Logging.HOTOS_LOGGER;
import static fabric.common.Logging.METRICS_LOGGER;
import static fabric.common.Logging.WORKER_DEADLOCK_LOGGER;
import static fabric.common.Logging.WORKER_TRANSACTION_LOGGER;
import static fabric.worker.transaction.Log.CommitState.Values.ABORTED;
import static fabric.worker.transaction.Log.CommitState.Values.ABORTING;
import static fabric.worker.transaction.Log.CommitState.Values.COMMITTED;
import static fabric.worker.transaction.Log.CommitState.Values.COMMITTING;
import static fabric.worker.transaction.Log.CommitState.Values.PREPARED;
import static fabric.worker.transaction.Log.CommitState.Values.PREPARE_FAILED;
import static fabric.worker.transaction.Log.CommitState.Values.PREPARING;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

import fabric.common.FabricThread;
import fabric.common.Logging;
import fabric.common.Threading;
import fabric.common.Threading.NamedRunnable;
import fabric.common.Timing;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.common.util.Triple;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Label;
import fabric.lang.security.SecurityCache;
import fabric.messages.NonAtomicCallMessage;
import fabric.metrics.SampledMetric;
import fabric.metrics.util.ReconfigLock;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;
import fabric.store.InProcessStore;
import fabric.worker.AbortException;
import fabric.worker.LocalStore;
import fabric.worker.Store;
import fabric.worker.TransactionAbortingException;
import fabric.worker.TransactionAtomicityViolationException;
import fabric.worker.TransactionRestartingException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.metrics.ExpiryExtension;
import fabric.worker.metrics.LockConflictException;
import fabric.worker.metrics.treaties.TreatySet;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.WriterMap;

/**
 * Holds transaction management information for a single thread. Each thread has
 * its own TransactionManager.
 * <p>
 * We say that a transaction has acquired a write lock on an object if any entry
 * in the object's <code>$history</code> list has <code>$writeLockHolder</code>
 * set to that transaction. @see fabric.lang.Object._Impl
 * </p>
 * <p>
 * We say that a transaction has acquired a read lock if it is in the
 * "read list" for that object. @see fabric.lang.Object._Impl.$readMapEntry
 * </p>
 * <p>
 * When a transaction acquires a read lock, we ensure that the <i>read
 * condition</i> holds: that the holder of the write lock is an ancestor of that
 * transaction. Before reading an object, we ensure that the transaction holds a
 * read lock and that the read condition still holds.
 * </p>
 * <p>
 * When a transaction acquires a write lock, we ensure that the <i>write
 * condition</i> holds: the holders of the read and write locks are all
 * ancestors of that transaction. Before writing an object, we ensure that the
 * transaction holds a write lock and that the write condition still holds.
 * </p>
 * <p>
 * Assumptions:
 * <ul>
 * <li>Once the top-level transaction within a thread aborts or commits, that
 * thread will terminate without performing further operations on the
 * transaction manager.</li>
 * <li>The fetch operation will not be invoked concurrently on the same object.</li>
 * </ul>
 * </p>
 * <p>
 * The following objects are used as condition variables:
 * <ul>
 * <li>Log.children - for signalling that all sub-transactions of a given
 * transaction have finished.</li>
 * <li>Impl objects - for signalling to readers and writers that a read or write
 * lock on that object has been released.
 * </ul>
 * </p>
 */
public final class TransactionManager {
  /**
   * The deadlock detector.
   */
  private static final DeadlockDetectorThread deadlockDetector =
      new DeadlockDetectorThread();

  /**
   * The innermost running transaction for the thread being managed.
   */
  private Log current;

  /**
   * Per-Thread stats for app-level transactions
   */
  public final TxnStats stats = new TxnStats();

  /**
   * If this thread is handling an {@link NonAtomicCallMessage}, this is the list of
   * oids for which writes have committed.
   */
  protected OidKeyHashMap<Integer> committedWrites;

  /**
   * The locks currently held by this Thread's transaction manager.
   */
  protected OidKeyHashMap<Boolean> contractLocksHeld = new OidKeyHashMap<>();

  /**
   * The locks to acquire before the next transaction attempt.
   */
  protected OidKeyHashMap<Boolean> contractsToAcquire = new OidKeyHashMap<>();

  /**
   * The locks that some other transaction acquired that we're waiting on.
   */
  protected OidKeyHashMap<Integer> waitingOn = new OidKeyHashMap<>();

  /**
   * Clear out locking state for lock objects.  Primarily intended for use after
   * a "single attempt" transaction in the system code.
   */
  public void clearLockObjectState() {
    contractsToAcquire.clear();
    waitingOn.clear();
    // Don't clear out locks held, that would cause issues.
  }

  // Mark a created lock.
  public void registerLockCreate(fabric.lang.Object lock) {
    Logging.log(METRICS_LOGGER, Level.FINER, "CREATING LOCK {0}/{1} IN {2}/{3}",
        lock.$getStore(), new Long(lock.$getOnum()), Thread.currentThread(),
        getCurrentTid());
    // You start out holding the lock if you created it.
    current.locksCreated.put(lock, true);
  }

  // Mark an acquired lock.
  public void registerLockAcquire(fabric.lang.Object lock) {
    Logging.log(METRICS_LOGGER, Level.FINER,
        "ACQUIRING LOCK {0}/{1} IN {2}/{3}", lock.$getStore(),
        new Long(lock.$getOnum()), Thread.currentThread(), getCurrentTid());
    current.acquires.put(lock, true);
    if (!acquiringLocks && !current.locksCreated.containsKey(lock)) {
      contractsToAcquire.put(lock, true);
    }
  }

  // Check that we have this lock.
  public boolean hasLock(fabric.lang.Object lock) {
    // TODO: consider allowing a pending acquire to be considered held?
    Logging.log(METRICS_LOGGER, Level.FINER, "CHECKING LOCK {0}/{1} IN {2}/{3}",
        lock.$getStore(), new Long(lock.$getOnum()), Thread.currentThread(),
        getCurrentTid());
    if (contractLocksHeld.containsKey(lock)) {
      // If you're checking for it and you have it, you're 'using' it.
      if (!current.locksCreated.containsKey(lock)) {
        // If you didn't create it, mark it as something to reacquire on retry.
        contractsToAcquire.put(lock, true);
      }
      return true;
    }
    if ((current != null && current.locksCreated.containsKey(lock))
        || current.acquires.containsKey(lock)) {
      return true;
    }
    return false;
  }

  // Unmark an acquired lock.
  public void registerLockRelease(fabric.lang.Object lock) {
    Logging.log(METRICS_LOGGER, Level.FINER,
        "RELEASING LOCK {0}/{1} IN {2}/{3}", lock.$getStore(),
        new Long(lock.$getOnum()), Thread.currentThread(), getCurrentTid());
    current.pendingReleases.put(lock, true);
  }

  // Note a lock conflict.  This means we should give up on locks we were
  // looking at, since we'll be taking a backseat until that conflict goes away
  // anyways.
  public void registerLockConflict(fabric.lang.Object lock) {
    // Don't get locks on retry
    contractsToAcquire.clear();
    // Mark the lock as being waited on.
    waitingOn.put(lock, ((_Impl) lock.fetch()).$version);
    TransactionID curTID = current.tid;
    while (curTID.parent != null)
      curTID = curTID.parent;
    Logging.METRICS_LOGGER
        .fine("ABORTING READ IN " + current + " FOR LOCK " + lock);
    throw new LockConflictException(new TransactionID(curTID.topTid));
  }

  protected void releaseHeldLocks() {
    if (!contractLocksHeld.isEmpty()) {
      List<Future<?>> releaseFutures = new ArrayList<>();
      for (final Store s : contractLocksHeld.storeSet()) {
        final LongSet onums = contractLocksHeld.get(s).keySet();
        releaseFutures
            .add(Threading.getPool().submit(new Threading.NamedRunnable(
                "Store " + s.name() + " lock object release") {
              @Override
              public void runImpl() {
                boolean oldState =
                    TransactionManager.getInstance().acquiringLocks;
                TransactionManager.getInstance().acquiringLocks = true;
                // Release everything
                Worker.runInTopLevelTransaction((new Code<Void>() {
                  @Override
                  public Void run() {
                    Logging.log(METRICS_LOGGER, Level.FINER,
                        "RELEASING LOCKS FROM {2} IN {0}/{1}",
                        Thread.currentThread(), getCurrentTid(), s.name());
                    for (LongIterator it = onums.iterator(); it.hasNext();) {
                      long onum = it.next();
                      ReconfigLock._Proxy l = new ReconfigLock._Proxy(s, onum);
                      l.release();
                    }
                    return null;
                  }
                }), true);
                TransactionManager.getInstance().acquiringLocks = oldState;
              }
            }));
      }
      for (Future<?> f : releaseFutures) {
        try {
          f.get();
        } catch (ExecutionException e) {
          StringWriter sw = new StringWriter();
          e.printStackTrace(new PrintWriter(sw));
          Logging.log(METRICS_LOGGER, Level.SEVERE,
              "EXECUTION EXCEPTION DURING LOCK RELEASE ATTEMPT\n{0}\n{1}", e,
              sw);
        } catch (InterruptedException e) {
          StringWriter sw = new StringWriter();
          e.printStackTrace(new PrintWriter(sw));
          Logging.log(METRICS_LOGGER, Level.SEVERE,
              "INTERRUPTION DURING LOCK RELEASE ATTEMPT\n{0}\n{1}", e, sw);
        }
      }
      if (METRICS_LOGGER.isLoggable(Level.FINER)) {
        for (Store s : contractLocksHeld.storeSet()) {
          for (LongIterator it =
              contractLocksHeld.get(s).keySet().iterator(); it.hasNext();) {
            long onum = it.next();
            Logging.log(METRICS_LOGGER, Level.FINER,
                "RELEASED LOCK {0}/{1} IN {2}", s, onum,
                Thread.currentThread());
          }
        }
      }
      contractLocksHeld.clear();
    }
  }

  protected void waitForOutstandingLocks() {
    // If we're waiting on some locks someone else holds, let's wait until those
    // are free
    if (!waitingOn.isEmpty()) {
      List<Future<?>> waitFutures = new ArrayList<>();
      for (final Store s : waitingOn.storeSet()) {
        waitFutures
            .add(Threading.getPool().submit(new Threading.NamedCallable<Void>(
                "Store " + s.name() + " lock object wait") {
              @Override
              public Void callImpl() {
                try {
                  s.waitForUpdate(waitingOn.get(s));
                } catch (AccessException e) {
                  Logging.log(METRICS_LOGGER, Level.SEVERE,
                      "WAITING FOR LOCK FAILED: {0}", e);
                  throw new InternalError(e);
                }
                return null;
              }
            }));
      }
      for (Future<?> f : waitFutures) {
        try {
          f.get();
        } catch (ExecutionException e) {
          StringWriter sw = new StringWriter();
          e.printStackTrace(new PrintWriter(sw));
          Logging.log(METRICS_LOGGER, Level.SEVERE,
              "EXECUTION EXCEPTION DURING WAIT FOR LOCKS\n{0}\n{1}", e, sw);
          throw new InternalError(e);
        } catch (InterruptedException e) {
          StringWriter sw = new StringWriter();
          e.printStackTrace(new PrintWriter(sw));
          Logging.log(METRICS_LOGGER, Level.SEVERE,
              "INTERRUPTION DURING LOCK ACQUIRE ATTEMPT\n{0}\n{1}", e, sw);
          throw new InternalError(e);
        }
      }
    }
    waitingOn.clear();
  }

  protected void acquireContractLocks() {
    // Nothing needed if we have all the locks we need and aren't waiting on
    // other locks.
    if (!contractsToAcquire.equals(contractLocksHeld) || !waitingOn.isEmpty()) {
      // Grab locks still needed.
      final OidKeyHashMap<Boolean> contractsToAcquireF =
          new OidKeyHashMap<>(contractsToAcquire);

      // First release any held locks.
      releaseHeldLocks();

      // Then wait for any locks we bounced on.
      waitForOutstandingLocks();

      // Only bother with lock dance if there's a change in locks we want.
      if (!contractsToAcquireF.equals(contractLocksHeld)) {
        boolean success = false;
        int attempts = 0;
        int successes = 0;
        while (!success) {
          // Let's just give up and move on after an attempt didn't manage to get
          // any locks.  Usually means someone else is coordinating and we should
          // just wait.
          if (attempts > 0 && successes == 0) {
            break;
          }

          // Clear out the waiting set, we'll update it below.
          waitingOn.clear();

          // Do an exponential backoff between attempts.
          if (attempts > 4) {
            try {
              int sleepTime = Math.min(1 << Math.min(attempts, 8), 250);
              sleepTime += ThreadLocalRandom.current().nextInt(0, sleepTime);
              Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
              StringWriter sw = new StringWriter();
              e.printStackTrace(new PrintWriter(sw));
              Logging.log(METRICS_LOGGER, Level.SEVERE,
                  "INTERRUPTION DURING WAIT BETWEEN LOCK ATTEMPTS\n{0}\n{1}", e,
                  sw);
              throw new InternalError(e);
            }
          }

          stats.markLockAttempt();

          Logging.log(METRICS_LOGGER, Level.FINER, "LOCK ATTEMPT {0} IN {1}",
              attempts, Thread.currentThread());

          // Attempt to acquire locks.
          success = true;
          successes = 0;
          if (!contractsToAcquireF.isEmpty()) {
            List<Future<Triple<Store, Boolean, OidKeyHashMap<Integer>>>> acquireFutures =
                new ArrayList<>();
            for (final Store s : contractsToAcquireF.storeSet()) {
              final LongSet onums = contractsToAcquireF.get(s).keySet();
              acquireFutures.add(Threading.getPool().submit(
                  new Threading.NamedCallable<Triple<Store, Boolean, OidKeyHashMap<Integer>>>(
                      "Store " + s.name() + " lock object acquire") {
                    @Override
                    public Triple<Store, Boolean, OidKeyHashMap<Integer>> callImpl() {
                      boolean result = true;
                      boolean oldState =
                          TransactionManager.getInstance().acquiringLocks;
                      TransactionManager.getInstance().acquiringLocks = true;
                      try {
                        // acquire everything
                        Worker.runInTopLevelTransaction((new Code<Void>() {
                          @Override
                          public Void run() {
                            Logging.log(METRICS_LOGGER, Level.FINER,
                                "ACQUIRING LOCKS FROM {2} IN {0}/{1}",
                                Thread.currentThread(), getCurrentTid(),
                                s.name());
                            for (LongIterator it = onums.iterator(); it
                                .hasNext();) {
                              long onum = it.next();
                              ReconfigLock._Proxy l =
                                  new ReconfigLock._Proxy(s, onum);
                              l.acquire();
                            }
                            return null;
                          }
                        }), false);
                      } catch (AbortException e) {
                        result = false;
                      } catch (LockConflictException e) {
                        result = false;
                      }
                      TransactionManager.getInstance().acquiringLocks =
                          oldState;
                      OidKeyHashMap<Integer> waitingOnCopy =
                          new OidKeyHashMap<>(waitingOn);
                      waitingOn.clear();
                      return new Triple<>(s, result, waitingOnCopy);
                    }
                  }));
            }
            for (Future<Triple<Store, Boolean, OidKeyHashMap<Integer>>> f : acquireFutures) {
              try {
                Triple<Store, Boolean, OidKeyHashMap<Integer>> p = f.get();
                success = success && p.second;
                if (p.second) {
                  successes++;
                  for (LongIterator it =
                      contractsToAcquireF.get(p.first).keySet().iterator(); it
                          .hasNext();) {
                    long onum = it.next();
                    contractLocksHeld.put(p.first, onum, true);
                    Logging.log(METRICS_LOGGER, Level.FINER,
                        "ACQUIRED LOCK {0}/{1} IN {2}", p.first, onum,
                        Thread.currentThread());
                  }
                }
                waitingOn.putAll(p.third);
              } catch (ExecutionException e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                Logging.log(METRICS_LOGGER, Level.SEVERE,
                    "EXECUTION EXCEPTION DURING LOCK ACQUIRE ATTEMPT\n{0}\n{1}",
                    e, sw);
                throw new InternalError(e);
              } catch (InterruptedException e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                Logging.log(METRICS_LOGGER, Level.SEVERE,
                    "INTERRUPTION DURING LOCK ACQUIRE ATTEMPT\n{0}\n{1}", e,
                    sw);
                throw new InternalError(e);
              }
            }
          }
          if (!success) {
            // Release locks before attempting again.
            releaseHeldLocks();
            attempts++;
          }
        }
      }
      // Again, check if there are more locks to wait for after the "lock dance"
      waitForOutstandingLocks();
      contractsToAcquire.clear();
    }
  }

  protected void releaseContractLocks() {
    OidKeyHashMap<Boolean> locksCopy = new OidKeyHashMap<>(contractLocksHeld);
    locksCopy.putAll(current.locksCreated);
    if (!acquiringLocks) {
      locksCopy.putAll(current.acquires);
      current.acquires.clear();
    }
    for (Store s : locksCopy.storeSet()) {
      for (LongIterator it = locksCopy.get(s).keySet().iterator(); it
          .hasNext();) {
        long onum = it.next();
        ReconfigLock._Proxy l = new ReconfigLock._Proxy(s, onum);
        l.release();
      }
    }
  }

  /**
   * Update lock state for this thread's transaction manager after a successful
   * commit.
   */
  protected void updateLockState(OidKeyHashMap<Boolean> acquires,
      OidKeyHashMap<Boolean> releases) {
    // Only do this if we're not doing "between attempt" lock wrangling.
    if (!acquiringLocks) {
      // XXX: Note that if there's overlap, the lock is considered released.  This
      // should not happen in normal operation but if things change this is worth
      // remembering.
      // TODO: putAll does the same thing but removes the opportunity to easily
      // log the event.
      for (Store s : acquires.storeSet()) {
        for (LongIterator it = acquires.get(s).keySet().iterator(); it
            .hasNext();) {
          long onum = it.next();
          contractLocksHeld.put(s, onum, true);
          Logging.log(METRICS_LOGGER, Level.FINER,
              "ACQUIRED LOCK {0}/{1} IN {2}", s, onum, Thread.currentThread());
        }
      }
      for (Store s : releases.storeSet()) {
        for (LongIterator it = releases.get(s).keySet().iterator(); it
            .hasNext();) {
          stats.markLocksUsed();
          long onum = it.next();
          contractLocksHeld.remove(s, onum);
          Logging.log(METRICS_LOGGER, Level.FINER,
              "RELEASED LOCK {0}/{1} IN {2}", s, onum, Thread.currentThread());
        }
      }
    }
  }

  /**
   * A debugging switch for storing a stack trace each time a write lock is
   * obtained. Enable this by passing "--trace-locks" as a command-line argument
   * to the node.
   */
  public static boolean TRACE_WRITE_LOCKS = false;

  /**
   * A map from tids to objects representing currently pending prepare phases.
   */
  public static final ConcurrentLongKeyMap<TransactionPrepare> pendingPrepares =
      new ConcurrentLongKeyHashMap<>();

  /**
   * A map from tids to a boolean, if an entry exists, it means that the tid has
   * not been committed at stores.  Worker should not successfully complete a
   * "normal" shutdown while this is nonempty.
   */
  public static final ConcurrentLongKeyMap<TransactionPrepare> outstandingCommits =
      new ConcurrentLongKeyHashMap<>();

  /**
   * A map from OIDs to a version number and a list of logs for transactions
   * that have read that version of the object. For each transaction tx, an
   * object o is in tx.reads exactly when tx is in readList[o]. A transaction
   * has acquired a read lock if its log is in this list. All entries in this
   * list have non-empty <code>readLocks</code> sets.
   */
  // Proxy objects aren't used here because doing so would result in calls to
  // hashcode() and equals() on such objects, resulting in fetching the
  // corresponding Impls from the store.
  static final ReadMap readMap = new ReadMap();

  public static boolean haveReaders(Store store, long onum) {
    return readMap.haveReaders(store, onum);
  }

  public static void abortReaders(Store store, long onum) {
    readMap.abortReaders(store, onum);
  }

  public static ReadMap.Entry getReadMapEntry(_Impl impl, long expiry) {
    return readMap.getEntry(impl);
  }

  private static final Map<Thread, TransactionManager> instanceMap =
      new WeakHashMap<>();

  public static TransactionManager getInstance() {
    Thread thread = Thread.currentThread();

    if (thread instanceof FabricThread) {
      FabricThread ft = (FabricThread) thread;
      TransactionManager result = ft.getTransactionManager();
      if (result == null) {
        result = new TransactionManager();
        ft.setTransactionManager(result);
      }
      return result;
    }

    synchronized (instanceMap) {
      TransactionManager result = instanceMap.get(thread);
      if (result == null) {
        result = new TransactionManager();
        instanceMap.put(thread, result);
      }

      return result;
    }
  }

  private TransactionManager() {
    this.current = null;
  }

  private void checkRetrySignal() {
    current.checkRetrySignal();
  }

  /**
   * Aborts the transaction, recursing to any workers that were called, and any
   * stores that were contacted.
   */
  public void abortTransaction() {
    if (current.tid.depth == 0) {
      // Aborting a top-level transaction. Make sure no other thread is working
      // on this transaction.
      synchronized (current.commitState) {
        if (current.commitState.value != PREPARING) {
          // Transaction failed due to reasons that are definitely not a
          // collision on a remote value, don't start using pessimistic locks.
          contractsToAcquire.clear();
        }
        while (current.commitState.value == PREPARING) {
          try {
            current.commitState.wait();
          } catch (InterruptedException e) {
            Logging.logIgnoredInterruptedException(e);
          }
        }

        switch (current.commitState.value) {
        case UNPREPARED:
          current.commitState.value = ABORTING;
          break;

        case PREPARE_FAILED:
        case PREPARED:
          current.commitState.value = ABORTING;
          break;

        case PREPARING:
          // We should've taken care of this case already in the 'while' loop
          // above.
          throw new InternalError();

        case COMMITTING:
        case COMMITTED:
          // Too late to abort! We shouldn't really enter this situation.
          WORKER_TRANSACTION_LOGGER
              .warning("Ignoring attempt to abort a committed transaction.");
          return;

        case ABORTING:
        case ABORTED:
          return;

        default:
          // All cases should have been specified above.
          throw new InternalError();
        }
      }
    }

    boolean readOnly = current.isReadOnly();

    WORKER_TRANSACTION_LOGGER.log(Level.INFO, "{0} aborting", current);
    // Assume only one thread will be executing this.
    HOTOS_LOGGER.log(Level.FINEST, "aborting {0}", current);

    // Set the retry flag in all our children, if that hasn't happened already.
    current.flagRetry();

    // Wait for all other threads to finish.
    current.waitForThreads();

    if (current.prepare != null) current.prepare.abort();
    current.abort();
    WORKER_TRANSACTION_LOGGER.log(Level.INFO, "{0} aborted", current);
    HOTOS_LOGGER.log(Level.INFO, "aborted {0} " + (readOnly ? "R" : "W"),
        current);

    if (current.tid.depth == 0) {
      // Aborted a top-level transaction. Remove from the transaction registry.
      TransactionRegistry.remove(current.tid.topTid);
    }

    synchronized (current.commitState) {
      // The commit state reflects the state of the top-level transaction, so
      // only set the flag if a top-level transaction is being aborted.
      if (current.tid.depth == 0) {
        current.commitState.value = ABORTED;
        current.commitState.notifyAll();
      }

      if (current.tid.parent == null || current.parent != null
          && current.parent.tid.equals(current.tid.parent)) {
        // The parent frame represents the parent transaction. Pop the stack.
        current = current.parent;
      } else {
        // Reuse the current frame for the parent transaction.
        current.tid = current.tid.parent;
      }
    }
  }

  /**
   * Commits the transaction if possible; otherwise, aborts the transaction.
   *
   * @throws AbortException
   *           if the transaction was aborted.
   * @throws TransactionRestartingException
   *           if the transaction was aborted and needs to be retried.
   */
  public void commitTransaction() throws AbortException,
      TransactionRestartingException, TransactionAtomicityViolationException {
    Timing.COMMIT.begin();
    try {
      commitTransaction(false);
    } finally {
      Timing.COMMIT.end();
    }
  }

  /**
   * @throws TransactionRestartingException
   *           if the prepare fails.
   */
  public void commitTransaction(boolean ignoreRetrySignal) {
    WORKER_TRANSACTION_LOGGER.log(Level.FINEST, "{0} attempting to commit",
        current);

    // Assume only one thread will be executing this.

    // XXX This is a long and ugly method. Refactor?

    // Resolve unobserved samples for top level txn before waiting for threads
    // and checking retry signal for the last time.
    // TODO: This should probably be run somewhere else prior to this call,
    // since it's technically not part of commit.
    if (current.tid.parent == null) {
      METRICS_LOGGER.log(Level.FINEST,
          "RESOLVING OBSERVATIONS AT THE END OF {0}", current);
      try {
        resolveObservations();
        if (!acquiringLocks) {
          // If we're not trying to grab up locks, we are trying to commit after
          // using those locks.  Release them in the same transaction.
          METRICS_LOGGER.log(Level.FINEST, "RELEASING LOCKS AT THE END OF {0}",
              current);
          if (METRICS_LOGGER.isLoggable(Level.FINEST)) {
            for (Store s : contractLocksHeld.storeSet()) {
              for (LongIterator it =
                  contractLocksHeld.get(s).keySet().iterator(); it.hasNext();) {
                METRICS_LOGGER.log(Level.FINEST,
                    "\t" + s.name() + "://" + it.next());
              }
            }
          }
          releaseContractLocks();
        }
      } catch (LockConflictException e) {
        TransactionID tid = current.tid;
        abortTransaction();
        throw new TransactionRestartingException(tid);
      } catch (TransactionAbortingException e) {
        abortTransaction();
        throw new AbortException();
      } catch (TransactionRestartingException e) {
        abortTransaction();
        throw e;
      } catch (Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        if (checkForStaleObjects()) {
          // Ugh. Need to restart.
          METRICS_LOGGER.log(Level.FINEST, "RESOLVING OBSERVATIONS " + current
              + " RESTARTING WITH " + e + "\n" + sw);
          abortTransaction();
          throw new AbortException(e);
        }
        METRICS_LOGGER.log(Level.FINEST, "RESOLVING OBSERVATIONS " + current
            + " DIED WITH " + e + "\n" + sw);
        throw e;
      }
      METRICS_LOGGER.log(Level.FINEST,
          "RESOLVED OBSERVATIONS AT THE END OF {0}", current);
    }

    // Wait for all sub-transactions to finish.
    current.waitForThreads();

    TransactionID ignoredRetrySignal = null;
    if (!ignoreRetrySignal) {
      // Make sure we're not supposed to abort or retry.
      try {
        checkRetrySignal();
      } catch (TransactionAbortingException e) {
        abortTransaction();
        throw new AbortException();
      } catch (TransactionRestartingException e) {
        abortTransaction();
        throw e;
      }
    } else {
      synchronized (current) {
        ignoredRetrySignal = current.retrySignal;
      }
    }

    WORKER_TRANSACTION_LOGGER.log(Level.FINEST, "{0} committing", current);

    Log parent = current.parent;
    if (current.tid.parent != null) {
      try {
        Timing.SUBTX.begin();
        // Update data structures to reflect the commit.
        current.commitNested();
        WORKER_TRANSACTION_LOGGER.log(Level.FINEST, "{0} committed", current);

        if (parent != null && parent.tid.equals(current.tid.parent)) {
          // Parent frame represents parent transaction. Pop the stack.
          current = parent;
        } else {
          // Reuse the current frame for the parent transaction. Update its TID.
          current.tid = current.tid.parent;
        }

        if (ignoredRetrySignal != null) {
          // Preserve the ignored retry signal.
          synchronized (current) {
            TransactionID signal = ignoredRetrySignal;
            if (current.retrySignal != null) {
              signal = signal.getLowestCommonAncestor(current.retrySignal);

              if (signal == null) {
                throw new InternalError("Something is broken with transaction "
                    + "management. Found retry signals for different "
                    + "transactions in the same log. (In transaction "
                    + current.tid + ".  Retry1=" + current.retrySignal
                    + "; Retry2=" + ignoredRetrySignal);
              }
            }

            current.retrySignal = signal;
          }
        }
        return;
      } finally {
        Timing.SUBTX.end();
      }
    }

    // Commit top-level transaction.
    Log HOTOS_current = current;
    final long prepareStart = System.currentTimeMillis();

    // Go through the transaction log and figure out the stores we need to
    // contact.
    Set<Store> stores = current.storesToContact();
    List<RemoteWorker> workers = current.workersCalled;

    // Determine whether to use the single-store optimization. The optimization
    // is only used for non-distributed transactions that use objects from a
    // single remote store.
    int numRemoteStores = 0;
    for (Store store : stores) {
      if (!store.isLocalStore()) numRemoteStores++;
    }
    boolean singleStore = workers.isEmpty() && numRemoteStores == 1;
    boolean readOnly = current.isReadOnly();

    // Set number of round trips to 0
    ROUND_TRIPS.set(0);

    // First check if the commit's already doomed.  If so, abort without talking
    // to stores.
    if (current.expiry() < System.currentTimeMillis()) {
      TransactionID tid = current.tid;
      abortTransaction();
      throw new TransactionRestartingException(tid);
    }

    // Send prepare messages to our cohorts. This will also abort our portion of
    // the transaction if the prepare fails.
    sendPrepareMessages(null, singleStore, readOnly, stores, workers);

    // Send commit messages to our cohorts.
    commitAndCleanUp();

    // Collect the names of nodes contacted.
    String[] contactedNodes = new String[stores.size() + workers.size()];
    int i = 0;
    for (Store s : stores) {
      contactedNodes[i++] = s.name();
    }
    for (RemoteWorker w : workers) {
      contactedNodes[i++] = w.name();
    }
    CONTACTED_NODES.set(contactedNodes);

    final long commitTime = System.currentTimeMillis();
    COMMIT_TIME.set(commitTime);
    if (!acquiringLocks) {
      // Coordinated if we had to commit at more than 1 store.
      if (LOCAL_STORE == null) LOCAL_STORE = Worker.getWorker().getLocalStore();
      if ((stores.size() - (stores.contains(LOCAL_STORE) ? 1 : 0)) > 1) {
        stats.markCoordination();
      }
      // Record the Tid
      stats.recordTid(HOTOS_current.tid.tid);
    }
    if (HOTOS_LOGGER.isLoggable(Level.FINE)) {
      final long commitLatency = commitTime - prepareStart;
      if (LOCAL_STORE == null) LOCAL_STORE = Worker.getWorker().getLocalStore();
      if (workers.size() > 0 || stores.size() != 1
          || !stores.contains(LOCAL_STORE)
              && !(stores.iterator().next() instanceof InProcessStore)) {
        Logging.log(HOTOS_LOGGER, Level.FINE,
            "committed tid {0} (latency {1} ms, {2} stores, {3} extensions, {4} delayed extensions, locking={5})",
            HOTOS_current, commitLatency,
            stores.size() - (stores.contains(LOCAL_STORE) ? 1 : 0),
            HOTOS_current.extendedTreaties.size(),
            HOTOS_current.delayedExtensions.size(), acquiringLocks);
      }
    }
  }

  /**
   * XXX Really gross HACK to make actual transaction commit times visible to
   * the application. This allows us to measure end-to-end application-level
   * transaction latency.
   */
  public static final ThreadLocal<Long> COMMIT_TIME = new ThreadLocal<>();

  /**
   * XXX Similarly gross HACK for making transaction commit round trips visible
   * to the application.
   */
  // TODO: This isn't modified anymore and should be replaced with transaction
  // stats object.
  public static final ThreadLocal<Integer> ROUND_TRIPS = new ThreadLocal<>();

  /**
   * XXX Similarly gross HACK for making the nodes contacted by this client
   * during commit visible to the application.
   */
  public static final ThreadLocal<String[]> CONTACTED_NODES =
      new ThreadLocal<>();

  private static LocalStore LOCAL_STORE;

  /**
   * Sends prepare messages to the cohorts in a distributed transaction. Also
   * sends abort messages if any cohort fails to prepare.
   *
   * @return A map from stores to maps from onums to contracts that have longer
   * expiries at the store, to update in the cache after this transaction
   * commits.
   *
   *
   * @throws TransactionRestartingException
   *           if the prepare fails.
   */
  public void sendPrepareMessages(RemoteWorker coordinator)
      throws TransactionRestartingException {
    sendPrepareMessages(coordinator, false, false, current.storesToContact(),
        current.workersCalled);
  }

  /**
   * Sends prepare messages to the given set of stores and workers. If the
   * prepare fails, the local portion and given branch of the transaction is
   * rolled back.
   *
   * @return A map from stores to maps from onums to contracts that have longer
   * expiries at the store, to update in the cache after this transaction
   * commits.
   *
   * @throws TransactionRestartingException
   *           if the prepare fails.
   */
  private void sendPrepareMessages(final RemoteWorker coordinator,
      final boolean singleStore, final boolean readOnly, Set<Store> stores,
      List<RemoteWorker> workers) throws TransactionRestartingException {
    synchronized (current.commitState) {
      switch (current.commitState.value) {
      case UNPREPARED:
        current.commitState.value = PREPARING;
        break;

      case PREPARING:
      case PREPARED:
        return;

      case COMMITTING:
      case COMMITTED:
        WORKER_TRANSACTION_LOGGER.log(Level.FINE,
            "Ignoring prepare request (transaction state = {0})",
            current.commitState.value);
        return;

      case PREPARE_FAILED:
        throw new InternalError();

      case ABORTING:
      case ABORTED:
        throw new TransactionRestartingException(current.tid);
      }
    }

    current.prepare = new TransactionPrepare(coordinator, current, singleStore,
        readOnly, stores, workers);

    try {
      current.prepare.prepare();
    } catch (TransactionRestartingException e) {
      synchronized (current.commitState) {
        current.commitState.value = PREPARE_FAILED;
        current.commitState.notifyAll();
      }

      abortTransaction();

      throw e;
    }

    synchronized (current.commitState) {
      current.commitState.value = PREPARED;
      current.commitState.notifyAll();
    }
  }

  /**
   * Sends commit messages to the cohorts in a distributed transaction.
   */
  public void commitAndCleanUp() {
    synchronized (current.commitState) {
      switch (current.commitState.value) {
      case UNPREPARED:
      case PREPARING:
        // This shouldn't happen.
        WORKER_TRANSACTION_LOGGER.log(Level.FINE,
            "Ignoring commit request (transaction state = {0}",
            current.commitState.value);
        return;
      case PREPARED:
        current.commitState.value = COMMITTING;
        break;
      case COMMITTING:
      case COMMITTED:
        return;
      case PREPARE_FAILED:
      case ABORTING:
      case ABORTED:
        throw new TransactionAtomicityViolationException();
      }
    }

    current.prepare.commit();

    // Update data structures to reflect successful commit.
    WORKER_TRANSACTION_LOGGER.log(Level.FINEST,
        "{0} committed at stores...updating data structures", current);
    current.commitTopLevel();
    contractsToAcquire.clear();
    WORKER_TRANSACTION_LOGGER.log(Level.FINEST, "{0} committed", current);

    synchronized (current.commitState) {
      current.commitState.value = COMMITTED;
    }

    TransactionRegistry.remove(current.tid.topTid);

    current = null;
  }

  public void registerCreate(_Impl obj) {
    Timing.TXLOG.begin();
    try {
      if (current == null)
        throw new InternalError("Cannot create objects outside a transaction");

      // Make sure we're not supposed to abort/retry.
      checkRetrySignal();

      // Grab a write lock on the object.
      obj.$writer = current;
      obj.$writeLockHolder = current;
      if (TRACE_WRITE_LOCKS)
        obj.$writeLockStackTrace = Thread.currentThread().getStackTrace();

      if (WORKER_DEADLOCK_LOGGER.isLoggable(Level.FINEST)) {
        Logging.log(WORKER_DEADLOCK_LOGGER, Level.FINEST,
            "{0} in {5} got write lock (via create) on {1}/{2} ({3}) ({4})",
            current, obj.$getStore(), obj.$getOnum(), obj.getClass(),
            System.identityHashCode(obj), Thread.currentThread());
      }

      // Own the object. The call to ensureOwnership is responsible for adding
      // the object to the set of created objects.
      ensureOwnership(obj);

      // The object has been written, so it's not being extended.
      synchronized (current.extendedTreaties) {
        current.extendedTreaties.remove(obj);
      }
      current.cancelDelayedExtension(obj);
    } finally {
      Timing.TXLOG.end();
    }
  }

  public void registerLabelsInitialized(_Impl obj) {
    current.writerMap.put(obj.$getProxy(), Worker.getWorker().getLocalWorker());
    current.writerMap.put(obj.$getProxy(), obj.get$$updateLabel());
  }

  public void registerExpiryUse(long exp) {
    if (current != null) current.updateExpiry(exp);
  }

  public void registerRead(_Impl obj) {
    synchronized (obj) {
      if (obj.$reader == current
          && obj.writerMapVersion == current.writerMap.version)
        return;

      // Nothing to do if we're not in a transaction.
      if (current == null) return;

      Timing.TXLOG.begin();
      try {
        ensureReadLock(obj);
        ensureObjectUpToDate(obj);
      } finally {
        Timing.TXLOG.end();
      }
    }
  }

  /**
   * Ensures the current transaction has a read lock for the given object,
   * blocking if necessary. This method assumes we are synchronized on the
   * object.
   */
  private void ensureReadLock(_Impl obj) {
    if (obj.$reader == current) return;

    // Make sure we're not supposed to abort/retry.
    checkRetrySignal();

    // Check read condition: wait until all writers are in our ancestry.
    boolean hadToWait = false;
    try {
      while (obj.$writeLockHolder != null
          && !current.isDescendantOf(obj.$writeLockHolder)) {
        if (WORKER_DEADLOCK_LOGGER.isLoggable(Level.FINEST)) {
          Logging.log(WORKER_DEADLOCK_LOGGER, Level.FINER,
              "{0} in {6} wants to read {1}/{2} ({3}) ({5}); waiting on writer {4}",
              current, obj.$getStore(), obj.$getOnum(), obj.getClass(),
              obj.$writeLockHolder, System.identityHashCode(obj),
              Thread.currentThread());
        }
        hadToWait = true;
        obj.$numWaiting++;
        try {
          current.setWaitsFor(obj.$writeLockHolder, obj);

          // Ask for deadlock detection while we wait.
          deadlockDetector.requestDetect(current);
          obj.wait();
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        }
        obj.$numWaiting--;

        // Make sure we weren't aborted/retried while we were waiting.
        checkRetrySignal();
      }
    } finally {
      current.clearWaitsFor();
    }

    // Set the object's reader stamp to the current transaction.
    obj.$reader = current;

    // Reset the object's update-map version stamp.
    obj.writerMapVersion = -1;

    current.acquireReadLock(obj);
    if (WORKER_DEADLOCK_LOGGER.isLoggable(Level.FINEST)) {
      Logging.log(WORKER_DEADLOCK_LOGGER, Level.FINEST,
          "{0} in {5} got read lock on {1}/{2} ({3}) ({4})", current,
          obj.$getStore(), obj.$getOnum(), obj.getClass(),
          System.identityHashCode(obj), Thread.currentThread());
    }
    if (hadToWait)
      WORKER_TRANSACTION_LOGGER.log(Level.FINEST, "{0} got read lock", current);
  }

  /**
   * This should be called <i>before</i> the object is modified.
   *
   * @return whether a new (top-level) transaction was created.
   */
  public boolean registerWrite(_Impl obj) {
    boolean needTransaction = (current == null);
    if (needTransaction) startTransaction();

    ExpiryExtension clobberedExtension = null;
    synchronized (obj) {
      // The object is being written, so it's not being extended.
      synchronized (current.extendedTreaties) {
        clobberedExtension = current.extendedTreaties.remove(obj);
      }
      current.cancelDelayedExtension(obj);
      if (obj.$writer == current
          && obj.writerMapVersion == current.writerMap.version
          && obj.$isOwned) {
        if (clobberedExtension != null)
          obj.$treaties = clobberedExtension.treaties;
        return needTransaction;
      }

      try {
        Timing.TXLOG.begin();
        ensureWriteLock(obj);
        ensureObjectUpToDate(obj);
        ensureOwnership(obj);
      } finally {
        Timing.TXLOG.end();
      }
    }
    if (clobberedExtension != null) obj.$treaties = clobberedExtension.treaties;

    return needTransaction;

  }

  /**
   * This should be called <i>before</i> the object's expiry is modified.
   *
   * @return whether a new (top-level) transaction was created.
   */
  @Deprecated
  public boolean registerExpiryWrite(_Impl obj, long oldExpiry,
      long newExpiry) {
    // TODO: this is now deprecated.
    return false;
  }

  /**
   * This should be called <i>before</i> the object's TreatySet is modified.
   *
   * @return whether a new (top-level) transaction was created.
   */
  public boolean registerTreatySetWrite(_Impl obj, TreatySet oldTreaties,
      TreatySet newTreaties) {
    boolean needTransaction = (current == null);
    if (needTransaction) startTransaction();

    ExpiryExtension clobberedExtension = null;
    synchronized (obj) {
      boolean extending = TreatySet.checkExtension(oldTreaties, newTreaties);
      if (!extending) {
        synchronized (current.extendedTreaties) {
          clobberedExtension = current.extendedTreaties.remove(obj);
        }
        current.cancelDelayedExtension(obj);
      }

      if (obj.$writer == current
          && obj.writerMapVersion == current.writerMap.version
          && obj.$isOwned) {
        if (clobberedExtension != null)
          obj.$treaties = clobberedExtension.treaties;
        return needTransaction;
      }

      boolean alreadyWritten = false;
      synchronized (current.writes) {
        alreadyWritten = current.writes.containsKey(obj);
      }
      // If this wasn't written and the treaties are extended, it's an
      // extension.
      if (!alreadyWritten && extending) {
        synchronized (current.extendedTreaties) {
          current.extendedTreaties.put(obj, new ExpiryExtension(obj));
        }
      }

      try {
        Timing.TXLOG.begin();
        if (extending)
          ensureReadLock(obj);
        else ensureWriteLock(obj);
        ensureObjectUpToDate(obj);
        if (!extending) ensureOwnership(obj);
      } finally {
        Timing.TXLOG.end();
      }
    }
    if (clobberedExtension != null) obj.$treaties = clobberedExtension.treaties;

    return needTransaction;

  }

  /**
   * Ensures the current transaction has a write lock for the given object,
   * blocking if necessary. This method assumes we are synchronized on the
   * object.
   */
  private void ensureWriteLock(_Impl obj) {
    // Nothing to do if the write stamp is us.
    if (obj.$writer == current) return;

    // Make sure we're not supposed to abort/retry.
    checkRetrySignal();

    // Check write condition: wait until writer is in our ancestry and all
    // readers are in our ancestry.
    boolean hadToWait = false;
    try {
      // This is the set of logs for those transactions we're waiting for.
      Set<Log> waitsFor = new HashSet<>();

      while (true) {
        waitsFor.clear();

        // Make sure writer is in our ancestry.
        if (obj.$writeLockHolder != null
            && !current.isDescendantOf(obj.$writeLockHolder)) {
          if (WORKER_DEADLOCK_LOGGER.isLoggable(Level.FINEST)) {
            Logging.log(WORKER_DEADLOCK_LOGGER, Level.FINER,
                "{0} in {6} wants to write {1}/{2} ({3}) ({5}); waiting on writer {4}",
                current, obj.$getStore(), obj.$getOnum(), obj.getClass(),
                obj.$writeLockHolder, System.identityHashCode(obj),
                Thread.currentThread());
          }
          waitsFor.add(obj.$writeLockHolder);
          hadToWait = true;
        } else {
          // Wait on any incompatible readers.
          ReadMap.Entry readMapEntry = obj.$readMapEntry;
          if (readMapEntry != null) {
            synchronized (readMapEntry) {
              for (Log lock : readMapEntry.getReaders()) {
                if (!current.isDescendantOf(lock)) {
                  if (WORKER_DEADLOCK_LOGGER.isLoggable(Level.FINEST)) {
                    Logging.log(WORKER_DEADLOCK_LOGGER, Level.FINER,
                        "{0} in {6} wants to write {1}/{2} ({3}) ({5}); aborting reader {4}",
                        current, obj.$getStore(), obj.$getOnum(),
                        obj.getClass(), lock, System.identityHashCode(obj),
                        Thread.currentThread());
                  }
                  waitsFor.add(lock);
                  lock.flagRetry();
                }
              }

              if (waitsFor.isEmpty()) {
                break;
              }
              hadToWait = true;
            }
          }
        }

        obj.$numWaiting++;
        try {
          current.setWaitsFor(waitsFor, obj);

          // Ask for deadlock detection while we wait.
          deadlockDetector.requestDetect(current);
          obj.wait();
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        }
        obj.$numWaiting--;

        // Make sure we weren't aborted/retried while we were waiting.
        checkRetrySignal();
      }
    } finally {
      current.clearWaitsFor();
    }

    // Set the write stamp.
    obj.$writer = current;

    if (hadToWait) WORKER_TRANSACTION_LOGGER.log(Level.FINEST,
        "{0} got write lock", current);

    if (obj.$writeLockHolder == current) return;

    // Create a backup object, grab the write lock, and add the object to our
    // write set.
    obj.$history = obj.clone();
    obj.$writeLockHolder = current;
    if (TRACE_WRITE_LOCKS)
      obj.$writeLockStackTrace = Thread.currentThread().getStackTrace();

    if (obj.$getStore().isLocalStore()) {
      synchronized (current.localStoreWrites) {
        current.localStoreWrites.add(obj);
      }
    } else {
      synchronized (current.writes) {
        current.writes.put(obj, obj);
      }
    }

    if (WORKER_DEADLOCK_LOGGER.isLoggable(Level.FINEST)) {
      Logging.log(WORKER_DEADLOCK_LOGGER, Level.FINEST,
          "{0} in {5} got write lock on {1}/{2} ({3}) ({4})", current,
          obj.$getStore(), obj.$getOnum(), obj.getClass(),
          System.identityHashCode(obj), Thread.currentThread());
    }

    if (obj.$reader != current) {
      // Clear the read stamp -- the reader's read condition no longer holds.
      obj.$reader = Log.NO_READER;
    }
    // Also grab a read lock so we can be preempted if the cache is updated.
    ensureReadLock(obj);
  }

  /**
   * Ensures the worker has ownership of the object. This method assumes we are
   * synchronized on the object.
   */
  private void ensureOwnership(_Impl obj) {
    if (obj.$isOwned) return;

    // Check the writer map to see if another worker currently owns the object,
    // but only do so if the object's labels are initialized.
    if (obj.$version != 0 || obj.get$$updateLabel() != null) {
      RemoteWorker owner = current.writerMap.getWriter(obj.$getProxy());
      if (owner != null)
        owner.takeOwnership(current.tid, obj.$getStore(), obj.$getOnum());
    }

    // We now own the object.
    obj.$isOwned = true;

    // Add the object to the writer map, but only do so if the object's labels
    // are initialized.
    if (obj.$version != 0 || obj.get$$updateLabel() != null) {
      current.writerMap.put(obj.$getProxy(),
          Worker.getWorker().getLocalWorker());
    }

    // If the object is fresh, add it to our set of creates.
    if (obj.$version == 0) {
      if (obj.$getStore().isLocalStore()) {
        synchronized (current.localStoreCreates) {
          current.localStoreCreates.add(obj);
        }
      } else {
        synchronized (current.creates) {
          current.creates.put(obj, obj);
        }
      }
    }
  }

  /**
   * Checks the writer map and fetches from the object's owner as necessary.
   * This method assumes we are synchronized on the object.
   */
  private void ensureObjectUpToDate(_Impl obj) {
    // Check the object's update-map version stamp.
    if (obj.writerMapVersion == current.writerMap.version) return;

    // Set the update-map version stamp on the object.
    obj.writerMapVersion = current.writerMap.version;

    if (obj.get$$updateLabel() == null) {
      // Labels not initialized yet. Objects aren't added to the writer map
      // until after label initialization, so no need to check the writer map.
      return;
    }

    // Check the writer map.
    RemoteWorker owner = current.writerMap.getWriter(obj.$getProxy());
    if (owner == null || owner == Worker.getWorker().getLocalWorker()) return;

    // Need to fetch from the owner.
    ensureWriteLock(obj);
    owner.readObject(current.tid, obj);
  }

  /**
   * Registers a sample that will need to be resolved before committing or
   * accessing any Warranty values.
   */
  public void registerSample(SampledMetric sampled) {
    // Add it to the log's unobserved queue if it's not already there.
    synchronized (current.unobservedSamples) {
      current.unobservedSamples.put(sampled, sampled);
    }
  }

  /**
   * Registers a contract that can and should be extended later closer to the
   * expiration.  This will be done by sending an extension message after the
   * transaction completes.
   *
   * This version includes an argument to indicate the object updated that
   * enables this extension.
   */
  public void registerDelayedExtension(fabric.lang.Object toBeExtended,
      fabric.lang.Object extendingObject) {
    _Impl obj = (_Impl) toBeExtended.fetch();
    synchronized (obj) {
      synchronized (current.writes) {
        if (current.writes.containsKey(obj)) return;
      }
      synchronized (current.creates) {
        if (current.creates.containsKey(obj)) return;
      }
      synchronized (current.extendedTreaties) {
        if (current.extendedTreaties.containsKey(obj)) return;
      }
      synchronized (current.delayedExtensions) {
        current.addDelayedExtension(toBeExtended, extendingObject);
      }
    }
  }

  /**
   * Registers a contract that can and should be extended later closer to the
   * expiration.  This will be done by sending an extension message after the
   * transaction completes.
   */
  public void registerDelayedExtension(fabric.lang.Object toBeExtended) {
    _Impl obj = (_Impl) toBeExtended.fetch();
    synchronized (obj) {
      synchronized (current.writes) {
        if (current.writes.containsKey(obj)) return;
      }
      synchronized (current.creates) {
        if (current.creates.containsKey(obj)) return;
      }
      synchronized (current.extendedTreaties) {
        if (current.extendedTreaties.containsKey(obj)) return;
      }
      synchronized (current.delayedExtensions) {
        current.addDelayedExtension(toBeExtended);
      }
    }
  }

  /**
   * Resolves observations in the current transaction.  Called before retrieving
   * a warranty's value.
   */
  public void resolveObservations() {
    if (current != null) {
      current.resolveObservations();
    }
  }

  /**
   * Checks whether any of the objects used by a transaction are stale.
   *
   * @return true iff stale objects were found
   */
  public boolean checkForStaleObjects() {
    Set<Store> stores = current.storesToCheckFreshness();
    int numNodesToContact = stores.size() + current.workersCalled.size();
    final List<RemoteNode<?>> nodesWithStaleObjects = Collections
        .synchronizedList(new ArrayList<RemoteNode<?>>(numNodesToContact));
    List<Future<?>> futures = new ArrayList<>(numNodesToContact);

    // Go through each worker and send check messages in parallel.
    for (final RemoteWorker worker : current.workersCalled) {
      NamedRunnable runnable =
          new NamedRunnable("worker freshness check to " + worker.name()) {
            @Override
            public void runImpl() {
              try {
                if (worker.checkForStaleObjects(current.tid))
                  nodesWithStaleObjects.add(worker);
              } catch (UnreachableNodeException e) {
                // Conservatively assume it had stale objects.
                nodesWithStaleObjects.add(worker);
              }
            }
          };
      futures.add(Threading.getPool().submit(runnable));
    }

    // Go through each store and send check messages in parallel.
    for (Iterator<Store> storeIt = stores.iterator(); storeIt.hasNext();) {
      final Store store = storeIt.next();
      NamedRunnable runnable =
          new NamedRunnable("worker freshness check to " + store.name()) {
            @Override
            public void runImpl() {
              LongKeyMap<Pair<Integer, TreatySet>> reads =
                  current.getReadsForStore(store, true);
              if (store.checkForStaleObjects(reads))
                nodesWithStaleObjects.add((RemoteNode<?>) store);
            }
          };

      // Optimization: only start a new thread if there are more stores to
      // contact
      if (storeIt.hasNext()) {
        futures.add(Threading.getPool().submit(runnable));
      } else {
        runnable.run();
      }
    }

    // Wait for replies.
    for (Future<?> future : futures) {
      while (true) {
        try {
          future.get();
          break;
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
      }
    }

    return !nodesWithStaleObjects.isEmpty();
  }

  /**
   * Starts a new transaction. The sub-transaction runs in the same thread as
   * the caller.
   */
  public void startTransaction() {
    startTransaction(null);
  }

  /**
   * Starts a new transaction with the given tid. The given tid is assumed to be
   * a valid descendant of the current tid. If the given tid is null, a random
   * tid is generated for the sub-transaction.
   */
  public void startTransaction(TransactionID tid) {
    startTransaction(tid, false);
  }

  private boolean acquiringLocks = false;

  private void startTransaction(TransactionID tid, boolean ignoreRetrySignal) {
    if (current != null && !ignoreRetrySignal) checkRetrySignal();

    // Acquire locks before starting but also avoid bad recursion.
    if ((current == null || current.tid == null) && !acquiringLocks) {
      stats.markTxnAttempt();
      acquiringLocks = true;
      acquireContractLocks();
      acquiringLocks = false;
    }

    try {
      Timing.BEGIN.begin();
      current = new Log(current, tid);
      if (current.parent == null) {
        Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINE,
            "started txn {0} in thread {1}", current, Thread.currentThread());
      } else {
        Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINEST,
            "{0} started subtx {1} in thread {2}", current.parent, current,
            Thread.currentThread());
      }
      HOTOS_LOGGER.log(Level.FINEST, "started {0}", current);
    } finally {
      Timing.BEGIN.end();
    }
  }

  /**
   * Starts the given thread, registering it as necessary.
   */
  public static void startThread(Thread thread) {
    if (!(thread instanceof FabricThread)) getInstance().registerThread(thread);

    thread.start();
  }

  /**
   * Registers the given thread with the current transaction. This should be
   * called before the thread is started.
   */
  public void registerThread(Thread thread) {
    Timing.TXLOG.begin();
    try {
      // XXX Eventually, we will want to support threads in transactions.
      if (current != null)
        throw new InternalError("Cannot create threads within transactions");

      TransactionManager tm = new TransactionManager();

      if (thread instanceof FabricThread) {
        ((FabricThread) thread).setTransactionManager(tm);
      } else {
        synchronized (instanceMap) {
          instanceMap.put(thread, tm);
        }
      }
    } finally {
      Timing.TXLOG.end();
    }
  }

  /**
   * Registers that the given thread has finished.
   */
  public void deregisterThread(Thread thread) {
    if (!(thread instanceof FabricThread)) {
      synchronized (instanceMap) {
        instanceMap.remove(thread);
      }
    }
  }

  /**
   * Registers a remote call to the given worker.
   */
  public void registerRemoteCall(RemoteWorker worker) {
    if (current != null) {
      if (!current.workersCalled.contains(worker))
        current.workersCalled.add(worker);
    }
  }

  /**
   * Starts tracking for a new nonatomic call being handled in this thread.
   * Initializes a new committedWrites list.
   */
  public void startNonAtomicCall() {
    if (committedWrites == null) committedWrites = new OidKeyHashMap<>();
  }

  /**
   * Finishes tracking for a new nonatomic call being handled in this thread.
   * @return The list of oids for which writes have been committed.
   */
  public OidKeyHashMap<Integer> finishNonAtomicCall() {
    OidKeyHashMap<Integer> writes = committedWrites;
    committedWrites = null;
    return writes;
  }

  /**
   * Add a batch of committed writes to the running list.
   */
  public void addCommittedWrites(OidKeyHashMap<Integer> writes) {
    if (committedWrites != null) committedWrites.putAll(writes);
  }

  /**
   * Associates the given transaction log with this transaction manager.
   */
  public void associateLog(Log log) {
    current = log;
  }

  public Log getCurrentLog() {
    return current;
  }

  public TransactionID getCurrentTid() {
    if (current == null) return null;
    return current.tid;
  }

  /**
   * Useful for writing code that should only run at the end of a top level
   * transaction.
   *
   * @return true iff the current txn context is nested within some other
   * transaction.
   */
  public boolean inNestedTxn() {
    return inTxn() && current.parent != null;
  }

  /**
   * Useful for writing code that should only run if currently (not) in a
   * transaction.
   *
   * @return true iff there is currently a transaction running.
   */
  public boolean inTxn() {
    return current != null;
  }

  public WriterMap getWriterMap() {
    if (current == null) return null;
    return current.writerMap;
  }

  /**
   * @return the worker on which the object resides. An object resides on a
   *         worker if it is either on that worker's local store, or if it was
   *         created by the current transaction and is owned by that worker.
   */
  public RemoteWorker getFetchWorker(_Proxy proxy) {
    if (current == null || !current.writerMap.containsCreate(proxy))
      return null;
    Label label = current.writerMap.getCreate(proxy);

    return current.writerMap.getWriter(proxy, label);
  }

  public SecurityCache getSecurityCache() {
    if (current == null) throw new InternalError(
        "Application attempting to perform label operations outside of a transaction");
    return (SecurityCache) current.securityCache;
  }

  /**
   * Associates the given log with this worker's transaction manager and
   * synchronizes the log with the given tid.
   */
  public void associateAndSyncLog(Log log, TransactionID tid) {
    associateLog(log);

    if (log == null) {
      if (tid != null) startTransaction(tid);
      return;
    }

    // Do the commits that we've missed. Ignore retry signals for now; they will
    // be handled the next time the application code interacts with the
    // transaction manager.
    TransactionID commonAncestor = log.getTid().getLowestCommonAncestor(tid);
    for (int i = log.getTid().depth; i > commonAncestor.depth; i--)
      commitTransaction(true);

    // Start new transactions if necessary.
    if (commonAncestor.depth != tid.depth) startTransaction(tid, true);
  }

  /**
   * Note that a coordination happened.
   */
  public void markCoordination() {
    // TODO: This is deprecated.
  }

  /**
   * Allow for an object to see the current pending extension for itself.
   */
  public ExpiryExtension getPendingExtension(_Impl obj) {
    return current != null ? current.extendedTreaties.get(obj) : null;
  }
}
