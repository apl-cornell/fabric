package fabric.messages;

import java.util.Map;

import fabric.client.RemoteCore;
import fabric.client.UnreachableCoreException;
import fabric.common.AccessError;
import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.core.SerializedObject;
import fabric.core.Worker;

/**
 * A <code>ReadMessage</code> represents a request to read an object at a
 * core.
 */
public final class ReadMessage extends Message<ReadMessage.Response> {
  public static class Response implements Message.Response {

    public Map<Long, SerializedObject> related;
    public SerializedObject result;

    public Response(SerializedObject obj, Map<Long, SerializedObject> group) {
      this.result = obj;
      this.related = group;
    }
  }

  public long onum;

  public ReadMessage(long onum) {
    super(Response.class);
    this.onum = onum;
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
  public Response send(RemoteCore core) throws AccessError, UnreachableCoreException {
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
}
