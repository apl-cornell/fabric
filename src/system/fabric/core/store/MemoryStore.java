package fabric.core.store;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import fabric.common.*;
import fabric.common.InternalError;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.dissemination.Glob;
import fabric.lang.Principal;

/**
 * <p>
 * An in-memory implementation of the ObjectStore implementation. This class
 * assumes there will be no failures and hopefully will provide very high
 * performance at the cost of no fault tolerance whatsoever. This class does
 * have a simple facility for loading and saving the store to a file.
 * </p>
 * <p>
 * This class is not thread-safe. Only the <code>TransactionManager</code>
 * should directly interact with this class. The <code>TransactionManager</code>
 * 's thread safety ensures safe usage of this class.
 * </p>
 */
public class MemoryStore extends ObjectStore {

  /**
   * Whether the store has been initialized.
   */
  private boolean isInitialized;

  /**
   * The next free onum.
   */
  private long nextOnum;

  /**
   * Largest transaction id ever used;
   */
  private int maxTid;

  /**
   * Maps 48-bit object numbers to SerializedObjects.
   */
  private LongKeyMap<SerializedObject> objectTable;
  
  /**
   * The next free glob ID.
   */
  private long nextGlobID;
  
  /**
   * Maps object numbers to globIDs. The glob with ID globIDByOnum(onum) holds a
   * copy of object onum.
   */
  private LongKeyMap<Long> globIDByOnum;

  /**
   * Maps globIDs to Globs and the number of times the glob is referenced in
   * globIDByOnum.
   */
  private LongKeyMap<Pair<Glob, Integer>> globTable;

  private Logger log = Logger.getLogger("fabric.core.store.mem");

  /**
   * Opens the core contained in file "var/coreName" if it exists, or an empty
   * core otherwise.
   * 
   * @param name
   *          name of core to create store for.
   */
  public MemoryStore(String name) {
    super(name);
    this.isInitialized = false;

    try {
      ObjectInputStream oin =
          new ObjectInputStream(Resources.readFile("var", name));

      this.isInitialized = oin.readBoolean();
      
      this.nextOnum = oin.readLong();

      int size = oin.readInt();
      this.objectTable = new LongKeyHashMap<SerializedObject>(size);
      for (int i = 0; i < size; i++)
        this.objectTable.put(oin.readLong(), new SerializedObject(oin));
      
      this.nextGlobID = oin.readLong();
      
      size = oin.readInt();
      this.globIDByOnum = new LongKeyHashMap<Long>(size);
      for (int i = 0; i < size; i++)
        this.globIDByOnum.put(oin.readLong(), oin.readLong());
      
      size = oin.readInt();
      this.globTable = new LongKeyHashMap<Pair<Glob, Integer>>(size);
      for (int i = 0; i < size; i++)
        this.globTable.put(oin.readLong(), new Pair<Glob, Integer>(
            new Glob(null, oin), oin.readInt()));
    } catch (BadSignatureException e) {
      throw new InternalError(e);
    } catch (Exception e) {
      // TODO: distinguish invalid files from nonexistent
      this.nextOnum = ONumConstants.FIRST_UNRESERVED;
      this.objectTable = new LongKeyHashMap<SerializedObject>();
      this.globIDByOnum = new LongKeyHashMap<Long>();
      this.globTable = new LongKeyHashMap<Pair<Glob,Integer>>();
    }

    this.maxTid = 0;
    log.info("Mem store loaded");
  }

  @Override
  protected int newTid(Principal client) {
    return ++maxTid;
  }

  @Override
  public void finishPrepare(int tid) {
  }

  @Override
  public void commit(Principal client, int tid) throws AccessException {
    PendingTransaction tx = remove(client, tid);

    // merge in the objects
    for (SerializedObject o : tx.modData) {
      objectTable.put(o.getOnum(), o);
      
      // Remove any cached globs containing the old version of this object.
      long onum = o.getOnum();
      Long globID = globIDByOnum.remove(onum);
      if (globID != null) globTable.remove(globID);
    }
  }

  @Override
  public void rollback(Principal client, int tid) throws AccessException {
    remove(client, tid);
  }

  @Override
  public SerializedObject read(long onum) {
    return objectTable.get(onum);
  }

  @Override
  public Glob getCachedGlob(long onum) {
    Long globID = globIDByOnum.get(onum);
    if (globID == null) return null;
    
    Pair<Glob, Integer> entry = globTable.get(globID);
    if (entry == null) return null;
    return entry.first;
  }
  
  @Override
  public void cacheGlob(LongSet onums, Glob glob) {
    // Get a new ID for the glob and insert into the glob table.
    long globID = nextGlobID++;
    globTable.put(globID, new Pair<Glob, Integer>(glob, onums.size()));
    
    // Establish globID bindings for all onums we're given.
    for (LongIterator it = onums.iterator(); it.hasNext();) {
      long onum = it.next();
      
      Long oldGlobID = globIDByOnum.put(onum, globID);
      if (oldGlobID == null) continue;
      
      Pair<Glob, Integer> entry = globTable.get(oldGlobID);
      if (entry == null) continue;

      if (entry.second == 1) {
        // We've removed the last pin. Evict the old glob from the glob table.
        globTable.remove(oldGlobID);
      } else {
        entry.second--;
      }
    }
  }

  @Override
  public boolean exists(long onum) {
    return isPrepared(onum) || objectTable.containsKey(onum);
  }

  @Override
  public long[] newOnums(int num) {
    final long[] result = new long[num < 0 ? 0 : num];

    for (int i = 0; i < num; i++)
      result[i] = nextOnum++;

    return result;
  }

  @Override
  public void close() throws IOException {
    // XXX TODO Save prepared txs to stable storage, implement finishPrepare().

    ObjectOutputStream oout =
        new ObjectOutputStream(Resources.writeFile("var", name));
    oout.writeBoolean(isInitialized);
    
    oout.writeLong(nextOnum);
    
    oout.writeInt(this.objectTable.size());
    for (LongKeyMap.Entry<SerializedObject> entry : this.objectTable.entrySet()) {
      oout.writeLong(entry.getKey());
      entry.getValue().write(oout);
    }
    
    oout.writeLong(this.nextGlobID);
    
    oout.writeInt(this.globIDByOnum.size());
    for (LongKeyMap.Entry<Long> entry : this.globIDByOnum.entrySet()) {
      oout.writeLong(entry.getKey());
      oout.writeLong(entry.getValue());
    }
    
    oout.writeInt(this.globTable.size());
    for (LongKeyMap.Entry<Pair<Glob, Integer>> entry : this.globTable.entrySet()) {
      Pair<Glob, Integer> val = entry.getValue();
      oout.writeLong(entry.getKey());
      val.first.write(oout);
      oout.writeInt(val.second);
    }
    
    oout.flush();
    oout.close();
  }

  /**
   * Helper method to check permissions and update the pending object table for
   * a commit or roll-back.
   */
  private PendingTransaction remove(Principal client, int tid)
      throws AccessException {
    PendingTransaction tx = pendingByTid.remove(tid);
    if (tx == null) throw new AccessException();

    // XXX Check if the client acts for the owner.

    unpin(tx);
    return tx;
  }

  @Override
  protected boolean isInitialized() {
    return this.isInitialized;
  }

  @Override
  protected void setInitialized() {
    this.isInitialized = true;
  }

}

/*
 * vim: ts=2 sw=2 et cindent cino=\:0
 */
