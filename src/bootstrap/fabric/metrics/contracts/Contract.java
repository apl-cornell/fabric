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
                fabric.worker.transaction.TransactionManager $tm168 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff169 = 1;
                boolean $doBackoff170 = true;
                $label164: for (boolean $commit165 = false; !$commit165; ) {
                    if ($doBackoff170) {
                        if ($backoff169 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff169);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e166) {
                                    
                                }
                            }
                        }
                        if ($backoff169 < 5000) $backoff169 *= 1;
                    }
                    $doBackoff170 = $backoff169 <= 32 || !$doBackoff170;
                    $commit165 = true;
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
                    catch (final fabric.worker.RetryException $e166) {
                        $commit165 = false;
                        continue $label164;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e166) {
                        $commit165 = false;
                        fabric.common.TransactionID $currentTid167 =
                          $tm168.getCurrentTid();
                        if ($e166.tid.isDescendantOf($currentTid167))
                            continue $label164;
                        if ($currentTid167.parent != null) throw $e166;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e166) {
                        $commit165 = false;
                        if ($tm168.checkForStaleObjects()) continue $label164;
                        throw new fabric.worker.AbortException($e166);
                    }
                    finally {
                        if ($commit165) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e166) {
                                $commit165 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e166) {
                                $commit165 = false;
                                fabric.common.TransactionID $currentTid167 =
                                  $tm168.getCurrentTid();
                                if ($currentTid167 != null) {
                                    if ($e166.tid.equals($currentTid167) ||
                                          !$e166.tid.isDescendantOf(
                                                       $currentTid167)) {
                                        throw $e166;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit165) {
                            {  }
                            continue $label164;
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
                fabric.worker.transaction.TransactionManager $tm175 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff176 = 1;
                boolean $doBackoff177 = true;
                $label171: for (boolean $commit172 = false; !$commit172; ) {
                    if ($doBackoff177) {
                        if ($backoff176 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff176);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e173) {
                                    
                                }
                            }
                        }
                        if ($backoff176 < 5000) $backoff176 *= 1;
                    }
                    $doBackoff177 = $backoff176 <= 32 || !$doBackoff177;
                    $commit172 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        super.removeObserver(obs);
                        if (!isObserved()) { this.set$$expiry((long) -1); }
                    }
                    catch (final fabric.worker.RetryException $e173) {
                        $commit172 = false;
                        continue $label171;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e173) {
                        $commit172 = false;
                        fabric.common.TransactionID $currentTid174 =
                          $tm175.getCurrentTid();
                        if ($e173.tid.isDescendantOf($currentTid174))
                            continue $label171;
                        if ($currentTid174.parent != null) throw $e173;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e173) {
                        $commit172 = false;
                        if ($tm175.checkForStaleObjects()) continue $label171;
                        throw new fabric.worker.AbortException($e173);
                    }
                    finally {
                        if ($commit172) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e173) {
                                $commit172 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e173) {
                                $commit172 = false;
                                fabric.common.TransactionID $currentTid174 =
                                  $tm175.getCurrentTid();
                                if ($currentTid174 != null) {
                                    if ($e173.tid.equals($currentTid174) ||
                                          !$e173.tid.isDescendantOf(
                                                       $currentTid174)) {
                                        throw $e173;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit172) {
                            {  }
                            continue $label171;
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
                fabric.worker.transaction.TransactionManager $tm182 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff183 = 1;
                boolean $doBackoff184 = true;
                $label178: for (boolean $commit179 = false; !$commit179; ) {
                    if ($doBackoff184) {
                        if ($backoff183 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff183);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e180) {
                                    
                                }
                            }
                        }
                        if ($backoff183 < 5000) $backoff183 *= 1;
                    }
                    $doBackoff184 = $backoff183 <= 32 || !$doBackoff184;
                    $commit179 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.attemptExtension(); }
                    catch (final fabric.worker.RetryException $e180) {
                        $commit179 = false;
                        continue $label178;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e180) {
                        $commit179 = false;
                        fabric.common.TransactionID $currentTid181 =
                          $tm182.getCurrentTid();
                        if ($e180.tid.isDescendantOf($currentTid181))
                            continue $label178;
                        if ($currentTid181.parent != null) throw $e180;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e180) {
                        $commit179 = false;
                        if ($tm182.checkForStaleObjects()) continue $label178;
                        throw new fabric.worker.AbortException($e180);
                    }
                    finally {
                        if ($commit179) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e180) {
                                $commit179 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e180) {
                                $commit179 = false;
                                fabric.common.TransactionID $currentTid181 =
                                  $tm182.getCurrentTid();
                                if ($currentTid181 != null) {
                                    if ($e180.tid.equals($currentTid181) ||
                                          !$e180.tid.isDescendantOf(
                                                       $currentTid181)) {
                                        throw $e180;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit179) {
                            {  }
                            continue $label178;
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
                        fabric.worker.transaction.TransactionManager $tm189 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff190 = 1;
                        boolean $doBackoff191 = true;
                        $label185: for (boolean $commit186 = false; !$commit186;
                                        ) {
                            if ($doBackoff191) {
                                if ($backoff190 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff190);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e187) {
                                            
                                        }
                                    }
                                }
                                if ($backoff190 < 5000) $backoff190 *= 1;
                            }
                            $doBackoff191 = $backoff190 <= 32 || !$doBackoff191;
                            $commit186 = true;
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
                            catch (final fabric.worker.RetryException $e187) {
                                $commit186 = false;
                                continue $label185;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e187) {
                                $commit186 = false;
                                fabric.common.TransactionID $currentTid188 =
                                  $tm189.getCurrentTid();
                                if ($e187.tid.isDescendantOf($currentTid188))
                                    continue $label185;
                                if ($currentTid188.parent != null) throw $e187;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e187) {
                                $commit186 = false;
                                if ($tm189.checkForStaleObjects())
                                    continue $label185;
                                throw new fabric.worker.AbortException($e187);
                            }
                            finally {
                                if ($commit186) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e187) {
                                        $commit186 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e187) {
                                        $commit186 = false;
                                        fabric.common.TransactionID
                                          $currentTid188 =
                                          $tm189.getCurrentTid();
                                        if ($currentTid188 != null) {
                                            if ($e187.tid.equals(
                                                            $currentTid188) ||
                                                  !$e187.tid.
                                                  isDescendantOf(
                                                    $currentTid188)) {
                                                throw $e187;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit186) {
                                    {  }
                                    continue $label185;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -7, 50, -12, 75, -71,
    89, 100, 65, -77, -17, 67, 36, 31, 82, 124, 114, -87, 19, 45, -87, -103,
    -54, 124, -57, 59, -125, -48, 67, -60, -71, 87, -93 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506965626000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
