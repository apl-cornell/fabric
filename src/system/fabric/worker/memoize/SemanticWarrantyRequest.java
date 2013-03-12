package fabric.worker.memoize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.common.SerializedObject;
import fabric.lang.Object;
import fabric.worker.Store;

/**
 * Represents all the data needed for a request for a SemanticWarranty.  Should
 * be constructed once the request has been computed and is about to be
 * committed.
 */
public class SemanticWarrantyRequest {

  public final long call;
  public final SerializedObject value;
  public final LongSet reads;
  public final LongSet calls;

  public SemanticWarrantyRequest(CallInstance call, Object._Impl value,
      LongSet reads, LongSet calls) {
    this.call = call.id();
    this.value = new SerializedObject(value);
    this.reads = reads;
    this.calls = calls;
  }

  public SemanticWarrantyRequest(DataInput in) throws IOException {
    this.call = in.readLong();
    this.value = new SerializedObject(in);

    this.reads = new LongHashSet();
    int readsLen = in.readInt();
    for (int i = 0; i < readsLen; i++)
      this.reads.add(in.readLong());

    this.calls = new LongHashSet();
    int callsLen = in.readInt();
    for (int i = 0; i < callsLen; i++)
      this.calls.add(in.readLong());
  }

  public void write(DataOutput out) throws IOException {
    out.writeLong(call);
    value.write(out);

    out.writeInt(reads.size());
    LongIterator readsIt = reads.iterator();
    while (readsIt.hasNext())
      out.writeLong(readsIt.next());

    out.writeInt(calls.size());
    LongIterator callsIt = calls.iterator();
    while (callsIt.hasNext())
      out.writeLong(callsIt.next());
  }
}
