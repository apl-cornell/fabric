package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
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
   * @return true iff the contract was retracted.
   */
    public abstract boolean refresh();
    
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
            long currentTime = java.lang.System.currentTimeMillis();
            if (newExpiry >
                  currentTime +
                  fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                  get$MIN_EXTENSION_FACTOR() * (getExpiry() - currentTime)) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerExtension((fabric.metrics.contracts.Contract)
                                      this.$getProxy());
            }
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.INFO,
                "EXTENDING {0} TO {1}",
                new fabric.lang.Object[] { (fabric.metrics.contracts.Contract)
                                             this.$getProxy(),
                  fabric.lang.WrappedJavaInlineable.$wrap(
                                                      new java.lang.Long(
                                                        newExpiry)) });
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
            if (!this.get$active()) {
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINEST,
                    "ACTIVATING {0}",
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap((fabric.metrics.contracts.Contract)
                                this.$getProxy()));
                this.set$active(true);
                refresh();
            }
        }
        
        /**
   * Deactivate and stop observing any evidence.
   */
        public void deactivate() {
            if (!isObserved()) {
                fabric.common.Logging.METRICS_LOGGER.
                  log(
                    java.util.logging.Level.FINEST,
                    "DEACTIVATING {0}",
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap((fabric.metrics.contracts.Contract)
                                this.$getProxy()));
                this.set$active(false);
                this.set$$expiry((long) -1);
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
            if (!localW.getStore(localW.getName()).equals($getStore()))
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
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.INFO,
                "REVOKING {0}",
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.
                  $unwrap((fabric.metrics.contracts.Contract)
                            this.$getProxy()));
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
        
        public long getExpiry() { return this.get$$expiry(); }
        
        /**
   * Update this contract's expiration time to stay valid in
   * response to a change in the value of the {@link Subject}s used
   * for enforcing this {@link Contract}. Revokes, extends, and
   * updates the enforcement strategy as needed.
   *
   * @return true iff the contract was retracted.
   */
        public abstract boolean refresh();
        
        public boolean handleUpdates() {
            if (isActive()) return refresh();
            return false;
        }
        
        public void attemptExtension_remote(
          fabric.lang.security.Principal caller) {
            this.attemptExtension();
        }
        
        public void attemptExtension() {
            fabric.common.Logging.METRICS_LOGGER.
              log(
                java.util.logging.Level.INFO,
                "ATTEMPTING EXTENSION OF {0}",
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.
                  $unwrap((fabric.metrics.contracts.Contract)
                            this.$getProxy()));
            this.refresh();
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
                        fabric.worker.transaction.TransactionManager $tm4 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
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
                                        catch (java.lang.
                                                 InterruptedException $e2) {
                                            
                                        }
                                    }
                                }
                                if ($backoff5 < 5000) $backoff5 *= 2;
                            }
                            $doBackoff6 = $backoff5 <= 32 || !$doBackoff6;
                            $commit1 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MIN_EXTENSION_FACTOR((double) 1.25);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MAX_EXTENSION((long) 30000);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$DRIFT_FACTOR((long) 50);
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
                                if ($tm4.checkForStaleObjects())
                                    continue $label0;
                                throw new fabric.worker.AbortException($e2);
                            }
                            finally {
                                if ($commit1) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e2) {
                                        $commit1 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e2) {
                                        $commit1 = false;
                                        fabric.common.TransactionID
                                          $currentTid3 = $tm4.getCurrentTid();
                                        if ($currentTid3 != null) {
                                            if ($e2.tid.equals($currentTid3) ||
                                                  !$e2.tid.isDescendantOf(
                                                             $currentTid3)) {
                                                throw $e2;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit1) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 88, -53, -33, 112, 69,
    -87, -54, -73, -50, 86, -69, 52, 16, 5, -35, -28, 74, -67, 2, 73, -77, 15,
    -44, 28, 112, 97, -104, 18, 46, -56, 18, 92 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492373310000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
