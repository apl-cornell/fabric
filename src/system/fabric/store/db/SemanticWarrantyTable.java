package fabric.store.db;

import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import fabric.common.SemanticWarranty;
import fabric.common.SerializedObject;
import fabric.common.Threading;
import fabric.common.VersionWarranty;
import fabric.common.Warranty;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.lang.Object._Proxy;
import fabric.lang.WrappedJavaInlineable;
import fabric.worker.AbortException;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.memoize.WarrantiedCallResult;
import fabric.worker.transaction.TransactionManager;
import fabric.worker.TransactionPrepareFailedException;

/**
 * A table containing semantic warranties, keyed by CallInstance id, and
 * supporting concurrent accesses.
 */
public class SemanticWarrantyTable {
  /**
   * Enumeration of states a call can be in.
   */
  private static enum CallStatus {
    VALID, // Active and no pending blocked writes
    STALE, // Warranty term past and no pending writes
    NOVALUE // This call does not currently have a value at the store.
  }

  /**
   * Status options for the result of an extend operation
   */
  public static enum SemanticExtendStatus {
    OK, BAD_VERSION, DENIED
  }

  /**
   * Used for checking if a call is updated by a set of writes.
   */
  private class CallCheckException extends RuntimeException {
    /* Calls that were ran during the run with their new requests. */
    public Map<CallInstance, SemanticWarrantyRequest> updates;

    public CallCheckException(Map<CallInstance, SemanticWarrantyRequest> updates) {
      this.updates = updates;
    }
  }

  /**
   * Information associated with a specific call's warranty state.
   */
  private class CallInfo {
    /**
     * Lock to be used whenever updating this object.  Use this instead of the
     * built-in synchronized support because it's easier to coordinate some
     * operations.
     */
    private final ReentrantLock lock = new ReentrantLock(true);

    public void lock() {
      lock.lock();
    }

    public void unlock() {
      lock.unlock();
    }

    private boolean writeLocked = false;

    /**
     * Acquire a write lock on this call for updating/creating purposes.
     *
     * Throws an UnableToLockException if the lock has already been acquired.
     */
    public void writeLock() throws UnableToLockException {
      lock();
      try {
        if (writeLocked)
          throw new UnableToLockException();
        writeLocked = true;
      } finally {
        unlock();
      }
    }

    /**
     * Unlock the call for writes.
     */
    public void writeUnlock() {
      lock();
      try {
        writeLocked = false;
      } finally {
        unlock();
      }
    }

    /**
     * The call this info is for.
     */
    public final CallInstance call;

    /**
     * The call's status.
     */
    private CallStatus status = CallStatus.NOVALUE;

    /**
     * Get the call's status.
     */
    public CallStatus getStatus() {
      lock();
      try {
        switch (status) {
        case VALID:
          if (warranty.expired(true)) setStatus(CallStatus.STALE);
          return CallStatus.STALE;
        case NOVALUE:
        case STALE:
        default:
          return status;
        }
      } finally {
        unlock();
      }
    }

    /**
     * Set the call's status.
     */
    public void setStatus(CallStatus newStatus) {
      lock();
      try {
        switch (newStatus) {
        case NOVALUE:
          throw new InternalError("NOVALUE should not be transitioned to!");
        case VALID:
        case STALE:
        default:
          // Otherwise, just set the status to the desired new state.
          status = newStatus;
          break;
        }
      } finally {
        unlock();
      }
    }

    /**
     * The call's value.
     */
    private fabric.lang.Object value;

    /**
     * Get the call's value.  Returns null if the call is expired.
     */
    public fabric.lang.Object getValue() {
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          throw new InternalError("Attempt to read value of a call with no value!");
        case VALID:
        case STALE:
        default:
          return value;
        }
      } finally {
        unlock();
      }
    }

    /**
     * Compare the current result with another value.  Return true if they
     * agree, otherwise false.
     */
    public boolean compareValue(fabric.lang.Object otherVal) {
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          return false; // No thing can equal nothing.
        case VALID:
        case STALE:
        default:
          if (value == null) {
            // Check if they're both null
            return otherVal == null;
          } else if (otherVal instanceof WrappedJavaInlineable) {
            // Check if they're both the same inlineable
            if (value instanceof WrappedJavaInlineable) {
              return otherVal.equals(value);
            }
            return false;
          } else if (otherVal instanceof fabric.lang.Object._Proxy) {
            // Check if they're the same object
            if (value instanceof fabric.lang.Object._Proxy) {
              return (otherVal.$getOnum() == value.$getOnum());
            }
            return false;
          }
          return false;
        }
      } finally {
        unlock();
      }
    }

    /**
     * This call's warranty.
     */
    private SemanticWarranty warranty;

    /**
     * Get the call's warranty.
     */
    public SemanticWarranty getWarranty() {
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          throw new InternalError(
              "Attempt to get warranty of call with no established value!");
        case VALID:
        case STALE:
        default:
          return warranty;
        }
      } finally {
        unlock();
      }
    }

    /**
     * The connections in the dependency graph.
     */
    private Set<CallInstance> callers;
    private Set<CallInstance> calls;
    private LongSet reads;
    private LongSet creates;

    /**
     * Get the calls that use this call.
     */
    public Set<CallInstance> getCallers() {
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          throw new InternalError(
              "Attempt to get callers of a valueless call!");
        case VALID:
        case STALE:
        default:
          return new HashSet<CallInstance>(callers);
        }
      } finally {
        unlock();
      }
    }

    /**
     * Get the calls this call used.
     */
    @SuppressWarnings("unused")
    public Set<CallInstance> getCalls() {
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          throw new InternalError("Attempt to get calls of a valueless call!");
        case VALID:
        case STALE:
        default:
          return new HashSet<CallInstance>(calls);
        }
      } finally {
        unlock();
      }
    }

    /**
     * Add a new caller.
     */
    private void addCaller(CallInstance caller) {
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          throw new InternalError(
              "Attempt to add a caller to a valueless call!");
        case VALID:
        case STALE:
        default:
          callers.add(caller);
        }
      } finally {
        unlock();
      }
    }

    /**
     * Remove a caller.
     */
    private void removeCaller(CallInstance caller) {
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          throw new InternalError(
              "Attempt to remove a caller from a valueless call!");
        case VALID:
        case STALE:
        default:
          callers.remove(caller);
        }
      } finally {
        unlock();
      }
    }

    /**
     * Get the reads made during this call.
     */
    @SuppressWarnings("unused")
    public LongSet getReads() {
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          throw new InternalError(
              "Attempt to get reads from a valueless call!");
        case VALID:
        case STALE:
        default:
          return new LongHashSet(reads);
        }
      } finally {
        unlock();
      }
    }

    /**
     * Get the oids created during this call.
     */
    public LongSet getCreates() {
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          throw new InternalError(
              "Attempt to get creates from a valueless call!");
        case VALID:
        case STALE:
        default:
          return new LongHashSet(creates);
        }
      } finally {
        unlock();
      }
    }

    /**
     * Add a new read.
     *
     * Should only be called when something EXPIREs or a new call is added.
     */
    private void addRead(long onum) {
      lock();
      try {
        switch (getStatus()) {
        case VALID:
        case STALE:
          throw new InternalError("Attempt to add a read to a stored call!");
        case NOVALUE:
        default:
          reads.add(onum);
          if (!readersTable.containsKey(onum)) {
            readersTable.put(onum, new HashSet<CallInstance>());
          }
          readersTable.get(onum).add(this.call);
        }
      } finally {
        unlock();
      }
    }

    /**
     * Add a new create.
     *
     * Should only be called when something EXPIREs or a new call is added.
     */
    // TODO: will this make sense anymore?
    private void addCreate(long onum) {
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          throw new InternalError(
              "Attempt to add a create to a valueless call!");
        case VALID:
        case STALE:
        default:
          creates.add(onum);
          if (!creatorTable.containsKey(onum)) {
            creatorTable.put(onum, new HashSet<CallInstance>());
          }
          creatorTable.get(onum).add(this.call);
        }
      } finally {
        unlock();
      }
    }

    public CallInfo(CallInstance call) {
      this.lock();
      try {
        this.call = call;
      } finally {
        this.unlock();
      }
    }

    public void request(SemanticWarrantyRequest req, long transactionID) throws
      TransactionPrepareFailedException {
      this.lock();
      try {
        switch (getStatus()) {
        case VALID:
        case STALE:
          throw new InternalError(
              "Attempt to request a new warranty for an existing call!");
        case NOVALUE:
        default:
          writeLock();
          //See what the dependencies say about the warranty time.
          long warTime = Long.MAX_VALUE;
          for (LongIterator iter = req.readOnums.iterator(); iter.hasNext();) {
            long onum = iter.next();
            if (database.isWritten(onum)) {
              throw new TransactionPrepareFailedException("Request for call " +
                  call + " depends on object " + onum +
                  " that has a write scheduled.");
            } else {
              Warranty objWar = database.getWarranty(onum);
              if (objWar.expired(false)) continue;
              warTime = objWar.expiry() < warTime ? objWar.expiry() : warTime;
            }
          }
          for (CallInstance c : req.calls.keySet()) {
            try {
              getInfo(c).writeLock();
              getInfo(c).writeUnlock();
            } catch (UnableToLockException e) {
              throw new TransactionPrepareFailedException( "Request for call " +
                  call + " depends on call " + call +
                  " that has a write scheduled.");
            }
            long callTime = getInfo(c).getWarranty().expiry();
            warTime = callTime < warTime ? callTime : warTime;
          }

          // Set warranty
          long suggestedTime = issuer.suggestWarranty(req.call);
          if (suggestedTime < warTime) {
            this.warranty = new SemanticWarranty(suggestedTime);
          } else {
            this.warranty = new SemanticWarranty(warTime);
          }

          // Set value
          this.value = req.value;

          // Set links from this call
          this.calls = new HashSet<CallInstance>(req.calls.keySet());
          this.callers = new HashSet<CallInstance>();
          this.creates = new LongHashSet();
          for (LongIterator iter = req.createOnums.iterator(); iter.hasNext();) {
            addCreate(iter.next());
          }
          this.reads = new LongHashSet();
          for (LongIterator iter = req.readOnums.iterator(); iter.hasNext();) {
            addRead(iter.next());
          }

          // Update links to this call
          for (CallInstance call : this.calls) {
            getInfo(call).addCaller(this.call);
          }

          // Set status later...

          pendingTIDMap.putIfAbsent(transactionID, new HashSet<CallInstance>());
          pendingTIDMap.get(transactionID).add(this.call);
        }
      } catch (UnableToLockException e) {
        throw new TransactionPrepareFailedException("Could not lock call " +
            call + " for write");
      } finally {
        this.unlock();
      }
    }

    /**
     * Activate this call, assuming it's pending.  Otherwise, throw an error.
     */
    public void activate() {
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          writeUnlock();
          if (warranty.expired(true))
            setStatus(CallStatus.STALE);
          else
            setStatus(CallStatus.VALID);
          break;
        case STALE:
        case VALID:
        default:
          throw new InternalError("Attempting to activate a non-pending call!");
        }
      } finally {
        unlock();
      }
    }

    /**
     * Abort the pending call.
     */
    public void abort() {
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          writeUnlock();
          break;
        case STALE:
        case VALID:
        default:
          throw new InternalError("Attempting to abort a non-pending call!");
        }
      } finally {
        unlock();
      }
    }

    public SemanticExtendStatus extendWarranty(fabric.lang.Object oldValue,
        long newTime) {
      lock();
      try {
        switch (getStatus()) {
        case STALE:
          // Check what they think it is.
          if (!compareValue(oldValue)) return SemanticExtendStatus.BAD_VERSION;
          // Update the warranty
          if (warranty.expiresBefore(newTime, true)) {
            warranty = new SemanticWarranty(newTime);
            // Update state back to valid.
            setStatus(CallStatus.VALID);
          }
          return SemanticExtendStatus.OK;
        case VALID:
          // Check what they think it is.
          if (!compareValue(oldValue))
            return SemanticExtendStatus.BAD_VERSION;
          // Update the warranty
          if (warranty.expiresBefore(newTime, true)) {
            // Check that we won't be extending past the next write.
            if (nextUpdate == null) {
              warranty = new SemanticWarranty(newTime);
            } else {
              return SemanticExtendStatus.DENIED;
            }
          }
          return SemanticExtendStatus.OK;
        case NOVALUE:
        default:
          return SemanticExtendStatus.DENIED;
        }
      } finally {
        unlock();
      }
    }

    /**
     * Find all calls that were used (directly or indirectly) by this call
     * (including itself) which are now expired.
     */
    // XXX: not sure this makes any sense anymore...
    public Set<CallInstance> getExpiredCalls() {
      Set<CallInstance> expired = new HashSet<CallInstance>();
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          expired.add(this.call);
          Set<CallInstance> children = new TreeSet<CallInstance>(this.calls);
          unlock();
          try {
            for (CallInstance child : children) {
              expired.addAll(getExpiredSubgraph(child));
            }
          } finally {
            // Re lock to match outer finally
            lock();
          }
          // $FALL-THROUGH$
        case VALID:
        case STALE:
        default:
          return expired;
        }
      } finally {
        unlock();
      }
    }

    /**
     * Callable to use for checking the call (allowing us to set a time limit on
     * how long we check by submitting this for a time limited thread).
     */
    private Callable<Map<CallInstance, SemanticWarrantyRequest>> getCallChecker(
        final Set<CallInstance> uncertainCalls,
        final Map<CallInstance, SemanticWarrantyRequest> updates,
        final Map<CallInstance, SemanticWarrantyRequest> changes,
        final Collection<SerializedObject> creates,
        final Collection<SerializedObject> writes) {
      return new Callable<Map<CallInstance, SemanticWarrantyRequest>>() {
        @Override
        public Map<CallInstance, SemanticWarrantyRequest> call() {
          try {
            Worker.runInTopLevelTransaction(new Code<Void>() {
              @Override
              public Void run() {
                checkCall(
                    Worker.getWorker().getStore(
                        Worker.getWorker().getLocalStore().name()),
                    uncertainCalls, updates, changes, creates, writes);
                return null;
              }
            }, false);
          } catch (AbortException e) {
            if (e.getCause() instanceof CallCheckException) {
              CallCheckException c = (CallCheckException) e.getCause();
              return c.updates;
            }
            throw e;
          }
          return null;
        }
      };
    }

    /**
     * Actual method for checking the call in a transaction (aborting and giving
     * back the new requests that would have resulted).
     */
    private void checkCall(Store localStore, Set<CallInstance> uncertainCalls,
        Map<CallInstance, SemanticWarrantyRequest> updates,
        Map<CallInstance, SemanticWarrantyRequest> changes,
        Collection<SerializedObject> creates,
        Collection<SerializedObject> writes) {
      TransactionManager tm = TransactionManager.getInstance();
      // Ensure that we don't reuse calls we're uncertain of or we know for a
      // fact will not be correct now.
      tm.getCurrentLog().blockedWarranties.addAll(uncertainCalls);

      // Insert already checked calls to allow for faster processing.
      tm.getCurrentLog().addRequests(updates);
      tm.getCurrentLog().addRequests(changes);

      // XXX: Do we actually need this?
      tm.getCurrentLog().useStaleWarranties = false;

      // Load up state from writes
      for (SerializedObject obj : writes) {
        (new _Proxy(localStore, obj.getOnum())).fetch().$copyAppStateFrom(
            obj.deserialize(localStore, new VersionWarranty(0)));
      }

      // Rerun the call.
      SEMANTIC_WARRANTY_LOGGER.finest("BEGINNING RECOMPUTATION OF " + call);
      call.runCall();
      SEMANTIC_WARRANTY_LOGGER.finest("DONE RECOMPUTING CALL " + call);

      Map<CallInstance, SemanticWarrantyRequest> updatedRequests =
          new HashMap<CallInstance, SemanticWarrantyRequest>();
      for (CallInstance checkcall : uncertainCalls) {
        SemanticWarrantyRequest req = tm.getCurrentLog().getRequest(checkcall);
        if (req != null) {
          updatedRequests.put(checkcall, req);
        }
      }
      throw new CallCheckException(updatedRequests);
    }

    /**
     * Take the update mapping from a call check and remove those that were
     * changed from what we have in our table already.  Updates the updates map
     * to no longer include the calls in the returned set.
     */
    private Map<CallInstance, SemanticWarrantyRequest> patchUpUpdates(
        Map<CallInstance, SemanticWarrantyRequest> updates) {
      Map<CallInstance, SemanticWarrantyRequest> callsCopy =
        new HashMap<CallInstance, SemanticWarrantyRequest>(updates);
      Map<CallInstance, SemanticWarrantyRequest> callsChanged =
        new HashMap<CallInstance, SemanticWarrantyRequest>();

      for (Map.Entry<CallInstance, SemanticWarrantyRequest> entry :
          callsCopy.entrySet()) {
        if (!updates.containsKey(entry.getKey())) continue;
        SemanticWarrantyRequest req = entry.getValue();
        CallInstance curCall = entry.getKey();
        // If we didn't get the old value, move it into the changed map.
        if (!getInfo(curCall).compareValue(req.value)) {
          updates.remove(curCall);
          callsChanged.put(curCall, req);
        }
      }
      return callsChanged;
    }

    /**
     * Check if the call value is changed given a set of updates.  Returns true
     * if either the call is verified to be changed by the updates or if the
     * current time reaches the given deadline time (in which case, we assume
     * the value changes).
     */
    private boolean isAffectedBy(Set<CallInstance> uncertainCalls,
        Map<CallInstance, SemanticWarrantyRequest> updates,
        Map<CallInstance, SemanticWarrantyRequest> changes,
        Collection<SerializedObject> creates,
        Collection<SerializedObject> writes, long deadline) {
      // This doesn't cause a deadlock with the spawned checking thread because
      // we explicitly don't try to re-use the value from the store for this
      // call.
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          throw new InternalError("Attempting to check a call that we have no" +
              " value for!");
          // For now, we're not going to bother with checking things that are
          // stale or pending.
        case STALE:
        case VALID:
        default:
          SEMANTIC_WARRANTY_LOGGER.finest("CHECKING CALL " + call);
          if (!warranty.expiresBefore(deadline, true)) {
            Future<Map<CallInstance, SemanticWarrantyRequest>> check =
                Executors.newSingleThreadExecutor().submit(
                    getCallChecker(uncertainCalls, updates, changes, creates, writes));

            // Get the result
            Map<CallInstance, SemanticWarrantyRequest> newUpdates = check.get();
            // Fix it up so we separate what's changed from what wasn't
            Map<CallInstance, SemanticWarrantyRequest> newChanges = patchUpUpdates(newUpdates);
            // Remove all the calls that we were able to check out
            uncertainCalls.removeAll(newUpdates.keySet());
            uncertainCalls.removeAll(newChanges.keySet());
            // Add to our "master" groups of things that changed or didn't
            changes.putAll(newChanges);
            updates.putAll(newUpdates);
            // Answer is whether we saw a change or not
            SEMANTIC_WARRANTY_LOGGER.finest("CALL CHECK FOR " + call
                + " FINISHED");
            return changes.containsKey(this.call);
          } else {
            return true;
          }
        }
      } catch (ExecutionException | InterruptedException e) {
        System.err.println(e.getMessage());
        e.printStackTrace(System.err);
        throw new InternalError("Call checking for " + call
            + " ran into an exception!!!!");
      } finally {
        unlock();
      }
    }

    /**
     * Acquire locks needed for write prepare operations.
     *
     * Sort of a kludge: this method is handed a set of calls it already owns a
     * lock on.  This method should not re-acquire a lock that it has from a
     * previous run of this method.
     *
     * Locks are unlocked in the writePrepare method after everything is done.
     */
    // XXX: Ugh, if the sister method does not run, we're in trouble.
    public void lockForWritePrepare(Set<CallInstance> locksOwned) {
      lock();
      try {
        if (locksOwned.contains(call)) {
          unlock();
        } else {
          locksOwned.add(call);
          issuer.notifyWritePrepare(call, warranty);
        }
        switch (getStatus()) {
        case NOVALUE:
          throw new InternalError(
              "Trying to lock and compute a write time for a valueless call!");
        case VALID:
        case STALE:
        default:
          for (CallInstance parent : new TreeSet<CallInstance>(getCallers())) {
            getInfo(parent).lockForWritePrepare(locksOwned);
          }
        }
      } catch (InternalError e) {
        locksOwned.remove(call);
        unlock();
        throw e;
      }
    }

    /**
     * Determine the next time this call can be updated.
     *
     * Assumes that all the locks needed have already been acquired.
     */
    public long proposeWriteTime(Set<CallInstance> uncertainCalls,
        long longestSoFar, Map<CallInstance, SemanticWarrantyRequest> updates,
        Map<CallInstance, SemanticWarrantyRequest> changes,
        Collection<SerializedObject> creates,
        Collection<SerializedObject> writes) throws
      TransactionPrepareFailedException {
      try {
        writeLock();
      } catch (UnableToLockException e) {
        throw new TransactionPrepareFailedException("Could not write " + 
            "lock dependent call " + call);
      }
      switch (getStatus()) {
      case NOVALUE:
        throw new InternalError(
            "Attempting to propose a write time for a valueless call!");
      case VALID:
      case STALE:
      default:
        long longest =
            longestSoFar > warranty.expiry() ? longestSoFar : warranty.expiry();
        if (isAffectedBy(uncertainCalls, updates, changes, creates, writes,
              longest)) {
          for (CallInstance parent : new TreeSet<CallInstance>(getCallers())) {
            long parentTime =
                getInfo(parent).proposeWriteTime(uncertainCalls, longest,
                    updates, changes, creates, writes);
            longest = parentTime > longest ? parentTime : longest;
          }
          return longest;
        } else {
          // Don't use warranty expiry since we know that it doesn't matter in
          // this case.
          return longestSoFar;
        }
      }
    }

    /**
     * Schedule the next write for this call.
     *
     * This should only be done after calling proposeWriteTime and with a time
     * greater than what proposeWriteTime returned.
     * 
     * This code assumes the call is already locked by the current thread (along
     * with any parent calls that this will traverse).
     */
    public void scheduleWriteAt(long time, long transactionID,
        Map<CallInstance, SemanticWarrantyRequest> updates,
        Map<CallInstance, SemanticWarrantyRequest> changes) {
      switch (getStatus()) {
      case NOVALUE:
        throw new InternalError(
            "Attempting to schedule a write on a valueless call!");
      case STALE:
      case VALID:
      default:
        // Schedule parents only if their dependency changed.
        if (changes.containsKey(call)) {
          for (CallInstance parent : new TreeSet<CallInstance>(getCallers())) {
            getInfo(parent).scheduleWriteAt(time, transactionID, updates, changes);
          }
        }
        SemanticWarrantyRequest newRequest;
        if (updates.containsKey(call)) {
          newRequest = updates.get(call);
        } else {
          newRequest = changes.get(call);
        }
        scheduleUpdateAt(transactionID, newRequest);
        extendWarranty(value, time); // XXX: Check if that worked...
        break;
      }
    }

    /**
     * Update from the next write to this call.
     *
     * null if there isn't a known upcoming write.
     */
    private SemanticWarrantyRequest nextUpdate;

    /**
     * Schedule update for the given commit.
     */
    //Currently checking for this before calling, but might be safer to check
    //here too.
    private void scheduleUpdateAt(long transactionID,
        SemanticWarrantyRequest update) {
      lock();
      try {
        switch (getStatus()) {
        case NOVALUE:
          throw new InternalError("Trying to update a valueless call!");
        case STALE:
        case VALID:
        default:
          // Defend the reads
          for (LongIterator iter = nextUpdate.readOnums.iterator();
              iter.hasNext();) {
            long read = iter.next();
            readersTable.get(read).add(call);
          }

          // Defend the creates
          for (LongIterator iter = nextUpdate.createOnums.iterator();
              iter.hasNext();) {
            long create = iter.next();
            creatorTable.get(create).add(call);
          }

          // Defend the subcalls
          for (CallInstance subcall : nextUpdate.calls.keySet()) {
            getInfo(subcall).addCaller(call);
          }
          
          // Set the update
          nextUpdate = update;

          // Add this call to the update map for the transaction
          updatingTIDMap.putIfAbsent(transactionID, new HashSet<CallInstance>());
          updatingTIDMap.get(transactionID).add(call);
        }
      } finally {
        unlock();
      }
    }

    /**
     * Cancel an update from a transaction.
     */
    //TODO: Do this by status
    public void removeUpdate(long transactionID) {
      lock();
      try {
        // Make sure there was actually an update.
        if (nextUpdate == null) return;

        // Handle reads
        for (LongIterator iter = nextUpdate.readOnums.iterator();
            iter.hasNext();) {
          long read = iter.next();
          if (!reads.contains(read)) readersTable.get(read).remove(call);
        }

        // Handle creates
        for (LongIterator iter = nextUpdate.createOnums.iterator();
            iter.hasNext();) {
          long create = iter.next();
          if (!creates.contains(create)) creatorTable.get(create).remove(call);
        }

        // Handle calls
        for (CallInstance subCall : nextUpdate.calls.keySet()) {
          if (!calls.contains(subCall)) {
            getInfo(subCall).removeCaller(this.call);
          }
        }
        
        // Remove the pending update
        nextUpdate = null;
      } finally {
        // Should have been write locked by the scheduled update.
        writeUnlock();
        unlock();
      }
    }

    /**
     * Complete an update from a transaction.
     */
    //TODO: Do this by status
    public void update(long transactionID) {
      lock();
      try {
        issuer.notifyWriteCommit(call);

        // Remove old stuff
        // Reads
        for (LongIterator iter = reads.iterator(); iter.hasNext();) {
          long read = iter.next();
          if (!nextUpdate.readOnums.contains(read))
            readersTable.get(read).remove(call);
        }

        // Creates
        for (LongIterator iter = creates.iterator(); iter.hasNext();) {
          long create = iter.next();
          if (!nextUpdate.createOnums.contains(create))
            creatorTable.get(create).remove(call);
        }

        // Calls
        for (CallInstance subCall : calls)
          if (!nextUpdate.calls.containsKey(subCall))
            getInfo(subCall).removeCaller(this.call);

        // Set new stuff up
        // Reads
        reads.removeAll(reads);
        for (LongIterator iter = nextUpdate.readOnums.iterator(); iter.hasNext();) {
          long read = iter.next();
          reads.add(read);
        }

        // Creates
        creates.removeAll(creates);
        for (LongIterator iter = nextUpdate.createOnums.iterator(); iter.hasNext();) {
          long create = iter.next();
          creates.add(create);
        }

        // Calls
        calls.removeAll(calls);
        for (CallInstance subCall : nextUpdate.calls.keySet()) {
          calls.add(subCall);
        }

        // Set value, warranty, and status
        value = nextUpdate.value;
        warranty = new SemanticWarranty(0);
        setStatus(CallStatus.STALE);

        // Reset update to nothing.
        nextUpdate = null;
      } finally {
        // This should have been locked when the update was scheduled.
        writeUnlock();
        unlock();
      }
    }
  }

  /**
   * Table mapping calls to their info.
   */
  private final ConcurrentMap<CallInstance, CallInfo> infoTable;

  /**
   * Table mapping from oid to their readers.
   */
  private final ConcurrentLongKeyMap<Set<CallInstance>> readersTable;

  /**
   * Table mapping from oid to their creating call.
   */
  private final ConcurrentLongKeyMap<Set<CallInstance>> creatorTable;

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
   * Table mapping from transactionIDs for pending transactions to sets of calls
   * that will be updated by the transaction.
   */
  private final ConcurrentLongKeyMap<Set<CallInstance>> updatingTIDMap;

  /**
   * Local reference to the ObjectDB for items on this store.
   */
  private final ObjectDB database;

  public SemanticWarrantyTable(ObjectDB database) {
    infoTable = new ConcurrentHashMap<CallInstance, CallInfo>();
    readersTable = new ConcurrentLongKeyHashMap<Set<CallInstance>>();
    creatorTable = new ConcurrentLongKeyHashMap<Set<CallInstance>>();
    issuer =
        new WarrantyIssuer<CallInstance>();
    pendingTIDMap = new ConcurrentLongKeyHashMap<Set<CallInstance>>();
    updatingTIDMap = new ConcurrentLongKeyHashMap<Set<CallInstance>>();
    this.database = database;
  }

  /**
   * Get the warranty + call value for the given call.
   */
  public final WarrantiedCallResult get(CallInstance call) {
    CallInfo info = getInfo(call);
    info.lock();
    try {
      switch (info.getStatus()) {
      case NOVALUE:
        return null;
      case VALID:
      case STALE:
      default:
        return new WarrantiedCallResult(info.getValue(), info.getWarranty());
      }
    } finally {
      info.unlock();
    }
  }

  /**
   * Get the info object associated with a call.  Create a new object if there
   * hasn't been one created already.
   */
  private CallInfo getInfo(CallInstance call) {
    infoTable.putIfAbsent(call, new CallInfo(call));
    return infoTable.get(call);
  }

  /**
   * Get the set of oids created during the last recording of the given call.
   */
  public final LongSet getCreates(CallInstance call) {
    CallInfo info = getInfo(call);
    if (info.getStatus() == CallStatus.NOVALUE) return new LongHashSet();
    return info.getCreates();
  }

  /**
   * Determine a term for a new warranty for the given request and schedule it
   * to become valid/expiring on the commit of the given transaction ID.
   */
  public SemanticWarranty requestWarranty(long transactionID,
      SemanticWarrantyRequest req) throws TransactionPrepareFailedException {
    CallInfo info = getInfo(req.call);
    info.lock();
    try {
      info.request(req, transactionID);
      return info.getWarranty();
    } finally {
      info.unlock();
    }
  }

  /**
   * Schedule a new semantic warranty to become active at the given time.
   */
  public void putAt(long commitTime, final CallInstance call) {
    Threading.scheduleAt(commitTime, new Runnable() {
      @Override
      public void run() {
        getInfo(call).activate();
      }
    });
  }

  /**
   * Extends the warranty for an id only if it currently has a specific
   * warrantied call result.
   */
  public final Pair<SemanticExtendStatus, WarrantiedCallResult> extend(
      CallInstance call, WarrantiedCallResult oldValue, long newTime) {
    CallInfo info = getInfo(call);
    info.lock();
    try {
      SemanticExtendStatus stat = info.extendWarranty(oldValue.value, newTime);
      WarrantiedCallResult res = get(call);
      return new Pair<SemanticExtendStatus, WarrantiedCallResult>(stat, res);
    } finally {
      info.unlock();
    }
  }

  /**
   * Gather up all the expired calls in the subgraph starting from the given
   * call.  This is used for notifying a worker of expired warranties.
   */
  public Set<CallInstance> getExpiredSubgraph(CallInstance root) {
    return getInfo(root).getExpiredCalls();
  }

  /**
   * Notifies the issuer of a read prepare extending the Semantic Warranty for
   * the given call.
   */
  public void notifyReadPrepare(CallInstance call, long commitTime) {
    SEMANTIC_WARRANTY_LOGGER.finer("Notifying read prepare on " + call);
    issuer.notifyReadPrepare(call, commitTime);
  }

  /**
   * Provides the longest SemanticWarranty that depended on any of the given
   * onums that is longer than the given commitTime.  Also performs any
   * bookkeeping associated with write events (like removing stale call values).
   */
  public Pair<SemanticWarranty, Map<CallInstance, SemanticWarrantyRequest>>
    prepareWrites(Collection<SerializedObject> writes,
        Collection<SerializedObject> creates, long transactionID,
        long commitTime, final String storeName) throws
    TransactionPrepareFailedException {
    // XXX: Pretty sure we're not actually being safe about lock order here...
    // need a way to ensure we don't accidentally operate on an ancestor of one
    // of the other calls first...
    TreeSet<CallInstance> affectedCalls = new TreeSet<CallInstance>();
    for (SerializedObject obj : writes) {
      readersTable.putIfAbsent(obj.getOnum(), new HashSet<CallInstance>());
      affectedCalls.addAll(readersTable.get(obj.getOnum()));
      creatorTable.putIfAbsent(obj.getOnum(), new HashSet<CallInstance>());
      affectedCalls.addAll(creatorTable.get(obj.getOnum()));
    }

    long longest = commitTime;
    Map<CallInstance, SemanticWarrantyRequest> changes =
      new HashMap<CallInstance, SemanticWarrantyRequest>();
    Map<CallInstance, SemanticWarrantyRequest> updates =
      new HashMap<CallInstance, SemanticWarrantyRequest>();

    // Lock the calls.
    TreeSet<CallInstance> lockedCalls = new TreeSet<CallInstance>();
    try {
      for (CallInstance call : affectedCalls) {
        getInfo(call).lockForWritePrepare(lockedCalls);
      }

      // Propose a time and check what will change.
      Set<CallInstance> uncertainCalls = new HashSet<CallInstance>(lockedCalls);
      for (CallInstance call : affectedCalls) {
        long suggestedTime =
            getInfo(call).proposeWriteTime(uncertainCalls, longest, updates,
                changes, creates, writes);
        longest = longest > suggestedTime ? longest : suggestedTime;
      }

      // Schedule the write/update
      for (CallInstance call : affectedCalls) {
        getInfo(call).scheduleWriteAt(longest, transactionID, updates, changes);
      }
    } catch (TransactionPrepareFailedException e) {
      // Remove write locks for those we actually checked and therefore did
      // writeLock.
      for (CallInstance writeLockedCall : updates.keySet())
        getInfo(writeLockedCall).writeUnlock();
      for (CallInstance writeLockedCall : changes.keySet())
        getInfo(writeLockedCall).writeUnlock();
      throw e;
    } finally {
      // Unlock calls locked during initial traversal
      for (CallInstance call : lockedCalls) {
        getInfo(call).unlock();
      }
    }

    updates.putAll(changes);

    return
      new Pair<SemanticWarranty, Map<CallInstance, SemanticWarrantyRequest>>(
          new SemanticWarranty(longest), updates);
  }

  /**
   * Remove any state associated with the given transactionID due to a
   * transaction abort.
   */
  public void abort(long transactionID) {
    SEMANTIC_WARRANTY_LOGGER.finer(String.format(
        "Aborting semantic warranty updates from %x", transactionID));
    Set<CallInstance> callSet = pendingTIDMap.remove(transactionID);
    if (callSet != null) {
      for (CallInstance call : callSet) {
        getInfo(call).abort();
      }
    }
    Set<CallInstance> updates = updatingTIDMap.remove(transactionID);
    if (updates != null) {
      for (CallInstance call : updates) {
        getInfo(call).removeUpdate(transactionID);
      }
    }
  }

  /**
   * Commit any state associated with the given transactionID at the given
   * commitTime.
   */
  public void commit(long transactionID, long commitTime) {
    SEMANTIC_WARRANTY_LOGGER.finer(String.format(
        "Committing semantic warranty updates from %x", transactionID));
    // Add requests made by the original transaction
    Set<CallInstance> callSet = pendingTIDMap.remove(transactionID);
    if (callSet != null) {
      for (CallInstance call : callSet) {
        putAt(commitTime, call);
      }
    }
    Set<CallInstance> updates = updatingTIDMap.remove(transactionID);
    if (updates != null) {
      for (CallInstance call : updates) {
        getInfo(call).update(transactionID);
      }
    }
  }
}
