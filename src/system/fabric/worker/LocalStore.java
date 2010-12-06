package fabric.worker;

import static fabric.common.Logging.WORKER_LOCAL_STORE_LOGGER;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import fabric.common.ONumConstants;
import fabric.common.TransactionID;
import fabric.common.exceptions.InternalError;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Pair;
import fabric.lang.Object;
import fabric.lang.Object._Impl;
import fabric.lang.security.*;
import fabric.lang.security.PrincipalUtil.TopPrincipal;
import fabric.util.HashMap;
import fabric.util.Map;

public final class LocalStore implements Store {

  private long freshOID = ONumConstants.FIRST_UNRESERVED;

  private Map rootMap;
  private Principal topPrincipal;
  private ConfPolicy topConfidPolicy;
  private ConfPolicy bottomConfidPolicy;
  private IntegPolicy topIntegPolicy;
  private IntegPolicy bottomIntegPolicy;
  private Label emptyLabel;
  private Label publicReadonlyLabel;

  private Set<Pair<Principal, Principal>> localDelegates;

  public synchronized boolean prepareTransaction(
      long tid, long commitTime, Collection<Object._Impl> toCreate,
      LongKeyMap<Integer> reads, Collection<Object._Impl> writes) {
    // Note: since we assume local single threading we can ignore reads
    // (conflicts are impossible)
    WORKER_LOCAL_STORE_LOGGER.fine("Local transaction preparing");
    return false;
  }

  public synchronized void abortTransaction(TransactionID tid) {
    WORKER_LOCAL_STORE_LOGGER.fine("Local transaction aborting");
  }

  public synchronized void commitTransaction(long transactionID) {
    WORKER_LOCAL_STORE_LOGGER.fine("Local transaction committing");
  }

  public synchronized long createOnum() {
    return freshOID++;
  }

  public synchronized Object._Impl readObject(long onum) {
    return readObjectNoDissem(onum);
  }

  public synchronized Object._Impl readObjectNoDissem(long onum) {
    if (!ONumConstants.isGlobalConstant(onum))
      throw new InternalError("Not supported.");

    if (Integer.MIN_VALUE <= onum && onum <= Integer.MAX_VALUE) {
      switch ((int) onum) {
      case ONumConstants.TOP_PRINCIPAL:
        return (_Impl) topPrincipal.fetch();

      case ONumConstants.TOP_CONFIDENTIALITY:
        return (_Impl) topConfidPolicy.fetch();

      case ONumConstants.BOTTOM_CONFIDENTIALITY:
        return (_Impl) bottomConfidPolicy.fetch();

      case ONumConstants.TOP_INTEGRITY:
        return (_Impl) topIntegPolicy.fetch();

      case ONumConstants.BOTTOM_INTEGRITY:
        return (_Impl) bottomIntegPolicy.fetch();

      case ONumConstants.EMPTY_LABEL:
        return (_Impl) emptyLabel.fetch();

      case ONumConstants.PUBLIC_READONLY_LABEL:
        return (_Impl) publicReadonlyLabel.fetch();
      }
    }

    throw new InternalError("Unknown global constant: onum " + onum);
  }

  public Object._Impl readObjectFromCache(long onum) {
    return readObject(onum);
  }
  
  public boolean checkForStaleObjects(LongKeyMap<Integer> reads) {
    return false;
  }

  /**
   * The singleton LocalStore object is managed by the Worker class.
   * 
   * @see fabric.worker.Worker.getLocalStore
   */
  protected LocalStore() {
  }

  @Override
  public String toString() {
    return "LocalStore";
  }

  public Map getRoot() {
    return rootMap;
  }

  public void addLocalDelegation(Principal p, Principal q) {
    localDelegates.add(new Pair<Principal, Principal>(p, q));
  }

  public void removeLocalDelegation(Principal p, Principal q) {
    localDelegates.remove(new Pair<Principal, Principal>(p, q));
  }

  public boolean localDelegatesTo(Principal p, Principal q) {
    return localDelegates.contains(new Pair<Principal, Principal>(p, q));
  }

  public Principal getTopPrincipal() {
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

  public NodePrincipal getPrincipal() {
    return Worker.getWorker().getPrincipal();
  }

  public boolean isLocalStore() {
    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public boolean notifyEvict(long onum) {
    // nothing to do
    return false;
  }

  public boolean evict(long onum) {
    // nothing to do
    return false;
  }

  public void cache(_Impl impl) {
    // nothing to do
  }

  public void initialize() {
    // Bootstrap labels with some proxies. Any remaining references to these
    // proxies will be resolved by the hack in readObject().
    this.emptyLabel =
        new Label._Proxy(LocalStore.this, ONumConstants.EMPTY_LABEL);

    this.publicReadonlyLabel =
        new Label._Proxy(LocalStore.this, ONumConstants.PUBLIC_READONLY_LABEL);

    Worker.runInSubTransaction(new Worker.Code<Void>() {
      @SuppressWarnings("deprecation")
      public Void run() {
        // Create global constant objects. We force renumbering on these because
        // references to these objects may leak to remote stores. (This leakage
        // is permitted since these objects are constant and are at well-known
        // onums.)

        // Create the object representing the top principal.
        topPrincipal =
            (Principal) new TopPrincipal._Impl(LocalStore.this,
                publicReadonlyLabel).$getProxy();
        topPrincipal.$forceRenumber(ONumConstants.TOP_PRINCIPAL);

        // Create the object representing the bottom confidentiality policy.
        bottomConfidPolicy =
            LabelUtil._Impl
                .readerPolicy(LocalStore.this, null, (Principal) null);
        bottomConfidPolicy.$forceRenumber(ONumConstants.BOTTOM_CONFIDENTIALITY);

        // Create the object representing the bottom integrity policy.
        bottomIntegPolicy =
            LabelUtil._Impl.writerPolicy(LocalStore.this, topPrincipal,
                topPrincipal);
        bottomIntegPolicy.$forceRenumber(ONumConstants.BOTTOM_INTEGRITY);

        // Create the object representing the public readonly label.
        publicReadonlyLabel =
            LabelUtil._Impl.toLabel(LocalStore.this, bottomConfidPolicy,
                bottomIntegPolicy);
        publicReadonlyLabel.$forceRenumber(ONumConstants.PUBLIC_READONLY_LABEL);

        // Create the object representing the top confidentiality policy.
        topConfidPolicy =
            LabelUtil._Impl.readerPolicy(LocalStore.this, topPrincipal,
                topPrincipal);
        topConfidPolicy.$forceRenumber(ONumConstants.TOP_CONFIDENTIALITY);

        // Create the object representing the top integrity policy.
        topIntegPolicy =
            LabelUtil._Impl
                .writerPolicy(LocalStore.this, null, (Principal) null);
        topIntegPolicy.$forceRenumber(ONumConstants.TOP_INTEGRITY);

        // Create the object representing the empty label.
        emptyLabel =
            LabelUtil._Impl.toLabel(LocalStore.this, bottomConfidPolicy,
                topIntegPolicy);
        emptyLabel.$forceRenumber(ONumConstants.EMPTY_LABEL);

        // Create the label {worker->_; worker<-_} for the root map.
        // No need to renumber this. References to the local store's root map
        // should not be leaking to remote stores.
        Principal workerPrincipal = Worker.getWorker().getPrincipal();
        ConfPolicy conf =
            LabelUtil._Impl.readerPolicy(LocalStore.this, workerPrincipal,
                (Principal) null);
        IntegPolicy integ =
            LabelUtil._Impl.writerPolicy(LocalStore.this, workerPrincipal,
                (Principal) null);
        Label label = LabelUtil._Impl.toLabel(LocalStore.this, conf, integ);

        rootMap = (Map) new HashMap._Impl(LocalStore.this, label).$getProxy();
        localDelegates = new HashSet<Pair<Principal, Principal>>();

        return null;
      }
    });
  }
}
