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
import java.security.PublicKey;

import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;
import fabric.common.ObjectGroup;
import fabric.common.exceptions.BadSignatureException;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.dissemination.Glob;

/**
 * Represents push notification that an object has been updated.
 */
public class ObjectUpdateMessage extends
    Message<RemoteWorker, ObjectUpdateMessage.Response> {

  public static class Response implements Message.Response {
    public final boolean resubscribe;

    public Response(boolean resubscribe) {
      this.resubscribe = resubscribe;
    }

    /**
     * Deserialization constructor.
     * 
     * @param node
     *          The node from which the response is being read.
     * @param in
     *          the input stream from which to read the response.
     */
    Response(RemoteWorker node, DataInput in) throws IOException {
      this.resubscribe = in.readBoolean();
    }

    /*
     * (non-Javadoc)
     * @see fabric.messages.Message.Response#write(java.io.DataOutput)
     */
    public void write(DataOutput out) throws IOException {
      out.writeBoolean(resubscribe);
    }
  }

  public final String store;
  public final long onum;
  public final Glob glob;
  public final ObjectGroup group;

  private ObjectUpdateMessage(String store, long onum, Glob glob,
      ObjectGroup group) {
    super(MessageType.OBJECT_UPDATE);
    this.store = store;
    this.onum = onum;
    this.glob = glob;
    this.group = group;

    // Exactly one of glob and group needs to be null.
    if ((glob == null) == (group == null)) {
      throw new InternalError();
    }
  }

  public ObjectUpdateMessage(String store, long onum, Glob update) {
    this(store, onum, update, null);
  }

  public ObjectUpdateMessage(long onum, ObjectGroup update) {
    this(null, onum, null, update);
  }

  /**
   * Deserialization constructor.
   */
  protected ObjectUpdateMessage(DataInput in) throws IOException,
      BadSignatureException {
    super(MessageType.OBJECT_UPDATE);

    this.onum = in.readLong();

    if (in.readBoolean()) {
      this.store = in.readUTF();
      PublicKey key = Worker.getWorker().getStore(store).getPublicKey();
      this.glob = new Glob(key, in);
      this.group = null;
    } else {
      this.store = null;
      this.glob = null;
      this.group = new ObjectGroup(in);
    }
  }

  @Override
  public Response dispatch(fabric.worker.remote.MessageHandlerThread w) {
    return w.handle(this);
  }

  public Response send(RemoteWorker worker) {
    try {
      boolean encrypt = group != null;
      return send(worker, encrypt);
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from worker.", e);
    }
  }

  @Override
  public Response response(RemoteWorker node, DataInput in) throws IOException {
    return new Response(node, in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeLong(onum);

    if (group == null) {
      out.writeBoolean(true);
      out.writeUTF(store);
      glob.write(out);
    } else {
      out.writeBoolean(false);
      group.write(out);
    }
  }

}
