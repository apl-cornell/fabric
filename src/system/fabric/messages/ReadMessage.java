package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.ObjectGroup;
import fabric.common.WarrantyGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.Pair;

/**
 * A <code>ReadMessage</code> represents a request from a worker to read an
 * object at a store.
 */
public class ReadMessage extends Message<ReadMessage.Response, AccessException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  /** The onum of the object to read. */
  public final long onum;

  public ReadMessage(long onum) {
    super(MessageType.READ_ONUM, AccessException.class);
    this.onum = onum;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {

    public final Pair<ObjectGroup, WarrantyGroup> group;

    public Response(Pair<ObjectGroup, WarrantyGroup> group) {
      this.group = group;
    }

  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity client, MessageHandler h)
      throws ProtocolError, AccessException {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeLong(onum);
  }

  /* readMessage */
  protected ReadMessage(DataInput in) throws IOException {
    this(in.readLong());
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    if (r.group != null) {
      out.writeBoolean(true);
      r.group.first.write(out);
      r.group.second.write(out);
    } else out.writeBoolean(false);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    Pair<ObjectGroup, WarrantyGroup> group = null;
    if (in.readBoolean()) {
      ObjectGroup objectGroup = new ObjectGroup(in);
      WarrantyGroup warrantyGroup = new WarrantyGroup(in);
      group = new Pair<>(objectGroup, warrantyGroup);
    }

    return new Response(group);
  }
}
