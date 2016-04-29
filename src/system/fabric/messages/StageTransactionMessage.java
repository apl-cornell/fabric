package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.worker.TransactionStagingFailedException;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>StageTransactionMessage</code> represents a transaction request to
 * a store.
 */
public class StageTransactionMessage extends
    Message<StageTransactionMessage.Response, TransactionStagingFailedException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final long tid;

  /**
   * Maps onums to version numbers.
   */
  public final LongKeyMap<Integer> reads;

  /**
   * Maps onums to version numbers.
   */
  public final LongKeyMap<Integer> writes;

  /**
   * Set of onums that were created.
   */
  public final LongSet creates;

  /**
   * Used to prepare transactions at remote workers.
   */
  public StageTransactionMessage(long tid) {
    this(tid, null, null, null);
  }

  /**
   * Only used by the worker.
   */
  public StageTransactionMessage(long tid, LongKeyMap<Integer> reads,
      LongKeyMap<Integer> writes, LongSet creates) {
    super(MessageType.STAGE_TRANSACTION,
        TransactionStagingFailedException.class);

    this.tid = tid;
    this.reads = reads;
    this.writes = writes;
    this.creates = creates;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    /**
     * Creates a Response indicating a successful prepare.
     */
    public Response() {
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client,
      MessageHandler h) throws TransactionStagingFailedException {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    // Serialize tid.
    out.writeLong(tid);

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

    // Serialize writes.
    if (writes == null) {
      out.writeInt(0);
    } else {
      out.writeInt(writes.size());
      for (LongKeyMap.Entry<Integer> entry : writes.entrySet()) {
        out.writeLong(entry.getKey());
        out.writeInt(entry.getValue());
      }
    }

    // Serialize creates.
    if (creates == null) {
      out.writeInt(0);
    } else {
      out.writeInt(creates.size());
      for (LongIterator it = creates.iterator(); it.hasNext();) {
        out.writeLong(it.next());
      }
    }
  }

  /* readMessage */
  protected StageTransactionMessage(DataInput in) throws IOException {
    super(MessageType.STAGE_TRANSACTION,
        TransactionStagingFailedException.class);
    // Read the TID.
    this.tid = in.readLong();

    // Read reads.
    int size = in.readInt();
    if (size == 0) {
      reads = new LongKeyHashMap<>();
    } else {
      reads = new LongKeyHashMap<>(size);
      for (int i = 0; i < size; i++)
        reads.put(in.readLong(), in.readInt());
    }

    // Read writes.
    size = in.readInt();
    if (size == 0) {
      writes = new LongKeyHashMap<>();
    } else {
      writes = new LongKeyHashMap<>(size);
      for (int i = 0; i < size; i++)
        writes.put(in.readLong(), in.readInt());
    }

    size = in.readInt();
    if (size == 0) {
      creates = new LongHashSet();
    } else {
      creates = new LongHashSet(size);
      for (int i = 0; i < size; i++)
        creates.add(in.readLong());
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
