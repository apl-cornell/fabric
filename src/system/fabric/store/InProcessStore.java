package fabric.store;

import static fabric.common.Logging.WORKER_LOCAL_STORE_LOGGER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object._Impl;
import fabric.messages.BeginTransactionMessage;
import fabric.net.UnreachableNodeException;
import fabric.worker.RemoteStore;
import fabric.worker.TransactionBeginFailedException;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;

/**
 * In-process implementation of the Store interface for use when a worker is
 * running in-process with a Store. The operations work directly on the Store's
 * TransactionManager object.
 * 
 * @author mdgeorge
 */
public class InProcessStore extends RemoteStore {

  protected final TransactionManager tm;
  protected final SurrogateManager sm;

  public InProcessStore(String name, Store c) {
    super(name, c.publicKey);
    tm = c.tm;
    sm = c.sm;
  }

  @Override
  public void abortTransaction(TransactionID tid) {
    try {
      tm.abortTransaction(Worker.getWorker().getPrincipal(), tid.topTid);
    } catch (AccessException e) {
      throw new InternalError(e);
    }
  }

  @Override
  public void commitTransaction(long transactionID)
      throws TransactionCommitFailedException {
    tm.commitTransaction(Worker.getWorker().getPrincipal(), transactionID);
  }

  @Override
  public long createOnum() {
    try {
      return tm.newOnums(Worker.getWorker().getPrincipal(), 1)[0];
    } catch (AccessException e) {
      throw new InternalError(e);
    }
  }

  @Override
  public boolean prepareTransaction(long tid, long commitTime,
      Collection<_Impl> toCreate, LongKeyMap<Integer> reads,
      Collection<_Impl> writes) throws TransactionPrepareFailedException {
    Collection<SerializedObject> serializedCreates =
        new ArrayList<SerializedObject>(toCreate.size());
    Collection<SerializedObject> serializedWrites =
        new ArrayList<SerializedObject>(writes.size());

    for (_Impl o : toCreate) {
      @SuppressWarnings("deprecation")
      SerializedObject serialized = new SerializedObject(o);
      serializedCreates.add(serialized);
    }

    for (_Impl o : writes) {
      @SuppressWarnings("deprecation")
      SerializedObject serialized = new SerializedObject(o);
      serializedWrites.add(serialized);
    }

    PrepareRequest req =
        new PrepareRequest(tid, commitTime, serializedCreates,
            serializedWrites, reads);

    // Swizzle remote pointers.
    sm.createSurrogates(req);

    return tm.prepare(Worker.getWorker().getPrincipal(), req);
  }

  @Override
  public boolean beginTransaction(long tid, long commitTime,
      Collection<_Impl> toCreate, LongKeyMap<Integer> reads,
      Collection<_Impl> writes) throws UnreachableNodeException,
      TransactionBeginFailedException {

    WORKER_LOCAL_STORE_LOGGER
        .severe("*** TODO: Implement InProcess.beginTransaction(...).*** ");

    BeginTransactionMessage.Response response =
        send(Worker.getWorker().authToStore, new BeginTransactionMessage(tid,
            commitTime, toCreate, reads, writes));

    return response.transactionBegun;

  }

  @Override
  public ObjectGroup readObjectFromStore(long onum) throws AccessException {
    LongKeyMap<SerializedObject> map = new LongKeyHashMap<SerializedObject>();
    SerializedObject obj = tm.read(onum);
    if (obj == null) throw new AccessException(this, onum);
    map.put(onum, obj);
    return new ObjectGroup(map);
  }

  @Override
  protected List<SerializedObject> getStaleObjects(LongKeyMap<Integer> reads) {
    try {
      return tm.checkForStaleObjects(getPrincipal(), reads);
    } catch (AccessException e) {
      throw new InternalError(e);
    }
  }

  @Override
  public java.lang.Object writeReplace() {
    return new SerializationProxy(name);
  }

}
