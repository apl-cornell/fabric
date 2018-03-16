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
                            try { if (tmp.isActivated()) return; }
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
                                    {  }
                                    continue $label378;
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
                        fabric.worker.transaction.TransactionManager $tm392 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled395 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff393 = 1;
                        boolean $doBackoff394 = true;
                        boolean $retry389 = true;
                        $label387: for (boolean $commit388 = false; !$commit388;
                                        ) {
                            if ($backoffEnabled395) {
                                if ($doBackoff394) {
                                    if ($backoff393 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff393);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e390) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff393 < 5000) $backoff393 *= 2;
                                }
                                $doBackoff394 = $backoff393 <= 32 ||
                                                  !$doBackoff394;
                            }
                            $commit388 = true;
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
                            catch (final fabric.worker.RetryException $e390) {
                                $commit388 = false;
                                continue $label387;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e390) {
                                $commit388 = false;
                                fabric.common.TransactionID $currentTid391 =
                                  $tm392.getCurrentTid();
                                if ($e390.tid.isDescendantOf($currentTid391))
                                    continue $label387;
                                if ($currentTid391.parent != null) {
                                    $retry389 = false;
                                    throw $e390;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e390) {
                                $commit388 = false;
                                if ($tm392.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid391 =
                                  $tm392.getCurrentTid();
                                if ($e390.tid.isDescendantOf($currentTid391)) {
                                    $retry389 = true;
                                }
                                else if ($currentTid391.parent != null) {
                                    $retry389 = false;
                                    throw $e390;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e390) {
                                $commit388 = false;
                                if ($tm392.checkForStaleObjects())
                                    continue $label387;
                                $retry389 = false;
                                throw new fabric.worker.AbortException($e390);
                            }
                            finally {
                                if ($commit388) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e390) {
                                        $commit388 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e390) {
                                        $commit388 = false;
                                        fabric.common.TransactionID
                                          $currentTid391 =
                                          $tm392.getCurrentTid();
                                        if ($currentTid391 != null) {
                                            if ($e390.tid.equals(
                                                            $currentTid391) ||
                                                  !$e390.tid.
                                                  isDescendantOf(
                                                    $currentTid391)) {
                                                throw $e390;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit388 && $retry389) {
                                    {  }
                                    continue $label387;
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
                                fabric.metrics.contracts.Contract._Impl.
                                  static_removeObserver(tmp, obs);
                                if (!tmp.isObserved() && tmp.get$observing()) {
                                    tmp.get$target().removeObserver(tmp);
                                    tmp.set$observing(false);
                                }
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
                                    {  }
                                    continue $label396;
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
                          proxy$var405 = proxy;
                        fabric.worker.transaction.TransactionManager $tm411 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled414 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff412 = 1;
                        boolean $doBackoff413 = true;
                        boolean $retry408 = true;
                        $label406: for (boolean $commit407 = false; !$commit407;
                                        ) {
                            if ($backoffEnabled414) {
                                if ($doBackoff413) {
                                    if ($backoff412 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff412);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e409) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff412 < 5000) $backoff412 *= 2;
                                }
                                $doBackoff413 = $backoff412 <= 32 ||
                                                  !$doBackoff413;
                            }
                            $commit407 = true;
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
                            catch (final fabric.worker.RetryException $e409) {
                                $commit407 = false;
                                continue $label406;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e409) {
                                $commit407 = false;
                                fabric.common.TransactionID $currentTid410 =
                                  $tm411.getCurrentTid();
                                if ($e409.tid.isDescendantOf($currentTid410))
                                    continue $label406;
                                if ($currentTid410.parent != null) {
                                    $retry408 = false;
                                    throw $e409;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e409) {
                                $commit407 = false;
                                if ($tm411.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid410 =
                                  $tm411.getCurrentTid();
                                if ($e409.tid.isDescendantOf($currentTid410)) {
                                    $retry408 = true;
                                }
                                else if ($currentTid410.parent != null) {
                                    $retry408 = false;
                                    throw $e409;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e409) {
                                $commit407 = false;
                                if ($tm411.checkForStaleObjects())
                                    continue $label406;
                                $retry408 = false;
                                throw new fabric.worker.AbortException($e409);
                            }
                            finally {
                                if ($commit407) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e409) {
                                        $commit407 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e409) {
                                        $commit407 = false;
                                        fabric.common.TransactionID
                                          $currentTid410 =
                                          $tm411.getCurrentTid();
                                        if ($currentTid410 != null) {
                                            if ($e409.tid.equals(
                                                            $currentTid410) ||
                                                  !$e409.tid.
                                                  isDescendantOf(
                                                    $currentTid410)) {
                                                throw $e409;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit407 && $retry408) {
                                    { proxy = proxy$var405; }
                                    continue $label406;
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
        
        public static final byte[] $classHash = new byte[] { 74, 90, 20, 14,
        100, 72, 66, 82, 62, 106, -94, -90, -89, 58, 10, 75, 16, 9, 75, -120,
        -66, 81, 69, -117, -34, -113, 2, -83, 127, 120, 65, -97 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1521041510000L;
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
                        tm.registerDelayedExtension(parent);
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
                    fabric.worker.transaction.TransactionManager $tm420 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled423 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff421 = 1;
                    boolean $doBackoff422 = true;
                    boolean $retry417 = true;
                    $label415: for (boolean $commit416 = false; !$commit416; ) {
                        if ($backoffEnabled423) {
                            if ($doBackoff422) {
                                if ($backoff421 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff421);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e418) {
                                            
                                        }
                                    }
                                }
                                if ($backoff421 < 5000) $backoff421 *= 2;
                            }
                            $doBackoff422 = $backoff421 <= 32 || !$doBackoff422;
                        }
                        $commit416 = true;
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
                        catch (final fabric.worker.RetryException $e418) {
                            $commit416 = false;
                            continue $label415;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e418) {
                            $commit416 = false;
                            fabric.common.TransactionID $currentTid419 =
                              $tm420.getCurrentTid();
                            if ($e418.tid.isDescendantOf($currentTid419))
                                continue $label415;
                            if ($currentTid419.parent != null) {
                                $retry417 = false;
                                throw $e418;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e418) {
                            $commit416 = false;
                            if ($tm420.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid419 =
                              $tm420.getCurrentTid();
                            if ($e418.tid.isDescendantOf($currentTid419)) {
                                $retry417 = true;
                            }
                            else if ($currentTid419.parent != null) {
                                $retry417 = false;
                                throw $e418;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e418) {
                            $commit416 = false;
                            if ($tm420.checkForStaleObjects())
                                continue $label415;
                            $retry417 = false;
                            throw new fabric.worker.AbortException($e418);
                        }
                        finally {
                            if ($commit416) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e418) {
                                    $commit416 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e418) {
                                    $commit416 = false;
                                    fabric.common.TransactionID $currentTid419 =
                                      $tm420.getCurrentTid();
                                    if ($currentTid419 != null) {
                                        if ($e418.tid.equals($currentTid419) ||
                                              !$e418.tid.isDescendantOf(
                                                           $currentTid419)) {
                                            throw $e418;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit416 && $retry417) {
                                {  }
                                continue $label415;
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
                    fabric.worker.transaction.TransactionManager $tm429 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled432 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff430 = 1;
                    boolean $doBackoff431 = true;
                    boolean $retry426 = true;
                    $label424: for (boolean $commit425 = false; !$commit425; ) {
                        if ($backoffEnabled432) {
                            if ($doBackoff431) {
                                if ($backoff430 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff430);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e427) {
                                            
                                        }
                                    }
                                }
                                if ($backoff430 < 5000) $backoff430 *= 2;
                            }
                            $doBackoff431 = $backoff430 <= 32 || !$doBackoff431;
                        }
                        $commit425 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.util.AbstractSubject._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved()) {
                                tmp.set$$expiry((long) -1);
                            }
                        }
                        catch (final fabric.worker.RetryException $e427) {
                            $commit425 = false;
                            continue $label424;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e427) {
                            $commit425 = false;
                            fabric.common.TransactionID $currentTid428 =
                              $tm429.getCurrentTid();
                            if ($e427.tid.isDescendantOf($currentTid428))
                                continue $label424;
                            if ($currentTid428.parent != null) {
                                $retry426 = false;
                                throw $e427;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e427) {
                            $commit425 = false;
                            if ($tm429.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid428 =
                              $tm429.getCurrentTid();
                            if ($e427.tid.isDescendantOf($currentTid428)) {
                                $retry426 = true;
                            }
                            else if ($currentTid428.parent != null) {
                                $retry426 = false;
                                throw $e427;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e427) {
                            $commit425 = false;
                            if ($tm429.checkForStaleObjects())
                                continue $label424;
                            $retry426 = false;
                            throw new fabric.worker.AbortException($e427);
                        }
                        finally {
                            if ($commit425) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e427) {
                                    $commit425 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e427) {
                                    $commit425 = false;
                                    fabric.common.TransactionID $currentTid428 =
                                      $tm429.getCurrentTid();
                                    if ($currentTid428 != null) {
                                        if ($e427.tid.equals($currentTid428) ||
                                              !$e427.tid.isDescendantOf(
                                                           $currentTid428)) {
                                            throw $e427;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit425 && $retry426) {
                                {  }
                                continue $label424;
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
                        try { tmp.refresh(true); }
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
                                {  }
                                continue $label433;
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
                      proxy$var442 = proxy;
                    fabric.worker.transaction.TransactionManager $tm448 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled451 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff449 = 1;
                    boolean $doBackoff450 = true;
                    boolean $retry445 = true;
                    $label443: for (boolean $commit444 = false; !$commit444; ) {
                        if ($backoffEnabled451) {
                            if ($doBackoff450) {
                                if ($backoff449 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff449);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e446) {
                                            
                                        }
                                    }
                                }
                                if ($backoff449 < 5000) $backoff449 *= 2;
                            }
                            $doBackoff450 = $backoff449 <= 32 || !$doBackoff450;
                        }
                        $commit444 = true;
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
                        catch (final fabric.worker.RetryException $e446) {
                            $commit444 = false;
                            continue $label443;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e446) {
                            $commit444 = false;
                            fabric.common.TransactionID $currentTid447 =
                              $tm448.getCurrentTid();
                            if ($e446.tid.isDescendantOf($currentTid447))
                                continue $label443;
                            if ($currentTid447.parent != null) {
                                $retry445 = false;
                                throw $e446;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e446) {
                            $commit444 = false;
                            if ($tm448.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid447 =
                              $tm448.getCurrentTid();
                            if ($e446.tid.isDescendantOf($currentTid447)) {
                                $retry445 = true;
                            }
                            else if ($currentTid447.parent != null) {
                                $retry445 = false;
                                throw $e446;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e446) {
                            $commit444 = false;
                            if ($tm448.checkForStaleObjects())
                                continue $label443;
                            $retry445 = false;
                            throw new fabric.worker.AbortException($e446);
                        }
                        finally {
                            if ($commit444) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e446) {
                                    $commit444 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e446) {
                                    $commit444 = false;
                                    fabric.common.TransactionID $currentTid447 =
                                      $tm448.getCurrentTid();
                                    if ($currentTid447 != null) {
                                        if ($e446.tid.equals($currentTid447) ||
                                              !$e446.tid.isDescendantOf(
                                                           $currentTid447)) {
                                            throw $e446;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit444 && $retry445) {
                                { proxy = proxy$var442; }
                                continue $label443;
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
                        fabric.worker.transaction.TransactionManager $tm457 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled460 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff458 = 1;
                        boolean $doBackoff459 = true;
                        boolean $retry454 = true;
                        $label452: for (boolean $commit453 = false; !$commit453;
                                        ) {
                            if ($backoffEnabled460) {
                                if ($doBackoff459) {
                                    if ($backoff458 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff458);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e455) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff458 < 5000) $backoff458 *= 2;
                                }
                                $doBackoff459 = $backoff458 <= 32 ||
                                                  !$doBackoff459;
                            }
                            $commit453 = true;
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
                            catch (final fabric.worker.RetryException $e455) {
                                $commit453 = false;
                                continue $label452;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e455) {
                                $commit453 = false;
                                fabric.common.TransactionID $currentTid456 =
                                  $tm457.getCurrentTid();
                                if ($e455.tid.isDescendantOf($currentTid456))
                                    continue $label452;
                                if ($currentTid456.parent != null) {
                                    $retry454 = false;
                                    throw $e455;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e455) {
                                $commit453 = false;
                                if ($tm457.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid456 =
                                  $tm457.getCurrentTid();
                                if ($e455.tid.isDescendantOf($currentTid456)) {
                                    $retry454 = true;
                                }
                                else if ($currentTid456.parent != null) {
                                    $retry454 = false;
                                    throw $e455;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e455) {
                                $commit453 = false;
                                if ($tm457.checkForStaleObjects())
                                    continue $label452;
                                $retry454 = false;
                                throw new fabric.worker.AbortException($e455);
                            }
                            finally {
                                if ($commit453) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e455) {
                                        $commit453 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e455) {
                                        $commit453 = false;
                                        fabric.common.TransactionID
                                          $currentTid456 =
                                          $tm457.getCurrentTid();
                                        if ($currentTid456 != null) {
                                            if ($e455.tid.equals(
                                                            $currentTid456) ||
                                                  !$e455.tid.
                                                  isDescendantOf(
                                                    $currentTid456)) {
                                                throw $e455;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit453 && $retry454) {
                                    {  }
                                    continue $label452;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 9, -70, 47, 1, -72, 55,
    51, 78, 15, -61, 46, 54, 105, 116, -124, 13, -80, -68, 105, 70, 116, 3, -92,
    22, 55, -59, 49, 12, -63, 21, -126, -46 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521041510000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALUaa5AUxbl37w133APugBOPAw4MB+yKz8ApCssdLCzc5R4+jug6O9t7N97szDDTeywafJURKg9M6YmYUlKVXCpRUWMSykTFMiUaDZqUxjImVUZSKSOK/jDGJJXyke/r6X3c3OzcblWyVf19s939ff29u3tnj35IKiyTLE1IMUUNsL0GtQI9Uiwc6ZNMi8ZDqmRZg9AblWeXhw+d/lG8zU/8EVIrS5quKbKkRjWLkTmR66VxKahRFhzqD3ftIjUyEm6VrFFG/Ls2pU3Sbujq3hFVZ2KRafzvWRWcuPfahp+WkfphUq9oA0xiihzSNUbTbJjUJmkyRk1rYzxO48OkUaM0PkBNRVKVG2Cirg2TJksZ0SSWMqnVTy1dHceJTVbKoCZfM9OJ4usgtpmSmW6C+A22+CmmqMGIYrGuCKlMKFSNW7vJTaQ8QioSqjQCE1siGS2CnGOwB/th+iwFxDQTkkwzJOVjihZnZLGTIqtxx3aYAKRVScpG9exS5ZoEHaTJFkmVtJHgADMVbQSmVugpWIWR1oJMYVK1Iclj0giNMrLAOa/PHoJZNdwsSMJIs3Ma5wQ+a3X4LM9bH+685OCN2lbNT3wgc5zKKspfDURtDqJ+mqAm1WRqE9Z2Rg5JLccP+AmByc2OyfacJ7720eWr25590Z5zlsuc3tj1VGZReTI259VFoZXrylCMakO3FAyFKZpzr/aJka60AdHekuWIg4HM4LP9L1x9y0P0jJ/MCpNKWVdTSYiqRllPGopKzS1Uo6bEaDxMaqgWD/HxMKmC54iiUbu3N5GwKAuTcpV3Ver8O5goASzQRFXwrGgJPfNsSGyUP6cNQkgDNOIjxN9ISPdL8LyUkIrrGOkLjupJGoypKboHwjsIjUqmPBqEvDUVOWiZctBMaUyBSaILogiQFYRQZ6YkM4gS8RQAWYz/A8806tGwx+cDEy+W9TiNSRb4S8TOpj4V0mOrrsapGZXVg8fDZO7x+3j81GDMWxC33EI+8PkiZ7XIp51Iber+6NHoSTv2kFYYkJEltqABIWggK2ggIyjIVoupFYBiFYBiddSXDoSOhB/mEVRp8VTLsqsFdusNVWIJ3Uymic/HdZvH6XnogOPHoKBAzahdOXDNtusOLC2DmDX2lKMbYWqHM4NydScMTxKkRVSu33/6n48d2qfncomRjmkpPp0SU3Sp01CmLtM4lMAc+8526Vj0+L4OP5aXGrSIBLEJZaTNucaUVO3KlD20RkWEzEYbSCoOZWrVLDZq6ntyPTwA5iBosmMBjeUQkFfMSweMB9787Xvn870kU1zr86rwAGVdeQmNzOp56jbmbD9oUgrz3jrcd/c9H+7fxQ0PM5a5LdiBMASJLEEG6+bXX9z9x7f/PPm6P+csRiqNVExV5DTXpfEL+PigfY4NsxI7EENtDomK0J4tCQauvCInGxQHFQoUiG51DGlJPa4kFCmmUoyUT+uXrz32wcEG290q9NjGM8nqmRnk+hduIrecvPZfbZyNT8bNKWe/3DS74s3Ncd5omtJelCN962tn3/dr6QGIfKhXlnID5SWIcHsQ7sDzuC3WcLjWMXYBgqW2tRbxfjxIOKt/D26juVgcDh69vzW04Yyd9tlYRB5LXNL+CikvTc57KPmJf2nl835SNUwa+A4uaewKCeoXhMEw7MFWSHRGSN2U8an7qb15dGVzbZEzD/KWdWZBrtzAM87G51l24NuBA4ZoQiN1QFtOSGW5jSv+jqNzDYTz0j7CH9ZzkmUcrkCwkhvSz0iNYeoMpKRwhqhRkskUQ+/zdVYxiBpdHuNUzYy0O2oe93I/BQMklJEITMR5rXY+IrwoK2cLyrkO2sWEVP9S4EMucobc5SzDx06GZRPPavjtsoyI83aEd0a7rxrs3jkQ7t0Z7dkYGuztdwmQPlNJQo6Pi+MBPTDxjS8CByfs5LDPUMumHWPyaexzFF+2jq+dhlWWeK3CKXrefWzfUz/et98+YzRNPRF0a6nkI2989nLg8KmXXHaZyrgOpYIWNGkA2npCai4T+BwXk/Z5mRTBlinmrN3cH+4ZzJgRO8NCWUQRHhL2xuUq0fnQLgFJmMBXukh0VWkSNeSce2V45+ZeznLITYJZmXSIwQFnncCLXSS4xl0CqM1VhqmMQ6FNZ5n6kWmNYNYm8Pw8ppA3sN1zqribwapiuq5Sie8tDekCiSgCvFqKWfz0kFuff+rFCS0qcF/e+nmF0ZdJ1UVuqdobs6g5bhfBVgzeswsdu3ngTt42cSTe+8O1flGHd4CiTDfWqHScqnmLLsQ0mHat28EvG7mieurM2etCY++M2Gmw2LGyc/aDO46+tGWFfJeflGWr57QbzlSirqk1c5ZJ4YKmDU6pnO1Zq9ZmKtIqqJzfFziVHyq5AJseJ/i4wREiswUTJvCY00Xuu9uNHmP7EKQYvzSDGzuENzuyh82OzGGzIyetOVXHldB6IDVOCvxoAR0RuGiEJI8IPFlYI1/OLkOc6+0eat2B4GYIdrhvw01mUHctM+O6EndTaAm0UUjB3wh8rDSFkOTnAj9cnIu+4zF2F4JvMjJbsTZmigB2OUXHAkIWQNMJaRwQeHMp8dbpUKZaMAkJfGlxynzXY+x+BBNYhYQq+P2Amya44Viw6DMCH/Bwwr3T5UaS/QLfOmNUZYpamyhqeLYKWFROmQrbizuuJiuGZJekhc67FJfmBx5KP4jgCNwKMkpHTZrU3XXnt+bzkB7OXnU2bjxTpBftGo9gi8OV9YLT+wK/VVSiNfDFHvdQ7WcIHgbV7GNTdEa3dkK7k5DmKwS+sDS3IskFAgeK0sG+yj/pocPTCI4xMge9Mk7z9zCnCny33ALtblj/pMD3lOIdyc07DYLThMB3FNbMn2PVkFPvhId6LyB4hpFm4aKZteSOOgcalOWFFTZe8G5pjkKSvwl8qihH2VX9FQ9NfofgRTizpoy4iDHX+v0laC8QsuiYwPd5iO5Sv5HksMB3liD6Gx6iv4ngVTipwZkBd9OCZse953VC2j4Q+PXSzI4kvxf4lRJkf9tD9r8g+BMjFeOSqnhvPW8T0r5M4HmlSY4kcwWuKyx5vmCnPcbeR/DXGYVGc4Oplzwh8PdKExpJjgh8uARzf+Qh+ccIPgDJIV9V1yDPmvsfcFS/SWC9NMmRRBN4tDhz/8dj7FMEn8wo9EJonxGy7GWBny5NaCR5SuBjRQntK/MYq8DOz+HOMUJZd9pQzL3cQw7Bm3E+XDR9UBuWnxH4RAHBXWv+BgSKo9LME5yeE/jxosInzgWv81CqHkEVrzQJk1qjBX3RDiznELKiTeCqknzBSSoFJsX5osVjbAGCRkbqRiUtrtIhXuEtN+H5L1JXw8pwlOj8lsDdpfij080fjYLTZoEvLKxTmf1jQubQ2Oy4Ce/gGAf5L1XbOeBKtnsYAO9Xvlbwm5I0VMVdde63CAh4LlwAbxb4ktL8hiRdAnvomBdzk1y+VR6yr0GwgpH5EmM0aUAywa3LUnTN46CbjcH1hKx+T+DXStMFSV4V+GRxMXiBx9hFCIKMNDjVcJOfHwXRkBsIWdMr8PJSwrDQUZBz6hC4pSgX8YO6b4OHbpcj+DK4KHNQL0JFnmlrYIkdhAROC/xKKSoWzDTk9LLAx4vz3FaPsW0INjEyT5J3pxST5v9kbBWMvuUgQD8hwYTA20qLPiQJCxwqwU1f8VBkAEGEkTKogjkJpl+NfbsIOfeEwAdLkxtJvi3w/hnlzlS5uaLK7dHNMWoGBphu2kXK9T7sG/ZQ8joEQ5BnsO/2mXp6b/aFplgrOOOrz44pdLzWuuXnV0GBKCFrrxV47v8kP5FTk8DlhQ3ouKr5dnHlr/cwDP7xwBfPJanTPjgcSzNSnenA92FnubyeFn+bkEMn6OQ721c3F3g1vWDaH1kE3aNH6qvnHxn6A3/Pmv1LRE2EVCdSqpr/3ijvudKAI4fCzVpjv0UyuFa7GVlQyKHMfnPGn9EkPt2mYXAdn0rD+L9L8Cl/3h64Ddrz8Fua+6E1BzIBtcztx+qN4ofwgRR/icYJuNytKRP/6XP04/n/rqwePMXfrOJmVfNk0PeLi8/fWf9c4CKF3V73k6eVHlY22XLx82trf9V822v/BWCEzVGBJAAA";
}
