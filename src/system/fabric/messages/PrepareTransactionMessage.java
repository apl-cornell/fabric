/**
 * Copyright (C) 2010 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.TransactionRestartingException;
import fabric.worker.debug.Timing;
import fabric.common.SerializedObject;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.ProtocolError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object._Impl;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;

/**
 * A <code>PrepareTransactionMessage</code> represents a transaction request to
 * a store.
 */
public class PrepareTransactionMessage extends
    Message<RemoteNode, PrepareTransactionMessage.Response> {

  public static class Response implements Message.Response {
    public final String message;

    /**
     * If the remote node is a store, this will indicate whether the worker
     * should send a commit/abort message to the store's worker to commit/abort
     * a sub-transaction. (This happens when Statistics objects are created
     * during transaction prepare.)
     */
    public final boolean subTransactionCreated;

    /**
     * A set of objects involved in the transaction that were out of date.
     */
    public final LongKeyMap<SerializedObject> versionConflicts;

    /**
     * Creates a Response indicating a successful prepare.
     */
    public Response() {
      this(false);
    }

    public Response(boolean subTransactionCreated) {
      this.subTransactionCreated = subTransactionCreated;
      this.message = null;
      this.versionConflicts = null;
    }

    /**
     * Deserialization constructor, used by the worker.
     * 
     * @param node
     *          The node from which the response is being read.
     * @param in
     *          the input stream from which to read the response.
     */
    Response(RemoteNode node, DataInput in) throws IOException {
      this.subTransactionCreated = in.readBoolean();
      if (in.readBoolean())
        this.message = in.readUTF();
      else this.message = null;

      int size = in.readInt();
      this.versionConflicts = new LongKeyHashMap<SerializedObject>(size);
      for (int i = 0; i < size; i++) {
        SerializedObject obj = new SerializedObject(in);
        versionConflicts.put(obj.getOnum(), obj);
      }
    }

    /*
     * (non-Javadoc)
     * @see fabric.messages.Message.Response#write(java.io.DataOutput)
     */
    public void write(DataOutput out) throws IOException {
      out.writeBoolean(subTransactionCreated);
      if (message != null) {
        out.writeBoolean(true);
        out.writeUTF(message);
      } else out.writeBoolean(false);

      if (versionConflicts == null)
        out.writeInt(0);
      else {
        out.writeInt(versionConflicts.size());
        for (SerializedObject obj : versionConflicts.values()) {
          obj.write(out);
        }
      }
    }
  }

  public final long tid;

  public final long commitTime;

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
  public PrepareTransactionMessage(long tid, long commitTime) {
    this(tid, commitTime, null, null, null);
  }

  /**
   * Only used by the worker.
   */
  public PrepareTransactionMessage(long tid, long commitTime,
      Collection<_Impl> toCreate, LongKeyMap<Integer> reads,
      Collection<_Impl> writes) {
    super(MessageType.PREPARE_TRANSACTION);

    this.tid = tid;
    this.commitTime = commitTime;
    this.creates = toCreate;
    this.reads = reads;
    this.writes = writes;
    this.serializedCreates = null;
    this.serializedWrites = null;
  }

  /**
   * Deserialization constructor. Used only by the store.
   * 
   * @throws IOException
   */
  protected PrepareTransactionMessage(DataInput in) throws IOException {
    super(MessageType.PREPARE_TRANSACTION);
    this.creates = null;
    this.writes = null;

    // Read the TID.
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

    // Read creates.
    size = in.readInt();
    if (size == 0) {
      serializedCreates = Collections.emptyList();
    } else {
      serializedCreates = new ArrayList<SerializedObject>(size);
      for (int i = 0; i < size; i++)
        serializedCreates.add(new SerializedObject(in));
    }

    // Read writes.
    size = in.readInt();
    if (size == 0) {
      serializedWrites = Collections.emptyList();
    } else {
      serializedWrites = new ArrayList<SerializedObject>(size);
      for (int i = 0; i < size; i++)
        serializedWrites.add(new SerializedObject(in));
    }
  }

  @Override
  public Response dispatch(fabric.store.MessageHandlerThread w)
      throws ProtocolError, TransactionPrepareFailedException {
    return w.handle(this);
  }

  @Override
  public Response dispatch(fabric.worker.remote.MessageHandlerThread handler)
      throws ProtocolError, TransactionPrepareFailedException {
    return handler.handle(this);
  }

  public Response send(RemoteNode node) throws UnreachableNodeException,
      TransactionPrepareFailedException {
    try {
      Timing.STORE.begin();
      return super.send(node, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (TransactionPrepareFailedException e) {
      throw e;
    } catch (TransactionRestartingException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from node.", e);
    } finally {
      Timing.STORE.end();
    }
  }

  @Override
  public Response response(RemoteNode node, DataInput in) throws IOException {
    return new Response(node, in);
  }

  /*
   * (non-Javadoc)
   * @see fabric.messages.Message#write(java.io.DataOutput)
   */
  @Override
  public void write(DataOutput out) throws IOException {
    // Serialize tid.
    out.writeLong(tid);

    // Serialize commitTime
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
}
