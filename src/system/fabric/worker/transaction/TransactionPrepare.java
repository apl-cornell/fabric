package fabric.worker.transaction;

import static fabric.common.Logging.HOTOS_LOGGER;
import static fabric.common.Logging.WORKER_TRANSACTION_LOGGER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.SerializedObject;
import fabric.common.SysUtil;
import fabric.common.Threading;
import fabric.common.Threading.NamedRunnable;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object._Impl;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;
import fabric.worker.RemoteStore;
import fabric.worker.Store;
import fabric.worker.TransactionAtomicityViolationException;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.TransactionRestartingException;
import fabric.worker.remote.RemoteWorker;

/**
 * Worker representation of a transaction prepare.
 */
public class TransactionPrepare {
  private enum Status {
    PREPARING, ABORTING, PREPARED, COMMITTING, COMMITTED;
  }

  private Status currentStatus = Status.PREPARING;

  private final Map<RemoteWorker, Boolean> outstandingWorkers;
  private final Set<RemoteWorker> respondedWorkers = new HashSet<>();

  private final Map<Store, Boolean> outstandingStores;
  private final Set<Store> respondedStores = new HashSet<>();

  private final RemoteWorker coordinator;
  private final Log txnLog;
  private final boolean singleStore;
  private final boolean readOnly;

  public TransactionPrepare(RemoteWorker coordinator, Log txnLog,
      boolean singleStore, boolean readOnly, Collection<Store> stores,
      Collection<RemoteWorker> workers) {
    this.coordinator = coordinator;
    this.txnLog = txnLog;
    this.singleStore = singleStore;
    this.readOnly = readOnly;
    this.outstandingStores = new HashMap<>(stores.size());
    for (Store store : stores) {
      this.outstandingStores.put(store, false);
    }
    this.outstandingWorkers = new HashMap<>(workers.size());
    for (RemoteWorker worker : workers) {
      this.outstandingWorkers.put(worker, false);
    }
  }

  public synchronized void prepare() throws TransactionRestartingException {
    WORKER_TRANSACTION_LOGGER.log(Level.FINER, "{0} running prepare", txnLog);
    // Make sure we weren't aborted already.
    txnLog.checkRetrySignal();

    final Map<RemoteNode<?>, TransactionPrepareFailedException> failures =
        Collections.synchronizedMap(
            new HashMap<RemoteNode<?>, TransactionPrepareFailedException>());

    // If we literally don't have anyone to prepare, we just move on.
    if (currentStatus == Status.PREPARING && outstandingStores.isEmpty()
        && outstandingWorkers.isEmpty()) {
      currentStatus = Status.PREPARED;
      return;
    }

    List<Future<?>> futures =
        new ArrayList<>(outstandingStores.size() + outstandingWorkers.size());

    // Go through each worker and send prepare messages in parallel.
    for (final RemoteWorker worker : outstandingWorkers.keySet()) {
      Threading.NamedRunnable runnable =
          new Threading.NamedRunnable("worker prepare to " + worker.name()) {
            @Override
            protected void runImpl() {
              outstandingWorkers.put(worker, true);
              try {
                worker.prepareTransaction(txnLog.tid.topTid);
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
              outstandingWorkers.remove(worker);
              respondedWorkers.add(worker);
            }
          };
      futures.add(Threading.getPool().submit(runnable));
    }

    // Go through each store and send prepare messages in parallel.
    for (Iterator<Store> storeIt =
        outstandingStores.keySet().iterator(); storeIt.hasNext();) {
      final Store store = storeIt.next();
      NamedRunnable runnable =
          new NamedRunnable("worker prepare to " + store.name()) {

            @Override
            public void runImpl() {
              try {
                Collection<_Impl> creates = txnLog.getCreatesForStore(store);
                LongKeyMap<Integer> reads =
                    txnLog.getReadsForStore(store, false);
                Collection<_Impl> writes = txnLog.getWritesForStore(store);
                store.prepareTransaction(txnLog.tid.topTid, singleStore,
                    readOnly, creates, reads, writes);
              } catch (TransactionPrepareFailedException e) {
                failures.put((RemoteNode<?>) store, e);
              } catch (UnreachableNodeException e) {
                failures.put((RemoteNode<?>) store,
                    new TransactionPrepareFailedException("Unreachable store"));
              }
            }
          };

      // Optimization: only start in a new thread if there are more stores to
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

    // Check for conflicts and unreachable stores/workers.
    if (!failures.isEmpty()) {
      String conflictsString = "";
      String logMessage = "Transaction tid="
          + Long.toHexString(txnLog.tid.topTid) + ":  prepare failed.";

      for (Map.Entry<RemoteNode<?>, TransactionPrepareFailedException> entry : failures
          .entrySet()) {
        if (entry.getKey() instanceof RemoteStore) {
          // Remove old objects from our cache.
          RemoteStore store = (RemoteStore) entry.getKey();
          LongKeyMap<SerializedObject> versionConflicts =
              entry.getValue().versionConflicts;
          if (versionConflicts != null) {
            for (SerializedObject obj : versionConflicts.values()) {
              store.updateCache(obj);
              if (!conflictsString.equals("")) {
                conflictsString += " ";
              }
              conflictsString +=
                  obj.getClassName() + "@" + store.name() + "#" + obj.getOnum();
            }
          }
        }

        if (WORKER_TRANSACTION_LOGGER.isLoggable(Level.FINE)) {
          logMessage +=
              "\n\t" + entry.getKey() + ": " + entry.getValue().getMessage();
        }
        txnLog.stats.addConflicts(conflictsString);
      }
      WORKER_TRANSACTION_LOGGER.fine(logMessage);
      HOTOS_LOGGER.fine("Prepare failed.");

      TransactionID tid = txnLog.tid;

      TransactionPrepareFailedException e =
          new TransactionPrepareFailedException(failures);
      Logging.log(WORKER_TRANSACTION_LOGGER, Level.INFO,
          "{0} error committing: prepare failed exception: {1}", txnLog, e);

      Set<RemoteNode<?>> abortedNodes = failures.keySet();
      if (readOnly) {
        // All remote stores should have aborted already.
        abortedNodes = new HashSet<>(abortedNodes);
        for (Store store : outstandingStores.keySet()) {
          if (store instanceof RemoteStore) {
            abortedNodes.add((RemoteStore) store);
          }
        }
      }
      throw new TransactionRestartingException(tid);
    }
  }

  public synchronized void commit() {
    if (currentStatus == Status.PREPARED) {
      WORKER_TRANSACTION_LOGGER.log(Level.FINER, "{0} running commit", txnLog);
      if (!readOnly && !singleStore
          && (!respondedStores.isEmpty() || !respondedWorkers.isEmpty())) {
        final List<RemoteNode<?>> unreachable =
            Collections.synchronizedList(new ArrayList<RemoteNode<?>>());
        final List<RemoteNode<?>> failed =
            Collections.synchronizedList(new ArrayList<RemoteNode<?>>());
        List<Future<?>> futures =
            new ArrayList<>(respondedStores.size() + respondedWorkers.size());

        // Send commit messages to the workers in parallel.
        for (final RemoteWorker worker : respondedWorkers) {
          NamedRunnable runnable =
              new NamedRunnable("worker commit to " + worker) {
                @Override
                public void runImpl() {
                  try {
                    worker.commitTransaction(txnLog.tid.topTid);
                  } catch (UnreachableNodeException e) {
                    unreachable.add(worker);
                  } catch (TransactionCommitFailedException e) {
                    failed.add(worker);
                  }
                }
              };

          futures.add(Threading.getPool().submit(runnable));
        }

        // Send commit messages to the stores in parallel.
        for (Iterator<Store> storeIt = respondedStores.iterator(); storeIt
            .hasNext();) {
          final Store store = storeIt.next();
          NamedRunnable runnable =
              new NamedRunnable("worker commit to " + store.name()) {
                @Override
                public void runImpl() {
                  try {
                    store.commitTransaction(txnLog.tid.topTid);
                  } catch (TransactionCommitFailedException e) {
                    failed.add((RemoteStore) store);
                  } catch (UnreachableNodeException e) {
                    unreachable.add((RemoteStore) store);
                  }
                }
              };

          // Optimization: only start in a new thread if there are more stores to
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
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        }

        if (!(unreachable.isEmpty() && failed.isEmpty())) {
          Logging.log(WORKER_TRANSACTION_LOGGER, Level.SEVERE,
              "{0} error committing: atomicity violation "
                  + "-- failed: {1} unreachable: {2}",
              txnLog, failed, unreachable);
          throw new TransactionAtomicityViolationException(failed, unreachable);
        }
      } else {
        // Mark this as committed.
        currentStatus = Status.COMMITTED;
        //if (coordinator != null)
        //  coordinator.notifyWorkerCommitted(txnLog.tid.topTid);
      }
    }
    // TODO: is it possible to be here before we've prepared?
  }

  /**
   * Initiate an abort due to some problem on the coordinator side.
   */
  public synchronized void abort() {
    if (currentStatus != Status.ABORTING) {
      WORKER_TRANSACTION_LOGGER.log(Level.FINE,
          "{0} aborted during prepare by external actor", txnLog);
      runAbort();
    }
  }

  /**
   * Actually perform abort round, updating state and sending messages.
   */
  private synchronized void runAbort() {
    currentStatus = Status.ABORTING;
    for (Store store : SysUtil.chain(outstandingStores.keySet(),
        respondedStores))
      try {
        store.abortTransaction(txnLog.tid);
      } catch (AccessException e) {
        Logging.log(WORKER_TRANSACTION_LOGGER, Level.WARNING,
            "Access error while aborting transaction: {0}", e);
      }

    for (RemoteWorker worker : SysUtil.chain(outstandingWorkers.keySet(),
        respondedWorkers))
      try {
        worker.abortTransaction(txnLog.tid);
      } catch (AccessException e) {
        Logging.log(WORKER_TRANSACTION_LOGGER, Level.WARNING,
            "Access error while aborting transaction: {0}", e);
      }
  }
}
