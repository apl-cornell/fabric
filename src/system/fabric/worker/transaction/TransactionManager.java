package fabric.worker.transaction;

import static fabric.common.Logging.HOTOS_LOGGER;
import static fabric.common.Logging.WORKER_TRANSACTION_LOGGER;
import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import fabric.common.FabricThread;
import fabric.common.Logging;
import fabric.common.SemanticWarranty;
import fabric.common.SerializedObject;
import fabric.common.Timing;
import fabric.common.TransactionID;
import fabric.common.VersionWarranty;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.lang.WrappedJavaInlineable;
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
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.TransactionRestartingException;
import fabric.worker.Worker;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.memoize.WarrantiedCallResult;
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

  public static ReadMapEntry getReadMapEntry(_Impl impl,
      VersionWarranty warranty) {
    FabricSoftRef ref = impl.$ref;

    // Optimization: if the impl lives on the local store, it will never be
    // evicted, so no need to store the entry in the read map.
    if (ref.store.isLocalStore()) return new ReadMapEntry(impl, warranty);

    while (true) {
      ReadMapEntry result;
      synchronized (readMap) {
        result = readMap.get(ref.store, ref.onum);
        if (result == null) {
          result = new ReadMapEntry(impl, warranty);
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
          result.warranty =
              result.warranty.expiresAfter(warranty) ? result.warranty
                  : warranty;
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
        }
      }
    }

    WORKER_TRANSACTION_LOGGER.warning(current + " aborting");

    HOTOS_LOGGER.log(Level.INFO, "aborting {0}", current);

    // Assume only one thread will be executing this.

    // Set the retry flag in all our children.
    current.flagRetry();

    // Wait for all other threads to finish.
    current.waitForThreads();

    sendAbortMessages(abortedNodes);
    current.abort();
    WORKER_TRANSACTION_LOGGER.warning(current + " aborted");
    HOTOS_LOGGER.log(Level.INFO, "aborted {0}", current);

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
  private void commitTransaction(boolean ignoreRetrySignal) {
    WORKER_TRANSACTION_LOGGER.log(Level.FINEST, "{0} attempting to commit",
        current);
    HOTOS_LOGGER.log(Level.INFO, "preparing {0}", current);

    // Assume only one thread will be executing this.

    // XXX This is a long and ugly method. Re-factor?

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
    Log HOTOS_current = current;
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

    // Create top level SemanticWarrantyRequest, if any.
    current.createCurrentRequest();

    // Collect logs from nested semantic warranty requests
    for (Entry<CallInstance, SemanticWarrantyRequest> entry : current.requests
        .entrySet()) {
      SemanticWarrantyRequest req = entry.getValue();
      // reads
      for (Entry<Store, LongKeyMap<ReadMapEntry>> readEntry : req.reads.nonNullEntrySet()) {
        for (ReadMapEntry read : readEntry.getValue().values()) {
          current.reads.put(read.obj.store, read.obj.onum, read);
          //SEMANTIC_WARRANTY_LOGGER.finest("Gathering read " + read.obj.onum + " from " + req.call + " " + req.id);
        }
      }

      // creates
      for (Entry<Store, LongKeyMap<_Impl>> createEntry : req.creates.nonNullEntrySet()) {
        for (_Impl create : createEntry.getValue().values()) {
          current.creates.add(create);
          //SEMANTIC_WARRANTY_LOGGER.finest("Gathering create " + create.$getOnum() + " from " + req.call + " " + req.id);
        }
      }

      current.semanticWarrantiesUsed.putAll(req.calls);
    }

    SEMANTIC_WARRANTY_LOGGER.finest("Creates:");
    for (_Impl create : current.creates)
      SEMANTIC_WARRANTY_LOGGER.finest("\tonum " + create.$getOnum());

    // Send prepare-write messages to our cohorts. If the prepare fails, this
    // will abort our portion of the transaction and throw a
    // TransactionRestartingException.
    long commitTime = sendPrepareWriteMessages();

    // Send prepare-read messages to our cohorts. If the prepare fails, this
    // will abort our portion of the transaction and throw a
    // TransactionRestartingException.
    sendPrepareReadMessages(commitTime);

    // Send commit messages to our cohorts.
    sendCommitMessagesAndCleanUp(commitTime);

    HOTOS_LOGGER.log(Level.INFO, "committed {0}", HOTOS_current);
  }

  /**
   * Sends prepare-write messages to the cohorts. If any cohort fails to
   * prepare, abort messages will be sent, and the local portion of the
   * transaction is rolled back.
   * 
   * @return a proposed commit time, based on the outstanding warranties for
   *           objects modified by the transaction. The returned commit time is
   *           guaranteed to be no earlier than the time at which this method is
   *           called.
   * @throws TransactionRestartingException
   *           if the prepare fails.
   */
  public long sendPrepareWriteMessages() {
    final AtomicBoolean readOnly = new AtomicBoolean(true);

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
        return current.commitState.commitTime;

      case COMMITTING:
      case COMMITTED:
      case PREPARE_FAILED:
        throw new InternalError(
            "Got a prepare-write request, but transaction state is "
                + current.commitState.value);

      case ABORTING:
      case ABORTED:
        throw new TransactionRestartingException(current.tid);
      }
    }

    List<Thread> threads = new ArrayList<Thread>();

    // A ref cell containing the max commit time. Using a ref cell so we can
    // synchronize on it.
    final long[] commitTime = new long[1];
    commitTime[0] = System.currentTimeMillis();

    // Go through each worker and send prepare messages in parallel.
    for (final RemoteWorker worker : current.workersCalled) {
      Thread thread = new Thread("worker write-prepare to " + worker.name()) {
        @Override
        public void run() {
          try {
            long response = worker.prepareTransactionWrites(current.tid.topTid);
            synchronized (commitTime) {
              if (response > commitTime[0]) commitTime[0] = response;
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
      thread.start();
      threads.add(thread);

    }

    // Go through each store and send prepare messages in parallel.
    Set<Store> storesWritten = current.storesWritten();

    current.commitState.storesContacted.addAll(storesWritten);
    for (Iterator<Store> storeIt = storesWritten.iterator(); storeIt.hasNext();) {
      final Store store = storeIt.next();
      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          try {
            Collection<_Impl> creates = current.getCreatesForStore(store);
            Collection<_Impl> writes = current.getWritesForStore(store);

            if (WORKER_TRANSACTION_LOGGER.isLoggable(Level.FINE)) {
              Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINE, "Preparing "
                  + "writes for transaction {0} to {1}: {2} created, "
                  + "{3} modified", current.tid.topTid, store, creates.size(),
                  writes.size());
            }

            if (!store.isLocalStore() && creates.size() + writes.size() > 0)
              readOnly.set(false);

            long response =
                store.prepareTransactionWrites(current.tid.topTid, creates,
                    writes);

            synchronized (commitTime) {
              if (response > commitTime[0]) commitTime[0] = response;
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
            new Thread(runnable, "worker write-prepare to " + store.name());
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

    HOTOS_LOGGER.info("Transaction is "
        + (readOnly.get() ? "read-only" : "read/write"));

    // Check for conflicts and unreachable stores/workers.
    if (!failures.isEmpty()) {
      String logMessage =
          "Transaction tid=" + current.tid.topTid + ":  write-prepare failed.";

      for (Map.Entry<RemoteNode, TransactionPrepareFailedException> entry : failures
          .entrySet()) {
        if (entry.getKey() instanceof RemoteStore) {
          // Remove old objects from our cache.
          RemoteStore store = (RemoteStore) entry.getKey();
          LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts =
              entry.getValue().versionConflicts;
          if (versionConflicts != null) {
            for (Pair<SerializedObject, VersionWarranty> obj : versionConflicts
                .values())
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
        return commitTime[0];
      }
    }
  }

  /**
   * Sends prepare-read messages to the cohorts. If any cohort fails to
   * prepare, abort messages will be sent, and the local portion of the
   * transaction is rolled back.
   * 
   * @throws TransactionRestartingException
   *           if the prepare fails.
   */
  public void sendPrepareReadMessages(final long commitTime) {
    final Map<RemoteNode, TransactionPrepareFailedException> failures =
        Collections
            .synchronizedMap(new HashMap<RemoteNode, TransactionPrepareFailedException>());

    synchronized (current.commitState) {
      while (current.commitState.value == PREPARING) {
        if (current.commitState.commitTime >= commitTime) return;

        try {
          current.commitState.wait();
        } catch (InterruptedException e) {
        }
      }

      switch (current.commitState.value) {
      case UNPREPARED:
        current.commitState.value = PREPARING;
        break;

      case PREPARING:
        // We should've taken care of this case already in the 'while' loop
        // above.
        throw new InternalError();

      case PREPARED:
        if (current.commitState.commitTime >= commitTime) return;
        current.commitState.value = PREPARING;
        break;

      case COMMITTING:
      case COMMITTED:
      case PREPARE_FAILED:
        throw new InternalError(
            "Got a prepare-read request, but transaction state is "
                + current.commitState.value);

      case ABORTING:
      case ABORTED:
        throw new TransactionRestartingException(current.tid);
      }
    }

    List<Thread> threads = new ArrayList<Thread>();

    // Go through each worker and send prepare messages in parallel.
    for (final RemoteWorker worker : current.workersCalled) {
      Thread thread = new Thread("worker read-prepare to " + worker.name()) {
        @Override
        public void run() {
          try {
            worker.prepareTransactionReads(current.tid.topTid, commitTime);
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
    Map<Store, LongKeyMap<Integer>> storesRead = current.storesRead(commitTime);
    Map<Store, Set<CallInstance>> storesCalled =
        current.storesCalled(commitTime);

    Set<Store> storesToContact = new HashSet<Store>(storesCalled.keySet());
    storesToContact.addAll(storesRead.keySet());

    current.commitState.commitTime = commitTime;
    int count = 0;
    for (Store store : storesToContact) {
      count++;
      LongKeyMap<Integer> tmpReads = storesRead.get(store);

      final Store s = store;
      final LongKeyMap<Integer> reads =
          tmpReads != null ? tmpReads : new LongKeyHashMap<Integer>();
      final Map<CallInstance, WarrantiedCallResult> calls = current.getCallsForStore(store);
      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          try {
            if (WORKER_TRANSACTION_LOGGER.isLoggable(Level.FINE)) {
              Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINE, "Preparing "
                  + "reads for transaction {0} to {1}: {2} version warranties "
                  + "will expire", current.tid.topTid, s, reads.size());
            }

            Pair<LongKeyMap<VersionWarranty>, Map<CallInstance,
              SemanticWarranty>> allNewWarranties =
                s.prepareTransactionReads(current.tid.topTid, reads, calls,
                    commitTime);

            // Prepare was successful. Update the objects' warranties.
            current.updateVersionWarranties(s, allNewWarranties.first);
            // TODO Update warranties on calls.
          } catch (TransactionPrepareFailedException e) {
            failures.put((RemoteNode) s, e);
          } catch (UnreachableNodeException e) {
            failures.put((RemoteNode) s, new TransactionPrepareFailedException(
                "Unreachable s"));
          }
        }
      };

      // Optimization: only start in a new thread if there are more ss to
      // contact and if it's a truly remote s (i.e., not in-process).
      if (!(s instanceof InProcessStore || s.isLocalStore())
          && count == storesToContact.size()) {
        Thread thread =
            new Thread(runnable, "worker read-prepare to " + s.name());
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
          "Transaction tid=" + current.tid.topTid + ":  read-prepare failed.";

      for (Map.Entry<RemoteNode, TransactionPrepareFailedException> entry : failures
          .entrySet()) {
        if (entry.getKey() instanceof RemoteStore) {
          // Remove old objects from our cache.
          RemoteStore store = (RemoteStore) entry.getKey();
          LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts =
              entry.getValue().versionConflicts;
          if (versionConflicts != null) {
            for (Pair<SerializedObject, VersionWarranty> obj : versionConflicts
                .values())
              store.updateCache(obj);
          }

          // Remove or update old calls in our cache.
          Map<CallInstance, WarrantiedCallResult> callConflictUpdates =
            entry.getValue().callConflictUpdates;
          if (callConflictUpdates != null) {
            for (Map.Entry<CallInstance, WarrantiedCallResult> update :
                callConflictUpdates.entrySet())
              store.insertResult(update.getKey(), update.getValue());
          }

          Set<CallInstance> callConflicts = entry.getValue().callConflicts;
          if (callConflicts != null) {
            for (CallInstance call : callConflicts)
              store.removeResult(call);
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
  public void sendCommitMessagesAndCleanUp(final long commitTime)
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
        new ArrayList<Thread>(current.commitState.storesContacted.size()
            + current.workersCalled.size());

    // Send commit messages to the workers in parallel.
    for (final RemoteWorker worker : current.workersCalled) {
      Thread thread = new Thread("worker commit to " + worker) {
        @Override
        public void run() {
          try {
            worker.commitTransaction(current.tid.topTid, commitTime);
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
    Set<Store> storesToCommit = current.storesRequested();
    storesToCommit.addAll(current.commitState.storesContacted);

    int count = 0;
    for (Store s : storesToCommit) {
      count++;
      final Store store = s;
      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          try {
            Map<CallInstance, SemanticWarranty> replies =
              store.commitTransaction(current.tid.topTid, commitTime,
                  current.getRequestsForStore(store),
                  !current.commitState.storesContacted.contains(store));
            current.requestReplies.putAll(replies);
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
          && count == storesToCommit.size()) {
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
    current.commitTopLevel(commitTime);
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
  private void sendAbortMessages(Set<RemoteNode> fails) {
    for (Store store : current.commitState.storesContacted)
      if (!fails.contains(store)) {
        try {
          store.abortTransaction(current.tid);
        } catch (AccessException e) {
          Logging.log(WORKER_TRANSACTION_LOGGER, Level.WARNING,
              "Access error while aborting transaction: {0}", e);
        }
      }

    for (RemoteWorker worker : current.workersCalled)
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
   * Registers the use of a pre-cached CallInstance's value.
   */
  public void registerSemanticWarrantyUse(CallInstance call,
      WarrantiedCallResult result) {
    current.semanticWarrantiesUsed.put(call, result);
  }

  /**
   * Sets the value of the currently ongoing semantic warranty request
   * transaction.  This should be called right before ending the transaction and
   * should NOT be called if the current log does not have a call associated
   * with it (so it's not a SemanticWarranty request).
   */
  public void setSemanticWarrantyValue(fabric.lang.Object v) {
    SEMANTIC_WARRANTY_LOGGER.finest("Call: " + current.semanticWarrantyCall
        + " gets value " + v.$getOnum());
    if (!(v instanceof WrappedJavaInlineable)) v = v.fetch();
    current.semanticWarrantyValue = v;
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

    /* Invalidate semantic warranty requests of parent log. */
    current.invalidateDependentRequests(obj.$getOnum());

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
          if (current.semanticWarrantyCall != null)
            SEMANTIC_WARRANTY_LOGGER.finest("" + current.semanticWarrantyCall + " CREATING " + obj.$getOnum());
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
          LongKeyMap<Integer> reads = current.getReadsForStore(store);
          // Because this could be during a subtransaction (and not at the top
          // level after gathering all the warranty request information), filter
          // reads for creates done by semantic warranty requests.
          for (SemanticWarrantyRequest req : current.requests.values())
            for (Entry<Store, LongKeyMap<_Impl>> entry : req.creates.nonNullEntrySet())
              for (_Impl create : entry.getValue().values())
                reads.remove(create.$getOnum());

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
    startTransaction((TransactionID) null);
  }

  /**
   * Starts a new transaction for computing a CallInstance's value and
   * submitting a request for a SemanticWarranty.
   */
  public void startTransaction(CallInstance call) {
    startTransaction(null, false, call);
  }

  /**
   * Starts a new transaction with the given tid. The given tid is assumed to be
   * a valid descendant of the current tid. If the given tid is null, a random
   * tid is generated for the sub-transaction.
   */
  public void startTransaction(TransactionID tid) {
    startTransaction(tid, false, null);
  }

  public void startTransaction(TransactionID tid, boolean ignoreRetrySignal) {
    startTransaction(tid, ignoreRetrySignal, null);
  }

  private void startTransaction(TransactionID tid, boolean ignoreRetrySignal,
      CallInstance call) {
    if (current != null && !ignoreRetrySignal) checkRetrySignal();

    try {
      Timing.BEGIN.begin();
      current = new Log(current, tid, call);
      Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINEST,
          "{0} started subtx {1} in thread {2}", current.parent, current,
          Thread.currentThread());
      HOTOS_LOGGER.log(Level.INFO, "started {0}", current);
    } finally {
      Timing.BEGIN.end();
    }
  }

  /**
   * Starts a new transaction with the given tid for requesting a
   * SemanticWarranty for the given CallInstance.
   */
  public void startTransaction(TransactionID tid, CallInstance call) {
    startTransaction(tid, false, call);
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
      commitTransaction();

    // Start new transactions if necessary.
    if (commonAncestor.depth != tid.depth) startTransaction(tid, true);
  }

}
