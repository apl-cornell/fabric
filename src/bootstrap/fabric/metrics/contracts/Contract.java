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
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.ImmutableSet;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;
import fabric.common.util.LongSet;
import fabric.common.util.LongIterator;
import fabric.common.util.Pair;

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
    
    public fabric.worker.metrics.ImmutableObserverSet
      handleUpdates(boolean includesObserver, fabric.common.util.LongSet treaties);
    
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
        
        public static final byte[] $classHash = new byte[] { -101, -107, -15,
        50, 78, 34, -4, -122, -26, -11, 8, -5, -16, -67, 37, -68, 13, -61, -74,
        -72, 104, -109, -82, -84, -4, -128, -105, -90, -30, 102, -92, -119 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527110224000L;
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
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean arg1, fabric.common.util.LongSet arg2) {
            return ((fabric.metrics.contracts.Contract) fetch()).handleUpdates(
                                                                   arg1, arg2);
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
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates(
          boolean includesObserver, fabric.common.util.LongSet treaties) {
            fabric.worker.metrics.ImmutableObserverSet affected =
              fabric.worker.metrics.ImmutableObserverSet.emptySet();
            if (includesObserver) {
                if (!stale()) {
                    if (refresh(false))
                        affected = affected.addAll(getObservers());
                }
                else {
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     this.get$currentPolicy(),
                                                     null)) {
                        this.get$currentPolicy().
                          unapply((fabric.metrics.contracts.Contract)
                                    this.$getProxy());
                        this.set$currentPolicy(null);
                        this.set$$associated(null);
                        affected = affected.addAll(getObservers());
                    }
                }
            }
            for (fabric.common.util.LongIterator iter = treaties.iterator();
                 iter.hasNext(); ) {
                long treatyId = iter.next();
                fabric.worker.metrics.treaties.MetricTreaty origTreaty =
                  this.get$$treaties().get(treatyId);
                fabric.common.util.Pair treatyUpdate =
                  origTreaty.update(
                               false,
                               fabric.worker.metrics.StatsMap.emptyStats());
                affected =
                  affected.addAll((fabric.worker.metrics.ImmutableObserverSet)
                                    treatyUpdate.second);
                fabric.worker.metrics.treaties.MetricTreaty newTreaty =
                  (fabric.worker.metrics.treaties.MetricTreaty)
                    treatyUpdate.first;
                if (!newTreaty.equals(origTreaty))
                    this.set$$treaties(this.get$$treaties().add(newTreaty));
            }
            return affected;
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
    
    public static final byte[] $classHash = new byte[] { 109, -51, -56, 68, 10,
    -73, -109, -91, 96, -58, 64, 64, 68, 36, 24, -3, 109, -16, -55, 13, -68,
    101, -16, -100, 123, 43, 45, 53, 92, -55, -8, 17 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527110224000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
