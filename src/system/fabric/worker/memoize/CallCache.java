package fabric.worker.memoize;

import fabric.common.SemanticWarranty;
import fabric.common.util.Pair;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Cache of all the call values known to this worker along with the
 * semantic warranties for these calls.
 */
public final class CallCache {

  private Map<CallInstance, Pair<Object, SemanticWarranty>> callTable;

  public CallCache() {
    callTable = Collections.synchronizedMap(
        new HashMap<CallInstance, Pair<Object, SemanticWarranty>>());
  }

  public Pair<Object, SemanticWarranty> get(CallInstance call) {
    synchronized (callTable) {
      Pair<Object, SemanticWarranty> result = callTable.get(call);
      if (result == null) {
        return null;
      } else if (result.second.expired(true)) {
        callTable.remove(call);
        return null;
      } else {
        // XXX Note the read of the warranty in the transaction log?
        return result;
      }
    }
  }

  public void put(CallInstance call, Object value, SemanticWarranty warranty) {
    callTable.put(call, new Pair<Object, SemanticWarranty>(value, warranty));
  }
}
