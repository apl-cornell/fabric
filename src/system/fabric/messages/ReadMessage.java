package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import fabric.common.ObjectGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>ReadMessage</code> represents a request from a worker to read an
 * object at a store.
 */
public class ReadMessage
    extends Message<ReadMessage.Response, AccessException> {
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

    public final Collection<ObjectGroup> groups;

    public Response(Collection<ObjectGroup> groups) {
      this.groups = groups;
    }

  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client,
      MessageHandler h) throws ProtocolError, AccessException {
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
    if (r.groups != null && r.groups.size() > 0) {
      out.writeBoolean(true);
      out.writeInt(r.groups.size());
      for (ObjectGroup group : r.groups) {
        group.write(out);
      }
    } else out.writeBoolean(false);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    if (in.readBoolean()) {
      int size = in.readInt();
      List<ObjectGroup> groups = new ArrayList<>(size);
      for (int i = 0; i < size; i++) {
        groups.add(new ObjectGroup(in));
      }
      return new Response(groups);
    } else {
      return new Response(Collections.<ObjectGroup> emptyList());
    }
  }
}
