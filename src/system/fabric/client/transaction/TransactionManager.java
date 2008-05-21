// TODO check lock ordering
// TODO somehow hook this into everything

package fabric.client.transaction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import fabric.client.*;
import fabric.common.InternalError;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object.$Impl;

/**
 * Holds transaction management information for a single thread. Each thread has
 * its own TransactionManager.
 * <p>
 * We say that a transaction has acquired a write lock on an object if any entry
 * in the object's <code>$history</code> list has
 * <code>$writeLockHolder</code> set to that transaction. We say that a
 * transaction has acquired a read lock if it is in the <code>readList</code>
 * for that object.
 * </p>
 * <p>
 * When a transaction acquires a read lock, we ensure that the <i>read condition</i>
 * holds: that the holder of the write lock is an ancestor of that transaction.
 * Before reading an object, we ensure that the transaction holds a read lock
 * and that the read condition still holds.
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
 * <li>Impl objects - for signalling to readers and writers that a read or
 * write lock on that object has been released.
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
  // corresponding Impls from the core.
  static final OidKeyHashMap<ReadMapEntry> readMap =
      new OidKeyHashMap<ReadMapEntry>();

  public static ReadMapEntry getReadMapEntry($Impl impl) {
    Core core = impl.$getCore();
    long onum = impl.$getOnum();

    while (true) {
      ReadMapEntry result;
      synchronized (readMap) {
        result = readMap.get(core, onum);
        if (result == null) {
          result = new ReadMapEntry(impl);
          readMap.put(core, onum, result);
          return result;
        }
      }

      synchronized (result) {
        synchronized (readMap) {
          // Make sure we still have the right entry.
          if (result != readMap.get(core, onum)) continue;
          
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

  private static final ThreadLocal<TransactionManager> instance =
      new InheritableThreadLocal<TransactionManager>() {
        @Override
        protected TransactionManager childValue(TransactionManager parentValue) {
          return new TransactionManager(parentValue);
        }

        @Override
        protected TransactionManager initialValue() {
          return new TransactionManager();
        }
      };

  public static TransactionManager getInstance() {
    return instance.get();
  }

  private TransactionManager() {
    this.current = null;
  }

  /**
   * A copy constructor.
   */
  private TransactionManager(TransactionManager tm) {
    this.current = tm.current;
  }

  private void checkAbortSignal() {
    if (current.abortSignal) {
      // Abort the transaction.
      // TODO Provide a reason for the abort.
      throw new AbortException(null);
    }
  }

  public void abortTransaction() {
    // Assume only one thread will be executing this.

    // Set the abort flag in all our children.
    current.flagAbort();

    // Wait for all other threads to finish.
    current.waitForThreads();

    current.abort();
    current = current.parent;
  }

  public void commitTransaction() throws AbortException {
    // Assume only one thread will be executing this.

    // XXX This is a long and ugly method. Refactor?

    // Wait for all sub-transactions to finish.
    current.waitForThreads();

    // Make sure we're not supposed to abort.
    checkAbortSignal();

    Log parent = current.parent;
    if (parent != null) {
      // Update data structures to reflect the commit.
      current.commitNested();
      current = parent;
      return;
    }

    // Commit to core.
    // Go through the transaction log and figure out the cores we need to
    // contact.
    Set<Core> cores = current.coresToContact();

    // The transaction ID assigned by each core.
    final Map<Core, Integer> tids = new ConcurrentHashMap<Core, Integer>();
    try {
      // Go through each core and send prepare messages in parallel.
      final int numCores = cores.size();
      List<Thread> threads = new ArrayList<Thread>(numCores);
      final Map<Core, TransactionPrepareFailedException> failures =
          Collections
              .synchronizedMap(new HashMap<Core, TransactionPrepareFailedException>(
                  numCores));

      for (final Core core : cores) {
        Thread thread = new Thread() {
          @Override
          public void run() {
            try {
              Collection<$Impl> creates = current.getCreatesForCore(core);
              LongKeyMap<Integer> reads = current.getReadsForCore(core);
              Collection<$Impl> writes = current.getWritesForCore(core);

              int transactionID =
                  core.prepareTransaction(creates, reads, writes);
              tids.put(core, transactionID);
            } catch (TransactionPrepareFailedException e) {
              failures.put(core, e);
            } catch (UnreachableCoreException e) {
              failures.put(core, new TransactionPrepareFailedException(
                  "Unreachable core"));
            }
          }
        };
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
      for (Map.Entry<Core, Integer> entry : tids.entrySet()) {
        final Core core = entry.getKey();
        final int tid = entry.getValue();
        Thread thread = new Thread() {
          @Override
          public void run() {
            try {
              core.commitTransaction(tid);

              // Update the local version numbers.
              for ($Impl obj : current.writes)
                if (obj.$getCore() == core) obj.$version++;

              for ($Impl obj : current.creates)
                if (obj.$getCore() == core) obj.$version = 1;
            } catch (TransactionCommitFailedException e) {
              failed.add(core);
            } catch (UnreachableCoreException e) {
              unreachable.add(core);
            }
          }
        };
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
            e.printStackTrace();
          }
        }
      }

      if (!(unreachable.isEmpty() && failed.isEmpty())) {
        throw new TransactionAtomicityViolationException(failed, unreachable);
      }
    } catch (TransactionPrepareFailedException e) {
      // Go through each core we've contacted and send abort messages.
      for (Map.Entry<Core, Integer> entry : tids.entrySet()) {
        entry.getKey().abortTransaction(entry.getValue());
      }
      // frames.push(null);
      abortTransaction();
      throw new AbortException(e);
    }

    // Update data structures to reflect successful commit.
    current.commitTopLevel();
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
    // Nothing to do if we're not in a transaction.
    if (current == null) return;

    synchronized (obj) {
      // Nothing to do if the object's $reader is us.
      if (obj.$reader == current) return;

      // Make sure we're not supposed to abort.
      checkAbortSignal();

      // Check read condition: wait until all writers are in our ancestry.
      while (obj.$writeLockHolder != null
          && !current.isDescendantOf(obj.$writeLockHolder)) {
        try {
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

      // Check write condition: wait until writer and all readers are in our
      // ancestry.
      while (true) {
        if (obj.$writeLockHolder == null
            || current.isDescendantOf(obj.$writeLockHolder)) {
          // Writer is in our ancestry. Check readers.
          ReadMapEntry readMapEntry = obj.$readMapEntry;
          if (readMapEntry == null) break;
          synchronized (readMapEntry) {
            boolean containsAll = true;
            for (Log lock : readMapEntry.readLocks) {
              if (!current.isDescendantOf(lock)) {
                containsAll = false;
                break;
              }
            }

            if (containsAll) break;
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
   * the parent transaction.
   */
  public void startTransaction() throws AbortException {
    if (current != null && current.abortSignal) {
      // Abort the transaction.
      // TODO Provide a reason for the abort.
      throw new AbortException(null);
    }
    current = new Log(current);
  }

  /**
   * Registers that a new thread has been started.
   */
  public void registerThread(Thread thread) {
    synchronized (current.threads) {
      current.threads.add(thread);
    }
  }

  /**
   * Registers that a thread has finished.
   */
  public void deregisterThread(Thread thread) {
    synchronized (current.threads) {
      current.threads.remove(thread);
    }
  }
}
