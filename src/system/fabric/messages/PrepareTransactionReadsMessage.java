package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.VersionWarranty;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.worker.TransactionPrepareFailedException;

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
  public final LongKeyMap<Integer> reads;

  /**
   * Used to prepare transactions at remote workers.
   */
  public PrepareTransactionReadsMessage(long tid, long commitTime) {
    this(tid, commitTime, null);
  }

  /**
   * Only used by the worker.
   */
  public PrepareTransactionReadsMessage(long tid, LongKeyMap<Integer> reads,
      long commitTime) {
    this(tid, commitTime, reads);
  }

  private PrepareTransactionReadsMessage(long tid, long commitTime,
      LongKeyMap<Integer> reads) {
    super(MessageType.PREPARE_TRANSACTION_READS,
        TransactionPrepareFailedException.class);

    this.tid = tid;
    this.commitTime = commitTime;
    this.reads = reads;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final LongKeyMap<VersionWarranty> newWarranties;

    public Response() {
      this.newWarranties = null;
    }

    public Response(LongKeyMap<VersionWarranty> newWarranties) {
      this.newWarranties = newWarranties;
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity client, MessageHandler h)
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
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    if (r.newWarranties == null) {
      out.writeBoolean(false);
      return;
    }

    out.writeBoolean(true);
    out.writeInt(r.newWarranties.size());
    for (LongKeyMap.Entry<VersionWarranty> entry : r.newWarranties.entrySet()) {
      out.writeLong(entry.getKey());
      out.writeLong(entry.getValue().expiry());
    }
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    if (!in.readBoolean()) {
      return new Response();
    }

    int size = in.readInt();
    LongKeyMap<VersionWarranty> newWarranties =
        new LongKeyHashMap<VersionWarranty>(size);
    for (int i = 0; i < size; i++) {
      newWarranties.put(in.readLong(), new VersionWarranty(in.readLong()));
    }

    return new Response(newWarranties);
  }
}
