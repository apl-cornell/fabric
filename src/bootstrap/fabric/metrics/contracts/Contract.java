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
                final fabric.worker.Store local =
                  fabric.worker.Worker.getWorker().getLocalStore();
                if (!this.get$observing())
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
                this.get$lock().acquireOptimistic();
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
        
        public static final byte[] $classHash = new byte[] { 2, 77, -45, -104,
        72, 123, 39, 11, -21, 36, 41, -64, -59, -102, 64, 124, 61, -26, 13,
        -110, 10, 107, -11, 25, -51, 55, 118, -8, 98, 30, 90, -71 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1520116324000L;
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
                          $getProxy(
                            (java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                parent)) instanceof fabric.metrics.contracts.Contract) {
                        tm.
                          registerDelayedExtension(
                            parent,
                            ((fabric.metrics.contracts.Contract)
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
    
    public static final byte[] $classHash = new byte[] { 70, 98, 18, -55, -98,
    22, -101, 65, -40, -3, -18, -72, -15, 96, 42, -88, -116, 99, -91, -31, 76,
    102, 78, 52, 8, 105, 110, 125, 37, 47, -85, 110 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520116324000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
