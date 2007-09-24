package fabric.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import fabric.lang.Object.$Impl;

public class LocalCore implements Core {

  private int freshTID = 0;
  private long freshOID = 0;

  private PendingTransaction prepared;
  private Map<Long, $Impl> objects;

  private static Logger log = Logger.getLogger("fabric.client.LocalCore");

  private class PendingTransaction {
    public int id;
    public Collection<$Impl> toCreate;
    public Collection<$Impl> writes;

    public PendingTransaction(int id, Collection<$Impl> toCreate,
        Collection<$Impl> writes) {
      this.id = id;
      this.toCreate = toCreate;
      this.writes = writes;
    }
  }

  public int beginTransaction() {
    log.info("Local transaction " + freshTID + " beginning");
    return freshTID++;
  }

  public void prepareTransaction(int transactionID,
      Collection<$Impl> toCreate, Map<Long, Integer> reads,
      Collection<$Impl> writes) {
    // Note: since we assume local single threading we can ignore reads
    // (conflicts are impossible)
    log.info("Local transaction " + transactionID + " preparing");
    // TODO: more robust checking
    assert prepared == null;

    prepared = new PendingTransaction(transactionID, toCreate, writes);
  }

  public void abortTransaction(int transactionID) {
    log.info("Local transaction " + transactionID + " aborting");
    assert (prepared != null);
    prepared = null;
  }

  public void commitTransaction(int transactionID) {
    log.info("Local transaction " + transactionID + " committing");
    assert prepared.id == transactionID;

    if (prepared.writes != null) for ($Impl obj : prepared.writes)
      this.objects.put(obj.$getOnum(), obj);
    if (prepared.toCreate != null) for ($Impl obj : prepared.toCreate)
      this.objects.put(obj.$getOnum(), obj);
    prepared = null;
  }

  public long createOnum() {
    return freshOID++;
  }

  public $Impl readObject(long onum) {
    return objects.get(onum);
  }

  /**
   * The singleton LocalCore object is managed by the Client class.
   * 
   * @see fabric.client.Client.getLocalCore
   */
  LocalCore() {
    this.prepared = null;
    this.objects = new HashMap<Long, $Impl>();
  }

  @Override
  public String toString() {
    return "LocalCore";
  }

}
