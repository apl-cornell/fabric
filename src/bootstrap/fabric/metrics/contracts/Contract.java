package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.contracts.warranties.WarrantyComp;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.metrics.util.ReconfigLock;
import fabric.metrics.util.Subject;
import fabric.util.ArrayList;
import java.util.Iterator;
import fabric.util.List;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.metrics.ImmutableMetricsVector;
import fabric.worker.metrics.ImmutableSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A {@link Contract} represents an assertion that is enforced until an
 * expiration time, once {@link #refresh()}ed. If the current time is earlier
 * than the expiration time and the {@link Contract} {@link #isActivated()},
 * then the {@link Contract} is <i>valid</i>: the enforcement protocol
 * implemented by the API will ensure the assertion holds.
 * <p>
 * This class follows the {@link Subject}-{@link Observer} pattern. An instance
 * can be an observer of a {@link Metric} or other {@link Contract}s, and can be
 * observed by {@link WarrantyComp}s or other {@link Contract}s.
 * <p>
 * TODO: right now all the uses of expiry assume that the check is for a time
 * after the time the {@link Contract} was created. Some of these operations
 * will not be correct if the times being compared with are prior to creation.
 */
public interface Contract
  extends fabric.metrics.util.Observer, fabric.metrics.util.AbstractSubject {
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      get$currentPolicy();
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      set$currentPolicy(fabric.metrics.contracts.enforcement.EnforcementPolicy val);
    
    /**
   * @param store
   *            the {@link Store} this {@link Contract} is stored at
   */
    public fabric.metrics.contracts.Contract fabric$metrics$contracts$Contract$();
    
    /**
   * Extends the expiration time (queued to be extended later if the current
   * time is much earlier than the current expiration).
   *
   * @param newExpiry
   *        the new expiration time (computed at the current node) for
   *        this {@link Contract} given in milliseconds.
   */
    public void extendTo(long newExpiry, boolean isAsyncExtension);
    
    public void
      refresh_remote(fabric.lang.security.Principal p,
                     boolean asyncExtension, fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * {@inheritDoc}
   *
   * If there are no {@link Observer}s of this {@link Contract} left, mark as
   * stale (to avoid unnecessary enforcement overhead) and stop observing any
   * enforcement evidence.
   */
    public void removeObserver(fabric.metrics.util.Observer obs);
    
    /**
   * Updates the expiration time of this contract, either extending or
   * retracting as needed.
   *
   * @param newExpiry
   *            the new expiration time that could be associated with this
   *            {@link Contract} (if it's past the current expiry, it may not
   *            be immediately used to avoid unnecessary extension overhead).
   * @return true iff the contract was retracted by this update.
   */
    public boolean update(long newExpiry, boolean isAsyncExtension);
    
    /**
   * Called to retract this contract's expiry to an earlier time (applied in
   * the current transaction context).
   *
   * @param newExpiry
   *        time to set the expiry back to.
   */
    public void retract(long newExpiry);
    
    /**
   * @param time
   *        the time we're checking validity against.
   * @return true iff the contract is valid at the given time in the current
   *       transaction context.
   */
    public boolean valid(long time);
    
    /**
   * @return true iff the contract is valid at the given time in the current
   *         transaction context.
   */
    public boolean valid();
    
    /**
   * @param time
   *            the time we're checking against.
   * @return true iff the contract is stale (no longer enforced) according to
   *         the local time.
   */
    public boolean stale(long time);
    
    /**
   * @return true iff the contract is stale (no longer enforced) according to
   *         the local time.
   */
    public boolean stale();
    
    /**
   * @return The expiration time for this {@link Contract} (shifted to account
   *         for a constant "max" clock drift between the node that last set
   *         the expiration time and other nodes checking it.)
   */
    public long getExpiry();
    
    /**
   * Update the state used to enforce this contract's expiration time (and
   * possibly update the expiration time) in response to a change in the value
   * of the current evidence ({@link Subject}s) used to enforce this
   * {@link Contract}. Revokes, extends, and updates the enforcement evidence
   * as needed.
   *
   * @return true iff the contract's expiration was retracted as a result of
   *         this operation.
   */
    public boolean refresh(boolean asyncExtension);
    
    /**
   * Update the state used to enforce this contract's expiration time (and
   * possibly update the expiration time) in response to a change in the value
   * of the current evidence ({@link Subject}s) used to enforce this
   * {@link Contract}. Revokes, extends, and updates the enforcement evidence
   * as needed.
   *
   * @return true iff the contract's expiration was retracted as a result of
   *         this operation.
   */
    public abstract boolean refresh(boolean asyncExtension,
                                    fabric.worker.metrics.StatsMap weakStats);
    
    public boolean handleUpdates();
    
    /**
   * Check if this implies another {@link Contract} being considered.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link Contract} would
   *        assert a bound on
   * @param otherRate
   *        the rate of the bound that would be used by the other
   *        {@link Contract}
   * @param otherBase
   *        the base of the bound that would be used by the other
   *        {@link Contract}
   * @return true iff this would imply (and therefore) can enforce another
   *       {@link Contract} with the given metric and bound.
   */
    public abstract boolean
      implies(fabric.metrics.Metric otherMetric, double otherRate, double otherBase);
    
    /**
   * Attempt to extend this {@link Contract}'s expiration time. (Invoked to
   * perform asynchronous extensions close to the current expiration time).
   */
    public void attemptExtension_remote(fabric.lang.security.Principal caller);
    
    /**
   * Attempt to extend this {@link Contract}'s expiration time. (Invoked to
   * perform asynchronous extensions close to the current expiration time).
   */
    public void attemptExtension();
    
    /**
   * Acquire reconfig locks starting from this contract.
   */
    public void acquireReconfigLocks();
    
    /**
   * Create a {@link ConjunctionContract} with this and another contract.
   */
    public fabric.metrics.contracts.Contract and(
      fabric.metrics.contracts.Contract other);
    
    /**
   * Create a proxy for this contract on the given store.
   */
    public fabric.metrics.contracts.Contract getProxyContract(final fabric.worker.Store proxyStore);
    
    /**
   * A Contract which basically acts as a proxy for another Contract
   * to allow local access on another store while the contract is valid.
   *
   * Basically operates by using the original Contract as the only witness
   * of this metric contract.
   */
    public static interface ProxyContract
      extends fabric.metrics.contracts.Contract {
        public fabric.metrics.contracts.Contract get$target();
        
        public fabric.metrics.contracts.Contract set$target(
          fabric.metrics.contracts.Contract val);
        
        /**
     * @param target
     *        the {@link Contract} this proxies
     */
        public ProxyContract fabric$metrics$contracts$Contract$ProxyContract$(
          fabric.metrics.contracts.Contract target);
        
        public boolean refresh(boolean asyncExtension,
                               fabric.worker.metrics.StatsMap weakStats);
        
        public boolean implies(fabric.metrics.Metric otherMetric,
                               double otherRate, double otherBase);
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
        
        public fabric.metrics.contracts.Contract and(
          fabric.metrics.contracts.Contract other);
        
        public java.lang.String toString();
        
        public static class _Proxy
        extends fabric.metrics.contracts.Contract._Proxy
          implements ProxyContract {
            public fabric.metrics.contracts.Contract get$target() {
                return ((fabric.metrics.contracts.Contract.ProxyContract._Impl)
                          fetch()).get$target();
            }
            
            public fabric.metrics.contracts.Contract set$target(
              fabric.metrics.contracts.Contract val) {
                return ((fabric.metrics.contracts.Contract.ProxyContract._Impl)
                          fetch()).set$target(val);
            }
            
            public fabric.metrics.contracts.Contract.ProxyContract
              fabric$metrics$contracts$Contract$ProxyContract$(
              fabric.metrics.contracts.Contract arg1) {
                return ((fabric.metrics.contracts.Contract.ProxyContract)
                          fetch()).
                  fabric$metrics$contracts$Contract$ProxyContract$(arg1);
            }
            
            public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects(
              ) {
                return ((fabric.metrics.contracts.Contract.ProxyContract)
                          fetch()).getLeafSubjects();
            }
            
            public _Proxy(ProxyContract._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl
        extends fabric.metrics.contracts.Contract._Impl implements ProxyContract
        {
            public fabric.metrics.contracts.Contract get$target() {
                return this.target;
            }
            
            public fabric.metrics.contracts.Contract set$target(
              fabric.metrics.contracts.Contract val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.target = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            protected fabric.metrics.contracts.Contract target;
            
            /**
     * @param target
     *        the {@link Contract} this proxies
     */
            public ProxyContract
              fabric$metrics$contracts$Contract$ProxyContract$(
              fabric.metrics.contracts.Contract target) {
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(target)) instanceof ProxyContract) {
                    this.set$target(
                           ((ProxyContract)
                              fabric.lang.Object._Proxy.$getProxy(
                                                          target)).get$target(
                                                                     ));
                }
                else {
                    this.set$target(target);
                }
                fabric$metrics$contracts$Contract$();
                this.
                  set$currentPolicy(
                    ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                       new fabric.metrics.contracts.enforcement.WitnessPolicy.
                         _Impl(this.$getStore()).
                       $getProxy()).
                        fabric$metrics$contracts$enforcement$WitnessPolicy$(
                          new fabric.metrics.contracts.Contract[] { this.
                              get$target() }));
                this.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(this.get$currentPolicy()));
                return (ProxyContract) this.$getProxy();
            }
            
            public boolean refresh(boolean asyncExtension,
                                   fabric.worker.metrics.StatsMap weakStats) {
                long currentTime = java.lang.System.currentTimeMillis();
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 this.get$currentPolicy(),
                                                 null)) {
                    this.get$currentPolicy().activate(weakStats);
                    long curExpiry = this.get$currentPolicy().expiry(weakStats);
                    boolean result = update(curExpiry, asyncExtension);
                    if (curExpiry < currentTime) {
                        this.get$currentPolicy().unapply((ProxyContract)
                                                           this.$getProxy());
                        this.set$currentPolicy(null);
                        this.set$$associated(null);
                    } else {
                        this.get$currentPolicy().apply((ProxyContract)
                                                         this.$getProxy());
                    }
                    return result;
                }
                return false;
            }
            
            public boolean implies(fabric.metrics.Metric otherMetric,
                                   double otherRate, double otherBase) {
                return this.get$target().implies(otherMetric, otherRate,
                                                 otherBase);
            }
            
            public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects(
              ) {
                final fabric.worker.Store local =
                  fabric.worker.Worker.getWorker().getLocalStore();
                if (fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                       null))
                    return fabric.worker.metrics.ImmutableMetricsVector.
                      emptyVector();
                return this.get$target().getLeafSubjects();
            }
            
            public fabric.metrics.contracts.Contract and(
              fabric.metrics.contracts.Contract other) {
                return this.get$target().and(other);
            }
            
            public java.lang.String toString() {
                return "Proxy @ " +
                $getStore() +
                " for " +
                java.lang.String.
                  valueOf(
                    fabric.lang.WrappedJavaInlineable.$unwrap(
                                                        this.get$target())) +
                " until " +
                this.get$$expiry();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.Contract.ProxyContract.
                         _Proxy(this);
            }
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.target, refTypes, out,
                          intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.target =
                  (fabric.metrics.contracts.Contract)
                    $readRef(fabric.metrics.contracts.Contract._Proxy.class,
                             (fabric.common.RefTypeEnum) refTypes.next(), in,
                             store, intraStoreRefs, interStoreRefs);
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.metrics.contracts.Contract.ProxyContract._Impl src =
                  (fabric.metrics.contracts.Contract.ProxyContract._Impl) other;
                this.target = src.target;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.metrics.contracts.Contract.ProxyContract._Static
            {
                public _Proxy(fabric.metrics.contracts.Contract.ProxyContract.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.metrics.contracts.Contract.
                  ProxyContract._Static $instance;
                
                static {
                    fabric.
                      metrics.
                      contracts.
                      Contract.
                      ProxyContract.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        metrics.
                        contracts.
                        Contract.
                        ProxyContract.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.metrics.contracts.Contract.ProxyContract.
                            _Static._Impl.class);
                    $instance =
                      (fabric.metrics.contracts.Contract.ProxyContract._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.metrics.contracts.Contract.ProxyContract._Static
            {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             long expiry,
                             fabric.worker.metrics.
                               ImmutableObserverSet observers,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, expiry, observers, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.metrics.contracts.Contract.ProxyContract.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 69, -69, 41, -90,
        -21, -74, -77, -3, -93, 43, 85, -53, -1, -52, -90, -122, 79, -1, 34,
        121, -101, -118, 47, 7, 115, -37, -51, -47, 43, -114, -2, -100 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1526767069000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.contracts.Contract {
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          get$currentPolicy() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              get$currentPolicy();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          set$currentPolicy(
          fabric.metrics.contracts.enforcement.EnforcementPolicy val) {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              set$currentPolicy(val);
        }
        
        public fabric.metrics.contracts.Contract
          fabric$metrics$contracts$Contract$() {
            return ((fabric.metrics.contracts.Contract) fetch()).
              fabric$metrics$contracts$Contract$();
        }
        
        public void extendTo(long arg1, boolean arg2) {
            ((fabric.metrics.contracts.Contract) fetch()).extendTo(arg1, arg2);
        }
        
        public void refresh_remote(fabric.lang.security.Principal arg1,
                                   boolean arg2,
                                   fabric.worker.metrics.StatsMap arg3) {
            ((fabric.metrics.contracts.Contract) fetch()).refresh_remote(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public static final java.lang.Class[] $paramTypes2 = { boolean.class,
        fabric.worker.metrics.StatsMap.class };
        
        public void refresh$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, boolean arg2,
          fabric.worker.metrics.StatsMap arg3) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                refresh(arg2, arg3);
            else
                try {
                    $remoteWorker.issueRemoteCall(this,
                                                  "refresh",
                                                  $paramTypes2,
                                                  new java.lang.Object[] { arg2,
                                                    arg3 });
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public boolean update(long arg1, boolean arg2) {
            return ((fabric.metrics.contracts.Contract) fetch()).update(arg1,
                                                                        arg2);
        }
        
        public void retract(long arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).retract(arg1);
        }
        
        public boolean valid(long arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).valid(arg1);
        }
        
        public boolean valid() {
            return ((fabric.metrics.contracts.Contract) fetch()).valid();
        }
        
        public boolean stale(long arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).stale(arg1);
        }
        
        public boolean stale() {
            return ((fabric.metrics.contracts.Contract) fetch()).stale();
        }
        
        public long getExpiry() {
            return ((fabric.metrics.contracts.Contract) fetch()).getExpiry();
        }
        
        public boolean refresh(boolean arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).refresh(arg1);
        }
        
        public boolean refresh(boolean arg1,
                               fabric.worker.metrics.StatsMap arg2) {
            return ((fabric.metrics.contracts.Contract) fetch()).refresh(arg1,
                                                                         arg2);
        }
        
        public boolean handleUpdates() {
            return ((fabric.metrics.contracts.Contract) fetch()).handleUpdates(
                                                                   );
        }
        
        public boolean implies(fabric.metrics.Metric arg1, double arg2,
                               double arg3) {
            return ((fabric.metrics.contracts.Contract) fetch()).implies(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public void attemptExtension_remote(
          fabric.lang.security.Principal arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).
              attemptExtension_remote(arg1);
        }
        
        public static final java.lang.Class[] $paramTypes3 = null;
        
        public void attemptExtension$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                attemptExtension();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "attemptExtension",
                                                  $paramTypes3, null);
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public void attemptExtension() {
            ((fabric.metrics.contracts.Contract) fetch()).attemptExtension();
        }
        
        public void acquireReconfigLocks() {
            ((fabric.metrics.contracts.Contract) fetch()).acquireReconfigLocks(
                                                            );
        }
        
        public fabric.metrics.contracts.Contract and(
          fabric.metrics.contracts.Contract arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).and(arg1);
        }
        
        public fabric.metrics.contracts.Contract getProxyContract(
          fabric.worker.Store arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).
              getProxyContract(arg1);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.contracts.Contract) fetch()).
              getLeafSubjects();
        }
        
        public _Proxy(Contract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl
    extends fabric.metrics.util.AbstractSubject._Impl
      implements fabric.metrics.contracts.Contract {
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          get$currentPolicy() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.currentPolicy;
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          set$currentPolicy(
          fabric.metrics.contracts.enforcement.EnforcementPolicy val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.currentPolicy = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.contracts.enforcement.EnforcementPolicy currentPolicy;
        
        /**
   * @param store
   *            the {@link Store} this {@link Contract} is stored at
   */
        public fabric.metrics.contracts.Contract
          fabric$metrics$contracts$Contract$() {
            fabric$metrics$util$AbstractSubject$();
            this.set$$expiry((long) -1);
            return (fabric.metrics.contracts.Contract) this.$getProxy();
        }
        
        /**
   * Extends the expiration time (queued to be extended later if the current
   * time is much earlier than the current expiration).
   *
   * @param newExpiry
   *        the new expiration time (computed at the current node) for
   *        this {@link Contract} given in milliseconds.
   */
        public void extendTo(long newExpiry, boolean isAsyncExtension) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            long currentTime = java.lang.System.currentTimeMillis();
            if (getExpiry() -
                  currentTime <=
                  fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                  get$EXTENSION_WINDOW() || isAsyncExtension) {
                for (java.util.Iterator iter = getObservers().iterator();
                     iter.hasNext(); ) {
                    fabric.metrics.util.Observer
                      parent =
                      (fabric.metrics.util.Observer)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          fabric.lang.WrappedJavaInlineable.$wrap(iter.next()));
                    if (fabric.lang.Object._Proxy.
                          $getProxy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                parent)) instanceof fabric.metrics.contracts.Contract) {
                        tm.registerDelayedExtension(
                             parent,
                             (fabric.metrics.contracts.Contract)
                               this.$getProxy());
                    }
                }
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINE,
                    "SYNCH EXTENSION OF {0} TO {2} IN {1}",
                    new java.lang.Object[] { (java.lang.Object)
                                               fabric.lang.WrappedJavaInlineable.
                                               $unwrap(
                                                 (fabric.metrics.contracts.Contract)
                                                   this.$getProxy()),
                      fabric.worker.transaction.TransactionManager.
                        getInstance().
                        getCurrentLog(),
                      java.lang.Long.
                        valueOf(newExpiry) });
                this.set$$expiry((long) newExpiry);
            }
            else {
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINE,
                    "DELAYED EXTENSION OF {0} IN {1}",
                    new java.lang.Object[] { (java.lang.Object)
                                               fabric.lang.WrappedJavaInlineable.
                                               $unwrap(
                                                 (fabric.metrics.contracts.Contract)
                                                   this.$getProxy()),
                      fabric.worker.transaction.TransactionManager.
                        getInstance().
                        getCurrentLog() });
                tm.registerDelayedExtension((fabric.metrics.contracts.Contract)
                                              this.$getProxy());
            }
        }
        
        public void refresh_remote(fabric.lang.security.Principal p,
                                   boolean asyncExtension,
                                   fabric.worker.metrics.StatsMap weakStats) {
            this.refresh(asyncExtension, weakStats);
        }
        
        /**
   * {@inheritDoc}
   *
   * If there are no {@link Observer}s of this {@link Contract} left, mark as
   * stale (to avoid unnecessary enforcement overhead) and stop observing any
   * enforcement evidence.
   */
        public void removeObserver(fabric.metrics.util.Observer obs) {
            fabric.metrics.contracts.Contract._Impl.
              static_removeObserver((fabric.metrics.contracts.Contract)
                                      this.$getProxy(), obs);
        }
        
        private static void static_removeObserver(
          fabric.metrics.contracts.Contract tmp,
          fabric.metrics.util.Observer obs) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.util.AbstractSubject._Impl.static_removeObserver(
                                                            tmp, obs);
                if (!tmp.isObserved()) {
                    tmp.set$$expiry((long) -1);
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     tmp.get$currentPolicy(),
                                                     null)) {
                        tmp.get$currentPolicy().unapply(tmp);
                        tmp.set$currentPolicy(null);
                        tmp.set$$associated(null);
                    }
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm386 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled389 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff387 = 1;
                    boolean $doBackoff388 = true;
                    boolean $retry383 = true;
                    $label381: for (boolean $commit382 = false; !$commit382; ) {
                        if ($backoffEnabled389) {
                            if ($doBackoff388) {
                                if ($backoff387 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff387);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e384) {
                                            
                                        }
                                    }
                                }
                                if ($backoff387 < 5000) $backoff387 *= 2;
                            }
                            $doBackoff388 = $backoff387 <= 32 || !$doBackoff388;
                        }
                        $commit382 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.util.AbstractSubject._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved()) {
                                tmp.set$$expiry((long) -1);
                                if (!fabric.lang.Object._Proxy.
                                      idEquals(tmp.get$currentPolicy(), null)) {
                                    tmp.get$currentPolicy().unapply(tmp);
                                    tmp.set$currentPolicy(null);
                                    tmp.set$$associated(null);
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e384) {
                            $commit382 = false;
                            continue $label381;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e384) {
                            $commit382 = false;
                            fabric.common.TransactionID $currentTid385 =
                              $tm386.getCurrentTid();
                            if ($e384.tid.isDescendantOf($currentTid385))
                                continue $label381;
                            if ($currentTid385.parent != null) {
                                $retry383 = false;
                                throw $e384;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e384) {
                            $commit382 = false;
                            if ($tm386.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid385 =
                              $tm386.getCurrentTid();
                            if ($e384.tid.isDescendantOf($currentTid385)) {
                                $retry383 = true;
                            }
                            else if ($currentTid385.parent != null) {
                                $retry383 = false;
                                throw $e384;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e384) {
                            $commit382 = false;
                            if ($tm386.checkForStaleObjects())
                                continue $label381;
                            $retry383 = false;
                            throw new fabric.worker.AbortException($e384);
                        }
                        finally {
                            if ($commit382) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e384) {
                                    $commit382 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e384) {
                                    $commit382 = false;
                                    fabric.common.TransactionID $currentTid385 =
                                      $tm386.getCurrentTid();
                                    if ($currentTid385 != null) {
                                        if ($e384.tid.equals($currentTid385) ||
                                              !$e384.tid.isDescendantOf(
                                                           $currentTid385)) {
                                            throw $e384;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit382 && $retry383) {
                                {  }
                                continue $label381;
                            }
                        }
                    }
                }
            }
        }
        
        /**
   * Updates the expiration time of this contract, either extending or
   * retracting as needed.
   *
   * @param newExpiry
   *            the new expiration time that could be associated with this
   *            {@link Contract} (if it's past the current expiry, it may not
   *            be immediately used to avoid unnecessary extension overhead).
   * @return true iff the contract was retracted by this update.
   */
        public boolean update(long newExpiry, boolean isAsyncExtension) {
            fabric.worker.Worker localW = fabric.worker.Worker.getWorker();
            if (!localW.getStore(localW.getName()).equals(getStore()))
                newExpiry =
                  newExpiry -
                    fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                    get$DRIFT_FACTOR();
            if (getExpiry() <
                  newExpiry -
                  fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                  get$DRIFT_FACTOR()) {
                extendTo(newExpiry, isAsyncExtension);
            }
            else if (getExpiry() > newExpiry) {
                retract(newExpiry);
                return true;
            }
            return false;
        }
        
        /**
   * Called to retract this contract's expiry to an earlier time (applied in
   * the current transaction context).
   *
   * @param newExpiry
   *        time to set the expiry back to.
   */
        public void retract(long newExpiry) {
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINE,
                "RETRACTION OF {0} TO {2} IN {1}",
                new java.lang.Object[] { (java.lang.Object)
                                           fabric.lang.WrappedJavaInlineable.
                                           $unwrap(
                                             (fabric.metrics.contracts.Contract)
                                               this.$getProxy()),
                  fabric.worker.transaction.TransactionManager.
                    getInstance().
                    getCurrentLog(),
                  java.lang.Long.
                    valueOf(newExpiry) });
            this.set$$expiry((long) newExpiry);
        }
        
        /**
   * @param time
   *        the time we're checking validity against.
   * @return true iff the contract is valid at the given time in the current
   *       transaction context.
   */
        public boolean valid(long time) {
            boolean rtn = getExpiry() >= time;
            if (rtn)
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerExpiryUse(getExpiry());
            return rtn;
        }
        
        /**
   * @return true iff the contract is valid at the given time in the current
   *         transaction context.
   */
        public boolean valid() {
            boolean rtn = getExpiry() >= java.lang.System.currentTimeMillis();
            if (rtn)
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerExpiryUse(getExpiry());
            return rtn;
        }
        
        /**
   * @param time
   *            the time we're checking against.
   * @return true iff the contract is stale (no longer enforced) according to
   *         the local time.
   */
        public boolean stale(long time) { return getExpiry() < time; }
        
        /**
   * @return true iff the contract is stale (no longer enforced) according to
   *         the local time.
   */
        public boolean stale() {
            return getExpiry() < java.lang.System.currentTimeMillis();
        }
        
        /**
   * @return The expiration time for this {@link Contract} (shifted to account
   *         for a constant "max" clock drift between the node that last set
   *         the expiration time and other nodes checking it.)
   */
        public long getExpiry() {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            return this.get$$expiry() -
              fabric.metrics.contracts.Contract._Static._Proxy.$instance.
              get$DRIFT_FACTOR();
        }
        
        /**
   * Update the state used to enforce this contract's expiration time (and
   * possibly update the expiration time) in response to a change in the value
   * of the current evidence ({@link Subject}s) used to enforce this
   * {@link Contract}. Revokes, extends, and updates the enforcement evidence
   * as needed.
   *
   * @return true iff the contract's expiration was retracted as a result of
   *         this operation.
   */
        public boolean refresh(boolean asyncExtension) {
            return refresh(asyncExtension,
                           fabric.worker.metrics.StatsMap.emptyStats());
        }
        
        /**
   * Update the state used to enforce this contract's expiration time (and
   * possibly update the expiration time) in response to a change in the value
   * of the current evidence ({@link Subject}s) used to enforce this
   * {@link Contract}. Revokes, extends, and updates the enforcement evidence
   * as needed.
   *
   * @return true iff the contract's expiration was retracted as a result of
   *         this operation.
   */
        public abstract boolean refresh(
          boolean asyncExtension, fabric.worker.metrics.StatsMap weakStats);
        
        public boolean handleUpdates() {
            if (valid()) { return refresh(false); }
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                this.get$currentPolicy().unapply(
                                           (fabric.metrics.contracts.Contract)
                                             this.$getProxy());
                this.set$currentPolicy(null);
                this.set$$associated(null);
                return true;
            }
            return false;
        }
        
        /**
   * Check if this implies another {@link Contract} being considered.
   *
   * @param otherMetric
   *        the {@link Metric} the other {@link Contract} would
   *        assert a bound on
   * @param otherRate
   *        the rate of the bound that would be used by the other
   *        {@link Contract}
   * @param otherBase
   *        the base of the bound that would be used by the other
   *        {@link Contract}
   * @return true iff this would imply (and therefore) can enforce another
   *       {@link Contract} with the given metric and bound.
   */
        public abstract boolean implies(
          fabric.metrics.Metric otherMetric, double otherRate, double otherBase);
        
        /**
   * Attempt to extend this {@link Contract}'s expiration time. (Invoked to
   * perform asynchronous extensions close to the current expiration time).
   */
        public void attemptExtension_remote(
          fabric.lang.security.Principal caller) {
            this.attemptExtension();
        }
        
        /**
   * Attempt to extend this {@link Contract}'s expiration time. (Invoked to
   * perform asynchronous extensions close to the current expiration time).
   */
        public void attemptExtension() {
            fabric.metrics.contracts.Contract._Impl.
              static_attemptExtension((fabric.metrics.contracts.Contract)
                                        this.$getProxy());
        }
        
        private static void static_attemptExtension(
          fabric.metrics.contracts.Contract tmp) {
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINE,
                "ASYNC EXTENSION OF " +
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         tmp)) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentTid());
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.valid()) {
                    tmp.refresh(true);
                }
                else if (!fabric.lang.Object._Proxy.idEquals(
                                                      tmp.get$currentPolicy(),
                                                      null)) {
                    tmp.get$currentPolicy().unapply(tmp);
                    tmp.set$currentPolicy(null);
                    tmp.set$$associated(null);
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm395 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled398 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff396 = 1;
                    boolean $doBackoff397 = true;
                    boolean $retry392 = true;
                    $label390: for (boolean $commit391 = false; !$commit391; ) {
                        if ($backoffEnabled398) {
                            if ($doBackoff397) {
                                if ($backoff396 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff396);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e393) {
                                            
                                        }
                                    }
                                }
                                if ($backoff396 < 5000) $backoff396 *= 2;
                            }
                            $doBackoff397 = $backoff396 <= 32 || !$doBackoff397;
                        }
                        $commit391 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.valid()) {
                                tmp.refresh(true);
                            }
                            else if (!fabric.lang.Object._Proxy.
                                       idEquals(tmp.get$currentPolicy(),
                                                null)) {
                                tmp.get$currentPolicy().unapply(tmp);
                                tmp.set$currentPolicy(null);
                                tmp.set$$associated(null);
                            }
                        }
                        catch (final fabric.worker.RetryException $e393) {
                            $commit391 = false;
                            continue $label390;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e393) {
                            $commit391 = false;
                            fabric.common.TransactionID $currentTid394 =
                              $tm395.getCurrentTid();
                            if ($e393.tid.isDescendantOf($currentTid394))
                                continue $label390;
                            if ($currentTid394.parent != null) {
                                $retry392 = false;
                                throw $e393;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e393) {
                            $commit391 = false;
                            if ($tm395.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid394 =
                              $tm395.getCurrentTid();
                            if ($e393.tid.isDescendantOf($currentTid394)) {
                                $retry392 = true;
                            }
                            else if ($currentTid394.parent != null) {
                                $retry392 = false;
                                throw $e393;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e393) {
                            $commit391 = false;
                            if ($tm395.checkForStaleObjects())
                                continue $label390;
                            $retry392 = false;
                            throw new fabric.worker.AbortException($e393);
                        }
                        finally {
                            if ($commit391) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e393) {
                                    $commit391 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e393) {
                                    $commit391 = false;
                                    fabric.common.TransactionID $currentTid394 =
                                      $tm395.getCurrentTid();
                                    if ($currentTid394 != null) {
                                        if ($e393.tid.equals($currentTid394) ||
                                              !$e393.tid.isDescendantOf(
                                                           $currentTid394)) {
                                            throw $e393;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit391 && $retry392) {
                                {  }
                                continue $label390;
                            }
                        }
                    }
                }
            }
        }
        
        /**
   * Acquire reconfig locks starting from this contract.
   */
        public void acquireReconfigLocks() {  }
        
        /**
   * Create a {@link ConjunctionContract} with this and another contract.
   */
        public fabric.metrics.contracts.Contract and(
          fabric.metrics.contracts.Contract other) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(other)) instanceof ProxyContract)
                other =
                  ((ProxyContract) fabric.lang.Object._Proxy.$getProxy(other)).
                    get$target();
            return ((fabric.metrics.contracts.ConjunctionContract)
                      new fabric.metrics.contracts.ConjunctionContract._Impl(
                        this.$getStore()).$getProxy()).
              fabric$metrics$contracts$ConjunctionContract$(
                new fabric.metrics.contracts.Contract[] { (fabric.metrics.contracts.Contract)
                                                            this.$getProxy(),
                  other });
        }
        
        /**
   * Create a proxy for this contract on the given store.
   */
        public fabric.metrics.contracts.Contract getProxyContract(
          final fabric.worker.Store proxyStore) {
            return fabric.metrics.contracts.Contract._Impl.
              static_getProxyContract((fabric.metrics.contracts.Contract)
                                        this.$getProxy(), proxyStore);
        }
        
        private static fabric.metrics.contracts.Contract
          static_getProxyContract(fabric.metrics.contracts.Contract tmp,
                                  final fabric.worker.Store proxyStore) {
            fabric.metrics.contracts.Contract proxy = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.$getStore().equals(proxyStore)) {
                    proxy = tmp;
                }
                else if (fabric.lang.Object._Proxy.
                           $getProxy(
                             (java.lang.Object)
                               fabric.lang.WrappedJavaInlineable.
                               $unwrap(tmp)) instanceof ProxyContract &&
                           ((ProxyContract)
                              fabric.lang.Object._Proxy.$getProxy(tmp)).
                           get$target().$getStore().equals(proxyStore)) {
                    proxy =
                      ((ProxyContract)
                         fabric.lang.Object._Proxy.$getProxy(tmp)).get$target();
                }
                else {
                    proxy =
                      ((ProxyContract)
                         new fabric.metrics.contracts.Contract.ProxyContract.
                           _Impl(proxyStore).
                         $getProxy()).
                        fabric$metrics$contracts$Contract$ProxyContract$(tmp);
                    proxy.refresh(false);
                }
            }
            else {
                {
                    fabric.metrics.contracts.Contract proxy$var399 = proxy;
                    fabric.worker.transaction.TransactionManager $tm405 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled408 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff406 = 1;
                    boolean $doBackoff407 = true;
                    boolean $retry402 = true;
                    $label400: for (boolean $commit401 = false; !$commit401; ) {
                        if ($backoffEnabled408) {
                            if ($doBackoff407) {
                                if ($backoff406 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff406);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e403) {
                                            
                                        }
                                    }
                                }
                                if ($backoff406 < 5000) $backoff406 *= 2;
                            }
                            $doBackoff407 = $backoff406 <= 32 || !$doBackoff407;
                        }
                        $commit401 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.$getStore().equals(proxyStore)) {
                                proxy = tmp;
                            }
                            else if (fabric.lang.Object._Proxy.
                                       $getProxy(
                                         (java.lang.Object)
                                           fabric.lang.WrappedJavaInlineable.
                                           $unwrap(
                                             tmp)) instanceof ProxyContract &&
                                       ((ProxyContract)
                                          fabric.lang.Object._Proxy.$getProxy(
                                                                      tmp)).
                                       get$target().$getStore().equals(
                                                                  proxyStore)) {
                                proxy =
                                  ((ProxyContract)
                                     fabric.lang.Object._Proxy.$getProxy(tmp)).
                                    get$target();
                            }
                            else {
                                proxy =
                                  ((ProxyContract)
                                     new fabric.metrics.contracts.Contract.
                                       ProxyContract._Impl(proxyStore).
                                     $getProxy()).
                                    fabric$metrics$contracts$Contract$ProxyContract$(
                                      tmp);
                                proxy.refresh(false);
                            }
                        }
                        catch (final fabric.worker.RetryException $e403) {
                            $commit401 = false;
                            continue $label400;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e403) {
                            $commit401 = false;
                            fabric.common.TransactionID $currentTid404 =
                              $tm405.getCurrentTid();
                            if ($e403.tid.isDescendantOf($currentTid404))
                                continue $label400;
                            if ($currentTid404.parent != null) {
                                $retry402 = false;
                                throw $e403;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e403) {
                            $commit401 = false;
                            if ($tm405.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid404 =
                              $tm405.getCurrentTid();
                            if ($e403.tid.isDescendantOf($currentTid404)) {
                                $retry402 = true;
                            }
                            else if ($currentTid404.parent != null) {
                                $retry402 = false;
                                throw $e403;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e403) {
                            $commit401 = false;
                            if ($tm405.checkForStaleObjects())
                                continue $label400;
                            $retry402 = false;
                            throw new fabric.worker.AbortException($e403);
                        }
                        finally {
                            if ($commit401) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e403) {
                                    $commit401 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e403) {
                                    $commit401 = false;
                                    fabric.common.TransactionID $currentTid404 =
                                      $tm405.getCurrentTid();
                                    if ($currentTid404 != null) {
                                        if ($e403.tid.equals($currentTid404) ||
                                              !$e403.tid.isDescendantOf(
                                                           $currentTid404)) {
                                            throw $e403;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit401 && $retry402) {
                                { proxy = proxy$var399; }
                                continue $label400;
                            }
                        }
                    }
                }
            }
            return proxy;
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.Contract._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.currentPolicy, refTypes, out,
                      intraStoreRefs, interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.currentPolicy =
              (fabric.
                metrics.
                contracts.
                enforcement.
                EnforcementPolicy)
                $readRef(
                  fabric.metrics.contracts.enforcement.EnforcementPolicy.
                    _Proxy.class, (fabric.common.RefTypeEnum) refTypes.next(),
                  in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.Contract._Impl src =
              (fabric.metrics.contracts.Contract._Impl) other;
            this.currentPolicy = src.currentPolicy;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public long get$DRIFT_FACTOR();
        
        public long set$DRIFT_FACTOR(long val);
        
        public long postInc$DRIFT_FACTOR();
        
        public long postDec$DRIFT_FACTOR();
        
        public long get$EXTENSION_WINDOW();
        
        public long set$EXTENSION_WINDOW(long val);
        
        public long postInc$EXTENSION_WINDOW();
        
        public long postDec$EXTENSION_WINDOW();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.Contract._Static {
            public long get$DRIFT_FACTOR() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$DRIFT_FACTOR();
            }
            
            public long set$DRIFT_FACTOR(long val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$DRIFT_FACTOR(val);
            }
            
            public long postInc$DRIFT_FACTOR() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$DRIFT_FACTOR();
            }
            
            public long postDec$DRIFT_FACTOR() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$DRIFT_FACTOR();
            }
            
            public long get$EXTENSION_WINDOW() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$EXTENSION_WINDOW();
            }
            
            public long set$EXTENSION_WINDOW(long val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$EXTENSION_WINDOW(val);
            }
            
            public long postInc$EXTENSION_WINDOW() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$EXTENSION_WINDOW();
            }
            
            public long postDec$EXTENSION_WINDOW() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$EXTENSION_WINDOW();
            }
            
            public _Proxy(fabric.metrics.contracts.Contract._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.Contract._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  Contract.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.contracts.Contract._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.Contract._Static._Impl.class);
                $instance = (fabric.metrics.contracts.Contract._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.Contract._Static {
            public long get$DRIFT_FACTOR() { return this.DRIFT_FACTOR; }
            
            public long set$DRIFT_FACTOR(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DRIFT_FACTOR = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$DRIFT_FACTOR() {
                long tmp = this.get$DRIFT_FACTOR();
                this.set$DRIFT_FACTOR((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$DRIFT_FACTOR() {
                long tmp = this.get$DRIFT_FACTOR();
                this.set$DRIFT_FACTOR((long) (tmp - 1));
                return tmp;
            }
            
            public long DRIFT_FACTOR;
            
            public long get$EXTENSION_WINDOW() { return this.EXTENSION_WINDOW; }
            
            public long set$EXTENSION_WINDOW(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.EXTENSION_WINDOW = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$EXTENSION_WINDOW() {
                long tmp = this.get$EXTENSION_WINDOW();
                this.set$EXTENSION_WINDOW((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$EXTENSION_WINDOW() {
                long tmp = this.get$EXTENSION_WINDOW();
                this.set$EXTENSION_WINDOW((long) (tmp - 1));
                return tmp;
            }
            
            public long EXTENSION_WINDOW;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeLong(this.DRIFT_FACTOR);
                out.writeLong(this.EXTENSION_WINDOW);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
                this.DRIFT_FACTOR = in.readLong();
                this.EXTENSION_WINDOW = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.Contract._Static._Proxy(
                         this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm414 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled417 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff415 = 1;
                        boolean $doBackoff416 = true;
                        boolean $retry411 = true;
                        $label409: for (boolean $commit410 = false; !$commit410;
                                        ) {
                            if ($backoffEnabled417) {
                                if ($doBackoff416) {
                                    if ($backoff415 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff415);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e412) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff415 < 5000) $backoff415 *= 2;
                                }
                                $doBackoff416 = $backoff415 <= 32 ||
                                                  !$doBackoff416;
                            }
                            $commit410 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$DRIFT_FACTOR((long) 0);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$EXTENSION_WINDOW((long) 1000);
                            }
                            catch (final fabric.worker.RetryException $e412) {
                                $commit410 = false;
                                continue $label409;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e412) {
                                $commit410 = false;
                                fabric.common.TransactionID $currentTid413 =
                                  $tm414.getCurrentTid();
                                if ($e412.tid.isDescendantOf($currentTid413))
                                    continue $label409;
                                if ($currentTid413.parent != null) {
                                    $retry411 = false;
                                    throw $e412;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e412) {
                                $commit410 = false;
                                if ($tm414.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid413 =
                                  $tm414.getCurrentTid();
                                if ($e412.tid.isDescendantOf($currentTid413)) {
                                    $retry411 = true;
                                }
                                else if ($currentTid413.parent != null) {
                                    $retry411 = false;
                                    throw $e412;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e412) {
                                $commit410 = false;
                                if ($tm414.checkForStaleObjects())
                                    continue $label409;
                                $retry411 = false;
                                throw new fabric.worker.AbortException($e412);
                            }
                            finally {
                                if ($commit410) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e412) {
                                        $commit410 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e412) {
                                        $commit410 = false;
                                        fabric.common.TransactionID
                                          $currentTid413 =
                                          $tm414.getCurrentTid();
                                        if ($currentTid413 != null) {
                                            if ($e412.tid.equals(
                                                            $currentTid413) ||
                                                  !$e412.tid.
                                                  isDescendantOf(
                                                    $currentTid413)) {
                                                throw $e412;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit410 && $retry411) {
                                    {  }
                                    continue $label409;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 55, -63, -88, -76, 73,
    22, 14, 81, -33, 17, -104, 11, 51, -40, -87, 91, -120, -89, 87, 40, -128,
    15, 84, 0, -26, 93, -34, -75, -72, -92, 74, -85 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526767069000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
