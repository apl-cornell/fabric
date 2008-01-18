package fabric.messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import fabric.client.Core;
import fabric.client.RemoteCore;
import fabric.client.UnreachableCoreException;
import fabric.common.AccessError;
import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.core.SerializedObject;
import fabric.core.Worker;
import fabric.lang.Object.$Impl;

/**
 * A <code>ReadMessage</code> represents a request to read an object at a
 * core.
 */
public final class ReadMessage extends Message<ReadMessage.Response> {
  public static class Response implements Message.Response {

    public final Map<Long, SerializedObject> related;

    /**
     * The serialized result of the read message. This will only be non-null on
     * the core. The client should use the <code>result</code> field instead.
     */
    public final SerializedObject serializedResult;

    /**
     * The unserialized result of the read message. This will only be non-null
     * on the client. The core should use the <code>result</code> field
     * instead.
     */
    public final $Impl result;

    /**
     * Used by the core to create a read-message response.
     */
    public Response(SerializedObject obj, Map<Long, SerializedObject> group) {
      this.serializedResult = obj;
      this.related = group;
      this.result = null;
    }

    /**
     * Deserialization constructor, used by the client.
     * 
     * @param core
     *                The core from which the response is being read.
     * @param in
     *                the input stream from which to read the response.
     */
    Response(Core core, ObjectInputStream in) throws ClassNotFoundException,
        IOException {
      this.serializedResult = null;

      int size = in.readInt();
      this.related = new HashMap<Long, SerializedObject>(size);
      for (int i = 0; i < size; i++) {
        related.put(in.readLong(), new SerializedObject(in));
      }
      this.result = SerializedObject.readImpl(core, in);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.messages.Message.Response#write(java.io.ObjectOutputStream)
     */
    public void write(ObjectOutputStream out) throws IOException {
      out.writeInt(related.size());
      for (Map.Entry<Long, SerializedObject> entry : related.entrySet()) {
        out.writeLong(entry.getKey());
        entry.getValue().write(out);
      }
      
      serializedResult.write(out);
    }
  }

  /**
   * The onum of the object to read.
   */
  public final long onum;

  public ReadMessage(long onum) {
    super(MessageType.READ_ONUM, Response.class);
    this.onum = onum;
  }

  /**
   * Deserialization constructor.
   */
  ReadMessage(ObjectInputStream in) throws IOException {
    this(in.readLong());
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#dispatch(fabric.core.Worker)
   */
  @Override
  public Response dispatch(Worker w) throws AccessError {
    return w.handle(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#send(fabric.client.Core)
   */
  @Override
  public Response send(RemoteCore core) throws AccessError,
      UnreachableCoreException {
    try {
      return super.send(core);
    } catch (AccessError e) {
      throw e;
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
    out.writeLong(onum);
  }
}
