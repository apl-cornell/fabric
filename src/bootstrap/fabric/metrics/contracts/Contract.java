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
        /**
   * @param store
   *            the {@link Store} this {@link Contract} is stored at
   */
        public fabric.metrics.contracts.Contract
          fabric$metrics$contracts$Contract$() {
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
            this.activated = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.Contract._Impl src =
              (fabric.metrics.contracts.Contract._Impl) other;
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
    
    public static final byte[] $classHash = new byte[] { -116, 32, -122, -49,
    -115, 75, 12, -24, 7, 12, 104, 91, -66, 1, -57, -9, -36, -70, 88, 33, -33,
    9, 12, 57, -81, 122, -118, 73, 31, 40, -92, 57 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507216270000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
