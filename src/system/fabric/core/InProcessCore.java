package fabric.core;

import java.util.ArrayList;
import java.util.Collection;

import fabric.client.Client;
import fabric.client.RemoteCore;
import fabric.client.TransactionCommitFailedException;
import fabric.client.TransactionPrepareFailedException;
import fabric.common.*;
import fabric.common.InternalError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.core.Node.Core;
import fabric.lang.Object.$Impl;

/**
 * In-process implementation of the Core interface for use when a client is
 * running in-process with a Core.  The operations work directly on the Core's
 * TransactionManager object.
 *
 * @author mdgeorge
 */
public class InProcessCore extends RemoteCore {

  protected TransactionManager tm;

  public InProcessCore(String name, Core c) {
    super(name);
    tm = c.tm;
  }
  
  @Override
  public void abortTransaction(int transactionID) {
    try {
      tm.abortTransaction(Client.getClient().getPrincipal(), transactionID);
    } catch (AccessException e) {
      throw new InternalError(e);
    }
  }

  @Override
  public void commitTransaction(int transactionID)
      throws TransactionCommitFailedException {
    tm.commitTransaction(Client.getClient().getPrincipal(), transactionID);
  }

  @Override
  public long createOnum() {
    try {
      return tm.newOnums(Client.getClient().getPrincipal(), 1)[0];
    } catch (AccessException e) {
      throw new InternalError(e);
    }
  }

  @Override
  @SuppressWarnings("deprecation")
  public int prepareTransaction(Collection<$Impl> toCreate,
      LongKeyMap<Integer> reads, Collection<$Impl> writes)
      throws TransactionPrepareFailedException {
    Collection<SerializedObject> serializedCreates =
        new ArrayList<SerializedObject>(toCreate.size());
    Collection<SerializedObject> serializedWrites =
        new ArrayList<SerializedObject>(writes.size());
    
    for ($Impl o : toCreate)
      serializedCreates.add(new SerializedObject(o));
    
    for ($Impl o : writes)
      serializedWrites.add(new SerializedObject(o));
    
    PrepareRequest req =
        new PrepareRequest(serializedCreates, serializedWrites, reads);
    
    return tm.prepare(Client.getClient().getPrincipal(), req);
  }

  @Override
  public ObjectGroup readObjectFromCore(long onum) throws FetchException {
    SerializedObject obj;
    try {
      obj = tm.read(Client.getClient().getPrincipal(), onum);
      return new ObjectGroup(obj, new LongKeyHashMap<SerializedObject>());
    } catch (AccessException e) {
      throw new FetchException(e);
    }
  }
  
}
