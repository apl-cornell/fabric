package fabric.client;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

import fabric.common.InternalError;
import fabric.common.SerializedObject;
import fabric.common.util.LongKeyMap;
import fabric.core.PrepareRequest;
import fabric.core.TransactionManager;
import fabric.core.store.StoreException;
import fabric.lang.Object.$Impl;

/**
 * In-process implementation of the Core interface for use when a client is
 * running in-process with a Core.  The operations work directly on the Core's
 * TransactionManager object.
 *
 * @author mdgeorge
 */
public class InProcessCore extends RemoteCore {

  InProcessCore(String name) {
    super(name);
    // TODO Auto-generated constructor stub
  }

  TransactionManager tm;
  Principal          client;

  @Override
  public void abortTransaction(int transactionID) {
    tm.abortTransaction(client, transactionID);
  }

  @Override
  public void commitTransaction(int transactionID)
      throws TransactionCommitFailedException {
    try {
      tm.commitTransaction(client, transactionID);
    } catch (TransactionCommitFailedException e) {
      throw new fabric.client.TransactionCommitFailedException(e.getMessage());
    }
  }

  @Override
  public long createOnum() {
    try {
      return tm.newOIDs(client, 1)[0];
    } catch (StoreException e) {
      throw new InternalError(e);
    }
  }

  @Override
  public String name() {
    return tm.getObjectStore().getName();
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
    
    return tm.prepare(client, req);
  }
  
}
