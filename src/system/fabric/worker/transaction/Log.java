package fabric.worker.transaction;

import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;
import static fabric.common.Logging.WORKER_TRANSACTION_LOGGER;

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
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.common.util.WeakReferenceArrayList;
import fabric.lang.Object._Impl;
import fabric.lang.WrappedJavaInlineable;
import fabric.lang.security.LabelCache;
import fabric.lang.security.SecurityCache;
import fabric.worker.FabricSoftRef;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.memoize.WarrantiedCallResult;
import fabric.worker.remote.RemoteWorker;
import fabric.worker.remote.WriterMap;

/**
 * Stores per-transaction information. Records the objects that are created,
 * read, and written during a single nested transaction.
 */
// TODO: fix onum vs. oid mess.
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
  protected final OidKeyHashMap<ReadMap.Entry> reads;

  /**
   * Reads that were made by memoized subcalls.
   */
  protected final OidKeyHashMap<ReadMap.Entry> readsInSubcalls;

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
  protected final LongKeyMap<_Impl> creates;

  /**
   * Creates that were not made by memoized subcalls.
   */
  protected final LongKeyMap<_Impl> createsInSubcalls;

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
   * Set of calls for which semantic warranties should _not_ be reused until it
   * has been recomputed during the transaction.  This is used for call checking
   * at the store.
   */
  public final Set<CallInstance> blockedWarranties;

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
   * Set of CallInstance ids for semantic warranties used during this
   * transaction that are not used by another call.
   *
   * Think of this as calls used but we don't want counting as dependencies of
   * any currently computing calls.
   */
  protected final Map<CallInstance, WarrantiedCallResult> callsInSubcalls;

  /**
   * Map from call ID to SemanticWarrantyRequests made by subtransactions.
   */
  protected final Map<CallInstance, SemanticWarrantyRequest> requests;

  /**
   * Map from call ID to the store where the request will go.
   */
  protected final Map<CallInstance, Store> requestLocations;

  /**
   * Map from call ID to the store's CallResult response which will be stored on
   * successfully finishing the commit phase.
   */
  protected final Map<CallInstance, SemanticWarranty> requestReplies;

  /**
   * Maps OIDs to the version number of the object and version warranty for
   * objects read in call updates from write prepare.  This allows us to give
   * back additional reads to check during the read prepare phase.
   */
  protected OidKeyHashMap<Pair<Integer, VersionWarranty>> addedReads;

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
     * state, objects whose version might be changed expire before this time are
     * (being) read-prepared.
     */
    public long commitTime = 0;

    /**
     * The set of stores that have been contacted by the commit protocol which
     * are possibly holding indefinite locks (so this will not include stores
     * contacted to extend a lease or warranty).
     */
    public final Set<Store> storesContacted = new HashSet<>();
  }

  public final AbstractSecurityCache securityCache;

  /**
   * The time at which this subtransaction was started.
   */
  public final long startTime;

  /**
   * If a transaction T's log appears in this set, then this transaction is
   * waiting for a lock that is held by transaction T.
   */
  private final Set<Log> waitsFor;

  /**
   * Look-aside caches for objects written/created and being call-checked
   * against.
   *
   * Items should ONLY be entered for the purposes of call checking and ONLY
   * from the top level!
   */
  private ObjectLookAsideMap writeLookAsideMap;
  private ObjectLookAsideMap createLookAsideMap;

  /**
   * Keep track of what has been already registered as written or created from
   * the look aside caches.
   */
  private Map<Store, LongSet> lookAsideWritesRegistered;
  private Map<Store, LongSet> lookAsideCreatesRegistered;

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
    this.reads = new OidKeyHashMap<>();
    this.readsInSubcalls = new OidKeyHashMap<>();
    this.readsReadByParent = new ArrayList<>();
    this.creates = new LongKeyHashMap<>();
    this.createsInSubcalls = new LongKeyHashMap<>();
    this.localStoreCreates = new WeakReferenceArrayList<>();
    this.writes = new ArrayList<>();
    this.localStoreWrites = new WeakReferenceArrayList<>();
    this.workersCalled = new ArrayList<>();
    this.semanticWarrantyCall = semanticWarrantyCall;
    this.semanticWarrantiesUsed = new HashMap<>();
    this.callsInSubcalls = new HashMap<>();
    this.readDependencies = new LongKeyHashMap<>();
    this.callDependencies = new HashMap<>();
    this.requests = new HashMap<>();
    this.requestLocations = new HashMap<>();
    this.requestReplies = Collections.synchronizedMap(
        new HashMap<CallInstance, SemanticWarranty>());
    this.blockedWarranties = new HashSet<>();
    this.startTime = System.currentTimeMillis();
    this.waitsFor = new HashSet<>();

    if (parent != null) {
      this.blockedWarranties.addAll(parent.blockedWarranties);
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

      this.writeLookAsideMap = parent.writeLookAsideMap;
      this.createLookAsideMap = parent.createLookAsideMap;
      this.lookAsideWritesRegistered = parent.lookAsideWritesRegistered;
      this.lookAsideCreatesRegistered = parent.lookAsideCreatesRegistered;
    } else {
      this.writerMap = new WriterMap(this.tid.topTid);
      commitState = new CommitState();

      LabelCache labelCache = Worker.getWorker().labelCache;
      this.securityCache = new SecurityCache(labelCache);

      this.writeLookAsideMap = null;
      this.createLookAsideMap = null;
      this.lookAsideWritesRegistered = new HashMap<>();
      this.lookAsideCreatesRegistered = new HashMap<>();

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
   * Set the look aside maps for call checking.  Should never be used outside of
   * semantic warranty call checking.
   */
  public void setLookAsideMaps(ObjectLookAsideMap w, ObjectLookAsideMap c) {
    writeLookAsideMap = w;
    createLookAsideMap = c;
    if (parent != null) parent.setLookAsideMaps(w, c);
  }

  /**
   * Check if we have look aside maps we're using (are we checking for updates
   * on semantic warranties?).
   */
  public boolean hasLookAsides() {
    return writeLookAsideMap != null && createLookAsideMap != null;
  }

  public boolean registeredInLookAside(Store store, long onum) {
    return (lookAsideWritesRegistered.containsKey(store) &&
        lookAsideWritesRegistered.get(store).contains(onum)) ||
      (lookAsideCreatesRegistered.containsKey(store) &&
       lookAsideCreatesRegistered.get(store).contains(onum));
  }

  /**
   * Fetch the object from the look aside maps (values that we're call checking
   * against) if it's there.
   */
  public _Impl fetchFromLookAside(Store store, long onum) {
    if (!hasLookAsides()) return null;
    _Impl result = writeLookAsideMap.get(store, onum);
    if (result != null) {
      if (!registeredInLookAside(store, onum)) {
        TransactionManager.getInstance().registerWrite(result);
        if (!lookAsideWritesRegistered.containsKey(store))
          lookAsideWritesRegistered.put(store, new LongHashSet());
        lookAsideWritesRegistered.get(store).add(onum);
      }
      return result;
    }
    result = createLookAsideMap.get(store, onum);
    if (result != null) {
      if (!registeredInLookAside(store, onum)) {
        TransactionManager.getInstance().registerCreate(result);
        if (!lookAsideCreatesRegistered.containsKey(store))
          lookAsideCreatesRegistered.put(store, new LongHashSet());
        lookAsideCreatesRegistered.get(store).add(onum);
        creates.put(onum, result);
      }
      return result;
    }
    return null;
  }

  /**
   * Get create for the given oid.  THIS SHOULD ONLY BE CALLED BY
   * Object._Proxy.fetchEntry.
   */
  public _Impl getCreate(long oid) {
    Log cur = this;
    while (cur != null) {
      for (_Impl create : SysUtil.chain(cur.creates.values(),
          cur.createsInSubcalls.values()))
        if (create.$getOnum() == oid) return create;
      cur = cur.parent;
    }
    return null;
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
  public void invalidateDependentRequests(Store store, long onum) {
    // Don't invalidate if we added it for call checking
    if (registeredInLookAside(store, onum)) return;
    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
        "Onum {0} written, invalidating calls that read this.", onum);
    Set<CallInstance> dependencies = readDependencies.get(onum);
    if (dependencies == null) return;
    for (CallInstance id : new HashSet<>(dependencies))
      removeRequest(id);
  }

  /**
   * Remove a semantic warranty request created by a subtransaction along with
   * all associated book keeping.
   */
  // TODO: This could probably avoid a lot of add/remove operations if we have
  // time to refactor this.
  private void removeRequest(CallInstance callId) {
    SemanticWarrantyRequest req = requests.get(callId);

    if (req == null) return; // Request already removed.

    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
        "Call {0} warranty request dropped", req.call);

    if (callDependencies.get(callId) != null
        && callDependencies.get(callId).size() > 0) {
      // Calls that used this call must get passed up the dependencies and also
      // be removed
      for (CallInstance call : callDependencies.get(callId)) {
        // Pass up reads
        for (Store s : req.reads.storeSet()) {
          for (LongKeyMap.Entry<ReadMap.Entry> entry : req.reads.get(s)
              .entrySet()) {
            requests.get(call).reads.put(s, entry.getKey(), entry.getValue());
            addReadDependency(entry.getValue().getRef().onum);
          }
        }

        // Pass up creates
        for (Store s : req.creates.storeSet()) {
          for (LongKeyMap.Entry<_Impl> entry : req.creates.get(s).entrySet()) {
            requests.get(call).creates.put(s, entry.getKey(), entry.getValue());
            addReadDependency(entry.getValue().$getOnum());
          }
        }

        // Pass up calls
        for (CallInstance subcall : req.calls.keySet()) {
          callDependencies.get(subcall).add(call);
          requests.get(call).calls.put(subcall, req.calls.get(subcall));
        }

        // Remove the call because if subcall was invalidated, the parent call
        // must also be in question.
        removeRequest(call);
      }
    } else {
      // Nothing depended on this, move dependencies back into the rest of the
      // world.
      // Remove reads
      for (Store s : req.reads.storeSet()) {
        for (LongKeyMap.Entry<ReadMap.Entry> entry : req.reads.get(s)
            .entrySet()) {
          readsInSubcalls.remove(s, entry.getKey());
          reads.put(s, entry.getKey(), entry.getValue());
        }
      }

      // Remove creates
      for (Store s : req.creates.storeSet()) {
        for (_Impl obj : req.creates.get(s).values()) {
          createsInSubcalls.remove(obj.$getOnum());
          creates.put(obj.$getOnum(), obj);
        }
      }

      // Remove calls
      for (CallInstance subcall : req.calls.keySet()) {
        callsInSubcalls.remove(subcall);
        semanticWarrantiesUsed.put(subcall, req.calls.get(subcall));
      }
    }

    // Remove this call from set of calls dependent on each read.
    for (Entry<Store, LongKeyMap<ReadMap.Entry>> entry : req.reads
        .nonNullEntrySet())
      for (ReadMap.Entry read : entry.getValue().values())
        readDependencies.get(read.getRef().onum).remove(req.call);

    // Remove this call from set of calls dependent on each create.
    for (Entry<Store, LongKeyMap<_Impl>> entry : req.creates.nonNullEntrySet())
      for (_Impl create : entry.getValue().values())
        readDependencies.get(create.$getOnum()).remove(req.call);

    // This call no longer depends on its various subcalls
    for (CallInstance call : req.calls.keySet()) {
      callDependencies.get(call).remove(callId);
    }

    requestLocations.remove(callId);
    requests.remove(callId);

    callDependencies.remove(callId);
  }

  /**
   * Utility Method for getting the full set of creates for a given requested
   * semantic warranty call.
   */
  private LongSet getAllRequestCreates(CallInstance call) {
    //if (requests.containsKey(call)) {
    //  SemanticWarrantyRequest req = requests.get(call);
    //  LongSet createOids = new LongHashSet(req.createOnums);
    //  for (WarrantiedCallResult subcallResult : req.calls.values())
    //    createOids.addAll(subcallResult.getCreates());
    //  return createOids;
    //}
    return new LongHashSet();
  }

  /**
   * Gets a result, if any, for a request we haven't pushed to the store yet
   */
  public WarrantiedCallResult getRequestResult(CallInstance call) {
    SemanticWarrantyRequest req = requests.get(call);
    if (req != null) {
      return new WarrantiedCallResult(req.value, new SemanticWarranty(0),
          getAllRequestCreates(call));
    }
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
   * Gets all SemanticWarrantyRequests.  THIS SHOULD ONLY BE USED BY THE STORE
   * DURING CALL CHECKING.
   */
  public Map<CallInstance, SemanticWarrantyRequest> getAllRequests() {
    return new HashMap<>(requests);
  }

  /**
   * @return true if the transaction is not distributed and neither creates nor
   *         modifies objects on remote stores.
   */
  public boolean isReadOnly() {
    return writes.isEmpty() && creates.isEmpty() && workersCalled.isEmpty()
        && requests.isEmpty() && createsInSubcalls.isEmpty();
  }

  /**
   * Returns a mapping of stores to (mappings of onums to version numbers),
   * indicating those objects read (but not modified) by this transaction that
   * could be updated in the time between commitState.commitTime (exclusive) and
   * the given commitTime (inclusive).
   */
  Map<Store, LongKeyMap<Integer>> storesRead(long commitTime) {
    Map<Store, LongKeyMap<Integer>> result = new HashMap<>();
    // Handle reads from the actual transaction
    Iterable<Entry<Store, LongKeyMap<ReadMap.Entry>>> chain =
        SysUtil.chain(reads.nonNullEntrySet(),
            readsInSubcalls.nonNullEntrySet());
    for (Entry<Store, LongKeyMap<ReadMap.Entry>> entry : chain) {
      Store store = entry.getKey();
      LongKeyMap<Integer> submap = new LongKeyHashMap<>();
      LongKeyMap<ReadMap.Entry> readOnlyObjects =
          filterModifiedReads(store, entry.getValue());

      for (LongKeyMap.Entry<ReadMap.Entry> subEntry : readOnlyObjects
          .entrySet()) {
        long onum = subEntry.getKey();
        ReadMap.Entry rme = subEntry.getValue();

        if (rme.getWarranty().expiresBefore(commitTime, true)) {
          submap.put(onum, rme.getVersionNumber());
        }
      }

      if (!submap.isEmpty()) {
        result.put(store, submap);
      }
    }

    // Handle added reads from write prepare
    if (addedReads != null) {
      for (Entry<Store, LongKeyMap<Pair<Integer, VersionWarranty>>> entry : addedReads
          .nonNullEntrySet()) {
        Store store = entry.getKey();
        LongKeyMap<Integer> submap = null;
        if (result.containsKey(store)) {
          submap = result.get(store);
        } else {
          submap = new LongKeyHashMap<>();
        }

        LongKeyMap<Pair<Integer, VersionWarranty>> readOnly =
            filterModifiedReads(store, entry.getValue());

        for (LongKeyMap.Entry<Pair<Integer, VersionWarranty>> subEntry : readOnly
            .entrySet()) {
          long onum = subEntry.getKey();
          int objVersion = subEntry.getValue().first;
          VersionWarranty objWarranty = subEntry.getValue().second;

          if (objWarranty.expiresBefore(commitTime, true)) {
            submap.put(onum, objVersion);
          }
        }

        if (!submap.isEmpty()) {
          result.put(store, submap);
        }
      }
    }

    return result;
  }

  /**
   * Returns a mapping of stores to call IDs, indicating those calls reused by
   * this transaction, whose semantic warranties expire between
   * commitState.commitTime (exclusive) and the given commitTime (inclusive).
   */
  Map<Store, Set<CallInstance>> storesCalled(long commitTime) {
    Map<Store, Set<CallInstance>> result = new HashMap<>();
    for (Entry<CallInstance, WarrantiedCallResult> e : SysUtil.chain(
        semanticWarrantiesUsed.entrySet(), callsInSubcalls.entrySet())) {
      Store store = e.getKey().target.$getStore();
      CallInstance call = e.getKey();
      WarrantiedCallResult callRes = e.getValue();
      if (callRes.getWarranty().expiresBefore(commitTime, true)
          && !requests.containsKey(call)) {
        Set<CallInstance> requestsAtStore = result.get(store);
        if (requestsAtStore == null) {
          requestsAtStore = new HashSet<>();
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
    return new HashSet<>(requestLocations.values());
  }

  /**
   * Update the warranties we needed to extend at the given store.
   * After a transaction has been prepared, update version warranties for
   * objects from that store.
   */
  void updateVersionWarranties(Store store,
      LongKeyMap<VersionWarranty> newWarranties) {
    if (store.isLocalStore()) return;

    for (LongKeyMap.Entry<VersionWarranty> entry : newWarranties.entrySet()) {
      long onum = entry.getKey();
      VersionWarranty warranty = entry.getValue();

      ReadMap.Entry rme = reads.get(store, onum);
      if (rme != null) {
        rme.updateWarranty(warranty);
      } else {
        rme = readsInSubcalls.get(store, onum);
        if (rme != null) {
          rme.updateWarranty(warranty);
        }
      }
    }
  }

  /**
   * Update the warranties we needed to extend at the given store.
   */
  void updateSemanticWarranties(Store store,
      Map<CallInstance, WarrantiedCallResult> newWarranties) {
    for (Map.Entry<CallInstance, WarrantiedCallResult> entry : newWarranties
        .entrySet()) {
      CallInstance call = entry.getKey();
      WarrantiedCallResult value = entry.getValue();
      store.insertResult(call, value);
    }
  }

  private <V> LongKeyMap<V> filterModifiedReads(Store store, LongKeyMap<V> map) {
    map = new LongKeyHashMap<>(map);

    if (store.isLocalStore()) {
      Iterable<_Impl> chain =
          SysUtil.chain(localStoreWrites, localStoreCreates);
      for (_Impl write : chain)
        map.remove(write.$getOnum());
    } else {
      Iterable<_Impl> chain =
          SysUtil.chain(SysUtil.chain(writes, creates.values()),
              createsInSubcalls.values());
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
    Set<Store> result = new HashSet<>();

    for (_Impl obj : writes) {
      if (obj.$isOwned) result.add(obj.$getStore());
    }

    for (_Impl obj : SysUtil
        .chain(creates.values(), createsInSubcalls.values())) {
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
   */
  LongKeyMap<Integer> getReadsForStore(Store store) {
    LongKeyMap<Integer> result = new LongKeyHashMap<>();
    LongKeyMap<ReadMap.Entry> submap = reads.get(store);
    if (submap != null) {
      for (LongKeyMap.Entry<ReadMap.Entry> entry : submap.entrySet()) {
        result.put(entry.getKey(), entry.getValue().getVersionNumber());
      }
    }

    LongKeyMap<ReadMap.Entry> submap2 = readsInSubcalls.get(store);
    if (submap2 != null) {
      for (LongKeyMap.Entry<ReadMap.Entry> entry : submap2.entrySet()) {
        result.put(entry.getKey(), entry.getValue().getVersionNumber());
      }
    }

    if (parent != null) {
      for (ReadMap.Entry entry : readsReadByParent) {
        FabricSoftRef entryRef = entry.getRef();
        if (store.equals(entryRef.store)) {
          result.put(entryRef.onum, entry.getVersionNumber());
        }
      }
    }

    Log curLog = this;
    while (curLog != null) {
      if (store.isLocalStore()) {
        for (_Impl create : curLog.localStoreCreates)
          result.remove(create.$getOnum());
      } else {
        for (_Impl create : curLog.creates.values())
          if (create.$getStore() == store) result.remove(create.$getOnum());
        for (_Impl create : curLog.createsInSubcalls.values())
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
    LongKeyMap<_Impl> result = new LongKeyHashMap<>();

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

      for (_Impl create : SysUtil.chain(creates.values(),
          createsInSubcalls.values()))
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
    LongKeyMap<_Impl> result = new LongKeyHashMap<>();

    if (store.isLocalStore()) {
      for (_Impl obj : localStoreCreates) {
        result.put(obj.$getOnum(), obj);
      }
    } else {
      for (_Impl obj : SysUtil.chain(creates.values(),
          createsInSubcalls.values()))
        if (obj.$getStore() == store && obj.$isOwned)
          result.put(obj.$getOnum(), obj);
    }

    return result.values();
  }

  /**
   * Returns the set of requests that will be stored on the given store.
   */
  Set<SemanticWarrantyRequest> getRequestsForStore(Store store) {
    Set<SemanticWarrantyRequest> reqSet = new HashSet<>();
    for (SemanticWarrantyRequest r : requests.values())
      if (requestLocations.get(r.call) == store) reqSet.add(r);
    return reqSet;
  }

  /**
   * Add all entries from the given request map to the current requests pool.
   *
   * XXX: THIS IS A HACK FOR CALL CHECKING.  THIS SHOULD NOT BE USED _ANYWHERE_
   * ELSE.
   */
  public void addRequests(Map<CallInstance, SemanticWarrantyRequest> updates) {
    requests.putAll(updates);
  }

  /**
   * Returns the set of call ids used from a given store
   */
  Map<CallInstance, WarrantiedCallResult> getCallsForStore(Store store) {
    Set<CallInstance> requestsSet = new HashSet<>();
    for (SemanticWarrantyRequest req : getRequestsForStore(store))
      requestsSet.add(req.call);
    Map<CallInstance, WarrantiedCallResult> callSet = new HashMap<>();
    for (CallInstance c : semanticWarrantiesUsed.keySet())
      if (c.target.$getStore() == store && !requestsSet.contains(c))
        callSet.put(c, semanticWarrantiesUsed.get(c));
    for (CallInstance c : callsInSubcalls.keySet())
      if (c.target.$getStore() == store && !requestsSet.contains(c))
        callSet.put(c, callsInSubcalls.get(c));
    return callSet;
  }

  /**
   * Sets the retry flag on this and the logs of all sub-transactions.
   */
  public void flagRetry() {
    Queue<Log> toFlag = new LinkedList<>();
    toFlag.add(this);
    while (!toFlag.isEmpty()) {
      Log log = toFlag.remove();
      synchronized (log) {
        if (log.child != null) toFlag.add(log.child);
        if (log.retrySignal == null || log.retrySignal.isDescendantOf(tid)) {
          log.retrySignal = tid;
          // XXX This was here to unblock a thread that may have been waiting on
          // XXX a lock. Commented out because it was causing a bunch of
          // XXX InterruptedExceptions and ClosedByInterruptExceptions that
          // XXX weren't being handled properly. This includes improper or
          // XXX insufficient handling by Java code in the examples (e.g.,
          // XXX Jetty).
          // XXX Instead, when a thread is blocked waiting on an object lock, we
          // XXX spin once a second to check the retry signal.

          // log.thread.interrupt();
        }
      }
    }
  }

  /**
   * Updates logs and data structures in <code>_Impl</code>s to abort this
   * transaction. All locks held by this transaction are released.
   */
  void abort() {
    // Release read locks.
    for (LongKeyMap<ReadMap.Entry> submap : SysUtil.chain(reads,
        readsInSubcalls)) {
      for (ReadMap.Entry entry : submap.values()) {
        entry.releaseLock(this);
      }
    }

    for (ReadMap.Entry entry : readsReadByParent)
      entry.releaseLock(this);

    // Roll back writes and release write locks.
    Iterable<_Impl> chain = SysUtil.chain(writes, localStoreWrites);
    for (_Impl write : chain) {
      lookAsideWritesRegistered.remove(write.$getOnum());

      synchronized (write) {
        write.$copyStateFrom(write.$history);

        // Signal any waiting readers/writers.
        if (write.$numWaiting > 0) write.notifyAll();
      }
    }

    // Release write locks on creates.
    Iterable<_Impl> chain2 =
        SysUtil.chain(SysUtil.chain(creates.values(), localStoreCreates),
            createsInSubcalls.values());
    for (_Impl obj : chain2) {
      lookAsideCreatesRegistered.remove(obj.$getOnum());

      synchronized (obj) {
        obj.$writer = null;
        obj.$writeLockHolder = null;
        obj.$writeLockStackTrace = null;
        //obj.$version = 1;
        //obj.$readMapEntry.incrementVersion();
        obj.$isOwned = false;

        // Signal any waiting readers/writers.
        if (obj.$numWaiting > 0) obj.notifyAll();
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
      readsInSubcalls.clear();
      creates.clear();
      createsInSubcalls.clear();
      localStoreCreates.clear();
      writes.clear();
      localStoreWrites.clear();
      workersCalled.clear();
      securityCache.reset();
      // Clear out semantic warranty state.
      semanticWarrantiesUsed.clear();
      callsInSubcalls.clear();
      readDependencies.clear();
      callDependencies.clear();
      requests.clear();
      requestLocations.clear();
      requestReplies.clear();
      addedReads = null;
      // Clear out lookAside meta data
      lookAsideWritesRegistered.clear();
      lookAsideCreatesRegistered.clear();

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

  protected void addReadDependency(long onum) {
    Set<CallInstance> deps = readDependencies.get(onum);
    if (deps == null) {
      deps = new HashSet<>();
      readDependencies.put(onum, deps);
    }
    deps.add(semanticWarrantyCall);
  }

  /**
   * Does all the manipulation to create the semantic warranty request at the
   * current log (if possible).  Should only be called when committing the log.
   */
  //TODO: We sort of ignore whether there's local values that were read here...
  public void createCurrentRequest() {
    if (semanticWarrantyCall == null) return;

    Store targetStore = semanticWarrantyCall.target.$getStore();

    // Check that we aren't accidentally making a call that has local target
    if (targetStore.isLocalStore()) {
      Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
          "Semantic warranty request for {0} aborted due to local target!",
          semanticWarrantyCall);
      return;
    }

    // Check that none of the arguments are local
    for (fabric.lang.Object arg : semanticWarrantyCall.arguments) {
      if (!(arg instanceof WrappedJavaInlineable)
          && arg != null
          && (arg.$getStore().isLocalStore() || !arg.$getStore().equals(
              targetStore))) {
        Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
            "Semantic warranty request for {0} aborted due to local argument!",
            semanticWarrantyCall);
        return;
      }
    }

    OidKeyHashMap<ReadMap.Entry> readsForTargetStore = new OidKeyHashMap<>();

    // Check writes
    for (Store store : storesWritten()) {
      Collection<_Impl> writesForStore = getWritesForStore(store);
      for (_Impl write : writesForStore) {
        if (!registeredInLookAside(store, write.$getOnum())) {
          Logging
              .log(
                  SEMANTIC_WARRANTY_LOGGER,
                  Level.FINER,
                  "Semantic warranty request for {0} aborted due to writes during the call!",
                  semanticWarrantyCall);
          return;
        }
      }
    }

    // Check reads
    for (LongKeyMap<ReadMap.Entry> submap : reads) {
      for (ReadMap.Entry entry : submap.values()) {
        if (entry.getStore().equals(targetStore)) {
          Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
              "Semantic warranty request for {0} reads {1}",
              semanticWarrantyCall, entry.getRef().onum);
          readsForTargetStore.put(entry.getStore(), entry.getRef().onum, entry);
        } else if (!entry.getStore().isLocalStore()) {
          Logging
              .log(
                  SEMANTIC_WARRANTY_LOGGER,
                  Level.FINER,
                  "Semantic warranty request for {0} aborted due to more than one remote store read during the call!",
                  semanticWarrantyCall);
          return;
        }
      }
    }
    for (ReadMap.Entry entry : readsReadByParent) {
      if (entry.getStore().equals(targetStore)) {
        Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
            "Semantic warranty request for {0} also reads {1}",
            semanticWarrantyCall, entry.getRef().onum);
        readsForTargetStore.put(entry.getStore(), entry.getRef().onum, entry);
      } else if (!entry.getStore().isLocalStore()) {
        Logging
            .log(
                SEMANTIC_WARRANTY_LOGGER,
                Level.FINER,
                "Semantic warranty request for {0} aborted due to more than one remote store read during the call!",
                semanticWarrantyCall);
        return;
      }
    }

    // Check creates
    OidKeyHashMap<_Impl> createsForTargetStore = new OidKeyHashMap<>();
    for (_Impl create : creates.values()) {
      if (!registeredInLookAside(create.$getStore(), create.$getOnum())) {
        if (create.$getStore().equals(targetStore)) {
          Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
              "Semantic warranty request for {0} creates {1}",
              semanticWarrantyCall, create.$getOnum());
          createsForTargetStore.put(create, create);
        } else if (!create.$getStore().isLocalStore()) {
          Logging
              .log(
                  SEMANTIC_WARRANTY_LOGGER,
                  Level.FINER,
                  "Semantic warranty request for {0} aborted due to more than one remote store create during the call!",
                  semanticWarrantyCall);
          return;
        }
      }
    }

    // Check calls
    Map<CallInstance, WarrantiedCallResult> callsForTargetStore =
      new HashMap<>();
    for (Entry<CallInstance, WarrantiedCallResult> entry : semanticWarrantiesUsed
        .entrySet()) {
      CallInstance call = entry.getKey();
      WarrantiedCallResult result = entry.getValue();

      if (call.target.$getStore().equals(targetStore)) {
        callsForTargetStore.put(call, result);
      } else if (!call.target.$getStore().isLocalStore()) {
        Logging
            .log(
                SEMANTIC_WARRANTY_LOGGER,
                Level.FINER,
                "Semantic warranty request for {0} aborted due to more than one remote store called during the call!",
                semanticWarrantyCall);
        return;
      }
    }

    // If we are here, then all reads, creates, call reuses, and results 
    // are on the target store. Now we can make a request object.
    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
        "Making request for {0}", semanticWarrantyCall);
    SemanticWarrantyRequest req =
        new SemanticWarrantyRequest(semanticWarrantyCall,
            semanticWarrantyValue, readsForTargetStore, createsForTargetStore,
            callsForTargetStore, getTid());
    requests.put(semanticWarrantyCall, req);
    requestLocations.put(semanticWarrantyCall, targetStore);

    // add reads to readDependency map and move them into the "in subcalls"
    // group
    for (Entry<Store, LongKeyMap<ReadMap.Entry>> entry : readsForTargetStore
        .nonNullEntrySet()) {
      Store s = entry.getKey();
      for (ReadMap.Entry read : entry.getValue().values()) {
        long onum = read.getRef().onum;
        addReadDependency(onum);
        if (reads.containsKey(s, onum)) {
          readsInSubcalls.put(s, onum, reads.get(s, onum));
          Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
              "REMOVING READ: {0} FOR CALL {1}", onum, semanticWarrantyCall);
          reads.remove(s, onum);
        } else if (readsReadByParent.contains(read)) {
          Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
              "READ BY PARENT BEING PUT IN SUBCALLS: {0} FOR CALL {1}", onum,
              semanticWarrantyCall);
          readsInSubcalls.put(s, onum, read);
        }
      }
    }

    // add creates to readDependency map and move them over to the "in subcalls"
    // group
    for (Entry<Store, LongKeyMap<_Impl>> entry : createsForTargetStore
        .nonNullEntrySet()) {
      for (_Impl create : entry.getValue().values()) {
        addReadDependency(create.$getOnum());
        createsInSubcalls.put(create.$getOnum(), create);
        Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
            "REMOVING CREATE: {0} FOR CALL {1}", create.$getOnum(),
            semanticWarrantyCall);
        creates.remove(create.$getOnum());
      }
    }

    // Add calls to dependency map and move them over to the "in subcalls" group
    for (CallInstance call : callsForTargetStore.keySet()) {
      Set<CallInstance> deps = callDependencies.get(call);
      if (deps == null) {
        deps = new HashSet<>();
        callDependencies.put(call, deps);
      }
      deps.add(semanticWarrantyCall);
      callsInSubcalls.put(call, semanticWarrantiesUsed.get(call));
      semanticWarrantiesUsed.remove(call);
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

    // Create this SemanticWarranty request (if there is one)
    createCurrentRequest();

    // Merge reads and transfer read locks.
    for (LongKeyMap<ReadMap.Entry> submap : reads) {
      for (ReadMap.Entry entry : submap.values()) {
        Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
            "TRANSFERRING READ LOCK: {0}", entry.getRef().onum);
        parent.transferReadLock(this, entry);
      }
    }

    for (ReadMap.Entry entry : readsReadByParent) {
      Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
          "RELEASING READ LOCK: {0}", entry.getRef().onum);
      entry.releaseLock(this);
    }

    // Pass up subcall reads
    for (Store s : readsInSubcalls.storeSet()) {
      for (LongKeyMap.Entry<ReadMap.Entry> entry : readsInSubcalls.get(s)
          .entrySet()) {
        ReadMap.Entry read = entry.getValue();
        parent.readsInSubcalls.put(s, entry.getKey(), entry.getValue());
        read.addLock(parent);
        read.releaseLock(this);
      }
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
    LongKeyMap<_Impl> parentCreates = parent.creates;
    synchronized (parentCreates) {
      for (_Impl obj : creates.values()) {
        parentCreates.put(obj.$getOnum(), obj);
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

    // Pass up subcall creates
    LongKeyMap<_Impl> parentSubcallCreates = parent.createsInSubcalls;
    synchronized (parentSubcallCreates) {
      for (_Impl obj : createsInSubcalls.values()) {
        parentSubcallCreates.put(obj.$getOnum(), obj);
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

    // Merge all child requests and dependencies
    parent.semanticWarrantiesUsed.putAll(semanticWarrantiesUsed);
    parent.callsInSubcalls.putAll(callsInSubcalls);
    // If we made a new request, add it to the calls used.
    if (semanticWarrantyCall != null
        && requests.containsKey(semanticWarrantyCall)) {
      WarrantiedCallResult curCallResult =
          new WarrantiedCallResult(requests.get(semanticWarrantyCall).value,
              new SemanticWarranty(0),
              getAllRequestCreates(semanticWarrantyCall));
      parent.semanticWarrantiesUsed.put(semanticWarrantyCall, curCallResult);
    }

    parent.requests.putAll(requests);
    parent.requestLocations.putAll(requestLocations);

    // Pass up dependency maps
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
    if (WORKER_TRANSACTION_LOGGER.isLoggable(Level.FINER)) {
      long commitDelay = commitTime - System.currentTimeMillis();
      Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINER,
          "Scheduled commit for tid {0} to run at {1} (in {2} ms)", tid,
          new Date(commitTime), commitDelay);
    }

    Threading.scheduleAt(commitTime, new Runnable() {
      @Override
      public void run() {
        Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINER,
            "Updating data structures for commit of tid {0}", tid);

        // Insert memoized calls into the local call cache
        for (Map.Entry<CallInstance, SemanticWarrantyRequest> e : requests
            .entrySet()) {
          CallInstance id = e.getKey();
          // If we got a warranty, then place it in the local cache.
          if (requestReplies.get(id) != null) {
            requestLocations.get(id).insertResult(
                id,
                new WarrantiedCallResult(e.getValue().value, requestReplies
                    .get(id), getAllRequestCreates(id)));
          }
        }

        // Release read locks.
        for (LongKeyMap<ReadMap.Entry> submap : SysUtil.chain(reads,
            readsInSubcalls)) {
          for (ReadMap.Entry entry : submap.values()) {
            entry.releaseLock(Log.this);
          }
        }

        // sanity check
        if (!readsReadByParent.isEmpty())
          throw new InternalError("something was read by a non-existent parent");

        // Release write locks and ownerships; update version numbers.
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
            obj.$readMapEntry.incrementVersion();
            obj.$isOwned = false;

            // Discard one layer of history.
            obj.$history = obj.$history.$history;

            // Signal any waiting readers/writers.
            if (obj.$numWaiting > 0) obj.notifyAll();
          }
        }

        // Release write locks on created objects and set version numbers.
        Iterable<_Impl> chain2 =
            SysUtil.chain(SysUtil.chain(creates.values(), localStoreCreates),
                createsInSubcalls.values());
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
            obj.$readMapEntry.incrementVersion();
            obj.$isOwned = false;

            // Signal any waiting readers/writers.
            if (obj.$numWaiting > 0) obj.notifyAll();
          }
        }

        // Merge the security cache into the top-level label cache.
        securityCache.mergeWithTopLevel();

        Logging.log(WORKER_TRANSACTION_LOGGER, Level.FINER,
            "Updated data structures for commit of tid {0}", tid);
      }
    });
  }

  /**
   * Transfers a read lock from a child transaction.
   */
  private void transferReadLock(Log child, ReadMap.Entry readMapEntry) {
    transferReadLock(child, readMapEntry, true);
  }

  /**
   * Transfers a read lock from a child transaction.
   */
  private void transferReadLock(Log child, ReadMap.Entry readMapEntry,
      boolean record) {
    // If we already have a read lock, return; otherwise, register a read lock.
    Boolean lockedByAncestor = readMapEntry.transferLockToParent(child);
    if (lockedByAncestor == null) {
      // We already had a lock; nothing to do.
      return;
    }

    if (record) {
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
   * Changes the waitsFor set to a singleton set containing the given log.
   */
  public void setWaitsFor(Log waitsFor) {
    synchronized (this.waitsFor) {
      this.waitsFor.clear();
      this.waitsFor.add(waitsFor);
    }
  }

  /**
   * Changes the waitsFor set to contain exactly the elements of the given set.
   */
  public void setWaitsFor(Set<Log> waitsFor) {
    synchronized (this.waitsFor) {
      this.waitsFor.clear();
      this.waitsFor.addAll(waitsFor);
    }
  }

  /**
   * Empties the waitsFor set.
   */
  public void clearWaitsFor() {
    synchronized (this.waitsFor) {
      this.waitsFor.clear();
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
}
