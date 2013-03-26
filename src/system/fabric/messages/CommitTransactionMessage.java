package fabric.messages;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fabric.common.SemanticWarranty;
import fabric.lang.security.Principal;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.TransactionCommitFailedException;

public class CommitTransactionMessage
    extends
    Message<CommitTransactionMessage.Response, TransactionCommitFailedException> {
  // ////////////////////////////////////////////////////////////////////////////
  // message contents //
  // ////////////////////////////////////////////////////////////////////////////

  public final long transactionID;
  public final long commitTime;
  public final boolean readOnly;
  public final Set<SemanticWarrantyRequest> requests;

  public CommitTransactionMessage(long transactionID, long commitTime) {
    this(transactionID, commitTime, null, false);
  }

  public CommitTransactionMessage(long transactionID, long commitTime, boolean readOnly) {
    this(transactionID, commitTime, null, readOnly);
  }

  public CommitTransactionMessage(long transactionID, long commitTime,
      Set<SemanticWarrantyRequest> requests, boolean readOnly) {
    super(MessageType.COMMIT_TRANSACTION,
        TransactionCommitFailedException.class);
    this.transactionID = transactionID;
    this.commitTime = commitTime;
    this.requests = requests;
    this.readOnly = readOnly;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // response contents //
  // ////////////////////////////////////////////////////////////////////////////

  public static class Response implements Message.Response {

    private Map<CallInstance, SemanticWarranty> requestResults;

    public Response(Map<CallInstance, SemanticWarranty> requestResults) {
      this.requestResults = requestResults;
    }

    public Map<CallInstance, SemanticWarranty> getResults() {
      return requestResults;
    }
  }

  // ////////////////////////////////////////////////////////////////////////////
  // visitor methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Response dispatch(Principal p, MessageHandler h)
      throws TransactionCommitFailedException {
    return h.handle(p, this);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // serialization cruft //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  protected void writeMessage(DataOutput out) throws IOException {
    out.writeLong(transactionID);
    out.writeLong(commitTime);
    out.writeBoolean(readOnly);
    
    if (requests == null) {
      out.writeInt(0);
    } else {
      out.writeInt(requests.size());
      for (SemanticWarrantyRequest r : requests)
        r.write(out);
    }
  }

  /* readMessage */
  protected CommitTransactionMessage(DataInput in) throws IOException {
    super(MessageType.COMMIT_TRANSACTION,
        TransactionCommitFailedException.class);

    this.transactionID = in.readLong();
    this.commitTime = in.readLong();
    this.readOnly = in.readBoolean();

    int requestsSize = in.readInt();
    this.requests = new HashSet<SemanticWarrantyRequest>(requestsSize);
    for (int i = 0; i < requestsSize; i++)
      this.requests.add(new SemanticWarrantyRequest(in));
  }

  @Override
  protected void writeResponse(DataOutput out, Response r) throws IOException {
    out.writeInt(r.requestResults.size());
    for (Map.Entry<CallInstance, SemanticWarranty> e : r.requestResults.entrySet()) {
      e.getKey().write(out);
      out.writeLong(e.getValue().expiry());
    }
  }

  @Override
  protected Response readResponse(DataInput in) throws IOException {
    int numResponses = in.readInt();
    Map<CallInstance, SemanticWarranty> responses =
      new HashMap<CallInstance, SemanticWarranty>(numResponses);
    for (int i = 0; i < numResponses; i++) {
      CallInstance call = new CallInstance(in);
      responses.put(call, new SemanticWarranty(in.readLong()));
    }
    return new Response(responses);
  }

}
