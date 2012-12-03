package fabric.worker.memoize;

import java.util.HashSet;
import java.util.Set;
import fabric.worker.transaction.TransactionManager;

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

  /* Is this call currently being computed? */
  private boolean currentlyComputing;

  /* Objects read last time this call was computed. */
  // TODO: Should this just be _Impl?
  private final Set<fabric.lang.Object> itemsRead;

  /* MemoizedCalls which were used as subcalls last time this was computed. */
  private final Set<CallTuple> subCalls;

  /* MemoizedCalls which have this call as a subcall. */
  private final Set<CallTuple> parentCalls;

  /* Runnable used for recomputing the value of this call. */
  private Runnable computation;

  /* The MemoCache that this MemoizedCall belongs to. */
  // TODO: Maybe this should be an inner class of MemoCache instead.
  private MemoCache memo;

  /**
   * Create a new MemoizedCall.
   */
  public MemoizedCall(CallTuple call, Runnable computation, MemoCache memo) {
    this.call = call;
    this.value = null;
    this.valid = false;
    this.currentlyComputing = false;
    this.itemsRead = new HashSet<fabric.lang.Object>();
    this.subCalls = new HashSet<CallTuple>();
    this.parentCalls = new HashSet<CallTuple>();
    this.computation = computation;
    this.memo = memo;
  }

  /**
   * (Re)computes the value of this call and wakes up all threads waiting on
   * this value once it is done.  If there's another thread computing the call,
   * then this returns immediately.
   */
  public void compute() {
    synchronized (this) {
      try {
        while (currentlyComputing) {
          wait();
        }
      } catch (InterruptedException e) {
        System.err.println("Interrupted while waiting for computation: " + e);
      }
      if (valid)
        return;
      currentlyComputing = true;
    }
    /* TODO: Will it ever be the case that it's still not valid after the
     * computation is run just once?
     *
     * Should this be synchronized...?
     */
    computation.run();

    synchronized (this) {
      if (!valid)
        throw new RuntimeException("Computation still valid after recomputing!");
      currentlyComputing = false;
      notifyAll();
    }
  }

  /**
   * Get the value that is memoized for this call.  If the call doesn't have a
   * valid value stored it either waits for the currently recomputing value or
   * it starts a recomputation itself.
   */
  public Object getValue() {
    compute();
    return value;
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
    synchronized (this) {
      valid = false;
      itemsRead.clear();
      for (CallTuple child : subCalls)
        memo.getMemoizedCall(child).removeParent(call);
      subCalls.clear();
      for (CallTuple parent : parentCalls)
        memo.invalidateCall(parent);
    }
    // TODO: Find a way to do recomputation that doesn't blow up when a
    // transaction is running.
  }

  /**
   * Return the itemsRead during the last computation of this call.  If the call
   * is currently computing, return an empty Set.
   */
  public synchronized Set<fabric.lang.Object> reads() {
    if (currentlyComputing || !valid)
      return new HashSet<fabric.lang.Object>();
    return new HashSet<fabric.lang.Object>(itemsRead);
  }

  /**
   * Add a new read dependency for this call.  The call should be currently
   * computing if this is called.
   */
  public synchronized void noteRead(fabric.lang.Object item) {
    if (!currentlyComputing)
      throw new RuntimeException("Adding a read dependency to a call that is not in the middle of computing: " + call);
    itemsRead.add(item);
  }

  /**
   * Add a new memoized subcall dependency for this call.  The call should be
   * currently computing if this is called.
   */
  public synchronized void noteMemoizedSubCall(CallTuple subCall) {
    subCalls.add(subCall);
    memo.getMemoizedCall(subCall).addParent(this.call);
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

  /**
   * Thread class for recomputing calls concurrently.
   */
  private static class Recomputation implements Runnable {
    final MemoizedCall call;

    public Recomputation(MemoizedCall call) {
      this.call = call;
    }

    @Override
    public void run() {
      //System.out.println("Running recomputation thread");
      this.call.compute();
    }
  }
}
