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
import fabric.common.SysUtil;
import fabric.common.exceptions.AccessException;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.OidKeyHashMap;
import fabric.lang.security.NodePrincipal;
import fabric.lang.security.Principal;
import fabric.store.SubscriptionManager;
import fabric.worker.Store;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;

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
 * name of the store and its private key, and opens the appropriate back-end
 * database if it exists, or creates it if it doesn't exist.
 * </p>
 */
public abstract class ObjectDB {
  private static final int INITIAL_OBJECT_VERSION_NUMBER = 1;
  /**
   * The store's name.
   */
  protected final String name;

  /**
   * The store's object grouper.
   */
  private final ObjectGrouper objectGrouper;

  public static enum UpdateMode {
    CREATE, WRITE
  }

  /**
   * The data stored for a partially prepared transaction.
   */
  protected static final class PendingTransaction
      implements FastSerializable, Iterable<Long> {
    public final long tid;
    public final Principal owner;
    public final Collection<Long> reads;

    /**
     * Objects that have been created.
     */
    public final Collection<SerializedObject> creates;

    /**
     * Objects that have been modified.
     */
    public final Collection<SerializedObject> writes;

    PendingTransaction(long tid, Principal owner) {
      this.tid = tid;
      this.owner = owner;
      this.reads = new ArrayList<>();
      this.creates = new ArrayList<>();
      this.writes = new ArrayList<>();
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
      this.reads = new ArrayList<>(size);
      for (int i = 0; i < size; i++)
        reads.add(in.readLong());

      int createsSize = in.readInt();
      this.creates = new ArrayList<>(createsSize);

      int writesSize = in.readInt();
      this.writes = new ArrayList<>(writesSize);

      if (in.readBoolean()) {
        for (int i = 0; i < createsSize; i++)
          creates.add(new SerializedObject(in));

        for (int i = 0; i < writesSize; i++)
          writes.add(new SerializedObject(in));
      }
    }

    /**
     * Returns an iterator of onums involved in this transaction.
     */
    @Override
    public Iterator<Long> iterator() {
      return new Iterator<Long>() {
        private Iterator<Long> readIt = reads.iterator();
        private Iterator<SerializedObject> createIt = creates.iterator();
        private Iterator<SerializedObject> writeIt = writes.iterator();

        @Override
        public boolean hasNext() {
          return readIt.hasNext() || createIt.hasNext() || writeIt.hasNext();
        }

        @Override
        public Long next() {
          if (readIt.hasNext()) return readIt.next();
          if (createIt.hasNext()) return createIt.next().getOnum();
          return writeIt.next().getOnum();
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

      for (SerializedObject obj : creates)
        obj.write(out);

      for (SerializedObject obj : writes)
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

      out.writeInt(creates.size());
      out.writeInt(writes.size());
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
  protected final ConcurrentLongKeyMap<OidKeyHashMap<PendingTransaction>> pendingByTid;

  /**
   * Maps onums to ObjectLocks. This should be recomputed from the set of
   * prepared transactions when restoring from stable storage.
   */
  protected final ConcurrentLongKeyMap<ObjectLocks> rwLocks;

  protected ObjectDB(String name, PrivateKey privateKey) {
    this.name = name;
    this.pendingByTid = new ConcurrentLongKeyHashMap<>();
    this.rwLocks = new ConcurrentLongKeyHashMap<>();
    this.objectGrouper = new ObjectGrouper(this, privateKey);
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
    // Ensure pendingByTid has a submap for the given TID.
    while (true) {
      OidKeyHashMap<PendingTransaction> submap = new OidKeyHashMap<>();
      OidKeyHashMap<PendingTransaction> existingSubmap =
          pendingByTid.putIfAbsent(tid, submap);
      if (existingSubmap != null) submap = existingSubmap;

      synchronized (submap) {
        submap.put(worker, new PendingTransaction(tid, worker));
      }

      // Ensure the submap wasn't removed out from under us.
      if (pendingByTid.get(tid) == submap) return;
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
      objectLocksFor(onum).lockForRead(tid, worker);
    } catch (UnableToLockException e) {
      throw new TransactionPrepareFailedException(versionConflicts,
          "Object " + onum + " has been locked by an uncommitted transaction.");
    }

    // Register that the transaction has locked the object.
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);

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
   * @param isExtension
   *          is this update intended as an extension for a contract?
   * @param versionConflicts
   *          a map containing the transaction's version-conflict information.
   *          If the object modified was out of date, then a new entry will be
   *          added to this map, binding the object's onum to its current
   *          version.
   */
  public final void prepareUpdate(long tid, Principal worker,
      SerializedObject obj, boolean isExtension,
      LongKeyMap<SerializedObject> versionConflicts,
      LongKeyMap<SerializedObject> longerContracts,
      UpdateMode mode)
      throws TransactionPrepareFailedException {
    long onum = obj.getOnum();

    // First, lock the object.
    try {
      objectLocksFor(onum).lockForWrite(tid);
    } catch (UnableToLockException e) {
      throw new TransactionPrepareFailedException(versionConflicts,
          "Object " + onum + " has been locked by an uncommitted transaction.");
    }

    // Record the updated object. Doing so will also register that the
    // transaction has locked the object.
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);

    switch (mode) {
    case CREATE:
      synchronized (submap) {
        submap.get(worker).creates.add(obj);
      }

      // Make sure the onum doesn't already exist in the database.
      if (exists(onum)) {
        throw new TransactionPrepareFailedException(versionConflicts,
            "Object " + onum + " already exists.");
      }

      // Set the object's initial version number.
      obj.setVersion(INITIAL_OBJECT_VERSION_NUMBER);
      return;

    case WRITE:
      synchronized (submap) {
        submap.get(worker).writes.add(obj);
      }

      // Read the old copy from the database.
      SerializedObject storeCopy = read(onum);

      // Check version numbers.
      int storeVersion = storeCopy.getVersion();
      int workerVersion = obj.getVersion();
      if (storeVersion != workerVersion) {
        versionConflicts.put(onum, storeCopy);
        return;
      }

      if (isExtension) {
        long storeExpiry = storeCopy.getExpiry();
        long newExpiry = obj.getExpiry();
        // Don't bother replacing if it's an extension and we have a longer
        // expiry already.
        if (storeExpiry > newExpiry) {
          submap.get(worker).writes.remove(obj);
          longerContracts.put(onum, read(onum));
        }
      } else {
        // Update the version number on the prepared copy of the object if it's
        // not an extended metric contract.
        obj.setVersion(storeVersion + 1);
      }
      return;
    }

    throw new InternalError("Unknown update mode.");
  }

  /**
   * Obtains the ObjectLocks for a given onum, creating one if it doesn't
   * already exist.
   */
  private ObjectLocks objectLocksFor(long onum) {
    ObjectLocks newLocks = new ObjectLocks();
    ObjectLocks curLocks = rwLocks.putIfAbsent(onum, newLocks);

    if (curLocks != null) return curLocks;
    return newLocks;
  }

  /**
   * Rolls back a partially prepared transaction. (i.e., one for which
   * finishPrepare() has yet to be called.)
   */
  public final void abortPrepare(long tid, Principal worker) {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);

    synchronized (submap) {
      unpin(submap.remove(worker));
      if (submap.isEmpty()) pendingByTid.remove(tid, submap);
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
   * @param workerIdentity
   *          the remote worker that is performing the commit
   * @throws AccessException
   *           if the principal differs from the caller of prepare()
   */
  public abstract void commit(long tid,
      RemoteIdentity<RemoteWorker> workerIdentity, SubscriptionManager sm)
      throws AccessException;

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
   * Returns a GroupContainer for the object stored at a particular onum.
   */
  public final GroupContainer readGroup(long onum) {
    return objectGrouper.getGroup(onum);
  }

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
   * Performs operations in response to a committed object update. Removes from
   * cache the glob associated with the onum and notifies the subscription
   * manager of the update.
   *
   * @param onum
   *          the onum of the object that was updated.
   * @param worker
   *          the worker that performed the update.
   */
  protected final void notifyCommittedUpdate(SubscriptionManager sm, long onum,
      RemoteWorker worker) {
    // Remove from the glob table the glob associated with the onum.
    LongSet groupOnums = objectGrouper.removeGroup(onum);

    if (SubscriptionManager.ENABLE_OBJECT_UPDATES) {
      // Notify the subscription manager that the group has been updated.
      LongSet updatedOnums = new LongHashSet();
      updatedOnums.add(onum);
      if (groupOnums != null) {
        for (LongIterator onumIt = groupOnums.iterator(); onumIt.hasNext();) {
          long relatedOnum = onumIt.next();
          if (relatedOnum == onum) continue;

          updatedOnums.add(relatedOnum);
        }
      }

      sm.notifyUpdate(updatedOnums, worker);
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
    ObjectLocks locks = rwLocks.get(onum);

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
    ObjectLocks locks = rwLocks.get(onum);

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
    for (long readOnum : tx.reads) {
      ObjectLocks locks = rwLocks.get(readOnum);
      if (locks != null) {
        synchronized (locks) {
          locks.unlockForRead(tx.tid, tx.owner);

          if (!locks.isLocked()) rwLocks.remove(readOnum, locks);
        }
      }
    }

    for (SerializedObject update : SysUtil.chain(tx.creates, tx.writes)) {
      long onum = update.getOnum();
      ObjectLocks locks = rwLocks.get(onum);
      if (locks != null) {
        synchronized (locks) {
          locks.unlockForWrite(tx.tid);

          if (!locks.isLocked()) rwLocks.remove(onum, locks);
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
        // Create the DerivedMetric map
        fabric.util.HashMap._Impl derivedMap =
            (fabric.util.HashMap._Impl) new fabric.util.HashMap._Impl(store)
                .fabric$util$HashMap$().fetch();
        derivedMap.$forceRenumber(ONumConstants.DERIVED_MAP);

        return null;
      }
    });

    setInitialized();
  }

}
