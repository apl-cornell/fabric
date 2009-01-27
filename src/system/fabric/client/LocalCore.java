package fabric.client;

import java.util.Collection;
import java.util.logging.Logger;

import fabric.common.InternalError;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object;
import fabric.util.HashMap;
import fabric.util.Map;

public class LocalCore implements Core {

  private long freshOID = 0;

  private final Map rootMap;

  private static final Logger log = Logger.getLogger("fabric.client.LocalCore");

  public synchronized int prepareTransaction(Collection<Object.$Impl> toCreate,
      LongKeyMap<Integer> reads, Collection<Object.$Impl> writes) {
    // Note: since we assume local single threading we can ignore reads
    // (conflicts are impossible)
    log.fine("Local transaction preparing");
    return 0;
  }

  public synchronized void abortTransaction(int transactionID) {
    log.fine("Local transaction aborting");
  }

  public synchronized void commitTransaction(int transactionID) {
    log.fine("Local transaction committing");
  }

  public synchronized long createOnum() {
    return freshOID++;
  }

  public synchronized Object.$Impl readObject(long onum) {
    throw new InternalError("Not supported.");
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
    this.rootMap = Client.runInTransaction(new Client.Code<Map>() {
      public Map run() {
        // XXX Use a proper label.
        return (Map) new HashMap.$Impl(LocalCore.this, null).$getProxy();
      }
    });
  }

  @Override
  public String toString() {
    return "LocalCore";
  }

  public Map getRoot() {
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
