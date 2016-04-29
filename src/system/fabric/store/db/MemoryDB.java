package fabric.store.db;

import static fabric.common.Logging.STORE_DB_LOGGER;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;

import fabric.common.ONumConstants;
import fabric.common.Resources;
import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.MutableLong;
import fabric.common.util.OidKeyHashMap;
import fabric.lang.security.Principal;
import fabric.store.CommitRequest;
import fabric.store.SubscriptionManager;
import fabric.worker.remote.RemoteWorker;

/**
 * An in-memory implementation of the ObjectDB. This class assumes there will be
 * no failures and hopefully will provide very high performance at the cost of
 * no fault tolerance whatsoever. This class has a simple facility for loading
 * and saving the database to a file. During the save process, no workers should
 * be actively preparing or committing transactions, and all prepared
 * transactions should have been either committed or aborted.
 */
public class MemoryDB extends ObjectDB {

  /**
   * Whether the database has been initialized.
   */
  private boolean isInitialized;

  /**
   * The next free onum.
   */
  private final MutableLong nextOnum;

  /**
   * Maps 48-bit object numbers to SerializedObjects.
   */
  private ConcurrentLongKeyMap<SerializedObject> objectTable;

  /**
   * Opens the store contained in file "var/storeName" if it exists, or an empty
   * store otherwise.
   *
   * @param name
   *          name of store to create database for.
   */
  public MemoryDB(String name, PrivateKey privateKey) {
    super(name, privateKey);
    this.isInitialized = false;
    long nextOnum = ONumConstants.FIRST_UNRESERVED;

    try {
      ObjectInputStream oin =
          new ObjectInputStream(Resources.readFile("var", name));

      this.isInitialized = oin.readBoolean();

      nextOnum = oin.readLong();

      int size = oin.readInt();
      this.objectTable = new ConcurrentLongKeyHashMap<>(size);
      for (int i = 0; i < size; i++)
        this.objectTable.put(oin.readLong(), new SerializedObject(oin));
    } catch (Exception e) {
      // TODO: distinguish invalid files from nonexistent
      this.objectTable = new ConcurrentLongKeyHashMap<>();
    }

    this.nextOnum = new MutableLong(nextOnum);
    STORE_DB_LOGGER.info("MemDB loaded");
  }

  @Override
  public void finishStage(long tid, Principal worker) {
  }

  @Override
  public void commit(RemoteIdentity<RemoteWorker> workerIdentity,
      CommitRequest req, SubscriptionManager sm) throws AccessException {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(req.tid);
    if (submap == null)
      throw new AccessException("Invalid transaction id: " + req.tid);

    if (!submap.containsKey(workerIdentity.principal))
      throw new AccessException("Worker " + workerIdentity
          + " not a partipant in transaction " + req.tid);

    // merge in the objects. We do creates before writes to avoid potential
    // dangling references in updated objects.
    for (SerializedObject obj : req.creates) {
      // Update the version number on the prepared copy of the object.
      obj.setVersion(INITIAL_OBJECT_VERSION_NUMBER);

      objectTable.put(obj.getOnum(), obj);
    }

    for (SerializedObject obj : req.writes) {
      // Update the version number on the prepared copy of the object.
      obj.setVersion(obj.getVersion() + 1);

      objectTable.put(obj.getOnum(), obj);

      // Remove any cached globs containing the old version of this object.
      notifyCommittedUpdate(sm, obj.getOnum(), workerIdentity.node);
    }

    remove(workerIdentity.principal, req.tid, UnpinMode.COMMIT);
  }

  @Override
  public void rollback(long tid, Principal worker) throws AccessException {
    remove(worker, tid, UnpinMode.ABORT);
  }

  @Override
  public SerializedObject read(long onum) {
    return objectTable.get(onum);
  }

  @Override
  public boolean exists(long onum) {
    return objectTable.containsKey(onum);
  }

  @Override
  public long[] newOnums(int num) {
    final long[] result = new long[num < 0 ? 0 : num];

    synchronized (nextOnum) {
      for (int i = 0; i < num; i++)
        result[i] = nextOnum.value++;
    }

    return result;
  }

  @Override
  public void close() throws IOException {
    // XXX TODO Save prepared txs to stable storage, implement finishPrepare().

    try (ObjectOutputStream oout =
        new ObjectOutputStream(Resources.writeFile("var", name))) {
      oout.writeBoolean(isInitialized);

      synchronized (nextOnum) {
        oout.writeLong(nextOnum.value);

        oout.writeInt(this.objectTable.size());
        for (LongKeyMap.Entry<SerializedObject> entry : this.objectTable
            .entrySet()) {
          oout.writeLong(entry.getKey());
          entry.getValue().write(oout);
        }
      }

      oout.flush();
    }
  }

  /**
   * Helper method to check permissions and update the pending object table for
   * a commit or roll-back.
   */
  private PendingTransaction remove(Principal worker, long tid, UnpinMode mode)
      throws AccessException {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    if (submap == null)
      throw new AccessException("Invalid transaction id: " + tid);

    PendingTransaction tx;
    synchronized (submap) {
      tx = submap.remove(worker);
      if (submap.isEmpty()) pendingByTid.remove(tid, submap);
    }

    if (tx == null) throw new AccessException("Invalid transaction id: " + tid);

    // XXX Check if the worker acts for the owner.

    unpin(tx, mode);
    return tx;
  }

  @Override
  protected boolean isInitialized() {
    return this.isInitialized;
  }

  @Override
  protected void setInitialized() {
    this.isInitialized = true;
  }

}

/*
 * vim: ts=2 sw=2 et cindent cino=\:0
 */
