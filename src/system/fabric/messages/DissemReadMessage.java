package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.WarrantyGlob;
import fabric.worker.remote.RemoteWorker;

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

    public final Pair<ObjectGlob, WarrantyGlob> globs;

    public Response(Pair<ObjectGlob, WarrantyGlob> glob) {
      this.globs = glob;
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
    out.writeLong(onum);
  }

  /* readMessage */
  protected DissemReadMessage(DataInput in) throws IOException {
    this(in.readLong());
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    ObjectGlob objectGlob = new ObjectGlob(in);
    WarrantyGlob warrantyGlob = in.readBoolean() ? new WarrantyGlob(in) : null;
    return new Response(new Pair<>(objectGlob, warrantyGlob));
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    r.globs.first.write(out);
    if (r.globs.second != null) {
      out.writeBoolean(true);
      r.globs.second.write(out);
    } else out.writeBoolean(false);
  }

}
