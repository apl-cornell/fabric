package fabric.worker.memoize;

import java.util.Map;
import java.util.Set;

import fabric.common.util.Pair;
import fabric.worker.AbortException;

public class CallCheckException extends AbortException {
  public final Pair<Map<CallInstance, ComputationWarrantyRequest>, Set<CallInstance>> updates;

  public CallCheckException(Map<CallInstance, ComputationWarrantyRequest> unchanged, Set<CallInstance> changed) {
    super();
    this.updates = new Pair<>(unchanged, changed);
  }
}
