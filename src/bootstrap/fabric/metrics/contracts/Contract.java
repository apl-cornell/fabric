package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.util.AbstractSubject;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.util.Iterator;
import fabric.worker.Store;
import fabric.worker.Worker;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;

/**
 * A {@link Contract} consists of an assertion and an expiration time. If the
 * current time is earlier than the expiration time, then the contract is
 * <i>valid</i>: the enforcement protocol will ensure the assertion holds.
 * <p>
 * This class follows the subject-observer pattern. An instance can be an
 * observer of a {@link Metric} or other {@link Contract}s, and can be observed
 * by other {@link Contract}s.
 */
public interface Contract
  extends fabric.metrics.util.Observer, fabric.metrics.util.AbstractSubject {
    public fabric.metrics.contracts.Contract fabric$metrics$contracts$Contract$();
    
    /**
   * Extends the expiration time.
   *
   * @param newExpiry
   *        the new expiration time (computed at the current node) for
   *        this {@link Contract} given in milliseconds.
   */
    public void extendTo(long newExpiry);
    
    public boolean get$activated();
    
    public boolean set$activated(boolean val);
    
    /**
   * @return true if this contract is currently active (enforced).
   */
    public boolean isActivated();
    
    /**
   * Activate and start enforcing this {@link Contract}.
   */
    public void activate();
    
    /**
   * {@inheritDoc}
   *
   * Make stale and stop observing any evidence if this is no longer observed.
   */
    public void removeObserver(fabric.metrics.util.Observer obs);
    
    /**
   * Updates the expiration time of this contract, either extending or
   * revoking as needed.
   *
   * @param newExpiry
   *        the new expiration time associated with this {@link Contract}.
   * @return true iff the contract was retracted by this update.
   */
    public boolean update(long newExpiry);
    
    /**
   * Called to retract this contract's expiry in the current transaction
   * context.
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
   * @return true iff the contract is stale according to the local time.
   */
    public boolean stale(long time);
    
    /**
   * @return true iff the contract is stale according to the local time.
   */
    public boolean stale();
    
    public long getExpiry();
    
    /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   *
   * @param force
   *        flag indicating if a new policy should be found if the old one
   *        has expired.
   *
   * @return true iff the contract was retracted as a result of this
   *         operation.
   */
    public abstract boolean refresh(boolean force);
    
    public boolean handleUpdates();
    
    public void attemptExtension_remote(fabric.lang.security.Principal caller);
    
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
        public fabric.metrics.contracts.Contract
          fabric$metrics$contracts$Contract$() {
            fabric$metrics$util$AbstractSubject$();
            this.set$$expiry((long) -1);
            return (fabric.metrics.contracts.Contract) this.$getProxy();
        }
        
        /**
   * Extends the expiration time.
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
        
        private boolean activated = false;
        
        /**
   * @return true if this contract is currently active (enforced).
   */
        public boolean isActivated() { return this.get$activated(); }
        
        /**
   * Activate and start enforcing this {@link Contract}.
   */
        public void activate() {
            {
                fabric.worker.transaction.TransactionManager $tm26 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff27 = 1;
                boolean $doBackoff28 = true;
                $label22: for (boolean $commit23 = false; !$commit23; ) {
                    if ($doBackoff28) {
                        if ($backoff27 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff27);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e24) {  }
                            }
                        }
                        if ($backoff27 < 5000) $backoff27 *= 2;
                    }
                    $doBackoff28 = $backoff27 <= 32 || !$doBackoff28;
                    $commit23 = true;
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
                    catch (final fabric.worker.RetryException $e24) {
                        $commit23 = false;
                        continue $label22;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e24) {
                        $commit23 = false;
                        fabric.common.TransactionID $currentTid25 =
                          $tm26.getCurrentTid();
                        if ($e24.tid.isDescendantOf($currentTid25))
                            continue $label22;
                        if ($currentTid25.parent != null) throw $e24;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e24) {
                        $commit23 = false;
                        if ($tm26.checkForStaleObjects()) continue $label22;
                        throw new fabric.worker.AbortException($e24);
                    }
                    finally {
                        if ($commit23) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e24) {
                                $commit23 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e24) {
                                $commit23 = false;
                                fabric.common.TransactionID $currentTid25 =
                                  $tm26.getCurrentTid();
                                if ($currentTid25 != null) {
                                    if ($e24.tid.equals($currentTid25) ||
                                          !$e24.tid.isDescendantOf(
                                                      $currentTid25)) {
                                        throw $e24;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit23) {  }
                    }
                }
            }
        }
        
        /**
   * {@inheritDoc}
   *
   * Make stale and stop observing any evidence if this is no longer observed.
   */
        public void removeObserver(fabric.metrics.util.Observer obs) {
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
                        super.removeObserver(obs);
                        if (!isObserved()) { this.set$$expiry((long) -1); }
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
   * Updates the expiration time of this contract, either extending or
   * revoking as needed.
   *
   * @param newExpiry
   *        the new expiration time associated with this {@link Contract}.
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
   * Called to retract this contract's expiry in the current transaction
   * context.
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
   * @return true iff the contract is stale according to the local time.
   */
        public boolean stale(long time) {
            return isActivated() && getExpiry() < time;
        }
        
        /**
   * @return true iff the contract is stale according to the local time.
   */
        public boolean stale() {
            return isActivated() && getExpiry() <
              java.lang.System.currentTimeMillis();
        }
        
        public long getExpiry() {
            return this.get$$expiry() -
              fabric.metrics.contracts.Contract._Static._Proxy.$instance.
              get$DRIFT_FACTOR();
        }
        
        /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   *
   * @param force
   *        flag indicating if a new policy should be found if the old one
   *        has expired.
   *
   * @return true iff the contract was retracted as a result of this
   *         operation.
   */
        public abstract boolean refresh(boolean force);
        
        public boolean handleUpdates() {
            fabric.common.Logging.METRICS_LOGGER.log(
                                                   java.util.logging.Level.FINE,
                                                   "CHECKING CONTRACT CHANGE");
            if (valid()) return refresh(true);
            fabric.common.Logging.METRICS_LOGGER.log(
                                                   java.util.logging.Level.FINE,
                                                   "CONTRACT INVALID");
            return false;
        }
        
        public void attemptExtension_remote(
          fabric.lang.security.Principal caller) {
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
                    try { this.attemptExtension(); }
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
        
        public void attemptExtension() {
            fabric.common.Logging.METRICS_LOGGER.log(
                                                   java.util.logging.Level.INFO,
                                                   "ASYNC EXTENSION");
            refresh(false);
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
                        fabric.worker.transaction.TransactionManager $tm47 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff48 = 1;
                        boolean $doBackoff49 = true;
                        $label43: for (boolean $commit44 = false; !$commit44;
                                       ) {
                            if ($doBackoff49) {
                                if ($backoff48 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff48);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e45) {
                                            
                                        }
                                    }
                                }
                                if ($backoff48 < 5000) $backoff48 *= 2;
                            }
                            $doBackoff49 = $backoff48 <= 32 || !$doBackoff49;
                            $commit44 = true;
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
                                if ($tm47.checkForStaleObjects())
                                    continue $label43;
                                throw new fabric.worker.AbortException($e45);
                            }
                            finally {
                                if ($commit44) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e45) {
                                        $commit44 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e45) {
                                        $commit44 = false;
                                        fabric.common.TransactionID
                                          $currentTid46 = $tm47.getCurrentTid();
                                        if ($currentTid46 != null) {
                                            if ($e45.tid.equals(
                                                           $currentTid46) ||
                                                  !$e45.tid.isDescendantOf(
                                                              $currentTid46)) {
                                                throw $e45;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit44) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 4, -20, 93, 74, -4,
    -51, -87, -72, -24, -104, -7, -65, 67, -82, -60, -84, 16, 27, 98, -62, -78,
    117, 14, 27, -99, 113, 29, 26, -36, 29, 125, 70 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500326596000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
