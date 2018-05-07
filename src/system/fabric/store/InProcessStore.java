package fabric.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Oid;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.lang.Object._Impl;
import fabric.worker.RemoteStore;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.metrics.ExpiryExtension;
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
  public Pair<LongKeyMap<Long>, Long> prepareTransaction(long tid,
      boolean singleStore, boolean readOnly, long expiryToCheck,
      Collection<_Impl> toCreate, LongKeyMap<Pair<Integer, Long>> reads,
      Collection<_Impl> writes, Collection<ExpiryExtension> extensions,
      LongKeyMap<Set<Oid>> extensionsTriggered, LongSet delayedExtensions)
      throws TransactionPrepareFailedException {
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
      serializedWrites.add(new SerializedObject(o));
    }

    PrepareRequest req =
        new PrepareRequest(tid, serializedCreates, serializedWrites, reads,
            extensions, extensionsTriggered, delayedExtensions);

    // Swizzle remote pointers.
    sm.createSurrogates(req);

    LongKeyMap<Long> longerContracts =
        tm.prepare(Worker.getWorker().getPrincipal(), req);

    long prepareTime = System.currentTimeMillis();

    if (singleStore || readOnly) {
      if (singleStore && prepareTime > expiryToCheck) {
        try {
          tm.abortTransaction(Worker.getWorker().getPrincipal(), tid);
        } catch (AccessException e) {
          // This should never happen.
          throw new InternalError("AccessException on abort but not prepare?");
        }
        throw new TransactionPrepareFailedException(
            new LongKeyHashMap<SerializedObject>(), longerContracts,
            "Single store prepare too late");
      }
      try {
        commitTransaction(tid);
      } catch (TransactionCommitFailedException e) {
        // Shouldn't happen.
        throw new InternalError("Single-store commit failed unexpectedly.", e);
      }
    }
    return new Pair<>(longerContracts, prepareTime);
  }

  @Override
  public Collection<ObjectGroup> readObjectFromStore(long onum)
      throws AccessException {
    LongKeyMap<SerializedObject> map = new LongKeyHashMap<>();
    SerializedObject obj = tm.read(onum);
    if (obj == null) throw new AccessException(this, onum);
    map.put(onum, obj);
    for (LongIterator iter = tm.getAssociatedOnums(onum).iterator(); iter
        .hasNext();) {
      long relatedOnum = iter.next();
      SerializedObject related = tm.read(relatedOnum);
      if (related == null) throw new AccessException(this, relatedOnum);
      map.put(relatedOnum, related);
    }
    return Collections.singletonList(new ObjectGroup(map));
  }

  @Override
  public LongKeyMap<ObjectGlob> readEncryptedObjectFromStore(long onum)
      throws AccessException {
    LongKeyMap<ObjectGlob> result = new LongKeyHashMap<>();
    result.put(onum, tm.getGlob(onum, getLocalWorkerIdentity().node));
    for (LongIterator iter = tm.getAssociatedOnums(onum).iterator(); iter
        .hasNext();) {
      long relatedOnum = iter.next();
      result.put(relatedOnum,
          tm.getGlob(relatedOnum, getLocalWorkerIdentity().node));
    }
    return result;
  }

  @Override
  protected List<SerializedObject> getStaleObjects(
      LongKeyMap<Pair<Integer, Long>> reads) {
    try {
      return tm.checkForStaleObjects(getPrincipal(), reads);
    } catch (AccessException e) {
      throw new InternalError(e);
    }
  }

  private Object writeReplace() {
    return new SerializationProxy(name);
  }

  @Override
  public void sendExtensions(LongSet extensions,
      Map<RemoteStore, Collection<SerializedObject>> updates) {
    tm.queueExtensions(extensions);
  }

  @Override
  public void unsubscribe(LongSet onums) {
    // Running this in the current thread because it should only be called in
    // the dedicated thread for InProcessRemoteWorker's handling of updates.
    tm.unsubscribe(Worker.getWorker().getLocalWorker(), onums);
  }
}
