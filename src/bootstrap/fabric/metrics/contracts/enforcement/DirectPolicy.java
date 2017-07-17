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
                        this.get$metric().addObserver(mc);
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
    
    public static final byte[] $classHash = new byte[] { 4, 34, -44, -56, -46,
    61, 29, 52, 70, -54, 102, -89, -105, 73, -108, -64, -126, 56, -54, 4, 14,
    110, -95, -41, 53, -68, 118, -93, 118, 34, 45, 3 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500319966000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
