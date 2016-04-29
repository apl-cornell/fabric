package fabric.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.dissemination.ObjectGlob;
import fabric.lang.Object._Impl;
import fabric.net.UnreachableNodeException;
import fabric.worker.RemoteStore;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionStagingFailedException;
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
  public void abortStage(long tid) throws AccessException {
    tm.abortStage(Worker.getWorker().getPrincipal(), tid);
  }

  @Override
  public void commitTransaction(long transactionID, Collection<_Impl> toCreate,
      Collection<_Impl> writes) throws TransactionCommitFailedException {
    Collection<SerializedObject> serializedCreates =
        new ArrayList<>(toCreate.size());
    Collection<SerializedObject> serializedWrites =
        new ArrayList<>(writes.size());

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

    CommitRequest req =
        new CommitRequest(transactionID, serializedCreates, serializedWrites);

    // Swizzle remote pointers.
    sm.createSurrogates(req);

    tm.commitTransaction(getLocalWorkerIdentity(), req);
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
  public void stageTransaction(long tid, LongKeyMap<Integer> reads,
      LongKeyMap<Integer> writes, LongSet creates)
      throws TransactionStagingFailedException, UnreachableNodeException {
    tm.stageTransaction(getPrincipal(), tid, reads, writes, creates);
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
