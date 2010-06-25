package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.ObjectGroup;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.FetchException;
import fabric.lang.security.NodePrincipal;

/**
 * A <code>ReadMessage</code> represents a request from a worker to read an
 * object at a store.
 */
public class ReadMessage
     extends Message<ReadMessage.Response, FetchException>
  implements MessageToStore
{
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  /** The onum of the object to read. */
  public final long onum;

  public ReadMessage(long onum) {
    super(MessageType.READ_ONUM, FetchException.class);
    this.onum = onum;
  }

  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {

    public final ObjectGroup group;

    public Response(ObjectGroup group) {
      this.group = group;
    }

  }

  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  public Response dispatch(NodePrincipal p, MessageToStoreHandler h) throws FabricException {
    return h.handle(p, this);
  }

  //////////////////////////////////////////////////////////////////////////////
  // serialization cruft                                                      //
  //////////////////////////////////////////////////////////////////////////////

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
      r.group.write(out);
    }
    else
      out.writeBoolean(false);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    ObjectGroup group = in.readBoolean() ? new ObjectGroup(in) : null;
    return new Response(group);
  }
}
