package fabric.core.store.bdb;

import static com.sleepycat.je.OperationStatus.SUCCESS;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sleepycat.je.*;

import fabric.common.InternalError;
import fabric.common.SerializedObject;
import fabric.common.util.LongIterator;
import fabric.core.ObjectStore;
import fabric.core.PrepareRequest;
import fabric.lang.Principal;

/**
 * An ObjectStore backed by a Berkeley Database.
 * 
 * @author xinz
 */
public class BdbStore extends ObjectStore {
  
  protected Environment env;
  protected Database store;
  protected Database prepared;
  protected Database prepared2;
  protected Database meta;
  
  private final DatabaseEntry initializationStatus;
  private final DatabaseEntry tidCounter;
  private final DatabaseEntry onumCounter;
  
  private Logger log = Logger.getLogger("fabric.core.store.bdb");
  
  /**
   * Creates a new BdbStore for the core specified. A new database will be
   * created if it does not exist.
   * 
   * @param name name of core to create store for.
   */
  public BdbStore(String name) {
    super(name);
    String path = "var/bdb/" + name;
    new File(path).mkdirs();  // create path if it does not exist
    
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
      prepared2 = env.openDatabase(null, "prepared2", dbconf);
      meta = env.openDatabase(null, "meta", dbconf);
      
      log.info("Bdb databases opened");
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in <init>: ", e);
      throw new InternalError(e);
    }
    
    try {
      initializationStatus =
          new DatabaseEntry("initialization_status".getBytes("UTF-8"));
      tidCounter = new DatabaseEntry("tid_counter".getBytes("UTF-8"));
      onumCounter = new DatabaseEntry("onum_counter".getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      throw new InternalError(e);
    }
  }

  @Override
  public void commit(Principal client, int tid) {
    log.finer("Bdb commit begin tid " + tid);
    
    try {
      Transaction txn = env.beginTransaction(null, null);
      PrepareRequest req = removePrepare(txn, tid);
      
      if (req != null) {
        for (SerializedObject o : req.written()) {
          long onum = o.getOnum();
          log.finest("Bdb committing onum " + onum);
          DatabaseEntry key = new DatabaseEntry(toBytes(onum));
          DatabaseEntry data = new DatabaseEntry(toBytes(o));
          store.put(txn, key, data);
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
  public boolean exists(long onum) {
    DatabaseEntry key = new DatabaseEntry(toBytes(onum));
    DatabaseEntry data = new DatabaseEntry();
    
    try {
      if (prepared2.get(null, key, data, LockMode.DEFAULT) == SUCCESS ||
          store.get(null, key, data, LockMode.DEFAULT) == SUCCESS) {
        return true;
      }
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in exists: ", e);
      throw new InternalError(e);
    }
    
    return false;
  }

  @Override
  public boolean isPrepared(long onum) {
    return prepared(onum) > 0;
  }

  @Override
  public boolean isRead(long onum) {
    return prepared(onum) == 1;
  }

  @Override
  public boolean isWritten(long onum) {
    return prepared(onum) == 2;
  }
  
  private int prepared(long onum) {
    DatabaseEntry key = new DatabaseEntry(toBytes(onum));
    DatabaseEntry data = new DatabaseEntry();
    
    try {
      if (prepared2.get(null, key, data, LockMode.DEFAULT) == SUCCESS) {
        return toInt(data.getData()) < 0 ? 2 : 1;
      }
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in isPrepared: ", e);
      throw new InternalError(e);
    }
    
    return 0;
  }
  
  @Override
  public long[] newOnums(int num) {
    log.fine("Bdb new onums begin");
    
    try {
      Transaction txn = env.beginTransaction(null, null);
      DatabaseEntry data = new DatabaseEntry();
      long c = 2;
      
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

  @Override
  public int prepare(Principal client, PrepareRequest req) {
    int tid = newTid();
    log.finer("Bdb prepare begin tid " + tid);
    
    try {
      Transaction txn = env.beginTransaction(null, null);
      DatabaseEntry key = new DatabaseEntry(toBytes(tid));
      DatabaseEntry data = new DatabaseEntry(toBytes(req));
      prepared.put(txn, key, data);
      
      for (SerializedObject o : req.written()) {
        addPrepare(txn, o.getOnum(), true);
      }
      
      for (LongIterator i = req.reads.keySet().iterator(); i.hasNext();) {
        addPrepare(txn, i.next(), false);
      }
      
      txn.commit();
      log.finer("Bdb prepare success tid " + tid);
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in prepare: ", e);
      throw new InternalError(e);
    }
    
    return tid;
  }

  private void addPrepare(Transaction txn, long onum, boolean write)
      throws DatabaseException {
    DatabaseEntry key = new DatabaseEntry(toBytes(onum));
    DatabaseEntry data = new DatabaseEntry();
    
    if (prepared2.get(txn, key, data, LockMode.DEFAULT) == SUCCESS) {
      int count = toInt(data.getData()) + 1;
      data.setData(toBytes(count));
      prepared2.put(txn, key, data);
    } else {
      data.setData(toBytes(write ? -1 : 1));
      prepared2.put(txn, key, data);
    }
  }

  @Override
  public SerializedObject read(Principal client, long onum)
      throws NoSuchElementException {
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
    
    throw new NoSuchElementException();
  }

  @Override
  public void rollback(Principal client, int tid) {
    log.finer("Bdb rollback begin tid " + tid);
    
    try {
      Transaction txn = env.beginTransaction(null, null);
      removePrepare(txn, tid);
      txn.commit();
      log.finer("Bdb rollback success tid " + tid);
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in rollback: ", e);
      throw new InternalError(e);
    }
  }
  
  /**
   * Clean up and close database.
   */
  @Override
  public void close() {
    try {
      if (store != null) {
        store.close();
      }

      if (prepared != null) {
        prepared.close();
      }

      if (prepared2 != null) {
        prepared2.close();
      }
      
      if (env != null) {
        env.close();
      }
    } catch (DatabaseException e) {}
  }
  
  /**
   * Removes a PrepareRequest from the prepare log and returns it. If no
   * request with the given transaction id is found, null is returned.
   * 
   * @param txn the BDB Transaction instance that should be used to perform the
   * retrieval.
   * @param tid the transaction id.
   * @return the PrepareRequest corresponding to tid
   * @throws DatabaseException if a database error occurs
   */
  private PrepareRequest removePrepare(Transaction txn, int tid)
      throws DatabaseException {
    DatabaseEntry key = new DatabaseEntry(toBytes(tid));
    DatabaseEntry data = new DatabaseEntry();
    
    if (prepared.get(txn, key, data, LockMode.DEFAULT) == SUCCESS) {
      PrepareRequest req = toPrepareRequest(data.getData());
      prepared.delete(txn, key);
      
      for (long onum : req) {
        key.setData(toBytes(onum));
        
        if (prepared2.get(txn, key, data, LockMode.DEFAULT) == SUCCESS) {
          int count = toInt(data.getData()) - 1;
          
          if (count <= 0) {
            prepared2.delete(txn, key);
          } else {
            data.setData(toBytes(count));
            prepared2.put(txn, key, data);
          }
        }
      }
      
      return req;
    }
    
    return null;
  }
  
  /**
   * Generated a new transaction id.
   */
  protected int newTid() {
    try {
      Transaction txn = env.beginTransaction(null, null);
      DatabaseEntry data = new DatabaseEntry();
      int c = 0;
      
      if (meta.get(txn, tidCounter, data, LockMode.DEFAULT) == SUCCESS) {
        c = toInt(data.getData());
      }

      data.setData(toBytes(c + 1));
      meta.put(txn, tidCounter, data);
      txn.commit();
      
      return c;
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in newTid: ", e);
      throw new InternalError(e);
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
      DatabaseEntry data = new DatabaseEntry();
      data.setData(toBytes(true));
      meta.put(txn, initializationStatus, data);
      txn.commit();
    } catch (DatabaseException e) {
      log.log(Level.SEVERE, "Bdb error in isInitialized: ", e);
      throw new InternalError(e);
    }
  }
  
  private byte[] toBytes(boolean b) {
    byte[] result = { (byte) (b ? 1 : 0) };
    return result;
  }
  
  private boolean toBoolean(byte[] data) {
    return data[0] == 1;
  }
  
  private byte[] toBytes(int i) {
    byte[] data = new byte[4];
    
    for (int j = 0; j < 4; j++) {
      data[3 - j] = (byte) (i & 0xff);
      i = i >>> 8;
    }
    
    return data;
  }

  private int toInt(byte[] data) {
    int i = 0;
    
    for (int j = 0; j < 4; j++) {
      i = i << 8;
      i = i | data[j];
    }
    
    return i;
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

  private byte[] toBytes(PrepareRequest o) {
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      o.write(oos);
      oos.flush();
      return bos.toByteArray();
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }
  
  private PrepareRequest toPrepareRequest(byte[] data) {
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      ObjectInputStream ois = new ObjectInputStream(bis);
      return new PrepareRequest(ois);
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }
  
  private byte[] toBytes(SerializedObject o) {
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      o.write(oos);
      oos.flush();
      return bos.toByteArray();
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }
  
  private SerializedObject toSerializedObject(byte[] data) {
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      ObjectInputStream ois = new ObjectInputStream(bis);
      SerializedObject o = new SerializedObject(ois);
      return o;
    } catch (IOException e) {
      throw new InternalError(e);
    }
  }

}
