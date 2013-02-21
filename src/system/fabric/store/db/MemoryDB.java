package fabric.store.db;

import static fabric.common.Logging.STORE_DB_LOGGER;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;

import fabric.common.ONumConstants;
import fabric.common.Resources;
import fabric.common.SerializedObject;
import fabric.common.Threading;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.MutableLong;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.lang.security.Principal;
import fabric.store.SubscriptionManager;
import fabric.store.TransactionManager;

/**
 * <p>
 * An in-memory implementation of the ObjectDB. This class assumes there will be
 * no failures and hopefully will provide very high performance at the cost of
 * no fault tolerance whatsoever. This class does have a simple facility for
 * loading and saving the database to a file.
 * </p>
 * <p>
 * This class is not thread-safe. Only the <code>TransactionManager</code>
 * should directly interact with this class. The <code>TransactionManager</code>
 * 's thread safety ensures safe usage of this class.
 * </p>
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
  private LongKeyMap<SerializedObject> objectTable;

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
      this.objectTable = new LongKeyHashMap<SerializedObject>(size);
      for (int i = 0; i < size; i++)
        this.objectTable.put(oin.readLong(), new SerializedObject(oin));
    } catch (Exception e) {
      // TODO: distinguish invalid files from nonexistent
      this.objectTable = new LongKeyHashMap<SerializedObject>();
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
      final Principal workerPrincipal, final SubscriptionManager sm) {
    Threading.scheduleAt(commitTime, new Runnable() {
      @Override
      public void run() {
        try {
          PendingTransaction tx = remove(workerPrincipal, tid);

          // merge in the objects
          for (Pair<SerializedObject, UpdateType> update : tx.modData) {
            SerializedObject o = update.first;
            synchronized (objectTable) {
              objectTable.put(o.getOnum(), o);
            }

            // Remove any cached globs containing the old version of this object.
            notifyCommittedUpdate(sm, o.getOnum());
          }
        } catch (AccessException e) {
          throw new InternalError(e);
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
    synchronized (objectTable) {
      return objectTable.get(onum);
    }
  }

  @Override
  public boolean exists(long onum) {
    if (writeLocks.get(onum) != null) return true;

    synchronized (objectTable) {
      return objectTable.containsKey(onum);
    }
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

    ObjectOutputStream oout =
        new ObjectOutputStream(Resources.writeFile("var", name));
    oout.writeBoolean(isInitialized);

    synchronized (nextOnum) {
      synchronized (objectTable) {
        oout.writeLong(nextOnum.value);

        oout.writeInt(this.objectTable.size());
        for (LongKeyMap.Entry<SerializedObject> entry : this.objectTable
            .entrySet()) {
          oout.writeLong(entry.getKey());
          entry.getValue().write(oout);
        }
      }
    }

    oout.flush();
    oout.close();
  }

  /**
   * Helper method to check permissions and update the pending object table for
   * a commit or roll-back.
   */
  private PendingTransaction remove(Principal worker, long tid)
      throws AccessException {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    PendingTransaction tx;
    synchronized (submap) {
      tx = submap.remove(worker);
      if (submap.isEmpty()) pendingByTid.remove(tid, submap);
    }

    if (tx == null)
      throw new AccessException("Invalid transaction id: " + tid);

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
  protected void saveLongestWarranty() {
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
