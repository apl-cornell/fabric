package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.worker.RemoteStore;
import fabric.common.*;
import fabric.common.exceptions.*;
import fabric.common.exceptions.InternalError;
import fabric.store.MessageHandlerThread;

/**
 * A <code>ReadMessage</code> represents a request from a worker to read an
 * object at a store.
 */
public final class ReadMessage extends
    Message<RemoteStore, ReadMessage.Response> {
  public static class Response implements Message.Response {

    public final ObjectGroup group;

    /**
     * Used by the store to create a read-message response.
     */
    public Response(ObjectGroup group) {
      this.group = group;
    }

    /**
     * Deserialization constructor, used by the worker.
     * 
     * @param in
     *                the input stream from which to read the response.
     */
    Response(DataInput in) throws IOException {
      if (in.readBoolean())
        this.group = new ObjectGroup(in);
      else this.group = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.messages.Message.Response#write(java.io.DataOutput)
     */
    public void write(DataOutput out) throws IOException {
      if (group != null) {
        out.writeBoolean(true);
        group.write(out);
      } else out.writeBoolean(false);
    }
  }

  /**
   * The onum of the object to read.
   */
  public final long onum;

  /**
   * Creates a read request for a worker.
   */
  public ReadMessage(long onum) {
    super(MessageType.READ_ONUM);
    this.onum = onum;
  }

  /**
   * Deserialization constructor.
   */
  protected ReadMessage(DataInput in) throws IOException {
    this(in.readLong());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#dispatch(fabric.store.MessageHandlerThread)
   */
  @Override
  public Response dispatch(MessageHandlerThread w) throws AccessException, ProtocolError {
    return w.handle(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#send(fabric.worker.Store, boolean)
   */
  public Response send(RemoteStore store) throws FetchException {
    try {
      return send(store, true);
    } catch (FetchException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from store.", e);
    }
  }

  @Override
  public Response response(RemoteStore c, DataInput in) throws IOException {
    return new Response(in);
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
