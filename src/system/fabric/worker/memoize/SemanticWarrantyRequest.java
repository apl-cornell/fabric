package fabric.worker.memoize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fabric.common.util.LongHashSet;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.OidKeyHashMap;
import fabric.common.TransactionID;
import fabric.lang.Object;
import fabric.lang.Object._Impl;
import fabric.worker.transaction.ReadMap;
import fabric.worker.Store;

/**
 * Represents all the data needed for a request for a SemanticWarranty.  Should
 * be constructed once the request has been computed and is about to be
 * committed.
 */
public class SemanticWarrantyRequest {

  public final CallInstance call;
  public final Object value;

  public final OidKeyHashMap<ReadMap.Entry> reads;
  public final OidKeyHashMap<_Impl> creates;

  public final LongSet readOnums;
  public final LongSet createOnums;
  public final Map<CallInstance, WarrantiedCallResult> calls;

  public TransactionID id;

  public SemanticWarrantyRequest(CallInstance call, Object value,
      OidKeyHashMap<ReadMap.Entry> reads, OidKeyHashMap<_Impl> creates,
      Map<CallInstance, WarrantiedCallResult> calls, TransactionID id) {
    this(call, value, reads, creates, calls);
    this.id = id;
  }

  public SemanticWarrantyRequest(CallInstance call, Object value,
      OidKeyHashMap<ReadMap.Entry> reads, OidKeyHashMap<_Impl> creates,
      Map<CallInstance, WarrantiedCallResult> calls) {
    this.call = call;
    this.value = value;
    this.reads = reads;
    this.readOnums = new LongHashSet();
    for (Store s : reads.storeSet())
      for (LongKeyMap.Entry<ReadMap.Entry> entry : reads.get(s).entrySet())
        this.readOnums.add(entry.getKey());
    this.creates = creates;
    this.createOnums = new LongHashSet(); 
    for (Store s : creates.storeSet())
      for (LongKeyMap.Entry<_Impl> entry : creates.get(s).entrySet())
        this.createOnums.add(entry.getKey());
    this.calls = calls;
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

    this.calls = new HashMap<>();
    int callsLen = in.readInt();
    for (int i = 0; i < callsLen; i++) {
      CallInstance subcall = new CallInstance(in);
      this.calls.put(subcall, new WarrantiedCallResult(in));
    }

    /* for worker use only */
    reads = null;
    creates = null;
  }

  public void write(DataOutput out) throws IOException {
    call.write(out);
    (new CallResult(value)).write(out);

    out.writeInt(reads.size());
    for (Entry<Store, LongKeyMap<ReadMap.Entry>> entry : reads.nonNullEntrySet())
      for (ReadMap.Entry read : entry.getValue().values())
        out.writeLong(read.getRef().onum);

    out.writeInt(creates.size());
    for (Entry<Store, LongKeyMap<_Impl>> entry : creates.nonNullEntrySet())
      for (_Impl create : entry.getValue().values())
        out.writeLong(create.$getOnum());

    out.writeInt(calls.size());
    for (Entry<CallInstance, WarrantiedCallResult> entry : calls.entrySet()) {
      entry.getKey().write(out);
      entry.getValue().write(out);
    }
  }
}
