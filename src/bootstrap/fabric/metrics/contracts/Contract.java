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
                        fabric.worker.transaction.TransactionManager $tm87 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled90 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff88 = 1;
                        boolean $doBackoff89 = true;
                        boolean $retry84 = true;
                        $label82: for (boolean $commit83 = false; !$commit83;
                                       ) {
                            if ($backoffEnabled90) {
                                if ($doBackoff89) {
                                    if ($backoff88 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff88);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e85) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff88 < 5000) $backoff88 *= 2;
                                }
                                $doBackoff89 = $backoff88 <= 32 ||
                                                 !$doBackoff89;
                            }
                            $commit83 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { if (tmp.isActivated()) return; }
                            catch (final fabric.worker.RetryException $e85) {
                                $commit83 = false;
                                continue $label82;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e85) {
                                $commit83 = false;
                                fabric.common.TransactionID $currentTid86 =
                                  $tm87.getCurrentTid();
                                if ($e85.tid.isDescendantOf($currentTid86))
                                    continue $label82;
                                if ($currentTid86.parent != null) {
                                    $retry84 = false;
                                    throw $e85;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e85) {
                                $commit83 = false;
                                if ($tm87.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid86 =
                                  $tm87.getCurrentTid();
                                if ($e85.tid.isDescendantOf($currentTid86)) {
                                    $retry84 = true;
                                }
                                else if ($currentTid86.parent != null) {
                                    $retry84 = false;
                                    throw $e85;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e85) {
                                $commit83 = false;
                                if ($tm87.checkForStaleObjects())
                                    continue $label82;
                                $retry84 = false;
                                throw new fabric.worker.AbortException($e85);
                            }
                            finally {
                                if ($commit83) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e85) {
                                        $commit83 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e85) {
                                        $commit83 = false;
                                        fabric.common.TransactionID
                                          $currentTid86 = $tm87.getCurrentTid();
                                        if ($currentTid86 != null) {
                                            if ($e85.tid.equals(
                                                           $currentTid86) ||
                                                  !$e85.tid.isDescendantOf(
                                                              $currentTid86)) {
                                                throw $e85;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit83 && $retry84) {
                                    {  }
                                    continue $label82;
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
                        fabric.worker.transaction.TransactionManager $tm96 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled99 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff97 = 1;
                        boolean $doBackoff98 = true;
                        boolean $retry93 = true;
                        $label91: for (boolean $commit92 = false; !$commit92;
                                       ) {
                            if ($backoffEnabled99) {
                                if ($doBackoff98) {
                                    if ($backoff97 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff97);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e94) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff97 < 5000) $backoff97 *= 2;
                                }
                                $doBackoff98 = $backoff97 <= 32 ||
                                                 !$doBackoff98;
                            }
                            $commit92 = true;
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
                            catch (final fabric.worker.RetryException $e94) {
                                $commit92 = false;
                                continue $label91;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e94) {
                                $commit92 = false;
                                fabric.common.TransactionID $currentTid95 =
                                  $tm96.getCurrentTid();
                                if ($e94.tid.isDescendantOf($currentTid95))
                                    continue $label91;
                                if ($currentTid95.parent != null) {
                                    $retry93 = false;
                                    throw $e94;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e94) {
                                $commit92 = false;
                                if ($tm96.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid95 =
                                  $tm96.getCurrentTid();
                                if ($e94.tid.isDescendantOf($currentTid95)) {
                                    $retry93 = true;
                                }
                                else if ($currentTid95.parent != null) {
                                    $retry93 = false;
                                    throw $e94;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e94) {
                                $commit92 = false;
                                if ($tm96.checkForStaleObjects())
                                    continue $label91;
                                $retry93 = false;
                                throw new fabric.worker.AbortException($e94);
                            }
                            finally {
                                if ($commit92) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e94) {
                                        $commit92 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e94) {
                                        $commit92 = false;
                                        fabric.common.TransactionID
                                          $currentTid95 = $tm96.getCurrentTid();
                                        if ($currentTid95 != null) {
                                            if ($e94.tid.equals(
                                                           $currentTid95) ||
                                                  !$e94.tid.isDescendantOf(
                                                              $currentTid95)) {
                                                throw $e94;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit92 && $retry93) {
                                    {  }
                                    continue $label91;
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
                        fabric.worker.transaction.TransactionManager $tm105 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled108 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff106 = 1;
                        boolean $doBackoff107 = true;
                        boolean $retry102 = true;
                        $label100: for (boolean $commit101 = false; !$commit101;
                                        ) {
                            if ($backoffEnabled108) {
                                if ($doBackoff107) {
                                    if ($backoff106 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff106);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e103) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff106 < 5000) $backoff106 *= 2;
                                }
                                $doBackoff107 = $backoff106 <= 32 ||
                                                  !$doBackoff107;
                            }
                            $commit101 = true;
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
                            catch (final fabric.worker.RetryException $e103) {
                                $commit101 = false;
                                continue $label100;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e103) {
                                $commit101 = false;
                                fabric.common.TransactionID $currentTid104 =
                                  $tm105.getCurrentTid();
                                if ($e103.tid.isDescendantOf($currentTid104))
                                    continue $label100;
                                if ($currentTid104.parent != null) {
                                    $retry102 = false;
                                    throw $e103;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e103) {
                                $commit101 = false;
                                if ($tm105.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid104 =
                                  $tm105.getCurrentTid();
                                if ($e103.tid.isDescendantOf($currentTid104)) {
                                    $retry102 = true;
                                }
                                else if ($currentTid104.parent != null) {
                                    $retry102 = false;
                                    throw $e103;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e103) {
                                $commit101 = false;
                                if ($tm105.checkForStaleObjects())
                                    continue $label100;
                                $retry102 = false;
                                throw new fabric.worker.AbortException($e103);
                            }
                            finally {
                                if ($commit101) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e103) {
                                        $commit101 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e103) {
                                        $commit101 = false;
                                        fabric.common.TransactionID
                                          $currentTid104 =
                                          $tm105.getCurrentTid();
                                        if ($currentTid104 != null) {
                                            if ($e103.tid.equals(
                                                            $currentTid104) ||
                                                  !$e103.tid.
                                                  isDescendantOf(
                                                    $currentTid104)) {
                                                throw $e103;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit101 && $retry102) {
                                    {  }
                                    continue $label100;
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
                          proxy$var109 = proxy;
                        fabric.worker.transaction.TransactionManager $tm115 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled118 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff116 = 1;
                        boolean $doBackoff117 = true;
                        boolean $retry112 = true;
                        $label110: for (boolean $commit111 = false; !$commit111;
                                        ) {
                            if ($backoffEnabled118) {
                                if ($doBackoff117) {
                                    if ($backoff116 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff116);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e113) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff116 < 5000) $backoff116 *= 2;
                                }
                                $doBackoff117 = $backoff116 <= 32 ||
                                                  !$doBackoff117;
                            }
                            $commit111 = true;
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
                            catch (final fabric.worker.RetryException $e113) {
                                $commit111 = false;
                                continue $label110;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e113) {
                                $commit111 = false;
                                fabric.common.TransactionID $currentTid114 =
                                  $tm115.getCurrentTid();
                                if ($e113.tid.isDescendantOf($currentTid114))
                                    continue $label110;
                                if ($currentTid114.parent != null) {
                                    $retry112 = false;
                                    throw $e113;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e113) {
                                $commit111 = false;
                                if ($tm115.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid114 =
                                  $tm115.getCurrentTid();
                                if ($e113.tid.isDescendantOf($currentTid114)) {
                                    $retry112 = true;
                                }
                                else if ($currentTid114.parent != null) {
                                    $retry112 = false;
                                    throw $e113;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e113) {
                                $commit111 = false;
                                if ($tm115.checkForStaleObjects())
                                    continue $label110;
                                $retry112 = false;
                                throw new fabric.worker.AbortException($e113);
                            }
                            finally {
                                if ($commit111) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e113) {
                                        $commit111 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e113) {
                                        $commit111 = false;
                                        fabric.common.TransactionID
                                          $currentTid114 =
                                          $tm115.getCurrentTid();
                                        if ($currentTid114 != null) {
                                            if ($e113.tid.equals(
                                                            $currentTid114) ||
                                                  !$e113.tid.
                                                  isDescendantOf(
                                                    $currentTid114)) {
                                                throw $e113;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit111 && $retry112) {
                                    { proxy = proxy$var109; }
                                    continue $label110;
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
        
        public static final byte[] $classHash = new byte[] { 36, -101, 12, -10,
        -85, -120, -27, 110, -73, -43, -66, 113, -124, 12, -119, -120, -14, 103,
        -41, -64, 64, -90, -9, -84, 56, 121, 75, -122, -119, 119, 88, 31 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1519938314000L;
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
                    fabric.worker.transaction.TransactionManager $tm124 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled127 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff125 = 1;
                    boolean $doBackoff126 = true;
                    boolean $retry121 = true;
                    $label119: for (boolean $commit120 = false; !$commit120; ) {
                        if ($backoffEnabled127) {
                            if ($doBackoff126) {
                                if ($backoff125 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff125);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e122) {
                                            
                                        }
                                    }
                                }
                                if ($backoff125 < 5000) $backoff125 *= 2;
                            }
                            $doBackoff126 = $backoff125 <= 32 || !$doBackoff126;
                        }
                        $commit120 = true;
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
                        catch (final fabric.worker.RetryException $e122) {
                            $commit120 = false;
                            continue $label119;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e122) {
                            $commit120 = false;
                            fabric.common.TransactionID $currentTid123 =
                              $tm124.getCurrentTid();
                            if ($e122.tid.isDescendantOf($currentTid123))
                                continue $label119;
                            if ($currentTid123.parent != null) {
                                $retry121 = false;
                                throw $e122;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e122) {
                            $commit120 = false;
                            if ($tm124.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid123 =
                              $tm124.getCurrentTid();
                            if ($e122.tid.isDescendantOf($currentTid123)) {
                                $retry121 = true;
                            }
                            else if ($currentTid123.parent != null) {
                                $retry121 = false;
                                throw $e122;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e122) {
                            $commit120 = false;
                            if ($tm124.checkForStaleObjects())
                                continue $label119;
                            $retry121 = false;
                            throw new fabric.worker.AbortException($e122);
                        }
                        finally {
                            if ($commit120) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e122) {
                                    $commit120 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e122) {
                                    $commit120 = false;
                                    fabric.common.TransactionID $currentTid123 =
                                      $tm124.getCurrentTid();
                                    if ($currentTid123 != null) {
                                        if ($e122.tid.equals($currentTid123) ||
                                              !$e122.tid.isDescendantOf(
                                                           $currentTid123)) {
                                            throw $e122;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit120 && $retry121) {
                                {  }
                                continue $label119;
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
                    fabric.worker.transaction.TransactionManager $tm133 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled136 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff134 = 1;
                    boolean $doBackoff135 = true;
                    boolean $retry130 = true;
                    $label128: for (boolean $commit129 = false; !$commit129; ) {
                        if ($backoffEnabled136) {
                            if ($doBackoff135) {
                                if ($backoff134 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff134);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e131) {
                                            
                                        }
                                    }
                                }
                                if ($backoff134 < 5000) $backoff134 *= 2;
                            }
                            $doBackoff135 = $backoff134 <= 32 || !$doBackoff135;
                        }
                        $commit129 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.util.AbstractSubject._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved()) {
                                tmp.set$$expiry((long) -1);
                            }
                        }
                        catch (final fabric.worker.RetryException $e131) {
                            $commit129 = false;
                            continue $label128;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e131) {
                            $commit129 = false;
                            fabric.common.TransactionID $currentTid132 =
                              $tm133.getCurrentTid();
                            if ($e131.tid.isDescendantOf($currentTid132))
                                continue $label128;
                            if ($currentTid132.parent != null) {
                                $retry130 = false;
                                throw $e131;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e131) {
                            $commit129 = false;
                            if ($tm133.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid132 =
                              $tm133.getCurrentTid();
                            if ($e131.tid.isDescendantOf($currentTid132)) {
                                $retry130 = true;
                            }
                            else if ($currentTid132.parent != null) {
                                $retry130 = false;
                                throw $e131;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e131) {
                            $commit129 = false;
                            if ($tm133.checkForStaleObjects())
                                continue $label128;
                            $retry130 = false;
                            throw new fabric.worker.AbortException($e131);
                        }
                        finally {
                            if ($commit129) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e131) {
                                    $commit129 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e131) {
                                    $commit129 = false;
                                    fabric.common.TransactionID $currentTid132 =
                                      $tm133.getCurrentTid();
                                    if ($currentTid132 != null) {
                                        if ($e131.tid.equals($currentTid132) ||
                                              !$e131.tid.isDescendantOf(
                                                           $currentTid132)) {
                                            throw $e131;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit129 && $retry130) {
                                {  }
                                continue $label128;
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
                    fabric.worker.transaction.TransactionManager $tm142 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled145 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff143 = 1;
                    boolean $doBackoff144 = true;
                    boolean $retry139 = true;
                    $label137: for (boolean $commit138 = false; !$commit138; ) {
                        if ($backoffEnabled145) {
                            if ($doBackoff144) {
                                if ($backoff143 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff143);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e140) {
                                            
                                        }
                                    }
                                }
                                if ($backoff143 < 5000) $backoff143 *= 2;
                            }
                            $doBackoff144 = $backoff143 <= 32 || !$doBackoff144;
                        }
                        $commit138 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.refresh(true); }
                        catch (final fabric.worker.RetryException $e140) {
                            $commit138 = false;
                            continue $label137;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e140) {
                            $commit138 = false;
                            fabric.common.TransactionID $currentTid141 =
                              $tm142.getCurrentTid();
                            if ($e140.tid.isDescendantOf($currentTid141))
                                continue $label137;
                            if ($currentTid141.parent != null) {
                                $retry139 = false;
                                throw $e140;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e140) {
                            $commit138 = false;
                            if ($tm142.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid141 =
                              $tm142.getCurrentTid();
                            if ($e140.tid.isDescendantOf($currentTid141)) {
                                $retry139 = true;
                            }
                            else if ($currentTid141.parent != null) {
                                $retry139 = false;
                                throw $e140;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e140) {
                            $commit138 = false;
                            if ($tm142.checkForStaleObjects())
                                continue $label137;
                            $retry139 = false;
                            throw new fabric.worker.AbortException($e140);
                        }
                        finally {
                            if ($commit138) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e140) {
                                    $commit138 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e140) {
                                    $commit138 = false;
                                    fabric.common.TransactionID $currentTid141 =
                                      $tm142.getCurrentTid();
                                    if ($currentTid141 != null) {
                                        if ($e140.tid.equals($currentTid141) ||
                                              !$e140.tid.isDescendantOf(
                                                           $currentTid141)) {
                                            throw $e140;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit138 && $retry139) {
                                {  }
                                continue $label137;
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
                      proxy$var146 = proxy;
                    fabric.worker.transaction.TransactionManager $tm152 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled155 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff153 = 1;
                    boolean $doBackoff154 = true;
                    boolean $retry149 = true;
                    $label147: for (boolean $commit148 = false; !$commit148; ) {
                        if ($backoffEnabled155) {
                            if ($doBackoff154) {
                                if ($backoff153 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff153);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e150) {
                                            
                                        }
                                    }
                                }
                                if ($backoff153 < 5000) $backoff153 *= 2;
                            }
                            $doBackoff154 = $backoff153 <= 32 || !$doBackoff154;
                        }
                        $commit148 = true;
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
                        catch (final fabric.worker.RetryException $e150) {
                            $commit148 = false;
                            continue $label147;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e150) {
                            $commit148 = false;
                            fabric.common.TransactionID $currentTid151 =
                              $tm152.getCurrentTid();
                            if ($e150.tid.isDescendantOf($currentTid151))
                                continue $label147;
                            if ($currentTid151.parent != null) {
                                $retry149 = false;
                                throw $e150;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e150) {
                            $commit148 = false;
                            if ($tm152.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid151 =
                              $tm152.getCurrentTid();
                            if ($e150.tid.isDescendantOf($currentTid151)) {
                                $retry149 = true;
                            }
                            else if ($currentTid151.parent != null) {
                                $retry149 = false;
                                throw $e150;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e150) {
                            $commit148 = false;
                            if ($tm152.checkForStaleObjects())
                                continue $label147;
                            $retry149 = false;
                            throw new fabric.worker.AbortException($e150);
                        }
                        finally {
                            if ($commit148) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e150) {
                                    $commit148 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e150) {
                                    $commit148 = false;
                                    fabric.common.TransactionID $currentTid151 =
                                      $tm152.getCurrentTid();
                                    if ($currentTid151 != null) {
                                        if ($e150.tid.equals($currentTid151) ||
                                              !$e150.tid.isDescendantOf(
                                                           $currentTid151)) {
                                            throw $e150;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit148 && $retry149) {
                                { proxy = proxy$var146; }
                                continue $label147;
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
                        fabric.worker.transaction.TransactionManager $tm161 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled164 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff162 = 1;
                        boolean $doBackoff163 = true;
                        boolean $retry158 = true;
                        $label156: for (boolean $commit157 = false; !$commit157;
                                        ) {
                            if ($backoffEnabled164) {
                                if ($doBackoff163) {
                                    if ($backoff162 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff162);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e159) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff162 < 5000) $backoff162 *= 2;
                                }
                                $doBackoff163 = $backoff162 <= 32 ||
                                                  !$doBackoff163;
                            }
                            $commit157 = true;
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
                            catch (final fabric.worker.RetryException $e159) {
                                $commit157 = false;
                                continue $label156;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e159) {
                                $commit157 = false;
                                fabric.common.TransactionID $currentTid160 =
                                  $tm161.getCurrentTid();
                                if ($e159.tid.isDescendantOf($currentTid160))
                                    continue $label156;
                                if ($currentTid160.parent != null) {
                                    $retry158 = false;
                                    throw $e159;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e159) {
                                $commit157 = false;
                                if ($tm161.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid160 =
                                  $tm161.getCurrentTid();
                                if ($e159.tid.isDescendantOf($currentTid160)) {
                                    $retry158 = true;
                                }
                                else if ($currentTid160.parent != null) {
                                    $retry158 = false;
                                    throw $e159;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e159) {
                                $commit157 = false;
                                if ($tm161.checkForStaleObjects())
                                    continue $label156;
                                $retry158 = false;
                                throw new fabric.worker.AbortException($e159);
                            }
                            finally {
                                if ($commit157) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e159) {
                                        $commit157 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e159) {
                                        $commit157 = false;
                                        fabric.common.TransactionID
                                          $currentTid160 =
                                          $tm161.getCurrentTid();
                                        if ($currentTid160 != null) {
                                            if ($e159.tid.equals(
                                                            $currentTid160) ||
                                                  !$e159.tid.
                                                  isDescendantOf(
                                                    $currentTid160)) {
                                                throw $e159;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit157 && $retry158) {
                                    {  }
                                    continue $label156;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -42, 120, 51, -41, 70,
    -127, 60, -42, -67, -19, 85, 89, -42, 57, -57, -52, -9, 80, -9, -43, 62,
    114, 84, -57, -81, -67, 121, 50, -51, 22, 97, -65 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519938314000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
