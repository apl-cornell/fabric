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
                        tm.registerDelayedExtension(parent);
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
            {
                fabric.worker.transaction.TransactionManager $tm188 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled191 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff189 = 1;
                boolean $doBackoff190 = true;
                $label184: for (boolean $commit185 = false; !$commit185; ) {
                    if ($backoffEnabled191) {
                        if ($doBackoff190) {
                            if ($backoff189 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff189);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e186) {
                                        
                                    }
                                }
                            }
                            if ($backoff189 < 5000) $backoff189 *= 2;
                        }
                        $doBackoff190 = $backoff189 <= 32 || !$doBackoff190;
                    }
                    $commit185 = true;
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
                    catch (final fabric.worker.RetryException $e186) {
                        $commit185 = false;
                        continue $label184;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e186) {
                        $commit185 = false;
                        fabric.common.TransactionID $currentTid187 =
                          $tm188.getCurrentTid();
                        if ($e186.tid.isDescendantOf($currentTid187))
                            continue $label184;
                        if ($currentTid187.parent != null) throw $e186;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e186) {
                        $commit185 = false;
                        if ($tm188.checkForStaleObjects()) continue $label184;
                        throw new fabric.worker.AbortException($e186);
                    }
                    finally {
                        if ($commit185) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e186) {
                                $commit185 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e186) {
                                $commit185 = false;
                                fabric.common.TransactionID $currentTid187 =
                                  $tm188.getCurrentTid();
                                if ($currentTid187 != null) {
                                    if ($e186.tid.equals($currentTid187) ||
                                          !$e186.tid.isDescendantOf(
                                                       $currentTid187)) {
                                        throw $e186;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit185) {
                            {  }
                            continue $label184;
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
                fabric.worker.transaction.TransactionManager $tm196 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled199 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff197 = 1;
                boolean $doBackoff198 = true;
                $label192: for (boolean $commit193 = false; !$commit193; ) {
                    if ($backoffEnabled199) {
                        if ($doBackoff198) {
                            if ($backoff197 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff197);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e194) {
                                        
                                    }
                                }
                            }
                            if ($backoff197 < 5000) $backoff197 *= 2;
                        }
                        $doBackoff198 = $backoff197 <= 32 || !$doBackoff198;
                    }
                    $commit193 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        super.removeObserver(obs);
                        if (!isObserved()) { this.set$$expiry((long) -1); }
                    }
                    catch (final fabric.worker.RetryException $e194) {
                        $commit193 = false;
                        continue $label192;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e194) {
                        $commit193 = false;
                        fabric.common.TransactionID $currentTid195 =
                          $tm196.getCurrentTid();
                        if ($e194.tid.isDescendantOf($currentTid195))
                            continue $label192;
                        if ($currentTid195.parent != null) throw $e194;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e194) {
                        $commit193 = false;
                        if ($tm196.checkForStaleObjects()) continue $label192;
                        throw new fabric.worker.AbortException($e194);
                    }
                    finally {
                        if ($commit193) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e194) {
                                $commit193 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e194) {
                                $commit193 = false;
                                fabric.common.TransactionID $currentTid195 =
                                  $tm196.getCurrentTid();
                                if ($currentTid195 != null) {
                                    if ($e194.tid.equals($currentTid195) ||
                                          !$e194.tid.isDescendantOf(
                                                       $currentTid195)) {
                                        throw $e194;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit193) {
                            {  }
                            continue $label192;
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
                fabric.worker.transaction.TransactionManager $tm204 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled207 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff205 = 1;
                boolean $doBackoff206 = true;
                $label200: for (boolean $commit201 = false; !$commit201; ) {
                    if ($backoffEnabled207) {
                        if ($doBackoff206) {
                            if ($backoff205 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff205);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e202) {
                                        
                                    }
                                }
                            }
                            if ($backoff205 < 5000) $backoff205 *= 2;
                        }
                        $doBackoff206 = $backoff205 <= 32 || !$doBackoff206;
                    }
                    $commit201 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.attemptExtension(); }
                    catch (final fabric.worker.RetryException $e202) {
                        $commit201 = false;
                        continue $label200;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e202) {
                        $commit201 = false;
                        fabric.common.TransactionID $currentTid203 =
                          $tm204.getCurrentTid();
                        if ($e202.tid.isDescendantOf($currentTid203))
                            continue $label200;
                        if ($currentTid203.parent != null) throw $e202;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e202) {
                        $commit201 = false;
                        if ($tm204.checkForStaleObjects()) continue $label200;
                        throw new fabric.worker.AbortException($e202);
                    }
                    finally {
                        if ($commit201) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e202) {
                                $commit201 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e202) {
                                $commit201 = false;
                                fabric.common.TransactionID $currentTid203 =
                                  $tm204.getCurrentTid();
                                if ($currentTid203 != null) {
                                    if ($e202.tid.equals($currentTid203) ||
                                          !$e202.tid.isDescendantOf(
                                                       $currentTid203)) {
                                        throw $e202;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit201) {
                            {  }
                            continue $label200;
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
                        fabric.worker.transaction.TransactionManager $tm212 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled215 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff213 = 1;
                        boolean $doBackoff214 = true;
                        $label208: for (boolean $commit209 = false; !$commit209;
                                        ) {
                            if ($backoffEnabled215) {
                                if ($doBackoff214) {
                                    if ($backoff213 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff213);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e210) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff213 < 5000) $backoff213 *= 2;
                                }
                                $doBackoff214 = $backoff213 <= 32 ||
                                                  !$doBackoff214;
                            }
                            $commit209 = true;
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
                            catch (final fabric.worker.RetryException $e210) {
                                $commit209 = false;
                                continue $label208;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e210) {
                                $commit209 = false;
                                fabric.common.TransactionID $currentTid211 =
                                  $tm212.getCurrentTid();
                                if ($e210.tid.isDescendantOf($currentTid211))
                                    continue $label208;
                                if ($currentTid211.parent != null) throw $e210;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e210) {
                                $commit209 = false;
                                if ($tm212.checkForStaleObjects())
                                    continue $label208;
                                throw new fabric.worker.AbortException($e210);
                            }
                            finally {
                                if ($commit209) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e210) {
                                        $commit209 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e210) {
                                        $commit209 = false;
                                        fabric.common.TransactionID
                                          $currentTid211 =
                                          $tm212.getCurrentTid();
                                        if ($currentTid211 != null) {
                                            if ($e210.tid.equals(
                                                            $currentTid211) ||
                                                  !$e210.tid.
                                                  isDescendantOf(
                                                    $currentTid211)) {
                                                throw $e210;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit209) {
                                    {  }
                                    continue $label208;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 94, 0, 6, -114, 108,
    117, 27, 97, -102, -28, -33, -24, 77, -1, -96, 75, 2, -12, 53, -67, -88,
    -82, 47, 114, -86, -117, -110, 101, -88, -112, -29, -45 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507057228000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
