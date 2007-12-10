package fabric.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import fabric.lang.Object;

public class LocalCore implements Core {

  private int freshTID = 0;
  private long freshOID = 0;

  private PendingTransaction prepared;
  private Map<Long, Object.$Impl> objects;
  // TODO: should be a fabric.util.HashMap
  private Object rootMap;
  private RemoteCore surrogate = null;

  private static final Logger log = Logger.getLogger("fabric.client.LocalCore");

  private class PendingTransaction {
    public int id;
    public Collection<Object.$Impl> toCreate;
    public Collection<Object.$Impl> writes;

    public PendingTransaction(int id, Collection<Object.$Impl> toCreate,
        Collection<Object.$Impl> writes) {
      this.id = id;
      this.toCreate = toCreate;
      this.writes = writes;
    }
  }

  public int prepareTransaction(Collection<Object.$Impl> toCreate,
      Map<Long, Integer> reads, Collection<Object.$Impl> writes) {
    // Note: since we assume local single threading we can ignore reads
    // (conflicts are impossible)
    log.info("Local transaction " + freshTID + " preparing");
    // TODO: more robust checking
    assert prepared == null;

    prepared = new PendingTransaction(freshTID, toCreate, writes);
    return freshTID++;
  }

  public void abortTransaction(int transactionID) {
    log.info("Local transaction " + transactionID + " aborting");
    assert (prepared != null);
    prepared = null;
  }

  public void commitTransaction(int transactionID) {
    log.info("Local transaction " + transactionID + " committing");
    assert prepared.id == transactionID;

    if (prepared.writes != null) for (Object.$Impl obj : prepared.writes)
      this.objects.put(obj.$getOnum(), obj);
    if (prepared.toCreate != null) for (Object.$Impl obj : prepared.toCreate)
      this.objects.put(obj.$getOnum(), obj);
    prepared = null;
  }

  public long createOnum() {
    return freshOID++;
  }

  public Object.$Impl readObject(long onum) {
    return objects.get(onum);
  }

  /**
   * The singleton LocalCore object is managed by the Client class.
   * 
   * @see fabric.client.Client.getLocalCore
   */
  LocalCore() {
    this.prepared = null;
    this.objects = new HashMap<Long, Object.$Impl>();
    this.rootMap = new Object.$Impl(this).$getProxy();
  }

  @Override
  public String toString() {
    return "LocalCore";
  }

  public Object getRoot() throws UnreachableCoreException {
    return rootMap;
  }

  /**
   * This is a hook for hacks. It allows one to turn the local core into any
   * other core for the purposes of serialization.
   */
  @Deprecated
  public void surrogate(RemoteCore surrogate) {
    this.surrogate = surrogate;
  }

  @Deprecated
  public Map<Long, Object.$Impl> objects() {
    return objects;
  }

  public long id() {
    return surrogate.id();
  }
}
