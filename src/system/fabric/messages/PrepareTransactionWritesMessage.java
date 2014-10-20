package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import fabric.common.SerializedObject;
import fabric.common.net.RemoteIdentity;
import fabric.lang.Object._Impl;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>PrepareTransactionWritesMessage</code> represents a transaction
 * PREPARE_WRITES request to a remote node.
 */
public class PrepareTransactionWritesMessage
    extends
    Message<PrepareTransactionWritesMessage.Response, TransactionPrepareFailedException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final long tid;

  /**
   * The objects created during the transaction, unserialized. This will only be
   * non-null on the worker. The store should use the
   * <code>serializedCreates</code> field instead.
   */
  public final Collection<_Impl> creates;

  /**
   * The objects created during the transaction, serialized. This will only be
   * non-null on the store. The worker should use the <code>creates</code> field
   * instead.
   */
  public final Collection<SerializedObject> serializedCreates;

  /**
   * The objects modified during the transaction, unserialized. This will only
   * be non-null on the worker. The store should use the
   * <code>serializedWrites</code> field instead.
   */
  public final Collection<_Impl> writes;

  /**
   * The objects modified during the transaction, serialized. This will only be
   * non-null on the store. The worker should use the <code>writes</code> field
   * instead.
   */
  public final Collection<SerializedObject> serializedWrites;

  /**
   * Used to prepare transactions at remote workers.
   */
  public PrepareTransactionWritesMessage(long tid) {
    this(tid, null, null);
  }

  /**
   * Only used by the worker.
   */
  public PrepareTransactionWritesMessage(long tid, Collection<_Impl> toCreate,
      Collection<_Impl> writes) {
    super(MessageType.PREPARE_TRANSACTION_WRITES,
        TransactionPrepareFailedException.class);

    this.tid = tid;
    this.creates = toCreate;
    this.writes = writes;
    this.serializedCreates = null;
    this.serializedWrites = null;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final long minCommitTime;

    /**
     * Creates a Response indicating a successful prepare.
     */
    public Response(long minCommitTime) {
      this.minCommitTime = minCommitTime;
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
    // Serialize tid.
    out.writeLong(tid);

    // Serialize creates.
    if (creates == null) {
      out.writeInt(0);
    } else {
      out.writeInt(creates.size());
      for (_Impl impl : creates)
        SerializedObject.write(impl, out);
    }

    // Serialize writes.
    if (writes == null) {
      out.writeInt(0);
    } else {
      out.writeInt(writes.size());
      for (_Impl impl : writes)
        SerializedObject.write(impl, out);
    }
  }

  /* readMessage */
  protected PrepareTransactionWritesMessage(DataInput in) throws IOException {
    super(MessageType.PREPARE_TRANSACTION_WRITES,
        TransactionPrepareFailedException.class);
    this.creates = null;
    this.writes = null;

    // Read the TID.
    this.tid = in.readLong();

    // Read creates.
    int size = in.readInt();
    if (size == 0) {
      // XXX: this list must be mutable since it can be added
      //      to by the surrogate manager
      serializedCreates = new ArrayList<>(0);
    } else {
      serializedCreates = new ArrayList<>(size);
      for (int i = 0; i < size; i++)
        serializedCreates.add(new SerializedObject(in));
    }

    // Read writes.
    size = in.readInt();
    if (size == 0) {
      serializedWrites = Collections.emptyList();
    } else {
      serializedWrites = new ArrayList<>(size);
      for (int i = 0; i < size; i++)
        serializedWrites.add(new SerializedObject(in));
    }
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeLong(r.minCommitTime);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    long minCommitTime = in.readLong();
    return new Response(minCommitTime);
  }
}
