package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.ProtocolError;
import fabric.lang.security.Principal;
import fabric.worker.memoize.CallResult;

/**
 * A <code>ReuseCallMessage</code> represents a request from a worker to reuse a
 * value of a previously computed call at a store.
 */
public class ReuseCallMessage extends Message<ReuseCallMessage.Response,
       AccessException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  /** The id of the call to reuse. */
  public final byte[] id;

  public ReuseCallMessage(byte[] id) {
    super(MessageType.REUSE_CALL, AccessException.class);
    this.id = id;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {

    public final CallResult result;

    public Response(CallResult result) {
      this.result = result;
    }

  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(Principal p, MessageHandler h) throws ProtocolError,
      AccessException {
    return h.handle(p, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeInt(id.length);
    out.write(id);
  }

  /* readMessage */
  protected ReuseCallMessage(DataInput in) throws IOException {
    super(MessageType.REUSE_CALL, AccessException.class);
    byte[] call = new byte[in.readInt()];
    in.readFully(call);
    this.id = call;
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    if (r.result != null) {
      out.writeBoolean(true);
      r.result.write(out);
    } else out.writeBoolean(false);
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    CallResult result = in.readBoolean() ? new CallResult(in) : null;
    return new Response(result);
  }
}
