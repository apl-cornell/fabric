package fabric.messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.client.TransactionPrepareFailedException;
import fabric.client.UnreachableCoreException;
import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.common.SerializedObject;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.core.Worker;
import fabric.lang.Object.$Impl;

/**
 * A <code>PrepareTransactionMessage</code> represents a transaction request
 * to a core.
 */
public class PrepareTransactionMessage extends
    Message<PrepareTransactionMessage.Response> {

  public static class Response implements Message.Response {
    public final int transactionID;

    public Response(int transactionID) {
      this.transactionID = transactionID;
    }

    /**
     * Deserialization constructor, used by the client.
     * 
     * @param core
     *                The core from which the response is being read.
     * @param in
     *                the input stream from which to read the response.
     */
    Response(Core core, ObjectInputStream in) throws IOException {
      transactionID = in.readInt();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.messages.Message.Response#write(java.io.ObjectOutputStream)
     */
    public void write(ObjectOutputStream out) throws IOException {
      out.writeInt(transactionID);
    }
  }

  public final LongKeyMap<Integer> reads;

  /**
   * The objects created during the transaction, unserialized. This will only be
   * non-null on the client. The core should use the
   * <code>serializedCreates</code> field instead.
   */
  public final Collection<$Impl> creates;

  /**
   * The objects created during the transaction, serialized. This will only be
   * non-null on the core. The client should use the <code>creates</code>
   * field instead.
   */
  public final Collection<SerializedObject> serializedCreates;

  /**
   * The objects modified during the transaction, unserialized. This will only
   * be non-null on the client. The core should use the
   * <code>serializedWrites</code> field instead.
   */
  public final Collection<$Impl> writes;

  /**
   * The objects modified during the transaction, serialized. This will only be
   * non-null on the core. The client should use the <code>writes</code> field
   * instead.
   */
  public final Collection<SerializedObject> serializedWrites;

  /**
   * Only used by the client.
   */
  public PrepareTransactionMessage(Collection<$Impl> toCreate,
      LongKeyMap<Integer> reads, Collection<$Impl> writes) {
    super(MessageType.PREPARE_TRANSACTION);

    this.creates = toCreate;
    this.reads = reads;
    this.writes = writes;
    this.serializedCreates = null;
    this.serializedWrites = null;
  }

  /**
   * Deserialization constructor. Used only by the core.
   * 
   * @throws IOException
   */
  protected PrepareTransactionMessage(ObjectInputStream in) throws IOException {
    super(MessageType.PREPARE_TRANSACTION);
    this.creates = null;
    this.writes = null;

    // Read reads.
    int size = in.readInt();
    if (size == 0) {
      reads = new LongKeyHashMap<Integer>();
    } else {
      reads = new LongKeyHashMap<Integer>(size);
      for (int i = 0; i < size; i++)
        reads.put(in.readLong(), in.readInt());
    }

    // Read creates.
    size = in.readInt();
    if (size == 0) {
      serializedCreates = Collections.emptyList();
    } else {
      serializedCreates = new ArrayList<SerializedObject>();
      for (int i = 0; i < size; i++)
        serializedCreates.add(new SerializedObject(in));
    }

    // Read writes.
    size = in.readInt();
    if (size == 0) {
      serializedWrites = Collections.emptyList();
    } else {
      serializedWrites = new ArrayList<SerializedObject>();
      for (int i = 0; i < size; i++)
        serializedWrites.add(new SerializedObject(in));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#dispatch(fabric.core.Worker)
   */
  @Override
  public Response dispatch(Worker w) throws TransactionPrepareFailedException {
    return w.handle(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#send(fabric.client.Core)
   */
  @Override
  public Response send(RemoteCore core) throws UnreachableCoreException,
      TransactionPrepareFailedException {
    try {
      return super.send(core);
    } catch (UnreachableCoreException e) {
      throw e;
    } catch (TransactionPrepareFailedException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from core.", e);
    }
  }

  @Override
  public Response response(Core c, ObjectInputStream in) throws IOException {
    return new Response(c, in);
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#write(java.io.ObjectOutputStream)
   */
  @Override
  public void write(ObjectOutputStream out) throws IOException {
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
      for ($Impl impl : creates)
        SerializedObject.write(impl, out);
    }

    // Serialize writes.
    if (writes == null) {
      out.writeInt(0);
    } else {
      out.writeInt(writes.size());
      for ($Impl impl : writes)
        SerializedObject.write(impl, out);
    }
  }
}
