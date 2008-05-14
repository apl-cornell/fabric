package fabric.client.transaction;

import java.util.*;

import fabric.client.Core;
import fabric.common.Pair;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object.$Impl;

/**
 * Stores per-transaction information. Records the objects that are created,
 * read, and written during a single nested transaction.
 */
public final class Log {
  /**
   * The log for the parent transaction, or null if there is none (i.e., this is
   * the log for a top-level transaction).
   */
  final Log parent;

  /**
   * The set of ancestors for this log. This is created lazily.
   */
  private List<Log> ancestors;

  /**
   * The set of sub-transactions.
   */
  private final Set<Log> children;

  /**
   * The set of threads started at this transaction level (and are still
   * running).
   */
  final Set<Thread> threads;

  /**
   * A flag indicating whether this transaction should abort. This flag should
   * be checked before each operation.
   */
  boolean abortSignal;

  /**
   * Maps OIDs to <code>readMap</code> entries for objects read in this
   * transaction or completed sub-transactions. Reads from running or aborted
   * sub-transactions don't count here. <code>readMap</code> entries are
   * paired with the specific <code>LockList.Node</code> containing the read
   * lock for this transaction.
   */
  // Proxy objects aren't used here because doing so would result in calls to
  // hashcode() and equals() on such objects, resulting in fetching the
  // corresponding Impls from the core.
  protected OidKeyHashMap<Pair<LockList.Node<Log>, ReadMapEntry>> reads;

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
   * Creates a nested transaction whose parent is the transaction with the given
   * log. The created transaction log is added to the parent's children.
   * 
   * @param parent
   *                the log for the parent transaction or null if creating the
   *                log for a top-level transaction.
   */
  Log(Log parent) {
    this.parent = parent;
    if (parent != null) {
      parent.children.add(this);
    }

    this.ancestors = null;
    this.children = Collections.synchronizedSet(new HashSet<Log>());
    this.threads = new HashSet<Thread>();
    this.abortSignal = false;
    this.reads = new OidKeyHashMap<Pair<LockList.Node<Log>, ReadMapEntry>>();
    this.creates = new ArrayList<$Impl>();
    this.writes = new ArrayList<$Impl>();
  }

  List<Log> ancestors() {
    synchronized (this) {
      if (ancestors == null) {
        List<Log> ancestors = new ArrayList<Log>();
        Log ancestor = this;
        while (ancestor != null) {
          ancestors.add(ancestor);
          ancestor = ancestor.parent;
        }

        this.ancestors = Collections.unmodifiableList(ancestors);
      }
    }

    return ancestors;
  }

  /**
   * Returns a set of cores affected by this transaction.
   */
  Set<Core> coresToContact() {
    Set<Core> result = new HashSet<Core>();
    result.addAll(reads.coreSet());
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
  LongKeyMap<Integer> getReadsForCore(Core core) {
    LongKeyMap<Integer> result = new LongKeyHashMap<Integer>();
    LongKeyMap<Pair<LockList.Node<Log>, ReadMapEntry>> submap = reads.get(core);
    if (submap == null) return result;

    for (LongKeyMap.Entry<Pair<LockList.Node<Log>, ReadMapEntry>> entry : submap
        .entrySet()) {
      result.put(entry.getKey(), entry.getValue().second.versionNumber);
    }

    for ($Impl create : creates)
      result.remove(create.$getOnum());

    return result;
  }

  /**
   * Returns a collection of objects modified at the given core. Writes on
   * created objects are not included.
   */
  Collection<$Impl> getWritesForCore(Core core) {
    // This should be a Set of $Impl, but we have a map indexed by OID to
    // avoid calling methods on the $Impls.
    Map<Pair<Core, Long>, $Impl> result =
        new HashMap<Pair<Core, Long>, $Impl>();
    for ($Impl obj : writes)
      if (obj.$getCore() == core)
        result.put(new Pair<Core, Long>(obj.$getCore(), obj.$getOnum()), obj);

    for ($Impl create : creates)
      result.remove(new Pair<Core, Long>(create.$getCore(), create.$getOnum()));

    return result.values();
  }

  /**
   * Returns a collection of objects created at the given core.
   */
  Collection<$Impl> getCreatesForCore(Core core) {
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
  void abort() {
    // Release read locks.
    for (Iterator<Pair<LockList.Node<Log>, ReadMapEntry>> it =
        reads.valueIterator(); it.hasNext();) {
      Pair<LockList.Node<Log>, ReadMapEntry> entry = it.next();
      entry.second.releaseLock(entry.first);
    }

    // Roll back writes and release write locks.
    for ($Impl write : writes) {
      synchronized (write) {
        write.$copyStateFrom(write.$history);

        // Signal any waiting readers/writers.
        if (write.$numWaiting > 0) write.notifyAll();
      }
    }

    if (parent != null) {
      parent.children.remove(this);
    }
  }

  /**
   * Updates logs and data structures in <code>$Impl</code>s to commit this
   * transaction. Assumes there is a parent transaction. This transaction log is
   * merged into the parent's log and any locks held are transferred to the
   * parent.
   */
  void commitNested() {
    // Merge reads and transfer read locks.
    OidKeyHashMap<Pair<LockList.Node<Log>, ReadMapEntry>> parentReads =
        parent.reads;

    synchronized (parentReads) {
      for (Iterator<Pair<LockList.Node<Log>, ReadMapEntry>> it =
          reads.valueIterator(); it.hasNext();) {
        Pair<LockList.Node<Log>, ReadMapEntry> entry = it.next();
        ReadMapEntry readMapEntry = entry.second;

        synchronized (readMapEntry) {
          readMapEntry.readLocks.remove(entry.first);
          entry.first = readMapEntry.readLocks.addOrGet(parent);
        }

        parentReads.put(readMapEntry.core, readMapEntry.onum, entry);

        // Signal any readers/writers.
        readMapEntry.signalObject();
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
          if (obj.$numWaiting > 0) obj.notifyAll();
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
  void commitTopLevel() {
    // Release read locks.
    for (Iterator<Pair<LockList.Node<Log>, ReadMapEntry>> it =
        reads.valueIterator(); it.hasNext();) {
      Pair<LockList.Node<Log>, ReadMapEntry> entry = it.next();
      entry.second.releaseLock(entry.first);
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
        if (obj.$numWaiting > 0) obj.notifyAll();
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
  void waitForThreads() {
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
