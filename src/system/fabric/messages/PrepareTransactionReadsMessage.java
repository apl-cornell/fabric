package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.security.Principal;
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
  public final Long commitTime;
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
  public PrepareTransactionReadsMessage(long tid, LongKeyMap<Integer> reads) {
    this(tid, null, reads);
  }

  private PrepareTransactionReadsMessage(long tid, Long commitTime,
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
    public Response() {
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
    // Serialize tid.
    out.writeLong(tid);

    // Serialize commitTime
    out.writeBoolean(commitTime != null);
    if (commitTime != null) out.writeLong(commitTime);

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

    // Read the TID.
    this.tid = in.readLong();
    if (in.readBoolean())
      this.commitTime = in.readLong();
    else this.commitTime = null;

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
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    return new Response();
  }
}
