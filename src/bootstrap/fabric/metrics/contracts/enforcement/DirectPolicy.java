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
                fabric.worker.transaction.TransactionManager $tm381 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff382 = 1;
                boolean $doBackoff383 = true;
                $label377: for (boolean $commit378 = false; !$commit378; ) {
                    if ($doBackoff383) {
                        if ($backoff382 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff382);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e379) {
                                    
                                }
                            }
                        }
                        if ($backoff382 < 5000) $backoff382 *= 1;
                    }
                    $doBackoff383 = $backoff382 <= 32 || !$doBackoff383;
                    $commit378 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (this.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e379) {
                        $commit378 = false;
                        continue $label377;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e379) {
                        $commit378 = false;
                        fabric.common.TransactionID $currentTid380 =
                          $tm381.getCurrentTid();
                        if ($e379.tid.isDescendantOf($currentTid380))
                            continue $label377;
                        if ($currentTid380.parent != null) throw $e379;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e379) {
                        $commit378 = false;
                        if ($tm381.checkForStaleObjects()) continue $label377;
                        throw new fabric.worker.AbortException($e379);
                    }
                    finally {
                        if ($commit378) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e379) {
                                $commit378 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e379) {
                                $commit378 = false;
                                fabric.common.TransactionID $currentTid380 =
                                  $tm381.getCurrentTid();
                                if ($currentTid380 != null) {
                                    if ($e379.tid.equals($currentTid380) ||
                                          !$e379.tid.isDescendantOf(
                                                       $currentTid380)) {
                                        throw $e379;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit378) {
                            {  }
                            continue $label377;
                        }
                    }
                }
            }
            refresh();
        }
        
        public void refresh() {
            {
                fabric.worker.transaction.TransactionManager $tm388 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff389 = 1;
                boolean $doBackoff390 = true;
                $label384: for (boolean $commit385 = false; !$commit385; ) {
                    if ($doBackoff390) {
                        if ($backoff389 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff389);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e386) {
                                    
                                }
                            }
                        }
                        if ($backoff389 < 5000) $backoff389 *= 1;
                    }
                    $doBackoff390 = $backoff389 <= 32 || !$doBackoff390;
                    $commit385 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        long currentTime = java.lang.System.currentTimeMillis();
                        long trueTime =
                          this.get$bound().trueExpiry(this.get$metric(),
                                                      currentTime);
                        if (trueTime < currentTime) {
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
                    catch (final fabric.worker.RetryException $e386) {
                        $commit385 = false;
                        continue $label384;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e386) {
                        $commit385 = false;
                        fabric.common.TransactionID $currentTid387 =
                          $tm388.getCurrentTid();
                        if ($e386.tid.isDescendantOf($currentTid387))
                            continue $label384;
                        if ($currentTid387.parent != null) throw $e386;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e386) {
                        $commit385 = false;
                        if ($tm388.checkForStaleObjects()) continue $label384;
                        throw new fabric.worker.AbortException($e386);
                    }
                    finally {
                        if ($commit385) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e386) {
                                $commit385 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e386) {
                                $commit385 = false;
                                fabric.common.TransactionID $currentTid387 =
                                  $tm388.getCurrentTid();
                                if ($currentTid387 != null) {
                                    if ($e386.tid.equals($currentTid387) ||
                                          !$e386.tid.isDescendantOf(
                                                       $currentTid387)) {
                                        throw $e386;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit385) {
                            {  }
                            continue $label384;
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
            double v = this.get$metric().velocity();
            double n = this.get$metric().noise();
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
                fabric.worker.transaction.TransactionManager $tm395 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff396 = 1;
                boolean $doBackoff397 = true;
                $label391: for (boolean $commit392 = false; !$commit392; ) {
                    if ($doBackoff397) {
                        if ($backoff396 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff396);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e393) {
                                    
                                }
                            }
                        }
                        if ($backoff396 < 5000) $backoff396 *= 1;
                    }
                    $doBackoff397 = $backoff396 <= 32 || !$doBackoff397;
                    $commit392 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (!this.get$activated()) activate();
                        this.get$metric().addObserver(mc);
                    }
                    catch (final fabric.worker.RetryException $e393) {
                        $commit392 = false;
                        continue $label391;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e393) {
                        $commit392 = false;
                        fabric.common.TransactionID $currentTid394 =
                          $tm395.getCurrentTid();
                        if ($e393.tid.isDescendantOf($currentTid394))
                            continue $label391;
                        if ($currentTid394.parent != null) throw $e393;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e393) {
                        $commit392 = false;
                        if ($tm395.checkForStaleObjects()) continue $label391;
                        throw new fabric.worker.AbortException($e393);
                    }
                    finally {
                        if ($commit392) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e393) {
                                $commit392 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e393) {
                                $commit392 = false;
                                fabric.common.TransactionID $currentTid394 =
                                  $tm395.getCurrentTid();
                                if ($currentTid394 != null) {
                                    if ($e393.tid.equals($currentTid394) ||
                                          !$e393.tid.isDescendantOf(
                                                       $currentTid394)) {
                                        throw $e393;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit392) {
                            {  }
                            continue $label391;
                        }
                    }
                }
            }
            fabric.common.Logging.METRICS_LOGGER.
              fine(
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
                        fabric.worker.transaction.TransactionManager $tm402 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff403 = 1;
                        boolean $doBackoff404 = true;
                        $label398: for (boolean $commit399 = false; !$commit399;
                                        ) {
                            if ($doBackoff404) {
                                if ($backoff403 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff403);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e400) {
                                            
                                        }
                                    }
                                }
                                if ($backoff403 < 5000) $backoff403 *= 1;
                            }
                            $doBackoff404 = $backoff403 <= 32 || !$doBackoff404;
                            $commit399 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e400) {
                                $commit399 = false;
                                continue $label398;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e400) {
                                $commit399 = false;
                                fabric.common.TransactionID $currentTid401 =
                                  $tm402.getCurrentTid();
                                if ($e400.tid.isDescendantOf($currentTid401))
                                    continue $label398;
                                if ($currentTid401.parent != null) throw $e400;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e400) {
                                $commit399 = false;
                                if ($tm402.checkForStaleObjects())
                                    continue $label398;
                                throw new fabric.worker.AbortException($e400);
                            }
                            finally {
                                if ($commit399) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e400) {
                                        $commit399 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e400) {
                                        $commit399 = false;
                                        fabric.common.TransactionID
                                          $currentTid401 =
                                          $tm402.getCurrentTid();
                                        if ($currentTid401 != null) {
                                            if ($e400.tid.equals(
                                                            $currentTid401) ||
                                                  !$e400.tid.
                                                  isDescendantOf(
                                                    $currentTid401)) {
                                                throw $e400;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit399) {
                                    {  }
                                    continue $label398;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -117, -114, -60, 116,
    -75, -16, 97, 82, -87, -2, 16, 61, -128, 2, -3, -24, -1, 1, 58, 61, 100,
    -62, -73, 114, -21, 73, 93, -26, 27, -52, -87, -126 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1504028847000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAL1Yb2wUxxWfO/8942BjMCbGGGIubiFwJ0gVCVyi4IuBa45i2SZVTYmztztnb9jbXXbnzJk/FUmFQE1L1YYQUhWqqqCExDFSK8oHSkUrQoNArZpGpamUlC9pqShqoyppPzSh783O3e6t9y72l5408+Zm3nvz5s17v5nZybukxrZIV0ZKq1qMTZjUjm2S0slUv2TZVElokm0PQe+IPKc6efz2K0pnmIRTpFGWdENXZUkb0W1G5qaekcaluE5ZfPtAsmcHicgouEWyxxgJ7+jNW2SZaWgTo5rBxCTT9L/4UPzYS081/6SKNA2TJlUfZBJT5YShM5pnw6QxS7NpatkbFYUqw2SeTqkySC1V0tS9wGjow6TFVkd1ieUsag9Q29DGkbHFzpnU4nMWOtF8A8y2cjIzLDC/2TE/x1QtnlJt1pMitRmVaoq9m3ydVKdITUaTRoFxYaqwijjXGN+E/cDeoIKZVkaSaUGkepeqK4ws9UsUVxx9AhhAtC5L2ZhRnKpal6CDtDgmaZI+Gh9klqqPAmuNkYNZGGkvqxSY6k1J3iWN0hFGFvn5+p0h4Ipwt6AII61+Nq4J9qzdt2ee3br75S8e3adv0cMkBDYrVNbQ/noQ6vQJDdAMtaguU0ewcWXquLTw0pEwIcDc6mN2eC7s//CxVZ2X33J4FgfwbEs/Q2U2Ip9Oz/1dR2LFuio0o940bBVDoWTlfFf7xUhP3oRoX1jUiIOxwuDlgatfPfgavRMmDUlSKxtaLgtRNU82sqaqUWsz1aklMaokSYTqSoKPJ0kdtFOqTp3ebZmMTVmSVGu8q9bg/8FFGVCBLqqDtqpnjELblNgYb+dNQkgdFBKCcpWQRb8HOgf+rmfk6fiYkaXxtJajeyC841CoZMljcchbS5XjtiXHrZzOVGASXRBFQOw4hDqzJJnZcQrTWjLNUp3FH1ctcGC/oanyRAxsM/8Pc+Rxnc17QiHYgqWyodC0ZMN+itjq7dcgfbYYmkKtEVk7eilJ5l96mcdXBHPChrjmHgxBTHT40cQreyzX2/fh1Mh1JzZRVjiYkTWO4TFheKxoeMxjeMxrONjaiKkYA3CLAbhNhvKxxKnk6zziam2emkX1jaB+valJDHRl8yQU4mtdwOV5qEGg7AIAAoxpXDG480tPH+mqghg391TjtgNr1J9xLk4loSVBGo3ITYdvf3zu+AHDzT1GotMgYbokpnSX33GWIVMFINNVv3KZdH7k0oFoGOEogh6SIJYBdjr9c5Skdk8BJtEbNSkyB30gaThUwLYGNmYZe9weHhBzsWpxYgOd5TOQI+yGQfPkH3/zt4f52VMA4yYPag9S1uMBAFTWxFN9nuv7IYtS4HvvRP8LL949vIM7HjiWB00YxToBiS9BxhvWobd2v/vn90+/E3Y3i5FaM5eGCMnztcy7B78QlE+xYBZjB1LA8oRAkGVFCDFx5m7XNgATDUIOTLej2/WsoagZVUprFCPlv00Prjn/96PNznZr0OM4zyKrPluB239/Lzl4/al/d3I1IRkPM9d/LpuDkPNdzRstS5pAO/LPvr3k5V9LJyHyAd9sdS/lkEW4PwjfwLXcF6t5vcY39gWsuhxvdfB+vHj4T4tNeOy6sTgcn/xBe+LROw4MFGMRdTwQAANPSp40Wfta9qNwV+2bYVI3TJr5iS/p7EkJ8A3CYBjObDshOlPkvpLx0vPXOWx6irnW4c8Dz7T+LHDhB9rIje0GJ/CdwAFHNKGTlkCZC4F1UdA3cHS+ifWCfIjwxnouspzX3Vit4I4MY3MlIxE1m80x3HY+wUMQowLo8G8rnPQ++NvKKQ62O/mH9SOldi2G0gxzNAlaFWBXbyW7sHq0YFBN2sjpSsGezrJw3ItsZQ1rQMMWQmnhkziUBhiWDDYMsrfOtNRxSMV8UWkYlUaEMkXQnR6l4E4IedWaCIjbfkvNAvSMi1sOPXLsm/diR485OetcBZdPu415ZZzrIHfTfdxXeZjlgUqzcIlNfz134OKrBw47V6WW0otNn57LvvGHT27ETty6FnAYVmuGc3YFurcDygLwwC8FPRfg3q847sUqNd2PKDUl6CslfozAHnPvO5HQLxaMZBC2Jm0YGpX0INPaUHkcymZC6q8KOhVg2kjwzvPo3crw5MbnRUlsNm7pe3xz38imjYmhbQNBhtUqBkA+d2Bzvkxk8ZB3g4r/asVFbp2gD3vs9eAhwS1fUu7Ozbf79HPHTinbzqwJC1AdAl8yw1yt0XGqeVRFMHimvem28peGi5C37ixZl9j1wagTPEt9M/u5z26dvLa5W/5emFQVoXDa86ZUqKcUABssCq8zfagEBpcVfYUxQxQn9KpuCrrfu7duRPCYU0tjrl6I7BM053ezezCFXXx6DKvNXHW+wvG1Fyu4dax1ICsqICtahKyo5wYZ9d4go67VZulaF0HpJqT6oqCTs1srirwu6Jnya/Wu4tkKY9/Aaj8j9YXkDEqB6nFDVYLW0ubkZc0aQZfPbi0o0iVox8zW8u0KY9/B6ghAiUUz8I7hL6xDPrM5yi2DMgDtfYLSCmYHQByKKILuLG92yMWGAa71eAXbT2D1XUCbMaqMUn4IDgR5HM+/M4S0vivojdl5HEWuC/rmzDz+wwpjP8Lq+8UjsqzVEKwEgnbhOkE7Zmc1iiwWtPUzHV64Z3yu7D3DuQElxH9kb+dWvFphqTxNfwx3Gck0tYmg0OIr/TyUn0FipARdPbuVosgqQbtnFFpnudafVrD8PFZTkBY5vbLt7VAAldpOCvqt2dmOIs8LemhmsfXzCmO/wOoCIBMznG9hhX1t5s8UvKTHPAP3+x/iQSt8EMplgOAWh7Z9PLsVoshHgv5jxnHYIuKQW+w8K4It5hZcq+CS32J1BdNtd05yTv6v5UGL9+DBh9zigO8s4vugnLhCT3/wxKrWMt9YFk37Yivkpk411bed2n6TfyAofvuLwPs7k9M074PH0641AYpVbnvEef6YnLzDSHQmH2UYmeP5xxf8tqPhJiOLymlgzqORt70yf2JkbqkM4x9iseXlew887PDhv/f5Hrf7qsLmPjKjb0t9btvZJRdz2nMWfiyf/Ffbf2rrh27xjw14RD1/9Ao7/09p4OynzRsOhj+5fS+0foPyqwvWneTOvyy+cfa5/wHkw/CnxBcAAA==";
}
