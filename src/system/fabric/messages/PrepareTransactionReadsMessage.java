package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fabric.common.VersionWarranty;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.WarrantiedCallResult;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>PrepareTransactionReadsMessage</code> represents a transaction
 * PREPARE_READS request to a remote node.
 */
public class PrepareTransactionReadsMessage
    extends
    Message<PrepareTransactionReadsMessage.Response, TransactionPrepareFailedException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final long tid;
  public final long commitTime;

  /**
   * A flag to indicate whether the transaction is read-only. A transaction is
   * read-only if it does not modify any persistent objects. If this value is
   * true, then the store will commit the transaction as soon as it is prepared.
   */
  public final boolean readOnly;

  public final LongKeyMap<Integer> reads;
  public final Map<CallInstance, WarrantiedCallResult> calls;

  /**
   * Used to prepare transactions at remote workers.
   */
  public PrepareTransactionReadsMessage(long tid, long commitTime) {
    this(tid, commitTime, false, null, null);
  }

  /**
   * Only used by the worker.
   */
  public PrepareTransactionReadsMessage(long tid, boolean readOnly,
      LongKeyMap<Integer> reads, Map<CallInstance, WarrantiedCallResult> calls,
      long commitTime) {
    this(tid, commitTime, readOnly, reads, calls);
  }

  private PrepareTransactionReadsMessage(long tid, long commitTime,
      boolean readOnly, LongKeyMap<Integer> reads,
      Map<CallInstance, WarrantiedCallResult> calls) {
    super(MessageType.PREPARE_TRANSACTION_READS,
        TransactionPrepareFailedException.class);

    this.tid = tid;
    this.commitTime = commitTime;
    this.readOnly = readOnly;
    this.reads = reads;
    this.calls = calls;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final LongKeyMap<VersionWarranty> newWarranties;
    public final Map<CallInstance, WarrantiedCallResult> newSemWarranties;

    public Response() {
      this.newWarranties = null;
      this.newSemWarranties = null;
    }

    public Response(LongKeyMap<VersionWarranty> newWarranties,
        Map<CallInstance, WarrantiedCallResult> newSemWarranties) {
      this.newWarranties = newWarranties;
      this.newSemWarranties = newSemWarranties;
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client, MessageHandler h)
      throws TransactionPrepareFailedException {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    // Serialize tid and commit time.
    out.writeLong(tid);
    out.writeLong(commitTime);

    // Serialize read-only flag.
    out.writeBoolean(readOnly);

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
      for (CallInstance call : calls.keySet()) {
        call.write(out);
        calls.get(call).write(out);
      }
    }
  }

  /* readMessage */
  protected PrepareTransactionReadsMessage(DataInput in) throws IOException {
    super(MessageType.PREPARE_TRANSACTION_READS,
        TransactionPrepareFailedException.class);

    // Read the TID and commit time.
    this.tid = in.readLong();
    this.commitTime = in.readLong();

    // Read the read-only flag.
    this.readOnly = in.readBoolean();

    // Read reads.
    int size = in.readInt();
    if (size == 0) {
      reads = new LongKeyHashMap<>();
    } else {
      reads = new LongKeyHashMap<>(size);
      for (int i = 0; i < size; i++)
        reads.put(in.readLong(), in.readInt());
    }

    int callSize = in.readInt();
    calls = new HashMap<>();
    for (int i = 0; i < callSize; i++) {
      CallInstance key = new CallInstance(in);
      calls.put(key, new WarrantiedCallResult(in));
    }
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    if (r.newWarranties == null) {
      out.writeInt(0);
    } else {
      out.writeInt(r.newWarranties.size());
      for (LongKeyMap.Entry<VersionWarranty> entry : r.newWarranties.entrySet()) {
        out.writeLong(entry.getKey());
        out.writeLong(entry.getValue().expiry());
      }
    }

    if (r.newSemWarranties == null) {
      out.writeInt(0);
    } else {
      out.writeInt(r.newSemWarranties.size());
      for (Map.Entry<CallInstance, WarrantiedCallResult> entry :
          r.newSemWarranties.entrySet()) {
        entry.getKey().write(out);
        entry.getValue().write(out);
      }
    }
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    int size = in.readInt();
    LongKeyMap<VersionWarranty> newWarranties = new LongKeyHashMap<>(size);
    for (int i = 0; i < size; i++) {
      newWarranties.put(in.readLong(), new VersionWarranty(in.readLong()));
    }

    int semSize = in.readInt();
    Map<CallInstance, WarrantiedCallResult> newSemWarranties =
        new HashMap<>(semSize);
    for (int i = 0; i < semSize; i++) {
      CallInstance key = new CallInstance(in);
      newSemWarranties.put(key, new WarrantiedCallResult(in));
    }

    return new Response(newWarranties, newSemWarranties);
  }
}
