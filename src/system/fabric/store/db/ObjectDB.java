package fabric.store.db;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
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
import fabric.common.util.MutableInteger;
import fabric.common.util.MutableLong;
import fabric.common.util.OidKeyHashMap;
import fabric.lang.security.NodePrincipal;
import fabric.lang.security.Principal;
import fabric.store.PartialObjectGroup;
import fabric.store.SubscriptionManager;
import fabric.worker.Store;
import fabric.worker.TransactionPrepareFailedException;
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
 * name of the store and opens the appropriate back-end database if it exists,
 * or creates it if it doesn't exist.
 * </p>
 */
public abstract class ObjectDB {
  private static final int INITIAL_OBJECT_VERSION_NUMBER = 1;

  protected final String name;
  private final MutableLong nextGlobID;

  /**
   * Maps globIDs to entries (either GroupContainers or PartialObjectGroups) and
   * the number of times the entry is referenced in globIDByOnum.
   */
  private final GroupTable globTable;

  public static enum UpdateMode {
    CREATE, WRITE
  }

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
      if (in.readBoolean()) {
        for (int i = 0; i < size; i++)
          modData.add(new SerializedObject(in));
      }
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
      writeCommon(out);

      // Indicate that contents of modData will follow.
      out.writeBoolean(true);

      for (SerializedObject obj : modData)
        obj.write(out);
    }

    /**
     * Serializes this PendingTransaction out to the given output stream, whilst
     * omitting data about the objects that have been modified or created.
     */
    void writeNoModData(DataOutput out) throws IOException {
      writeCommon(out);

      // Indicate that contents of modData will not follow.
      out.writeBoolean(false);
    }

    /**
     * Writes everything but contents of modData.
     */
    private void writeCommon(DataOutput out) throws IOException {
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
  protected final LongKeyMap<ObjectLocks> rwLocks;

  protected ObjectDB(String name) {
    this.name = name;
    this.pendingByTid = new LongKeyHashMap<OidKeyHashMap<PendingTransaction>>();
    this.rwLocks = new LongKeyHashMap<ObjectLocks>();
    this.globTable = new GroupTable();
    this.nextGlobID = new MutableLong(0);
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
    OidKeyHashMap<PendingTransaction> submap;
    synchronized (pendingByTid) {
      submap = pendingByTid.get(tid);
      if (submap == null) {
        submap = new OidKeyHashMap<PendingTransaction>();
        pendingByTid.put(tid, submap);
      }
    }

    synchronized (submap) {
      submap.put(worker, new PendingTransaction(tid, worker));
    }
  }

  /**
   * Prepares a read against the database.
   * 
   * @param tid
   *          the identifier for the transaction preparing the read.
   * @param worker
   *          the worker preparing the read.
   * @param onum
   *          the object number that was read.
   * @param version
   *          the version that was read.
   * @param versionConflicts
   *          a map containing the transaction's version-conflict information.
   *          If the object read was out of date, then a new entry will be added
   *          to this map, binding the object's onum to its current version.
   */
  public final void prepareRead(long tid, Principal worker, long onum,
      int version, LongKeyMap<SerializedObject> versionConflicts)
      throws TransactionPrepareFailedException {
    // First, lock the object.
    try {
      objectLocksFor(onum).lockForRead(tid);
    } catch (UnableToLockException e) {
      throw new TransactionPrepareFailedException(versionConflicts, "Object "
          + onum + " has been locked by an uncommitted transaction.");
    }

    // Register that the transaction has locked the object.
    OidKeyHashMap<PendingTransaction> submap;
    synchronized (pendingByTid) {
      submap = pendingByTid.get(tid);
    }

    synchronized (submap) {
      submap.get(worker).reads.add(onum);
    }

    // Check version numbers.
    int curVersion;
    try {
      curVersion = getVersion(onum);
    } catch (AccessException e) {
      throw new TransactionPrepareFailedException(versionConflicts,
          e.getMessage());
    }

    if (curVersion != version) {
      versionConflicts.put(onum, read(onum));
    }
  }

  /**
   * Prepares a create/write against the database.
   * 
   * @param tid
   *          the identifier for the transaction preparing the create/write.
   * @param worker
   *          the worker preparing the create/write.
   * @param obj
   *          the modified object.
   * @param versionConflicts
   *          a map containing the transaction's version-conflict information.
   *          If the object modified was out of date, then a new entry will be
   *          added to this map, binding the object's onum to its current
   *          version.
   */
  public final void prepareUpdate(long tid, Principal worker,
      SerializedObject obj, LongKeyMap<SerializedObject> versionConflicts,
      UpdateMode mode) throws TransactionPrepareFailedException {
    long onum = obj.getOnum();

    // First, lock the object.
    try {
      objectLocksFor(onum).lockForWrite(tid);
    } catch (UnableToLockException e) {
      throw new TransactionPrepareFailedException(versionConflicts, "Object "
          + onum + " has been locked by an uncommitted transaction.");
    }

    // Record the updated object. Doing so will also register that the
    // transaction has locked the object.
    OidKeyHashMap<PendingTransaction> submap;
    synchronized (pendingByTid) {
      submap = pendingByTid.get(tid);
    }

    synchronized (submap) {
      submap.get(worker).modData.add(obj);
    }

    switch (mode) {
    case CREATE:
      // Make sure the onum doesn't already exist in the database.
      if (exists(onum)) {
        throw new TransactionPrepareFailedException(versionConflicts, "Object "
            + onum + " already exists.");
      }

      // Set the object's initial version number.
      obj.setVersion(INITIAL_OBJECT_VERSION_NUMBER);
      break;

    case WRITE:
      // Read the old copy from the database.
      SerializedObject storeCopy = read(onum);

      // Check version numbers.
      int storeVersion = storeCopy.getVersion();
      int workerVersion = obj.getVersion();
      if (storeVersion != workerVersion) {
        versionConflicts.put(onum, storeCopy);
        return;
      }

      // Update the version number on the prepared copy of the object.
      obj.setVersion(storeVersion + 1);
      break;

    default:
      throw new InternalError("Unknown update mode.");
    }
  }

  /**
   * Obtains the ObjectLocks for a given onum, creating one if it doesn't
   * already exist.
   */
  private ObjectLocks objectLocksFor(long onum) {
    return objectLocksFor(onum, true);
  }

  /**
   * @param create
   *         whether to create an entry if one does not exist.
   */
  private ObjectLocks objectLocksFor(long onum, boolean create) {
    synchronized (rwLocks) {
      ObjectLocks result = rwLocks.get(onum);
      if (result == null && create) {
        result = new ObjectLocks();
        rwLocks.put(onum, result);
      }

      return result;
    }
  }

  /**
   * Rolls back a partially prepared transaction. (i.e., one for which
   * finishPrepare() has yet to be called.)
   */
  public final void abortPrepare(long tid, Principal worker) {
    synchronized (pendingByTid) {
      OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);

      synchronized (submap) {
        unpin(submap.remove(worker));
        if (submap.isEmpty()) pendingByTid.remove(tid);
      }
    }
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
   * Causes the objects prepared in transaction [tid] to be committed. The
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
  public abstract void commit(long tid, Principal workerPrincipal,
      SubscriptionManager sm) throws AccessException;

  /**
   * Causes the objects prepared in transaction [tid] to be discarded.
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
   * Returns the object stored at a particular onum.
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

    return obj.getVersion();
  }

  /**
   * Gives the ID of the group to which the given onum belongs. Null is returned
   * if no such group exists.
   */
  public final Long getCachedGroupID(long onum) {
    synchronized (globTable) {
      return globTable.getGlobIDByOnum(onum);
    }
  }

  private final GroupTable.Entry getGroupTableEntry(long onum) {
    synchronized (globTable) {
      Long groupID = getCachedGroupID(onum);
      if (groupID == null) return null;
      return globTable.getContainer(groupID);
    }
  }

  /**
   * Gives the cached partial group for the given onum. Null is returned if no
   * such partial group exists.
   */
  public final PartialObjectGroup getCachedPartialGroup(long onum) {
    GroupTable.Entry entry = getGroupTableEntry(onum);
    if (entry instanceof PartialObjectGroup) return (PartialObjectGroup) entry;
    return null;
  }

  /**
   * Returns the cached GroupContainer containing the given onum. Null is
   * returned if no such GroupContainer exists.
   */
  public final GroupContainer getCachedGroupContainer(long onum) {
    GroupTable.Entry entry = getGroupTableEntry(onum);
    if (entry instanceof GroupContainer) return (GroupContainer) entry;
    return null;
  }

  /**
   * Inserts the given partial group into the cache.
   */
  public final void cachePartialGroup(PartialObjectGroup partialGroup) {
    // Get a new ID for the partial group.
    long groupID;
    synchronized (nextGlobID) {
      groupID = nextGlobID.value++;
    }
    partialGroup.setID(groupID);

    synchronized (globTable) {
      globTable.put(partialGroup);
    }
  }

  /**
   * Coalesces one partial group into another.
   */
  public void coalescePartialGroups(PartialObjectGroup from,
      PartialObjectGroup to) {
    synchronized (globTable) {
      globTable.coalescePartialGroups(from, to);
    }
  }

  public GroupContainer promotePartialGroup(PrivateKey signingKey,
      PartialObjectGroup partialGroup) {
    synchronized (globTable) {
      return globTable.promotePartialGroup(
          Worker.getWorker().getStore(getName()), signingKey, partialGroup);
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
    Long globID;
    GroupContainer group = null;
    synchronized (globTable) {
      globID = globTable.getGlobIDByOnum(onum);
      if (globID != null) {
        GroupTable.Entry entry = globTable.remove(globID);
        if (entry instanceof GroupContainer) {
          group = (GroupContainer) entry;
        }
      }

      // Notify the subscription manager that the group has been updated.
      // sm.notifyUpdate(onum, worker);
      if (group != null) {
        for (LongIterator onumIt = group.onums.iterator(); onumIt.hasNext();) {
          long relatedOnum = onumIt.next();
          if (relatedOnum == onum) continue;

          Long relatedGlobId = globTable.getGlobIDByOnum(relatedOnum);
          if (relatedGlobId != null && relatedGlobId == globID) {
            // sm.notifyUpdate(relatedOnum, worker);
          }
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
    ObjectLocks locks;
    synchronized (rwLocks) {
      locks = rwLocks.get(onum);
    }

    if (locks == null) return false;

    synchronized (locks) {
      if (locks.writeLock != null) return true;

      if (locks.readLocks.isEmpty()) return false;
      if (locks.readLocks.size() > 1) return true;
      return !locks.readLocks.containsKey(tid);
    }
  }

  /**
   * Determines whether an onum has outstanding uncommitted changes.
   * 
   * @param onum
   *          the object number in question
   * @return true if the object has been changed by a transaction that hasn't
   *         been committed or rolled back.
   */
  public final boolean isWritten(long onum) {
    ObjectLocks locks;
    synchronized (rwLocks) {
      locks = rwLocks.get(onum);
    }

    if (locks == null) return false;

    synchronized (locks) {
      return locks.writeLock != null;
    }
  }

  /**
   * Adjusts rwLocks to account for the fact that the given transaction is about
   * to be committed or aborted.
   */
  protected final void unpin(PendingTransaction tx) {
    synchronized (rwLocks) {
      for (long readOnum : tx.reads) {
        ObjectLocks locks = rwLocks.get(readOnum);

        synchronized (locks) {
          MutableInteger pinCount = locks.readLocks.get(tx.tid);
          if (pinCount != null) {
            pinCount.value--;
            if (pinCount.value == 0) locks.readLocks.remove(tx.tid);
          }

          if (locks.writeLock == null && locks.readLocks.isEmpty())
            rwLocks.remove(readOnum);
        }
      }

      for (SerializedObject update : tx.modData) {
        long onum = update.getOnum();
        ObjectLocks locks = rwLocks.get(onum);

        synchronized (locks) {
          if (locks.writeLock != null && locks.writeLock == tx.tid)
            locks.writeLock = null;

          if (locks.writeLock == null && locks.readLocks.isEmpty())
            rwLocks.remove(onum);
        }
      }
    }
  }

  /**
   * <p>
   * Returns a set of onums that aren't currently occupied. The ObjectDB may
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
   * Gracefully shuts down the object database.
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
  public final void ensureInit() {
    if (isInitialized()) return;

    final Store store = Worker.getWorker().getStore(name);

    Worker.runInSubTransaction(new Worker.Code<Void>() {
      @SuppressWarnings("deprecation")
      @Override
      public Void run() {
        // No need to initialize global constants here, as those objects will be
        // supplied by the workers' local store.
        String principalName = new X500Principal("CN=" + name).getName();
        NodePrincipal._Impl principal =
            (NodePrincipal._Impl) new NodePrincipal._Impl(store)
                .fabric$lang$security$NodePrincipal$(principalName).fetch();
        principal.$forceRenumber(ONumConstants.STORE_PRINCIPAL);

        // Create the label {store->_; store<-_} for the root map.
        // XXX above not done. HashMap needs to be parameterized on labels.
        fabric.util.HashMap._Impl map =
            (fabric.util.HashMap._Impl) new fabric.util.HashMap._Impl(store)
                .fabric$util$HashMap$().fetch();
        map.$forceRenumber(ONumConstants.ROOT_MAP);

        return null;
      }
    });

    setInitialized();
  }

}
