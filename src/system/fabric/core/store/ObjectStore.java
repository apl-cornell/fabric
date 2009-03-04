package fabric.core.store;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.security.auth.x500.X500Principal;

import jif.lang.Label;
import jif.lang.PairLabel;
import jif.lang.ReaderPolicy;
import jif.lang.WriterPolicy;
import fabric.client.Client;
import fabric.client.Core;
import fabric.common.*;
import fabric.common.util.*;
import fabric.lang.NodePrincipal;

/**
 * <p>
 * An Object Store encapsulates the persistent state of the Core. It is
 * responsible for storing and retrieving objects, and also for checking
 * permissions.
 * </p>
 * <p>
 * The Object Store interface is designed to support a two-phase commit
 * protocol. Consequently to insert or modify an object, users must first call
 * the prepare() method, passing in the set of objects to update. These objects
 * will be stored, but will remain unavailable until the commit() method is
 * called with the returned transaction identifier.
 * </p>
 * <p>
 * In general, implementations of ObjectStore are not thread-safe. Only
 * TransactionManager should be interacting directly with ObjectStore
 * implementations; it is responsible for ensuring safe use of ObjectStore.
 * </p>
 * <p>
 * All ObjectStore implementations should provide a constructor which takes the
 * name of the core and opens the appropriate backend store if it exists, or
 * creates it if it doesn't exist.
 * </p>
 */
public abstract class ObjectStore {

  protected final String name;
  private NodePrincipal corePrincipal;
  private Label publicReadonlyLabel;

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
  private final LongKeyMap<Pair<GroupContainer, MutableInteger>> globTable;

  /**
   * The data stored for a partially prepared transaction.
   */
  protected static final class PendingTransaction implements FastSerializable,
      Iterable<Long> {
    public final long tid;
    public final NodePrincipal owner;
    public final Collection<Long> reads;

    /**
     * Objects that have been modified or created.
     */
    public final Collection<SerializedObject> modData;

    PendingTransaction(long tid, NodePrincipal owner) {
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
        Core core = Client.getClient().getCore(in.readUTF());
        this.owner = new NodePrincipal.$Proxy(core, in.readLong());
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
    public Iterator<Long> iterator() {
      return new Iterator<Long>() {
        private Iterator<Long> readIt = reads.iterator();
        private Iterator<SerializedObject> modIt = modData.iterator();

        public boolean hasNext() {
          return readIt.hasNext() || modIt.hasNext();
        }

        public Long next() {
          if (readIt.hasNext()) return readIt.next();
          return modIt.next().getOnum();
        }

        public void remove() {
          throw new UnsupportedOperationException();
        }
      };
    }

    /**
     * Serializes this object out to the given output stream.
     */
    public void write(DataOutput out) throws IOException {
      out.writeLong(tid);

      out.writeBoolean(owner != null);
      if (owner != null) {
        out.writeUTF(owner.$getCore().name());
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
   * second component is the set of tids for the read-lock holders.
   * </p>
   * <p>
   * This should be recomputed from the set of prepared transactions when
   * restoring from stable storage.
   * </p>
   */
  protected final LongKeyMap<Pair<Long, LongSet>> rwLocks;

  protected ObjectStore(String name) {
    this.name = name;
    this.pendingByTid = new LongKeyHashMap<OidKeyHashMap<PendingTransaction>>();
    this.rwLocks = new LongKeyHashMap<Pair<Long, LongSet>>();
    this.globIDByOnum = new LongKeyHashMap<Long>();
    this.globTable = new LongKeyHashMap<Pair<GroupContainer, MutableInteger>>();
  }

  /**
   * Opens a new transaction.
   * 
   * @param client
   *          the client under whose authority the transaction is running.
   * @throws AccessException
   *           if the client has insufficient privileges.
   */
  public final void beginTransaction(long tid, NodePrincipal client)
      throws AccessException {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    if (submap == null) {
      submap = new OidKeyHashMap<PendingTransaction>();
      pendingByTid.put(tid, submap);
    }

    submap.put(client, new PendingTransaction(tid, client));
  }

  /**
   * Registers that a transaction has read an object.
   */
  public final void registerRead(long tid, NodePrincipal client, long onum) {
    addReadLock(onum, tid);
    pendingByTid.get(tid).get(client).reads.add(onum);
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
  public final void registerUpdate(long tid, NodePrincipal client,
      SerializedObject obj) {
    addWriteLock(obj.getOnum(), tid);
    pendingByTid.get(tid).get(client).modData.add(obj);
  }

  /**
   * Acquires a read lock on the given onum for the given transaction.
   */
  private void addReadLock(long onum, long tid) {
    Pair<Long, LongSet> locks = rwLocks.get(onum);
    if (locks == null) {
      locks = new Pair<Long, LongSet>(null, new LongHashSet());
      rwLocks.put(onum, locks);
    }
    locks.second.add(tid);
  }

  /**
   * Acquires a write lock on the given onum for the given transaction.
   */
  private void addWriteLock(long onum, long tid) {
    Pair<Long, LongSet> locks = rwLocks.get(onum);
    if (locks == null) {
      locks = new Pair<Long, LongSet>(null, new LongHashSet());
      rwLocks.put(onum, locks);
    }

    locks.first = tid;
  }

  /**
   * Rolls back a partially prepared transaction. (i.e., one for which
   * finishPrepare() has yet to be called.)
   */
  public final void abortPrepare(long tid, NodePrincipal client) {
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    unpin(submap.remove(client));
    if (submap.isEmpty()) pendingByTid.remove(tid);
  }

  /**
   * <p>
   * Notifies the store that the given transaction is finished preparing. The
   * transaction is not considered to be prepared until this is called. After
   * calling this method, there should not be any further calls to
   * registerRead() or registerUpdate() for the given transaction. This method
   * MUST be called before calling commit().
   * </p>
   * <p>
   * Upon receiving this call, the object store should save the prepared
   * transaction to stable storage so that it can be recovered in case of
   * failure.
   * </p>
   */
  public abstract void finishPrepare(long tid, NodePrincipal client);

  /**
   * Cause the objects prepared in transaction [tid] to be committed. The
   * changes will hereafter be visible to read.
   * 
   * @param tid
   *          the transaction id
   * @param client
   *          the principal requesting the commit
   * @throws AccessException
   *           if the principal differs from the caller of prepare()
   */
  public abstract void commit(long tid, NodePrincipal client)
      throws AccessException;

  /**
   * Cause the objects prepared in transaction [tid] to be discarded.
   * 
   * @param tid
   *          the transaction id
   * @param client
   *          the principal requesting the rollback
   * @throws AccessException
   *           if the principal differs from the caller of prepare()
   */
  public abstract void rollback(long tid, NodePrincipal client)
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
   * Returns a fresh globID.
   */
  protected abstract long nextGlobID();

  /**
   * Returns the cached GroupContainer containing the given onum. Null is
   * returned if no such GroupContainer exists.
   */
  public final GroupContainer getCachedGroupContainer(long onum) {
    Long globID = globIDByOnum.get(onum);
    if (globID == null) return null;

    Pair<GroupContainer, MutableInteger> entry = globTable.get(globID);
    if (entry == null) return null;
    return entry.first;
  }

  /**
   * Inserts the given group container into the cache for the given onums.
   */
  public final void cacheGroupContainer(LongSet onums, GroupContainer container) {
    // Get a new ID for the glob and insert into the glob table.
    long globID = nextGlobID();
    globTable.put(globID, new Pair<GroupContainer, MutableInteger>(container,
        new MutableInteger(onums.size())));

    // Establish globID bindings for all onums we're given.
    for (LongIterator it = onums.iterator(); it.hasNext();) {
      long onum = it.next();

      Long oldGlobID = globIDByOnum.put(onum, globID);
      if (oldGlobID == null) continue;

      Pair<GroupContainer, MutableInteger> entry = globTable.get(oldGlobID);
      if (entry == null) continue;

      if (entry.second.value == 1) {
        // We've removed the last pin. Evict the old glob from the glob table.
        globTable.remove(oldGlobID);
      } else {
        entry.second.value--;
      }
    }
  }

  /**
   * Removes from cache the glob associated with the given onum.
   */
  protected final void removeGlobByOnum(long onum) {
    Long globID = globIDByOnum.remove(onum);
    if (globID != null) globTable.remove(globID);
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
    Pair<Long, LongSet> locks = rwLocks.get(onum);
    if (locks == null) return false;

    if (locks.first != null) return true;

    for (LongIterator it = locks.second.iterator(); it.hasNext();)
      if (it.next() != tid) return true;

    return false;
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
    Pair<Long, LongSet> locks = rwLocks.get(onum);
    return locks != null && locks.first != null;
  }

  /**
   * Adjusts rwCount to account for the fact that the given transaction is about
   * to be committed or aborted.
   */
  protected final void unpin(PendingTransaction tx) {
    for (long onum : tx) {
      Pair<Long, LongSet> locks = rwLocks.get(onum);
      if (locks.first != null && locks.first == tx.tid) locks.first = null;
      locks.second.remove(tx.tid);

      if (locks.first == null && locks.second.isEmpty()) rwLocks.remove(onum);
    }
  }

  /**
   * <p>
   * Return a set of onums that aren't currently occupied. The ObjectStore may
   * return the same onum more than once from this method, althogh doing so
   * would encourage collisions. There is no assumption of unpredictability or
   * randomness about the returned ids.
   * </p>
   * <p>
   * The returned onums should be packed in the lower 48 bits. We assume that
   * the object store is never full, and can always provide new onums
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
   * Returns the name of this core.
   */
  public final String getName() {
    return name;
  }

  /**
   * Gracefully shutdown the object store.
   * 
   * @throws IOException
   */
  public abstract void close() throws IOException;

  /**
   * Determines whether the object store has been initialized.
   */
  protected abstract boolean isInitialized();

  /**
   * Sets a flag to indicate that the object store has been initialized.
   */
  protected abstract void setInitialized();

  /**
   * Ensures that the object store has been properly initialized. This creates,
   * for example, the name-service map and the core's principal, if they do not
   * already exist in the store.
   */
  @SuppressWarnings("deprecation")
  public final void ensureInit() {
    if (isInitialized()) return;

    final Core core = Client.getClient().getCore(name);

    Client.runInTransaction(new Client.Code<Void>() {
      public Void run() {
        // No need to initialize global constants here, as those objects will be
        // supplied by the clients' local core.
        String principalName =
            new X500Principal("CN=" + name
                + ",OU=Fabric,O=Cornell University,L=Ithaca,ST=NY,C=US")
                .getName();
        NodePrincipal.$Impl principal =
            new NodePrincipal.$Impl(core, null, principalName);
        principal.$forceRenumber(ONumConstants.CORE_PRINCIPAL);

        // Create the label {core->_; core<-_} for the root map.
        ReaderPolicy confid =
            (ReaderPolicy) new ReaderPolicy.$Impl(core, publicReadonlyLabel(),
                corePrincipal(), null).$getProxy();
        WriterPolicy integ =
            (WriterPolicy) new WriterPolicy.$Impl(core, publicReadonlyLabel(),
                corePrincipal(), null).$getProxy();
        Label label =
            (Label) new PairLabel.$Impl(core, null, confid, integ).$getProxy();

        fabric.util.HashMap.$Impl map =
            new fabric.util.HashMap.$Impl(core, label);
        map.$forceRenumber(ONumConstants.ROOT_MAP);

        return null;
      }
    });

    setInitialized();
  }

  private final NodePrincipal corePrincipal() {
    if (corePrincipal == null) {
      Core core = Client.getClient().getCore(name);
      corePrincipal = new NodePrincipal.$Proxy(core, ONumConstants.CORE_PRINCIPAL);
    }

    return corePrincipal;
  }

  private final Label publicReadonlyLabel() {
    if (publicReadonlyLabel == null) {
      Core core = Client.getClient().getCore(name);
      publicReadonlyLabel =
          new Label.$Proxy(core, ONumConstants.PUBLIC_READONLY_LABEL);
    }

    return publicReadonlyLabel;
  }

}
