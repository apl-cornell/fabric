package fabric.messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.common.NoSuchCoreError;
import fabric.common.ProtocolError;
import fabric.core.Worker;

/**
 * A <code>ConnectMessage</code> is used by a client to connect to a
 * specificcore on a core node.
 */
public final class ConnectMessage extends Message<ConnectMessage.Response> {

  public static class Response implements Message.Response {
  }

  public final String coreName;

  /**
   * Connects to the core at the given host name.
   * 
   * @param name
   *                The host name for the core to which the connection is being
   *                made.
   */
  public ConnectMessage(String name) {
    super(Response.class);
    this.coreName = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#dispatch(fabric.core.Worker)
   */
  @Override
  public Response dispatch(Worker w) throws ProtocolError, NoSuchCoreError {
    return w.handle(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#send(java.io.ObjectInputStream,
   *      java.io.ObjectOutputStream)
   */
  @Override
  public Response send(ObjectInputStream in, ObjectOutputStream out)
      throws IOException, ProtocolError, NoSuchCoreError {
    try {
      return super.send(in, out);
    } catch (ProtocolError e) {
      throw e;
    } catch (NoSuchCoreError e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from core.", e);
    }
  }
}
