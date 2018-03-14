package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.exceptions.ProtocolError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongSet;
import fabric.messages.Message.NoException;
import fabric.worker.remote.RemoteWorker;

/**
 * A <code>ContractExtensionMessage</code> represents a request to
 * refresh a contract's expiry at the store.
 */
public class ContractExtensionMessage
    extends Message<ContractExtensionMessage.Response, NoException> {
  /* message contents */

  /** The extensions to be run. */
  public final LongSet extensions;

  /**
   * Used to refresh expiries of contracts.
   */
  public ContractExtensionMessage(LongSet extensions) {
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
    for (LongIterator it = extensions.iterator(); it.hasNext();) {
      out.writeLong(it.next());
    }
  }

  /* readMessage */
  protected ContractExtensionMessage(DataInput in) throws IOException {
    super(MessageType.CONTRACT_EXTENSION, NoException.class);
    int size = in.readInt();
    extensions = new LongHashSet(size);
    for (int i = 0; i < size; i++) {
      long onum = in.readLong();
      extensions.add(onum);
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
