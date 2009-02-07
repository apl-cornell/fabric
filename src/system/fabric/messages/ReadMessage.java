package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.common.*;
import fabric.common.InternalError;
import fabric.core.Worker;
import fabric.lang.Object.$Impl;

/**
 * A <code>ReadMessage</code> represents a request from a client to read an
 * object at a core.
 */
public final class ReadMessage extends Message<ReadMessage.Response> {
  public static class Response implements Message.Response {

    public final ObjectGroup group;

    private transient final Core core;

    /**
     * Used by the core to create a read-message response.
     */
    public Response(ObjectGroup group) {
      this.group = group;
      this.core = null;
    }

    /**
     * Deserialization constructor, used by the client.
     * 
     * @param core
     *                The core from which the response is being read.
     * @param in
     *                the input stream from which to read the response.
     */
    Response(Core core, DataInput in) throws IOException {
      this.core = core;
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
    
    public $Impl result() throws ClassNotFoundException {
      return group.obj().deserialize(core);
    }
  }

  /**
   * The onum of the object to read.
   */
  public final long onum;

  /**
   * Creates a read request for a client.
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
   * @see fabric.messages.Message#dispatch(fabric.core.Worker)
   */
  @Override
  public Response dispatch(Worker w) throws AccessException, ProtocolError {
    return w.handle(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#send(fabric.client.Core, boolean)
   */
  public Response send(RemoteCore core) throws FetchException {
    try {
      return send(core, true);
    } catch (FetchException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from core.", e);
    }
  }

  @Override
  public Response response(Core c, DataInput in) throws IOException {
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
