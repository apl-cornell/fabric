package fabric.messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.client.UnreachableCoreException;
import fabric.common.AccessException;
import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.core.Worker;

/**
 * An <code>AllocateMessage</code> represents a request to allocate a number
 * of object IDs at a core.
 */
public final class AllocateMessage extends Message<AllocateMessage.Response> {

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
    Response(Core core, ObjectInputStream in) throws IOException {
      oids = new long[in.readInt()];
      for (int i = 0; i < oids.length; i++)
        oids[i] = in.readLong();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.messages.Message.Response#write(java.io.ObjectOutputStream)
     */
    public void write(ObjectOutputStream out) throws IOException {
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
    super(MessageType.ALLOCATE_ONUMS, Response.class);
    this.num = num;
  }

  /**
   * Deserialization constructor.
   */
  AllocateMessage(ObjectInputStream in) throws IOException {
    this(in.readInt());
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
   * @see fabric.messages.Message#send(fabric.client.Core)
   */
  @Override
  public Response send(RemoteCore core) throws UnreachableCoreException {
    try {
      return super.send(core);
    } catch (UnreachableCoreException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from core.", e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#write(java.io.ObjectOutputStream)
   */
  @Override
  public void write(ObjectOutputStream out) throws IOException {
    out.writeInt(num);
  }

}
