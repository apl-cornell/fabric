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
    public long get$lastAdvertised();
    
    public long set$lastAdvertised(long val);
    
    public long postInc$lastAdvertised();
    
    public long postDec$lastAdvertised();
    
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
        public long get$lastAdvertised() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              get$lastAdvertised();
        }
        
        public long set$lastAdvertised(long val) {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              set$lastAdvertised(val);
        }
        
        public long postInc$lastAdvertised() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              postInc$lastAdvertised();
        }
        
        public long postDec$lastAdvertised() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              postDec$lastAdvertised();
        }
        
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
        public long get$lastAdvertised() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.lastAdvertised;
        }
        
        public long set$lastAdvertised(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.lastAdvertised = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$lastAdvertised() {
            long tmp = this.get$lastAdvertised();
            this.set$lastAdvertised((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$lastAdvertised() {
            long tmp = this.get$lastAdvertised();
            this.set$lastAdvertised((long) (tmp - 1));
            return tmp;
        }
        
        private long lastAdvertised;
        
        public fabric.metrics.contracts.Contract
          fabric$metrics$contracts$Contract$() {
            fabric$metrics$util$Subject$();
            this.set$$expiry((long) -1);
            this.set$lastAdvertised((long) -1);
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
                  get$MIN_EXTENSION_FACTOR() *
                  (this.get$lastAdvertised() - currentTime)) {
                for (fabric.util.Iterator iter = getObservers().iterator();
                     iter.hasNext(); ) {
                    fabric.metrics.util.Observer o =
                      (fabric.metrics.util.Observer)
                        fabric.lang.Object._Proxy.$getProxy(iter.next());
                    if (fabric.lang.Object._Proxy.
                          $getProxy((java.lang.Object)
                                      fabric.lang.WrappedJavaInlineable.$unwrap(o)) instanceof fabric.metrics.contracts.MetricContract) {
                        fabric.worker.transaction.TransactionManager.
                          getInstance().registerExtension(
                                          (fabric.metrics.contracts.Contract)
                                            fabric.lang.Object._Proxy.$getProxy(
                                                                        o));
                    }
                }
                this.set$lastAdvertised((long) newExpiry);
            }
            fabric.common.Logging.METRICS_LOGGER.log(
                                                   java.util.logging.Level.INFO,
                                                   "SYNCH EXTENSION");
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
            this.set$$expiry((long) newExpiry);
            this.set$lastAdvertised((long) newExpiry);
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
                    try { this.attemptExtension(); }
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
            out.writeLong(this.lastAdvertised);
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
            this.lastAdvertised = in.readLong();
            this.active = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.Contract._Impl src =
              (fabric.metrics.contracts.Contract._Impl) other;
            this.lastAdvertised = src.lastAdvertised;
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
                        fabric.worker.transaction.TransactionManager $tm11 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
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
                                        catch (java.lang.
                                                 InterruptedException $e9) {
                                            
                                        }
                                    }
                                }
                                if ($backoff12 < 5000) $backoff12 *= 2;
                            }
                            $doBackoff13 = $backoff12 <= 32 || !$doBackoff13;
                            $commit8 = true;
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
                                  set$MAX_EXTENSION((long) 5000);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$DRIFT_FACTOR((long) 100);
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
                                if ($tm11.checkForStaleObjects())
                                    continue $label7;
                                throw new fabric.worker.AbortException($e9);
                            }
                            finally {
                                if ($commit8) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e9) {
                                        $commit8 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e9) {
                                        $commit8 = false;
                                        fabric.common.TransactionID
                                          $currentTid10 = $tm11.getCurrentTid();
                                        if ($currentTid10 != null) {
                                            if ($e9.tid.equals($currentTid10) ||
                                                  !$e9.tid.isDescendantOf(
                                                             $currentTid10)) {
                                                throw $e9;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit8) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 115, -90, -40, -47, 94,
    -51, 49, 122, 124, 62, 95, -90, 42, 34, -79, -89, -62, 105, -11, 6, -103,
    -53, 28, 80, -92, -18, -121, -118, 72, -124, 125, -40 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492455040000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
