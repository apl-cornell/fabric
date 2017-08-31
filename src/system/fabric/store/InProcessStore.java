package fabric.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.lang.Object._Impl;
import fabric.worker.RemoteStore;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;

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
  protected RemoteIdentity<RemoteWorker> localWorkerIdentity;

  public InProcessStore(String name, Store c) {
    super(name, c.publicKey);
    tm = c.tm;
    sm = c.sm;

    // This will be lazily populated.
    localWorkerIdentity = null;
  }

  private RemoteIdentity<RemoteWorker> getLocalWorkerIdentity() {
    if (localWorkerIdentity == null) {
      Worker worker = Worker.getWorker();
      localWorkerIdentity =
          new RemoteIdentity<>(worker.getLocalWorker(), worker.getPrincipal());
    }

    return localWorkerIdentity;
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
    tm.commitTransaction(getLocalWorkerIdentity(), transactionID);
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
  public Pair<LongKeyMap<SerializedObject>, Long> prepareTransaction(long tid,
      boolean singleStore, boolean readOnly, Collection<_Impl> toCreate,
      LongKeyMap<Integer> reads, Collection<Pair<_Impl, Boolean>> writes)
      throws TransactionPrepareFailedException {
    Collection<SerializedObject> serializedCreates =
        new ArrayList<>(toCreate.size());
    Collection<Pair<SerializedObject, Boolean>> serializedWrites =
        new ArrayList<>(writes.size());

    for (_Impl o : toCreate) {
      @SuppressWarnings("deprecation")
      SerializedObject serialized = new SerializedObject(o);
      serializedCreates.add(serialized);
    }

    for (Pair<_Impl, Boolean> o : writes) {
      @SuppressWarnings("deprecation")
      SerializedObject serialized = new SerializedObject(o.first);
      serializedWrites.add(new Pair<>(serialized, o.second));
    }

    PrepareRequest req =
        new PrepareRequest(tid, serializedCreates, serializedWrites, reads);

    // Swizzle remote pointers.
    sm.createSurrogates(req);

    LongKeyMap<SerializedObject> longerContracts =
        tm.prepare(Worker.getWorker().getPrincipal(), req);

    if (singleStore || readOnly) {
      try {
        commitTransaction(tid);
      } catch (TransactionCommitFailedException e) {
        // Shouldn't happen.
        throw new InternalError("Single-store commit failed unexpectedly.", e);
      }
    }
    return new Pair<>(longerContracts, System.currentTimeMillis());
  }

  @Override
  public ObjectGroup readObjectFromStore(long onum) throws AccessException {
    LongKeyMap<SerializedObject> map = new LongKeyHashMap<>();
    SerializedObject obj = tm.read(onum);
    if (obj == null) throw new AccessException(this, onum);
    map.put(onum, obj);
    return new ObjectGroup(map);
  }

  @Override
  public ObjectGlob readEncryptedObjectFromStore(long onum)
      throws AccessException {
    return tm.getGlob(onum, getLocalWorkerIdentity().node);
  }

  @Override
  protected List<SerializedObject> getStaleObjects(LongKeyMap<Integer> reads) {
    try {
      return tm.checkForStaleObjects(getPrincipal(), reads);
    } catch (AccessException e) {
      throw new InternalError(e);
    }
  }

  private Object writeReplace() {
    return new SerializationProxy(name);
  }

}
