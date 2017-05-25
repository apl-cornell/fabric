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
    
    public boolean get$active();
    
    public boolean set$active(boolean val);
    
    /**
   * @return true if this contract is currently active (enforced).
   */
    public boolean isActive();
    
    /**
   * Activate and start enforcing this {@link Contract}.
   */
    public void activate();
    
    /**
   * Deactivate and stop observing any evidence.
   */
    public void deactivate();
    
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
   * Called to revoke this contract in the current transaction context.
   *
   * @param newExpiry
   *        time to set the expiry back to.
   */
    public void revoke(long newExpiry);
    
    /**
   * @param time
   *        the time we're checking validity against.
   * @return true iff the contract is valid at the given time in the current
   *       transaction context.
   */
    public boolean valid(long time);
    
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
        public boolean get$active() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              get$active();
        }
        
        public boolean set$active(boolean val) {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              set$active(val);
        }
        
        public fabric.metrics.contracts.Contract
          fabric$metrics$contracts$Contract$() {
            return ((fabric.metrics.contracts.Contract) fetch()).
              fabric$metrics$contracts$Contract$();
        }
        
        public void extendTo(long arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).extendTo(arg1);
        }
        
        public boolean isActive() {
            return ((fabric.metrics.contracts.Contract) fetch()).isActive();
        }
        
        public void activate() {
            ((fabric.metrics.contracts.Contract) fetch()).activate();
        }
        
        public void deactivate() {
            ((fabric.metrics.contracts.Contract) fetch()).deactivate();
        }
        
        public boolean update(long arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).update(arg1);
        }
        
        public void revoke(long arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).revoke(arg1);
        }
        
        public boolean valid(long arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).valid(arg1);
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
                        tm.registerParentExtension(
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
        
        public boolean get$active() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.active;
        }
        
        public boolean set$active(boolean val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.active = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private boolean active = false;
        
        /**
   * @return true if this contract is currently active (enforced).
   */
        public boolean isActive() { return this.get$active(); }
        
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
                        if (!this.get$active()) {
                            fabric.common.Logging.METRICS_LOGGER.
                              log(java.util.logging.Level.FINE,
                                  "CONTRACT ACTIVATE");
                            this.set$active(true);
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
   * Deactivate and stop observing any evidence.
   */
        public void deactivate() {
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
                        if (!isObserved()) {
                            fabric.common.Logging.METRICS_LOGGER.
                              log(java.util.logging.Level.FINE,
                                  "CONTRACT DEACTIVATE");
                            this.set$active(false);
                            this.set$$expiry((long) -1);
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
            }
            else if (getExpiry() > newExpiry) {
                revoke(newExpiry);
                return true;
            }
            return false;
        }
        
        /**
   * Called to revoke this contract in the current transaction context.
   *
   * @param newExpiry
   *        time to set the expiry back to.
   */
        public void revoke(long newExpiry) {
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
            if (!isActive()) return false;
            return getExpiry() >= time;
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
            if (isActive()) return refresh(true);
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
            out.writeBoolean(this.active);
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
            this.active = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.Contract._Impl src =
              (fabric.metrics.contracts.Contract._Impl) other;
            this.active = src.active;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public double get$MIN_EXTENSION_FACTOR();
        
        public double set$MIN_EXTENSION_FACTOR(double val);
        
        public double postInc$MIN_EXTENSION_FACTOR();
        
        public double postDec$MIN_EXTENSION_FACTOR();
        
        public long get$MAX_EXTENSION();
        
        public long set$MAX_EXTENSION(long val);
        
        public long postInc$MAX_EXTENSION();
        
        public long postDec$MAX_EXTENSION();
        
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
            
            public long get$MAX_EXTENSION() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).get$MAX_EXTENSION();
            }
            
            public long set$MAX_EXTENSION(long val) {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).set$MAX_EXTENSION(val);
            }
            
            public long postInc$MAX_EXTENSION() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postInc$MAX_EXTENSION();
            }
            
            public long postDec$MAX_EXTENSION() {
                return ((fabric.metrics.contracts.Contract._Static._Impl)
                          fetch()).postDec$MAX_EXTENSION();
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
            
            public long get$MAX_EXTENSION() { return this.MAX_EXTENSION; }
            
            public long set$MAX_EXTENSION(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.MAX_EXTENSION = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$MAX_EXTENSION() {
                long tmp = this.get$MAX_EXTENSION();
                this.set$MAX_EXTENSION((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$MAX_EXTENSION() {
                long tmp = this.get$MAX_EXTENSION();
                this.set$MAX_EXTENSION((long) (tmp - 1));
                return tmp;
            }
            
            public long MAX_EXTENSION;
            
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
                out.writeLong(this.MAX_EXTENSION);
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
                this.MAX_EXTENSION = in.readLong();
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
                                  set$MIN_EXTENSION_FACTOR((double) 2);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MAX_EXTENSION((long)
                                                      (1000L * 60 * 60 * 24));
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
    
    public static final byte[] $classHash = new byte[] { -36, 117, 30, -15, 66,
    27, -51, -20, -79, -128, -50, -22, -35, 62, -1, 32, -123, 16, 29, -53, -74,
    65, 27, -9, 36, -62, 24, -92, -50, 104, -94, -49 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1495740956000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
