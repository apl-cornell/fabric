package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.MetricContract;

/**
 * An {@link EnforcementPolicy} for enforcing a {@link MetricContract}s by
 * checking every update to the associated {@link Metric}.
 */
public interface DirectPolicy
  extends fabric.metrics.contracts.enforcement.EnforcementPolicy {
    public fabric.metrics.Metric get$metric();
    
    public fabric.metrics.Metric set$metric(fabric.metrics.Metric val);
    
    public fabric.metrics.contracts.Bound get$bound();
    
    public fabric.metrics.contracts.Bound set$bound(
      fabric.metrics.contracts.Bound val);
    
    public long get$expiry();
    
    public long set$expiry(long val);
    
    public long postInc$expiry();
    
    public long postDec$expiry();
    
    public boolean get$activated();
    
    public boolean set$activated(boolean val);
    
    /**
   * @param metric
   *            the Metric the associated contract is bounding.
   * @param bound
   *            the bound enforced by the associated contract.
   */
    public fabric.metrics.contracts.enforcement.DirectPolicy
      fabric$metrics$contracts$enforcement$DirectPolicy$(
      fabric.metrics.Metric metric, fabric.metrics.contracts.Bound bound);
    
    public void activate();
    
    public void refresh();
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy
    extends fabric.metrics.contracts.enforcement.EnforcementPolicy._Proxy
      implements fabric.metrics.contracts.enforcement.DirectPolicy {
        public fabric.metrics.Metric get$metric() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).get$metric();
        }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).set$metric(val);
        }
        
        public fabric.metrics.contracts.Bound get$bound() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).get$bound();
        }
        
        public fabric.metrics.contracts.Bound set$bound(
          fabric.metrics.contracts.Bound val) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).set$bound(val);
        }
        
        public long get$expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).get$expiry();
        }
        
        public long set$expiry(long val) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).set$expiry(val);
        }
        
        public long postInc$expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).postInc$expiry();
        }
        
        public long postDec$expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).postDec$expiry();
        }
        
        public boolean get$activated() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).get$activated();
        }
        
        public boolean set$activated(boolean val) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).set$activated(val);
        }
        
        public fabric.metrics.contracts.enforcement.DirectPolicy
          fabric$metrics$contracts$enforcement$DirectPolicy$(
          fabric.metrics.Metric arg1, fabric.metrics.contracts.Bound arg2) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      fetch()).
              fabric$metrics$contracts$enforcement$DirectPolicy$(arg1, arg2);
        }
        
        public void refresh() {
            ((fabric.metrics.contracts.enforcement.DirectPolicy) fetch()).
              refresh();
        }
        
        public _Proxy(DirectPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl
    extends fabric.metrics.contracts.enforcement.EnforcementPolicy._Impl
      implements fabric.metrics.contracts.enforcement.DirectPolicy {
        public fabric.metrics.Metric get$metric() { return this.metric; }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.metric = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.Metric metric;
        
        public fabric.metrics.contracts.Bound get$bound() { return this.bound; }
        
        public fabric.metrics.contracts.Bound set$bound(
          fabric.metrics.contracts.Bound val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.bound = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.contracts.Bound bound;
        
        public long get$expiry() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.expiry;
        }
        
        public long set$expiry(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.expiry = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$expiry() {
            long tmp = this.get$expiry();
            this.set$expiry((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$expiry() {
            long tmp = this.get$expiry();
            this.set$expiry((long) (tmp - 1));
            return tmp;
        }
        
        /** The currently calculated expiration time to use for the contract. */
        private long expiry;
        
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
        
        /** Is this policy active and being enforced? */
        private boolean activated;
        
        /**
   * @param metric
   *            the Metric the associated contract is bounding.
   * @param bound
   *            the bound enforced by the associated contract.
   */
        public fabric.metrics.contracts.enforcement.DirectPolicy
          fabric$metrics$contracts$enforcement$DirectPolicy$(
          fabric.metrics.Metric metric, fabric.metrics.contracts.Bound bound) {
            this.set$metric(metric);
            this.set$bound(bound);
            fabric$metrics$contracts$enforcement$EnforcementPolicy$();
            this.set$expiry((long) -1);
            this.set$activated(false);
            return (fabric.metrics.contracts.enforcement.DirectPolicy)
                     this.$getProxy();
        }
        
        public void activate() {
            {
                fabric.worker.transaction.TransactionManager $tm225 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff226 = 1;
                boolean $doBackoff227 = true;
                $label221: for (boolean $commit222 = false; !$commit222; ) {
                    if ($doBackoff227) {
                        if ($backoff226 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff226);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e223) {
                                    
                                }
                            }
                        }
                        if ($backoff226 < 5000) $backoff226 *= 1;
                    }
                    $doBackoff227 = $backoff226 <= 32 || !$doBackoff227;
                    $commit222 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (this.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e223) {
                        $commit222 = false;
                        continue $label221;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e223) {
                        $commit222 = false;
                        fabric.common.TransactionID $currentTid224 =
                          $tm225.getCurrentTid();
                        if ($e223.tid.isDescendantOf($currentTid224))
                            continue $label221;
                        if ($currentTid224.parent != null) throw $e223;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e223) {
                        $commit222 = false;
                        if ($tm225.checkForStaleObjects()) continue $label221;
                        throw new fabric.worker.AbortException($e223);
                    }
                    finally {
                        if ($commit222) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e223) {
                                $commit222 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e223) {
                                $commit222 = false;
                                fabric.common.TransactionID $currentTid224 =
                                  $tm225.getCurrentTid();
                                if ($currentTid224 != null) {
                                    if ($e223.tid.equals($currentTid224) ||
                                          !$e223.tid.isDescendantOf(
                                                       $currentTid224)) {
                                        throw $e223;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit222) {
                            {  }
                            continue $label221;
                        }
                    }
                }
            }
        }
        
        public void refresh() {
            {
                fabric.worker.transaction.TransactionManager $tm232 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff233 = 1;
                boolean $doBackoff234 = true;
                $label228: for (boolean $commit229 = false; !$commit229; ) {
                    if ($doBackoff234) {
                        if ($backoff233 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff233);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e230) {
                                    
                                }
                            }
                        }
                        if ($backoff233 < 5000) $backoff233 *= 1;
                    }
                    $doBackoff234 = $backoff233 <= 32 || !$doBackoff234;
                    $commit229 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        long currentTime = java.lang.System.currentTimeMillis();
                        long trueTime =
                          this.get$bound().trueExpiry(this.get$metric(),
                                                      currentTime);
                        if (trueTime < currentTime && currentTime <=
                              this.get$expiry()) {
                            this.set$expiry((long) 0);
                        }
                        else {
                            long hedgedTime =
                              ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                                 this.fetch()).hedged(currentTime);
                            if (this.get$expiry() <= trueTime) {
                                hedgedTime =
                                  java.lang.Math.max(this.get$expiry(),
                                                     hedgedTime);
                            }
                            this.set$expiry((long) hedgedTime);
                        }
                        this.set$activated(true);
                    }
                    catch (final fabric.worker.RetryException $e230) {
                        $commit229 = false;
                        continue $label228;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e230) {
                        $commit229 = false;
                        fabric.common.TransactionID $currentTid231 =
                          $tm232.getCurrentTid();
                        if ($e230.tid.isDescendantOf($currentTid231))
                            continue $label228;
                        if ($currentTid231.parent != null) throw $e230;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e230) {
                        $commit229 = false;
                        if ($tm232.checkForStaleObjects()) continue $label228;
                        throw new fabric.worker.AbortException($e230);
                    }
                    finally {
                        if ($commit229) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e230) {
                                $commit229 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e230) {
                                $commit229 = false;
                                fabric.common.TransactionID $currentTid231 =
                                  $tm232.getCurrentTid();
                                if ($currentTid231 != null) {
                                    if ($e230.tid.equals($currentTid231) ||
                                          !$e230.tid.isDescendantOf(
                                                       $currentTid231)) {
                                        throw $e230;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit229) {
                            {  }
                            continue $label228;
                        }
                    }
                }
            }
        }
        
        /**
   * Using the associated {@link Metric's} statistics and some tuning
   * constants, figure out a time to advertise given the current time.
   *
   * @param time
   *        the current time we're calculating this hedged expiration at
   * @return an appropriately conservative (unlikely to be retracted again)
   *         time to advertise to other nodes in the system for this contract.
   */
        private long hedged(long time) {
            double r = this.get$bound().get$rate();
            double b = this.get$bound().value(time);
            double x = this.get$metric().value();
            double v = this.get$metric().velocity(true);
            double n = this.get$metric().noise(true);
            long hedgedResult = this.get$bound().trueExpiry(this.get$metric(),
                                                            time);
            if (r <= 0) { return hedgedResult; }
            n *=
              fabric.metrics.contracts.enforcement.DirectPolicy._Static._Proxy.$instance.
                get$HEDGE_FACTOR() *
                fabric.metrics.contracts.enforcement.DirectPolicy._Static._Proxy.$instance.
                get$HEDGE_FACTOR();
            if (hedgedResult < java.lang.Long.MAX_VALUE) {
                n = java.lang.Math.max(n, 4 * (v - r) * (x - b));
            }
            double minYs = 0.0;
            long min = -1;
            if (v > 0) {
                minYs = x - n / (4 * v);
                min = this.get$bound().trueExpiry(minYs, time);
                if (minYs < x && this.get$bound().test(minYs, time)) {
                    hedgedResult = java.lang.Math.min(min, hedgedResult);
                    return hedgedResult;
                }
            }
            double mb = x - b;
            double vr = v - r;
            long intersect = -1;
            if (vr != 0) {
                double factor = 1.0 / (2.0 * vr * vr);
                double constant = n - 2 * mb * vr;
                double discriminant = java.lang.Math.sqrt(n) *
                  java.lang.Math.sqrt(n - 4.0 * mb * vr);
                if (!java.lang.Double.isNaN(discriminant)) {
                    double first = factor * (constant + discriminant) + time;
                    double second = factor * (constant - discriminant) + time;
                    if (first > 0 || second > 0) {
                        if (first < 0) {
                            intersect = (long) second;
                        } else if (second < 0) {
                            intersect = (long) first;
                        } else {
                            intersect = (long)
                                          java.lang.Math.min(first, second);
                        }
                        hedgedResult = java.lang.Math.min(intersect,
                                                          hedgedResult);
                    }
                }
            } else if (n > 0) {
                hedgedResult = java.lang.Math.min(hedgedResult,
                                                  (long) (mb * mb / n));
            }
            return hedgedResult;
        }
        
        public long expiry() {
            refresh();
            return this.get$expiry();
        }
        
        public void apply(fabric.metrics.contracts.MetricContract mc) {
            {
                fabric.worker.transaction.TransactionManager $tm239 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff240 = 1;
                boolean $doBackoff241 = true;
                $label235: for (boolean $commit236 = false; !$commit236; ) {
                    if ($doBackoff241) {
                        if ($backoff240 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff240);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e237) {
                                    
                                }
                            }
                        }
                        if ($backoff240 < 5000) $backoff240 *= 1;
                    }
                    $doBackoff241 = $backoff240 <= 32 || !$doBackoff241;
                    $commit236 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (!this.get$activated()) activate();
                        this.get$metric().addObserver(mc);
                    }
                    catch (final fabric.worker.RetryException $e237) {
                        $commit236 = false;
                        continue $label235;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e237) {
                        $commit236 = false;
                        fabric.common.TransactionID $currentTid238 =
                          $tm239.getCurrentTid();
                        if ($e237.tid.isDescendantOf($currentTid238))
                            continue $label235;
                        if ($currentTid238.parent != null) throw $e237;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e237) {
                        $commit236 = false;
                        if ($tm239.checkForStaleObjects()) continue $label235;
                        throw new fabric.worker.AbortException($e237);
                    }
                    finally {
                        if ($commit236) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e237) {
                                $commit236 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e237) {
                                $commit236 = false;
                                fabric.common.TransactionID $currentTid238 =
                                  $tm239.getCurrentTid();
                                if ($currentTid238 != null) {
                                    if ($e237.tid.equals($currentTid238) ||
                                          !$e237.tid.isDescendantOf(
                                                       $currentTid238)) {
                                        throw $e237;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit236) {
                            {  }
                            continue $label235;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.
              finer(
                "DEFENDING " +
                  java.lang.String.
                    valueOf(fabric.lang.WrappedJavaInlineable.$unwrap(mc)) +
                  " WITH " +
                  java.lang.String.
                    valueOf(
                      fabric.lang.WrappedJavaInlineable.
                          $unwrap(
                            (fabric.metrics.contracts.enforcement.DirectPolicy)
                              this.$getProxy())));
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract mc) {
            this.get$metric().removeObserver(mc);
        }
        
        public java.lang.String toString() {
            return "Directly watching " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(this.get$metric())) +
            " " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(this.get$bound()));
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (!(fabric.lang.Object._Proxy.
                    $getProxy((java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(other)) instanceof fabric.metrics.contracts.enforcement.DirectPolicy))
                return false;
            fabric.metrics.contracts.enforcement.DirectPolicy that =
              (fabric.metrics.contracts.enforcement.DirectPolicy)
                fabric.lang.Object._Proxy.$getProxy(other);
            return this.get$metric().equals(that.get$metric()) &&
              this.get$bound().equals(that.get$bound());
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.enforcement.DirectPolicy._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.metric, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.bound, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            out.writeLong(this.expiry);
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
            this.metric = (fabric.metrics.Metric)
                            $readRef(fabric.metrics.Metric._Proxy.class,
                                     (fabric.common.RefTypeEnum)
                                       refTypes.next(), in, store,
                                     intraStoreRefs, interStoreRefs);
            this.bound = (fabric.metrics.contracts.Bound)
                           $readRef(fabric.metrics.contracts.Bound._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.expiry = in.readLong();
            this.activated = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.enforcement.DirectPolicy._Impl src =
              (fabric.metrics.contracts.enforcement.DirectPolicy._Impl) other;
            this.metric = src.metric;
            this.bound = src.bound;
            this.expiry = src.expiry;
            this.activated = src.activated;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public double get$HEDGE_FACTOR();
        
        public double set$HEDGE_FACTOR(double val);
        
        public double postInc$HEDGE_FACTOR();
        
        public double postDec$HEDGE_FACTOR();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.enforcement.DirectPolicy._Static {
            public double get$HEDGE_FACTOR() {
                return ((fabric.metrics.contracts.enforcement.DirectPolicy.
                          _Static._Impl) fetch()).get$HEDGE_FACTOR();
            }
            
            public double set$HEDGE_FACTOR(double val) {
                return ((fabric.metrics.contracts.enforcement.DirectPolicy.
                          _Static._Impl) fetch()).set$HEDGE_FACTOR(val);
            }
            
            public double postInc$HEDGE_FACTOR() {
                return ((fabric.metrics.contracts.enforcement.DirectPolicy.
                          _Static._Impl) fetch()).postInc$HEDGE_FACTOR();
            }
            
            public double postDec$HEDGE_FACTOR() {
                return ((fabric.metrics.contracts.enforcement.DirectPolicy.
                          _Static._Impl) fetch()).postDec$HEDGE_FACTOR();
            }
            
            public _Proxy(fabric.metrics.contracts.enforcement.DirectPolicy.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.enforcement.
              DirectPolicy._Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  enforcement.
                  DirectPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    enforcement.
                    DirectPolicy.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.enforcement.DirectPolicy._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.enforcement.DirectPolicy._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.enforcement.DirectPolicy._Static {
            public double get$HEDGE_FACTOR() { return this.HEDGE_FACTOR; }
            
            public double set$HEDGE_FACTOR(double val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.HEDGE_FACTOR = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public double postInc$HEDGE_FACTOR() {
                double tmp = this.get$HEDGE_FACTOR();
                this.set$HEDGE_FACTOR((double) (tmp + 1));
                return tmp;
            }
            
            public double postDec$HEDGE_FACTOR() {
                double tmp = this.get$HEDGE_FACTOR();
                this.set$HEDGE_FACTOR((double) (tmp - 1));
                return tmp;
            }
            
            private double HEDGE_FACTOR;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeDouble(this.HEDGE_FACTOR);
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
                this.HEDGE_FACTOR = in.readDouble();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.enforcement.DirectPolicy.
                         _Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm246 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff247 = 1;
                        boolean $doBackoff248 = true;
                        $label242: for (boolean $commit243 = false; !$commit243;
                                        ) {
                            if ($doBackoff248) {
                                if ($backoff247 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff247);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e244) {
                                            
                                        }
                                    }
                                }
                                if ($backoff247 < 5000) $backoff247 *= 1;
                            }
                            $doBackoff248 = $backoff247 <= 32 || !$doBackoff248;
                            $commit243 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e244) {
                                $commit243 = false;
                                continue $label242;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e244) {
                                $commit243 = false;
                                fabric.common.TransactionID $currentTid245 =
                                  $tm246.getCurrentTid();
                                if ($e244.tid.isDescendantOf($currentTid245))
                                    continue $label242;
                                if ($currentTid245.parent != null) throw $e244;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e244) {
                                $commit243 = false;
                                if ($tm246.checkForStaleObjects())
                                    continue $label242;
                                throw new fabric.worker.AbortException($e244);
                            }
                            finally {
                                if ($commit243) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e244) {
                                        $commit243 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e244) {
                                        $commit243 = false;
                                        fabric.common.TransactionID
                                          $currentTid245 =
                                          $tm246.getCurrentTid();
                                        if ($currentTid245 != null) {
                                            if ($e244.tid.equals(
                                                            $currentTid245) ||
                                                  !$e244.tid.
                                                  isDescendantOf(
                                                    $currentTid245)) {
                                                throw $e244;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit243) {
                                    {  }
                                    continue $label242;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 116, -72, 90, -110,
    -21, 61, 104, 3, 68, 110, -120, -88, 9, 86, -85, 119, 61, -27, -9, 125,
    -121, -65, -81, -115, 114, 122, 93, 36, -48, -47, -39, -118 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506965834000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
