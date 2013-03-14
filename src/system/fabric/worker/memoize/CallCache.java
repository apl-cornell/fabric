package fabric.worker.memoize;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Cache of all the call values known to this worker along with the
 * semantic warranties for these calls.
 */
public final class CallCache {

  private Map<CallID, CallResult> callTable;

  public CallCache() {
    callTable = Collections.synchronizedMap(
        new HashMap<CallID, CallResult>());
  }

  public CallResult get(CallInstance call) {
    synchronized (callTable) {
      CallResult result = callTable.get(call.id());
      if (result == null) {
        return null;
      } else if (result.warranty.expired(true)) {
        callTable.remove(call);
        return null;
      } else {
        // XXX Note the read of the warranty in the transaction log?
        return result;
      }
    }
  }

  public void put(CallInstance call, CallResult result) {
    callTable.put(call.id(), result);
  }
}
