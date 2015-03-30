package fabric.store.db;

import static fabric.common.Logging.STORE_DB_LOGGER;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;
import java.util.Date;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.ONumConstants;
import fabric.common.Resources;
import fabric.common.SerializedObject;
import fabric.common.Threading;
import fabric.common.exceptions.AccessException;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.MutableLong;
import fabric.common.util.OidKeyHashMap;
import fabric.lang.security.Principal;
import fabric.store.SubscriptionManager;
import fabric.store.TransactionManager;
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
   * Maps onums to SerializedObjects.
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
  public void finishPrepareWrites(long tid, Principal worker) {
    // TODO Implement me!
  }

  @Override
  public void scheduleCommit(final long tid, long commitTime,
      final RemoteIdentity<RemoteWorker> workerIdentity,
      final SubscriptionManager sm) {
    // Only need to schedule a commit if there are pending updates. Reads are
    // handled by extending warranties on the objects read, which is done during
    // the read-prepare phase.
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    if (submap == null) return;
    synchronized (submap) {
      if (!submap.containsKey(workerIdentity.principal)) return;
    }

    if (STORE_DB_LOGGER.isLoggable(Level.FINER)) {
      long commitDelay = commitTime - System.currentTimeMillis();
      Logging.log(STORE_DB_LOGGER, Level.FINER,
          "Scheduling commit for tid {0} to run at {1} (in {2} ms)",
          Long.toHexString(tid), new Date(commitTime), commitDelay);
    }

    Threading.scheduleAt(commitTime, new Runnable() {
      @Override
      public void run() {
        OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
        PendingTransaction tx = submap.get(workerIdentity.principal);

        // merge in the objects. We do creates before writes to avoid potential
        // dangling references in update objects.
        for (SerializedObject o : tx.creates) {
          o.setVersion(o.getVersion() + 1);
          objectTable.put(o.getOnum(), o);
        }

        for (SerializedObject o : tx.writes) {
          o.setVersion(o.getVersion() + 1);
          objectTable.put(o.getOnum(), o);

          // Remove any cached globs containing the old version of this object.
          notifyCommittedUpdate(sm, o.getOnum(), workerIdentity.node);
        }

        remove(workerIdentity.principal, tid);

        // If we have a semantic warranites table initialized (which should
        // always be true) then commit updates to the calls involved as well.
        if (computationWarranties != null) {
          computationWarranties.commit(tid);
        } else {
          throw new InternalError(
              "Somehow we don't have a semantic warranties table!");
        }
      }
    });
  }

  @Override
  public void rollback(long tid, Principal worker) throws AccessException {
    remove(worker, tid);
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
    // XXX TODO Save prepared txs to stable storage, implement finishPrepareWrites().

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
  private PendingTransaction remove(Principal worker, long tid) {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    if (submap == null) return null;

    PendingTransaction tx;
    synchronized (submap) {
      tx = submap.remove(worker);
      if (submap.isEmpty()) pendingByTid.remove(tid, submap);
    }

    if (tx == null) return null;

    // XXX Check if the worker acts for the owner.

    unpin(tx);
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

  @Override
  protected void saveLongestExpiry() {
    // TODO Implement me!
  }

  @Override
  protected void recoverState(TransactionManager tm) {
    // TODO Implement me!
  }

}

/*
 * vim: ts=2 sw=2 et cindent cino=\:0
 */
