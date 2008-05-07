package fabric.client;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import fabric.common.FabricRuntimeException;
import fabric.common.Pair;
import fabric.common.TwoKeyHashMap;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object.$Impl;
import fabric.lang.Object.$Proxy;

public final class TransactionManager {
  /**
   * Records the objects that are created, read, and written during a single
   * nested transaction.
   */
  private static final class Log {
    /**
     * The identifier for the nested transaction being logged.
     */
    private final int tid;

    /**
     * The log for the outer transaction.
     */
    private final Log outerLog;

    /**
     * Contains version numbers for the objects that have been read.
     */
    protected Map<Core, LongKeyHashMap<Pair<SoftReference<$Impl>, Integer>>> reads;

    /**
     * Contains $Impls that have been created.
     */
    protected Map<Core, List<$Impl>> creates;

    /**
     * Contains $Impls that have been modified.
     */
    protected Map<Core, List<$Impl>> writes;

    private Log(Log outerLog) {
      this.tid = outerLog == null ? 1 : (outerLog.tid + 1);
      this.outerLog = outerLog;
      this.reads =
          new HashMap<Core, LongKeyHashMap<Pair<SoftReference<$Impl>, Integer>>>();
      this.creates = new HashMap<Core, List<$Impl>>();
      this.writes = new HashMap<Core, List<$Impl>>();
    }

    /**
     * Incorporates the reads, creates, and writes in this log into the parent
     * transaction's log.
     */
    protected void mergeWithParent() {
      // Merge creates. We do this by adding everything in our "creates" list to
      // our parent's "creates" list. Also, for each object created in this
      // transaction, we update the $createTransactionID to our parent's TID.
      for (Map.Entry<Core, List<$Impl>> entry : creates.entrySet()) {
        Core core = entry.getKey();
        List<$Impl> newCreates = entry.getValue();

        List<$Impl> parentCreates = outerLog.creates.get(core);
        if (parentCreates == null) {
          parentCreates = new ArrayList<$Impl>();
          outerLog.creates.put(core, parentCreates);
        }

        parentCreates.addAll(newCreates);
        for ($Impl obj : newCreates)
          obj.$createTransactionID = tid;
      }

      // Merge writes.
      for (Map.Entry<Core, List<$Impl>> entry : writes.entrySet()) {
        Core core = entry.getKey();
        for ($Impl obj : entry.getValue()) {
          if (obj.$commit(outerLog.tid)) {
            Log.addEntry(outerLog.writes, core, obj);
          }
        }
      }

      // Merge reads.
      for (Map.Entry<Core, LongKeyHashMap<Pair<SoftReference<$Impl>, Integer>>> entry : reads
          .entrySet()) {
        Core core = entry.getKey();

        LongKeyMap<Pair<SoftReference<$Impl>, Integer>> parentMap =
            outerLog.reads.get(core);
        if (parentMap == null) {
          parentMap = new LongKeyHashMap<Pair<SoftReference<$Impl>, Integer>>();
          outerLog.reads.put(core,
              (LongKeyHashMap<Pair<SoftReference<$Impl>, Integer>>) parentMap);
        }

        for (LongKeyMap.Entry<Pair<SoftReference<$Impl>, Integer>> subEntry : entry
            .getValue().entrySet()) {
          long onum = subEntry.getKey();
          Pair<SoftReference<$Impl>, Integer> pair = subEntry.getValue();

          // Add the entry to the parent.
          parentMap.put(onum, pair);

          $Impl obj = pair.first.get();
          if (obj == null) {
            // Object was evicted at some point. Get a copy from the object
            // cache.
            obj = core.readObjectFromCache(onum);
            if (obj == null) continue;
            pair.first = new SoftReference<$Impl>(obj);
          }

          obj.$readTransactionID = outerLog.tid;
        }
      }
    }

    /**
     * Returns information about the first time a given object was read.
     * 
     * @param core
     *                the on which the object lives.
     * @param onum
     *                the object's onum.
     * @return null if the object was never read; otherwise, returns the ID of
     *         the transaction that first read the object, a soft reference to
     *         the object, and the version number that was read.
     */
    public Pair<Integer, Pair<SoftReference<$Impl>, Integer>> getFirstRead(
        Core core, Long onum) {
      Log curLog = this;
      while (curLog != null) {
        Pair<SoftReference<$Impl>, Integer> pair =
            TwoKeyHashMap.get(curLog.reads, core, onum);
        if (pair != null) {
          return new Pair<Integer, Pair<SoftReference<$Impl>, Integer>>(
              curLog.tid, pair);
        }

        curLog = curLog.outerLog;
      }

      return null;
    }

    /**
     * Goes through the objects first read during this sub-transaction and sets
     * their read-transaction-IDs to the given value.
     * 
     * @param tid
     */
    public void setReadTIDs(int tid) {
      for (Map.Entry<Core, LongKeyHashMap<Pair<SoftReference<$Impl>, Integer>>> entry : reads
          .entrySet()) {
        for (LongKeyMap.Entry<Pair<SoftReference<$Impl>, Integer>> subEntry : entry
            .getValue().entrySet()) {
          Pair<SoftReference<$Impl>, Integer> pair = subEntry.getValue();
          $Impl obj = pair.first.get();
          if (obj == null) {
            // Object was evicted at some point. Get a copy from the object
            // cache.
            Core core = entry.getKey();
            long onum = subEntry.getKey();
            obj = core.readObjectFromCache(onum);
            if (obj == null) continue;
            pair.first = new SoftReference<$Impl>(obj);
          }

          obj.$readTransactionID = tid;
        }
      }
    }

    /**
     * Adds an entry to a log structure.
     */
    private static void addEntry(Map<Core, List<$Impl>> map, Core core,
        $Impl obj) {
      List<$Impl> list = map.get(core);
      if (list == null) {
        list = new ArrayList<$Impl>();
        map.put(core, list);
      }

      list.add(obj);
    }
  }

  public static final class VersionConflictException extends
      FabricRuntimeException {
    public $Proxy reference;

    public VersionConflictException($Impl obj) {
      this(obj.$getProxy());
    }

    public VersionConflictException($Proxy reference) {
      this.reference = reference;
    }
  }

  public static final TransactionManager INSTANCE = new TransactionManager();

  private Log curFrame;

  private TransactionManager() {
    curFrame = null;
  }

  public void abortTransaction() {
//    System.out.println("abortTransaction: " + curFrame.tid);
    
    // Restore the state of all objects that were modified during the aborted
    // transaction.
    for (List<$Impl> list : curFrame.writes.values()) {
      for ($Impl obj : list) {
        obj.$abort();
      }
    }

    // Reset the read-transaction-ID for any object that was read for the first
    // time by this transaction.
    curFrame.setReadTIDs(-1);

    curFrame = curFrame.outerLog;
  }

  public void commitTransaction() throws AbortException {
//    System.out.println("commitTransaction: " + curFrame.tid);
    
    // XXX This is a long and ugly method. Refactor?
    Log parentFrame = curFrame.outerLog;
    if (parentFrame != null) {
      // Merge current transaction with its parent.
      curFrame.mergeWithParent();
      curFrame = parentFrame;
      return;
    }

    Set<Core> cores = new HashSet<Core>();
    final Map<Core, Integer> transactionIDs =
        new ConcurrentHashMap<Core, Integer>();

    // Go through the transaction log and figure out the cores we need to
    // contact.
    cores.addAll(curFrame.creates.keySet());
    cores.addAll(curFrame.reads.keySet());
    cores.addAll(curFrame.writes.keySet());

    try {
      // Go through each core and send begin and prepare messages.
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
              Collection<$Impl> creates = curFrame.creates.get(core);
              LongKeyMap<Pair<SoftReference<$Impl>, Integer>> readsWithRefs =
                  curFrame.reads.get(core);
              LongKeyMap<Integer> reads = new LongKeyHashMap<Integer>();
              Collection<$Impl> writes = curFrame.writes.get(core);

              // Convert the read map.
              if (readsWithRefs != null) {
                for (LongKeyMap.Entry<Pair<SoftReference<$Impl>, Integer>> entry : readsWithRefs
                    .entrySet()) {
                  reads.put(entry.getKey(), entry.getValue().second);
                }
              }

              int transactionID =
                  core.prepareTransaction(creates, reads, writes);
              transactionIDs.put(core, transactionID);
            } catch (TransactionPrepareFailedException exc) {
              failures.put(core, exc);
            } catch (UnreachableCoreException exc) {
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
      for (Map.Entry<Core, Integer> entry : transactionIDs.entrySet()) {
        final Core core = entry.getKey();
        final int transactionID = entry.getValue();
        Thread thread = new Thread() {
          @Override
          public void run() {
            try {
              core.commitTransaction(transactionID);

              // Update the local version numbers.
              List<$Impl> writes = curFrame.writes.get(core);
              if (writes != null) {
                for ($Impl obj : writes)
                  obj.$version++;
              }

              List<$Impl> creates = curFrame.creates.get(core);
              if (creates != null) {
                for ($Impl obj : creates)
                  obj.$version = 1;
              }
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
      for (Map.Entry<Core, Integer> entry : transactionIDs.entrySet()) {
        entry.getKey().abortTransaction(entry.getValue());
      }
      // frames.push(null);
      abortTransaction();
      throw new AbortException(e);
    }

    // Reset the create-transaction-IDs for all the new objects.
    for (List<$Impl> list : curFrame.creates.values()) {
      for ($Impl obj : list)
        obj.$createTransactionID = -1;
    }

    // Reset the read-transaction-IDs for all the read objects.
    curFrame.setReadTIDs(-1);
    
    // Reset the write history for all modified objects.
    for (List<$Impl> list : curFrame.writes.values()) {
      for ($Impl obj : list) obj.$commit(0);
    }

    curFrame = null;
  }

  public void registerCreate($Impl obj) {
    // If we're not in a transaction, start one just for the object creation.
    boolean needTransaction = curFrame == null;
    if (needTransaction) startTransaction();

    // Register the object creation.
    obj.$createTransactionID = curFrame.tid;
    Log.addEntry(curFrame.creates, obj.$getCore(), obj);

    // Commit if we started a transaction for the object creation.
    if (needTransaction) commitTransaction();
  }

  public void registerRead($Impl obj) throws VersionConflictException {
    // Nothing to do if we're not in a transaction.
    if (curFrame == null) return;

    // Nothing to do if we created the object ourselves. (If this is the case,
    // we will be working on version 0 of the object.)
    if (obj.$version == 0) return;

    // Nothing to do if the object is marked as being read already.
    if (obj.$readTransactionID > 0) return;

    Core core = obj.$getCore();
    long onum = obj.$getOnum();

    Pair<Integer, Pair<SoftReference<$Impl>, Integer>> firstRead =
        curFrame.getFirstRead(core, onum);

    if (firstRead == null) {
      // First time reading the object.
      SoftReference<$Impl> ref = new SoftReference<$Impl>(obj);
      TwoKeyHashMap.put(curFrame.reads, core, onum,
          new Pair<SoftReference<$Impl>, Integer>(ref, obj.$getVersion()));
      obj.$readTransactionID = curFrame.tid;
    } else {
      // Object was evicted from cache.
      Pair<SoftReference<$Impl>, Integer> readEntry = firstRead.second;
      int otherVersionNum = readEntry.second;

      if (otherVersionNum != obj.$getVersion())
        throw new VersionConflictException(obj);

      // Restore object's read-transaction ID.
      int firstReadTID = firstRead.first;
      obj.$readTransactionID = firstReadTID;

      // Save a soft reference.
      readEntry.first = new SoftReference<$Impl>(obj);
    }
  }

  /**
   * This should be called <i>before</i> the object is modified.
   * 
   * @return whether a new (top-level) transaction was created.
   */
  public boolean registerWrite($Impl obj) {
    // Ensure that we're running in a transaction.
    boolean needTransaction = curFrame == null;
    if (needTransaction) startTransaction();

    // Make sure the object hasn't already been modified or created during the
    // current transaction.
    if (obj.$createTransactionID == curFrame.tid
        || !obj.$prepareToWrite(curFrame.tid)) return needTransaction;

    // First time modifying the object in this transaction. Save a copy of the
    // object along with a pointer to the actual working copy of the object.
    Core core = obj.$getCore();
    Log.addEntry(curFrame.writes, core, obj);

    return needTransaction;
  }

  public void startTransaction() {
    curFrame = new Log(curFrame);
//    System.out.println("startTransaction: " + curFrame.tid);
  }
}
