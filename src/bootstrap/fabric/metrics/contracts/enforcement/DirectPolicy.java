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
    
    public void activate(fabric.worker.metrics.StatsMap weakStats);
    
    public void refresh(fabric.worker.metrics.StatsMap weakStats);
    
    public long expiry(fabric.worker.metrics.StatsMap weakStats);
    
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
        
        public void activate(fabric.worker.metrics.StatsMap arg1) {
            ((fabric.metrics.contracts.enforcement.DirectPolicy) fetch()).
              activate(arg1);
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
        
        public long expiry(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      fetch()).expiry(arg1);
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
        
        public void activate(fabric.worker.metrics.StatsMap weakStats) {
            fabric.metrics.contracts.enforcement.DirectPolicy._Impl.
              static_activate(
                (fabric.metrics.contracts.enforcement.DirectPolicy)
                  this.$getProxy(), weakStats);
        }
        
        private static void static_activate(
          fabric.metrics.contracts.enforcement.DirectPolicy tmp,
          fabric.worker.metrics.StatsMap weakStats) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.get$activated()) return;
                tmp.refresh(weakStats);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm509 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled512 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff510 = 1;
                    boolean $doBackoff511 = true;
                    boolean $retry506 = true;
                    $label504: for (boolean $commit505 = false; !$commit505; ) {
                        if ($backoffEnabled512) {
                            if ($doBackoff511) {
                                if ($backoff510 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff510);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e507) {
                                            
                                        }
                                    }
                                }
                                if ($backoff510 < 5000) $backoff510 *= 2;
                            }
                            $doBackoff511 = $backoff510 <= 32 || !$doBackoff511;
                        }
                        $commit505 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$activated()) return;
                            tmp.refresh(weakStats);
                        }
                        catch (final fabric.worker.RetryException $e507) {
                            $commit505 = false;
                            continue $label504;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e507) {
                            $commit505 = false;
                            fabric.common.TransactionID $currentTid508 =
                              $tm509.getCurrentTid();
                            if ($e507.tid.isDescendantOf($currentTid508))
                                continue $label504;
                            if ($currentTid508.parent != null) {
                                $retry506 = false;
                                throw $e507;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e507) {
                            $commit505 = false;
                            if ($tm509.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid508 =
                              $tm509.getCurrentTid();
                            if ($e507.tid.isDescendantOf($currentTid508)) {
                                $retry506 = true;
                            }
                            else if ($currentTid508.parent != null) {
                                $retry506 = false;
                                throw $e507;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e507) {
                            $commit505 = false;
                            if ($tm509.checkForStaleObjects())
                                continue $label504;
                            $retry506 = false;
                            throw new fabric.worker.AbortException($e507);
                        }
                        finally {
                            if ($commit505) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e507) {
                                    $commit505 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e507) {
                                    $commit505 = false;
                                    fabric.common.TransactionID $currentTid508 =
                                      $tm509.getCurrentTid();
                                    if ($currentTid508 != null) {
                                        if ($e507.tid.equals($currentTid508) ||
                                              !$e507.tid.isDescendantOf(
                                                           $currentTid508)) {
                                            throw $e507;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit505 && $retry506) {
                                {  }
                                continue $label504;
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
                    fabric.worker.transaction.TransactionManager $tm518 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled521 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff519 = 1;
                    boolean $doBackoff520 = true;
                    boolean $retry515 = true;
                    $label513: for (boolean $commit514 = false; !$commit514; ) {
                        if ($backoffEnabled521) {
                            if ($doBackoff520) {
                                if ($backoff519 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff519);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e516) {
                                            
                                        }
                                    }
                                }
                                if ($backoff519 < 5000) $backoff519 *= 2;
                            }
                            $doBackoff520 = $backoff519 <= 32 || !$doBackoff520;
                        }
                        $commit514 = true;
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
                        catch (final fabric.worker.RetryException $e516) {
                            $commit514 = false;
                            continue $label513;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e516) {
                            $commit514 = false;
                            fabric.common.TransactionID $currentTid517 =
                              $tm518.getCurrentTid();
                            if ($e516.tid.isDescendantOf($currentTid517))
                                continue $label513;
                            if ($currentTid517.parent != null) {
                                $retry515 = false;
                                throw $e516;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e516) {
                            $commit514 = false;
                            if ($tm518.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid517 =
                              $tm518.getCurrentTid();
                            if ($e516.tid.isDescendantOf($currentTid517)) {
                                $retry515 = true;
                            }
                            else if ($currentTid517.parent != null) {
                                $retry515 = false;
                                throw $e516;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e516) {
                            $commit514 = false;
                            if ($tm518.checkForStaleObjects())
                                continue $label513;
                            $retry515 = false;
                            throw new fabric.worker.AbortException($e516);
                        }
                        finally {
                            if ($commit514) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e516) {
                                    $commit514 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e516) {
                                    $commit514 = false;
                                    fabric.common.TransactionID $currentTid517 =
                                      $tm518.getCurrentTid();
                                    if ($currentTid517 != null) {
                                        if ($e516.tid.equals($currentTid517) ||
                                              !$e516.tid.isDescendantOf(
                                                           $currentTid517)) {
                                            throw $e516;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit514 && $retry515) {
                                {  }
                                continue $label513;
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
            double v = weakStats.containsKey(this.get$metric())
              ? weakStats.getVelocity(this.get$metric())
              : this.get$metric().velocity(weakStats);
            double n = weakStats.containsKey(this.get$metric())
              ? weakStats.getNoise(this.get$metric())
              : this.get$metric().noise(weakStats);
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
            double x = weakStats.containsKey(metric)
              ? weakStats.getValue(metric)
              : metric.value(weakStats);
            double v = weakStats.containsKey(metric)
              ? weakStats.getVelocity(metric)
              : metric.velocity(weakStats);
            double n = weakStats.containsKey(metric)
              ? weakStats.getNoise(metric)
              : metric.noise(weakStats);
            long hedgedResult =
              fabric.metrics.contracts.Bound._Impl.trueExpiry(rate, base, x,
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
        
        public long expiry(fabric.worker.metrics.StatsMap weakStats) {
            refresh(weakStats);
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
                if (!tmp.get$activated())
                    tmp.activate(fabric.worker.metrics.StatsMap.emptyStats());
                tmp.get$metric().addObserver(mc);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm527 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled530 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff528 = 1;
                    boolean $doBackoff529 = true;
                    boolean $retry524 = true;
                    $label522: for (boolean $commit523 = false; !$commit523; ) {
                        if ($backoffEnabled530) {
                            if ($doBackoff529) {
                                if ($backoff528 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff528);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e525) {
                                            
                                        }
                                    }
                                }
                                if ($backoff528 < 5000) $backoff528 *= 2;
                            }
                            $doBackoff529 = $backoff528 <= 32 || !$doBackoff529;
                        }
                        $commit523 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated())
                                tmp.activate(
                                      fabric.worker.metrics.StatsMap.emptyStats(
                                                                       ));
                            tmp.get$metric().addObserver(mc);
                        }
                        catch (final fabric.worker.RetryException $e525) {
                            $commit523 = false;
                            continue $label522;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e525) {
                            $commit523 = false;
                            fabric.common.TransactionID $currentTid526 =
                              $tm527.getCurrentTid();
                            if ($e525.tid.isDescendantOf($currentTid526))
                                continue $label522;
                            if ($currentTid526.parent != null) {
                                $retry524 = false;
                                throw $e525;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e525) {
                            $commit523 = false;
                            if ($tm527.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid526 =
                              $tm527.getCurrentTid();
                            if ($e525.tid.isDescendantOf($currentTid526)) {
                                $retry524 = true;
                            }
                            else if ($currentTid526.parent != null) {
                                $retry524 = false;
                                throw $e525;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e525) {
                            $commit523 = false;
                            if ($tm527.checkForStaleObjects())
                                continue $label522;
                            $retry524 = false;
                            throw new fabric.worker.AbortException($e525);
                        }
                        finally {
                            if ($commit523) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e525) {
                                    $commit523 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e525) {
                                    $commit523 = false;
                                    fabric.common.TransactionID $currentTid526 =
                                      $tm527.getCurrentTid();
                                    if ($currentTid526 != null) {
                                        if ($e525.tid.equals($currentTid526) ||
                                              !$e525.tid.isDescendantOf(
                                                           $currentTid526)) {
                                            throw $e525;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit523 && $retry524) {
                                {  }
                                continue $label522;
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
                        fabric.worker.transaction.TransactionManager $tm536 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled539 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff537 = 1;
                        boolean $doBackoff538 = true;
                        boolean $retry533 = true;
                        $label531: for (boolean $commit532 = false; !$commit532;
                                        ) {
                            if ($backoffEnabled539) {
                                if ($doBackoff538) {
                                    if ($backoff537 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff537);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e534) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff537 < 5000) $backoff537 *= 2;
                                }
                                $doBackoff538 = $backoff537 <= 32 ||
                                                  !$doBackoff538;
                            }
                            $commit532 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e534) {
                                $commit532 = false;
                                continue $label531;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e534) {
                                $commit532 = false;
                                fabric.common.TransactionID $currentTid535 =
                                  $tm536.getCurrentTid();
                                if ($e534.tid.isDescendantOf($currentTid535))
                                    continue $label531;
                                if ($currentTid535.parent != null) {
                                    $retry533 = false;
                                    throw $e534;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e534) {
                                $commit532 = false;
                                if ($tm536.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid535 =
                                  $tm536.getCurrentTid();
                                if ($e534.tid.isDescendantOf($currentTid535)) {
                                    $retry533 = true;
                                }
                                else if ($currentTid535.parent != null) {
                                    $retry533 = false;
                                    throw $e534;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e534) {
                                $commit532 = false;
                                if ($tm536.checkForStaleObjects())
                                    continue $label531;
                                $retry533 = false;
                                throw new fabric.worker.AbortException($e534);
                            }
                            finally {
                                if ($commit532) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e534) {
                                        $commit532 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e534) {
                                        $commit532 = false;
                                        fabric.common.TransactionID
                                          $currentTid535 =
                                          $tm536.getCurrentTid();
                                        if ($currentTid535 != null) {
                                            if ($e534.tid.equals(
                                                            $currentTid535) ||
                                                  !$e534.tid.
                                                  isDescendantOf(
                                                    $currentTid535)) {
                                                throw $e534;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit532 && $retry533) {
                                    {  }
                                    continue $label531;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -88, 37, -62, 24, -106,
    65, 9, 28, -28, 67, -41, 15, -38, -113, 75, -29, -43, -75, 63, 113, 105, 42,
    -43, 105, -21, 14, 21, 76, -8, 66, -54, -27 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527629368000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
