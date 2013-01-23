package fabric.worker.memoize;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.WeakHashMap;

/**
 * A cache for memoized method calls.
 */
public class MemoCache {

  /* The lookup table itself. */
  private static final Map<CallTuple, MemoizedCall> table =
    Collections.synchronizedMap(new HashMap<CallTuple, MemoizedCall>());

  /* Map each object to the various memo entries that depend on it */
  private static final Map<Long, Set<CallTuple>> dependentCalls =
    Collections.synchronizedMap(new HashMap<Long,Set<CallTuple>>());

  /* Currently computing calls that are to be memoized. */
  private static final Set<CallTuple> computingCalls =
    Collections.synchronizedSet(new HashSet<CallTuple>());

  /* Mapping of Threads to MemoCaches backing them. */
  private static final Map<Thread, MemoCache> threadsMap =
    Collections.synchronizedMap(new WeakHashMap<Thread, MemoCache>());

  /* Thread specific stack of memoized calls. */
  private final Stack<CallTuple> callStack;

  private MemoCache() {
    callStack = new Stack<CallTuple>();

    Thread curThread = Thread.currentThread();
    if (threadsMap.containsKey(curThread)) {
      MemoCache parent = threadsMap.get(curThread);
      if (!parent.callStack.empty())
        callStack.push(parent.callStack.peek());
    }
  }

  /**
   * Returns the stored value of the call (based on last time it was computed).
   *
   * @param key Memoized call to reuse.
   * @return Last computed value of the call or null if there is no value stored
   * for the given call.
   */
  public Object reuseCall(CallTuple key) {
    noteMemoCall(key);
    return table.get(key).getValue();
  }

  /**
   * Returns true if the call has an entry and false otherwise.
   */
  public static boolean containsCall(CallTuple key) {
    synchronized (computingCalls) {
      try {
        while (computingCalls.contains(key)) computingCalls.wait();
      } catch (InterruptedException e) {
        System.err.println("Interrupted while waiting for a computing call: " + e);
      }
    }

    return table.containsKey(key) && table.get(key).isValid();
  }

  /**
   * Invalidates a memoized call.
   *
   * Removes memoized value from table if it exists.  Removes all items in the
   * dependencies listing for this call and removes this call from each read
   * item's dependentCalls listing.
   *
   * @param key Call to be invalidated.
   */
  public synchronized static void invalidateCall(CallTuple key) {
    /* Other read items don't need to invalidate this call anymore. */
    for (long readItem : table.get(key).reads())
      dependentCalls.get(readItem).remove(key);

    if (table.containsKey(key)) {
      MemoizedCall mc = table.get(key);
      mc.invalidate();
    }
  }

  /**
   * Invalidates all calls that involved the given object.
   *
   * Do nothing if the object hasn't been part of a previous call.
   *
   * @param o The object that was updated.
   */
  public static void invalidateCallsUsing(long o) {
    if (dependentCalls.containsKey(o)) {
      Set<CallTuple> callSet = dependentCalls.get(o);
      Set<CallTuple> calls = new HashSet<CallTuple>(callSet);
      for (CallTuple c : calls)
        if (!computingCalls.contains(c))
          invalidateCall(c);
    }
  }

  /**
   * Initiate recording of a memoized call to be stored in the table.  This
   * allows us to keep track of reads that happen as the call is being computed.
   *
   * @param call The call we are beginning to compute a memoized value for.
   */
  public void beginMemoRecord(CallTuple call) {
    table.put(call, new MemoizedCall(call));
    noteMemoCall(call);
    computingCalls.add(call);
    callStack.push(call);
  }

  /**
   * Remove call from stack and pass up all read dependencies.
   *
   * @return The call that we are leaving (based on the MemoCache's call stack).
   */
  private void leaveMemoRecord(CallTuple call) {
    assert call == callStack.pop() : "Calls not consistent in a thread specific call stack!";
    synchronized (computingCalls) {
      computingCalls.remove(call);
      computingCalls.notifyAll();
    }
  }

  /**
   * End and store the currently active memoized call.  If this was called
   * during another memoized call, then add the current call's reads to the
   * parent call.
   *
   * @param val The result of the currently active memoized call.
   * @return The result of the memoized call.  This allows us to rewrite returns
   * without adding an additional statement.  It is a bit of a "hack."
   */
  public Object endMemoRecord(CallTuple call, Object val) {
    leaveMemoRecord(call);
    getMemoizedCall(call).setValue(val);
    return val;
  }

  /**
   * Abruptly end the computation of a memoized call in the event of an
   * Exception or Error.
   */
  public void abruptEndMemoRecord(CallTuple call) {
    /* XXX: Not sure if should remember reads on abrupt exit from method, but I
     * think this is the right thing to do.
     */
    leaveMemoRecord(call);
  }

  /**
   * Note that itemRead was read during the computation of the currently active
   * memoized call (if there is one being computed).  This allows us to
   * invalidate the call if this item is later written.
   *
   * @param itemRead The item that has been read.
   */
  public void noteReadDependency(long itemRead) {
    if (!callStack.empty()) {
      CallTuple curCall = callStack.peek();

      if (!dependentCalls.containsKey(itemRead))
        dependentCalls.put(itemRead, new HashSet<CallTuple>());
      dependentCalls.get(itemRead).add(curCall);

      getMemoizedCall(curCall).noteRead(itemRead);
    }
  }

  /**
   * Note that the given call was called in case it is being used while
   * computing another memoized call.  This allows us to note what objects are
   * read in order to compute the currently running memoized call.
   *
   * @param subcall The subcall that we're using a memoized value for.
   */
  public void noteMemoCall(CallTuple subcall) {
    if (!callStack.empty()) {
      CallTuple parentCall = callStack.peek();
      getMemoizedCall(parentCall).noteMemoizedSubCall(subcall);
    }
  }

  /**
   * Get the MemoizedCall object associated with the given CallTuple.
   */
  static MemoizedCall getMemoizedCall(CallTuple call) {
    return table.get(call);
  }

  /**
   * Register the given thread which will be starting at this point.
   */
  public static void registerThread(Thread t) {
    threadsMap.put(t, new MemoCache());
  }

  /**
   * Get the currently active instance of MemoCache for this thread.
   */
  public static MemoCache getInstance() {
    Thread curThread = Thread.currentThread();
    synchronized (threadsMap) {
      MemoCache mc = threadsMap.get(curThread);
      if (mc == null) {
        mc = new MemoCache();
        threadsMap.put(curThread, mc);
      }
      return mc;
    }
  }
}
