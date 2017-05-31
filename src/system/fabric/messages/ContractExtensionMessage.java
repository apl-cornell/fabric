package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.messages.Message.NoException;
import fabric.store.DelayedExtension;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>ContractExtensionMessage</code> represents a request to
 * refresh a contract's expiry at the store.
 */
public class ContractExtensionMessage
    extends Message<ContractExtensionMessage.Response, NoException> {
  /* message contents */

  /** The extensions to be run. */
  public final List<DelayedExtension> extensions;

  /**
   * Used to refresh expiries of contracts.
   */
  public ContractExtensionMessage(List<DelayedExtension> extensions) {
    super(MessageType.CONTRACT_EXTENSION, NoException.class);
    this.extensions = extensions;
  }

  /** Response */
  public static class Response implements Message.Response {
    /**
     * Creates a Response indicating extension has been queued.
     */
    public Response() {
    }
  }

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client,
      MessageHandler h) throws ProtocolError {
    return h.handle(client, this);
  }

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    // Serialize onum.
    out.writeInt(extensions.size());
    for (DelayedExtension extension : extensions) {
      out.writeLong(extension.time);
      out.writeLong(extension.onum);
    }
  }

  /* readMessage */
  protected ContractExtensionMessage(DataInput in) throws IOException {
    super(MessageType.CONTRACT_EXTENSION, NoException.class);
    int size = in.readInt();
    extensions = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      long time = in.readLong();
      long onum = in.readLong();
      extensions.add(new DelayedExtension(time, onum));
    }
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    return new Response();
  }
}
