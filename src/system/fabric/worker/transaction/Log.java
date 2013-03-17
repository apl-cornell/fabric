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
import java.util.logging.Level;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import fabric.common.Logging;
import fabric.common.SysUtil;
import fabric.common.SemanticWarranty;
import fabric.common.Threading;
import fabric.common.Timing;
import fabric.common.TransactionID;
import fabric.common.VersionWarranty;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.WeakReferenceArrayList;
import fabric.lang.Object._Impl;
import fabric.lang.security.LabelCache;
import fabric.lang.security.SecurityCache;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.memoize.CallID;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.CallResult;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.WriterMap;

/* TODO:
 *      - Double check code for merging a completed subtransaction
 *              + Might want to think about whether writes of requests should
 *              appear in the TL transaction. 
 *      - Store away results given by re-used calls so they can be checked by
 *      the transaction.
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
  protected _Impl semanticWarrantyValue;

  /**
   * Mapping from onums to CallInstances we're requesting semantic warranties
   * for.
   */
  protected final LongKeyMap<Set<CallID>> readDependencies;

  /**
   * Mapping from call ids to CallInstances we're requesting semantic warranties
   * for.
   */
  protected final Map<CallID, Set<CallID>> callDependencies;

  /**
   * Set of CallInstance ids for semantic warranties used during this
   * transaction.
   */
  protected final Map<CallInstance, CallResult> semanticWarrantiesUsed;

  /**
   * Map from call ID to SemanticWarrantyRequests made by subtransactions.
   */
  protected final Map<CallID, SemanticWarrantyRequest> requests;

  /**
   * Map from call ID to SemanticWarrantyRequests made by subtransactions.
   */
  protected final Map<CallID, CallInstance> requestInstances;

  /**
   * Map from call ID to the store where the request will go.
   */
  protected final Map<CallID, Store> requestLocations;

  /**
   * Map from call ID to the store's CallResult response which will be stored on
   * successfully finishing the commit phase.
   */
  protected final Map<CallID, SemanticWarranty> requestReplies;

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
    this.semanticWarrantiesUsed = new HashMap<CallInstance, CallResult>();
    this.readDependencies = new LongKeyHashMap<Set<CallID>>();
    this.callDependencies = new HashMap<CallID, Set<CallID>>();
    this.requests = new HashMap<CallID, SemanticWarrantyRequest>();
    this.requestInstances = new HashMap<CallID, CallInstance>();
    this.requestLocations = new HashMap<CallID, Store>();
    this.requestReplies = Collections.synchronizedMap(
        new HashMap<CallID, SemanticWarranty>());

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
    Set<CallID> dependencies = readDependencies.get(onum);
    if (dependencies == null) return;
    for (CallID id : dependencies)
      removeRequest(id);
  }

  /**
   * Remove a semantic warranty request created by a subtransaction along with
   * all associated book keeping.
   */
  private void removeRequest(CallID callId) {
    /* TODO: Invalidate other calls that depend on this */
    SemanticWarrantyRequest req = requests.get(callId);
  
    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
        "Call {0} warranty request dropped", req.call.toString());

    LongIterator it = req.reads.iterator();
    while (it.hasNext()) {
      readDependencies.remove(it.next());
    }

    for (CallID call : req.calls) {
      callDependencies.remove(call);
    }

    requestLocations.remove(callId);
    requestInstances.remove(callId);
    requests.remove(callId);
  }

  /**
   * Gets a result, if any, for a request we haven't pushed to the store yet
   */
  public CallResult getRequestResult(CallInstance call) {
    SemanticWarrantyRequest req = requests.get(call.id());
    if (req != null) return new CallResult(req.value, new SemanticWarranty(0));
    return null;
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
    for (Entry<Store, LongKeyMap<ReadMapEntry>> entry : reads.nonNullEntrySet()) {
      Store store = entry.getKey();
      LongKeyMap<Integer> submap = new LongKeyHashMap<Integer>();
      for (LongKeyMap.Entry<ReadMapEntry> subEntry : filterModifiedReads(store,
          entry.getValue()).entrySet()) {
        long onum = subEntry.getKey();
        ReadMapEntry rme = subEntry.getValue();
        if (rme.warranty.expiresAfter(commitState.commitTime, true)
            && rme.warranty.expiresBefore(commitTime, true)) {
          submap.put(onum, rme.versionNumber);
        }
      }

      if (!submap.isEmpty()) result.put(store, submap);
    }

    return result;
  }

  /**
   * Returns a mapping of stores to call IDs, indicating those calls reused by
   * this transaction, whose semantic warranties expire between
   * commitState.commitTime (exclusive) and the given commitTime (inclusive).
   */
  Map<Store, Set<CallID>> storesCalled(long commitTime) {
    Map<Store, Set<CallID>> result = new HashMap<Store, Set<CallID>>();
    for (Entry<CallInstance, CallResult> e
        : semanticWarrantiesUsed.entrySet()) {
      Store store = e.getKey().target.$getStore();
      CallID call = e.getKey().id();
      CallResult callRes = e.getValue();
      if (callRes.warranty.expiresBefore(commitTime, true)
          && !requests.containsKey(call)) {
        Set<CallID> requestsAtStore = result.get(store);
        if (requestsAtStore == null) {
          requestsAtStore = new HashSet<CallID>();
          result.put(store, requestsAtStore);
        }
        requestsAtStore.add(e.getKey().id());
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

  void updateVersionWarranties(Store store, LongSet onums, long commitTime) {
    if (store.isLocalStore()) return;

    VersionWarranty warranty = new VersionWarranty(commitTime);

    for (LongIterator it = onums.iterator(); it.hasNext();) {
      long onum = it.next();
      ReadMapEntry entry = reads.get(store, onum);
      entry.warranty = warranty;
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
    Set<SemanticWarrantyRequest> reqSet = new HashSet<SemanticWarrantyRequest>();
    for (SemanticWarrantyRequest r : requests.values())
      if (requestLocations.get(r.call) == store)
        reqSet.add(r);
    return reqSet;
  }

  /**
   * Returns the set of call ids used from a given store
   */
  Set<CallID> getCallsForStore(Store store) {
    Set<CallID> requestsSet = new HashSet<CallID>();
    for (SemanticWarrantyRequest req : getRequestsForStore(store))
      requestsSet.add(req.call);
    Set<CallID> callSet = new HashSet<CallID>();
    for (CallInstance c : semanticWarrantiesUsed.keySet())
      if (c.target.$getStore() == store && !requestsSet.contains(c.id()))
        callSet.add(c.id());
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
   * Does all the manipulation to create the semantic warranty request at the
   * current log (if possible).  Should only be called when committing the log.
   */
  public void createCurrentRequest() {
    if (semanticWarrantyCall != null && writes.size() > 0) {
      Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
          "Semantic warranty request for {0} "
          + "aborted due to writes during the call!",
          semanticWarrantyCall.toString());
    }
    if (semanticWarrantyCall != null && writes.size() == 0) {
      // Add call to readDependencies and build up reads set.
      LongSet readSet = new LongHashSet();
      for (LongKeyMap<ReadMapEntry> submap : reads) {
        for (ReadMapEntry entry : submap.values()) {
          readSet.add(entry.obj.onum);

          if (parent != null) {
            Set<CallID> dependencies = parent.readDependencies.get(entry.obj.onum);
            if (dependencies == null) {
              dependencies = new HashSet<CallID>();
              parent.readDependencies.put(entry.obj.onum, dependencies);
            }
            dependencies.add(semanticWarrantyCall.id());
          }
        }
      }

      Set<CallID> callSet = new HashSet<CallID>();
      for (CallInstance c : semanticWarrantiesUsed.keySet()) {
        callSet.add(c.id());
        if (parent != null) {
          Set<CallID> dependencies = parent.callDependencies.get(c.id());
          if (dependencies == null) {
            dependencies = new HashSet<CallID>();
            parent.callDependencies.put(c.id(), dependencies);
          }
          dependencies.add(semanticWarrantyCall.id());
        }
      }

      Store targetStore = semanticWarrantyCall.target.$getStore();
      LongSet readsForTargetStore = getReadsForStore(targetStore).keySet();
      Collection<_Impl> createsForTargetStore = getCreatesForStore(targetStore);
      Set<CallID> callsForTargetStore = getCallsForStore(targetStore);

      if (readsForTargetStore.containsAll(readSet) &&
          callsForTargetStore.containsAll(callSet) &&
          createsForTargetStore.containsAll(creates)) {
        // Create the request object only if we keep all of the reads and call
        // reuses on a single store (the same as where we're storing the target
        // of the call).
        //
        // Include creates as part of the group of things I have to defend.
        if (readsForTargetStore.isEmpty()) {
          // Because apparently the set might be an empty set which can't have
          // items added...
          readsForTargetStore = new LongHashSet();
        }
        for (_Impl createdObj : createsForTargetStore) {
          readsForTargetStore.add(createdObj.$getOnum());
        }
        SemanticWarrantyRequest req = new
          SemanticWarrantyRequest(semanticWarrantyCall, semanticWarrantyValue,
              readSet, callSet);
        requests.put(semanticWarrantyCall.id(), req);
        requestLocations.put(semanticWarrantyCall.id(), targetStore);
        requestInstances.put(semanticWarrantyCall.id(), semanticWarrantyCall);
        //targetStore.insertResult(semanticWarrantyCall, new CallResult(req.value,
              //new SemanticWarranty(0)));
      } else if (parent != null) {
        // Otherwise, remove the dependency mappings in the parent.
        LongIterator it = readSet.iterator();
        while (it.hasNext()) {
          parent.readDependencies.get(it.next()).remove(semanticWarrantyCall.id());
        }

        for (CallID call : callSet) {
          parent.callDependencies.get(call).remove(semanticWarrantyCall.id());
        }
      }
    }
  }

  /**
   * Updates logs and data structures in <code>_Impl</code>s to commit this
   * transaction. Assumes there is a parent transaction. This transaction log is
   * merged into the parent's log and any locks held are transferred to the
   * parent.
   */
  /* TODO: Should I not merge writes of semantic warranty requests? */
  void commitNested() {
    // TODO See if lazy merging of logs helps performance.

    if (parent == null || !parent.tid.equals(tid.parent)) {
      // Reuse this frame for the parent transaction.
      return;
    }

    // Merge reads and transfer read locks.
    for (LongKeyMap<ReadMapEntry> submap : reads) {
      for (ReadMapEntry entry : submap.values()) {
        parent.transferReadLock(this, entry);
      }
    }

    for (ReadMapEntry entry : readsReadByParent) {
      entry.releaseLock(this);
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
    synchronized (parentCreates) {
      for (_Impl obj : creates) {
        parentCreates.add(obj);
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

    // Create this SemanticWarranty request (if there is one)
    createCurrentRequest();

    // Merge all child requests and dependencies
    parent.semanticWarrantiesUsed.putAll(semanticWarrantiesUsed);
    parent.requests.putAll(requests);
    parent.requestInstances.putAll(requestInstances);
    parent.requestLocations.putAll(requestLocations);

    LongIterator readDependenciesIt = readDependencies.keySet().iterator();
    while (readDependenciesIt.hasNext()) {
      long curRead = readDependenciesIt.next();
      if (parent.readDependencies.containsKey(curRead)) {
        parent.readDependencies.get(curRead).addAll(readDependencies.get(curRead));
      } else {
        parent.readDependencies.put(curRead, readDependencies.get(curRead));
      }
    }

    for (CallID call : callDependencies.keySet()) {
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
    Logging.WORKER_TRANSACTION_LOGGER.finer("Scheduled commit for tid " + tid
        + " to run at " + new Date(commitTime) + " (in "
        + (commitTime - System.currentTimeMillis()) + " ms)");
    Threading.scheduleAt(commitTime, new Runnable() {
      @Override
      public void run() {
        Logging.WORKER_TRANSACTION_LOGGER
            .finer("Updating data structures for commit of tid " + tid);

        // Insert memoized calls into the local call cache
        for (Map.Entry<CallID, SemanticWarrantyRequest> e
          : requests.entrySet()) {
          CallID id = e.getKey();
          // If we got a warranty, then place it in the local cache.
          // XXX: AFAIK, the only time you don't get a warranty is when you
          // use the local store.
          if (requestReplies.get(id) != null) {
            requestLocations.get(id).insertResult(requestInstances.get(id),
              new CallResult(e.getValue().value, requestReplies.get(id)));
          }
        }

        // Release read locks.
        for (LongKeyMap<ReadMapEntry> submap : reads) {
          for (ReadMapEntry entry : submap.values()) {
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

    // Only record the read in this transaction if none of our ancestors have
    // read this object.
    if (!lockedByAncestor) {
      synchronized (reads) {
        reads.put(readMapEntry.obj.store, readMapEntry.obj.onum, readMapEntry);
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
