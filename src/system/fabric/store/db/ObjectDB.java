package fabric.store.db;

import static fabric.common.Logging.HOTOS_LOGGER;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import javax.security.auth.x500.X500Principal;

import fabric.common.FastSerializable;
import fabric.common.Logging;
import fabric.common.ONumConstants;
import fabric.common.SerializedObject;
import fabric.common.SerializedObjectAndTokens;
import fabric.common.SysUtil;
import fabric.common.VersionWarranty;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.lang.security.NodePrincipal;
import fabric.lang.security.Principal;
import fabric.store.SubscriptionManager;
import fabric.store.TransactionManager;
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
 * The ObjectDB interface is designed to support a three-phase commit protocol.
 * Consequently to insert or modify an object, users must first call the
 * beginPrepareWrites() method, registerUpdate() for each inserted or modified
 * object, followed by finishPrepareWrites(). These objects will be stored, but
 * will remain unavailable until after the commit() method is called.
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
  // Needed to coordinate commits.
  protected SemanticWarrantyTable semanticWarranties;

  // Hack hack hack
  private static final boolean ENABLE_WARRANTY_REFRESHES = false;

  @SuppressWarnings("all")
  private static class Ugh {
    static {
      if (ObjectDB.ENABLE_WARRANTY_REFRESHES != SubscriptionManager.ENABLE_WARRANTY_REFRESHES) {
        throw new InternalError();
      }
    }
  }

  /**
   * The store's name.
   */
  protected final String name;

  /**
   * The store's object grouper.
   */
  private final ObjectGrouper objectGrouper;

  public static enum UpdateType {
    CREATE, WRITE
  }

  /**
   * The data stored for a partially prepared transaction.
   */
  protected static final class PendingTransaction implements FastSerializable,
      Iterable<Long> {
    public final long tid;
    public final Principal owner;

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
        private Iterator<SerializedObject> createIt = creates.iterator();
        private Iterator<SerializedObject> writeIt = writes.iterator();

        @Override
        public boolean hasNext() {
          return createIt.hasNext() || writeIt.hasNext();
        }

        @Override
        public Long next() {
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

      for (SerializedObject obj : SysUtil.chain(creates, writes))
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
   * <p>
   * A ref cell containing a warranty that will expire after all warranties
   * issued thus far. The ref cell is used as a mutex for accessing its value.
   * </p>
   * <p>
   * This should be saved to stable storage and restored when starting up.
   * </p>
   */
  protected final VersionWarranty[] longestWarranty;

  /**
   * The table containing the version warranties that we've issued.
   */
  protected final LongKeyWarrantyIssuer<VersionWarranty> warrantyIssuer;

  /**
   * The table containing the access metrics for each object.
   */
  protected final LongKeyAccessMetrics accessMetrics;

  /**
   * <p>
   * Tracks the write locks for each onum. Maps each onum to the tid for the
   * lock holder.
   * </p>
   * <p>
   * This should be recomputed from the set of prepared transactions when
   * restoring from stable storage.
   * </p>
   */
  protected final ConcurrentLongKeyMap<Long> writeLocks;

  /**
   * <p>
   * Tracks the set of onums written by each transaction that has been prepared,
   * but for which a commit message has yet to be received.
   * </p>
   * <p>
   * Maps TIDs to principal oids to sets of onums.
   * </p>
   * <p>
   * This should be recomputed from the set of prepared transactions when
   * restoring from stable storage.
   * </p>
   */
  protected final ConcurrentLongKeyMap<OidKeyHashMap<LongSet>> writtenOnumsByTid;

  protected ObjectDB(String name, PrivateKey privateKey) {
    this.name = name;
    this.pendingByTid = new ConcurrentLongKeyHashMap<>();
    this.writeLocks = new ConcurrentLongKeyHashMap<>();
    this.writtenOnumsByTid = new ConcurrentLongKeyHashMap<>();
    this.objectGrouper = new ObjectGrouper(this, privateKey);
    this.longestWarranty = new VersionWarranty[] { new VersionWarranty(0) };
    this.accessMetrics = new LongKeyAccessMetrics();
    this.warrantyIssuer =
        new LongKeyWarrantyIssuer<>(new VersionWarranty(0), this.accessMetrics);
  }

  /**
   * Set the semantic warranty table to use for coordinating commits.
   */
  public final void setSemanticWarrantyTable(SemanticWarrantyTable swt) {
    this.semanticWarranties = swt;
  }

  /**
   * Opens a transaction so it can be write-prepared.
   *
   * @param worker
   *          the worker under whose authority the transaction is running.
   */
  public final void beginPrepareWrites(long tid, Principal worker) {
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
   * Result status when trying to extend a read lock (warranty or otherwise) on
   * an object.
   */
  public static enum ExtendReadLockStatus {
    /**
     * Indicates that a new warranty was created to satisfy an extend-warranty
     * request.
     */
    NEW,

    /**
     * Indicates that the existing warranty satisfied an extend-warranty
     * request.
     */
    OLD,

    /**
     * Indicates that the object version in an extend-warranty request is
     * out-of-date.
     */
    BAD_VERSION,

    /**
     * Indicates that an extend-warranty request was denied.
     */
    DENIED
  }

  /**
   * Utility class for packaging all of the values returned when read preparing
   * an object for some commit time.
   */
  public static class ReadPrepareResult {
    /**
     * Status of result of read prepare.
     */
    protected ExtendReadLockStatus status;

    /**
     * Warranty now associated with the object.
     */
    protected VersionWarranty warranty;

    public ReadPrepareResult(ExtendReadLockStatus status,
        VersionWarranty warranty) {
      this.status = status;
      this.warranty = warranty;
    }

    /**
     * @return the status
     */
    public ExtendReadLockStatus getStatus() {
      return status;
    }

    /**
     * @return the warranty
     */
    public VersionWarranty getWarranty() {
      return warranty;
    }
  }

  /**
   * Attempts to create or extend the read lock (through warranties or
   * otherwise) on a particular version of an object, owing to a read prepare.
   *
   * @return a result status and the new warranty (if the status is OK).
   * @throws AccessException if no object exists at the given onum.
   */
  public final ReadPrepareResult lockForReadPrepare(
      ReadPrepareResult resultObj, Principal worker, long onum, int version,
      long commitTime) throws AccessException {
    // First, check the object's popularity status. If it is too
    // unpopular, don't bother notifying the warranty issuer about the read
    // prepare, and don't extend beyond the requested commit time.
    SerializedObject obj = read(onum);
    if (obj == null) throw new AccessException(name, onum);
    boolean unpopular = obj.getUnpopularity() > 2;
    if (unpopular) {
      obj.incrementUnpopularity(accessMetrics);
    } else {
      accessMetrics.notifyReadPrepare(onum);
    }

    final boolean extendBeyondCommitTime = !unpopular;
    ReadPrepareResult extendResult =
        extendReadLock(resultObj, onum, commitTime, true,
            extendBeyondCommitTime, false);

    if (extendResult == EXTEND_READ_LOCK_DENIED)
      return EXTEND_READ_LOCK_DENIED;
    if (version != getVersion(onum)) return EXTEND_READ_LOCK_BAD_VERSION;
    return extendResult;
  }

  private static ReadPrepareResult EXTEND_READ_LOCK_DENIED =
      new ReadPrepareResult(ExtendReadLockStatus.DENIED, null);

  private static ReadPrepareResult EXTEND_READ_LOCK_BAD_VERSION =
      new ReadPrepareResult(ExtendReadLockStatus.BAD_VERSION, null);

  /**
   * Registers that a transaction has created or written to an object. This
   * update will not become visible in the store until after commit() is called
   * for the transaction.
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
   * @param create
   *          whether the object was newly created by the transaction.
   *
   * @return the object's existing warranty.
   */
  public final VersionWarranty registerUpdate(ReadPrepareResult scratchObj,
      long tid, Principal worker, SerializedObject obj,
      LongKeyMap<SerializedObjectAndTokens> versionConflicts,
      UpdateType updateType) throws TransactionPrepareFailedException {
    long onum = obj.getOnum();

    // First, lock the object.
    try {
      lockForWrite(onum, tid);
    } catch (UnableToLockException e) {
      throw new TransactionPrepareFailedException(versionConflicts, "Object "
          + onum + " has been locked by an uncommitted transaction.");
    }

    // Record the updated object. Doing so will also register that the
    // transaction has updated the object.
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);

    switch (updateType) {
    case CREATE:
      synchronized (submap) {
        submap.get(worker).creates.add(obj);
      }

      // Make sure the onum doesn't already exist in the database.
      if (exists(onum)) {
        throw new TransactionPrepareFailedException(versionConflicts, "Object "
            + onum + " already exists.");
      }

      // Set the object's initial version number.
      obj.setVersion(INITIAL_OBJECT_VERSION_NUMBER - 1);
      return VersionWarranty.EXPIRED_WARRANTY;

    case WRITE:
      synchronized (submap) {
        submap.get(worker).writes.add(obj);
      }

      // Notify the warranty issuer.
      accessMetrics.notifyWritePrepare(onum);

      // Register the update.
      addWrittenOnumByTid(tid, worker, onum);

      // Read the old copy from the database.
      SerializedObject storeCopy = read(onum);

      // Check version numbers.
      int storeVersion = storeCopy.getVersion();
      int workerVersion = obj.getVersion();
      if (storeVersion != workerVersion) {
        ReadPrepareResult refreshReadResult =
            refreshRead(scratchObj, onum);
        versionConflicts.put(onum, new SerializedObjectAndTokens(storeCopy,
            refreshReadResult.warranty));
        return VersionWarranty.EXPIRED_WARRANTY;
      }

      // Obtain existing warranty.
      VersionWarranty warranty = getWarranty(onum);

      if (HOTOS_LOGGER.isLoggable(Level.FINE)) {
        Logging.log(HOTOS_LOGGER, Level.FINE,
            "writing {0}, warranty expires in {1} ms", onum, warranty.expiry()
                - System.currentTimeMillis());
      }

      return warranty;
    }

    throw new fabric.common.exceptions.InternalError("Unknown update type: "
        + updateType);
  }

  protected void addWrittenOnumByTid(long tid, Principal worker, long onum) {
    // Get the submap corresponding to the given TID, creating the submap if
    // necessary.
    OidKeyHashMap<LongSet> submap = new OidKeyHashMap<>();
    OidKeyHashMap<LongSet> existingSubmap =
        writtenOnumsByTid.putIfAbsent(tid, submap);
    if (existingSubmap != null) submap = existingSubmap;

    LongSet set;
    synchronized (submap) {
      set = submap.get(worker);
      if (set == null) {
        set = new LongHashSet();
        submap.put(worker, set);
      }
    }

    synchronized (set) {
      set.add(onum);
    }
  }

  private LongSet removeWrittenOnumsByTid(long tid, Principal worker) {
    OidKeyHashMap<LongSet> writtenOnumsSubmap = writtenOnumsByTid.get(tid);
    if (writtenOnumsSubmap == null) return null;

    LongSet result;
    synchronized (writtenOnumsSubmap) {
      result = writtenOnumsSubmap.remove(worker);
      if (writtenOnumsSubmap.isEmpty()) {
        writtenOnumsByTid.remove(tid, writtenOnumsSubmap);
      }
    }

    return result;
  }

  /**
   * Registers a write lock for the given TID.
   *
   * @throws UnableToLockException
   *          when a conflicting lock is held.
   */
  private void lockForWrite(long onum, long tid) throws UnableToLockException {
    if (writeLocks.putIfAbsent(onum, tid) != null) {
      throw new UnableToLockException();
    }
  }

  /**
   * Rolls back a partially prepared transaction. (i.e., one for which
   * finishPrepareWrites() has yet to be called.)
   */
  public final void abortPrepareWrites(long tid, Principal worker) {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);

    synchronized (submap) {
      unpin(submap.remove(worker));
      if (submap.isEmpty()) pendingByTid.remove(tid, submap);
    }

    removeWrittenOnumsByTid(tid, worker);
  }

  /**
   * <p>
   * Notifies the database that the given transaction is finished preparing. The
   * transaction is not considered to be prepared until this is called. After
   * calling this method, there should not be any further calls to
   * registerUpdate() for the given transaction. This method
   * MUST be called before calling commit().
   * </p>
   * <p>
   * Upon receiving this call, the object database should save the prepared
   * transaction to stable storage so that it can be recovered in case of
   * failure.
   * </p>
   */
  public abstract void finishPrepareWrites(long tid, Principal worker);

  /**
   * Causes the objects prepared in transaction [tid] to be committed. The
   * changes will be visible to read after the given commit time.
   *
   * @param tid
   *          the transaction id
   * @param commitTime
   *          the time after which the commit should take effect.
   * @param workerIdentity
   *          the remote worker that is performing the commit
   */
  public final void commit(long tid, long commitTime,
      RemoteIdentity<RemoteWorker> workerIdentity, SubscriptionManager sm) {
    // Extend the version warranties for the updated objects.
    List<VersionWarranty.Binding> newWarranties =
        ENABLE_WARRANTY_REFRESHES ? new ArrayList<VersionWarranty.Binding>()
            : null;
    try {
      LongSet onums = removeWrittenOnumsByTid(tid, workerIdentity.principal);
      if (onums != null) {
        ReadPrepareResult resultObj = new ReadPrepareResult(null, null);
        for (LongIterator it = onums.iterator(); it.hasNext();) {
          long onum = it.next();
          ReadPrepareResult extendResult =
              extendReadLock(resultObj, onum, commitTime, true, false, true);
          if (ENABLE_WARRANTY_REFRESHES) {
            if (extendResult.status == ExtendReadLockStatus.NEW) {
              try {
                newWarranties.add(extendResult.warranty.new Binding(onum,
                    getVersion(onum)));
              } catch (AccessException e) {
                throw new InternalError(e);
              }
            }
          }
        }
      }

      scheduleCommit(tid, commitTime, workerIdentity, sm);
    } finally {
      sm.notifyNewWarranties(newWarranties, workerIdentity.node);
    }
  }

  /**
   * Schedules a transaction for commit.
   *
   * @param tid
   *          the transaction id
   * @param commitTime
   *          the time after which the commit should take effect.
   * @param workerIdentity
   *          the remote worker that is performing the commit
   */
  protected abstract void scheduleCommit(long tid, long commitTime,
      RemoteIdentity<RemoteWorker> workerIdentity, SubscriptionManager sm);

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
  public final void abort(long tid, Principal worker) throws AccessException {
    rollback(tid, worker);
    removeWrittenOnumsByTid(tid, worker);
  }

  protected abstract void rollback(long tid, Principal worker)
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
   * Returns the version warranty for the object stored at the given onum.
   *
   * @return the version warranty. If no warranty has been issued, a really old
   *       warranty will be returned.
   */
  public final VersionWarranty getWarranty(long onum) {
    return warrantyIssuer.get(onum);
  }

  private void updateLongestWarranty(VersionWarranty warranty) {
    synchronized (longestWarranty) {
      if (warranty.expiresAfter(longestWarranty[0])) {
        // Fudge longestWarranty so we don't continually touch disk when we create
        // a sequence of warranties whose expiries increase with real time.
        longestWarranty[0] = new VersionWarranty(warranty.expiry() + 30 * 1000);
        saveLongestWarranty();
      }
    }
  }

  /**
   * Saves the warranty in <code>longestWarranty</code> to stable storage. On
   * recovery, this value will be used as the default warranty.
   */
  protected abstract void saveLongestWarranty();

  /**
   * Extends the version warranty of an object, if necessary and possible. The
   * object's resulting warranty is returned.
   *
   * @param onum the onum of the object whose warranty is to be extended.
   * @param minExpiry if the existing warranty already extends beyond this time,
   *          just return it without extending it.
   * @param minExpiryStrict whether to use strict comparisons when comparing the
   *          existing warranty with minExpiry. If this is false, clock skew
   *          will be taken into account.
   * @param extendBeyondMinExpiry if this is true and the existing warranty
   *          isn't sufficiently long to cover minExpiry, then the warranty will
   *          be extended beyond minExpiry, if possible, according to what the
   *          warrantyIssuer gives us.
   * @param ignoreWriteLocks if true, then write locks will be ignored when
   *          determining whether it is possible to extend the warranty.
   *
   * @return the resulting warranty, and whether it was extended. If the current
   *          warranty does not meet minExpiry and could not be renewed,
   *          EXTEND_WARRANTY_DENIE is returned.
   */
  private ReadPrepareResult extendReadLock(ReadPrepareResult result, long onum,
      long minExpiry, boolean minExpiryStrict, boolean extendBeyondMinExpiry,
      boolean ignoreWriteLocks) {
    while (true) {
      // Get the object's current warranty and determine whether it needs to be
      // extended.
      VersionWarranty curWarranty = warrantyIssuer.get(onum);
      if (minExpiryStrict) {
        if (curWarranty.expiresAfterStrict(minExpiry)) {
          result.status = ExtendReadLockStatus.OLD;
          result.warranty = curWarranty;
          return result;
        }
      } else {
        if (curWarranty.expiresAfter(minExpiry, false)) {
          result.status = ExtendReadLockStatus.OLD;
          result.warranty = curWarranty;
          return result;
        }
      }

      // Need to extend warranty.
      if (!ignoreWriteLocks && isWritten(onum)) {
        // Unable to extend.
        return EXTEND_READ_LOCK_DENIED;
      }

      // Extend the object's warranty.
      long expiry = minExpiry;
      if (extendBeyondMinExpiry) {
        expiry = warrantyIssuer.suggestWarranty(onum, minExpiry);
      }
      VersionWarranty newWarranty = new VersionWarranty(expiry);
      if (expiry > System.currentTimeMillis()) {
        if (!warrantyIssuer.replace(onum, curWarranty, newWarranty)) continue;

        updateLongestWarranty(newWarranty);
      }

      result.status = ExtendReadLockStatus.NEW;
      result.warranty = newWarranty;
      return result;
    }
  }

  /**
   * Returns any existing version warranty on the object stored at the given
   * onum, if the warranty is still valid. Otherwise, attempts to create and
   * return a new version warranty for the object. If the object is
   * write-locked, then a new warranty cannot be created, and the existing one
   * is returned.
   */
  public ReadPrepareResult refreshRead(ReadPrepareResult resultObj,
      long onum) {
    ReadPrepareResult result =
        extendReadLock(resultObj, onum, System.currentTimeMillis(), false,
            true, false);
    if (result == EXTEND_READ_LOCK_DENIED) {
      return new ReadPrepareResult(ExtendReadLockStatus.OLD,
          warrantyIssuer.get(onum));
    }

    return result;
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

    // Notify the warranty issuer.
    accessMetrics.notifyWriteCommit(onum);

    if (SubscriptionManager.ENABLE_OBJECT_UPDATES) {
      LongSet updatedOnums = new LongHashSet();
      updatedOnums.add(onum);
      if (groupOnums != null) {
        for (LongIterator onumIt = groupOnums.iterator(); onumIt.hasNext();) {
          long relatedOnum = onumIt.next();
          if (relatedOnum == onum) continue;

          updatedOnums.add(relatedOnum);
        }
      }

      // Notify the subscription manager that the group has been updated.
      sm.notifyUpdate(updatedOnums, worker);
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
    return writeLocks.get(onum) != null;
  }

  /**
   * Determines whether an onum has outstanding uncommitted changes.
   * 
   * @param onum
   *          the object number in question
   * @return true if the object has been changed by a transaction that hasn't
   *         been committed or rolled back.
   */
  public final boolean isWrittenBy(long onum, Long tid) {
    return tid.equals(writeLocks.get(onum));
  }

  /**
   * Adjusts writeLocks to account for the fact that the given transaction is
   * about to be committed or aborted.
   */
  protected final void unpin(PendingTransaction tx) {
    for (SerializedObject update : SysUtil.chain(tx.creates, tx.writes)) {
      long onum = update.getOnum();
      writeLocks.remove(onum, tx.tid);
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
  public final void ensureInit(TransactionManager tm) {
    if (isInitialized()) {
      recoverState(tm);
      return;
    }

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

  /**
   * Recovers the object database's in-memory state from stable storage.
   */
  protected abstract void recoverState(TransactionManager tm);
}
