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
                fabric.worker.transaction.TransactionManager $tm55 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled58 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff56 = 1;
                boolean $doBackoff57 = true;
                $label51: for (boolean $commit52 = false; !$commit52; ) {
                    if ($backoffEnabled58) {
                        if ($doBackoff57) {
                            if ($backoff56 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff56);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e53) {
                                        
                                    }
                                }
                            }
                            if ($backoff56 < 5000) $backoff56 *= 2;
                        }
                        $doBackoff57 = $backoff56 <= 32 || !$doBackoff57;
                    }
                    $commit52 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (this.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e53) {
                        $commit52 = false;
                        continue $label51;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e53) {
                        $commit52 = false;
                        fabric.common.TransactionID $currentTid54 =
                          $tm55.getCurrentTid();
                        if ($e53.tid.isDescendantOf($currentTid54))
                            continue $label51;
                        if ($currentTid54.parent != null) throw $e53;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e53) {
                        $commit52 = false;
                        if ($tm55.checkForStaleObjects()) continue $label51;
                        throw new fabric.worker.AbortException($e53);
                    }
                    finally {
                        if ($commit52) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e53) {
                                $commit52 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e53) {
                                $commit52 = false;
                                fabric.common.TransactionID $currentTid54 =
                                  $tm55.getCurrentTid();
                                if ($currentTid54 != null) {
                                    if ($e53.tid.equals($currentTid54) ||
                                          !$e53.tid.isDescendantOf(
                                                      $currentTid54)) {
                                        throw $e53;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit52) {
                            {  }
                            continue $label51;
                        }
                    }
                }
            }
        }
        
        public void refresh() {
            {
                fabric.worker.transaction.TransactionManager $tm63 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled66 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff64 = 1;
                boolean $doBackoff65 = true;
                $label59: for (boolean $commit60 = false; !$commit60; ) {
                    if ($backoffEnabled66) {
                        if ($doBackoff65) {
                            if ($backoff64 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff64);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e61) {
                                        
                                    }
                                }
                            }
                            if ($backoff64 < 5000) $backoff64 *= 2;
                        }
                        $doBackoff65 = $backoff64 <= 32 || !$doBackoff65;
                    }
                    $commit60 = true;
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
                    catch (final fabric.worker.RetryException $e61) {
                        $commit60 = false;
                        continue $label59;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e61) {
                        $commit60 = false;
                        fabric.common.TransactionID $currentTid62 =
                          $tm63.getCurrentTid();
                        if ($e61.tid.isDescendantOf($currentTid62))
                            continue $label59;
                        if ($currentTid62.parent != null) throw $e61;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e61) {
                        $commit60 = false;
                        if ($tm63.checkForStaleObjects()) continue $label59;
                        throw new fabric.worker.AbortException($e61);
                    }
                    finally {
                        if ($commit60) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e61) {
                                $commit60 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e61) {
                                $commit60 = false;
                                fabric.common.TransactionID $currentTid62 =
                                  $tm63.getCurrentTid();
                                if ($currentTid62 != null) {
                                    if ($e61.tid.equals($currentTid62) ||
                                          !$e61.tid.isDescendantOf(
                                                      $currentTid62)) {
                                        throw $e61;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit60) {
                            {  }
                            continue $label59;
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
                fabric.worker.transaction.TransactionManager $tm71 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled74 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff72 = 1;
                boolean $doBackoff73 = true;
                $label67: for (boolean $commit68 = false; !$commit68; ) {
                    if ($backoffEnabled74) {
                        if ($doBackoff73) {
                            if ($backoff72 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff72);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e69) {
                                        
                                    }
                                }
                            }
                            if ($backoff72 < 5000) $backoff72 *= 2;
                        }
                        $doBackoff73 = $backoff72 <= 32 || !$doBackoff73;
                    }
                    $commit68 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (!this.get$activated()) activate();
                        this.get$metric().addObserver(mc);
                    }
                    catch (final fabric.worker.RetryException $e69) {
                        $commit68 = false;
                        continue $label67;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e69) {
                        $commit68 = false;
                        fabric.common.TransactionID $currentTid70 =
                          $tm71.getCurrentTid();
                        if ($e69.tid.isDescendantOf($currentTid70))
                            continue $label67;
                        if ($currentTid70.parent != null) throw $e69;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e69) {
                        $commit68 = false;
                        if ($tm71.checkForStaleObjects()) continue $label67;
                        throw new fabric.worker.AbortException($e69);
                    }
                    finally {
                        if ($commit68) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e69) {
                                $commit68 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e69) {
                                $commit68 = false;
                                fabric.common.TransactionID $currentTid70 =
                                  $tm71.getCurrentTid();
                                if ($currentTid70 != null) {
                                    if ($e69.tid.equals($currentTid70) ||
                                          !$e69.tid.isDescendantOf(
                                                      $currentTid70)) {
                                        throw $e69;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit68) {
                            {  }
                            continue $label67;
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
                        fabric.worker.transaction.TransactionManager $tm79 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled82 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff80 = 1;
                        boolean $doBackoff81 = true;
                        $label75: for (boolean $commit76 = false; !$commit76;
                                       ) {
                            if ($backoffEnabled82) {
                                if ($doBackoff81) {
                                    if ($backoff80 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff80);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e77) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff80 < 5000) $backoff80 *= 2;
                                }
                                $doBackoff81 = $backoff80 <= 32 ||
                                                 !$doBackoff81;
                            }
                            $commit76 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e77) {
                                $commit76 = false;
                                continue $label75;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e77) {
                                $commit76 = false;
                                fabric.common.TransactionID $currentTid78 =
                                  $tm79.getCurrentTid();
                                if ($e77.tid.isDescendantOf($currentTid78))
                                    continue $label75;
                                if ($currentTid78.parent != null) throw $e77;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e77) {
                                $commit76 = false;
                                if ($tm79.checkForStaleObjects())
                                    continue $label75;
                                throw new fabric.worker.AbortException($e77);
                            }
                            finally {
                                if ($commit76) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e77) {
                                        $commit76 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e77) {
                                        $commit76 = false;
                                        fabric.common.TransactionID
                                          $currentTid78 = $tm79.getCurrentTid();
                                        if ($currentTid78 != null) {
                                            if ($e77.tid.equals(
                                                           $currentTid78) ||
                                                  !$e77.tid.isDescendantOf(
                                                              $currentTid78)) {
                                                throw $e77;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit76) {
                                    {  }
                                    continue $label75;
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
    public static final long jlc$SourceLastModified$fabil = 1506967531000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
