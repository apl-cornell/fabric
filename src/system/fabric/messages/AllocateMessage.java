package fabric.messages;

import java.io.*;

import fabric.worker.RemoteStore;
import fabric.worker.debug.Timing;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.net.UnreachableNodeException;

/**
 * An <code>AllocateMessage</code> represents a request to allocate a number
 * of object IDs at a store.
 */
public final class AllocateMessage
           extends Message<AllocateMessage.Response>
        implements MessageToStore 
{

  //////////////////////////////////////////////////////////////////////////////
  // message  contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  /** The number of object IDs to allocate. */
  public final int num;

  public AllocateMessage(int num) {
    super(MessageType.ALLOCATE_ONUMS);
    this.num = num;
  }

  //////////////////////////////////////////////////////////////////////////////
  // response contents                                                        //
  //////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public long[] oids;

    public Response(long[] onums) {
      this.oids = onums;
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // visitor methods                                                          //
  //////////////////////////////////////////////////////////////////////////////

  public Response dispatch(MessageToStoreHandler h) throws FabricException {
    return h.handle(this);
  }
  
  //////////////////////////////////////////////////////////////////////////////
  // convenience method for sending                                           //
  //////////////////////////////////////////////////////////////////////////////

  public Response send(RemoteStore store) throws UnreachableNodeException {
    try {
      Timing.STORE.begin();
      return send(store, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from store.", e);
    } finally {
      Timing.STORE.end();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // serialization cruft                                                      //
  //////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeInt(num);
  }

  /* readMessage */
  protected AllocateMessage(DataInput in) throws IOException {
    this(in.readInt());
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeInt(r.oids.length);
    for (long oid : r.oids) out.writeLong(oid);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    long[] oids = new long[in.readInt()];
    for (int i = 0; i < oids.length; i++)
      oids[i] = in.readLong();

    return new Response(oids);
  }

}
