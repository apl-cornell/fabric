package fabric.core.store;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import fabric.common.Resources;
import fabric.common.SerializedObject;
import fabric.common.Util;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.core.PrepareRequest;
import fabric.lang.Principal;

/**
 * <p>An in-memory implementation of the ObjectStore implementation.  This class
 * assumes there will be no failures and hopefully will provide very high
 * performance at the cost of no fault tolerance whatsoever.  This class does
 * have a simple facility for loading and saving the store to a file.</p>
 *
 * <p> TODO: This class is not thread-safe. Only the
 * <code>TransactionManager</code> should directly interact with this class.
 * The <code>TransactionManager</code>'s thread safety ensures safe usage of
 * this class.</p>
 */
public class MemoryStore extends ObjectStore {
  
  /**
   * The data stored for a prepared transaction
   */
  private class PendingTransaction {
    public Principal        owner;
    public PrepareRequest   request;
  }

  /**
   * Whether the store has been initialized.
   */
  private boolean isInitialized;
  
  /**
   * Largest object number ever handed out
   */
  private long maxOnum;

  /**
   * Largest transaction id ever used;
   */
  private int maxTid;

  /**
   * The table of pending objects. For each value tx in pendingByTid, it should
   * be the case that for each onum l in tx.request, tx is contained in
   * pendingByOnum(l). Similarly, each tx in the values of pendingByOnum should
   * be a value of pendingByTid.
   */
  private Map<Integer, PendingTransaction>           pendingByTid;
  private LongKeyMap<Collection<PendingTransaction>> pendingByOnum;

  /**
   * Maps 48-bit object numbers to SerializedObjects.
   */
  private LongKeyMap<SerializedObject> objectTable;
  
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
    this.pendingByTid  = new HashMap<Integer, PendingTransaction>();
    this.pendingByOnum = new LongKeyHashMap<Collection<PendingTransaction>> ();
    
    try {
      ObjectInputStream oin = new ObjectInputStream(Resources.readFile("var", name));
      
      this.isInitialized = oin.readBoolean();
      
      int size = oin.readInt();
      this.objectTable = new LongKeyHashMap<SerializedObject>(size);
      for (int i = 0; i < size; i++)
        this.objectTable.put(oin.readLong(), new SerializedObject(oin));
    } catch (Exception e) {
      // Do nothing
      // TODO: distinguish invalid files from nonexistent
      this.objectTable = new LongKeyHashMap<SerializedObject>();
    }

    // Note: this would be much faster if objectTable was sorted...but it's just
    //       recovery so IMO probably not worth it
    this.maxOnum = 1;
    LongIterator iter = this.objectTable.keySet().iterator();
    while(iter.hasNext()) {
      long l = iter.next();
      if (this.maxOnum < l)
        maxOnum = l;
    }

    this.maxTid = 0;
    log.info("Mem store loaded");
  }

  @Override
  public int prepare(Principal client, PrepareRequest req) {
    // create and register PendingTransaction
    int tid = ++maxTid;
    PendingTransaction entry = new PendingTransaction();

    entry.owner   = client;
    entry.request = req;

    pendingByTid.put(tid, entry);
    for (Long onum : entry.request)
      if (pendingByOnum.containsKey(onum))
        pendingByOnum.get(onum).add(entry);
      else
      {
        Collection<PendingTransaction> pending = new ArrayList<PendingTransaction>();
        pending.add(entry);
        pendingByOnum.put(onum, pending);
      }

    return tid;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void commit(Principal client, int tid) throws StoreException {
    PendingTransaction tx = remove(client, tid);

    // merge in the objects
    for (SerializedObject o : Util.chain(tx.request.creates, tx.request.writes)) {
      objectTable.put(o.getOnum(), o);
    }
  }

  @Override
  public void rollback(Principal client, int tid) throws StoreException {
    remove(client, tid);
  }

  @Override
  public SerializedObject read(Principal client, long onum) {
    SerializedObject obj = objectTable.get(onum);
    return obj;
  }

  @Override
  public boolean exists(long onum) {
    return isPrepared(onum) || objectTable.containsKey(onum);
  }

  @Override
  public boolean isPrepared(long onum) {
    return pendingByOnum.containsKey(onum);
  }

  @Override
  public boolean isRead(long onum) {
    return isPrepared(onum);  // TODO
  }

  @Override
  public boolean isWritten(long onum) {
    return isPrepared(onum);  // TODO
  }
  
  @Override
  public long[] newOnums(int num) {
    final long[] result = new long[num < 0 ? 0 : num];

    for (int i = 0; i < num; i++)
      result[i] = ++maxOnum;

    return result;
  }

  /**
   * Helper method to check permissions and update the pending object table for a commit or rollback
   */
  private PendingTransaction remove(Principal client, int tid)
      throws StoreException {
    PendingTransaction tx = pendingByTid.get(tid);
    if (tx == null)
      throw new StoreException();
    
    // XXX Check if the client acts for the owner.

    // remove the pending transaction
    pendingByTid.remove(tid);
    for (Long l : tx.request) {
      Collection<PendingTransaction> index = pendingByOnum.get(l);
      index.remove(tx);
      if (index.isEmpty())
        pendingByOnum.remove(l);
    }

    return tx;
  }

  @Override
  public void close() throws IOException {
    ObjectOutputStream oout = new ObjectOutputStream(Resources.writeFile("var", name));
    oout.writeBoolean(isInitialized);
    oout.writeInt(this.objectTable.size());
    for (LongKeyMap.Entry<SerializedObject> entry : this.objectTable.entrySet()) {
      oout.writeLong(entry.getKey());
      entry.getValue().write(oout);
    }
    oout.flush();
    oout.close();
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
** vim: ts=2 sw=2 et cindent cino=\:0
*/
