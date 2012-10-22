package fabric.worker.memoize;

import java.util.*;

/**
 * A cache for memoized method calls.
 */
public class MemoCache {

  /* The lookup table itself. */
  private Map<CallTuple, Object> table;

  public MemoCache() {
    table = new HashMap<CallTuple, Object>();
  }

  /**
   * Gets the value of the given call.
   */
  public Object getCall(CallTuple key) {
    return table.get(key);
  }

  /**
   * Returns true if the call has an entry and false otherwise.
   */
  public boolean containsCall(CallTuple key) {
    return table.containsKey(key);
  }

  /**
   * Adds or updates a given call with the given return value.
   */
  public Object setCall(CallTuple key, Object value) {
    invalidateCall(key);
    table.put(key, value);
    return value;
  }

  /**
   * Invalidates a memoized call.
   */
  public void invalidateCall(CallTuple key) {
    Object oldResult = getCall(key);
    table.remove(key);
    /* For now I'm going to do the really slow way of handling this.  Manually
     * find all items that use the current value for this call and remove them.
     */
    for (CallTuple c : table.keySet()) {
      if (c.getCallee().equals(oldResult) || c.getArgs().contains(oldResult)) {
        invalidateCall(c);
      }
    }
  }
}
