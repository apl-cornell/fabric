package fabric.worker.memoize;

import fabric.worker.AbortException;

public class CallUnchangedException extends AbortException {
  public final SemanticWarrantyRequest updatedReq;

  public CallUnchangedException(SemanticWarrantyRequest updatedReq) {
    super();
    this.updatedReq = updatedReq;
  }
}
