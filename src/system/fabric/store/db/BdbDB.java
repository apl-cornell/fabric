package fabric.store.db;

import static com.sleepycat.je.OperationStatus.SUCCESS;

import static fabric.common.Logging.STORE_DB_LOGGER;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.Random;
import java.util.logging.Level;

import com.sleepycat.bind.tuple.BooleanBinding;
import com.sleepycat.bind.tuple.LongBinding;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockConflictException;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;

import fabric.common.FastSerializable;
import fabric.common.Logging;
import fabric.common.ONumConstants;
import fabric.common.Resources;
import fabric.common.SerializedObject;
import fabric.common.Surrogate;
import fabric.common.SysUtil;
import fabric.common.exceptions.AccessException;
import fabric.common.exceptions.InternalError;
import fabric.common.net.RemoteIdentity;
import fabric.common.util.LongHashSet;
import fabric.common.util.LongKeyCache;
import fabric.common.util.LongSet;
import fabric.common.util.MutableInteger;
import fabric.common.util.MutableLong;
import fabric.common.util.OidKeyHashMap;
import fabric.lang.FClass;
import fabric.lang.security.Principal;
import fabric.store.SubscriptionManager;
import fabric.worker.TransactionPrepareFailedException;
import fabric.worker.Worker;
import fabric.worker.remote.RemoteWorker;

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

  private final DatabaseEntry initializationStatus;
  private final DatabaseEntry onumCounter;

  private final MutableLong nextOnum;

  /**
   * To prevent touching BDB on every onum reservation request, we keep a bunch
   * of onums in reserve. If nextOnum > lastReservedOnum, it's time to touch BDB
   * again to reserve more onums.
   */
  private final MutableLong lastReservedOnum;

  /**
   * Cache: maps onums to object versions of objects that are currently stored
   * in BDB. Because Integers are interned, it doesn't make much sense to have
   * SoftReferences to Integers, so we use MutableIntegers instead.
   */
  private final LongKeyCache<MutableInteger> cachedVersions;

  /**
   * Creates a new BdbStore for the store specified. A new database will be
   * created if it does not exist.
   *
   * @param name
   *          name of store to create store for.
   */
  public BdbDB(String name, PrivateKey privateKey) {
    super(name, privateKey);

    String path = Resources.relpathRewrite("var", "bdb", name);
    new File(path).mkdirs(); // create path if it does not exist

    try {
      EnvironmentConfig conf = new EnvironmentConfig();
      conf.setAllowCreate(true);
      conf.setTransactional(true);
      conf.setCachePercent(40);
      conf.setSharedCache(true);
      env = new Environment(new File(path), conf);

      STORE_DB_LOGGER.info("Bdb env opened");

      DatabaseConfig dbconf = new DatabaseConfig();
      dbconf.setAllowCreate(true);
      dbconf.setTransactional(true);
      db = env.openDatabase(null, "store", dbconf);
      meta = env.openDatabase(null, "meta", dbconf);

      dbconf.setSortedDuplicates(true);

      initRwCount();

      STORE_DB_LOGGER.info("Bdb databases opened");
    } catch (DatabaseException e) {
      STORE_DB_LOGGER.log(Level.SEVERE, "Bdb error in <init>: ", e);
      throw new InternalError(e);
    }

    try {
      initializationStatus =
          new DatabaseEntry("initialization_status".getBytes("UTF-8"));
      onumCounter = new DatabaseEntry("onum_counter".getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new InternalError(e);
    }

    this.nextOnum = new MutableLong(-1);
    this.lastReservedOnum = new MutableLong(-2);
    this.cachedVersions = new LongKeyCache<>();
  }

  @Override
  public void finishPrepare(final long tid, final Principal worker)
      throws TransactionPrepareFailedException {
    // Copy the transaction data into BDB.
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    final PendingTransaction pending;
    synchronized (submap) {
      pending = submap.get(worker);
      if (pending != null) {
        synchronized (pending) {
          switch (pending.state) {
          case ABORTING:
            // Should clean up here.  This means the abort message was handled
            // after prepare was done but before this call.
            abortPrepare(tid, worker);
            throw new TransactionPrepareFailedException(
                "Aborted by another thread");
          case PREPARED:
            throw new InternalError(
                "Attempting to finish an already finished prepare.");
          case PREPARING:
            pending.state = PendingTransaction.State.PREPARED;
          }
        }
      }
    }
    STORE_DB_LOGGER.log(Level.FINER, "Bdb prepare success tid {0}", tid);
  }

  @Override
  public void commit(final long tid,
      final RemoteIdentity<RemoteWorker> workerIdentity,
      final SubscriptionManager sm) {
    STORE_DB_LOGGER.log(Level.FINER, "Bdb commit begin tid {0}", tid);

    PendingTransaction pending =
        runInBdbTransaction(new Code<PendingTransaction, RuntimeException>() {
          @Override
          public PendingTransaction run(Transaction txn)
              throws RuntimeException {
            PendingTransaction pending =
                getPrepared(workerIdentity.principal, txn, tid);

            if (pending != null) {
              Serializer<SerializedObject> serializer = new Serializer<>();
              for (SerializedObject o : pending.getCreates()) {
                long onum = o.getOnum();
                STORE_DB_LOGGER.log(Level.FINEST, "Bdb committing onum {0}",
                    onum);

                DatabaseEntry onumData = new DatabaseEntry();
                LongBinding.longToEntry(onum, onumData);

                DatabaseEntry objData =
                    new DatabaseEntry(serializer.toBytes(o));

                db.put(txn, onumData, objData);
              }
              for (SerializedObject o : pending.getWrites()) {
                long onum = o.getOnum();
                STORE_DB_LOGGER.log(Level.FINEST, "Bdb committing onum {0}",
                    onum);

                DatabaseEntry onumData = new DatabaseEntry();
                LongBinding.longToEntry(onum, onumData);

                DatabaseEntry objData =
                    new DatabaseEntry(serializer.toBytes(o));

                db.put(txn, onumData, objData);

                // Update the local worker cache if this is a remote worker
                // updating the value.
                // If the update is from the local worker, the already
                // deserialized version in the worker transaction will be in the
                // cache after 2PC.
                if (!workerIdentity.node
                    .equals(Worker.getWorker().inProcessRemoteWorker))
                  Worker.getWorker().getStore(getName()).updateCache(o);
              }

              return pending;
            } else {
              STORE_DB_LOGGER.log(Level.WARNING, "Bdb commit not found tid {0}",
                  tid);
              throw new InternalError("Unknown transaction id " + tid);
            }
          }
        });

    LongSet writtenOnums = new LongHashSet();

    // Fix up caches.
    for (SerializedObject o : SysUtil.chain(pending.getCreates(),
        pending.getWrites())) {
      long onum = o.getOnum();

      writtenOnums.add(onum);

      // Update the version-number cache.
      cacheVersionNumber(onum, o.getVersion());
    }

    // Remove any cached globs containing the old version of this object.
    notifyCommittedUpdates(sm, writtenOnums, workerIdentity.node);

    runInBdbTransaction(new Code<Void, RuntimeException>() {
      @Override
      public Void run(Transaction txn) {
        // Now remove and clean it up.
        remove(workerIdentity.principal, txn, tid);
        return null;
      }
    });

    STORE_DB_LOGGER.log(Level.FINER, "Bdb commit success tid {0}", tid);
  }

  @Override
  public void rollback(final long tid, final Principal worker) {
    STORE_DB_LOGGER.log(Level.FINER, "Bdb rollback begin tid {0}", tid);

    runInBdbTransaction(new Code<Void, RuntimeException>() {
      @Override
      public Void run(Transaction txn) throws RuntimeException {
        remove(worker, txn, tid);
        return null;
      }
    });

    STORE_DB_LOGGER.log(Level.FINER, "Bdb rollback success tid {0}", tid);
  }

  @Override
  public SerializedObject read(final long onum) {
    STORE_DB_LOGGER.log(Level.FINEST, "Bdb read onum {0}", onum);

    return runInBdbTransaction(new Code<SerializedObject, RuntimeException>() {
      @Override
      public SerializedObject run(Transaction txn) throws RuntimeException {
        DatabaseEntry key = new DatabaseEntry();
        LongBinding.longToEntry(onum, key);

        DatabaseEntry data = new DatabaseEntry();

        if (db.get(null, key, data, LockMode.DEFAULT) == SUCCESS) {
          SerializedObject result = toSerializedObject(data.getData());
          if (result != null) {
            cacheVersionNumber(onum, result.getVersion());
          }

          return result;
        }

        return null;
      }
    });
  }

  @Override
  public int getVersion(long onum) throws AccessException {
    MutableInteger ver = cachedVersions.get(onum);

    if (ver == null) return super.getVersion(onum);

    synchronized (ver) {
      return ver.value;
    }
  }

  @Override
  public boolean exists(final long onum) {
    return runInBdbTransaction(new Code<Boolean, RuntimeException>() {
      @Override
      public Boolean run(Transaction txn) throws RuntimeException {
        DatabaseEntry key = new DatabaseEntry();
        LongBinding.longToEntry(onum, key);

        DatabaseEntry data = new DatabaseEntry();
        if (db.get(null, key, data, LockMode.DEFAULT) == SUCCESS) {
          return true;
        }

        return false;
      }
    });
  }

  private final long ONUM_RESERVE_SIZE = 10240;

  @Override
  public long[] newOnums(final int num) {
    STORE_DB_LOGGER.fine("Bdb new onums begin");

    long[] onums = new long[num];
    synchronized (nextOnum) {
      synchronized (lastReservedOnum) {
        for (int i = 0; i < num; i++) {
          final int curI = i;
          if (nextOnum.value > lastReservedOnum.value) {
            // Reserve more onums from BDB.
            runInBdbTransaction(new Code<Void, RuntimeException>() {
              @Override
              public Void run(Transaction txn) throws RuntimeException {
                DatabaseEntry data = new DatabaseEntry();
                nextOnum.value = ONumConstants.FIRST_UNRESERVED;

                if (meta.get(txn, onumCounter, data,
                    LockMode.DEFAULT) == SUCCESS) {
                  nextOnum.value = LongBinding.entryToLong(data);
                }

                lastReservedOnum.value =
                    nextOnum.value + ONUM_RESERVE_SIZE + num - curI - 1;

                LongBinding.longToEntry(lastReservedOnum.value + 1, data);
                meta.put(txn, onumCounter, data);

                return null;
              }
            });

            Logging.log(STORE_DB_LOGGER, Level.FINE,
                "Bdb reserved onums {0}--{1}", nextOnum, lastReservedOnum);
          }

          onums[i] = nextOnum.value++;
        }
      }
    }

    return onums;
  }

  /**
   * Clean up and close database.
   */
  @Override
  public void close() {
    try {
      if (db != null) db.close();
      if (meta != null) meta.close();
      if (env != null) env.close();
    } catch (DatabaseException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean isInitialized() {
    STORE_DB_LOGGER.fine("Bdb is initialized begin");

    return runInBdbTransaction(new Code<Boolean, RuntimeException>() {
      @Override
      public Boolean run(Transaction txn) throws RuntimeException {
        DatabaseEntry data = new DatabaseEntry();

        if (meta.get(txn, initializationStatus, data,
            LockMode.DEFAULT) == SUCCESS) {
          return BooleanBinding.entryToBoolean(data);
        }

        return false;
      }
    });
  }

  @Override
  public void setInitialized() {
    STORE_DB_LOGGER.fine("Bdb set initialized begin");

    runInBdbTransaction(new Code<Void, RuntimeException>() {
      @Override
      public Void run(Transaction txn) throws RuntimeException {
        DatabaseEntry data = new DatabaseEntry();
        BooleanBinding.booleanToEntry(true, data);
        meta.put(txn, initializationStatus, data);
        return null;
      }
    });
  }

  private void initRwCount() {
    // TODO Recover rwCount info from prepared
  }

  /**
   * Grabs a PendingTransaction from the prepare log and returns it. If no
   * transaction with the given transaction id is found, null is returned.
   *
   * @param worker
   *          the principal under which this action is being executed.
   * @param txn
   *          the BDB Transaction instance that should be used to perform the
   *          retrieval.
   * @param tid
   *          the transaction id.
   * @return the PrepareRequest corresponding to tid
   * @throws DatabaseException
   *           if a database error occurs
   */
  private PendingTransaction getPrepared(Principal worker, Transaction txn,
      long tid) throws DatabaseException {
    // Get it from "cache"
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    synchronized (submap) {
      PendingTransaction cached = submap.get(worker);
      return cached;
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
   * @return the PrepareRequest corresponding to tid
   * @throws DatabaseException
   *           if a database error occurs
   */
  private PendingTransaction remove(Principal worker, Transaction txn, long tid)
      throws DatabaseException {
    // Also remove value from the table if it's in the "cache"
    OidKeyHashMap<PendingTransaction> submap = pendingByTid.get(tid);
    PendingTransaction cached = null;
    synchronized (submap) {
      cached = submap.remove(worker);
      if (submap.isEmpty()) pendingByTid.remove(tid, submap);
    }

    if (cached != null) {
      cached.unpin(this);
    }
    return cached;
  }

  private void cacheVersionNumber(long onum, int versionNumber) {
    MutableInteger curEntry =
        cachedVersions.putIfAbsent(onum, new MutableInteger(versionNumber));

    if (curEntry != null) {
      synchronized (curEntry) {
        curEntry.value = versionNumber;
      }
    }
  }

  private static int MAX_TX_WAIT_AVG = 1000;
  private static final Random RAND = new Random();

  private static int randInt(int max) {
    synchronized (RAND) {
      return RAND.nextInt(max);
    }
  }

  /**
   * Executes the given code from within a BDB transaction, automatically
   * retrying as necessary with a random exponential back-off. If the given code
   * throws an exception, the transaction will be aborted.
   */
  private <T, E extends Exception> T runInBdbTransaction(Code<T, E> code)
      throws E {
    // This code is adapted from the JavaDoc for LockConflictException.
    boolean success = false;
    int backoff = 1;
    while (true) {
      int waitTime = randInt(backoff);
      if (waitTime > 0) {
        while (true) {
          try {
            Thread.sleep(waitTime);
            break;
          } catch (InterruptedException e) {
            Logging.logIgnoredInterruptedException(e);
          }
        }
      }

      if (backoff < MAX_TX_WAIT_AVG) {
        backoff *= 2;
      } else {
        backoff = 2 * MAX_TX_WAIT_AVG;
      }

      Transaction txn = null;
      try {
        txn = env.beginTransaction(null, null);
        T result = code.run(txn);
        txn.commit();
        success = true;
        return result;
      } catch (LockConflictException e) {
        continue;
      } catch (DatabaseException e) {
        STORE_DB_LOGGER.log(Level.SEVERE, "Bdb error: ", e);
        throw new InternalError(e);
      } finally {
        if (!success && txn != null) txn.abort();
      }
    }
  }

  private static interface Code<T, E extends Exception> {
    T run(Transaction txn) throws E;
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
  private static class Serializer<FS extends FastSerializable> {
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

    public final byte[] toBytes(FS obj) {
      try {
        bos.reset();
        bos.write(HEADER);
        write(obj, oos);
        oos.flush();
        return bos.toByteArray();
      } catch (IOException e) {
        throw new InternalError(e);
      }
    }

    public void write(FS obj, ObjectOutputStream out) throws IOException {
      obj.write(out);
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

    BdbDB db = new BdbDB(args[0], null);
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
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      System.out.println(onum + "," + className + "," + version + ","
          + updateLabelOnum + "," + accessPolicyOnum + extraInfo);
    }
  }
}
