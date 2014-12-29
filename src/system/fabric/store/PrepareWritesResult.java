package fabric.store;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fabric.common.SemanticWarranty;
import fabric.worker.memoize.CallInstance;

/**
 * Convenience class to bundle up everything that resulted from the prepare
 * writes phase.
 */
public final class PrepareWritesResult {
  public final long commitTime;
  public final Map<CallInstance, SemanticWarranty> callResults;

  public PrepareWritesResult(long commitTime,
      Map<CallInstance, SemanticWarranty> callResults) {
    this.commitTime = commitTime;
    this.callResults = callResults;
  }

  public PrepareWritesResult(DataInput in) throws IOException {
    // Read the commit time
    this.commitTime = in.readLong();

    // Read the request responses
    int numResponses = in.readInt();
    this.callResults = new HashMap<>(numResponses);
    for (int i = 0; i < numResponses; i++) {
      CallInstance call = new CallInstance(in);
      this.callResults.put(call, new SemanticWarranty(in.readLong()));
    }
  }

  public void write(DataOutput out) throws IOException {
    // Write commit time
    out.writeLong(commitTime);

    // Write request responses
    if (callResults == null) {
      out.writeInt(0);
    } else {
      out.writeInt(callResults.size());
      for (Map.Entry<CallInstance, SemanticWarranty> e :
          callResults.entrySet()) {
        e.getKey().write(out);
        out.writeLong(e.getValue().expiry());
      }
    }
  }
}
