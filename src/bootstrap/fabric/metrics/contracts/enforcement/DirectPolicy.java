package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.metrics.contracts.Bound;
import fabric.metrics.contracts.Contract;
import fabric.worker.metrics.StatsMap;
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
    
    public void refresh(fabric.worker.metrics.StatsMap weakStats);
    
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
        
        public void refresh(fabric.worker.metrics.StatsMap arg1) {
            ((fabric.metrics.contracts.enforcement.DirectPolicy) fetch()).
              refresh(arg1);
        }
        
        public static long hedgedEstimate(fabric.metrics.Metric arg1,
                                          double arg2, double arg3, long arg4,
                                          fabric.worker.metrics.StatsMap arg5) {
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
                tmp.refresh(fabric.worker.metrics.StatsMap.emptyStats());
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm478 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled481 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff479 = 1;
                    boolean $doBackoff480 = true;
                    boolean $retry475 = true;
                    $label473: for (boolean $commit474 = false; !$commit474; ) {
                        if ($backoffEnabled481) {
                            if ($doBackoff480) {
                                if ($backoff479 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff479);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e476) {
                                            
                                        }
                                    }
                                }
                                if ($backoff479 < 5000) $backoff479 *= 2;
                            }
                            $doBackoff480 = $backoff479 <= 32 || !$doBackoff480;
                        }
                        $commit474 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$activated()) return;
                            tmp.refresh(
                                  fabric.worker.metrics.StatsMap.emptyStats());
                        }
                        catch (final fabric.worker.RetryException $e476) {
                            $commit474 = false;
                            continue $label473;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e476) {
                            $commit474 = false;
                            fabric.common.TransactionID $currentTid477 =
                              $tm478.getCurrentTid();
                            if ($e476.tid.isDescendantOf($currentTid477))
                                continue $label473;
                            if ($currentTid477.parent != null) {
                                $retry475 = false;
                                throw $e476;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e476) {
                            $commit474 = false;
                            if ($tm478.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid477 =
                              $tm478.getCurrentTid();
                            if ($e476.tid.isDescendantOf($currentTid477)) {
                                $retry475 = true;
                            }
                            else if ($currentTid477.parent != null) {
                                $retry475 = false;
                                throw $e476;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e476) {
                            $commit474 = false;
                            if ($tm478.checkForStaleObjects())
                                continue $label473;
                            $retry475 = false;
                            throw new fabric.worker.AbortException($e476);
                        }
                        finally {
                            if ($commit474) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e476) {
                                    $commit474 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e476) {
                                    $commit474 = false;
                                    fabric.common.TransactionID $currentTid477 =
                                      $tm478.getCurrentTid();
                                    if ($currentTid477 != null) {
                                        if ($e476.tid.equals($currentTid477) ||
                                              !$e476.tid.isDescendantOf(
                                                           $currentTid477)) {
                                            throw $e476;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit474 && $retry475) {
                                {  }
                                continue $label473;
                            }
                        }
                    }
                }
            }
        }
        
        public void refresh(fabric.worker.metrics.StatsMap weakStats) {
            fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
              static_refresh((fabric.metrics.contracts.enforcement.DirectPolicy)
                               this.$getProxy(), weakStats);
        }
        
        private static void static_refresh(
          fabric.metrics.contracts.enforcement.DirectPolicy tmp,
          fabric.worker.metrics.StatsMap weakStats) {
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
                         tmp.fetch()).hedged(currentTime, weakStats);
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
                    fabric.worker.transaction.TransactionManager $tm487 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled490 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff488 = 1;
                    boolean $doBackoff489 = true;
                    boolean $retry484 = true;
                    $label482: for (boolean $commit483 = false; !$commit483; ) {
                        if ($backoffEnabled490) {
                            if ($doBackoff489) {
                                if ($backoff488 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff488);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e485) {
                                            
                                        }
                                    }
                                }
                                if ($backoff488 < 5000) $backoff488 *= 2;
                            }
                            $doBackoff489 = $backoff488 <= 32 || !$doBackoff489;
                        }
                        $commit483 = true;
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
                                                         weakStats);
                                if (tmp.get$expiry() <= trueTime) {
                                    hedgedTime =
                                      java.lang.Math.max(tmp.get$expiry(),
                                                         hedgedTime);
                                }
                                tmp.set$expiry((long) hedgedTime);
                            }
                            tmp.set$activated(true);
                        }
                        catch (final fabric.worker.RetryException $e485) {
                            $commit483 = false;
                            continue $label482;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e485) {
                            $commit483 = false;
                            fabric.common.TransactionID $currentTid486 =
                              $tm487.getCurrentTid();
                            if ($e485.tid.isDescendantOf($currentTid486))
                                continue $label482;
                            if ($currentTid486.parent != null) {
                                $retry484 = false;
                                throw $e485;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e485) {
                            $commit483 = false;
                            if ($tm487.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid486 =
                              $tm487.getCurrentTid();
                            if ($e485.tid.isDescendantOf($currentTid486)) {
                                $retry484 = true;
                            }
                            else if ($currentTid486.parent != null) {
                                $retry484 = false;
                                throw $e485;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e485) {
                            $commit483 = false;
                            if ($tm487.checkForStaleObjects())
                                continue $label482;
                            $retry484 = false;
                            throw new fabric.worker.AbortException($e485);
                        }
                        finally {
                            if ($commit483) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e485) {
                                    $commit483 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e485) {
                                    $commit483 = false;
                                    fabric.common.TransactionID $currentTid486 =
                                      $tm487.getCurrentTid();
                                    if ($currentTid486 != null) {
                                        if ($e485.tid.equals($currentTid486) ||
                                              !$e485.tid.isDescendantOf(
                                                           $currentTid486)) {
                                            throw $e485;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit483 && $retry484) {
                                {  }
                                continue $label482;
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
        private long hedged(long time,
                            fabric.worker.metrics.StatsMap weakStats) {
            double r = this.get$rate();
            double b =
              fabric.metrics.contracts.Bound._Impl.value(this.get$rate(),
                                                         this.get$base(), time);
            double x = this.get$metric().value();
            double v = this.get$metric().velocity(weakStats);
            double n = this.get$metric().noise(weakStats);
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
        public static long hedgedEstimate(
          fabric.metrics.Metric metric, double rate, double base, long time,
          fabric.worker.metrics.StatsMap weakStats) {
            double r = rate;
            double b = fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                                  time);
            double x = metric.value(weakStats);
            double v = metric.velocity(weakStats);
            double n = metric.noise(weakStats);
            long hedgedResult =
              fabric.metrics.contracts.Bound._Impl.trueExpiry(
                                                     rate, base,
                                                     metric.value(weakStats),
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
            refresh(fabric.worker.metrics.StatsMap.emptyStats());
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
                    fabric.worker.transaction.TransactionManager $tm496 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled499 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff497 = 1;
                    boolean $doBackoff498 = true;
                    boolean $retry493 = true;
                    $label491: for (boolean $commit492 = false; !$commit492; ) {
                        if ($backoffEnabled499) {
                            if ($doBackoff498) {
                                if ($backoff497 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff497);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e494) {
                                            
                                        }
                                    }
                                }
                                if ($backoff497 < 5000) $backoff497 *= 2;
                            }
                            $doBackoff498 = $backoff497 <= 32 || !$doBackoff498;
                        }
                        $commit492 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated()) tmp.activate();
                            tmp.get$metric().addObserver(mc);
                        }
                        catch (final fabric.worker.RetryException $e494) {
                            $commit492 = false;
                            continue $label491;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e494) {
                            $commit492 = false;
                            fabric.common.TransactionID $currentTid495 =
                              $tm496.getCurrentTid();
                            if ($e494.tid.isDescendantOf($currentTid495))
                                continue $label491;
                            if ($currentTid495.parent != null) {
                                $retry493 = false;
                                throw $e494;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e494) {
                            $commit492 = false;
                            if ($tm496.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid495 =
                              $tm496.getCurrentTid();
                            if ($e494.tid.isDescendantOf($currentTid495)) {
                                $retry493 = true;
                            }
                            else if ($currentTid495.parent != null) {
                                $retry493 = false;
                                throw $e494;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e494) {
                            $commit492 = false;
                            if ($tm496.checkForStaleObjects())
                                continue $label491;
                            $retry493 = false;
                            throw new fabric.worker.AbortException($e494);
                        }
                        finally {
                            if ($commit492) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e494) {
                                    $commit492 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e494) {
                                    $commit492 = false;
                                    fabric.common.TransactionID $currentTid495 =
                                      $tm496.getCurrentTid();
                                    if ($currentTid495 != null) {
                                        if ($e494.tid.equals($currentTid495) ||
                                              !$e494.tid.isDescendantOf(
                                                           $currentTid495)) {
                                            throw $e494;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit492 && $retry493) {
                                {  }
                                continue $label491;
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
                        fabric.worker.transaction.TransactionManager $tm505 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled508 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff506 = 1;
                        boolean $doBackoff507 = true;
                        boolean $retry502 = true;
                        $label500: for (boolean $commit501 = false; !$commit501;
                                        ) {
                            if ($backoffEnabled508) {
                                if ($doBackoff507) {
                                    if ($backoff506 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff506);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e503) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff506 < 5000) $backoff506 *= 2;
                                }
                                $doBackoff507 = $backoff506 <= 32 ||
                                                  !$doBackoff507;
                            }
                            $commit501 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e503) {
                                $commit501 = false;
                                continue $label500;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e503) {
                                $commit501 = false;
                                fabric.common.TransactionID $currentTid504 =
                                  $tm505.getCurrentTid();
                                if ($e503.tid.isDescendantOf($currentTid504))
                                    continue $label500;
                                if ($currentTid504.parent != null) {
                                    $retry502 = false;
                                    throw $e503;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e503) {
                                $commit501 = false;
                                if ($tm505.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid504 =
                                  $tm505.getCurrentTid();
                                if ($e503.tid.isDescendantOf($currentTid504)) {
                                    $retry502 = true;
                                }
                                else if ($currentTid504.parent != null) {
                                    $retry502 = false;
                                    throw $e503;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e503) {
                                $commit501 = false;
                                if ($tm505.checkForStaleObjects())
                                    continue $label500;
                                $retry502 = false;
                                throw new fabric.worker.AbortException($e503);
                            }
                            finally {
                                if ($commit501) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e503) {
                                        $commit501 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e503) {
                                        $commit501 = false;
                                        fabric.common.TransactionID
                                          $currentTid504 =
                                          $tm505.getCurrentTid();
                                        if ($currentTid504 != null) {
                                            if ($e503.tid.equals(
                                                            $currentTid504) ||
                                                  !$e503.tid.
                                                  isDescendantOf(
                                                    $currentTid504)) {
                                                throw $e503;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit501 && $retry502) {
                                    {  }
                                    continue $label500;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -61, -27, 6, -55, -105,
    -92, 44, -10, 37, 125, -50, -20, -72, -120, 86, 59, 55, 28, 126, 121, -94,
    -119, -116, -58, -23, 96, -111, 80, 49, 41, 21, 26 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526753800000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
