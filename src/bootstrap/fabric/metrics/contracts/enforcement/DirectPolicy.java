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
    
    public static final byte[] $classHash = new byte[] { 116, -72, 90, -110,
    -21, 61, 104, 3, 68, 110, -120, -88, 9, 86, -85, 119, 61, -27, -9, 125,
    -121, -65, -81, -115, 114, 122, 93, 36, -48, -47, -39, -118 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507151216000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
