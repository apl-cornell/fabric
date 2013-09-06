package fabric.store;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fabric.common.SemanticWarranty;
import fabric.common.VersionWarranty;
import fabric.common.util.LongKeyMap;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.WarrantiedCallResult;

/**
 * Convenience class to bundle up everything that resulted from the prepare
 * writes phase.
 */
public final class PrepareWritesResult {
  public final long commitTime;
  public final OidKeyHashMap<Pair<Integer, VersionWarranty>> addedReads;
  public final Map<CallInstance, WarrantiedCallResult> addedCalls;
  public final Map<CallInstance, SemanticWarranty> callResults;

  public PrepareWritesResult(long commitTime,
      OidKeyHashMap<Pair<Integer, VersionWarranty>> addedReads,
      Map<CallInstance, WarrantiedCallResult> addedCalls,
      Map<CallInstance, SemanticWarranty> callResults) {
    this.commitTime = commitTime;
    this.addedReads = addedReads;
    this.addedCalls = addedCalls;
    this.callResults = callResults;
  }

  public PrepareWritesResult(DataInput in) throws IOException {
    // Read the commit time
    this.commitTime = in.readLong();

    // Write added reads
    int addedReadsCount = in.readInt();
    this.addedReads =
      new OidKeyHashMap<Pair<Integer, VersionWarranty>>();
    for (int i = 0; i < addedReadsCount; i++) {
      Store s = Worker.getWorker().getStore(in.readUTF());
      int submapCount = in.readInt();
      for (int j = 0; j < submapCount; j++) {
        long oid = in.readLong();
        int version = in.readInt();
        VersionWarranty warranty = new VersionWarranty(in.readLong());
        this.addedReads.put(s, oid,
            new Pair<Integer, VersionWarranty>(version, warranty));
      }
    }

    // Write added calls
    int addedCallsCount = in.readInt();
    this.addedCalls =
      new HashMap<CallInstance, WarrantiedCallResult>(addedCallsCount);
    for (int i = 0; i < addedCallsCount; i++) {
      CallInstance call = new CallInstance(in);
      WarrantiedCallResult result = new WarrantiedCallResult(in);
      this.addedCalls.put(call, result);
    }

    // Read the request responses
    int numResponses = in.readInt();
    this.callResults =
      new HashMap<CallInstance, SemanticWarranty>(numResponses);
    for (int i = 0; i < numResponses; i++) {
      CallInstance call = new CallInstance(in);
      this.callResults.put(call, new SemanticWarranty(in.readLong()));
    }
  }

  public void write(DataOutput out) throws IOException {
    // Write commit time
    out.writeLong(commitTime);

    // Write added reads
    if (addedReads == null) {
      out.writeInt(0);
    } else {
      out.writeInt(addedReads.size());
      for (Entry<Store, LongKeyMap<Pair<Integer, VersionWarranty>>> entry :
          addedReads.nonNullEntrySet()) {
        out.writeUTF(entry.getKey().name());
        out.writeInt(entry.getValue().size());
        for (LongKeyMap.Entry<Pair<Integer, VersionWarranty>> subEntry :
            entry.getValue().entrySet()) {
          out.writeLong(subEntry.getKey());
          out.writeInt(subEntry.getValue().first);
          out.writeLong(subEntry.getValue().second.expiry());
        }
      }
    }

    // Write added calls
    if (addedCalls == null) {
      out.writeInt(0);
    } else {
      out.writeInt(addedCalls.size());
      for (Map.Entry<CallInstance, WarrantiedCallResult> entry :
          addedCalls.entrySet()) {
        entry.getKey().write(out);
        entry.getValue().write(out);
      }
    }

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
