package fabric.client;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import fabric.common.Concurrent2KeyHashMap;
import fabric.common.FabricException;
import fabric.common.Pair;
import fabric.lang.Object.$Impl;
import fabric.lang.Object.$Proxy;

public final class TransactionManager {
  private static final class Log {
    /**
     * Maps OIDs to objects that have been created.
     */
    protected Concurrent2KeyHashMap<Core, Long, $Impl> creates;

    /**
     * Maps OIDs to the version number that was read.
     */
    protected Concurrent2KeyHashMap<Core, Long, Integer> reads;

    /**
     * Maps OIDs to pairs of $Impls. The first $Impl is a copy of the state of
     * the object at the start of the transaction. The second is a pointer to
     * the $Impl being used by the application.
     */
    protected Concurrent2KeyHashMap<Core, Long, Pair<$Impl, $Impl>> writes;

    /**
     * @param log
     *          The outer transaction's log. Can be null if creating the log for
     *          a top-level transaction.
     */
    private Log(Log log) {
      if (log != null) {
        this.reads = log.reads.clone();
      } else {
        this.reads = new Concurrent2KeyHashMap<Core, Long, Integer>();
      }
      this.writes = new Concurrent2KeyHashMap<Core, Long, Pair<$Impl, $Impl>>();
      this.creates = new Concurrent2KeyHashMap<Core, Long, $Impl>();
    }

    /**
     * Incorporates the reads, creates, and writes in the given log into this
     * log.
     */
    protected void merge(Log log) {
      // Copy in the other log's reads. Ignore any reads on objects that we've
      // created.
      for (Core core : log.reads.keySet()) {
        for (Map.Entry<Long, Integer> entry : log.reads.get(core).entrySet()) {
          long oid = entry.getKey();
          if (!creates.containsKey(core, oid))
            reads.put(core, oid, entry.getValue());
        }
      }

      // Copy in the other log's creates.
      creates.putAll(log.creates);

      // Add writes from the other log. Ignore any writes to objects that we've
      // created or have already written to.
      for (Core core : log.writes.keySet()) {
        for (Map.Entry<Long, Pair<$Impl, $Impl>> entry : log.writes.get(core)
            .entrySet()) {
          long oid = entry.getKey();
          if (!creates.containsKey(core, oid) && !writes.containsKey(core, oid))
            writes.put(core, oid, entry.getValue());
        }
      }
    }
  }

  public static final class VersionConflictException extends FabricException {
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

  private final Stack<Log> frames;

  private TransactionManager() {
    this.frames = new Stack<Log>();
  }

  public void abortTransaction() {
    // Restore the state of all objects that were modified during the aborted
    // transaction.
    for (Pair<$Impl, $Impl> value : curFrame.writes.values()) {
      value.second.$copyStateFrom(value.first);
    }

    curFrame = frames.pop();
  }

  public void commitTransaction() throws TransactionPrepareFailedException {
    // XXX This is a long and ugly method. Refactor?
    Log parentFrame = frames.pop();
    if (parentFrame != null) {
      // Merge the current transaction with its parent.
      parentFrame.merge(curFrame);
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
              int transactionID = core.beginTransaction();
              transactionIDs.put(core, transactionID);

              Map<Long, $Impl> createsMap = curFrame.creates.get(core);
              Collection<$Impl> creates = null;
              if (createsMap != null) creates = createsMap.values();

              Map<Long, Pair<$Impl, $Impl>> writesMap =
                  curFrame.writes.get(core);
              Collection<$Impl> writes = null;
              if (writesMap != null) {
                writes = new ArrayList<$Impl>(writesMap.size());
                for (Pair<$Impl, $Impl> value : writesMap.values()) {
                  writes.add(value.second);
                }
              }

              core.prepareTransaction(transactionID, creates, curFrame.reads
                  .get(core), writes);
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
              Map<Long, Pair<$Impl, $Impl>> writes = curFrame.writes.get(core);
              if (writes != null)
                for (Pair<$Impl, $Impl> pair : writes.values())
                  pair.second.$version++;
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
      frames.push(null);
      abortTransaction();
      throw e;
    }

    curFrame = null;
  }

  /**
   * Determines whether there is a transaction running.
   */
  public boolean inTransaction() {
    return curFrame != null;
  }

  public void registerCreate($Impl obj) {
    // If we're not in a transaction, start one just for the object creation.
    boolean needTransaction = !inTransaction();
    if (needTransaction) startTransaction();

    // Register the object creation.
    curFrame.creates.put(obj.$getCore(), obj.$getOnum(), obj);

    // Commit if we started a transaction for the object creation.
    if (needTransaction) commitTransaction();
  }

  public void registerRead($Impl obj) throws VersionConflictException {
    // Nothing to do if we're not in a transaction.
    if (!inTransaction()) return;

    Core core = obj.$getCore();
    long onum = obj.$getOnum();

    // Nothing to do if we created the object ourselves.
    if (curFrame.creates.containsKey(core, onum)) return;

    if (curFrame.reads.containsKey(core, onum)
        && curFrame.reads.get(core, onum) != obj.$getVersion())
      throw new VersionConflictException(obj);

    curFrame.reads.put(core, onum, obj.$getVersion());
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

    if (curFrame.creates.containsKey(core, onum)
        || curFrame.writes.containsKey(core, onum)) return needTransaction;

    // First time modifying the object. Save a copy of the object and a pointer
    // to the actual working copy of the object.
    curFrame.writes.put(core, onum, new Pair<$Impl, $Impl>(obj.clone(), obj));

    return needTransaction;
  }

  public void startTransaction() {
    frames.push(curFrame);
    curFrame = new Log(curFrame);
  }
}
