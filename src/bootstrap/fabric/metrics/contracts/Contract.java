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
            long
              adjustedNewExpiry =
              java.lang.Math.
              min(
                newExpiry,
                currentTime +
                    fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                    get$MAX_EXTENSION());
            if (adjustedNewExpiry >
                  currentTime +
                  fabric.metrics.contracts.Contract._Static._Proxy.$instance.
                  get$MIN_EXTENSION_FACTOR() * (getExpiry() - currentTime)) {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerExtension((fabric.metrics.contracts.Contract)
                                      this.$getProxy());
            }
            final fabric.worker.Store s = $getStore();
            this.set$$expiry((long) adjustedNewExpiry);
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
                  info(
                    "ACTIVATING " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap((fabric.metrics.contracts.Contract)
                                        this.$getProxy())) +
                      " IN " +
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     ).
                        getCurrentTid());
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
                  info(
                    "DEACTIVATING " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap((fabric.metrics.contracts.Contract)
                                        this.$getProxy())) +
                      " IN " +
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     ).
                        getCurrentTid());
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
              info(
                "REVOKING " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.contracts.Contract)
                                    this.$getProxy())) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentTid());
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
        
        public boolean handleUpdates() { return refresh(); }
        
        public void attemptExtension_remote(
          fabric.lang.security.Principal caller) {
            this.attemptExtension();
        }
        
        public void attemptExtension() {
            fabric.common.Logging.METRICS_LOGGER.
              info(
                "ATTEMPTING EXTENSION OF " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap((fabric.metrics.contracts.Contract)
                                    this.$getProxy())) +
                  " IN " +
                  fabric.worker.transaction.TransactionManager.getInstance().
                    getCurrentTid());
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
                        fabric.worker.transaction.TransactionManager $tm26 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff27 = 1;
                        boolean $doBackoff28 = true;
                        $label22: for (boolean $commit23 = false; !$commit23;
                                       ) {
                            if ($doBackoff28) {
                                if ($backoff27 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff27);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e24) {
                                            
                                        }
                                    }
                                }
                                if ($backoff27 < 5000) $backoff27 *= 2;
                            }
                            $doBackoff28 = $backoff27 <= 32 || !$doBackoff28;
                            $commit23 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MIN_EXTENSION_FACTOR((double) 1.01);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MAX_EXTENSION((long) 5000);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$DRIFT_FACTOR((long) 50);
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
                                if ($tm26.checkForStaleObjects())
                                    continue $label22;
                                throw new fabric.worker.AbortException($e24);
                            }
                            finally {
                                if ($commit23) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e24) {
                                        $commit23 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e24) {
                                        $commit23 = false;
                                        fabric.common.TransactionID
                                          $currentTid25 = $tm26.getCurrentTid();
                                        if ($currentTid25 != null) {
                                            if ($e24.tid.equals(
                                                           $currentTid25) ||
                                                  !$e24.tid.isDescendantOf(
                                                              $currentTid25)) {
                                                throw $e24;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit23) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -76, 28, 11, -26, -104,
    -119, -55, -108, 5, -108, 6, -46, -86, 77, 5, 74, -124, -119, -23, 51, -36,
    -71, -29, 114, 33, -58, -47, -103, 46, 63, 45, -98 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492364484000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALUZbWwUx3Xu/G0MNsZ8GTBfF1oI3IWkakucJjWHDQdnbPmDEtPmMrc7Zy/s7S67c+ZIQ5tUaYEoRWpjCEjBiipQGkpJGoVGUYqEqjSFJkqb9INWSlLUNm0opUoUFSr1I31vdu5rfT58P2pp3pubee/Nm/c1M+vT10iVY5NlCRrX9CDfazEn2EXjkWgvtR2mhnXqOAMwGlOmVUaOvP+02uYn/ihpUKhhGppC9ZjhcDIjupOO0pDBeGiwL9K+g9QpyLiJOiOc+HesT9tkiWXqe4d1k8tFJsg/fGto7In7mp6vII1DpFEz+jnlmhI2Dc7SfIg0JFkyzmynQ1WZOkRmGoyp/czWqK49AISmMUSaHW3YoDxlM6ePOaY+ioTNTspitlgzM4jqm6C2nVK4aYP6Ta76Ka7poajm8PYoqU5oTFed3eQrpDJKqhI6HQbCOdHMLkJCYqgLx4G8XgM17QRVWIalcpdmqJws9nJkdxzYAgTAWpNkfMTMLlVpUBggza5KOjWGQ/3c1oxhIK0yU7AKJ62TCgWiWosqu+gwi3Eyz0vX604BVZ0wC7JwMttLJiSBz1o9Psvz1rWtdx36srHJ8BMf6KwyRUf9a4GpzcPUxxLMZobCXMaGVdEjdM65A35CgHi2h9ilefHBDz+/uu38BZdmQRGanvhOpvCYciI+482F4ZXrKlCNWst0NAyFgp0Lr/bKmfa0BdE+JysRJ4OZyfN9r9770Cl21U/qI6RaMfVUEqJqpmImLU1n9kZmMJtypkZIHTPUsJiPkBroRzWDuaM9iYTDeIRU6mKo2hS/wUQJEIEmqoG+ZiTMTN+ifET00xYhpAka8UF7jpA53wHcQoj/LCe9oREzyUJxPcX2QHiHoDFqKyMhyFtbU0KOrYTslME1IJJDEEWAnBCEOrepwiFKZC8Iulj/B5lp3EfTHp8PTLxYMVUWpw74S8bO+l4d0mOTqavMjin6oXMRMuvcMRE/dRjzDsStsJAPfL7QWy3yecdS6zs/PBN7zY095JUG5GSpq2hQKhrMKhrMKAq6NWBqBaFYBaFYnfalg+HxyPdEBFU7ItWy4hpA3J2WTnnCtJNp4vOJvbUIfhE64PhdUFCgZjSs7P/S5vsPLKuAmLX2VKIbgTTgzaBc3YlAj0JaxJTG/e9ff/bIPjOXS5wEJqT4RE5M0WVeQ9mmwlQogTnxq5bQs7Fz+wJ+LC91aBEKsQllpM27RkGqtmfKHlqjKkqmoQ2ojlOZWlXPR2xzT25EBMAMBM1uLKCxPAqKivm5fuv4b9+4coc4SzLFtTGvCvcz3p6X0CisUaTuzJztB2zGgO6do72PH762f4cwPFAsL7ZgAGEYEplCBpv21y/s/t3v3z3xK3/OWZxUW6m4rilpsZeZH8OfD9p/sWFW4gBiqM1hWRGWZEuChSuvyOkGxUGHAgWqO4FBI2mqWkKjcZ1hpPy78Za1Z/92qMl1tw4jrvFssvrmAnLj89eTh16770abEONT8HDK2S9H5la8WTnJHbZN96Ie6YffWnTsp/Q4RD7UK0d7gIkSRIQ9iHDg7cIWawRc65n7FIJlrrUWivFKZ2L178JjNBeLQ6HTT7aG777qpn02FlHG0iJpv43mpcntp5L/8C+r/omf1AyRJnGCU4Nvo1C/IAyG4Ax2wnIwSqYXzBeep+7h0Z7NtYXePMhb1psFuXIDfaTGfr0b+G7ggCHmoJHWQVsKRrkq8QWcnWUhbEn7iOjcKViWC7gCwUphyArsruJYjvAOxEmdlkymOPpfrHQrJy3dka2xzu0DnVv7Iz1bY10d4YGeviL277W1JKTQqDx92YGxRz8OHhpzY8+9oiyfcEvI53GvKWLZ6WLtNKyytNQqgqPrL8/ue/m7+/a7R3hz4YHbaaSS3//Nf14PHr18sUgRr1ZNyETmVhCEny607G3QbiGk6q8S/6KIZTeVsiyCuxHckzHn9O6O7TlzCq4OuVtEGzgkqukeDEVVWgPtkwSOe4m3F1GptzyVGjb0RboGpGdxrLvY6vXEDS9yN4F8kjhaZPXB4qtD3auxbG0Uilg6K9SPQuuksC0Sb8gTCj6CoxT8XcxSNXHT1BkVRbspXXxZv4zwWhp3xLGcW1z8NcqrzwsSn8xbPK/i+ER/NqSw59wXYdYTd5g96laXVgzbRZPdZ0XInvja2Ljac3KtXxa4Tsg7blprdDbK9LxFp2MCTHgvdYtbfK5aXb66aF1413vDbgIs9qzspX6m+/TFjSuUb/tJRbYsTXg6FDK1FxajepvBy8cYKChJS7JWbciUJAjXioCL/Tfy4yQXXcWCpM6yTQ5Vk6meMJkmZV2X+JrXU8VPD15ibhRBkotHKXgzIJ0ayF7mApnLXCCn9M7Cra6EBnJrFIm7JtkqAmvijpClU+K7Jt+RzzWPyE4hdV+JbX0VQRpiHt6z8FIYMIuWmVFTUz0bwkwkC6CFof9zic9N1Xci0zxOq5VCfiTxD6fmtEdLzD2G4BHYneZ0iNKAv79YbCfzoG2GyrVR4s+WcM3+iXojy2ckXjs1vR8vMXcYwSGsRKg1lEH8/XAxvVuhDUJ4jEj8hfL0RpZtEvdOTe8nS8yNI3iCk3qV3VTzT0BjULhGJN5cnubIEpE4XEYynCyh/tMInoJzJGWpUnVvsDRkVH8Q3sVLJK4qL4+RpdLFjf8qQ/UzJVR/DsEzoLrNRs1dk1sdT+WDhMy8LvG75VkdWd6R+FIZqr9YQvWXEDzPSdUo1TV10gydD+1bhDQfl/ib5WmOLI9J/I2pRfr5EnM/RvAyHEHDjHemLc3eKzbrUXw20q+ABkq3tEhcMYniRa8juxFQT6XMSPK7eNYHU9vPz0rMvY7gFbgn2SxhM2dkUjdgzD8Fa2+U+Lby3IAsIYlXTk3tX5aY+zWCN+CqPEINVWeDIm+dSZWPQjsBK/9AYqM85ZElKfHwTaM/cw1sk9dAfOYFHaakbI3vxdeJoWgWdS9x872fdYQ2b5fY+R8QXOJkLuWcJS0IQji/HXhbx2yWNEvUXXTgKQjNPRLfX54NkCUm8b1Tc+CVEnNXEfyJkybvNoT+eDHJXKvwi8GCIh/w5IdlJfwKO/HeltWzJ/l4N2/Cp37Jd2a8sXbu+OAl8SUq+9G4LkpqEyldz39Z5/WrLcgTTeygzn1nWwJ9wMm8yb72cffbgugLW/zd5fmIkxmFPFx8f8dePt11qO4uHf66IczemgOZcFtQ7NXRnxKfFwShENaasvF/IKc/mvvP6tqBy+KbEwbHCwun/fnowYtjVWPVb53qrtr8yMErd7z90h/tpa++eSx4z5rx/wH2iY/amxkAAA==";
}
