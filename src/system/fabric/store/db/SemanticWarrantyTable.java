package fabric.store.db;

import static fabric.common.Logging.SEMANTIC_WARRANTY_LOGGER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

import fabric.common.SemanticWarranty;
import fabric.common.SerializedObject;
import fabric.common.Threading;
import fabric.common.Warranty;
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
   * Status options for the result of an extend operation
   */
  public static enum SemanticExtendStatus {
    OK, BAD_VERSION, DENIED
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
      lastLockingStack.add(Thread.currentThread().getStackTrace());
    }

    public void unlock() {
      lastLockingStack.remove(lastLockingStack.size() - 1);
      lock.unlock();
    }

    /**
     * The call this info is for.
     */
    public final CallInstance call;

    /**
     * The call's status.
     */
    private CallStatus status = CallStatus.EXPIRED;

    /**
     * Get the call's status.
     */
    public CallStatus getStatus() {
      lock();
      try {
        switch (status) {
        case EXPIRED:
          return CallStatus.EXPIRED;
        case VALID:
          if (warranty.expired(true)) setStatus(CallStatus.STALE);
          return CallStatus.STALE;
        case EXPIRING:
          if (warranty.expired(true)) setStatus(CallStatus.EXPIRED);
          return CallStatus.EXPIRED;
        case VALIDPENDING:
        case VALIDUPDATING:
        case EXPIRINGPENDING:
        case EXPIRINGUPDATING:
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
        case EXPIRED:
          // Set status to EXPIRED
          status = newStatus;

          // Remove value, warranty is "implicitly" removed when it expires.
          setValue(null);

          // Move creates and reads
          for (CallInstance caller : callers) {
            for (LongIterator iter = creates.iterator(); iter.hasNext();) {
              getInfo(caller).addCreate(iter.next());
            }
            for (LongIterator iter = reads.iterator(); iter.hasNext();) {
              getInfo(caller).addRead(iter.next());
            }
          }

          // Remove this call from creators/readers tables
          for (LongIterator iter = creates.iterator(); iter.hasNext();) {
            creatorTable.get(iter.next()).remove(this.call);
          }
          for (LongIterator iter = reads.iterator(); iter.hasNext();) {
            readersTable.get(iter.next()).remove(this.call);
          }
          // Leave call graph intact for getting the expired call graph later.
          Set<CallInstance> parents = new HashSet<CallInstance>(callers);
          // Clear callers, don't need them anymore.  Should be updated in the
          // future if this call is re-requested.
          callers.removeAll(callers);
          Set<CallInstance> children = new HashSet<CallInstance>(calls);

          unlock();
          try {
            // TODO: Will this ever result in a child being propogated up after
            // becoming EXPIRED?
            for (CallInstance child : children) {
              getInfo(child).removeCaller(this.call);
              for (CallInstance parent : parents) {
                getInfo(parent).removeCall(this.call);
                getInfo(parent).addCall(child);
                getInfo(child).addCaller(parent);
              }
            }
            break;
          } finally {
            // Re lock so locks match unlocks on outer finally block.
            lock();
          }
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
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
        case EXPIRED:
          throw new InternalError("Attempt to read value of an expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
        default:
          return value;
        }
      } finally {
        unlock();
      }
    }

    /**
     * Set the call's value.
     */
    private void setValue(fabric.lang.Object v) {
      lock();
      try {
        switch (getStatus()) {
        case EXPIRED:
          value = v;
          break;
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
        default:
          throw new InternalError(
              "Attempt to change value of a non-expired call!");
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
        case EXPIRED:
          throw new InternalError(
              "Attempt to compare value of an expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
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
        case EXPIRED:
          throw new InternalError("Attempt to get warranty of expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
        default:
          return warranty;
        }
      } finally {
        unlock();
      }
    }

    /**
     * Time of the next write scheduled "on" this call.
     *
     * Defaults to MAX_VALUE if there isn't a known upcoming write.
     */
    private long nextWrite = Long.MAX_VALUE;

    /**
     * Get the time of the next write.
     */
    public long getNextWrite() {
      lock();
      try {
        switch (getStatus()) {
        case EXPIRED:
          throw new InternalError("Attempt to get next write of expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
        default:
          return nextWrite;
        }
      } finally {
        unlock();
      }
    }

    /**
     * Set the time of the next write.  If the time given is later than the
     * already existing time, then there is no update.
     */
    public void setNextWrite(long newTime) {
      lock();
      try {
        switch (getStatus()) {
        case EXPIRED:
          nextWrite = newTime;
          break;
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
          if (getWarranty().expiresAfter(newTime, true))
            throw new InternalError("Attempt to set write time to before "
                + "current warranty expiration!");
          // $FALL-THROUGH$
        case STALE:
        default:
          if (newTime < nextWrite) nextWrite = newTime;
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
        case EXPIRED:
          throw new InternalError("Attempt to get callers of an expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
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
    public Set<CallInstance> getCalls() {
      lock();
      try {
        switch (getStatus()) {
        case EXPIRED:
          throw new InternalError("Attempt to get calls of an expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
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
        case EXPIRED:
          throw new InternalError("Attempt to add a caller to an expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
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
        case EXPIRED:
          throw new InternalError(
              "Attempt to remove a caller from an expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
        default:
          callers.remove(caller);
        }
      } finally {
        unlock();
      }
    }

    /**
     * Add a new call.
     */
    private void addCall(CallInstance callee) {
      lock();
      try {
        switch (getStatus()) {
        case EXPIRED:
          throw new InternalError("Attempt to add a call to an expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
        default:
          calls.add(callee);
        }
      } finally {
        unlock();
      }
    }

    /**
     * Remove a call.
     */
    private void removeCall(CallInstance callee) {
      lock();
      try {
        switch (getStatus()) {
        case EXPIRED:
          throw new InternalError(
              "Attempt to remove a call from an expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
        default:
          calls.remove(callee);
        }
      } finally {
        unlock();
      }
    }

    /**
     * Get the reads made during this call.
     */
    public LongSet getReads() {
      lock();
      try {
        switch (getStatus()) {
        case EXPIRED:
          throw new InternalError("Attempt to get reads from an expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
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
        case EXPIRED:
          throw new InternalError(
              "Attempt to get creates from an expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
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
        case EXPIRED:
          throw new InternalError("Attempt to add a read to an expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
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
    private void addCreate(long onum) {
      lock();
      try {
        switch (getStatus()) {
        case EXPIRED:
          throw new InternalError("Attempt to add a create to an expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
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

    public void request(SemanticWarrantyRequest req, long transactionID) {
      this.lock();
      try {
        switch (getStatus()) {
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
          throw new InternalError(
              "Attempt to request a new warranty for a non-expired call!");
        case EXPIRED:
        default:
          this.value = req.value;

          //Set nextWrite.
          this.nextWrite = Long.MAX_VALUE;
          for (LongIterator iter = req.readOnums.iterator(); iter.hasNext();) {
            long onum = iter.next();
            if (database.isWritten(onum)) {
              // This works because the warranty will have been extended to when the
              // write is scheduled for.
              Warranty objWar = database.getWarranty(onum);
              if (objWar.expired(false)) continue;
              this.nextWrite =
                  objWar.expiry() < this.nextWrite ? objWar.expiry()
                      : this.nextWrite;
            }
          }
          for (CallInstance c : req.calls.keySet()) {
            long callTime = nextScheduledWrite(c);
            this.nextWrite =
                callTime < this.nextWrite ? callTime : this.nextWrite;
          }

          // Set status
          if (this.nextWrite == Long.MAX_VALUE) {
            setStatus(CallStatus.VALIDPENDING);
          } else {
            setStatus(CallStatus.EXPIRINGPENDING);
          }

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

          // Set warranty
          long suggestedTime = issuer.suggestWarranty(req.call);
          if (suggestedTime < nextWrite) {
            this.warranty = new SemanticWarranty(suggestedTime);
          } else {
            this.warranty = new SemanticWarranty(nextWrite);
          }

          // Update links to this call
          for (CallInstance call : this.calls) {
            getInfo(call).addCaller(this.call);
          }

          pendingTIDMap.putIfAbsent(transactionID, new HashSet<CallInstance>());
          pendingTIDMap.get(transactionID).add(this.call);
        }
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
        case VALIDPENDING:
          setStatus(CallStatus.VALID);
          break;
        case EXPIRINGPENDING:
          setStatus(CallStatus.EXPIRING);
          break;
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRING:
        case EXPIRINGUPDATING:
        case EXPIRED:
        default:
          throw new InternalError("Attempting to activate a non-pending call!");
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
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
          // Check what they think it is.
          if (!compareValue(oldValue)) return SemanticExtendStatus.BAD_VERSION;
          // Is the warranty already long enough?
          if (warranty.expiresAfter(newTime, false))
            return SemanticExtendStatus.OK;
          if (getNextWrite() > newTime) {
            warranty = new SemanticWarranty(newTime);
            return SemanticExtendStatus.OK;
          }
          return SemanticExtendStatus.DENIED;
        case STALE:
          // Check what they think it is.
          if (!compareValue(oldValue)) return SemanticExtendStatus.BAD_VERSION;
          // Update state back to valid.
          setStatus(CallStatus.VALID);
          // Update the warranty
          if (warranty.expiresBefore(newTime, true))
            warranty = new SemanticWarranty(newTime);
          return SemanticExtendStatus.OK;
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
          // Check what they think it is.
          if (!compareValue(oldValue)) return SemanticExtendStatus.BAD_VERSION;
          // Update the warranty
          if (warranty.expiresBefore(newTime, true))
            warranty = new SemanticWarranty(newTime);
          // XXX: Not actually sure about this one for UPDATING.
          return SemanticExtendStatus.OK;
        case EXPIRED:
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
    public Set<CallInstance> getExpiredCalls() {
      Set<CallInstance> expired = new HashSet<CallInstance>();
      lock();
      try {
        switch (getStatus()) {
        case EXPIRED:
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
        case VALIDPENDING:
        case EXPIRINGPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRING:
        case EXPIRINGUPDATING:
        default:
          return expired;
        }
      } finally {
        unlock();
      }
    }

    /**
     * Determine the next time this call can be updated.
     *
     * This write locks everything in the call graph above this call, it is
     * unlocked in the sister method scheduleWrite.
     * 
     * Sort of a kludge: this method is handed a set of calls it already owns a lock on.  This method should not re-acquire a lock that it has from a previous run of this method.
     */
    // XXX: Ugh, if the sister method does not run, we're in trouble.
    public long proposeWriteTime(Set<CallInstance> locksOwned) {
      lock();
      if (locksOwned.contains(call))
        unlock();
      else locksOwned.add(call);
      try {
        switch (getStatus()) {
        case EXPIRED:
          throw new InternalError(
              "Attempting to propose a write time for an expired call!");
        case VALIDPENDING:
        case VALID:
        case VALIDUPDATING:
        case STALE:
        case EXPIRINGPENDING:
        case EXPIRING:
        case EXPIRINGUPDATING:
        default:
          long longest = warranty.expiry();
          for (CallInstance parent : new TreeSet<CallInstance>(getCallers())) {
            long parentTime = getInfo(parent).proposeWriteTime(locksOwned);
            longest = parentTime > longest ? parentTime : longest;
          }
          return longest;
        }
      } catch (InternalError e) {
        locksOwned.remove(call);
        unlock();
        throw e;
      }
    }

    /**
     * Schedule the next write for this call.
     *
     * This should only be done after calling proposeWriteTime and with a time
     * greater than what proposeWriteTime returned.
     * 
     * This code assumes the call is already locked by the current thread (along with any parent calls that this will traverse).
     */
    public void scheduleWriteAt(long time) {
      switch (getStatus()) {
      case EXPIRED:
        throw new InternalError(
            "Attempting to schedule a write on an expired call!");
      case STALE:
        // Need to do this in a specific order since this expires the call.
        for (CallInstance parent : new TreeSet<CallInstance>(getCallers())) {
          getInfo(parent).scheduleWriteAt(time);
        }
        setNextWrite(time);
        setStatus(CallStatus.EXPIRED);
        break;
      case VALIDPENDING:
        setStatus(CallStatus.EXPIRINGPENDING);
        for (CallInstance parent : new TreeSet<CallInstance>(getCallers())) {
          getInfo(parent).scheduleWriteAt(time);
        }
        setNextWrite(time);
        break;
      case VALID:
        setStatus(CallStatus.EXPIRING);
        for (CallInstance parent : new TreeSet<CallInstance>(getCallers())) {
          getInfo(parent).scheduleWriteAt(time);
        }
        setNextWrite(time);
        break;
      case VALIDUPDATING:
        setStatus(CallStatus.EXPIRINGUPDATING);
        for (CallInstance parent : new TreeSet<CallInstance>(getCallers())) {
          getInfo(parent).scheduleWriteAt(time);
        }
        setNextWrite(time);
        break;
      case EXPIRINGPENDING:
      case EXPIRING:
      case EXPIRINGUPDATING:
      default:
        for (CallInstance parent : new TreeSet<CallInstance>(getCallers())) {
          getInfo(parent).scheduleWriteAt(time);
        }
        setNextWrite(time);
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
   * Local reference to the ObjectDB for items on this store.
   */
  private final ObjectDB database;

  public SemanticWarrantyTable(ObjectDB database) {
    infoTable = new ConcurrentHashMap<CallInstance, CallInfo>();
    readersTable = new ConcurrentLongKeyHashMap<Set<CallInstance>>();
    creatorTable = new ConcurrentLongKeyHashMap<Set<CallInstance>>();
    issuer =
        new WarrantyIssuer<CallInstance>(MIN_SEMANTIC_WARRANTY,
            MAX_SEMANTIC_WARRANTY);
    pendingTIDMap = new ConcurrentLongKeyHashMap<Set<CallInstance>>();
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
      case EXPIRED:
      case VALIDPENDING:
      case EXPIRINGPENDING:
        return null;
      case VALID:
      case VALIDUPDATING:
      case STALE:
      case EXPIRING:
      case EXPIRINGUPDATING:
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
    if (info.getStatus() == CallStatus.EXPIRED) return new LongHashSet();
    return info.getCreates();
  }

  /**
   * The next write scheduled for any of the dependencies of this call.
   *
   * Returns MAX_VALUE if there is no scheduled write.
   */
  private long nextScheduledWrite(CallInstance call) {
    return getInfo(call).getNextWrite();
  }

  /**
   * Determine a term for a new warranty for the given request and schedule it
   * to become valid/expiring on the commit of the given transaction ID.
   */
  public SemanticWarranty requestWarranty(long transactionID,
      SemanticWarrantyRequest req) {
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
  // TODO: Check if we can allow the write before the term expiration.
  public Pair<SemanticWarranty, Collection<SerializedObject>> prepareWrites(
      final Collection<SerializedObject> writes,
      final Collection<SerializedObject> creates, final long transactionID,
      long commitTime, final String storeName) {
    // FIXME: Pretty sure we're not actually being safe about lock order here...
    // need a way to ensure we don't accidentally operate on an ancestor of one
    // of the other calls first...
    long longest = 0l;
    TreeSet<CallInstance> affectedCalls = new TreeSet<CallInstance>();
    TreeSet<CallInstance> lockedCalls = new TreeSet<CallInstance>();
    for (SerializedObject obj : writes) {
      readersTable.putIfAbsent(obj.getOnum(), new HashSet<CallInstance>());
      affectedCalls.addAll(readersTable.get(obj.getOnum()));
      creatorTable.putIfAbsent(obj.getOnum(), new HashSet<CallInstance>());
      affectedCalls.addAll(creatorTable.get(obj.getOnum()));
    }

    for (CallInstance call : affectedCalls) {
      // XXX: Ugh, need to ensure we release locks acquired in below call are
      // released on exception...
      long suggestedTime = getInfo(call).proposeWriteTime(lockedCalls);
      longest = longest > suggestedTime ? longest : suggestedTime;
    }
    for (CallInstance call : affectedCalls) {
      getInfo(call).scheduleWriteAt(longest);
    }
    // Unlock calls locked during proposal traversal
    for (CallInstance call : lockedCalls) {
      getInfo(call).unlock();
    }

    return new Pair<SemanticWarranty, Collection<SerializedObject>>(
        new SemanticWarranty(longest), new HashSet<SerializedObject>());
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
        getInfo(call).setStatus(CallStatus.EXPIRED);
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
  }
}
