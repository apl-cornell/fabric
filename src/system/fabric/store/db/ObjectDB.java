package fabric.store.db;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.security.auth.x500.X500Principal;

import fabric.common.FastSerializable;
import fabric.common.ONumConstants;
import fabric.common.SerializedObject;
import fabric.common.exceptions.AccessException;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.MutableInteger;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.lang.security.NodePrincipal;
import fabric.lang.security.Principal;
import fabric.store.SubscriptionManager;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * <p>
 * An ObjectDB encapsulates the persistent state of the Store. It is responsible
 * for storing and retrieving objects, and also for checking permissions.
 * </p>
 * <p>
 * The ObjectDB interface is designed to support a two-phase commit protocol.
 * Consequently to insert or modify an object, users must first call the
 * prepare() method, passing in the set of objects to update. These objects will
 * be stored, but will remain unavailable until the commit() method is called
 * with the returned transaction identifier.
 * </p>
 * <p>
 * In general, implementations of ObjectDB are not thread-safe. Only
 * TransactionManager should be interacting directly with ObjectDB
 * implementations; it is responsible for ensuring safe use of ObjectDB.
 * </p>
 * <p>
 * All ObjectDB implementations should provide a constructor which takes the
 * name of the store and opens the appropriate back-end database if it exists, or
 * creates it if it doesn't exist.
 * </p>
 */
public abstract class ObjectDB {

  protected final String name;
  private long nextGlobID;

  /**
   * Maps object numbers to globIDs. The group container with ID
   * globIDByOnum(onum) holds a copy of object onum. (globIDs really ought to be
   * called group-container IDs, but we're sticking with globID for historical
   * reasons and because it's shorter.)
   */
  private final LongKeyMap<Long> globIDByOnum;

  /**
   * Maps globIDs to GroupContainers and the number of times the GroupContainer
   * is referenced in globIDByOnum.
   */
  private final GroupContainerTable globTable;

  /**
   * The data stored for a partially prepared transaction.
   */
  protected static final class PendingTransaction implements FastSerializable,
      Iterable<Long> {
    public final long tid;
    public final Principal owner;
    public final Collection<Long> reads;

    /**
     * Objects that have been modified or created.
     */
    public final Collection<SerializedObject> modData;

    PendingTransaction(long tid, Principal owner) {
      this.tid = tid;
      this.owner = owner;
      this.reads = new ArrayList<Long>();
      this.modData = new ArrayList<SerializedObject>();
    }

    /**
     * Deserialization constructor.
     */
    public PendingTransaction(ObjectInputStream in) throws IOException {
      this.tid = in.readLong();

      if (in.readBoolean()) {
        Store store = Worker.getWorker().getStore(in.readUTF());
        this.owner = new Principal._Proxy(store, in.readLong());
      } else {
        this.owner = null;
      }

      int size = in.readInt();
      this.reads = new ArrayList<Long>(size);
      for (int i = 0; i < size; i++)
        reads.add(in.readLong());

      size = in.readInt();
      this.modData = new ArrayList<SerializedObject>(size);
      for (int i = 0; i < size; i++)
        modData.add(new SerializedObject(in));
    }

    /**
     * Returns an iterator of onums involved in this transaction.
     */
    @Override
    public Iterator<Long> iterator() {
      return new Iterator<Long>() {
        private Iterator<Long> readIt = reads.iterator();
        private Iterator<SerializedObject> modIt = modData.iterator();

        @Override
        public boolean hasNext() {
          return readIt.hasNext() || modIt.hasNext();
        }

        @Override
        public Long next() {
          if (readIt.hasNext()) return readIt.next();
          return modIt.next().getOnum();
        }

        @Override
        public void remove() {
          throw new UnsupportedOperationException();
        }
      };
    }

    /**
     * Serializes this object out to the given output stream.
     */
    @Override
    public void write(DataOutput out) throws IOException {
      out.writeLong(tid);

      out.writeBoolean(owner != null);
      if (owner != null) {
        out.writeUTF(owner.$getStore().name());
        out.writeLong(owner.$getOnum());
      }

      out.writeInt(reads.size());
      for (Long onum : reads)
        out.writeLong(onum);

      out.writeInt(modData.size());
      for (SerializedObject obj : modData)
        obj.write(out);
    }
  }

  /**
   * <p>
   * The table of partially prepared transactions. Note that this does not need
   * to be saved to stable storage, since we only need to persist transactions
   * that are fully prepared.
   * </p>
   * <p>
   * Maps tids to principal oids to PendingTransactions.
   * </p>
   */
  protected final LongKeyMap<OidKeyHashMap<PendingTransaction>> pendingByTid;

  /**
   * <p>
   * Tracks the read/write locks for each onum. Maps each onum to a pair. The
   * first component of the pair is the tid for the write-lock holder. The
   * second component is a map from the of tids for the read-lock holders to the
   * number of workers in the transaction that have read locks on the onum.
   * </p>
   * <p>
   * This should be recomputed from the set of prepared transactions when
   * restoring from stable storage.
   * </p>
   */
  protected final LongKeyMap<Pair<Long, LongKeyMap<MutableInteger>>> rwLocks;

  protected ObjectDB(String name) {
    this.name = name;
    this.pendingByTid = new LongKeyHashMap<OidKeyHashMap<PendingTransaction>>();
    this.rwLocks = new LongKeyHashMap<Pair<Long, LongKeyMap<MutableInteger>>>();
    this.globIDByOnum = new LongKeyHashMap<Long>();
    this.globTable = new GroupContainerTable();
    this.nextGlobID = 0;
  }

  /**
   * Opens a new transaction.
   * 
   * @param worker
   *          the worker under whose authority the transaction is running.
   * @throws AccessException
   *           if the worker has insufficient privileges.
   */
  public final void beginTransaction(long tid, Principal worker)
      throws AccessException {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    if (submap == null) {
      submap = new OidKeyHashMap<PendingTransaction>();
      pendingByTid.put(tid, submap);
    }

    submap.put(worker, new PendingTransaction(tid, worker));
  }

  /**
   * Registers that a transaction has read an object.
   */
  public final void registerRead(long tid, Principal worker, long onum) {
    addReadLock(onum, tid);
    pendingByTid.get(tid).get(worker).reads.add(onum);
  }

  /**
   * Registers that a transaction has created or written to an object. This
   * update will not become visible in the store until commit() is called for
   * the transaction.
   * 
   * @param tid
   *          the identifier for the transaction.
   * @param obj
   *          the updated object.
   */
  public final void registerUpdate(long tid, Principal worker,
      SerializedObject obj) {
    addWriteLock(obj.getOnum(), tid);
    pendingByTid.get(tid).get(worker).modData.add(obj);
  }

  /**
   * Acquires a read lock on the given onum for the given transaction.
   */
  private void addReadLock(long onum, long tid) {
    Pair<Long, LongKeyMap<MutableInteger>> locks = rwLocks.get(onum);
    if (locks == null) {
      locks =
          new Pair<Long, LongKeyMap<MutableInteger>>(null,
              new LongKeyHashMap<MutableInteger>());
      rwLocks.put(onum, locks);
    }

    MutableInteger pinCount = locks.second.get(tid);
    if (pinCount == null) {
      pinCount = new MutableInteger(0);
      locks.second.put(tid, pinCount);
    }

    pinCount.value++;
  }

  /**
   * Acquires a write lock on the given onum for the given transaction.
   */
  private void addWriteLock(long onum, long tid) {
    Pair<Long, LongKeyMap<MutableInteger>> locks = rwLocks.get(onum);
    if (locks == null) {
      locks =
          new Pair<Long, LongKeyMap<MutableInteger>>(null,
              new LongKeyHashMap<MutableInteger>());
      rwLocks.put(onum, locks);
    }

    locks.first = tid;
  }

  /**
   * Rolls back a partially prepared transaction. (i.e., one for which
   * finishPrepare() has yet to be called.)
   */
  public final void abortPrepare(long tid, Principal worker) {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    unpin(submap.remove(worker));
    if (submap.isEmpty()) pendingByTid.remove(tid);
  }

  /**
   * <p>
   * Notifies the database that the given transaction is finished preparing. The
   * transaction is not considered to be prepared until this is called. After
   * calling this method, there should not be any further calls to
   * registerRead() or registerUpdate() for the given transaction. This method
   * MUST be called before calling commit().
   * </p>
   * <p>
   * Upon receiving this call, the object database should save the prepared
   * transaction to stable storage so that it can be recovered in case of
   * failure.
   * </p>
   */
  public abstract void finishPrepare(long tid, Principal worker);

  /**
   * Cause the objects prepared in transaction [tid] to be committed. The
   * changes will hereafter be visible to read.
   * 
   * @param tid
   *          the transaction id
   * @param workerNode
   *          the remote worker that is performing the commit
   * @param workerPrincipal
   *          the principal requesting the commit
   * @throws AccessException
   *           if the principal differs from the caller of prepare()
   */
  public abstract void commit(long tid, Principal workerPrincipal, SubscriptionManager sm)
      throws AccessException;

  /**
   * Cause the objects prepared in transaction [tid] to be discarded.
   * 
   * @param tid
   *          the transaction id
   * @param worker
   *          the principal requesting the rollback
   * @throws AccessException
   *           if the principal differs from the caller of prepare()
   */
  public abstract void rollback(long tid, Principal worker)
      throws AccessException;

  /**
   * Return the object stored at a particular onum.
   * 
   * @param onum
   *          the identifier
   * @return the object or null if no object exists at the given onum
   */
  public abstract SerializedObject read(long onum);

  /**
   * Returns the version number on the object stored at a particular onum.
   * 
   * @throws AccessException
   *           if no object exists at the given onum.
   */
  public int getVersion(long onum) throws AccessException {
    SerializedObject obj = read(onum);
    if (obj == null) throw new AccessException(name, onum);

    return read(onum).getVersion();
  }

  /**
   * Returns the cached GroupContainer containing the given onum. Null is
   * returned if no such GroupContainer exists.
   */
  public final GroupContainer getCachedGroupContainer(long onum) {
    Long globID = globIDByOnum.get(onum);
    if (globID == null) return null;
    return globTable.getContainer(globID);
  }

  /**
   * Inserts the given group container into the cache for the given onums.
   */
  public final void cacheGroupContainer(LongSet onums, GroupContainer container) {
    // Get a new ID for the glob and insert into the glob table.
    long globID = nextGlobID++;
    globTable.put(globID, container, onums.size());

    // Establish globID bindings for all onums we're given.
    for (LongIterator it = onums.iterator(); it.hasNext();) {
      long onum = it.next();

      Long oldGlobID = globIDByOnum.put(onum, globID);
      if (oldGlobID == null) continue;

      globTable.unpin(oldGlobID);
    }
  }

  /**
   * Performs operations in response to a committed object update. Removes from
   * cache the glob associated with the onum and notifies the subscription
   * manager of the update.
   * 
   * @param onum
   *          the onum of the object that was updated.
   * @param worker
   *          the worker that performed the update.
   */
  protected final void notifyCommittedUpdate(SubscriptionManager sm, long onum) {
    // Remove from the glob table the glob associated with the onum.
    Long globID = globIDByOnum.remove(onum);
    GroupContainer group = null;
    if (globID != null) {
      group = globTable.remove(globID);
    }

    // Notify the subscription manager that the group has been updated.
    // sm.notifyUpdate(onum, worker);
    if (group != null) {
      for (LongIterator onumIt = group.onums.iterator(); onumIt.hasNext();) {
        long relatedOnum = onumIt.next();
        if (relatedOnum == onum) continue;

        Long relatedGlobId = globIDByOnum.get(relatedOnum);
        if (relatedGlobId != null && relatedGlobId == globID) {
          // sm.notifyUpdate(relatedOnum, worker);
        }
      }
    }

  }

  /**
   * Determines whether an onum has an outstanding uncommitted conflicting
   * change or read. Outstanding uncommitted changes are always considered
   * conflicting. Outstanding uncommitted reads are considered conflicting if
   * they are by transactions whose tid is different from the one given.
   * 
   * @param onum
   *          the object number in question
   */
  public final boolean isPrepared(long onum, long tid) {
    Pair<Long, LongKeyMap<MutableInteger>> locks = rwLocks.get(onum);
    if (locks == null) return false;

    if (locks.first != null) return true;

    if (locks.second.isEmpty()) return false;
    if (locks.second.size() > 1) return true;
    return !locks.second.containsKey(tid);
  }

  /**
   * Determine whether an onum has outstanding uncommitted changes.
   * 
   * @param onum
   *          the object number in question
   * @return true if the object has been changed by a transaction that hasn't
   *         been committed or rolled back.
   */
  public final boolean isWritten(long onum) {
    Pair<Long, LongKeyMap<MutableInteger>> locks = rwLocks.get(onum);
    return locks != null && locks.first != null;
  }

  /**
   * Adjusts rwLocks to account for the fact that the given transaction is about
   * to be committed or aborted.
   */
  protected final void unpin(PendingTransaction tx) {
    for (long readOnum : tx.reads) {
      Pair<Long, LongKeyMap<MutableInteger>> locks = rwLocks.get(readOnum);

      MutableInteger pinCount = locks.second.get(tx.tid);
      if (pinCount != null) {
        pinCount.value--;
        if (pinCount.value == 0) locks.second.remove(tx.tid);
      }

      if (locks.first == null && locks.second.isEmpty())
        rwLocks.remove(readOnum);
    }

    for (SerializedObject update : tx.modData) {
      long onum = update.getOnum();
      Pair<Long, LongKeyMap<MutableInteger>> locks = rwLocks.get(onum);
      if (locks.first != null && locks.first == tx.tid) locks.first = null;

      if (locks.first == null && locks.second.isEmpty()) rwLocks.remove(onum);
    }
  }

  /**
   * <p>
   * Return a set of onums that aren't currently occupied. The ObjectDB may
   * return the same onum more than once from this method, althogh doing so
   * would encourage collisions. There is no assumption of unpredictability or
   * randomness about the returned ids.
   * </p>
   * <p>
   * The returned onums should be packed in the lower 48 bits. We assume that
   * the object database is never full, and can always provide new onums
   * </p>
   * 
   * @param num
   *          the number of onums to return
   * @return num fresh onums
   */
  public abstract long[] newOnums(int num);

  /**
   * Checks whether an object with the corresponding onum exists, in either
   * prepared or committed form.
   * 
   * @param onum
   *          the onum of to check
   * @return true if an object exists for onum
   */
  public abstract boolean exists(long onum);

  /**
   * Returns the name of this store.
   */
  public final String getName() {
    return name;
  }

  /**
   * Gracefully shutdown the object database.
   * 
   * @throws IOException
   */
  public abstract void close() throws IOException;

  /**
   * Determines whether the object database has been initialized.
   */
  protected abstract boolean isInitialized();

  /**
   * Sets a flag to indicate that the object database has been initialized.
   */
  protected abstract void setInitialized();

  /**
   * Ensures that the object database has been properly initialized. This
   * creates, for example, the name-service map and the store's principal, if
   * they do not already exist in the database.
   */
  @SuppressWarnings("deprecation")
  public final void ensureInit() {
    if (isInitialized()) return;

    final Store store = Worker.getWorker().getStore(name);

    Worker.runInSubTransaction(new Worker.Code<Void>() {
      @Override
      public Void run() {
        // No need to initialize global constants here, as those objects will be
        // supplied by the workers' local store.
        String principalName = new X500Principal("CN=" + name).getName();
        NodePrincipal._Impl principal =
            (NodePrincipal._Impl) new NodePrincipal._Impl(store).fabric$lang$security$NodePrincipal$(principalName);
        principal.$forceRenumber(ONumConstants.STORE_PRINCIPAL);

        // Create the label {store->_; store<-_} for the root map.
        // XXX above not done. HashMap needs to be parameterized on labels.
        fabric.util.HashMap._Impl map =
            (fabric.util.HashMap._Impl) new fabric.util.HashMap._Impl(store).fabric$util$HashMap$().fetch();
        map.$forceRenumber(ONumConstants.ROOT_MAP);

        return null;
      }
    });

    setInitialized();
  }

}
