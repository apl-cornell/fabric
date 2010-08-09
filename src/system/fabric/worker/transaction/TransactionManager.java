package fabric.worker.transaction;

import static fabric.common.Logging.WORKER_TRANSACTION_LOGGER;
import static fabric.worker.transaction.Log.CommitState.Values.*;

import java.util.*;
import java.util.logging.Level;

import fabric.common.FabricThread;
import fabric.common.Logging;
import fabric.common.SerializedObject;
import fabric.common.TransactionID;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.security.Label;
import fabric.lang.security.SecurityCache;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;
import fabric.store.InProcessStore;
import fabric.worker.*;
import fabric.worker.debug.Timing;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.UpdateMap;

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
   * A map from OIDs to a version number and a list of logs for transactions
   * that have read that version of the object. For each transaction tx, an
   * object o is in tx.reads exactly when tx is in readList[o]. A transaction
   * has acquired a read lock if its log is in this list. All entries in this
   * list have non-empty <code>readLocks</code> sets.
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

  public void abortTransaction() {
    abortTransaction(true);
  }

  /**
   * @param recurseToCohorts
   *          true iff should send abort messages to stores and workers.
   */
  private void abortTransaction(boolean recurseToCohorts) {
    if (current.tid.depth == 0) {
      // Make sure no other thread is working on this transaction.
      synchronized (current.commitState) {
        while (current.commitState.value == PREPARING) {
          try {
            current.commitState.wait();
          } catch (InterruptedException e) {
          }
        }

        switch (current.commitState.value) {
        case UNPREPARED:
        case PREPARE_FAILED:
        case PREPARED:
          current.commitState.value = ABORTING;
          break;

        case PREPARING:
          // We should've taken care of this case already.
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
        }
      }
    }

    WORKER_TRANSACTION_LOGGER.warning(current + " aborting");
    // Assume only one thread will be executing this.

    // Set the retry flag in all our children.
    current.flagRetry();

    // Wait for all other threads to finish.
    current.waitForThreads();

    if (recurseToCohorts) sendAbortMessages();
    current.abort();
    WORKER_TRANSACTION_LOGGER.warning(current + " aborted");

    synchronized (current.commitState) {
      current.commitState.value = ABORTED;

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
    commitTransaction(true);
  }

  /**
   * Commits the transaction if possible; otherwise, aborts the transaction.
   * 
   * @param useAuthentication
   *          whether to use an authenticated channel to talk to the store
   * @throws AbortException
   *           if the transaction was aborted.
   * @throws TransactionRestartingException
   *           if the transaction was aborted and needs to be retried.
   */
  public void commitTransaction(boolean useAuthentication)
      throws AbortException, TransactionRestartingException,
      TransactionAtomicityViolationException {
    Timing.COMMIT.begin();
    try {
      commitTransactionAt(System.currentTimeMillis(), useAuthentication, false);
    } finally {
      Timing.COMMIT.end();
    }
  }

  public void commitTransactionAt(long commitTime) throws AbortException,
      TransactionRestartingException {
    commitTransactionAt(commitTime, true, false);
  }

  /**
   * Commits the transaction if possible; otherwise, aborts the transaction.
   * 
   * @param useAuthentication
   *          whether to use an authenticated channel to talk to the store
   * @param ignoreRetrySignal
   *          whether to ignore the retry signal
   * @throws AbortException
   *           if the transaction was aborted.
   * @throws TransactionRestartingException
   *           if the transaction was aborted and needs to be retried.
   */
  private void commitTransactionAt(long commitTime, boolean useAuthentication,
      boolean ignoreRetrySignal) throws AbortException,
      TransactionRestartingException {
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

    // Send prepare messages to our cohorts. This will also abort our cohorts if
    // the prepare fails.
    Map<RemoteNode, TransactionPrepareFailedException> failures =
        sendPrepareMessages(useAuthentication, commitTime, stores, workers);

    if (!failures.isEmpty()) {
      failures.remove(null);
      TransactionPrepareFailedException e =
          new TransactionPrepareFailedException(failures);
      Logging.log(WORKER_TRANSACTION_LOGGER, Level.WARNING,
          "{0} error committing: prepare failed exception: {1}", current, e);
      TransactionID tid = current.tid;
      abortTransaction(false);
      throw new TransactionRestartingException(tid);
    }

    // Send commit messages to our cohorts.
    sendCommitMessagesAndCleanUp(useAuthentication, stores, workers);
  }

  /**
   * Sends prepare messages to the cohorts. Also sends abort messages if any
   * cohort fails to prepare.
   */
  public Map<RemoteNode, TransactionPrepareFailedException> sendPrepareMessages(
      long commitTime) {
    return sendPrepareMessages(true, commitTime, current.storesToContact(),
        current.workersCalled);
  }

  /**
   * Sends prepare messages to the given set of stores and workers. Also sends
   * abort messages if any of them fails to prepare.
   */
  private Map<RemoteNode, TransactionPrepareFailedException> sendPrepareMessages(
      final boolean useAuthentication, final long commitTime,
      Set<Store> stores, List<RemoteWorker> workers) {
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
        return failures;
      case COMMITTING:
      case COMMITTED:
        WORKER_TRANSACTION_LOGGER.log(Level.FINE,
            "Ignoring prepare request (transaction state = {0})",
            current.commitState.value);
        return failures;
      case PREPARE_FAILED:
      case ABORTING:
      case ABORTED:
        // XXX HACK UGLY
        failures.put(null, null);
        return failures;
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
        public void run() {
          try {
            Collection<_Impl> creates = current.getCreatesForStore(store);
            LongKeyMap<Integer> reads = current.getReadsForStore(store, false);
            Collection<_Impl> writes = current.getWritesForStore(store);
            boolean subTransactionCreated =
                store.prepareTransaction(useAuthentication, current.tid.topTid,
                    commitTime, creates, reads, writes);

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

      sendAbortMessages(useAuthentication, stores, workers, failures.keySet());
    }

    synchronized (current.commitState) {
      current.commitState.value =
          failures.isEmpty() ? PREPARED : PREPARE_FAILED;
      current.commitState.notifyAll();
    }

    return failures;
  }

  /**
   * Sends commit messages to the cohorts.
   */
  public void sendCommitMessagesAndCleanUp()
      throws TransactionAtomicityViolationException {
    sendCommitMessagesAndCleanUp(true, current.storesToContact(),
        current.workersCalled);
  }

  /**
   * Sends commit messages to the given set of stores and workers.
   */
  private void sendCommitMessagesAndCleanUp(final boolean useAuthentication,
      Set<Store> stores, List<RemoteWorker> workers)
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
        public void run() {
          try {
            store.commitTransaction(useAuthentication, current.tid.topTid);
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
   * Sends abort messages to all other nodes that were contacted during the
   * transaction.
   */
  @SuppressWarnings("unchecked")
  private void sendAbortMessages() {
    sendAbortMessages(true, current.storesToContact(), current.workersCalled,
        Collections.EMPTY_SET);
  }

  /**
   * Sends abort messages to those nodes that haven't reported failures.
   * 
   * @param useAuthentication
   *          whether to authenticate to the stores.
   * @param stores
   *          the set of stores involved in the transaction.
   * @param workers
   *          the set of workers involved in the transaction.
   * @param fails
   *          the set of nodes that have reported failure.
   */
  private void sendAbortMessages(boolean useAuthentication, Set<Store> stores,
      List<RemoteWorker> workers, Set<RemoteNode> fails) {
    for (Store store : stores)
      if (!fails.contains(store))
        store.abortTransaction(useAuthentication, current.tid);

    for (RemoteWorker worker : workers)
      if (!fails.contains(worker)) worker.abortTransaction(current.tid);
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

      // Own the object. The call to ensureOwnership is responsible for adding
      // the object to the set of created objects.
      ensureOwnership(obj);
      current.updateMap.put(obj.$getProxy(), obj.get$label());
    } finally {
      Timing.TXLOG.end();
    }
  }

  public void registerRead(_Impl obj) {
    synchronized (obj) {
      if (obj.$reader == current
          && obj.$updateMapVersion == current.updateMap.version) return;

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
    obj.$updateMapVersion = -1;

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
          && obj.$updateMapVersion == current.updateMap.version && obj.$isOwned)
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

    // Check the update map to see if another worker currently owns the object.
    RemoteWorker owner = current.updateMap.getUpdate(obj.$getProxy());
    if (owner != null)
      owner.takeOwnership(current.tid, obj.$getStore(), obj.$getOnum());

    // We now own the object.
    obj.$isOwned = true;
    current.updateMap.put(obj.$getProxy(), Worker.getWorker().getLocalWorker());

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
   * Checks the update map and fetches from the object's owner as necessary.
   * This method assumes we are synchronized on the object.
   */
  private void ensureObjectUpToDate(_Impl obj) {
    // Check the object's update-map version stamp.
    if (obj.$updateMapVersion == current.updateMap.version) return;

    // Set the update-map version stamp on the object.
    obj.$updateMapVersion = current.updateMap.version;

    // Check the update map.
    RemoteWorker owner = current.updateMap.getUpdate(obj.$getProxy());
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

  public UpdateMap getUpdateMap() {
    if (current == null) return null;
    return current.updateMap;
  }

  /**
   * @return the worker on which the object resides. An object resides on a
   *         worker if it is either on that worker's local store, or if it was
   *         created by the current transaction and is owned by that worker.
   */
  public RemoteWorker getFetchWorker(_Proxy proxy) {
    if (current == null || !current.updateMap.containsCreate(proxy))
      return null;
    Label label = current.updateMap.getCreate(proxy);

    return current.updateMap.getUpdate(proxy, label);
  }

  public SecurityCache getSecurityCache() {
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
      commitTransactionAt(System.currentTimeMillis(), true, true);

    // Start new transactions if necessary.
    if (commonAncestor.depth != tid.depth) startTransaction(tid, true);
  }

}
