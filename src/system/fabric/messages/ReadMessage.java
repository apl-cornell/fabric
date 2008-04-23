package fabric.messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.common.AccessException;
import fabric.common.FabricException;
import fabric.common.FetchException;
import fabric.common.InternalError;
import fabric.common.SerializedObject;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.core.Worker;
import fabric.lang.Object.$Impl;

/**
 * A <code>ReadMessage</code> represents a request to read an object at a
 * core.
 */
public final class ReadMessage extends Message<ReadMessage.Response> {
  public static class Response implements Message.Response {

    public final LongKeyMap<SerializedObject> related;

    /**
     * The serialized result of the read message.
     */
    public final SerializedObject serializedResult;

    private transient final Core core;

    /**
     * Used by the core to create a read-message response.
     */
    public Response(SerializedObject obj, LongKeyMap<SerializedObject> group) {
      this.serializedResult = obj;
      this.related = group;
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
    Response(Core core, ObjectInputStream in) throws IOException {
      this.core = core;
      int size = in.readInt();
      this.related = new LongKeyHashMap<SerializedObject>(size);
      for (int i = 0; i < size; i++) {
        related.put(in.readLong(), new SerializedObject(in));
      }
      this.serializedResult = new SerializedObject(in);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.messages.Message.Response#write(java.io.ObjectOutputStream)
     */
    public void write(ObjectOutputStream out) throws IOException {
      out.writeInt(related.size());
      for (LongKeyMap.Entry<SerializedObject> entry : related.entrySet()) {
        out.writeLong(entry.getKey());
        entry.getValue().write(out);
      }
      
      serializedResult.write(out);
    }
    
    public $Impl result() throws ClassNotFoundException {
      return serializedResult.deserialize(core);
    }
  }

  /**
   * The onum of the object to read.
   */
  public final long onum;

  public ReadMessage(long onum) {
    super(MessageType.READ_ONUM);
    this.onum = onum;
  }

  /**
   * Deserialization constructor.
   */
  protected ReadMessage(ObjectInputStream in) throws IOException {
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
   * @see fabric.messages.Message#send(fabric.client.Core)
   */
  @Override
  public Response send(RemoteCore core) throws FetchException {
    try {
      return super.send(core);
    } catch (FetchException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from core.", e);
    }
  }

  @Override
  public Response response(Core c, ObjectInputStream in) throws IOException {
    return new Response(c, in);
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#write(java.io.ObjectOutputStream)
   */
  @Override
  public void write(ObjectOutputStream out) throws IOException {
    out.writeLong(onum);
  }

}
