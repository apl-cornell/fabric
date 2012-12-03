package fabric.worker.memoize;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import fabric.common.util.OidKeyHashMap;

/**
 * A cache for memoized method calls.
 */
public class MemoCache {

  /* The lookup table itself. */
  private final Map<CallTuple, Object> table;

  /* Map each object to the various memo entries that depend on it */
  private final OidKeyHashMap<Set<CallTuple>> dependentCalls;

  /* Map each call to the objects that it read when computed. */
  private final Map<CallTuple, Set<fabric.lang.Object>> dependencies;

  /* Map each call to the memoized subcalls it used. */
  private final Map<CallTuple, Set<CallTuple>> subCalls;

  /* Map each call to the memoized calls that used this as a subcall. */
  private final Map<CallTuple, Set<CallTuple>> parentCalls;

  /* Stack of currently computing calls that are to be memoized. */
  private final Stack<CallTuple> callStack;

  /* Map each call tuple to a Runnable for recomputing and storing the value of
   * the call.
   */
  private final Map<CallTuple, Runnable> computations;

  public MemoCache() {
    table = new HashMap<CallTuple, Object>();
    dependentCalls = new OidKeyHashMap<Set<CallTuple>>();
    dependencies = new HashMap<CallTuple, Set<fabric.lang.Object>>();
    subCalls = new HashMap<CallTuple, Set<CallTuple>>();
    parentCalls = new HashMap<CallTuple, Set<CallTuple>>();
    callStack = new Stack<CallTuple>();
    computations = new HashMap<CallTuple, Runnable>();
  }

  /**
   * Returns the stored value of the call (based on last time it was computed).
   *
   * @param key Memoized call to reuse.
   * @return Last computed value of the call or null if there is no value stored
   * for the given call.
   */
  public synchronized Object reuseCall(CallTuple key) {
    noteMemoSubcall(key);
    return table.get(key);
  }

  /**
   * Returns true if the call has an entry and false otherwise.
   */
  public synchronized boolean containsCall(CallTuple key) {
    return table.containsKey(key);
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
  public synchronized void invalidateCall(CallTuple key) {
    if (dependencies.containsKey(key)) {
      /* Other read items don't need to invalidate this call anymore. */
      for (fabric.lang.Object readItem : dependencies.get(key)) {
        dependentCalls.get(readItem).remove(key);
      }
      /* Clear out items read using this call. */
      dependencies.get(key).clear();
    }

    if (subCalls.containsKey(key)) {
      /* Forget subCalls used in the computation. */
      subCalls.get(key).clear();
    }

    if (table.containsKey(key)) {
      ///* Remove the memoized value */
      //table.remove(key);
      /* Recompute the memoized value */
      recomputeCall(key);
    }
  }

  /**
   * Invalidates all calls that involved the given object.
   *
   * Do nothing if the object hasn't been part of a previous call.
   *
   * @param o The object that was updated.
   */
  public synchronized void invalidateCallsUsing(fabric.lang.Object o) {
    if (dependentCalls.containsKey(o)) {
      Set<CallTuple> callSet = dependentCalls.get(o);
      Set<CallTuple> calls = new HashSet<CallTuple>(callSet);
      for (CallTuple c : calls) {
        /* Don't invalidate if we're computing it! */
        /* TODO: Is that right? */
        if (callStack.search(c) == -1) {
          invalidateCall(c);
        }
      }
    }
  }

  /**
   * Initiate recording of a memoized call to be stored in the table.  This
   * allows us to keep track of reads that happen as the call is being computed.
   *
   * @param call The call we are beginning to compute a memoized value for.
   */
  public synchronized void beginMemoRecord(CallTuple call) {
    if (!dependencies.containsKey(call)) {
      dependencies.put(call, new HashSet<fabric.lang.Object>());
    } else {
      dependencies.get(call).clear();
    }
    callStack.push(call);
  }

  /**
   * Remove call from stack and pass up all read dependencies.
   *
   * @return The call that we are leaving (based on the MemoCache's call stack).
   */
  private synchronized CallTuple leaveMemoRecord() {
    CallTuple call = callStack.pop();
    noteMemoSubcall(call);
    return call;
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
  public synchronized Object endMemoRecord(Object val) {
    CallTuple call = leaveMemoRecord();
    table.put(call, val);
    return val;
  }

  /**
   * Abruptly end the computation of a memoized call in the event of an
   * Exception or Error.
   */
  public synchronized void abruptEndMemoRecord() {
    /* XXX: Not sure if should remember reads on abrupt exit from method, but I
     * think this is the right thing to do.
     */
    leaveMemoRecord();
  }

  /**
   * Note that itemRead was read during the computation of the currently active
   * memoized call (if there is one being computed).  This allows us to
   * invalidate the call if this item is later written.
   *
   * @param itemRead The item that has been read.
   */
  public synchronized void noteReadDependency(fabric.lang.Object itemRead) {
    if (!callStack.empty()) {
      dependencies.get(callStack.peek()).add(itemRead);

      if (!dependentCalls.containsKey(itemRead)) {
        dependentCalls.put(itemRead, new HashSet<CallTuple>());
      }
      dependentCalls.get(itemRead).add(callStack.peek());
    }
  }

  /**
   * Note that the given call was called in case it is being used while
   * computing another memoized call.  This allows us to note what objects are
   * read in order to compute the currently running memoized call.
   *
   * @param subcall The subcall that we're using a memoized value for.
   */
  public synchronized void noteMemoSubcall(CallTuple subcall) {
    if (!callStack.empty()) {
      CallTuple parentCall = callStack.peek();

      /* XXX For now, we don't try the "bubble up recomputations" bit, instead
       * just propogate up dependencies and handle each
       * invalidation/recomputation independently.  This will change later but
       * might be worth comparing these two approaches.
       */
      dependencies.get(parentCall).addAll(dependencies.get(subcall));

      /* Link this and the parent call */
      if (!parentCalls.containsKey(subcall)) {
        parentCalls.put(subcall, new HashSet<CallTuple>());
      }
      parentCalls.get(subcall).add(parentCall);

      if (!subCalls.containsKey(parentCall)) {
        subCalls.put(parentCall, new HashSet<CallTuple>());
      }
      subCalls.get(parentCall).add(subcall);
    }
  }

  public void storeComputation(CallTuple call, Runnable computation) {
    computations.put(call, computation);
  }

  public void recomputeCall(CallTuple call) {
    if (computations.containsKey(call)) {
      computations.get(call).run();
    }
  }
}
