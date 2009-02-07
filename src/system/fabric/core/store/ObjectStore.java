package fabric.core.store;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import jif.lang.*;
import fabric.client.Client;
import fabric.client.Core;
import fabric.common.AccessException;
import fabric.common.ONumConstants;
import fabric.common.SerializedObject;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.Principal;

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
  private Principal corePrincipal;
  private Label publicReadonlyLabel;

  protected static class MutableInteger {
    public int value;

    public MutableInteger() {
      this(0);
    }

    public MutableInteger(int value) {
      this.value = value;
    }
  }

  /**
   * The data stored for a partially prepared transaction.
   */
  protected static final class PendingTransaction implements Iterable<Long> {
    public final Principal owner;
    public final Collection<Long> reads;

    /**
     * Objects that have been modified or created.
     */
    public final Collection<SerializedObject> modData;

    PendingTransaction(Principal owner) {
      this.owner = owner;
      this.reads = new ArrayList<Long>();
      this.modData = new ArrayList<SerializedObject>();
    }

    /**
     * Deserialization constructor.
     */
    public PendingTransaction(ObjectInputStream in) throws IOException {
      if (in.readBoolean()) {
        Core core = Client.getClient().getCore(in.readUTF());
        this.owner = new Principal.$Proxy(core, in.readLong());
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
    public void write(ObjectOutputStream out) throws IOException {
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
   * The table of partially prepared transactions.
   */
  protected final Map<Integer, PendingTransaction> pendingByTid;

  /**
   * Tracks the read/write pins for each onum. A value of -1 represents a
   * write-pin; otherwise, the value is the number of read-pins. While this may
   * look like a locking mechanism, it is not. TransactionManager is responsible
   * for implementing the locking discipline for preparing transactions.
   */
  protected final LongKeyMap<MutableInteger> rwCount;

  protected ObjectStore(String name) {
    this.name = name;
    this.pendingByTid = new HashMap<Integer, PendingTransaction>();
    this.rwCount = new LongKeyHashMap<MutableInteger>();
  }

  /**
   * Opens a new transaction.
   * 
   * @param client
   *          the client under whose authority the transaction is running.
   * @return a transaction identifier that can be subsequently be passed to
   *         registerCreate(), registerRead(), registerWrite(), commit(), or
   *         abort().
   * @throws AccessException
   *           if the client has insufficient privileges.
   */
  public final int beginTransaction(Principal client) throws AccessException {
    int tid = newTid(client);
    pendingByTid.put(tid, new PendingTransaction(client));
    return tid;
  }

  /**
   * Allocates a new transaction ID for the given client.
   * 
   * @throws AccessException
   *           if the client has insufficient privileges.
   */
  protected abstract int newTid(Principal client) throws AccessException;

  /**
   * Registers that a transaction has read an object.
   */
  public final void registerRead(int tid, long onum) {
    addRWPin(onum, false);
    pendingByTid.get(tid).reads.add(onum);
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
  public final void registerUpdate(int tid, SerializedObject obj) {
    addRWPin(obj.getOnum(), true);
    pendingByTid.get(tid).modData.add(obj);
  }

  /**
   * Registers that an onum was involved in a transaction by associating another
   * reader/writer pin with the onum.
   */
  private void addRWPin(long onum, boolean write) {
    MutableInteger count = rwCount.get(onum);
    if (count != null) {
      count.value++;
      return;
    }

    rwCount.put(onum, new MutableInteger(write ? -1 : 1));
  }

  /**
   * Rolls back a partially prepared transaction. (i.e., one for which
   * finishPrepare() has yet to be called.)
   */
  public final void abortPrepare(int tid) {
    unpin(pendingByTid.remove(tid));
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
  public abstract void finishPrepare(int tid);

  /**
   * Cause the objects prepared in transaction [tid] to be committed. The
   * changes will hereafter be visible to read.
   * 
   * @param client
   *          the principal requesting the commit
   * @param tid
   *          the identifier (returned by prepare) corresponding to the
   *          transaction
   * @throws AccessException
   *           if the principal differs from the caller of prepare()
   */
  public abstract void commit(Principal client, int tid) throws AccessException;

  /**
   * Cause the objects prepared in transaction [tid] to be discarded.
   * 
   * @param client
   *          the principal requesting the rollback
   * @param tid
   *          the identifier (returned by prepare) corresponding to the
   *          transaction
   * @throws AccessException
   *           if the principal differs from the caller of prepare()
   */
  public abstract void rollback(Principal client, int tid)
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
   * Determine whether an onum has outstanding uncommitted changes or reads.
   * 
   * @param onum
   *          the object number in question
   * @return true if the object has been prepared by transaction that hasn't
   *         been committed or rolled back.
   */
  public final boolean isPrepared(long onum) {
    return rwCount.containsKey(onum);
  }

  /**
   * Determine whether an onum has outstanding uncommitted reads.
   * 
   * @param onum
   *          the object number in question
   * @return true if the object has been read by a transaction that hasn't been
   *         committed or rolled back.
   */
  public final boolean isRead(long onum) {
    MutableInteger count = rwCount.get(onum);
    return count != null && count.value > 0;
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
    MutableInteger count = rwCount.get(onum);
    return count != null && count.value < 0;
  }

  /**
   * Adjusts rwCount to account for the fact that the given transaction is about
   * to be committed or aborted.
   */
  protected final void unpin(PendingTransaction tx) {
    for (long oid : tx) {
      MutableInteger count = rwCount.get(oid);
      count.value--;
      if (count.value <= 0) rwCount.remove(oid);
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
        Principal.$Impl principal =
            new Principal.$Impl(core, publicReadonlyLabel(), name);
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

  private final Principal corePrincipal() {
    if (corePrincipal == null) {
      Core core = Client.getClient().getCore(name);
      corePrincipal = new Principal.$Proxy(core, ONumConstants.CORE_PRINCIPAL);
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
