package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.RemoteStore;
import fabric.common.exceptions.*;
import fabric.common.exceptions.InternalError;
import fabric.store.MessageHandlerThread;
import fabric.dissemination.Glob;

/**
 * A <code>DissemReadMessage</code> represents a request from a dissemination
 * node to read an object at a store. This implicitly subscribes the worker to
 * receive the next update to the object.
 */
public final class DissemReadMessage extends
    Message<RemoteStore, DissemReadMessage.Response> {
  public static class Response implements Message.Response {

    public final Glob glob;

    /**
     * Used by the store to create a read-message response.
     */
    public Response(Glob glob) {
      this.glob = glob;
    }

    /**
     * Deserialization constructor, used by the worker.
     * 
     * @param store
     *                The store from which the response is being read.
     * @param in
     *                the input stream from which to read the response.
     */
    Response(RemoteStore store, DataInput in) throws IOException {
      Glob glob;
      try {
        glob = new Glob(store.getPublicKey(), in);
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
   * Creates a read request for a worker.
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
   * @see fabric.messages.Message#dispatch(fabric.store.MessageHandlerThread)
   */
  @Override
  public Response dispatch(MessageHandlerThread w) throws AccessException {
    return w.handle(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#send(fabric.worker.Store, boolean)
   */
  public Response send(RemoteStore store) throws FetchException {
    try {
      return send(store, false);
    } catch (FetchException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from store.", e);
    }
  }

  @Override
  public Response response(RemoteStore c, DataInput in) throws IOException {
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
