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
            if (!this.get$active()) {
                this.set$active(true);
                refresh();
            }
        }
        
        /**
   * Deactivate and stop observing any evidence.
   */
        public void deactivate() {
            if (!isObserved()) {
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
   * @return true iff the contract was retracted.
   */
        public abstract boolean refresh();
        
        public boolean handleUpdates() {
            if (isActive()) return refresh();
            return false;
        }
        
        public void attemptExtension_remote(
          fabric.lang.security.Principal caller) {
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
                    try { this.attemptExtension(); }
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
        
        public void attemptExtension() {
            fabric.common.Logging.METRICS_LOGGER.log(
                                                   java.util.logging.Level.INFO,
                                                   "ASYNC EXTENSION");
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
                        fabric.worker.transaction.TransactionManager $tm33 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff34 = 1;
                        boolean $doBackoff35 = true;
                        $label29: for (boolean $commit30 = false; !$commit30;
                                       ) {
                            if ($doBackoff35) {
                                if ($backoff34 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff34);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e31) {
                                            
                                        }
                                    }
                                }
                                if ($backoff34 < 5000) $backoff34 *= 2;
                            }
                            $doBackoff35 = $backoff34 <= 32 || !$doBackoff35;
                            $commit30 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MIN_EXTENSION_FACTOR((double) 5);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MAX_EXTENSION((long) 5000);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$DRIFT_FACTOR((long) 100);
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
                                if ($tm33.checkForStaleObjects())
                                    continue $label29;
                                throw new fabric.worker.AbortException($e31);
                            }
                            finally {
                                if ($commit30) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e31) {
                                        $commit30 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e31) {
                                        $commit30 = false;
                                        fabric.common.TransactionID
                                          $currentTid32 = $tm33.getCurrentTid();
                                        if ($currentTid32 != null) {
                                            if ($e31.tid.equals(
                                                           $currentTid32) ||
                                                  !$e31.tid.isDescendantOf(
                                                              $currentTid32)) {
                                                throw $e31;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit30) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -15, 79, -51, 23, -75,
    -112, 48, 121, -84, 62, -63, 7, -96, 113, 75, 120, -120, -43, 98, 40, -75,
    72, -105, -41, -13, 18, 94, -8, 54, 23, -75, -95 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492535467000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVZe2wUxxmfO/zGYGMeBgPmddBCyF0gESoxpTUXO75wxq5totQ0uHO7c/bivd1ld86c09JC+gBFLWoTnlJipRIoCXVIlZaiKqWiVdomJUV9JKWV+kBV0yaiVKWoSaQ+0u+bnXut7w7fH7U0883NfN/MN9/jN7PjyRuk0rHJyjiNaXqQj1vMCXbSWCTaS22HqWGdOs4A9A4pMysix996Rm31E3+U1CvUMA1NofqQ4XAyO7qHjtGQwXhoZ1+kbRepVVCwizojnPh3bUvZZLll6uPDusnlIlPmP3ZH6OiJ3Y0vziANg6RBM/o55ZoSNg3OUnyQ1CdYIsZsp11VmTpI5hiMqf3M1qiuPQKMpjFImhxt2KA8aTOnjzmmPoaMTU7SYrZYM92J6pugtp1UuGmD+o2u+kmu6aGo5vC2KKmKa0xXnb3kM6QiSirjOh0GxgXR9C5CYsZQJ/YDe50GatpxqrC0SMWoZqicLPNKZHYc2A4MIFqdYHzEzCxVYVDoIE2uSjo1hkP93NaMYWCtNJOwCictRScFphqLKqN0mA1xstDL1+sOAVetMAuKcDLfyyZmAp+1eHyW460bO7Yc+ZTRZfiJD3RWmaKj/jUg1OoR6mNxZjNDYa5g/brocbrg4mE/IcA838Ps8lz49M2Prm+99IrLs7gAT09sD1P4kHI6NvvnS8JrN89ANWos09EwFPJ2LrzaK0faUhZE+4LMjDgYTA9e6vvRxw+cZdf9pC5CqhRTTyYgquYoZsLSdGbfzwxmU87UCKllhhoW4xFSDe2oZjC3tycedxiPkApddFWZ4jeYKA5ToImqoa0ZcTPdtigfEe2URQhphEJ8UL5LSPMaoAsI8f+Dk97QiJlgoZieZPsgvENQGLWVkRDkra0pIcdWQnbS4BowyS6IIiBOCEKd21ThECWyFQRdrP/DnCncR+M+nw9MvEwxVRajDvhLxs62Xh3So8vUVWYPKfqRixEy9+IpET+1GPMOxK2wkA98vsSLFrmyR5PbOm6eG7rsxh7KSgNyssJVNCgVDWYUDaYVBd3qMbWCAFZBAKtJXyoYnoh8XURQlSNSLTNdPUx3r6VTHjftRIr4fGJv84S8CB1w/CgACmBG/dr+hx/45OGVMyBmrX0V6EZgDXgzKIs7EWhRSIshpeHQW++8cHy/mc0lTgJTUnyqJKboSq+hbFNhKkBgdvp1y+n5oYv7A36El1q0CIXYBBhp9a6Rl6ptadhDa1RGyUy0AdVxKI1VdXzENvdle0QAzMaqyY0FNJZHQYGYH+63nvr1lbfvFmdJGlwbclC4n/G2nITGyRpE6s7J2n7AZgz4fney94ljNw7tEoYHjlWFFgxgHYZEppDBpv2FV/b+5g+/P/26P+ssTqqsZEzXlJTYy5z34c8H5b9YMCuxAylgc1giwvIMJFi48pqsbgAOOgAUqO4EdhoJU9XiGo3pDCPl3w2rN5z/65FG19069LjGs8n620+Q7V+0jRy4vPvdVjGNT8HDKWu/LJuLeHOzM7fbNh1HPVIHf7H01I/pUxD5gFeO9ggTEESEPYhw4EZhiztFvcEzdg9WK11rLRH9Fc5U9O/EYzQbi4OhySdbwluvu2mfiUWcY0WBtH+Q5qTJxrOJf/pXVv3QT6oHSaM4wanBH6SAXxAGg3AGO2HZGSWz8sbzz1P38GjL5NoSbx7kLOvNgizcQBu5sV3nBr4bOC5oE7IJyipCKldLWomjcy2s56V8RDTuFSKrRL0Gq7XCkDOwuY4jHOEdiJNaLZFIcvS/WOkOTuZ1R3YMdTw00LGjP9KzY6izPTzQ01fA/r22loAUGpOnLzt89LH3g0eOurHnXlFWTbkl5Mq41xSx7CyxdgpWWVFqFSHR+ZcX9r/07P5D7hHelH/gdhjJxPO/+s9rwZPXXi0A4lWqCZnIXATBelO+Ze+C8gFCqgKS1hWwbFcpy2K1FauPpM05q7v9oaw5hVS73C2S+zgkqukeDAVVCkJZB6qclfTxAir1lqdS/X19kc4B6Vns6y60eh2uvgJKB7RVSbsLrL6z8OqAe9WWrY0BiKUyk/px0lo5WVTSjpxJwUdwlIK/C1mqOmaaOqMCtBtThZf1ywivoTFHHMvZxcVfg7z63JT0jzmL5yCOT7TnQwp7zn0RZj0xh9ljLrq0YNguLXafFSF7+tGjE2rPmQ1+CXAdkHfctO7U2RjTcxadhQkw5XupW9zis2h17frSzeHRN4fdBFjmWdnL/Vz35Kv3r1Ee95MZGVia8umQL9SWD0Z1NoMvH2MgD5KWZ6xaj1bdDGUhRByV9J7cOMlGV6EgqbVskwNqMtUTJjPlXHdLus7rqcKnBy8xNoZVgouPUvBmQDo1kLnMBdKXuUBW6T35W10LBVSqnpD0YJGtYmVN3RGKHJB0X/Ed+VzziOwUs+4vsa3PYpWCmIfvWfhSGDALwsyYqameDWEmksVQHoA0/Jmk35uu70SmeZxWIye5KOmF6TntsRJjX8Lq87A7zWkX0IC/P1FoJxAl5GNg5i5JN5dwzaGpeqPIhyTdOD29nygxdgyrI4hEqDXAIP4+WEjvFii7YNE/S/pGeXqjyOuSXpme3k+WGJvA6gQH0Ge31RwOSbIHgItKGi5PcxTZJumWMpLhTAn1n8HqaThHkpYqVfcGS31a9c/Bd/GXJbXLy2MU2SvpSBmqnyuh+jeweg5Ut9mYOVrc6ngqf5WQplFJ+8uzOor0SRotQ/ULJVT/DlYvclI5RnVNLZqhi6CcImRuk6T+8jRHEZ9Lm/41vUi/VGLsB1i9BEfQMOMdKUuzx8VmPYrPR358MTlDyLyTkh4qonjB68herKgHKefJmb4oKZ/efn5SYuw1rF6Ge5LN4jZzRoq6YTmUZ2HNy5J+qzw3oMg3JX1+emr/ssSYgLsrcFUeoYaqs50ib52iyuO18Ry45WFJW8pTHkUWSTr3ttGfvga2ymsgfuYFHaYkbY2P49eJoWgWdS9xi7zPOkKb35bYubh8XuWkmXLOEhYEIZzfDnxbD9ksYZbAXXTgt+ECu0XS1eXZAEUCkrZOz4Fvlxi7jtWfOGn0bkPojxeT9LUKXwwWF3jAkw/LSvhldvrN7evnF3m8WzjlqV/KnZtoqGme2HlVvERlHo1ro6QmntT13C/rnHaVBXmiiR3Uut/ZliB/52Rhsdc+7r4tiLawxd9cmVuczM6X4eL9HVu5fO8Aurt8+OtdYfaWbJUOt8WFvjr6k+J5QTCKyVqSNv4PZPJW83tVNQPXxJsTBsfNnp82n//KXeOTW79f/fTe7anDb8Q+eL7rxNVbTbvf29R8/mv/A2QEp2GbGQAA";
}
