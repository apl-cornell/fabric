package fabric.client.transaction;

import java.util.*;
import java.util.logging.Logger;

import fabric.client.*;
import fabric.client.remote.RemoteClient;
import fabric.common.FabricThread;
import fabric.common.InternalError;
import fabric.common.util.LongKeyMap;
import fabric.core.InProcessCore;
import fabric.lang.Object.$Impl;

/**
 * Holds transaction management information for a single thread. Each thread has
 * its own TransactionManager.
 * <p>
 * We say that a transaction has acquired a write lock on an object if any entry
 * in the object's <code>$history</code> list has <code>$writeLockHolder</code>
 * set to that transaction. @see fabric.lang.Object.$Impl
 * </p>
 * <p>
 * We say that a transaction has acquired a read lock if it is in the
 * "read list" for that object. @see fabric.lang.Object.$Impl.$readMapEntry
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

  static final Logger logger = Logger.getLogger("fabric.client.transaction");

  /**
   * A map from OIDs to a version number and a list of logs for transactions
   * that have read that version of the object. For each transaction tx, an
   * object o is in tx.reads exactly when tx is in readList[o]. A transaction
   * has acquired a read lock if its log is in this list. All entries in this
   * list have non-empty <code>readLocks</code> sets.
   */
  // Proxy objects aren't used here because doing so would result in calls to
  // hashcode() and equals() on such objects, resulting in fetching the
  // corresponding Impls from the core.
  static final OidKeyHashMap<ReadMapEntry> readMap =
      new OidKeyHashMap<ReadMapEntry>();

  public static ReadMapEntry getReadMapEntry($Impl impl) {
    FabricSoftRef ref = impl.$ref;

    // Optimization: if the impl lives on the local core, it will never be
    // evicted, so no need to store the entry in the read map.
    if (ref.core.isLocalCore()) return new ReadMapEntry(impl);

    while (true) {
      ReadMapEntry result;
      synchronized (readMap) {
        result = readMap.get(ref.core, ref.onum);
        if (result == null) {
          result = new ReadMapEntry(impl);
          readMap.put(ref.core, ref.onum, result);
          return result;
        }
      }

      synchronized (result) {
        synchronized (readMap) {
          // Make sure we still have the right entry.
          if (result != readMap.get(ref.core, ref.onum)) continue;

          result.obj = impl.$ref;
          result.pinCount++;
          int ver = impl.$getVersion();
          if (ver == result.versionNumber) return result;

          // Version numbers don't match. Abort all other transactions.
          // XXX What if we just read in an older copy of the object?
          for (Log reader : result.readLocks) {
            reader.flagAbort();
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

  private void checkAbortSignal() {
    if (current.abortSignal) {
      logger.finest(current + " got abort signal");
      // Abort the transaction.
      // TODO Provide a reason for the abort.
      throw new AbortException(null);
    }
  }

  public void abortTransaction() {
    logger.finest(current + " aborting");
    // Assume only one thread will be executing this.

    // Set the abort flag in all our children.
    current.flagAbort();

    // Wait for all other threads to finish.
    current.waitForThreads();

    current.abort();
    logger.finest(current + " aborted");

    if (current.tid.parent == null || current.parent != null
        && current.parent.tid == current.tid.parent) {
      // The parent frame represents the parent transaction. Pop the stack.
      current = current.parent;
    } else {
      // Reuse the current frame for the parent transaction.
      current.tid = current.tid.parent;
    }
  }

  /**
   * Commits the transaction if possible; otherwise, aborts the transaction.
   */
  public void commitTransaction() throws AbortException {
    logger.finest(current + " attempting to commit");
    // Assume only one thread will be executing this.

    // XXX This is a long and ugly method. Refactor?

    // Wait for all sub-transactions to finish.
    current.waitForThreads();

    // Make sure we're not supposed to abort.
    try {
      checkAbortSignal();
    } catch (AbortException e) {
      abortTransaction();
      throw e;
    }

    logger.finest(current + " committing");

    Log parent = current.parent;
    if (current.tid.parent != null) {
      // Update data structures to reflect the commit.
      current.commitNested();
      logger.finest(current + " committed");
      if (parent.tid == current.tid.parent) {
        // Parent frame represents parent transaction. Pop the stack.
        current = parent;
      } else {
        // Reuse the current frame for the parent transaction. Update its TID.
        current.tid = current.tid.parent;
      }
      return;
    }

    // Commit to core.
    // Go through the transaction log and figure out the cores we need to
    // contact.
    Set<Core> cores = current.coresToContact();

    try {
      // Go through each core and send prepare messages in parallel.
      final int numCores = cores.size();
      List<Thread> threads = new ArrayList<Thread>(numCores);
      final Map<Core, TransactionPrepareFailedException> failures =
          Collections
              .synchronizedMap(new HashMap<Core, TransactionPrepareFailedException>(
                  numCores));

      for (Iterator<Core> coreIt = cores.iterator(); coreIt.hasNext();) {
        final Core core = coreIt.next();
        Runnable runnable = new Runnable() {
          public void run() {
            try {
              Collection<$Impl> creates = current.getCreatesForCore(core);
              LongKeyMap<Integer> reads = current.getReadsForCore(core);
              Collection<$Impl> writes = current.getWritesForCore(core);
              core.prepareTransaction(current.tid.topTid, creates, reads,
                  writes);
            } catch (TransactionPrepareFailedException e) {
              failures.put(core, e);
            } catch (UnreachableNodeException e) {
              failures.put(core, new TransactionPrepareFailedException(
                  "Unreachable core"));
            }
          }
        };

        // Optimization: only start in a new thread if there are more cores to
        // contact and if it's a truly remote core (i.e., not in-process).
        if (!(core instanceof InProcessCore || core.isLocalCore())
            && coreIt.hasNext()) {
          Thread thread =
              new Thread(runnable, "client prepare to " + core.name());
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

      // Check for conflicts and unreachable cores.
      if (!failures.isEmpty()) {
        throw new TransactionPrepareFailedException(failures);
      }

      // At this point, everything is PREPARED at the cores. Send commit
      // messages.
      final List<Core> unreachable =
          Collections.synchronizedList(new ArrayList<Core>());
      final List<Core> failed =
          Collections.synchronizedList(new ArrayList<Core>());
      threads.clear();
      for (Iterator<Core> coreIt = cores.iterator(); coreIt.hasNext();) {
        final Core core = coreIt.next();
        Runnable runnable = new Runnable() {
          public void run() {
            try {
              core.commitTransaction(current.tid.topTid);
            } catch (TransactionCommitFailedException e) {
              failed.add(core);
            } catch (UnreachableNodeException e) {
              unreachable.add(core);
            }
          }
        };

        // Optimization: only start in a new thread if there are more cores to
        // contact and if it's a truly remote core (i.e., not in-process).
        if (!(core instanceof InProcessCore || core.isLocalCore())
            && coreIt.hasNext()) {
          Thread thread =
              new Thread(runnable, "client commit to " + core.name());
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
        logger.finest(current
            + " error committing: atomicity violation -- failed:" + failed
            + " unreachable:" + unreachable);
        throw new TransactionAtomicityViolationException(failed, unreachable);
      }
    } catch (TransactionPrepareFailedException e) {
      // Go through each core we've contacted and send abort messages.
      for (Core core : cores)
        core.abortTransaction(current.tid.topTid);
      logger.finest(current + " error committing: abort exception: " + e);
      abortTransaction();
      throw new AbortException(e);
    }

    // Update data structures to reflect successful commit.
    logger.finest(current + " committed at cores...updating data structures");
    current.commitTopLevel();
    logger.finest(current + " committed");
    current = null;
  }

  public void registerCreate($Impl obj) {
    if (current == null)
      throw new InternalError("Cannot create objects outside a transaction");

    // Make sure we're not supposed to abort.
    checkAbortSignal();

    // Grab a write lock on the object.
    obj.$writer = current;
    obj.$writeLockHolder = current;

    // Add the object to our creates set.
    synchronized (current.creates) {
      current.creates.add(obj);
    }
  }

  public void registerRead($Impl obj) {
    synchronized (obj) {
      // Nothing to do if the object's $reader is us or we're not in a
      // transaction.
      if (obj.$reader == current || current == null) return;

      // Make sure we're not supposed to abort.
      checkAbortSignal();

      // Check read condition: wait until all writers are in our ancestry.
      boolean hadToWait = false;
      while (obj.$writeLockHolder != null
          && !current.isDescendantOf(obj.$writeLockHolder)) {
        try {
          logger.finest(current + " wants to read " + obj.$getCore() + "/"
              + obj.$getOnum() + " (" + obj.getClass()
              + "); waiting on writer " + obj.$writeLockHolder);
          hadToWait = true;
          obj.$numWaiting++;
          obj.wait();
        } catch (InterruptedException e) {
        }
        obj.$numWaiting--;

        // Make sure we weren't aborted while we were waiting.
        checkAbortSignal();
      }

      // Set the object's reader stamp to the current transaction.
      obj.$reader = current;

      current.acquireReadLock(obj);
      if (hadToWait) logger.finest(current + " got read lock");
    }
  }

  /**
   * This should be called <i>before</i> the object is modified.
   * 
   * @return whether a new (top-level) transaction was created.
   */
  public boolean registerWrite($Impl obj) {
    boolean needTransaction = current == null;
    if (needTransaction) startTransaction();

    synchronized (obj) {
      // Nothing to do if the write stamp is us.
      if (obj.$writer == current) return needTransaction;

      // Make sure we're not supposed to abort.
      if (!needTransaction) checkAbortSignal();

      // Check write condition: wait until writer is in our ancestry and all
      // readers are in our ancestry.
      boolean hadToWait = false;
      while (true) {
        // Make sure writer is in our ancestry.
        if (obj.$writeLockHolder != null
            && !current.isDescendantOf(obj.$writeLockHolder)) {
          logger.finest(current + " wants to write " + obj.$getCore() + "/"
              + obj.$getOnum() + " (" + obj.getClass()
              + "); waiting on writer " + obj.$writeLockHolder);
          hadToWait = true;
        } else {
          // Abort any incompatible readers.
          ReadMapEntry readMapEntry = obj.$readMapEntry;
          if (readMapEntry != null) {
            synchronized (readMapEntry) {
              boolean allReadersInAncestry = true;
              for (Log lock : readMapEntry.readLocks) {
                if (!current.isDescendantOf(lock)) {
                  logger.finest(current + " wants to write " + obj.$getCore()
                      + "/" + obj.$getOnum() + " (" + obj.getClass()
                      + "); aborting reader " + lock);
                  lock.flagAbort();
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

        // Make sure we weren't aborted while we were waiting.
        checkAbortSignal();
      }

      // Set the write stamp.
      obj.$writer = current;

      if (hadToWait) logger.finest(current + " got write lock");

      if (obj.$writeLockHolder == current) return needTransaction;

      // Create a backup object, grab the write lock, and add the object to our
      // write set.
      obj.$history = obj.clone();
      obj.$writeLockHolder = current;
      synchronized (current.writes) {
        current.writes.add(obj);
      }

      if (obj.$reader != current) {
        // Clear the read stamp -- the reader's read condition no longer holds.
        obj.$reader = null;
      }
    }

    return needTransaction;
  }

  /**
   * Starts a new transaction. The sub-transaction runs in the same thread as
   * the caller.
   */
  public void startTransaction() throws AbortException {
    startTransaction(null);
  }

  /**
   * Starts a new transaction with the given tid. The given tid is assumed to be
   * a valid descendent of the current tid. If the given tid is null, a random
   * tid is generated for the subtransaction.
   */
  public void startTransaction(TransactionID tid) {
    if (current != null && current.abortSignal) {
      // Abort the transaction.
      // TODO Provide a reason for the abort.
      throw new AbortException(null);
    }
    current = new Log(current, tid);
    logger.finest(current.parent + " started subtx " + current + " in thread "
        + Thread.currentThread());
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
   * Registers a remote call to the given client.
   * 
   * @return the current transaction ID.
   */
  public TransactionID registerRemoteCall(RemoteClient client) {
    if (current != null) {
      if (!current.clientsCalled.contains(client))
        current.clientsCalled.add(client);
    }

    return current.tid;
  }

  /**
   * Associates the given transaction log with this transaction manager.
   */
  public void associateLog(Log log) {
    current = log;
  }
}
