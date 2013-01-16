package fabric.worker.memoize;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a memoized call along with all of the associated book keeping.
 */
public class MemoizedCall {

  /* Call Tuple for this call. */
  public final CallTuple call;

  /* Value of the call. */
  private Object value;

  /* Is the memoized value valid? */
  private boolean valid;

  /* Objects read last time this call was computed. */
  private final Set<Long> itemsRead;

  /* MemoizedCalls which were used as subcalls last time this was computed. */
  private final Set<CallTuple> subCalls;

  /* MemoizedCalls which have this call as a subcall. */
  private final Set<CallTuple> parentCalls;

  /**
   * Create a new MemoizedCall.
   */
  public MemoizedCall(CallTuple call) {
    this.call = call;
    this.value = null;
    this.valid = false;
    this.itemsRead = new HashSet<Long>();
    this.subCalls = new HashSet<CallTuple>();
    this.parentCalls = new HashSet<CallTuple>();
  }

  /**
   * Get the value that is memoized for this call.  If the call doesn't have a
   * valid value stored it either waits for the currently recomputing value or
   * it starts a recomputation itself.
   */
  public synchronized Object getValue() {
    return value;
  }

  public synchronized boolean isValid() {
    return valid;
  }

  /**
   * Set the value of this computation to val.  This should only be used by the
   * Runnable for recomputing the call value.
   */
  public synchronized void setValue(Object val) {
    value = val;
    valid = true;
  }

  /**
   * Invalidate this call's value.
   */
  public synchronized void invalidate() {
    valid = false;
    itemsRead.clear();
    for (CallTuple child : new HashSet<CallTuple>(subCalls))
      MemoCache.getMemoizedCall(child).removeParent(call);
    subCalls.clear();
    for (CallTuple parent : new HashSet<CallTuple>(parentCalls))
      MemoCache.invalidateCall(parent);
  }

  /**
   * Return the itemsRead during the last computation of this call.  If the call
   * is currently computing, return an empty Set.
   */
  public synchronized Set<Long> reads() {
    return (!valid) ? new HashSet<Long>() : new HashSet<Long>(itemsRead);
  }

  /**
   * Add a new read dependency for this call.  The call should be currently
   * computing if this is called.
   */
  public synchronized void noteRead(long item) {
    itemsRead.add(item);
  }

  /**
   * Add a new memoized subcall dependency for this call.  The call should be
   * currently computing if this is called.
   */
  public synchronized void noteMemoizedSubCall(CallTuple subCall) {
    subCalls.add(subCall);
    MemoCache.getMemoizedCall(subCall).addParent(this.call);
  }
  
  /**
   * Remove a parent call for this memoized call.
   */
  public synchronized void removeParent(CallTuple parentCall) {
    parentCalls.remove(parentCall);
  }

  /**
   * Add a parent call for this memoized call.
   */
  public synchronized void addParent(CallTuple parentCall) {
    parentCalls.add(parentCall);
  }
}
