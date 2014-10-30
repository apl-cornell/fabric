package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import fabric.common.net.RemoteIdentity;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.remote.RemoteWorker;

public class CommitTransactionMessage
extends
Message<CommitTransactionMessage.Response, TransactionCommitFailedException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final long transactionID;
  public final long commitTime;
  public final boolean readOnly;

  public CommitTransactionMessage(long transactionID, long commitTime) {
    this(transactionID, commitTime, false);
  }

  public CommitTransactionMessage(long transactionID, long commitTime, boolean readOnly) {
    super(MessageType.COMMIT_TRANSACTION,
        TransactionCommitFailedException.class);
    this.transactionID = transactionID;
    this.commitTime = commitTime;
    this.readOnly = readOnly;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(RemoteIdentity<RemoteWorker> client, MessageHandler h)
      throws TransactionCommitFailedException {
    return h.handle(client, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeLong(transactionID);
    out.writeLong(commitTime);
    out.writeBoolean(readOnly);
  }

  /* readMessage */
  protected CommitTransactionMessage(DataInput in) throws IOException {
    super(MessageType.COMMIT_TRANSACTION,
        TransactionCommitFailedException.class);

    this.transactionID = in.readLong();
    this.commitTime = in.readLong();
    this.readOnly = in.readBoolean();
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    return new Response();
  }

}
