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
        
        public static final java.lang.Class[] $paramTypes0 = null;
        
        public void attemptExtension$remote(
          final fabric.worker.remote.RemoteWorker $remoteWorker,
          fabric.lang.security.Principal arg1) {
            if ($remoteWorker ==
                  fabric.worker.Worker.getWorker().getLocalWorker())
                attemptExtension();
            else
                try {
                    $remoteWorker.issueRemoteCall(this, "attemptExtension",
                                                  $paramTypes0, null);
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
                    fabric.worker.transaction.TransactionManager $tm5 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled8 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff6 = 1;
                    boolean $doBackoff7 = true;
                    boolean $retry2 = true;
                    $label0: for (boolean $commit1 = false; !$commit1; ) {
                        if ($backoffEnabled8) {
                            if ($doBackoff7) {
                                if ($backoff6 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff6);
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
                        try {
                            if (!tmp.get$activated()) {
                                fabric.common.Logging.METRICS_LOGGER.
                                  log(java.util.logging.Level.FINER,
                                      "CONTRACT ACTIVATE");
                                tmp.set$activated(true);
                            }
                        }
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
                        catch (final Throwable $e3) {
                            $commit1 = false;
                            if ($tm5.checkForStaleObjects()) continue $label0;
                            $retry2 = false;
                            throw new fabric.worker.AbortException($e3);
                        }
                        finally {
                            if ($commit1) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.AbortException $e3) {
                                    $commit1 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e3) {
                                    $commit1 = false;
                                    fabric.common.TransactionID $currentTid4 =
                                      $tm5.getCurrentTid();
                                    if ($currentTid4 != null) {
                                        if ($e3.tid.equals($currentTid4) ||
                                              !$e3.tid.isDescendantOf(
                                                         $currentTid4)) {
                                            throw $e3;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit1 && $retry2) {
                                {  }
                                continue $label0;
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
                    fabric.worker.transaction.TransactionManager $tm14 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled17 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff15 = 1;
                    boolean $doBackoff16 = true;
                    boolean $retry11 = true;
                    $label9: for (boolean $commit10 = false; !$commit10; ) {
                        if ($backoffEnabled17) {
                            if ($doBackoff16) {
                                if ($backoff15 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff15);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e12) {
                                            
                                        }
                                    }
                                }
                                if ($backoff15 < 5000) $backoff15 *= 2;
                            }
                            $doBackoff16 = $backoff15 <= 32 || !$doBackoff16;
                        }
                        $commit10 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.util.AbstractSubject._Impl.
                              static_removeObserver(tmp, obs);
                            if (!tmp.isObserved()) {
                                tmp.set$$expiry((long) -1);
                            }
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
                        catch (final Throwable $e12) {
                            $commit10 = false;
                            if ($tm14.checkForStaleObjects()) continue $label9;
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
                                    fabric.common.TransactionID $currentTid13 =
                                      $tm14.getCurrentTid();
                                    if ($currentTid13 != null) {
                                        if ($e12.tid.equals($currentTid13) ||
                                              !$e12.tid.isDescendantOf(
                                                          $currentTid13)) {
                                            throw $e12;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                    fabric.worker.transaction.TransactionManager $tm23 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled26 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff24 = 1;
                    boolean $doBackoff25 = true;
                    boolean $retry20 = true;
                    $label18: for (boolean $commit19 = false; !$commit19; ) {
                        if ($backoffEnabled26) {
                            if ($doBackoff25) {
                                if ($backoff24 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff24);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e21) {
                                            
                                        }
                                    }
                                }
                                if ($backoff24 < 5000) $backoff24 *= 2;
                            }
                            $doBackoff25 = $backoff24 <= 32 || !$doBackoff25;
                        }
                        $commit19 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { tmp.refresh(true); }
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
                        catch (final Throwable $e21) {
                            $commit19 = false;
                            if ($tm23.checkForStaleObjects()) continue $label18;
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
                                    fabric.common.TransactionID $currentTid22 =
                                      $tm23.getCurrentTid();
                                    if ($currentTid22 != null) {
                                        if ($e21.tid.equals($currentTid22) ||
                                              !$e21.tid.isDescendantOf(
                                                          $currentTid22)) {
                                            throw $e21;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                        fabric.worker.transaction.TransactionManager $tm32 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled35 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff33 = 1;
                        boolean $doBackoff34 = true;
                        boolean $retry29 = true;
                        $label27: for (boolean $commit28 = false; !$commit28;
                                       ) {
                            if ($backoffEnabled35) {
                                if ($doBackoff34) {
                                    if ($backoff33 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff33);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e30) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff33 < 5000) $backoff33 *= 2;
                                }
                                $doBackoff34 = $backoff33 <= 32 ||
                                                 !$doBackoff34;
                            }
                            $commit28 = true;
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
                            catch (final fabric.worker.RetryException $e30) {
                                $commit28 = false;
                                continue $label27;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e30) {
                                $commit28 = false;
                                fabric.common.TransactionID $currentTid31 =
                                  $tm32.getCurrentTid();
                                if ($e30.tid.isDescendantOf($currentTid31))
                                    continue $label27;
                                if ($currentTid31.parent != null) {
                                    $retry29 = false;
                                    throw $e30;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e30) {
                                $commit28 = false;
                                if ($tm32.checkForStaleObjects())
                                    continue $label27;
                                $retry29 = false;
                                throw new fabric.worker.AbortException($e30);
                            }
                            finally {
                                if ($commit28) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e30) {
                                        $commit28 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e30) {
                                        $commit28 = false;
                                        fabric.common.TransactionID
                                          $currentTid31 = $tm32.getCurrentTid();
                                        if ($currentTid31 != null) {
                                            if ($e30.tid.equals(
                                                           $currentTid31) ||
                                                  !$e30.tid.isDescendantOf(
                                                              $currentTid31)) {
                                                throw $e30;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit28 && $retry29) {
                                    {  }
                                    continue $label27;
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
    public static final long jlc$SourceLastModified$fabil = 1518538105000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
