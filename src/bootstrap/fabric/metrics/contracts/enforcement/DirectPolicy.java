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
                    try { if (this.get$activated()) return; }
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
            refresh();
        }
        
        public void refresh() {
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
        
        public long expiry() { return this.get$expiry(); }
        
        public void apply(fabric.metrics.contracts.MetricContract mc) {
            {
                fabric.worker.transaction.TransactionManager $tm105 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                int $backoff106 = 1;
                boolean $doBackoff107 = true;
                $label101: for (boolean $commit102 = false; !$commit102; ) {
                    if ($doBackoff107) {
                        if ($backoff106 > 32) {
                            while (true) {
                                try {
                                    java.lang.Thread.sleep($backoff106);
                                    break;
                                }
                                catch (java.lang.InterruptedException $e103) {
                                    
                                }
                            }
                        }
                        if ($backoff106 < 5000) $backoff106 *= 2;
                    }
                    $doBackoff107 = $backoff106 <= 32 || !$doBackoff107;
                    $commit102 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (!this.get$activated()) activate();
                        this.get$metric().addObserver(mc);
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
                        if ($tm105.checkForStaleObjects()) continue $label101;
                        throw new fabric.worker.AbortException($e103);
                    }
                    finally {
                        if ($commit102) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e103) {
                                $commit102 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e103) {
                                $commit102 = false;
                                fabric.common.TransactionID $currentTid104 =
                                  $tm105.getCurrentTid();
                                if ($currentTid104 != null) {
                                    if ($e103.tid.equals($currentTid104) ||
                                          !$e103.tid.isDescendantOf(
                                                       $currentTid104)) {
                                        throw $e103;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit102) {  }
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
                        fabric.worker.transaction.TransactionManager $tm112 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        int $backoff113 = 1;
                        boolean $doBackoff114 = true;
                        $label108: for (boolean $commit109 = false; !$commit109;
                                        ) {
                            if ($doBackoff114) {
                                if ($backoff113 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff113);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e110) {
                                            
                                        }
                                    }
                                }
                                if ($backoff113 < 5000) $backoff113 *= 2;
                            }
                            $doBackoff114 = $backoff113 <= 32 || !$doBackoff114;
                            $commit109 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e110) {
                                $commit109 = false;
                                continue $label108;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e110) {
                                $commit109 = false;
                                fabric.common.TransactionID $currentTid111 =
                                  $tm112.getCurrentTid();
                                if ($e110.tid.isDescendantOf($currentTid111))
                                    continue $label108;
                                if ($currentTid111.parent != null) throw $e110;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e110) {
                                $commit109 = false;
                                if ($tm112.checkForStaleObjects())
                                    continue $label108;
                                throw new fabric.worker.AbortException($e110);
                            }
                            finally {
                                if ($commit109) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e110) {
                                        $commit109 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e110) {
                                        $commit109 = false;
                                        fabric.common.TransactionID
                                          $currentTid111 =
                                          $tm112.getCurrentTid();
                                        if ($currentTid111 != null) {
                                            if ($e110.tid.equals(
                                                            $currentTid111) ||
                                                  !$e110.tid.
                                                  isDescendantOf(
                                                    $currentTid111)) {
                                                throw $e110;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit109) {  }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -1, -98, 26, -13, 58,
    -72, 4, -126, -87, 60, 116, 96, -55, -6, 41, 99, -83, 114, 96, -75, 92, -87,
    -17, 120, 102, -112, -96, 38, 1, 112, 109, -15 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1501602696000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAL1YfWwUxxWfO3+ecbAxX44xhpiLWwjcCVJFAqco+GLDJUexbJOqJsmxtztnb9jbXXbnzJlAm6SKQJVK05YQEjW0VUEJxICUFKUVJUol2sRNm7RRVNpKpPwTNRVFFYr6oaotfW927nZvvXex/+lJM29u5r03v3nz3puZnbpB6myLdGeljKrF2KRJ7diAlEmmBiXLpkpCk2x7BHrT8rza5LGPX1K6wiScIs2ypBu6KktaWrcZmZ96TJqQ4jpl8Z1Dyd5dJCKj4DbJHmckvKuvYJGVpqFNjmkGE5PM0P/sXfGjzz3a+moNaRklLao+zCSmyglDZ7TARklzjuYy1LK3KApVRskCnVJlmFqqpKn7gdHQR0mbrY7pEstb1B6itqFNIGObnTepxecsdiJ8A2BbeZkZFsBvdeDnmarFU6rNelOkPqtSTbH3ki+T2hSpy2rSGDAuSRVXEeca4wPYD+xNKsC0spJMiyK1e1RdYWSFX6K04uiDwACiDTnKxo3SVLW6BB2kzYGkSfpYfJhZqj4GrHVGHmZhpKOiUmBqNCV5jzRG04y0+/kGnSHginCzoAgji/1sXBPsWYdvzzy7deML9x55XN+mh0kIMCtU1hB/Iwh1+YSGaJZaVJepI9i8JnVMWnLpcJgQYF7sY3Z4Xj9w8761XW++7fAsC+DZkXmMyiwtn8zM/01nYvXGGoTRaBq2iq5QtnK+q4NipLdggrcvKWnEwVhx8M2hn3/piTP0epg0JUm9bGj5HHjVAtnImapGra1Up5bEqJIkEaorCT6eJA3QTqk6dXp3ZLM2ZUlSq/GueoP/BxNlQQWaqAHaqp41im1TYuO8XTAJIQ1QSAjKZULaGdB58HcTI7vj40aOxjNanu4D945DoZIlj8chbi1VjtuWHLfyOlOBSXSBFwGx4+DqzJJkZscpTGvJNEd1Fr9ftcCAg4amypMxwGb+H+Yo4Dpb94VCsAUrZEOhGcmG/RS+1TeoQfhsMzSFWmlZO3IpSRZeep77VwRjwga/5hYMgU90+rOJV/Zovq//5rn0O45voqwwMCPrHeAxATxWAh7zAI95gQPWZgzFGCS3GCS3qVAhljiRfIV7XL3NQ7OkvhnUbzI1iYGuXIGEQnyti7g8dzVwlD2QgCDHNK8efuSB3Ye7a8DHzX21uO3AGvVHnJunktCSIIzScsuhj/9+/thBw409RqIzUsJMSQzpbr/hLEOmCqRMV/2aldKF9KWD0TCmowhaSAJfhrTT5Z+jLLR7i2kSrVGXIvPQBpKGQ8Xc1sTGLWOf28MdYj5WbY5voLF8AHmG/fyw+eLv3v3z3fzsKSbjFk/WHqas15MAUFkLD/UFru1HLEqB7+rxwW8/e+PQLm544FgVNGEU6wQEvgQRb1hPv73393/88OQHYXezGKk38xnwkAJfy4Jb8AtB+S8WjGLsQAq5PCEyyMpSCjFx5h4XGyQTDVwOoNvRnXrOUNSsKmU0ip7y75Y711/4y5FWZ7s16HGMZ5G1n67A7b+9jzzxzqP/6OJqQjIeZq79XDYnQy50NW+xLGkScRSefH/5829JL4LnQ36z1f2UpyzC7UH4Bm7gtljH6/W+sc9h1e1Yq5P348XDf1oM4LHr+uJofOo7HYnN1500UPJF1HFHQBp4SPKEyYYzub+Fu+t/FiYNo6SVn/iSzh6SIL+BG4zCmW0nRGeK3FY2Xn7+OodNbynWOv1x4JnWHwVu+oE2cmO7yXF8x3HAEC1opOVQ5oNjXRT0LI4uNLFeVAgR3tjERVbxuger1dyQYWyuYSSi5nJ5htvOJ7gLfFQkOvy7GE56X/rbzikOdjjxh/U95biWQWmFOVoErQnA1VcNF1abi4DqMkZeV4p4uiqm4z5kqwisCYEtgdLGJ3EoDQCWDAYG0dtgWuoEhGKhpDSMSiNCmSLoIx6lYE5wedWaDPDbQUvNQeqZELccevjo127Fjhx1Yta5Cq6acRvzyjjXQW6m27itCjDLHdVm4RIDfzp/8OLLBw85V6W28otNv57Pnf3tf34ZO35tOuAwrNUM5+wKNG8nlEVggZ8Kej7AvF90zItVaqYdUeqcoC+V2TECe8yt73jCoFgwkmHYmoxhaFTSg6AtReVxKFsJafyFoK8GQEsH7zz33u0MT258XpT5ZvO2/vu39qcHtiRGdgwFAatXDEj53ICthQqexV3edSr+qxcXuY2C3u3B68mHBLd8eaU7N9/uk08dPaHsOLU+LJLqCNiSGeY6jU5QzaMqgs4z4023nb803Ax57fryjYk9H405zrPCN7Of+/T2qemtPfK3wqSmlApnPG/KhXrLE2CTReF1po+UpcGVJVuhzxDFcb2aK4Ie8O6t6xHc59Ryn2sUIo8Lmveb2T2Ywm5+ug+rrVx1ocrxtR8ruHVscFJWVKSsaCllRT03yKj3Bhl1UZvla22H0kNI7UVBp+a2VhR5RdBTldfqXcWTVca+itUBRhqLwRkUArUThqoErWWpE5d16wVdNbe1oEi3oJ2zW8vXq4x9A6vDkEosmoV3DH9hPe2DzbPcSihD0P6KoGoV2AEpDkXGBZUqww65uWGIaz1WBftxrL4J2WacKmOUH4JDQRbH8+8UIYuvCvrruVkcRd4TdHp2Fv9ulbHvY/VC6YisiBqclZwB8DFBF8wNNYq0Ctr0qQYv3jM+U/Ge4dyAEuI/sndwFC9XWSoP0x/AXUYyTW0yyLX4Sj8L5YcA84ag785tpSjyK0HfmpVrneZaX6uC/AJW5yAs8np17B1QfgxBvVnQdXPDjiJrBe2ZnW/9pMrYG1i9DpmJGc63sOK+tvJnCl7SY56B2/0P8aAV3gnlDYD3mqDPzW2FKHJM0Gdm7Ydtwg85YudZEYyYI5iuYpL3sLqM4bY3Lzkn/8MF0OI9ePAhtyzgO4v4PignLtOTHz24dnGFbyztM77YCrlzJ1oal57YeYV/ICh9+4vA+zub1zTvg8fTrjchFasce8R5/picfMBIdDYfZRiZ5/nHF/y+o+EKI+2VNDDn0cjbXpk/MDK/XIbxD7HY8vJdBQs7fPjvQ77HHb6quLn3zOrbUr/bdnbJzTkdeQs/lk99svSf9Y0j1/jHBjyibp3o+GTTj2qfOn0v2z39r9XyWWv3hYdP/7WQfeZ7PSEzd/N/AFgTtMQXAAA=";
}
