package fabric.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import fabric.common.AccessError;
import fabric.common.Resources;
import fabric.common.Util;
import fabric.common.util.LongIterator;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;

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
public class MemoryStore implements ObjectStore {
  /**
   * The data stored for a prepared transaction
   */
  private class PendingTransaction {
    public Principal        owner;
    public PrepareRequest   request;
  }

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

  private String name;
  
  private Logger log = Logger.getLogger("fabric.core.store.mem");

  /**
   * Opens the core contained in file "var/coreName" if it exists, or an empty
   * core otherwise. 
   */
  @SuppressWarnings("unchecked")
  public MemoryStore(String name) {
    this.pendingByTid  = new HashMap<Integer, PendingTransaction>();
    this.pendingByOnum = new LongKeyHashMap<Collection<PendingTransaction>> ();
    this.name          = name;
    
    try {
      ObjectInputStream oin = new ObjectInputStream(Resources.readFile("var", name));
      this.objectTable = (LongKeyMap<SerializedObject>) oin.readObject();
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

  public int prepare(Principal client, PrepareRequest req)
               throws AccessError {
    
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

  @SuppressWarnings("unchecked")
  public void commit(Principal client, int tid) throws AccessError {
    PendingTransaction tx = remove(client, tid);

    // merge in the objects
    for (SerializedObject o : Util.chain(tx.request.creates, tx.request.writes))
      objectTable.put(o.getOnum(), o);
  }

  public void rollback(Principal client, int tid) throws AccessError {
    remove(client, tid);
  }

  public SerializedObject read(Principal client, long onum) {
    SerializedObject obj = objectTable.get(onum);

    if (obj != null && !obj.getPolicy().canRead(client)) return null;
    return obj;
  }

  public boolean exists(long onum) {
    return isPrepared(onum) || objectTable.containsKey(onum);
  }

  public boolean isPrepared(long onum) {
    return pendingByOnum.containsKey(onum);
  }

  public long[] newOnums(int num) throws AccessError {
    final long[] result = new long[num < 0 ? 0 : num];

    for (int i = 0; i < num; i++)
      result[i] = ++maxOnum;

    return result;
  }

  /**
   * Helper method to check permissions and update the pending object table for a commit or rollback
   */
  private PendingTransaction remove(Principal client, int tid) throws AccessError {
    PendingTransaction tx = pendingByTid.get(tid);
    if (tx == null)
      throw new AccessError();
    if (!client.equals(tx.owner))
      throw new AccessError();

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

  public String getName() {
    return name;
  }

  public void close() throws IOException {
    ObjectOutputStream oout = new ObjectOutputStream(Resources.writeFile("var", name));
    oout.writeObject(this.objectTable);
    oout.close();
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0
*/
