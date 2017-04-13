package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.util.NodeTime;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.worker.Store;
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
    public fabric.metrics.util.NodeTime get$expiry();
    
    public fabric.metrics.util.NodeTime set$expiry(
      fabric.metrics.util.NodeTime val);
    
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
   */
    public void update(long newExpiry);
    
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
    
    /**
   * @param time
   *        the time we're checking validity against.
   * @return true iff the contract is valid at the given time in the current
   *       transaction context.
   */
    public boolean valid(fabric.metrics.util.NodeTime time);
    
    /**
   * @return the lower bound on the expiration time of the current state, in
   *       milliseconds, lowered to account for clock drift relative to the
   *       current store
   */
    public long getExpiryLower();
    
    /**
   * @param s
   *        the store we're giving the lower bound relative to
   * @return the lower bound on the expiration time of the current state, in
   *       milliseconds, lowered to account for clock drift relative to s
   */
    public long getExpiryLower(fabric.worker.Store s);
    
    /**
   * @return the upper bound on the expiration time of the current state, in
   *       milliseconds, raised to account for clock drift relative to the
   *       current store
   */
    public long getExpiryUpper();
    
    /**
   * @param s
   *        the store we're giving the lower bound relative to
   * @return the upper bound on the expiration time of the current state, in
   *       milliseconds, raised to account for clock drift relative to s
   */
    public long getExpiryUpper(fabric.worker.Store s);
    
    public fabric.metrics.util.NodeTime getExpiry();
    
    /**
   * @return the expiration time of the curren state
   *
   *       /** Update this contract's expiration time to stay valid in
   *       response to a change in the value of the {@link Subject}s used
   *       for enforcing this {@link Contract}. Revokes, extends, and
   *       updates the enforcement strategy as needed.
   */
    public abstract void refresh();
    
    public void handleUpdates();
    
    public static class _Proxy extends fabric.metrics.util.Subject._Proxy
      implements fabric.metrics.contracts.Contract {
        public fabric.metrics.util.NodeTime get$expiry() {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              get$expiry();
        }
        
        public fabric.metrics.util.NodeTime set$expiry(
          fabric.metrics.util.NodeTime val) {
            return ((fabric.metrics.contracts.Contract._Impl) fetch()).
              set$expiry(val);
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
        
        public void update(long arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).update(arg1);
        }
        
        public void revoke(long arg1) {
            ((fabric.metrics.contracts.Contract) fetch()).revoke(arg1);
        }
        
        public boolean valid(long arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).valid(arg1);
        }
        
        public boolean valid(fabric.metrics.util.NodeTime arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).valid(arg1);
        }
        
        public long getExpiryLower() {
            return ((fabric.metrics.contracts.Contract) fetch()).getExpiryLower(
                                                                   );
        }
        
        public long getExpiryLower(fabric.worker.Store arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).getExpiryLower(
                                                                   arg1);
        }
        
        public long getExpiryUpper() {
            return ((fabric.metrics.contracts.Contract) fetch()).getExpiryUpper(
                                                                   );
        }
        
        public long getExpiryUpper(fabric.worker.Store arg1) {
            return ((fabric.metrics.contracts.Contract) fetch()).getExpiryUpper(
                                                                   arg1);
        }
        
        public fabric.metrics.util.NodeTime getExpiry() {
            return ((fabric.metrics.contracts.Contract) fetch()).getExpiry();
        }
        
        public void refresh() {
            ((fabric.metrics.contracts.Contract) fetch()).refresh();
        }
        
        public void handleUpdates() {
            ((fabric.metrics.contracts.Contract) fetch()).handleUpdates();
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
        public fabric.metrics.util.NodeTime get$expiry() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.expiry;
        }
        
        public fabric.metrics.util.NodeTime set$expiry(
          fabric.metrics.util.NodeTime val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.expiry = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.metrics.util.NodeTime expiry;
        
        public fabric.metrics.contracts.Contract
          fabric$metrics$contracts$Contract$() {
            fabric$metrics$util$Subject$();
            this.set$expiry(
                   ((fabric.metrics.util.NodeTime)
                      new fabric.metrics.util.NodeTime._Impl(
                        this.$getStore()).$getProxy(
                                            )).fabric$metrics$util$NodeTime$(
                                                 $getStore(), -1));
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
            if (this.get$expiry().lessThan(adjustedNewExpiry)) {
                if (adjustedNewExpiry > currentTime +
                      fabric.metrics.contracts.Contract._Static._Proxy.$instance.get$MIN_EXTENSION_FACTOR() *
                      (getExpiryLower() - currentTime)) {
                    fabric.worker.transaction.TransactionManager.getInstance().
                      registerExtension((fabric.metrics.contracts.Contract)
                                          this.$getProxy());
                }
                this.set$expiry(this.get$expiry().max(adjustedNewExpiry));
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
                final fabric.worker.Store s = $getStore();
                this.set$expiry(
                       ((fabric.metrics.util.NodeTime)
                          new fabric.metrics.util.NodeTime._Impl(
                            s).$getProxy()).fabric$metrics$util$NodeTime$(-1));
            }
        }
        
        /**
   * Updates the expiration time of this contract, either extending or
   * revoking as needed.
   *
   * @param newExpiry
   *        the new expiration time associated with this {@link Contract}.
   */
        public void update(long newExpiry) {
            if (getExpiry().lessThan(newExpiry)) {
                extendTo(newExpiry);
            } else if (getExpiry().greaterThan(newExpiry)) {
                revoke(newExpiry);
            }
        }
        
        /**
   * Called to revoke this contract in the current transaction context.
   *
   * @param newExpiry
   *        time to set the expiry back to.
   */
        public void revoke(long newExpiry) {
            markModified();
            final fabric.worker.Store s = $getStore();
            this.set$expiry(
                   ((fabric.metrics.util.NodeTime)
                      new fabric.metrics.util.NodeTime._Impl(
                        s).$getProxy()).fabric$metrics$util$NodeTime$(
                                          newExpiry));
        }
        
        /**
   * @param time
   *        the time we're checking validity against.
   * @return true iff the contract is valid at the given time in the current
   *       transaction context.
   */
        public boolean valid(long time) {
            final fabric.worker.Store s = $getStore();
            return valid(
                     ((fabric.metrics.util.NodeTime)
                        new fabric.metrics.util.NodeTime._Impl(s).$getProxy()).
                         fabric$metrics$util$NodeTime$(time));
        }
        
        /**
   * @param time
   *        the time we're checking validity against.
   * @return true iff the contract is valid at the given time in the current
   *       transaction context.
   */
        public boolean valid(fabric.metrics.util.NodeTime time) {
            if (!isActive()) return false;
            if (time.greaterThan(getExpiry())) refresh();
            return getExpiry().greaterThan(time);
        }
        
        /**
   * @return the lower bound on the expiration time of the current state, in
   *       milliseconds, lowered to account for clock drift relative to the
   *       current store
   */
        public long getExpiryLower() { return this.get$expiry().lowerBound(); }
        
        /**
   * @param s
   *        the store we're giving the lower bound relative to
   * @return the lower bound on the expiration time of the current state, in
   *       milliseconds, lowered to account for clock drift relative to s
   */
        public long getExpiryLower(fabric.worker.Store s) {
            return this.get$expiry().lowerBoundAt(s);
        }
        
        /**
   * @return the upper bound on the expiration time of the current state, in
   *       milliseconds, raised to account for clock drift relative to the
   *       current store
   */
        public long getExpiryUpper() { return this.get$expiry().upperBound(); }
        
        /**
   * @param s
   *        the store we're giving the lower bound relative to
   * @return the upper bound on the expiration time of the current state, in
   *       milliseconds, raised to account for clock drift relative to s
   */
        public long getExpiryUpper(fabric.worker.Store s) {
            return this.get$expiry().upperBoundAt(s);
        }
        
        public fabric.metrics.util.NodeTime getExpiry() {
            return this.get$expiry();
        }
        
        /**
   * @return the expiration time of the curren state
   *
   *       /** Update this contract's expiration time to stay valid in
   *       response to a change in the value of the {@link Subject}s used
   *       for enforcing this {@link Contract}. Revokes, extends, and
   *       updates the enforcement strategy as needed.
   */
        public abstract void refresh();
        
        public void handleUpdates() { refresh(); }
        
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
            $writeRef($getStore(), this.expiry, refTypes, out, intraStoreRefs,
                      interStoreRefs);
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
            this.expiry = (fabric.metrics.util.NodeTime)
                            $readRef(fabric.metrics.util.NodeTime._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            this.active = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.Contract._Impl src =
              (fabric.metrics.contracts.Contract._Impl) other;
            this.expiry = src.expiry;
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
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
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
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeDouble(this.MIN_EXTENSION_FACTOR);
                out.writeLong(this.MAX_EXTENSION);
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
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.Contract._Static._Proxy(
                         this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm23 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff24 = 1;
                        $label19: for (boolean $commit20 = false; !$commit20;
                                       ) {
                            if ($backoff24 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff24);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e21) {
                                        
                                    }
                                }
                            }
                            if ($backoff24 < 5000) $backoff24 *= 2;
                            $commit20 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MIN_EXTENSION_FACTOR((double) 4);
                                fabric.metrics.contracts.Contract._Static.
                                  _Proxy.
                                  $instance.
                                  set$MAX_EXTENSION((long) 1000);
                            }
                            catch (final fabric.worker.RetryException $e21) {
                                $commit20 = false;
                                continue $label19;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e21) {
                                $commit20 = false;
                                fabric.common.TransactionID $currentTid22 =
                                  $tm23.getCurrentTid();
                                if ($e21.tid.isDescendantOf($currentTid22))
                                    continue $label19;
                                if ($currentTid22.parent != null) throw $e21;
                                throw new InternalError(
                                        "Something is broken with transaction management. Got a signal to restart a different transaction than the one being managed.");
                            }
                            catch (final Throwable $e21) {
                                $commit20 = false;
                                if ($tm23.checkForStaleObjects())
                                    continue $label19;
                                throw new fabric.worker.AbortException($e21);
                            }
                            finally {
                                if ($commit20) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e21) {
                                        $commit20 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e21) {
                                        $commit20 = false;
                                        fabric.common.TransactionID
                                          $currentTid22 = $tm23.getCurrentTid();
                                        if ($currentTid22 ==
                                              null ||
                                              $e21.tid.isDescendantOf(
                                                         $currentTid22) &&
                                              !$currentTid22.equals($e21.tid))
                                            continue $label19;
                                        throw $e21;
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit20) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 24, 64, -33, 56, -61,
    -34, -27, -23, 21, 44, 44, 31, 56, 66, -35, -65, -40, -74, 44, 17, -56, 35,
    20, 5, -63, -25, -5, 35, 3, 127, -55, 33 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492107457000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
