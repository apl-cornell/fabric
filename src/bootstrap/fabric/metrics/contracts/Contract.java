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
        
        public static final byte[] $classHash = new byte[] { 84, -99, -41, -61,
        -27, 40, 104, 83, -101, -19, 120, 83, -66, 59, -30, 29, -20, -97, -48,
        86, 36, -13, 39, 7, 19, 5, -46, 79, 108, 72, -82, -18 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1521386144000L;
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
                        tm.registerDelayedExtension(
                             parent,
                             (fabric.metrics.contracts.Contract)
                               this.$getProxy());
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
    
    public static final byte[] $classHash = new byte[] { 101, -10, -100, -103,
    -31, 43, -10, 102, 44, -16, 60, -71, 109, 28, 6, 78, 101, -78, -49, -52,
    -38, 29, -13, -30, 123, -70, -15, -19, 62, 23, 18, -31 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1521386144000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
