package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.FabricException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.net.RemoteNode;
import fabric.net.UnreachableNodeException;
import fabric.store.MessageHandlerThread;
import fabric.worker.debug.Timing;

/**
 * A <code>StalenessCheckMessage</code> represents a request to a store to check
 * whether a given set of objects is still fresh.
 */
public class StalenessCheckMessage extends
    Message<RemoteNode, StalenessCheckMessage.Response> {

  public static class Response implements Message.Response {
    public final List<SerializedObject> staleObjects;

    public Response(List<SerializedObject> staleObjects) {
      this.staleObjects = staleObjects;
    }

    /**
     * Deserialization constructor, used by the worker.
     * 
     * @param node
     *          The node from which the response is being read.
     * @param in
     *          the input stream from which to read the response.
     */
    Response(RemoteNode node, DataInput in) throws IOException {
      int size = in.readInt();
      this.staleObjects = new ArrayList<SerializedObject>(size);
      for (int i = 0; i < size; i++) {
        staleObjects.add(new SerializedObject(in));
      }
    }

    public void write(DataOutput out) throws IOException {
      out.writeInt(staleObjects.size());
      for (SerializedObject obj : staleObjects) {
        obj.write(out);
      }
    }
  }

  public final LongKeyMap<Integer> versions;

  public StalenessCheckMessage(LongKeyMap<Integer> versions) {
    super(MessageType.STALENESS_CHECK);
    this.versions = versions;
  }

  /**
   * Deserialization constructor. Used only by the store.
   */
  protected StalenessCheckMessage(DataInput in) throws IOException {
    super(MessageType.STALENESS_CHECK);
    int size = in.readInt();
    versions = new LongKeyHashMap<Integer>(size);
    for (int i = 0; i < size; i++)
      versions.put(in.readLong(), in.readInt());
  }

  @Override
  public Response dispatch(MessageHandlerThread handler) throws AccessException {
    return new Response(handler.handle(this));
  }

  @Override
  public Response response(RemoteNode node, DataInput in) throws IOException {
    return new Response(node, in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(versions.size());
    for (LongKeyMap.Entry<Integer> entry : versions.entrySet()) {
      out.writeLong(entry.getKey());
      out.writeInt(entry.getValue());
    }
  }

  public Response send(RemoteNode node) {
    try {
      Timing.STORE.begin();
      return super.send(node, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from node.", e);
    } finally {
      Timing.STORE.end();
    }
  }

}
