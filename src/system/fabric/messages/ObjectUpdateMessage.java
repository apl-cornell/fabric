/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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

import fabric.common.ObjectGroup;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.ProtocolError;
import fabric.dissemination.Glob;
import fabric.lang.security.Principal;

/**
 * Represents push notification that an object has been updated.
 */
public class ObjectUpdateMessage extends
    Message<ObjectUpdateMessage.Response, fabric.messages.Message.NoException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final String store;
  public final long onum;
  public final Glob glob;
  public final ObjectGroup group;

  private ObjectUpdateMessage(String store, long onum, Glob glob,
      ObjectGroup group) {
    super(MessageType.OBJECT_UPDATE, NoException.class);
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

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final boolean resubscribe;

    public Response(boolean resubscribe) {
      this.resubscribe = resubscribe;
    }

  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(Principal p, MessageHandler h) throws ProtocolError {
    return h.handle(p, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
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

  /* readMessage */
  protected ObjectUpdateMessage(DataInput in) throws IOException {
    super(MessageType.OBJECT_UPDATE, NoException.class);

    this.onum = in.readLong();

    if (in.readBoolean()) {
      this.store = in.readUTF();
      this.glob = new Glob(in);
      this.group = null;
    } else {
      this.store = null;
      this.glob = null;
      this.group = new ObjectGroup(in);
    }
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeBoolean(r.resubscribe);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    return new Response(in.readBoolean());
  }

}
