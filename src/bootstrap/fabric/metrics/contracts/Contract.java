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
                  get$MIN_EXTENSION_FACTOR() * (getExpiry() - currentTime)) {
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
                fabric.worker.transaction.TransactionManager $tm33 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff34 = 1;
                boolean $doBackoff35 = true;
                $label29: for (boolean $commit30 = false; !$commit30; ) {
                    if ($doBackoff35) {
                        if ($backoff34 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff34);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e31) {  }
                            }
                        }
                        if ($backoff34 < 5000) $backoff34 *= 2;
                    }
                    $doBackoff35 = $backoff34 <= 32 || !$doBackoff35;
                    $commit30 = true;
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
                    catch (final fabric.worker.RetryException $e31) {
                        $commit30 = false;
                        continue $label29;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e31) {
                        $commit30 = false;
                        fabric.common.TransactionID $currentTid32 =
                          $tm33.getCurrentTid();
                        if ($e31.tid.isDescendantOf($currentTid32))
                            continue $label29;
                        if ($currentTid32.parent != null) throw $e31;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e31) {
                        $commit30 = false;
                        if ($tm33.checkForStaleObjects()) continue $label29;
                        throw new fabric.worker.AbortException($e31);
                    }
                    finally {
                        if ($commit30) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e31) {
                                $commit30 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e31) {
                                $commit30 = false;
                                fabric.common.TransactionID $currentTid32 =
                                  $tm33.getCurrentTid();
                                if ($currentTid32 != null) {
                                    if ($e31.tid.equals($currentTid32) ||
                                          !$e31.tid.isDescendantOf(
                                                      $currentTid32)) {
                                        throw $e31;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit30) {  }
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
                fabric.worker.transaction.TransactionManager $tm40 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff41 = 1;
                boolean $doBackoff42 = true;
                $label36: for (boolean $commit37 = false; !$commit37; ) {
                    if ($doBackoff42) {
                        if ($backoff41 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff41);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e38) {  }
                            }
                        }
                        if ($backoff41 < 5000) $backoff41 *= 2;
                    }
                    $doBackoff42 = $backoff41 <= 32 || !$doBackoff42;
                    $commit37 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        super.removeObserver(obs);
                        if (!isObserved()) { this.set$$expiry((long) -1); }
                    }
                    catch (final fabric.worker.RetryException $e38) {
                        $commit37 = false;
                        continue $label36;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e38) {
                        $commit37 = false;
                        fabric.common.TransactionID $currentTid39 =
                          $tm40.getCurrentTid();
                        if ($e38.tid.isDescendantOf($currentTid39))
                            continue $label36;
                        if ($currentTid39.parent != null) throw $e38;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e38) {
                        $commit37 = false;
                        if ($tm40.checkForStaleObjects()) continue $label36;
                        throw new fabric.worker.AbortException($e38);
                    }
                    finally {
                        if ($commit37) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e38) {
                                $commit37 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e38) {
                                $commit37 = false;
                                fabric.common.TransactionID $currentTid39 =
                                  $tm40.getCurrentTid();
                                if ($currentTid39 != null) {
                                    if ($e38.tid.equals($currentTid39) ||
                                          !$e38.tid.isDescendantOf(
                                                      $currentTid39)) {
                                        throw $e38;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit37) {  }
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
            return isActivated() && getExpiry() >= time;
        }
        
        /**
   * @return true iff the contract is valid at the given time in the current
   *         transaction context.
   */
        public boolean valid() {
            return isActivated() && getExpiry() >=
              java.lang.System.currentTimeMillis();
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
                fabric.worker.transaction.TransactionManager $tm47 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff48 = 1;
                boolean $doBackoff49 = true;
                $label43: for (boolean $commit44 = false; !$commit44; ) {
                    if ($doBackoff49) {
                        if ($backoff48 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff48);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e45) {  }
                            }
                        }
                        if ($backoff48 < 5000) $backoff48 *= 2;
                    }
                    $doBackoff49 = $backoff48 <= 32 || !$doBackoff49;
                    $commit44 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.attemptExtension(); }
                    catch (final fabric.worker.RetryException $e45) {
                        $commit44 = false;
                        continue $label43;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e45) {
                        $commit44 = false;
                        fabric.common.TransactionID $currentTid46 =
                          $tm47.getCurrentTid();
                        if ($e45.tid.isDescendantOf($currentTid46))
                            continue $label43;
                        if ($currentTid46.parent != null) throw $e45;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e45) {
                        $commit44 = false;
                        if ($tm47.checkForStaleObjects()) continue $label43;
                        throw new fabric.worker.AbortException($e45);
                    }
                    finally {
                        if ($commit44) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e45) {
                                $commit44 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e45) {
                                $commit44 = false;
                                fabric.common.TransactionID $currentTid46 =
                                  $tm47.getCurrentTid();
                                if ($currentTid46 != null) {
                                    if ($e45.tid.equals($currentTid46) ||
                                          !$e45.tid.isDescendantOf(
                                                      $currentTid46)) {
                                        throw $e45;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit44) {  }
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
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeDouble(this.MIN_EXTENSION_FACTOR);
                out.writeLong(this.DRIFT_FACTOR);
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
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.Contract._Static._Proxy(
                         this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm54 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff55 = 1;
                        boolean $doBackoff56 = true;
                        $label50: for (boolean $commit51 = false; !$commit51;
                                       ) {
                            if ($doBackoff56) {
                                if ($backoff55 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff55);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e52) {
                                            
                                        }
                                    }
                                }
                                if ($backoff55 < 5000) $backoff55 *= 2;
                            }
                            $doBackoff56 = $backoff55 <= 32 || !$doBackoff56;
                            $commit51 = true;
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
                            }
                            catch (final fabric.worker.RetryException $e52) {
                                $commit51 = false;
                                continue $label50;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e52) {
                                $commit51 = false;
                                fabric.common.TransactionID $currentTid53 =
                                  $tm54.getCurrentTid();
                                if ($e52.tid.isDescendantOf($currentTid53))
                                    continue $label50;
                                if ($currentTid53.parent != null) throw $e52;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e52) {
                                $commit51 = false;
                                if ($tm54.checkForStaleObjects())
                                    continue $label50;
                                throw new fabric.worker.AbortException($e52);
                            }
                            finally {
                                if ($commit51) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e52) {
                                        $commit51 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e52) {
                                        $commit51 = false;
                                        fabric.common.TransactionID
                                          $currentTid53 = $tm54.getCurrentTid();
                                        if ($currentTid53 != null) {
                                            if ($e52.tid.equals(
                                                           $currentTid53) ||
                                                  !$e52.tid.isDescendantOf(
                                                              $currentTid53)) {
                                                throw $e52;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit51) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -71, -59, 116, -102,
    -33, 58, -17, -14, -100, -128, 12, -9, 1, -67, -88, 103, -118, -48, -16,
    -34, -90, 55, 75, 73, -81, 109, -114, -51, -64, -88, -87, 24 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1501602696000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
