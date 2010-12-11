package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.FetchException;
import fabric.dissemination.Glob;
import fabric.lang.security.NodePrincipal;

/**
 * A <code>DissemReadMessage</code> represents a request from a dissemination
 * node to read an object at a store. This implicitly subscribes the worker to
 * receive the next update to the object.
 */
public final class DissemReadMessage
           extends Message<DissemReadMessage.Response, FetchException>
        implements MessageToStore<FabricException>
{
  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  /** The onum of the object to read. */
  public final long onum;

  public DissemReadMessage(long onum) {
    super(MessageType.DISSEM_READ_ONUM, FetchException.class);
    this.onum = onum;
  }

  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {

    public final Glob glob;

    public Response(Glob glob) {
      this.glob = glob;
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

