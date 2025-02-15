package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import fabric.common.SerializedObject;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object._Impl;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>PrepareTransactionMessage</code> represents a transaction request to
 * a store.
 */
public class PrepareTransactionMessage extends AsyncMessage {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final long tid;

  /**
   * A flag to indicate whether objects from just a single (persistent) store
   * are involved in this transaction. This is always false if the transaction
   * is distributed. If this is true, then the store will commit the transaction
   * as soon as it is prepared.
   */
  public final boolean singleStore;

  /**
   * A flag to indicate whether the transaction is read-only. A transaction is
   * read-only if it does not modify any persistent objects. If this value is
   * true, then the store will commit the transaction as soon as it is prepared.
   */
  public final boolean readOnly;

  public final LongKeyMap<Integer> reads;

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
  public PrepareTransactionMessage(long tid) {
    this(tid, false, false, null, null, null);
  }

  /**
   * Only used by the worker.
   */
  public PrepareTransactionMessage(long tid, boolean singleStore,
      boolean readOnly, Collection<_Impl> toCreate, LongKeyMap<Integer> reads,
      Collection<_Impl> writes) {
    super(MessageType.PREPARE_TRANSACTION);

    this.tid = tid;
    this.singleStore = singleStore;
    this.readOnly = readOnly;
    this.creates = toCreate;
    this.reads = reads;
    this.writes = writes;
    this.serializedCreates = null;
    this.serializedWrites = null;
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
  public void dispatch(RemoteIdentity<RemoteWorker> client, MessageHandler h)
      throws ProtocolError {
    h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    // Serialize tid.
    out.writeLong(tid);

    // Serialize single-store flag.
    out.writeBoolean(singleStore);

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
  protected PrepareTransactionMessage(DataInput in) throws IOException {
    super(MessageType.PREPARE_TRANSACTION);
    this.creates = null;
    this.writes = null;

    // Read the TID.
    this.tid = in.readLong();

    // Read the single-store flag.
    this.singleStore = in.readBoolean();

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

    // Read creates.
    size = in.readInt();
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
}
