package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.MetricContract;
import fabric.worker.transaction.TransactionManager;

/**
 * An {@link EnforcementPolicy} for enforcing a {@link MetricContract}s by
 * checking every update to the associated {@link Metric}.
 */
public interface DirectPolicy
  extends fabric.metrics.contracts.enforcement.EnforcementPolicy,
          fabric.lang.Object
{
    public fabric.metrics.Metric get$metric();
    
    public fabric.metrics.Metric set$metric(fabric.metrics.Metric val);
    
    public double get$rate();
    
    public double set$rate(double val);
    
    public double postInc$rate();
    
    public double postDec$rate();
    
    public double get$base();
    
    public double set$base(double val);
    
    public double postInc$base();
    
    public double postDec$base();
    
    public long get$expiry();
    
    public long set$expiry(long val);
    
    public long postInc$expiry();
    
    public long postDec$expiry();
    
    public boolean get$activated();
    
    public boolean set$activated(boolean val);
    
    /**
   * @param metric
   *            the Metric the associated contract is bounding.
   * @param rate
   *            the rate of the bound enforced by the associated contract.
   * @param base
   *            the base of the bound enforced by the associated contract.
   */
    public fabric.metrics.contracts.enforcement.DirectPolicy
      fabric$metrics$contracts$enforcement$DirectPolicy$(
      fabric.metrics.Metric metric, double rate, double base);
    
    public void activate();
    
    public void refresh();
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
    public void acquireReconfigLocks();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.enforcement.DirectPolicy {
        public fabric.metrics.Metric get$metric() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).get$metric();
        }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).set$metric(val);
        }
        
        public double get$rate() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).get$rate();
        }
        
        public double set$rate(double val) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).set$rate(val);
        }
        
        public double postInc$rate() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).postInc$rate();
        }
        
        public double postDec$rate() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).postDec$rate();
        }
        
        public double get$base() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).get$base();
        }
        
        public double set$base(double val) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).set$base(val);
        }
        
        public double postInc$base() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).postInc$base();
        }
        
        public double postDec$base() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).postDec$base();
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
          fabric.metrics.Metric arg1, double arg2, double arg3) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      fetch()).
              fabric$metrics$contracts$enforcement$DirectPolicy$(arg1, arg2,
                                                                 arg3);
        }
        
        public void activate() {
            ((fabric.metrics.contracts.enforcement.DirectPolicy) fetch()).
              activate();
        }
        
        public void refresh() {
            ((fabric.metrics.contracts.enforcement.DirectPolicy) fetch()).
              refresh();
        }
        
        public static long hedged(fabric.metrics.Metric arg1, double arg2,
                                  double arg3, long arg4) {
            return fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
              hedged(arg1, arg2, arg3, arg4);
        }
        
        public long expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      fetch()).expiry();
        }
        
        public void apply(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.contracts.enforcement.DirectPolicy) fetch()).
              apply(arg1);
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.contracts.enforcement.DirectPolicy) fetch()).
              unapply(arg1);
        }
        
        public void acquireReconfigLocks() {
            ((fabric.metrics.contracts.enforcement.DirectPolicy) fetch()).
              acquireReconfigLocks();
        }
        
        public _Proxy(DirectPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
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
        
        public double get$rate() { return this.rate; }
        
        public double set$rate(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.rate = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$rate() {
            double tmp = this.get$rate();
            this.set$rate((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$rate() {
            double tmp = this.get$rate();
            this.set$rate((double) (tmp - 1));
            return tmp;
        }
        
        public double rate;
        
        public double get$base() { return this.base; }
        
        public double set$base(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.base = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$base() {
            double tmp = this.get$base();
            this.set$base((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$base() {
            double tmp = this.get$base();
            this.set$base((double) (tmp - 1));
            return tmp;
        }
        
        public double base;
        
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
   * @param rate
   *            the rate of the bound enforced by the associated contract.
   * @param base
   *            the base of the bound enforced by the associated contract.
   */
        public fabric.metrics.contracts.enforcement.DirectPolicy
          fabric$metrics$contracts$enforcement$DirectPolicy$(
          fabric.metrics.Metric metric, double rate, double base) {
            this.set$metric(metric);
            this.set$rate((double) rate);
            this.set$base((double) base);
            fabric$lang$Object$();
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
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$activated()) return;
                tmp.refresh();
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm467 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled470 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff468 = 1;
                    boolean $doBackoff469 = true;
                    boolean $retry464 = true;
                    $label462: for (boolean $commit463 = false; !$commit463; ) {
                        if ($backoffEnabled470) {
                            if ($doBackoff469) {
                                if ($backoff468 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff468);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e465) {
                                            
                                        }
                                    }
                                }
                                if ($backoff468 < 5000) $backoff468 *= 2;
                            }
                            $doBackoff469 = $backoff468 <= 32 || !$doBackoff469;
                        }
                        $commit463 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$activated()) return;
                            tmp.refresh();
                        }
                        catch (final fabric.worker.RetryException $e465) {
                            $commit463 = false;
                            continue $label462;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e465) {
                            $commit463 = false;
                            fabric.common.TransactionID $currentTid466 =
                              $tm467.getCurrentTid();
                            if ($e465.tid.isDescendantOf($currentTid466))
                                continue $label462;
                            if ($currentTid466.parent != null) {
                                $retry464 = false;
                                throw $e465;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e465) {
                            $commit463 = false;
                            if ($tm467.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid466 =
                              $tm467.getCurrentTid();
                            if ($e465.tid.isDescendantOf($currentTid466)) {
                                $retry464 = true;
                            }
                            else if ($currentTid466.parent != null) {
                                $retry464 = false;
                                throw $e465;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e465) {
                            $commit463 = false;
                            if ($tm467.checkForStaleObjects())
                                continue $label462;
                            $retry464 = false;
                            throw new fabric.worker.AbortException($e465);
                        }
                        finally {
                            if ($commit463) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e465) {
                                    $commit463 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e465) {
                                    $commit463 = false;
                                    fabric.common.TransactionID $currentTid466 =
                                      $tm467.getCurrentTid();
                                    if ($currentTid466 != null) {
                                        if ($e465.tid.equals($currentTid466) ||
                                              !$e465.tid.isDescendantOf(
                                                           $currentTid466)) {
                                            throw $e465;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit463 && $retry464) {
                                {  }
                                continue $label462;
                            }
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
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                long currentTime = java.lang.System.currentTimeMillis();
                long trueTime =
                  fabric.metrics.contracts.Bound._Impl.trueExpiry(
                                                         tmp.get$rate(),
                                                         tmp.get$base(),
                                                         tmp.get$metric().value(
                                                                            ),
                                                         currentTime);
                if (trueTime < currentTime && currentTime <= tmp.get$expiry()) {
                    tmp.set$expiry((long) 0);
                }
                else {
                    long hedgedTime =
                      ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                         tmp.fetch()).hedged(currentTime);
                    if (tmp.get$expiry() <= trueTime) {
                        hedgedTime = java.lang.Math.max(tmp.get$expiry(),
                                                        hedgedTime);
                    }
                    tmp.set$expiry((long) hedgedTime);
                }
                tmp.set$activated(true);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm476 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled479 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff477 = 1;
                    boolean $doBackoff478 = true;
                    boolean $retry473 = true;
                    $label471: for (boolean $commit472 = false; !$commit472; ) {
                        if ($backoffEnabled479) {
                            if ($doBackoff478) {
                                if ($backoff477 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff477);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e474) {
                                            
                                        }
                                    }
                                }
                                if ($backoff477 < 5000) $backoff477 *= 2;
                            }
                            $doBackoff478 = $backoff477 <= 32 || !$doBackoff478;
                        }
                        $commit472 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            long currentTime =
                              java.lang.System.currentTimeMillis();
                            long trueTime =
                              fabric.metrics.contracts.Bound._Impl.
                              trueExpiry(tmp.get$rate(), tmp.get$base(),
                                         tmp.get$metric().value(), currentTime);
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
                        catch (final fabric.worker.RetryException $e474) {
                            $commit472 = false;
                            continue $label471;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e474) {
                            $commit472 = false;
                            fabric.common.TransactionID $currentTid475 =
                              $tm476.getCurrentTid();
                            if ($e474.tid.isDescendantOf($currentTid475))
                                continue $label471;
                            if ($currentTid475.parent != null) {
                                $retry473 = false;
                                throw $e474;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e474) {
                            $commit472 = false;
                            if ($tm476.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid475 =
                              $tm476.getCurrentTid();
                            if ($e474.tid.isDescendantOf($currentTid475)) {
                                $retry473 = true;
                            }
                            else if ($currentTid475.parent != null) {
                                $retry473 = false;
                                throw $e474;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e474) {
                            $commit472 = false;
                            if ($tm476.checkForStaleObjects())
                                continue $label471;
                            $retry473 = false;
                            throw new fabric.worker.AbortException($e474);
                        }
                        finally {
                            if ($commit472) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e474) {
                                    $commit472 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e474) {
                                    $commit472 = false;
                                    fabric.common.TransactionID $currentTid475 =
                                      $tm476.getCurrentTid();
                                    if ($currentTid475 != null) {
                                        if ($e474.tid.equals($currentTid475) ||
                                              !$e474.tid.isDescendantOf(
                                                           $currentTid475)) {
                                            throw $e474;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit472 && $retry473) {
                                {  }
                                continue $label471;
                            }
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
            double r = this.get$rate();
            double b =
              fabric.metrics.contracts.Bound._Impl.value(this.get$rate(),
                                                         this.get$base(), time);
            double x = this.get$metric().value();
            double v = this.get$metric().velocity(true);
            double n = this.get$metric().noise(true);
            long hedgedResult =
              fabric.metrics.contracts.Bound._Impl.trueExpiry(
                                                     this.get$rate(),
                                                     this.get$base(),
                                                     this.get$metric().value(),
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
                min =
                  fabric.metrics.contracts.Bound._Impl.trueExpiry(
                                                         this.get$rate(),
                                                         this.get$base(), minYs,
                                                         time);
                if (minYs <
                      x &&
                      fabric.metrics.contracts.Bound._Impl.test(this.get$rate(),
                                                                this.get$base(),
                                                                minYs, time)) {
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
        
        /**
   * Using the associated {@link Metric's} statistics and some tuning
   * constants, figure out a time to advertise given the current time.
   *
   * @param metric
   *        The metric being bounded
   * @param rate
   *        The adaptive multiplier for the bound
   * @param base
   *        The base value for the bound (relative to t = 0)
   * @param time
   *        the current time we're calculating this hedged expiration at
   * @return an appropriately conservative (unlikely to be retracted again)
   *         time to advertise to other nodes in the system for this contract.
   */
        public static long hedged(fabric.metrics.Metric metric, double rate,
                                  double base, long time) {
            double r = rate;
            double b = fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                                  time);
            double x = metric.value();
            double v = metric.velocity(true);
            double n = metric.noise(true);
            long hedgedResult =
              fabric.metrics.contracts.Bound._Impl.trueExpiry(rate, base,
                                                              metric.value(),
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
                min = fabric.metrics.contracts.Bound._Impl.trueExpiry(rate,
                                                                      base,
                                                                      minYs,
                                                                      time);
                if (minYs <
                      x &&
                      fabric.metrics.contracts.Bound._Impl.test(rate, base,
                                                                minYs, time)) {
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
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (!tmp.get$activated()) tmp.activate();
                tmp.get$metric().addObserver(mc);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm485 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled488 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff486 = 1;
                    boolean $doBackoff487 = true;
                    boolean $retry482 = true;
                    $label480: for (boolean $commit481 = false; !$commit481; ) {
                        if ($backoffEnabled488) {
                            if ($doBackoff487) {
                                if ($backoff486 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff486);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e483) {
                                            
                                        }
                                    }
                                }
                                if ($backoff486 < 5000) $backoff486 *= 2;
                            }
                            $doBackoff487 = $backoff486 <= 32 || !$doBackoff487;
                        }
                        $commit481 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated()) tmp.activate();
                            tmp.get$metric().addObserver(mc);
                        }
                        catch (final fabric.worker.RetryException $e483) {
                            $commit481 = false;
                            continue $label480;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e483) {
                            $commit481 = false;
                            fabric.common.TransactionID $currentTid484 =
                              $tm485.getCurrentTid();
                            if ($e483.tid.isDescendantOf($currentTid484))
                                continue $label480;
                            if ($currentTid484.parent != null) {
                                $retry482 = false;
                                throw $e483;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e483) {
                            $commit481 = false;
                            if ($tm485.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid484 =
                              $tm485.getCurrentTid();
                            if ($e483.tid.isDescendantOf($currentTid484)) {
                                $retry482 = true;
                            }
                            else if ($currentTid484.parent != null) {
                                $retry482 = false;
                                throw $e483;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e483) {
                            $commit481 = false;
                            if ($tm485.checkForStaleObjects())
                                continue $label480;
                            $retry482 = false;
                            throw new fabric.worker.AbortException($e483);
                        }
                        finally {
                            if ($commit481) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e483) {
                                    $commit481 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e483) {
                                    $commit481 = false;
                                    fabric.common.TransactionID $currentTid484 =
                                      $tm485.getCurrentTid();
                                    if ($currentTid484 != null) {
                                        if ($e483.tid.equals($currentTid484) ||
                                              !$e483.tid.isDescendantOf(
                                                           $currentTid484)) {
                                            throw $e483;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit481 && $retry482) {
                                {  }
                                continue $label480;
                            }
                        }
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
            " >= " +
            this.get$rate() +
            " * t + " +
            this.get$base();
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
              this.get$rate() == that.get$rate() && this.get$base() ==
              that.get$base();
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
            out.writeDouble(this.rate);
            out.writeDouble(this.base);
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
            this.rate = in.readDouble();
            this.base = in.readDouble();
            this.expiry = in.readLong();
            this.activated = in.readBoolean();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.enforcement.DirectPolicy._Impl src =
              (fabric.metrics.contracts.enforcement.DirectPolicy._Impl) other;
            this.metric = src.metric;
            this.rate = src.rate;
            this.base = src.base;
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
                        fabric.worker.transaction.TransactionManager $tm494 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled497 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff495 = 1;
                        boolean $doBackoff496 = true;
                        boolean $retry491 = true;
                        $label489: for (boolean $commit490 = false; !$commit490;
                                        ) {
                            if ($backoffEnabled497) {
                                if ($doBackoff496) {
                                    if ($backoff495 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff495);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e492) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff495 < 5000) $backoff495 *= 2;
                                }
                                $doBackoff496 = $backoff495 <= 32 ||
                                                  !$doBackoff496;
                            }
                            $commit490 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e492) {
                                $commit490 = false;
                                continue $label489;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e492) {
                                $commit490 = false;
                                fabric.common.TransactionID $currentTid493 =
                                  $tm494.getCurrentTid();
                                if ($e492.tid.isDescendantOf($currentTid493))
                                    continue $label489;
                                if ($currentTid493.parent != null) {
                                    $retry491 = false;
                                    throw $e492;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e492) {
                                $commit490 = false;
                                if ($tm494.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid493 =
                                  $tm494.getCurrentTid();
                                if ($e492.tid.isDescendantOf($currentTid493)) {
                                    $retry491 = true;
                                }
                                else if ($currentTid493.parent != null) {
                                    $retry491 = false;
                                    throw $e492;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e492) {
                                $commit490 = false;
                                if ($tm494.checkForStaleObjects())
                                    continue $label489;
                                $retry491 = false;
                                throw new fabric.worker.AbortException($e492);
                            }
                            finally {
                                if ($commit490) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e492) {
                                        $commit490 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e492) {
                                        $commit490 = false;
                                        fabric.common.TransactionID
                                          $currentTid493 =
                                          $tm494.getCurrentTid();
                                        if ($currentTid493 != null) {
                                            if ($e492.tid.equals(
                                                            $currentTid493) ||
                                                  !$e492.tid.
                                                  isDescendantOf(
                                                    $currentTid493)) {
                                                throw $e492;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit490 && $retry491) {
                                    {  }
                                    continue $label489;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 70, 73, 23, -7, -42,
    78, -124, -50, 0, 39, -92, 97, 42, -26, -93, 106, 75, -68, 70, -39, 4, -106,
    80, -14, 116, 36, -53, -86, -115, 38, 42, -84 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519071321000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
