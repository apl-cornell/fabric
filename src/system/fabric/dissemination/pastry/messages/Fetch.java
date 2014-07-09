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
package fabric.dissemination.pastry.messages;

import java.io.IOException;
import java.security.GeneralSecurityException;

import rice.p2p.commonapi.Endpoint;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.NodeHandle;
import rice.p2p.commonapi.rawserialization.InputBuffer;
import rice.p2p.commonapi.rawserialization.OutputBuffer;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.pastry.Disseminator;
import fabric.worker.Worker;

/**
 * A Fetch message represents a request to fetch a particular object.
 */
public class Fetch extends AbstractRawMessage {

  private transient final NodeHandle sender;
  private final Id id;
  private final String store;
  private final long onum;
  private boolean refresh;

  private transient Reply reply;

  public Fetch(NodeHandle sender, Id id, String store, long onum) {
    super(RawMessageType.FETCH);
    this.sender = sender;
    this.id = id;
    this.store = store;
    this.onum = onum;
  }

  /** The sender of this message. */
  public NodeHandle sender() {
    return sender;
  }

  /** The random id of this message. */
  public Id id() {
    return id;
  }

  /** The store where the requested object resides. */
  public String store() {
    return store;
  }

  /** The object number of the requested object. */
  public long onum() {
    return onum;
  }

  /** The reply message. */
  public Reply reply() {
    return reply;
  }

  /** Sets the reply message. */
  public void reply(Reply reply) {
    this.reply = reply;
  }

  @Override
  public int getPriority() {
    return MEDIUM_PRIORITY;
  }

  @Override
  public void dispatch(Disseminator disseminator) {
    disseminator.fetch(this);
  }

  @Override
  public String toString() {
    return store + "/" + onum;
  }

  @Override
  public void serialize(OutputBuffer buf) throws IOException {
    buf.writeShort(id.getType());
    id.serialize(buf);
    buf.writeUTF(store);
    buf.writeLong(onum);
    buf.writeBoolean(refresh);
  }

  /**
   * Deserialization constructor.
   */
  public Fetch(InputBuffer buf, Endpoint endpoint, NodeHandle sender)
      throws IOException {
    super(RawMessageType.FETCH);
    this.sender = sender;
    id = endpoint.readId(buf, buf.readShort());
    store = buf.readUTF();
    onum = buf.readLong();
    refresh = buf.readBoolean();
  }

  /**
   * A reply to a Fetch message. Should carry the object requested by the
   * original fetch message.
   */
  public static class Reply extends AbstractRawMessage {

    private final Id id;
    private final String store;
    private final long onum;
    private final ObjectGlob glob;

    public Reply(Fetch parent, ObjectGlob glob) {
      super(RawMessageType.FETCH_REPLY);
      id = parent.id();
      store = parent.store();
      onum = parent.onum();
      this.glob = glob;
    }

    /** The glob returned. */
    public ObjectGlob glob() {
      return glob;
    }

    /** The id of this message. */
    public Id id() {
      return id;
    }

    /** The store where the requested object resides. */
    public String store() {
      return store;
    }

    /** The object number of the requested object. */
    public long onum() {
      return onum;
    }

    @Override
    public int getPriority() {
      return MEDIUM_PRIORITY;
    }

    @Override
    public void dispatch(Disseminator disseminator) {
      disseminator.fetch(this);
    }

    @Override
    public void serialize(OutputBuffer buf) throws IOException {
      DataOutputBuffer out = new DataOutputBuffer(buf);
      out.writeShort(id.getType());
      id.serialize(out);
      out.writeUTF(store);
      out.writeLong(onum);
      glob.write(out);
    }

    /**
     * Deserialization constructor.
     */
    public Reply(InputBuffer buf, Endpoint endpoint) throws IOException {
      super(RawMessageType.FETCH_REPLY);

      DataInputBuffer in = new DataInputBuffer(buf);
      id = endpoint.readId(in, in.readShort());
      store = in.readUTF();
      onum = in.readLong();

      ObjectGlob glob;
      try {
        glob = new ObjectGlob(in);
        glob.verifySignature(Worker.getWorker().getStore(store).getPublicKey());
      } catch (GeneralSecurityException e) {
        glob = null;
      }
      this.glob = glob;
    }

  }

}
