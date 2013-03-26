package fabric.worker.memoize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import fabric.common.util.LongHashSet;
import fabric.common.util.LongSet;
import fabric.lang.Object;
import fabric.lang.Object._Impl;
import fabric.worker.transaction.ReadMapEntry;

/**
 * Represents all the data needed for a request for a SemanticWarranty.  Should
 * be constructed once the request has been computed and is about to be
 * committed.
 */
public class SemanticWarrantyRequest {

  public final CallInstance call;
  public final Object value;

  public final Set<ReadMapEntry> reads;
  public final Set<_Impl> creates;

  public final LongSet readOnums;
  public final LongSet createOnums;
  public final Set<CallInstance> calls;

  public SemanticWarrantyRequest(CallInstance call, Object value,
      Set<ReadMapEntry> reads, Set<_Impl> creates, Set<CallInstance> calls) {
    this.call = call;
    this.value = value;
    this.reads = reads;
    this.creates = creates;
    this.calls = calls;

    /* for store use only */
    readOnums = null;
    createOnums = null;
  }

  public SemanticWarrantyRequest(DataInput in) throws IOException {
    this.call = new CallInstance(in);
    this.value = new CallResult(in).value;

    this.readOnums = new LongHashSet();
    int readsLen = in.readInt();
    for (int i = 0; i < readsLen; i++)
      this.readOnums.add(in.readLong());

    this.createOnums = new LongHashSet();
    int createsLen = in.readInt();
    for (int i = 0; i < createsLen; i++)
      this.createOnums.add(in.readLong());

    this.calls = new HashSet<CallInstance>();
    int callsLen = in.readInt();
    for (int i = 0; i < callsLen; i++)
      this.calls.add(new CallInstance(in));

    /* for worker use only */
    reads = null;
    creates = null;
  }

  public void write(DataOutput out) throws IOException {
    call.write(out);
    (new CallResult(value)).write(out);

    out.writeInt(reads.size());
    for (ReadMapEntry read : reads)
      out.writeLong(read.obj.onum);

    out.writeInt(creates.size());
    for (_Impl create : creates)
      out.writeLong(create.$getOnum());

    out.writeInt(calls.size());
    for (CallInstance subcall : calls) {
      subcall.write(out);
    }
  }
}
