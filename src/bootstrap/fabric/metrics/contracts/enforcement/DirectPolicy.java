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
                            if (tmp.get$activated()) return;
                            tmp.refresh(true);
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
                    fabric.worker.transaction.TransactionManager $tm559 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled562 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff560 = 1;
                    boolean $doBackoff561 = true;
                    boolean $retry556 = true;
                    $label554: for (boolean $commit555 = false; !$commit555; ) {
                        if ($backoffEnabled562) {
                            if ($doBackoff561) {
                                if ($backoff560 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff560);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e557) {
                                            
                                        }
                                    }
                                }
                                if ($backoff560 < 5000) $backoff560 *= 2;
                            }
                            $doBackoff561 = $backoff560 <= 32 || !$doBackoff561;
                        }
                        $commit555 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated()) tmp.activate();
                            tmp.get$metric().addObserver(mc);
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
                                    fabric.common.TransactionID $currentTid558 =
                                      $tm559.getCurrentTid();
                                    if ($currentTid558 != null) {
                                        if ($e557.tid.equals($currentTid558) ||
                                              !$e557.tid.isDescendantOf(
                                                           $currentTid558)) {
                                            throw $e557;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
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
                        fabric.worker.transaction.TransactionManager $tm568 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled571 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff569 = 1;
                        boolean $doBackoff570 = true;
                        boolean $retry565 = true;
                        $label563: for (boolean $commit564 = false; !$commit564;
                                        ) {
                            if ($backoffEnabled571) {
                                if ($doBackoff570) {
                                    if ($backoff569 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff569);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e566) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff569 < 5000) $backoff569 *= 2;
                                }
                                $doBackoff570 = $backoff569 <= 32 ||
                                                  !$doBackoff570;
                            }
                            $commit564 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e566) {
                                $commit564 = false;
                                continue $label563;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e566) {
                                $commit564 = false;
                                fabric.common.TransactionID $currentTid567 =
                                  $tm568.getCurrentTid();
                                if ($e566.tid.isDescendantOf($currentTid567))
                                    continue $label563;
                                if ($currentTid567.parent != null) {
                                    $retry565 = false;
                                    throw $e566;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e566) {
                                $commit564 = false;
                                if ($tm568.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid567 =
                                  $tm568.getCurrentTid();
                                if ($e566.tid.isDescendantOf($currentTid567)) {
                                    $retry565 = true;
                                }
                                else if ($currentTid567.parent != null) {
                                    $retry565 = false;
                                    throw $e566;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e566) {
                                $commit564 = false;
                                if ($tm568.checkForStaleObjects())
                                    continue $label563;
                                $retry565 = false;
                                throw new fabric.worker.AbortException($e566);
                            }
                            finally {
                                if ($commit564) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e566) {
                                        $commit564 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e566) {
                                        $commit564 = false;
                                        fabric.common.TransactionID
                                          $currentTid567 =
                                          $tm568.getCurrentTid();
                                        if ($currentTid567 != null) {
                                            if ($e566.tid.equals(
                                                            $currentTid567) ||
                                                  !$e566.tid.
                                                  isDescendantOf(
                                                    $currentTid567)) {
                                                throw $e566;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit564 && $retry565) {
                                    {  }
                                    continue $label563;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -17, 52, -74, -17, 105,
    38, -67, 76, 77, -74, -61, 9, -116, -71, 66, -34, -121, -35, -120, 111, -41,
    96, -29, -66, -126, 1, 109, -4, 69, -90, 21, 18 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526344812000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
