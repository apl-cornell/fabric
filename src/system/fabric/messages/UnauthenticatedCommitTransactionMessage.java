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

import fabric.worker.RemoteStore;
import fabric.worker.debug.Timing;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;

public class UnauthenticatedCommitTransactionMessage extends
    Message<RemoteStore, UnauthenticatedCommitTransactionMessage.Response> {

  public static class Response implements Message.Response {
    public final boolean success;
    public final String message;

    public Response(boolean success) {
      this(success, null);
    }

    public Response(boolean success, String message) {
      this.success = success;
      this.message = message;
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
      this.success = in.readBoolean();
      if (in.readBoolean())
        this.message = in.readUTF();
      else this.message = null;
    }

    /*
     * (non-Javadoc)
     * @see fabric.messages.Message.Response#write(java.io.DataOutput)
     */
    public void write(DataOutput out) throws IOException {
      out.writeBoolean(success);
      if (message != null) {
        out.writeBoolean(true);
        out.writeUTF(message);
      } else out.writeBoolean(false);
    }
  }

  public final long transactionID;

  public UnauthenticatedCommitTransactionMessage(long transactionID) {
    super(MessageType.UNAUTHENTICATED_COMMIT_TRANSACTION);
    this.transactionID = transactionID;
  }

  /**
   * Deserialization constructor.
   */
  protected UnauthenticatedCommitTransactionMessage(DataInput in) throws IOException {
    this(in.readLong());
  }

  @Override
  public Response dispatch(fabric.store.MessageHandlerThread w) {
    return w.handle(this);
  }

  public Response send(RemoteStore store) throws UnreachableNodeException {
    try {
      Timing.STORE.begin();
      return super.send(store, false);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from node.", e);
    } finally {
      Timing.STORE.end();
    }
  }

  @Override
  public Response response(RemoteStore store, DataInput in) throws IOException {
    return new Response(store, in);
  }

  /*
   * (non-Javadoc)
   * @see fabric.messages.Message#write(java.io.DataOutput)
   */
  @Override
  public void write(DataOutput out) throws IOException {
    out.writeLong(transactionID);
  }

}
