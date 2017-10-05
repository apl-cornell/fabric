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
                        if (!this.get$activated()) {
                            fabric.common.Logging.METRICS_LOGGER.
                              log(java.util.logging.Level.FINER,
                                  "CONTRACT ACTIVATE");
                            this.set$activated(true);
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
                        super.removeObserver(obs);
                        if (!isObserved()) { this.set$$expiry((long) -1); }
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
                    try { this.attemptExtension(); }
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
   * Attempt to extend this {@link Contract}'s expiration time. (Invoked to
   * perform asynchronous extensions close to the current expiration time).
   */
        public void attemptExtension() {
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.FINER,
                "ASYNC EXTENSION OF " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.contracts.Contract)
                                    this.$getProxy())) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentTid());
            refresh(true);
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
    
    public static final byte[] $classHash = new byte[] { -128, -20, 36, -128,
    -80, -7, 95, 62, -101, -100, 95, 31, 68, 64, 123, -44, 65, 60, 89, -62, 102,
    -77, -55, -35, -113, -61, -15, 41, -95, 82, -27, -1 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507217545000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
