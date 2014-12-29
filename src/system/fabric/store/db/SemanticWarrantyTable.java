package fabric.store.db;

import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.SemanticWarranty;
import fabric.common.SerializedObject;
import fabric.common.SysUtil;
import fabric.common.VersionWarranty;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.lang.Object._Impl;
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
import fabric.worker.transaction.ObjectLookAsideMap;
import fabric.worker.transaction.TransactionManager;

/**
 * A table containing semantic warranties, keyed by CallInstance id, and
 * supporting concurrent accesses.
 */
//TODO: Turn the sets in CallInfo into concurrent structures.
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
    public final Map<CallInstance, SemanticWarrantyRequest> updates;
    public final Map<CallInstance, SemanticWarrantyRequest> changes;
    public final Map<CallInstance, SemanticWarrantyRequest> newCalls;

    public CallCheckException(
        Map<CallInstance, SemanticWarrantyRequest> updates,
        Map<CallInstance, SemanticWarrantyRequest> changes,
        Map<CallInstance, SemanticWarrantyRequest> newCalls) {
      this.updates = updates;
      this.changes = changes;
      this.newCalls = newCalls;
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

    private StackTraceElement[] lastLockingStack = null;

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
        lastLockingStack = Thread.currentThread().getStackTrace();
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
        //lastLockingStack.remove(lastLockingStack.size() - 1);
        if (!writeLocked)
          throw new InternalError("Write unlock on an already unlocked call!");
        lastLockingStack = null;
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
      if (issuer.get(call).expired(true)) return CallStatus.STALE;
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
     * Compare the current result with another value for read prepare.  Return
     * true if they agree, otherwise false.  Unlike the general compareValue,
     * this does "reference" (onum and version) equality.
     *
     * Use .equals() if the reference equality fails in case we're dealing with
     * a surrogate or the value has changed due to a non-invalidating write...
     */
    public boolean compareValueForReadPrepare(fabric.lang.Object otherVal) {
      switch (getStatus()) {
      case NOVALUE:
        return false; // No thing can equal nothing.
      case VALID:
      case STALE:
      default:
        if (value == null) {
          // Check if they're both null
          return otherVal == null;
        } else if (otherVal instanceof WrappedJavaInlineable
            && value instanceof WrappedJavaInlineable) {
          // Check if they're both the same inlineable
          return otherVal.equals(value);
        } else if (!(otherVal instanceof WrappedJavaInlineable)
            && !(value instanceof WrappedJavaInlineable) && value != null
            && otherVal != null) {
          // Check if they're the same object
          if (value.idEquals(otherVal)) return true;

          // Check if they're equivalent objects.
          try {
            final fabric.lang.Object value1 = value;
            final fabric.lang.Object value2 = otherVal;
            Worker.runInTopLevelTransaction(new Code<Void>() {
              @Override
              public Void run() {
                boolean result = value1.equals(value2);
                if (result) {
                  SEMANTIC_WARRANTY_LOGGER.finest("Values are equal!");
                } else {
                  SEMANTIC_WARRANTY_LOGGER.finest("Values are not equal!");
                }
                throw new CompareCheckException(result);
              }
            }, false);
          } catch (AbortException e) {
            if (e.getCause() instanceof CompareCheckException) {
              return ((CompareCheckException) e.getCause()).result;
            }
          }
        }
        return false;
      }
    }

    public SemanticWarranty getWarranty() {
      switch (getStatus()) {
      case NOVALUE:
        throw new InternalError(
            "Attempt to get warranty of call with no established value!");
      case VALID:
      case STALE:
      default:
        return issuer.get(call);
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
        throw new InternalError("Attempt to get callers of a valueless call: "
            + call + "!");
      case VALID:
      case STALE:
      default:
        synchronized (callers) {
          return new HashSet<>(callers);
        }
      }
    }

    /**
     * Get the calls this call used.
     */
    @SuppressWarnings("unused")
    public Set<CallInstance> getCalls() {
      switch (getStatus()) {
      case NOVALUE:
        throw new InternalError("Attempt to get calls of a valueless call: "
            + call + "!");
      case VALID:
      case STALE:
      default:
        synchronized (calls) {
          return new HashSet<CallInstance>(calls);
        }
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
        synchronized (callers) {
          callers.add(caller);
        }
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
        synchronized (callers) {
          callers.remove(caller);
        }
      }
    }

    /**
     * Get the reads made during this call.
     */
    @SuppressWarnings("unused")
    public LongSet getReads() {
      switch (getStatus()) {
      case NOVALUE:
        throw new InternalError("Attempt to get reads from a valueless call: "
            + call + "!");
      case VALID:
      case STALE:
      default:
        synchronized (reads) {
          return new LongHashSet(reads);
        }
      }
    }

    /**
     * Get the oids created during this call and any subcalls.
     */
    public LongSet getCreates() {
      switch (getStatus()) {
      case NOVALUE:
        throw new InternalError(
            "Attempt to get creates from a valueless call: " + call + "!");
      case VALID:
      case STALE:
      default:
        LongSet createOids = null;
        Set<CallInstance> callsCopy = null;
        synchronized (creates) {
          createOids = new LongHashSet(creates);
        }
        synchronized (calls) {
          callsCopy = new HashSet<>(calls);
        }
        for (CallInstance subcall : callsCopy)
          createOids.addAll(getInfo(subcall).getCreates());
        return createOids;
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
        this.callers = new HashSet<>();
        this.calls = new HashSet<>();
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
     *
     * If there is another transaction currently making/updating this warranty,
     * then we don't make a warranty and return null.
     */
    public SemanticWarranty request(SemanticWarrantyRequest req,
        long transactionID, boolean useIssuer) {
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
            if (database.isWritten(onum)
                && !database.isWrittenBy(onum, transactionID)) {
              Logging
                  .log(
                      SEMANTIC_WARRANTY_LOGGER,
                      Level.FINEST,
                      "Request for call {0} by {2} depends on object {1} that has a write scheduled.",
                      call, onum, Long.toHexString(transactionID));
              writeUnlock();
              //throw new TransactionPrepareFailedException("Request for call "
              //+ call + " depends on object " + onum
              //+ " that has a write scheduled.");
              // We just don't make a warranty.  Don't kill the transaction yet...
              return null;
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
                Logging
                    .log(
                        SEMANTIC_WARRANTY_LOGGER,
                        Level.FINEST,
                        "Request for call {0} by {2} depends on call {1} that has a write scheduled.",
                        call, c, Long.toHexString(transactionID));
                writeUnlock();
                //We just don't make a warranty.  Don't kill the transaction yet...
                return null;
              }
            }
          }

          // Set warranty
          SemanticWarranty newWarranty = new SemanticWarranty(0);
          if (useIssuer) {
            long suggestedTime = issuer.suggestWarranty(req.call);
            newWarranty = new SemanticWarranty(suggestedTime);
          }

          if (!issuer.replace(call, DEFAULT_SEM_WARRANTY, newWarranty)) {
            Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
                "Request for call {0} by {1} denied because the warranty in the " +
                "table was not the default one!", call,
                Long.toHexString(transactionID));
            writeUnlock();
            return null;
          }

          // Schedule the update
          scheduleUpdateAt(transactionID, req);

          return newWarranty;
        } catch (UnableToLockException e) {
          Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
              "Could not lock call {0} for write, no warranty created by {1}.",
              call, Long.toHexString(transactionID));
          return null;
        }
      }
    }

    /**
     * Extends the current warranty on the call.  First checks that the value
     * provided is what we have in the table.  If this is for a read prepare,
     * use the issuer to see about making an EVEN LONGER warranty.
     */
    // TODO: Should probably have a better way to differentiate between an
    // actual write operation going on and just having two different
    // transactions extending out the warranty.
    //
    // TODO: Handle issue of extendingWarrantys on calls read during a
    // transaction that also plans to update it...?
    public SemanticExtendStatus extendWarranty(fabric.lang.Object oldValue,
        long commitTime, boolean readPrepare) {
      switch (getStatus()) {
      case STALE:
        // Check what they think it is.
        if (!compareValueForReadPrepare(oldValue)) {
          if (readPrepare) {
            long newTime = issuer.suggestWarranty(call);
            try {
              writeLock();
              issuer.put(call, new SemanticWarranty(newTime));
              writeUnlock();
              // Call is being read, so make the warranty valid anyways.
              Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
                  "Extending warranty for call {0} by {1}ms", call, newTime
                      - System.currentTimeMillis());
            } catch (UnableToLockException e) {
              // This means we shouldn't extend the warranty.  Ignore it.
            }
          }
          return SemanticExtendStatus.BAD_VERSION;
        }
        // Update the warranty
        if (getWarranty().expiresBefore(commitTime, false)) {
          long newTime = commitTime;
          if (readPrepare) {
            newTime = issuer.suggestWarranty(call, commitTime);
          }
          try {
            writeLock();
            issuer.put(call, new SemanticWarranty(newTime));
            writeUnlock();
            Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
                "Extending warranty for call {0} by {1}ms", call, newTime
                    - System.currentTimeMillis());
          } catch (UnableToLockException e) {
            // There's a pending write.  Can't!
            String msg = "DENIED EXTEND: COULD NOT LOCK STALE CALL " + call;
            //for (StackTraceElement ste : lastLockingStack)
            //msg += "\n\t" + ste;
            SEMANTIC_WARRANTY_LOGGER.finest(msg);
            return SemanticExtendStatus.DENIED;
          }
        }
        return SemanticExtendStatus.OK;
      case VALID:
        // Check what they think it is.
        if (!compareValueForReadPrepare(oldValue)) {
          return SemanticExtendStatus.BAD_VERSION;
        }
        // Update the warranty
        if (getWarranty().expiresBefore(commitTime, false)) {
          // Check that we won't be extending past the next write.
          long newTime = commitTime;
          if (readPrepare) {
            newTime = issuer.suggestWarranty(call, commitTime);
          }
          try {
            writeLock();
            issuer.put(call, new SemanticWarranty(newTime));
            writeUnlock();
            Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
                "Extending warranty for call {0} by {1}", call, commitTime
                    - System.currentTimeMillis());
          } catch (UnableToLockException e) {
            // There's a pending write.  Can't!
            String msg = "DENIED EXTEND: COULD NOT LOCK STALE CALL " + call;
            //for (StackTraceElement ste : lastLockingStack)
            //msg += "\n\t" + ste;
            SEMANTIC_WARRANTY_LOGGER.finest(msg);
            return SemanticExtendStatus.DENIED;
          }
        }
        return SemanticExtendStatus.OK;
      case NOVALUE:
      default:
        Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
            "DENIED EXTEND: CALL {0} HAS NO VALUE", call);
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
        long newTime = issuer.suggestWarranty(call);
        try {
          writeLock();
          issuer.put(call, new SemanticWarranty(newTime));
          writeUnlock();
          Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
              "Updating warranty for call {0} by {1}", call,
              newTime - System.currentTimeMillis());
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
      Set<CallInstance> expired = new HashSet<>();
      switch (getStatus()) {
      case NOVALUE:
        expired.add(call);
        Set<CallInstance> children = null;
        synchronized (calls) {
          children = new TreeSet<>(calls);
        }
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
    private void checkCallOuter(final Set<CallInstance> uncertainCalls,
        final Map<CallInstance, SemanticWarrantyRequest> updates,
        final Map<CallInstance, SemanticWarrantyRequest> changes,
        final Map<CallInstance, SemanticWarrantyRequest> newCalls,
        final ObjectLookAsideMap creates, final ObjectLookAsideMap writes) {
      try {
        Worker.runInTopLevelTransaction(new Code<Void>() {
          @Override
          public Void run() {
            checkCall(uncertainCalls, updates, changes, newCalls, creates,
                writes);
            return null;
          }
        }, true);
      } catch (AbortException e) {
        if (e.getCause() instanceof CallCheckException) {
          CallCheckException cce = (CallCheckException) e.getCause();
          updates.putAll(cce.updates);
          uncertainCalls.removeAll(cce.updates.keySet());
          changes.putAll(cce.changes);
          uncertainCalls.removeAll(cce.changes.keySet());
          newCalls.putAll(cce.newCalls);
          uncertainCalls.removeAll(cce.newCalls.keySet());
          return;
        }
        throw e;
      }
    }

    /**
     * Actual method for checking the call in a transaction (aborting and giving
     * back the new requests that would have resulted).
     */
    private void checkCall(Set<CallInstance> uncertainCalls,
        Map<CallInstance, SemanticWarrantyRequest> updates,
        Map<CallInstance, SemanticWarrantyRequest> changes,
        Map<CallInstance, SemanticWarrantyRequest> newCalls,
        ObjectLookAsideMap creates, ObjectLookAsideMap writes) {
      TransactionManager tm = TransactionManager.getInstance();
      Log current = tm.getCurrentLog();
      // Ensure that we don't reuse calls we're uncertain of or we know for a
      // fact will not be correct now.
      current.blockedWarranties.addAll(uncertainCalls);

      // Insert already checked calls to allow for faster processing.
      current.addRequests(updates);
      current.addRequests(changes);
      current.addRequests(newCalls);

      // Load up state from writes and creates
      current.setLookAsideMaps(writes, creates);

      // Load up state from previously checked calls
      for (SemanticWarrantyRequest req : SysUtil.chain(
          SysUtil.chain(updates.values(), changes.values()), newCalls.values())) {
        for (LongKeyMap<_Impl> submap : req.creates) {
          for (_Impl c : submap.values()) {
            tm.registerCreate(c);
          }
        }
      }

      // Rerun the call.
      call.runCall();

      Map<CallInstance, SemanticWarrantyRequest> newUpdates = new HashMap<>();
      Map<CallInstance, SemanticWarrantyRequest> newChanges = new HashMap<>();
      Map<CallInstance, SemanticWarrantyRequest> newNewCalls = new HashMap<>();

      Map<CallInstance, SemanticWarrantyRequest> updatedRequests =
          current.getAllRequests();
      for (CallInstance updatedCall : updatedRequests.keySet()) {
        // Skip if we added this request...
        if (newCalls.containsKey(updatedCall)
            || updates.containsKey(updatedCall)
            || changes.containsKey(updatedCall)) {
          continue;
        }

        Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
            "GOT AN UPDATE ON {0} when checking {1}", updatedCall, call);
        // If we don't have it, it's a new call.  Otherwise it's either an
        // update (same value) or a change (different value).
        if (getInfo(updatedCall).getStatus() == CallStatus.NOVALUE) {
          Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
              "{0} was new when checking {1}", updatedCall, call);
          newNewCalls.put(updatedCall, updatedRequests.get(updatedCall));
        } else {
          fabric.lang.Object newValue = updatedRequests.get(updatedCall).value;
          fabric.lang.Object oldValue = getInfo(updatedCall).getValue();
          if (!newValue.equals(oldValue)) {
            newChanges.put(updatedCall, updatedRequests.get(updatedCall));
            Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
                "{0} was changed when checking {1}", updatedCall, call);
          } else {
            newUpdates.put(updatedCall, updatedRequests.get(updatedCall));
            Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
                "{0} was unchanged when checking {1}", updatedCall, call);
          }
        }
      }
      throw new CallCheckException(newUpdates, newChanges, newNewCalls);
    }

    /**
     * Check if the call value is changed given a set of updates.  Returns true
     * if either the call is verified to be changed by the updates.
     */
    private boolean isAffectedBy(Set<CallInstance> uncertainCalls,
        Map<CallInstance, SemanticWarrantyRequest> updates,
        Map<CallInstance, SemanticWarrantyRequest> changes,
        Map<CallInstance, SemanticWarrantyRequest> newCalls,
        ObjectLookAsideMap creates, ObjectLookAsideMap writes)
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
          Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
              "CHECKING CALL {0}", call);

          if (updates.containsKey(call)) {
            SEMANTIC_WARRANTY_LOGGER
                .finest("CALL ALREADY CHECKED EARLIER AND WAS UNAFFECTED");
            return false;
          } else if (changes.containsKey(call)) {
            SEMANTIC_WARRANTY_LOGGER
                .finest("CALL ALREADY CHECKED EARLIER AND WAS AFFECTED");
            return true;
          }

          checkCallOuter(uncertainCalls, updates, changes, newCalls, creates,
              writes);

          // Answer is whether we saw a change or not
          Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
              "CALL CHECK FOR {0} FINISHED", call);
          return changes.containsKey(this.call);
        }
      } catch (Exception e) {
        Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.SEVERE,
            "CALL CHECK FOR {0} HAD PROBLEMS: {1}", call, e.getMessage());
        TransactionPrepareFailedException tpfe =
            new TransactionPrepareFailedException("Call checking for " + call
                + " ran into an " + "error! " + e.getMessage());
        tpfe.initCause(e);
        throw tpfe;
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
      Set<CallInstance> affected = new TreeSet<>();
      affected.add(call);
      switch (getStatus()) {
      case NOVALUE:
      case VALID:
      case STALE:
      default:
        Set<CallInstance> callersCopy = null;
        synchronized (callers) {
          // Go around getCallers since this is used for proposing a write time,
          // which will fail later on if this had no value...
          callersCopy = new TreeSet<>(callers);
        }
        for (CallInstance parent : callersCopy) {
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
        ObjectLookAsideMap creates, ObjectLookAsideMap writes,
        Set<CallInstance> writeLocked) throws TransactionPrepareFailedException {
      try {
        if (!writeLocked.contains(call)) {
          writeLock();
          writeLocked.add(call);
        } else {
          Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
              "{0} was already write locked before proposing a write time.",
              call);
        }
      } catch (UnableToLockException e) {
        String msg = "Could not write lock dependent call {0}";
        if (lastLockingStack != null)
          for (StackTraceElement ste : lastLockingStack)
            msg += "\n\t" + ste;
        Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST, msg, call);
        throw new TransactionPrepareFailedException("Could not write "
            + "lock dependent call " + call);
      }
      switch (getStatus()) {
      case NOVALUE:
        // This could happen if we originally saw a call was affected and the
        // update was cancelled before we got here.
        writeLocked.remove(call);
        writeUnlock();
        return longestSoFar;
      case VALID:
      case STALE:
      default:
        if (isAffectedBy(uncertainCalls, updates, changes, newCalls, creates,
            writes)) {
          long longest =
              longestSoFar > getWarranty().expiry() ? longestSoFar
                  : getWarranty().expiry();
          if (getWarranty().expiry() > longestSoFar)
            Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
                "Call {0} extending commit time to {1}", call, getWarranty()
                    .expiry());
          for (CallInstance parent : new TreeSet<>(getCallers())) {
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
        if (updates.containsKey(call) || changes.containsKey(call)) {
          throw new InternalError("Attempting to schedule a write on a "
              + "valueless call: " + call + "!");
        } else {
          return;
        }
      case STALE:
      case VALID:
      default:
        // Schedule parents only if their dependency changed.
        if (changes.containsKey(call)) {
          for (CallInstance parent : new TreeSet<>(getCallers())) {
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
        Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
            "Extending warranty for call {0} by {1} for write.", call, time
                - System.currentTimeMillis());
        issuer.put(call, new SemanticWarranty(time));
        scheduleUpdateAt(transactionID, newRequest);
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
      Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
          "Scheduling update on {0} by {1}", call,
          Long.toHexString(transactionID));
      switch (getStatus()) {
      case NOVALUE:
      case STALE:
      case VALID:
      default:
        // Defend the reads
        for (LongIterator iter = update.readOnums.iterator(); iter.hasNext();) {
          long read = iter.next();
          addReaderForOnum(read, call);
        }

        // Defend the creates
        for (LongIterator iter = update.createOnums.iterator(); iter.hasNext();) {
          long create = iter.next();
          addCreatorForOnum(create, call);
        }

        // Defend the subcalls
        for (CallInstance subcall : update.calls.keySet()) {
          getInfo(subcall).addCaller(call);
        }

        // Set the update
        nextUpdate = update;

        // Add this call to the update map for the transaction
        updatingTIDMap.putIfAbsent(transactionID, Collections
            .newSetFromMap(new ConcurrentHashMap<CallInstance, Boolean>()));
        updatingTIDMap.get(transactionID).add(call);
      }
    }

    /**
     * Cancel an update from a transaction.
     */
    public void removeUpdate() {
      Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
          "Aborting update to {0}", call);
      // Make sure there was actually an update.
      if (nextUpdate == null) return;

      // Handle reads
      for (LongIterator iter = nextUpdate.readOnums.iterator(); iter.hasNext();) {
        long read = iter.next();
        synchronized (reads) {
          if (!reads.contains(read)) removeReaderForOnum(read, call);
        }
      }

      // Handle creates
      for (LongIterator iter = nextUpdate.createOnums.iterator(); iter
          .hasNext();) {
        long create = iter.next();
        synchronized (creates) {
          if (!creates.contains(create)) removeCreatorForOnum(create, call);
        }
      }

      // Handle calls
      for (CallInstance subCall : nextUpdate.calls.keySet()) {
        synchronized (calls) {
          if (!calls.contains(subCall)) {
            getInfo(subCall).removeCaller(this.call);
          }
        }
      }

      // Remove the pending update
      nextUpdate = null;
    }

    /**
     * Complete an update from a transaction.
     */
    public void update() {
      Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
          "Completing update to {0}", call);
      accessMetrics.notifyWriteCommit(call);

      // Remove old stuff
      // Reads
      synchronized (reads) {
        for (LongIterator iter = reads.iterator(); iter.hasNext();) {
          long read = iter.next();
          if (!nextUpdate.readOnums.contains(read))
            removeReaderForOnum(read, call);
        }
      }

      // Creates
      synchronized (creates) {
        for (LongIterator iter = creates.iterator(); iter.hasNext();) {
          long create = iter.next();
          if (!nextUpdate.createOnums.contains(create))
            removeCreatorForOnum(create, call);
        }
      }

      // Calls
      synchronized (calls) {
        for (CallInstance subCall : calls)
          if (!nextUpdate.calls.containsKey(subCall))
            getInfo(subCall).removeCaller(this.call);
      }

      // Set new stuff up
      // Reads
      synchronized (reads) {
        reads.clear();
        for (LongIterator iter = nextUpdate.readOnums.iterator(); iter
            .hasNext();) {
          long read = iter.next();
          reads.add(read);
        }
      }

      // Creates
      synchronized (creates) {
        creates.clear();
        for (LongIterator iter = nextUpdate.createOnums.iterator(); iter
            .hasNext();) {
          long create = iter.next();
          creates.add(create);
        }
      }

      // Calls
      synchronized (calls) {
        calls.clear();
        for (CallInstance subCall : nextUpdate.calls.keySet()) {
          calls.add(subCall);
        }
      }

      // Set value, warranty, and status
      if (nextUpdate.value == null
          || nextUpdate.value instanceof WrappedJavaInlineable) {
        value = nextUpdate.value;
      } else {
        value = nextUpdate.value.$getProxy();
      }

      // Mark as having a value now.
      hasValue = true;

      // Reset update to nothing.
      nextUpdate = null;
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
   * Default warranty to be used for the issuer.
   */
  private final SemanticWarranty DEFAULT_SEM_WARRANTY = new SemanticWarranty(0);

  /**
   * AccessMetrics for semantic warranties.
   */
  private final AccessMetrics<CallInstance> accessMetrics;

  /**
   * AccessMetrics for semantic warranties.
   */
  private final WarrantyIssuer<CallInstance, SemanticWarranty> issuer;

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
    infoTable = new ConcurrentHashMap<>();
    readersTable = new ConcurrentLongKeyHashMap<>();
    creatorTable = new ConcurrentLongKeyHashMap<>();
    accessMetrics = new AccessMetrics<>();
    issuer = new WarrantyIssuer<>(DEFAULT_SEM_WARRANTY, accessMetrics);
    updatingTIDMap = new ConcurrentLongKeyHashMap<>();
    this.database = database;
    this.database.setSemanticWarrantyTable(this);
  }

  private void addReaderForOnum(long onum, CallInstance reader) {
    Set<CallInstance> blankSlate =
        Collections
            .newSetFromMap(new ConcurrentHashMap<CallInstance, Boolean>());
    blankSlate.add(reader);
    Set<CallInstance> readerSet = readersTable.putIfAbsent(onum, blankSlate);
    if (readerSet != null) {
      synchronized (readerSet) {
        readerSet.add(reader);
      }
    }
  }

  private void removeReaderForOnum(long onum, CallInstance reader) {
    Set<CallInstance> readerSet = readersTable.get(onum);
    if (readerSet != null) {
      synchronized (readerSet) {
        readerSet.remove(reader);
        if (readerSet.size() == 0) {
          readersTable.remove(onum, readerSet);
        }
      }
    }
  }

  private Set<CallInstance> getReadersForOnum(long onum) {
    Set<CallInstance> s = readersTable.get(onum);
    // Make sure t
    if (s != null) return Collections.unmodifiableSet(s);
    return Collections.emptySet();
  }

  private void addCreatorForOnum(long onum, CallInstance creator) {
    Set<CallInstance> blankSlate =
        Collections
            .newSetFromMap(new ConcurrentHashMap<CallInstance, Boolean>());
    blankSlate.add(creator);
    Set<CallInstance> creatorSet = creatorTable.putIfAbsent(onum, blankSlate);
    if (creatorSet != null) {
      synchronized (creatorSet) {
        creatorSet.add(creator);
      }
    }
  }

  private void removeCreatorForOnum(long onum, CallInstance creator) {
    Set<CallInstance> creatorSet = creatorTable.get(onum);
    if (creatorSet != null) {
      synchronized (creatorSet) {
        creatorSet.remove(creator);
        if (creatorSet.size() == 0) {
          creatorTable.remove(onum, creatorSet);
        }
      }
    }
  }

  private Set<CallInstance> getCreatorsForOnum(long onum) {
    Set<CallInstance> s = creatorTable.get(onum);
    // Make sure t
    if (s != null) return Collections.unmodifiableSet(s);
    return Collections.emptySet();
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
      //return new WarrantiedCallResult(info.getValue(), info.getWarranty(),
      //info.getCreates());
      return new WarrantiedCallResult(info.getValue(), info.getWarranty(),
          new LongHashSet());
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
      //return new WarrantiedCallResult(info.getValue(), info.getWarranty(),
      //info.getCreates());
      return new WarrantiedCallResult(info.getValue(), info.getWarranty(),
          new LongHashSet());
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
   * Determine a term for a new warranty for the given request and schedule it
   * to become valid/expiring on the commit of the given transaction ID.
   */
  public SemanticWarranty requestWarranty(long transactionID,
      SemanticWarrantyRequest req, boolean getNonZeroWarranty) {
    CallInfo info = getInfo(req.call);
    return info.request(req, transactionID, getNonZeroWarranty);
  }

  /**
   * Extends the warranty for an id only if it currently has a specific
   * warrantied call result.
   */
  public final Pair<SemanticExtendStatus, WarrantiedCallResult> extendForReadPrepare(
      CallInstance call, WarrantiedCallResult oldValue, long newTime, long tid) {
    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
        "Notifying read prepare on {0}", call);
    accessMetrics.notifyReadPrepare(call);
    // TODO: This is going to have false positives until I separate out things
    // that are changing value from things that are just changing dependencies.
    if (updatingTIDMap.containsKey(tid) &&
        updatingTIDMap.get(tid).contains(call)) {
      Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.SEVERE,
          "CALL {0} IS BEING READ REQUESTED BY TID {1} WHICH UPDATES IT", call,
          tid);
      return new Pair<>(SemanticExtendStatus.DENIED, get(call));
    }
    CallInfo info = getInfo(call);
    SemanticExtendStatus stat;
    try {
      stat = info.extendWarranty(oldValue.getValue(), newTime, true);
    } catch (Exception e) {
      Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.SEVERE,
          "Issue read preparing {0}: {1}", call, e);
      e.printStackTrace();
      throw e;
    }
    WarrantiedCallResult res = get(call);
    return new Pair<>(stat, res);
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
    long startTime = System.currentTimeMillis();
    TreeSet<CallInstance> affectedCalls = new TreeSet<>();
    ObjectLookAsideMap writeLookAside = new ObjectLookAsideMap();
    ObjectLookAsideMap createLookAside = new ObjectLookAsideMap();
    Store localStore = Worker.getWorker().getStore(database.getName());
    for (SerializedObject obj : writes) {
      // Don't need to worry about the set changing, since the transaction being
      // prepared has a write lock on this object.
      for (CallInstance call : getReadersForOnum(obj.getOnum())) {
        // Don't check calls we're requesting...
        if (!updatingTIDMap.containsKey(transactionID)
            || !updatingTIDMap.get(transactionID).contains(call)) {
          affectedCalls.add(call);
          Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
              "Write on {0} affects {1}", obj.getOnum(), call);
        }
      }
      // Don't need to worry about the set changing, since the transaction being
      // prepared has a write lock on this object.
      for (CallInstance call : getCreatorsForOnum(obj.getOnum())) {
        if (!updatingTIDMap.containsKey(transactionID)
            || !updatingTIDMap.get(transactionID).contains(call)) {
          affectedCalls.add(call);
          Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINEST,
              "Write on {0} affects {1}", obj.getOnum(), call);
        }
      }
      writeLookAside.put(localStore, obj, new VersionWarranty(0));
    }
    for (SerializedObject obj : creates) {
      createLookAside.put(localStore, obj, new VersionWarranty(0));
    }

    long curTime = System.currentTimeMillis();
    long longest = commitTime > curTime ? commitTime : curTime;
    Map<CallInstance, SemanticWarrantyRequest> changes = new HashMap<>();
    Map<CallInstance, SemanticWarrantyRequest> updates = new HashMap<>();
    Map<CallInstance, SemanticWarrantyRequest> newCalls = new HashMap<>();

    // Lock the calls.
    Set<CallInstance> writeLockedCalls = new HashSet<>();
    try {
      Set<CallInstance> uncertainCalls = new TreeSet<>();
      for (CallInstance call : affectedCalls) {
        for (CallInstance uncertain : getInfo(call).getAffectedSet()) {
          if (!updatingTIDMap.containsKey(transactionID)
              || !updatingTIDMap.get(transactionID).contains(uncertain)) {
            uncertainCalls.add(uncertain);
          }
        }
      }

      // Propose a time and check what will change.
      for (CallInstance call : affectedCalls) {
        long suggestedTime =
            getInfo(call).proposeWriteTime(uncertainCalls, longest, updates,
                changes, newCalls, createLookAside, writeLookAside,
                writeLockedCalls);
        longest = longest > suggestedTime ? longest : suggestedTime;
      }

      curTime = System.currentTimeMillis();
      longest = longest > curTime ? longest : curTime;

      // Make sure we can make the new warranties.  Otherwise, we need to abort
      // and retry.
      for (CallInstance call : newCalls.keySet()) {
        if (requestWarranty(transactionID, newCalls.get(call), false) == null) {
          throw new TransactionPrepareFailedException(
              "Could not create new call warranty " + call
                  + " for write update!");
        }
      }

      // Schedule the write/update
      for (CallInstance call : affectedCalls) {
        getInfo(call).scheduleWriteAt(longest, transactionID, updates, changes);
      }
    } catch (TransactionPrepareFailedException e) {
      // Remove write locks for those we actually checked and therefore did
      // writeLock.
      Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINE,
          "Transaction write prepare by {0} failed during call checking: {1}",
          Long.toHexString(transactionID), e.getMessage());
      for (CallInstance writeLockedCall : writeLockedCalls)
        getInfo(writeLockedCall).writeUnlock();
      throw e;
    }

    updates.putAll(changes);
    Logging
        .log(
            SEMANTIC_WARRANTY_LOGGER,
            Level.FINEST,
            "DONE CHECKING FOR UPDATES ON TRANSACTION {0}, SUGGESTING ADDITIONAL DELAY OF {1} after {2} ms of checking",
            Long.toHexString(transactionID), longest - commitTime,
            System.currentTimeMillis() - startTime);

    return new Pair<>(new SemanticWarranty(longest), new Pair<>(updates,
          newCalls));
  }

  /**
   * Remove any state associated with the given transactionID due to a
   * transaction abort.
   */
  public void abort(long transactionID) {
    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
        "Aborting semantic warranty updates from {0}",
        Long.toHexString(transactionID));
    Set<CallInstance> updates = updatingTIDMap.remove(transactionID);
    if (updates != null) {
      try {
        for (CallInstance call : updates) {
          getInfo(call).removeUpdate();
        }
      } finally {
        for (CallInstance call : updates) {
          getInfo(call).writeUnlock();
        }
      }
    }
  }

  /**
   * Commit any state associated with the given transactionID right now.
   */
  public void commit(long transactionID) {
    Logging.log(SEMANTIC_WARRANTY_LOGGER, Level.FINER,
        "Committing semantic warranty updates from {0}",
        Long.toHexString(transactionID));
    // Add requests made by the original transaction
    Set<CallInstance> updates = updatingTIDMap.remove(transactionID);
    if (updates != null) {
      try {
        for (CallInstance call : updates) {
          getInfo(call).update();
        }
      } finally {
        for (CallInstance call : updates) {
          getInfo(call).writeUnlock();
        }
      }
    }
  }
}
