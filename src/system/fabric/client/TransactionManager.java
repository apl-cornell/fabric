package fabric.client;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import fabric.common.FabricRuntimeException;
import fabric.common.Pair;
import fabric.common.TwoKeyHashMap;
import fabric.lang.Object.$Impl;
import fabric.lang.Object.$Proxy;

public final class TransactionManager {
  /**
   * Represents a transaction log, recording the objects that have been created,
   * read, and written during a single top-level transaction and all its
   * sub-transactions.
   */
  private static final class TransactionLog {
    /**
     * Maps OIDs to objects that have been created.
     */
    protected TwoKeyHashMap<Core, Long, $Impl> creates;

    /**
     * Maps OIDs to the version number that was read.
     */
    protected TwoKeyHashMap<Core, Long, Integer> reads;

    /**
     * Maps OIDs to $Impls that have been modified and a count of how many undo
     * logs exist for the object.
     */
    protected TwoKeyHashMap<Core, Long, Pair<$Impl, Integer>> writes;

    private TransactionLog() {
      this.creates = new TwoKeyHashMap<Core, Long, $Impl>();
      this.reads = new TwoKeyHashMap<Core, Long, Integer>();
      this.writes = new TwoKeyHashMap<Core, Long, Pair<$Impl, Integer>>();
    }
  }

  /**
   * Provides undo information for rolling back a sub-transaction.
   */
  private final class UndoLog {
    /**
     * The set of OIDs for objects that have been created during the
     * sub-transaction.
     */
    protected Map<Core, Set<Long>> creates;

    /**
     * The set of OIDs for objects that were read for the first time during the
     * sub-transaction.
     */
    protected Map<Core, Set<Long>> reads;

    /**
     * Maps the OID of each object modified during the sub-transaction to a copy
     * of the original state of the object.
     */
    protected TwoKeyHashMap<Core, Long, $Impl> writes;
    protected Stack<TwoKeyHashMap<Core, Long, $Impl>> writeStack;

    /**
     * A stack of undo logs for sub-transactions.
     */
    protected Stack<UndoLog> nestedLogs;

    private UndoLog() {
      this.creates = new HashMap<Core, Set<Long>>();
      this.reads = new HashMap<Core, Set<Long>>();
      this.writes = new TwoKeyHashMap<Core, Long, $Impl>();
      this.writeStack = new Stack<TwoKeyHashMap<Core, Long, $Impl>>();
      this.nestedLogs = new Stack<UndoLog>();
    }

    /**
     * Starts a new sub-log for a new sub-transaction.
     * 
     * @return the new sub-log
     */
    private UndoLog startSublog() {
      UndoLog result = new UndoLog();
      nestedLogs.push(result);
      if (writes.isEmpty())
        writeStack.push(null);
      else {
        writeStack.push(writes);
        writes = new TwoKeyHashMap<Core, Long, $Impl>();
      }
      return result;
    }

    private void discardLastSublog() {
      nestedLogs.pop();

      TwoKeyHashMap<Core, Long, $Impl> writes = writeStack.pop();
      if (writes == null)
        this.writes.clear();
      else this.writes = writes;
    }

    /**
     * Undoes the actions recorded in this undo log.
     */
    private void rollback() {
      // Undo creates.
      for (Map.Entry<Core, Set<Long>> entry : creates.entrySet()) {
        Core core = entry.getKey();
        for (long onum : entry.getValue()) {
          log.creates.remove(core, onum);
        }
      }

      // Undo reads.
      for (Map.Entry<Core, Set<Long>> entry : reads.entrySet()) {
        Core core = entry.getKey();
        for (long onum : entry.getValue()) {
          log.reads.remove(core, onum);
        }
      }

      // Undo writes and sub-logs.
      while (true) {
        if (writes != null) {
          for (Core core : writes.keySet()) {
            for (Map.Entry<Long, $Impl> entry : writes.get(core).entrySet()) {
              long onum = entry.getKey();
              Pair<$Impl, Integer> pair = log.writes.get(core, onum);
              pair.first.$copyStateFrom(entry.getValue());
              pair.second--;

              if (pair.second == 0) writes.remove(core, onum);
            }
          }
        }

        if (nestedLogs.isEmpty()) break;
        nestedLogs.pop().rollback();
        writes = writeStack.pop();
      }
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

  private TransactionLog log;
  private UndoLog undoLog;

  private final Stack<UndoLog> frames;

  private TransactionManager() {
    this.log = null;
    this.undoLog = null;
    this.frames = new Stack<UndoLog>();
  }

  public void abortTransaction() {
    undoLog.rollback();
    undoLog = frames.pop();
    if (undoLog != null) {
      undoLog.discardLastSublog();
    }
  }

  public void commitTransaction() throws TransactionPrepareFailedException {
    // XXX This is a long and ugly method. Refactor?
    undoLog = frames.pop();
    if (undoLog != null) return;

    Set<Core> cores = new HashSet<Core>();
    final Map<Core, Integer> transactionIDs =
        new ConcurrentHashMap<Core, Integer>();

    // Go through the transaction log and figure out the cores we need to
    // contact.
    cores.addAll(log.creates.keySet());
    cores.addAll(log.reads.keySet());
    cores.addAll(log.writes.keySet());

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
              Map<Long, $Impl> createsMap = log.creates.get(core);
              Collection<$Impl> creates = null;
              if (createsMap != null) creates = createsMap.values();

              Map<Long, Pair<$Impl, Integer>> writesMap = log.writes.get(core);
              Collection<$Impl> writes = null;
              if (writesMap != null) {
                writes = new ArrayList<$Impl>(writesMap.size());
                for (Map.Entry<Long, Pair<$Impl, Integer>> entry : writesMap
                    .entrySet()) {
                  if (!createsMap.containsKey(entry.getKey()))
                    writes.add(entry.getValue().first);
                }
              }

              int transactionID =
                  core.prepareTransaction(creates, log.reads.get(core), writes);
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
              Map<Long, Pair<$Impl, Integer>> writes = log.writes.get(core);
              if (writes != null)
                for (Pair<$Impl, Integer> obj : writes.values())
                  obj.first.$version++;

              Map<Long, $Impl> creates = log.creates.get(core);
              if (creates != null) for ($Impl obj : creates.values())
                obj.$version = 1;
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
      throw e;
    }

    log = null;
  }

  /**
   * Determines whether there is a transaction running.
   */
  public final boolean inTransaction() {
    return log != null;
  }

  public void registerCreate($Impl obj) {
    // If we're not in a transaction, start one just for the object creation.
    boolean needTransaction = !inTransaction();
    if (needTransaction) startTransaction();

    // Register the object creation.
    log.creates.put(obj.$getCore(), obj.$getOnum(), obj);

    // Commit if we started a transaction for the object creation.
    if (needTransaction) commitTransaction();
  }

  public void registerRead($Impl obj) throws VersionConflictException {
    // Nothing to do if we're not in a transaction.
    if (!inTransaction()) return;

    // Nothing to do if we created the object ourselves. (If this is the case,
    // we will be working on version 0 of the object.)
    if (obj.$version == 0) return;

    Core core = obj.$getCore();
    long onum = obj.$getOnum();

    Integer otherVersionNum = log.reads.get(core, onum);
    if (otherVersionNum == null) {
      log.reads.put(core, onum, obj.$getVersion());
      return;
    }

    if (otherVersionNum.intValue() != obj.$getVersion())
      throw new VersionConflictException(obj);
  }

  /**
   * This should be called <i>before</i> the object is modified.
   * 
   * @return whether a new (top-level) transaction was created.
   */
  public boolean registerWrite($Impl obj) {
    // Ensure that we're running in a transaction.
    boolean needTransaction = !inTransaction();
    if (needTransaction) startTransaction();

    // Make sure the object hasn't already been modified or created during the
    // current transaction.
    Core core = obj.$getCore();
    long onum = obj.$getOnum();

    // If we're modifying a pre-existing object, log the write.
    Set<Long> onums = undoLog.creates.get(core);
    if (onums == null || !onums.contains(onum)) {
      undoLog.writes.put(core, onum, obj.clone());

      Pair<$Impl, Integer> pair = log.writes.get(core, onum);
      if (pair != null)
        pair.second++;
      else log.writes.put(core, onum, new Pair<$Impl, Integer>(obj, 1));
    }

    return needTransaction;
  }

  public void startTransaction() {
    frames.push(undoLog);

    if (undoLog == null)
      undoLog = new UndoLog();
    else undoLog = undoLog.startSublog();

    if (log == null) log = new TransactionLog();
  }
}
