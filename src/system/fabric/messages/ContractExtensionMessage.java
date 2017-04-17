package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.messages.Message.NoException;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>ContractExtensionMessage</code> represents a request to
 * refresh a contract's expiry at the store.
 */
public class ContractExtensionMessage
    extends Message<ContractExtensionMessage.Response, NoException> {
  /* message contents */

  /** The onum that should be extended. */
  public final List<Long> onums;

  /**
   * Used to refresh expiries of contracts.
   */
  public ContractExtensionMessage(List<Long> onums) {
    super(MessageType.CONTRACT_EXTENSION, NoException.class);
    this.onums = onums;
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
    out.writeInt(onums.size());
    for (long onum : onums) {
      out.writeLong(onum);
    }
  }

  /* readMessage */
  protected ContractExtensionMessage(DataInput in) throws IOException {
    super(MessageType.CONTRACT_EXTENSION, NoException.class);
    int size = in.readInt();
    onums = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      onums.add(in.readLong());
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
