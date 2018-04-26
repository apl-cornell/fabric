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
                    fabric.worker.transaction.TransactionManager $tm570 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled573 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff571 = 1;
                    boolean $doBackoff572 = true;
                    boolean $retry567 = true;
                    $label565: for (boolean $commit566 = false; !$commit566; ) {
                        if ($backoffEnabled573) {
                            if ($doBackoff572) {
                                if ($backoff571 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff571);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e568) {
                                            
                                        }
                                    }
                                }
                                if ($backoff571 < 5000) $backoff571 *= 2;
                            }
                            $doBackoff572 = $backoff571 <= 32 || !$doBackoff572;
                        }
                        $commit566 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$activated()) return;
                            tmp.refresh(true);
                        }
                        catch (final fabric.worker.RetryException $e568) {
                            $commit566 = false;
                            continue $label565;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e568) {
                            $commit566 = false;
                            fabric.common.TransactionID $currentTid569 =
                              $tm570.getCurrentTid();
                            if ($e568.tid.isDescendantOf($currentTid569))
                                continue $label565;
                            if ($currentTid569.parent != null) {
                                $retry567 = false;
                                throw $e568;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e568) {
                            $commit566 = false;
                            if ($tm570.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid569 =
                              $tm570.getCurrentTid();
                            if ($e568.tid.isDescendantOf($currentTid569)) {
                                $retry567 = true;
                            }
                            else if ($currentTid569.parent != null) {
                                $retry567 = false;
                                throw $e568;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e568) {
                            $commit566 = false;
                            if ($tm570.checkForStaleObjects())
                                continue $label565;
                            $retry567 = false;
                            throw new fabric.worker.AbortException($e568);
                        }
                        finally {
                            if ($commit566) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e568) {
                                    $commit566 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e568) {
                                    $commit566 = false;
                                    fabric.common.TransactionID $currentTid569 =
                                      $tm570.getCurrentTid();
                                    if ($currentTid569 != null) {
                                        if ($e568.tid.equals($currentTid569) ||
                                              !$e568.tid.isDescendantOf(
                                                           $currentTid569)) {
                                            throw $e568;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit566 && $retry567) {
                                {  }
                                continue $label565;
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
                    fabric.worker.transaction.TransactionManager $tm579 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled582 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff580 = 1;
                    boolean $doBackoff581 = true;
                    boolean $retry576 = true;
                    $label574: for (boolean $commit575 = false; !$commit575; ) {
                        if ($backoffEnabled582) {
                            if ($doBackoff581) {
                                if ($backoff580 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff580);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e577) {
                                            
                                        }
                                    }
                                }
                                if ($backoff580 < 5000) $backoff580 *= 2;
                            }
                            $doBackoff581 = $backoff580 <= 32 || !$doBackoff581;
                        }
                        $commit575 = true;
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
                        catch (final fabric.worker.RetryException $e577) {
                            $commit575 = false;
                            continue $label574;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e577) {
                            $commit575 = false;
                            fabric.common.TransactionID $currentTid578 =
                              $tm579.getCurrentTid();
                            if ($e577.tid.isDescendantOf($currentTid578))
                                continue $label574;
                            if ($currentTid578.parent != null) {
                                $retry576 = false;
                                throw $e577;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e577) {
                            $commit575 = false;
                            if ($tm579.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid578 =
                              $tm579.getCurrentTid();
                            if ($e577.tid.isDescendantOf($currentTid578)) {
                                $retry576 = true;
                            }
                            else if ($currentTid578.parent != null) {
                                $retry576 = false;
                                throw $e577;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e577) {
                            $commit575 = false;
                            if ($tm579.checkForStaleObjects())
                                continue $label574;
                            $retry576 = false;
                            throw new fabric.worker.AbortException($e577);
                        }
                        finally {
                            if ($commit575) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e577) {
                                    $commit575 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e577) {
                                    $commit575 = false;
                                    fabric.common.TransactionID $currentTid578 =
                                      $tm579.getCurrentTid();
                                    if ($currentTid578 != null) {
                                        if ($e577.tid.equals($currentTid578) ||
                                              !$e577.tid.isDescendantOf(
                                                           $currentTid578)) {
                                            throw $e577;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit575 && $retry576) {
                                {  }
                                continue $label574;
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
                    fabric.worker.transaction.TransactionManager $tm588 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled591 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff589 = 1;
                    boolean $doBackoff590 = true;
                    boolean $retry585 = true;
                    $label583: for (boolean $commit584 = false; !$commit584; ) {
                        if ($backoffEnabled591) {
                            if ($doBackoff590) {
                                if ($backoff589 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff589);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e586) {
                                            
                                        }
                                    }
                                }
                                if ($backoff589 < 5000) $backoff589 *= 2;
                            }
                            $doBackoff590 = $backoff589 <= 32 || !$doBackoff590;
                        }
                        $commit584 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated()) tmp.activate();
                            tmp.get$metric().addObserver(mc);
                        }
                        catch (final fabric.worker.RetryException $e586) {
                            $commit584 = false;
                            continue $label583;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e586) {
                            $commit584 = false;
                            fabric.common.TransactionID $currentTid587 =
                              $tm588.getCurrentTid();
                            if ($e586.tid.isDescendantOf($currentTid587))
                                continue $label583;
                            if ($currentTid587.parent != null) {
                                $retry585 = false;
                                throw $e586;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e586) {
                            $commit584 = false;
                            if ($tm588.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid587 =
                              $tm588.getCurrentTid();
                            if ($e586.tid.isDescendantOf($currentTid587)) {
                                $retry585 = true;
                            }
                            else if ($currentTid587.parent != null) {
                                $retry585 = false;
                                throw $e586;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e586) {
                            $commit584 = false;
                            if ($tm588.checkForStaleObjects())
                                continue $label583;
                            $retry585 = false;
                            throw new fabric.worker.AbortException($e586);
                        }
                        finally {
                            if ($commit584) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e586) {
                                    $commit584 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e586) {
                                    $commit584 = false;
                                    fabric.common.TransactionID $currentTid587 =
                                      $tm588.getCurrentTid();
                                    if ($currentTid587 != null) {
                                        if ($e586.tid.equals($currentTid587) ||
                                              !$e586.tid.isDescendantOf(
                                                           $currentTid587)) {
                                            throw $e586;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit584 && $retry585) {
                                {  }
                                continue $label583;
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
                        fabric.worker.transaction.TransactionManager $tm597 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled600 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff598 = 1;
                        boolean $doBackoff599 = true;
                        boolean $retry594 = true;
                        $label592: for (boolean $commit593 = false; !$commit593;
                                        ) {
                            if ($backoffEnabled600) {
                                if ($doBackoff599) {
                                    if ($backoff598 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff598);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e595) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff598 < 5000) $backoff598 *= 2;
                                }
                                $doBackoff599 = $backoff598 <= 32 ||
                                                  !$doBackoff599;
                            }
                            $commit593 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e595) {
                                $commit593 = false;
                                continue $label592;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e595) {
                                $commit593 = false;
                                fabric.common.TransactionID $currentTid596 =
                                  $tm597.getCurrentTid();
                                if ($e595.tid.isDescendantOf($currentTid596))
                                    continue $label592;
                                if ($currentTid596.parent != null) {
                                    $retry594 = false;
                                    throw $e595;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e595) {
                                $commit593 = false;
                                if ($tm597.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid596 =
                                  $tm597.getCurrentTid();
                                if ($e595.tid.isDescendantOf($currentTid596)) {
                                    $retry594 = true;
                                }
                                else if ($currentTid596.parent != null) {
                                    $retry594 = false;
                                    throw $e595;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e595) {
                                $commit593 = false;
                                if ($tm597.checkForStaleObjects())
                                    continue $label592;
                                $retry594 = false;
                                throw new fabric.worker.AbortException($e595);
                            }
                            finally {
                                if ($commit593) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e595) {
                                        $commit593 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e595) {
                                        $commit593 = false;
                                        fabric.common.TransactionID
                                          $currentTid596 =
                                          $tm597.getCurrentTid();
                                        if ($currentTid596 != null) {
                                            if ($e595.tid.equals(
                                                            $currentTid596) ||
                                                  !$e595.tid.
                                                  isDescendantOf(
                                                    $currentTid596)) {
                                                throw $e595;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit593 && $retry594) {
                                    {  }
                                    continue $label592;
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
    public static final long jlc$SourceLastModified$fabil = 1524675608000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
