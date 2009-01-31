package fabric.client;

import java.util.Collection;
import java.util.logging.Logger;

import jif.lang.ConfPolicy;
import jif.lang.IntegPolicy;
import jif.lang.Label;
import jif.lang.LabelUtil;
import jif.lang.PrincipalUtil.TopPrincipal;
import fabric.common.InternalError;
import fabric.common.ONumConstants;
import fabric.common.util.LongKeyMap;
import fabric.lang.Object;
import fabric.lang.Principal;
import fabric.lang.Object.$Impl;
import fabric.util.HashMap;
import fabric.util.Map;

public class LocalCore implements Core {

  private long freshOID = ONumConstants.FIRST_UNRESERVED;

  private Map rootMap;
  private jif.lang.Principal topPrincipal;
  private ConfPolicy topConfidPolicy;
  private ConfPolicy bottomConfidPolicy;
  private IntegPolicy topIntegPolicy;
  private IntegPolicy bottomIntegPolicy;
  private Label emptyLabel;
  private Label publicReadonlyLabel;

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
    if (onum == ONumConstants.EMPTY_LABEL)
      return ($Impl) emptyLabel.fetch();
    
    if (onum == ONumConstants.PUBLIC_READONLY_LABEL)
      return ($Impl) publicReadonlyLabel.fetch();
    
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
  
  public jif.lang.Principal getTopPrincipal() {
    return topPrincipal;
  }
  
  public ConfPolicy getTopConfidPolicy() {
    return topConfidPolicy;
  }
  
  public ConfPolicy getBottomConfidPolicy() {
    return bottomConfidPolicy;
  }
  
  public IntegPolicy getTopIntegPolicy() {
    return topIntegPolicy;
  }
  
  public IntegPolicy getBottomIntegPolicy() {
    return bottomIntegPolicy;
  }
  
  public Label getEmptyLabel() {
    return emptyLabel;
  }
  
  public Label getPublicReadonlyLabel() {
    return publicReadonlyLabel;
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
    // Bootstrap labels with some proxies. Any remaining references to these
    // proxies will be resolved by the hack in readObject().
    this.emptyLabel =
      new Label.$Proxy(LocalCore.this, ONumConstants.EMPTY_LABEL);
    
    this.publicReadonlyLabel =
      new Label.$Proxy(LocalCore.this, ONumConstants.PUBLIC_READONLY_LABEL);
    
    Client.runInTransaction(new Client.Code<Void>() {
      public Void run() {
        // Create the object representing the top principal.
        topPrincipal =
            (jif.lang.Principal) new TopPrincipal.$Impl(
                LocalCore.this, publicReadonlyLabel).$getProxy();

        // Create the object representing the bottom confidentiality policy.
        bottomConfidPolicy =
            LabelUtil.$Impl
                .readerPolicy(LocalCore.this, null, (Principal) null);

        // Create the object representing the bottom integrity policy.
        bottomIntegPolicy =
            LabelUtil.$Impl.writerPolicy(LocalCore.this, topPrincipal,
                topPrincipal);
        
        // Create the object representing the public readonly label.
        publicReadonlyLabel =
            LabelUtil.$Impl.toLabel(LocalCore.this, bottomConfidPolicy,
                bottomIntegPolicy);
        
        // Create the object representing the top confidentiality policy.
        topConfidPolicy =
            LabelUtil.$Impl.readerPolicy(LocalCore.this, topPrincipal,
                topPrincipal);

        // Create the object representing the top integrity policy.
        topIntegPolicy =
            LabelUtil.$Impl
                .writerPolicy(LocalCore.this, null, (Principal) null);

        // Create the object representing the empty label.
        emptyLabel =
            LabelUtil.$Impl.toLabel(LocalCore.this, bottomConfidPolicy,
                topIntegPolicy);

        // Create the label {client->_; client<-_} for the root map.
        Principal clientPrincipal = Client.getClient().getPrincipal();
        ConfPolicy conf =
            LabelUtil.$Impl.readerPolicy(LocalCore.this, clientPrincipal, null);
        IntegPolicy integ =
            LabelUtil.$Impl.writerPolicy(LocalCore.this, clientPrincipal, null);
        Label label = LabelUtil.$Impl.toLabel(LocalCore.this, conf, integ);

        rootMap = (Map) new HashMap.$Impl(LocalCore.this, label).$getProxy();

        return null;
      }
    });
  }
}
