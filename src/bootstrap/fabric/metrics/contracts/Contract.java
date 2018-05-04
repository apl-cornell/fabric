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
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A {@link Contract} represents an assertion that is enforced until an
 * expiration time, once {@link #activate()}d. If the current time is earlier
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
    
    /**
   * Activate and start enforcing this {@link Contract} in the System.
   */
    public void activate();
    
    public void activate_remote(fabric.lang.security.Principal p);
    
    public void refresh_remote(fabric.lang.security.Principal p, boolean asyncExtension);
    
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
    public abstract boolean refresh(boolean asyncExtension);
    
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
        
        public boolean refresh(boolean asyncExtension);
        
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
            
            public boolean refresh(boolean asyncExtension) {
                long currentTime = java.lang.System.currentTimeMillis();
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 this.get$currentPolicy(),
                                                 null)) {
                    this.get$currentPolicy().activate();
                    long curExpiry = this.get$currentPolicy().expiry();
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
        
        public static final byte[] $classHash = new byte[] { -92, 23, 115, 12,
        -92, 57, -24, 102, 7, -20, -100, 68, -94, 70, 114, -35, -67, 36, -71,
        -95, -120, -59, 75, 71, 84, -91, 64, -78, -46, 30, -53, -47 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1525449948000L;
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
        
        public void activate() {
            ((fabric.metrics.contracts.Contract) fetch()).activate();
        }
        
        public void activate_remote(fabric.lang.security.Principal arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).activate_remote(arg1);
        }
        
        public static final java.lang.Class[] $paramTypes2 = null;
        
        public void activate$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                activate();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "activate",
                                                  $paramTypes2, null);
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public void refresh_remote(fabric.lang.security.Principal arg1,
                                   boolean arg2) {
            ((fabric.metrics.contracts.Contract) fetch()).refresh_remote(arg1,
                                                                         arg2);
        }
        
        public static final java.lang.Class[] $paramTypes3 = { boolean.class };
        
        public void refresh$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, boolean arg2) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                refresh(arg2);
            else
                try {
                    $remoteWorker.issueRemoteCall(
                                    this, "refresh", $paramTypes3,
                                    new java.lang.Object[] { arg2 });
                }
                catch (fabric.worker.remote.RemoteCallException $e) {
                    java.lang.Throwable $t = $e.getCause();
                    throw new fabric.common.exceptions.InternalError($e);
                }
        }
        
        public static void static_activate(
          fabric.metrics.contracts.Contract arg1) {
            fabric.metrics.contracts.Contract._Impl.static_activate(arg1);
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
        
        public static final java.lang.Class[] $paramTypes4 = null;
        
        public void attemptExtension$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                attemptExtension();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "attemptExtension",
                                                  $paramTypes4, null);
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
        
        /**
   * Activate and start enforcing this {@link Contract} in the System.
   */
        public void activate() { refresh(false); }
        
        public void activate_remote(fabric.lang.security.Principal p) {
            this.refresh(false);
        }
        
        public void refresh_remote(fabric.lang.security.Principal p,
                                   boolean asyncExtension) {
            this.refresh(asyncExtension);
        }
        
        public static void static_activate(
          fabric.metrics.contracts.Contract tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (!fabric.lang.Object._Proxy.idEquals(tmp.get$currentPolicy(),
                                                        null)) {
                    tmp.get$currentPolicy().activate();
                }
                if (!fabric.lang.Object._Proxy.idEquals(tmp.get$currentPolicy(),
                                                        null) &&
                      tmp.get$currentPolicy().expiry() >
                      java.lang.System.currentTimeMillis()) {
                    tmp.set$$expiry((long) tmp.get$currentPolicy().expiry());
                    tmp.get$currentPolicy().apply(tmp);
                } else {
                    tmp.set$currentPolicy(null);
                    tmp.set$$associated(null);
                }
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINE,
                    "ACTIVATED {0} IN {1}",
                    new java.lang.Object[] { (java.lang.Object)
                                               fabric.lang.WrappedJavaInlineable.
                                               $unwrap(tmp),
                      fabric.worker.transaction.TransactionManager.
                        getInstance().
                        getCurrentLog() });
            }
            else {
                if (!fabric.lang.Object._Proxy.idEquals(tmp.get$currentPolicy(),
                                                        null)) {
                    tmp.get$currentPolicy().activate();
                }
                {
                    fabric.worker.transaction.TransactionManager $tm460 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled463 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff461 = 1;
                    boolean $doBackoff462 = true;
                    boolean $retry457 = true;
                    $label455: for (boolean $commit456 = false; !$commit456; ) {
                        if ($backoffEnabled463) {
                            if ($doBackoff462) {
                                if ($backoff461 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff461);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e458) {
                                            
                                        }
                                    }
                                }
                                if ($backoff461 < 5000) $backoff461 *= 2;
                            }
                            $doBackoff462 = $backoff461 <= 32 || !$doBackoff462;
                        }
                        $commit456 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!fabric.lang.Object._Proxy.
                                  idEquals(tmp.get$currentPolicy(), null) &&
                                  tmp.get$currentPolicy().expiry() >
                                  java.lang.System.currentTimeMillis()) {
                                tmp.set$$expiry(
                                      (long) tmp.get$currentPolicy().expiry());
                                tmp.get$currentPolicy().apply(tmp);
                            }
                            else {
                                tmp.set$currentPolicy(null);
                                tmp.set$$associated(null);
                            }
                            fabric.common.Logging.METRICS_LOGGER.
                              log(
                                java.util.logging.Level.FINE,
                                "ACTIVATED {0} IN {1}",
                                new java.lang.Object[] { (java.lang.Object)
                                                           fabric.lang.WrappedJavaInlineable.
                                                           $unwrap(tmp),
                                  fabric.worker.transaction.TransactionManager.
                                    getInstance().
                                    getCurrentLog() });
                        }
                        catch (final fabric.worker.RetryException $e458) {
                            $commit456 = false;
                            continue $label455;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e458) {
                            $commit456 = false;
                            fabric.common.TransactionID $currentTid459 =
                              $tm460.getCurrentTid();
                            if ($e458.tid.isDescendantOf($currentTid459))
                                continue $label455;
                            if ($currentTid459.parent != null) {
                                $retry457 = false;
                                throw $e458;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e458) {
                            $commit456 = false;
                            if ($tm460.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid459 =
                              $tm460.getCurrentTid();
                            if ($e458.tid.isDescendantOf($currentTid459)) {
                                $retry457 = true;
                            }
                            else if ($currentTid459.parent != null) {
                                $retry457 = false;
                                throw $e458;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e458) {
                            $commit456 = false;
                            if ($tm460.checkForStaleObjects())
                                continue $label455;
                            $retry457 = false;
                            throw new fabric.worker.AbortException($e458);
                        }
                        finally {
                            if ($commit456) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e458) {
                                    $commit456 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e458) {
                                    $commit456 = false;
                                    fabric.common.TransactionID $currentTid459 =
                                      $tm460.getCurrentTid();
                                    if ($currentTid459 != null) {
                                        if ($e458.tid.equals($currentTid459) ||
                                              !$e458.tid.isDescendantOf(
                                                           $currentTid459)) {
                                            throw $e458;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit456 && $retry457) {
                                {  }
                                continue $label455;
                            }
                        }
                    }
                }
            }
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
                    fabric.worker.transaction.TransactionManager $tm469 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled472 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff470 = 1;
                    boolean $doBackoff471 = true;
                    boolean $retry466 = true;
                    $label464: for (boolean $commit465 = false; !$commit465; ) {
                        if ($backoffEnabled472) {
                            if ($doBackoff471) {
                                if ($backoff470 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff470);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e467) {
                                            
                                        }
                                    }
                                }
                                if ($backoff470 < 5000) $backoff470 *= 2;
                            }
                            $doBackoff471 = $backoff470 <= 32 || !$doBackoff471;
                        }
                        $commit465 = true;
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
                        catch (final fabric.worker.RetryException $e467) {
                            $commit465 = false;
                            continue $label464;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e467) {
                            $commit465 = false;
                            fabric.common.TransactionID $currentTid468 =
                              $tm469.getCurrentTid();
                            if ($e467.tid.isDescendantOf($currentTid468))
                                continue $label464;
                            if ($currentTid468.parent != null) {
                                $retry466 = false;
                                throw $e467;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e467) {
                            $commit465 = false;
                            if ($tm469.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid468 =
                              $tm469.getCurrentTid();
                            if ($e467.tid.isDescendantOf($currentTid468)) {
                                $retry466 = true;
                            }
                            else if ($currentTid468.parent != null) {
                                $retry466 = false;
                                throw $e467;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e467) {
                            $commit465 = false;
                            if ($tm469.checkForStaleObjects())
                                continue $label464;
                            $retry466 = false;
                            throw new fabric.worker.AbortException($e467);
                        }
                        finally {
                            if ($commit465) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e467) {
                                    $commit465 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e467) {
                                    $commit465 = false;
                                    fabric.common.TransactionID $currentTid468 =
                                      $tm469.getCurrentTid();
                                    if ($currentTid468 != null) {
                                        if ($e467.tid.equals($currentTid468) ||
                                              !$e467.tid.isDescendantOf(
                                                           $currentTid468)) {
                                            throw $e467;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit465 && $retry466) {
                                {  }
                                continue $label464;
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
        public abstract boolean refresh(boolean asyncExtension);
        
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
                    fabric.worker.transaction.TransactionManager $tm478 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled481 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff479 = 1;
                    boolean $doBackoff480 = true;
                    boolean $retry475 = true;
                    $label473: for (boolean $commit474 = false; !$commit474; ) {
                        if ($backoffEnabled481) {
                            if ($doBackoff480) {
                                if ($backoff479 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff479);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e476) {
                                            
                                        }
                                    }
                                }
                                if ($backoff479 < 5000) $backoff479 *= 2;
                            }
                            $doBackoff480 = $backoff479 <= 32 || !$doBackoff480;
                        }
                        $commit474 = true;
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
                        catch (final fabric.worker.RetryException $e476) {
                            $commit474 = false;
                            continue $label473;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e476) {
                            $commit474 = false;
                            fabric.common.TransactionID $currentTid477 =
                              $tm478.getCurrentTid();
                            if ($e476.tid.isDescendantOf($currentTid477))
                                continue $label473;
                            if ($currentTid477.parent != null) {
                                $retry475 = false;
                                throw $e476;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e476) {
                            $commit474 = false;
                            if ($tm478.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid477 =
                              $tm478.getCurrentTid();
                            if ($e476.tid.isDescendantOf($currentTid477)) {
                                $retry475 = true;
                            }
                            else if ($currentTid477.parent != null) {
                                $retry475 = false;
                                throw $e476;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e476) {
                            $commit474 = false;
                            if ($tm478.checkForStaleObjects())
                                continue $label473;
                            $retry475 = false;
                            throw new fabric.worker.AbortException($e476);
                        }
                        finally {
                            if ($commit474) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e476) {
                                    $commit474 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e476) {
                                    $commit474 = false;
                                    fabric.common.TransactionID $currentTid477 =
                                      $tm478.getCurrentTid();
                                    if ($currentTid477 != null) {
                                        if ($e476.tid.equals($currentTid477) ||
                                              !$e476.tid.isDescendantOf(
                                                           $currentTid477)) {
                                            throw $e476;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit474 && $retry475) {
                                {  }
                                continue $label473;
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
                    fabric.metrics.contracts.Contract proxy$var482 = proxy;
                    fabric.worker.transaction.TransactionManager $tm488 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled491 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff489 = 1;
                    boolean $doBackoff490 = true;
                    boolean $retry485 = true;
                    $label483: for (boolean $commit484 = false; !$commit484; ) {
                        if ($backoffEnabled491) {
                            if ($doBackoff490) {
                                if ($backoff489 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff489);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e486) {
                                            
                                        }
                                    }
                                }
                                if ($backoff489 < 5000) $backoff489 *= 2;
                            }
                            $doBackoff490 = $backoff489 <= 32 || !$doBackoff490;
                        }
                        $commit484 = true;
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
                        catch (final fabric.worker.RetryException $e486) {
                            $commit484 = false;
                            continue $label483;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e486) {
                            $commit484 = false;
                            fabric.common.TransactionID $currentTid487 =
                              $tm488.getCurrentTid();
                            if ($e486.tid.isDescendantOf($currentTid487))
                                continue $label483;
                            if ($currentTid487.parent != null) {
                                $retry485 = false;
                                throw $e486;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e486) {
                            $commit484 = false;
                            if ($tm488.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid487 =
                              $tm488.getCurrentTid();
                            if ($e486.tid.isDescendantOf($currentTid487)) {
                                $retry485 = true;
                            }
                            else if ($currentTid487.parent != null) {
                                $retry485 = false;
                                throw $e486;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e486) {
                            $commit484 = false;
                            if ($tm488.checkForStaleObjects())
                                continue $label483;
                            $retry485 = false;
                            throw new fabric.worker.AbortException($e486);
                        }
                        finally {
                            if ($commit484) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e486) {
                                    $commit484 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e486) {
                                    $commit484 = false;
                                    fabric.common.TransactionID $currentTid487 =
                                      $tm488.getCurrentTid();
                                    if ($currentTid487 != null) {
                                        if ($e486.tid.equals($currentTid487) ||
                                              !$e486.tid.isDescendantOf(
                                                           $currentTid487)) {
                                            throw $e486;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit484 && $retry485) {
                                { proxy = proxy$var482; }
                                continue $label483;
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
        public double get$MIN_EXTENSION_FACTOR();
        
        public double set$MIN_EXTENSION_FACTOR(double val);
        
        public double postInc$MIN_EXTENSION_FACTOR();
        
        public double postDec$MIN_EXTENSION_FACTOR();
        
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
            public double get$MIN_EXTENSION_FACTOR() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$MIN_EXTENSION_FACTOR();
            }
            
            public double set$MIN_EXTENSION_FACTOR(double val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$MIN_EXTENSION_FACTOR(val);
            }
            
            public double postInc$MIN_EXTENSION_FACTOR() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$MIN_EXTENSION_FACTOR();
            }
            
            public double postDec$MIN_EXTENSION_FACTOR() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$MIN_EXTENSION_FACTOR();
            }
            
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
            public double get$MIN_EXTENSION_FACTOR() {
                return this.MIN_EXTENSION_FACTOR;
            }
            
            public double set$MIN_EXTENSION_FACTOR(double val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.MIN_EXTENSION_FACTOR = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public double postInc$MIN_EXTENSION_FACTOR() {
                double tmp = this.get$MIN_EXTENSION_FACTOR();
                this.set$MIN_EXTENSION_FACTOR((double) (tmp + 1));
                return tmp;
            }
            
            public double postDec$MIN_EXTENSION_FACTOR() {
                double tmp = this.get$MIN_EXTENSION_FACTOR();
                this.set$MIN_EXTENSION_FACTOR((double) (tmp - 1));
                return tmp;
            }
            
            public double MIN_EXTENSION_FACTOR;
            
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
                out.writeDouble(this.MIN_EXTENSION_FACTOR);
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
                this.MIN_EXTENSION_FACTOR = in.readDouble();
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
                        fabric.worker.transaction.TransactionManager $tm497 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled500 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff498 = 1;
                        boolean $doBackoff499 = true;
                        boolean $retry494 = true;
                        $label492: for (boolean $commit493 = false; !$commit493;
                                        ) {
                            if ($backoffEnabled500) {
                                if ($doBackoff499) {
                                    if ($backoff498 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff498);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e495) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff498 < 5000) $backoff498 *= 2;
                                }
                                $doBackoff499 = $backoff498 <= 32 ||
                                                  !$doBackoff499;
                            }
                            $commit493 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MIN_EXTENSION_FACTOR((double) 1.05);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$DRIFT_FACTOR((long) 0);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$EXTENSION_WINDOW((long) 1000);
                            }
                            catch (final fabric.worker.RetryException $e495) {
                                $commit493 = false;
                                continue $label492;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e495) {
                                $commit493 = false;
                                fabric.common.TransactionID $currentTid496 =
                                  $tm497.getCurrentTid();
                                if ($e495.tid.isDescendantOf($currentTid496))
                                    continue $label492;
                                if ($currentTid496.parent != null) {
                                    $retry494 = false;
                                    throw $e495;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e495) {
                                $commit493 = false;
                                if ($tm497.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid496 =
                                  $tm497.getCurrentTid();
                                if ($e495.tid.isDescendantOf($currentTid496)) {
                                    $retry494 = true;
                                }
                                else if ($currentTid496.parent != null) {
                                    $retry494 = false;
                                    throw $e495;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e495) {
                                $commit493 = false;
                                if ($tm497.checkForStaleObjects())
                                    continue $label492;
                                $retry494 = false;
                                throw new fabric.worker.AbortException($e495);
                            }
                            finally {
                                if ($commit493) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e495) {
                                        $commit493 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e495) {
                                        $commit493 = false;
                                        fabric.common.TransactionID
                                          $currentTid496 =
                                          $tm497.getCurrentTid();
                                        if ($currentTid496 != null) {
                                            if ($e495.tid.equals(
                                                            $currentTid496) ||
                                                  !$e495.tid.
                                                  isDescendantOf(
                                                    $currentTid496)) {
                                                throw $e495;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit493 && $retry494) {
                                    {  }
                                    continue $label492;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 113, -83, 33, -110, 54,
    -92, 51, -45, -51, -115, 21, 61, 84, -112, -51, -103, 87, 68, 6, 31, 1, 110,
    -14, -78, 68, -90, -93, 90, -74, -69, -48, -33 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525449948000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
