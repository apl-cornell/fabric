package fabric.store.db;

import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import fabric.common.SysUtil;
import fabric.common.Threading;
import fabric.common.VersionWarranty;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;
import fabric.lang.WrappedJavaInlineable;
import fabric.worker.AbortException;
import fabric.worker.Store;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.Worker.Code;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.memoize.WarrantiedCallResult;
import fabric.worker.transaction.Log;
import fabric.worker.transaction.TransactionManager;

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
   * Used for comparing call results.
   */
  private class CompareCheckException extends RuntimeException {
    /* Result of the comparison */
    public final boolean result;

    public CompareCheckException(boolean result) {
      this.result = result;
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

    private List<StackTraceElement[]> lastLockingStack =
        new ArrayList<StackTraceElement[]>();

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
        if (writeLocked) throw new UnableToLockException();
        writeLocked = true;
        lastLockingStack.add(Thread.currentThread().getStackTrace());
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
        lastLockingStack.remove(lastLockingStack.size() - 1);
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
    private boolean hasValue = false;

    /**
     * Get the call's status.
     */
    public CallStatus getStatus() {
      if (!hasValue) return CallStatus.NOVALUE;
      if (warranty.expired(true)) return CallStatus.STALE;
      return CallStatus.VALID;
    }

    /**
     * The call's value.
     */
    private fabric.lang.Object value;

    /**
     * Get the call's value.  Returns null if the call is expired.
     */
    public fabric.lang.Object getValue() {
      switch (getStatus()) {
      case NOVALUE:
        throw new InternalError(
            "Attempt to read value of a call with no value!");
      case VALID:
      case STALE:
      default:
        return value;
      }
    }

    /**
     * Compare the current result with another value.  Return true if they
     * agree, otherwise false.
     */
    public boolean compareValue(fabric.lang.Object otherVal) {
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
            try {
              final fabric.lang.Object value1 = value;
              final fabric.lang.Object value2 = otherVal;
              Worker.runInTopLevelTransaction(new Code<Void>() {
                @Override
                public Void run() {
                  throw new CompareCheckException(value1.equals(value2));
                }
              }, false);
            } catch (AbortException e) {
              if (e.getCause() instanceof CompareCheckException) {
                return ((CompareCheckException) e.getCause()).result;
              }
            }
          }
        }
        return false;
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
      switch (getStatus()) {
      case NOVALUE:
        throw new InternalError(
            "Attempt to get warranty of call with no established value!");
      case VALID:
      case STALE:
      default:
        return warranty;
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
      switch (getStatus()) {
      case NOVALUE:
        throw new InternalError("Attempt to get callers of a valueless call!");
      case VALID:
      case STALE:
      default:
        return new HashSet<CallInstance>(callers);
      }
    }

    /**
     * Get the calls this call used.
     */
    @SuppressWarnings("unused")
    public Set<CallInstance> getCalls() {
      switch (getStatus()) {
      case NOVALUE:
        throw new InternalError("Attempt to get calls of a valueless call!");
      case VALID:
      case STALE:
      default:
        return new HashSet<CallInstance>(calls);
      }
    }

    /**
     * Add a new caller.
     *
     * Don't error in any case right now.  Might be better if we verify that a
     * caller is only being added in the case of NOVALUE when it's being
     * requested...
     */
    private void addCaller(CallInstance caller) {
      switch (getStatus()) {
      case NOVALUE:
      case VALID:
      case STALE:
      default:
        callers.add(caller);
      }
    }

    /**
     * Remove a caller.
     */
    private void removeCaller(CallInstance caller) {
      switch (getStatus()) {
      case NOVALUE:
      case VALID:
      case STALE:
      default:
        callers.remove(caller);
      }
    }

    /**
     * Get the reads made during this call.
     */
    @SuppressWarnings("unused")
    public LongSet getReads() {
      switch (getStatus()) {
      case NOVALUE:
        throw new InternalError("Attempt to get reads from a valueless call!");
      case VALID:
      case STALE:
      default:
        return new LongHashSet(reads);
      }
    }

    /**
     * Get the oids created during this call.
     */
    public LongSet getCreates() {
      switch (getStatus()) {
      case NOVALUE:
        throw new InternalError("Attempt to get creates from a valueless call!");
      case VALID:
      case STALE:
      default:
        return new LongHashSet(creates);
      }
    }

    public CallInfo(CallInstance call) {
      try {
        this.writeLock();
      } catch (UnableToLockException e) {
        throw new InternalError("This shouldn't happen");
      }
      try {
        this.call = call;
        this.value = null;
        this.warranty = new SemanticWarranty(0);
        this.callers = new HashSet<CallInstance>();
        this.calls = new HashSet<CallInstance>();
        this.creates = new LongHashSet();
        this.reads = new LongHashSet();
        this.nextUpdate = null;
      } finally {
        this.writeUnlock();
      }
    }

    /**
     * Request a new warranty for the given call request and return the warranty
     * that will be used for the call.
     */
    public SemanticWarranty request(SemanticWarrantyRequest req,
        long transactionID, boolean useIssuer)
        throws TransactionPrepareFailedException {
      switch (getStatus()) {
      case VALID:
      case STALE:
      case NOVALUE:
      default:
        try {
          writeLock();
          //Check that dependencies aren't planning to update in the near future
          for (LongIterator iter = req.readOnums.iterator(); iter.hasNext();) {
            long onum = iter.next();
            if (database.isWritten(onum)) {
              SEMANTIC_WARRANTY_LOGGER.finest("Request for call " + call
                  + " depends on object " + onum
                  + " that has a write scheduled.");
              writeUnlock();
              throw new TransactionPrepareFailedException("Request for call "
                  + call + " depends on object " + onum
                  + " that has a write scheduled.");
            }
          }

          updatingTIDMap
              .putIfAbsent(transactionID, new HashSet<CallInstance>());

          // Check calls
          for (CallInstance c : req.calls.keySet()) {
            if (!updatingTIDMap.get(transactionID).contains(c)) {
              try {
                getInfo(c).writeLock();
                getInfo(c).writeUnlock();
              } catch (UnableToLockException e) {
                SEMANTIC_WARRANTY_LOGGER.finest("Request for call " + call
                    + " depends on call " + call
                    + " that has a write scheduled.");
                writeUnlock();
                throw new TransactionPrepareFailedException("Request for call "
                    + call + " depends on call " + call
                    + " that has a write scheduled.");
              }
            }
          }

          // Set warranty
          SemanticWarranty newWarranty = new SemanticWarranty(0);
          if (useIssuer) {
            long suggestedTime = issuer.suggestWarranty(req.call);
            newWarranty = new SemanticWarranty(suggestedTime);
          }

          // Schedule the update
          scheduleUpdateAt(transactionID, req, newWarranty);

          return nextUpdateWarranty;
        } catch (UnableToLockException e) {
          SEMANTIC_WARRANTY_LOGGER.finest("Could not lock call " + call
              + " for write");
          throw new TransactionPrepareFailedException("Could not lock call "
              + call + " for write");
        }
      }
    }

    /**
     * Extends the current warranty on the call.  First checks that the value
     * provided is what we have in the table.  If this is for a read prepare,
     * use the issuer to see about making an EVEN LONGER warranty.
     */
    public SemanticExtendStatus extendWarranty(fabric.lang.Object oldValue,
        long commitTime, boolean readPrepare) {
      switch (getStatus()) {
      case STALE:
        // Check what they think it is.
        if (!compareValue(oldValue)) {
          if (readPrepare) {
            try {
              writeLock();
              // Call is being read, so make the warranty valid anyways.
              warranty = new SemanticWarranty(issuer.suggestWarranty(call));
              writeUnlock();
            } catch (UnableToLockException e) {
              // This means we shouldn't extend the warranty.  Ignore it.
            }
          }
          return SemanticExtendStatus.BAD_VERSION;
        }
        // Update the warranty
        if (warranty.expiresBefore(commitTime, true)) {
          try {
            writeLock();
            if (readPrepare) {
              warranty =
                  new SemanticWarranty(issuer.suggestWarranty(call, commitTime));
            } else {
              warranty = new SemanticWarranty(commitTime);
            }
            writeUnlock();
          } catch (UnableToLockException e) {
            // There's a pending write.  Can't!
            return SemanticExtendStatus.DENIED;
          }
        }
        return SemanticExtendStatus.OK;
      case VALID:
        // Check what they think it is.
        if (!compareValue(oldValue)) return SemanticExtendStatus.BAD_VERSION;
        // Update the warranty
        if (warranty.expiresBefore(commitTime, true)) {
          // Check that we won't be extending past the next write.
          try {
            writeLock();
            if (readPrepare) {
              warranty =
                  new SemanticWarranty(issuer.suggestWarranty(call, commitTime));
            } else {
              warranty = new SemanticWarranty(commitTime);
            }
            writeUnlock();
          } catch (UnableToLockException e) {
            // There's a pending write.  Can't!
            return SemanticExtendStatus.DENIED;
          }
        }
        return SemanticExtendStatus.OK;
      case NOVALUE:
      default:
        return SemanticExtendStatus.DENIED;
      }
    }

    /**
     * Creates a new warranty on the call if the current one is expired (if
     * possible).
     */
    public void updateWarranty() {
      switch (getStatus()) {
      case STALE:
        // Update the warranty if possible
        try {
          writeLock();
          warranty = new SemanticWarranty(issuer.suggestWarranty(call));
          writeUnlock();
        } catch (UnableToLockException e) {
          // Can't update, do nothing.
        }
        return;
      case VALID:
      case NOVALUE:
      default:
        return;
      }
    }

    /**
     * Find all calls that were used (directly or indirectly) by this call
     * (including itself) which are now expired.
     */
    public Set<CallInstance> getExpiredCalls() {
      Set<CallInstance> expired = new HashSet<CallInstance>();
      switch (getStatus()) {
      case NOVALUE:
        expired.add(this.call);
        Set<CallInstance> children = new TreeSet<CallInstance>(this.calls);
        for (CallInstance child : children) {
          expired.addAll(getExpiredSubgraph(child));
        }
        // $FALL-THROUGH$
      case VALID:
      case STALE:
      default:
        return expired;
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
        final Map<CallInstance, SemanticWarrantyRequest> newCalls,
        final Collection<SerializedObject> creates,
        final Collection<SerializedObject> writes) {
      return new Callable<Map<CallInstance, SemanticWarrantyRequest>>() {
        @Override
        public Map<CallInstance, SemanticWarrantyRequest> call() {
          try {
            Worker.runInTopLevelTransaction(new Code<Void>() {
              @Override
              public Void run() {
                checkCall(Worker.getWorker().getStore(database.getName()),
                    uncertainCalls, updates, changes, newCalls, creates, writes);
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
        Map<CallInstance, SemanticWarrantyRequest> newCalls,
        Collection<SerializedObject> creates,
        Collection<SerializedObject> writes) {
      TransactionManager tm = TransactionManager.getInstance();
      Log current = tm.getCurrentLog();
      // Ensure that we don't reuse calls we're uncertain of or we know for a
      // fact will not be correct now.
      current.blockedWarranties.addAll(uncertainCalls);

      // Insert already checked calls to allow for faster processing.
      current.addRequests(updates);
      current.addRequests(changes);
      current.addRequests(newCalls);

      // Load up state from writes
      for (SerializedObject obj : writes) {
        tm.registerWrite(obj.deserialize(localStore, new VersionWarranty(0)));
      }

      // Load up state from creates
      for (SerializedObject obj : creates) {
        SEMANTIC_WARRANTY_LOGGER.finest("Loading up create " + obj.getOnum());
        tm.registerCreate(obj.deserialize(localStore, new VersionWarranty(0)));
      }
      for (SemanticWarrantyRequest req : SysUtil.chain(
          SysUtil.chain(updates.values(), changes.values()), newCalls.values())) {
        for (LongKeyMap<_Impl> submap : req.creates) {
          for (_Impl c : submap.values()) {
            SEMANTIC_WARRANTY_LOGGER.finest("Loading up create from other "
                + "request " + c.$getOnum());
            tm.registerCreate(c);
          }
        }
      }

      // Rerun the call.
      SEMANTIC_WARRANTY_LOGGER.finest("BEGINNING RECOMPUTATION OF " + call);
      call.runCall();
      SEMANTIC_WARRANTY_LOGGER.finest("DONE RECOMPUTING CALL " + call);

      Map<CallInstance, SemanticWarrantyRequest> updatedRequests =
          current.getAllRequests();
      for (CallInstance key : updatedRequests.keySet())
        SEMANTIC_WARRANTY_LOGGER.finest("GOT AN UPDATE ON " + key);
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

      for (Map.Entry<CallInstance, SemanticWarrantyRequest> entry : callsCopy
          .entrySet()) {
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
        Map<CallInstance, SemanticWarrantyRequest> newCalls,
        Collection<SerializedObject> creates,
        Collection<SerializedObject> writes, long deadline)
        throws TransactionPrepareFailedException {
      try {
        switch (getStatus()) {
        case NOVALUE:
          throw new InternalError("Attempting to check a call that we have no"
              + " value for!");
          // For now, we're not going to bother with checking things that are
          // stale or pending.
        case STALE:
        case VALID:
        default:
          SEMANTIC_WARRANTY_LOGGER.finest("CHECKING CALL " + call);

          if (updates.containsKey(call)) {
            SEMANTIC_WARRANTY_LOGGER.finest("CALL ALREADY CHECKED EARLIER AND"
                + " WAS UNAFFECTED");
            return false;
          } else if (changes.containsKey(call)) {
            SEMANTIC_WARRANTY_LOGGER.finest("CALL ALREADY CHECKED EARLIER AND"
                + " WAS AFFECTED");
            return true;
          }

          Future<Map<CallInstance, SemanticWarrantyRequest>> check =
              Executors.newSingleThreadExecutor().submit(
                  getCallChecker(uncertainCalls, updates, changes, newCalls,
                      creates, writes));

          // Get the result
          Map<CallInstance, SemanticWarrantyRequest> newUpdates = check.get();
          for (CallInstance call : new HashSet<CallInstance>(
              newUpdates.keySet())) {
            if (getInfo(call).getStatus() == CallStatus.NOVALUE) {
              newCalls.put(call, newUpdates.get(call));
              newUpdates.remove(call);
            }
          }

          // Fix it up so we separate what's changed from what wasn't
          Map<CallInstance, SemanticWarrantyRequest> newChanges =
              patchUpUpdates(newUpdates);
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
        }
      } catch (ExecutionException | InterruptedException e) {
        throw new TransactionPrepareFailedException(
            "Call checking ran into an " + "error! " + e.getMessage());
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
    public Set<CallInstance> getAffectedSet() {
      Set<CallInstance> affected = new TreeSet<CallInstance>();
      affected.add(call);
      switch (getStatus()) {
      case NOVALUE:
      case VALID:
      case STALE:
      default:
        for (CallInstance parent : new TreeSet<CallInstance>(getCallers())) {
          affected.addAll(getInfo(parent).getAffectedSet());
        }
      }
      return affected;
    }

    /**
     * Determine the next time this call can be updated.
     *
     * Assumes that all the locks needed have already been acquired.
     */
    public long proposeWriteTime(Set<CallInstance> uncertainCalls,
        long longestSoFar, Map<CallInstance, SemanticWarrantyRequest> updates,
        Map<CallInstance, SemanticWarrantyRequest> changes,
        Map<CallInstance, SemanticWarrantyRequest> newCalls,
        Collection<SerializedObject> creates,
        Collection<SerializedObject> writes, Set<CallInstance> writeLocked)
        throws TransactionPrepareFailedException {
      try {
        if (!writeLocked.contains(call)) {
          writeLock();
          writeLocked.add(call);
        }
      } catch (UnableToLockException e) {
        SEMANTIC_WARRANTY_LOGGER.finest("Could not write lock dependent call "
            + call);
        throw new TransactionPrepareFailedException("Could not write "
            + "lock dependent call " + call);
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
        if (isAffectedBy(uncertainCalls, updates, changes, newCalls, creates,
            writes, longest)) {
          for (CallInstance parent : new TreeSet<CallInstance>(getCallers())) {
            long parentTime =
                getInfo(parent).proposeWriteTime(uncertainCalls, longest,
                    updates, changes, newCalls, creates, writes, writeLocked);
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
            getInfo(parent).scheduleWriteAt(time, transactionID, updates,
                changes);
          }
        }
        SemanticWarrantyRequest newRequest;
        if (updates.containsKey(call)) {
          newRequest = updates.get(call);
        } else if (changes.containsKey(call)) {
          newRequest = changes.get(call);
        } else {
          throw new InternalError("Somehow have a call with no associated "
              + "request being updated! " + call);
        }
        warranty = new SemanticWarranty(time);
        scheduleUpdateAt(transactionID, newRequest, new SemanticWarranty(0));
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
     * Warranty to use for the next update.
     *
     * null if there isn't a known upcoming update.
     */
    private SemanticWarranty nextUpdateWarranty;

    /**
     * Schedule update for the given commit.
     */
    //Currently checking for this before calling, but might be safer to check
    //here too.
    private void scheduleUpdateAt(long transactionID,
        SemanticWarrantyRequest update, SemanticWarranty updateWarranty) {
      switch (getStatus()) {
      case NOVALUE:
      case STALE:
      case VALID:
      default:
        // Defend the reads
        for (LongIterator iter = update.readOnums.iterator(); iter.hasNext();) {
          long read = iter.next();
          readersTable.putIfAbsent(read, new HashSet<CallInstance>());
          readersTable.get(read).add(call);
        }

        // Defend the creates
        for (LongIterator iter = update.createOnums.iterator(); iter.hasNext();) {
          long create = iter.next();
          creatorTable.putIfAbsent(create, new HashSet<CallInstance>());
          creatorTable.get(create).add(call);
        }

        // Defend the subcalls
        for (CallInstance subcall : update.calls.keySet()) {
          getInfo(subcall).addCaller(call);
        }

        // Set the update
        nextUpdate = update;
        nextUpdateWarranty = updateWarranty;

        // Add this call to the update map for the transaction
        updatingTIDMap.putIfAbsent(transactionID, new HashSet<CallInstance>());
        updatingTIDMap.get(transactionID).add(call);
      }
    }

    /**
     * Cancel an update from a transaction.
     */
    public void removeUpdate() {
      try {
        // Make sure there was actually an update.
        if (nextUpdate == null) return;

        // Handle reads
        for (LongIterator iter = nextUpdate.readOnums.iterator(); iter
            .hasNext();) {
          long read = iter.next();
          if (!reads.contains(read)) readersTable.get(read).remove(call);
        }

        // Handle creates
        for (LongIterator iter = nextUpdate.createOnums.iterator(); iter
            .hasNext();) {
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
        nextUpdateWarranty = null;
      } finally {
        // Should have been write locked by the scheduled update.
        writeUnlock();
      }
    }

    /**
     * Complete an update from a transaction.
     */
    public void update() {
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
        reads.clear();
        for (LongIterator iter = nextUpdate.readOnums.iterator(); iter
            .hasNext();) {
          long read = iter.next();
          reads.add(read);
        }

        // Creates
        creates.clear();
        for (LongIterator iter = nextUpdate.createOnums.iterator(); iter
            .hasNext();) {
          long create = iter.next();
          creates.add(create);
        }

        // Calls
        calls.clear();
        for (CallInstance subCall : nextUpdate.calls.keySet()) {
          calls.add(subCall);
        }

        // Set value, warranty, and status
        if (nextUpdate.value == null
            || nextUpdate.value instanceof WrappedJavaInlineable) {
          value = nextUpdate.value;
        } else {
          value =
              new _Proxy(nextUpdate.value.$getStore(),
                  nextUpdate.value.$getOnum());
        }

        // Set warranty
        warranty = nextUpdateWarranty;

        // Mark as having a value now.
        hasValue = true;

        // Reset update to nothing.
        nextUpdate = null;
        nextUpdateWarranty = null;
      } finally {
        // This should have been locked when the update was scheduled.
        writeUnlock();
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
    issuer = new WarrantyIssuer<CallInstance>();
    updatingTIDMap = new ConcurrentLongKeyHashMap<Set<CallInstance>>();
    this.database = database;
  }

  /**
   * Get the warranty + call value for the given call.
   */
  private WarrantiedCallResult get(CallInstance call) {
    CallInfo info = getInfo(call);
    switch (info.getStatus()) {
    case NOVALUE:
      return null;
    case VALID:
    case STALE:
    default:
      return new WarrantiedCallResult(info.getValue(), info.getWarranty());
    }
  }

  /**
   * Get the warranty + call value for the given call.
   */
  public final WarrantiedCallResult fetchForWorker(CallInstance call) {
    CallInfo info = getInfo(call);
    switch (info.getStatus()) {
    case NOVALUE:
      return null;
    case VALID:
    case STALE:
    default:
      info.updateWarranty();
      return new WarrantiedCallResult(info.getValue(), info.getWarranty());
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
      SemanticWarrantyRequest req, boolean getNonZeroWarranty)
      throws TransactionPrepareFailedException {
    CallInfo info = getInfo(req.call);
    return info.request(req, transactionID, getNonZeroWarranty);
  }

  /**
   * Schedule a new semantic warranty to become active at the given time.
   */
  public void putAt(long commitTime, final CallInstance call) {
    Threading.scheduleAt(commitTime, new Runnable() {
      @Override
      public void run() {
        getInfo(call).update();
      }
    });
  }

  /**
   * Extends the warranty for an id only if it currently has a specific
   * warrantied call result.
   */
  public final Pair<SemanticExtendStatus, WarrantiedCallResult> extendForReadPrepare(
      CallInstance call, WarrantiedCallResult oldValue, long newTime) {
    SEMANTIC_WARRANTY_LOGGER.finer("Notifying read prepare on " + call);
    issuer.notifyReadPrepare(call, newTime);
    CallInfo info = getInfo(call);
    SemanticExtendStatus stat =
        info.extendWarranty(oldValue.value, newTime, true);
    WarrantiedCallResult res = get(call);
    return new Pair<SemanticExtendStatus, WarrantiedCallResult>(stat, res);
  }

  /**
   * Gather up all the expired calls in the subgraph starting from the given
   * call.  This is used for notifying a worker of expired warranties.
   */
  public Set<CallInstance> getExpiredSubgraph(CallInstance root) {
    return getInfo(root).getExpiredCalls();
  }

  /**
   * Provides the longest SemanticWarranty that depended on any of the given
   * onums that is longer than the given commitTime.  Also performs any
   * bookkeeping associated with write events (like removing stale call values).
   */
  public Pair<SemanticWarranty, Pair<Map<CallInstance, SemanticWarrantyRequest>, Map<CallInstance, SemanticWarrantyRequest>>> prepareWrites(
      Collection<SerializedObject> writes,
      Collection<SerializedObject> creates, long transactionID,
      long commitTime, final String storeName)
      throws TransactionPrepareFailedException {
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
    Map<CallInstance, SemanticWarrantyRequest> newCalls =
        new HashMap<CallInstance, SemanticWarrantyRequest>();

    // Lock the calls.
    Set<CallInstance> writeLockedCalls = new HashSet<CallInstance>();
    try {
      Set<CallInstance> uncertainCalls = new TreeSet<CallInstance>();
      for (CallInstance call : affectedCalls) {
        uncertainCalls.addAll(getInfo(call).getAffectedSet());
      }

      // Propose a time and check what will change.
      for (CallInstance call : affectedCalls) {
        long suggestedTime =
            getInfo(call).proposeWriteTime(uncertainCalls, longest, updates,
                changes, newCalls, creates, writes, writeLockedCalls);
        longest = longest > suggestedTime ? longest : suggestedTime;
      }

      // Schedule the write/update
      for (CallInstance call : affectedCalls) {
        getInfo(call).scheduleWriteAt(longest, transactionID, updates, changes);
      }
    } catch (TransactionPrepareFailedException e) {
      // Remove write locks for those we actually checked and therefore did
      // writeLock.
      for (CallInstance writeLockedCall : writeLockedCalls)
        getInfo(writeLockedCall).writeUnlock();
      throw e;
    }

    updates.putAll(changes);
    SEMANTIC_WARRANTY_LOGGER.finest("DONE CHECKING FOR UPDATES ON TRANSACTION "
        + Long.toHexString(transactionID));

    return new Pair<SemanticWarranty, Pair<Map<CallInstance, SemanticWarrantyRequest>, Map<CallInstance, SemanticWarrantyRequest>>>(
        new SemanticWarranty(longest),
        new Pair<Map<CallInstance, SemanticWarrantyRequest>, Map<CallInstance, SemanticWarrantyRequest>>(
            updates, newCalls));
  }

  /**
   * Remove any state associated with the given transactionID due to a
   * transaction abort.
   */
  public void abort(long transactionID) {
    SEMANTIC_WARRANTY_LOGGER.finer(String.format(
        "Aborting semantic warranty updates from %x", transactionID));
    Set<CallInstance> updates = updatingTIDMap.remove(transactionID);
    if (updates != null) {
      for (CallInstance call : updates) {
        getInfo(call).removeUpdate();
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
    Set<CallInstance> updates = updatingTIDMap.remove(transactionID);
    if (updates != null) {
      for (CallInstance call : updates) {
        putAt(commitTime, call);
      }
    }
  }
}