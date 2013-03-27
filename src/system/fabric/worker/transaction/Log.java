package fabric.worker.transaction;

import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.SemanticWarranty;
import fabric.common.SysUtil;
import fabric.common.Threading;
import fabric.common.Timing;
import fabric.common.TransactionID;
import fabric.common.VersionWarranty;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.WeakReferenceArrayList;
import fabric.lang.Object._Impl;
import fabric.lang.WrappedJavaInlineable;
import fabric.lang.security.LabelCache;
import fabric.lang.security.SecurityCache;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.memoize.WarrantiedCallResult;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.WriterMap;

/* TODO:
 *      - Store away results given by re-used calls so they can be checked by
 *      the transaction.
 *      - Currently there's a problem with calls being passed up to parents even
 *      in semantic warranty transactions.
 */
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
   * A flag indicating whether this transaction should abort or be retried. This
   * flag should be checked before each operation. This flag is set when it's
   * non-null and indicates the transaction in the stack that is to be retried;
   * all child transactions are to be aborted.
   */
  volatile TransactionID retrySignal;

  /**
   * Maps OIDs to <code>readMap</code> entries for objects read in this
   * transaction or completed sub-transactions. Reads from running or aborted
   * sub-transactions don't count here.
   */
  // Proxy objects aren't used for keys here because doing so would result in
  // calls to hashcode() and equals() on such objects, resulting in fetching the
  // corresponding Impls from the store.
  protected final OidKeyHashMap<ReadMapEntry> reads;

  /**
   * Reads on objects that have been read by an ancestor transaction.
   */
  protected final List<ReadMapEntry> readsReadByParent;

  /**
   * A collection of all objects created in this transaction or completed
   * sub-transactions. Objects created in running or aborted sub-transactions
   * don't count here. To keep them from being pinned, objects on local store
   * are not tracked here.
   */
  protected final List<_Impl> creates;

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
  protected final List<_Impl> writes;

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
   * A call for which this transaction will represent a request for a
   * SemanticWarranty on the call.  This is only for the current transaction,
   * not a subtransaction.
   */
  protected final CallInstance semanticWarrantyCall;

  /**
   * The value of the call for which this log represents a semantic warranty
   * request.  This is only for the current transaction, not a subtransaction.
   */
  protected fabric.lang.Object semanticWarrantyValue;

  /**
   * Mapping from onums to CallInstances that depend on them.
   */
  protected final LongKeyMap<Set<CallInstance>> readDependencies;

  /**
   * Mapping from call ids to CallInstancs that depend on them.
   */
  protected final Map<CallInstance, Set<CallInstance>> callDependencies;

  /**
   * Set of CallInstance ids for semantic warranties used during this
   * transaction.
   */
  protected final Map<CallInstance, WarrantiedCallResult> semanticWarrantiesUsed;

  /**
   * Map from call ID to SemanticWarrantyRequests made by subtransactions.
   */
  protected final Map<CallInstance, SemanticWarrantyRequest> requests;

  /**
   * Map from call ID to the store where the request will go.
   */
  protected final Map<CallInstance, Store> requestLocations;

  /**
   * Map from call instance to the log of the subtransaction that was
   * responsible for creating it.  This is purely for convenience when trying to
   * remove requests from earlier in the transaction and need to transfer over
   * their read locks.
   */
  protected final Map<CallInstance, Log> requestLogs;

  /**
   * Map from call ID to the store's CallResult response which will be stored on
   * successfully finishing the commit phase.
   */
  protected final Map<CallInstance, SemanticWarranty> requestReplies;

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

    /**
     * The transaction's proposed commit time. In the PREPARING or PREPARED
     * state, objects whose warranties expire before this time are (being)
     * read-prepared.
     */
    public long commitTime = 0;

    /**
     * The set of stores that have been contacted by the commit protocol.
     */
    public final Set<Store> storesContacted = new HashSet<Store>();
  }

  public final AbstractSecurityCache securityCache;

  /**
   * Creates a new log with the given parent and the given transaction ID. The
   * TID for the parent and the given TID are assumed to be consistent. If the
   * given TID is null, a random tid is generated for the subtransaction.  This
   * transaction does not generate a SemanticWarranty request itself.
   */
  Log(Log parent, TransactionID tid) {
    this(parent, tid, null);
  }

  /**
   * Creates a new log with the given parent, the given transaction ID, and
   * CallInstance for a SemanticWarranty request. The TID for the parent and the
   * given TID are assumed to be consistent. If the given TID is null, a random
   * tid is generated for the subtransaction.  If the given CallInstance is
   * null, it is assumed that this transaction does not make a request.
   */
  Log(Log parent, TransactionID tid, CallInstance semanticWarrantyCall) {
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
    this.reads = new OidKeyHashMap<ReadMapEntry>();
    this.readsReadByParent = new ArrayList<ReadMapEntry>();
    this.creates = new ArrayList<_Impl>();
    this.localStoreCreates = new WeakReferenceArrayList<_Impl>();
    this.writes = new ArrayList<_Impl>();
    this.localStoreWrites = new WeakReferenceArrayList<_Impl>();
    this.workersCalled = new ArrayList<RemoteWorker>();
    this.semanticWarrantyCall = semanticWarrantyCall;
    this.semanticWarrantiesUsed =
        new HashMap<CallInstance, WarrantiedCallResult>();
    this.readDependencies = new LongKeyHashMap<Set<CallInstance>>();
    this.callDependencies = new HashMap<CallInstance, Set<CallInstance>>();
    this.requests = new HashMap<CallInstance, SemanticWarrantyRequest>();
    this.requestLocations = new HashMap<CallInstance, Store>();
    this.requestLogs = new HashMap<CallInstance, Log>();
    this.requestReplies =
        Collections
            .synchronizedMap(new HashMap<CallInstance, SemanticWarranty>());

    if (parent != null) {
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
    } else {
      this.writerMap = new WriterMap(this.tid.topTid);
      commitState = new CommitState();

      LabelCache labelCache = Worker.getWorker().labelCache;
      this.securityCache = new SecurityCache(labelCache);

      // New top-level frame. Register it in the transaction registry.
      TransactionRegistry.register(this);
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
    return tid.isDescendantOf(log.tid);
  }

  /**
   * Handles invalidation of all semantic warranty requests made previously by
   * the parent that read the given onum.
   */
  /* TODO: Possibly do this just at the end of the invalidating subtransaction
   * rather than immediately on the write.
   *
   * Issue is that we need to avoid accidentally invalidating based on writes
   * that happened BEFORE the request was computed...
   */
  public void invalidateDependentRequests(long onum) {
    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
        "Onum {0} written, dropping semantic warranties "
            + "earlier in transaction that use it.", onum);
    Set<CallInstance> dependencies = readDependencies.get(onum);
    if (dependencies == null) return;
    for (CallInstance id : dependencies)
      removeRequest(id);
  }

  /**
   * Remove a semantic warranty request created by a subtransaction along with
   * all associated book keeping.
   */
  private void removeRequest(CallInstance callId) {
    /* TODO: Currently broken, need to transfer over information to callers.
     */
    SemanticWarrantyRequest req = requests.get(callId);

    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
        "Call {0} warranty request dropped", req.call.toString());

    cleanupFailedRequest(req);

    for (Entry<Store, LongKeyMap<ReadMapEntry>> entry :
        req.reads.nonNullEntrySet())
      for (ReadMapEntry read : entry.getValue().values())
        readDependencies.remove(read.obj.onum);

    // This call no longer depends on its various subcalls
    for (CallInstance call : req.calls.keySet()) {
      callDependencies.get(call).remove(callId);
    }

    requestLocations.remove(callId);
    requests.remove(callId);

    // Calls that used this call must also be removed
    for (CallInstance call : callDependencies.get(callId)) {
      removeRequest(call);
    }

    callDependencies.remove(callId);
  }

  /**
   * Gets a result, if any, for a request we haven't pushed to the store yet
   */
  public WarrantiedCallResult getRequestResult(CallInstance call) {
    SemanticWarrantyRequest req = requests.get(call);
    if (req != null)
      return new WarrantiedCallResult(req.value, new SemanticWarranty(0));
    if (req == null && parent != null) return parent.getRequestResult(call);
    return null;
  }

  /**
   * Gets the SemanticWarrantyRequest, if any, associated with the given call
   * instance.
   */
  public SemanticWarrantyRequest getRequest(CallInstance call) {
    return requests.get(call);
  }

  /**
   * Returns a mapping of stores to (mappings of onums to version numbers),
   * indicating those objects read (but not modified) by this transaction, whose
   * version warranties expire between commitState.commitTime (exclusive) and
   * the given commitTime (inclusive).
   */
  Map<Store, LongKeyMap<Integer>> storesRead(long commitTime) {
    Map<Store, LongKeyMap<Integer>> result =
        new HashMap<Store, LongKeyMap<Integer>>();
    int numReadsToPrepare = 0;
    int numTotalReads = 0;

    for (Entry<Store, LongKeyMap<ReadMapEntry>> entry : reads.nonNullEntrySet()) {
      Store store = entry.getKey();
      LongKeyMap<Integer> submap = new LongKeyHashMap<Integer>();

      boolean isRemoteStore = !store.isLocalStore();

      LongKeyMap<ReadMapEntry> readOnly =
          filterModifiedReads(store, entry.getValue());
      if (isRemoteStore) numTotalReads += readOnly.size();

      for (LongKeyMap.Entry<ReadMapEntry> subEntry : readOnly.entrySet()) {
        long onum = subEntry.getKey();
        ReadMapEntry rme = subEntry.getValue();

        if (rme.warranty.expiresAfter(commitState.commitTime, true)
            && rme.warranty.expiresBefore(commitTime, true)) {
          submap.put(onum, rme.versionNumber);
        }
      }

      if (!submap.isEmpty()) {
        if (isRemoteStore) numReadsToPrepare += submap.size();
        result.put(store, submap);
      }
    }

    Logging.HOTOS_LOGGER.info("Read-preparing " + numReadsToPrepare
        + " out of " + numTotalReads + " objects");

    return result;
  }

  /**
   * Returns a mapping of stores to call IDs, indicating those calls reused by
   * this transaction, whose semantic warranties expire between
   * commitState.commitTime (exclusive) and the given commitTime (inclusive).
   */
  Map<Store, Set<CallInstance>> storesCalled(long commitTime) {
    Map<Store, Set<CallInstance>> result =
        new HashMap<Store, Set<CallInstance>>();
    for (Entry<CallInstance, WarrantiedCallResult> e : semanticWarrantiesUsed
        .entrySet()) {
      Store store = e.getKey().target.$getStore();
      CallInstance call = e.getKey();
      WarrantiedCallResult callRes = e.getValue();
      if (callRes.warranty.expiresBefore(commitTime, true)
          && !requests.containsKey(call)) {
        Set<CallInstance> requestsAtStore = result.get(store);
        if (requestsAtStore == null) {
          requestsAtStore = new HashSet<CallInstance>();
          result.put(store, requestsAtStore);
        }
        requestsAtStore.add(e.getKey());
      }
    }
    return result;
  }

  /**
   * Returns a set of Stores we are making requests at.
   */
  Set<Store> storesRequested() {
    return new HashSet<Store>(requestLocations.values());
  }

  void updateVersionWarranties(Store store,
      LongKeyMap<VersionWarranty> newWarranties) {
    if (store.isLocalStore()) return;

    for (LongKeyMap.Entry<VersionWarranty> entry : newWarranties.entrySet()) {
      long onum = entry.getKey();
      VersionWarranty warranty = entry.getValue();

      ReadMapEntry rme = reads.get(store, onum);
      rme.warranty = warranty;
    }
  }

  private <V> LongKeyMap<V> filterModifiedReads(Store store, LongKeyMap<V> map) {
    map = new LongKeyHashMap<V>(map);

    if (store.isLocalStore()) {
      @SuppressWarnings("unchecked")
      Iterable<_Impl> chain =
          SysUtil.chain(localStoreWrites, localStoreCreates);
      for (_Impl write : chain)
        map.remove(write.$getOnum());
    } else {
      @SuppressWarnings("unchecked")
      Iterable<_Impl> chain = SysUtil.chain(writes, creates);
      for (_Impl write : chain)
        if (write.$getStore() == store) map.remove(write.$getOnum());
    }

    return map;
  }

  /**
   * Returns a set of stores on which objects were modified or created by this
   * transaction.
   */
  Set<Store> storesWritten() {
    Set<Store> result = new HashSet<Store>();

    for (_Impl obj : writes) {
      if (obj.$isOwned) result.add(obj.$getStore());
    }

    for (_Impl obj : creates) {
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
    Set<Store> result = new HashSet<Store>();
    result.addAll(reads.storeSet());
    for (ReadMapEntry entry : readsReadByParent) {
      result.add(entry.obj.store);
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
  LongKeyMap<Integer> getReadsForStore(Store store) {
    LongKeyMap<Integer> result = new LongKeyHashMap<Integer>();
    LongKeyMap<ReadMapEntry> submap = reads.get(store);
    if (submap == null) return result;

    for (LongKeyMap.Entry<ReadMapEntry> entry : submap.entrySet()) {
      result.put(entry.getKey(), entry.getValue().versionNumber);
    }

    if (parent != null) {
      for (ReadMapEntry entry : readsReadByParent) {
        if (store.equals(entry.obj.store)) {
          result.put(entry.obj.onum, entry.versionNumber);
        }
      }
    }

    Log curLog = this;
    while (curLog != null) {
      if (store.isLocalStore()) {
        for (_Impl create : curLog.localStoreCreates)
          result.remove(create.$getOnum());
      } else {
        for (_Impl create : curLog.creates)
          if (create.$getStore() == store) result.remove(create.$getOnum());
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
    LongKeyMap<_Impl> result = new LongKeyHashMap<_Impl>();

    if (store.isLocalStore()) {
      for (_Impl obj : localStoreWrites) {
        result.put(obj.$getOnum(), obj);
      }

      for (_Impl create : localStoreCreates) {
        result.remove(create.$getOnum());
      }
    } else {
      for (_Impl obj : writes)
        if (obj.$getStore() == store && obj.$isOwned)
          result.put(obj.$getOnum(), obj);

      for (_Impl create : creates)
        if (create.$getStore() == store) result.remove(create.$getOnum());
    }

    return result.values();
  }

  /**
   * Returns a collection of objects created at the given store.
   */
  Collection<_Impl> getCreatesForStore(Store store) {
    // This should be a Set of _Impl, but to avoid calling methods on the
    // _Impls, we instead use a map keyed on OID.
    LongKeyMap<_Impl> result = new LongKeyHashMap<_Impl>();

    if (store.isLocalStore()) {
      for (_Impl obj : localStoreCreates) {
        result.put(obj.$getOnum(), obj);
      }
    } else {
      for (_Impl obj : creates)
        if (obj.$getStore() == store && obj.$isOwned)
          result.put(obj.$getOnum(), obj);
    }

    return result.values();
  }

  /**
   * Returns the set of requests that will be stored on the given store.
   */
  Set<SemanticWarrantyRequest> getRequestsForStore(Store store) {
    Set<SemanticWarrantyRequest> reqSet =
        new HashSet<SemanticWarrantyRequest>();
    for (SemanticWarrantyRequest r : requests.values())
      if (requestLocations.get(r.call) == store) reqSet.add(r);
    return reqSet;
  }

  /**
   * Returns the set of call ids used from a given store
   */
  Map<CallInstance, WarrantiedCallResult> getCallsForStore(Store store) {
    Set<CallInstance> requestsSet = new HashSet<CallInstance>();
    for (SemanticWarrantyRequest req : getRequestsForStore(store))
      requestsSet.add(req.call);
    Map<CallInstance, WarrantiedCallResult> callSet = new
      HashMap<CallInstance, WarrantiedCallResult>();
    for (CallInstance c : semanticWarrantiesUsed.keySet())
      if (c.target.$getStore() == store && !requestsSet.contains(c))
        callSet.put(c, semanticWarrantiesUsed.get(c));
    return callSet;
  }

  /**
   * Sets the retry flag on this and the logs of all sub-transactions.
   */
  public void flagRetry() {
    Queue<Log> toFlag = new LinkedList<Log>();
    toFlag.add(this);
    while (!toFlag.isEmpty()) {
      Log log = toFlag.remove();
      synchronized (log) {
        if (log.child != null) toFlag.add(log.child);
        if (log.retrySignal == null || log.retrySignal.isDescendantOf(tid))
          log.retrySignal = tid;
        // XXX This was here to unblock a thread that may have been waiting on a
        // XXX lock. Commented out because it was causing a bunch of
        // XXX InterruptedExceptions and ClosedByInterruptExceptions that
        // XXX weren't being handled properly.

        // log.thread.interrupt();
      }
    }
  }

  /**
   * Updates logs and data structures in <code>_Impl</code>s to abort this
   * transaction. All locks held by this transaction are released.
   */
  void abort() {
    // Release read locks.
    for (LongKeyMap<ReadMapEntry> submap : reads) {
      for (ReadMapEntry entry : submap.values()) {
        entry.releaseLock(this);
      }
    }

    for (ReadMapEntry entry : readsReadByParent)
      entry.releaseLock(this);

    for (Entry<CallInstance, SemanticWarrantyRequest> rentry : requests
        .entrySet()) {
      SemanticWarrantyRequest req = rentry.getValue();
      for (Entry<Store, LongKeyMap<ReadMapEntry>> entry : req.reads.nonNullEntrySet())
        for (ReadMapEntry read : entry.getValue().values())
          read.releaseLock(this);
    }
    // Roll back writes and release write locks.
    @SuppressWarnings("unchecked")
    Iterable<_Impl> chain = SysUtil.chain(writes, localStoreWrites);
    for (_Impl write : chain) {
      synchronized (write) {
        write.$copyStateFrom(write.$history);

        // Signal any waiting readers/writers.
        if (write.$numWaiting > 0) write.notifyAll();
      }
    }

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
      localStoreWrites.clear();
      workersCalled.clear();
      securityCache.reset();
      // Clear out semantic warranty state.
      semanticWarrantiesUsed.clear();
      readDependencies.clear();
      callDependencies.clear();
      requests.clear();
      requestLogs.clear();
      requestLocations.clear();
      requestReplies.clear();

      if (parent != null) {
        writerMap = new WriterMap(parent.writerMap);
      } else {
        writerMap = new WriterMap(tid.topTid);
      }

      if (retrySignal != null) {
        synchronized (this) {
          if (retrySignal.equals(tid)) retrySignal = null;
        }
      }
    }
  }

  /**
   * Merge read and create logs with parent after failing to create 
   * a semantic warranty request. 
   */
  protected void cleanupFailedRequest() {
    // Merge reads
    for (LongKeyMap<ReadMapEntry> submap : reads) {
      for (ReadMapEntry entry : submap.values()) {
        parent.transferReadLock(this, entry);
      }
    }

    // Merge creates and transfer write locks.
    List<_Impl> parentCreates = parent.creates;
    synchronized (parentCreates) {
      for (_Impl obj : creates) {
        parentCreates.add(obj);
        obj.$writeLockHolder = parent;
      }
    }
  }

  /**
   * Merge read and create logs after removing request for req. 
   * a semantic warranty request. 
   *
   * This merges with the current log rather than the parent because we call
   * this from a parent transaction of the transaction that made the request.
   */
  protected void cleanupFailedRequest(SemanticWarrantyRequest req) {
    // Merge reads
    for (Entry<Store, LongKeyMap<ReadMapEntry>> entry : req.reads.nonNullEntrySet())
      for (ReadMapEntry read : entry.getValue().values())
        this.transferReadLock(requestLogs.get(req), read);

    // Merge creates and transfer write locks.
    for (Entry<Store, LongKeyMap<_Impl>> entry : req.creates.nonNullEntrySet()) {
      for (_Impl obj : entry.getValue().values()) {
        creates.add(obj);
        obj.$writeLockHolder = this;
      }
    }
  }

  protected void addReadDependency(long onum) {
    Set<CallInstance> deps = readDependencies.get(onum);
    if (deps == null) {
      deps = new HashSet<CallInstance>();
      readDependencies.put(onum, deps);
    }
    deps.add(semanticWarrantyCall);
  }

  /**
   * Does all the manipulation to create the semantic warranty request at the
   * current log (if possible).  Should only be called when committing the log.
   */
  public void createCurrentRequest() {
    if (semanticWarrantyCall == null) return;

    Store targetStore = semanticWarrantyCall.target.$getStore();
    Store localStore = Worker.getWorker().getLocalStore();

    // Check writes (not including creates)
    int writeCount = 0;
    for (Store store : storesWritten())
      writeCount += getWritesForStore(store).size();

    if (writeCount > 0) {
      Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
          "Semantic warranty request for {0} "
              + "aborted due to writes during the call!",
          semanticWarrantyCall.toString());

      cleanupFailedRequest();
      return;
    }

    // Check reads, collecting localStore reads 
    OidKeyHashMap<ReadMapEntry> readsForTargetStore = new OidKeyHashMap<ReadMapEntry>();
    List<ReadMapEntry> localReads = new ArrayList<ReadMapEntry>();
    for (LongKeyMap<ReadMapEntry> submap : reads) {
      for (ReadMapEntry entry : submap.values()) {
        if (entry.obj.store.equals(localStore)) {
          localReads.add(entry);
        } else if (entry.obj.store.equals(targetStore)) {
          readsForTargetStore.put(entry.obj.store, entry.obj.onum, entry);
        } else {
          Logging
              .log(
                  SEMANTIC_WARRANTY_LOGGER,
                  Level.FINEST,
                  "Semantic warranty request for {0} "
                      + "aborted due to more than one remote store read during the call!",
                  semanticWarrantyCall.toString());

          cleanupFailedRequest();
          return;
        }
      }
    }
    // Collect reads shared by the parent transaction.
    for (ReadMapEntry entry : readsReadByParent) {
      if (entry.obj.store.equals(localStore)) {
        localReads.add(entry);
      } else if (entry.obj.store.equals(targetStore)) {
        readsForTargetStore.put(entry.obj.store, entry.obj.onum, entry);
      } else {
        Logging
            .log(
                SEMANTIC_WARRANTY_LOGGER,
                Level.FINEST,
                "Semantic warranty request for {0} "
                    + "aborted due to more than one remote store read during the call!",
                semanticWarrantyCall.toString());

        cleanupFailedRequest();
        return;
      }
    }

    // Merge localStore reads with parent.
    if (parent != null) {
      for (ReadMapEntry read : localReads)
        parent.transferReadLock(this, read);
      for (Entry<Store, LongKeyMap<ReadMapEntry>> entry :
          readsForTargetStore.nonNullEntrySet())
        for (ReadMapEntry read : entry.getValue().values())
          parent.transferReadLock(this, read, false);
    }

    // Check creates, collecting localStore creates
    OidKeyHashMap<_Impl> createsForTargetStore = new OidKeyHashMap<_Impl>();
    List<_Impl> localCreates = new ArrayList<_Impl>();
    for (_Impl create : creates) {
      if (create.$getStore().equals(localStore)) {
        localCreates.add(create);
      } else if (create.$getStore().equals(targetStore)) {
        SEMANTIC_WARRANTY_LOGGER.finest("TID: " + getTid() + " created " + create.$getOnum());
        createsForTargetStore.put(create, create);
      } else {
        Logging
            .log(
                SEMANTIC_WARRANTY_LOGGER,
                Level.FINEST,
                "Semantic warranty request for {0} "
                    + "aborted due to more than one remote store create during the call!",
                semanticWarrantyCall.toString());

        cleanupFailedRequest();
        return;
      }
    }

    if (parent != null) {
      // Merge local creates with parent
      List<_Impl> parentCreates = parent.creates;
      synchronized (parentCreates) {
        for (_Impl obj : localCreates) {
          parentCreates.add(obj);
          obj.$writeLockHolder = parent;
        }
        for (Entry<Store, LongKeyMap<_Impl>> entry : createsForTargetStore.nonNullEntrySet())
          for (_Impl obj : entry.getValue().values())
            obj.$writeLockHolder = parent;
      }
    }

    // Check calls
    Map<CallInstance, WarrantiedCallResult> callsForTargetStore = new
      HashMap<CallInstance, WarrantiedCallResult>();
    for (Entry<CallInstance, WarrantiedCallResult> entry : semanticWarrantiesUsed.entrySet()) {
      CallInstance call = entry.getKey();
      WarrantiedCallResult result = entry.getValue();
      if (call.target.$getStore().equals(localStore))
        continue;
      else if (call.target.$getStore().equals(targetStore))
        callsForTargetStore.put(call, result);
      else {
        Logging
            .log(
                SEMANTIC_WARRANTY_LOGGER,
                Level.FINEST,
                "Semantic warranty request for {0} "
                    + "aborted due to more than one remote store called during the call!",
                semanticWarrantyCall.toString());

        cleanupFailedRequest();
        return;
      }
    }

    // If we are here, then all reads, creates, call reuses, and results 
    // are on the target store. Now we can make a request object.
    SemanticWarrantyRequest req =
        new SemanticWarrantyRequest(semanticWarrantyCall,
            semanticWarrantyValue, readsForTargetStore, createsForTargetStore,
            callsForTargetStore, getTid());
    /*
    SEMANTIC_WARRANTY_LOGGER.finest("Creating request for " + req.call + getTid());
    for (_Impl create : req.creates) {
        SEMANTIC_WARRANTY_LOGGER.finest("AFTER REQ TID: " + getTid() + " created " + create.$getOnum());
    }
    */
    requests.put(semanticWarrantyCall, req);
    requestLocations.put(semanticWarrantyCall, targetStore);
    requestLogs.put(semanticWarrantyCall, this);

    // add reads to readDependency map
    for (Entry<Store, LongKeyMap<ReadMapEntry>> entry :
        readsForTargetStore.nonNullEntrySet()) {
      for (ReadMapEntry read : entry.getValue().values()) {
        long onum = read.obj.onum;
        addReadDependency(onum);
      }
    }

    // add return to readDependency map
    if (!(semanticWarrantyValue instanceof WrappedJavaInlineable))
      addReadDependency(semanticWarrantyValue.$getOnum());

    // add creates to readDependency map
    for (Entry<Store, LongKeyMap<_Impl>> entry :
        createsForTargetStore.nonNullEntrySet()) {
      for (_Impl create : entry.getValue().values()) {
        addReadDependency(create.$getOnum());
      }
    }

    for (CallInstance call : callsForTargetStore.keySet()) {
      Set<CallInstance> deps = callDependencies.get(call);
      if (deps == null) {
        deps = new HashSet<CallInstance>();
        callDependencies.put(call, deps);
      }
      deps.add(semanticWarrantyCall);
    }
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
    // Don't record the read in this transaction if the child is a semantic 
    // warranty (see also cleanupFailedRequest)
    if (semanticWarrantyCall == null) {
      // Reads unique to this subtransaction
      for (LongKeyMap<ReadMapEntry> submap : reads) {
        for (ReadMapEntry entry : submap.values()) {
          parent.transferReadLock(this, entry);
        }
      }

      // Reads that this subtransaction shared with the parent.
      for (ReadMapEntry entry : readsReadByParent) {
        entry.releaseLock(this);
      }
    }

    // Transfer read and write locks of requests (which should _not_ show up in
    // the reads and creates of the parent transactions.
    for (SemanticWarrantyRequest req : requests.values()) {
      for (Entry<Store, LongKeyMap<ReadMapEntry>> entry :
          req.reads.nonNullEntrySet())
        for (ReadMapEntry read : entry.getValue().values())
          parent.transferReadLock(this, read, false);
      
      for (Entry<Store, LongKeyMap<_Impl>> entry :
          req.creates.nonNullEntrySet())
        for (_Impl create : entry.getValue().values())
          create.$writeLockHolder = parent;
    }

    // Merge writes and transfer write locks.
    List<_Impl> parentWrites = parent.writes;
    for (_Impl obj : writes) {
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

    WeakReferenceArrayList<_Impl> parentLocalStoreWrites =
        parent.localStoreWrites;
    for (_Impl obj : localStoreWrites) {
      synchronized (obj) {
        if (obj.$history.$writeLockHolder == parent) {
          // The parent transaction already wrote to the object. Discard one
          // layer of history. In doing so, we also end up releasing this
          // transaction's write lock.
          obj.$history = obj.$history.$history;
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
        if (obj.$numWaiting > 0) obj.notifyAll();
      }
    }

    // Merge creates and transfer write locks.
    List<_Impl> parentCreates = parent.creates;
    // Don't merge creates if we are trying to
    // create a semantic warranty.      
    // (see also cleanupFailedRequest)
    if (semanticWarrantyCall == null) {
      synchronized (parentCreates) {
        for (_Impl obj : creates) {
          parentCreates.add(obj);
          obj.$writeLockHolder = parent;
        }
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

    // Create this SemanticWarranty request (if there is one)
    createCurrentRequest();

    // Merge all child requests and dependencies
    // TODO: This shouldn't be blindly done.
    if (semanticWarrantyCall == null) {
      parent.semanticWarrantiesUsed.putAll(semanticWarrantiesUsed);
    }

    parent.requests.putAll(requests);
    parent.requestLocations.putAll(requestLocations);
    parent.requestLogs.putAll(requestLogs);

    LongIterator readDependenciesIt = readDependencies.keySet().iterator();
    while (readDependenciesIt.hasNext()) {
      long curRead = readDependenciesIt.next();
      if (parent.readDependencies.containsKey(curRead)) {
        parent.readDependencies.get(curRead).addAll(
            readDependencies.get(curRead));
      } else {
        parent.readDependencies.put(curRead, readDependencies.get(curRead));
      }
    }

    for (CallInstance call : callDependencies.keySet()) {
      if (parent.callDependencies.containsKey(call)) {
        parent.callDependencies.get(call).addAll(callDependencies.get(call));
      } else {
        parent.callDependencies.put(call, callDependencies.get(call));
      }
    }

    synchronized (parent) {
      parent.child = null;
    }
  }

  /**
   * Spawns a thread that will wait until the given commit time before updating
   * logs and data structures in <code>_Impl</code>s to commit this transaction.
   * Assumes this is a top-level transaction. All locks held by this transaction
   * will be released after the given commit time.
   */
  void commitTopLevel(long commitTime) {
    long commitDelay = commitTime - System.currentTimeMillis();
    Logging.WORKER_TRANSACTION_LOGGER
        .finer("Scheduled commit for tid " + tid + " to run at "
            + new Date(commitTime) + " (in " + commitDelay + " ms)");
    Logging.HOTOS_LOGGER.info("Commit delayed "
        + (commitDelay < 0 ? 0 : commitDelay) + " ms");
    Threading.scheduleAt(commitTime, new Runnable() {
      @Override
      public void run() {
        Logging.WORKER_TRANSACTION_LOGGER
            .finer("Updating data structures for commit of tid " + tid);

        // Insert memoized calls into the local call cache
        for (Map.Entry<CallInstance, SemanticWarrantyRequest> e : requests
            .entrySet()) {
          CallInstance id = e.getKey();
          // If we got a warranty, then place it in the local cache.
          // XXX: AFAIK, the only time you don't get a warranty is when you
          // use the local store.
          if (requestReplies.get(id) != null) {
            requestLocations.get(id).insertResult(
                id, new WarrantiedCallResult(e.getValue().value,
                  requestReplies.get(id)));
          }
        }

        // Release read locks.
        for (LongKeyMap<ReadMapEntry> submap : reads) {
          for (ReadMapEntry entry : submap.values()) {
            SEMANTIC_WARRANTY_LOGGER.finest("Releasing read lock on " + entry.obj.onum + " by " + Log.this.getTid());
            entry.releaseLock(Log.this);
          }
        }

        // sanity check
        if (!readsReadByParent.isEmpty())
          throw new InternalError("something was read by a non-existent parent");

        // Release write locks and ownerships; update version numbers.
        @SuppressWarnings("unchecked")
        Iterable<_Impl> chain = SysUtil.chain(writes, localStoreWrites);
        for (_Impl obj : chain) {
          if (!obj.$isOwned) {
            // The cached object is out-of-date. Evict it.
            obj.$ref.evict();
            continue;
          }

          synchronized (obj) {
            obj.$writer = null;
            obj.$writeLockHolder = null;
            obj.$writeLockStackTrace = null;
            obj.$version++;
            obj.$readMapEntry.versionNumber++;
            obj.$isOwned = false;

            // Discard one layer of history.
            obj.$history = obj.$history.$history;

            // Signal any waiting readers/writers.
            if (obj.$numWaiting > 0) obj.notifyAll();
          }
        }

        // Release write locks on created objects and set version numbers.
        @SuppressWarnings("unchecked")
        Iterable<_Impl> chain2 = SysUtil.chain(creates, localStoreCreates);
        for (_Impl obj : chain2) {
          if (!obj.$isOwned) {
            // The cached object is out-of-date. Evict it.
            obj.$ref.evict();
            continue;
          }

          synchronized (obj) {
            obj.$writer = null;
            obj.$writeLockHolder = null;
            obj.$writeLockStackTrace = null;
            obj.$version = 1;
            obj.$readMapEntry.versionNumber = 1;
            obj.$isOwned = false;

            // Signal any waiting readers/writers.
            if (obj.$numWaiting > 0) obj.notifyAll();
          }
        }

        // Merge the security cache into the top-level label cache.
        securityCache.mergeWithTopLevel();
      }
    });
  }

  /**
   * Transfers a read lock from a child transaction.
   */
  private void transferReadLock(Log child, ReadMapEntry readMapEntry) {
    transferReadLock(child, readMapEntry, true);
  }

  /**
   * Transfers a read lock from a child transaction.
   */
  private void transferReadLock(Log child, ReadMapEntry readMapEntry,
      boolean record) {
    SEMANTIC_WARRANTY_LOGGER.finest("Releasing read lock on " + readMapEntry.obj.onum + " by " + child.getTid());
    // If we already have a read lock, return; otherwise, register a read lock.
    boolean lockedByAncestor = false;
    synchronized (readMapEntry) {
      // Release child's read lock.
      readMapEntry.readLocks.remove(child);

      // Scan the list for an existing read lock. At the same time, check if
      // any of our ancestors already has a read lock.
      for (Log cur : readMapEntry.readLocks) {
        if (cur == this) {
          // We already have a lock; nothing to do.
          return;
        }

        if (!lockedByAncestor && isDescendantOf(cur)) lockedByAncestor = true;
      }

      readMapEntry.readLocks.add(this);
    }

    if (record) {
      // Only record the read in this transaction if none of our ancestors have
      // read this object.
      if (!lockedByAncestor) {
        synchronized (reads) {
          reads
              .put(readMapEntry.obj.store, readMapEntry.obj.onum, readMapEntry);
        }
      } else {
        readsReadByParent.add(readMapEntry);
      }
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
    SEMANTIC_WARRANTY_LOGGER.finest("Acquiring read lock on " + obj.$getOnum() + " by " + getTid());
    ReadMapEntry readMapEntry = obj.$readMapEntry;
    boolean lockedByAncestor = false;
    synchronized (readMapEntry) {
      // Scan the list for an existing read lock. At the same time, check if
      // any of our ancestors already has a read lock.
      for (Log cur : readMapEntry.readLocks) {
        if (cur == this) {
          // We already have a lock; nothing to do.
          return;
        }

        if (!lockedByAncestor && isDescendantOf(cur)) lockedByAncestor = true;
      }

      readMapEntry.readLocks.add(this);
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
   * Goes through this transaction log and performs an onum renumbering. This is
   * used by fabric.worker.TransactionRegistery.renumberObject. Do not call this
   * unless if you really know what you are doing.
   * 
   * @deprecated
   */
  @Deprecated
  public void renumberObject(Store store, long onum, long newOnum) {
    ReadMapEntry entry = reads.remove(store, onum);
    if (entry != null) {
      reads.put(store, newOnum, entry);
    }

    if (child != null) child.renumberObject(store, onum, newOnum);
  }

  @Override
  public String toString() {
    return "[" + tid + "]";
  }
}
