package fabric.client;

import java.util.Collection;
import java.util.logging.Logger;

import fabric.client.transaction.TransactionManager;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object;

public class LocalCore implements Core {

  private int freshTID = 0;
  private long freshOID = 0;

  /**
   * Maps transaction IDs of prepared transactions to
   * <code>PendingTransaction</code> objects.
   */
  private LongKeyMap<PendingTransaction> prepared;

  private LongKeyMap<Object.$Impl> objects;

  // TODO: should be a fabric.util.HashMap
  private Object rootMap;

  private static final Logger log = Logger.getLogger("fabric.client.LocalCore");

  private class PendingTransaction {
    public int id;
    public Collection<Object.$Impl> toCreate;

    public PendingTransaction(int id, Collection<Object.$Impl> toCreate) {
      this.id = id;
      this.toCreate = toCreate;
    }
  }

  public synchronized int prepareTransaction(Collection<Object.$Impl> toCreate,
      LongKeyMap<Integer> reads, Collection<Object.$Impl> writes) {
    // Note: since we assume local single threading we can ignore reads
    // (conflicts are impossible)
    log.fine("Local transaction " + freshTID + " preparing");

    prepared.put(freshTID, new PendingTransaction(freshTID, toCreate));
    return freshTID++;
  }

  public synchronized void abortTransaction(int transactionID) {
    log.fine("Local transaction " + transactionID + " aborting");
    prepared.remove(transactionID);
  }

  public synchronized void commitTransaction(int transactionID) {
    log.fine("Local transaction " + transactionID + " committing");
    
    PendingTransaction xact = prepared.remove(transactionID);

    if (xact.toCreate != null) for (Object.$Impl obj : xact.toCreate)
      this.objects.put(obj.$getOnum(), obj);
  }

  public synchronized long createOnum() {
    return freshOID++;
  }

  public synchronized Object.$Impl readObject(long onum) {
    return objects.get(onum);
  }

  public Object.$Impl readObjectFromCache(long onum) {
    return readObject(onum);
  }

  /**
   * The singleton LocalCore object is managed by the Client class.
   * 
   * @see fabric.client.Client.getLocalCore
   */
  protected LocalCore() {
    this.prepared = new LongKeyHashMap<PendingTransaction>();
    this.objects = new LongKeyHashMap<Object.$Impl>();
    TransactionManager.getInstance().startTransaction();
    this.rootMap = new Object.$Impl(this).$getProxy();
    TransactionManager.getInstance().commitTransaction();
  }

  @Override
  public String toString() {
    return "LocalCore";
  }

  public Object getRoot() {
    return rootMap;
  }

  public String name() {
    return "local";
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public void notifyEvict(long onum) {
    // nothing to do
  }
}
