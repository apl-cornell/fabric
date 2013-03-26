package fabric.worker.memoize;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* TODO:
 *      - Maybe use a concurrent map rather than a synchronized map?
 */
/**
 * Represents a Cache of all the call values known to this worker along with the
 * semantic warranties for these calls.
 */
public final class CallCache {

  private Map<CallInstance, WarrantiedCallResult> callTable;

  public CallCache() {
    callTable = Collections.synchronizedMap(
        new HashMap<CallInstance, WarrantiedCallResult>());
  }

  public WarrantiedCallResult get(CallInstance call) {
    return callTable.get(call);
  }

  public void put(CallInstance call, WarrantiedCallResult result) {
    if (result.warranty == null) {
      throw new InternalError("Tried to add a call with no warranty!");
    }
    callTable.put(call, result);
  }

  public void remove(CallInstance call) {
    callTable.remove(call);
  }
}
