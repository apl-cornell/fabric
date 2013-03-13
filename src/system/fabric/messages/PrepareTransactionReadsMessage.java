package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fabric.common.SemanticWarranty;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.security.Principal;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.TransactionPrepareFailedException;

/**
 * A <code>PrepareTransactionReadsMessage</code> represents a transaction
 * PREPARE_READS request to a remote node.
 */
public class PrepareTransactionReadsMessage extends
  Message<PrepareTransactionReadsMessage.Response,
  TransactionPrepareFailedException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final long tid;
  public final long commitTime;
  public final LongKeyMap<Integer> reads;
  public final Set<byte[]> calls;
  public final Set<SemanticWarrantyRequest> requests;

  /**
   * Used to prepare transactions at remote workers.
   */
  public PrepareTransactionReadsMessage(long tid, long commitTime) {
    this(tid, commitTime, null, null, null);
  }

  /**
   * Only used by the worker.
   */
  public PrepareTransactionReadsMessage(long tid, LongKeyMap<Integer> reads,
      Set<byte[]> calls, Set<SemanticWarrantyRequest> requests,
      long commitTime) {
    this(tid, commitTime, reads, calls, requests);
  }

  private PrepareTransactionReadsMessage(long tid, long commitTime,
      LongKeyMap<Integer> reads, Set<byte[]> calls,
      Set<SemanticWarrantyRequest> requests) {
    super(MessageType.PREPARE_TRANSACTION_READS,
        TransactionPrepareFailedException.class);

    this.tid = tid;
    this.commitTime = commitTime;
    this.reads = reads;
    this.calls = calls;
    this.requests = requests;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {

    private Map<byte[], SemanticWarranty> requestResults;

    public Response(Map<byte[], SemanticWarranty> requestResults) {
      this.requestResults = requestResults;
    }

    public Map<byte[], SemanticWarranty> getResults() {
      return requestResults;
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(Principal p, MessageHandler h)
      throws TransactionPrepareFailedException {
    return h.handle(p, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    // Serialize tid and commit time.
    out.writeLong(tid);
    out.writeLong(commitTime);

    // Serialize reads.
    if (reads == null) {
      out.writeInt(0);
    } else {
      out.writeInt(reads.size());
      for (LongKeyMap.Entry<Integer> entry : reads.entrySet()) {
        out.writeLong(entry.getKey());
        out.writeInt(entry.getValue());
      }
    }

    if (calls == null) {
      out.writeInt(0);
    } else {
      out.writeInt(calls.size());
      for (byte[] id : calls) {
        out.writeInt(id.length);
        out.write(id);
      }
    }

    if (requests == null) {
      out.writeInt(0);
    } else {
      out.writeInt(requests.size());
      for (SemanticWarrantyRequest r : requests)
        r.write(out);
    }
  }

  /* readMessage */
  protected PrepareTransactionReadsMessage(DataInput in) throws IOException {
    super(MessageType.PREPARE_TRANSACTION_READS,
        TransactionPrepareFailedException.class);

    // Read the TID and commit time.
    this.tid = in.readLong();
    this.commitTime = in.readLong();

    // Read reads.
    int size = in.readInt();
    if (size == 0) {
      reads = new LongKeyHashMap<Integer>();
    } else {
      reads = new LongKeyHashMap<Integer>(size);
      for (int i = 0; i < size; i++)
        reads.put(in.readLong(), in.readInt());
    }

    int callSize = in.readInt();
    calls = new HashSet<byte[]>();
    for (int i = 0; i < callSize; i++) {
      byte[] call = new byte[in.readInt()];
      in.readFully(call);
      calls.add(call);
    }

    int requestsSize = in.readInt();
    requests = new HashSet<SemanticWarrantyRequest>(requestsSize);
    for (int i = 0; i < requestsSize; i++)
      requests.add(new SemanticWarrantyRequest(in));
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeInt(r.requestResults.size());
    for (Map.Entry<byte[], SemanticWarranty> e : r.requestResults.entrySet()) {
      out.writeInt(e.getKey().length);
      out.write(e.getKey());
      out.writeLong(e.getValue().expiry());
    }
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    int numResponses = in.readInt();
    Map<byte[], SemanticWarranty> responses =
      new HashMap<byte[], SemanticWarranty>(numResponses);
    for (int i = 0; i < numResponses; i++) {
      byte[] call = new byte[in.readInt()];
      in.readFully(call);
      responses.put(call, new SemanticWarranty(in.readLong()));
    }
    return new Response(responses);
  }
}
