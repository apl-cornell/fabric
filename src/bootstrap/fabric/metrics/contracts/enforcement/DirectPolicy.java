package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.Contract;
import fabric.worker.transaction.TransactionManager;

/**
 * An {@link EnforcementPolicy} for enforcing a {@link Contract}s by
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
    
    public void refresh(boolean useWeakValues);
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.Contract mc);
    
    public void unapply(fabric.metrics.contracts.Contract mc);
    
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
        
        public void refresh(boolean arg1) {
            ((fabric.metrics.contracts.enforcement.DirectPolicy) fetch()).
              refresh(arg1);
        }
        
        public static long hedgedEstimate(fabric.metrics.Metric arg1,
                                          double arg2, double arg3, long arg4,
                                          boolean arg5) {
            return fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
              hedgedEstimate(arg1, arg2, arg3, arg4, arg5);
        }
        
        public long expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      fetch()).expiry();
        }
        
        public void apply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.DirectPolicy) fetch()).
              apply(arg1);
        }
        
        public void unapply(fabric.metrics.contracts.Contract arg1) {
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
                tmp.refresh(false);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm532 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled535 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff533 = 1;
                    boolean $doBackoff534 = true;
                    boolean $retry529 = true;
                    $label527: for (boolean $commit528 = false; !$commit528; ) {
                        if ($backoffEnabled535) {
                            if ($doBackoff534) {
                                if ($backoff533 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff533);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e530) {
                                            
                                        }
                                    }
                                }
                                if ($backoff533 < 5000) $backoff533 *= 2;
                            }
                            $doBackoff534 = $backoff533 <= 32 || !$doBackoff534;
                        }
                        $commit528 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$activated()) return;
                            tmp.refresh(true);
                        }
                        catch (final fabric.worker.RetryException $e530) {
                            $commit528 = false;
                            continue $label527;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e530) {
                            $commit528 = false;
                            fabric.common.TransactionID $currentTid531 =
                              $tm532.getCurrentTid();
                            if ($e530.tid.isDescendantOf($currentTid531))
                                continue $label527;
                            if ($currentTid531.parent != null) {
                                $retry529 = false;
                                throw $e530;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e530) {
                            $commit528 = false;
                            if ($tm532.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid531 =
                              $tm532.getCurrentTid();
                            if ($e530.tid.isDescendantOf($currentTid531)) {
                                $retry529 = true;
                            }
                            else if ($currentTid531.parent != null) {
                                $retry529 = false;
                                throw $e530;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e530) {
                            $commit528 = false;
                            if ($tm532.checkForStaleObjects())
                                continue $label527;
                            $retry529 = false;
                            throw new fabric.worker.AbortException($e530);
                        }
                        finally {
                            if ($commit528) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e530) {
                                    $commit528 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e530) {
                                    $commit528 = false;
                                    fabric.common.TransactionID $currentTid531 =
                                      $tm532.getCurrentTid();
                                    if ($currentTid531 != null) {
                                        if ($e530.tid.equals($currentTid531) ||
                                              !$e530.tid.isDescendantOf(
                                                           $currentTid531)) {
                                            throw $e530;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit528 && $retry529) {
                                {  }
                                continue $label527;
                            }
                        }
                    }
                }
            }
        }
        
        public void refresh(boolean useWeakValues) {
            fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
              static_refresh((fabric.metrics.contracts.enforcement.DirectPolicy)
                               this.$getProxy(), useWeakValues);
        }
        
        private static void static_refresh(
          fabric.metrics.contracts.enforcement.DirectPolicy tmp,
          boolean useWeakValues) {
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
                         tmp.fetch()).hedged(currentTime, useWeakValues);
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
                    fabric.worker.transaction.TransactionManager $tm541 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled544 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff542 = 1;
                    boolean $doBackoff543 = true;
                    boolean $retry538 = true;
                    $label536: for (boolean $commit537 = false; !$commit537; ) {
                        if ($backoffEnabled544) {
                            if ($doBackoff543) {
                                if ($backoff542 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff542);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e539) {
                                            
                                        }
                                    }
                                }
                                if ($backoff542 < 5000) $backoff542 *= 2;
                            }
                            $doBackoff543 = $backoff542 <= 32 || !$doBackoff543;
                        }
                        $commit537 = true;
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
                                     tmp.fetch()).hedged(currentTime,
                                                         useWeakValues);
                                if (tmp.get$expiry() <= trueTime) {
                                    hedgedTime =
                                      java.lang.Math.max(tmp.get$expiry(),
                                                         hedgedTime);
                                }
                                tmp.set$expiry((long) hedgedTime);
                            }
                            tmp.set$activated(true);
                        }
                        catch (final fabric.worker.RetryException $e539) {
                            $commit537 = false;
                            continue $label536;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e539) {
                            $commit537 = false;
                            fabric.common.TransactionID $currentTid540 =
                              $tm541.getCurrentTid();
                            if ($e539.tid.isDescendantOf($currentTid540))
                                continue $label536;
                            if ($currentTid540.parent != null) {
                                $retry538 = false;
                                throw $e539;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e539) {
                            $commit537 = false;
                            if ($tm541.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid540 =
                              $tm541.getCurrentTid();
                            if ($e539.tid.isDescendantOf($currentTid540)) {
                                $retry538 = true;
                            }
                            else if ($currentTid540.parent != null) {
                                $retry538 = false;
                                throw $e539;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e539) {
                            $commit537 = false;
                            if ($tm541.checkForStaleObjects())
                                continue $label536;
                            $retry538 = false;
                            throw new fabric.worker.AbortException($e539);
                        }
                        finally {
                            if ($commit537) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e539) {
                                    $commit537 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e539) {
                                    $commit537 = false;
                                    fabric.common.TransactionID $currentTid540 =
                                      $tm541.getCurrentTid();
                                    if ($currentTid540 != null) {
                                        if ($e539.tid.equals($currentTid540) ||
                                              !$e539.tid.isDescendantOf(
                                                           $currentTid540)) {
                                            throw $e539;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit537 && $retry538) {
                                {  }
                                continue $label536;
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
        private long hedged(long time, boolean useWeakParameters) {
            double r = this.get$rate();
            double b =
              fabric.metrics.contracts.Bound._Impl.value(this.get$rate(),
                                                         this.get$base(), time);
            double x = this.get$metric().value();
            double v = this.get$metric().velocity(useWeakParameters);
            double n = this.get$metric().noise(useWeakParameters);
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
        public static long hedgedEstimate(fabric.metrics.Metric metric,
                                          double rate, double base, long time,
                                          boolean useWeakValues) {
            double r = rate;
            double b = fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                                  time);
            double x = metric.value(useWeakValues);
            double v = metric.velocity(useWeakValues);
            double n = metric.noise(useWeakValues);
            long hedgedResult =
              fabric.metrics.contracts.Bound._Impl.trueExpiry(
                                                     rate,
                                                     base,
                                                     metric.value(
                                                              useWeakValues),
                                                     time);
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
            refresh(
              !fabric.worker.transaction.TransactionManager.getInstance().inTxn(
                                                                            ));
            return this.get$expiry();
        }
        
        public void apply(fabric.metrics.contracts.Contract mc) {
            fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
              static_apply((fabric.metrics.contracts.enforcement.DirectPolicy)
                             this.$getProxy(), mc);
        }
        
        private static void static_apply(
          fabric.metrics.contracts.enforcement.DirectPolicy tmp,
          fabric.metrics.contracts.Contract mc) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (!tmp.get$activated()) tmp.activate();
                tmp.get$metric().addObserver(mc);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm550 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled553 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff551 = 1;
                    boolean $doBackoff552 = true;
                    boolean $retry547 = true;
                    $label545: for (boolean $commit546 = false; !$commit546; ) {
                        if ($backoffEnabled553) {
                            if ($doBackoff552) {
                                if ($backoff551 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff551);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e548) {
                                            
                                        }
                                    }
                                }
                                if ($backoff551 < 5000) $backoff551 *= 2;
                            }
                            $doBackoff552 = $backoff551 <= 32 || !$doBackoff552;
                        }
                        $commit546 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated()) tmp.activate();
                            tmp.get$metric().addObserver(mc);
                        }
                        catch (final fabric.worker.RetryException $e548) {
                            $commit546 = false;
                            continue $label545;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e548) {
                            $commit546 = false;
                            fabric.common.TransactionID $currentTid549 =
                              $tm550.getCurrentTid();
                            if ($e548.tid.isDescendantOf($currentTid549))
                                continue $label545;
                            if ($currentTid549.parent != null) {
                                $retry547 = false;
                                throw $e548;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e548) {
                            $commit546 = false;
                            if ($tm550.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid549 =
                              $tm550.getCurrentTid();
                            if ($e548.tid.isDescendantOf($currentTid549)) {
                                $retry547 = true;
                            }
                            else if ($currentTid549.parent != null) {
                                $retry547 = false;
                                throw $e548;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e548) {
                            $commit546 = false;
                            if ($tm550.checkForStaleObjects())
                                continue $label545;
                            $retry547 = false;
                            throw new fabric.worker.AbortException($e548);
                        }
                        finally {
                            if ($commit546) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e548) {
                                    $commit546 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e548) {
                                    $commit546 = false;
                                    fabric.common.TransactionID $currentTid549 =
                                      $tm550.getCurrentTid();
                                    if ($currentTid549 != null) {
                                        if ($e548.tid.equals($currentTid549) ||
                                              !$e548.tid.isDescendantOf(
                                                           $currentTid549)) {
                                            throw $e548;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit546 && $retry547) {
                                {  }
                                continue $label545;
                            }
                        }
                    }
                }
            }
        }
        
        public void unapply(fabric.metrics.contracts.Contract mc) {
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
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
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
                        fabric.worker.transaction.TransactionManager $tm559 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled562 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff560 = 1;
                        boolean $doBackoff561 = true;
                        boolean $retry556 = true;
                        $label554: for (boolean $commit555 = false; !$commit555;
                                        ) {
                            if ($backoffEnabled562) {
                                if ($doBackoff561) {
                                    if ($backoff560 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff560);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e557) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff560 < 5000) $backoff560 *= 2;
                                }
                                $doBackoff561 = $backoff560 <= 32 ||
                                                  !$doBackoff561;
                            }
                            $commit555 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e557) {
                                $commit555 = false;
                                continue $label554;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e557) {
                                $commit555 = false;
                                fabric.common.TransactionID $currentTid558 =
                                  $tm559.getCurrentTid();
                                if ($e557.tid.isDescendantOf($currentTid558))
                                    continue $label554;
                                if ($currentTid558.parent != null) {
                                    $retry556 = false;
                                    throw $e557;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e557) {
                                $commit555 = false;
                                if ($tm559.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid558 =
                                  $tm559.getCurrentTid();
                                if ($e557.tid.isDescendantOf($currentTid558)) {
                                    $retry556 = true;
                                }
                                else if ($currentTid558.parent != null) {
                                    $retry556 = false;
                                    throw $e557;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e557) {
                                $commit555 = false;
                                if ($tm559.checkForStaleObjects())
                                    continue $label554;
                                $retry556 = false;
                                throw new fabric.worker.AbortException($e557);
                            }
                            finally {
                                if ($commit555) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e557) {
                                        $commit555 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e557) {
                                        $commit555 = false;
                                        fabric.common.TransactionID
                                          $currentTid558 =
                                          $tm559.getCurrentTid();
                                        if ($currentTid558 != null) {
                                            if ($e557.tid.equals(
                                                            $currentTid558) ||
                                                  !$e557.tid.
                                                  isDescendantOf(
                                                    $currentTid558)) {
                                                throw $e557;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit555 && $retry556) {
                                    {  }
                                    continue $label554;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -25, 73, -45, -36, 63,
    -28, -65, -41, 121, -41, 1, 94, -119, -82, 55, -93, 1, -109, 59, 43, -3, 20,
    -1, -118, -70, 29, -120, 113, 81, 21, 96, 74 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525034484000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAL1ZC2wUxxmeOz/PGGwM5mGwAXNB4nUnaEuUOE0DFwMHBzg2ENUQjr29OXvD3u55d86coU5D+oBSFVXhFVSCEpW2KaXQh6KqSVEimqYg0lRpq5KoTYqqRAmiqEVtk6oK0P+fnbvdW+8dthQVMfOPd+b/539+M7t3+jqpMg3SnpISihpiQxlqhlZKiWisSzJMmoyokmluhKdxeVxl9MgH30u2+Yk/RuplSdM1RZbUuGYyMiH2qDQohTXKwpu6ox1bSEBGxtWS2c+If8uKnEFmZ3R1qE/VmdhkhPzDC8OHjm5r/EkFaeglDYrWwySmyBFdYzTHekl9mqYT1DCXJ5M02UsmapQme6ihSKqyCxbqWi9pMpU+TWJZg5rd1NTVQVzYZGYz1OB75h+i+jqobWRlphugfqOlfpYpajimmKwjRqpTClWT5gB5jFTGSFVKlfpg4ZRY3oowlxheic9heZ0CahopSaZ5lsodipZkZJabo2BxcC0sANaaNGX9emGrSk2CB6TJUkmVtL5wDzMUrQ+WVulZ2IWRlpJCYVFtRpJ3SH00zsg097ouawpWBbhbkIWRZvcyLgli1uKKmSNa19ffd2C3tlrzEx/onKSyivrXAlObi6mbpqhBNZlajPULYkekKef2+QmBxc2uxdaan33hxgOL2l6+YK2Z4bFmQ+JRKrO4fDIx4Y2Zkfn3VKAatRndVDAViiznUe0SMx25DGT7lIJEnAzlJ1/ufvXzj5+i1/ykLkqqZV3NpiGrJsp6OqOo1FhFNWpIjCajJEC1ZITPR0kNjGOKRq2nG1Ipk7IoqVT5o2qd/w0uSoEIdFENjBUtpefHGYn183EuQwipgUZ88H8rIeFnYTwBxl9mZHu4X0/TcELN0p2Q3mFoVDLk/jDUraHIYdOQw0ZWYwosEo8gi4CYYUh1ZkgyM8MUtjVkmqYaCz+oGODALl1V5KEQ6Jb5P+yRQzsbd/p8EIJZsp6kCcmEeIrcWtGlQvms1tUkNeKyeuBclEw6d4znVwBrwoS85h70QU7MdKOJk/dQdkXnjTPxS1ZuIq9wMCNLLMVDQvFQQfGQQ/GQU3HQtR5LMQTgFgJwO+3LhSInoj/gGVdt8tIsiK8H8fdmVImBrHSO+Hzc1smcn6caJMoOACDAmPr5PY+s2b6vvQJyPLOzEsMOS4PuirNxKgojCcooLjfs/eDDs0eGdbv2GAmOgISRnFjS7W7HGbpMkwCZtvgFs6Xn4+eGg36EowB6SIJcBthpc+9RVNodeZhEb1TFyDj0gaTiVB7b6li/oe+0n/CEmIBdk5Ub6CyXghxhP9uTefrN169+ip89eTBucKB2D2UdDgBAYQ281Cfavt9oUArr3n6q6+Dh63u3cMfDirleGwaxj0DhS1DxuvGVCwNv/eWdk3/w28FipDqTTUCG5LgtE2/DPx+0W9iwivEBUsDyiECQ2QUIyeDO82zdAExUSDlQ3Qxu0tJ6UkkpUkKlmCkfN9y15Pm/HWi0wq3CE8t5Bll0ZwH28+kryOOXtn3UxsX4ZDzMbP/ZyyyEnGRLXm4Y0hDqkdvzu9Zjv5aehswHfDOVXZRDFuH+IDyAS7kvFvN+iWvu09i1W96ayZ9XmyNPi5V47Nq52Bs+fbwlcv81CwYKuYgy5njAwGbJUSZLT6X/7W+v/pWf1PSSRn7iSxrbLAG+QRr0wpltRsTDGBlfNF98/lqHTUeh1ma668CxrbsKbPiBMa7GcZ2V+FbigCMa0Emt0OAPf61Fff/F2UkZ7CfnfIQP7uUsc3k/D7v53JF+HC5gJKCk01mGYecbLIQcFUCHfzbDSe+Cv3Wc4mSLVX/YLyvWawa0SbBHv6BbPfRaUU4v7O7PK1SJ6e8R9y5DSUPpDopbAt13aP/t0IFDVs5bV6m5I24zTh7rOsW3Gc/3ysEuc8rtwjlWvn92+MXnhvdaV42m4otBp5ZN//CPN18LPXXlosdhUp3UAQFoWc81gxcuCPoLD8+tH73n8LTEcdRrwzrccAq0qYRULBC0zWPDHu8NAc9qMoYyCNHJFYT6UWhACGsVdIpDKPgAQEAxhjjLKuF3JGtAYVW3jkZPXWdCmw7idgna76HrI5au2G0eqRRy9Qm6vUipAJzo3JSkl141CV1XqaR5qTYVhYehAbI1BAWt91CNeruxAocPM7wY4NtLUQDrV3c+uKozvnJ5ZOOGbjuQuRIB4Rlgx4L/qxY3wi8JOuzQzAGsvny9LxvVdafTHlt3Hg4IWD+tpV4AeO2cfOLQieSG7yzxC4RfC55nemaxSgep6lBnAlbiiBfMdfy1x4brK9da74nseK/PqsRZrp3dq7+/7vTFVfPkJ/2kooDLI961ipk6itG4zqDwqqhtLMLk2QV/Y4aRAWhzCamqtGjlcWcm2PnDM1QtztBawfItQQ+7Q2WfkhV26jxQyIsolz9c5kD9InY5RpZaQQ6KIAcLQQ46ghx03mmDturZYoOnQQuBwW8KenFsBiPLBUHPlzbYacVXy8ztw24PI7X5evaEmUFdSbps4QVzN7SlUDNLBa0vYYsn9j6MneKCwkYhaZxFq26WNtFnF3Ej3+xgGTt5bnwDbrQWasTz5uLj/V5hWgRtObwtxgW9b2xhQpYOQT8zKhsSXOrxMjacwO4ooKtBU/Dm2O+lOw/LOmidsPHbgj77iYQFJT0j6JOlTfLbohptu75bxq7nsHuGAYhZsSljHj/X0K+7Qad/CfrLMqHxONSQ5bygL47Kjl7bjrNl7PgxdqfgZOqnyT6a5Jwu/dGPRIP2DiGzXhB0awn9S99Y3OFpEJK2CLq+tFlVXFSVCwldVv68jJXcZT+FaFlWdppMSYtCclvLCwluTD4opoV5GhhTIXGWPPWXNsup4fkyc69gd65wqSqp9WzYEvBn4TFBnxib1siyR9Dddyz//F1iTsm7RESMcGEL3/9SGSN/i92rjFRJmYw6VBImAJt8y0DBW4K+/knABJf0G0FfGj1MvMZ3vFzGqrewewMueXkIL2UcD2A7qLCKkMU/EvTo2AKILEcE/eYdA2gbcKWMAX/F7k+A31mtvO4tBN+ESMhn0cX/GJvuyPJ3Qa+OrmSulpm7ht27cEVguvWZPJ+ujfwLBr6/hxwT093f6LwsvAvUewgs/Lqg+tgsRBZN0P7SFrrKq0mUF9fY+uLgrTHX4MMyLvkYuxuIIgNZydIv4WUmvN74euFt55igj43NTGQZFnTnqALpqygzx0H/FiOTJXkgC3fVbgoAk1L6Yrq8gzPsh6tuvfMWi9+pZnh8RhY/f8iRV+jJ99Yuai7xCXnaiB+kBN+ZEw21U09susy/fxZ+2gjESG0qq6rO7zmOcXUGLgYKd1vA+rqT4WbVMRIczUsYI+Mcf6HBvlpLwnhGppWSwKxvYnzs5GmEE7CYh/HfmXDkXDcJssRah39N5gFs8eg+4ia1ZA38Me/0P6f+p7p24xX+MRRPo/ejv//z59596fLQZd+2r525+9u+gx0Lb06+vf+F1n0DDzVvX/M/+Cv7nWQcAAA=";
}
