package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
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
        
        public static void static_activate(
          fabric.metrics.contracts.Contract arg1) {
            fabric.metrics.contracts.Contract._Impl.static_activate(arg1);
        }
        
        public static void removeObserver(
          fabric.metrics.contracts.Contract arg1,
          fabric.metrics.util.Observer arg2) {
            fabric.metrics.contracts.Contract._Impl.removeObserver(arg1, arg2);
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
        
        public void attemptExtension_remote(
          fabric.lang.security.Principal arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).
              attemptExtension_remote(arg1);
        }
        
        public static final java.lang.Class[] $paramTypes2 = null;
        
        public void attemptExtension$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                attemptExtension();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "attemptExtension",
                                                  $paramTypes2, null);
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
        
        public fabric.util.Set getLeafSubjects() {
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
                  log(java.util.logging.Level.FINER, "SYNCH EXTENSION");
                this.set$$expiry((long) newExpiry);
            }
            else {
                fabric.common.Logging.METRICS_LOGGER.
                  log(java.util.logging.Level.FINER, "DELAYED EXTENSION");
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
        
        public static void static_activate(
          fabric.metrics.contracts.Contract tmp) {
            {
                fabric.worker.transaction.TransactionManager $tm370 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled373 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff371 = 1;
                boolean $doBackoff372 = true;
                $label366: for (boolean $commit367 = false; !$commit367; ) {
                    if ($backoffEnabled373) {
                        if ($doBackoff372) {
                            if ($backoff371 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff371);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e368) {
                                        
                                    }
                                }
                            }
                            if ($backoff371 < 5000) $backoff371 *= 2;
                        }
                        $doBackoff372 = $backoff371 <= 32 || !$doBackoff372;
                    }
                    $commit367 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (!tmp.get$activated()) {
                            fabric.common.Logging.METRICS_LOGGER.
                              log(java.util.logging.Level.FINER,
                                  "CONTRACT ACTIVATE");
                            tmp.set$activated(true);
                        }
                    }
                    catch (final fabric.worker.RetryException $e368) {
                        $commit367 = false;
                        continue $label366;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e368) {
                        $commit367 = false;
                        fabric.common.TransactionID $currentTid369 =
                          $tm370.getCurrentTid();
                        if ($e368.tid.isDescendantOf($currentTid369))
                            continue $label366;
                        if ($currentTid369.parent != null) throw $e368;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e368) {
                        $commit367 = false;
                        if ($tm370.checkForStaleObjects()) continue $label366;
                        throw new fabric.worker.AbortException($e368);
                    }
                    finally {
                        if ($commit367) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e368) {
                                $commit367 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e368) {
                                $commit367 = false;
                                fabric.common.TransactionID $currentTid369 =
                                  $tm370.getCurrentTid();
                                if ($currentTid369 != null) {
                                    if ($e368.tid.equals($currentTid369) ||
                                          !$e368.tid.isDescendantOf(
                                                       $currentTid369)) {
                                        throw $e368;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit367) {
                            {  }
                            continue $label366;
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
            fabric.metrics.util.AbstractSubject._Impl.
              static_removeObserver((fabric.metrics.contracts.Contract)
                                      this.$getProxy(), obs);
        }
        
        public static void removeObserver(fabric.metrics.contracts.Contract tmp,
                                          fabric.metrics.util.Observer obs) {
            {
                fabric.worker.transaction.TransactionManager $tm378 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled381 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff379 = 1;
                boolean $doBackoff380 = true;
                $label374: for (boolean $commit375 = false; !$commit375; ) {
                    if ($backoffEnabled381) {
                        if ($doBackoff380) {
                            if ($backoff379 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff379);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e376) {
                                        
                                    }
                                }
                            }
                            if ($backoff379 < 5000) $backoff379 *= 2;
                        }
                        $doBackoff380 = $backoff379 <= 32 || !$doBackoff380;
                    }
                    $commit375 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.metrics.util.AbstractSubject._Impl.
                          static_removeObserver(tmp, obs);
                        if (!tmp.isObserved()) { tmp.set$$expiry((long) -1); }
                    }
                    catch (final fabric.worker.RetryException $e376) {
                        $commit375 = false;
                        continue $label374;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e376) {
                        $commit375 = false;
                        fabric.common.TransactionID $currentTid377 =
                          $tm378.getCurrentTid();
                        if ($e376.tid.isDescendantOf($currentTid377))
                            continue $label374;
                        if ($currentTid377.parent != null) throw $e376;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e376) {
                        $commit375 = false;
                        if ($tm378.checkForStaleObjects()) continue $label374;
                        throw new fabric.worker.AbortException($e376);
                    }
                    finally {
                        if ($commit375) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e376) {
                                $commit375 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e376) {
                                $commit375 = false;
                                fabric.common.TransactionID $currentTid377 =
                                  $tm378.getCurrentTid();
                                if ($currentTid377 != null) {
                                    if ($e376.tid.equals($currentTid377) ||
                                          !$e376.tid.isDescendantOf(
                                                       $currentTid377)) {
                                        throw $e376;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit375) {
                            {  }
                            continue $label374;
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
                fabric.common.Logging.METRICS_LOGGER.
                  finer(
                    "EXPIRY OF " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap((fabric.metrics.contracts.Contract)
                                        this.$getProxy())) +
                      " IS NOW " +
                      this.get$$expiry());
            }
            else if (getExpiry() > newExpiry) {
                retract(newExpiry);
                fabric.common.Logging.METRICS_LOGGER.
                  finer(
                    "EXPIRY OF " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap((fabric.metrics.contracts.Contract)
                                        this.$getProxy())) +
                      " IS NOW " +
                      this.get$$expiry());
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
            fabric.common.Logging.METRICS_LOGGER.log(
                                                   java.util.logging.Level.FINE,
                                                   "RETRACTION");
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
            fabric.common.Logging.METRICS_LOGGER.
              log(java.util.logging.Level.FINER, "CHECKING CONTRACT CHANGE");
            this.get$lock().checkForRead();
            if (valid()) return refresh(false);
            fabric.common.Logging.METRICS_LOGGER.
              log(java.util.logging.Level.FINER, "CONTRACT INVALID");
            return false;
        }
        
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
            {
                fabric.worker.transaction.TransactionManager $tm386 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled389 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff387 = 1;
                boolean $doBackoff388 = true;
                $label382: for (boolean $commit383 = false; !$commit383; ) {
                    if ($backoffEnabled389) {
                        if ($doBackoff388) {
                            if ($backoff387 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff387);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e384) {
                                        
                                    }
                                }
                            }
                            if ($backoff387 < 5000) $backoff387 *= 2;
                        }
                        $doBackoff388 = $backoff387 <= 32 || !$doBackoff388;
                    }
                    $commit383 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.common.Logging.METRICS_LOGGER.
                          log(
                            java.util.logging.Level.FINER,
                            "ASYNC EXTENSION OF " +
                              java.lang.String.
                                valueOf(
                                  fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                      tmp)) +
                              " IN " +
                              fabric.worker.transaction.TransactionManager.
                                getInstance().getCurrentTid());
                        tmp.refresh(true);
                    }
                    catch (final fabric.worker.RetryException $e384) {
                        $commit383 = false;
                        continue $label382;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e384) {
                        $commit383 = false;
                        fabric.common.TransactionID $currentTid385 =
                          $tm386.getCurrentTid();
                        if ($e384.tid.isDescendantOf($currentTid385))
                            continue $label382;
                        if ($currentTid385.parent != null) throw $e384;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e384) {
                        $commit383 = false;
                        if ($tm386.checkForStaleObjects()) continue $label382;
                        throw new fabric.worker.AbortException($e384);
                    }
                    finally {
                        if ($commit383) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e384) {
                                $commit383 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e384) {
                                $commit383 = false;
                                fabric.common.TransactionID $currentTid385 =
                                  $tm386.getCurrentTid();
                                if ($currentTid385 != null) {
                                    if ($e384.tid.equals($currentTid385) ||
                                          !$e384.tid.isDescendantOf(
                                                       $currentTid385)) {
                                        throw $e384;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit383) {
                            {  }
                            continue $label382;
                        }
                    }
                }
            }
        }
        
        /**
   * Acquire reconfig locks starting from this contract.
   */
        public void acquireReconfigLocks() { this.get$lock().acquire(); }
        
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
                        fabric.worker.transaction.TransactionManager $tm394 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled397 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff395 = 1;
                        boolean $doBackoff396 = true;
                        $label390: for (boolean $commit391 = false; !$commit391;
                                        ) {
                            if ($backoffEnabled397) {
                                if ($doBackoff396) {
                                    if ($backoff395 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff395);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e392) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff395 < 5000) $backoff395 *= 2;
                                }
                                $doBackoff396 = $backoff395 <= 32 ||
                                                  !$doBackoff396;
                            }
                            $commit391 = true;
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
                            catch (final fabric.worker.RetryException $e392) {
                                $commit391 = false;
                                continue $label390;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e392) {
                                $commit391 = false;
                                fabric.common.TransactionID $currentTid393 =
                                  $tm394.getCurrentTid();
                                if ($e392.tid.isDescendantOf($currentTid393))
                                    continue $label390;
                                if ($currentTid393.parent != null) throw $e392;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e392) {
                                $commit391 = false;
                                if ($tm394.checkForStaleObjects())
                                    continue $label390;
                                throw new fabric.worker.AbortException($e392);
                            }
                            finally {
                                if ($commit391) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e392) {
                                        $commit391 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e392) {
                                        $commit391 = false;
                                        fabric.common.TransactionID
                                          $currentTid393 =
                                          $tm394.getCurrentTid();
                                        if ($currentTid393 != null) {
                                            if ($e392.tid.equals(
                                                            $currentTid393) ||
                                                  !$e392.tid.
                                                  isDescendantOf(
                                                    $currentTid393)) {
                                                throw $e392;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit391) {
                                    {  }
                                    continue $label390;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -31, 111, 100, -77, 22,
    -38, 43, -99, 46, 3, 76, 37, 105, -25, 16, -70, 112, -46, -2, -77, 12, -38,
    -123, 117, 95, 38, -83, 105, 96, 67, 48, -56 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1508274565000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
