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
            fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
              static_activate(
                (fabric.metrics.contracts.enforcement.DirectPolicy)
                  this.$getProxy());
        }
        
        private static void static_activate(
          fabric.metrics.contracts.enforcement.DirectPolicy tmp) {
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
                    try {
                        if (tmp.get$activated()) return;
                        tmp.refresh();
                    }
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
            fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
              static_refresh((fabric.metrics.contracts.enforcement.DirectPolicy)
                               this.$getProxy());
        }
        
        private static void static_refresh(
          fabric.metrics.contracts.enforcement.DirectPolicy tmp) {
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
                          tmp.get$bound().trueExpiry(tmp.get$metric(),
                                                     currentTime);
                        if (trueTime < currentTime && currentTime <=
                              tmp.get$expiry()) {
                            tmp.set$expiry((long) 0);
                        }
                        else {
                            long hedgedTime =
                              ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                                 tmp.fetch()).hedged(currentTime);
                            if (tmp.get$expiry() <= trueTime) {
                                hedgedTime =
                                  java.lang.Math.max(tmp.get$expiry(),
                                                     hedgedTime);
                            }
                            tmp.set$expiry((long) hedgedTime);
                        }
                        tmp.set$activated(true);
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
            fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
              static_apply((fabric.metrics.contracts.enforcement.DirectPolicy)
                             this.$getProxy(), mc);
        }
        
        private static void static_apply(
          fabric.metrics.contracts.enforcement.DirectPolicy tmp,
          fabric.metrics.contracts.MetricContract mc) {
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
                        if (!tmp.get$activated()) tmp.activate();
                        tmp.get$metric().addObserver(mc);
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
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         mc)) +
                  " WITH " +
                  java.lang.String.valueOf(
                                     fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                         tmp)));
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
    
    public static final byte[] $classHash = new byte[] { 116, -45, 55, 1, -104,
    -109, -96, -35, -88, 70, -36, -90, -84, -115, 20, 106, -61, -45, 95, 126,
    -68, -15, 89, 86, 90, 39, 45, -11, -41, -69, -44, 83 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507234491000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAL0ZC2wUx3Xu/DcGH+ZvwDhwIYXAnYCKCNxGwRdsLjmKZRsqTOFY783ZC3u7y+6cORNApBUFRSqqEiCgNihRoVBwQCVFaUVQaWkbUtJIKUlpq6RFkaKAKI1Qm7RV26Tvzc7d7a33LrYU1dLMm5t57837z+x46C6psEwyOyn1KmqIDRrUCrVJvdFYh2RaNBFRJcvqhtm4PKY8evjWyUSTn/hjpE6WNF1TZEmNaxYj42JbpAEprFEWXtsZbdlAamQkXCVZ/Yz4N7RmTNJs6Opgn6ozsckw/oceDB98dlPgfBmp7yH1itbFJKbIEV1jNMN6SF2Kpnqpaa1IJGiih4zXKE10UVORVGUHIOpaD2mwlD5NYmmTWp3U0tUBRGyw0gY1+Z7ZSRRfB7HNtMx0E8QP2OKnmaKGY4rFWmKkMqlQNWFtI7tJeYxUJFWpDxAnx7JahDnHcBvOA3qtAmKaSUmmWZLyrYqWYGSWmyKncfBxQADSqhRl/Xpuq3JNggnSYIukSlpfuIuZitYHqBV6GnZhpLEoU0CqNiR5q9RH44xMdeN12EuAVcPNgiSMTHKjcU7gs0aXzxzeuvuVLx14Qlul+YkPZE5QWUX5q4GoyUXUSZPUpJpMbcK6+bHD0uRL+/2EAPIkF7KN8/LOe48saLp81caZ7oGzpncLlVlcPt477s0ZkXnLylCMakO3FAyFAs25VzvESkvGgGifnOOIi6Hs4uXOX63fc5re8ZPaKKmUdTWdgqgaL+spQ1Gp2U41akqMJqKkhmqJCF+PkioYxxSN2rNrkkmLsigpV/lUpc5/g4mSwAJNVAVjRUvq2bEhsX4+zhiEkCpoxAftBiGN1wCOgZ/LGdkc7tdTNNyrpul2CO8wNCqZcn8Y8tZU5LBlymEzrTEFkMQURBEAKwyhzkxJZlaYwramTFNUY+FHFRMM2KGrijwYAtmM/8MeGdQzsN3nAxfMkvUE7ZUs8KeIrdYOFdJnla4mqBmX1QOXomTCpaM8vmowJyyIa25BH8TEDHc1cdIeTLeuvHc2fs2OTaQVBmZkkS14SAgeygkecggecgoOstZhKoaguIWguA35MqHIsegZHnGVFk/NHPs6YL/cUCUGvFIZ4vNxXSdyeh5qEChboQBBjamb17Xxsc37Z5dBjBvby9HtgBp0Z1y+TkVhJEEaxeX6fbc+Pnd4l57PPUaCw0rCcEpM6dluw5m6TBNQMvPs5zdLF+KXdgX9WI5q0EISxDKUnSb3HgWp3ZItk2iNihgZgzaQVFzK1rZa1m/q2/MzPCDGYddgxwYayyUgr7Bf7jKe+/0bt5fwsydbjOsdVbuLshZHAUBm9TzVx+dt321SCnjvHul45tDdfRu44QFjjteGQewjkPgSZLxu7r267Q9//tPxt/x5ZzFSaaR7IUIyXJfxn8KfD9on2DCLcQIh1PKIqCDNuRJi4M5z87JBMVEh5EB0K7hWS+kJJalIvSrFSPlP/f2LLvzlQMB2twoztvFMsuCzGeTnp7WSPdc2/aOJs/HJeJjl7ZdHsyvkhDznFaYpDaIcmSd/O/Poq9JzEPlQ3yxlB+Uli3B7EO7AxdwWC3m/yLX2Rexm29aawefx4uE+Ldrw2M3HYk946LuNkYfv2GUgF4vI4z6PMrBOcqTJ4tOpj/yzK3/pJ1U9JMBPfElj6ySobxAGPXBmWxExGSNjC9YLz1/7sGnJ5doMdx44tnVnQb78wBixcVxrB74dOGCIejTSTGjjILAuCvgirk4wsJ+Y8RE+WM5J5vB+LnbzuCH9OJzPSI2SSqUZup1v8CDEqCh0+HMSnPSu8reaQ1xstPMP+6WFck2HFoA96gUs85CrtZRc2D2cFaiiV09riaw8TUXLcSuiFRWsFgWbDK2Bb2JD6iFY1FswyN4qw1QGIBUzOaZ+ZFojmCUE3OhgCuaEkFfMQY+47TCVFJSeAXHLofsPPvVp6MBBO2ftq+CcYbcxJ419HeRmGsttlYFd7iu1C6do++Dcroundu2zr0oNhReblVo69eLv/vt66MjN1zwOw3JVt88uT/POgDYRLPAzAc95mPertnmxiw23I1KdFfBkgR1rwMfc+nYkdAiFEXSBa3p1XaWS5iXaFGQehrYG9vipgCc9RIt7e55H72qGJzd+XhTEZt2qlY+2r4y3rYh0r+n0EqwyoUPJ5wYMZIpEFg/5fFDxv0pxkVsm4BKHvI56SNDlM4vdubm7j3/94LHEmhOL/KKodoMtmW4sVOkAVR2sxmLwDPumW82/NPIV8uadmcsiW9/vs4NnlmtnN/YPVg+91j5XftpPynKlcNjnTSFRS2EBrDUpfJ1p3QVlsDlnK4wZkrBDr+yGgDudvs1HBI85pTDmqgXJEwKm3WbOH0z+fH16BLt2zjpT4vjagR3cOhbbJSsoSlYwV7KCjhtk0HmDDOalNgp1nQptLiHlFwUcGp2uSHJGwBPFdXVq8WSJtW9gt5OR6mxyeqVA+YCuJFy68Dh/CNp8ONMn2bD8wyK6eJ4Rq7GTXaU4IDj9VcCbxVX05XMvwDc7UELPb2O3H+6PdgmIZ9XF6b1ebsKaswRUOyTgvtG5CUm+KeCekbnp2RJrR7F7GqqkSZPwidbvJTb3yFLbKxV3Bbz6uXgEOb0q4E9G4ZEXSqj0Pey+w+BGZHukhGb8aGqG1gOllAm4qYRDPM4lJNko4LoRqdDJuZ4uoQJP3RNwRPTTRB/lN5dOr1jCSwuciVOaBBw/ulhCkoCAtSOLpZdKrF3A7mzuXlNUaqgw5Eew5W4BpdFJjSSbBez5TINnL4cPFL0c2tfWiPiN6I1cildKqHoZu5fhAioZhjpYNGnaof0YxLwt4EufS9Igp/MCHi9uAMepFMDuEt/xagmtfo3dz+Hqkq1lxZTjbvwCtCuETKu24dTbo3MjktwS8L0R5Y2twJslFLiO3etQzdJaadkboV0F2dcLuGp0siNJu4ArRpY4fyyx9g52b8NZyXT7dTYbtAH+4YyfjSHHwjT305CXhvdD+w2I94aA50enIZL8UMAzI06yBpFkXGL7Q9dbYi7BByVMche797CWbEtL9l30a15qBqG9Df68IuCp0amJJCcFfGFkjvx7ibWPsfuQkYmSvC0Nl7ZOCmUmqfTFdHkrJ9ibAUs4r3P4PDLd4/VSvLrLkV/Q4+8/vmBSkZfLqcP+DyLozh6rr55ybO0N/uyWe1GviZHqZFpVnc8IjnGlAWelwjWpsR8VDA7+zUhwJE+djIxx/OIK/8vm8AkjU4txYPZTDB87aHzwWT2ukIbxf2/gyIlXDlFi4+GvCu7ARleXDdClI3qxXZkf217KHwqNaRP/BTX0tyn/rKzuvsmf8MD7zez6Q74jzzz/7qm2d74/9K2JW65cj+9+5d76dT0PLPzoxsW3uv4HYLcIdBobAAA=";
}
