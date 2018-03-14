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
                        fabric.worker.transaction.TransactionManager $tm5 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled8 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff6 = 1;
                        boolean $doBackoff7 = true;
                        boolean $retry2 = true;
                        $label0: for (boolean $commit1 = false; !$commit1; ) {
                            if ($backoffEnabled8) {
                                if ($doBackoff7) {
                                    if ($backoff6 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff6);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e3) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff6 < 5000) $backoff6 *= 2;
                                }
                                $doBackoff7 = $backoff6 <= 32 || !$doBackoff7;
                            }
                            $commit1 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try { if (tmp.isActivated()) return; }
                            catch (final fabric.worker.RetryException $e3) {
                                $commit1 = false;
                                continue $label0;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e3) {
                                $commit1 = false;
                                fabric.common.TransactionID $currentTid4 =
                                  $tm5.getCurrentTid();
                                if ($e3.tid.isDescendantOf($currentTid4))
                                    continue $label0;
                                if ($currentTid4.parent != null) {
                                    $retry2 = false;
                                    throw $e3;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e3) {
                                $commit1 = false;
                                if ($tm5.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid4 =
                                  $tm5.getCurrentTid();
                                if ($e3.tid.isDescendantOf($currentTid4)) {
                                    $retry2 = true;
                                }
                                else if ($currentTid4.parent != null) {
                                    $retry2 = false;
                                    throw $e3;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e3) {
                                $commit1 = false;
                                if ($tm5.checkForStaleObjects())
                                    continue $label0;
                                $retry2 = false;
                                throw new fabric.worker.AbortException($e3);
                            }
                            finally {
                                if ($commit1) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e3) {
                                        $commit1 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e3) {
                                        $commit1 = false;
                                        fabric.common.TransactionID
                                          $currentTid4 = $tm5.getCurrentTid();
                                        if ($currentTid4 != null) {
                                            if ($e3.tid.equals($currentTid4) ||
                                                  !$e3.tid.isDescendantOf(
                                                             $currentTid4)) {
                                                throw $e3;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit1 && $retry2) {
                                    {  }
                                    continue $label0;
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
                        fabric.worker.transaction.TransactionManager $tm14 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled17 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff15 = 1;
                        boolean $doBackoff16 = true;
                        boolean $retry11 = true;
                        $label9: for (boolean $commit10 = false; !$commit10; ) {
                            if ($backoffEnabled17) {
                                if ($doBackoff16) {
                                    if ($backoff15 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff15);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e12) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff15 < 5000) $backoff15 *= 2;
                                }
                                $doBackoff16 = $backoff15 <= 32 ||
                                                 !$doBackoff16;
                            }
                            $commit10 = true;
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
                            catch (final fabric.worker.RetryException $e12) {
                                $commit10 = false;
                                continue $label9;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e12) {
                                $commit10 = false;
                                fabric.common.TransactionID $currentTid13 =
                                  $tm14.getCurrentTid();
                                if ($e12.tid.isDescendantOf($currentTid13))
                                    continue $label9;
                                if ($currentTid13.parent != null) {
                                    $retry11 = false;
                                    throw $e12;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e12) {
                                $commit10 = false;
                                if ($tm14.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid13 =
                                  $tm14.getCurrentTid();
                                if ($e12.tid.isDescendantOf($currentTid13)) {
                                    $retry11 = true;
                                }
                                else if ($currentTid13.parent != null) {
                                    $retry11 = false;
                                    throw $e12;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e12) {
                                $commit10 = false;
                                if ($tm14.checkForStaleObjects())
                                    continue $label9;
                                $retry11 = false;
                                throw new fabric.worker.AbortException($e12);
                            }
                            finally {
                                if ($commit10) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e12) {
                                        $commit10 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e12) {
                                        $commit10 = false;
                                        fabric.common.TransactionID
                                          $currentTid13 = $tm14.getCurrentTid();
                                        if ($currentTid13 != null) {
                                            if ($e12.tid.equals(
                                                           $currentTid13) ||
                                                  !$e12.tid.isDescendantOf(
                                                              $currentTid13)) {
                                                throw $e12;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit10 && $retry11) {
                                    {  }
                                    continue $label9;
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
                        fabric.worker.transaction.TransactionManager $tm23 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled26 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff24 = 1;
                        boolean $doBackoff25 = true;
                        boolean $retry20 = true;
                        $label18: for (boolean $commit19 = false; !$commit19;
                                       ) {
                            if ($backoffEnabled26) {
                                if ($doBackoff25) {
                                    if ($backoff24 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff24);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e21) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff24 < 5000) $backoff24 *= 2;
                                }
                                $doBackoff25 = $backoff24 <= 32 ||
                                                 !$doBackoff25;
                            }
                            $commit19 = true;
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
                            catch (final fabric.worker.RetryException $e21) {
                                $commit19 = false;
                                continue $label18;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e21) {
                                $commit19 = false;
                                fabric.common.TransactionID $currentTid22 =
                                  $tm23.getCurrentTid();
                                if ($e21.tid.isDescendantOf($currentTid22))
                                    continue $label18;
                                if ($currentTid22.parent != null) {
                                    $retry20 = false;
                                    throw $e21;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e21) {
                                $commit19 = false;
                                if ($tm23.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid22 =
                                  $tm23.getCurrentTid();
                                if ($e21.tid.isDescendantOf($currentTid22)) {
                                    $retry20 = true;
                                }
                                else if ($currentTid22.parent != null) {
                                    $retry20 = false;
                                    throw $e21;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e21) {
                                $commit19 = false;
                                if ($tm23.checkForStaleObjects())
                                    continue $label18;
                                $retry20 = false;
                                throw new fabric.worker.AbortException($e21);
                            }
                            finally {
                                if ($commit19) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e21) {
                                        $commit19 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e21) {
                                        $commit19 = false;
                                        fabric.common.TransactionID
                                          $currentTid22 = $tm23.getCurrentTid();
                                        if ($currentTid22 != null) {
                                            if ($e21.tid.equals(
                                                           $currentTid22) ||
                                                  !$e21.tid.isDescendantOf(
                                                              $currentTid22)) {
                                                throw $e21;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit19 && $retry20) {
                                    {  }
                                    continue $label18;
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
                            fabric.metrics.util.AbstractSubject._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved()) {
                                tmp.set$$expiry((long) -1);
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
                    fabric.worker.transaction.TransactionManager $tm60 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled63 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff61 = 1;
                    boolean $doBackoff62 = true;
                    boolean $retry57 = true;
                    $label55: for (boolean $commit56 = false; !$commit56; ) {
                        if ($backoffEnabled63) {
                            if ($doBackoff62) {
                                if ($backoff61 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff61);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e58) {
                                            
                                        }
                                    }
                                }
                                if ($backoff61 < 5000) $backoff61 *= 2;
                            }
                            $doBackoff62 = $backoff61 <= 32 || !$doBackoff62;
                        }
                        $commit56 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.refresh(true); }
                        catch (final fabric.worker.RetryException $e58) {
                            $commit56 = false;
                            continue $label55;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e58) {
                            $commit56 = false;
                            fabric.common.TransactionID $currentTid59 =
                              $tm60.getCurrentTid();
                            if ($e58.tid.isDescendantOf($currentTid59))
                                continue $label55;
                            if ($currentTid59.parent != null) {
                                $retry57 = false;
                                throw $e58;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e58) {
                            $commit56 = false;
                            if ($tm60.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid59 =
                              $tm60.getCurrentTid();
                            if ($e58.tid.isDescendantOf($currentTid59)) {
                                $retry57 = true;
                            }
                            else if ($currentTid59.parent != null) {
                                $retry57 = false;
                                throw $e58;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e58) {
                            $commit56 = false;
                            if ($tm60.checkForStaleObjects()) continue $label55;
                            $retry57 = false;
                            throw new fabric.worker.AbortException($e58);
                        }
                        finally {
                            if ($commit56) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e58) {
                                    $commit56 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e58) {
                                    $commit56 = false;
                                    fabric.common.TransactionID $currentTid59 =
                                      $tm60.getCurrentTid();
                                    if ($currentTid59 != null) {
                                        if ($e58.tid.equals($currentTid59) ||
                                              !$e58.tid.isDescendantOf(
                                                          $currentTid59)) {
                                            throw $e58;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit56 && $retry57) {
                                {  }
                                continue $label55;
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
                      proxy$var64 = proxy;
                    fabric.worker.transaction.TransactionManager $tm70 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled73 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff71 = 1;
                    boolean $doBackoff72 = true;
                    boolean $retry67 = true;
                    $label65: for (boolean $commit66 = false; !$commit66; ) {
                        if ($backoffEnabled73) {
                            if ($doBackoff72) {
                                if ($backoff71 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff71);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e68) {
                                            
                                        }
                                    }
                                }
                                if ($backoff71 < 5000) $backoff71 *= 2;
                            }
                            $doBackoff72 = $backoff71 <= 32 || !$doBackoff72;
                        }
                        $commit66 = true;
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
                            if ($tm70.checkForStaleObjects()) continue $label65;
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
                                    fabric.common.TransactionID $currentTid69 =
                                      $tm70.getCurrentTid();
                                    if ($currentTid69 != null) {
                                        if ($e68.tid.equals($currentTid69) ||
                                              !$e68.tid.isDescendantOf(
                                                          $currentTid69)) {
                                            throw $e68;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit66 && $retry67) {
                                { proxy = proxy$var64; }
                                continue $label65;
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
                        fabric.worker.transaction.TransactionManager $tm79 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled82 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff80 = 1;
                        boolean $doBackoff81 = true;
                        boolean $retry76 = true;
                        $label74: for (boolean $commit75 = false; !$commit75;
                                       ) {
                            if ($backoffEnabled82) {
                                if ($doBackoff81) {
                                    if ($backoff80 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff80);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e77) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff80 < 5000) $backoff80 *= 2;
                                }
                                $doBackoff81 = $backoff80 <= 32 ||
                                                 !$doBackoff81;
                            }
                            $commit75 = true;
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
                            catch (final fabric.worker.RetryException $e77) {
                                $commit75 = false;
                                continue $label74;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e77) {
                                $commit75 = false;
                                fabric.common.TransactionID $currentTid78 =
                                  $tm79.getCurrentTid();
                                if ($e77.tid.isDescendantOf($currentTid78))
                                    continue $label74;
                                if ($currentTid78.parent != null) {
                                    $retry76 = false;
                                    throw $e77;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e77) {
                                $commit75 = false;
                                if ($tm79.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid78 =
                                  $tm79.getCurrentTid();
                                if ($e77.tid.isDescendantOf($currentTid78)) {
                                    $retry76 = true;
                                }
                                else if ($currentTid78.parent != null) {
                                    $retry76 = false;
                                    throw $e77;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e77) {
                                $commit75 = false;
                                if ($tm79.checkForStaleObjects())
                                    continue $label74;
                                $retry76 = false;
                                throw new fabric.worker.AbortException($e77);
                            }
                            finally {
                                if ($commit75) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e77) {
                                        $commit75 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e77) {
                                        $commit75 = false;
                                        fabric.common.TransactionID
                                          $currentTid78 = $tm79.getCurrentTid();
                                        if ($currentTid78 != null) {
                                            if ($e77.tid.equals(
                                                           $currentTid78) ||
                                                  !$e77.tid.isDescendantOf(
                                                              $currentTid78)) {
                                                throw $e77;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit75 && $retry76) {
                                    {  }
                                    continue $label74;
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
      "H4sIAAAAAAAAALUaC2wcxXXu/L3EiX9xPsY4TnIJjUnuCOGjxBCaXGxycLEt2yHFaTn29ubsxXu7y+6cc6YECv0kQjRIxaRBLWmFUkEhQFs1ohSCaPkUSKjEp18ViFRRoIGqtKW0UoG+Nzv38XpvfSe1lua99cy8N+8/M7t37H1SY5lkZUpKKGqITRnUCvVJiWhsUDItmoyokmWNQG9cnl8dPfTOfclOP/HHSIMsabqmyJIa1yxGFsaukyalsEZZeNdQtGcPCchIuEOyxhnx79mWNUmXoatTY6rOxCKz+N91bnj6m9c0/aiKNI6SRkUbZhJT5IiuMZplo6QhTdMJalpbk0maHCXNGqXJYWoqkqrcABN1bZS0WMqYJrGMSa0haunqJE5ssTIGNfmauU4UXwexzYzMdBPEb7LFzzBFDccUi/XESG1KoWrSup7cRKpjpCalSmMwcXEsp0WYcwz3YT9Mn6eAmGZKkmmOpHpC0ZKMLHdS5DUOXgkTgLQuTdm4nl+qWpOgg7TYIqmSNhYeZqaijcHUGj0DqzDSXpIpTKo3JHlCGqNxRpY65w3aQzArwM2CJIy0OadxTuCzdofPirz1fv8lB7+o7dD8xAcyJ6msovz1QNTpIBqiKWpSTaY2YUN37JC0+MQBPyEwuc0x2Z7z6I0ffHZd51PP23POcpkzkLiOyiwuH00sfLkjsnZTFYpRb+iWgqEwQ3Pu1UEx0pM1INoX5zniYCg3+NTQc1d/6QF6xk/mRUmtrKuZNERVs6ynDUWl5uVUo6bEaDJKAlRLRvh4lNTBc0zRqN07kEpZlEVJtcq7anX+P5goBSzQRHXwrGgpPfdsSGycP2cNQkgTNOIjxN9MSO8L8LySkJprGRkMj+tpGk6oGboXwjsMjUqmPB6GvDUVOWyZctjMaEyBSaILogiQFYZQZ6YkM4gS8RQCWYz/A88s6tG01+cDEy+X9SRNSBb4S8TOtkEV0mOHriapGZfVgyeipPXE3Tx+AhjzFsQtt5APfN7hrBbFtNOZbb0fPBw/acce0goDMrLCFjQkBA3lBQ3lBAXZGjC1QlCsQlCsjvmyociR6IM8gmotnmp5dg3AbrOhSiylm+ks8fm4bos4PQ8dcPwEFBSoGQ1rh79wxbUHVlZBzBp7q9GNMDXozKBC3YnCkwRpEZcb97/zz0cO7dMLucRIcFaKz6bEFF3pNJSpyzQJJbDAvrtLOh4/sS/ox/ISQItIEJtQRjqda8xI1Z5c2UNr1MTIfLSBpOJQrlbNY+OmvrfQwwNgIYIWOxbQWA4BecW8dNi457e/fHcj30tyxbWxqAoPU9ZTlNDIrJGnbnPB9iMmpTDv9cODd971/v493PAwY5XbgkGEEUhkCTJYN7/6/PW/e/ONo6/5C85ipNbIJFRFznJdmj+FPx+0T7BhVmIHYqjNEVERuvIlwcCV1xRkg+KgQoEC0a3gLi2tJ5WUIiVUipHyn8bVG46/d7DJdrcKPbbxTLJubgaF/mXbyJdOXvNRJ2fjk3FzKtivMM2ueK0FzltNU5pCObK3vHL23b+Q7oHIh3plKTdQXoIItwfhDjyf22I9hxscYxcgWGlbq4P340HCWf37cBstxOJo+Ni32yNbzthpn49F5LHCJe2vkorS5PwH0h/6V9Y+6yd1o6SJ7+CSxq6SoH5BGIzCHmxFRGeMLJgxPnM/tTePnnyudTjzoGhZZxYUyg0842x8nmcHvh04YIgWNFIQ2mpCaqttXPM3HG01EC7K+gh/2MxJVnG4BsFabkg/IwHD1BlISeEMEVDS6QxD7/N1zmUQNbo8wanaGOly1Dzu5SEKBkgpY7HcxGXOamYnKMKL8oIvRsE3QbuYkPrHBD7kInivu+BV+NjNsI7i4Q3/uywn86Kd0f547+dGevuHowP98b6tkZGBIZeIGTSVNCT9pDgv0APTt30aOjhtZ4t9qFo161xTTGMfrPiyC/jaWVhlhdcqnKLv7Uf2PX7/vv32oaNl5hGhV8ukH/r1x6dCh0+/4LLt1CZ1qB20pElD0DYTErhM4HNcTDrkZVIE0RnmbNg+FO0byZkRO68UyiLq5zFi72SuEm2EdglIwgTe7SLRaGUSNRWcuzvav32As9ztJsG8XH4k4MSzSeDlLhLE3SWAYl1nmMokVN5snqkfmQYEs06BlxQxhUSC/Z9TJd0MVpfQdZVKfLNpypbITBHg9VLC4seJwvr8r1Ec2eICDxatX1Qpfbnc7XDL3YGERc1JuOu45i1G89mlDuY8ko/eOn0kOfC9DX5RqQdAc6Yb61U6SdUiKZZhXsy6+O3k15FC2T195uxNkYm3xuy8WO5Y2Tn7+zuPvXD5GvkbflKVr6+z7kAziXpmVtV5JoUrnDYyo7Z25c3ckCtR50JtvVfgTHHsFCJuduDg4xZHzMwXTJjAE06fue9/N3uM3YJgivFrNfg1KNwbzB9Hg7njaLAg7eRMHddC64NcOSnwwyV0RHDjbI2Q5CGBj5bWyFewy27O9YCHWrch+DJEP9zI4a4zorvWnUldSboptALaOOTkiwIfr0whJPmxwA+W56JpjzG+q93ByHzF2pqrCtiVcoiOFYUshaYT0jws8PZK4q3bEW/1gklE4EvLU+aIx9h3ERzGsiRUwf9vd9MEdyALFn1S4AMeTvjWbLmRZL/At8wZVbkq1ymqHJ6+QhaVM6bCpnAL1mTFkNQSdQ577/NQ+iEE98K9Iad03KRp3V13fq8+H+nhdLbAxs1nyvSiXfQRRB2ubBSc/izw62UlWhNf7LiHao8ieARUs89R8Tnd2g3tDkLarhL4wsrciiQXCBwqSweNc33SQ4enEDzGyEL0yiTNbWpuKvDt83Jod8L6JwW+qxLvyG7eaRKcpgX+WmnN/AVWTQX1nvdQ70UEP2ekTbhobi25o86BBmV5WY2Nl75dmaOQ5E8Cny7LUXZVf9lDk1cRnIJDbMZIihhzFkFevz8D7TlCOo4LfLeH6C71G0kOC3xHBaL/3kP0PyD4FRzd4MyAu2lJs+Pe8xohne8J/FplZkeSVwV+qQLZ/+gh+1sI3mCkZlJSFe+t501CulYJvKgyyZGkVeAFpSUvFuw9j7G/IHh7TqHR3GDqFY8K/J3KhEaSIwIfrsDcH3pI/hGCv4LkkK+qa5Dnzf0POLvfJLBemeRIogk8Xp65Pyk95iPY+e85hV4G7WNCVp0S+InKhEaSxwU+XpbQvjqPsQACfIUxRllv1lDMKe4hh+BtOB9unj6oDavPCPxMCcFda/4WBBOOmr9IcHpa4B+WFT4pLnizh1KtCObzSpMyqTVe0hddwHIhIWs6Ba6ryBecpFZgUp4v2j3GOhDACWzBuKQlVbqLV3jLTXj+zupqWBmOEt23C9xbiT+63fzRLDhtF/jC0jpV2W8XcofGNsfVeKeNcdT1XdZODrjWQQ+LoJS+TnCkkjZUxd0W3JExkPg8uBHeLPAllTkSSXoE9lC6KAjv5/Jt8JB9I4J1jCyRGKNpA7ILrmGWomseJ998UG4mZN27Ar9SmS5I8rLAJ8sLys0eY2hJ34WMNDnVcJOfnw3RkFsIWT8g8OpK4rLU2ZBzCgq8uCwX8ZO7b7uHbn0ItoCLcif3MlTkqbcelthJSOgdgV+qRMWSqYecTgl8ojzP9XuM4ess3w5GFkny9RnFpLPeMrtG32oQYIiQcErgKyqLPiSJChypwE27PRS5GsEQI1VQFgsSzL4r+/YQct4zAh+sTG4k+brA++eUO1f2WkXZ26ubE9QMDTPdpO5FjytyrYeSKQR7IM9gIx409exU/huoWCs859fS4Aw6JGt3y8/PgwJxQjZcI3Dr/yQ/kVOLwNWlDei4u/kkrrzhYRi8nvmuKySp0z44PJ5lpD7XgZ/QznL5oi1+aSFHnqFH37pyXVuJr9lLZ/32RdA9fKSxfsmRXb/hn2bzv6IIxEh9KqOqxZ+aip5rDTiDKNysAfvDk8G12svI0lIOZfbHNv6MJvFlbJob4H4+k4bxH6TgU/G8fXA9tOfhfzdxP7QXQC6gVrm9zt4qXpUPZ/h3t9LBTNozJv5a6Njfl/yrtn7kNP86i7tX4Kdh308u3tjf+HToIoV9ZcEPnlD6WNXRxRc/u6HhZ223vvJf8Ai0ycUkAAA=";
}
