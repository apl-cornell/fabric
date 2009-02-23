package fabric.client.remote.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.client.Client;
import fabric.client.Core;
import fabric.client.UnreachableNodeException;
import fabric.client.remote.RemoteClient;
import fabric.client.remote.Worker;
import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.common.SerializedObject;
import fabric.common.TransactionID;
import fabric.lang.Object.$Impl;
import fabric.messages.Message;

/**
 * Represents a request from a client to read an object owned by another client.
 */
public class ReadMessage extends InterClientMessage<ReadMessage.Response> {

  public final TransactionID tid;
  public final Core core;
  public final long onum;

  public static class Response implements Message.Response {

    public final $Impl obj;

    public Response($Impl obj) {
      this.obj = obj;
    }

    Response(DataInput in) throws IOException {
      if (!in.readBoolean()) {
        this.obj = null;
        return;
      }

      Core core = Client.getClient().getCore(in.readUTF());
      SerializedObject serialized = new SerializedObject(in);
      try {
        this.obj = serialized.deserialize(core);
      } catch (ClassNotFoundException e) {
        throw new InternalError(e);
      }
    }

    public void write(DataOutput out) throws IOException {
      if (obj != null) {
        out.writeBoolean(true);
        out.writeUTF(obj.$getCore().name());
        SerializedObject.write(obj, out);
      } else {
        out.writeBoolean(false);
      }
    }
  }

  public ReadMessage(TransactionID tid, Core core, long onum) {
    super(MessageType.INTERCLIENT_READ);

    this.tid = tid;
    this.core = core;
    this.onum = onum;
  }

  protected ReadMessage(DataInput in) throws IOException {
    this(new TransactionID(in), Client.getClient().getCore(in.readUTF()), in
        .readLong());
  }

  @Override
  public Response dispatch(Worker handler) {
    return handler.handle(this);
  }

  public Response send(RemoteClient remoteClient)
      throws UnreachableNodeException {
    try {
      return super.send(remoteClient, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from client.", e);
    }
  }

  @Override
  public Response response(RemoteClient client, DataInput in)
      throws IOException {
    return new Response(in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    tid.write(out);
    out.writeUTF(core.name());
    out.writeLong(onum);
  }
}
