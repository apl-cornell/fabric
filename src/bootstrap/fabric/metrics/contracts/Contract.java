package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.warranties.WarrantyComp;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.metrics.util.ReconfigLock;
import fabric.metrics.util.Subject;
import fabric.util.Iterator;
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
    
    public fabric.metrics.util.ReconfigLock set$lock(fabric.metrics.util.ReconfigLock val);
    
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
    public abstract void acquireReconfigLocks();
    
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
        
        public boolean get$observing();
        
        public boolean set$observing(boolean val);
        
        /**
     * @param target
     *        the {@link Contract} this proxies
     */
        public ProxyContract fabric$metrics$contracts$Contract$ProxyContract$(
          fabric.metrics.contracts.Contract target);
        
        public void activate();
        
        public boolean refresh(boolean asyncExtension);
        
        public boolean implies(fabric.metrics.Metric otherMetric,
                               double otherRate, double otherBase);
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects();
        
        public fabric.metrics.contracts.Contract and(fabric.metrics.contracts.Contract other);
        
        /**
     * {@inheritDoc}
     *
     * Stops observing any evidence used by the current enforcement policy (by
     * unapplying the policy).
     */
        public void removeObserver(fabric.metrics.util.Observer obs);
        
        public void acquireReconfigLocks();
        
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
            
            public boolean get$observing() {
                return ((fabric.metrics.contracts.Contract.ProxyContract._Impl)
                          fetch()).get$observing();
            }
            
            public boolean set$observing(boolean val) {
                return ((fabric.metrics.contracts.Contract.ProxyContract._Impl)
                          fetch()).set$observing(val);
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
            
            public boolean get$observing() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.observing;
            }
            
            public boolean set$observing(boolean val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.observing = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            protected boolean observing;
            
            /**
     * @param target
     *        the {@link Contract} this proxies
     */
            public ProxyContract
              fabric$metrics$contracts$Contract$ProxyContract$(
              fabric.metrics.contracts.Contract target) {
                this.set$target(target);
                fabric$metrics$contracts$Contract$();
                this.set$observing(false);
                return (ProxyContract) this.$getProxy();
            }
            
            public void activate() {
                fabric.metrics.contracts.Contract.ProxyContract._Impl.
                  static_activate((ProxyContract) this.$getProxy());
            }
            
            private static void static_activate(ProxyContract tmp) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    if (tmp.isActivated()) return;
                }
                else {
                    {
                        fabric.worker.transaction.TransactionManager $tm373 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled376 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff374 = 1;
                        boolean $doBackoff375 = true;
                        boolean $retry370 = true;
                        $label368: for (boolean $commit369 = false; !$commit369;
                                        ) {
                            if ($backoffEnabled376) {
                                if ($doBackoff375) {
                                    if ($backoff374 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff374);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e371) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff374 < 5000) $backoff374 *= 2;
                                }
                                $doBackoff375 = $backoff374 <= 32 ||
                                                  !$doBackoff375;
                            }
                            $commit369 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { if (tmp.isActivated()) return; }
                            catch (final fabric.worker.RetryException $e371) {
                                $commit369 = false;
                                continue $label368;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e371) {
                                $commit369 = false;
                                fabric.common.TransactionID $currentTid372 =
                                  $tm373.getCurrentTid();
                                if ($e371.tid.isDescendantOf($currentTid372))
                                    continue $label368;
                                if ($currentTid372.parent != null) {
                                    $retry370 = false;
                                    throw $e371;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e371) {
                                $commit369 = false;
                                if ($tm373.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid372 =
                                  $tm373.getCurrentTid();
                                if ($e371.tid.isDescendantOf($currentTid372)) {
                                    $retry370 = true;
                                }
                                else if ($currentTid372.parent != null) {
                                    $retry370 = false;
                                    throw $e371;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e371) {
                                $commit369 = false;
                                if ($tm373.checkForStaleObjects())
                                    continue $label368;
                                $retry370 = false;
                                throw new fabric.worker.AbortException($e371);
                            }
                            finally {
                                if ($commit369) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e371) {
                                        $commit369 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e371) {
                                        $commit369 = false;
                                        fabric.common.TransactionID
                                          $currentTid372 =
                                          $tm373.getCurrentTid();
                                        if ($currentTid372 != null) {
                                            if ($e371.tid.equals(
                                                            $currentTid372) ||
                                                  !$e371.tid.
                                                  isDescendantOf(
                                                    $currentTid372)) {
                                                throw $e371;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit369 && $retry370) {
                                    {  }
                                    continue $label368;
                                }
                            }
                        }
                    }
                }
                tmp.get$target().activate();
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    if (tmp.get$target().valid()) {
                        tmp.set$$expiry((long) tmp.get$target().getExpiry());
                        tmp.get$target().addObserver(tmp);
                        tmp.set$observing(true);
                    }
                    fabric.metrics.contracts.Contract._Impl.static_activate(
                                                              tmp);
                }
                else {
                    {
                        fabric.worker.transaction.TransactionManager $tm382 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled385 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff383 = 1;
                        boolean $doBackoff384 = true;
                        boolean $retry379 = true;
                        $label377: for (boolean $commit378 = false; !$commit378;
                                        ) {
                            if ($backoffEnabled385) {
                                if ($doBackoff384) {
                                    if ($backoff383 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff383);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e380) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff383 < 5000) $backoff383 *= 2;
                                }
                                $doBackoff384 = $backoff383 <= 32 ||
                                                  !$doBackoff384;
                            }
                            $commit378 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                if (tmp.get$target().valid()) {
                                    tmp.set$$expiry(
                                          (long) tmp.get$target().getExpiry());
                                    tmp.get$target().addObserver(tmp);
                                    tmp.set$observing(true);
                                }
                                fabric.metrics.contracts.Contract._Impl.
                                  static_activate(tmp);
                            }
                            catch (final fabric.worker.RetryException $e380) {
                                $commit378 = false;
                                continue $label377;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e380) {
                                $commit378 = false;
                                fabric.common.TransactionID $currentTid381 =
                                  $tm382.getCurrentTid();
                                if ($e380.tid.isDescendantOf($currentTid381))
                                    continue $label377;
                                if ($currentTid381.parent != null) {
                                    $retry379 = false;
                                    throw $e380;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e380) {
                                $commit378 = false;
                                if ($tm382.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid381 =
                                  $tm382.getCurrentTid();
                                if ($e380.tid.isDescendantOf($currentTid381)) {
                                    $retry379 = true;
                                }
                                else if ($currentTid381.parent != null) {
                                    $retry379 = false;
                                    throw $e380;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e380) {
                                $commit378 = false;
                                if ($tm382.checkForStaleObjects())
                                    continue $label377;
                                $retry379 = false;
                                throw new fabric.worker.AbortException($e380);
                            }
                            finally {
                                if ($commit378) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e380) {
                                        $commit378 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e380) {
                                        $commit378 = false;
                                        fabric.common.TransactionID
                                          $currentTid381 =
                                          $tm382.getCurrentTid();
                                        if ($currentTid381 != null) {
                                            if ($e380.tid.equals(
                                                            $currentTid381) ||
                                                  !$e380.tid.
                                                  isDescendantOf(
                                                    $currentTid381)) {
                                                throw $e380;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit378 && $retry379) {
                                    {  }
                                    continue $label377;
                                }
                            }
                        }
                    }
                }
            }
            
            public boolean refresh(boolean asyncExtension) {
                if (!isActivated()) { return false; }
                long currentTime = java.lang.System.currentTimeMillis();
                if (this.get$observing()) {
                    long curExpiry = this.get$target().getExpiry();
                    if (curExpiry < currentTime) {
                        this.get$target().removeObserver((ProxyContract)
                                                           this.$getProxy());
                        this.set$observing(false);
                    }
                    boolean result = update(curExpiry);
                    return result;
                }
                return false;
            }
            
            public boolean implies(fabric.metrics.Metric otherMetric,
                                   double otherRate, double otherBase) {
                return this.get$target().implies(otherMetric, otherRate,
                                                 otherBase);
            }
            
            public fabric.lang.arrays.ObjectArray getLeafSubjects() {
                if (!this.get$observing())
                    return (fabric.lang.arrays.ObjectArray)
                             new fabric.lang.arrays.ObjectArray._Impl(
                               this.$getStore()).
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
            
            /**
     * {@inheritDoc}
     *
     * Stops observing any evidence used by the current enforcement policy (by
     * unapplying the policy).
     */
            public void removeObserver(fabric.metrics.util.Observer obs) {
                fabric.metrics.contracts.Contract.ProxyContract._Impl.
                  static_removeObserver((ProxyContract) this.$getProxy(), obs);
            }
            
            private static void static_removeObserver(
              ProxyContract tmp, fabric.metrics.util.Observer obs) {
                if (fabric.worker.transaction.TransactionManager.getInstance().
                      inTxn()) {
                    fabric.metrics.contracts.Contract._Impl.
                      static_removeObserver(tmp, obs);
                    if (!tmp.isObserved() && tmp.get$observing()) {
                        tmp.get$target().removeObserver(tmp);
                        tmp.set$observing(false);
                    }
                }
                else {
                    {
                        fabric.worker.transaction.TransactionManager $tm391 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled394 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff392 = 1;
                        boolean $doBackoff393 = true;
                        boolean $retry388 = true;
                        $label386: for (boolean $commit387 = false; !$commit387;
                                        ) {
                            if ($backoffEnabled394) {
                                if ($doBackoff393) {
                                    if ($backoff392 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff392);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e389) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff392 < 5000) $backoff392 *= 2;
                                }
                                $doBackoff393 = $backoff392 <= 32 ||
                                                  !$doBackoff393;
                            }
                            $commit387 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.Contract._Impl.
                                  static_removeObserver(tmp, obs);
                                if (!tmp.isObserved() && tmp.get$observing()) {
                                    tmp.get$target().removeObserver(tmp);
                                    tmp.set$observing(false);
                                }
                            }
                            catch (final fabric.worker.RetryException $e389) {
                                $commit387 = false;
                                continue $label386;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e389) {
                                $commit387 = false;
                                fabric.common.TransactionID $currentTid390 =
                                  $tm391.getCurrentTid();
                                if ($e389.tid.isDescendantOf($currentTid390))
                                    continue $label386;
                                if ($currentTid390.parent != null) {
                                    $retry388 = false;
                                    throw $e389;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e389) {
                                $commit387 = false;
                                if ($tm391.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid390 =
                                  $tm391.getCurrentTid();
                                if ($e389.tid.isDescendantOf($currentTid390)) {
                                    $retry388 = true;
                                }
                                else if ($currentTid390.parent != null) {
                                    $retry388 = false;
                                    throw $e389;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e389) {
                                $commit387 = false;
                                if ($tm391.checkForStaleObjects())
                                    continue $label386;
                                $retry388 = false;
                                throw new fabric.worker.AbortException($e389);
                            }
                            finally {
                                if ($commit387) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e389) {
                                        $commit387 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e389) {
                                        $commit387 = false;
                                        fabric.common.TransactionID
                                          $currentTid390 =
                                          $tm391.getCurrentTid();
                                        if ($currentTid390 != null) {
                                            if ($e389.tid.equals(
                                                            $currentTid390) ||
                                                  !$e389.tid.
                                                  isDescendantOf(
                                                    $currentTid390)) {
                                                throw $e389;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit387 && $retry388) {
                                    {  }
                                    continue $label386;
                                }
                            }
                        }
                    }
                }
            }
            
            public void acquireReconfigLocks() {
                this.get$lock().acquire();
                if (this.get$observing())
                    this.get$target().acquireReconfigLocks();
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
                          proxy$var395 = proxy;
                        fabric.worker.transaction.TransactionManager $tm401 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled404 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff402 = 1;
                        boolean $doBackoff403 = true;
                        boolean $retry398 = true;
                        $label396: for (boolean $commit397 = false; !$commit397;
                                        ) {
                            if ($backoffEnabled404) {
                                if ($doBackoff403) {
                                    if ($backoff402 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff402);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e399) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff402 < 5000) $backoff402 *= 2;
                                }
                                $doBackoff403 = $backoff402 <= 32 ||
                                                  !$doBackoff403;
                            }
                            $commit397 = true;
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
                            catch (final fabric.worker.RetryException $e399) {
                                $commit397 = false;
                                continue $label396;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e399) {
                                $commit397 = false;
                                fabric.common.TransactionID $currentTid400 =
                                  $tm401.getCurrentTid();
                                if ($e399.tid.isDescendantOf($currentTid400))
                                    continue $label396;
                                if ($currentTid400.parent != null) {
                                    $retry398 = false;
                                    throw $e399;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e399) {
                                $commit397 = false;
                                if ($tm401.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid400 =
                                  $tm401.getCurrentTid();
                                if ($e399.tid.isDescendantOf($currentTid400)) {
                                    $retry398 = true;
                                }
                                else if ($currentTid400.parent != null) {
                                    $retry398 = false;
                                    throw $e399;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e399) {
                                $commit397 = false;
                                if ($tm401.checkForStaleObjects())
                                    continue $label396;
                                $retry398 = false;
                                throw new fabric.worker.AbortException($e399);
                            }
                            finally {
                                if ($commit397) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e399) {
                                        $commit397 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e399) {
                                        $commit397 = false;
                                        fabric.common.TransactionID
                                          $currentTid400 =
                                          $tm401.getCurrentTid();
                                        if ($currentTid400 != null) {
                                            if ($e399.tid.equals(
                                                            $currentTid400) ||
                                                  !$e399.tid.
                                                  isDescendantOf(
                                                    $currentTid400)) {
                                                throw $e399;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit397 && $retry398) {
                                    { proxy = proxy$var395; }
                                    continue $label396;
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
                out.writeBoolean(this.observing);
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
                this.observing = in.readBoolean();
            }
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.metrics.contracts.Contract.ProxyContract._Impl src =
                  (fabric.metrics.contracts.Contract.ProxyContract._Impl) other;
                this.target = src.target;
                this.observing = src.observing;
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
        
        public static final byte[] $classHash = new byte[] { -51, -39, -106,
        -103, 60, 64, 103, -64, -115, -73, 31, -115, 43, -116, -12, 44, -128,
        25, -56, -111, -102, 48, 35, -116, -5, -72, -81, -59, 9, -7, -28,
        -115 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1519504590000L;
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
                          $getProxy((java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.$unwrap(parent)) instanceof fabric.metrics.contracts.MetricContract) {
                        tm.
                          registerDelayedExtension(
                            parent,
                            ((fabric.metrics.contracts.MetricContract)
                               fabric.lang.Object._Proxy.$getProxy(parent)).
                                get$$expiry());
                    }
                }
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINE,
                    "SYNCH EXTENSION OF {0} IN {1}",
                    new java.lang.Object[] { (java.lang.Object)
                                               fabric.lang.WrappedJavaInlineable.
                                               $unwrap(
                                                 (fabric.metrics.contracts.Contract)
                                                   this.$getProxy()),
                      fabric.worker.transaction.TransactionManager.
                        getInstance().
                        getCurrentLog() });
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
                                              this.$getProxy(),
                                            this.get$$expiry());
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
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm410 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled413 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff411 = 1;
                    boolean $doBackoff412 = true;
                    boolean $retry407 = true;
                    $label405: for (boolean $commit406 = false; !$commit406; ) {
                        if ($backoffEnabled413) {
                            if ($doBackoff412) {
                                if ($backoff411 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff411);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e408) {
                                            
                                        }
                                    }
                                }
                                if ($backoff411 < 5000) $backoff411 *= 2;
                            }
                            $doBackoff412 = $backoff411 <= 32 || !$doBackoff412;
                        }
                        $commit406 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated()) {
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
                            }
                        }
                        catch (final fabric.worker.RetryException $e408) {
                            $commit406 = false;
                            continue $label405;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e408) {
                            $commit406 = false;
                            fabric.common.TransactionID $currentTid409 =
                              $tm410.getCurrentTid();
                            if ($e408.tid.isDescendantOf($currentTid409))
                                continue $label405;
                            if ($currentTid409.parent != null) {
                                $retry407 = false;
                                throw $e408;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e408) {
                            $commit406 = false;
                            if ($tm410.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid409 =
                              $tm410.getCurrentTid();
                            if ($e408.tid.isDescendantOf($currentTid409)) {
                                $retry407 = true;
                            }
                            else if ($currentTid409.parent != null) {
                                $retry407 = false;
                                throw $e408;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e408) {
                            $commit406 = false;
                            if ($tm410.checkForStaleObjects())
                                continue $label405;
                            $retry407 = false;
                            throw new fabric.worker.AbortException($e408);
                        }
                        finally {
                            if ($commit406) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e408) {
                                    $commit406 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e408) {
                                    $commit406 = false;
                                    fabric.common.TransactionID $currentTid409 =
                                      $tm410.getCurrentTid();
                                    if ($currentTid409 != null) {
                                        if ($e408.tid.equals($currentTid409) ||
                                              !$e408.tid.isDescendantOf(
                                                           $currentTid409)) {
                                            throw $e408;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit406 && $retry407) {
                                {  }
                                continue $label405;
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
                if (!tmp.isObserved()) { tmp.set$$expiry((long) -1); }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm419 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled422 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff420 = 1;
                    boolean $doBackoff421 = true;
                    boolean $retry416 = true;
                    $label414: for (boolean $commit415 = false; !$commit415; ) {
                        if ($backoffEnabled422) {
                            if ($doBackoff421) {
                                if ($backoff420 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff420);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e417) {
                                            
                                        }
                                    }
                                }
                                if ($backoff420 < 5000) $backoff420 *= 2;
                            }
                            $doBackoff421 = $backoff420 <= 32 || !$doBackoff421;
                        }
                        $commit415 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.util.AbstractSubject._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved()) {
                                tmp.set$$expiry((long) -1);
                            }
                        }
                        catch (final fabric.worker.RetryException $e417) {
                            $commit415 = false;
                            continue $label414;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e417) {
                            $commit415 = false;
                            fabric.common.TransactionID $currentTid418 =
                              $tm419.getCurrentTid();
                            if ($e417.tid.isDescendantOf($currentTid418))
                                continue $label414;
                            if ($currentTid418.parent != null) {
                                $retry416 = false;
                                throw $e417;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e417) {
                            $commit415 = false;
                            if ($tm419.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid418 =
                              $tm419.getCurrentTid();
                            if ($e417.tid.isDescendantOf($currentTid418)) {
                                $retry416 = true;
                            }
                            else if ($currentTid418.parent != null) {
                                $retry416 = false;
                                throw $e417;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e417) {
                            $commit415 = false;
                            if ($tm419.checkForStaleObjects())
                                continue $label414;
                            $retry416 = false;
                            throw new fabric.worker.AbortException($e417);
                        }
                        finally {
                            if ($commit415) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e417) {
                                    $commit415 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e417) {
                                    $commit415 = false;
                                    fabric.common.TransactionID $currentTid418 =
                                      $tm419.getCurrentTid();
                                    if ($currentTid418 != null) {
                                        if ($e417.tid.equals($currentTid418) ||
                                              !$e417.tid.isDescendantOf(
                                                           $currentTid418)) {
                                            throw $e417;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit415 && $retry416) {
                                {  }
                                continue $label414;
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
                "RETRACTION OF {0} IN {1}",
                new java.lang.Object[] { (java.lang.Object)
                                           fabric.lang.WrappedJavaInlineable.
                                           $unwrap(
                                             (fabric.metrics.contracts.Contract)
                                               this.$getProxy()),
                  fabric.worker.transaction.TransactionManager.
                    getInstance().
                    getCurrentLog() });
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
                java.util.logging.Level.FINER,
                "ASYNC EXTENSION OF " +
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         tmp)) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentTid());
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.refresh(true);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm428 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled431 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff429 = 1;
                    boolean $doBackoff430 = true;
                    boolean $retry425 = true;
                    $label423: for (boolean $commit424 = false; !$commit424; ) {
                        if ($backoffEnabled431) {
                            if ($doBackoff430) {
                                if ($backoff429 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff429);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e426) {
                                            
                                        }
                                    }
                                }
                                if ($backoff429 < 5000) $backoff429 *= 2;
                            }
                            $doBackoff430 = $backoff429 <= 32 || !$doBackoff430;
                        }
                        $commit424 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.refresh(true); }
                        catch (final fabric.worker.RetryException $e426) {
                            $commit424 = false;
                            continue $label423;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e426) {
                            $commit424 = false;
                            fabric.common.TransactionID $currentTid427 =
                              $tm428.getCurrentTid();
                            if ($e426.tid.isDescendantOf($currentTid427))
                                continue $label423;
                            if ($currentTid427.parent != null) {
                                $retry425 = false;
                                throw $e426;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e426) {
                            $commit424 = false;
                            if ($tm428.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid427 =
                              $tm428.getCurrentTid();
                            if ($e426.tid.isDescendantOf($currentTid427)) {
                                $retry425 = true;
                            }
                            else if ($currentTid427.parent != null) {
                                $retry425 = false;
                                throw $e426;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e426) {
                            $commit424 = false;
                            if ($tm428.checkForStaleObjects())
                                continue $label423;
                            $retry425 = false;
                            throw new fabric.worker.AbortException($e426);
                        }
                        finally {
                            if ($commit424) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e426) {
                                    $commit424 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e426) {
                                    $commit424 = false;
                                    fabric.common.TransactionID $currentTid427 =
                                      $tm428.getCurrentTid();
                                    if ($currentTid427 != null) {
                                        if ($e426.tid.equals($currentTid427) ||
                                              !$e426.tid.isDescendantOf(
                                                           $currentTid427)) {
                                            throw $e426;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit424 && $retry425) {
                                {  }
                                continue $label423;
                            }
                        }
                    }
                }
            }
        }
        
        /**
   * Acquire reconfig locks starting from this contract.
   */
        public abstract void acquireReconfigLocks();
        
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
                      proxy$var432 = proxy;
                    fabric.worker.transaction.TransactionManager $tm438 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled441 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff439 = 1;
                    boolean $doBackoff440 = true;
                    boolean $retry435 = true;
                    $label433: for (boolean $commit434 = false; !$commit434; ) {
                        if ($backoffEnabled441) {
                            if ($doBackoff440) {
                                if ($backoff439 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff439);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e436) {
                                            
                                        }
                                    }
                                }
                                if ($backoff439 < 5000) $backoff439 *= 2;
                            }
                            $doBackoff440 = $backoff439 <= 32 || !$doBackoff440;
                        }
                        $commit434 = true;
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
                        catch (final fabric.worker.RetryException $e436) {
                            $commit434 = false;
                            continue $label433;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e436) {
                            $commit434 = false;
                            fabric.common.TransactionID $currentTid437 =
                              $tm438.getCurrentTid();
                            if ($e436.tid.isDescendantOf($currentTid437))
                                continue $label433;
                            if ($currentTid437.parent != null) {
                                $retry435 = false;
                                throw $e436;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e436) {
                            $commit434 = false;
                            if ($tm438.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid437 =
                              $tm438.getCurrentTid();
                            if ($e436.tid.isDescendantOf($currentTid437)) {
                                $retry435 = true;
                            }
                            else if ($currentTid437.parent != null) {
                                $retry435 = false;
                                throw $e436;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e436) {
                            $commit434 = false;
                            if ($tm438.checkForStaleObjects())
                                continue $label433;
                            $retry435 = false;
                            throw new fabric.worker.AbortException($e436);
                        }
                        finally {
                            if ($commit434) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e436) {
                                    $commit434 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e436) {
                                    $commit434 = false;
                                    fabric.common.TransactionID $currentTid437 =
                                      $tm438.getCurrentTid();
                                    if ($currentTid437 != null) {
                                        if ($e436.tid.equals($currentTid437) ||
                                              !$e436.tid.isDescendantOf(
                                                           $currentTid437)) {
                                            throw $e436;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit434 && $retry435) {
                                { proxy = proxy$var432; }
                                continue $label433;
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
            this.activated = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.Contract._Impl src =
              (fabric.metrics.contracts.Contract._Impl) other;
            this.lock = src.lock;
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
                        fabric.worker.transaction.TransactionManager $tm447 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled450 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff448 = 1;
                        boolean $doBackoff449 = true;
                        boolean $retry444 = true;
                        $label442: for (boolean $commit443 = false; !$commit443;
                                        ) {
                            if ($backoffEnabled450) {
                                if ($doBackoff449) {
                                    if ($backoff448 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff448);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e445) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff448 < 5000) $backoff448 *= 2;
                                }
                                $doBackoff449 = $backoff448 <= 32 ||
                                                  !$doBackoff449;
                            }
                            $commit443 = true;
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
                            catch (final fabric.worker.RetryException $e445) {
                                $commit443 = false;
                                continue $label442;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e445) {
                                $commit443 = false;
                                fabric.common.TransactionID $currentTid446 =
                                  $tm447.getCurrentTid();
                                if ($e445.tid.isDescendantOf($currentTid446))
                                    continue $label442;
                                if ($currentTid446.parent != null) {
                                    $retry444 = false;
                                    throw $e445;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e445) {
                                $commit443 = false;
                                if ($tm447.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid446 =
                                  $tm447.getCurrentTid();
                                if ($e445.tid.isDescendantOf($currentTid446)) {
                                    $retry444 = true;
                                }
                                else if ($currentTid446.parent != null) {
                                    $retry444 = false;
                                    throw $e445;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e445) {
                                $commit443 = false;
                                if ($tm447.checkForStaleObjects())
                                    continue $label442;
                                $retry444 = false;
                                throw new fabric.worker.AbortException($e445);
                            }
                            finally {
                                if ($commit443) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e445) {
                                        $commit443 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e445) {
                                        $commit443 = false;
                                        fabric.common.TransactionID
                                          $currentTid446 =
                                          $tm447.getCurrentTid();
                                        if ($currentTid446 != null) {
                                            if ($e445.tid.equals(
                                                            $currentTid446) ||
                                                  !$e445.tid.
                                                  isDescendantOf(
                                                    $currentTid446)) {
                                                throw $e445;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit443 && $retry444) {
                                    {  }
                                    continue $label442;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 126, -104, -12, -30,
    77, -42, -120, 69, -39, -20, -109, 0, 26, 68, -72, -109, -4, 28, -75, 57,
    82, -85, -104, -48, -17, -70, 37, -8, 15, -123, 84, 107 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519504590000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
