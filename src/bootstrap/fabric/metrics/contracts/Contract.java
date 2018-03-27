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
                          proxy$var377 = proxy;
                        fabric.worker.transaction.TransactionManager $tm383 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled386 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff384 = 1;
                        boolean $doBackoff385 = true;
                        boolean $retry380 = true;
                        $label378: for (boolean $commit379 = false; !$commit379;
                                        ) {
                            if ($backoffEnabled386) {
                                if ($doBackoff385) {
                                    if ($backoff384 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff384);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e381) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff384 < 5000) $backoff384 *= 2;
                                }
                                $doBackoff385 = $backoff384 <= 32 ||
                                                  !$doBackoff385;
                            }
                            $commit379 = true;
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
                            catch (final fabric.worker.RetryException $e381) {
                                $commit379 = false;
                                continue $label378;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e381) {
                                $commit379 = false;
                                fabric.common.TransactionID $currentTid382 =
                                  $tm383.getCurrentTid();
                                if ($e381.tid.isDescendantOf($currentTid382))
                                    continue $label378;
                                if ($currentTid382.parent != null) {
                                    $retry380 = false;
                                    throw $e381;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e381) {
                                $commit379 = false;
                                if ($tm383.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid382 =
                                  $tm383.getCurrentTid();
                                if ($e381.tid.isDescendantOf($currentTid382)) {
                                    $retry380 = true;
                                }
                                else if ($currentTid382.parent != null) {
                                    $retry380 = false;
                                    throw $e381;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e381) {
                                $commit379 = false;
                                if ($tm383.checkForStaleObjects())
                                    continue $label378;
                                $retry380 = false;
                                throw new fabric.worker.AbortException($e381);
                            }
                            finally {
                                if ($commit379) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e381) {
                                        $commit379 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e381) {
                                        $commit379 = false;
                                        fabric.common.TransactionID
                                          $currentTid382 =
                                          $tm383.getCurrentTid();
                                        if ($currentTid382 != null) {
                                            if ($e381.tid.equals(
                                                            $currentTid382) ||
                                                  !$e381.tid.
                                                  isDescendantOf(
                                                    $currentTid382)) {
                                                throw $e381;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit379 && $retry380) {
                                    { proxy = proxy$var377; }
                                    continue $label378;
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
          "H4sIAAAAAAAAALVYfYxUVxW/M7vMfrDLLgvLx3aBXZiiUJwRqlW6KguzLIwMstkBoktke/fNnd3Hvnnv9b07MKCrtdVANKFNpQhGiDFotd2CMa1GK6Y12nZTA5VYUJMC/zS2oSSi1hKj1nPuvTNv5s3sUIxOMvfe9+45955z7jm/c+6bvE5muA5ZmqYjuhHh+23mRvrpSDwxQB2XpWIGdd3t8HZYm1kbP/rG46nFQRJMkCaNmpapa9QYNl1OZiX20L00ajIe3TEY79lFGjRk3EzdMU6CuzbkHNJlW8b+UcPiapOy9R+7K3rkG7tbf1RDWoZIi24mOeW6FrNMznJ8iDRlWGaEOe76VIqlhshsk7FUkjk6NfQDQGiZQ6TN1UdNyrMOcweZaxl7kbDNzdrMEXvmX6L4FojtZDVuOSB+qxQ/y3UjmtBd3pMgobTOjJR7P/kCqU2QGWmDjgLhvERei6hYMdqP74G8UQcxnTTVWJ6ldlw3U5ws8XMUNA5vAQJgrcswPmYVtqo1KbwgbVIkg5qj0SR3dHMUSGdYWdiFk45pFwWieptq43SUDXOywE83IKeAqkGYBVk4afeTiZXgzDp8Z1Z0Wtc/9bHDnzM3m0ESAJlTTDNQ/npgWuxjGmRp5jBTY5KxaWXiKJ139lCQECBu9xFLmp98/kbvqsXPvSRp7qhAs21kD9P4sHZqZNZvO2Mr1tagGPW25eroCiWai1MdUDM9ORu8fV5hRZyM5CefG3zhMw88wa4FSWOchDTLyGbAq2ZrVsbWDeZsYiZzKGepOGlgZiom5uOkDsYJ3WTy7bZ02mU8TmoN8SpkiWcwURqWQBPVwVg301Z+bFM+JsY5mxAyB/6khpBgOyHxJCGBFwhZB240EB2zMiw6YmTZPnDvKPwZdbSxKMSto2tR19GiTtbkOhCpV+BF0LlRcHXuUI2Dl6hRBGSx/w9r5lCP1n2BAJh4iWal2Ah14byU72wYMCA8NltGijnDmnH4bJzMOXtc+E8D+rwLfissFIAz7/SjRTHvkeyGjTdOD78sfQ95lQE5UYJGlKCRgqCRvKDhAcfK7c8/gaRNGGgRgK4IQNdkIBeJnYw/Kfwp5IrAKyzeBIvfaxuUpy0nkyOBgNB0ruAXjgRuMA7wAgjStCL52U/ed2gpHGXO3lcLh4qkYX88eSgUhxGFIBnWWg6+8fczRycsL7I4CZcFfDknBuxSv9kcS2MpAERv+ZVd9JnhsxPhIIJNA9qHgqcCqCz271ESuD15EERrzEiQmWgDauBUHrka+Zhj7fPeCHeYhU2b9Aw0lk9AgZ8fT9onfn/uzbtFZslDbUsRJicZ7ykKb1ysRQTybM/22x3GgO61YwNff+z6wV3C8ECxrNKGYWxjENYU4tlyvvLS/X+4cvnU74LeYXHSYDsWB4xhqZxQZ/a78AvA/9/4xzDFF9gDWMcURHQVMMLGzZd74gFaGLAaSO+Gd5gZK6WndTpiMHSWf7bcufqZtw63yhM34I20n0NW3XoB7/3CDeSBl3e/s1gsE9AwW3km9MgkBM7xVl7vOHQ/ypH70oVFx1+kJ8D5AcBc/QATmESESYg4wzXCFh8Q7Wrf3IewWSqt1VnweX866Me86rnjUHTyWx2xT1yTOFBwR1yjuwIO7KRFkbLmiczbwaWhXwdJ3RBpFSmdmnwnBUADTxiCpOzG1MsEaS6ZL02wMpv0FMKt0x8KRdv6A8HDHxgjNY4bpe9LxwFDzEUjhcEgUwDnl1V/Dmfn2NjOzQWIGNwrWJaJdjk2K4QhgzhcCU6pZzJZjscuNriLkxCnzijjgqGdk+5b4h8SdohwzFXfC9APC7BcQYkgKtGmcpKm+mSREkUnT3Jw9IumKx9E6XPqwSMnU9u+u1om+bbSlLzRzGaeuviv30SOXZ2qAPMhVQx6G9bDft1lRexWUVp5HnP12qK1sfHXR+WeS3zy+al/sHVyatNy7dEgqSm4Rlk9V8rUU+oQjQ6DctTcXuIWXQWLzkRLDYElzxPS+7Dqe4vdQuJmxXMCjArZ2RGj+IiE6RvVQutUv9Z/RF74BuRK+Ngr9tpZJb4/jc02Tj4ofSysfCxc8LFw5Rwb9pRIFCSdhct+GPa+RMj651X/zfequnBRn9rNapHjqn/klmpXQKcBR89AjtmrilV26MhX340cPiJdUFb0y8qK6mIeWdUL4ZtFiGIgdFfbRXD0/+nMxLPfnzgYVLbezEndiGUZjJri+b4q5yLKxyFgcFgaajjxqPnMLTxtEHS+Tkifpfr3T2NubAbLfQpZ3qf67umNWyNEq8kjUrsPkbaKXqCQIKHKStiBdUMpC3xaRIsjZLKqaL4Xmz2guZ6xDV2WHRU1XwVi/wXEfkf1F29Pc2R5VfXnp9e8WLSJKnNfxCYHJQ4Ad4LRdDIr8o+LkOmDMDCVSM/SHc89fnPh2fCbNyV8+W9nRYR/nrxy7ULzotOiyKvFMlzAj/9aW35rLbmMCmGbCuZoRQUWwf8eQurWqX4NJ1v++3tEH1zg4UIufUJdS/6Xy+XyXtjp88IkBY/JU3rOWAwO+HwPNl/G5Ox7xMHXquTPJIcqQTepUcjTBjNH+Vgll6+BU8DhQ1XALinXweZhbAS4PZQrCB2UW+fVldUd1jaQdC2TYcUg5hZCDYG3GMPSqGcdeYXRrUjhg4oKwWO5imZJSDsUCS0CR4hYxe+/XWXuO9icAKtpKG9esFZPD1mjFQnlC3FEpn8QsvEt1b9yeyGOLOdVP3XLzIGPMmE+WUWnp7D5HhwwNVOCo5LcnbAcXCP6+1R/9+3JjSxrVL/qvUHT01XmfozNGU7quSW/OlU4iaKJhf5LcSUN14Njwg79N1T/7G1pKFh+pvqnb3kyhQhQAb/PcsaZAzJbDqssshDh+So2eRGbn4MJAK5LChtPdk9j8QEHiuNgPSGbjqq+fxqNy6ADUpkNEAYXSHzZ56tw2tRyG1X/kemtEfSgqBWbX4ptX6mi5QVspjiZL8v+4YrKQtJqLnvbC3nrjgqfe9RnSC32K3bq9S2r2qf51LOg7MOw4jt9sqV+/skdl2QSy39ibEiQ+nTWMIqvXUXjkA1VkC40apCXMFt0lzhZMN3diMuLpxgL27wqef7IyaxSHi4SJI6K6V4DeJd0+HRZHEhHaSPBoiPr4Nfwyb/Ovxmq335VfG8A+3f9oiVlNBy88cO2n3Z1sr8de7vvwEevOPZuOrXs9MSdUxd3/Qd71/ejpRcAAA==";
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
                    boolean unactivated$var387 = unactivated;
                    fabric.worker.transaction.TransactionManager $tm393 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled396 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff394 = 1;
                    boolean $doBackoff395 = true;
                    boolean $retry390 = true;
                    $label388: for (boolean $commit389 = false; !$commit389; ) {
                        if ($backoffEnabled396) {
                            if ($doBackoff395) {
                                if ($backoff394 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff394);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e391) {
                                            
                                        }
                                    }
                                }
                                if ($backoff394 < 5000) $backoff394 *= 2;
                            }
                            $doBackoff395 = $backoff394 <= 32 || !$doBackoff395;
                        }
                        $commit389 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { unactivated = !tmp.get$activated(); }
                        catch (final fabric.worker.RetryException $e391) {
                            $commit389 = false;
                            continue $label388;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e391) {
                            $commit389 = false;
                            fabric.common.TransactionID $currentTid392 =
                              $tm393.getCurrentTid();
                            if ($e391.tid.isDescendantOf($currentTid392))
                                continue $label388;
                            if ($currentTid392.parent != null) {
                                $retry390 = false;
                                throw $e391;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e391) {
                            $commit389 = false;
                            if ($tm393.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid392 =
                              $tm393.getCurrentTid();
                            if ($e391.tid.isDescendantOf($currentTid392)) {
                                $retry390 = true;
                            }
                            else if ($currentTid392.parent != null) {
                                $retry390 = false;
                                throw $e391;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e391) {
                            $commit389 = false;
                            if ($tm393.checkForStaleObjects())
                                continue $label388;
                            $retry390 = false;
                            throw new fabric.worker.AbortException($e391);
                        }
                        finally {
                            if ($commit389) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e391) {
                                    $commit389 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e391) {
                                    $commit389 = false;
                                    fabric.common.TransactionID $currentTid392 =
                                      $tm393.getCurrentTid();
                                    if ($currentTid392 != null) {
                                        if ($e391.tid.equals($currentTid392) ||
                                              !$e391.tid.isDescendantOf(
                                                           $currentTid392)) {
                                            throw $e391;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit389 && $retry390) {
                                { unactivated = unactivated$var387; }
                                continue $label388;
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
                        boolean unactivated$var397 = unactivated;
                        fabric.worker.transaction.TransactionManager $tm403 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled406 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff404 = 1;
                        boolean $doBackoff405 = true;
                        boolean $retry400 = true;
                        $label398: for (boolean $commit399 = false; !$commit399;
                                        ) {
                            if ($backoffEnabled406) {
                                if ($doBackoff405) {
                                    if ($backoff404 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff404);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e401) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff404 < 5000) $backoff404 *= 2;
                                }
                                $doBackoff405 = $backoff404 <= 32 ||
                                                  !$doBackoff405;
                            }
                            $commit399 = true;
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
                            catch (final fabric.worker.RetryException $e401) {
                                $commit399 = false;
                                continue $label398;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e401) {
                                $commit399 = false;
                                fabric.common.TransactionID $currentTid402 =
                                  $tm403.getCurrentTid();
                                if ($e401.tid.isDescendantOf($currentTid402))
                                    continue $label398;
                                if ($currentTid402.parent != null) {
                                    $retry400 = false;
                                    throw $e401;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e401) {
                                $commit399 = false;
                                if ($tm403.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid402 =
                                  $tm403.getCurrentTid();
                                if ($e401.tid.isDescendantOf($currentTid402)) {
                                    $retry400 = true;
                                }
                                else if ($currentTid402.parent != null) {
                                    $retry400 = false;
                                    throw $e401;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e401) {
                                $commit399 = false;
                                if ($tm403.checkForStaleObjects())
                                    continue $label398;
                                $retry400 = false;
                                throw new fabric.worker.AbortException($e401);
                            }
                            finally {
                                if ($commit399) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e401) {
                                        $commit399 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e401) {
                                        $commit399 = false;
                                        fabric.common.TransactionID
                                          $currentTid402 =
                                          $tm403.getCurrentTid();
                                        if ($currentTid402 != null) {
                                            if ($e401.tid.equals(
                                                            $currentTid402) ||
                                                  !$e401.tid.
                                                  isDescendantOf(
                                                    $currentTid402)) {
                                                throw $e401;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit399 && $retry400) {
                                    { unactivated = unactivated$var397; }
                                    continue $label398;
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
                    fabric.worker.transaction.TransactionManager $tm412 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled415 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff413 = 1;
                    boolean $doBackoff414 = true;
                    boolean $retry409 = true;
                    $label407: for (boolean $commit408 = false; !$commit408; ) {
                        if ($backoffEnabled415) {
                            if ($doBackoff414) {
                                if ($backoff413 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff413);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e410) {
                                            
                                        }
                                    }
                                }
                                if ($backoff413 < 5000) $backoff413 *= 2;
                            }
                            $doBackoff414 = $backoff413 <= 32 || !$doBackoff414;
                        }
                        $commit408 = true;
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
                        catch (final fabric.worker.RetryException $e410) {
                            $commit408 = false;
                            continue $label407;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e410) {
                            $commit408 = false;
                            fabric.common.TransactionID $currentTid411 =
                              $tm412.getCurrentTid();
                            if ($e410.tid.isDescendantOf($currentTid411))
                                continue $label407;
                            if ($currentTid411.parent != null) {
                                $retry409 = false;
                                throw $e410;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e410) {
                            $commit408 = false;
                            if ($tm412.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid411 =
                              $tm412.getCurrentTid();
                            if ($e410.tid.isDescendantOf($currentTid411)) {
                                $retry409 = true;
                            }
                            else if ($currentTid411.parent != null) {
                                $retry409 = false;
                                throw $e410;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e410) {
                            $commit408 = false;
                            if ($tm412.checkForStaleObjects())
                                continue $label407;
                            $retry409 = false;
                            throw new fabric.worker.AbortException($e410);
                        }
                        finally {
                            if ($commit408) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e410) {
                                    $commit408 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e410) {
                                    $commit408 = false;
                                    fabric.common.TransactionID $currentTid411 =
                                      $tm412.getCurrentTid();
                                    if ($currentTid411 != null) {
                                        if ($e410.tid.equals($currentTid411) ||
                                              !$e410.tid.isDescendantOf(
                                                           $currentTid411)) {
                                            throw $e410;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit408 && $retry409) {
                                {  }
                                continue $label407;
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
                    fabric.worker.transaction.TransactionManager $tm421 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled424 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff422 = 1;
                    boolean $doBackoff423 = true;
                    boolean $retry418 = true;
                    $label416: for (boolean $commit417 = false; !$commit417; ) {
                        if ($backoffEnabled424) {
                            if ($doBackoff423) {
                                if ($backoff422 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff422);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e419) {
                                            
                                        }
                                    }
                                }
                                if ($backoff422 < 5000) $backoff422 *= 2;
                            }
                            $doBackoff423 = $backoff422 <= 32 || !$doBackoff423;
                        }
                        $commit417 = true;
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
                        catch (final fabric.worker.RetryException $e419) {
                            $commit417 = false;
                            continue $label416;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e419) {
                            $commit417 = false;
                            fabric.common.TransactionID $currentTid420 =
                              $tm421.getCurrentTid();
                            if ($e419.tid.isDescendantOf($currentTid420))
                                continue $label416;
                            if ($currentTid420.parent != null) {
                                $retry418 = false;
                                throw $e419;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e419) {
                            $commit417 = false;
                            if ($tm421.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid420 =
                              $tm421.getCurrentTid();
                            if ($e419.tid.isDescendantOf($currentTid420)) {
                                $retry418 = true;
                            }
                            else if ($currentTid420.parent != null) {
                                $retry418 = false;
                                throw $e419;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e419) {
                            $commit417 = false;
                            if ($tm421.checkForStaleObjects())
                                continue $label416;
                            $retry418 = false;
                            throw new fabric.worker.AbortException($e419);
                        }
                        finally {
                            if ($commit417) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e419) {
                                    $commit417 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e419) {
                                    $commit417 = false;
                                    fabric.common.TransactionID $currentTid420 =
                                      $tm421.getCurrentTid();
                                    if ($currentTid420 != null) {
                                        if ($e419.tid.equals($currentTid420) ||
                                              !$e419.tid.isDescendantOf(
                                                           $currentTid420)) {
                                            throw $e419;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit417 && $retry418) {
                                {  }
                                continue $label416;
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
                      proxy$var425 = proxy;
                    fabric.worker.transaction.TransactionManager $tm431 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled434 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff432 = 1;
                    boolean $doBackoff433 = true;
                    boolean $retry428 = true;
                    $label426: for (boolean $commit427 = false; !$commit427; ) {
                        if ($backoffEnabled434) {
                            if ($doBackoff433) {
                                if ($backoff432 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff432);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e429) {
                                            
                                        }
                                    }
                                }
                                if ($backoff432 < 5000) $backoff432 *= 2;
                            }
                            $doBackoff433 = $backoff432 <= 32 || !$doBackoff433;
                        }
                        $commit427 = true;
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
                        catch (final fabric.worker.RetryException $e429) {
                            $commit427 = false;
                            continue $label426;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e429) {
                            $commit427 = false;
                            fabric.common.TransactionID $currentTid430 =
                              $tm431.getCurrentTid();
                            if ($e429.tid.isDescendantOf($currentTid430))
                                continue $label426;
                            if ($currentTid430.parent != null) {
                                $retry428 = false;
                                throw $e429;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e429) {
                            $commit427 = false;
                            if ($tm431.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid430 =
                              $tm431.getCurrentTid();
                            if ($e429.tid.isDescendantOf($currentTid430)) {
                                $retry428 = true;
                            }
                            else if ($currentTid430.parent != null) {
                                $retry428 = false;
                                throw $e429;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e429) {
                            $commit427 = false;
                            if ($tm431.checkForStaleObjects())
                                continue $label426;
                            $retry428 = false;
                            throw new fabric.worker.AbortException($e429);
                        }
                        finally {
                            if ($commit427) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e429) {
                                    $commit427 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e429) {
                                    $commit427 = false;
                                    fabric.common.TransactionID $currentTid430 =
                                      $tm431.getCurrentTid();
                                    if ($currentTid430 != null) {
                                        if ($e429.tid.equals($currentTid430) ||
                                              !$e429.tid.isDescendantOf(
                                                           $currentTid430)) {
                                            throw $e429;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit427 && $retry428) {
                                { proxy = proxy$var425; }
                                continue $label426;
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
                        fabric.worker.transaction.TransactionManager $tm440 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled443 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff441 = 1;
                        boolean $doBackoff442 = true;
                        boolean $retry437 = true;
                        $label435: for (boolean $commit436 = false; !$commit436;
                                        ) {
                            if ($backoffEnabled443) {
                                if ($doBackoff442) {
                                    if ($backoff441 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff441);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e438) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff441 < 5000) $backoff441 *= 2;
                                }
                                $doBackoff442 = $backoff441 <= 32 ||
                                                  !$doBackoff442;
                            }
                            $commit436 = true;
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
                            catch (final fabric.worker.RetryException $e438) {
                                $commit436 = false;
                                continue $label435;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e438) {
                                $commit436 = false;
                                fabric.common.TransactionID $currentTid439 =
                                  $tm440.getCurrentTid();
                                if ($e438.tid.isDescendantOf($currentTid439))
                                    continue $label435;
                                if ($currentTid439.parent != null) {
                                    $retry437 = false;
                                    throw $e438;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e438) {
                                $commit436 = false;
                                if ($tm440.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid439 =
                                  $tm440.getCurrentTid();
                                if ($e438.tid.isDescendantOf($currentTid439)) {
                                    $retry437 = true;
                                }
                                else if ($currentTid439.parent != null) {
                                    $retry437 = false;
                                    throw $e438;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e438) {
                                $commit436 = false;
                                if ($tm440.checkForStaleObjects())
                                    continue $label435;
                                $retry437 = false;
                                throw new fabric.worker.AbortException($e438);
                            }
                            finally {
                                if ($commit436) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e438) {
                                        $commit436 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e438) {
                                        $commit436 = false;
                                        fabric.common.TransactionID
                                          $currentTid439 =
                                          $tm440.getCurrentTid();
                                        if ($currentTid439 != null) {
                                            if ($e438.tid.equals(
                                                            $currentTid439) ||
                                                  !$e438.tid.
                                                  isDescendantOf(
                                                    $currentTid439)) {
                                                throw $e438;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit436 && $retry437) {
                                    {  }
                                    continue $label435;
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
