package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.client.RemoteCore;
import fabric.common.exceptions.*;
import fabric.common.exceptions.InternalError;
import fabric.core.Worker;
import fabric.dissemination.Glob;

/**
 * A <code>DissemReadMessage</code> represents a request from a dissemination
 * node to read an object at a core.
 */
public final class DissemReadMessage extends
    Message<RemoteCore, DissemReadMessage.Response> {
  public static class Response implements Message.Response {

    public final Glob glob;

    /**
     * Used by the core to create a read-message response.
     */
    public Response(Glob glob) {
      this.glob = glob;
    }

    /**
     * Deserialization constructor, used by the client.
     * 
     * @param core
     *                The core from which the response is being read.
     * @param in
     *                the input stream from which to read the response.
     */
    Response(RemoteCore core, DataInput in) throws IOException {
      Glob glob;
      try {
        glob = new Glob(core.getPublicKey(), in);
      } catch (BadSignatureException e) {
        glob = null;
      }
      
      this.glob = glob;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.messages.Message.Response#write(java.io.DataOutput)
     */
    public void write(DataOutput out) throws IOException {
      glob.write(out);
    }
  }

  /**
   * The onum of the object to read.
   */
  public final long onum;

  /**
   * Creates a read request for a client.
   */
  public DissemReadMessage(long onum) {
    super(MessageType.DISSEM_READ_ONUM);
    this.onum = onum;
  }

  /**
   * Deserialization constructor.
   */
  protected DissemReadMessage(DataInput in) throws IOException {
    this(in.readLong());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#dispatch(fabric.core.Worker)
   */
  @Override
  public Response dispatch(Worker w) throws AccessException {
    return w.handle(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#send(fabric.client.Core, boolean)
   */
  public Response send(RemoteCore core) throws FetchException {
    try {
      return send(core, false);
    } catch (FetchException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from core.", e);
    }
  }

  @Override
  public Response response(RemoteCore c, DataInput in) throws IOException {
    return new Response(c, in);
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#write(java.io.DataOutput)
   */
  @Override
  public void write(DataOutput out) throws IOException {
    out.writeLong(onum);
  }

}
