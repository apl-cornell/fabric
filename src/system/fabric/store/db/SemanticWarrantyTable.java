package fabric.store.db;

import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import fabric.common.SemanticWarranty;
import fabric.common.SerializedObject;
import fabric.common.Threading;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.lang.WrappedJavaInlineable;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.memoize.WarrantiedCallResult;

/**
 * A table containing semantic warranties, keyed by CallInstance id, and
 * supporting concurrent accesses.
 */
public class SemanticWarrantyTable {
  private static final int MIN_SEMANTIC_WARRANTY = 250;
  private static final int MAX_SEMANTIC_WARRANTY = 1000;

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
   */
  private static enum CallStatus {
    VALIDPENDING, // Request not committed and no pending blocked writes
    VALID, // Active and no pending blocked writes
    VALIDUPDATING, // Active and pending allowed writes
    STALE, // Warranty term past and no pending writes
    EXPIRINGPENDING, // Request not committed and pending blocked writes
    EXPIRING, // Active and pending blocked writes
    EXPIRINGUPDATING, // Active and both pending blocked and allowed writes
    EXPIRED // Warranty term past and writes have been made
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
   * Table mapping from transactionIDs for pending transactions to sets of calls
   * for which there will be a new semantic warranty.
   */
  private final ConcurrentLongKeyMap<Set<CallInstance>> pendingTIDMap;

  /**
   * Table mapping from transactionIDs for pending transactions to
   * updates for semantic warranty dependencies.
   */
  private final ConcurrentLongKeyMap<Map<CallInstance, SemanticWarrantyRequest>> refreshMap;

  /**
   * Local reference to the ObjectDB for items on this store.
   */
  private final ObjectDB database;

  public SemanticWarrantyTable(ObjectDB database) {
    lockTable = new ConcurrentHashMap<CallInstance, Object>();
    warrantyTable = new WarrantyTable<CallInstance, SemanticWarranty>(new
        SemanticWarranty(0));
    valueTable = new ConcurrentHashMap<CallInstance, fabric.lang.Object>();
    dependencyTable = new SemanticWarrantyDependencies();
    issuer = new WarrantyIssuer<CallInstance>(MIN_SEMANTIC_WARRANTY,
        MAX_SEMANTIC_WARRANTY);
    pendingTIDMap = new ConcurrentLongKeyHashMap<Set<CallInstance>>();
    refreshMap = new ConcurrentLongKeyHashMap<Map<CallInstance,
               SemanticWarrantyRequest>>();
    statusTable  = new ConcurrentHashMap<CallInstance, CallStatus>();
    this.database = database;
  }

  /**
   * Get the current state of the call's warranty.
   */
  public final CallStatus getCallStatus(CallInstance id) {
    lockTable.putIfAbsent(id, new Object());
    synchronized (lockTable.get(id)) {
      statusTable.putIfAbsent(id, CallStatus.EXPIRED);
      CallStatus cur = statusTable.get(id);
      if (cur == CallStatus.EXPIRED) {
        setCallStatus(id, CallStatus.EXPIRED);
        return CallStatus.EXPIRED;
      } else if (cur == CallStatus.VALID
          && warrantyTable.get(id).expired(true)) {
        setCallStatus(id, CallStatus.STALE);
        return CallStatus.STALE;
      } else if (cur == CallStatus.EXPIRING
          && warrantyTable.get(id).expired(true)) {
        setCallStatus(id, CallStatus.EXPIRED);
        return CallStatus.EXPIRED;
      } else {
        return cur;
      }
    }
  }

  /**
   * Utility method for checking if the call's warranty is expiring/expired.
   */
  public final boolean isExpiring(CallInstance call) {
    lockTable.putIfAbsent(call, new Object());
    synchronized (lockTable.get(call)) {
      CallStatus cur = statusTable.get(call);
      if (cur == null) cur = CallStatus.EXPIRED;
      return (cur == CallStatus.EXPIRINGPENDING || cur == CallStatus.EXPIRING
        || cur == CallStatus.EXPIRINGUPDATING);
    }
  }

  /**
   * Update the state of the call's warranty.  If it is being set to expired,
   * wipe the warranty from the table.
   */
  private final void setCallStatus(CallInstance id, CallStatus s) {
    lockTable.putIfAbsent(id, new Object());
    synchronized (lockTable.get(id)) {
      switch (s) {
        case EXPIRED:
          // Remove value, warranty is "implicitly" removed when it expires.
          valueTable.remove(id);

          // Do a dependen-ectomy where we remove the call from the dependency
          // graph and pass up it's old dependencies to those who depended on
          // this call.
          Set<CallInstance> subcalls = dependencyTable.getCalls(id);
          LongSet subreads = dependencyTable.getReads(id);
          Set<CallInstance> callers = dependencyTable.getCallers(id);
          dependencyTable.removeCall(id);
          if (subcalls != null && subreads != null && callers != null) {
            for (CallInstance caller : callers) {
              lockTable.putIfAbsent(caller, new Object());
              synchronized (lockTable.get(caller)) {
                for (CallInstance subcall : subcalls)
                  dependencyTable.addDependency(caller, subcall);
                for (LongIterator iter = subreads.iterator(); iter.hasNext();)
                  dependencyTable.addDependency(caller, iter.next());
              }
            }
          }

          // Remove the status entry
          statusTable.remove(id);
          break;
        default:
          // Otherwise, just set the status to the desired new state.
          statusTable.put(id, s);
          break;
      }
    }
  }

  /**
   * Get the warranty + call value for the given call.
   */
  // TODO: Copy construction for non-store items.
  public final WarrantiedCallResult get(CallInstance id) {
    lockTable.putIfAbsent(id, new Object());
    synchronized (lockTable.get(id)) {
      if (getCallStatus(id) == CallStatus.EXPIRED
          || getCallStatus(id) == CallStatus.VALIDPENDING
          || getCallStatus(id) == CallStatus.EXPIRINGPENDING)
        return null;
      return new WarrantiedCallResult(valueTable.get(id),
          warrantyTable.get(id));
    }
  }

  /**
   * The next write scheduled for any of the dependencies of this call.  This
   * only works if the call is in the table.
   *
   * Returns 0 if there is no scheduled write.
   */
  public long nextScheduledWrite(CallInstance call) {
    lockTable.putIfAbsent(call, new Object());
    synchronized (lockTable.get(call)) {
      long nextWrite = 0;
      for (CallInstance subcall : dependencyTable.getCalls(call)) {
        long callTime = nextScheduledWrite(subcall);
        if (callTime < nextWrite || nextWrite == 0) {
          nextWrite = callTime;
        }
      }
      for (LongIterator iter = dependencyTable.getReads(call).iterator();
          iter.hasNext();) {
        long onum = iter.next();
        if (database.isWritten(onum)) {
          // This works because the warranty will have been extended to when the
          // write is scheduled for.
          long objTime = database.getWarranty(onum).expiry();
          if (objTime < nextWrite || nextWrite == 0) {
            nextWrite = objTime;
          }
        }
      }
      return nextWrite;
    }
  }

  /**
   * Determine the time to set for the initial warranty on a call.
   */
  private long pickWarrantyTime(SemanticWarrantyRequest req) {
    long bestTime = issuer.suggestWarranty(req.call);
    for (LongIterator iter = req.readOnums.iterator(); iter.hasNext();) {
      long onum = iter.next();
      if (database.isWritten(onum)) {
        // This works because the warranty will have been extended to when the
        // write is scheduled for.
        long objTime = database.getWarranty(onum).expiry();
        bestTime = objTime < bestTime ? objTime : bestTime;
      }
    }
    for (CallInstance c : req.calls.keySet()) {
      long callTime = nextScheduledWrite(c);
      bestTime = callTime < bestTime ? callTime : bestTime;
    }
    return bestTime;
  }

  /**
   * Determine a term for a new warranty for the given request and schedule it
   * to become valid/expiring on the commit of the given transaction ID.
   */
  public SemanticWarranty requestWarranty(long transactionID,
      SemanticWarrantyRequest req) {
    lockTable.putIfAbsent(req.call, new Object());
    synchronized (lockTable.get(req.call)) {
      if (!warrantyTable.get(req.call).expired(true))
        throw new InternalError(
            "Adding a warranty while there is a pre-existing"
            + " warranty that is still valid!");

      // Add the warranty dependencies to the dependencyTable
      LongSet deps = new LongHashSet(req.readOnums);
      deps.addAll(req.createOnums);
      if (!(req.value instanceof WrappedJavaInlineable))
        deps.add(req.value.$getOnum());
      dependencyTable.addCall(req.call, deps, req.calls.keySet());
      
      // Determine a warranty term and make the warranty.
      SemanticWarranty newWarranty = new SemanticWarranty(pickWarrantyTime(req));

      // Add warranty to warranty table
      warrantyTable.put(req.call, newWarranty);

      // Add result to value table
      valueTable.put(req.call, req.value);
      
      // Set status based on whether we know there's already a pending write for
      // this call
      if (nextScheduledWrite(req.call) >= System.currentTimeMillis())
        statusTable.put(req.call, CallStatus.EXPIRINGPENDING);
      else
        statusTable.put(req.call, CallStatus.VALIDPENDING);

      // Schedule the promotion to an usable warranty (note, we could probably
      // do something to avoid rechecking if there's a pending write later).
      pendingTIDMap.putIfAbsent(transactionID, new HashSet<CallInstance>());
      pendingTIDMap.get(transactionID).add(req.call);
      return newWarranty;
    }
  }

  /**
   * Wrapper of the "more arguments" version of putAt.
   */
  public void putAt(long commitTime, final CallInstance call) {
    lockTable.putIfAbsent(call, new Object());
    synchronized (lockTable.get(call)) {
      Threading.scheduleAt(commitTime, new Runnable() {
        @Override
        public void run() {
          put(call);
        }
      });
    }
  }

  /**
   * Add a new call, with the result and all the reads, calls, and creates, into
   * the table with the provided warranty.
   */
  public final void put(CallInstance id) {
    lockTable.putIfAbsent(id, new Object());
    synchronized (lockTable.get(id)) {
      // Set the status
      CallStatus cur = getCallStatus(id);
      if (cur == CallStatus.VALIDPENDING)
        setCallStatus(id, CallStatus.VALID);
      else if (cur == CallStatus.EXPIRINGPENDING)
        setCallStatus(id, CallStatus.EXPIRING);
      else
        throw new InternalError("Invalid call status when activating!");
    }
  }

  /**
   * Compare the result found in the table currently with the value in the
   * WarrantiedCallResult res.  Return true if they agree.
   */
  private boolean compareResult(CallInstance call, WarrantiedCallResult res) {
    lockTable.putIfAbsent(call, new Object());
    synchronized (lockTable.get(call)) {
      fabric.lang.Object inTableVal = valueTable.get(call);
      return inTableVal != null 
            && ((res.value instanceof WrappedJavaInlineable &&
                  inTableVal instanceof WrappedJavaInlineable &&
                  res.value.equals(inTableVal))
                || (res.value instanceof fabric.lang.Object._Proxy &&
                    inTableVal instanceof fabric.lang.Object._Proxy &&
                    res.value.$getOnum() == inTableVal.$getOnum()));
    }
  }

  public static enum SemanticExtendStatus {
    OK, BAD_VERSION, DENIED
  }

  /**
   * Extends the warranty for an id only if it currently has a specific
   * warrantied call result.
   */
  public final Pair<SemanticExtendStatus, WarrantiedCallResult> extend(CallInstance
      id, WarrantiedCallResult oldValue, long newTime) {
    lockTable.putIfAbsent(id, new Object());
    synchronized (lockTable.get(id)) {
      SEMANTIC_WARRANTY_LOGGER.finest("Extending warranty for " + id);

      if (!compareResult(id, oldValue)) {
        return new Pair<SemanticExtendStatus, WarrantiedCallResult>(
            SemanticExtendStatus.BAD_VERSION,
            get(id));
      }

      switch (getCallStatus(id)) {
        // No reason we can't extend.
        case STALE:
          // Update state back to valid.
          // XXX this is the only case I'm not really sure about.
          setCallStatus(id, CallStatus.VALID);
          // $FALL-THROUGH$
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
          if (warrantyTable.get(id).expiresBefore(newTime, true))
            warrantyTable.put(id, new SemanticWarranty(newTime));
          return new Pair<SemanticExtendStatus, WarrantiedCallResult>(
              SemanticExtendStatus.OK,
              get(id));
        // Can't extend because there's a pending write after expiry or the
        // warranty already expired.
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
        case EXPIRED:
        default:
          return new Pair<SemanticExtendStatus, WarrantiedCallResult>(
              SemanticExtendStatus.DENIED,
              get(id));
      }
    }
  }

  /**
   * Gather up all the expired calls in the subgraph starting from the given
   * call.  This is used for notifying a worker of expired warranties.
   */
  // synchronized now because of deadlock with letExpire
  public synchronized Set<CallInstance> getExpiredSubgraph(CallInstance root) {
    Set<CallInstance> expiredSet = new HashSet<CallInstance>();
    lockTable.putIfAbsent(root, new Object());
    synchronized (lockTable.get(root)) {
      SEMANTIC_WARRANTY_LOGGER.finest("BUILDING UP EXPIRED SUBGRAPH FROM "
          + root + " which is expired? " +
          warrantyTable.get(root).expired(true));
      if (!warrantyTable.get(root).expired(true)) return expiredSet;
      expiredSet.add(root);
      for (CallInstance subcall : dependencyTable.getCalls(root)) {
        expiredSet.addAll(getExpiredSubgraph(subcall));
      }
      return expiredSet;
    }
  }

  /**
   * Let the call's associated value expire from the table at the end of the
   * current warranty.  Returns the set of calls that used this value (directly
   * or indirectly) and are under valid warranties.
   *
   * Valid Warranty Calls that used this call
   */
  // synchronized now because of deadlock with getExpiredSubgraph
  /*
  private synchronized final Set<CallInstance> letExpire(CallInstance call) {
    SEMANTIC_WARRANTY_LOGGER.finest("letting " + call + " expire");
    Set<CallInstance> validCallers = new HashSet<CallInstance>();
    synchronized (lockTable.putIfAbsent(call, new Object())) {
      // Mark the entry as going out of style like jean jackets :P
      setCallStatus(call, CallStatus.EXPIRING);

      // Expire callers and group up the ones that haven't expired yet.
      for (CallInstance caller : dependencyTable.getCallers(call)) {
        if (!warrantyTable.get(caller).expired(false)) {
          validCallers.add(caller);
        }

        // Mark the caller as expiring once the warranty runs out.
        validCallers.addAll(letExpire(caller));
      }

      // Remove this call if we know it's expired.
      if (warrantyTable.get(call).expired(false)) {
        valueTable.remove(call);
      }

      return validCallers;
    }
  }
  */

  /**
   * Notifies the issuer of a read prepare extending the Semantic Warranty for
   * the given call.
   */
  public void notifyReadPrepare(CallInstance call, long commitTime) {
    SEMANTIC_WARRANTY_LOGGER.finer("Notifying read prepare on " + call);
    issuer.notifyReadPrepare(call, commitTime);
  }

  /**
   * Set everything that uses this call to expire (in the future or
   * immediately) and return the largest warranty term of the calls we set to
   * expire.
   */
  private long scheduleWriteOn(CallInstance call) {
    lockTable.putIfAbsent(call, new Object());
    synchronized (lockTable.get(call)) {
      // Handle propogating this up the graph.
      long longest = warrantyTable.get(call).expiry();
      for (CallInstance caller : dependencyTable.getCallers(call)) {
        long callerTime = scheduleWriteOn(caller);
        longest = longest > callerTime ? longest : callerTime;
      }

      // Handle the state transition at the leaf.
      switch (getCallStatus(call)) {
        case VALIDPENDING:
          setCallStatus(call, CallStatus.EXPIRINGPENDING);
          break;
        case VALID:
          setCallStatus(call, CallStatus.EXPIRING);
          break;
        case VALIDUPDATING:
          setCallStatus(call, CallStatus.EXPIRINGUPDATING);
          break;
        case STALE:
          setCallStatus(call, CallStatus.EXPIRED);
          break;
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
        case EXPIRED:
          break;
      }
      issuer.notifyWritePrepare(call);

      return longest;
    }
  }

  /**
   * Provides the longest SemanticWarranty that depended on any of the given
   * onums that is longer than the given commitTime.  Also performs any
   * bookkeeping associated with write events (like removing stale call values).
   */
  // TODO: Oh god this needs a rewrite.
  // Also, this needs to be fixed to properly lock and handle the transition
  // between different dependencies.
  // XXX: For now, going back to blocking everything.
  public Pair<SemanticWarranty, Collection<SerializedObject>>
    prepareWrites(final Collection<SerializedObject> writes, final
        Collection<SerializedObject> creates, final long transactionID, long
        commitTime, final String storeName) {
    {
    /*
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

    // Build up set of calls that need to be valid by the current commit time
    for (SerializedObject obj : writes) {
      for (CallInstance call : new HashSet<CallInstance>(dependencyTable.getReaders(obj.getOnum()))) {
        // Let the call expire and add all valid callers to the dependency group
        // we'll recompute over.
        Set<CallInstance> validCallers = letExpire(call);
        for (CallInstance caller : validCallers) {
          if (get(caller) != null) {
            SemanticWarranty callerWarranty = get(caller).warranty;
            if (!callerWarranty.expired(false)) {
              SEMANTIC_WARRANTY_LOGGER.finest("Call " + caller + " needs to be checked!");
              dependencies.add(new Pair<CallInstance, SemanticWarranty>(caller,
                    callerWarranty));
              uncertainCalls.add(caller);
            }
          }
        }

        // If the call will also have to remain valid by commit time (possibly)
        // add it to the dependency group for recomputation.
        WarrantiedCallResult entry = get(call);
        if (entry == null) continue;
        SemanticWarranty dependentWarranty = entry.warranty;
        if (!dependentWarranty.expired(false)) {
          SEMANTIC_WARRANTY_LOGGER.finest("Call " + call + " needs to be checked!");
          dependencies.add(new Pair<CallInstance, SemanticWarranty>(call,
                dependentWarranty));
          uncertainCalls.add(call);
        }
      }
    }

    SEMANTIC_WARRANTY_LOGGER.finest(
        String.format("Transaction %x puts %d calls at risk!", transactionID,
          dependencies.size()));

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
      SEMANTIC_WARRANTY_LOGGER.finest(
          String.format("Checking %s for transaction %x", call.toString(),
            transactionID));
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
        SEMANTIC_WARRANTY_LOGGER.log(Level.FINEST, "Error: ", e);
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
    */
    }
    long longest = 0l;
    for (SerializedObject obj : writes) {
      for (CallInstance call : new HashSet<CallInstance>(dependencyTable.getReaders(obj.getOnum()))) {
        long writeDelay = scheduleWriteOn(call);
        longest = longest > writeDelay ? longest : writeDelay;
      }
    }
    return new Pair<SemanticWarranty, Collection<SerializedObject>>(
        new SemanticWarranty(longest), new HashSet<SerializedObject>());
  }

  /**
   * Helper function for cleaning up the request map and the various requests it
   * contains to not use requests on calls that changed.  Returns the set of
   * calls that changed value.
   */
  /*
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
      boolean areEqual = get(call).value == req.value;
      areEqual |= (get(call).value instanceof WrappedJavaInlineable &&
                    req.value instanceof WrappedJavaInlineable &&
                    get(call).value.$unwrap() == req.value.$unwrap());
      if (!areEqual) {
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
  */

  /**
   * Remove any state associated with the given transactionID due to a
   * transaction abort.
   */
  public void abort(long transactionID) {
    SEMANTIC_WARRANTY_LOGGER.finer(String.format(
        "Aborting semantic warranty updates from %x", transactionID));
    Set<CallInstance> callSet = pendingTIDMap.get(transactionID);
    if (callSet != null) {
      for (CallInstance call : callSet) {
        lockTable.putIfAbsent(call, new Object());
        synchronized (lockTable.get(call)) {
          setCallStatus(call, CallStatus.EXPIRED);
        }
      }
    }
    pendingTIDMap.remove(transactionID);
    refreshMap.remove(transactionID);
  }

  /**
   * Commit any state associated with the given transactionID at the given
   * commitTime.
   */
  public void commit(long transactionID, long commitTime) {
    SEMANTIC_WARRANTY_LOGGER.finer(String.format(
        "Committing semantic warranty updates from %x", transactionID));
    // Add requests made by the original transaction
    if (pendingTIDMap.get(transactionID) != null) {
      for (CallInstance call : pendingTIDMap.get(transactionID)) {
        putAt(commitTime, call);
      }
    }
    pendingTIDMap.remove(transactionID);

    // Update warranties that were refreshed by the transaction
    if (refreshMap.get(transactionID) != null) {
      Map<CallInstance, SemanticWarrantyRequest> m =
          refreshMap.get(transactionID);
      for (CallInstance call : m.keySet()) {
        SEMANTIC_WARRANTY_LOGGER.finest("Refreshing " + call + ": "
            + m.get(call));
        // TODO: Following commented line is incorrect, still fixing other stuff
        // before I worry about updates to active warranties.
        //putAt(commitTime, m.get(call), get(call).warranty);
        putAt(commitTime, call);
      }
    }
    refreshMap.remove(transactionID);
  }
}
