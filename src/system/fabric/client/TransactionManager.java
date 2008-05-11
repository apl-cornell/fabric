// TODO check lock ordering
// TODO somehow hook this into everything

package fabric.client;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import fabric.common.FabricRuntimeException;
import fabric.common.InternalError;
import fabric.common.Pair;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object.$Impl;
import fabric.lang.Object.$Proxy;

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
   * Stores per-transaction information. Records the objects that are created,
   * read, and written during a single nested transaction.
   */
  public static final class Log {
    /**
     * The log for the parent transaction, or null if there is none (i.e., this
     * is the log for a top-level transaction).
     */
    private final Log parent;

    /**
     * The set of ancestors for this log. This is created lazily.
     */
    private Set<Log> ancestors;

    /**
     * The set of sub-transactions.
     */
    private final Set<Log> children;

    /**
     * The set of threads started at this transaction level (and are still
     * running).
     */
    private final Set<Thread> threads;

    /**
     * A flag indicating whether this transaction should abort. This flag should
     * be checked before each operation.
     */
    private boolean abortSignal;

    /**
     * Maps OIDs to <code>readList</code> entries for objects read in this
     * transaction or completed sub-transactions. Reads from running or aborted
     * sub-transactions don't count here.
     */
    // Proxy objects aren't used here because doing so would result in calls to
    // hashcode() and equals() on such objects, resulting in fetching the
    // corresponding Impls from the core.
    protected Map<Pair<Core, Long>, ReadListEntry> reads;

    /**
     * A collection of all objects created in this transaction or completed
     * sub-transactions. Objects created in running or aborted sub-transactions
     * don't count here.
     */
    protected List<$Impl> creates;

    /**
     * A collection of all objects modified in this transaction or completed
     * sub-transactions. Objects modified in running or aborted sub-transactions
     * don't count here.
     */
    protected List<$Impl> writes;

    /**
     * Creates a nested transaction whose parent is the transaction with the
     * given log. The created transaction log is added to the parent's children.
     * 
     * @param parent
     *                the log for the parent transaction or null if creating the
     *                log for a top-level transaction.
     */
    private Log(Log parent) {
      this.parent = parent;
      if (parent != null) {
        parent.children.add(this);
      }

      this.ancestors = null;
      this.children = Collections.synchronizedSet(new HashSet<Log>());
      this.threads = new HashSet<Thread>();
      this.abortSignal = false;
      this.reads = new HashMap<Pair<Core, Long>, ReadListEntry>();
      this.creates = new ArrayList<$Impl>();
      this.writes = new ArrayList<$Impl>();
    }

    private Set<Log> ancestors() {
      synchronized (this) {
        if (ancestors == null) {
          Set<Log> ancestors = new HashSet<Log>();
          Log ancestor = this;
          while (ancestor != null) {
            ancestors.add(ancestor);
            ancestor = ancestor.parent;
          }

          this.ancestors = Collections.unmodifiableSet(ancestors);
        }
      }

      return ancestors;
    }

    /**
     * Returns a set of cores affected by this transaction.
     */
    private Set<Core> coresToContact() {
      Set<Core> result = new HashSet<Core>();
      for (Pair<Core, Long> oid : reads.keySet())
        result.add(oid.first);
      for ($Impl obj : writes)
        result.add(obj.$getCore());
      for ($Impl obj : creates)
        result.add(obj.$getCore());
      return result;
    }

    /**
     * Returns a map from onums to version numbers of objects read at the given
     * core. Reads on created objects are not included.
     */
    private LongKeyMap<Integer> getReadsForCore(Core core) {
      LongKeyMap<Integer> result = new LongKeyHashMap<Integer>();
      for (Map.Entry<Pair<Core, Long>, ReadListEntry> entry : reads.entrySet()) {
        Pair<Core, Long> oid = entry.getKey();
        if (oid.first == core) {
          result.put(oid.second, entry.getValue().versionNumber);
        }
      }

      for ($Impl create : creates)
        result.remove(create.$getOnum());

      return result;
    }

    /**
     * Returns a collection of objects modified at the given core. Writes on
     * created objects are not included.
     */
    private Collection<$Impl> getWritesForCore(Core core) {
      // This should be a Set of $Impl, but we have a map indexed by OID to
      // avoid calling methods on the $Impls.
      Map<Pair<Core, Long>, $Impl> result =
          new HashMap<Pair<Core, Long>, $Impl>();
      for ($Impl obj : writes)
        if (obj.$getCore() == core)
          result.put(new Pair<Core, Long>(obj.$getCore(), obj.$getOnum()), obj);

      for ($Impl create : creates)
        result
            .remove(new Pair<Core, Long>(create.$getCore(), create.$getOnum()));

      return result.values();
    }

    /**
     * Returns a collection of objects created at the given core.
     */
    private Collection<$Impl> getCreatesForCore(Core core) {
      // This should be a Set of $Impl, but to avoid calling methods on the
      // $Impls, we instead use a map keyed on OID.
      Map<Pair<Core, Long>, $Impl> result =
          new HashMap<Pair<Core, Long>, $Impl>();
      for ($Impl obj : creates)
        if (obj.$getCore() == core)
          result.put(new Pair<Core, Long>(obj.$getCore(), obj.$getOnum()), obj);

      return result.values();
    }

    /**
     * Sets the abort flag on this and the logs of all sub-transactions.
     */
    public void flagAbort() {
      Queue<Log> toFlag = new LinkedList<Log>();
      toFlag.add(this);
      while (!toFlag.isEmpty()) {
        Log log = toFlag.remove();
        synchronized (log) {
          toFlag.addAll(log.children);
          log.abortSignal = true;
        }
      }
    }

    /**
     * Updates logs and data structures in <code>$Impl</code>s to abort this
     * transaction. All locks held by this transaction are released.
     */
    private void abort() {
      // Release read locks.
      for (ReadListEntry readListEntry : reads.values()) {
        readListEntry.releaseLock(this);
      }

      // Roll back writes and release write locks.
      for ($Impl write : writes) {
        synchronized (write) {
          write.$copyStateFrom(write.$history);

          // Signal any waiting readers/writers.
          write.notifyAll();
        }
      }

      if (parent != null) {
        parent.children.remove(this);
      }
    }

    /**
     * Updates logs and data structures in <code>$Impl</code>s to commit this
     * transaction. Assumes there is a parent transaction. This transaction log
     * is merged into the parent's log and any locks held are transferred to the
     * parent.
     */
    private void commitNested() {
      // Merge reads and transfer read locks.
      Map<Pair<Core, Long>, ReadListEntry> parentReads = parent.reads;
      synchronized (parentReads) {
        for (ReadListEntry readListEntry : reads.values()) {
          parentReads.put(readListEntry.oid, readListEntry);

          synchronized (readListEntry) {
            readListEntry.readLocks.remove(this);
            readListEntry.readLocks.add(parent);
          }

          // Signal any readers/writers.
          readListEntry.signalObject();
        }
      }

      // Merge writes and transfer write locks.
      List<$Impl> parentWrites = parent.writes;
      synchronized (parentWrites) {
        for ($Impl obj : writes) {
          synchronized (obj) {
            if (obj.$history.$writeLockHolder == parent) {
              // The parent transaction already wrote to the object. Discard one
              // layer of history. In doing so, we also end up releasing this
              // transaction's write lock.
              obj.$history = obj.$history.$history;
            } else {
              // The parent transaction didn't write to the object. Add write to
              // parent and transfer our write lock.
              parentWrites.add(obj);
            }
            obj.$writeLockHolder = parent;

            // Signal any readers/writers.
            obj.notifyAll();
          }
        }
      }

      // Merge creates and transfer write locks.
      List<$Impl> parentCreates = parent.creates;
      synchronized (parentCreates) {
        for ($Impl obj : creates) {
          parentCreates.add(obj);
          obj.$writeLockHolder = parent;
        }
      }

      parent.children.remove(this);
    }

    /**
     * Updates logs and data structures in <code>$Impl</code>s to commit this
     * transaction. Assumes this is a top-level transaction. All locks held by
     * this transaction are released.
     */
    private void commitTopLevel() {
      // Release read locks.
      for (ReadListEntry readListEntry : reads.values()) {
        readListEntry.releaseLock(this);
      }

      // Release write locks and update version numbers.
      for ($Impl obj : writes) {
        synchronized (obj) {
          obj.$writeLockHolder = null;
          obj.$version++;
          obj.$readListEntry.versionNumber++;

          // Discard one layer of history.
          obj.$history = obj.$history.$history;

          // Signal any waiting readers/writers.
          obj.notifyAll();
        }
      }

      // Release write locks on created objects and set version numbers.
      for ($Impl obj : creates) {
        obj.$writeLockHolder = null;
        obj.$version = 1;
        obj.$readListEntry.versionNumber = 1;
      }
    }

    /**
     * Blocks until all threads in <code>threads</code> are finished.
     */
    private void waitForThreads() {
      while (true) {
        Thread thread;
        synchronized (threads) {
          if (threads.isEmpty()) return;
          thread = threads.iterator().next();
        }
        try {
          thread.join();
          synchronized (threads) {
            threads.remove(thread);
          }
        } catch (InterruptedException e) {
        }
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
  private static final Map<Pair<Core, Long>, ReadListEntry> readList =
      new HashMap<Pair<Core, Long>, ReadListEntry>();

  public static final class ReadListEntry {
    private Pair<Core, Long> oid;
    private SoftReference<$Impl> obj;
    private Set<Log> readLocks;
    private int versionNumber;
    private int pinCount;

    private ReadListEntry($Impl obj) {
      this.oid = new Pair<Core, Long>(obj.$getCore(), obj.$getOnum());
      this.obj = new SoftReference<$Impl>(obj);
      this.readLocks = new HashSet<Log>();
      this.versionNumber = obj.$version;
      this.pinCount = 1;
    }

    /**
     * Removes the lock owned by the given transaction log.
     */
    private void releaseLock(Log lockOwner) {
      synchronized (this) {
        readLocks.remove(lockOwner);

        if (readLocks.isEmpty() && pinCount == 0) {
          // There are no read locks and no references to this entry. Garbage
          // collect.
          synchronized (readList) {
            readList.remove(oid);
          }
        }
      }

      signalObject();
    }

    /**
     * Signals the object corresponding to this entry (if the object is resident
     * in memory).
     */
    private void signalObject() {
      $Impl obj = this.obj.get();
      if (obj == null) {
        obj = oid.first.readObjectFromCache(oid.second);
        if (obj == null) return;

        synchronized (this) {
          this.obj = new SoftReference<$Impl>(obj);
        }
      }

      synchronized (obj) {
        obj.notifyAll();
      }
    }
  }

  public static ReadListEntry getReadListEntry($Impl impl) {
    ReadListEntry result;

    synchronized (readList) {
      Pair<Core, Long> oid =
          new Pair<Core, Long>(impl.$getCore(), impl.$getOnum());
      result = readList.get(oid);
      if (result == null) {
        result = new ReadListEntry(impl);
        readList.put(result.oid, result);
        return result;
      }
    }

    synchronized (result) {
      result.obj = new SoftReference<$Impl>(impl);
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

  private static final Map<Thread, TransactionManager> txmanMap =
      Collections.synchronizedMap(new HashMap<Thread, TransactionManager>());

  public static TransactionManager getInstance() {
    Thread thread = Thread.currentThread();
    TransactionManager result = txmanMap.get(thread);
    if (result == null) {
      result = new TransactionManager();
      txmanMap.put(thread, result);
    }
    return result;
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

    // Ensure we're in a transaction if we're creating a remote object.
    if (current == null && obj.$getCore() instanceof RemoteCore) {
      throw new InternalError(
          "Cannot create a remote object outside of a transactional context.");
    }

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

    // Make sure we're not supposed to abort.
    checkAbortSignal();

    synchronized (obj) {
      // Nothing to do if the object's $reader is us.
      if (obj.$reader == current) return;

      // Check read condition: wait until all writers are in our ancestry.
      Set<Log> ancestors = current.ancestors();
      while (obj.$writeLockHolder != null
          && !ancestors.contains(obj.$writeLockHolder)) {
        try {
          obj.wait();
        } catch (InterruptedException e) {
        }

        // Make sure we weren't aborted while we were waiting.
        checkAbortSignal();
      }

      // Set the object's reader stamp to the current transaction.
      obj.$reader = current;

      // If we already have a read lock, return; otherwise, register a read
      // lock.
      ReadListEntry readListEntry = obj.$readListEntry;
      synchronized (readListEntry) {
        if (!readListEntry.readLocks.add(current)) return;
      }

      if (obj.$writer != current) {
        // Clear the object's write stamp -- the writer's write condition no
        // longer holds.
        obj.$writer = null;
      }

      // Record the read in this transaction.
      synchronized (current.reads) {
        current.reads.put(new Pair<Core, Long>(obj.$getCore(), obj.$getOnum()),
            readListEntry);
      }
    }
  }

  /**
   * This should be called <i>before</i> the object is modified.
   * 
   * @return whether a new (top-level) transaction was created.
   */
  public boolean registerWrite($Impl obj) {
    boolean needTransaction = current == null;
    if (needTransaction) {
      startTransaction();
    } else {
      // Make sure we're not supposed to abort.
      checkAbortSignal();
    }

    synchronized (obj) {
      // Nothing to do if the write stamp is us.
      if (obj.$writer == current) return needTransaction;

      // Check write condition: wait until writer and all readers are in our
      // ancestry.
      Set<Log> ancestors = current.ancestors();
      while (true) {
        if (obj.$writeLockHolder == null
            || ancestors.contains(obj.$writeLockHolder)) {
          // Writer is in our ancestry. Check readers.
          ReadListEntry readListEntry = obj.$readListEntry;
          if (readListEntry == null) break;
          synchronized (readListEntry) {
            if (ancestors.containsAll(readListEntry.readLocks)) break;
          }
        }

        try {
          obj.wait();
        } catch (InterruptedException e) {
        }

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
   * Registers that a new thread has been started. Returns a new
   * TransactionManager for the new thread to use.
   */
  public TransactionManager registerThread(Thread thread) {
    TransactionManager result = new TransactionManager(this);
    synchronized (current.threads) {
      current.threads.add(thread);
    }
    txmanMap.put(thread, result);
    return result;
  }

  /**
   * Registers that a thread has finished.
   */
  public void deregisterThread(Thread thread) {
    txmanMap.remove(thread);
    synchronized (current.threads) {
      current.threads.remove(thread);
    }
  }
}
