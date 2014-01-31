package fabric.store.db;

import static fabric.common.Logging.HOTOS_LOGGER;
import static fabric.store.db.ObjectDB.ExtendWarrantyStatus.NEW_PROACTIVE;
import static fabric.store.db.ObjectDB.ExtendWarrantyStatus.NEW_REACTIVE;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import javax.security.auth.x500.X500Principal;

import fabric.common.FastSerializable;
import fabric.common.ONumConstants;
import fabric.common.SerializedObject;
import fabric.common.VersionWarranty;
import fabric.common.Warranty;
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
  private final WarrantyIssuer<Long> warrantyIssuer;

  /**
   * The store's name.
   */
  protected final String name;

  /**
   * The store's object grouper.
   */
  private final ObjectGrouper objectGrouper;

  private SubscriptionManager sm;

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
     * Objects that have been modified or created.
     */
    public final Collection<Pair<SerializedObject, UpdateType>> modData;

    PendingTransaction(long tid, Principal owner) {
      this.tid = tid;
      this.owner = owner;
      this.modData = new ArrayList<Pair<SerializedObject, UpdateType>>();
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
      this.modData = new ArrayList<Pair<SerializedObject, UpdateType>>(size);
      if (in.readBoolean()) {
        for (int i = 0; i < size; i++) {
          SerializedObject obj = new SerializedObject(in);
          UpdateType updateType =
              in.readBoolean() ? UpdateType.CREATE : UpdateType.WRITE;
          modData.add(new Pair<SerializedObject, UpdateType>(obj, updateType));
        }
      }
    }

    /**
     * Returns an iterator of onums involved in this transaction.
     */
    @Override
    public Iterator<Long> iterator() {
      return new Iterator<Long>() {
        private Iterator<Pair<SerializedObject, UpdateType>> modIt = modData
            .iterator();

        @Override
        public boolean hasNext() {
          return modIt.hasNext();
        }

        @Override
        public Long next() {
          return modIt.next().first.getOnum();
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

      for (Pair<SerializedObject, UpdateType> obj : modData) {
        obj.first.write(out);
        out.writeBoolean(obj.second == UpdateType.CREATE);
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
   * The table containing the version warranties that we've issued, and their
   * corresponding virtual warranties.
   */
  protected final WarrantyTable<Long, VersionWarranty> warrantyTable;

  protected final WarrantyRefreshThread refreshThread;

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
    this.pendingByTid =
        new ConcurrentLongKeyHashMap<OidKeyHashMap<PendingTransaction>>();
    this.writeLocks = new ConcurrentLongKeyHashMap<Long>();
    this.writtenOnumsByTid =
        new ConcurrentLongKeyHashMap<OidKeyHashMap<LongSet>>();
    this.objectGrouper = new ObjectGrouper(this, privateKey);
    this.longestWarranty = new VersionWarranty[] { new VersionWarranty(0) };
    this.warrantyTable =
        new WarrantyTable<Long, VersionWarranty>(new VersionWarranty(0));
    this.warrantyIssuer = new WarrantyIssuer<Long>();
    this.refreshThread = new WarrantyRefreshThread();
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
      OidKeyHashMap<PendingTransaction> submap =
          new OidKeyHashMap<PendingTransaction>();
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

  public static enum ExtendWarrantyStatus {
    /**
     * Indicates that a new virtual warranty was created to satisfy an
     * extend-warranty request.
     */
    NEW_REACTIVE,

    /**
     * Indicates that a new warranty was created to satisfy an extend-warranty
     * request, but an existing virtual warranty was sufficient for the request.
     */
    NEW_PROACTIVE,

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
   * Attempts to extend the warranty on a particular version of an object, owing
   * to a read prepare.
   * 
   * @return a result status and the new warranty (if the status is OK).
   * @throws AccessException if no object exists at the given onum.
   */
  public final Pair<ExtendWarrantyStatus, VersionWarranty> extendWarrantyForReadPrepare(
      Principal worker, long onum, int version, long commitTime)
      throws AccessException {
    warrantyIssuer.notifyReadPrepare(onum, commitTime);
    Pair<ExtendWarrantyStatus, VersionWarranty> newWarranty =
        extendWarranty(onum, commitTime, true, false, true);

    if (newWarranty == EXTEND_WARRANTY_DENIED) return EXTEND_WARRANTY_DENIED;
    if (version != getVersion(onum)) return EXTEND_WARRANTY_BAD_VERSION;
    return newWarranty;
  }

  private static Pair<ExtendWarrantyStatus, VersionWarranty> EXTEND_WARRANTY_DENIED =
      new Pair<ExtendWarrantyStatus, VersionWarranty>(
          ExtendWarrantyStatus.DENIED, null);

  private static Pair<ExtendWarrantyStatus, VersionWarranty> EXTEND_WARRANTY_BAD_VERSION =
      new Pair<ExtendWarrantyStatus, VersionWarranty>(
          ExtendWarrantyStatus.BAD_VERSION, null);

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
  public final VersionWarranty registerUpdate(long tid, Principal worker,
      SerializedObject obj,
      LongKeyMap<Pair<SerializedObject, VersionWarranty>> versionConflicts,
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

    synchronized (submap) {
      submap.get(worker).modData.add(new Pair<SerializedObject, UpdateType>(
          obj, updateType));
    }

    switch (updateType) {
    case CREATE:
      // Make sure the onum doesn't already exist in the database.
      if (exists(onum)) {
        throw new TransactionPrepareFailedException(versionConflicts, "Object "
            + onum + " already exists.");
      }

      // Set the object's initial version number.
      obj.setVersion(INITIAL_OBJECT_VERSION_NUMBER);
      return VersionWarranty.EXPIRED_WARRANTY;

    case WRITE:
      // Register the update.
      addWrittenOnumByTid(tid, worker, onum);

      // Read the old copy from the database.
      SerializedObject storeCopy = read(onum);

      // Check version numbers.
      int storeVersion = storeCopy.getVersion();
      int workerVersion = obj.getVersion();
      if (storeVersion != workerVersion) {
        Pair<ExtendWarrantyStatus, VersionWarranty> refreshWarrantyResult =
            refreshWarranty(onum);
        versionConflicts.put(onum, new Pair<SerializedObject, VersionWarranty>(
            storeCopy, refreshWarrantyResult.second));
        return VersionWarranty.EXPIRED_WARRANTY;
      }

      // Cut short the virtual warranty.
      WarrantyTable<Long, VersionWarranty>.Entry entry;
      while (true) {
        entry = warrantyTable.getEntry(onum);
        if (entry == null) break;
        if (entry.truncateVersionWarranty()) break;
      }

      // Obtain existing warranty.
      VersionWarranty warranty = warrantyTable.getIssuedWarranty(onum);
      HOTOS_LOGGER
          .log(
              Level.INFO,
              "writing {0}, warranty expires in {1} ms",
              new Object[] { onum,
                  warranty.expiry() - System.currentTimeMillis() });

      // Update the version number on the prepared copy of the object.
      obj.setVersion(storeVersion + 1);

      // Notify the warranty issuer.
      warrantyIssuer.notifyWritePrepare(onum);

      return warranty;
    }

    throw new fabric.common.exceptions.InternalError("Unknown update type: "
        + updateType);
  }

  protected void addWrittenOnumByTid(long tid, Principal worker, long onum) {
    // Get the submap corresponding to the given TID, creating the submap if
    // necessary.
    OidKeyHashMap<LongSet> submap = new OidKeyHashMap<LongSet>();
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
      RemoteIdentity<RemoteWorker> workerIdentity) {
    // Extend the version warranties for the updated objects.
    List<VersionWarranty.Binding> newProactiveWarranties =
        new ArrayList<VersionWarranty.Binding>();
    List<VersionWarranty.Binding> newReactiveWarranties =
        new ArrayList<VersionWarranty.Binding>();
    try {
      LongSet onums = removeWrittenOnumsByTid(tid, workerIdentity.principal);
      if (onums != null) {
        for (LongIterator it = onums.iterator(); it.hasNext();) {
          long onum = it.next();
          Pair<ExtendWarrantyStatus, VersionWarranty> extendResult =
              extendWarranty(onum, commitTime, true, true, true);
          try {
            switch (extendResult.first) {
            case NEW_PROACTIVE:
              newProactiveWarranties.add(extendResult.second.new Binding(onum,
                  getVersion(onum)));
              break;
            case NEW_REACTIVE:
              newReactiveWarranties.add(extendResult.second.new Binding(onum,
                  getVersion(onum)));
              break;
            case OLD:
            case BAD_VERSION:
            case DENIED:
            }
          } catch (AccessException e) {
            throw new InternalError(e);
          }
        }
      }

      scheduleCommit(tid, commitTime, workerIdentity);
    } finally {
      sm.notifyNewWarranties(newProactiveWarranties, newReactiveWarranties,
          workerIdentity.node);
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
      RemoteIdentity<RemoteWorker> workerIdentity);

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
   * @param causedByWrite true iff the extend-warranty request was caused by
   *          the object being written. If this is false and the existing
   *          warranty isn't sufficiently long to cover minExpiry, then the
   *          warranty will be extended beyond minExpiry, if possible, according
   *          to what the warrantyIssuer gives us.
   * @param canExtendVirtual if the virtual warranty needs to be extended and
   *          this is false, then the existing warranty will be returned
   *          instead. Used for proactive refreshes.
   *          
   * @return the resulting warranty, and whether it was extended. If the current
   *          warranty does not meet minExpiry and could not be renewed,
   *          EXTEND_WARRANTY_DENIED is returned.
   */
  private Pair<ExtendWarrantyStatus, VersionWarranty> extendWarranty(
      final long onum, long minExpiry, boolean minExpiryStrict,
      boolean causedByWrite, boolean canExtendVirtual) {
    while (true) {
      // Get the object's current warranty and determine whether it needs to be
      // extended.
      WarrantyTable<Long, VersionWarranty>.Entry tableEntry =
          warrantyTable.getEntry(onum);
      VersionWarranty curIssuedWarranty =
          tableEntry == null ? warrantyTable.getDefaultWarranty() : tableEntry
              .issuedWarranty();
      if (minExpiryStrict) {
        if (curIssuedWarranty.expiresAfterStrict(minExpiry))
          return new Pair<ExtendWarrantyStatus, VersionWarranty>(
              ExtendWarrantyStatus.OLD, curIssuedWarranty);
      } else {
        if (curIssuedWarranty.expiresAfter(minExpiry, false))
          return new Pair<ExtendWarrantyStatus, VersionWarranty>(
              ExtendWarrantyStatus.OLD, curIssuedWarranty);
      }

      // Need to extend warranty.
      if (!causedByWrite && isWritten(onum)) {
        // Unable to extend.
        return EXTEND_WARRANTY_DENIED;
      }

      // See if the virtual warranty needs to be extended.
      VersionWarranty curVirtualWarranty =
          tableEntry == null ? null : tableEntry.virtualWarranty();
      final boolean canUseVirtualWarranty =
          curVirtualWarranty != null
              && (minExpiryStrict ? curVirtualWarranty
                  .expiresAfterStrict(minExpiry) : curVirtualWarranty
                  .expiresAfter(minExpiry, false));

      // Extend the object's warranty.
      long expiry = minExpiry;
      boolean proactiveRefresh = true;
      if (!canUseVirtualWarranty) {
        // Need a new virtual warranty.
        proactiveRefresh = false;

        if (!canExtendVirtual) {
          return new Pair<ExtendWarrantyStatus, VersionWarranty>(
              ExtendWarrantyStatus.OLD, curIssuedWarranty);
        }

        // Extend the virtual warranty to cover the min expiry, any previously
        // issued warranty, and (if applicable) the warranty period suggested
        // by the warranty issuer.
        long curExpiry = curIssuedWarranty.expiry();
        long newVirtualExpiry = Math.max(minExpiry, curExpiry);
        if (!causedByWrite) {
          newVirtualExpiry =
              warrantyIssuer.suggestWarranty(onum, newVirtualExpiry);
        }

        if (tableEntry == null) {
          // Add to warranty table.
          curVirtualWarranty = new VersionWarranty(newVirtualExpiry);
          tableEntry =
              warrantyTable.putIfAbsentAndGet(onum, curVirtualWarranty);
          if (tableEntry.virtualWarranty() != curVirtualWarranty) continue;
        } else if (newVirtualExpiry != curVirtualWarranty.expiry()) {
          // Update existing table entry.
          VersionWarranty newVirtualWarranty =
              new VersionWarranty(newVirtualExpiry);
          if (!tableEntry.replaceVirtualWarranty(curVirtualWarranty,
              newVirtualWarranty)) continue;
          curVirtualWarranty = newVirtualWarranty;
        }
      }

      if (!causedByWrite) {
        // Ensure the new warranty lasts at least REFRESH_INTERVAL_MS long...
        expiry =
            Math.max(minExpiry, System.currentTimeMillis()
                + Warranty.REFRESH_INTERVAL_MS);

        // ...but not longer than the virtual warranty.
        expiry = Math.min(expiry, curVirtualWarranty.expiry());

        // Also, if there isn't enough of the virtual warranty left over for
        // another refresh period, just issue it all.
        if (!curVirtualWarranty.expiresAfterStrict(expiry
            + Warranty.REFRESH_INTERVAL_MS)) {
          expiry = curVirtualWarranty.expiry();
        }
      }

      VersionWarranty newWarranty = new VersionWarranty(expiry);
      if (expiry > System.currentTimeMillis()) {
        if (!tableEntry.replaceIssuedWarranty(curIssuedWarranty, newWarranty))
          continue;

        updateLongestWarranty(newWarranty);
      }

      // If needed, schedule the next proactive refresh.
      if (!causedByWrite && expiry < curVirtualWarranty.expiry()) {
        // Schedule the refresh to happen 1.5 CLOCK_SKEWs before the warranty
        // expires.
        refreshThread.submitRequest(newWarranty.expiry()
            - (long) (1.5 * Warranty.CLOCK_SKEW), onum, expiry,
            curVirtualWarranty.expiry());
      }

      return new Pair<ExtendWarrantyStatus, VersionWarranty>(
          proactiveRefresh ? NEW_PROACTIVE : NEW_REACTIVE, newWarranty);
    }
  }

  /**
   * Returns any existing version warranty on the object stored at the given
   * onum, if the warranty is still valid. Otherwise, attempts to create and
   * return a new version warranty for the object. If the object is
   * write-locked, then a new warranty cannot be created, and the existing one
   * is returned.
   */
  public Pair<ExtendWarrantyStatus, VersionWarranty> refreshWarranty(long onum) {
    Pair<ExtendWarrantyStatus, VersionWarranty> result =
        extendWarranty(onum, System.currentTimeMillis(), false, false, true);
    if (result == EXTEND_WARRANTY_DENIED) {
      return new Pair<ExtendWarrantyStatus, VersionWarranty>(
          ExtendWarrantyStatus.OLD, warrantyTable.getIssuedWarranty(onum));
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
  protected final void notifyCommittedUpdate(long onum, RemoteWorker worker) {
    // Remove from the glob table the glob associated with the onum.
    LongSet groupOnums = objectGrouper.removeGroup(onum);

    LongSet updatedOnums = new LongHashSet();
    updatedOnums.add(onum);
    if (groupOnums != null) {
      for (LongIterator onumIt = groupOnums.iterator(); onumIt.hasNext();) {
        long relatedOnum = onumIt.next();
        if (relatedOnum == onum) continue;

        updatedOnums.add(relatedOnum);
      }
    }

    // Notify the warranty issuer.
    warrantyIssuer.notifyWriteCommit(onum);

    // Notify the subscription manager that the group has been updated.
    sm.notifyUpdate(updatedOnums, worker);
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
   * Adjusts writeLocks to account for the fact that the given transaction is
   * about to be committed or aborted.
   */
  protected final void unpin(PendingTransaction tx) {
    for (Pair<SerializedObject, UpdateType> update : tx.modData) {
      long onum = update.first.getOnum();
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
    this.sm = tm.subscriptionManager();

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

  /**
   * Proactively refreshes warranties before they expire.
   */
  private final class WarrantyRefreshThread extends Thread {
    final class QueueItem implements Comparable<QueueItem> {
      final long onum;

      /**
       * The time at which the next warranty should be issued.
       */
      final long refreshTime;

      /**
       * The expiry time for the last warranty issued.
       */
      final long expiry;

      /**
       * The expiry time for the virtual warranty.
       */
      final long virtualExpiry;

      QueueItem(long refreshTime, long onum, long expiry, long virtualExpiry) {
        this.refreshTime = refreshTime;
        this.onum = onum;
        this.expiry = expiry;
        this.virtualExpiry = virtualExpiry;
      }

      @Override
      public int compareTo(QueueItem other) {
        return Long.compare(refreshTime, other.refreshTime);
      }
    }

    private final long MIN_SLEEP_TIME_MS = 100;
    private final AtomicBoolean haveNewRequest;

    /**
     * Priority queue of requests.
     */
    private final PriorityBlockingQueue<QueueItem> refreshQueue;

    WarrantyRefreshThread() {
      super("Proactive warranty refresh thread");
      this.setDaemon(true);
      this.refreshQueue = new PriorityBlockingQueue<>();
      this.haveNewRequest = new AtomicBoolean(false);
      start();
    }

    public void submitRequest(long refreshTime, long onum, long expiry,
        long virtualExpiry) {
      refreshQueue.add(new QueueItem(refreshTime, onum, expiry, virtualExpiry));
      haveNewRequest.set(true);
    }

    @Override
    public void run() {
      try {
        while (true) {
          long nextRefreshTime;
          // Loop until the refresh queue is empty or the next refresh request is
          // too far out.
          List<VersionWarranty.Binding> newWarranties = new ArrayList<>();
          while (true) {
            QueueItem request = refreshQueue.peek();
            long now = System.currentTimeMillis();
            if (request == null
                || request.refreshTime > now + MIN_SLEEP_TIME_MS) {
              nextRefreshTime =
                  request == null ? Long.MAX_VALUE : request.refreshTime;
              break;
            }

            request = refreshQueue.remove();

            // Ignore the request if we're so far behind that the virtual
            // warranty has expired.
            if (Warranty.isAfter(now, request.virtualExpiry, true)) continue;

            // Fulfill the refresh request.
            long minExpiry =
                Math.max(now, request.expiry) + Warranty.REFRESH_INTERVAL_MS;
            Pair<ExtendWarrantyStatus, VersionWarranty> result =
                extendWarranty(request.onum, minExpiry, true, false, false);
            if (result.first == NEW_PROACTIVE) {
              try {
                newWarranties.add(result.second.new Binding(request.onum,
                    getVersion(request.onum)));
              } catch (AccessException e) {
                throw new InternalError();
              }
            }
          }

          // Notify subscribers of new warranties.
          if (!newWarranties.isEmpty()) {
            sm.notifyNewWarranties(newWarranties,
                Collections.<VersionWarranty.Binding> emptyList(), null);
          }

          // Wait until either the next refresh time or we have more requests.
          long waitTime = nextRefreshTime - System.currentTimeMillis();
          waitTime -= waitTime % MIN_SLEEP_TIME_MS;
          for (int timeWaited = 0; timeWaited < waitTime; timeWaited +=
              MIN_SLEEP_TIME_MS) {
            try {
              Thread.sleep(MIN_SLEEP_TIME_MS);
            } catch (InterruptedException e) {
            }

            if (haveNewRequest.getAndSet(false)) {
              break;
            }
          }
        }
      } catch (Throwable e) {
        e.printStackTrace();
      }
    }
  }
}
