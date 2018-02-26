package fabric.worker.transaction;

import static fabric.common.Logging.HOTOS_LOGGER;
import static fabric.common.Logging.METRICS_LOGGER;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
import fabric.common.SerializedObject;
import fabric.common.Threading;
import fabric.common.Threading.NamedRunnable;
import fabric.common.Timing;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
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
import fabric.messages.AsyncCallMessage;
import fabric.metrics.SampledMetric;
import fabric.metrics.util.ReconfigLock;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;
import fabric.store.InProcessStore;
import fabric.worker.AbortException;
import fabric.worker.LocalStore;
import fabric.worker.RemoteStore;
import fabric.worker.Store;
import fabric.worker.TransactionAbortingException;
import fabric.worker.TransactionAtomicityViolationException;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.TransactionRestartingException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.metrics.LockConflictException;
import fabric.worker.metrics.TxnStats;
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
   * If this thread is handling an {@link AsyncCallMessage}, this is the list of
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

  // Mark a created lock.
  public void registerLockCreate(fabric.lang.Object lock) {
    Logging.log(METRICS_LOGGER, Level.FINE, "CREATING LOCK {0}/{1} IN {2}/{3}",
        lock.$getStore(), new Long(lock.$getOnum()), Thread.currentThread(),
        getCurrentTid());
    // You start out holding the lock if you created it.
    current.locksCreated.put(lock, true);
  }

  // Mark an acquired lock.
  public void registerLockAcquire(fabric.lang.Object lock) {
    Logging.log(METRICS_LOGGER, Level.FINE, "ACQUIRING LOCK {0}/{1} IN {2}/{3}",
        lock.$getStore(), new Long(lock.$getOnum()), Thread.currentThread(),
        getCurrentTid());
    current.acquires.put(lock, true);
    if (!acquiringLocks && !current.locksCreated.containsKey(lock)) {
      contractsToAcquire.put(lock, true);
    }
  }

  // Check that we have this lock.
  public boolean hasLock(fabric.lang.Object lock) {
    // TODO: consider allowing a pending acquire to be considered held?
    Logging.log(METRICS_LOGGER, Level.FINE, "CHECKING LOCK {0}/{1} IN {2}/{3}",
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
    Logging.log(METRICS_LOGGER, Level.FINE, "RELEASING LOCK {0}/{1} IN {2}/{3}",
        lock.$getStore(), new Long(lock.$getOnum()), Thread.currentThread(),
        getCurrentTid());
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
      if (METRICS_LOGGER.isLoggable(Level.FINE)) {
        for (Store s : contractLocksHeld.storeSet()) {
          for (LongIterator it =
              contractLocksHeld.get(s).keySet().iterator(); it.hasNext();) {
            long onum = it.next();
            Logging.log(METRICS_LOGGER, Level.FINE,
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
    if (!contractsToAcquire.equals(contractLocksHeld)
        || !waitingOn.isEmpty()) {
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
              int sleepTime = Math.min(1 << attempts, 1000);
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

          Logging.log(METRICS_LOGGER, Level.FINE, "LOCK ATTEMPT {0} IN {1}",
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
                    Logging.log(METRICS_LOGGER, Level.FINE,
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
          Logging.log(METRICS_LOGGER, Level.FINE,
              "ACQUIRED LOCK {0}/{1} IN {2}", s, onum, Thread.currentThread());
        }
      }
      for (Store s : releases.storeSet()) {
        for (LongIterator it = releases.get(s).keySet().iterator(); it
            .hasNext();) {
          long onum = it.next();
          contractLocksHeld.remove(s, onum);
          Logging.log(METRICS_LOGGER, Level.FINE,
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
    if (current.retrySignal != null) {
      synchronized (current) {
        WORKER_TRANSACTION_LOGGER.log(Level.FINEST, "{0} got retry signal",
            current);

        throw new TransactionRestartingException(current.retrySignal);
      }
    }
  }

  /**
   * Aborts the transaction, recursing to any workers that were called, and any
   * stores that were contacted.
   */
  public void abortTransaction() {
    abortTransaction(Collections.<RemoteNode<?>> emptySet());
  }

  /**
   * @param abortedNodes
   *          a set of nodes that don't need to be contacted because they
   *          already know about the abort.
   */
  private void abortTransaction(Set<RemoteNode<?>> abortedNodes) {
    Set<Store> storesToContact;
    List<RemoteWorker> workersToContact;
    if (current.tid.depth == 0) {
      // Aborting a top-level transaction. Make sure no other thread is working
      // on this transaction.
      synchronized (current.commitState) {
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
          storesToContact = Collections.emptySet();
          workersToContact = current.workersCalled;
          break;

        case PREPARE_FAILED:
        case PREPARED:
          current.commitState.value = ABORTING;
          storesToContact = current.storesToContact();
          workersToContact = current.workersCalled;
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
    } else {
      // Aborting a nested transaction. Only need to abort at the workers we've
      // called.
      storesToContact = Collections.emptySet();
      workersToContact = current.workersCalled;
    }

    boolean readOnly = current.isReadOnly();

    WORKER_TRANSACTION_LOGGER.log(Level.INFO, "{0} aborting", current);
    // Assume only one thread will be executing this.
    HOTOS_LOGGER.log(Level.FINEST, "aborting {0}", current);

    // Set the retry flag in all our children.
    current.flagRetry();

    // Wait for all other threads to finish.
    current.waitForThreads();

    sendAbortMessages(storesToContact, workersToContact, abortedNodes);
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

    // Send prepare messages to our cohorts. This will also abort our portion of
    // the transaction if the prepare fails.
    current.longerContracts =
        sendPrepareMessages(singleStore, readOnly, stores, workers).first;

    // Send commit messages to our cohorts.
    sendCommitMessagesAndCleanUp(singleStore, readOnly, stores, workers);

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
    if (HOTOS_LOGGER.isLoggable(Level.FINE)) {
      final long commitLatency = commitTime - prepareStart;
      if (LOCAL_STORE == null) LOCAL_STORE = Worker.getWorker().getLocalStore();
      if (workers.size() > 0 || stores.size() != 1
          || !stores.contains(LOCAL_STORE)
              && !(stores.iterator().next() instanceof InProcessStore)) {
        Logging.log(HOTOS_LOGGER, Level.FINE,
            "committed tid {0} (latency {1} ms, {2} stores, {3} retractions, {4} extensions, {5} delayed extensions, locking={6})",
            HOTOS_current, commitLatency,
            stores.size() - (stores.contains(LOCAL_STORE) ? 1 : 0),
            HOTOS_current.retractedContracts.size(),
            HOTOS_current.extendedContracts.size(),
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
  public Pair<Map<RemoteStore, LongKeyMap<SerializedObject>>, Long> sendPrepareMessages() {
    return sendPrepareMessages(false, false, current.storesToContact(),
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
  private Pair<Map<RemoteStore, LongKeyMap<SerializedObject>>, Long> sendPrepareMessages(
      final boolean singleStore, final boolean readOnly, Set<Store> stores,
      List<RemoteWorker> workers) throws TransactionRestartingException {
    final Map<RemoteNode<?>, TransactionPrepareFailedException> failures =
        Collections.synchronizedMap(
            new HashMap<RemoteNode<?>, TransactionPrepareFailedException>());
    final Map<RemoteStore, LongKeyMap<SerializedObject>> longerContracts =
        Collections.synchronizedMap(
            new HashMap<RemoteStore, LongKeyMap<SerializedObject>>());
    // Time to use for contract comparisons at the end of prepare.
    final long[] time = new long[] { System.currentTimeMillis() };

    synchronized (current.commitState) {
      switch (current.commitState.value) {
      case UNPREPARED:
        current.commitState.value = PREPARING;
        break;

      case PREPARING:
      case PREPARED:
        return new Pair<>(longerContracts, time[0]);

      case COMMITTING:
      case COMMITTED:
        WORKER_TRANSACTION_LOGGER.log(Level.FINE,
            "Ignoring prepare request (transaction state = {0})",
            current.commitState.value);
        return new Pair<>(longerContracts, time[0]);

      case PREPARE_FAILED:
        throw new InternalError();

      case ABORTING:
      case ABORTED:
        throw new TransactionRestartingException(current.tid);
      }
    }

    List<Future<?>> futures = new ArrayList<>(stores.size() + workers.size());

    // Go through each worker and send prepare messages in parallel.
    for (final RemoteWorker worker : workers) {
      Threading.NamedRunnable runnable =
          new Threading.NamedRunnable("worker prepare to " + worker.name()) {
            @Override
            protected void runImpl() {
              try {
                long t = worker.prepareTransaction(current.tid.topTid);
                synchronized (time) {
                  time[0] = Math.max(t, time[0]);
                }
              } catch (UnreachableNodeException e) {
                failures.put(worker, new TransactionPrepareFailedException(
                    "Unreachable worker"));
              } catch (TransactionPrepareFailedException e) {
                failures.put(worker,
                    new TransactionPrepareFailedException(e.getMessage()));
              } catch (TransactionRestartingException e) {
                failures.put(worker, new TransactionPrepareFailedException(
                    "transaction restarting"));
              }
            }
          };
      futures.add(Threading.getPool().submit(runnable));
    }

    boolean haveRoundTrip = false;

    // Go through each store and send prepare messages in parallel.
    for (Iterator<Store> storeIt = stores.iterator(); storeIt.hasNext();) {
      final Store store = storeIt.next();
      NamedRunnable runnable =
          new NamedRunnable("worker prepare to " + store.name()) {
            @Override
            public void runImpl() {
              try {
                Collection<_Impl> creates = current.getCreatesForStore(store);
                LongKeyMap<Pair<Integer, Long>> reads =
                    current.getReadsForStore(store, false);
                Collection<Pair<_Impl, Boolean>> writes =
                    current.getWritesForStore(store);
                if (store instanceof RemoteStore) {
                  Pair<LongKeyMap<SerializedObject>, Long> p =
                      store.prepareTransaction(current.tid.topTid, singleStore,
                          readOnly, creates, reads, writes);
                  longerContracts.put((RemoteStore) store, p.first);
                  synchronized (time) {
                    time[0] = Math.max(p.second, time[0]);
                  }
                } else {
                  store.prepareTransaction(current.tid.topTid, singleStore,
                      readOnly, creates, reads, writes);
                }
              } catch (TransactionPrepareFailedException e) {
                failures.put((RemoteNode<?>) store, e);
              } catch (UnreachableNodeException e) {
                failures.put((RemoteNode<?>) store,
                    new TransactionPrepareFailedException("Unreachable store"));
              }
            }
          };

      // Optimization: only start in a new thread if there are more stores to
      // contact and if it's a truly remote store (i.e., not in-process).
      if (!(store instanceof InProcessStore || store.isLocalStore())
          && storeIt.hasNext()) {
        futures.add(Threading.getPool().submit(runnable));
      } else {
        runnable.run();
      }

      if (!(store instanceof InProcessStore || store.isLocalStore()))
        haveRoundTrip = true;
    }

    if (haveRoundTrip) {
      Integer curRoundTrips = ROUND_TRIPS.get();
      if (curRoundTrips != null) {
        ROUND_TRIPS.set(curRoundTrips + 1);
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

    // Check for conflicts and unreachable stores/workers.
    if (!failures.isEmpty()) {
      String logMessage = "Transaction tid="
          + Long.toHexString(current.tid.topTid) + ":  prepare failed.";

      for (Map.Entry<RemoteNode<?>, TransactionPrepareFailedException> entry : failures
          .entrySet()) {
        if (WORKER_TRANSACTION_LOGGER.isLoggable(Level.FINE)) {
          logMessage +=
              "\n\t" + entry.getKey() + ": " + entry.getValue().getMessage();
        }
        if (entry.getKey() instanceof RemoteStore) {
          // Remove old objects from our cache.
          RemoteStore store = (RemoteStore) entry.getKey();
          LongKeyMap<SerializedObject> versionConflicts =
              entry.getValue().versionConflicts;
          if (versionConflicts != null) {
            for (SerializedObject obj : versionConflicts.values()) {
              if (WORKER_TRANSACTION_LOGGER.isLoggable(Level.FINE)) {
                try {
                  long onum = obj.getOnum();
                  long oldVersion = -1;
                  LongKeyMap<Pair<Integer, Long>> reads =
                      current.getReadsForStore(store, false);
                  if (reads.containsKey(onum))
                    oldVersion = reads.get(onum).first;
                  logMessage += "\n\t\tBad version for " + obj.getClassName()
                      + " " + obj.getOnum() + " (should be ver. "
                      + obj.getVersion() + " was " + oldVersion
                      + " and currently have "
                      + store.readObject(obj.getOnum()).getVersion() + ")";
                } catch (Exception e) {
                }
              }
              store.updateCache(obj);
            }
          }
        }
      }

      // Update the contract objects locally so that we're using the latest
      // version next time.
      for (Map.Entry<RemoteStore, LongKeyMap<SerializedObject>> e1 : longerContracts
          .entrySet()) {
        RemoteStore s = e1.getKey();
        for (SerializedObject obj : e1.getValue().values()) {
          // Evict the old version.
          s.updateCache(obj);
        }
      }

      WORKER_TRANSACTION_LOGGER.fine(logMessage);
      HOTOS_LOGGER.fine("Prepare failed.");

      synchronized (current.commitState) {
        current.commitState.value = PREPARE_FAILED;
        current.commitState.notifyAll();
      }

      TransactionID tid = current.tid;

      TransactionPrepareFailedException e =
          new TransactionPrepareFailedException(failures);
      Logging.log(WORKER_TRANSACTION_LOGGER, Level.INFO,
          "{0} error committing: prepare failed exception: {1}", current, e);

      Set<RemoteNode<?>> abortedNodes = failures.keySet();
      if (readOnly) {
        // All remote stores should have aborted already.
        abortedNodes = new HashSet<>(abortedNodes);
        for (Store store : stores) {
          if (store instanceof RemoteStore) {
            abortedNodes.add((RemoteStore) store);
          }
        }
      }
      abortTransaction(abortedNodes);
      throw new TransactionRestartingException(tid);

    } else if (current.expiry() < time[0]) {

      HOTOS_LOGGER.fine("Prepare failed (expiry passed).");

      synchronized (current.commitState) {
        current.commitState.value = PREPARE_FAILED;
        current.commitState.notifyAll();
      }

      Logging.log(WORKER_TRANSACTION_LOGGER, Level.INFO,
          "{0} error committing: prepare too late", current);

      Set<RemoteNode<?>> abortedNodes = new HashSet<>();
      if (readOnly || singleStore) {
        // All remote stores should have aborted already if they're read only or
        // this is single store.
        for (Store store : stores) {
          if (store instanceof RemoteStore) {
            abortedNodes.add((RemoteStore) store);
          }
        }
      }

      TransactionID tid = current.tid;
      abortTransaction(abortedNodes);
      throw new TransactionRestartingException(tid);
    } else {
      synchronized (current.commitState) {
        current.commitState.value = PREPARED;
        current.commitState.notifyAll();
      }
    }

    return new Pair<>(longerContracts, time[0]);
  }

  /**
   * Sends commit messages to the cohorts in a distributed transaction.
   */
  public void sendCommitMessagesAndCleanUp()
      throws TransactionAtomicityViolationException {
    sendCommitMessagesAndCleanUp(false, false, current.storesToContact(),
        current.workersCalled);
  }

  /**
   * Sends commit messages to the given set of stores and workers.
   */
  private void sendCommitMessagesAndCleanUp(boolean singleStore,
      boolean readOnly, Set<Store> stores, List<RemoteWorker> workers)
      throws TransactionAtomicityViolationException {
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

    if (!singleStore && !readOnly) {
      final List<RemoteNode<?>> unreachable =
          Collections.synchronizedList(new ArrayList<RemoteNode<?>>());
      final List<RemoteNode<?>> failed =
          Collections.synchronizedList(new ArrayList<RemoteNode<?>>());
      List<Future<?>> futures = new ArrayList<>(stores.size() + workers.size());

      // Send commit messages to the workers in parallel.
      for (final RemoteWorker worker : workers) {
        NamedRunnable runnable =
            new NamedRunnable("worker commit to " + worker) {
              @Override
              public void runImpl() {
                try {
                  worker.commitTransaction(current.tid.topTid);
                } catch (UnreachableNodeException e) {
                  unreachable.add(worker);
                } catch (TransactionCommitFailedException e) {
                  failed.add(worker);
                }
              }
            };

        futures.add(Threading.getPool().submit(runnable));
      }

      boolean haveRoundTrip = false;

      // Send commit messages to the stores in parallel.
      for (Iterator<Store> storeIt = stores.iterator(); storeIt.hasNext();) {
        final Store store = storeIt.next();
        NamedRunnable runnable =
            new NamedRunnable("worker commit to " + store.name()) {
              @Override
              public void runImpl() {
                try {
                  store.commitTransaction(current.tid.topTid);
                } catch (TransactionCommitFailedException e) {
                  failed.add((RemoteStore) store);
                } catch (UnreachableNodeException e) {
                  unreachable.add((RemoteStore) store);
                }
              }
            };

        // Optimization: only start in a new thread if there are more stores to
        // contact and if it's a truly remote store (i.e., not in-process).
        if (!(store instanceof InProcessStore || store.isLocalStore())
            && storeIt.hasNext()) {
          futures.add(Threading.getPool().submit(runnable));
        } else {
          runnable.run();
        }

        if (!(store instanceof InProcessStore || store.isLocalStore()))
          haveRoundTrip = true;
      }

      if (haveRoundTrip) {
        Integer curRoundTrips = ROUND_TRIPS.get();
        if (curRoundTrips != null) {
          ROUND_TRIPS.set(curRoundTrips + 1);
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
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }

      if (!(unreachable.isEmpty() && failed.isEmpty())) {
        Logging.log(WORKER_TRANSACTION_LOGGER, Level.SEVERE,
            "{0} error committing: atomicity violation "
                + "-- failed: {1} unreachable: {2}",
            current, failed, unreachable);
        throw new TransactionAtomicityViolationException(failed, unreachable);
      }
    }

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

  /**
   * Sends abort messages to those nodes that haven't reported failures.
   *
   * @param stores
   *          the set of stores involved in the transaction.
   * @param workers
   *          the set of workers involved in the transaction.
   * @param fails
   *          the set of nodes that have reported failure.
   */
  private void sendAbortMessages(Set<Store> stores, List<RemoteWorker> workers,
      Set<RemoteNode<?>> fails) {
    for (Store store : stores)
      if (!fails.contains(store)) {
        try {
          store.abortTransaction(current.tid);
        } catch (AccessException e) {
          Logging.log(WORKER_TRANSACTION_LOGGER, Level.WARNING,
              "Access error while aborting transaction: {0}", e);
        }
      }

    for (RemoteWorker worker : workers)
      if (!fails.contains(worker)) {
        try {
          worker.abortTransaction(current.tid);
        } catch (AccessException e) {
          Logging.log(WORKER_TRANSACTION_LOGGER, Level.WARNING,
              "Access error while aborting transaction: {0}", e);
        }
      }
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

      // Own the object. The call to ensureOwnership is responsible for adding
      // the object to the set of created objects.
      ensureOwnership(obj);

      // The object has been written, so it's not being extended.
      synchronized (current.extendedContracts) {
        current.extendedContracts.remove(obj);
      }
      synchronized (current.delayedExtensions) {
        current.delayedExtensions.remove(obj);
      }
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
      boolean firstWait = true;
      boolean deadlockDetectRequested = false;
      while (obj.$writeLockHolder != null
          && !current.isDescendantOf(obj.$writeLockHolder)) {
        try {
          Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINEST,
              "{0} wants to read {1}/{2} ({3}); waiting on writer {4}", current,
              obj.$getStore(), obj.$getOnum(), obj.getClass(),
              obj.$writeLockHolder);
          hadToWait = true;
          obj.$numWaiting++;
          current.setWaitsFor(obj.$writeLockHolder);

          if (firstWait) {
            // This is the first time we're waiting. Wait with a 10 ms timeout.
            firstWait = false;
            obj.wait(10);
          } else {
            // Not the first time through the loop. Ask for deadlock detection
            // if we haven't already.
            if (!deadlockDetectRequested) {
              deadlockDetector.requestDetect(current);
              deadlockDetectRequested = true;
            }

            // Should be waiting indefinitely, but this requires proper handling
            // of InterruptedExceptions in the entire system. Instead, we spin
            // once a second so that we periodically check the retry signal.
            obj.wait(1000);
          }
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

    synchronized (obj) {
      if (obj.$writer == current
          && obj.writerMapVersion == current.writerMap.version && obj.$isOwned)
        return needTransaction;

      try {
        Timing.TXLOG.begin();
        ensureWriteLock(obj);
        ensureObjectUpToDate(obj);
        ensureOwnership(obj);

        // The object has been written, so it's not being extended.
        synchronized (current.extendedContracts) {
          current.extendedContracts.remove(obj);
        }
        synchronized (current.delayedExtensions) {
          current.delayedExtensions.remove(obj);
        }
      } finally {
        Timing.TXLOG.end();
      }
    }

    return needTransaction;

  }

  /**
   * This should be called <i>before</i> the object's expiry is modified.
   *
   * @return whether a new (top-level) transaction was created.
   */
  public boolean registerExpiryWrite(_Impl obj, long oldExpiry,
      long newExpiry) {
    boolean needTransaction = (current == null);
    if (needTransaction) startTransaction();

    synchronized (obj) {
      if (obj.$writer == current
          && obj.writerMapVersion == current.writerMap.version && obj.$isOwned)
        return needTransaction;

      // If this is the only write, it's an extension.
      boolean extension = oldExpiry < newExpiry;
      synchronized (current.writes) {
        if (current.writes.contains(obj)) extension = false;
      }
      synchronized (current.creates) {
        if (current.creates.contains(obj)) extension = false;
      }

      try {
        Timing.TXLOG.begin();
        ensureWriteLock(obj);
        ensureObjectUpToDate(obj);
        ensureOwnership(obj);
      } finally {
        Timing.TXLOG.end();
      }

      if (extension) {
        synchronized (current.extendedContracts) {
          current.extendedContracts.put(obj, obj);
        }
      } else {
        synchronized (current.extendedContracts) {
          current.extendedContracts.remove(obj);
        }
        synchronized (current.delayedExtensions) {
          current.delayedExtensions.remove(obj);
        }
      }

      // Track retractions
      if (oldExpiry >= newExpiry) {
        synchronized (current.retractedContracts) {
          current.retractedContracts.put(obj, obj);
        }
      }
    }

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

      boolean firstWait = true;
      boolean deadlockDetectRequested = false;
      while (true) {
        waitsFor.clear();

        // Flag indicating if we temporarily set a write lock for current
        boolean tempWriteLock = false;
        // Make sure writer is in our ancestry.
        if (obj.$writeLockHolder != null
            && !current.isDescendantOf(obj.$writeLockHolder)) {
          Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINEST,
              "{0} wants to write {1}/{2} ({3}); waiting on writer {4}",
              current, obj.$getStore(), obj.$getOnum(), obj.getClass(),
              obj.$writeLockHolder);
          waitsFor.add(obj.$writeLockHolder);
          hadToWait = true;
        } else {
          if (obj.$writeLockHolder == null) {
            // Grab a write lock temporarily to avoid later readers dogpiling.
            tempWriteLock = true;
            obj.$writeLockHolder = current;
          }
          // Restart any incompatible readers.
          ReadMap.Entry readMapEntry = obj.$readMapEntry;
          if (readMapEntry != null) {
            synchronized (readMapEntry) {
              for (Log lock : readMapEntry.getReaders()) {
                if (!current.isDescendantOf(lock)) {
                  Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINEST,
                      "{0} wants to write {1}/{2} ({3}); aborting reader {4}",
                      current, obj.$getStore(), obj.$getOnum(), obj.getClass(),
                      lock);
                  waitsFor.add(lock);
                  lock.flagRetry();
                }
              }

              if (waitsFor.isEmpty()) {
                // Release the temp lock and acquire it after the clone below.
                if (tempWriteLock) obj.$writeLockHolder = null;
                break;
              }
            }
          }
        }

        try {
          obj.$numWaiting++;
          current.setWaitsFor(waitsFor);

          if (firstWait) {
            // This is the first time we're waiting. Wait with a 10 ms timeout.
            firstWait = false;
            obj.wait(10);
          } else {
            // Not the first time through the loop. Ask for deadlock detection
            // if we haven't already.
            if (!deadlockDetectRequested) {
              deadlockDetector.requestDetect(current);
              deadlockDetectRequested = true;
            }

            // Should be waiting indefinitely, but this requires proper handling
            // of InterruptedExceptions in the entire system. Instead, we spin
            // once a second so that we periodically check the retry signal.
            obj.wait(1000);
          }
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        }
        // Release the temp lock and acquire it after the clone below.
        if (tempWriteLock) obj.$writeLockHolder = null;
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
        current.writes.add(obj);
      }
    }

    if (obj.$reader != current) {
      // Clear the read stamp -- the reader's read condition no longer holds.
      obj.$reader = Log.NO_READER;
    }
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
          current.creates.add(obj);
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
   */
  public void registerDelayedExtension(fabric.lang.Object toBeExtended,
      long expiry) {
    _Impl obj = (_Impl) toBeExtended.fetch();
    synchronized (obj) {
      synchronized (current.writes) {
        if (current.writes.contains(obj)) return;
      }
      synchronized (current.creates) {
        if (current.creates.contains(obj)) return;
      }
      synchronized (current.extendedContracts) {
        if (current.extendedContracts.containsKey(obj)) return;
      }
      synchronized (current.delayedExtensions) {
        current.delayedExtensions.put(obj, expiry);
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
              LongKeyMap<Pair<Integer, Long>> reads =
                  current.getReadsForStore(store, true);
              if (store.checkForStaleObjects(reads))
                nodesWithStaleObjects.add((RemoteNode<?>) store);
            }
          };

      // Optimization: only start a new thread if there are more stores to
      // contact and if it's truly a remote store (i.e., not in-process).
      if (!(store instanceof InProcessStore || store.isLocalStore())
          && storeIt.hasNext()) {
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
      Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINEST,
          "{0} started subtx {1} in thread {2}", current.parent, current,
          Thread.currentThread());
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
   * Starts tracking for a new async call being handled in this thread.
   * Initializes a new committedWrites list.
   */
  public void startAsyncCall() {
    if (committedWrites == null) committedWrites = new OidKeyHashMap<>();
  }

  /**
   * Finishes tracking for a new async call being handled in this thread.
   * @return The list of oids for which writes have been committed.
   */
  public OidKeyHashMap<Integer> finishAsyncCall() {
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
    if (current != null) {
      current.coordinated = true;
    }
  }
}
