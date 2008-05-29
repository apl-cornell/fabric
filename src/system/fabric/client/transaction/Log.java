package fabric.client.transaction;

import java.util.*;

import fabric.client.Core;
import fabric.client.transaction.LockList.Node;
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
   * TODO: DOCO
   */
  protected List<Pair<LockList.Node<Log>, ReadMapEntry>> readsReadByParent;

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

    this.children = Collections.synchronizedSet(new HashSet<Log>());
    this.threads = new HashSet<Thread>();
    this.abortSignal = false;
    this.reads = new OidKeyHashMap<Pair<LockList.Node<Log>, ReadMapEntry>>();
    this.readsReadByParent =
        new ArrayList<Pair<LockList.Node<Log>, ReadMapEntry>>();
    this.creates = new ArrayList<$Impl>();
    this.writes = new ArrayList<$Impl>();
  }

  /**
   * Returns true iff the given Log is in the ancestry of this log.
   */
  boolean isDescendantOf(Log log) {
    Log cur = this;
    while (cur != null) {
      if (cur == log) return true;
      cur = cur.parent;
    }

    return false;
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
    for (LongKeyMap<Pair<LockList.Node<Log>, ReadMapEntry>> submap : reads) {
      for (Pair<LockList.Node<Log>, ReadMapEntry> entry : submap.values()) {
        entry.second.releaseLock(entry.first);
      }
    }

    for (Pair<LockList.Node<Log>, ReadMapEntry> entry : readsReadByParent)
      entry.second.releaseLock(entry.first);

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
    for (LongKeyMap<Pair<LockList.Node<Log>, ReadMapEntry>> submap : reads) {
      for (Pair<LockList.Node<Log>, ReadMapEntry> entry : submap.values()) {
        parent.transferReadLock(entry);
      }
    }

    int size = readsReadByParent.size();
    for (int i = 0; i < size; i++) {
      Pair<LockList.Node<Log>, ReadMapEntry> entry = readsReadByParent.get(i);
      entry.second.releaseLock(entry.first);
    }

    // Merge writes and transfer write locks.
    List<$Impl> parentWrites = parent.writes;
    size = writes.size();
    for (int i = 0; i < size; i++) {
      $Impl obj = writes.get(i);
      synchronized (obj) {
        if (obj.$history.$writeLockHolder == parent) {
          // The parent transaction already wrote to the object. Discard one
          // layer of history. In doing so, we also end up releasing this
          // transaction's write lock.
          obj.$history = obj.$history.$history;
        } else {
          // The parent transaction didn't write to the object. Add write to
          // parent and transfer our write lock.
          synchronized (parentWrites) {
            parentWrites.add(obj);
          }
        }
        obj.$writer = null;
        obj.$writeLockHolder = parent;

        // Signal any readers/writers.
        if (obj.$numWaiting > 0) obj.notifyAll();
      }
    }

    // Merge creates and transfer write locks.
    List<$Impl> parentCreates = parent.creates;
    size = creates.size();
    synchronized (parentCreates) {
      for (int i = 0; i < size; i++) {
        $Impl obj = creates.get(i);
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
    for (LongKeyMap<Pair<LockList.Node<Log>, ReadMapEntry>> submap : reads) {
      for (Pair<LockList.Node<Log>, ReadMapEntry> entry : submap.values()) {
        entry.second.releaseLock(entry.first);
      }
    }

    for (Pair<LockList.Node<Log>, ReadMapEntry> entry : readsReadByParent)
      entry.second.releaseLock(entry.first);

    // Release write locks and update version numbers.
    for ($Impl obj : writes) {
      synchronized (obj) {
        obj.$writer = null;
        obj.$writeLockHolder = null;
        obj.$version++;
        obj.$readMapEntry.versionNumber++;

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
      obj.$readMapEntry.versionNumber = 1;
    }
  }

  /**
   * Transfers a read lock from a child transaction.
   */
  private void transferReadLock(Pair<Node<Log>, ReadMapEntry> childEntry) {
    ReadMapEntry readMapEntry = childEntry.second;

    // If we already have a read lock, return; otherwise, register a read lock.
    boolean lockedByAncestor = false;
    synchronized (readMapEntry) {
      // Release child's read lock.
      readMapEntry.readLocks.remove(childEntry.first);

      // Scan the list for an existing read lock. At the same time, check if
      // any of our ancestors already has a read lock.
      LockList.Node<Log> cur = readMapEntry.readLocks.head;
      while (cur != null) {
        if (cur.data == this) {
          // We already have a lock; nothing to do.
          return;
        }

        if (!lockedByAncestor && isDescendantOf(cur.data))
          lockedByAncestor = true;

        cur = cur.next;
      }

      childEntry.first.data = this;
      readMapEntry.readLocks.add(childEntry.first);
    }

    // Only record the read in this transaction if none of our ancestors have
    // read this object.
    if (!lockedByAncestor) {
      synchronized (reads) {
        reads.put(readMapEntry.obj.core, readMapEntry.obj.onum, childEntry);
      }
    } else {
      readsReadByParent.add(childEntry);
    }

    // Signal any readers/writers and clear the $reader stamp.
    readMapEntry.signalObject();
  }

  /**
   * Grabs a read lock for the given object.
   */
  void acquireReadLock($Impl obj) {
    // If we already have a read lock, return; otherwise, register a read
    // lock.
    ReadMapEntry readMapEntry = obj.$readMapEntry;
    LockList.Node<Log> lockListNode;
    boolean lockedByAncestor = false;
    synchronized (readMapEntry) {
      // Scan the list for an existing read lock. At the same time, check if
      // any of our ancestors already has a read lock.
      LockList.Node<Log> cur = readMapEntry.readLocks.head;
      while (cur != null) {
        if (cur.data == this) {
          // We already have a lock; nothing to do.
          return;
        }

        if (!lockedByAncestor && isDescendantOf(cur.data))
          lockedByAncestor = true;

        cur = cur.next;
      }

      lockListNode = readMapEntry.readLocks.add(this);
    }

    if (obj.$writer != this) {
      // Clear the object's write stamp -- the writer's write condition no
      // longer holds.
      obj.$writer = null;
    }

    // Only record the read in this transaction if none of our ancestors have
    // read this object.
    if (!lockedByAncestor) {
      synchronized (reads) {
        reads.put(obj.$ref.core, obj.$ref.onum,
            new Pair<LockList.Node<Log>, ReadMapEntry>(lockListNode,
                readMapEntry));
      }
    } else {
      readsReadByParent.add(new Pair<LockList.Node<Log>, ReadMapEntry>(
          lockListNode, readMapEntry));
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
