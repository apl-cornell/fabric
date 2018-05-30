package fabric.store.db;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.x500.X500Principal;

import fabric.common.FastSerializable;
import fabric.common.ONumConstants;
import fabric.common.SerializedObject;
import fabric.common.SysUtil;
import fabric.common.Threading;
import fabric.common.exceptions.AccessException;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Oid;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.lang.security.NodePrincipal;
import fabric.lang.security.Principal;
import fabric.store.InProcessStore;
import fabric.store.SubscriptionManager;
import fabric.worker.LocalStore;
import fabric.worker.RemoteStore;
import fabric.worker.Store;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.metrics.ExpiryExtension;
import fabric.worker.metrics.treaties.TreatySet;
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
    /**
     * State of a PendingTransaction at the store.
     */
    public static enum State {
      /**
       * Began preparing but not finished preparing.
       * May abort due to a lock conflict or a concurrent abort message from the
       * worker.
       */
      PREPARING,
      /**
       * Began preparing but not finished preparing and is now marked to abort.
       * This state happens when an abort message arrives from the worker and
       * the prepare message hasn't finished processing.
       */
      ABORTING,
      /**
       * Finished preparing but not yet committed.
       * Can still abort, but this indicates the prepare message has finished
       * being handled.
       */
      PREPARED;
    }

    public final long tid;
    public final Principal owner;
    private final Collection<Long> reads;
    public State state;

    /**
     * Objects that have been created.
     */
    private final Collection<SerializedObject> creates;

    /**
     * Objects that have been modified.
     */
    private final Collection<SerializedObject> writes;

    /**
     * Extensions to apply
     */
    private final Collection<ExpiryExtension> extensions;

    /**
     * Lock the transaction is waiting on, if any.
     */
    private transient Object waitingOn = null;

    /**
     * Extensions that this will trigger.
     * Pair is first onums on the store to be extended, second the onums for
     * values here that should be shipped with the extension message.
     */
    private final Map<Store, Pair<LongSet, LongSet>> extensionsTriggered;

    PendingTransaction(long tid, Principal owner) {
      this.tid = tid;
      this.owner = owner;
      this.reads = new ArrayList<>();
      this.creates = new ArrayList<>();
      this.writes = new ArrayList<>();
      this.extensions = new ArrayList<>();
      this.extensionsTriggered = new HashMap<>();
      this.state = State.PREPARING; // By default, start in preparing state.
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

      this.state = State.values()[in.readInt()];

      int size = in.readInt();
      this.reads = new ArrayList<>(size);
      for (int i = 0; i < size; i++)
        reads.add(in.readLong());

      int createsSize = in.readInt();
      this.creates = new ArrayList<>(createsSize);

      int writesSize = in.readInt();
      this.writes = new ArrayList<>(writesSize);

      int extensionsSize = in.readInt();
      this.extensions = new ArrayList<>(extensionsSize);

      int extensionsTriggeredSize = in.readInt();
      this.extensionsTriggered = new HashMap<>();

      if (in.readBoolean()) {
        for (int i = 0; i < createsSize; i++)
          creates.add(new SerializedObject(in));

        for (int i = 0; i < writesSize; i++)
          writes.add(new SerializedObject(in));

        for (int i = 0; i < extensionsSize; i++) {
          extensions.add(new ExpiryExtension(in));
        }

        for (int i = 0; i < extensionsTriggeredSize; i++) {
          Store s = Worker.getWorker().getStore(in.readUTF());
          int onumsSize = in.readInt();
          int updatesSize = in.readInt();
          extensionsTriggered.put(s, new Pair<LongSet, LongSet>(
              new LongHashSet(onumsSize), new LongHashSet(updatesSize)));
          for (int j = 0; j < onumsSize; j++) {
            extensionsTriggered.get(s).first.add(in.readLong());
          }
          for (int j = 0; j < updatesSize; j++) {
            extensionsTriggered.get(s).second.add(in.readLong());
          }
        }
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
        private Iterator<ExpiryExtension> extensionsIt = extensions.iterator();

        @Override
        public boolean hasNext() {
          return readIt.hasNext() || createIt.hasNext() || writeIt.hasNext()
              || extensionsIt.hasNext();
        }

        @Override
        public Long next() {
          if (readIt.hasNext()) return readIt.next();
          if (createIt.hasNext()) return createIt.next().getOnum();
          if (writeIt.hasNext()) return writeIt.next().getOnum();
          return extensionsIt.next().onum;
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

      for (ExpiryExtension extension : extensions) {
        extension.write(out);
      }

      for (Store s : extensionsTriggered.keySet()) {
        out.writeUTF(s.name());
        Pair<LongSet, LongSet> sub = extensionsTriggered.get(s);
        out.writeInt(sub.first.size());
        out.writeInt(sub.second.size());
        for (LongIterator it = sub.first.iterator(); it.hasNext();) {
          out.writeLong(it.next());
        }
        for (LongIterator it = sub.second.iterator(); it.hasNext();) {
          out.writeLong(it.next());
        }
      }
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

      out.writeInt(state.ordinal());

      out.writeInt(reads.size());
      for (Long onum : reads)
        out.writeLong(onum);

      out.writeInt(creates.size());
      out.writeInt(writes.size());
      out.writeInt(extensions.size());
      out.writeInt(extensionsTriggered.size());
    }

    /**
     * Unpin the locks held by this PendingTransaction.
     */
    public synchronized void unpin(ObjectDB db) {
      switch (state) {
      case PREPARING:
        throw new InternalError(
            "Only PREPARED and ABORTING transactions can be unpinned.");
      case ABORTING:
      case PREPARED:
        for (long onum : reads) {
          db.rwLocks.releaseReadLock(onum, this);
        }
        reads.clear();

        for (SerializedObject update : SysUtil.chain(creates, writes)) {
          long onum = update.getOnum();
          db.rwLocks.releaseWriteLock(onum, this);
        }
        creates.clear();
        writes.clear();

        for (ExpiryExtension extension : extensions) {
          long onum = extension.onum;
          db.rwLocks.releaseSoftWriteLock(onum, this);
        }
        extensions.clear();

        extensionsTriggered.clear();
      }
    }

    /**
     * Wake up the thread preparing this PendingTransaction, if it is waiting on
     * a lock.
     */
    public void wakeForAbort() {
      Object waitingOnCopy;
      synchronized (this) {
        waitingOnCopy = waitingOn;
        if (waitingOnCopy == null) return;
      }
      synchronized (waitingOnCopy) {
        waitingOnCopy.notifyAll();
      }
    }

    /**
     * Set the currently waited on lock for this transaction.
     */
    public synchronized void setWaitsFor(Object lock) {
      waitingOn = lock;
    }

    /**
     * Clear the currently waited on lock for this transaction.
     */
    public synchronized void clearWaitsFor() {
      waitingOn = null;
    }

    /**
     * Add a create.
     */
    public void addCreate(ObjectDB db, SerializedObject obj)
        throws TransactionPrepareFailedException {
      // First, lock the object.
      try {
        db.rwLocks.acquireWriteLock(obj.getOnum(), this);
      } catch (UnableToLockException e) {
        throw new TransactionPrepareFailedException("Object " + obj.getOnum()
            + " has been locked by an uncommitted transaction.");
      }

      try {
        synchronized (this) {
          if (state == State.ABORTING) {
            throw new TransactionPrepareFailedException(
                "Trying to add a create for an aborting transaction.");
          }
          // Don't freak out on prepared, this is called to deserialize in BdbDB
          creates.add(obj);
        }
      } catch (TransactionPrepareFailedException e) {
        // Release lock, since it won't be added to the pending set.
        db.rwLocks.releaseWriteLock(obj.getOnum(), this);
        throw e;
      }
    }

    /**
     * Add a write.
     */
    public synchronized void addWrite(ObjectDB db, SerializedObject obj)
        throws TransactionPrepareFailedException {
      // First, lock the object.
      try {
        db.rwLocks.acquireWriteLock(obj.getOnum(), this);
      } catch (UnableToLockException e) {
        throw new TransactionPrepareFailedException("Object " + obj.getOnum()
            + " has been locked by an uncommitted transaction.");
      }

      try {
        synchronized (this) {
          if (state == State.ABORTING) {
            throw new TransactionPrepareFailedException(
                "Trying to add a write for an aborting transaction.");
          }
          // Don't freak out on prepared, this is called to deserialize in BdbDB
          writes.add(obj);
        }
      } catch (TransactionPrepareFailedException e) {
        // Release lock, since it won't be added to the pending set.
        db.rwLocks.releaseWriteLock(obj.getOnum(), this);
        throw e;
      }
    }

    /**
     * Add a read.
     */
    public synchronized void addRead(ObjectDB db, long onum)
        throws TransactionPrepareFailedException {
      // First, lock the object.
      try {
        db.rwLocks.acquireReadLock(onum, this);
      } catch (UnableToLockException e) {
        throw new TransactionPrepareFailedException("Object " + onum
            + " has been write-locked by an uncommitted transaction.");
      }

      try {
        synchronized (this) {
          if (state == State.ABORTING) {
            throw new TransactionPrepareFailedException(
                "Trying to add a read for an aborting transaction.");
          }
          // Don't freak out on prepared, this is called to deserialize in BdbDB
          reads.add(onum);
        }
      } catch (TransactionPrepareFailedException e) {
        // Release lock, since it won't be added to the pending set.
        db.rwLocks.releaseReadLock(onum, this);
        throw e;
      }
    }

    /**
     * Add an extension.
     */
    public void addExtension(ObjectDB db, ExpiryExtension extension)
        throws TransactionPrepareFailedException {
      // First, lock the object.
      try {
        db.rwLocks.acquireSoftWriteLock(extension.onum, this);
        db.rwLocks.acquireReadLock(extension.onum, this);
      } catch (UnableToLockException e) {
        throw new TransactionPrepareFailedException("Object " + extension.onum
            + " has been locked by an uncommitted transaction.");
      }

      try {
        synchronized (this) {
          if (state == State.ABORTING)
            throw new TransactionPrepareFailedException(
                "Trying to add an extension for an aborting transaction.");
          // Don't freak out on prepared, this is called to deserialize in BdbDB
          extensions.add(extension);
          reads.add(extension.onum);
        }
      } catch (TransactionPrepareFailedException e) {
        // Release lock, since it won't be added to the pending set.
        db.rwLocks.releaseSoftWriteLock(extension.onum, this);
        db.rwLocks.releaseReadLock(extension.onum, this);
        throw e;
      }
    }

    /**
     * Downgrade an extension to a read.
     */
    public void downgradeExtension(ObjectDB db, ExpiryExtension extension) {
      boolean removed;
      synchronized (this) {
        removed = extensions.remove(extension);
      }

      if (removed) {
        // First, lock the object.
        db.rwLocks.releaseSoftWriteLock(extension.onum, this);
      }
    }

    /**
     * Add a triggered extension.
     */
    public synchronized void addTriggeredExtension(long trigger, Oid triggered)
        throws TransactionPrepareFailedException {
      if (state == State.ABORTING) throw new TransactionPrepareFailedException(
          "Trying to add an extension for an aborting transaction.");
      // Don't freak out on prepared, this is called to deserialize in BdbDB
      if (!extensionsTriggered.containsKey(triggered.store)) {
        extensionsTriggered.put(triggered.store,
            new Pair<LongSet, LongSet>(new LongHashSet(), new LongHashSet()));
      }
      extensionsTriggered.get(triggered.store).first.add(triggered.onum);
      extensionsTriggered.get(triggered.store).second.add(trigger);
    }

    /**
     * Add a triggerless triggered extension.
     */
    public synchronized void addTriggeredExtension(long triggered)
        throws TransactionPrepareFailedException {
      if (state == State.ABORTING) throw new TransactionPrepareFailedException(
          "Trying to add an extension for an aborting transaction.");
      Store curStore = Worker.getWorker().getStore(Worker.getWorkerName());
      if (!extensionsTriggered.containsKey(curStore)) {
        extensionsTriggered.put(curStore,
            new Pair<LongSet, LongSet>(new LongHashSet(), new LongHashSet()));
      }
      extensionsTriggered.get(curStore).first.add(triggered);
    }

    /**
     * @return the reads
     */
    public Collection<Long> getReads() {
      return Collections.unmodifiableCollection(reads);
    }

    /**
     * @return the creates
     */
    public Collection<SerializedObject> getCreates() {
      return Collections.unmodifiableCollection(creates);
    }

    /**
     * @return the writes
     */
    public Collection<SerializedObject> getWrites() {
      return Collections.unmodifiableCollection(writes);
    }

    /**
     * @return the extensions
     */
    public Collection<ExpiryExtension> getExtensions() {
      return extensions;
    }

    /**
     * @return the extensionsTriggered
     */
    public Map<Store, Pair<LongSet, LongSet>> getExtensionsTriggered() {
      return extensionsTriggered;
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
   * Table holding locks for ongoing transactions.
   */
  protected final ObjectLocksTable rwLocks;

  protected ObjectDB(String name, PrivateKey privateKey) {
    this.name = name;
    this.pendingByTid = new ConcurrentLongKeyHashMap<>();
    this.rwLocks = new ObjectLocksTable();
    this.objectGrouper = new ObjectGrouper(this, privateKey);
  }

  /**
   * Returns (and if necessary, creates if it doesn't exist already) the pending transaction map for a given
   * tid.
   */
  private OidKeyHashMap<PendingTransaction> getOrCreatePendingMap(long tid) {
    // Ensure pendingByTid has a submap for the given TID.
    while (true) {
      OidKeyHashMap<PendingTransaction> submap = new OidKeyHashMap<>();
      OidKeyHashMap<PendingTransaction> existingSubmap =
          pendingByTid.putIfAbsent(tid, submap);
      if (existingSubmap != null) submap = existingSubmap;
      // Ensure the submap wasn't removed out from under us.
      if (pendingByTid.get(tid) == submap) return submap;
    }
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
    while (true) {
      OidKeyHashMap<PendingTransaction> submap = getOrCreatePendingMap(tid);
      synchronized (submap) {
        if (!submap.containsKey(worker))
          submap.put(worker, new PendingTransaction(tid, worker));

        // Paranoia: make sure the submap wasn't swiped from under us.
        if (pendingByTid.get(tid) == submap) return;
      }
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
      int version, TreatySet treaties,
      OidKeyHashMap<SerializedObject> versionConflicts,
      OidKeyHashMap<TreatySet> longerTreaties)
      throws TransactionPrepareFailedException {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    if (submap == null) {
      throw new TransactionPrepareFailedException(versionConflicts,
          longerTreaties, "Aborted by another thread");
    }

    PendingTransaction tx;
    synchronized (submap) {
      if (!submap.containsKey(worker)
          || submap.get(worker).state == PendingTransaction.State.ABORTING)
        throw new TransactionPrepareFailedException(versionConflicts,
            longerTreaties, "Aborted by another thread");

      tx = submap.get(worker);
    }

    // First, lock the object and register it.
    tx.addRead(this, onum);

    // Check version numbers.
    int curVersion;
    TreatySet curTreaties;
    try {
      curVersion = getVersion(onum);
      curTreaties = getTreaties(onum);
    } catch (AccessException e) {
      throw new TransactionPrepareFailedException(versionConflicts,
          longerTreaties, e.getMessage());
    }

    if (curVersion != version) {
      versionConflicts.put(Worker.getWorker().getStore(getName()), onum,
          read(onum));
    } else if (TreatySet.checkExtension(treaties, curTreaties)) {
      longerTreaties.put(Worker.getWorker().getStore(getName()), onum,
          curTreaties);
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
      SerializedObject obj, OidKeyHashMap<SerializedObject> versionConflicts,
      OidKeyHashMap<TreatySet> longerTreaties, UpdateMode mode)
      throws TransactionPrepareFailedException {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    if (submap == null) {
      throw new TransactionPrepareFailedException(versionConflicts,
          longerTreaties, "Aborted by another thread");
    }

    long onum = obj.getOnum();
    PendingTransaction tx;
    synchronized (submap) {
      if (!submap.containsKey(worker)
          || submap.get(worker).state == PendingTransaction.State.ABORTING)
        throw new TransactionPrepareFailedException(versionConflicts,

            longerTreaties, "Aborted by another thread");
      tx = submap.get(worker);
    }

    // Record the updated object. Doing so will also register that the
    // transaction has locked the object.
    switch (mode) {
    case CREATE:
      tx.addCreate(this, obj);

      // Make sure the onum doesn't already exist in the database.
      if (exists(onum)) {
        throw new TransactionPrepareFailedException(versionConflicts,

            longerTreaties, "Object " + onum + " already exists.");
      }

      // Set the object's initial version number.
      obj.setVersion(INITIAL_OBJECT_VERSION_NUMBER);
      return;

    case WRITE:
      tx.addWrite(this, obj);

      // Read the old copy from the database.
      SerializedObject storeCopy = read(onum);

      // Check version numbers.
      int storeVersion = storeCopy.getVersion();
      int workerVersion = obj.getVersion();
      if (storeVersion != workerVersion) {
        versionConflicts.put(Worker.getWorker().getStore(getName()), onum,
            storeCopy);
        return;
      }

      // Update the version number on the prepared copy of the object
      obj.setVersion(storeVersion + 1);
      return;
    }

    throw new InternalError("Unknown update mode.");
  }

  /**
   * Prepares an extension against the database.
   *
   * @param tid
   *          the identifier for the transaction preparing the create/write.
   * @param worker
   *          the worker preparing the create/write.
   * @param extension
   *          the modified object.
   * @param versionConflicts
   *          a map containing the transaction's version-conflict information.
   *          If the object modified was out of date, then a new entry will be
   *          added to this map, binding the object's onum to its current
   *          version.
   * @param longerTreaties
   *          the running set of treaties to send back to the worker which have
   *          a later expiry than the worker has.
   */
  public final void prepareExtension(long tid, Principal worker,
      ExpiryExtension extension,
      OidKeyHashMap<SerializedObject> versionConflicts,
      OidKeyHashMap<TreatySet> longerTreaties)
      throws TransactionPrepareFailedException {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    if (submap == null) {
      throw new TransactionPrepareFailedException(versionConflicts,
          longerTreaties, "Aborted by another thread");
    }

    long onum = extension.onum;
    PendingTransaction tx;
    synchronized (submap) {
      if (!submap.containsKey(worker)
          || submap.get(worker).state == PendingTransaction.State.ABORTING)
        throw new TransactionPrepareFailedException(versionConflicts,
            longerTreaties, "Aborted by another thread");
      tx = submap.get(worker);
    }

    // Record the updated object. Doing so will also register that the
    // transaction has locked the object.
    tx.addExtension(this, extension);

    // Check version numbers.
    SerializedObject storeCopy = read(onum);
    int storeVersion = storeCopy.getVersion();
    int workerVersion = extension.version;
    if (storeVersion != workerVersion) {
      versionConflicts.put(Worker.getWorker().getStore(getName()), onum,
          storeCopy);
      return;
    }

    // Check if this is a "real" extension, otherwise we skip it.
    TreatySet curTreaties = storeCopy.getTreaties();
    if (TreatySet.checkExtension(extension.treaties, curTreaties)) {
      if (!curTreaties.equals(extension.treaties)) {
        longerTreaties.put(Worker.getWorker().getStore(getName()), onum,
            curTreaties);
      }
      // Only a read, demote from an extension.
      tx.downgradeExtension(this, extension);
      return;
    }
  }

  /**
   * Prepares an extension against the database.
   *
   * @param tid
   *          the identifier for the transaction preparing the create/write.
   * @param worker
   *          the worker preparing the create/write.
   */
  public final void prepareDelayedExtensions(long tid, Principal worker,
      LongKeyMap<Set<Oid>> extensionsTriggered, LongSet delayedExtensions)
      throws TransactionPrepareFailedException {
    // Record the extension triggering object, updating maps of triggered values
    // to updates. Doing so will also register that the transaction has locked
    // the object.
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    for (LongIterator it = extensionsTriggered.keySet().iterator(); it
        .hasNext();) {
      long onum = it.next();
      for (Oid o : extensionsTriggered.get(onum)) {
        submap.get(worker).addTriggeredExtension(onum, o);
      }
    }
    // Add the extensions to trigger on this store with no trigger objects.
    for (LongIterator it = delayedExtensions.iterator(); it.hasNext();) {
      submap.get(worker).addTriggeredExtension(it.next());
    }
  }

  /**
   * Abort a transaction.
   */
  public final void abortPrepare(long tid, Principal worker) {
    while (true) {
      OidKeyHashMap<PendingTransaction> submap = getOrCreatePendingMap(tid);
      PendingTransaction stillPending;
      synchronized (submap) {
        // Try again.
        if (pendingByTid.get(tid) != submap) continue;
        stillPending = submap.get(worker);
        if (stillPending == null) {
          stillPending = new PendingTransaction(tid, worker);
          submap.put(worker, stillPending);
        }
        synchronized (stillPending) {
          switch (stillPending.state) {
          case PREPARING:
            // Drop the locks so far, change the state, but don't drop from the
            // table.
            stillPending.state = PendingTransaction.State.ABORTING;
            stillPending.unpin(this);
            break;
          case ABORTING:
            // If already aborting, then this is a second call indicating both
            // abort and prepare messages have now seen and acknowledged.
          case PREPARED:
            // Roll back and drop from the table.
            try {
              rollback(tid, worker);
            } catch (AccessException e) {
              throw new InternalError(
                  "Rollback of a pending transaction failed", e);
            }
            break;
          }
        }
      }
      stillPending.wakeForAbort();
      return;
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
   * @throws TransactionPrepareFailedException if the transaction was marked as
   * aborting by another process (due to receiving an Abort message.
   */
  public abstract void finishPrepare(long tid, Principal worker)
      throws TransactionPrepareFailedException;

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
   * Returns the treaties on the object stored at a particular onum.
   *
   * @throws AccessException
   *           if no object exists at the given onum.
   */
  public TreatySet getTreaties(long onum) throws AccessException {
    SerializedObject obj = read(onum);
    if (obj == null) throw new AccessException(name, onum);

    return obj.getTreaties();
  }

  /**
   * Performs operations in response to a committed object update. Removes from
   * cache the glob associated with the onum and notifies the subscription
   * manager, as well as any waiting clients, of the update.
   *
   * @param onum
   *          the onum of the object that was updated.
   * @param worker
   *          the worker that performed the update.
   */
  protected final void notifyCommittedUpdates(SubscriptionManager sm,
      LongSet onums, RemoteWorker worker) {
    // Remove from the glob table the glob associated with the onum.
    LongSet groupOnums = new LongHashSet();
    for (LongIterator iter = onums.iterator(); iter.hasNext();) {
      long onum = iter.next();
      if (Worker.getWorker().config.useSubscriptions) {
        // Make sure the worker performing these updates is subscribed to future
        // updates.
        // TODO: Running it as a dissem subscription right now... that should be
        // revisited.
        sm.subscribe(onum, worker, true);
      }
      if (!groupOnums.contains(onum)) {
        LongSet group = objectGrouper.removeGroup(onum);
        if (group != null) groupOnums.addAll(group);
      }
      // TODO: this is now defunct, is there a new way to do this?
      //synchronized (objectLocksFor(onum)) {
      //  objectLocksFor(onum).notifyAll();
      //}
    }

    if (Worker.getWorker().config.useSubscriptions) {
      // Notify the subscription manager that the group has been updated.
      sm.notifyUpdate(groupOnums, worker);
    }
  }

  /**
   * Sends extension messages that were triggered by a successful commit.
   *
   * @param tid
   *    tid to send triggered extensions for.
   */
  protected final void sendTriggeredExtensions(long tid) {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    synchronized (submap) {
      for (PendingTransaction p : submap.values()) {
        for (final Map.Entry<Store, Pair<LongSet, LongSet>> e : p.extensionsTriggered
            .entrySet()) {
          final Store s = e.getKey();
          final LongSet delayedOnums = e.getValue().first;
          final LongSet updatedOnums = e.getValue().second;
          Threading.getPool().submit(new Runnable() {
            @Override
            public void run() {
              Map<RemoteStore, Collection<SerializedObject>> updates =
                  new HashMap<>();
              if (!(s instanceof InProcessStore)
                  && !(s instanceof LocalStore)) {
                // Only bother to package things up when it's going to a
                // distinct node.
                List<SerializedObject> updateVals =
                    new ArrayList<>(updatedOnums.size());
                for (LongIterator it = updatedOnums.iterator(); it.hasNext();) {
                  updateVals.add(read(it.next()));
                }
                updates.put(Worker.getWorker().getStore(Worker.getWorkerName()),
                    updateVals);
              }
              s.sendExtensions(delayedOnums, updates);
            }
          });
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

  public final SerializedObject waitForUpdate(long onum, int version)
      throws AccessException {
    // TODO: this is now defunct, is there a new way to do this?
    //synchronized (objectLocksFor(onum)) {
    //  while (getVersion(onum) <= version) {
    //    try {
    //      objectLocksFor(onum).wait();
    //    } catch (InterruptedException e) {
    //      STORE_TRANSACTION_LOGGER.log(Level.SEVERE,
    //          "Handling Wait For Update Message interrupted! {0}", e);
    //    }
    //  }
    //}
    return read(onum);
  }
}
