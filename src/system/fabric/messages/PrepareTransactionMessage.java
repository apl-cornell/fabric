package fabric.messages;

import java.util.*;

import fabric.client.RemoteCore;
import fabric.client.TransactionPrepareFailedException;
import fabric.client.UnreachableCoreException;
import fabric.common.FabricException;
import fabric.common.InternalError;
import fabric.core.SerializedObject;
import fabric.core.Worker;
import fabric.lang.Object.$Impl;

/**
 * A <code>PrepareTransactionMessage</code> represents a transaction request
 * to a core.
 */
public class PrepareTransactionMessage extends
    Message<PrepareTransactionMessage.Response> {

  public static class Response implements Message.Response {
    public final int transactionID;
    public Response(int transactionID) {
      this.transactionID = transactionID;
    }
  }

  public final Collection<SerializedObject> toCreate;
  public final Map<Long, Integer> reads;
  public final Collection<SerializedObject> writes;

  public PrepareTransactionMessage(Collection<$Impl> toCreate,
      Map<Long, Integer> reads, Collection<$Impl> writes) {
    super(Response.class);

    this.toCreate = new HashSet<SerializedObject>();
    if (reads != null)
      this.reads = new HashMap<Long, Integer>(reads);
    else this.reads = new HashMap<Long, Integer>();
    this.writes = new HashSet<SerializedObject>();

    if (toCreate != null) for ($Impl obj : toCreate)
      this.toCreate.add(new SerializedObject(obj));

    if (writes != null) for ($Impl obj : writes)
      this.writes.add(new SerializedObject(obj));
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#dispatch(fabric.core.Worker)
   */
  @Override
  public Response dispatch(Worker w) throws TransactionPrepareFailedException {
    return w.handle(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.messages.Message#send(fabric.client.Core)
   */
  @Override
  public Response send(RemoteCore core) throws UnreachableCoreException, TransactionPrepareFailedException {
    try {
      return super.send(core);
    } catch (UnreachableCoreException e) {
      throw e;
    } catch (TransactionPrepareFailedException e) {
      throw e;
    } catch (FabricException e) {
      throw new InternalError("Unexpected response from core.", e);
    }
  }
}
