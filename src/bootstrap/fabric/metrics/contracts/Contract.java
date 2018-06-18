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
   * Use this instead of valid() if you're checking something that doesn't need
   * to stay true by the end of the transaction.
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
    public boolean refresh(boolean asyncExtension, fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * Inner implementation of static_updateWithCurPolicy, to be run within a
   * transaction.
   *
   * Try to update the expiration time using the current policy.
   *
   * @return true iff this contract's expiry was retracted.
   */
    public byte updateWithCurPolicy(boolean asyncExtension,
                                    fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * @return a new policy to enforce this treaty.
   */
    public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
      getNewPolicy(fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * Attempt switching to a new policy (only switching if the policy enables the
   * treaty to be valid).
   *
   * @return true iff the updated expiry is a retraction of the preexisting
   * expiry.
   */
    public boolean switchToNewPolicy(
      fabric.metrics.contracts.enforcement.EnforcementPolicy newPolicy,
      boolean asyncExtension, fabric.worker.metrics.StatsMap weakStats);
    
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
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          getNewPolicy(fabric.worker.metrics.StatsMap weakStats);
        
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
            
            public fabric.metrics.contracts.enforcement.EnforcementPolicy
              getNewPolicy(fabric.worker.metrics.StatsMap weakStats) {
                return ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                          new fabric.metrics.contracts.enforcement.
                            WitnessPolicy._Impl(this.$getStore()).
                          $getProxy()).
                  fabric$metrics$contracts$enforcement$WitnessPolicy$(
                    new fabric.metrics.contracts.Contract[] { this.get$target(
                                                                     ) });
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
        
        public static final byte[] $classHash = new byte[] { 59, -38, -48, 111,
        -93, -24, 18, 4, -40, -3, -72, 29, 5, -60, 86, 41, -120, 100, -31, 119,
        -57, -71, -42, -11, -86, 72, -38, -116, 31, 19, 46, 61 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1529349674000L;
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
        
        public static byte static_updateWithCurPolicy(
          fabric.metrics.contracts.Contract arg1, boolean arg2,
          fabric.worker.metrics.StatsMap arg3) {
            return fabric.metrics.contracts.Contract._Impl.
              static_updateWithCurPolicy(arg1, arg2, arg3);
        }
        
        public byte updateWithCurPolicy(boolean arg1,
                                        fabric.worker.metrics.StatsMap arg2) {
            return ((fabric.metrics.contracts.Contract) fetch()).
              updateWithCurPolicy(arg1, arg2);
        }
        
        public static boolean updateToNewPolicy(
          fabric.metrics.contracts.Contract arg1, boolean arg2,
          fabric.worker.metrics.StatsMap arg3) {
            return fabric.metrics.contracts.Contract._Impl.updateToNewPolicy(
                                                             arg1, arg2, arg3);
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          getNewPolicy(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).getNewPolicy(
                                                                   arg1);
        }
        
        public boolean switchToNewPolicy(
          fabric.metrics.contracts.enforcement.EnforcementPolicy arg1,
          boolean arg2, fabric.worker.metrics.StatsMap arg3) {
            return ((fabric.metrics.contracts.Contract) fetch()).
              switchToNewPolicy(arg1, arg2, arg3);
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
                    fabric.worker.transaction.TransactionManager $tm414 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled417 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff415 = 1;
                    boolean $doBackoff416 = true;
                    boolean $retry411 = true;
                    $label409: for (boolean $commit410 = false; !$commit410; ) {
                        if ($backoffEnabled417) {
                            if ($doBackoff416) {
                                if ($backoff415 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff415);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e412) {
                                            
                                        }
                                    }
                                }
                                if ($backoff415 < 5000) $backoff415 *= 2;
                            }
                            $doBackoff416 = $backoff415 <= 32 || !$doBackoff416;
                        }
                        $commit410 = true;
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
                                    fabric.common.TransactionID $currentTid413 =
                                      $tm414.getCurrentTid();
                                    if ($currentTid413 != null) {
                                        if ($e412.tid.equals($currentTid413) ||
                                              !$e412.tid.isDescendantOf(
                                                           $currentTid413)) {
                                            throw $e412;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
   * Use this instead of valid() if you're checking something that doesn't need
   * to stay true by the end of the transaction.
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
        public boolean refresh(boolean asyncExtension,
                               fabric.worker.metrics.StatsMap weakStats) {
            switch (fabric.metrics.contracts.Contract._Impl.
                      static_updateWithCurPolicy(
                        (fabric.metrics.contracts.Contract) this.$getProxy(),
                        asyncExtension, weakStats)) {
                case 0:
                case 1:
                    return !asyncExtension &&
                      fabric.metrics.contracts.Contract._Impl.
                      updateToNewPolicy((fabric.metrics.contracts.Contract)
                                          this.$getProxy(), asyncExtension,
                                        weakStats);
                case 2:
                    return false;
                case 3:
                default:
                    return true;
            }
        }
        
        /**
   * Try to update the expiration time using the current policy.
   *
   * @return true iff this contract's expiry was retracted.
   */
        public static byte static_updateWithCurPolicy(
          fabric.metrics.contracts.Contract tmp, boolean asyncExtension,
          fabric.worker.metrics.StatsMap weakStats) {
            byte result =
              fabric.metrics.contracts.Contract._Static._Proxy.$instance.
              get$NO_POLICY();
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                result = tmp.updateWithCurPolicy(asyncExtension, weakStats);
            }
            else {
                {
                    byte result$var418 = result;
                    fabric.worker.transaction.TransactionManager $tm424 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled427 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff425 = 1;
                    boolean $doBackoff426 = true;
                    boolean $retry421 = true;
                    $label419: for (boolean $commit420 = false; !$commit420; ) {
                        if ($backoffEnabled427) {
                            if ($doBackoff426) {
                                if ($backoff425 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff425);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e422) {
                                            
                                        }
                                    }
                                }
                                if ($backoff425 < 5000) $backoff425 *= 2;
                            }
                            $doBackoff426 = $backoff425 <= 32 || !$doBackoff426;
                        }
                        $commit420 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            result = tmp.updateWithCurPolicy(asyncExtension,
                                                             weakStats);
                        }
                        catch (final fabric.worker.RetryException $e422) {
                            $commit420 = false;
                            continue $label419;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e422) {
                            $commit420 = false;
                            fabric.common.TransactionID $currentTid423 =
                              $tm424.getCurrentTid();
                            if ($e422.tid.isDescendantOf($currentTid423))
                                continue $label419;
                            if ($currentTid423.parent != null) {
                                $retry421 = false;
                                throw $e422;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e422) {
                            $commit420 = false;
                            if ($tm424.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid423 =
                              $tm424.getCurrentTid();
                            if ($e422.tid.isDescendantOf($currentTid423)) {
                                $retry421 = true;
                            }
                            else if ($currentTid423.parent != null) {
                                $retry421 = false;
                                throw $e422;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e422) {
                            $commit420 = false;
                            if ($tm424.checkForStaleObjects())
                                continue $label419;
                            $retry421 = false;
                            throw new fabric.worker.AbortException($e422);
                        }
                        finally {
                            if ($commit420) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e422) {
                                    $commit420 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e422) {
                                    $commit420 = false;
                                    fabric.common.TransactionID $currentTid423 =
                                      $tm424.getCurrentTid();
                                    if ($currentTid423 != null) {
                                        if ($e422.tid.equals($currentTid423) ||
                                              !$e422.tid.isDescendantOf(
                                                           $currentTid423)) {
                                            throw $e422;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit420 && $retry421) {
                                { result = result$var418; }
                                continue $label419;
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        /**
   * Inner implementation of static_updateWithCurPolicy, to be run within a
   * transaction.
   *
   * Try to update the expiration time using the current policy.
   *
   * @return true iff this contract's expiry was retracted.
   */
        public byte updateWithCurPolicy(
          boolean asyncExtension, fabric.worker.metrics.StatsMap weakStats) {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                long updatedExpiry = this.get$currentPolicy().expiry(weakStats);
                if (updatedExpiry >= java.lang.System.currentTimeMillis()) {
                    if (this.update(updatedExpiry, asyncExtension)) {
                        this.get$currentPolicy().
                          apply(
                            (fabric.metrics.contracts.Contract) this.$getProxy());
                        return fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                          get$POLICY_GOOD_RETRACTED();
                    }
                    else {
                        this.get$currentPolicy().
                          apply(
                            (fabric.metrics.contracts.Contract) this.$getProxy());
                        return fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                          get$POLICY_GOOD_EXTENDED();
                    }
                } else {
                    return fabric.metrics.contracts.Contract._Static._Proxy.$instance.get$POLICY_BAD();
                }
            }
            return fabric.metrics.contracts.Contract._Static._Proxy.$instance.
              get$NO_POLICY();
        }
        
        /**
   * Update the treaty to use a new policy (or none if the treaty is no longer
   * valid).
   *
   * @return true iff the updated expiry is a retraction from the preexisting
   * expiry.
   */
        public static boolean updateToNewPolicy(
          fabric.metrics.contracts.Contract tmp, boolean asyncExtension,
          fabric.worker.metrics.StatsMap weakStats) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            tm.markCoordination();
            if (tm.inTxn()) {
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  newPolicy = tmp.getNewPolicy(weakStats);
                newPolicy.activate(weakStats);
                return tmp.switchToNewPolicy(newPolicy, asyncExtension,
                                             weakStats);
            }
            else {
                fabric.metrics.contracts.enforcement.EnforcementPolicy
                  newPolicy = null;
                {
                    fabric.metrics.contracts.enforcement.EnforcementPolicy
                      newPolicy$var428 = newPolicy;
                    fabric.worker.transaction.TransactionManager $tm434 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled437 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff435 = 1;
                    boolean $doBackoff436 = true;
                    boolean $retry431 = true;
                    $label429: for (boolean $commit430 = false; !$commit430; ) {
                        if ($backoffEnabled437) {
                            if ($doBackoff436) {
                                if ($backoff435 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff435);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e432) {
                                            
                                        }
                                    }
                                }
                                if ($backoff435 < 5000) $backoff435 *= 2;
                            }
                            $doBackoff436 = $backoff435 <= 32 || !$doBackoff436;
                        }
                        $commit430 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { newPolicy = tmp.getNewPolicy(weakStats); }
                        catch (final fabric.worker.RetryException $e432) {
                            $commit430 = false;
                            continue $label429;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e432) {
                            $commit430 = false;
                            fabric.common.TransactionID $currentTid433 =
                              $tm434.getCurrentTid();
                            if ($e432.tid.isDescendantOf($currentTid433))
                                continue $label429;
                            if ($currentTid433.parent != null) {
                                $retry431 = false;
                                throw $e432;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e432) {
                            $commit430 = false;
                            if ($tm434.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid433 =
                              $tm434.getCurrentTid();
                            if ($e432.tid.isDescendantOf($currentTid433)) {
                                $retry431 = true;
                            }
                            else if ($currentTid433.parent != null) {
                                $retry431 = false;
                                throw $e432;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e432) {
                            $commit430 = false;
                            if ($tm434.checkForStaleObjects())
                                continue $label429;
                            $retry431 = false;
                            throw new fabric.worker.AbortException($e432);
                        }
                        finally {
                            if ($commit430) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e432) {
                                    $commit430 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e432) {
                                    $commit430 = false;
                                    fabric.common.TransactionID $currentTid433 =
                                      $tm434.getCurrentTid();
                                    if ($currentTid433 != null) {
                                        if ($e432.tid.equals($currentTid433) ||
                                              !$e432.tid.isDescendantOf(
                                                           $currentTid433)) {
                                            throw $e432;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit430 && $retry431) {
                                { newPolicy = newPolicy$var428; }
                                continue $label429;
                            }
                        }
                    }
                }
                newPolicy.activate(weakStats);
                boolean result = false;
                {
                    boolean result$var438 = result;
                    fabric.metrics.contracts.enforcement.EnforcementPolicy
                      newPolicy$var439 = newPolicy;
                    fabric.worker.transaction.TransactionManager $tm445 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled448 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff446 = 1;
                    boolean $doBackoff447 = true;
                    boolean $retry442 = true;
                    $label440: for (boolean $commit441 = false; !$commit441; ) {
                        if ($backoffEnabled448) {
                            if ($doBackoff447) {
                                if ($backoff446 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff446);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e443) {
                                            
                                        }
                                    }
                                }
                                if ($backoff446 < 5000) $backoff446 *= 2;
                            }
                            $doBackoff447 = $backoff446 <= 32 || !$doBackoff447;
                        }
                        $commit441 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            result = tmp.switchToNewPolicy(newPolicy,
                                                           asyncExtension,
                                                           weakStats);
                        }
                        catch (final fabric.worker.RetryException $e443) {
                            $commit441 = false;
                            continue $label440;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e443) {
                            $commit441 = false;
                            fabric.common.TransactionID $currentTid444 =
                              $tm445.getCurrentTid();
                            if ($e443.tid.isDescendantOf($currentTid444))
                                continue $label440;
                            if ($currentTid444.parent != null) {
                                $retry442 = false;
                                throw $e443;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e443) {
                            $commit441 = false;
                            if ($tm445.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid444 =
                              $tm445.getCurrentTid();
                            if ($e443.tid.isDescendantOf($currentTid444)) {
                                $retry442 = true;
                            }
                            else if ($currentTid444.parent != null) {
                                $retry442 = false;
                                throw $e443;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e443) {
                            $commit441 = false;
                            if ($tm445.checkForStaleObjects())
                                continue $label440;
                            $retry442 = false;
                            throw new fabric.worker.AbortException($e443);
                        }
                        finally {
                            if ($commit441) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e443) {
                                    $commit441 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e443) {
                                    $commit441 = false;
                                    fabric.common.TransactionID $currentTid444 =
                                      $tm445.getCurrentTid();
                                    if ($currentTid444 != null) {
                                        if ($e443.tid.equals($currentTid444) ||
                                              !$e443.tid.isDescendantOf(
                                                           $currentTid444)) {
                                            throw $e443;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit441 && $retry442) {
                                {
                                    result = result$var438;
                                    newPolicy = newPolicy$var439;
                                }
                                continue $label440;
                            }
                        }
                    }
                }
                return result;
            }
        }
        
        /**
   * @return a new policy to enforce this treaty.
   */
        public abstract fabric.metrics.contracts.enforcement.EnforcementPolicy
          getNewPolicy(fabric.worker.metrics.StatsMap weakStats);
        
        /**
   * Attempt switching to a new policy (only switching if the policy enables the
   * treaty to be valid).
   *
   * @return true iff the updated expiry is a retraction of the preexisting
   * expiry.
   */
        public boolean switchToNewPolicy(
          fabric.metrics.contracts.enforcement.EnforcementPolicy newPolicy,
          boolean asyncExtension, fabric.worker.metrics.StatsMap weakStats) {
            fabric.metrics.contracts.enforcement.EnforcementPolicy oldPolicy =
              this.get$currentPolicy();
            long newExpiry = newPolicy.expiry(weakStats);
            if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null))
                oldPolicy.unapply((fabric.metrics.contracts.Contract)
                                    this.$getProxy());
            boolean result = this.update(newExpiry, asyncExtension);
            if (newExpiry >= java.lang.System.currentTimeMillis()) {
                this.set$currentPolicy(newPolicy);
                this.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(this.get$currentPolicy()));
                this.get$currentPolicy().apply(
                                           (fabric.metrics.contracts.Contract)
                                             this.$getProxy());
            } else {
                newPolicy.unapply((fabric.metrics.contracts.Contract)
                                    this.$getProxy());
                this.set$currentPolicy(null);
                this.set$$associated(null);
            }
            return result;
        }
        
        public boolean handleUpdates() {
            if (!stale()) { return refresh(false); }
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
                    fabric.worker.transaction.TransactionManager $tm454 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled457 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff455 = 1;
                    boolean $doBackoff456 = true;
                    boolean $retry451 = true;
                    $label449: for (boolean $commit450 = false; !$commit450; ) {
                        if ($backoffEnabled457) {
                            if ($doBackoff456) {
                                if ($backoff455 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff455);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e452) {
                                            
                                        }
                                    }
                                }
                                if ($backoff455 < 5000) $backoff455 *= 2;
                            }
                            $doBackoff456 = $backoff455 <= 32 || !$doBackoff456;
                        }
                        $commit450 = true;
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
                        catch (final fabric.worker.RetryException $e452) {
                            $commit450 = false;
                            continue $label449;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e452) {
                            $commit450 = false;
                            fabric.common.TransactionID $currentTid453 =
                              $tm454.getCurrentTid();
                            if ($e452.tid.isDescendantOf($currentTid453))
                                continue $label449;
                            if ($currentTid453.parent != null) {
                                $retry451 = false;
                                throw $e452;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e452) {
                            $commit450 = false;
                            if ($tm454.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid453 =
                              $tm454.getCurrentTid();
                            if ($e452.tid.isDescendantOf($currentTid453)) {
                                $retry451 = true;
                            }
                            else if ($currentTid453.parent != null) {
                                $retry451 = false;
                                throw $e452;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e452) {
                            $commit450 = false;
                            if ($tm454.checkForStaleObjects())
                                continue $label449;
                            $retry451 = false;
                            throw new fabric.worker.AbortException($e452);
                        }
                        finally {
                            if ($commit450) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e452) {
                                    $commit450 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e452) {
                                    $commit450 = false;
                                    fabric.common.TransactionID $currentTid453 =
                                      $tm454.getCurrentTid();
                                    if ($currentTid453 != null) {
                                        if ($e452.tid.equals($currentTid453) ||
                                              !$e452.tid.isDescendantOf(
                                                           $currentTid453)) {
                                            throw $e452;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit450 && $retry451) {
                                {  }
                                continue $label449;
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
                    fabric.metrics.contracts.Contract proxy$var458 = proxy;
                    fabric.worker.transaction.TransactionManager $tm464 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled467 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff465 = 1;
                    boolean $doBackoff466 = true;
                    boolean $retry461 = true;
                    $label459: for (boolean $commit460 = false; !$commit460; ) {
                        if ($backoffEnabled467) {
                            if ($doBackoff466) {
                                if ($backoff465 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff465);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e462) {
                                            
                                        }
                                    }
                                }
                                if ($backoff465 < 5000) $backoff465 *= 2;
                            }
                            $doBackoff466 = $backoff465 <= 32 || !$doBackoff466;
                        }
                        $commit460 = true;
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
                        catch (final fabric.worker.RetryException $e462) {
                            $commit460 = false;
                            continue $label459;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e462) {
                            $commit460 = false;
                            fabric.common.TransactionID $currentTid463 =
                              $tm464.getCurrentTid();
                            if ($e462.tid.isDescendantOf($currentTid463))
                                continue $label459;
                            if ($currentTid463.parent != null) {
                                $retry461 = false;
                                throw $e462;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e462) {
                            $commit460 = false;
                            if ($tm464.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid463 =
                              $tm464.getCurrentTid();
                            if ($e462.tid.isDescendantOf($currentTid463)) {
                                $retry461 = true;
                            }
                            else if ($currentTid463.parent != null) {
                                $retry461 = false;
                                throw $e462;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e462) {
                            $commit460 = false;
                            if ($tm464.checkForStaleObjects())
                                continue $label459;
                            $retry461 = false;
                            throw new fabric.worker.AbortException($e462);
                        }
                        finally {
                            if ($commit460) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e462) {
                                    $commit460 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e462) {
                                    $commit460 = false;
                                    fabric.common.TransactionID $currentTid463 =
                                      $tm464.getCurrentTid();
                                    if ($currentTid463 != null) {
                                        if ($e462.tid.equals($currentTid463) ||
                                              !$e462.tid.isDescendantOf(
                                                           $currentTid463)) {
                                            throw $e462;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit460 && $retry461) {
                                { proxy = proxy$var458; }
                                continue $label459;
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
        
        public byte get$NO_POLICY();
        
        public byte set$NO_POLICY(byte val);
        
        public byte postInc$NO_POLICY();
        
        public byte postDec$NO_POLICY();
        
        public byte get$POLICY_BAD();
        
        public byte set$POLICY_BAD(byte val);
        
        public byte postInc$POLICY_BAD();
        
        public byte postDec$POLICY_BAD();
        
        public byte get$POLICY_GOOD_EXTENDED();
        
        public byte set$POLICY_GOOD_EXTENDED(byte val);
        
        public byte postInc$POLICY_GOOD_EXTENDED();
        
        public byte postDec$POLICY_GOOD_EXTENDED();
        
        public byte get$POLICY_GOOD_RETRACTED();
        
        public byte set$POLICY_GOOD_RETRACTED(byte val);
        
        public byte postInc$POLICY_GOOD_RETRACTED();
        
        public byte postDec$POLICY_GOOD_RETRACTED();
        
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
            
            public byte get$NO_POLICY() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$NO_POLICY();
            }
            
            public byte set$NO_POLICY(byte val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$NO_POLICY(val);
            }
            
            public byte postInc$NO_POLICY() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$NO_POLICY();
            }
            
            public byte postDec$NO_POLICY() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$NO_POLICY();
            }
            
            public byte get$POLICY_BAD() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$POLICY_BAD();
            }
            
            public byte set$POLICY_BAD(byte val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$POLICY_BAD(val);
            }
            
            public byte postInc$POLICY_BAD() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$POLICY_BAD();
            }
            
            public byte postDec$POLICY_BAD() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$POLICY_BAD();
            }
            
            public byte get$POLICY_GOOD_EXTENDED() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$POLICY_GOOD_EXTENDED();
            }
            
            public byte set$POLICY_GOOD_EXTENDED(byte val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$POLICY_GOOD_EXTENDED(val);
            }
            
            public byte postInc$POLICY_GOOD_EXTENDED() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$POLICY_GOOD_EXTENDED();
            }
            
            public byte postDec$POLICY_GOOD_EXTENDED() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$POLICY_GOOD_EXTENDED();
            }
            
            public byte get$POLICY_GOOD_RETRACTED() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$POLICY_GOOD_RETRACTED();
            }
            
            public byte set$POLICY_GOOD_RETRACTED(byte val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$POLICY_GOOD_RETRACTED(val);
            }
            
            public byte postInc$POLICY_GOOD_RETRACTED() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$POLICY_GOOD_RETRACTED();
            }
            
            public byte postDec$POLICY_GOOD_RETRACTED() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$POLICY_GOOD_RETRACTED();
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
            
            public byte get$NO_POLICY() { return this.NO_POLICY; }
            
            public byte set$NO_POLICY(byte val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.NO_POLICY = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public byte postInc$NO_POLICY() {
                byte tmp = this.get$NO_POLICY();
                this.set$NO_POLICY((byte) (tmp + 1));
                return tmp;
            }
            
            public byte postDec$NO_POLICY() {
                byte tmp = this.get$NO_POLICY();
                this.set$NO_POLICY((byte) (tmp - 1));
                return tmp;
            }
            
            protected byte NO_POLICY;
            
            public byte get$POLICY_BAD() { return this.POLICY_BAD; }
            
            public byte set$POLICY_BAD(byte val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.POLICY_BAD = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public byte postInc$POLICY_BAD() {
                byte tmp = this.get$POLICY_BAD();
                this.set$POLICY_BAD((byte) (tmp + 1));
                return tmp;
            }
            
            public byte postDec$POLICY_BAD() {
                byte tmp = this.get$POLICY_BAD();
                this.set$POLICY_BAD((byte) (tmp - 1));
                return tmp;
            }
            
            protected byte POLICY_BAD;
            
            public byte get$POLICY_GOOD_EXTENDED() {
                return this.POLICY_GOOD_EXTENDED;
            }
            
            public byte set$POLICY_GOOD_EXTENDED(byte val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.POLICY_GOOD_EXTENDED = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public byte postInc$POLICY_GOOD_EXTENDED() {
                byte tmp = this.get$POLICY_GOOD_EXTENDED();
                this.set$POLICY_GOOD_EXTENDED((byte) (tmp + 1));
                return tmp;
            }
            
            public byte postDec$POLICY_GOOD_EXTENDED() {
                byte tmp = this.get$POLICY_GOOD_EXTENDED();
                this.set$POLICY_GOOD_EXTENDED((byte) (tmp - 1));
                return tmp;
            }
            
            protected byte POLICY_GOOD_EXTENDED;
            
            public byte get$POLICY_GOOD_RETRACTED() {
                return this.POLICY_GOOD_RETRACTED;
            }
            
            public byte set$POLICY_GOOD_RETRACTED(byte val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.POLICY_GOOD_RETRACTED = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public byte postInc$POLICY_GOOD_RETRACTED() {
                byte tmp = this.get$POLICY_GOOD_RETRACTED();
                this.set$POLICY_GOOD_RETRACTED((byte) (tmp + 1));
                return tmp;
            }
            
            public byte postDec$POLICY_GOOD_RETRACTED() {
                byte tmp = this.get$POLICY_GOOD_RETRACTED();
                this.set$POLICY_GOOD_RETRACTED((byte) (tmp - 1));
                return tmp;
            }
            
            protected byte POLICY_GOOD_RETRACTED;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeLong(this.DRIFT_FACTOR);
                out.writeLong(this.EXTENSION_WINDOW);
                out.writeByte(this.NO_POLICY);
                out.writeByte(this.POLICY_BAD);
                out.writeByte(this.POLICY_GOOD_EXTENDED);
                out.writeByte(this.POLICY_GOOD_RETRACTED);
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
                this.NO_POLICY = in.readByte();
                this.POLICY_BAD = in.readByte();
                this.POLICY_GOOD_EXTENDED = in.readByte();
                this.POLICY_GOOD_RETRACTED = in.readByte();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.Contract._Static._Proxy(
                         this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm473 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled476 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff474 = 1;
                        boolean $doBackoff475 = true;
                        boolean $retry470 = true;
                        $label468: for (boolean $commit469 = false; !$commit469;
                                        ) {
                            if ($backoffEnabled476) {
                                if ($doBackoff475) {
                                    if ($backoff474 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff474);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e471) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff474 < 5000) $backoff474 *= 2;
                                }
                                $doBackoff475 = $backoff474 <= 32 ||
                                                  !$doBackoff475;
                            }
                            $commit469 = true;
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
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$NO_POLICY((byte) 0);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$POLICY_BAD((byte) 1);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$POLICY_GOOD_EXTENDED((byte) 2);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$POLICY_GOOD_RETRACTED((byte) 3);
                            }
                            catch (final fabric.worker.RetryException $e471) {
                                $commit469 = false;
                                continue $label468;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e471) {
                                $commit469 = false;
                                fabric.common.TransactionID $currentTid472 =
                                  $tm473.getCurrentTid();
                                if ($e471.tid.isDescendantOf($currentTid472))
                                    continue $label468;
                                if ($currentTid472.parent != null) {
                                    $retry470 = false;
                                    throw $e471;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e471) {
                                $commit469 = false;
                                if ($tm473.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid472 =
                                  $tm473.getCurrentTid();
                                if ($e471.tid.isDescendantOf($currentTid472)) {
                                    $retry470 = true;
                                }
                                else if ($currentTid472.parent != null) {
                                    $retry470 = false;
                                    throw $e471;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e471) {
                                $commit469 = false;
                                if ($tm473.checkForStaleObjects())
                                    continue $label468;
                                $retry470 = false;
                                throw new fabric.worker.AbortException($e471);
                            }
                            finally {
                                if ($commit469) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e471) {
                                        $commit469 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e471) {
                                        $commit469 = false;
                                        fabric.common.TransactionID
                                          $currentTid472 =
                                          $tm473.getCurrentTid();
                                        if ($currentTid472 != null) {
                                            if ($e471.tid.equals(
                                                            $currentTid472) ||
                                                  !$e471.tid.
                                                  isDescendantOf(
                                                    $currentTid472)) {
                                                throw $e471;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit469 && $retry470) {
                                    {  }
                                    continue $label468;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -25, -105, -82, 65, 93,
    72, -58, -121, -84, 50, 77, 4, -36, -67, 44, 35, 0, 74, -108, 95, -52, -94,
    85, -47, 4, -60, 48, 113, 34, 126, 126, -57 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1529349674000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
