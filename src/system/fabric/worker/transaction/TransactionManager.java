package fabric.worker.transaction;

import static fabric.common.Logging.WORKER_TRANSACTION_LOGGER;
import static fabric.worker.transaction.Log.CommitState.Values.ABORTED;
import static fabric.worker.transaction.Log.CommitState.Values.ABORTING;
import static fabric.worker.transaction.Log.CommitState.Values.COMMITTED;
import static fabric.worker.transaction.Log.CommitState.Values.COMMITTING;
import static fabric.worker.transaction.Log.CommitState.Values.PREPARED;
import static fabric.worker.transaction.Log.CommitState.Values.PREPARE_FAILED;
import static fabric.worker.transaction.Log.CommitState.Values.PREPARING;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.logging.Level;

import fabric.common.FabricThread;
import fabric.common.Logging;
import fabric.common.Options;
import fabric.common.SerializedObject;
import fabric.common.Timing;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Label;
import fabric.lang.security.SecurityCache;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;
import fabric.store.InProcessStore;
import fabric.worker.AbortException;
import fabric.worker.FabricSoftRef;
import fabric.worker.RemoteStore;
import fabric.worker.Store;
import fabric.worker.TransactionAbortingException;
import fabric.worker.TransactionAtomicityViolationException;
import fabric.worker.TransactionBeginFailedException;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.TransactionRestartingException;
import fabric.worker.Worker;
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
   * The innermost running transaction for the thread being managed.
   */
  private Log current;

  /**
   * A debugging switch for storing a stack trace each time a write lock is
   * obtained. Enable this by passing "--trace-locks" as a command-line argument
   * to the node.
   */
  public static boolean TRACE_WRITE_LOCKS = false;

  /**
   * A map from OIDs to <code>ReadMapEntry</code>s. Each ReadMapEntry
   * encapsulates a version number and a list of logs for transactions that have
   * read that version of the object. For each transaction tx, an object o is in
   * tx.reads exactly when tx is in readMap[o].readLocks. A transaction has
   * acquired a read lock if its log is in this list. All entries in this list
   * have non-empty <code>readLocks</code> sets.
   */
  // Proxy objects aren't used here because doing so would result in calls to
  // hashcode() and equals() on such objects, resulting in fetching the
  // corresponding Impls from the store.
  static final OidKeyHashMap<ReadMapEntry> readMap =
      new OidKeyHashMap<ReadMapEntry>();

  public static ReadMapEntry getReadMapEntry(_Impl impl, long expiry) {
    FabricSoftRef ref = impl.$ref;

    // Optimization: if the impl lives on the local store, it will never be
    // evicted, so no need to store the entry in the read map.
    if (ref.store.isLocalStore()) return new ReadMapEntry(impl, expiry);

    while (true) {
      ReadMapEntry result;
      synchronized (readMap) {
        result = readMap.get(ref.store, ref.onum);
        if (result == null) {
          result = new ReadMapEntry(impl, expiry);
          readMap.put(ref.store, ref.onum, result);
          return result;
        }
      }

      synchronized (result) {
        synchronized (readMap) {
          // Make sure we still have the right entry.
          if (result != readMap.get(ref.store, ref.onum)) continue;

          result.obj = impl.$ref;
          result.pinCount++;
          result.promise = result.promise > expiry ? result.promise : expiry;
          int ver = impl.$getVersion();
          if (ver == result.versionNumber) return result;

          // Version numbers don't match. Retry all other transactions.
          // XXX What if we just read in an older copy of the object?
          for (Log reader : result.readLocks) {
            reader.flagRetry();
          }

          result.versionNumber = ver;
          return result;
        }
      }
    }
  }

  private static final Map<Thread, TransactionManager> instanceMap =
      new WeakHashMap<Thread, TransactionManager>();

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
    abortTransaction(Collections.<RemoteNode> emptySet());
  }

  /**
   * @param abortedNodes
   *          a set of nodes that don't need to be contacted because they
   *          already know about the abort.
   */
  private void abortTransaction(Set<RemoteNode> abortedNodes) {
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

    WORKER_TRANSACTION_LOGGER.warning(current + " aborting");
    // Assume only one thread will be executing this.

    // Set the retry flag in all our children.
    current.flagRetry();

    // Wait for all other threads to finish.
    current.waitForThreads();

    sendAbortMessages(storesToContact, workersToContact, abortedNodes);
    current.abort();
    WORKER_TRANSACTION_LOGGER.warning(current + " aborted");

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
      commitTransactionAt(System.currentTimeMillis());
    } finally {
      Timing.COMMIT.end();
    }
  }

  /**
   * @throws TransactionRestartingException
   *           if the prepare fails.
   */
  public void commitTransactionAt(long commitTime) {
    commitTransactionAt(commitTime, false);
  }

  /**
   * @throws TransactionRestartingException
   *           if the prepare fails.
   */
  public void commitTransactionAt(long commitTime, boolean ignoreRetrySignal) {
    WORKER_TRANSACTION_LOGGER.log(Level.FINEST, "{0} attempting to commit",
        current);
    // Assume only one thread will be executing this.

    // XXX This is a long and ugly method. Refactor?

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

    // Remove and unlock reads that have valid promises on them
    current.removePromisedReads(commitTime);

    // Go through the transaction log and figure out the stores we need to
    // contact.
    Set<Store> stores = current.storesToContact();
    List<RemoteWorker> workers = current.workersCalled;

    if (Options.DEBUG_ENABLE_PROMISES) {
      // Send begin messages to our cohorts
      sendBeginMessages(commitTime, stores, workers);
    }

    // Send prepare messages to our cohorts. This will also abort our portion of
    // the transaction if the prepare fails.
    sendPrepareMessages(commitTime, stores, workers);

    // Send commit messages to our cohorts.
    sendCommitMessagesAndCleanUp(stores, workers);
  }

  /**
   * Sends begin messages to the cohorts.
   * 
   * TODO Implement this to send begin messages at the start of a transaction.
   * @param commitTime
   *
   * @param stores
   * @param workers
   * @return
   */
  private Map<RemoteNode, TransactionBeginFailedException> sendBeginMessages(
      final long commitTime, Set<Store> stores, List<RemoteWorker> workers) {
    final Map<RemoteNode, TransactionBeginFailedException> failures =
        Collections
        .synchronizedMap(new HashMap<RemoteNode, TransactionBeginFailedException>());

    Logging.log(WORKER_TRANSACTION_LOGGER, Level.SEVERE,
        "TODO: Implement fabric.worker.transaction.TansactionManager.sendBeginMessages(...) from {0}.",
        current.tid.topTid);

    List<Thread> threads = new ArrayList<Thread>();

    final Worker worker = Worker.getWorker();
    for (final Store store : stores) {
      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          try {
            LongKeyMap<Integer> reads =
                Options.DEBUG_COMMIT_READS ? current.getReadsForStore(store,
                    false) : new LongKeyHashMap<Integer>();
                Collection<_Impl> writes = current.getWritesForStore(store);

                boolean transactionBegun =
                    store.beginTransaction(current.tid.topTid, commitTime, writes,
                        reads, writes);

                if (transactionBegun) {
                  RemoteWorker storeWorker = worker.getWorker(store.name());
                  synchronized (current.workersCalled) {
                    current.workersCalled.add(storeWorker);
                  }
                }
          } catch (TransactionBeginFailedException e) {
            failures.put((RemoteNode) store, e);
          } catch (UnreachableNodeException e) {
            failures.put((RemoteNode) store,
                new TransactionBeginFailedException("Unreachable store"));
          }
        }
      };
      Thread thread = new Thread(runnable, "worker begin to " + store.name());
      threads.add(thread);
      thread.start();
    }

    // Wait for replies.
    for (Thread thread : threads) {
      while (true) {
        try {
          thread.join();
          break;
        } catch (InterruptedException e) {
          // Do nothing
        }
      }
    }

    // Check for conflicts and unreachable stores/workers.
    if (!failures.isEmpty()) {
      String logMessage =
          "Transaction tid=" + current.tid.topTid + ":  begin failed.";
      WORKER_TRANSACTION_LOGGER.fine(logMessage);
    }

    return failures;
  }

  /**
   * Sends prepare messages to the cohorts. Also sends abort messages if any
   * cohort fails to prepare.
   * 
   * @throws TransactionRestartingException
   *           if the prepare fails.
   */
  public void sendPrepareMessages(long commitTime) {
    sendPrepareMessages(commitTime, current.storesToContact(),
        current.workersCalled);
  }

  /**
   * Sends prepare messages to the given set of stores and workers. If the
   * prepare fails, the local portion and given branch of the transaction is
   * rolled back.
   * 
   * @throws TransactionRestartingException
   *           if the prepare fails.
   */
  private void sendPrepareMessages(final long commitTime, Set<Store> stores,
      List<RemoteWorker> workers) {
    final Map<RemoteNode, TransactionPrepareFailedException> failures =
        Collections
            .synchronizedMap(new HashMap<RemoteNode, TransactionPrepareFailedException>());

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

    List<Thread> threads =
        new ArrayList<Thread>(stores.size() + workers.size());

    // Go through each worker and send prepare messages in parallel.
    for (final RemoteWorker worker : workers) {
      Thread thread = new Thread("worker prepare to " + worker.name()) {
        @Override
        public void run() {
          try {
            worker.prepareTransaction(current.tid.topTid, commitTime);
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
      thread.start();
      threads.add(thread);

    }

    // Go through each store and send prepare messages in parallel.
    final Worker worker = Worker.getWorker();
    for (Iterator<Store> storeIt = stores.iterator(); storeIt.hasNext();) {
      final Store store = storeIt.next();
      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          try {
            Collection<_Impl> creates = current.getCreatesForStore(store);
            LongKeyMap<Integer> reads =
                Options.DEBUG_COMMIT_READS ? current.getReadsForStore(store,
                    false) : new LongKeyHashMap<Integer>();
                Collection<_Impl> writes = current.getWritesForStore(store);

                if (WORKER_TRANSACTION_LOGGER.isLoggable(Level.FINE)) {
                  Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINE, "Preparing "
                      + "transaction {0} to {1}: {2} created, {3} read, "
                      + "{4} modified", current.tid.topTid, store, creates.size(),
                      reads.size(), writes.size());
                }

                boolean subTransactionCreated =
                    store.prepareTransaction(current.tid.topTid, commitTime,
                        creates, reads, writes);

                if (subTransactionCreated) {
                  RemoteWorker storeWorker = worker.getWorker(store.name());
                  synchronized (current.workersCalled) {
                    current.workersCalled.add(storeWorker);
                  }
                }
          } catch (TransactionPrepareFailedException e) {
            failures.put((RemoteNode) store, e);
          } catch (UnreachableNodeException e) {
            failures.put((RemoteNode) store,
                new TransactionPrepareFailedException("Unreachable store"));
          }
        }
      };

      // Optimization: only start in a new thread if there are more stores to
      // contact and if it's a truly remote store (i.e., not in-process).
      if (!(store instanceof InProcessStore || store.isLocalStore())
          && storeIt.hasNext()) {
        Thread thread =
            new Thread(runnable, "worker prepare to " + store.name());
        threads.add(thread);
        thread.start();
      } else {
        runnable.run();
      }
    }

    // Wait for replies.
    for (Thread thread : threads) {
      while (true) {
        try {
          thread.join();
          break;
        } catch (InterruptedException e) {
        }
      }
    }

    // Check for conflicts and unreachable stores/workers.
    if (!failures.isEmpty()) {
      String logMessage =
          "Transaction tid=" + current.tid.topTid + ":  prepare failed.";

      for (Map.Entry<RemoteNode, TransactionPrepareFailedException> entry : failures
          .entrySet()) {
        if (entry.getKey() instanceof RemoteStore) {
          // Remove old objects from our cache.
          RemoteStore store = (RemoteStore) entry.getKey();
          LongKeyMap<SerializedObject> versionConflicts =
              entry.getValue().versionConflicts;
          if (versionConflicts != null) {
            for (SerializedObject obj : versionConflicts.values())
              store.updateCache(obj);
          }
        }

        if (WORKER_TRANSACTION_LOGGER.isLoggable(Level.FINE)) {
          logMessage +=
              "\n\t" + entry.getKey() + ": " + entry.getValue().getMessage();
        }
      }
      WORKER_TRANSACTION_LOGGER.fine(logMessage);

      synchronized (current.commitState) {
        current.commitState.value = PREPARE_FAILED;
        current.commitState.notifyAll();
      }

      TransactionID tid = current.tid;

      TransactionPrepareFailedException e =
          new TransactionPrepareFailedException(failures);
      Logging.log(WORKER_TRANSACTION_LOGGER, Level.WARNING,
          "{0} error committing: prepare failed exception: {1}", current, e);

      abortTransaction(failures.keySet());
      throw new TransactionRestartingException(tid);

    } else {
      synchronized (current.commitState) {
        current.commitState.value = PREPARED;
        current.commitState.notifyAll();
      }
    }
  }

  /**
   * Sends commit messages to the cohorts.
   */
  public void sendCommitMessagesAndCleanUp()
      throws TransactionAtomicityViolationException {
    sendCommitMessagesAndCleanUp(current.storesToContact(),
        current.workersCalled);
  }

  /**
   * Sends commit messages to the given set of stores and workers.
   */
  private void sendCommitMessagesAndCleanUp(Set<Store> stores,
      List<RemoteWorker> workers) throws TransactionAtomicityViolationException {
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

    final List<RemoteNode> unreachable =
        Collections.synchronizedList(new ArrayList<RemoteNode>());
    final List<RemoteNode> failed =
        Collections.synchronizedList(new ArrayList<RemoteNode>());
    List<Thread> threads =
        new ArrayList<Thread>(stores.size() + workers.size());

    // Send commit messages to the workers in parallel.
    for (final RemoteWorker worker : workers) {
      Thread thread = new Thread("worker commit to " + worker) {
        @Override
        public void run() {
          try {
            worker.commitTransaction(current.tid.topTid);
          } catch (UnreachableNodeException e) {
            unreachable.add(worker);
          } catch (TransactionCommitFailedException e) {
            failed.add(worker);
          }
        }
      };
      thread.start();
      threads.add(thread);
    }

    // Send commit messages to the stores in parallel.
    for (Iterator<Store> storeIt = stores.iterator(); storeIt.hasNext();) {
      final Store store = storeIt.next();
      Runnable runnable = new Runnable() {
        @Override
        public void run() {
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
        Thread thread =
            new Thread(runnable, "worker commit to " + store.name());
        threads.add(thread);
        thread.start();
      } else {
        runnable.run();
      }
    }

    // Wait for replies.
    for (Thread thread : threads) {
      while (true) {
        try {
          thread.join();
          break;
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    if (!(unreachable.isEmpty() && failed.isEmpty())) {
      Logging
          .log(WORKER_TRANSACTION_LOGGER, Level.SEVERE,
              "{0} error committing: atomicity violation "
                  + "-- failed: {1} unreachable: {2}", current, failed,
              unreachable);
      throw new TransactionAtomicityViolationException(failed, unreachable);
    }

    // Update data structures to reflect successful commit.
    WORKER_TRANSACTION_LOGGER.log(Level.FINEST,
        "{0} committed at stores...updating data structures", current);
    current.commitTopLevel();
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
      Set<RemoteNode> fails) {
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
      current.writerMap.put(obj.$getProxy(), obj.get$$updateLabel());
    } finally {
      Timing.TXLOG.end();
    }
  }

  public void registerRead(_Impl obj) {
    synchronized (obj) {
      if (obj.$reader == current
          && obj.writerMapVersion == current.writerMap.version) return;

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
    while (obj.$writeLockHolder != null
        && !current.isDescendantOf(obj.$writeLockHolder)) {
      try {
        Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINEST, current
            + "{0} wants to read {1}/" + obj.$getOnum()
            + " ({2}); waiting on writer {3}", current, obj.$getStore(),
            obj.getClass(), obj.$writeLockHolder);
        hadToWait = true;
        obj.$numWaiting++;
        obj.wait();
      } catch (InterruptedException e) {
      }
      obj.$numWaiting--;

      // Make sure we weren't aborted/retried while we were waiting.
      checkRetrySignal();
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
      } finally {
        Timing.TXLOG.end();
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
    while (true) {
      // Make sure writer is in our ancestry.
      if (obj.$writeLockHolder != null
          && !current.isDescendantOf(obj.$writeLockHolder)) {
        Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINEST,
            "{0} wants to write {1}/" + obj.$getOnum()
                + " ({2}); waiting on writer {3}", current, obj.$getStore(),
            obj.getClass(), obj.$writeLockHolder);
        hadToWait = true;
      } else {
        // Restart any incompatible readers.
        ReadMapEntry readMapEntry = obj.$readMapEntry;
        if (readMapEntry != null) {
          synchronized (readMapEntry) {
            boolean allReadersInAncestry = true;
            for (Log lock : readMapEntry.readLocks) {
              if (!current.isDescendantOf(lock)) {
                Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINEST,
                    "{0} wants to write {1}/" + obj.$getOnum()
                        + " ({2}); aborting reader {3}", current,
                    obj.$getStore(), obj.getClass(), lock);
                lock.flagRetry();
                allReadersInAncestry = false;
              }
            }

            if (allReadersInAncestry) break;
          }
        }
      }

      try {
        obj.$numWaiting++;
        obj.wait();
      } catch (InterruptedException e) {
      }
      obj.$numWaiting--;

      // Make sure we weren't aborted/retried while we were waiting.
      checkRetrySignal();
    }

    // Set the write stamp.
    obj.$writer = current;

    if (hadToWait)
      WORKER_TRANSACTION_LOGGER
          .log(Level.FINEST, "{0} got write lock", current);

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

    // Check the writer map to see if another worker currently owns the object.
    RemoteWorker owner = current.writerMap.getWriter(obj.$getProxy());
    if (owner != null)
      owner.takeOwnership(current.tid, obj.$getStore(), obj.$getOnum());

    // We now own the object.
    obj.$isOwned = true;
    current.writerMap.put(obj.$getProxy(), Worker.getWorker().getLocalWorker());

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

    // Check the writer map.
    RemoteWorker owner = current.writerMap.getWriter(obj.$getProxy());
    if (owner == null || owner == Worker.getWorker().getLocalWorker()) return;

    // Need to fetch from the owner.
    ensureWriteLock(obj);
    owner.readObject(current.tid, obj);
  }

  /**
   * Checks whether any of the objects used by a transaction are stale.
   * 
   * @return true iff stale objects were found
   */
  public boolean checkForStaleObjects() {
    Set<Store> stores = current.storesToCheckFreshness();
    int numNodesToContact = stores.size() + current.workersCalled.size();
    final List<RemoteNode> nodesWithStaleObjects =
        Collections.synchronizedList(new ArrayList<RemoteNode>(
            numNodesToContact));
    List<Thread> threads = new ArrayList<Thread>(numNodesToContact);

    // Go through each worker and send check messages in parallel.
    for (final RemoteWorker worker : current.workersCalled) {
      Thread thread = new Thread("worker freshness check to " + worker.name()) {
        @Override
        public void run() {
          try {
            if (worker.checkForStaleObjects(current.tid))
              nodesWithStaleObjects.add(worker);
          } catch (UnreachableNodeException e) {
            // Conservatively assume it had stale objects.
            nodesWithStaleObjects.add(worker);
          }
        }
      };
      thread.start();
      threads.add(thread);
    }

    // Go through each store and send check messages in parallel.
    for (Iterator<Store> storeIt = stores.iterator(); storeIt.hasNext();) {
      final Store store = storeIt.next();
      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          LongKeyMap<Integer> reads = current.getReadsForStore(store, true);
          if (store.checkForStaleObjects(reads))
            nodesWithStaleObjects.add((RemoteNode) store);
        }
      };

      // Optimization: only start a new thread if there are more stores to
      // contact and if it's truly a remote store (i.e., not in-process).
      if (!(store instanceof InProcessStore || store.isLocalStore())
          && storeIt.hasNext()) {
        Thread thread =
            new Thread(runnable, "worker freshness check to " + store.name());
        threads.add(thread);
        thread.start();
      } else {
        runnable.run();
      }
    }

    // Wait for replies.
    for (Thread thread : threads) {
      while (true) {
        try {
          thread.join();
          break;
        } catch (InterruptedException e) {
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

  private void startTransaction(TransactionID tid, boolean ignoreRetrySignal) {
    if (current != null && !ignoreRetrySignal) checkRetrySignal();

    try {
      Timing.BEGIN.begin();
      current = new Log(current, tid);
      Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINEST,
          "{0} started subtx {1} in thread {2}", current.parent, current,
          Thread.currentThread());
    } finally {
      Timing.BEGIN.end();
    }
  }

  /**
   * Starts the given thread, registering it as necessary.
   */
  public static void startThread(Thread thread) {
    if (!(thread instanceof FabricThread))
      getInstance().registerThread(thread);

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
    if (current == null)
      throw new InternalError(
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
      commitTransactionAt(System.currentTimeMillis());

    // Start new transactions if necessary.
    if (commonAncestor.depth != tid.depth) startTransaction(tid, true);
  }

}
