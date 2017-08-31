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
    public abstract boolean refresh();
    
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
        
        public boolean refresh() {
            return ((fabric.metrics.contracts.Contract) fetch()).refresh();
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
            if (newExpiry >
                  currentTime +
                  fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                  get$MIN_EXTENSION_FACTOR() *
                  (getExpiry() - currentTime) ||
                  getExpiry() -
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
                        tm.registerDelayedExtension(
                             (fabric.metrics.contracts.Contract)
                               fabric.lang.Object._Proxy.$getProxy(parent));
                    }
                }
                fabric.common.Logging.METRICS_LOGGER.
                  log(java.util.logging.Level.INFO, "SYNCH EXTENSION");
                tm.registerExtension((fabric.metrics.contracts.Contract)
                                       this.$getProxy());
                this.set$$expiry((long) newExpiry);
            }
            else {
                fabric.common.Logging.METRICS_LOGGER.
                  log(java.util.logging.Level.INFO, "DELAYED EXTENSION");
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
                fabric.worker.transaction.TransactionManager $tm324 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff325 = 1;
                boolean $doBackoff326 = true;
                $label320: for (boolean $commit321 = false; !$commit321; ) {
                    if ($doBackoff326) {
                        if ($backoff325 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff325);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e322) {
                                    
                                }
                            }
                        }
                        if ($backoff325 < 5000) $backoff325 *= 1;
                    }
                    $doBackoff326 = $backoff325 <= 32 || !$doBackoff326;
                    $commit321 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (!this.get$activated()) {
                            fabric.common.Logging.METRICS_LOGGER.
                              log(java.util.logging.Level.FINE,
                                  "CONTRACT ACTIVATE");
                            this.set$activated(true);
                        }
                    }
                    catch (final fabric.worker.RetryException $e322) {
                        $commit321 = false;
                        continue $label320;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e322) {
                        $commit321 = false;
                        fabric.common.TransactionID $currentTid323 =
                          $tm324.getCurrentTid();
                        if ($e322.tid.isDescendantOf($currentTid323))
                            continue $label320;
                        if ($currentTid323.parent != null) throw $e322;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e322) {
                        $commit321 = false;
                        if ($tm324.checkForStaleObjects()) continue $label320;
                        throw new fabric.worker.AbortException($e322);
                    }
                    finally {
                        if ($commit321) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e322) {
                                $commit321 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e322) {
                                $commit321 = false;
                                fabric.common.TransactionID $currentTid323 =
                                  $tm324.getCurrentTid();
                                if ($currentTid323 != null) {
                                    if ($e322.tid.equals($currentTid323) ||
                                          !$e322.tid.isDescendantOf(
                                                       $currentTid323)) {
                                        throw $e322;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit321) {
                            {  }
                            continue $label320;
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
                fabric.worker.transaction.TransactionManager $tm331 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff332 = 1;
                boolean $doBackoff333 = true;
                $label327: for (boolean $commit328 = false; !$commit328; ) {
                    if ($doBackoff333) {
                        if ($backoff332 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff332);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e329) {
                                    
                                }
                            }
                        }
                        if ($backoff332 < 5000) $backoff332 *= 1;
                    }
                    $doBackoff333 = $backoff332 <= 32 || !$doBackoff333;
                    $commit328 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        super.removeObserver(obs);
                        if (!isObserved()) { this.set$$expiry((long) -1); }
                    }
                    catch (final fabric.worker.RetryException $e329) {
                        $commit328 = false;
                        continue $label327;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e329) {
                        $commit328 = false;
                        fabric.common.TransactionID $currentTid330 =
                          $tm331.getCurrentTid();
                        if ($e329.tid.isDescendantOf($currentTid330))
                            continue $label327;
                        if ($currentTid330.parent != null) throw $e329;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e329) {
                        $commit328 = false;
                        if ($tm331.checkForStaleObjects()) continue $label327;
                        throw new fabric.worker.AbortException($e329);
                    }
                    finally {
                        if ($commit328) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e329) {
                                $commit328 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e329) {
                                $commit328 = false;
                                fabric.common.TransactionID $currentTid330 =
                                  $tm331.getCurrentTid();
                                if ($currentTid330 != null) {
                                    if ($e329.tid.equals($currentTid330) ||
                                          !$e329.tid.isDescendantOf(
                                                       $currentTid330)) {
                                        throw $e329;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit328) {
                            {  }
                            continue $label327;
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
                  fine(
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
                  fine(
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
                                                   java.util.logging.Level.INFO,
                                                   "REVOCATION");
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRetraction((fabric.metrics.contracts.Contract)
                                   this.$getProxy());
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
        public abstract boolean refresh();
        
        public boolean handleUpdates() {
            fabric.common.Logging.METRICS_LOGGER.log(
                                                   java.util.logging.Level.FINE,
                                                   "CHECKING CONTRACT CHANGE");
            if (valid()) return refresh();
            fabric.common.Logging.METRICS_LOGGER.log(
                                                   java.util.logging.Level.FINE,
                                                   "CONTRACT INVALID");
            return false;
        }
        
        /**
   * Attempt to extend this {@link Contract}'s expiration time. (Invoked to
   * perform asynchronous extensions close to the current expiration time).
   */
        public void attemptExtension_remote(
          fabric.lang.security.Principal caller) {
            {
                fabric.worker.transaction.TransactionManager $tm338 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff339 = 1;
                boolean $doBackoff340 = true;
                $label334: for (boolean $commit335 = false; !$commit335; ) {
                    if ($doBackoff340) {
                        if ($backoff339 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff339);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e336) {
                                    
                                }
                            }
                        }
                        if ($backoff339 < 5000) $backoff339 *= 1;
                    }
                    $doBackoff340 = $backoff339 <= 32 || !$doBackoff340;
                    $commit335 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.attemptExtension(); }
                    catch (final fabric.worker.RetryException $e336) {
                        $commit335 = false;
                        continue $label334;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e336) {
                        $commit335 = false;
                        fabric.common.TransactionID $currentTid337 =
                          $tm338.getCurrentTid();
                        if ($e336.tid.isDescendantOf($currentTid337))
                            continue $label334;
                        if ($currentTid337.parent != null) throw $e336;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e336) {
                        $commit335 = false;
                        if ($tm338.checkForStaleObjects()) continue $label334;
                        throw new fabric.worker.AbortException($e336);
                    }
                    finally {
                        if ($commit335) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e336) {
                                $commit335 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e336) {
                                $commit335 = false;
                                fabric.common.TransactionID $currentTid337 =
                                  $tm338.getCurrentTid();
                                if ($currentTid337 != null) {
                                    if ($e336.tid.equals($currentTid337) ||
                                          !$e336.tid.isDescendantOf(
                                                       $currentTid337)) {
                                        throw $e336;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit335) {
                            {  }
                            continue $label334;
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
            fabric.common.Logging.METRICS_LOGGER.log(
                                                   java.util.logging.Level.INFO,
                                                   "ASYNC EXTENSION");
            refresh();
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
                        fabric.worker.transaction.TransactionManager $tm345 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff346 = 1;
                        boolean $doBackoff347 = true;
                        $label341: for (boolean $commit342 = false; !$commit342;
                                        ) {
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
                                if ($backoff346 < 5000) $backoff346 *= 1;
                            }
                            $doBackoff347 = $backoff346 <= 32 || !$doBackoff347;
                            $commit342 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MIN_EXTENSION_FACTOR((double) 1);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$DRIFT_FACTOR((long) 100);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$EXTENSION_WINDOW((long) 1000);
                            }
                            catch (final fabric.worker.RetryException $e343) {
                                $commit342 = false;
                                continue $label341;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e343) {
                                $commit342 = false;
                                fabric.common.TransactionID $currentTid344 =
                                  $tm345.getCurrentTid();
                                if ($e343.tid.isDescendantOf($currentTid344))
                                    continue $label341;
                                if ($currentTid344.parent != null) throw $e343;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e343) {
                                $commit342 = false;
                                if ($tm345.checkForStaleObjects())
                                    continue $label341;
                                throw new fabric.worker.AbortException($e343);
                            }
                            finally {
                                if ($commit342) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e343) {
                                        $commit342 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e343) {
                                        $commit342 = false;
                                        fabric.common.TransactionID
                                          $currentTid344 =
                                          $tm345.getCurrentTid();
                                        if ($currentTid344 != null) {
                                            if ($e343.tid.equals(
                                                            $currentTid344) ||
                                                  !$e343.tid.
                                                  isDescendantOf(
                                                    $currentTid344)) {
                                                throw $e343;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit342) {
                                    {  }
                                    continue $label341;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 55, 26, -84, 39, -105,
    32, 111, 8, 96, -37, 60, -37, 45, 19, 123, 12, 37, -44, -118, -54, -102,
    115, 95, -19, -32, 34, 46, 15, 105, 120, -50, 16 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1504207838000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
