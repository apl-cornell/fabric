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
    public ProxyContract getProxyContract(final fabric.worker.Store proxyStore);
    
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
        
        public ProxyContract getProxyContract(
          final fabric.worker.Store proxyStore);
        
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
                this.set$target(target);
                fabric$metrics$contracts$Contract$();
                this.
                  set$currentPolicy(
                    ((fabric.metrics.contracts.enforcement.WitnessPolicy)
                       new fabric.metrics.contracts.enforcement.WitnessPolicy.
                         _Impl(this.$getStore()).
                       $getProxy()).
                        fabric$metrics$contracts$enforcement$WitnessPolicy$(
                          new fabric.metrics.contracts.Contract[] { target }));
                return (ProxyContract) this.$getProxy();
            }
            
            public boolean refresh(boolean asyncExtension) {
                long currentTime = java.lang.System.currentTimeMillis();
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 this.get$currentPolicy(),
                                                 null)) {
                    long curExpiry = this.get$currentPolicy().expiry();
                    if (curExpiry < currentTime) {
                        this.get$currentPolicy().unapply((ProxyContract)
                                                           this.$getProxy());
                        this.set$currentPolicy(null);
                    } else {
                        this.get$currentPolicy().apply((ProxyContract)
                                                         this.$getProxy());
                    }
                    return update(curExpiry, asyncExtension);
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
            
            public ProxyContract getProxyContract(
              final fabric.worker.Store proxyStore) {
                return fabric.metrics.contracts.Contract.ProxyContract._Impl.
                  static_getProxyContract((ProxyContract) this.$getProxy(),
                                          proxyStore);
            }
            
            private static ProxyContract static_getProxyContract(
              ProxyContract tmp, final fabric.worker.Store proxyStore) {
                ProxyContract proxy = null;
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    proxy =
                      ((ProxyContract)
                         new fabric.metrics.contracts.Contract.ProxyContract.
                           _Impl(proxyStore).
                         $getProxy()).
                        fabric$metrics$contracts$Contract$ProxyContract$(
                          tmp.get$target());
                }
                else {
                    {
                        fabric.metrics.contracts.Contract.ProxyContract
                          proxy$var18 = proxy;
                        fabric.worker.transaction.TransactionManager $tm24 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled27 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff25 = 1;
                        boolean $doBackoff26 = true;
                        boolean $retry21 = true;
                        $label19: for (boolean $commit20 = false; !$commit20;
                                       ) {
                            if ($backoffEnabled27) {
                                if ($doBackoff26) {
                                    if ($backoff25 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff25);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e22) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff25 < 5000) $backoff25 *= 2;
                                }
                                $doBackoff26 = $backoff25 <= 32 ||
                                                 !$doBackoff26;
                            }
                            $commit20 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                proxy =
                                  ((ProxyContract)
                                     new fabric.metrics.contracts.Contract.
                                       ProxyContract._Impl(proxyStore).
                                     $getProxy()).
                                    fabric$metrics$contracts$Contract$ProxyContract$(
                                      tmp.get$target());
                            }
                            catch (final fabric.worker.RetryException $e22) {
                                $commit20 = false;
                                continue $label19;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e22) {
                                $commit20 = false;
                                fabric.common.TransactionID $currentTid23 =
                                  $tm24.getCurrentTid();
                                if ($e22.tid.isDescendantOf($currentTid23))
                                    continue $label19;
                                if ($currentTid23.parent != null) {
                                    $retry21 = false;
                                    throw $e22;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e22) {
                                $commit20 = false;
                                if ($tm24.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid23 =
                                  $tm24.getCurrentTid();
                                if ($e22.tid.isDescendantOf($currentTid23)) {
                                    $retry21 = true;
                                }
                                else if ($currentTid23.parent != null) {
                                    $retry21 = false;
                                    throw $e22;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e22) {
                                $commit20 = false;
                                if ($tm24.checkForStaleObjects())
                                    continue $label19;
                                $retry21 = false;
                                throw new fabric.worker.AbortException($e22);
                            }
                            finally {
                                if ($commit20) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e22) {
                                        $commit20 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e22) {
                                        $commit20 = false;
                                        fabric.common.TransactionID
                                          $currentTid23 = $tm24.getCurrentTid();
                                        if ($currentTid23 != null) {
                                            if ($e22.tid.equals(
                                                           $currentTid23) ||
                                                  !$e22.tid.isDescendantOf(
                                                              $currentTid23)) {
                                                throw $e22;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit20 && $retry21) {
                                    { proxy = proxy$var18; }
                                    continue $label19;
                                }
                            }
                        }
                    }
                }
                proxy.refresh(false);
                return proxy;
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
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
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
                
                public _Impl(fabric.worker.Store store, long onum, int version,
                             long expiry, fabric.worker.Store labelStore,
                             long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.io.IOException,
                    java.lang.ClassNotFoundException {
                    super(store, onum, version, expiry, labelStore, labelOnum,
                          accessPolicyStore, accessPolicyOnum, in, refTypes,
                          intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.metrics.contracts.Contract.ProxyContract.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 49, 98, -112, -20,
        -121, 17, 75, 105, 9, -60, -104, 36, -22, -63, -125, 57, -84, 20, -59,
        40, 15, -32, -70, 104, -36, 25, -29, 43, -29, -94, 75, 110 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1524613293000L;
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
        
        public static final java.lang.Class[] $paramTypes0 = null;
        
        public void activate$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                activate();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "activate",
                                                  $paramTypes0, null);
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
        
        public static final java.lang.Class[] $paramTypes1 = { boolean.class };
        
        public void refresh$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1, boolean arg2) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                refresh(arg2);
            else
                try {
                    $remoteWorker.issueRemoteCall(
                                    this, "refresh", $paramTypes1,
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
        
        public static final java.lang.Class[] $paramTypes2 = null;
        
        public void attemptExtension$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                attemptExtension();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "attemptExtension",
                                                  $paramTypes2, null);
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
        
        public fabric.metrics.contracts.Contract.ProxyContract getProxyContract(
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
        public void activate() {
            fabric.metrics.contracts.Contract._Impl.
              static_activate((fabric.metrics.contracts.Contract)
                                this.$getProxy());
        }
        
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
                    fabric.worker.transaction.TransactionManager $tm33 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled36 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff34 = 1;
                    boolean $doBackoff35 = true;
                    boolean $retry30 = true;
                    $label28: for (boolean $commit29 = false; !$commit29; ) {
                        if ($backoffEnabled36) {
                            if ($doBackoff35) {
                                if ($backoff34 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff34);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e31) {
                                            
                                        }
                                    }
                                }
                                if ($backoff34 < 5000) $backoff34 *= 2;
                            }
                            $doBackoff35 = $backoff34 <= 32 || !$doBackoff35;
                        }
                        $commit29 = true;
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
                        catch (final fabric.worker.RetryException $e31) {
                            $commit29 = false;
                            continue $label28;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e31) {
                            $commit29 = false;
                            fabric.common.TransactionID $currentTid32 =
                              $tm33.getCurrentTid();
                            if ($e31.tid.isDescendantOf($currentTid32))
                                continue $label28;
                            if ($currentTid32.parent != null) {
                                $retry30 = false;
                                throw $e31;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e31) {
                            $commit29 = false;
                            if ($tm33.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid32 =
                              $tm33.getCurrentTid();
                            if ($e31.tid.isDescendantOf($currentTid32)) {
                                $retry30 = true;
                            }
                            else if ($currentTid32.parent != null) {
                                $retry30 = false;
                                throw $e31;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e31) {
                            $commit29 = false;
                            if ($tm33.checkForStaleObjects()) continue $label28;
                            $retry30 = false;
                            throw new fabric.worker.AbortException($e31);
                        }
                        finally {
                            if ($commit29) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e31) {
                                    $commit29 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e31) {
                                    $commit29 = false;
                                    fabric.common.TransactionID $currentTid32 =
                                      $tm33.getCurrentTid();
                                    if ($currentTid32 != null) {
                                        if ($e31.tid.equals($currentTid32) ||
                                              !$e31.tid.isDescendantOf(
                                                          $currentTid32)) {
                                            throw $e31;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit29 && $retry30) {
                                {  }
                                continue $label28;
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
                    }
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm42 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled45 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff43 = 1;
                    boolean $doBackoff44 = true;
                    boolean $retry39 = true;
                    $label37: for (boolean $commit38 = false; !$commit38; ) {
                        if ($backoffEnabled45) {
                            if ($doBackoff44) {
                                if ($backoff43 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff43);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e40) {
                                            
                                        }
                                    }
                                }
                                if ($backoff43 < 5000) $backoff43 *= 2;
                            }
                            $doBackoff44 = $backoff43 <= 32 || !$doBackoff44;
                        }
                        $commit38 = true;
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
                                }
                            }
                        }
                        catch (final fabric.worker.RetryException $e40) {
                            $commit38 = false;
                            continue $label37;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e40) {
                            $commit38 = false;
                            fabric.common.TransactionID $currentTid41 =
                              $tm42.getCurrentTid();
                            if ($e40.tid.isDescendantOf($currentTid41))
                                continue $label37;
                            if ($currentTid41.parent != null) {
                                $retry39 = false;
                                throw $e40;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e40) {
                            $commit38 = false;
                            if ($tm42.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid41 =
                              $tm42.getCurrentTid();
                            if ($e40.tid.isDescendantOf($currentTid41)) {
                                $retry39 = true;
                            }
                            else if ($currentTid41.parent != null) {
                                $retry39 = false;
                                throw $e40;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e40) {
                            $commit38 = false;
                            if ($tm42.checkForStaleObjects()) continue $label37;
                            $retry39 = false;
                            throw new fabric.worker.AbortException($e40);
                        }
                        finally {
                            if ($commit38) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e40) {
                                    $commit38 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e40) {
                                    $commit38 = false;
                                    fabric.common.TransactionID $currentTid41 =
                                      $tm42.getCurrentTid();
                                    if ($currentTid41 != null) {
                                        if ($e40.tid.equals($currentTid41) ||
                                              !$e40.tid.isDescendantOf(
                                                          $currentTid41)) {
                                            throw $e40;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit38 && $retry39) {
                                {  }
                                continue $label37;
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
                if (tmp.valid()) { tmp.refresh(true); }
                if (!fabric.lang.Object._Proxy.idEquals(tmp.get$currentPolicy(),
                                                        null)) {
                    tmp.get$currentPolicy().unapply(tmp);
                    tmp.set$currentPolicy(null);
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm51 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled54 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff52 = 1;
                    boolean $doBackoff53 = true;
                    boolean $retry48 = true;
                    $label46: for (boolean $commit47 = false; !$commit47; ) {
                        if ($backoffEnabled54) {
                            if ($doBackoff53) {
                                if ($backoff52 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff52);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e49) {
                                            
                                        }
                                    }
                                }
                                if ($backoff52 < 5000) $backoff52 *= 2;
                            }
                            $doBackoff53 = $backoff52 <= 32 || !$doBackoff53;
                        }
                        $commit47 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.valid()) { tmp.refresh(true); }
                            if (!fabric.lang.Object._Proxy.
                                  idEquals(tmp.get$currentPolicy(), null)) {
                                tmp.get$currentPolicy().unapply(tmp);
                                tmp.set$currentPolicy(null);
                            }
                        }
                        catch (final fabric.worker.RetryException $e49) {
                            $commit47 = false;
                            continue $label46;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e49) {
                            $commit47 = false;
                            fabric.common.TransactionID $currentTid50 =
                              $tm51.getCurrentTid();
                            if ($e49.tid.isDescendantOf($currentTid50))
                                continue $label46;
                            if ($currentTid50.parent != null) {
                                $retry48 = false;
                                throw $e49;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e49) {
                            $commit47 = false;
                            if ($tm51.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid50 =
                              $tm51.getCurrentTid();
                            if ($e49.tid.isDescendantOf($currentTid50)) {
                                $retry48 = true;
                            }
                            else if ($currentTid50.parent != null) {
                                $retry48 = false;
                                throw $e49;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e49) {
                            $commit47 = false;
                            if ($tm51.checkForStaleObjects()) continue $label46;
                            $retry48 = false;
                            throw new fabric.worker.AbortException($e49);
                        }
                        finally {
                            if ($commit47) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e49) {
                                    $commit47 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e49) {
                                    $commit47 = false;
                                    fabric.common.TransactionID $currentTid50 =
                                      $tm51.getCurrentTid();
                                    if ($currentTid50 != null) {
                                        if ($e49.tid.equals($currentTid50) ||
                                              !$e49.tid.isDescendantOf(
                                                          $currentTid50)) {
                                            throw $e49;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit47 && $retry48) {
                                {  }
                                continue $label46;
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
        public ProxyContract getProxyContract(
          final fabric.worker.Store proxyStore) {
            return fabric.metrics.contracts.Contract._Impl.
              static_getProxyContract((fabric.metrics.contracts.Contract)
                                        this.$getProxy(), proxyStore);
        }
        
        private static ProxyContract static_getProxyContract(
          fabric.metrics.contracts.Contract tmp,
          final fabric.worker.Store proxyStore) {
            ProxyContract proxy = null;
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                proxy =
                  ((ProxyContract)
                     new fabric.metrics.contracts.Contract.ProxyContract._Impl(
                       proxyStore).$getProxy()).
                    fabric$metrics$contracts$Contract$ProxyContract$(tmp);
            }
            else {
                {
                    fabric.metrics.contracts.Contract.ProxyContract
                      proxy$var55 = proxy;
                    fabric.worker.transaction.TransactionManager $tm61 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled64 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff62 = 1;
                    boolean $doBackoff63 = true;
                    boolean $retry58 = true;
                    $label56: for (boolean $commit57 = false; !$commit57; ) {
                        if ($backoffEnabled64) {
                            if ($doBackoff63) {
                                if ($backoff62 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff62);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e59) {
                                            
                                        }
                                    }
                                }
                                if ($backoff62 < 5000) $backoff62 *= 2;
                            }
                            $doBackoff63 = $backoff62 <= 32 || !$doBackoff63;
                        }
                        $commit57 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            proxy =
                              ((ProxyContract)
                                 new fabric.metrics.contracts.Contract.
                                   ProxyContract._Impl(proxyStore).
                                 $getProxy()).
                                fabric$metrics$contracts$Contract$ProxyContract$(
                                  tmp);
                        }
                        catch (final fabric.worker.RetryException $e59) {
                            $commit57 = false;
                            continue $label56;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e59) {
                            $commit57 = false;
                            fabric.common.TransactionID $currentTid60 =
                              $tm61.getCurrentTid();
                            if ($e59.tid.isDescendantOf($currentTid60))
                                continue $label56;
                            if ($currentTid60.parent != null) {
                                $retry58 = false;
                                throw $e59;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e59) {
                            $commit57 = false;
                            if ($tm61.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid60 =
                              $tm61.getCurrentTid();
                            if ($e59.tid.isDescendantOf($currentTid60)) {
                                $retry58 = true;
                            }
                            else if ($currentTid60.parent != null) {
                                $retry58 = false;
                                throw $e59;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e59) {
                            $commit57 = false;
                            if ($tm61.checkForStaleObjects()) continue $label56;
                            $retry58 = false;
                            throw new fabric.worker.AbortException($e59);
                        }
                        finally {
                            if ($commit57) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e59) {
                                    $commit57 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e59) {
                                    $commit57 = false;
                                    fabric.common.TransactionID $currentTid60 =
                                      $tm61.getCurrentTid();
                                    if ($currentTid60 != null) {
                                        if ($e59.tid.equals($currentTid60) ||
                                              !$e59.tid.isDescendantOf(
                                                          $currentTid60)) {
                                            throw $e59;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit57 && $retry58) {
                                { proxy = proxy$var55; }
                                continue $label56;
                            }
                        }
                    }
                }
            }
            proxy.refresh(false);
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
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
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
                        fabric.worker.transaction.TransactionManager $tm70 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled73 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff71 = 1;
                        boolean $doBackoff72 = true;
                        boolean $retry67 = true;
                        $label65: for (boolean $commit66 = false; !$commit66;
                                       ) {
                            if ($backoffEnabled73) {
                                if ($doBackoff72) {
                                    if ($backoff71 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff71);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e68) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff71 < 5000) $backoff71 *= 2;
                                }
                                $doBackoff72 = $backoff71 <= 32 ||
                                                 !$doBackoff72;
                            }
                            $commit66 = true;
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
                                  set$DRIFT_FACTOR((long) 100);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$EXTENSION_WINDOW((long) 1000);
                            }
                            catch (final fabric.worker.RetryException $e68) {
                                $commit66 = false;
                                continue $label65;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e68) {
                                $commit66 = false;
                                fabric.common.TransactionID $currentTid69 =
                                  $tm70.getCurrentTid();
                                if ($e68.tid.isDescendantOf($currentTid69))
                                    continue $label65;
                                if ($currentTid69.parent != null) {
                                    $retry67 = false;
                                    throw $e68;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e68) {
                                $commit66 = false;
                                if ($tm70.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid69 =
                                  $tm70.getCurrentTid();
                                if ($e68.tid.isDescendantOf($currentTid69)) {
                                    $retry67 = true;
                                }
                                else if ($currentTid69.parent != null) {
                                    $retry67 = false;
                                    throw $e68;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e68) {
                                $commit66 = false;
                                if ($tm70.checkForStaleObjects())
                                    continue $label65;
                                $retry67 = false;
                                throw new fabric.worker.AbortException($e68);
                            }
                            finally {
                                if ($commit66) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e68) {
                                        $commit66 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e68) {
                                        $commit66 = false;
                                        fabric.common.TransactionID
                                          $currentTid69 = $tm70.getCurrentTid();
                                        if ($currentTid69 != null) {
                                            if ($e68.tid.equals(
                                                           $currentTid69) ||
                                                  !$e68.tid.isDescendantOf(
                                                              $currentTid69)) {
                                                throw $e68;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit66 && $retry67) {
                                    {  }
                                    continue $label65;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -119, 107, 112, -21,
    -10, -114, -123, 100, -18, -115, 63, 1, 30, -17, -34, -30, 0, -117, -69,
    -123, 117, 32, 29, 86, -63, 63, -55, 39, 117, 21, 37, -104 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524613293000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALUaf3AU1fntJeQXkYRA+BEghCSgINxVRaYQ+RkCnBxJJgkYQmu62XuXLNnbXXffJRctSn8gVKdMx6LitOK0xWoV0WKt0ypTtWp1sO2046j9Q7RaW1ukDnWqtmNrv+/tux/Z7G3uZmxm9vte3nvf977f773bPXGeTLEt0hiTB1QtyMZMage3yAPhSKds2TTaqsm23QO9/crU4vAd794XrQ+QQIRUKrJu6Koia/26zci0yF55RA7plIV2doVb9pByBQm3yfYQI4E9m5IWaTANbWxQM5hYZAL/2y8NHbnz2upTRaSqj1SpejeTmaq0GjqjSdZHKuM0PkAte2M0SqN9ZLpOabSbWqqsqdfDREPvIzW2OqjLLGFRu4vahjaCE2vshEktvmaqE8U3QGwroTDDAvGrHfETTNVCEdVmLRFSElOpFrWvIzeS4giZEtPkQZg4K5LSIsQ5hrZgP0yvUEFMKyYrNEVSPKzqUUYWuinSGjdvhwlAWhqnbMhIL1Wsy9BBahyRNFkfDHUzS9UHYeoUIwGrMFKXkylMKjNlZVgepP2MzHHP63SGYFY5NwuSMFLrnsY5gc/qXD7L8tb59qsO36Bv0wNEApmjVNFQ/jIgqncRddEYtaiuUIewclnkDnnW6UMBQmByrWuyM+fxL1/YsLz+qRecOfM85nQM7KUK61eOD0z73fzWpauLUIwy07BVDIVxmnOvdoqRlqQJ0T4rzREHg6nBp7qe373/AXouQCrCpEQxtEQcomq6YsRNVaPWVqpTS2Y0GiblVI+28vEwKYV2RNWp09sRi9mUhUmxxrtKDP4/mCgGLNBEpdBW9ZiRapsyG+LtpEkIqYaHSIQEGgjZfgu0LyakZDMjnaEhI05DA1qCjkJ4h+ChsqUMhSBvLVUJ2ZYSshI6U2GS6IIoAmSHINSZJSsMokS0giCL+X/gmUQ9qkclCUy8UDGidEC2wV8idjZ1apAe2wwtSq1+RTt8OkxmnL6Lx085xrwNccstJIHP57urRTbtkcSmtgsn+884sYe0woCMLHIEDQpBg2lBgylBQbZKTK0gFKsgFKsTUjLYeiz8II+gEpunWppdJbBbY2oyixlWPEkkies2k9Pz0AHHD0NBgZpRubT7i1d/6VBjEcSsOVqMboSpze4MytSdMLRkSIt+pergux8+fMc+I5NLjDRPSPGJlJiijW5DWYZCo1ACM+yXNciP9Z/e1xzA8lKOFpEhNqGM1LvXGJeqLamyh9aYEiFT0QayhkOpWlXBhixjNNPDA2AaghonFtBYLgF5xVzbbd792m/+egXfS1LFtSqrCndT1pKV0Misiqfu9IzteyxKYd7rRzu/ffv5g3u44WFGk9eCzQhbIZFlyGDDOvDCdX944+zxlwMZZzFSYiYGNFVJcl2mfwp/Ejz/xQezEjsQQ21uFRWhIV0STFx5SUY2KA4aFCgQ3W7eqceNqBpT5QGNYqR8UrX4ssfeO1ztuFuDHsd4Flk+OYNM/9xNZP+Zaz+q52wkBTenjP0y05yKNyPDeaNlyWMoR/Irv19w16/kuyHyoV7Z6vWUlyDC7UG4Ay/ntljB4WWusZUIGh1rzef9xfbE6r8Ft9FMLPaFTny3rnXdOSft07GIPBZ5pP0uOStNLn8g/s9AY8lzAVLaR6r5Di7rbJcM9QvCoA/2YLtVdEbIRePGx++nzubRks61+e48yFrWnQWZcgNtnI3tCifwncABQ1SikZbDs4KQ0g0CN+DoDBPhzKREeGMNJ2nicAmCpalgLDctg4GUNJpMsw0g26mC3UKBZ2WxZaB2woIMZp0GRPIYp6xlZFXOqkhhJ7IUGgeSYFumnUU+110FncRGuCot2SyUbDU86wkp/0jglz0UbvNWuAibyxjWXzz0gfpqPJ5gGPB8vUsZmbkj3N7f1tvT1t4d7mjv37KxtaejyyPgOi01DjVjRBw36KEjt3waPHzESTbnTNY04ViUTeOcy/iyF/G1k7DKIr9VOMWWvzy874n79x10ziw1408YbXoi/tAr/3kpePTNFz12rZKoAaWH5rRsEJ5NhFTsE5h6WLbbz7IIwgiuTpmzcnNXeEtPyozYGRHKIupgUJgMZyP0lOgKeDaDJKcEvt1Doj2FSVSdce414fbNHddgfy+XIOnNKSCipkwesHk8Z1KF/1WJ81OrwCuzhMwqW1IqTea70oS7rmPAptYIXDw8kwFjY0GuUzKPi+NfPXIs2nHvZQFRNjshuJlhrtDoCNWypJiLUTbhFraD3w0yNfDNcwtWtw6/M+hE2ULXyu7ZP9px4sWtS5TbAqQoXewmXEjGE7WML3EVFoX7lN4zrtA1jC90q514KD0r8I+zYyETQU0I1k4sZkjyiMD3uT3kvfUkfMZGEUBONTrObBbObE7XvObUSbA5I1t8vEYYMO3QbnLw1PcK0whJzgn8Vm6NApkg7vXKwNIBw9CorPMVb/RR+WsIxiAP4KIMV5AewzOfRww16lK2HFnMQWaE1JwX+GwOZSdsUjz7XLtTmWDyusCv5ufQwz5j30JwCLMczjMjcNjC/w94aQKbDrmBkBm9Ajf6uO3WiXIjySKB5+WWWxpfNOpF0cCTRdCmsP+qbAz3B11RTVnLUTaw904fpe9GcBuciVNK91s0bvjovgue/YTMnObgGU8XpjuSPCXwz/MK2aMIbuKsj/so8kME9zAyzaIxuGAO+ejB776Xw/N10GOvwNvyjEZnL0AQdoVkleC0VeA1k7qWL8MXO+mj2SMI7gcXOWeW/knDcxk8sF/W/UngXxbmIiR5RuAn89LBOTs97qPDzxCc4t6JGyM0tdd5qcB3VbThTwmZt1vgpny9A9XMtNLmcbuoWrBrFHhmXhFYndHxGR8dn0XwBCO1wk+Tq8q9tRGe1whZ+KjANxTmLSS5XmCWlzq9mYQ646POrxE8D2fGhBkV5rzJaw+7BJ4LhDQlBN7tI7/HHoYkvQJ35BVtvZzryz6iv4LgtxAKcKjADTin7bEKw1178UsC/6Qw2yPJowI/VIDsr/vI/gaC1xiZMiJratTL6qnNVKqA02apg5d8XJDknOQjgS/kljxbsD/7jL2L4I+TCg3mlmZDxNwj8MHChEaSmwXeX4C53/eRnCv/N5AcklbzDPK0ueEivHSvwHsKkxxJ+gTuyc/cH/uM/RvBB5MKPRdWhOxc9qTAJwoTGkkeFPjevISWJJ8xfh37BC4lg5S1JU3VGuMecgleg/PhoC9tgNvo5wQuzyF47r15wFX4pwtOZQ5e8a+8wocXSWmqj1J4cZem8ErDTx05fdEALOG0H1QEjhTmCyTZLnBbfr7wG8PfcqQqRi4akvWoRnfyCm97Cc/9AbuwFIOL080Cr/9M/IGc1gkcyq1TkXOZTx2Da1135x0OxlHPX5DaOeBa1/tYBO9o0hxwpBo3NdXbFtyREZBYI2TlmMCrCnMkklwpsI/SWUF4lMt3qY/sKxAsYWS2zBiNm5BdcDuzVUOf7CyPQTlCyJVvC/xSYbogyRmBn8svKFf6jKElpRAj1W41vOTnp8QWWBzOPqvCAi8sJC5/gcDjgMg51QtcnZeL+BleWuej2wYEnwcXpc7weajIXdQMS8B2uea0wN8vzEVI8j2Bv5Ofi7b6jOEPadImRmbKynUJ1aJdVDH0mDoYMZRhO6cOi0GABwi5qk3gpYXpgCSXCNyYW4cJ/uj0UaQLwXZGiqD+ZSSYeM2X4Hi39gcCxwuTG0k0gWOTyp2qbzNEfRs1rGFqBbuZYVHv6sYV2e2jZD+CHkgo2HE7LSM5ln41KdYKTfoSs3kcHZLVeSXiF0CBxwlZt9bBa//xmSQicrog8Nu5Dei6qUl9XHnVxzDDCJRMNrrtg8NykpGyVAe+2Zrn8aJZfAChtD5Lj7+zfXltjpfMcyZ8kiLoTh6rKpt9bOer/I1p+uOG8ggpiyU0LfsNUFa7xITDhsrNWu68DzK5ViYjc3I5lDnvwHgbTSLpDg1+UjOehvHvRLCVPW8E7oHOPPxvlPuhLgNSAdXk9cP2RvGjeXeCvw7jBFzuuoSF3+yc+GD2xyVlPW/yd6S4K31j2Dz34eED0b9/c71U//7Zt8itTxxINCzY9fT6Fy9O1C4++j/ACO0VSyQAAA==";
}
