package fabric.client;

import java.util.Collection;
import java.util.logging.Logger;

import jif.lang.ConfPolicy;
import jif.lang.IntegPolicy;
import jif.lang.Label;
import jif.lang.LabelUtil;

import fabric.common.InternalError;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object;
import fabric.lang.Principal;
import fabric.util.HashMap;
import fabric.util.Map;

public class LocalCore implements Core {

  private long freshOID = 0;

  private Map rootMap;
  private ConfPolicy bottomConfidPolicy;
  private IntegPolicy topIntegPolicy;
  private Label emptyLabel;

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
  }

  @Override
  public String toString() {
    return "LocalCore";
  }

  public Map getRoot() {
    return rootMap;
  }
  
  public ConfPolicy getBottomConfidPolicy() {
    return bottomConfidPolicy;
  }
  
  public IntegPolicy getTopIntegPolicy() {
    return topIntegPolicy;
  }
  
  public Label getEmptyLabel() {
    return emptyLabel;
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
  
  public void initialize() {
    // Create the object representing the bottom confidentiality policy.
    this.bottomConfidPolicy =
        Client.runInTransaction(new Client.Code<ConfPolicy>() {
          public ConfPolicy run() {
            return LabelUtil.$Impl.readerPolicy(LocalCore.this, null,
                (Principal) null);
          }
        });

    // Create the object representing the top integrity policy.
    this.topIntegPolicy =
        Client.runInTransaction(new Client.Code<IntegPolicy>() {
          public IntegPolicy run() {
            return LabelUtil.$Impl.writerPolicy(LocalCore.this, null,
                (Principal) null);
          }
        });
    
    // Create the object representing the empty label.
    this.emptyLabel = Client.runInTransaction(new Client.Code<Label>() {
      public Label run() {
        return LabelUtil.$Impl.toLabel(LocalCore.this, bottomConfidPolicy,
            topIntegPolicy);
      }
    });

    // Label the policies created thus far with the empty label.
    bottomConfidPolicy.set$label(emptyLabel);
    topIntegPolicy.set$label(emptyLabel);

    this.rootMap = Client.runInTransaction(new Client.Code<Map>() {
      public Map run() {
        // Create the label {client->_; client<-_} for the root map.
        Principal clientPrincipal = Client.getClient().getPrincipal();
        ConfPolicy conf =
            LabelUtil.$Impl.readerPolicy(LocalCore.this, clientPrincipal, null);
        IntegPolicy integ =
            LabelUtil.$Impl.writerPolicy(LocalCore.this, clientPrincipal, null);
        Label label = LabelUtil.$Impl.toLabel(LocalCore.this, conf, integ);
        
        return (Map) new HashMap.$Impl(LocalCore.this, label).$getProxy();
      }
    });
  }
}
