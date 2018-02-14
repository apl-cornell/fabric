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
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (!tmp.get$activated()) {
                    fabric.common.Logging.METRICS_LOGGER.
                      log(java.util.logging.Level.FINER, "CONTRACT ACTIVATE");
                    tmp.set$activated(true);
                }
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm345 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled348 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff346 = 1;
                    boolean $doBackoff347 = true;
                    boolean $retry342 = true;
                    $label340: for (boolean $commit341 = false; !$commit341; ) {
                        if ($backoffEnabled348) {
                            if ($doBackoff347) {
                                if ($backoff346 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff346);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e343) {
                                            
                                        }
                                    }
                                }
                                if ($backoff346 < 5000) $backoff346 *= 2;
                            }
                            $doBackoff347 = $backoff346 <= 32 || !$doBackoff347;
                        }
                        $commit341 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated()) {
                                fabric.common.Logging.METRICS_LOGGER.
                                  log(java.util.logging.Level.FINER,
                                      "CONTRACT ACTIVATE");
                                tmp.set$activated(true);
                            }
                        }
                        catch (final fabric.worker.RetryException $e343) {
                            $commit341 = false;
                            continue $label340;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e343) {
                            $commit341 = false;
                            fabric.common.TransactionID $currentTid344 =
                              $tm345.getCurrentTid();
                            if ($e343.tid.isDescendantOf($currentTid344))
                                continue $label340;
                            if ($currentTid344.parent != null) {
                                $retry342 = false;
                                throw $e343;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e343) {
                            $commit341 = false;
                            if ($tm345.checkForStaleObjects())
                                continue $label340;
                            $retry342 = false;
                            throw new fabric.worker.AbortException($e343);
                        }
                        finally {
                            if ($commit341) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e343) {
                                    $commit341 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e343) {
                                    $commit341 = false;
                                    fabric.common.TransactionID $currentTid344 =
                                      $tm345.getCurrentTid();
                                    if ($currentTid344 != null) {
                                        if ($e343.tid.equals($currentTid344) ||
                                              !$e343.tid.isDescendantOf(
                                                           $currentTid344)) {
                                            throw $e343;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit341 && $retry342) {
                                {  }
                                continue $label340;
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
                    fabric.worker.transaction.TransactionManager $tm354 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled357 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff355 = 1;
                    boolean $doBackoff356 = true;
                    boolean $retry351 = true;
                    $label349: for (boolean $commit350 = false; !$commit350; ) {
                        if ($backoffEnabled357) {
                            if ($doBackoff356) {
                                if ($backoff355 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff355);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e352) {
                                            
                                        }
                                    }
                                }
                                if ($backoff355 < 5000) $backoff355 *= 2;
                            }
                            $doBackoff356 = $backoff355 <= 32 || !$doBackoff356;
                        }
                        $commit350 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.util.AbstractSubject._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved()) {
                                tmp.set$$expiry((long) -1);
                            }
                        }
                        catch (final fabric.worker.RetryException $e352) {
                            $commit350 = false;
                            continue $label349;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e352) {
                            $commit350 = false;
                            fabric.common.TransactionID $currentTid353 =
                              $tm354.getCurrentTid();
                            if ($e352.tid.isDescendantOf($currentTid353))
                                continue $label349;
                            if ($currentTid353.parent != null) {
                                $retry351 = false;
                                throw $e352;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e352) {
                            $commit350 = false;
                            if ($tm354.checkForStaleObjects())
                                continue $label349;
                            $retry351 = false;
                            throw new fabric.worker.AbortException($e352);
                        }
                        finally {
                            if ($commit350) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e352) {
                                    $commit350 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e352) {
                                    $commit350 = false;
                                    fabric.common.TransactionID $currentTid353 =
                                      $tm354.getCurrentTid();
                                    if ($currentTid353 != null) {
                                        if ($e352.tid.equals($currentTid353) ||
                                              !$e352.tid.isDescendantOf(
                                                           $currentTid353)) {
                                            throw $e352;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit350 && $retry351) {
                                {  }
                                continue $label349;
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
                "RETRACTION IN {0}",
                fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentLog());
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
            this.get$lock().checkForRead();
            if (valid()) return refresh(false);
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
                    fabric.worker.transaction.TransactionManager $tm363 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled366 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff364 = 1;
                    boolean $doBackoff365 = true;
                    boolean $retry360 = true;
                    $label358: for (boolean $commit359 = false; !$commit359; ) {
                        if ($backoffEnabled366) {
                            if ($doBackoff365) {
                                if ($backoff364 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff364);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e361) {
                                            
                                        }
                                    }
                                }
                                if ($backoff364 < 5000) $backoff364 *= 2;
                            }
                            $doBackoff365 = $backoff364 <= 32 || !$doBackoff365;
                        }
                        $commit359 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.refresh(true); }
                        catch (final fabric.worker.RetryException $e361) {
                            $commit359 = false;
                            continue $label358;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e361) {
                            $commit359 = false;
                            fabric.common.TransactionID $currentTid362 =
                              $tm363.getCurrentTid();
                            if ($e361.tid.isDescendantOf($currentTid362))
                                continue $label358;
                            if ($currentTid362.parent != null) {
                                $retry360 = false;
                                throw $e361;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e361) {
                            $commit359 = false;
                            if ($tm363.checkForStaleObjects())
                                continue $label358;
                            $retry360 = false;
                            throw new fabric.worker.AbortException($e361);
                        }
                        finally {
                            if ($commit359) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e361) {
                                    $commit359 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e361) {
                                    $commit359 = false;
                                    fabric.common.TransactionID $currentTid362 =
                                      $tm363.getCurrentTid();
                                    if ($currentTid362 != null) {
                                        if ($e361.tid.equals($currentTid362) ||
                                              !$e361.tid.isDescendantOf(
                                                           $currentTid362)) {
                                            throw $e361;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit359 && $retry360) {
                                {  }
                                continue $label358;
                            }
                        }
                    }
                }
            }
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
                        fabric.worker.transaction.TransactionManager $tm372 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled375 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff373 = 1;
                        boolean $doBackoff374 = true;
                        boolean $retry369 = true;
                        $label367: for (boolean $commit368 = false; !$commit368;
                                        ) {
                            if ($backoffEnabled375) {
                                if ($doBackoff374) {
                                    if ($backoff373 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff373);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e370) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff373 < 5000) $backoff373 *= 2;
                                }
                                $doBackoff374 = $backoff373 <= 32 ||
                                                  !$doBackoff374;
                            }
                            $commit368 = true;
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
                            catch (final fabric.worker.RetryException $e370) {
                                $commit368 = false;
                                continue $label367;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e370) {
                                $commit368 = false;
                                fabric.common.TransactionID $currentTid371 =
                                  $tm372.getCurrentTid();
                                if ($e370.tid.isDescendantOf($currentTid371))
                                    continue $label367;
                                if ($currentTid371.parent != null) {
                                    $retry369 = false;
                                    throw $e370;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e370) {
                                $commit368 = false;
                                if ($tm372.checkForStaleObjects())
                                    continue $label367;
                                $retry369 = false;
                                throw new fabric.worker.AbortException($e370);
                            }
                            finally {
                                if ($commit368) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e370) {
                                        $commit368 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e370) {
                                        $commit368 = false;
                                        fabric.common.TransactionID
                                          $currentTid371 =
                                          $tm372.getCurrentTid();
                                        if ($currentTid371 != null) {
                                            if ($e370.tid.equals(
                                                            $currentTid371) ||
                                                  !$e370.tid.
                                                  isDescendantOf(
                                                    $currentTid371)) {
                                                throw $e370;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit368 && $retry369) {
                                    {  }
                                    continue $label367;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -3, 116, -30, 115, 126,
    -32, 52, -85, 15, 126, -76, -94, 102, 88, -96, 13, 104, -106, 68, -55, -32,
    94, 31, 110, 115, 116, -44, -112, -24, -112, -10, 58 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518571543000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
