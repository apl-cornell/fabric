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
                          proxy$var437 = proxy;
                        fabric.worker.transaction.TransactionManager $tm443 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled446 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff444 = 1;
                        boolean $doBackoff445 = true;
                        boolean $retry440 = true;
                        $label438: for (boolean $commit439 = false; !$commit439;
                                        ) {
                            if ($backoffEnabled446) {
                                if ($doBackoff445) {
                                    if ($backoff444 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff444);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e441) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff444 < 5000) $backoff444 *= 2;
                                }
                                $doBackoff445 = $backoff444 <= 32 ||
                                                  !$doBackoff445;
                            }
                            $commit439 = true;
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
                            catch (final fabric.worker.RetryException $e441) {
                                $commit439 = false;
                                continue $label438;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e441) {
                                $commit439 = false;
                                fabric.common.TransactionID $currentTid442 =
                                  $tm443.getCurrentTid();
                                if ($e441.tid.isDescendantOf($currentTid442))
                                    continue $label438;
                                if ($currentTid442.parent != null) {
                                    $retry440 = false;
                                    throw $e441;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e441) {
                                $commit439 = false;
                                if ($tm443.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid442 =
                                  $tm443.getCurrentTid();
                                if ($e441.tid.isDescendantOf($currentTid442)) {
                                    $retry440 = true;
                                }
                                else if ($currentTid442.parent != null) {
                                    $retry440 = false;
                                    throw $e441;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e441) {
                                $commit439 = false;
                                if ($tm443.checkForStaleObjects())
                                    continue $label438;
                                $retry440 = false;
                                throw new fabric.worker.AbortException($e441);
                            }
                            finally {
                                if ($commit439) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e441) {
                                        $commit439 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e441) {
                                        $commit439 = false;
                                        fabric.common.TransactionID
                                          $currentTid442 =
                                          $tm443.getCurrentTid();
                                        if ($currentTid442 != null) {
                                            if ($e441.tid.equals(
                                                            $currentTid442) ||
                                                  !$e441.tid.
                                                  isDescendantOf(
                                                    $currentTid442)) {
                                                throw $e441;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit439 && $retry440) {
                                    { proxy = proxy$var437; }
                                    continue $label438;
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
        public static final long jlc$SourceLastModified$fabil = 1523309338000L;
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
                    boolean unactivated$var447 = unactivated;
                    fabric.worker.transaction.TransactionManager $tm453 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled456 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff454 = 1;
                    boolean $doBackoff455 = true;
                    boolean $retry450 = true;
                    $label448: for (boolean $commit449 = false; !$commit449; ) {
                        if ($backoffEnabled456) {
                            if ($doBackoff455) {
                                if ($backoff454 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff454);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e451) {
                                            
                                        }
                                    }
                                }
                                if ($backoff454 < 5000) $backoff454 *= 2;
                            }
                            $doBackoff455 = $backoff454 <= 32 || !$doBackoff455;
                        }
                        $commit449 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { unactivated = !tmp.get$activated(); }
                        catch (final fabric.worker.RetryException $e451) {
                            $commit449 = false;
                            continue $label448;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e451) {
                            $commit449 = false;
                            fabric.common.TransactionID $currentTid452 =
                              $tm453.getCurrentTid();
                            if ($e451.tid.isDescendantOf($currentTid452))
                                continue $label448;
                            if ($currentTid452.parent != null) {
                                $retry450 = false;
                                throw $e451;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e451) {
                            $commit449 = false;
                            if ($tm453.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid452 =
                              $tm453.getCurrentTid();
                            if ($e451.tid.isDescendantOf($currentTid452)) {
                                $retry450 = true;
                            }
                            else if ($currentTid452.parent != null) {
                                $retry450 = false;
                                throw $e451;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e451) {
                            $commit449 = false;
                            if ($tm453.checkForStaleObjects())
                                continue $label448;
                            $retry450 = false;
                            throw new fabric.worker.AbortException($e451);
                        }
                        finally {
                            if ($commit449) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e451) {
                                    $commit449 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e451) {
                                    $commit449 = false;
                                    fabric.common.TransactionID $currentTid452 =
                                      $tm453.getCurrentTid();
                                    if ($currentTid452 != null) {
                                        if ($e451.tid.equals($currentTid452) ||
                                              !$e451.tid.isDescendantOf(
                                                           $currentTid452)) {
                                            throw $e451;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit449 && $retry450) {
                                { unactivated = unactivated$var447; }
                                continue $label448;
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
                        boolean unactivated$var457 = unactivated;
                        fabric.worker.transaction.TransactionManager $tm463 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled466 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff464 = 1;
                        boolean $doBackoff465 = true;
                        boolean $retry460 = true;
                        $label458: for (boolean $commit459 = false; !$commit459;
                                        ) {
                            if ($backoffEnabled466) {
                                if ($doBackoff465) {
                                    if ($backoff464 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff464);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e461) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff464 < 5000) $backoff464 *= 2;
                                }
                                $doBackoff465 = $backoff464 <= 32 ||
                                                  !$doBackoff465;
                            }
                            $commit459 = true;
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
                            catch (final fabric.worker.RetryException $e461) {
                                $commit459 = false;
                                continue $label458;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e461) {
                                $commit459 = false;
                                fabric.common.TransactionID $currentTid462 =
                                  $tm463.getCurrentTid();
                                if ($e461.tid.isDescendantOf($currentTid462))
                                    continue $label458;
                                if ($currentTid462.parent != null) {
                                    $retry460 = false;
                                    throw $e461;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e461) {
                                $commit459 = false;
                                if ($tm463.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid462 =
                                  $tm463.getCurrentTid();
                                if ($e461.tid.isDescendantOf($currentTid462)) {
                                    $retry460 = true;
                                }
                                else if ($currentTid462.parent != null) {
                                    $retry460 = false;
                                    throw $e461;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e461) {
                                $commit459 = false;
                                if ($tm463.checkForStaleObjects())
                                    continue $label458;
                                $retry460 = false;
                                throw new fabric.worker.AbortException($e461);
                            }
                            finally {
                                if ($commit459) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e461) {
                                        $commit459 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e461) {
                                        $commit459 = false;
                                        fabric.common.TransactionID
                                          $currentTid462 =
                                          $tm463.getCurrentTid();
                                        if ($currentTid462 != null) {
                                            if ($e461.tid.equals(
                                                            $currentTid462) ||
                                                  !$e461.tid.
                                                  isDescendantOf(
                                                    $currentTid462)) {
                                                throw $e461;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit459 && $retry460) {
                                    { unactivated = unactivated$var457; }
                                    continue $label458;
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
                    fabric.worker.transaction.TransactionManager $tm472 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled475 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff473 = 1;
                    boolean $doBackoff474 = true;
                    boolean $retry469 = true;
                    $label467: for (boolean $commit468 = false; !$commit468; ) {
                        if ($backoffEnabled475) {
                            if ($doBackoff474) {
                                if ($backoff473 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff473);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e470) {
                                            
                                        }
                                    }
                                }
                                if ($backoff473 < 5000) $backoff473 *= 2;
                            }
                            $doBackoff474 = $backoff473 <= 32 || !$doBackoff474;
                        }
                        $commit468 = true;
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
                        catch (final fabric.worker.RetryException $e470) {
                            $commit468 = false;
                            continue $label467;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e470) {
                            $commit468 = false;
                            fabric.common.TransactionID $currentTid471 =
                              $tm472.getCurrentTid();
                            if ($e470.tid.isDescendantOf($currentTid471))
                                continue $label467;
                            if ($currentTid471.parent != null) {
                                $retry469 = false;
                                throw $e470;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e470) {
                            $commit468 = false;
                            if ($tm472.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid471 =
                              $tm472.getCurrentTid();
                            if ($e470.tid.isDescendantOf($currentTid471)) {
                                $retry469 = true;
                            }
                            else if ($currentTid471.parent != null) {
                                $retry469 = false;
                                throw $e470;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e470) {
                            $commit468 = false;
                            if ($tm472.checkForStaleObjects())
                                continue $label467;
                            $retry469 = false;
                            throw new fabric.worker.AbortException($e470);
                        }
                        finally {
                            if ($commit468) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e470) {
                                    $commit468 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e470) {
                                    $commit468 = false;
                                    fabric.common.TransactionID $currentTid471 =
                                      $tm472.getCurrentTid();
                                    if ($currentTid471 != null) {
                                        if ($e470.tid.equals($currentTid471) ||
                                              !$e470.tid.isDescendantOf(
                                                           $currentTid471)) {
                                            throw $e470;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit468 && $retry469) {
                                {  }
                                continue $label467;
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
                    fabric.worker.transaction.TransactionManager $tm481 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled484 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff482 = 1;
                    boolean $doBackoff483 = true;
                    boolean $retry478 = true;
                    $label476: for (boolean $commit477 = false; !$commit477; ) {
                        if ($backoffEnabled484) {
                            if ($doBackoff483) {
                                if ($backoff482 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff482);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e479) {
                                            
                                        }
                                    }
                                }
                                if ($backoff482 < 5000) $backoff482 *= 2;
                            }
                            $doBackoff483 = $backoff482 <= 32 || !$doBackoff483;
                        }
                        $commit477 = true;
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
                                    fabric.common.TransactionID $currentTid480 =
                                      $tm481.getCurrentTid();
                                    if ($currentTid480 != null) {
                                        if ($e479.tid.equals($currentTid480) ||
                                              !$e479.tid.isDescendantOf(
                                                           $currentTid480)) {
                                            throw $e479;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit477 && $retry478) {
                                {  }
                                continue $label476;
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
                      proxy$var485 = proxy;
                    fabric.worker.transaction.TransactionManager $tm491 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled494 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff492 = 1;
                    boolean $doBackoff493 = true;
                    boolean $retry488 = true;
                    $label486: for (boolean $commit487 = false; !$commit487; ) {
                        if ($backoffEnabled494) {
                            if ($doBackoff493) {
                                if ($backoff492 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff492);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e489) {
                                            
                                        }
                                    }
                                }
                                if ($backoff492 < 5000) $backoff492 *= 2;
                            }
                            $doBackoff493 = $backoff492 <= 32 || !$doBackoff493;
                        }
                        $commit487 = true;
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
                        catch (final fabric.worker.RetryException $e489) {
                            $commit487 = false;
                            continue $label486;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e489) {
                            $commit487 = false;
                            fabric.common.TransactionID $currentTid490 =
                              $tm491.getCurrentTid();
                            if ($e489.tid.isDescendantOf($currentTid490))
                                continue $label486;
                            if ($currentTid490.parent != null) {
                                $retry488 = false;
                                throw $e489;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e489) {
                            $commit487 = false;
                            if ($tm491.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid490 =
                              $tm491.getCurrentTid();
                            if ($e489.tid.isDescendantOf($currentTid490)) {
                                $retry488 = true;
                            }
                            else if ($currentTid490.parent != null) {
                                $retry488 = false;
                                throw $e489;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e489) {
                            $commit487 = false;
                            if ($tm491.checkForStaleObjects())
                                continue $label486;
                            $retry488 = false;
                            throw new fabric.worker.AbortException($e489);
                        }
                        finally {
                            if ($commit487) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e489) {
                                    $commit487 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e489) {
                                    $commit487 = false;
                                    fabric.common.TransactionID $currentTid490 =
                                      $tm491.getCurrentTid();
                                    if ($currentTid490 != null) {
                                        if ($e489.tid.equals($currentTid490) ||
                                              !$e489.tid.isDescendantOf(
                                                           $currentTid490)) {
                                            throw $e489;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit487 && $retry488) {
                                { proxy = proxy$var485; }
                                continue $label486;
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
                        fabric.worker.transaction.TransactionManager $tm500 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled503 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff501 = 1;
                        boolean $doBackoff502 = true;
                        boolean $retry497 = true;
                        $label495: for (boolean $commit496 = false; !$commit496;
                                        ) {
                            if ($backoffEnabled503) {
                                if ($doBackoff502) {
                                    if ($backoff501 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff501);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e498) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff501 < 5000) $backoff501 *= 2;
                                }
                                $doBackoff502 = $backoff501 <= 32 ||
                                                  !$doBackoff502;
                            }
                            $commit496 = true;
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
                            catch (final fabric.worker.RetryException $e498) {
                                $commit496 = false;
                                continue $label495;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e498) {
                                $commit496 = false;
                                fabric.common.TransactionID $currentTid499 =
                                  $tm500.getCurrentTid();
                                if ($e498.tid.isDescendantOf($currentTid499))
                                    continue $label495;
                                if ($currentTid499.parent != null) {
                                    $retry497 = false;
                                    throw $e498;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e498) {
                                $commit496 = false;
                                if ($tm500.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid499 =
                                  $tm500.getCurrentTid();
                                if ($e498.tid.isDescendantOf($currentTid499)) {
                                    $retry497 = true;
                                }
                                else if ($currentTid499.parent != null) {
                                    $retry497 = false;
                                    throw $e498;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e498) {
                                $commit496 = false;
                                if ($tm500.checkForStaleObjects())
                                    continue $label495;
                                $retry497 = false;
                                throw new fabric.worker.AbortException($e498);
                            }
                            finally {
                                if ($commit496) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e498) {
                                        $commit496 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e498) {
                                        $commit496 = false;
                                        fabric.common.TransactionID
                                          $currentTid499 =
                                          $tm500.getCurrentTid();
                                        if ($currentTid499 != null) {
                                            if ($e498.tid.equals(
                                                            $currentTid499) ||
                                                  !$e498.tid.
                                                  isDescendantOf(
                                                    $currentTid499)) {
                                                throw $e498;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit496 && $retry497) {
                                    {  }
                                    continue $label495;
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
    public static final long jlc$SourceLastModified$fabil = 1523309338000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
