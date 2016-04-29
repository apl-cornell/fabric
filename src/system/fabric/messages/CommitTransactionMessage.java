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
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.remote.RemoteWorker;;

public class CommitTransactionMessage extends
    Message<CommitTransactionMessage.Response, TransactionCommitFailedException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final long transactionID;

  /**
   * The objects created during the transaction, unserialized. This will only be
   * non-null on the worker. The store should use the {@link #serializedCreates}
   * field instead.
   */
  public final Collection<_Impl> creates;

  /**
   * The objects created during the transaction, serialized. This will only be
   * non-null on the store. The worker should use the {@link #creates} field
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

  public CommitTransactionMessage(long transactionID) {
    this(transactionID, null, null);
  }

  /**
   * Only used by the worker.
   */
  public CommitTransactionMessage(long transactionID,
      Collection<_Impl> toCreate, Collection<_Impl> writes) {
    super(MessageType.COMMIT_TRANSACTION,
        TransactionCommitFailedException.class);
    this.transactionID = transactionID;
    this.creates = toCreate;
    this.writes = writes;
    this.serializedCreates = null;
    this.serializedWrites = null;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client,
      MessageHandler h) throws TransactionCommitFailedException {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    // Serialize tid.
    out.writeLong(transactionID);

    // Serialize creates.
    if (creates == null) {
      out.writeInt(0);
    } else {
      out.writeInt(creates.size());
      for (_Impl impl : creates) {
        SerializedObject.write(impl, out);
      }
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
  protected CommitTransactionMessage(DataInput in) throws IOException {
    super(MessageType.COMMIT_TRANSACTION,
        TransactionCommitFailedException.class);
    this.creates = null;
    this.writes = null;

    // Read the TID.
    this.transactionID = in.readLong();

    // Read creates.
    int size = in.readInt();
    if (size == 0) {
      // XXX This list must be mutable since it can be added to by the surrogate
      // manager.
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
  protected void writeResponse(DataOutput out, Response r) {
    // do nothing
  }

  @Override
  protected Response readResponse(DataInput in) {
    return new Response();
  }

}
