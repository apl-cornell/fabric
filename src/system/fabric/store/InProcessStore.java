package fabric.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fabric.common.ObjectGroup;
import fabric.common.SerializedObject;
import fabric.common.Threading;
import fabric.common.TransactionID;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
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
  public void abortTransaction(final TransactionID tid) {
    Threading.getPool().submit(new Runnable() {
      @Override
      public void run() {
        tm.abortTransaction(Worker.getWorker().getPrincipal(), tid.topTid);
      }
    });
  }

  @Override
  public void commitTransaction(final long transactionID) {
    Threading.getPool().submit(new Runnable() {
      @Override
      public void run() {
        try {
          tm.commitTransaction(getLocalWorkerIdentity(), transactionID);
          Worker.getWorker().getLocalWorker()
              .notifyStoreCommitted(transactionID);
        } catch (TransactionCommitFailedException e) {
          throw new InternalError(
              "Commit phase failed for " + Long.toHexString(transactionID), e);
        }
      }
    });
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
  public void prepareTransaction(final long tid, final boolean singleStore,
      final boolean readOnly, final Collection<_Impl> toCreate,
      final LongKeyMap<Integer> reads, final Collection<_Impl> writes) {
    final Collection<SerializedObject> serializedCreates =
        new ArrayList<>(toCreate.size());
    final Collection<SerializedObject> serializedWrites =
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

    Threading.getPool().submit(new Runnable() {
      @Override
      public void run() {
        PrepareRequest req =
            new PrepareRequest(tid, serializedCreates, serializedWrites, reads);

        // Swizzle remote pointers.
        sm.createSurrogates(req);

        try {
          tm.prepare(Worker.getWorker().getPrincipal(), req);

          if (singleStore || readOnly) {
            tm.commitTransaction(getLocalWorkerIdentity(), tid);
          }
          Worker.getWorker().inProcessRemoteWorker
              .notifyStorePrepareSuccess(tid);
        } catch (TransactionPrepareFailedException e) {
          Worker.getWorker().inProcessRemoteWorker.notifyStorePrepareFailed(tid,
              e);
        } catch (TransactionCommitFailedException e) {
          throw new InternalError(e);
        }
      }
    });
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
      return tm.checkForStaleObjects(Worker.getWorker().getPrincipal(), reads);
    } catch (AccessException e) {
      throw new InternalError(e);
    }
  }

  private Object writeReplace() {
    return new SerializationProxy(name);
  }

  @Override
  public void unsubscribe(LongSet onums) {
    // Running this in the current thread because it should only be called in
    // the dedicated thread for InProcessRemoteWorker's handling of updates.
    tm.unsubscribe(Worker.getWorker().getLocalWorker(), onums);
  }
}
