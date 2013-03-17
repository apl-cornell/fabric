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

  public final CallID call;
  public final Object value;
  public final LongSet reads;
  public final Set<CallID> calls;

  public SemanticWarrantyRequest(CallInstance call, Object value,
      LongSet reads, Set<CallID> calls) {
    this.call = call.id();
    this.value = value;
    this.reads = reads;
    this.calls = calls;
  }

  public SemanticWarrantyRequest(DataInput in) throws IOException {
    int callIdLen = in.readInt();
    byte[] callBytes = new byte[callIdLen];
    in.readFully(callBytes);
    this.call = new CallID(callBytes);

    this.value = new CallResult(in).value;

    this.reads = new LongHashSet();
    int readsLen = in.readInt();
    for (int i = 0; i < readsLen; i++)
      this.reads.add(in.readLong());

    this.calls = new HashSet<CallID>();
    int callsLen = in.readInt();
    for (int i = 0; i < callsLen; i++) {
      int callUsedIdLen = in.readInt();
      byte[] callId = new byte[callUsedIdLen];
      in.readFully(callId);
      this.calls.add(new CallID(callId));
    }
  }

  public void write(DataOutput out) throws IOException {
    out.writeInt(call.id().length);
    out.write(call.id());
    (new CallResult(value)).write(out);

    out.writeInt(reads.size());
    LongIterator readsIt = reads.iterator();
    while (readsIt.hasNext())
      out.writeLong(readsIt.next());

    out.writeInt(calls.size());
    for (CallID callId : calls) {
      out.writeInt(callId.id().length);
      out.write(callId.id());
    }
  }
}
