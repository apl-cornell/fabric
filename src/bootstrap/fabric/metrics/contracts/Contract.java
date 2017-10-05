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
                fabric.worker.transaction.TransactionManager $tm351 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled354 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff352 = 1;
                boolean $doBackoff353 = true;
                $label347: for (boolean $commit348 = false; !$commit348; ) {
                    if ($backoffEnabled354) {
                        if ($doBackoff353) {
                            if ($backoff352 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff352);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e349) {
                                        
                                    }
                                }
                            }
                            if ($backoff352 < 5000) $backoff352 *= 2;
                        }
                        $doBackoff353 = $backoff352 <= 32 || !$doBackoff353;
                    }
                    $commit348 = true;
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
                    catch (final fabric.worker.RetryException $e349) {
                        $commit348 = false;
                        continue $label347;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e349) {
                        $commit348 = false;
                        fabric.common.TransactionID $currentTid350 =
                          $tm351.getCurrentTid();
                        if ($e349.tid.isDescendantOf($currentTid350))
                            continue $label347;
                        if ($currentTid350.parent != null) throw $e349;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e349) {
                        $commit348 = false;
                        if ($tm351.checkForStaleObjects()) continue $label347;
                        throw new fabric.worker.AbortException($e349);
                    }
                    finally {
                        if ($commit348) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e349) {
                                $commit348 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e349) {
                                $commit348 = false;
                                fabric.common.TransactionID $currentTid350 =
                                  $tm351.getCurrentTid();
                                if ($currentTid350 != null) {
                                    if ($e349.tid.equals($currentTid350) ||
                                          !$e349.tid.isDescendantOf(
                                                       $currentTid350)) {
                                        throw $e349;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit348) {
                            {  }
                            continue $label347;
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
                fabric.worker.transaction.TransactionManager $tm359 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled362 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff360 = 1;
                boolean $doBackoff361 = true;
                $label355: for (boolean $commit356 = false; !$commit356; ) {
                    if ($backoffEnabled362) {
                        if ($doBackoff361) {
                            if ($backoff360 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff360);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e357) {
                                        
                                    }
                                }
                            }
                            if ($backoff360 < 5000) $backoff360 *= 2;
                        }
                        $doBackoff361 = $backoff360 <= 32 || !$doBackoff361;
                    }
                    $commit356 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        fabric.metrics.util.AbstractSubject._Impl.
                          static_removeObserver(tmp, obs);
                        if (!tmp.isObserved()) { tmp.set$$expiry((long) -1); }
                    }
                    catch (final fabric.worker.RetryException $e357) {
                        $commit356 = false;
                        continue $label355;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e357) {
                        $commit356 = false;
                        fabric.common.TransactionID $currentTid358 =
                          $tm359.getCurrentTid();
                        if ($e357.tid.isDescendantOf($currentTid358))
                            continue $label355;
                        if ($currentTid358.parent != null) throw $e357;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e357) {
                        $commit356 = false;
                        if ($tm359.checkForStaleObjects()) continue $label355;
                        throw new fabric.worker.AbortException($e357);
                    }
                    finally {
                        if ($commit356) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e357) {
                                $commit356 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e357) {
                                $commit356 = false;
                                fabric.common.TransactionID $currentTid358 =
                                  $tm359.getCurrentTid();
                                if ($currentTid358 != null) {
                                    if ($e357.tid.equals($currentTid358) ||
                                          !$e357.tid.isDescendantOf(
                                                       $currentTid358)) {
                                        throw $e357;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit356) {
                            {  }
                            continue $label355;
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
                fabric.worker.transaction.TransactionManager $tm367 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled370 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff368 = 1;
                boolean $doBackoff369 = true;
                $label363: for (boolean $commit364 = false; !$commit364; ) {
                    if ($backoffEnabled370) {
                        if ($doBackoff369) {
                            if ($backoff368 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff368);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e365) {
                                        
                                    }
                                }
                            }
                            if ($backoff368 < 5000) $backoff368 *= 2;
                        }
                        $doBackoff369 = $backoff368 <= 32 || !$doBackoff369;
                    }
                    $commit364 = true;
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
                    catch (final fabric.worker.RetryException $e365) {
                        $commit364 = false;
                        continue $label363;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e365) {
                        $commit364 = false;
                        fabric.common.TransactionID $currentTid366 =
                          $tm367.getCurrentTid();
                        if ($e365.tid.isDescendantOf($currentTid366))
                            continue $label363;
                        if ($currentTid366.parent != null) throw $e365;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e365) {
                        $commit364 = false;
                        if ($tm367.checkForStaleObjects()) continue $label363;
                        throw new fabric.worker.AbortException($e365);
                    }
                    finally {
                        if ($commit364) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e365) {
                                $commit364 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e365) {
                                $commit364 = false;
                                fabric.common.TransactionID $currentTid366 =
                                  $tm367.getCurrentTid();
                                if ($currentTid366 != null) {
                                    if ($e365.tid.equals($currentTid366) ||
                                          !$e365.tid.isDescendantOf(
                                                       $currentTid366)) {
                                        throw $e365;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit364) {
                            {  }
                            continue $label363;
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
                        fabric.worker.transaction.TransactionManager $tm375 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled378 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff376 = 1;
                        boolean $doBackoff377 = true;
                        $label371: for (boolean $commit372 = false; !$commit372;
                                        ) {
                            if ($backoffEnabled378) {
                                if ($doBackoff377) {
                                    if ($backoff376 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff376);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e373) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff376 < 5000) $backoff376 *= 2;
                                }
                                $doBackoff377 = $backoff376 <= 32 ||
                                                  !$doBackoff377;
                            }
                            $commit372 = true;
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
                            catch (final fabric.worker.RetryException $e373) {
                                $commit372 = false;
                                continue $label371;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e373) {
                                $commit372 = false;
                                fabric.common.TransactionID $currentTid374 =
                                  $tm375.getCurrentTid();
                                if ($e373.tid.isDescendantOf($currentTid374))
                                    continue $label371;
                                if ($currentTid374.parent != null) throw $e373;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e373) {
                                $commit372 = false;
                                if ($tm375.checkForStaleObjects())
                                    continue $label371;
                                throw new fabric.worker.AbortException($e373);
                            }
                            finally {
                                if ($commit372) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e373) {
                                        $commit372 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e373) {
                                        $commit372 = false;
                                        fabric.common.TransactionID
                                          $currentTid374 =
                                          $tm375.getCurrentTid();
                                        if ($currentTid374 != null) {
                                            if ($e373.tid.equals(
                                                            $currentTid374) ||
                                                  !$e373.tid.
                                                  isDescendantOf(
                                                    $currentTid374)) {
                                                throw $e373;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit372) {
                                    {  }
                                    continue $label371;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -67, 59, -93, 92, 70,
    -51, -60, -110, -49, 84, -92, 93, 60, -102, 89, -52, -112, 30, -84, -36, 11,
    83, -32, -14, -126, 32, 65, -66, -8, 63, -80, 82 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507234491000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
