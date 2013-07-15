package fabric.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fabric.common.ObjectGroup;
import fabric.common.SemanticWarranty;
import fabric.common.SerializedObject;
import fabric.common.TransactionID;
import fabric.common.VersionWarranty;
import fabric.common.WarrantyGroup;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Pair;
import fabric.dissemination.ObjectGlob;
import fabric.dissemination.WarrantyGlob;
import fabric.lang.Object._Impl;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.memoize.WarrantiedCallResult;
import fabric.store.db.GroupContainer;
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

  private RemoteIdentity<RemoteWorker> localWorkerIdentity() {
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
  public Map<CallInstance, SemanticWarranty> commitTransaction(long transactionID,
      long commitTime, Set<SemanticWarrantyRequest> requests, boolean readOnly)
  throws TransactionCommitFailedException {
    Map<CallInstance, SemanticWarranty> replies =
      tm.prepareRequests(Worker.getWorker().getPrincipal(), transactionID,
          requests, commitTime);
    if (!readOnly)
      tm.commitTransaction(localWorkerIdentity(), transactionID,
          commitTime);
    return replies;
  }

  @Override
  public long createOnum() {
    try {
      return tm.newOnums(getPrincipal(), 1)[0];
    } catch (AccessException e) {
      throw new InternalError(e);
    }
  }

  @Override
  public long prepareTransactionWrites(long tid, Collection<_Impl> toCreate,
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

    PrepareWritesRequest req =
        new PrepareWritesRequest(tid, serializedCreates, serializedWrites);

    // Swizzle remote pointers.
    sm.createSurrogates(req);

    return tm.prepareWrites(getPrincipal(), req);
  }

  @Override
  public Pair<LongKeyMap<VersionWarranty>, Map<CallInstance, SemanticWarranty>>
  prepareTransactionReads(long tid, LongKeyMap<Integer> reads, Map<CallInstance,
      WarrantiedCallResult> calls, long commitTime) throws
  TransactionPrepareFailedException {
    LongKeyMap<VersionWarranty> updates =
	tm.prepareReads(localWorkerIdentity(), tid, reads,
                        commitTime);
    Map<CallInstance, SemanticWarranty> semUpdates =
      tm.prepareCalls(Worker.getWorker().getPrincipal(), tid, calls,
          commitTime);
    return new Pair<LongKeyMap<VersionWarranty>, Map<CallInstance,
           SemanticWarranty>>(updates, semUpdates);
  }

  @Override
  public Pair<ObjectGroup, WarrantyGroup> readObjectFromStore(long onum)
      throws AccessException {
    // First, create an object group containing just the requested object.
    LongKeyMap<SerializedObject> map = new LongKeyHashMap<SerializedObject>();
    SerializedObject obj = tm.read(onum);
    if (obj == null) throw new AccessException(this, onum);
    map.put(onum, obj);

    ObjectGroup objectGroup = new ObjectGroup(map);

    // Next, get a warranty group for the onum.
    GroupContainer groupContainer = tm.getGroupContainer(onum);
    WarrantyGroup warrantyGroup = groupContainer.getWarranties();

    return new Pair<>(objectGroup, warrantyGroup);
  }

  @Override
  public Pair<ObjectGlob, WarrantyGlob> readEncryptedObjectFromStore(long onum)
      throws AccessException {
    return tm.getGlobs(onum, localWorkerIdentity().node);
  }

  @Override
  protected List<Pair<SerializedObject, VersionWarranty>> getStaleObjects(
      LongKeyMap<Integer> reads) {
    try {
      return tm.checkForStaleObjects(localWorkerIdentity(), reads);
    } catch (AccessException e) {
      throw new InternalError(e);
    }
  }

  @Override
  public java.lang.Object writeReplace() {
    return new SerializationProxy(name);
  }

}
