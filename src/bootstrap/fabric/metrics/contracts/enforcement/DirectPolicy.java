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
    
    public static final byte[] $classHash = new byte[] { 84, 94, -3, 37, 14, 98,
    -33, -79, 76, 39, 18, 15, -47, 15, -49, 72, -126, -12, -98, 122, -40, -122,
    80, -120, 91, -71, 19, -21, 98, -75, 57, 15 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507234248000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
