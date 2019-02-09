package fabric.worker.transaction;

import static fabric.common.Logging.HOTOS_LOGGER;
import static fabric.common.Logging.WORKER_DEADLOCK_LOGGER;
import static fabric.common.Logging.WORKER_TRANSACTION_LOGGER;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import fabric.common.Logging;
import fabric.common.SerializedObject;
import fabric.common.SysUtil;
import fabric.common.Threading;
import fabric.common.Timing;
import fabric.common.TransactionID;
import fabric.common.VersionAndExpiry;
import fabric.common.exceptions.NotImplementedException;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Oid;
import fabric.common.util.OidHashSet;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.common.util.Triple;
import fabric.common.util.WeakReferenceArrayList;
import fabric.lang.Object._Impl;
import fabric.lang.security.LabelCache;
import fabric.lang.security.SecurityCache;
import fabric.metrics.DerivedMetric;
import fabric.metrics.Metric;
import fabric.metrics.treaties.Treaty;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.metrics.util.TreatiesBox;
import fabric.worker.FabricSoftRef;
import fabric.worker.RemoteStore;
import fabric.worker.Store;
import fabric.worker.TransactionAbortingException;
import fabric.worker.TransactionRestartingException;
import fabric.worker.Worker;
import fabric.worker.metrics.ExpiryExtension;
import fabric.worker.metrics.treaties.TreatySet;
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.WriterMap;

/**
 * Stores per-transaction information. Records the objects that are created,
 * read, and written during a single nested transaction.
 */
public final class Log {
  public static final Log NO_READER = new Log((Log) null);

  /**
   * The transaction ID for this log.
   */
  TransactionID tid;

  /**
   * The log for the parent transaction, or null if there is none. A null value
   * here does not necessarily mean that this is the top-level transaction. The
   * tid should be checked to determine whether this transaction is top-level.
   */
  final Log parent;

  /**
   * A map indicating where to fetch objects from.
   */
  WriterMap writerMap;

  /**
   * The sub-transaction.
   */
  private Log child;

  /**
   * The thread that is running this transaction.
   */
  Thread thread;

  /**
   * The TxnStats associated with this transaction.
   */
  TxnStats stats;

  /**
   * Prepare object associated with this log.  Allows for remote workers to
   * initiate second phase of commit protocol through log.
   */
  TransactionPrepare prepare;

  /**
   * A flag indicating whether this transaction should abort or be retried. This
   * flag should be checked before each operation. This flag is set when it's
   * non-null and indicates the transaction in the stack that is to be retried;
   * all child transactions are to be aborted.
   */
  volatile TransactionID retrySignal;

  /**
   * An exception with stack trace and reason for retry being signalled.  Null
   * if there's no retry being signalled.
   */
  volatile RetrySignalException retryCause;

  /**
   * Maps OIDs to <code>readMap</code> entries for objects read in this
   * transaction or completed sub-transactions. Reads from running or aborted
   * sub-transactions don't count here.
   */
  // Proxy objects aren't used for keys here because doing so would result in
  // calls to hashcode() and equals() on such objects, resulting in fetching the
  // corresponding Impls from the store.
  protected final OidKeyHashMap<ReadMap.Entry> reads;

  /**
   * Reads on objects that have been read by an ancestor transaction.
   */
  protected final List<ReadMap.Entry> readsReadByParent;

  /**
   * A collection of all objects created in this transaction or completed
   * sub-transactions. Objects created in running or aborted sub-transactions
   * don't count here. To keep them from being pinned, objects on local store
   * are not tracked here.
   */
  protected final OidKeyHashMap<_Impl> creates;

  /**
   * Tracks objects created on local store. See <code>creates</code>.
   */
  protected final WeakReferenceArrayList<_Impl> localStoreCreates;

  /**
   * A collection of all objects modified in this transaction or completed
   * sub-transactions. Objects modified in running or aborted sub-transactions
   * don't count here. To keep them from being pinned, objects on local store
   * are not tracked here.
   */
  protected final OidKeyHashMap<_Impl> writes;

  /**
   * Collection of {@link Subjects} in this transaction that need to be/have
   * been observed by {@link Observer}s before the transaction commits.
   */
  protected final OidKeyHashMap<Subject> unobservedSamples;

  /**
   * Flag indicating if we're in the middle of resolving observations (to avoid
   * infinite loop).
   */
  protected boolean resolving = false;

  /**
   * A collection of {@link TreatySet}s that are extended by this transaction
   */
  protected final OidKeyHashMap<ExpiryExtension> extendedTreaties;

  /**
   * A collection of {@link Oid}s that have treaties that should be extended
   * after this transaction, mapping to the objects that trigger this possible
   * extension.
   * TODO: Maybe index by treaty id as well.
   */
  protected final OidKeyHashMap<OidHashSet> delayedExtensions;

  /**
   * A collection of Oids that trigger delayedExtensions to run after this
   * transaction. (Acts as the reverse map for delayedExtensions.) Null key used
   * for those extensions which have no trigger.
   * TODO: Maybe specify treaty id in mapping target.
   */
  protected final OidKeyHashMap<OidHashSet> extensionTriggers;

  /**
   * Collection of checks that aren't currently treatied and should be checked
   * before the transaction finishes (aborting writes if they fail).
   */
  protected final Multimap<Metric._Proxy, TreatyStatement> untreatiedUpdateChecks;

  /**
   * Collection of checks that aren't currently treatied and should be checked
   * before the transaction finishes (aborting writes if they fail).
   */
  protected final OidKeyHashMap<Triple<Metric._Proxy, TreatyStatement, Long>> treatiedUpdateChecks;

  /**
   * Tracks objects on local store that have been modified. See
   * <code>writes</code>.
   */
  protected final WeakReferenceArrayList<_Impl> localStoreWrites;

  /**
   * The set of workers called by this transaction and completed
   * sub-transactions.
   */
  public final List<RemoteWorker> workersCalled;

  /**
   * Set of runnables to run if this transaction commits.
   */
  public final List<Runnable> commitHooks;

  /**
   * Indicates the state of commit for the top-level transaction.
   */
  public final CommitState commitState;

  public static class CommitState {
    public static enum Values {
      /**
       * Signifies a transaction before it has been prepared or aborted.
       */
      UNPREPARED,
      /**
       * Signifies a transaction that is currently being prepared.
       */
      PREPARING,
      /**
       * Signifies a transaction that has successfully prepared, but has not yet
       * been committed.
       */
      PREPARED,
      /**
       * Signifies a transaction that has failed to prepare, but has not yet
       * been rolled back.
       */
      PREPARE_FAILED,
      /**
       * Signifies a transaction that is currently being committed.
       */
      COMMITTING,
      /**
       * Signifies a transaction that has been committed.
       */
      COMMITTED,
      /**
       * Signifies a transaction that is currently being aborted.
       */
      ABORTING,
      /**
       * Signifies a transaction that has been aborted.
       */
      ABORTED
    }

    public Values value = Values.UNPREPARED;
  }

  public final AbstractSecurityCache securityCache;

  /**
   * The time at which this subtransaction was started.
   */
  public final long startTime;

  /**
   * Earliest expiry used in this transaction.
   */
  private long expiryToCheck = Long.MAX_VALUE;

  /**
   * If a transaction T's log appears in this set, then this transaction is
   * waiting for a lock that is held by transaction T.
   */
  private final Set<Log> waitsFor;

  /**
   * The lock, if any, this transaction is waiting on.
   */
  private Object waitsOn;

  /**
   * The condition and java lock, if any, this transaction is waiting on.
   */
  private Pair<Lock, Condition> waitsOnJavaCond;

  /**
   * Creates a new log with the given parent and the given transaction ID. The
   * TID for the parent and the given TID are assumed to be consistent. If the
   * given TID is null, a random tid is generated for the subtransaction.
   */
  Log(Log parent, TransactionID tid) {
    this.parent = parent;
    if (tid == null) {
      if (parent == null) {
        this.tid = new TransactionID();
      } else {
        this.tid = new TransactionID(parent.tid);
      }
    } else {
      this.tid = tid;
    }

    this.child = null;
    this.thread = Thread.currentThread();
    this.retrySignal = parent == null ? null : parent.retrySignal;
    this.retryCause = parent == null ? null : parent.retryCause;
    this.reads = new OidKeyHashMap<>();
    this.readsReadByParent = new ArrayList<>();
    this.creates = new OidKeyHashMap<>();
    this.localStoreCreates = new WeakReferenceArrayList<>();
    this.writes = new OidKeyHashMap<>();
    this.unobservedSamples = new OidKeyHashMap<>();
    this.extendedTreaties = new OidKeyHashMap<>();
    this.delayedExtensions = new OidKeyHashMap<>();
    this.extensionTriggers = new OidKeyHashMap<>();
    this.localStoreWrites = new WeakReferenceArrayList<>();
    this.workersCalled = new ArrayList<>();
    this.commitHooks = new ArrayList<>();
    this.startTime = System.currentTimeMillis();
    this.waitsFor = new HashSet<>();
    this.waitsOn = null;
    this.untreatiedUpdateChecks =
        TreeMultimap.create(new Comparator<Metric._Proxy>() {
          @Override
          public int compare(Metric._Proxy a, Metric._Proxy b) {
            int storeComp =
                a.$getStore().name().compareTo(b.$getStore().name());
            if (storeComp != 0) return storeComp;
            return Long.compare(a.$getOnum(), b.$getOnum());
          }
        }, new Comparator<TreatyStatement>() {
          @Override
          public int compare(TreatyStatement a, TreatyStatement b) {
            if (a instanceof ThresholdStatement) {
              if (b instanceof ThresholdStatement) {
                ThresholdStatement aT = (ThresholdStatement) a;
                ThresholdStatement bT = (ThresholdStatement) b;
                int rateComp = Double.compare(aT.rate(), bT.rate());
                if (rateComp != 0) return rateComp;
                return Double.compare(aT.base(), bT.base());
              } else {
                return 1;
              }
            } else {
              if (b instanceof ThresholdStatement) {
                EqualityStatement aT = (EqualityStatement) a;
                EqualityStatement bT = (EqualityStatement) b;
                return Double.compare(aT.value(), bT.value());
              } else {
                return -1;
              }
            }
          }
        });
    this.treatiedUpdateChecks = new OidKeyHashMap<>();

    if (parent != null) {
      this.unobservedSamples.putAll(parent.unobservedSamples);
      try {
        Timing.SUBTX.begin();
        this.writerMap = new WriterMap(parent.writerMap);
        synchronized (parent) {
          parent.child = this;
        }

        commitState = parent.commitState;
        this.securityCache =
            new SecurityCache((SecurityCache) parent.securityCache);
      } finally {
        Timing.SUBTX.end();
      }
      this.resolving = parent.resolving;
      stats = parent.stats;
    } else {
      this.writerMap = new WriterMap(this.tid.topTid);
      commitState = new CommitState();

      LabelCache labelCache = Worker.getWorker().labelCache;
      this.securityCache = new SecurityCache(labelCache);

      // New top-level frame. Register it in the transaction registry.
      TransactionRegistry.register(this);
      stats = TransactionManager.getInstance().stats;
    }
  }

  /**
   * Creates a nested transaction whose parent is the transaction with the given
   * log. The created transaction log is added to the parent's children.
   *
   * @param parent
   *          the log for the parent transaction or null if creating the log for
   *          a top-level transaction.
   */
  Log(Log parent) {
    this(parent, null);
  }

  /**
   * Creates a log with the given transaction ID.
   */
  public Log(TransactionID tid) {
    this(null, tid);
  }

  /**
   * Returns true iff the given Log is in the ancestry of (or is the same as)
   * this log.
   */
  boolean isDescendantOf(Log log) {
    return log != null && tid.isDescendantOf(log.tid);
  }

  /**
   * @return true if the transaction is not distributed and neither creates nor
   *         modifies objects on remote stores.
   */
  public boolean isReadOnly() {
    return writes.isEmpty() && creates.isEmpty() && workersCalled.isEmpty();
  }

  /**
   * Returns a set of stores affected by this transaction. This is the set of
   * stores to contact when preparing and committing a transaction.
   */
  Set<Store> storesToContact() {
    Set<Store> result = new HashSet<>();

    result.addAll(reads.storeSet());

    for (_Impl obj : writes.values()) {
      if (obj.$isOwned) result.add(obj.$getStore());
    }

    for (_Impl obj : creates.values()) {
      if (obj.$isOwned) result.add(obj.$getStore());
    }

    if (!localStoreWrites.isEmpty() || !localStoreCreates.isEmpty()) {
      result.add(Worker.getWorker().getLocalStore());
    }

    return result;
  }

  /**
   * @return a set of stores to contact when checking for object freshness.
   */
  Set<Store> storesToCheckFreshness() {
    Set<Store> result = new HashSet<>();
    result.addAll(reads.storeSet());
    for (ReadMap.Entry entry : readsReadByParent) {
      result.add(entry.getStore());
    }

    return result;
  }

  /**
   * Returns a map from onums to version numbers of objects read at the given
   * store. Reads on created objects are never included.
   *
   * @param includeModified
   *          whether to include reads on modified objects.
   */
  LongKeyMap<VersionAndExpiry> getReadsForStore(Store store,
      boolean includeModified) {
    LongKeyMap<VersionAndExpiry> result = new LongKeyHashMap<>();
    LongKeyMap<ReadMap.Entry> submap = reads.get(store);
    if (submap == null) return result;

    for (LongKeyMap.Entry<ReadMap.Entry> entry : submap.entrySet()) {
      result.put(entry.getKey(), new VersionAndExpiry(
          entry.getValue().getVersionNumber(), entry.getValue().getExpiry()));
    }

    if (parent != null) {
      for (ReadMap.Entry entry : readsReadByParent) {
        FabricSoftRef entryRef = entry.getRef();
        if (store.equals(entryRef.store)) {
          result.put(entryRef.onum, new VersionAndExpiry(
              entry.getVersionNumber(), entry.getExpiry()));
        }
      }
    }

    Log curLog = this;
    while (curLog != null) {
      if (store.isLocalStore()) {
        Iterable<_Impl> writesToExclude =
            includeModified ? Collections.<_Impl> emptyList()
                : curLog.localStoreWrites;
        Iterable<_Impl> chain =
            SysUtil.chain(writesToExclude, curLog.localStoreCreates);
        for (_Impl write : chain)
          result.remove(write.$getOnum());
      } else {
        Iterable<_Impl> writesToExclude =
            includeModified ? Collections.<_Impl> emptyList()
                : curLog.writes.values();
        Iterable<_Impl> chain =
            SysUtil.chain(writesToExclude, curLog.creates.values());
        for (_Impl write : chain)
          if (write.$getStore() == store) result.remove(write.$getOnum());
      }
      curLog = curLog.parent;
    }

    return result;
  }

  /**
   * Returns a collection of objects modified at the given store. Writes on
   * created objects are not included.
   */
  Collection<_Impl> getWritesForStore(Store store) {
    // This should be a Set of _Impl, but we have a map indexed by OID to
    // avoid calling hashCode and equals on the _Impls.
    LongKeyMap<_Impl> result = new LongKeyHashMap<>();

    if (store.isLocalStore()) {
      for (_Impl obj : localStoreWrites) {
        // TODO: This condition should never fail...
        if (!extendedTreaties.containsKey(obj)) {
          result.put(obj.$getOnum(), obj);
        }
      }

      for (_Impl create : localStoreCreates) {
        result.remove(create.$getOnum());
      }
    } else {
      for (_Impl obj : writes.values()) {
        if (obj.$getStore() == store && obj.$isOwned
        // TODO: This condition should never fail...
            && !extendedTreaties.containsKey(obj)) {
          result.put(obj.$getOnum(), obj);
        }
      }

      for (_Impl create : creates.values())
        if (create.$getStore() == store) result.remove(create.$getOnum());
    }

    return result.values();
  }

  Collection<ExpiryExtension> getExtensionsForStore(Store store) {
    if (!extendedTreaties.storeSet().contains(store)) {
      return Collections.emptyList();
    }
    List<ExpiryExtension> extensions =
        new ArrayList<>(extendedTreaties.get(store).size());
    for (ExpiryExtension extension : extendedTreaties.get(store).values()) {
      extensions.add(extension);
    }
    return extensions;
  }

  LongKeyMap<OidHashSet> getTriggeredExtensionsForStore(Store store) {
    if (!extensionTriggers.storeSet().contains(store)) {
      return new LongKeyHashMap<>();
    }
    return extensionTriggers.get(store);
  }

  LongSet getDelayedExtensionsForStore(Store store) {
    LongSet result = new LongHashSet();
    if (extensionTriggers.containsKey((fabric.lang.Object) null)) {
      LongSet onums =
          extensionTriggers.get((fabric.lang.Object) null).get(store);
      if (onums != null) return onums;
    }
    return result;
  }

  /**
   * Returns a collection of objects created at the given store.
   */
  Collection<_Impl> getCreatesForStore(Store store) {
    // This should be a Set of _Impl, but to avoid calling methods on the
    // _Impls, we instead use a map keyed on OID.
    LongKeyMap<_Impl> result = new LongKeyHashMap<>();

    if (store.isLocalStore()) {
      for (_Impl obj : localStoreCreates) {
        result.put(obj.$getOnum(), obj);
      }
    } else {
      for (_Impl obj : creates.values())
        if (obj.$getStore() == store && obj.$isOwned)
          result.put(obj.$getOnum(), obj);
    }

    return result.values();
  }

  /**
   * Check if this transaction has been told to abort and retry.
   * @throws TransactionRestartingException if a retry was flagged.
   */
  public void checkRetrySignal() throws TransactionRestartingException {
    if (this.retrySignal != null) {
      synchronized (this) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        this.retryCause.printStackTrace(pw);
        WORKER_TRANSACTION_LOGGER.log(Level.INFO,
            "{0} got retry signal up to {1} for {2}\n{3}",
            new Object[] { this, this.retrySignal, this.retryCause, sw });

        throw new TransactionRestartingException(this.retrySignal,
            this.retryCause);
      }
    }
  }

  /**
   * Sets the retry flag on this and the logs of all sub-transactions.
   */
  public void flagRetry(String reason) {
    Queue<Log> toFlag = new LinkedList<>();
    toFlag.add(this);
    while (!toFlag.isEmpty()) {
      Log log = toFlag.remove();
      if (WORKER_DEADLOCK_LOGGER.isLoggable(Level.FINE)) {
        Logging.log(WORKER_DEADLOCK_LOGGER, Level.FINE,
            "Flagging retry for {0} on {1} in {2}", reason, log,
            Thread.currentThread());
      }
      synchronized (log) {
        if (log.child != null) toFlag.add(log.child);
        if (log.retrySignal == null || log.retrySignal.isDescendantOf(tid)) {
          log.retrySignal = tid;
          try {
            throw new RetrySignalException(reason);
          } catch (RetrySignalException e) {
            log.retryCause = e;
          }

          // Grab the currently marked blocking dependency
          final Object curWaitingOn = log.waitsOn;
          if (curWaitingOn != null) {
            // Run this in a different thread, we need to avoid any deadlocks in
            // this step.
            final Log logCopy = log;
            Threading.getPool().submit(new Runnable() {
              @Override
              public void run() {
                synchronized (curWaitingOn) {
                  // If still blocked on the object after synchronizing, wake
                  // the thread that needs to be aborted so it sees retry
                  // signal.
                  if (logCopy.waitsOn == curWaitingOn) {
                    logCopy.waitsOn.notifyAll();
                  }
                }
              }
            });
          }
          // Grab the currently marked blocking dependency
          final Pair<Lock, Condition> curWaitingOnJavaCond =
              log.waitsOnJavaCond;
          if (curWaitingOnJavaCond != null) {
            // Run this in a different thread, we need to avoid any deadlocks in
            // this step.
            final Log logCopy = log;
            Threading.getPool().submit(new Runnable() {
              @Override
              public void run() {
                curWaitingOnJavaCond.first.lock();
                try {
                  // If still blocked on the object after synchronizing, wake
                  // the thread that needs to be aborted so it sees retry
                  // signal.
                  if (logCopy.waitsOnJavaCond == curWaitingOnJavaCond) {
                    logCopy.waitsOnJavaCond.second.signalAll();
                  }
                } finally {
                  curWaitingOnJavaCond.first.unlock();
                }
              }
            });
          }
        }
      }
    }
  }

  /**
   * Updates logs and data structures in <code>_Impl</code>s to abort this
   * transaction. All locks held by this transaction are released.
   */
  void abort() {
    Store localStore = Worker.getWorker().getLocalStore();
    Set<Store> stores = storesToContact();
    // Note what we were trying to do before we aborted.
    Logging.log(HOTOS_LOGGER, Level.FINE,
        "aborted tid {0} ({1} stores, {2} extensions, {3} delayed extensions)",
        tid, stores.size() - (stores.contains(localStore) ? 1 : 0),
        extendedTreaties.size(), delayedExtensions.size());
    // Release read locks.
    for (LongKeyMap<ReadMap.Entry> submap : reads) {
      for (ReadMap.Entry entry : submap.values()) {
        entry.releaseLock(this);
      }
    }

    for (ReadMap.Entry entry : readsReadByParent)
      entry.releaseLock(this);

    // Roll back writes and release write locks.
    Iterable<_Impl> chain = SysUtil.chain(writes.values(), localStoreWrites);
    for (_Impl write : chain) {
      synchronized (write) {
        if (write.$writeLockHolder != null
            && write.$writeLockHolder.isDescendantOf(this)) {
          if (WORKER_DEADLOCK_LOGGER.isLoggable(Level.FINEST)) {
            Logging.log(WORKER_DEADLOCK_LOGGER, Level.FINEST,
                "{0} in {5} aborted and released write lock on {1}/{2} ({3}) ({4})",
                this, write.$getStore(), write.$getOnum(), write.getClass(),
                System.identityHashCode(write), Thread.currentThread());
          }
          write.$copyStateFrom(write.$history);
        }
        // Signal any waiting readers/writers.
        if (write.$numWaiting > 0 && !isDescendantOf(write.$writeLockHolder))
          write.notifyAll();
      }
    }
    // Reset the expiry to check.
    expiryToCheck = Long.MAX_VALUE;

    prepare = null;

    if (parent != null && parent.tid.equals(tid.parent)) {
      // The parent frame represents the parent transaction. Null out its child.
      synchronized (parent) {
        parent.child = null;
      }
    } else {
      // This frame will be reused to represent the parent transaction. Clear
      // out the log data structures.
      reads.clear();
      readsReadByParent.clear();
      creates.clear();
      localStoreCreates.clear();
      writes.clear();
      unobservedSamples.clear();
      extendedTreaties.clear();
      delayedExtensions.clear();
      extensionTriggers.clear();
      localStoreWrites.clear();
      workersCalled.clear();
      securityCache.reset();
      commitHooks.clear();
      untreatiedUpdateChecks.clear();
      treatiedUpdateChecks.clear();

      if (parent != null) {
        writerMap = new WriterMap(parent.writerMap);
        unobservedSamples.putAll(parent.unobservedSamples);
        resolving = parent.resolving;
      } else {
        writerMap = new WriterMap(tid.topTid);
      }

      if (retrySignal != null) {
        synchronized (this) {
          retrySignal = null;
          retryCause = null;
        }
      }
    }
  }

  /**
   * Updates logs and data structures in <code>_Impl</code>s to abort this
   * transaction's updates, while merging the reads into the parent transaction.
   * All write locks held by this transaction are released.
   */
  void abortUpdates() {
    if (parent != null && parent.tid.equals(tid.parent)) {
      Store localStore = Worker.getWorker().getLocalStore();
      Set<Store> stores = storesToContact();
      // Note what we were trying to do before we aborted.
      Logging.log(HOTOS_LOGGER, Level.FINE,
          "aborted writes of tid {0} ({1} stores, {2} extensions, {3} delayed extensions)",
          tid, stores.size() - (stores.contains(localStore) ? 1 : 0),
          extendedTreaties.size(), delayedExtensions.size());
      // Release read locks.
      for (LongKeyMap<ReadMap.Entry> submap : reads) {
        for (ReadMap.Entry entry : submap.values()) {
          parent.transferReadLock(this, entry);
        }
      }

      for (ReadMap.Entry entry : readsReadByParent)
        entry.releaseLock(this);

      // Roll back writes and release write locks.
      Iterable<_Impl> chain = SysUtil.chain(writes.values(), localStoreWrites);
      for (_Impl write : chain) {
        synchronized (write) {
          if (write.$writeLockHolder != null
              && write.$writeLockHolder.isDescendantOf(this)) {
            if (WORKER_DEADLOCK_LOGGER.isLoggable(Level.FINEST)) {
              Logging.log(WORKER_DEADLOCK_LOGGER, Level.FINEST,
                  "{0} in {5} aborted and released write lock on {1}/{2} ({3}) ({4})",
                  this, write.$getStore(), write.$getOnum(), write.getClass(),
                  System.identityHashCode(write), Thread.currentThread());
            }
            write.$copyStateFrom(write.$history);
          }
          // Signal any waiting readers/writers.
          if (write.$numWaiting > 0 && !isDescendantOf(write.$writeLockHolder))
            write.notifyAll();
        }
      }
      // Update the expiry time and drop this child, this acts like a read.
      synchronized (parent) {
        parent.expiryToCheck = Math.min(expiryToCheck, parent.expiryToCheck);
        parent.child = null;
      }

      prepare = null;

      // The parent frame represents the parent transaction. Null out its child.
      if (parent != null && parent.tid.equals(tid.parent)) {
        // The parent frame represents the parent transaction. Null out its child.
        synchronized (parent) {
          parent.child = null;
        }
      } else {
        // This frame will be reused to represent the parent transaction. Clear
        // out the log data structures.
        reads.clear();
        readsReadByParent.clear();
        creates.clear();
        localStoreCreates.clear();
        writes.clear();
        unobservedSamples.clear();
        extendedTreaties.clear();
        delayedExtensions.clear();
        extensionTriggers.clear();
        localStoreWrites.clear();
        workersCalled.clear();
        securityCache.reset();
        commitHooks.clear();
        untreatiedUpdateChecks.clear();
        treatiedUpdateChecks.clear();

        if (parent != null) {
          writerMap = new WriterMap(parent.writerMap);
          unobservedSamples.putAll(parent.unobservedSamples);
          resolving = parent.resolving;
        } else {
          writerMap = new WriterMap(tid.topTid);
        }

        if (retrySignal != null) {
          synchronized (this) {
            retrySignal = null;
            retryCause = null;
          }
        }
      }
    } else {
      // XXX TODO XXX TODO Not sure what to do here? Do we allow committing the
      // reads at the stores?
      throw new NotImplementedException();
    }
  }

  /**
   * Resolve unobserved subjects, either before attempting to commit at the top
   * level or before using a {@link Treaty}.
   */
  public void resolveObservations() {
    // Skip if there's nothing to handle or if this was called in the middle of
    // an already ongoing resolve.
    if (!resolving && !unobservedSamples.isEmpty()) {
      Logging.METRICS_LOGGER.finer("PROCESSING SAMPLES IN " + tid);
      try {
        resolving = true;
        LinkedList<Subject> q = new LinkedList<>();
        for (Subject s : unobservedSamples.values()) {
          q.add(s);
        }
        AbstractSubject._Impl.processSamples(q);
        unobservedSamples.clear();
      } finally {
        resolving = false;
      }
    }
  }

  public void addUntreatiedPostcondition(Metric m, TreatyStatement stmt) {
    untreatiedUpdateChecks.put((Metric._Proxy) m.$getProxy(), stmt);
  }

  public void addTreatiedPostcondition(Treaty t) {
    treatiedUpdateChecks.put(t,
        new Triple<>((Metric._Proxy) t.get$metric().$getProxy(),
            t.get$predicate(), t.get$$expiry()));
  }

  private boolean finalResolve = false;

  /**
   * Check if a treaty deactivation violates a treaty update check, throwing an
   * TransactionAbortingException if it does.
   */
  public void checkTreatyDeactivation(Treaty t) {
    //if (finalResolve) {
    //  TreatyRef r = new TreatyRef(t);
    //  if (treatiedUpdateChecks.containsKey(r)) {
    //    Logging.METRICS_LOGGER.fine("ABORTING FOR POSTCONDITION IN "
    //        + Thread.currentThread() + ":" + tid);
    //    if (treatiedUpdateChecks.get(r).third.longValue() >= System
    //        .currentTimeMillis())
    //      throw new TransactionAbortingException(tid, true);
    //    //TransactionManager.getInstance().checkForStaleObjects();
    //    //checkRetrySignal();
    //    //// Otherwise, the treaty we wanted to use went stale, so retry.
    //    //throw new TransactionRestartingException(tid);
    //  }
    //}
  }

  /**
   * Run the final observation resolution along with checks for postconditions.
   */
  public void runFinalResolution() {
    finalResolve = true;
    try {
      // resolve observations and, in the process, check treaties.
      resolveObservations();
      // Check untreatied checks.
      Logging.METRICS_LOGGER.fine("CHECKING UNTREATIED POSTCONDITIONS IN "
          + Thread.currentThread() + ":" + tid);
      for (Map.Entry<Metric._Proxy, TreatyStatement> entry : untreatiedUpdateChecks
          .entries()) {
        if (!entry.getValue().check(entry.getKey())) {
          Logging.METRICS_LOGGER.fine("ABORTING FOR POSTCONDITION IN "
              + Thread.currentThread() + ":" + tid);
          throw new TransactionAbortingException(tid, true);
        }
        entry.getKey().createAndActivateTreaty(entry.getValue(), true);
      }
      Set<Store> storesTouched = storesToContact();
      // Check treaties still exist.
      Logging.METRICS_LOGGER.fine("CHECKING TREATIED POSTCONDITIONS IN "
          + Thread.currentThread() + ":" + tid);
      for (Store store : treatiedUpdateChecks.storeSet()) {
        for (LongKeyMap.Entry<Triple<Metric._Proxy, TreatyStatement, Long>> entry : treatiedUpdateChecks
            .get(store).entrySet()) {
          Treaty t = new Treaty._Proxy(store, entry.getKey());
          Triple<Metric._Proxy, TreatyStatement, Long> trpl = entry.getValue();
          if (!t.valid()) {
            if (trpl.second.check(trpl.first)) {
              trpl.first.createAndActivateTreaty(trpl.second, true);
            } else {
              Logging.METRICS_LOGGER.fine("ABORTING FOR POSTCONDITION IN "
                  + Thread.currentThread() + ":" + tid);
              throw new TransactionAbortingException(tid, true);
            }
          } else if (Worker.getWorker().config.aggressiveRebalancing
              && storesTouched.containsAll(storesForMetric(t.get$metric()))) {
            // Rebalance if we're touching all of the stores already.
            t.rebalance();
          }
        }
      }
    } finally {
      finalResolve = false;
      // Clear out the checks, just in case...
      untreatiedUpdateChecks.clear();
      treatiedUpdateChecks.clear();
    }
  }

  /**
   * Utility for getting the set of stores that the associated metric spans in
   * its subtree.
   * This operation may fetch some items but it should not produce any new reads
   * in the current transaction.
   */
  private Set<Store> storesForMetric(Metric metric) {
    Set<Store> results = new HashSet<>();
    Queue<Metric> q = new LinkedList<>();
    q.add(metric);
    while (!q.isEmpty()) {
      Metric m = (Metric) q.poll().fetch();
      results.add(m.$getStore());
      if (m instanceof DerivedMetric) {
        DerivedMetric dm = (DerivedMetric) m;
        for (Metric sub : dm.get$terms().array()) {
          q.add(sub);
        }
      }
    }
    return results;
  }

  /**
   * Update the earliest expiry we have to check against.
   */
  protected synchronized void updateExpiry(long exp) {
    expiryToCheck = Math.min(expiryToCheck, exp);
  }

  /**
   * @return the earliest expiry used by this transaction.
   */
  protected long expiry() {
    return expiryToCheck;
  }

  /**
   * Updates logs and data structures in <code>_Impl</code>s to commit this
   * transaction. Assumes there is a parent transaction. This transaction log is
   * merged into the parent's log and any locks held are transferred to the
   * parent.
   */
  void commitNested() {
    // TODO See if lazy merging of logs helps performance.

    if (parent == null || !parent.tid.equals(tid.parent)) {
      // Reuse this frame for the parent transaction.
      return;
    }

    // Merge reads and transfer read locks.
    for (LongKeyMap<ReadMap.Entry> submap : reads) {
      for (ReadMap.Entry entry : submap.values()) {
        parent.transferReadLock(this, entry);
      }
    }

    for (ReadMap.Entry entry : readsReadByParent) {
      entry.releaseLock(this);
    }

    synchronized (parent.unobservedSamples) {
      parent.unobservedSamples.clear();
      parent.unobservedSamples.putAll(unobservedSamples);
    }

    // Do this before writes and creates!
    for (Store s : extendedTreaties.storeSet()) {
      for (ExpiryExtension obs : extendedTreaties.get(s).values()) {
        // Check if the parent already plans to extend the treaties, if so,
        // update the mapping.
        synchronized (parent.extendedTreaties) {
          if (parent.extendedTreaties.containsKey(new Oid(s, obs.onum))) {
            parent.extendedTreaties.put(new Oid(s, obs.onum), obs);
            continue;
          }
        }
        synchronized (parent.writes) {
          if (parent.writes.containsKey(new Oid(s, obs.onum))) continue;
        }
        synchronized (parent.creates) {
          if (parent.creates.containsKey(new Oid(s, obs.onum))) continue;
        }
        synchronized (parent.extendedTreaties) {
          // If not already being written or created, add it to the extensions.
          parent.extendedTreaties.put(new Oid(s, obs.onum), obs);
        }
      }
    }

    for (Store s : delayedExtensions.storeSet()) {
      for (LongKeyMap.Entry<OidHashSet> e : delayedExtensions.get(s)
          .entrySet()) {
        long onum = e.getKey();
        Oid oid = new Oid(s, onum);
        synchronized (parent.delayedExtensions) {
          OidHashSet triggers = e.getValue();
          if (triggers.isEmpty()) {
            parent.addDelayedExtension(oid);
          } else {
            for (Oid trigger : triggers) {
              parent.addDelayedExtension(new Oid(s, onum), trigger);
            }
          }
        }
      }
    }

    // Merge writes and transfer write locks.
    OidKeyHashMap<_Impl> parentWrites = parent.writes;
    for (_Impl obj : writes.values()) {
      synchronized (obj) {
        if (obj.$history.$writeLockHolder == parent) {
          // The parent transaction already wrote to the object. Discard one
          // layer of history. In doing so, we also end up releasing this
          // transaction's write lock.
          obj.$history = obj.$history.$history;
          if (extendedTreaties.containsKey(obj)) {
            // Make sure that we don't lose "subextensions"
            if (!parent.extendedTreaties.containsKey(obj))
              obj.$expiry = extendedTreaties.get(obj).expiry;
          }
          // Flatten any changes to the treatyset, if necessary.
          if (obj instanceof TreatiesBox)
            ((TreatiesBox) obj).get$treaties().flattenUpdates();
        } else {
          // The parent transaction didn't write to the object. Add write to
          // parent and transfer our write lock.
          synchronized (parentWrites) {
            parentWrites.put(obj, obj);
          }
        }
        obj.$writer = null;
        obj.$writeLockHolder = parent;

        // Signal any readers/writers.
        if (obj.$numWaiting > 0) obj.notifyAll();
      }
    }

    WeakReferenceArrayList<_Impl> parentLocalStoreWrites =
        parent.localStoreWrites;
    for (_Impl obj : localStoreWrites) {
      synchronized (obj) {
        if (obj.$history.$writeLockHolder == parent) {
          // The parent transaction already wrote to the object. Discard one
          // layer of history. In doing so, we also end up releasing this
          // transaction's write lock.
          obj.$history = obj.$history.$history;
          if (extendedTreaties.containsKey(obj)) {
            // Make sure we don't lose "subextensions"
            if (!parent.extendedTreaties.containsKey(obj))
              obj.$expiry = extendedTreaties.get(obj).expiry;
          }
          // Flatten any changes to the treatyset, if necessary.
          if (obj instanceof TreatiesBox)
            ((TreatiesBox) obj).get$treaties().flattenUpdates();
        } else {
          // The parent transaction didn't write to the object. Add write to
          // parent and transfer our write lock.
          synchronized (parentLocalStoreWrites) {
            parentLocalStoreWrites.add(obj);
          }
        }
        obj.$writer = null;
        obj.$writeLockHolder = parent;

        // Signal any readers/writers.
        //if (obj.$numWaiting > 0) obj.notifyAll();
      }
    }

    // Merge creates and transfer write locks.
    OidKeyHashMap<_Impl> parentCreates = parent.creates;
    synchronized (parentCreates) {
      for (_Impl obj : creates.values()) {
        parentCreates.put(obj, obj);
        obj.$writeLockHolder = parent;
      }
    }

    WeakReferenceArrayList<_Impl> parentLocalStoreCreates =
        parent.localStoreCreates;
    synchronized (parentLocalStoreCreates) {
      for (_Impl obj : localStoreCreates) {
        parentLocalStoreCreates.add(obj);
        obj.$writeLockHolder = parent;
      }
    }

    // Merge the set of workers that have been called.
    synchronized (parent.workersCalled) {
      for (RemoteWorker worker : workersCalled) {
        if (!parent.workersCalled.contains(worker))
          parent.workersCalled.add(worker);
      }
    }

    // Replace the parent's security cache with the current cache.
    parent.securityCache.set((SecurityCache) securityCache);

    // Merge the writer map.
    synchronized (parent.writerMap) {
      parent.writerMap.putAll(writerMap);
    }

    // Merge hooks.
    synchronized (parent.commitHooks) {
      parent.commitHooks.addAll(commitHooks);
    }

    // Update the expiry time and drop this child.
    synchronized (parent) {
      parent.expiryToCheck = Math.min(expiryToCheck, parent.expiryToCheck);
      parent.child = null;
    }
  }

  // Time before expiration runs out to process pending extensions
  public static final long EXTENSION_WINDOW = 1000;

  /**
   * Updates logs and data structures in <code>_Impl</code>s to commit this
   * transaction. Assumes this is a top-level transaction. All locks held by
   * this transaction are released.
   */
  void commitTopLevel() {
    // Grab the stores already contacted to be checked against below.
    Set<Store> alreadyContacted = storesToContact();

    // Release write locks on created objects and set version numbers.
    // XXX TRM 3/9/18: Do this before reads and writes so that nobody gets a
    // reference to the creates before we've released the writer locks on
    // creates.
    Iterable<_Impl> chain2 = SysUtil.chain(creates.values(), localStoreCreates);
    for (_Impl obj : chain2) {
      if (WORKER_DEADLOCK_LOGGER.isLoggable(Level.FINEST)) {
        Logging.log(WORKER_DEADLOCK_LOGGER, Level.FINEST,
            "{0} in {5} committed and released (created) write lock on {1}/{2} ({3}) ({4})",
            this, obj.$getStore(), obj.$getOnum(), obj.getClass(),
            System.identityHashCode(obj), Thread.currentThread());
      }
      if (!obj.$isOwned) {
        // The cached object is out-of-date. Evict it.
        obj.$ref.evict();
        continue;
      }

      // XXX: Should we notify here in case somehow a reference is found in
      // another transaction in another thread still?
      obj.$writer = null;
      obj.$writeLockHolder = null;
      obj.$writeLockStackTrace = null;
      obj.$version = 1;
      long expiry = getFinalExpiry(obj);
      obj.$readMapEntry.incrementVersionAndUpdateExpiry(expiry);
      obj.$expiry = expiry;
      obj.$isOwned = false;
    }

    // Release read locks.
    for (LongKeyMap<ReadMap.Entry> submap : reads) {
      for (LongKeyMap.Entry<ReadMap.Entry> e : submap.entrySet()) {
        ReadMap.Entry entry = e.getValue();
        entry.releaseLock(this);
      }
    }

    // sanity check
    if (!readsReadByParent.isEmpty())
      throw new InternalError("something was read by a non-existent parent");

    // Release write locks and ownerships; update version numbers.
    Iterable<_Impl> chain = SysUtil.chain(writes.values(), localStoreWrites);
    for (_Impl obj : chain) {
      if (!obj.$isOwned) {
        // The cached object is out-of-date. Evict it.
        obj.$ref.evict();
        continue;
      }

      synchronized (obj) {
        if (WORKER_DEADLOCK_LOGGER.isLoggable(Level.FINEST)) {
          Logging.log(WORKER_DEADLOCK_LOGGER, Level.FINEST,
              "{0} in {5} committed and released write lock on {1}/{2} ({3}) ({4})",
              this, obj.$getStore(), obj.$getOnum(), obj.getClass(),
              System.identityHashCode(obj), Thread.currentThread());
        }
        obj.$writer = null;
        obj.$writeLockHolder = null;
        obj.$writeLockStackTrace = null;
        // Discard one layer of history.
        obj.$history = obj.$history.$history;

        long expiry = getFinalExpiry(obj);
        // Flatten any changes to the treatyset, if necessary.
        if (obj instanceof TreatiesBox)
          ((TreatiesBox) obj).get$treaties().flattenUpdates();
        obj.$expiry = expiry;
        // Don't increment the version if it's only extending treaties
        if (!extendedTreaties.containsKey(obj)) {
          obj.$version++;
          obj.$readMapEntry.incrementVersionAndUpdateExpiry(expiry);
        } else {
          obj.$readMapEntry.extendExpiry(expiry);
        }
        obj.$isOwned = false;

        // Signal any waiting readers/writers.
        if (obj.$numWaiting > 0) obj.notifyAll();
      }

      // Note writes that committed so they can be flushed at the caller.
      TransactionManager tm = TransactionManager.getInstance();
      if (tm.committedWrites != null)
        tm.committedWrites.put(new Oid(obj), obj.$version);
    }

    // Queue up extension transactions not triggered by an update and not sent
    // in the commit message already.
    OidHashSet extensionsToSend =
        extensionTriggers.get((fabric.lang.Object) null);

    if (extensionsToSend != null) {
      for (String sName : extensionsToSend.storeNameSet()) {
        Store s = Worker.getWorker().getStore(sName);
        if (alreadyContacted.contains(s)) continue;
        s.sendExtensions(extensionsToSend.get(s),
            new HashMap<RemoteStore, Collection<SerializedObject>>());
      }
    }

    prepare = null;

    // Merge the security cache into the top-level label cache.
    securityCache.mergeWithTopLevel();

    // Run commit hooks.
    for (Runnable hook : commitHooks) {
      hook.run();
    }
  }

  /**
   * Transfers a read lock from a child transaction.
   */
  private void transferReadLock(Log child, ReadMap.Entry readMapEntry) {
    // If we already have a read lock, return; otherwise, register a read lock.
    Boolean lockedByAncestor = readMapEntry.transferLockToParent(child);
    if (lockedByAncestor == null) {
      // We already had a lock; nothing to do.
      return;
    }

    // Only record the read in this transaction if none of our ancestors have
    // read this object.
    if (!lockedByAncestor) {
      FabricSoftRef ref = readMapEntry.getRef();
      synchronized (reads) {
        reads.put(ref.store, ref.onum, readMapEntry);
      }
    } else {
      readsReadByParent.add(readMapEntry);
    }

    // Signal any readers/writers and clear the $reader stamp.
    readMapEntry.signalObject();
  }

  /**
   * Grabs a read lock for the given object.
   */
  void acquireReadLock(_Impl obj) {
    // If we already have a read lock, return; otherwise, register a read
    // lock.
    ReadMap.Entry readMapEntry = obj.$readMapEntry;
    boolean lockedByAncestor = false;
    synchronized (readMapEntry) {
      Set<Log> readers = readMapEntry.getReaders();
      if (readers.contains(this)) {
        // We already have a lock; nothing to do.
        return;
      }

      // Check if any of our ancestors already has a read lock.
      Log curAncestor = parent;
      while (curAncestor != null) {
        if (readers.contains(curAncestor)) {
          lockedByAncestor = true;
          break;
        }

        curAncestor = curAncestor.parent;
      }

      readMapEntry.addLock(this);
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
        reads.put(obj.$ref.store, obj.$ref.onum, readMapEntry);
      }
    } else {
      readsReadByParent.add(readMapEntry);
    }
  }

  /**
   * Blocks until all threads in <code>threads</code> are finished.
   */
  void waitForThreads() {
  }

  public TransactionID getTid() {
    return tid;
  }

  public Log getChild() {
    return child;
  }

  /**
   * @return the log of the outermost ancestor transaction available at this
   *     worker. This will be different from the top-level transaction if the
   *     top-level was started at a different worker.
   */
  public Log getOutermost() {
    Log result = this;
    while (result.parent != null) {
      result = result.parent;
    }
    return result;
  }

  /**
   * Changes the waitsFor set to an empty set.  This is for waiting on objects
   * which are not necessarily held by other worker transactions, such as
   * waiting for a fetch to complete.
   */
  public void setWaitsFor(Lock lock, Condition cond) {
    synchronized (this.waitsFor) {
      this.waitsFor.clear();
      this.waitsOn = null;
      this.waitsOnJavaCond = new Pair<>(lock, cond);
    }
  }

  /**
   * Changes the waitsFor set to an empty set.  This is for waiting on objects
   * which are not necessarily held by other worker transactions, such as
   * waiting for a fetch to complete.
   */
  public void setWaitsFor(Object obj) {
    synchronized (this.waitsFor) {
      this.waitsFor.clear();
      this.waitsOn = obj;
    }
  }

  /**
   * Changes the waitsFor set to a singleton set containing the given log.
   */
  public void setWaitsFor(Log waitsFor, Object obj) {
    synchronized (this.waitsFor) {
      this.waitsFor.clear();
      this.waitsFor.add(waitsFor);
      this.waitsOn = obj;
    }
  }

  /**
   * Changes the waitsFor set to contain exactly the elements of the given set.
   */
  public void setWaitsFor(Set<Log> waitsFor, Object obj) {
    synchronized (this.waitsFor) {
      this.waitsFor.clear();
      this.waitsFor.addAll(waitsFor);
      this.waitsOn = obj;
    }
  }

  /**
   * Empties the waitsFor set.
   */
  public void clearWaitsFor() {
    synchronized (this.waitsFor) {
      this.waitsFor.clear();
      this.waitsOn = null;
    }
  }

  /**
   * Returns a copy of the waitsFor set.
   */
  public Set<Log> getWaitsFor() {
    synchronized (this.waitsFor) {
      return new HashSet<>(this.waitsFor);
    }
  }

  /**
   * Goes through this transaction log and performs an onum renumbering. This is
   * used by fabric.worker.TransactionRegistery.renumberObject. Do not call this
   * unless if you really know what you are doing.
   *
   * @deprecated
   */
  @Deprecated
  public void renumberObject(Store store, long onum, long newOnum) {
    ReadMap.Entry entry = reads.remove(store, onum);
    if (entry != null) {
      reads.put(store, newOnum, entry);
    }

    if (child != null) child.renumberObject(store, onum, newOnum);
  }

  @Override
  public String toString() {
    return "[" + tid + "]";
  }

  // Delayed Extension Management

  /**
   * Update data structures for a new delayed extension, given the triggering
   * dependency.
   */
  public void addDelayedExtension(fabric.lang.Object toBeExtended,
      fabric.lang.Object trigger) {
    addDelayedExtension(new Oid(toBeExtended), new Oid(trigger));
  }

  /**
   * Update data structures for a new delayed extension, given the triggering
   * dependency.
   */
  public void addDelayedExtension(Oid toBeExtended,
      fabric.lang.Object trigger) {
    addDelayedExtension(toBeExtended, new Oid(trigger));
  }

  /**
   * Update data structures for a new delayed extension, given the triggering
   * dependency.
   */
  public void addDelayedExtension(fabric.lang.Object toBeExtended,
      Oid trigger) {
    addDelayedExtension(new Oid(toBeExtended), trigger);
  }

  /**
   * Update data structures for a new delayed extension, given the triggering
   * dependency.
   */
  public void addDelayedExtension(Oid toBeExtended, Oid trigger) {
    synchronized (delayedExtensions) {
      // Don't add if this is being updated otherwise in this transaction.
      if (writes.containsKey(toBeExtended)) return;

      // Forward
      if (!delayedExtensions.containsKey(toBeExtended)) {
        delayedExtensions.put(toBeExtended, new OidHashSet());
      }
      delayedExtensions.get(toBeExtended).add(trigger);

      // Backward
      if (!extensionTriggers.containsKey(trigger)) {
        extensionTriggers.put(trigger, new OidHashSet());
      }
      extensionTriggers.get(trigger).add(toBeExtended);

      // Remove null trigger.
      OidHashSet nullGroup = extensionTriggers.get((fabric.lang.Object) null);
      if (nullGroup != null && nullGroup.contains(toBeExtended)) {
        nullGroup.remove(toBeExtended);
      }
    }
  }

  /**
   * Update data structures for a new delayed extension, given no trigger.
   */
  public void addDelayedExtension(fabric.lang.Object toBeExtended) {
    addDelayedExtension(new Oid(toBeExtended));
  }

  /**
   * Update data structures for a new delayed extension, given no trigger.
   */
  public void addDelayedExtension(Oid toBeExtended) {
    synchronized (delayedExtensions) {
      // Don't add if we're updating this treaty.
      if (writes.containsKey(toBeExtended)) return;

      // Forward
      if (!delayedExtensions.containsKey(toBeExtended)) {
        delayedExtensions.put(toBeExtended, new OidHashSet());
      }

      // Backward
      if (!extensionTriggers.containsKey((fabric.lang.Object) null)) {
        extensionTriggers.put((fabric.lang.Object) null, new OidHashSet());
      }
      extensionTriggers.get((fabric.lang.Object) null).add(toBeExtended);
    }
  }

  /**
   * Cancel delayed extensions for treaties associated with the given object.
   */
  public void cancelDelayedExtension(Oid obj) {
    synchronized (delayedExtensions) {
      OidHashSet removed = delayedExtensions.remove(obj);

      if (removed != null) {
        if (removed.isEmpty()) {
          // No triggers
          OidHashSet nullGroup =
              extensionTriggers.get((fabric.lang.Object) null);
          nullGroup.remove(obj);
          if (nullGroup.isEmpty()) {
            extensionTriggers.remove((fabric.lang.Object) null);
          }
        } else {
          // Triggers
          for (Oid trigger : removed) {
            extensionTriggers.get(trigger).remove(obj);
            if (extensionTriggers.get(trigger).isEmpty()) {
              extensionTriggers.remove(trigger);
            }
          }
        }
      }
    }
  }

  public OidKeyHashMap<Long> getLongerTreaties() {
    if (prepare != null) return prepare.getLongerTreaties();
    return new OidKeyHashMap<>();
  }

  /**
   * Get the final treaties associated with the written object, accounting for
   * any extensions during the transaction or update sent back from the store.
   */
  public long getFinalExpiry(_Impl obj) {
    if (getLongerTreaties().containsKey(obj))
      return getLongerTreaties().get(obj);
    if (extendedTreaties.containsKey(obj))
      return extendedTreaties.get(obj).expiry;
    return obj.$expiry;
  }

  /**
   * Get the final treaties associated with the read oid, accounting for
   * any extensions during the transaction or update sent back from the store.
   * If there was no change in the treaties, returns null.
   */
  public Long getFinalExpiry(Store s, long onum) {
    if (getLongerTreaties().containsKey(s, onum))
      return getLongerTreaties().get(s, onum);
    if (extendedTreaties.containsKey(s, onum))
      return extendedTreaties.get(s, onum).expiry;
    return null;
  }

  public long getCommitTime() {
    if (prepare != null) return prepare.getCommitTime();
    return 0;
  }

  /**
   * Clear an extension in this log and treat it as a full on write of the
   * treaties.
   */
  public void clearExtension(fabric.lang.Object._Impl o) {
    ExpiryExtension p = extendedTreaties.remove(o);
    if (p != null) o.$expiry = p.expiry;
  }

  /**
   * Dumb hack to check we aren't clobbering an existing write of a different
   * _Impl copy.  Aborts up to the furthest ancestor with a different _Impl for
   * the obj.
   */
  public void checkWriteClobber(fabric.lang.Object._Impl o) {
    // Walk from top level down.
    Log cur = this;
    while (cur.parent != null)
      cur = cur.parent;
    while (cur != null) {
      if (cur.writes.containsKey(o) && cur.writes.get(o) != o) {
        Logging.METRICS_LOGGER.log(Level.WARNING,
            "FOUND DIFFERENT IMPLS FOR {0}/{1}: {2} vs. {3}",
            new Object[] { o.$getStore(), o.$getOnum(),
                System.identityHashCode(cur.writes.get(o)),
                System.identityHashCode(o) });
        throw new TransactionRestartingException(cur.tid);
      }
      cur = cur.child;
    }
  }
}
