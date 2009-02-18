package fabric.core.store.bdb;

import static com.sleepycat.je.OperationStatus.SUCCESS;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sleepycat.je.*;

import fabric.common.FastSerializable;
import fabric.common.InternalError;
import fabric.common.ONumConstants;
import fabric.common.SerializedObject;
import fabric.core.store.ObjectStore;
import fabric.lang.Principal;

/**
 * An ObjectStore backed by a Berkeley Database.
 */
public class BdbStore extends ObjectStore {

  private Environment env;
  private Database meta;
  private Database store;
  private Database prepared;

  private final DatabaseEntry initializationStatus;
  private final DatabaseEntry onumCounter;
  private final DatabaseEntry globIDCounter;

  private Logger log = Logger.getLogger("fabric.core.store.bdb");

  /**
   * Creates a new BdbStore for the core specified. A new database will be
   * created if it does not exist.
   * 
   * @param name
   *          name of core to create store for.
   */
  public BdbStore(String name) {
    super(name);

    String path = "var/bdb/" + name;
    new File(path).mkdirs(); // create path if it does not exist

    try {
      EnvironmentConfig conf = new EnvironmentConfig();
      conf.setAllowCreate(true);
      conf.setTransactional(true);
      env = new Environment(new File(path), conf);

      log.info("Bdb env opened");

      DatabaseConfig dbconf = new DatabaseConfig();
      dbconf.setAllowCreate(true);
      dbconf.setTransactional(true);
      store = env.openDatabase(null, "store", dbconf);
      prepared = env.openDatabase(null, "prepared", dbconf);
      meta = env.openDatabase(null, "meta", dbconf);

      initRwCount();

      log.info("Bdb databases opened");
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in <init>: ", e);
      throw new InternalError(e);
    }

    try {
      initializationStatus =
          new DatabaseEntry("initialization_status".getBytes("UTF-8"));
      onumCounter = new DatabaseEntry("onum_counter".getBytes("UTF-8"));
      globIDCounter = new DatabaseEntry("globID_counter".getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new InternalError(e);
    }
  }

  @Override
  public void finishPrepare(long tid) {
    // Move the transaction data out of memory and into BDB.
    PendingTransaction pending = pendingByTid.remove(tid);

    try {
      Transaction txn = env.beginTransaction(null, null);
      DatabaseEntry key = new DatabaseEntry(toBytes(tid));
      DatabaseEntry data = new DatabaseEntry(toBytes(pending));
      prepared.put(txn, key, data);
      txn.commit();
      log.finer("Bdb prepare success tid " + tid);
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in finishPrepare: ", e);
      throw new InternalError(e);
    }
  }

  @Override
  public void commit(Principal client, long tid) {
    log.finer("Bdb commit begin tid " + tid);

    try {
      Transaction txn = env.beginTransaction(null, null);
      PendingTransaction pending = remove(client, txn, tid);

      if (pending != null) {
        for (SerializedObject o : pending.modData) {
          long onum = o.getOnum();
          log.finest("Bdb committing onum " + onum);
          DatabaseEntry onumData = new DatabaseEntry(toBytes(onum));
          DatabaseEntry objData = new DatabaseEntry(toBytes(o));
          store.put(txn, onumData, objData);
          
          // Remove any cached globs containing the old version of this object.
          removeGlobByOnum(toLong(onumData.getData()));
        }

        txn.commit();
        log.finer("Bdb commit success tid " + tid);
      } else {
        txn.abort();
        log.warning("Bdb commit not found tid " + tid);
        throw new InternalError("Unknown transaction id " + tid);
      }
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in commit: ", e);
      throw new InternalError(e);
    }
  }

  @Override
  public void rollback(Principal client, long tid) {
    log.finer("Bdb rollback begin tid " + tid);

    try {
      Transaction txn = env.beginTransaction(null, null);
      remove(client, txn, tid);
      txn.commit();
      log.finer("Bdb rollback success tid " + tid);
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in rollback: ", e);
      throw new InternalError(e);
    }
  }

  @Override
  public SerializedObject read(long onum) {
    log.finest("Bdb read onum " + onum);
    DatabaseEntry key = new DatabaseEntry(toBytes(onum));
    DatabaseEntry data = new DatabaseEntry();

    try {
      if (store.get(null, key, data, LockMode.DEFAULT) == SUCCESS) {
        return toSerializedObject(data.getData());
      }
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in read: ", e);
      throw new InternalError(e);
    }

    return null;
  }

  @Override
  protected long nextGlobID() {
    try {
      Transaction txn = env.beginTransaction(null, null);

      // Get a new ID for the glob.
      DatabaseEntry globIDData = new DatabaseEntry();
      if (meta.get(txn, globIDCounter, globIDData, LockMode.DEFAULT) != SUCCESS)
        globIDData.setData(toBytes(0L));
      long nextGlobID = toLong(globIDData.getData()) + 1;
      DatabaseEntry nextGlobIDData = new DatabaseEntry(toBytes(nextGlobID));
      meta.put(txn, globIDCounter, nextGlobIDData);

      txn.commit();
      return toLong(globIDData.getData());
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in nextGlobID: ", e);
      throw new InternalError(e);
    }
  }

  @Override
  public boolean exists(long onum) {
    DatabaseEntry key = new DatabaseEntry(toBytes(onum));
    DatabaseEntry data = new DatabaseEntry();

    try {
      if (rwCount.get(onum) != null
          || store.get(null, key, data, LockMode.DEFAULT) == SUCCESS) {
        return true;
      }
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in exists: ", e);
      throw new InternalError(e);
    }

    return false;
  }

  @Override
  public long[] newOnums(int num) {
    log.fine("Bdb new onums begin");

    try {
      Transaction txn = env.beginTransaction(null, null);
      DatabaseEntry data = new DatabaseEntry();
      long c = ONumConstants.FIRST_UNRESERVED;

      if (meta.get(txn, onumCounter, data, LockMode.DEFAULT) == SUCCESS) {
        c = toLong(data.getData());
      }

      data.setData(toBytes(c + num));
      meta.put(txn, onumCounter, data);
      txn.commit();

      long[] onums = new long[num];

      for (int i = 0; i < num; i++) {
        onums[i] = c + i;
      }

      log.fine("Bdb new onums " + c + "--" + (c + num - 1));
      return onums;
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in newOnums: ", e);
      throw new InternalError(e);
    }
  }

  /**
   * Clean up and close database.
   */
  @Override
  public void close() {
    try {
      if (store != null) store.close();
      if (prepared != null) prepared.close();
      if (env != null) env.close();
    } catch (DatabaseException e) {
    }
  }

  @Override
  public boolean isInitialized() {
    log.fine("Bdb is initialized begin");

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
      log.log(Level.SEVERE, "Bdb error in isInitialized: ", e);
      throw new InternalError(e);
    }
  }

  @Override
  public void setInitialized() {
    log.fine("Bdb set initialized begin");

    try {
      Transaction txn = env.beginTransaction(null, null);
      DatabaseEntry data = new DatabaseEntry(toBytes(true));
      meta.put(txn, initializationStatus, data);
      txn.commit();
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in isInitialized: ", e);
      throw new InternalError(e);
    }
  }

  private void initRwCount() {
    // TODO Recover rwCount info from prepared
  }

  /**
   * Removes a PendingTransaction from the prepare log and returns it. If no
   * transaction with the given transaction id is found, null is returned.
   * 
   * @param client
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
  private PendingTransaction remove(Principal client, Transaction txn, long tid)
      throws DatabaseException {
    DatabaseEntry key = new DatabaseEntry(toBytes(tid));
    DatabaseEntry data = new DatabaseEntry();

    if (prepared.get(txn, key, data, LockMode.DEFAULT) == SUCCESS) {
      PendingTransaction pending = toPendingTransaction(data.getData());
      prepared.delete(txn, key);
      unpin(pending);

      return pending;
    }

    return null;
  }

  private byte[] toBytes(boolean b) {
    byte[] result = { (byte) (b ? 1 : 0) };
    return result;
  }

  private boolean toBoolean(byte[] data) {
    return data[0] == 1;
  }

  private byte[] toBytes(long i) {
    byte[] data = new byte[8];

    for (int j = 0; j < 8; j++) {
      data[7 - j] = (byte) (i & 0xff);
      i = i >>> 8;
    }

    return data;
  }

  private long toLong(byte[] data) {
    long i = 0;

    for (int j = 0; j < 8; j++) {
      i = i << 8;
      i = i | (data[j] & 0xff);
    }

    return i;
  }

  private byte[] toBytes(FastSerializable obj) {
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      obj.write(oos);
      oos.flush();
      return bos.toByteArray();
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }
  
  private PendingTransaction toPendingTransaction(byte[] data) {
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      ObjectInputStream ois = new ObjectInputStream(bis);
      return new PendingTransaction(ois);
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }

  private SerializedObject toSerializedObject(byte[] data) {
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      ObjectInputStream ois = new ObjectInputStream(bis);
      return new SerializedObject(ois);
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }

}
