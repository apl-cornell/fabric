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
    
    public void acquireReconfigLocks();
    
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
                fabric.worker.transaction.TransactionManager $tm416 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled419 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff417 = 1;
                boolean $doBackoff418 = true;
                $label412: for (boolean $commit413 = false; !$commit413; ) {
                    if ($backoffEnabled419) {
                        if ($doBackoff418) {
                            if ($backoff417 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff417);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e414) {
                                        
                                    }
                                }
                            }
                            if ($backoff417 < 5000) $backoff417 *= 2;
                        }
                        $doBackoff418 = $backoff417 <= 32 || !$doBackoff418;
                    }
                    $commit413 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try { if (this.get$activated()) return; }
                    catch (final fabric.worker.RetryException $e414) {
                        $commit413 = false;
                        continue $label412;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e414) {
                        $commit413 = false;
                        fabric.common.TransactionID $currentTid415 =
                          $tm416.getCurrentTid();
                        if ($e414.tid.isDescendantOf($currentTid415))
                            continue $label412;
                        if ($currentTid415.parent != null) throw $e414;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e414) {
                        $commit413 = false;
                        if ($tm416.checkForStaleObjects()) continue $label412;
                        throw new fabric.worker.AbortException($e414);
                    }
                    finally {
                        if ($commit413) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e414) {
                                $commit413 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e414) {
                                $commit413 = false;
                                fabric.common.TransactionID $currentTid415 =
                                  $tm416.getCurrentTid();
                                if ($currentTid415 != null) {
                                    if ($e414.tid.equals($currentTid415) ||
                                          !$e414.tid.isDescendantOf(
                                                       $currentTid415)) {
                                        throw $e414;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit413) {
                            {  }
                            continue $label412;
                        }
                    }
                }
            }
        }
        
        public void refresh() {
            {
                fabric.worker.transaction.TransactionManager $tm424 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled427 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff425 = 1;
                boolean $doBackoff426 = true;
                $label420: for (boolean $commit421 = false; !$commit421; ) {
                    if ($backoffEnabled427) {
                        if ($doBackoff426) {
                            if ($backoff425 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff425);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e422) {
                                        
                                    }
                                }
                            }
                            if ($backoff425 < 5000) $backoff425 *= 2;
                        }
                        $doBackoff426 = $backoff425 <= 32 || !$doBackoff426;
                    }
                    $commit421 = true;
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
                    catch (final fabric.worker.RetryException $e422) {
                        $commit421 = false;
                        continue $label420;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e422) {
                        $commit421 = false;
                        fabric.common.TransactionID $currentTid423 =
                          $tm424.getCurrentTid();
                        if ($e422.tid.isDescendantOf($currentTid423))
                            continue $label420;
                        if ($currentTid423.parent != null) throw $e422;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e422) {
                        $commit421 = false;
                        if ($tm424.checkForStaleObjects()) continue $label420;
                        throw new fabric.worker.AbortException($e422);
                    }
                    finally {
                        if ($commit421) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e422) {
                                $commit421 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e422) {
                                $commit421 = false;
                                fabric.common.TransactionID $currentTid423 =
                                  $tm424.getCurrentTid();
                                if ($currentTid423 != null) {
                                    if ($e422.tid.equals($currentTid423) ||
                                          !$e422.tid.isDescendantOf(
                                                       $currentTid423)) {
                                        throw $e422;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit421) {
                            {  }
                            continue $label420;
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
                fabric.worker.transaction.TransactionManager $tm432 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled435 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff433 = 1;
                boolean $doBackoff434 = true;
                $label428: for (boolean $commit429 = false; !$commit429; ) {
                    if ($backoffEnabled435) {
                        if ($doBackoff434) {
                            if ($backoff433 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.sleep($backoff433);
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e430) {
                                        
                                    }
                                }
                            }
                            if ($backoff433 < 5000) $backoff433 *= 2;
                        }
                        $doBackoff434 = $backoff433 <= 32 || !$doBackoff434;
                    }
                    $commit429 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        if (!this.get$activated()) activate();
                        this.get$metric().addObserver(mc);
                    }
                    catch (final fabric.worker.RetryException $e430) {
                        $commit429 = false;
                        continue $label428;
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e430) {
                        $commit429 = false;
                        fabric.common.TransactionID $currentTid431 =
                          $tm432.getCurrentTid();
                        if ($e430.tid.isDescendantOf($currentTid431))
                            continue $label428;
                        if ($currentTid431.parent != null) throw $e430;
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final Throwable $e430) {
                        $commit429 = false;
                        if ($tm432.checkForStaleObjects()) continue $label428;
                        throw new fabric.worker.AbortException($e430);
                    }
                    finally {
                        if ($commit429) {
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e430) {
                                $commit429 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e430) {
                                $commit429 = false;
                                fabric.common.TransactionID $currentTid431 =
                                  $tm432.getCurrentTid();
                                if ($currentTid431 != null) {
                                    if ($e430.tid.equals($currentTid431) ||
                                          !$e430.tid.isDescendantOf(
                                                       $currentTid431)) {
                                        throw $e430;
                                    }
                                }
                            }
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit429) {
                            {  }
                            continue $label428;
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
        
        public void acquireReconfigLocks() {  }
        
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
                        fabric.worker.transaction.TransactionManager $tm440 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled443 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff441 = 1;
                        boolean $doBackoff442 = true;
                        $label436: for (boolean $commit437 = false; !$commit437;
                                        ) {
                            if ($backoffEnabled443) {
                                if ($doBackoff442) {
                                    if ($backoff441 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff441);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e438) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff441 < 5000) $backoff441 *= 2;
                                }
                                $doBackoff442 = $backoff441 <= 32 ||
                                                  !$doBackoff442;
                            }
                            $commit437 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e438) {
                                $commit437 = false;
                                continue $label436;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e438) {
                                $commit437 = false;
                                fabric.common.TransactionID $currentTid439 =
                                  $tm440.getCurrentTid();
                                if ($e438.tid.isDescendantOf($currentTid439))
                                    continue $label436;
                                if ($currentTid439.parent != null) throw $e438;
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e438) {
                                $commit437 = false;
                                if ($tm440.checkForStaleObjects())
                                    continue $label436;
                                throw new fabric.worker.AbortException($e438);
                            }
                            finally {
                                if ($commit437) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e438) {
                                        $commit437 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e438) {
                                        $commit437 = false;
                                        fabric.common.TransactionID
                                          $currentTid439 =
                                          $tm440.getCurrentTid();
                                        if ($currentTid439 != null) {
                                            if ($e438.tid.equals(
                                                            $currentTid439) ||
                                                  !$e438.tid.
                                                  isDescendantOf(
                                                    $currentTid439)) {
                                                throw $e438;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit437) {
                                    {  }
                                    continue $label436;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 14, -114, 29, 25, -53,
    -13, 114, -71, 124, 85, 15, 99, 88, 118, 85, 10, 41, -15, -107, 70, 41, 1,
    110, -85, 43, 103, 102, -15, 83, -63, -112, -76 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507217540000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
