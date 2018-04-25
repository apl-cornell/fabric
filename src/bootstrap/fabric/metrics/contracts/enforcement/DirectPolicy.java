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
    
    public static final byte[] $classHash = new byte[] { -66, -3, -60, 114, -38,
    67, 3, 105, -98, 108, -77, 52, 39, -55, -103, -49, 4, -19, 99, -95, -102,
    -20, 109, -60, 65, 70, -100, -88, 54, 73, -39, -63 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524505527000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
