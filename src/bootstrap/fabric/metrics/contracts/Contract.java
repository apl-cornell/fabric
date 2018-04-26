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
                          proxy$var446 = proxy;
                        fabric.worker.transaction.TransactionManager $tm452 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled455 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff453 = 1;
                        boolean $doBackoff454 = true;
                        boolean $retry449 = true;
                        $label447: for (boolean $commit448 = false; !$commit448;
                                        ) {
                            if ($backoffEnabled455) {
                                if ($doBackoff454) {
                                    if ($backoff453 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff453);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e450) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff453 < 5000) $backoff453 *= 2;
                                }
                                $doBackoff454 = $backoff453 <= 32 ||
                                                  !$doBackoff454;
                            }
                            $commit448 = true;
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
                            catch (final fabric.worker.RetryException $e450) {
                                $commit448 = false;
                                continue $label447;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e450) {
                                $commit448 = false;
                                fabric.common.TransactionID $currentTid451 =
                                  $tm452.getCurrentTid();
                                if ($e450.tid.isDescendantOf($currentTid451))
                                    continue $label447;
                                if ($currentTid451.parent != null) {
                                    $retry449 = false;
                                    throw $e450;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e450) {
                                $commit448 = false;
                                if ($tm452.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid451 =
                                  $tm452.getCurrentTid();
                                if ($e450.tid.isDescendantOf($currentTid451)) {
                                    $retry449 = true;
                                }
                                else if ($currentTid451.parent != null) {
                                    $retry449 = false;
                                    throw $e450;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e450) {
                                $commit448 = false;
                                if ($tm452.checkForStaleObjects())
                                    continue $label447;
                                $retry449 = false;
                                throw new fabric.worker.AbortException($e450);
                            }
                            finally {
                                if ($commit448) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e450) {
                                        $commit448 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e450) {
                                        $commit448 = false;
                                        fabric.common.TransactionID
                                          $currentTid451 =
                                          $tm452.getCurrentTid();
                                        if ($currentTid451 != null) {
                                            if ($e450.tid.equals(
                                                            $currentTid451) ||
                                                  !$e450.tid.
                                                  isDescendantOf(
                                                    $currentTid451)) {
                                                throw $e450;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit448 && $retry449) {
                                    { proxy = proxy$var446; }
                                    continue $label447;
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
        
        public static final byte[] $classHash = new byte[] { -42, -24, 29, 27,
        -11, 38, 112, 88, -46, 82, -50, 102, 83, -66, 14, -106, 4, -35, 99,
        -128, -36, 122, 56, -47, 46, 127, -107, -88, 15, 32, -64, -35 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1524675608000L;
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
                    fabric.worker.transaction.TransactionManager $tm461 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled464 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff462 = 1;
                    boolean $doBackoff463 = true;
                    boolean $retry458 = true;
                    $label456: for (boolean $commit457 = false; !$commit457; ) {
                        if ($backoffEnabled464) {
                            if ($doBackoff463) {
                                if ($backoff462 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff462);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e459) {
                                            
                                        }
                                    }
                                }
                                if ($backoff462 < 5000) $backoff462 *= 2;
                            }
                            $doBackoff463 = $backoff462 <= 32 || !$doBackoff463;
                        }
                        $commit457 = true;
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
                        catch (final fabric.worker.RetryException $e459) {
                            $commit457 = false;
                            continue $label456;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e459) {
                            $commit457 = false;
                            fabric.common.TransactionID $currentTid460 =
                              $tm461.getCurrentTid();
                            if ($e459.tid.isDescendantOf($currentTid460))
                                continue $label456;
                            if ($currentTid460.parent != null) {
                                $retry458 = false;
                                throw $e459;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e459) {
                            $commit457 = false;
                            if ($tm461.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid460 =
                              $tm461.getCurrentTid();
                            if ($e459.tid.isDescendantOf($currentTid460)) {
                                $retry458 = true;
                            }
                            else if ($currentTid460.parent != null) {
                                $retry458 = false;
                                throw $e459;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e459) {
                            $commit457 = false;
                            if ($tm461.checkForStaleObjects())
                                continue $label456;
                            $retry458 = false;
                            throw new fabric.worker.AbortException($e459);
                        }
                        finally {
                            if ($commit457) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e459) {
                                    $commit457 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e459) {
                                    $commit457 = false;
                                    fabric.common.TransactionID $currentTid460 =
                                      $tm461.getCurrentTid();
                                    if ($currentTid460 != null) {
                                        if ($e459.tid.equals($currentTid460) ||
                                              !$e459.tid.isDescendantOf(
                                                           $currentTid460)) {
                                            throw $e459;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit457 && $retry458) {
                                {  }
                                continue $label456;
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
                    fabric.worker.transaction.TransactionManager $tm470 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled473 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff471 = 1;
                    boolean $doBackoff472 = true;
                    boolean $retry467 = true;
                    $label465: for (boolean $commit466 = false; !$commit466; ) {
                        if ($backoffEnabled473) {
                            if ($doBackoff472) {
                                if ($backoff471 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff471);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e468) {
                                            
                                        }
                                    }
                                }
                                if ($backoff471 < 5000) $backoff471 *= 2;
                            }
                            $doBackoff472 = $backoff471 <= 32 || !$doBackoff472;
                        }
                        $commit466 = true;
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
                        catch (final fabric.worker.RetryException $e468) {
                            $commit466 = false;
                            continue $label465;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e468) {
                            $commit466 = false;
                            fabric.common.TransactionID $currentTid469 =
                              $tm470.getCurrentTid();
                            if ($e468.tid.isDescendantOf($currentTid469))
                                continue $label465;
                            if ($currentTid469.parent != null) {
                                $retry467 = false;
                                throw $e468;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e468) {
                            $commit466 = false;
                            if ($tm470.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid469 =
                              $tm470.getCurrentTid();
                            if ($e468.tid.isDescendantOf($currentTid469)) {
                                $retry467 = true;
                            }
                            else if ($currentTid469.parent != null) {
                                $retry467 = false;
                                throw $e468;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e468) {
                            $commit466 = false;
                            if ($tm470.checkForStaleObjects())
                                continue $label465;
                            $retry467 = false;
                            throw new fabric.worker.AbortException($e468);
                        }
                        finally {
                            if ($commit466) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e468) {
                                    $commit466 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e468) {
                                    $commit466 = false;
                                    fabric.common.TransactionID $currentTid469 =
                                      $tm470.getCurrentTid();
                                    if ($currentTid469 != null) {
                                        if ($e468.tid.equals($currentTid469) ||
                                              !$e468.tid.isDescendantOf(
                                                           $currentTid469)) {
                                            throw $e468;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit466 && $retry467) {
                                {  }
                                continue $label465;
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
                    fabric.worker.transaction.TransactionManager $tm479 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled482 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff480 = 1;
                    boolean $doBackoff481 = true;
                    boolean $retry476 = true;
                    $label474: for (boolean $commit475 = false; !$commit475; ) {
                        if ($backoffEnabled482) {
                            if ($doBackoff481) {
                                if ($backoff480 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff480);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e477) {
                                            
                                        }
                                    }
                                }
                                if ($backoff480 < 5000) $backoff480 *= 2;
                            }
                            $doBackoff481 = $backoff480 <= 32 || !$doBackoff481;
                        }
                        $commit475 = true;
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
                        catch (final fabric.worker.RetryException $e477) {
                            $commit475 = false;
                            continue $label474;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e477) {
                            $commit475 = false;
                            fabric.common.TransactionID $currentTid478 =
                              $tm479.getCurrentTid();
                            if ($e477.tid.isDescendantOf($currentTid478))
                                continue $label474;
                            if ($currentTid478.parent != null) {
                                $retry476 = false;
                                throw $e477;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e477) {
                            $commit475 = false;
                            if ($tm479.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid478 =
                              $tm479.getCurrentTid();
                            if ($e477.tid.isDescendantOf($currentTid478)) {
                                $retry476 = true;
                            }
                            else if ($currentTid478.parent != null) {
                                $retry476 = false;
                                throw $e477;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e477) {
                            $commit475 = false;
                            if ($tm479.checkForStaleObjects())
                                continue $label474;
                            $retry476 = false;
                            throw new fabric.worker.AbortException($e477);
                        }
                        finally {
                            if ($commit475) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e477) {
                                    $commit475 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e477) {
                                    $commit475 = false;
                                    fabric.common.TransactionID $currentTid478 =
                                      $tm479.getCurrentTid();
                                    if ($currentTid478 != null) {
                                        if ($e477.tid.equals($currentTid478) ||
                                              !$e477.tid.isDescendantOf(
                                                           $currentTid478)) {
                                            throw $e477;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit475 && $retry476) {
                                {  }
                                continue $label474;
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
                      proxy$var483 = proxy;
                    fabric.worker.transaction.TransactionManager $tm489 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled492 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff490 = 1;
                    boolean $doBackoff491 = true;
                    boolean $retry486 = true;
                    $label484: for (boolean $commit485 = false; !$commit485; ) {
                        if ($backoffEnabled492) {
                            if ($doBackoff491) {
                                if ($backoff490 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff490);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e487) {
                                            
                                        }
                                    }
                                }
                                if ($backoff490 < 5000) $backoff490 *= 2;
                            }
                            $doBackoff491 = $backoff490 <= 32 || !$doBackoff491;
                        }
                        $commit485 = true;
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
                        catch (final fabric.worker.RetryException $e487) {
                            $commit485 = false;
                            continue $label484;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e487) {
                            $commit485 = false;
                            fabric.common.TransactionID $currentTid488 =
                              $tm489.getCurrentTid();
                            if ($e487.tid.isDescendantOf($currentTid488))
                                continue $label484;
                            if ($currentTid488.parent != null) {
                                $retry486 = false;
                                throw $e487;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e487) {
                            $commit485 = false;
                            if ($tm489.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid488 =
                              $tm489.getCurrentTid();
                            if ($e487.tid.isDescendantOf($currentTid488)) {
                                $retry486 = true;
                            }
                            else if ($currentTid488.parent != null) {
                                $retry486 = false;
                                throw $e487;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e487) {
                            $commit485 = false;
                            if ($tm489.checkForStaleObjects())
                                continue $label484;
                            $retry486 = false;
                            throw new fabric.worker.AbortException($e487);
                        }
                        finally {
                            if ($commit485) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e487) {
                                    $commit485 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e487) {
                                    $commit485 = false;
                                    fabric.common.TransactionID $currentTid488 =
                                      $tm489.getCurrentTid();
                                    if ($currentTid488 != null) {
                                        if ($e487.tid.equals($currentTid488) ||
                                              !$e487.tid.isDescendantOf(
                                                           $currentTid488)) {
                                            throw $e487;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit485 && $retry486) {
                                { proxy = proxy$var483; }
                                continue $label484;
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
                        fabric.worker.transaction.TransactionManager $tm498 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled501 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff499 = 1;
                        boolean $doBackoff500 = true;
                        boolean $retry495 = true;
                        $label493: for (boolean $commit494 = false; !$commit494;
                                        ) {
                            if ($backoffEnabled501) {
                                if ($doBackoff500) {
                                    if ($backoff499 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff499);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e496) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff499 < 5000) $backoff499 *= 2;
                                }
                                $doBackoff500 = $backoff499 <= 32 ||
                                                  !$doBackoff500;
                            }
                            $commit494 = true;
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
                            catch (final fabric.worker.RetryException $e496) {
                                $commit494 = false;
                                continue $label493;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e496) {
                                $commit494 = false;
                                fabric.common.TransactionID $currentTid497 =
                                  $tm498.getCurrentTid();
                                if ($e496.tid.isDescendantOf($currentTid497))
                                    continue $label493;
                                if ($currentTid497.parent != null) {
                                    $retry495 = false;
                                    throw $e496;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e496) {
                                $commit494 = false;
                                if ($tm498.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid497 =
                                  $tm498.getCurrentTid();
                                if ($e496.tid.isDescendantOf($currentTid497)) {
                                    $retry495 = true;
                                }
                                else if ($currentTid497.parent != null) {
                                    $retry495 = false;
                                    throw $e496;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e496) {
                                $commit494 = false;
                                if ($tm498.checkForStaleObjects())
                                    continue $label493;
                                $retry495 = false;
                                throw new fabric.worker.AbortException($e496);
                            }
                            finally {
                                if ($commit494) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e496) {
                                        $commit494 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e496) {
                                        $commit494 = false;
                                        fabric.common.TransactionID
                                          $currentTid497 =
                                          $tm498.getCurrentTid();
                                        if ($currentTid497 != null) {
                                            if ($e496.tid.equals(
                                                            $currentTid497) ||
                                                  !$e496.tid.
                                                  isDescendantOf(
                                                    $currentTid497)) {
                                                throw $e496;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit494 && $retry495) {
                                    {  }
                                    continue $label493;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 115, 12, -2, -115, 26,
    49, 125, -15, -122, 5, -111, 31, 34, 22, 82, 49, -3, 107, -114, 78, 112, -1,
    87, -95, 102, -103, 113, -44, 116, 15, 22, -108 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524675608000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
