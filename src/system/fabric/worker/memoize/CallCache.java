package fabric.worker.memoize;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Represents a Cache of all the call values known to this worker along with the
 * semantic warranties for these calls.
 */
public final class CallCache {

  private ConcurrentMap<CallInstance, WarrantiedCallResult> callTable;

  public CallCache() {
    callTable = new ConcurrentHashMap<>();
  }

  public WarrantiedCallResult get(CallInstance call) {
    return callTable.get(call);
  }

  public void put(CallInstance call, WarrantiedCallResult result) {
    if (result.getWarranty() == null) {
      throw new InternalError("Tried to add a call with no warranty!");
    }
    callTable.put(call, result);
  }

  public void remove(CallInstance call) {
    callTable.remove(call);
  }
}
