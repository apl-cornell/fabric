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
                    fabric.worker.transaction.TransactionManager $tm317 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled320 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff318 = 1;
                    boolean $doBackoff319 = true;
                    boolean $retry314 = true;
                    $label312: for (boolean $commit313 = false; !$commit313; ) {
                        if ($backoffEnabled320) {
                            if ($doBackoff319) {
                                if ($backoff318 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff318);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e315) {
                                            
                                        }
                                    }
                                }
                                if ($backoff318 < 5000) $backoff318 *= 2;
                            }
                            $doBackoff319 = $backoff318 <= 32 || !$doBackoff319;
                        }
                        $commit313 = true;
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
                        catch (final fabric.worker.RetryException $e315) {
                            $commit313 = false;
                            continue $label312;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e315) {
                            $commit313 = false;
                            fabric.common.TransactionID $currentTid316 =
                              $tm317.getCurrentTid();
                            if ($e315.tid.isDescendantOf($currentTid316))
                                continue $label312;
                            if ($currentTid316.parent != null) {
                                $retry314 = false;
                                throw $e315;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e315) {
                            $commit313 = false;
                            if ($tm317.checkForStaleObjects())
                                continue $label312;
                            $retry314 = false;
                            throw new fabric.worker.AbortException($e315);
                        }
                        finally {
                            if ($commit313) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e315) {
                                    $commit313 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e315) {
                                    $commit313 = false;
                                    fabric.common.TransactionID $currentTid316 =
                                      $tm317.getCurrentTid();
                                    if ($currentTid316 != null) {
                                        if ($e315.tid.equals($currentTid316) ||
                                              !$e315.tid.isDescendantOf(
                                                           $currentTid316)) {
                                            throw $e315;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit313 && $retry314) {
                                {  }
                                continue $label312;
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
                    fabric.worker.transaction.TransactionManager $tm326 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled329 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff327 = 1;
                    boolean $doBackoff328 = true;
                    boolean $retry323 = true;
                    $label321: for (boolean $commit322 = false; !$commit322; ) {
                        if ($backoffEnabled329) {
                            if ($doBackoff328) {
                                if ($backoff327 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff327);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e324) {
                                            
                                        }
                                    }
                                }
                                if ($backoff327 < 5000) $backoff327 *= 2;
                            }
                            $doBackoff328 = $backoff327 <= 32 || !$doBackoff328;
                        }
                        $commit322 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.util.AbstractSubject._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved()) {
                                tmp.set$$expiry((long) -1);
                            }
                        }
                        catch (final fabric.worker.RetryException $e324) {
                            $commit322 = false;
                            continue $label321;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e324) {
                            $commit322 = false;
                            fabric.common.TransactionID $currentTid325 =
                              $tm326.getCurrentTid();
                            if ($e324.tid.isDescendantOf($currentTid325))
                                continue $label321;
                            if ($currentTid325.parent != null) {
                                $retry323 = false;
                                throw $e324;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e324) {
                            $commit322 = false;
                            if ($tm326.checkForStaleObjects())
                                continue $label321;
                            $retry323 = false;
                            throw new fabric.worker.AbortException($e324);
                        }
                        finally {
                            if ($commit322) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e324) {
                                    $commit322 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e324) {
                                    $commit322 = false;
                                    fabric.common.TransactionID $currentTid325 =
                                      $tm326.getCurrentTid();
                                    if ($currentTid325 != null) {
                                        if ($e324.tid.equals($currentTid325) ||
                                              !$e324.tid.isDescendantOf(
                                                           $currentTid325)) {
                                            throw $e324;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit322 && $retry323) {
                                {  }
                                continue $label321;
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
                    fabric.worker.transaction.TransactionManager $tm335 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled338 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff336 = 1;
                    boolean $doBackoff337 = true;
                    boolean $retry332 = true;
                    $label330: for (boolean $commit331 = false; !$commit331; ) {
                        if ($backoffEnabled338) {
                            if ($doBackoff337) {
                                if ($backoff336 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff336);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e333) {
                                            
                                        }
                                    }
                                }
                                if ($backoff336 < 5000) $backoff336 *= 2;
                            }
                            $doBackoff337 = $backoff336 <= 32 || !$doBackoff337;
                        }
                        $commit331 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.refresh(true); }
                        catch (final fabric.worker.RetryException $e333) {
                            $commit331 = false;
                            continue $label330;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e333) {
                            $commit331 = false;
                            fabric.common.TransactionID $currentTid334 =
                              $tm335.getCurrentTid();
                            if ($e333.tid.isDescendantOf($currentTid334))
                                continue $label330;
                            if ($currentTid334.parent != null) {
                                $retry332 = false;
                                throw $e333;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e333) {
                            $commit331 = false;
                            if ($tm335.checkForStaleObjects())
                                continue $label330;
                            $retry332 = false;
                            throw new fabric.worker.AbortException($e333);
                        }
                        finally {
                            if ($commit331) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e333) {
                                    $commit331 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e333) {
                                    $commit331 = false;
                                    fabric.common.TransactionID $currentTid334 =
                                      $tm335.getCurrentTid();
                                    if ($currentTid334 != null) {
                                        if ($e333.tid.equals($currentTid334) ||
                                              !$e333.tid.isDescendantOf(
                                                           $currentTid334)) {
                                            throw $e333;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit331 && $retry332) {
                                {  }
                                continue $label330;
                            }
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
                        fabric.worker.transaction.TransactionManager $tm344 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled347 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff345 = 1;
                        boolean $doBackoff346 = true;
                        boolean $retry341 = true;
                        $label339: for (boolean $commit340 = false; !$commit340;
                                        ) {
                            if ($backoffEnabled347) {
                                if ($doBackoff346) {
                                    if ($backoff345 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff345);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e342) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff345 < 5000) $backoff345 *= 2;
                                }
                                $doBackoff346 = $backoff345 <= 32 ||
                                                  !$doBackoff346;
                            }
                            $commit340 = true;
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
                            catch (final fabric.worker.RetryException $e342) {
                                $commit340 = false;
                                continue $label339;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e342) {
                                $commit340 = false;
                                fabric.common.TransactionID $currentTid343 =
                                  $tm344.getCurrentTid();
                                if ($e342.tid.isDescendantOf($currentTid343))
                                    continue $label339;
                                if ($currentTid343.parent != null) {
                                    $retry341 = false;
                                    throw $e342;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e342) {
                                $commit340 = false;
                                if ($tm344.checkForStaleObjects())
                                    continue $label339;
                                $retry341 = false;
                                throw new fabric.worker.AbortException($e342);
                            }
                            finally {
                                if ($commit340) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e342) {
                                        $commit340 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e342) {
                                        $commit340 = false;
                                        fabric.common.TransactionID
                                          $currentTid343 =
                                          $tm344.getCurrentTid();
                                        if ($currentTid343 != null) {
                                            if ($e342.tid.equals(
                                                            $currentTid343) ||
                                                  !$e342.tid.
                                                  isDescendantOf(
                                                    $currentTid343)) {
                                                throw $e342;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit340 && $retry341) {
                                    {  }
                                    continue $label339;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -6, -47, -75, 11, -122,
    -102, -103, 80, -113, -28, -67, -76, -104, -36, -58, 98, -113, 116, 12, -73,
    -98, 81, 82, 8, 27, 93, 55, -10, 113, -113, -116, -40 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518448064000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
