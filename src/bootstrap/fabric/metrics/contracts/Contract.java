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
    public void extendTo(long newExpiry, boolean isAsyncExtension);
    
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
                    return update(curExpiry, asyncExtension);
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
                          proxy$var455 = proxy;
                        fabric.worker.transaction.TransactionManager $tm461 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled464 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff462 = 1;
                        boolean $doBackoff463 = true;
                        boolean $retry458 = true;
                        $label456: for (boolean $commit457 = false; !$commit457;
                                        ) {
                            if ($backoffEnabled464) {
                                if ($doBackoff463) {
                                    if ($backoff462 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff462);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e459) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff462 < 5000) $backoff462 *= 2;
                                }
                                $doBackoff463 = $backoff462 <= 32 ||
                                                  !$doBackoff463;
                            }
                            $commit457 = true;
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
                                        fabric.common.TransactionID
                                          $currentTid460 =
                                          $tm461.getCurrentTid();
                                        if ($currentTid460 != null) {
                                            if ($e459.tid.equals(
                                                            $currentTid460) ||
                                                  !$e459.tid.
                                                  isDescendantOf(
                                                    $currentTid460)) {
                                                throw $e459;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit457 && $retry458) {
                                    { proxy = proxy$var455; }
                                    continue $label456;
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
        
        public static final byte[] $classHash = new byte[] { -50, 113, -93, -19,
        -110, 64, 100, -22, -107, -110, -95, 113, 115, -72, -64, 122, -105, -63,
        27, -23, 53, 99, -63, 59, -12, 52, -77, -90, 67, -71, -14, 105 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1523829671000L;
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
        
        public void extendTo(long arg1, boolean arg2) {
            ((fabric.metrics.contracts.Contract) fetch()).extendTo(arg1, arg2);
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
        public void extendTo(long newExpiry, boolean isAsyncExtension) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            long currentTime = java.lang.System.currentTimeMillis();
            if (getExpiry() -
                  currentTime <=
                  fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                  get$EXTENSION_WINDOW() || isAsyncExtension) {
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
            }
            else {
                boolean unactivated = false;
                {
                    boolean unactivated$var465 = unactivated;
                    fabric.worker.transaction.TransactionManager $tm471 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled474 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff472 = 1;
                    boolean $doBackoff473 = true;
                    boolean $retry468 = true;
                    $label466: for (boolean $commit467 = false; !$commit467; ) {
                        if ($backoffEnabled474) {
                            if ($doBackoff473) {
                                if ($backoff472 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff472);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e469) {
                                            
                                        }
                                    }
                                }
                                if ($backoff472 < 5000) $backoff472 *= 2;
                            }
                            $doBackoff473 = $backoff472 <= 32 || !$doBackoff473;
                        }
                        $commit467 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { unactivated = !tmp.get$activated(); }
                        catch (final fabric.worker.RetryException $e469) {
                            $commit467 = false;
                            continue $label466;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e469) {
                            $commit467 = false;
                            fabric.common.TransactionID $currentTid470 =
                              $tm471.getCurrentTid();
                            if ($e469.tid.isDescendantOf($currentTid470))
                                continue $label466;
                            if ($currentTid470.parent != null) {
                                $retry468 = false;
                                throw $e469;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e469) {
                            $commit467 = false;
                            if ($tm471.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid470 =
                              $tm471.getCurrentTid();
                            if ($e469.tid.isDescendantOf($currentTid470)) {
                                $retry468 = true;
                            }
                            else if ($currentTid470.parent != null) {
                                $retry468 = false;
                                throw $e469;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e469) {
                            $commit467 = false;
                            if ($tm471.checkForStaleObjects())
                                continue $label466;
                            $retry468 = false;
                            throw new fabric.worker.AbortException($e469);
                        }
                        finally {
                            if ($commit467) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e469) {
                                    $commit467 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e469) {
                                    $commit467 = false;
                                    fabric.common.TransactionID $currentTid470 =
                                      $tm471.getCurrentTid();
                                    if ($currentTid470 != null) {
                                        if ($e469.tid.equals($currentTid470) ||
                                              !$e469.tid.isDescendantOf(
                                                           $currentTid470)) {
                                            throw $e469;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit467 && $retry468) {
                                { unactivated = unactivated$var465; }
                                continue $label466;
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
                        boolean unactivated$var475 = unactivated;
                        fabric.worker.transaction.TransactionManager $tm481 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled484 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff482 = 1;
                        boolean $doBackoff483 = true;
                        boolean $retry478 = true;
                        $label476: for (boolean $commit477 = false; !$commit477;
                                        ) {
                            if ($backoffEnabled484) {
                                if ($doBackoff483) {
                                    if ($backoff482 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff482);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e479) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff482 < 5000) $backoff482 *= 2;
                                }
                                $doBackoff483 = $backoff482 <= 32 ||
                                                  !$doBackoff483;
                            }
                            $commit477 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
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
                            catch (final fabric.worker.RetryException $e479) {
                                $commit477 = false;
                                continue $label476;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e479) {
                                $commit477 = false;
                                fabric.common.TransactionID $currentTid480 =
                                  $tm481.getCurrentTid();
                                if ($e479.tid.isDescendantOf($currentTid480))
                                    continue $label476;
                                if ($currentTid480.parent != null) {
                                    $retry478 = false;
                                    throw $e479;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e479) {
                                $commit477 = false;
                                if ($tm481.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid480 =
                                  $tm481.getCurrentTid();
                                if ($e479.tid.isDescendantOf($currentTid480)) {
                                    $retry478 = true;
                                }
                                else if ($currentTid480.parent != null) {
                                    $retry478 = false;
                                    throw $e479;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e479) {
                                $commit477 = false;
                                if ($tm481.checkForStaleObjects())
                                    continue $label476;
                                $retry478 = false;
                                throw new fabric.worker.AbortException($e479);
                            }
                            finally {
                                if ($commit477) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e479) {
                                        $commit477 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e479) {
                                        $commit477 = false;
                                        fabric.common.TransactionID
                                          $currentTid480 =
                                          $tm481.getCurrentTid();
                                        if ($currentTid480 != null) {
                                            if ($e479.tid.equals(
                                                            $currentTid480) ||
                                                  !$e479.tid.
                                                  isDescendantOf(
                                                    $currentTid480)) {
                                                throw $e479;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit477 && $retry478) {
                                    { unactivated = unactivated$var475; }
                                    continue $label476;
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
                    fabric.worker.transaction.TransactionManager $tm490 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled493 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff491 = 1;
                    boolean $doBackoff492 = true;
                    boolean $retry487 = true;
                    $label485: for (boolean $commit486 = false; !$commit486; ) {
                        if ($backoffEnabled493) {
                            if ($doBackoff492) {
                                if ($backoff491 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff491);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e488) {
                                            
                                        }
                                    }
                                }
                                if ($backoff491 < 5000) $backoff491 *= 2;
                            }
                            $doBackoff492 = $backoff491 <= 32 || !$doBackoff492;
                        }
                        $commit486 = true;
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
                        catch (final fabric.worker.RetryException $e488) {
                            $commit486 = false;
                            continue $label485;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e488) {
                            $commit486 = false;
                            fabric.common.TransactionID $currentTid489 =
                              $tm490.getCurrentTid();
                            if ($e488.tid.isDescendantOf($currentTid489))
                                continue $label485;
                            if ($currentTid489.parent != null) {
                                $retry487 = false;
                                throw $e488;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e488) {
                            $commit486 = false;
                            if ($tm490.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid489 =
                              $tm490.getCurrentTid();
                            if ($e488.tid.isDescendantOf($currentTid489)) {
                                $retry487 = true;
                            }
                            else if ($currentTid489.parent != null) {
                                $retry487 = false;
                                throw $e488;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e488) {
                            $commit486 = false;
                            if ($tm490.checkForStaleObjects())
                                continue $label485;
                            $retry487 = false;
                            throw new fabric.worker.AbortException($e488);
                        }
                        finally {
                            if ($commit486) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e488) {
                                    $commit486 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e488) {
                                    $commit486 = false;
                                    fabric.common.TransactionID $currentTid489 =
                                      $tm490.getCurrentTid();
                                    if ($currentTid489 != null) {
                                        if ($e488.tid.equals($currentTid489) ||
                                              !$e488.tid.isDescendantOf(
                                                           $currentTid489)) {
                                            throw $e488;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit486 && $retry487) {
                                {  }
                                continue $label485;
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
                    fabric.worker.transaction.TransactionManager $tm499 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled502 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff500 = 1;
                    boolean $doBackoff501 = true;
                    boolean $retry496 = true;
                    $label494: for (boolean $commit495 = false; !$commit495; ) {
                        if ($backoffEnabled502) {
                            if ($doBackoff501) {
                                if ($backoff500 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff500);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e497) {
                                            
                                        }
                                    }
                                }
                                if ($backoff500 < 5000) $backoff500 *= 2;
                            }
                            $doBackoff501 = $backoff500 <= 32 || !$doBackoff501;
                        }
                        $commit495 = true;
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
                        catch (final fabric.worker.RetryException $e497) {
                            $commit495 = false;
                            continue $label494;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e497) {
                            $commit495 = false;
                            fabric.common.TransactionID $currentTid498 =
                              $tm499.getCurrentTid();
                            if ($e497.tid.isDescendantOf($currentTid498))
                                continue $label494;
                            if ($currentTid498.parent != null) {
                                $retry496 = false;
                                throw $e497;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e497) {
                            $commit495 = false;
                            if ($tm499.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid498 =
                              $tm499.getCurrentTid();
                            if ($e497.tid.isDescendantOf($currentTid498)) {
                                $retry496 = true;
                            }
                            else if ($currentTid498.parent != null) {
                                $retry496 = false;
                                throw $e497;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e497) {
                            $commit495 = false;
                            if ($tm499.checkForStaleObjects())
                                continue $label494;
                            $retry496 = false;
                            throw new fabric.worker.AbortException($e497);
                        }
                        finally {
                            if ($commit495) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e497) {
                                    $commit495 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e497) {
                                    $commit495 = false;
                                    fabric.common.TransactionID $currentTid498 =
                                      $tm499.getCurrentTid();
                                    if ($currentTid498 != null) {
                                        if ($e497.tid.equals($currentTid498) ||
                                              !$e497.tid.isDescendantOf(
                                                           $currentTid498)) {
                                            throw $e497;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit495 && $retry496) {
                                {  }
                                continue $label494;
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
                      proxy$var503 = proxy;
                    fabric.worker.transaction.TransactionManager $tm509 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled512 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff510 = 1;
                    boolean $doBackoff511 = true;
                    boolean $retry506 = true;
                    $label504: for (boolean $commit505 = false; !$commit505; ) {
                        if ($backoffEnabled512) {
                            if ($doBackoff511) {
                                if ($backoff510 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff510);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e507) {
                                            
                                        }
                                    }
                                }
                                if ($backoff510 < 5000) $backoff510 *= 2;
                            }
                            $doBackoff511 = $backoff510 <= 32 || !$doBackoff511;
                        }
                        $commit505 = true;
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
                        catch (final fabric.worker.RetryException $e507) {
                            $commit505 = false;
                            continue $label504;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e507) {
                            $commit505 = false;
                            fabric.common.TransactionID $currentTid508 =
                              $tm509.getCurrentTid();
                            if ($e507.tid.isDescendantOf($currentTid508))
                                continue $label504;
                            if ($currentTid508.parent != null) {
                                $retry506 = false;
                                throw $e507;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e507) {
                            $commit505 = false;
                            if ($tm509.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid508 =
                              $tm509.getCurrentTid();
                            if ($e507.tid.isDescendantOf($currentTid508)) {
                                $retry506 = true;
                            }
                            else if ($currentTid508.parent != null) {
                                $retry506 = false;
                                throw $e507;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e507) {
                            $commit505 = false;
                            if ($tm509.checkForStaleObjects())
                                continue $label504;
                            $retry506 = false;
                            throw new fabric.worker.AbortException($e507);
                        }
                        finally {
                            if ($commit505) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e507) {
                                    $commit505 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e507) {
                                    $commit505 = false;
                                    fabric.common.TransactionID $currentTid508 =
                                      $tm509.getCurrentTid();
                                    if ($currentTid508 != null) {
                                        if ($e507.tid.equals($currentTid508) ||
                                              !$e507.tid.isDescendantOf(
                                                           $currentTid508)) {
                                            throw $e507;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit505 && $retry506) {
                                { proxy = proxy$var503; }
                                continue $label504;
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
                        fabric.worker.transaction.TransactionManager $tm518 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled521 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff519 = 1;
                        boolean $doBackoff520 = true;
                        boolean $retry515 = true;
                        $label513: for (boolean $commit514 = false; !$commit514;
                                        ) {
                            if ($backoffEnabled521) {
                                if ($doBackoff520) {
                                    if ($backoff519 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff519);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e516) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff519 < 5000) $backoff519 *= 2;
                                }
                                $doBackoff520 = $backoff519 <= 32 ||
                                                  !$doBackoff520;
                            }
                            $commit514 = true;
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
                            catch (final fabric.worker.RetryException $e516) {
                                $commit514 = false;
                                continue $label513;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e516) {
                                $commit514 = false;
                                fabric.common.TransactionID $currentTid517 =
                                  $tm518.getCurrentTid();
                                if ($e516.tid.isDescendantOf($currentTid517))
                                    continue $label513;
                                if ($currentTid517.parent != null) {
                                    $retry515 = false;
                                    throw $e516;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e516) {
                                $commit514 = false;
                                if ($tm518.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid517 =
                                  $tm518.getCurrentTid();
                                if ($e516.tid.isDescendantOf($currentTid517)) {
                                    $retry515 = true;
                                }
                                else if ($currentTid517.parent != null) {
                                    $retry515 = false;
                                    throw $e516;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e516) {
                                $commit514 = false;
                                if ($tm518.checkForStaleObjects())
                                    continue $label513;
                                $retry515 = false;
                                throw new fabric.worker.AbortException($e516);
                            }
                            finally {
                                if ($commit514) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e516) {
                                        $commit514 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e516) {
                                        $commit514 = false;
                                        fabric.common.TransactionID
                                          $currentTid517 =
                                          $tm518.getCurrentTid();
                                        if ($currentTid517 != null) {
                                            if ($e516.tid.equals(
                                                            $currentTid517) ||
                                                  !$e516.tid.
                                                  isDescendantOf(
                                                    $currentTid517)) {
                                                throw $e516;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit514 && $retry515) {
                                    {  }
                                    continue $label513;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -36, 82, -36, -110,
    -100, -59, 99, 102, -9, -57, 39, -99, 68, -26, -33, 59, 77, 60, 65, 113, 86,
    -3, 30, 34, -108, 28, 126, 95, -14, -70, 10, 111 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1523829671000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
