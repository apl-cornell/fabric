package fabric.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fabric.common.ObjectGroup;
import fabric.common.SemanticWarranty;
import fabric.common.SerializedObject;
import fabric.common.SerializedObjectAndTokens;
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
import fabric.store.db.GroupContainer;
import fabric.worker.RemoteStore;
import fabric.worker.TransactionCommitFailedException;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.memoize.CallInstance;
import fabric.worker.memoize.SemanticWarrantyRequest;
import fabric.worker.memoize.WarrantiedCallResult;
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
  public void commitTransaction(long transactionID, long commitTime,
      boolean readOnly) throws TransactionCommitFailedException {
    if (!readOnly)
      tm.commitTransaction(localWorkerIdentity(), transactionID, commitTime);
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
  public PrepareWritesResult prepareTransactionWrites(long tid,
      Collection<_Impl> toCreate, Collection<_Impl> writes,
      Set<SemanticWarrantyRequest> calls)
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
      @SuppressWarnings("deprecation")
      SerializedObject serialized = new SerializedObject(o);
      serializedWrites.add(serialized);
    }

    PrepareWritesRequest req =
        new PrepareWritesRequest(tid, serializedCreates, serializedWrites,
            calls);

    // Swizzle remote pointers.
    sm.createSurrogates(req);

    // Handle call requests
    Map<CallInstance, SemanticWarranty> callReplies =
        tm.prepareRequests(Worker.getWorker().getPrincipal(), tid, calls);

    // Prepare object writes
    long commitTime = tm.prepareWrites(localWorkerIdentity(), req);

    return new PrepareWritesResult(commitTime, callReplies);
  }

  @Override
  public Pair<LongKeyMap<VersionWarranty>, Map<CallInstance, WarrantiedCallResult>> prepareTransactionReads(
      long tid, boolean readOnly, LongKeyMap<Integer> reads,
      Map<CallInstance, WarrantiedCallResult> calls, long commitTime)
      throws TransactionPrepareFailedException {
    LongKeyMap<VersionWarranty> updates =
        tm.prepareReads(localWorkerIdentity(), tid, reads, commitTime);
    Map<CallInstance, WarrantiedCallResult> semUpdates =
        tm.prepareCalls(Worker.getWorker().getPrincipal(), tid, calls,
            commitTime);

    if (readOnly) {
      // Optimization for read-only transaction: commit the transaction right
      // away.

      // Nothing to commit -- warranties have already been extended during the
      // prepare phase.
    }

    return new Pair<>(updates, semUpdates);
  }

  @Override
  public Pair<ObjectGroup, WarrantyGroup> readObjectFromStore(long onum)
      throws AccessException {
    // First, create an object group containing just the requested object.
    LongKeyMap<SerializedObject> map = new LongKeyHashMap<>();
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
  public WarrantiedCallResult lookupCall(CallInstance call) {
    // First argument isn't used...
    try {
      return tm.getCall(null, call);
    } catch (AccessException e) {
      return null;
    }
  }

  @Override
  protected List<SerializedObjectAndTokens> getStaleObjects(
      LongKeyMap<Integer> reads) {
    try {
      return tm.checkForStaleObjects(localWorkerIdentity(), reads);
    } catch (AccessException e) {
      throw new InternalError(e);
    }
  }

  private Object writeReplace() {
    return new SerializationProxy(name);
  }

}
