package fabric.store.db;

import static com.sleepycat.je.OperationStatus.SUCCESS;
import static fabric.common.Logging.STORE_DB_LOGGER;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;

import fabric.common.FastSerializable;
import fabric.common.ONumConstants;
import fabric.common.Resources;
import fabric.common.SerializedObject;
import fabric.common.Surrogate;
import fabric.common.Threading;
import fabric.common.VersionWarranty;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.util.Cache;
import fabric.common.util.LongKeyCache;
import fabric.common.util.OidKeyHashMap;
import fabric.common.util.Pair;
import fabric.lang.FClass;
import fabric.lang.security.Principal;
import fabric.store.SubscriptionManager;
import fabric.store.TransactionManager;
import fabric.worker.Store;
import fabric.worker.Worker;

/**
 * An ObjectDB backed by a Berkeley Database.
 */
public class BdbDB extends ObjectDB {

  private Environment env;
  private Database meta;

  /**
   * Database containing the actual serialized Fabric objects.
   */
  private Database db;

  /**
   * Database containing data for prepared but uncommitted transactions.
   */
  private Database prepared;

  /**
   * Database containing the commit times for confirmed transactions
   * (uncommitted transactions for which COMMIT messages have been received).
   */
  private Database commitTimes;

  private final DatabaseEntry initializationStatus;
  private final DatabaseEntry onumCounter;

  /**
   * The BDB key under which the longest warranty is stored.
   */
  private final DatabaseEntry longestWarrantyEntry;

  private long nextOnum;

  /**
   * To prevent touching BDB on every onum reservation request, we keep a bunch
   * of onums in reserve. If nextOnum > lastReservedOnum, it's time to touch BDB
   * again to reserve more onums.
   */
  private long lastReservedOnum;

  /**
   * Cache: maps onums to object versions of objects that are currently stored
   * in BDB.
   */
  private final LongKeyCache<Integer> cachedVersions;

  /**
   * Cache: objects.
   */
  private final LongKeyCache<SerializedObject> cachedObjects;

  /**
   * Cache: maps BDB keys to prepared-transaction records.
   */
  private final Cache<ByteArray, PendingTransaction> preparedTransactions;

  /**
   * Creates a new BdbStore for the store specified. A new database will be
   * created if it does not exist.
   * 
   * @param name
   *          name of store to create store for.
   */
  public BdbDB(String name) {
    super(name);

    String path = Resources.relpathRewrite("var", "bdb", name);
    new File(path).mkdirs(); // create path if it does not exist

    try {
      EnvironmentConfig conf = new EnvironmentConfig();
      conf.setAllowCreate(true);
      conf.setTransactional(true);
      env = new Environment(new File(path), conf);

      STORE_DB_LOGGER.info("Bdb env opened");

      DatabaseConfig dbconf = new DatabaseConfig();
      dbconf.setAllowCreate(true);
      dbconf.setTransactional(true);
      db = env.openDatabase(null, "store", dbconf);
      prepared = env.openDatabase(null, "prepared", dbconf);
      commitTimes = env.openDatabase(null, "commitTimes", dbconf);
      meta = env.openDatabase(null, "meta", dbconf);

      STORE_DB_LOGGER.info("Bdb databases opened");
    } catch (DatabaseException e) {
      STORE_DB_LOGGER.log(Level.SEVERE, "Bdb error in <init>: ", e);
      throw new InternalError(e);
    }

    try {
      initializationStatus =
          new DatabaseEntry("initialization_status".getBytes("UTF-8"));
      onumCounter = new DatabaseEntry("onum_counter".getBytes("UTF-8"));
      longestWarrantyEntry =
          new DatabaseEntry("longest_warranty".getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new InternalError(e);
    }

    this.nextOnum = -1;
    this.lastReservedOnum = -2;
    this.cachedVersions = new LongKeyCache<Integer>();
    this.cachedObjects = new LongKeyCache<SerializedObject>();
    this.preparedTransactions = new Cache<ByteArray, PendingTransaction>();
  }

  @Override
  protected void saveLongestWarranty() {
    STORE_DB_LOGGER.fine("Bdb saving longest warranty");

    try {
      Transaction txn = env.beginTransaction(null, null);
      DatabaseEntry data = new DatabaseEntry(toBytes(longestWarranty.expiry()));
      meta.put(txn, longestWarrantyEntry, data);
      txn.commit();
    } catch (DatabaseException e) {
      STORE_DB_LOGGER
          .log(Level.SEVERE, "Bdb error in saveLongestWarranty: ", e);
      throw new InternalError(e);
    }
  }

  @Override
  public void finishPrepareWrites(long tid, Principal worker) {
    // Copy the transaction data into BDB.
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    PendingTransaction pending = submap.remove(worker);
    if (submap.isEmpty()) pendingByTid.remove(tid);

    try {
      Transaction txn = env.beginTransaction(null, null);
      byte[] key = toBytes(tid, worker);
      DatabaseEntry data = new DatabaseEntry(toBytes(pending));
      prepared.put(txn, new DatabaseEntry(key), data);
      txn.commit();

      preparedTransactions.put(new ByteArray(key), pending);
      STORE_DB_LOGGER.finer("Bdb prepare success tid " + tid);
    } catch (DatabaseException e) {
      STORE_DB_LOGGER.log(Level.SEVERE, "Bdb error in finishPrepare: ", e);
      throw new InternalError(e);
    }
  }

  @Override
  public void scheduleCommit(long tid, long commitTime,
      Principal workerPrincipal, SubscriptionManager sm) {
    scheduleCommit(tid, commitTime, workerPrincipal, sm, true);
  }

  /**
   * @param logCommitTime
   *          whether to log the commit time to stable storage for recovery
   *          purposes.
   */
  private void scheduleCommit(final long tid, long commitTime,
      final Principal workerPrincipal, final SubscriptionManager sm,
      boolean logCommitTime) {
    long commitDelay = commitTime - System.currentTimeMillis();
    STORE_DB_LOGGER
        .finer("Scheduling Bdb commit for tid " + tid + " to run at "
            + new Date(commitTime) + " (in " + commitDelay + " ms)");

    // Record the commit time in BDB.
    final DatabaseEntry tidBdbKey =
        new DatabaseEntry(toBytes(tid, workerPrincipal));
    if (logCommitTime) {
      try {
        Transaction txn = env.beginTransaction(null, null);
        DatabaseEntry data = new DatabaseEntry(toBytes(commitTime));
        commitTimes.put(txn, tidBdbKey, data);
        txn.commit();
      } catch (DatabaseException e) {
        STORE_DB_LOGGER.log(Level.SEVERE, "Bdb error in scheduleCommit: ", e);
        throw new InternalError(e);
      }
    }

    Threading.scheduleAt(commitTime, new Runnable() {
      @Override
      public void run() {
        synchronized (BdbDB.this) {
          STORE_DB_LOGGER.finer("Bdb commit begin tid " + tid);

          try {
            Transaction txn = env.beginTransaction(null, null);

            // Remove the commit time from BDB.
            commitTimes.delete(txn, tidBdbKey);

            // Obtain the transaction record.
            PendingTransaction pending = remove(workerPrincipal, txn, tid);

            if (pending != null) {
              Serializer serializer = new Serializer();
              for (Pair<SerializedObject, UpdateType> update : pending.modData) {
                SerializedObject o = update.first;
                long onum = o.getOnum();
                STORE_DB_LOGGER.finest("Bdb committing onum " + onum);
                DatabaseEntry onumData = new DatabaseEntry(toBytes(onum));
                DatabaseEntry objData = new DatabaseEntry(serializer.toBytes(o));
                db.put(txn, onumData, objData);

                // Remove any cached globs containing the old version of this object.
                notifyCommittedUpdate(sm, toLong(onumData.getData()));

                // Update caches.
                cachedVersions.put(onum, o.getVersion());
                cachedObjects.put(onum, o);
              }

              txn.commit();
              STORE_DB_LOGGER.finer("Bdb commit success tid " + tid);
            } else {
              txn.abort();
              STORE_DB_LOGGER.warning("Bdb commit not found tid " + tid);
              throw new InternalError("Unknown transaction id " + tid);
            }
          } catch (DatabaseException e) {
            // Problem. Clear out caches.
            cachedVersions.clear();
            cachedObjects.clear();

            STORE_DB_LOGGER.log(Level.SEVERE, "Bdb error in commit: ", e);
            throw new InternalError(e);
          }
        }
      }
    });
  }

  @Override
  public void rollback(long tid, Principal worker) {
    STORE_DB_LOGGER.finer("Bdb rollback begin tid " + tid);

    try {
      Transaction txn = env.beginTransaction(null, null);
      remove(worker, txn, tid);
      txn.commit();
      STORE_DB_LOGGER.finer("Bdb rollback success tid " + tid);
    } catch (DatabaseException e) {
      STORE_DB_LOGGER.log(Level.SEVERE, "Bdb error in rollback: ", e);
      throw new InternalError(e);
    }
  }

  @Override
  public SerializedObject read(long onum) {
    if (cachedObjects.containsKey(onum)) {
      SerializedObject cached = cachedObjects.get(onum);
      if (cached != null) return cached;
    }

    STORE_DB_LOGGER.finest("Bdb read onum " + onum);
    DatabaseEntry key = new DatabaseEntry(toBytes(onum));
    DatabaseEntry data = new DatabaseEntry();

    try {
      if (db.get(null, key, data, LockMode.DEFAULT) == SUCCESS) {
        SerializedObject result = toSerializedObject(data.getData());
        if (result != null) {
          cachedVersions.put(onum, result.getVersion());
          cachedObjects.put(onum, result);
        }

        return result;
      }
    } catch (DatabaseException e) {
      STORE_DB_LOGGER.log(Level.SEVERE, "Bdb error in read: ", e);
      throw new InternalError(e);
    }

    return null;
  }

  @Override
  public int getVersion(long onum) throws AccessException {
    Integer ver = cachedVersions.get(onum);
    if (ver != null) return ver;

    return super.getVersion(onum);
  }

  @Override
  public boolean exists(long onum) {
    DatabaseEntry key = new DatabaseEntry(toBytes(onum));
    DatabaseEntry data = new DatabaseEntry();

    try {
      if (writeLocks.get(onum) != null
          || db.get(null, key, data, LockMode.DEFAULT) == SUCCESS) {
        return true;
      }
    } catch (DatabaseException e) {
      STORE_DB_LOGGER.log(Level.SEVERE, "Bdb error in exists: ", e);
      throw new InternalError(e);
    }

    return false;
  }

  private final long ONUM_RESERVE_SIZE = 10240;

  @Override
  public long[] newOnums(int num) {
    STORE_DB_LOGGER.fine("Bdb new onums begin");

    try {
      long[] onums = new long[num];
      for (int i = 0; i < num; i++) {
        if (nextOnum > lastReservedOnum) {
          // Reserve more onums from BDB.
          Transaction txn = env.beginTransaction(null, null);
          DatabaseEntry data = new DatabaseEntry();
          nextOnum = ONumConstants.FIRST_UNRESERVED;

          if (meta.get(txn, onumCounter, data, LockMode.DEFAULT) == SUCCESS) {
            nextOnum = toLong(data.getData());
          }

          lastReservedOnum = nextOnum + ONUM_RESERVE_SIZE + num - i - 1;

          data.setData(toBytes(lastReservedOnum + 1));
          meta.put(txn, onumCounter, data);
          txn.commit();

          STORE_DB_LOGGER.fine("Bdb reserved onums " + nextOnum + "--"
              + lastReservedOnum);
        }

        onums[i] = nextOnum++;
      }

      return onums;
    } catch (DatabaseException e) {
      STORE_DB_LOGGER.log(Level.SEVERE, "Bdb error in newOnums: ", e);
      throw new InternalError(e);
    }
  }

  /**
   * Clean up and close database.
   */
  @Override
  public void close() {
    try {
      if (db != null) db.close();
      if (prepared != null) prepared.close();
      if (commitTimes != null) commitTimes.close();
      if (meta != null) meta.close();
      if (env != null) env.close();
    } catch (DatabaseException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean isInitialized() {
    STORE_DB_LOGGER.fine("Bdb is initialized begin");

    try {
      Transaction txn = env.beginTransaction(null, null);
      DatabaseEntry data = new DatabaseEntry();
      boolean result = false;

      if (meta.get(txn, initializationStatus, data, LockMode.DEFAULT) == SUCCESS) {
        result = toBoolean(data.getData());
      }

      txn.commit();

      return result;
    } catch (DatabaseException e) {
      STORE_DB_LOGGER.log(Level.SEVERE, "Bdb error in isInitialized: ", e);
      throw new InternalError(e);
    }
  }

  @Override
  public void setInitialized() {
    STORE_DB_LOGGER.fine("Bdb set initialized begin");

    try {
      Transaction txn = env.beginTransaction(null, null);
      DatabaseEntry data = new DatabaseEntry(toBytes(true));
      meta.put(txn, initializationStatus, data);
      txn.commit();
    } catch (DatabaseException e) {
      STORE_DB_LOGGER.log(Level.SEVERE, "Bdb error in isInitialized: ", e);
      throw new InternalError(e);
    }
  }

  @Override
  protected void recoverState(TransactionManager tm) {
    STORE_DB_LOGGER.fine("Bdb recovering state");

    try {
      // Scan through the database of prepares to recover writeLocks,
      // writtenOnumsByTid, and warranties.
      Transaction txn = env.beginTransaction(null, null);
      DatabaseEntry key = new DatabaseEntry();
      DatabaseEntry data = new DatabaseEntry();
      {
        Cursor preparedCursor = prepared.openCursor(txn, null);
        while (preparedCursor.getNext(key, data, null) != OperationStatus.NOTFOUND) {
          PendingTransaction pending = toPendingTransaction(data.getData());
          long tid = pending.tid;
          Principal owner = pending.owner;
          if (commitTimes.get(txn, key, data, LockMode.DEFAULT) != SUCCESS) {
            throw new InternalError("Cannot find commit time for tid=" + tid
                + ", owner=" + owner.$getStore() + "/" + owner.$getOnum());
          }
          VersionWarranty warranty =
              new VersionWarranty(toLong(data.getData()));

          STORE_DB_LOGGER.finer("Recoving state for tid=" + tid + ", owner="
              + owner.$getStore() + "/" + owner.$getOnum() + " (commit time="
              + new Date(warranty.expiry()) + ")");

          // Loop through the updates for the current transaction.
          for (Pair<SerializedObject, UpdateType> update : pending.modData) {
            long onum = update.first.getOnum();

            // Recover the transaction's write lock for the current update.
            writeLocks.put(onum, tid);

            if (update.second == UpdateType.WRITE) {
              STORE_DB_LOGGER.finest("Recovered write to " + onum);
              addWrittenOnumByTid(tid, owner, onum);

              // Restore the object's warranty.
              versionWarrantyTable.put(onum, warranty);
            } else {
              STORE_DB_LOGGER.finest("Recovered create for " + onum);
            }
          }
        }
        preparedCursor.close();
      }

      // Scan through the commitTimes database and build a commit schedule.
      SortedMap<Long, List<Pair<Long, Principal>>> commitSchedule =
          new TreeMap<Long, List<Pair<Long, Principal>>>();
      {
        Cursor commitTimesCursor = commitTimes.openCursor(txn, null);
        while (commitTimesCursor.getNext(key, data, null) != OperationStatus.NOTFOUND) {
          Pair<Long, Principal> tid = toTid(key.getData());
          long commitTime = toLong(data.getData());

          List<Pair<Long, Principal>> toCommit = commitSchedule.get(commitTime);
          if (toCommit == null) {
            toCommit = new ArrayList<Pair<Long, Principal>>(2);
            commitSchedule.put(commitTime, toCommit);
          }

          toCommit.add(tid);
        }
        commitTimesCursor.close();
      }

      // Recover the longest warranty issued and use that as the default
      // warranty.
      if (meta.get(txn, longestWarrantyEntry, data, LockMode.DEFAULT) == SUCCESS) {
        longestWarranty = new VersionWarranty(toLong(data.getData()));
        versionWarrantyTable.setDefaultWarranty(longestWarranty);
      }

      txn.commit();

      // Re-schedule commits.
      SubscriptionManager sm = tm.subscriptionManager();
      for (Entry<Long, List<Pair<Long, Principal>>> entry : commitSchedule
          .entrySet()) {
        long commitTime = entry.getKey();
        for (Pair<Long, Principal> tid : entry.getValue()) {
          scheduleCommit(tid.first, commitTime, tid.second, sm, false);
        }
      }
    } catch (DatabaseException e) {
      STORE_DB_LOGGER.log(Level.SEVERE, "Bdb error in recoverState: ", e);
      throw new InternalError(e);
    }
  }

  /**
   * Removes a PendingTransaction from the prepare log and returns it. If no
   * transaction with the given transaction id is found, null is returned.
   * 
   * @param worker
   *          the principal under which this action is being executed.
   * @param txn
   *          the BDB Transaction instance that should be used to perform the
   *          retrieval.
   * @param tid
   *          the transaction id.
   * @return the PendingTransaction corresponding to tid
   * @throws DatabaseException
   *           if a database error occurs
   */
  private PendingTransaction remove(Principal worker, Transaction txn, long tid)
      throws DatabaseException {
    byte[] key = toBytes(tid, worker);
    DatabaseEntry bdbKey = new DatabaseEntry(key);
    DatabaseEntry data = new DatabaseEntry();

    PendingTransaction pending =
        preparedTransactions.remove(new ByteArray(key));

    if (pending == null
        && prepared.get(txn, bdbKey, data, LockMode.DEFAULT) == SUCCESS)
      pending = toPendingTransaction(data.getData());

    if (pending == null) return null;
    prepared.delete(txn, bdbKey);

    unpin(pending);
    return pending;
  }

  private static byte[] toBytes(boolean b) {
    byte[] result = { (byte) (b ? 1 : 0) };
    return result;
  }

  private static boolean toBoolean(byte[] data) {
    return data[0] == 1;
  }

  private static byte[] toBytes(long i) {
    byte[] data = new byte[8];

    for (int j = 0; j < 8; j++) {
      data[7 - j] = (byte) (i & 0xff);
      i = i >>> 8;
    }

    return data;
  }

  private static byte[] toBytes(long tid, Principal worker) {
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      DataOutputStream dos = new DataOutputStream(bos);
      dos.writeLong(tid);
      if (worker != null) {
        dos.writeUTF(worker.$getStore().name());
        dos.writeLong(worker.$getOnum());
      }
      dos.flush();
      return bos.toByteArray();
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }

  private static Pair<Long, Principal> toTid(byte[] data) {
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      DataInputStream dis = new DataInputStream(bis);
      long tid = dis.readLong();

      if (data.length < 10) return new Pair<Long, Principal>(tid, null);
      Store store = Worker.getWorker().getStore(dis.readUTF());
      long onum = dis.readLong();
      Principal owner = new Principal._Proxy(store, onum);
      return new Pair<Long, Principal>(tid, owner);
    } catch (IOException e) {
      throw new InternalError();
    }
  }

  private static long toLong(byte[] data) {
    long i = 0;

    for (int j = 0; j < 8; j++) {
      i = i << 8;
      i = i | (data[j] & 0xff);
    }

    return i;
  }

  private static byte[] toBytes(FastSerializable obj) {
    return new Serializer().toBytes(obj);
  }

  private static PendingTransaction toPendingTransaction(byte[] data) {
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      ObjectInputStream ois = new ObjectInputStream(bis);
      return new PendingTransaction(ois);
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }

  private static SerializedObject toSerializedObject(byte[] data) {
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      ObjectInputStream ois = new ObjectInputStream(bis);
      return new SerializedObject(ois);
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }

  /**
   * Utility class for serializing FastSerializable objects. This avoids
   * creating a new ObjectOutputStream for each object serialized, while
   * maintaining the illusion that each object is serialized with a fresh
   * ObjectOutputStream. This allows each object to be deserialized separately.
   */
  private static class Serializer {
    private final ByteArrayOutputStream bos;
    private final ObjectOutputStream oos;
    private static final byte[] HEADER;

    static {
      // Save the header that ObjectOutputStream writes upon its initialization.
      try {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.flush();
        HEADER = bos.toByteArray();
      } catch (IOException e) {
        throw new InternalError(e);
      }
    }

    public Serializer() {
      try {
        this.bos = new ByteArrayOutputStream();
        this.oos = new ObjectOutputStream(bos);
        oos.flush();
      } catch (IOException e) {
        throw new InternalError(e);
      }
    }

    public byte[] toBytes(FastSerializable obj) {
      try {
        bos.reset();
        bos.write(HEADER);
        obj.write(oos);
        oos.flush();
        return bos.toByteArray();
      } catch (IOException e) {
        throw new InternalError(e);
      }
    }
  }

  /**
   * Dumps the contents of a BDB object database to stdout.
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Usage: fabric.store.db.BdbDB STORE_NAME");
      System.err.println();
      System.err.println("  Dumps a BDB object database in CSV format.");
      return;
    }

    BdbDB db = new BdbDB(args[0]);
    Cursor cursor = db.db.openCursor(null, null);
    DatabaseEntry key = new DatabaseEntry();
    DatabaseEntry value = new DatabaseEntry();

    System.out.println("onum,class name,version number,update label onum,"
        + "access label onum");
    while (cursor.getNext(key, value, null) == OperationStatus.SUCCESS) {
      SerializedObject obj = toSerializedObject(value.getData());
      long onum = obj.getOnum();
      String className = obj.getClassName();
      int version = obj.getVersion();
      long updateLabelOnum = obj.getUpdateLabelOnum();
      long accessPolicyOnum = obj.getAccessPolicyOnum();
      String extraInfo = "";
      try {
        // Get extra information on surrogates and FClasses.
        // This code depends on the serialization format of those classes, and
        // is therefore rather fragile.
        if (Surrogate.class.getName().equals(className)) {
          ObjectInputStream ois =
              new ObjectInputStream(obj.getSerializedDataStream());
          extraInfo = ",ref=" + ois.readUTF() + "/" + ois.readLong();
        } else if (FClass.class.getName().equals(className)) {
          ObjectInputStream ois =
              new ObjectInputStream(obj.getSerializedDataStream());
          extraInfo = ",name=" + ois.readObject();
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
      System.out.println(onum + "," + className + "," + version + ","
          + updateLabelOnum + "," + accessPolicyOnum + extraInfo);
    }
  }

  private static class ByteArray {
    private final byte[] data;

    public ByteArray(byte[] data) {
      this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof ByteArray)) return false;

      byte[] data = ((ByteArray) obj).data;
      return Arrays.equals(data, ((ByteArray) obj).data);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(data);
    }

  }
}
