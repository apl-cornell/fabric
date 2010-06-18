package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.ObjectGroup;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.dissemination.Glob;
import fabric.lang.security.NodePrincipal;

/**
 * Represents push notification that an object has been updated.
 */
public class ObjectUpdateMessage
     extends Message<ObjectUpdateMessage.Response>
  implements MessageToWorker
{
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

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

  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public final boolean resubscribe;

    public Response(boolean resubscribe) {
      this.resubscribe = resubscribe;
    }

  }

  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  public Response dispatch(MessageToWorkerHandler h, NodePrincipal p) throws FabricException {
    return h.handle(p, this);
  }

  //////////////////////////////////////////////////////////////////////////////
  // serialization cruft                                                      //
  //////////////////////////////////////////////////////////////////////////////

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
    super(MessageType.OBJECT_UPDATE);

    this.onum = in.readLong();

    if (in.readBoolean()) {
      this.store = in.readUTF();
      this.glob  = new Glob(in);
      this.group = null;
    } else {
      this.store = null;
      this.glob  = null;
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
