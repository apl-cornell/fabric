package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.exceptions.AccessException;
import fabric.common.net.RemoteIdentity;
import fabric.worker.remote.RemoteWorker;

public class AbortStageMessage
    extends Message<AbortStageMessage.Response, AccessException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  /** The tid for the transaction that is aborting. */
  public final long tid;

  public AbortStageMessage(long tid) {
    super(MessageType.ABORT_STAGE, AccessException.class);
    this.tid = tid;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
    public Response() {
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client,
      MessageHandler h) throws AccessException {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeLong(tid);
  }

  /* readMessage */
  protected AbortStageMessage(DataInput in) throws IOException {
    this(in.readLong());
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) {
  }

  @Override
  protected Response readResponse(DataInput in) {
    return new Response();
  }
}
