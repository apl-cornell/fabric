/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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
import fabric.dissemination.Glob;
import fabric.lang.security.Principal;

/**
 * A <code>DissemReadMessage</code> represents a request from a dissemination
 * node to read an object at a store. This implicitly subscribes the worker to
 * receive the next update to the object.
 */
public final class DissemReadMessage extends
    Message<DissemReadMessage.Response, AccessException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  /** The onum of the object to read. */
  public final long onum;

  public DissemReadMessage(long onum) {
    super(MessageType.DISSEM_READ_ONUM, AccessException.class);
    this.onum = onum;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {

    public final Glob glob;

    public Response(Glob glob) {
      this.glob = glob;
    }

  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(Principal p, MessageHandler h) throws ProtocolError,
      AccessException {
    return h.handle(p, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeLong(onum);
  }

  /* readMessage */
  protected DissemReadMessage(DataInput in) throws IOException {
    this(in.readLong());
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    Glob glob = new Glob(in);
    return new Response(glob);
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    r.glob.write(out);
  }

}
