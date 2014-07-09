/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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

import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.worker.remote.RemoteWorker;

/**
 * An <code>AllocateMessage</code> represents a request to allocate a number of
 * object IDs at a store.
 */
public final class AllocateMessage extends
    Message<AllocateMessage.Response, AccessException> {

  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  /** The number of object IDs to allocate. */
  public final int num;

  public AllocateMessage(int num) {
    super(MessageType.ALLOCATE_ONUMS, AccessException.class);
    this.num = num;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public long[] oids;

    public Response(long[] onums) {
      this.oids = onums;
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client, MessageHandler h)
      throws ProtocolError, AccessException {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeInt(num);
  }

  /* readMessage */
  protected AllocateMessage(DataInput in) throws IOException {
    this(in.readInt());
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeInt(r.oids.length);
    for (long oid : r.oids)
      out.writeLong(oid);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    long[] oids = new long[in.readInt()];
    for (int i = 0; i < oids.length; i++)
      oids[i] = in.readLong();

    return new Response(oids);
  }

}
