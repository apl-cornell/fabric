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
import fabric.util.Iterator;
import fabric.util.List;
import fabric.worker.Store;
import fabric.worker.Worker;
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
    public fabric.metrics.util.ReconfigLock get$lock();
    
    public fabric.metrics.util.ReconfigLock set$lock(
      fabric.metrics.util.ReconfigLock val);
    
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
    public void extendTo(long newExpiry);
    
    public boolean get$activated();
    
    public boolean set$activated(boolean val);
    
    /**
   * @return true iff this contract has been activated (the System is
   *         enforcing it).
   */
    public boolean isActivated();
    
    /**
   * Activate and start enforcing this {@link Contract} in the System.
   */
    public void activate();
    
    public void activate_remote(fabric.lang.security.Principal p);
    
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
    public boolean update(long newExpiry);
    
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
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects();
        
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
            
            public fabric.lang.arrays.ObjectArray getLeafSubjects() {
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
                if (!isActivated()) { return false; }
                long currentTime = java.lang.System.currentTimeMillis();
                if (!fabric.lang.Object._Proxy.idEquals(
                                                 this.get$currentPolicy(),
                                                 null)) {
                    long curExpiry = this.get$currentPolicy().expiry();
                    if (curExpiry < currentTime) {
                        this.get$currentPolicy().unapply((ProxyContract)
                                                           this.$getProxy());
                        this.set$currentPolicy(null);
                    }
                    return update(curExpiry);
                }
                return false;
            }
            
            public boolean implies(fabric.metrics.Metric otherMetric,
                                   double otherRate, double otherBase) {
                return this.get$target().implies(otherMetric, otherRate,
                                                 otherBase);
            }
            
            public fabric.lang.arrays.ObjectArray getLeafSubjects() {
                final fabric.worker.Store local =
                  fabric.worker.Worker.getWorker().getLocalStore();
                if (fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                       null))
                    return (fabric.lang.arrays.ObjectArray)
                             new fabric.lang.arrays.ObjectArray._Impl(local).
                             fabric$lang$arrays$ObjectArray$(
                               this.get$$updateLabel(),
                               this.get$$updateLabel().confPolicy(),
                               fabric.metrics.SampledMetric._Proxy.class, 0).
                             $getProxy();
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
                          proxy$var27 = proxy;
                        fabric.worker.transaction.TransactionManager $tm33 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled36 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff34 = 1;
                        boolean $doBackoff35 = true;
                        boolean $retry30 = true;
                        $label28: for (boolean $commit29 = false; !$commit29;
                                       ) {
                            if ($backoffEnabled36) {
                                if ($doBackoff35) {
                                    if ($backoff34 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff34);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e31) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff34 < 5000) $backoff34 *= 2;
                                }
                                $doBackoff35 = $backoff34 <= 32 ||
                                                 !$doBackoff35;
                            }
                            $commit29 = true;
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
                                if ($tm33.checkForStaleObjects())
                                    continue $label28;
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
                                        fabric.common.TransactionID
                                          $currentTid32 = $tm33.getCurrentTid();
                                        if ($currentTid32 != null) {
                                            if ($e31.tid.equals(
                                                           $currentTid32) ||
                                                  !$e31.tid.isDescendantOf(
                                                              $currentTid32)) {
                                                throw $e31;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit29 && $retry30) {
                                    { proxy = proxy$var27; }
                                    continue $label28;
                                }
                            }
                        }
                    }
                }
                if (tmp.isActivated()) proxy.activate();
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
        
        public static final byte[] $classHash = new byte[] { -66, 15, 100, 108,
        9, -121, -15, -80, 18, -71, 32, 28, 101, -12, -104, -11, 68, 122, 56,
        -33, 114, 112, 94, 97, -55, 35, -82, 125, 37, -55, -42, 91 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1521834554000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public static class _Proxy
    extends fabric.metrics.util.AbstractSubject._Proxy
      implements fabric.metrics.contracts.Contract {
        public fabric.metrics.util.ReconfigLock get$lock() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).get$lock(
                                                                         );
        }
        
        public fabric.metrics.util.ReconfigLock set$lock(
          fabric.metrics.util.ReconfigLock val) {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).set$lock(
                                                                         val);
        }
        
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
        
        public boolean get$activated() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              get$activated();
        }
        
        public boolean set$activated(boolean val) {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              set$activated(val);
        }
        
        public fabric.metrics.contracts.Contract
          fabric$metrics$contracts$Contract$() {
            return ((fabric.metrics.contracts.Contract) fetch()).
              fabric$metrics$contracts$Contract$();
        }
        
        public void extendTo(long arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).extendTo(arg1);
        }
        
        public boolean isActivated() {
            return ((fabric.metrics.contracts.Contract) fetch()).isActivated();
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
        
        public static void static_activate(
          fabric.metrics.contracts.Contract arg1) {
            fabric.metrics.contracts.Contract._Impl.static_activate(arg1);
        }
        
        public boolean update(long arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).update(arg1);
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
        
        public static final java.lang.Class[] $paramTypes1 = null;
        
        public void attemptExtension$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                attemptExtension();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "attemptExtension",
                                                  $paramTypes1, null);
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
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
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
        public fabric.metrics.util.ReconfigLock get$lock() { return this.lock; }
        
        public fabric.metrics.util.ReconfigLock set$lock(
          fabric.metrics.util.ReconfigLock val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.lock = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.util.ReconfigLock lock;
        
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
            this.set$lock(
                   ((fabric.metrics.util.ReconfigLock)
                      new fabric.metrics.util.ReconfigLock._Impl(
                        this.$getStore(
                               )).$getProxy(
                                    )).fabric$metrics$util$ReconfigLock$());
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
        public void extendTo(long newExpiry) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            long currentTime = java.lang.System.currentTimeMillis();
            if (getExpiry() -
                  currentTime <=
                  fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                  get$EXTENSION_WINDOW()) {
                for (fabric.util.Iterator iter = getObservers().iterator();
                     iter.hasNext(); ) {
                    fabric.metrics.util.Observer parent =
                      (fabric.metrics.util.Observer)
                        fabric.lang.Object._Proxy.$getProxy(iter.next());
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
        
        public boolean get$activated() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.activated;
        }
        
        public boolean set$activated(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.activated = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
   * Has the {@link Contract} been activated (the System has started enforcing
   * and maintaining the expiration time)?
   */
        private boolean activated = false;
        
        /**
   * @return true iff this contract has been activated (the System is
   *         enforcing it).
   */
        public boolean isActivated() { return this.get$activated(); }
        
        /**
   * Activate and start enforcing this {@link Contract} in the System.
   */
        public void activate() {
            fabric.metrics.contracts.Contract._Impl.
              static_activate((fabric.metrics.contracts.Contract)
                                this.$getProxy());
        }
        
        public void activate_remote(fabric.lang.security.Principal p) {
            this.activate();
        }
        
        public static void static_activate(
          fabric.metrics.contracts.Contract tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (!tmp.get$activated()) {
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     tmp.get$currentPolicy(),
                                                     null)) {
                        tmp.get$currentPolicy().activate();
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
                    tmp.set$activated(true);
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     tmp.get$currentPolicy(),
                                                     null) &&
                          tmp.get$currentPolicy().expiry() >
                          java.lang.System.currentTimeMillis()) {
                        tmp.set$$expiry((long)
                                          tmp.get$currentPolicy().expiry());
                        tmp.get$currentPolicy().apply(tmp);
                    } else {
                        tmp.set$currentPolicy(null);
                    }
                }
            }
            else {
                boolean unactivated = false;
                {
                    boolean unactivated$var37 = unactivated;
                    fabric.worker.transaction.TransactionManager $tm43 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled46 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff44 = 1;
                    boolean $doBackoff45 = true;
                    boolean $retry40 = true;
                    $label38: for (boolean $commit39 = false; !$commit39; ) {
                        if ($backoffEnabled46) {
                            if ($doBackoff45) {
                                if ($backoff44 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff44);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e41) {
                                            
                                        }
                                    }
                                }
                                if ($backoff44 < 5000) $backoff44 *= 2;
                            }
                            $doBackoff45 = $backoff44 <= 32 || !$doBackoff45;
                        }
                        $commit39 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { unactivated = !tmp.get$activated(); }
                        catch (final fabric.worker.RetryException $e41) {
                            $commit39 = false;
                            continue $label38;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e41) {
                            $commit39 = false;
                            fabric.common.TransactionID $currentTid42 =
                              $tm43.getCurrentTid();
                            if ($e41.tid.isDescendantOf($currentTid42))
                                continue $label38;
                            if ($currentTid42.parent != null) {
                                $retry40 = false;
                                throw $e41;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e41) {
                            $commit39 = false;
                            if ($tm43.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid42 =
                              $tm43.getCurrentTid();
                            if ($e41.tid.isDescendantOf($currentTid42)) {
                                $retry40 = true;
                            }
                            else if ($currentTid42.parent != null) {
                                $retry40 = false;
                                throw $e41;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e41) {
                            $commit39 = false;
                            if ($tm43.checkForStaleObjects()) continue $label38;
                            $retry40 = false;
                            throw new fabric.worker.AbortException($e41);
                        }
                        finally {
                            if ($commit39) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e41) {
                                    $commit39 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e41) {
                                    $commit39 = false;
                                    fabric.common.TransactionID $currentTid42 =
                                      $tm43.getCurrentTid();
                                    if ($currentTid42 != null) {
                                        if ($e41.tid.equals($currentTid42) ||
                                              !$e41.tid.isDescendantOf(
                                                          $currentTid42)) {
                                            throw $e41;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit39 && $retry40) {
                                { unactivated = unactivated$var37; }
                                continue $label38;
                            }
                        }
                    }
                }
                if (unactivated) {
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     tmp.get$currentPolicy(),
                                                     null)) {
                        tmp.get$currentPolicy().activate();
                    }
                    {
                        boolean unactivated$var47 = unactivated;
                        fabric.worker.transaction.TransactionManager $tm53 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled56 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff54 = 1;
                        boolean $doBackoff55 = true;
                        boolean $retry50 = true;
                        $label48: for (boolean $commit49 = false; !$commit49;
                                       ) {
                            if ($backoffEnabled56) {
                                if ($doBackoff55) {
                                    if ($backoff54 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff54);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e51) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff54 < 5000) $backoff54 *= 2;
                                }
                                $doBackoff55 = $backoff54 <= 32 ||
                                                 !$doBackoff55;
                            }
                            $commit49 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
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
                                tmp.set$activated(true);
                                if (!fabric.lang.Object._Proxy.
                                      idEquals(tmp.get$currentPolicy(), null) &&
                                      tmp.get$currentPolicy().expiry() >
                                      java.lang.System.currentTimeMillis()) {
                                    tmp.set$$expiry(
                                          (long)
                                            tmp.get$currentPolicy().expiry());
                                    tmp.get$currentPolicy().apply(tmp);
                                }
                                else {
                                    tmp.set$currentPolicy(null);
                                }
                            }
                            catch (final fabric.worker.RetryException $e51) {
                                $commit49 = false;
                                continue $label48;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e51) {
                                $commit49 = false;
                                fabric.common.TransactionID $currentTid52 =
                                  $tm53.getCurrentTid();
                                if ($e51.tid.isDescendantOf($currentTid52))
                                    continue $label48;
                                if ($currentTid52.parent != null) {
                                    $retry50 = false;
                                    throw $e51;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e51) {
                                $commit49 = false;
                                if ($tm53.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid52 =
                                  $tm53.getCurrentTid();
                                if ($e51.tid.isDescendantOf($currentTid52)) {
                                    $retry50 = true;
                                }
                                else if ($currentTid52.parent != null) {
                                    $retry50 = false;
                                    throw $e51;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e51) {
                                $commit49 = false;
                                if ($tm53.checkForStaleObjects())
                                    continue $label48;
                                $retry50 = false;
                                throw new fabric.worker.AbortException($e51);
                            }
                            finally {
                                if ($commit49) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e51) {
                                        $commit49 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e51) {
                                        $commit49 = false;
                                        fabric.common.TransactionID
                                          $currentTid52 = $tm53.getCurrentTid();
                                        if ($currentTid52 != null) {
                                            if ($e51.tid.equals(
                                                           $currentTid52) ||
                                                  !$e51.tid.isDescendantOf(
                                                              $currentTid52)) {
                                                throw $e51;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit49 && $retry50) {
                                    { unactivated = unactivated$var47; }
                                    continue $label48;
                                }
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
                    fabric.worker.transaction.TransactionManager $tm62 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled65 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff63 = 1;
                    boolean $doBackoff64 = true;
                    boolean $retry59 = true;
                    $label57: for (boolean $commit58 = false; !$commit58; ) {
                        if ($backoffEnabled65) {
                            if ($doBackoff64) {
                                if ($backoff63 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff63);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e60) {
                                            
                                        }
                                    }
                                }
                                if ($backoff63 < 5000) $backoff63 *= 2;
                            }
                            $doBackoff64 = $backoff63 <= 32 || !$doBackoff64;
                        }
                        $commit58 = true;
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
                        catch (final fabric.worker.RetryException $e60) {
                            $commit58 = false;
                            continue $label57;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e60) {
                            $commit58 = false;
                            fabric.common.TransactionID $currentTid61 =
                              $tm62.getCurrentTid();
                            if ($e60.tid.isDescendantOf($currentTid61))
                                continue $label57;
                            if ($currentTid61.parent != null) {
                                $retry59 = false;
                                throw $e60;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e60) {
                            $commit58 = false;
                            if ($tm62.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid61 =
                              $tm62.getCurrentTid();
                            if ($e60.tid.isDescendantOf($currentTid61)) {
                                $retry59 = true;
                            }
                            else if ($currentTid61.parent != null) {
                                $retry59 = false;
                                throw $e60;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e60) {
                            $commit58 = false;
                            if ($tm62.checkForStaleObjects()) continue $label57;
                            $retry59 = false;
                            throw new fabric.worker.AbortException($e60);
                        }
                        finally {
                            if ($commit58) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e60) {
                                    $commit58 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e60) {
                                    $commit58 = false;
                                    fabric.common.TransactionID $currentTid61 =
                                      $tm62.getCurrentTid();
                                    if ($currentTid61 != null) {
                                        if ($e60.tid.equals($currentTid61) ||
                                              !$e60.tid.isDescendantOf(
                                                          $currentTid61)) {
                                            throw $e60;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit58 && $retry59) {
                                {  }
                                continue $label57;
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
        public boolean update(long newExpiry) {
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
                extendTo(newExpiry);
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
            boolean rtn = isActivated() && getExpiry() >= time;
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
            boolean rtn = isActivated() && getExpiry() >=
              java.lang.System.currentTimeMillis();
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
        public boolean stale(long time) {
            return isActivated() && getExpiry() < time;
        }
        
        /**
   * @return true iff the contract is stale (no longer enforced) according to
   *         the local time.
   */
        public boolean stale() {
            return isActivated() && getExpiry() <
              java.lang.System.currentTimeMillis();
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
            if (valid()) {
                this.get$lock().checkForRead();
                return refresh(false);
            }
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
                if (tmp.valid()) {
                    tmp.get$lock().checkForRead();
                    tmp.refresh(true);
                }
                if (!fabric.lang.Object._Proxy.idEquals(tmp.get$currentPolicy(),
                                                        null)) {
                    tmp.get$currentPolicy().unapply(tmp);
                    tmp.set$currentPolicy(null);
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm71 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled74 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff72 = 1;
                    boolean $doBackoff73 = true;
                    boolean $retry68 = true;
                    $label66: for (boolean $commit67 = false; !$commit67; ) {
                        if ($backoffEnabled74) {
                            if ($doBackoff73) {
                                if ($backoff72 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff72);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e69) {
                                            
                                        }
                                    }
                                }
                                if ($backoff72 < 5000) $backoff72 *= 2;
                            }
                            $doBackoff73 = $backoff72 <= 32 || !$doBackoff73;
                        }
                        $commit67 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.valid()) {
                                tmp.get$lock().checkForRead();
                                tmp.refresh(true);
                            }
                            if (!fabric.lang.Object._Proxy.
                                  idEquals(tmp.get$currentPolicy(), null)) {
                                tmp.get$currentPolicy().unapply(tmp);
                                tmp.set$currentPolicy(null);
                            }
                        }
                        catch (final fabric.worker.RetryException $e69) {
                            $commit67 = false;
                            continue $label66;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e69) {
                            $commit67 = false;
                            fabric.common.TransactionID $currentTid70 =
                              $tm71.getCurrentTid();
                            if ($e69.tid.isDescendantOf($currentTid70))
                                continue $label66;
                            if ($currentTid70.parent != null) {
                                $retry68 = false;
                                throw $e69;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e69) {
                            $commit67 = false;
                            if ($tm71.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid70 =
                              $tm71.getCurrentTid();
                            if ($e69.tid.isDescendantOf($currentTid70)) {
                                $retry68 = true;
                            }
                            else if ($currentTid70.parent != null) {
                                $retry68 = false;
                                throw $e69;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e69) {
                            $commit67 = false;
                            if ($tm71.checkForStaleObjects()) continue $label66;
                            $retry68 = false;
                            throw new fabric.worker.AbortException($e69);
                        }
                        finally {
                            if ($commit67) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e69) {
                                    $commit67 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e69) {
                                    $commit67 = false;
                                    fabric.common.TransactionID $currentTid70 =
                                      $tm71.getCurrentTid();
                                    if ($currentTid70 != null) {
                                        if ($e69.tid.equals($currentTid70) ||
                                              !$e69.tid.isDescendantOf(
                                                          $currentTid70)) {
                                            throw $e69;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit67 && $retry68) {
                                {  }
                                continue $label66;
                            }
                        }
                    }
                }
            }
        }
        
        /**
   * Acquire reconfig locks starting from this contract.
   */
        public void acquireReconfigLocks() {
            this.get$lock().acquireOptimistic();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null))
                this.get$currentPolicy().acquireReconfigLocks();
        }
        
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
                      proxy$var75 = proxy;
                    fabric.worker.transaction.TransactionManager $tm81 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled84 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff82 = 1;
                    boolean $doBackoff83 = true;
                    boolean $retry78 = true;
                    $label76: for (boolean $commit77 = false; !$commit77; ) {
                        if ($backoffEnabled84) {
                            if ($doBackoff83) {
                                if ($backoff82 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff82);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e79) {
                                            
                                        }
                                    }
                                }
                                if ($backoff82 < 5000) $backoff82 *= 2;
                            }
                            $doBackoff83 = $backoff82 <= 32 || !$doBackoff83;
                        }
                        $commit77 = true;
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
                        catch (final fabric.worker.RetryException $e79) {
                            $commit77 = false;
                            continue $label76;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e79) {
                            $commit77 = false;
                            fabric.common.TransactionID $currentTid80 =
                              $tm81.getCurrentTid();
                            if ($e79.tid.isDescendantOf($currentTid80))
                                continue $label76;
                            if ($currentTid80.parent != null) {
                                $retry78 = false;
                                throw $e79;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e79) {
                            $commit77 = false;
                            if ($tm81.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid80 =
                              $tm81.getCurrentTid();
                            if ($e79.tid.isDescendantOf($currentTid80)) {
                                $retry78 = true;
                            }
                            else if ($currentTid80.parent != null) {
                                $retry78 = false;
                                throw $e79;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e79) {
                            $commit77 = false;
                            if ($tm81.checkForStaleObjects()) continue $label76;
                            $retry78 = false;
                            throw new fabric.worker.AbortException($e79);
                        }
                        finally {
                            if ($commit77) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e79) {
                                    $commit77 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e79) {
                                    $commit77 = false;
                                    fabric.common.TransactionID $currentTid80 =
                                      $tm81.getCurrentTid();
                                    if ($currentTid80 != null) {
                                        if ($e79.tid.equals($currentTid80) ||
                                              !$e79.tid.isDescendantOf(
                                                          $currentTid80)) {
                                            throw $e79;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit77 && $retry78) {
                                { proxy = proxy$var75; }
                                continue $label76;
                            }
                        }
                    }
                }
            }
            if (tmp.isActivated()) proxy.activate();
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
            $writeRef($getStore(), this.lock, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.currentPolicy, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            out.writeBoolean(this.activated);
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
            this.lock =
              (fabric.metrics.util.ReconfigLock)
                $readRef(fabric.metrics.util.ReconfigLock._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
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
            this.activated = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.Contract._Impl src =
              (fabric.metrics.contracts.Contract._Impl) other;
            this.lock = src.lock;
            this.currentPolicy = src.currentPolicy;
            this.activated = src.activated;
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
                        fabric.worker.transaction.TransactionManager $tm90 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled93 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff91 = 1;
                        boolean $doBackoff92 = true;
                        boolean $retry87 = true;
                        $label85: for (boolean $commit86 = false; !$commit86;
                                       ) {
                            if ($backoffEnabled93) {
                                if ($doBackoff92) {
                                    if ($backoff91 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff91);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e88) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff91 < 5000) $backoff91 *= 2;
                                }
                                $doBackoff92 = $backoff91 <= 32 ||
                                                 !$doBackoff92;
                            }
                            $commit86 = true;
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
                            catch (final fabric.worker.RetryException $e88) {
                                $commit86 = false;
                                continue $label85;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e88) {
                                $commit86 = false;
                                fabric.common.TransactionID $currentTid89 =
                                  $tm90.getCurrentTid();
                                if ($e88.tid.isDescendantOf($currentTid89))
                                    continue $label85;
                                if ($currentTid89.parent != null) {
                                    $retry87 = false;
                                    throw $e88;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e88) {
                                $commit86 = false;
                                if ($tm90.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid89 =
                                  $tm90.getCurrentTid();
                                if ($e88.tid.isDescendantOf($currentTid89)) {
                                    $retry87 = true;
                                }
                                else if ($currentTid89.parent != null) {
                                    $retry87 = false;
                                    throw $e88;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e88) {
                                $commit86 = false;
                                if ($tm90.checkForStaleObjects())
                                    continue $label85;
                                $retry87 = false;
                                throw new fabric.worker.AbortException($e88);
                            }
                            finally {
                                if ($commit86) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e88) {
                                        $commit86 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e88) {
                                        $commit86 = false;
                                        fabric.common.TransactionID
                                          $currentTid89 = $tm90.getCurrentTid();
                                        if ($currentTid89 != null) {
                                            if ($e88.tid.equals(
                                                           $currentTid89) ||
                                                  !$e88.tid.isDescendantOf(
                                                              $currentTid89)) {
                                                throw $e88;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit86 && $retry87) {
                                    {  }
                                    continue $label85;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -53, -20, 34, -104,
    125, -46, 42, 11, -117, 4, 72, 5, 115, 79, 87, -52, -74, -100, 67, 72, 52,
    -103, 102, 48, 54, 80, 43, 41, 62, -109, -96, -5 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521834554000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
