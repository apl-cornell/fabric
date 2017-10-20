package fabric.worker;

import static fabric.common.Logging.WORKER_LOCAL_STORE_LOGGER;

import java.io.NotSerializableException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.DelayQueue;
import java.util.logging.Level;

import fabric.common.Logging;
import fabric.common.ONumConstants;
import fabric.common.SerializedObject;
import fabric.common.Threading;
import fabric.common.TransactionID;
import fabric.common.exceptions.InternalError;
import fabric.common.util.ConcurrentLongKeyHashMap;
import fabric.common.util.ConcurrentLongKeyMap;
import fabric.common.util.LongKeyHashMap;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Pair;
import fabric.lang.Object;
import fabric.lang.Object._Impl;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.IntegPolicy;
import fabric.lang.security.Label;
import fabric.lang.security.LabelUtil;
import fabric.lang.security.NodePrincipal;
import fabric.lang.security.Principal;
import fabric.lang.security.PrincipalUtil.TopPrincipal;
import fabric.metrics.contracts.Contract;
import fabric.store.DelayedExtension;
import fabric.util.HashMap;
import fabric.util.Map;
import fabric.worker.Worker.Code;

public final class LocalStore implements Store, Serializable {

  private long freshOID = ONumConstants.FIRST_UNRESERVED;

  private Map rootMap;
  private Principal topPrincipal;
  private ConfPolicy topConfidPolicy;
  private ConfPolicy bottomConfidPolicy;
  private IntegPolicy topIntegPolicy;
  private IntegPolicy bottomIntegPolicy;
  private Label emptyLabel;
  private Label publicReadonlyLabel;

  /**
   * Only used to obtain ObjectCache.Entry objects so we can satisfy the
   * contract for readFromCache(long).
   */
  private final ObjectCache cache;

  private Set<Pair<Principal, Principal>> localDelegates;

  @Override
  public Pair<LongKeyMap<SerializedObject>, Long> prepareTransaction(long tid,
      boolean singleStore, boolean readOnly, Collection<Object._Impl> toCreate,
      LongKeyMap<Pair<Integer, Long>> reads,
      Collection<Pair<Object._Impl, Boolean>> writes) {
    // Note: since we assume local single threading we can ignore reads
    // (conflicts are impossible)
    WORKER_LOCAL_STORE_LOGGER.fine("Local transaction preparing");
    return new Pair<LongKeyMap<SerializedObject>, Long>(
        new LongKeyHashMap<SerializedObject>(), System.currentTimeMillis());
  }

  @Override
  public void abortTransaction(TransactionID tid) {
    WORKER_LOCAL_STORE_LOGGER.fine("Local transaction aborting");
  }

  @Override
  public void commitTransaction(long transactionID) {
    WORKER_LOCAL_STORE_LOGGER.fine("Local transaction committing");
  }

  @Override
  public synchronized long createOnum() {
    return freshOID++;
  }

  @Override
  public ObjectCache.Entry readObject(long onum) {
    return readObjectNoDissem(onum);
  }

  @Override
  public ObjectCache.Entry readObjectNoDissem(long onum) {
    return readImplNoDissem(onum).$cacheEntry;
  }

  private _Impl readImplNoDissem(long onum) {
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

  @Override
  public ObjectCache.Entry readFromCache(long onum) {
    return cache.get(onum);
  }

  @Override
  public boolean checkForStaleObjects(LongKeyMap<Pair<Integer, Long>> reads) {
    return false;
  }

  /**
   * Checks if there are any existing extensions for the associated onum of each
   * {@link DelayedExtension}. If there isn't one, or if the existing one is for
   * a later time than the new request, the new {@link DelayedExtension} is
   * added to the queue, and the onum-request mapping is updated.
   */
  @Override
  public void sendExtensions(List<DelayedExtension> extensions) {
    for (DelayedExtension de : extensions) {
      synchronized (de) {
        // Keep trying until we're sure we've consistently updated the extension
        // for this onum.
        while (true) {
          DelayedExtension existing =
              unresolvedExtensions.putIfAbsent(de.onum, de);
          if (existing == null) {
            waitingExtensions.add(de);
            break;
          } else {
            synchronized (existing) {
              // Don't do anything if there's an earlier extension queued.
              if (existing.compareTo(de) <= 0) {
                break;
              }
              // Update to this event if it's earlier than the queued one.
              if (unresolvedExtensions.replace(de.onum, existing, de)) {
                waitingExtensions.remove(existing);
                waitingExtensions.add(de);
                break;
              }
            }
          }
        }
      }
    }
  }

  /**
   * The currently queued or running extensions for each onum, so we may easily
   * replace the request with a new (to be handled earlier) request, if
   * necessary.
   */
  private final ConcurrentLongKeyMap<DelayedExtension> unresolvedExtensions =
      new ConcurrentLongKeyHashMap<>();

  /**
   * The extensions waiting to run.
   */
  private final DelayQueue<DelayedExtension> waitingExtensions =
      new DelayQueue<>();

  /**
   * A thread that goes through the extensions queue, waiting until the next
   * DelayedExtension's delay is 0, and updates the expiration time of the
   * DelayedExtension's associated onum.  It continually waits until the earliest
   * {@code DelayedExtension}'s time, handles the extension in a transaction,
   * then dequeues the extension.
   */
  private final Threading.NamedRunnable extensionsRunner =
      new Threading.NamedRunnable("Extensions runner") {
        @Override
        protected void runImpl() {
          while (true) {
            try {
              // Get the next delayed extension item.
              // XXX: In an ideal world, the extension item isn't taken off the
              // queue until we've synchronized on it.  However, this doesn't
              // hurt correctness although its a little inefficient.
              //
              // I'm not too worried about this because the stars would have to
              // align so that:
              //   - The second request comes in between those lines
              //   - The second request is marked to be handled before the
              //   current request
              // At worst, this causes two requests to be handled when on would
              // have been sufficient and that second request is unlikely to be
              // very expensive to process.
              final DelayedExtension extension = waitingExtensions.take();
              Threading.getPool().submit(new Threading.NamedRunnable(
                  "Extension of " + extension.onum) {
                @Override
                protected void runImpl() {
                  // Don't want new extensions to walk away after this is
                  // done before we remove the mapping.
                  synchronized (extension) {
                    // Run a transaction handling updates
                    Logging.METRICS_LOGGER.log(Level.FINER,
                        "RUNNING EXTENSION OF {0}", extension.onum);
                    Worker.runInTopLevelTransaction(new Code<Void>() {
                      @Override
                      public Void run() {
                        Store store = LocalStore.this;
                        final Contract._Proxy target =
                            new Contract._Proxy(store, extension.onum);
                        target.attemptExtension();
                        return null;
                      }
                    }, true);
                    unresolvedExtensions.remove(extension.onum, extension);
                  }
                }
              });
            } catch (InterruptedException e) {
              Logging.logIgnoredInterruptedException(e);
            }
          }
        }
      };

  /**
   * The singleton LocalStore object is managed by the Worker class.
   *
   * @see fabric.worker.Worker.getLocalStore
   */
  protected LocalStore() {
    this.cache = new ObjectCache(this);
  }

  @Override
  public String toString() {
    return "LocalStore";
  }

  @Override
  public Map getRoot() {
    return rootMap;
  }

  public void addLocalDelegation(Principal p, Principal q) {
    localDelegates.add(new Pair<>(p, q));
  }

  public void removeLocalDelegation(Principal p, Principal q) {
    localDelegates.remove(new Pair<>(p, q));
  }

  public boolean localDelegatesTo(Principal p, Principal q) {
    return localDelegates.contains(new Pair<>(p, q));
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

  @Override
  public String name() {
    return "local";
  }

  @Override
  public NodePrincipal getPrincipal() {
    return Worker.getWorker().getPrincipal();
  }

  @Override
  public boolean isLocalStore() {
    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public void evict(long onum) {
    // nothing to do
  }

  @Override
  public void evict(long onum, int version) {
    // nothing to do
  }

  @Override
  public void cache(_Impl impl) {
    // nothing to do
  }

  @Override
  public ObjectCache.Entry newCacheEntry(_Impl impl) {
    return cache.new Entry(impl);
  }

  @Override
  public ObjectCache.Entry cache(SerializedObject obj) {
    throw new InternalError(
        "Unexpected attempt to cache a serialized local-store object.");
  }

  public void initialize() {
    // Bootstrap labels with some proxies. Any remaining references to these
    // proxies will be resolved by the hack in readObject().
    this.emptyLabel =
        new Label._Proxy(LocalStore.this, ONumConstants.EMPTY_LABEL);

    this.publicReadonlyLabel =
        new Label._Proxy(LocalStore.this, ONumConstants.PUBLIC_READONLY_LABEL);

    Worker.runInSubTransaction(new Worker.Code<Void>() {
      @Override
      @SuppressWarnings("deprecation")
      public Void run() {
        // Create global constant objects. We force renumbering on these because
        // references to these objects may leak to remote stores. (This leakage
        // is permitted since these objects are constant and are at well-known
        // onums.)

        // Create the object representing the top principal.
        topPrincipal = new TopPrincipal._Impl(LocalStore.this)
            .fabric$lang$security$PrincipalUtil$TopPrincipal$();
        topPrincipal.$forceRenumber(ONumConstants.TOP_PRINCIPAL);

        // Create the object representing the bottom confidentiality policy.
        bottomConfidPolicy = LabelUtil._Impl.readerPolicy(LocalStore.this, null,
            (Principal) null);
        bottomConfidPolicy.$forceRenumber(ONumConstants.BOTTOM_CONFIDENTIALITY);

        // Create the object representing the bottom integrity policy.
        bottomIntegPolicy = LabelUtil._Impl.writerPolicy(LocalStore.this,
            topPrincipal, topPrincipal);
        bottomIntegPolicy.$forceRenumber(ONumConstants.BOTTOM_INTEGRITY);

        // Create the object representing the public readonly label.
        publicReadonlyLabel = LabelUtil._Impl.toLabel(LocalStore.this,
            bottomConfidPolicy, bottomIntegPolicy);
        publicReadonlyLabel.$forceRenumber(ONumConstants.PUBLIC_READONLY_LABEL);

        // Create the object representing the top confidentiality policy.
        topConfidPolicy = LabelUtil._Impl.readerPolicy(LocalStore.this,
            topPrincipal, topPrincipal);
        topConfidPolicy.$forceRenumber(ONumConstants.TOP_CONFIDENTIALITY);

        // Create the object representing the top integrity policy.
        topIntegPolicy = LabelUtil._Impl.writerPolicy(LocalStore.this, null,
            (Principal) null);
        topIntegPolicy.$forceRenumber(ONumConstants.TOP_INTEGRITY);

        // Create the object representing the empty label.
        emptyLabel = LabelUtil._Impl.toLabel(LocalStore.this,
            bottomConfidPolicy, topIntegPolicy);
        emptyLabel.$forceRenumber(ONumConstants.EMPTY_LABEL);

        // Create the label {worker->_; worker<-_} for the root map.
        // No need to renumber this. References to the local store's root map
        // should not be leaking to remote stores.
        // XXX the above is not being done. HashMap needs to be parameterized on
        // labels.

        // Create root map.
        rootMap = new HashMap._Impl(LocalStore.this).fabric$util$HashMap$();
        localDelegates = new HashSet<>();

        return null;
      }
    });

    // Put global constants into the cache.
    this.cache.put((_Impl) topPrincipal.fetch());
    this.cache.put((_Impl) topConfidPolicy.fetch());
    this.cache.put((_Impl) bottomConfidPolicy.fetch());
    this.cache.put((_Impl) topIntegPolicy.fetch());
    this.cache.put((_Impl) bottomIntegPolicy.fetch());
    this.cache.put((_Impl) emptyLabel.fetch());
    this.cache.put((_Impl) publicReadonlyLabel.fetch());

    // Start the extensions runner.
    Threading.getPool().submit(extensionsRunner);
  }

  // ////////////////////////////////
  // Java custom-serialization gunk
  // ////////////////////////////////

  private java.lang.Object writeReplace() throws ObjectStreamException {
    throw new NotSerializableException();
  }
}
