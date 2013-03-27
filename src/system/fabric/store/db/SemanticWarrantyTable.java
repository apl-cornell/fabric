package fabric.store.db;

import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import fabric.common.SemanticWarranty;
import fabric.common.SerializedObject;
import fabric.common.Threading;
import fabric.common.VersionWarranty;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.WrappedJavaInlineable;
import fabric.worker.AbortException;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.memoize.CallCheckException;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.memoize.WarrantiedCallResult;
import fabric.worker.transaction.ReadMapEntry;
import fabric.worker.transaction.TransactionManager;

/* TODO:
 *      - It would be more reassuring if we associated calls in the dependencies
 *      with the values we thought they had.
 */
/**
 * A table containing semantic warranties, keyed by CallInstance id, and
 * supporting concurrent accesses.
 */
public class SemanticWarrantyTable {
  private static final int MIN_SEMANTIC_WARRANTY = 2000;
  private static final int MAX_SEMANTIC_WARRANTY = 2000;

  /**
   * Table of objects to lock on for interacting with a certain call.
   */
  private final ConcurrentMap<CallInstance, Object> lockTable;

  /**
   * Table for looking up warranties of calls.
   */
  private final WarrantyTable<CallInstance, SemanticWarranty> warrantyTable;

  /**
   * Table for looking up call values.
   */
  private final ConcurrentMap<CallInstance, fabric.lang.Object> valueTable;

  /**
   * Enumeration of states a call can be in.
   *
   * VALID -> As far as the table is concerned, this call is still valid and
   * will be valid for the foreseeable future.
   * EXPIRING -> The call is valid as long as the warranty is valid and
   * otherwise is known to be in a bad state.
   */
  private static enum CallStatus {
    VALID, EXPIRING
  }

  /**
   * Table for determining if a call is marked for wipe once the warranty
   * expires.
   */
  private final ConcurrentMap<CallInstance, CallStatus> statusTable;
  
  /**
   * Table for looking up dependencies of calls on various reads and calls
   */
  private final SemanticWarrantyDependencies dependencyTable;

  /**
   * WarrantyIssuer for semantic warranties.
   */
  private final WarrantyIssuer<CallInstance> issuer;

  /**
   * Table mapping from transactionIDs for pending transactions to additons for
   * semantic warranty dependencies.
   */
  private final ConcurrentLongKeyMap<Map<CallInstance, Pair<SemanticWarrantyRequest, SemanticWarranty>>> pendingMap;

  /**
   * Table mapping from transactionIDs for pending transactions to
   * updates for semantic warranty dependencies.
   */
  private final ConcurrentLongKeyMap<Map<CallInstance, SemanticWarrantyRequest>> refreshMap;

  public SemanticWarrantyTable(ObjectDB database) {
    lockTable = new ConcurrentHashMap<CallInstance, Object>();
    warrantyTable = new WarrantyTable<CallInstance, SemanticWarranty>(new
        SemanticWarranty(0));
    valueTable = new ConcurrentHashMap<CallInstance, fabric.lang.Object>();
    dependencyTable = new SemanticWarrantyDependencies();
    issuer = new WarrantyIssuer<CallInstance>(MIN_SEMANTIC_WARRANTY,
        MAX_SEMANTIC_WARRANTY);
    pendingMap = new ConcurrentLongKeyHashMap<Map<CallInstance,
               Pair<SemanticWarrantyRequest, SemanticWarranty>>>();
    refreshMap = new ConcurrentLongKeyHashMap<Map<CallInstance,
               SemanticWarrantyRequest>>();
    statusTable  = new ConcurrentHashMap<CallInstance, CallStatus>();
  }

  public final WarrantiedCallResult get(CallInstance id) {
    lockTable.putIfAbsent(id, new Object());
    synchronized (lockTable.get(id)) {
      if (valueTable.get(id) == null
          || (statusTable.get(id) == CallStatus.EXPIRING
            && warrantyTable.get(id).expired(true)))
        return null;
      // Don't necessarily issue a new warranty until the transaction attempts
      // committing.
      return new WarrantiedCallResult(valueTable.get(id),
          warrantyTable.get(id));
    }
  }

  /* Create a warranty with a suggested time for the given call with the
   * associated reads and calls.
   */
  public SemanticWarranty proposeWarranty(CallInstance id) {
    return new SemanticWarranty(issuer.suggestWarranty(id));
  }

  /**
   * Adds a proposal to be inserted at commitTime.
   */
  public void addPendingWarranty(long commitTime, SemanticWarrantyRequest req,
      SemanticWarranty war, long transactionID) {
    CallInstance id = req.call;
    lockTable.putIfAbsent(id, new Object());
    synchronized (lockTable.get(id)) {
      if (warrantyTable.get(id).expiresAfter(commitTime, false))
        throw new InternalError(
            "Adding a pending warranty while there is a "
            + "pre-existing warranty that will still be valid at commit!");
      pendingMap.putIfAbsent(transactionID, new HashMap<CallInstance,
          Pair<SemanticWarrantyRequest, SemanticWarranty>>());
      pendingMap.get(transactionID).put(req.call, new
          Pair<SemanticWarrantyRequest, SemanticWarranty>(req, war));
    }
  }

  /**
   * Wrapper of the "more arguments" version of putAt.
   */
  public void putAt(long commitTime, SemanticWarrantyRequest req,
      SemanticWarranty warranty) {
    putAt(commitTime, req.call, req.readOnums, req.createOnums,
        new HashSet<CallInstance>(req.calls.keySet()), req.value, warranty);
  }

  /**
   * Schedule to perform a put with all the arguments at the given time.
   */
  public void putAt(long commitTime, final CallInstance id,
      final LongSet reads, final LongSet creates,
      final Set<CallInstance> calls, final fabric.lang.Object value,
      final SemanticWarranty warranty) {
    Threading.scheduleAt(commitTime, new Runnable() {
      @Override
      public void run() {
        put(id, reads, creates, calls, value, warranty);
      }
    });
  }

  /**
   * Add a new call, with the result and all the reads, calls, and creates, into
   * the table with the provided warranty.
   */
  public final void put(CallInstance id, LongSet reads, LongSet creates,
      Set<CallInstance> calls, fabric.lang.Object value,
      SemanticWarranty warranty) {

    lockTable.putIfAbsent(id, new Object());
    synchronized (lockTable.get(id)) {
      if (!warrantyTable.get(id).expired(true))
        throw new InternalError(
            "Adding a warranty while there is a pre-existing"
            + " warranty that is still valid!");
      // Add warranty to warranty table
      warrantyTable.put(id, warranty);

      // Add result to value table
      valueTable.put(id, value);

      // Add the warranty dependencies to the dependencyTable
      LongSet deps = new LongHashSet(reads);
      deps.addAll(creates);
      if (!(value instanceof WrappedJavaInlineable)) deps.add(value.$getOnum());
      dependencyTable.addCall(id, deps, calls);
    }
  }

  public static enum SemanticExtendStatus {
    OK, BAD_VERSION, DENIED
  }

  /**
   * Extends the warranty for an id only if it currently has a specific
   * warranty.
   * 
   * @return true iff the warranty was replaced.
   */
  public final Pair<SemanticExtendStatus, WarrantiedCallResult> extend(CallInstance
      id, WarrantiedCallResult oldValue, long newTime) {
    lockTable.putIfAbsent(id, new Object());
    synchronized (lockTable.get(id)) {
      if (oldValue.value.equals(valueTable.get(id))) {
        SemanticWarranty newWarranty = new SemanticWarranty(newTime);
        if (!oldValue.warranty.expired(true)) {
          if (statusTable.get(id) != CallStatus.EXPIRING
              && warrantyTable.extend(id, oldValue.warranty, newWarranty)) {
            return new Pair<SemanticExtendStatus,
                   WarrantiedCallResult>(SemanticExtendStatus.OK, new
                       WarrantiedCallResult(oldValue.value,
                         warrantyTable.get(id)));
          } else {
            return new Pair<SemanticExtendStatus,
                   WarrantiedCallResult>(SemanticExtendStatus.DENIED, null);
          }
        } else {
          warrantyTable.put(id, newWarranty);
          return new Pair<SemanticExtendStatus,
                 WarrantiedCallResult>(SemanticExtendStatus.OK, new
                     WarrantiedCallResult(oldValue.value,
                       warrantyTable.get(id)));
        }
      }
      if (valueTable.get(id) == null) {
        return new Pair<SemanticExtendStatus,
               WarrantiedCallResult>(SemanticExtendStatus.BAD_VERSION, null);
      } else {
        return new Pair<SemanticExtendStatus,
               WarrantiedCallResult>(SemanticExtendStatus.BAD_VERSION, new
                   WarrantiedCallResult(valueTable.get(id),
                     warrantyTable.get(id)));
      }
    }
  }

  /**
   * Let the call's associated value expire from the table at the end of the
   * current warranty.  Returns the set of calls that used this value (directly
   * or indirectly) and are under valid warranties.
   *
   * Valid Warranty Calls that used this call
   */
  public final Set<CallInstance> letExpire(CallInstance call) {
    SEMANTIC_WARRANTY_LOGGER.finest("ZAPPPPPPP " + call);
    Set<CallInstance> validCallers = new HashSet<CallInstance>();
    lockTable.putIfAbsent(call, new Object());
    synchronized (lockTable.get(call)) {
      // Mark the entry as going out of style like jean jackets :P
      statusTable.put(call, CallStatus.EXPIRING);

      // Update callers of the call to have the callers dependencies if they're
      // still valid, otherwise remove them too.
      Set<CallInstance> callsUsed = dependencyTable.getCalls(call);
      LongSet readsUsed = dependencyTable.getReads(call);
      Set<CallInstance> callers = new
        HashSet<CallInstance>(dependencyTable.getCallers(call));
      for (CallInstance caller : callers) {
          if (!warrantyTable.get(caller).expired(true)) {
            validCallers.add(caller);
          }
          // Update the dependency table so that the writes on the old call now
          // are marked for defense of the caller.
          dependencyTable.addDependenciesForCall(caller, readsUsed, callsUsed);

          // Mark the caller as expiring once the warranty runs out.
          validCallers.addAll(letExpire(caller));
      }

      return validCallers;
    }
  }

  /**
   * Extend the warranty to be defended until the given expiry at which point it
   * should be removed from the table.  This should _not_ be called if the
   * call's status in the table is VALID.
   */ 
  private final void extendExpiration(CallInstance call, long extendTime) {
    lockTable.putIfAbsent(call, new Object());
    synchronized (lockTable.get(call)) {
      SemanticWarranty newWarranty = new SemanticWarranty(extendTime);
      SemanticWarranty oldWarranty = warrantyTable.get(call);
      warrantyTable.extend(call, oldWarranty, newWarranty);
    }
  }

  /**
   * Notifies the issuer of a read prepare extending the Semantic Warranty for
   * the given call.
   */
  public void notifyReadPrepare(CallInstance call, long commitTime) {
    SEMANTIC_WARRANTY_LOGGER.finest("Notifying read prepare on " + call);
    issuer.notifyReadPrepare(call, commitTime);
  }

  /**
   * Provides the longest SemanticWarranty that depended on any of the given
   * onums that is longer than the given commitTime.  Also performs any
   * bookkeeping associated with write events (like removing stale call values).
   */
  public Pair<SemanticWarranty, Collection<SerializedObject>>
    prepareWrites(final Collection<SerializedObject> writes, final
        Collection<SerializedObject> creates, final long transactionID, long
        commitTime, final String storeName) {
    int initCapacity = writes.size() >= 1 ? writes.size() : 1;
    PriorityQueue<Pair<CallInstance, SemanticWarranty>> dependencies =
        new PriorityQueue<Pair<CallInstance, SemanticWarranty>>(initCapacity,
            new Comparator<Pair<CallInstance, SemanticWarranty>>() {
              @Override
              public int compare(Pair<CallInstance, SemanticWarranty> p1,
                  Pair<CallInstance, SemanticWarranty> p2) {
                if (p1.second.expiry() == p2.second.expiry()) return 0;
                return p1.second.expiry() > p2.second.expiry() ? -1 : 1;
              }
            });

    // Set of calls that we're not 100% sure the value will be the same with the
    // given write set.
    final Set<CallInstance> uncertainCalls = new HashSet<CallInstance>();

    SEMANTIC_WARRANTY_LOGGER.finest(
        String.format("Going through call dependencies of %x", transactionID));
    // Build up set of calls that need to be valid by the current commit time
    for (SerializedObject obj : writes) {
      for (CallInstance call : new HashSet<CallInstance>(dependencyTable.getReaders(obj.getOnum()))) {
        // Let the call expire and add all valid callers to the dependency group
        // we'll recompute over.
        Set<CallInstance> validCallers = letExpire(call);
        for (CallInstance caller : validCallers) {
          SemanticWarranty callerWarranty = get(caller).warranty;
          if (callerWarranty.expiresAfter(commitTime, true))
            SEMANTIC_WARRANTY_LOGGER.finest("Call " + caller + " needs to be checked!");
            dependencies.add(new Pair<CallInstance, SemanticWarranty>(caller,
                  callerWarranty));
            uncertainCalls.add(caller);
        }

        // If the call will also have to remain valid by commit time (possibly)
        // add it to the dependency group for recomputation.
        SemanticWarranty dependentWarranty = get(call).warranty;
        if (dependentWarranty.expiresAfter(commitTime, true)) {
          SEMANTIC_WARRANTY_LOGGER.finest("Call " + call + " needs to be checked!");
          dependencies.add(new Pair<CallInstance, SemanticWarranty>(call,
                dependentWarranty));
          uncertainCalls.add(call);
        }
      }
    }

    // Map of things that didn't change
    Map<CallInstance, SemanticWarrantyRequest> updatedRequests = new
      HashMap<CallInstance, SemanticWarrantyRequest>();

    // Set of things that DID change
    Set<CallInstance> changedCalls = new HashSet<CallInstance>();

    // Keep track of the furthest SemanticWarranty that we'd have to defend
    // (will be changed by the end of the first iteration of the loop).
    SemanticWarranty longestAffected = new SemanticWarranty(commitTime);

    // Keep track of creates by refreshed warranties.
    LinkedList<_Impl> addedCreates = new LinkedList<_Impl>();

    // Deserialized list of creates
    final Store localStore = Worker.getWorker().getStore(storeName);
    final ArrayList<_Impl> deserializedCreates = new
      ArrayList<_Impl>(creates.size());
    for (SerializedObject o : creates) {
      SEMANTIC_WARRANTY_LOGGER.finest("" + transactionID + " DESERIALIZING " +
          o.getOnum());
      deserializedCreates.add(o.deserialize(localStore, new
            VersionWarranty(0)));
    }

    // Keep walking through (from longest warranty down) until we find something
    // that changes or we run out of time to check.
    while (!dependencies.isEmpty()) {
      Pair<CallInstance, SemanticWarranty> p = dependencies.poll();
      // If we already checked this and found it changed, we're done.
      if (changedCalls.contains(p.first)) {
        longestAffected = p.second;
        break;
      }

      // If we already checked this and didn't find it changed, skip!
      if (updatedRequests.containsKey(p.first)) {
        continue;
      }

      // Check to make sure we have time to check it.
      long currentTime = System.currentTimeMillis();
      if (p.second.expiresBefore(currentTime, false)) {
        // Add it back to the group of things that haven't been checked.
        dependencies.add(p);
        break;
      }

      final CallInstance call = p.first;
      Future<Pair<Map<CallInstance, SemanticWarrantyRequest>, Set<CallInstance>>> checkHandler =
          Executors.newSingleThreadExecutor().submit(
              (new Callable<Pair<Map<CallInstance, SemanticWarrantyRequest>, Set<CallInstance>>>() {
                @Override
                public Pair<Map<CallInstance, SemanticWarrantyRequest>, Set<CallInstance>> call() {
                  try {
                    Worker.runInTopLevelTransaction(new Code<Void>() {
                      @Override
                      public Void run() {
                        SEMANTIC_WARRANTY_LOGGER
                            .finest("" + transactionID + " CHECKING CALL " + call);
                        TransactionManager tm =
                            TransactionManager.getInstance();
                        // Ensure that we don't reuse calls we're uncertain of.
                        tm.getCurrentLog().blockedWarranties.addAll(
                          uncertainCalls);
                        tm.getCurrentLog().useStaleWarranties = false;
                        tm.getCurrentLog().addCreates(deserializedCreates);

                        // Load up state from writes
                        for (SerializedObject obj : writes) {
                          (new _Proxy(localStore, obj.getOnum())).fetch()
                              .$copyAppStateFrom(
                                  obj.deserialize(localStore,
                                      new VersionWarranty(0)));
                        }
                        
                        // Rerun the call.
                        call.runCall();
                        SEMANTIC_WARRANTY_LOGGER.finest("DONE RECOMPUTING CALL " + call);
                        Map<CallInstance, SemanticWarrantyRequest> updatedRequests =
                          new HashMap<CallInstance, SemanticWarrantyRequest>();
                        for (CallInstance checkcall : uncertainCalls) {
                          SemanticWarrantyRequest req =
                            tm.getCurrentLog().getRequest(checkcall);
                          if (req != null) {
                            updatedRequests.put(checkcall, req);
                          }
                        }
                        Set<CallInstance> changed = SemanticWarrantyTable.this.patchUpUpdates(updatedRequests);
                        throw new CallCheckException(updatedRequests, changed);
                      }
                    }, false);
                  } catch (AbortException e) {
                    if (e.getCause() instanceof CallCheckException) {
                      CallCheckException exp =
                        (CallCheckException) e.getCause();
                      return exp.updates;
                    }
                    throw e;
                  }
                  return null;
                }
              }));
      try {
        Pair<Map<CallInstance, SemanticWarrantyRequest>, Set<CallInstance>> check =
          checkHandler.get(p.second.expiry() - currentTime,
                TimeUnit.MILLISECONDS);
        Map<CallInstance, SemanticWarrantyRequest> checkedReqs = check.first;
        Set<CallInstance> changed = check.second;

        // Note all the calls to be refreshed.
        for (Map.Entry<CallInstance, SemanticWarrantyRequest> entry :
            checkedReqs.entrySet()) {
          CallInstance checkedCall = entry.getKey();
          SemanticWarrantyRequest checkedRequest = entry.getValue();
          // If a call didn't change, add the new creates to the list to be
          // returned and add it to the map of things that need to be refreshed.
          SEMANTIC_WARRANTY_LOGGER.finest("Checked Call " + checkedCall
              + " unaffected by " + transactionID);
          for (Map.Entry<Store, LongKeyMap<_Impl>> reqEntry :
              checkedRequest.creates.nonNullEntrySet())
            for (_Impl create : reqEntry.getValue().values())
              addedCreates.add(create);
          updatedRequests.put(checkedCall, checkedRequest);
        }

        // Note all the calls that changed
        for (CallInstance changedCall : changed) {
          // Otherwise, notify about the write and mark it for extension.
          SEMANTIC_WARRANTY_LOGGER.finest("Checked Call " + changedCall
              + " affected by " + transactionID);
          issuer.notifyWritePrepare(changedCall);
          changedCalls.add(changedCall);
        }

        // Done if the top call changed
        if (changed.contains(call)) {
          SEMANTIC_WARRANTY_LOGGER.finest("Checked " + call + " affected by " +
              transactionID);
          longestAffected = p.second;
          break;
        }
        SEMANTIC_WARRANTY_LOGGER.finest("Checked " + call + " unaffected by " +
            transactionID);
      } catch (ExecutionException e) {
        SEMANTIC_WARRANTY_LOGGER.finest("Checked Call " + call
            + " had an error in check");
        SEMANTIC_WARRANTY_LOGGER.finest("\t" + e.getMessage());
        longestAffected = p.second;
        issuer.notifyWritePrepare(call);
        break;
      } catch (InterruptedException e) {
        SEMANTIC_WARRANTY_LOGGER.finest("Checked Call " + call
            + " had interrupted in check");
        longestAffected = p.second;
        issuer.notifyWritePrepare(call);
        break;
      } catch (TimeoutException e) {
        SEMANTIC_WARRANTY_LOGGER.finest("Checked Call " + call
            + " timed out in check");
        longestAffected = p.second;
        issuer.notifyWritePrepare(call);
        break;
      }
    }

    // For things we didn't check, assume that we had to write the value and
    // extend our expiring warranty until the write so that a new warranty can't
    // be added between write prepare and commit.
    //
    // Note that we had a write that, as far as we could tell, affected the
    // value.
    for (Pair<CallInstance, SemanticWarranty> remaining : dependencies) {
      issuer.notifyWritePrepare(remaining.first);
      extendExpiration(remaining.first, longestAffected.expiry());
    }

    // For things we did check, extend the expiration.
    for (CallInstance changed : changedCalls) {
      extendExpiration(changed, longestAffected.expiry());
    }

    // For those that recomputed to the same value, revert the status to VALID
    // and schedule an update when the commit goes through.
    for (Map.Entry<CallInstance, SemanticWarrantyRequest> entry :
        updatedRequests.entrySet()) {
      lockTable.putIfAbsent(entry.getKey(), new Object());
      synchronized (lockTable.get(entry.getKey())) {
        statusTable.put(entry.getKey(), CallStatus.VALID);
        Map<CallInstance, SemanticWarrantyRequest> upMap =
          new HashMap<CallInstance, SemanticWarrantyRequest>();
        upMap.put(entry.getKey(), entry.getValue());
        refreshMap.putIfAbsent(transactionID,
            new HashMap<CallInstance, SemanticWarrantyRequest>());
        refreshMap.get(transactionID).putAll(upMap);
      }
    }
    ArrayList<SerializedObject> serializedCreates = new
      ArrayList<SerializedObject>(addedCreates.size());
    for (_Impl create : addedCreates)
      serializedCreates.add(new SerializedObject(create));
    return new Pair<SemanticWarranty,
           Collection<SerializedObject>>(longestAffected, serializedCreates);
  }

  /**
   * Helper function for cleaning up the request map and the various requests it
   * contains to not use requests on calls that changed.  Returns the set of
   * calls that changed value.
   */
  private Set<CallInstance> patchUpUpdates(Map<CallInstance,
      SemanticWarrantyRequest> updates) {
    Set<CallInstance> callsCopy = new HashSet<CallInstance>(updates.keySet());
    Set<CallInstance> callsChanged = new HashSet<CallInstance>();
    for (CallInstance call : callsCopy) {
      if (!updates.containsKey(call)) continue;
      SemanticWarrantyRequest req = updates.get(call);
      // If we didn't get a proper refresh, go through and propogate all it's
      // request info into the other requests that used it and move it from the
      // updates set to the set of requests that changed value.
      if (!get(call).value.equals(req.value)) {
        for (Map.Entry<CallInstance, SemanticWarrantyRequest> update :
            updates.entrySet()) {
          CallInstance parentCall = update.getKey();
          SemanticWarrantyRequest parentReq = update.getValue();
          if (parentCall.equals(call)) continue;
          if (parentReq.calls.containsKey(call)) {
            // Remove the call
            parentReq.calls.remove(call);
            // Add sub calls
            parentReq.calls.putAll(req.calls);
            // Add reads
            for (Map.Entry<Store, LongKeyMap<ReadMapEntry>> entry :
                req.reads.nonNullEntrySet())
              for (ReadMapEntry read : entry.getValue().values())
                parentReq.reads.put(read.obj.store, read.obj.onum, read);
            // Add creates
            for (Map.Entry<Store, LongKeyMap<_Impl>> entry :
                req.creates.nonNullEntrySet())
              for (_Impl create : entry.getValue().values())
                parentReq.creates.put(create.$getStore(), create.$getOnum(),
                    create);
          }
        }
        updates.remove(call);
        callsChanged.add(call);
      }
    }
    return callsChanged;
  }

  /**
   * Remove any state associated with the given transactionID due to a
   * transaction abort.
   */
  public void abort(long transactionID) {
    SEMANTIC_WARRANTY_LOGGER.finest(String.format(
        "Aborting semantic warranty updates from %x", transactionID));
    pendingMap.remove(transactionID);
    refreshMap.remove(transactionID);
  }

  /**
   * Commit any state associated with the given transactionID at the given
   * commitTime.
   */
  public void commit(long transactionID, long commitTime) {
    SEMANTIC_WARRANTY_LOGGER.finest(String.format(
        "Committing semantic warranty updates from %x", transactionID));
    // Add requests made by the original transaction
    if (pendingMap.get(transactionID) != null) {
      Map<CallInstance, Pair<SemanticWarrantyRequest, SemanticWarranty>> m =
          pendingMap.get(transactionID);
      for (CallInstance call : m.keySet()) {
        SEMANTIC_WARRANTY_LOGGER.finest("Adding " + call + ": "
            + m.get(call).first + ", " + m.get(call).second);
        putAt(commitTime, m.get(call).first, m.get(call).second);
      }
    }
    pendingMap.remove(transactionID);

    // Update warranties that were refreshed by the transaction
    if (refreshMap.get(transactionID) != null) {
      Map<CallInstance, SemanticWarrantyRequest> m =
          refreshMap.get(transactionID);
      for (CallInstance call : m.keySet()) {
        SEMANTIC_WARRANTY_LOGGER.finest("Refreshing " + call + ": "
            + m.get(call));
        putAt(commitTime, m.get(call), get(call).warranty);
      }
    }
    refreshMap.remove(transactionID);
  }
}
