package fabric.worker.memoize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.common.SerializedObject;
import fabric.lang.Object;

/**
 * Represents all the data needed for a request for a SemanticWarranty.  Should
 * be constructed once the request has been computed and is about to be
 * committed.
 */
public class SemanticWarrantyRequest {

  public final byte[] call;
  public final SerializedObject value;
  public final LongSet reads;
  public final Set<byte[]> calls;

  public SemanticWarrantyRequest(CallInstance call, Object._Impl value,
      LongSet reads, Set<byte[]> calls) {
    this.call = call.id();
    this.value = new SerializedObject(value);
    this.reads = reads;
    this.calls = calls;
  }

  public SemanticWarrantyRequest(DataInput in) throws IOException {
    int callIdLen = in.readInt();
    this.call = new byte[callIdLen];
    in.readFully(this.call);

    this.value = new SerializedObject(in);

    this.reads = new LongHashSet();
    int readsLen = in.readInt();
    for (int i = 0; i < readsLen; i++)
      this.reads.add(in.readLong());

    this.calls = new HashSet<byte[]>();
    int callsLen = in.readInt();
    for (int i = 0; i < callsLen; i++) {
      int callUsedIdLen = in.readInt();
      byte[] callId = new byte[callUsedIdLen];
      in.readFully(callId);
      this.calls.add(callId);
    }
  }

  public void write(DataOutput out) throws IOException {
    out.writeInt(call.length);
    out.write(call);
    value.write(out);

    out.writeInt(reads.size());
    LongIterator readsIt = reads.iterator();
    while (readsIt.hasNext())
      out.writeLong(readsIt.next());

    out.writeInt(calls.size());
    for (byte[] callId : calls) {
      out.writeInt(callId.length);
      out.write(callId);
    }
  }
}
