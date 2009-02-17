package fabric.client.remote.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.client.UnreachableNodeException;
import fabric.client.remote.RemoteClient;
import fabric.client.remote.Worker;
import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.lang.Object.$Proxy;
import fabric.messages.Message;

public class RemoteCallMessage extends
    InterClientMessage<RemoteCallMessage.Response> {

  private long tid;
  private int commitSync;
  private int callTransactionNestDepth;
  private $Proxy receiver;
  private String methodName;
  private $Proxy[] args;

  public static class Response implements Message.Response {
    public final $Proxy result;

    public Response($Proxy result) {
      this.result = result;
    }

    /**
     * Deserialization constructor.
     * 
     * @param client
     *          The client from which the response is being read.
     * @param in
     *          The input stream from which to read the response.
     */
    Response(RemoteClient client, DataInput in) throws IOException {
      this.result = readRef(in);
    }

    public void write(DataOutput out) throws IOException {
      writeRef(result, out);
    }
  }

  /**
   * @param tid
   *          The identifier for the top-level transaction. This should be the
   *          same across all sub-tranactions across all hosts. When committing
   *          the top-level transaction, this tid will be used by the cores to
   *          identify prepare requests that are part of the same transaction.
   * @param commitSync
   *          The number of times the callee should commit nested transactions
   *          to synchronize its transaction stack with the caller.
   * @param callTransactionNestDepth
   *          The nesting depth for the transaction in which the call will be
   *          executed. A callTransactionNestDepth of 0 indicates that the call
   *          will be executed in a top-level transaction that will immediately
   *          commit to the core.
   * @param receiver
   *          The object that is receiving the call.
   * @param methodName
   *          The name of the method to be called.
   * @param args
   *          The arguments to the method.
   */
  public RemoteCallMessage(long tid, int commitSync,
      int callTransactionNestDepth, $Proxy receiver, String methodName,
      $Proxy[] args) {
    super(MessageType.REMOTE_CALL);

    this.tid = tid;
    this.commitSync = commitSync;
    this.callTransactionNestDepth = callTransactionNestDepth;
    this.receiver = receiver;
    this.methodName = methodName;
    this.args = args;
  }

  /**
   * Deserialization constructor.
   */
  public RemoteCallMessage(DataInput in) throws IOException {
    this(in.readLong(), in.readInt(), in.readInt(), readRef(in), in.readUTF(),
        readArgs(in));
  }

  private static $Proxy[] readArgs(DataInput in) throws IOException {
    $Proxy[] result = new $Proxy[in.readInt()];
    for (int i = 0; i < result.length; i++)
      result[i] = readRef(in);
    return result;
  }

  @Override
  public Response dispatch(Worker handler) {
    // XXX TODO
    return null;
  }

  public Response send(RemoteClient client) throws UnreachableNodeException {
    try {
      return super.send(client, true);
    } catch (UnreachableNodeException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from client.", e);
    }
  }

  @Override
  public Response response(RemoteClient c, DataInput in) throws IOException {
    return new Response(c, in);
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeLong(tid);
    out.writeInt(commitSync);
    out.writeInt(callTransactionNestDepth);
    writeRef(receiver, out);
    out.writeUTF(methodName);
    out.writeInt(args.length);
    for (int i = 0; i < args.length; i++)
      writeRef(args[i], out);
  }

}
