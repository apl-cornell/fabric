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
 * An enforcement policy for enforcing a {@link MetricContract}s by checking
 * every update to the associated metric.
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
   * @param expiry
   *        how long to enforce the {@link MetricContract} this is applied
   *        to using direct checks on updates to the metric.
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
        
        private boolean activated;
        
        /**
   * @param expiry
   *        how long to enforce the {@link MetricContract} this is applied
   *        to using direct checks on updates to the metric.
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
                fabric.worker.transaction.TransactionManager $tm84 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff85 = 1;
                boolean $doBackoff86 = true;
                $label80: for (boolean $commit81 = false; !$commit81; ) {
                    if ($doBackoff86) {
                        if ($backoff85 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff85);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e82) {  }
                            }
                        }
                        if ($backoff85 < 5000) $backoff85 *= 2;
                    }
                    $doBackoff86 = $backoff85 <= 32 || !$doBackoff86;
                    $commit81 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (this.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e82) {
                        $commit81 = false;
                        continue $label80;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e82) {
                        $commit81 = false;
                        fabric.common.TransactionID $currentTid83 =
                          $tm84.getCurrentTid();
                        if ($e82.tid.isDescendantOf($currentTid83))
                            continue $label80;
                        if ($currentTid83.parent != null) throw $e82;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e82) {
                        $commit81 = false;
                        if ($tm84.checkForStaleObjects()) continue $label80;
                        throw new fabric.worker.AbortException($e82);
                    }
                    finally {
                        if ($commit81) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e82) {
                                $commit81 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e82) {
                                $commit81 = false;
                                fabric.common.TransactionID $currentTid83 =
                                  $tm84.getCurrentTid();
                                if ($currentTid83 != null) {
                                    if ($e82.tid.equals($currentTid83) ||
                                          !$e82.tid.isDescendantOf(
                                                      $currentTid83)) {
                                        throw $e82;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit81) {  }
                    }
                }
            }
            refresh();
        }
        
        public void refresh() {
            {
                fabric.worker.transaction.TransactionManager $tm91 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff92 = 1;
                boolean $doBackoff93 = true;
                $label87: for (boolean $commit88 = false; !$commit88; ) {
                    if ($doBackoff93) {
                        if ($backoff92 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff92);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e89) {  }
                            }
                        }
                        if ($backoff92 < 5000) $backoff92 *= 2;
                    }
                    $doBackoff93 = $backoff92 <= 32 || !$doBackoff93;
                    $commit88 = true;
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
                            if (expiry() <= trueTime) {
                                hedgedTime = java.lang.Math.max(expiry(),
                                                                hedgedTime);
                            }
                            this.set$expiry((long) hedgedTime);
                        }
                        this.set$activated(true);
                    }
                    catch (final fabric.worker.RetryException $e89) {
                        $commit88 = false;
                        continue $label87;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e89) {
                        $commit88 = false;
                        fabric.common.TransactionID $currentTid90 =
                          $tm91.getCurrentTid();
                        if ($e89.tid.isDescendantOf($currentTid90))
                            continue $label87;
                        if ($currentTid90.parent != null) throw $e89;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e89) {
                        $commit88 = false;
                        if ($tm91.checkForStaleObjects()) continue $label87;
                        throw new fabric.worker.AbortException($e89);
                    }
                    finally {
                        if ($commit88) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e89) {
                                $commit88 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e89) {
                                $commit88 = false;
                                fabric.common.TransactionID $currentTid90 =
                                  $tm91.getCurrentTid();
                                if ($currentTid90 != null) {
                                    if ($e89.tid.equals($currentTid90) ||
                                          !$e89.tid.isDescendantOf(
                                                      $currentTid90)) {
                                        throw $e89;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit88) {  }
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
   * @return an appropriately conservative time to advertise to other nodes in
   *       the system for this contract.
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
        
        public long expiry() { return this.get$expiry(); }
        
        public void apply(fabric.metrics.contracts.MetricContract mc) {
            {
                fabric.worker.transaction.TransactionManager $tm98 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff99 = 1;
                boolean $doBackoff100 = true;
                $label94: for (boolean $commit95 = false; !$commit95; ) {
                    if ($doBackoff100) {
                        if ($backoff99 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff99);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e96) {  }
                            }
                        }
                        if ($backoff99 < 5000) $backoff99 *= 2;
                    }
                    $doBackoff100 = $backoff99 <= 32 || !$doBackoff100;
                    $commit95 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (!this.get$activated()) activate();
                        mc.getMetric().startTracking(mc);
                    }
                    catch (final fabric.worker.RetryException $e96) {
                        $commit95 = false;
                        continue $label94;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e96) {
                        $commit95 = false;
                        fabric.common.TransactionID $currentTid97 =
                          $tm98.getCurrentTid();
                        if ($e96.tid.isDescendantOf($currentTid97))
                            continue $label94;
                        if ($currentTid97.parent != null) throw $e96;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e96) {
                        $commit95 = false;
                        if ($tm98.checkForStaleObjects()) continue $label94;
                        throw new fabric.worker.AbortException($e96);
                    }
                    finally {
                        if ($commit95) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e96) {
                                $commit95 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e96) {
                                $commit95 = false;
                                fabric.common.TransactionID $currentTid97 =
                                  $tm98.getCurrentTid();
                                if ($currentTid97 != null) {
                                    if ($e96.tid.equals($currentTid97) ||
                                          !$e96.tid.isDescendantOf(
                                                      $currentTid97)) {
                                        throw $e96;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit95) {  }
                    }
                }
            }
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract mc) {
            mc.getMetric().stopTracking(mc);
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
                        fabric.worker.transaction.TransactionManager $tm105 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff106 = 1;
                        boolean $doBackoff107 = true;
                        $label101: for (boolean $commit102 = false; !$commit102;
                                        ) {
                            if ($doBackoff107) {
                                if ($backoff106 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff106);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e103) {
                                            
                                        }
                                    }
                                }
                                if ($backoff106 < 5000) $backoff106 *= 2;
                            }
                            $doBackoff107 = $backoff106 <= 32 || !$doBackoff107;
                            $commit102 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e103) {
                                $commit102 = false;
                                continue $label101;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e103) {
                                $commit102 = false;
                                fabric.common.TransactionID $currentTid104 =
                                  $tm105.getCurrentTid();
                                if ($e103.tid.isDescendantOf($currentTid104))
                                    continue $label101;
                                if ($currentTid104.parent != null) throw $e103;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e103) {
                                $commit102 = false;
                                if ($tm105.checkForStaleObjects())
                                    continue $label101;
                                throw new fabric.worker.AbortException($e103);
                            }
                            finally {
                                if ($commit102) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e103) {
                                        $commit102 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e103) {
                                        $commit102 = false;
                                        fabric.common.TransactionID
                                          $currentTid104 =
                                          $tm105.getCurrentTid();
                                        if ($currentTid104 != null) {
                                            if ($e103.tid.equals(
                                                            $currentTid104) ||
                                                  !$e103.tid.
                                                  isDescendantOf(
                                                    $currentTid104)) {
                                                throw $e103;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit102) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 89, -103, -29, 40, 52,
    -126, -64, -106, -102, 65, -2, -99, 85, -44, 42, -96, -90, -102, 53, -73,
    -123, 3, 93, 74, -77, -58, 6, 32, -66, -11, 127, -93 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1495741682000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
