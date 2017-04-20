package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
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
  extends fabric.metrics.util.Observer, fabric.metrics.util.Subject {
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
   * @return true iff this contract was revoked.
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
   * Update this contract's expiration time to stay valid in
   * response to a change in the value of the {@link Subject}s used
   * for enforcing this {@link Contract}. Revokes, extends, and
   * updates the enforcement strategy as needed.
   *
   * @param force
   *        flag to indicate if finding a new expiry should be forced.
   *
   * @return true iff the contract was retracted.
   */
    public abstract boolean refresh(boolean force);
    
    public boolean handleUpdates();
    
    public void attemptExtension_remote(fabric.lang.security.Principal caller);
    
    public void attemptExtension();
    
    public static class _Proxy extends fabric.metrics.util.Subject._Proxy
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
    
    public abstract static class _Impl extends fabric.metrics.util.Subject._Impl
      implements fabric.metrics.contracts.Contract {
        public fabric.metrics.contracts.Contract
          fabric$metrics$contracts$Contract$() {
            fabric$metrics$util$Subject$();
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
                  get$MIN_EXTENSION_FACTOR() *
                  (this.get$$expiry() - currentTime)) {
                for (fabric.util.Iterator iter = getObservers().iterator();
                     iter.hasNext(); ) {
                    fabric.metrics.util.Observer o =
                      (fabric.metrics.util.Observer)
                        fabric.lang.Object._Proxy.$getProxy(iter.next());
                    if (fabric.lang.Object._Proxy.
                          $getProxy((java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.$unwrap(o)) instanceof fabric.metrics.contracts.MetricContract) {
                        tm.registerParentExtension(
                             (fabric.metrics.contracts.Contract)
                               fabric.lang.Object._Proxy.$getProxy(o));
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.log(
                                                   java.util.logging.Level.INFO,
                                                   "SYNCH EXTENSION");
            tm.registerExtension((fabric.metrics.contracts.Contract)
                                   this.$getProxy());
            this.set$$expiry((long) newExpiry);
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
                fabric.worker.transaction.TransactionManager $tm4 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff5 = 1;
                boolean $doBackoff6 = true;
                $label0: for (boolean $commit1 = false; !$commit1; ) {
                    if ($doBackoff6) {
                        if ($backoff5 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff5);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e2) {  }
                            }
                        }
                        if ($backoff5 < 5000) $backoff5 *= 2;
                    }
                    $doBackoff6 = $backoff5 <= 32 || !$doBackoff6;
                    $commit1 = true;
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
                    catch (final fabric.worker.RetryException $e2) {
                        $commit1 = false;
                        continue $label0;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e2) {
                        $commit1 = false;
                        fabric.common.TransactionID $currentTid3 =
                          $tm4.getCurrentTid();
                        if ($e2.tid.isDescendantOf($currentTid3))
                            continue $label0;
                        if ($currentTid3.parent != null) throw $e2;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e2) {
                        $commit1 = false;
                        if ($tm4.checkForStaleObjects()) continue $label0;
                        throw new fabric.worker.AbortException($e2);
                    }
                    finally {
                        if ($commit1) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e2) {
                                $commit1 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e2) {
                                $commit1 = false;
                                fabric.common.TransactionID $currentTid3 =
                                  $tm4.getCurrentTid();
                                if ($currentTid3 != null) {
                                    if ($e2.tid.equals($currentTid3) ||
                                          !$e2.tid.isDescendantOf(
                                                     $currentTid3)) {
                                        throw $e2;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit1) {  }
                    }
                }
            }
        }
        
        /**
   * Deactivate and stop observing any evidence.
   */
        public void deactivate() {
            {
                fabric.worker.transaction.TransactionManager $tm11 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff12 = 1;
                boolean $doBackoff13 = true;
                $label7: for (boolean $commit8 = false; !$commit8; ) {
                    if ($doBackoff13) {
                        if ($backoff12 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff12);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e9) {  }
                            }
                        }
                        if ($backoff12 < 5000) $backoff12 *= 2;
                    }
                    $doBackoff13 = $backoff12 <= 32 || !$doBackoff13;
                    $commit8 = true;
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
                    catch (final fabric.worker.RetryException $e9) {
                        $commit8 = false;
                        continue $label7;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e9) {
                        $commit8 = false;
                        fabric.common.TransactionID $currentTid10 =
                          $tm11.getCurrentTid();
                        if ($e9.tid.isDescendantOf($currentTid10))
                            continue $label7;
                        if ($currentTid10.parent != null) throw $e9;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e9) {
                        $commit8 = false;
                        if ($tm11.checkForStaleObjects()) continue $label7;
                        throw new fabric.worker.AbortException($e9);
                    }
                    finally {
                        if ($commit8) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e9) {
                                $commit8 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e9) {
                                $commit8 = false;
                                fabric.common.TransactionID $currentTid10 =
                                  $tm11.getCurrentTid();
                                if ($currentTid10 != null) {
                                    if ($e9.tid.equals($currentTid10) ||
                                          !$e9.tid.isDescendantOf(
                                                     $currentTid10)) {
                                        throw $e9;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit8) {  }
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
   * @return true iff this contract was revoked.
   */
        public boolean update(long newExpiry) {
            fabric.worker.Worker localW = fabric.worker.Worker.getWorker();
            if (!localW.getStore(localW.getName()).equals(getStore()))
                newExpiry =
                  newExpiry -
                    fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                    get$DRIFT_FACTOR();
            long currentTime = java.lang.System.currentTimeMillis();
            newExpiry =
              java.lang.Math.
                min(
                  newExpiry,
                  currentTime +
                      fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                      get$MAX_EXTENSION());
            if (getExpiry() < newExpiry) {
                extendTo(newExpiry);
            } else if (getExpiry() > newExpiry) {
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
            return getExpiry() > time;
        }
        
        public long getExpiry() {
            return this.get$$expiry() -
              fabric.metrics.contracts.Contract._Static._Proxy.$instance.
              get$DRIFT_FACTOR();
        }
        
        /**
   * Update this contract's expiration time to stay valid in
   * response to a change in the value of the {@link Subject}s used
   * for enforcing this {@link Contract}. Revokes, extends, and
   * updates the enforcement strategy as needed.
   *
   * @param force
   *        flag to indicate if finding a new expiry should be forced.
   *
   * @return true iff the contract was retracted.
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
                fabric.worker.transaction.TransactionManager $tm18 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff19 = 1;
                boolean $doBackoff20 = true;
                $label14: for (boolean $commit15 = false; !$commit15; ) {
                    if ($doBackoff20) {
                        if ($backoff19 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff19);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e16) {  }
                            }
                        }
                        if ($backoff19 < 5000) $backoff19 *= 2;
                    }
                    $doBackoff20 = $backoff19 <= 32 || !$doBackoff20;
                    $commit15 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { this.attemptExtension(); }
                    catch (final fabric.worker.RetryException $e16) {
                        $commit15 = false;
                        continue $label14;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e16) {
                        $commit15 = false;
                        fabric.common.TransactionID $currentTid17 =
                          $tm18.getCurrentTid();
                        if ($e16.tid.isDescendantOf($currentTid17))
                            continue $label14;
                        if ($currentTid17.parent != null) throw $e16;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e16) {
                        $commit15 = false;
                        if ($tm18.checkForStaleObjects()) continue $label14;
                        throw new fabric.worker.AbortException($e16);
                    }
                    finally {
                        if ($commit15) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e16) {
                                $commit15 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e16) {
                                $commit15 = false;
                                fabric.common.TransactionID $currentTid17 =
                                  $tm18.getCurrentTid();
                                if ($currentTid17 != null) {
                                    if ($e16.tid.equals($currentTid17) ||
                                          !$e16.tid.isDescendantOf(
                                                      $currentTid17)) {
                                        throw $e16;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit15) {  }
                    }
                }
            }
        }
        
        public void attemptExtension() {
            fabric.common.Logging.METRICS_LOGGER.log(
                                                   java.util.logging.Level.INFO,
                                                   "ASYNC EXTENSION");
            this.refresh(false);
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
                        fabric.worker.transaction.TransactionManager $tm25 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff26 = 1;
                        boolean $doBackoff27 = true;
                        $label21: for (boolean $commit22 = false; !$commit22;
                                       ) {
                            if ($doBackoff27) {
                                if ($backoff26 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff26);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e23) {
                                            
                                        }
                                    }
                                }
                                if ($backoff26 < 5000) $backoff26 *= 2;
                            }
                            $doBackoff27 = $backoff26 <= 32 || !$doBackoff27;
                            $commit22 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MIN_EXTENSION_FACTOR((double) 0);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MAX_EXTENSION((long) 5000000000L);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$DRIFT_FACTOR((long) 0);
                            }
                            catch (final fabric.worker.RetryException $e23) {
                                $commit22 = false;
                                continue $label21;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e23) {
                                $commit22 = false;
                                fabric.common.TransactionID $currentTid24 =
                                  $tm25.getCurrentTid();
                                if ($e23.tid.isDescendantOf($currentTid24))
                                    continue $label21;
                                if ($currentTid24.parent != null) throw $e23;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e23) {
                                $commit22 = false;
                                if ($tm25.checkForStaleObjects())
                                    continue $label21;
                                throw new fabric.worker.AbortException($e23);
                            }
                            finally {
                                if ($commit22) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e23) {
                                        $commit22 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e23) {
                                        $commit22 = false;
                                        fabric.common.TransactionID
                                          $currentTid24 = $tm25.getCurrentTid();
                                        if ($currentTid24 != null) {
                                            if ($e23.tid.equals(
                                                           $currentTid24) ||
                                                  !$e23.tid.isDescendantOf(
                                                              $currentTid24)) {
                                                throw $e23;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit22) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -43, -125, 111, -5,
    -45, -39, -82, -44, 89, -86, -64, 96, -79, -109, -38, 66, -30, -118, 28, 67,
    -62, 117, -18, -114, -102, 57, 84, -70, -10, -42, -53, 56 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492662146000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
