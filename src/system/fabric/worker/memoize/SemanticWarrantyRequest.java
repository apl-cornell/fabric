package fabric.worker.memoize;

import fabric.common.util.LongSet;
import fabric.worker.Store;

/**
 * Represents all the data needed for a request for a SemanticWarranty.  Should
 * be constructed once the request has been computed and is about to be
 * committed.
 */
public class SemanticWarrantyRequest {

  public final CallInstance call;
  public final Object value;
  public final LongSet reads;
  public final LongSet calls;
  public final Store store;

  public SemanticWarrantyRequest(CallInstance call, Object value, LongSet reads,
      LongSet calls, Store store) {
    this.call = call;
    this.value = value;
    this.reads = reads;
    this.calls = calls;
    this.store = store;
  }
}
