package fabric.worker.memoize;

import java.util.Map;
import java.util.Set;

import fabric.common.util.Pair;
import fabric.worker.AbortException;

public class CallCheckException extends AbortException {
  public final Pair<Map<CallInstance, SemanticWarrantyRequest>, Set<CallInstance>> updates;

  public CallCheckException(Map<CallInstance, SemanticWarrantyRequest> unchanged, Set<CallInstance> changed) {
    super();
    this.updates = new Pair<>(unchanged, changed);
  }
}
