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
    
    public fabric.worker.metrics.ImmutableObserverSet handleUpdates();
    
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
        
        public static final byte[] $classHash = new byte[] { -13, 4, 58, 16,
        -85, 1, -122, 68, -19, -66, -94, -41, 81, 20, -57, 65, 33, -122, 106,
        -97, -99, -104, 121, 40, -49, 65, -25, 98, -64, 32, 71, -22 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1527097530000L;
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
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates() {
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
        
        public fabric.worker.metrics.ImmutableObserverSet handleUpdates() {
            if (!stale()) {
                if (refresh(false)) return getObservers();
                return fabric.worker.metrics.ImmutableObserverSet.emptySet();
            }
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                this.get$currentPolicy().unapply(
                                           (fabric.metrics.contracts.Contract)
                                             this.$getProxy());
                this.set$currentPolicy(null);
                this.set$$associated(null);
                return getObservers();
            }
            return fabric.worker.metrics.ImmutableObserverSet.emptySet();
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
    
    public static final byte[] $classHash = new byte[] { 17, -128, 72, -98, 110,
    -33, 51, -109, -51, 68, 43, 39, -83, 30, 51, 44, -13, 33, 94, -124, -49, 33,
    -30, -118, -44, 114, 64, -120, 74, -9, -73, -39 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527097530000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
