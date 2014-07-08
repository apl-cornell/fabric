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
import java.util.ArrayList;
import java.util.List;

import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.security.Principal;

/**
 * A <code>StalenessCheckMessage</code> represents a request to a store to check
 * whether a given set of objects is still fresh.
 */
public final class StalenessCheckMessage extends
    Message<StalenessCheckMessage.Response, AccessException> {

  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final LongKeyMap<Integer> versions;

  public StalenessCheckMessage(LongKeyMap<Integer> versions) {
    super(MessageType.STALENESS_CHECK, AccessException.class);
    this.versions = versions;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final List<SerializedObject> staleObjects;

    public Response(List<SerializedObject> staleObjects) {
      this.staleObjects = staleObjects;
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
    out.writeInt(versions.size());
    for (LongKeyMap.Entry<Integer> entry : versions.entrySet()) {
      out.writeLong(entry.getKey());
      out.writeInt(entry.getValue());
    }
  }

  /* readMessage */
  protected StalenessCheckMessage(DataInput in) throws IOException {
    this(readMap(in));
  }

  /* helper method for deserialization constructor */
  private static LongKeyHashMap<Integer> readMap(DataInput in)
      throws IOException {
    int size = in.readInt();
    LongKeyHashMap<Integer> versions = new LongKeyHashMap<Integer>(size);
    for (int i = 0; i < size; i++)
      versions.put(in.readLong(), in.readInt());

    return versions;
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeInt(r.staleObjects.size());
    for (SerializedObject obj : r.staleObjects) {
      obj.write(out);
    }
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    int size = in.readInt();
    List<SerializedObject> staleObjects = new ArrayList<SerializedObject>(size);
    for (int i = 0; i < size; i++) {
      staleObjects.add(new SerializedObject(in));
    }

    return new Response(staleObjects);
  }

}
