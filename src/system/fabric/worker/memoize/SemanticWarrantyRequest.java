package fabric.worker.memoize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.lang.Object;
import fabric.worker.memoize.CallResult;

/**
 * Represents all the data needed for a request for a SemanticWarranty.  Should
 * be constructed once the request has been computed and is about to be
 * committed.
 */
public class SemanticWarrantyRequest {

  public final CallInstance call;
  public final Object value;
  public final LongSet reads;
  public final Set<CallInstance> calls;

  public SemanticWarrantyRequest(CallInstance call, Object value,
      LongSet reads, Set<CallInstance> calls) {
    this.call = call;
    this.value = value;
    this.reads = reads;
    this.calls = calls;
  }

  public SemanticWarrantyRequest(DataInput in) throws IOException {
    this.call = new CallInstance(in);

    this.value = new CallResult(in).value;

    this.reads = new LongHashSet();
    int readsLen = in.readInt();
    for (int i = 0; i < readsLen; i++)
      this.reads.add(in.readLong());

    this.calls = new HashSet<CallInstance>();
    int callsLen = in.readInt();
    for (int i = 0; i < callsLen; i++) {
      this.calls.add(new CallInstance(in));
    }
  }

  public void write(DataOutput out) throws IOException {
    call.write(out);
    (new CallResult(value)).write(out);

    out.writeInt(reads.size());
    LongIterator readsIt = reads.iterator();
    while (readsIt.hasNext())
      out.writeLong(readsIt.next());

    out.writeInt(calls.size());
    for (CallInstance subcall : calls) {
      subcall.write(out);
    }
  }
}
