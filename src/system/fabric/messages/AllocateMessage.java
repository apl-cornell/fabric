package fabric.messages;

import java.io.*;

import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.client.debug.Timing;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.ProtocolError;
import fabric.core.Worker;
import fabric.net.UnreachableNodeException;

/**
 * An <code>AllocateMessage</code> represents a request to allocate a number
 * of object IDs at a core.
 */
public final class AllocateMessage extends
    Message<RemoteCore, AllocateMessage.Response> {

  public static class Response implements Message.Response {
    public long[] oids;

    public Response(long[] onums) {
      this.oids = onums;
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
      oids = new long[in.readInt()];
      for (int i = 0; i < oids.length; i++)
        oids[i] = in.readLong();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.messages.Message.Response#write(java.io.DataOutput)
     */
    public void write(DataOutput out) throws IOException {
      out.writeInt(oids.length);
      for (long oid : oids) out.writeLong(oid);
    }
  }

  public final int num;

  /**
   * @param num
   *                The number of object IDs to allocate.
   */
  public AllocateMessage(int num) {
    super(MessageType.ALLOCATE_ONUMS);
    this.num = num;
  }

  /**
   * Deserialization constructor.
   */
  protected AllocateMessage(DataInput in) throws IOException {
    this(in.readInt());
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
  public Response send(RemoteCore core) throws UnreachableNodeException {
    try {
      Timing.CORE.begin();
      return send(core, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from core.", e);
    } finally {
      Timing.CORE.end();
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
    out.writeInt(num);
  }

}
