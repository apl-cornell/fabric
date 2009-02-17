package fabric.client.remote.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.remote.RemoteClient;
import fabric.lang.Object.$Proxy;
import fabric.messages.Message;
import fabric.messages.Message.Response;

public abstract class InterClientMessage<R extends Response> extends
    Message<RemoteClient, R> {

  public InterClientMessage(MessageType messageType) {
    super(messageType);
  }

  /**
   * Used for passing object references between clients.
   */
  public static void writeRef($Proxy ref, DataOutput out) throws IOException {
    out.writeUTF(ref.$getCore().name());
    out.writeLong(ref.$getOnum());
  }

  /**
   * Used for passing object references between clients.
   */
  public static $Proxy readRef(DataInput in) throws IOException {
    Core core = Client.getClient().getCore(in.readUTF());
    return new $Proxy(core, in.readLong());
  }
}
