package fabric.worker.memoize;

import java.util.Map;

import fabric.worker.AbortException;

public class CallCheckException extends AbortException {
  public final Map<CallInstance, SemanticWarrantyRequest> updatedReqs;

  public CallCheckException(Map<CallInstance, SemanticWarrantyRequest> updatedReqs) {
    super();
    this.updatedReqs = updatedReqs;
  }
}
