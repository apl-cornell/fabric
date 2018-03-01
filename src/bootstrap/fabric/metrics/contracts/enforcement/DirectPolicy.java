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
    
    public void refresh();
    
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
        
        public void refresh() {
            ((fabric.metrics.contracts.enforcement.DirectPolicy) fetch()).
              refresh();
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
                tmp.refresh();
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm170 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled173 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff171 = 1;
                    boolean $doBackoff172 = true;
                    boolean $retry167 = true;
                    $label165: for (boolean $commit166 = false; !$commit166; ) {
                        if ($backoffEnabled173) {
                            if ($doBackoff172) {
                                if ($backoff171 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff171);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e168) {
                                            
                                        }
                                    }
                                }
                                if ($backoff171 < 5000) $backoff171 *= 2;
                            }
                            $doBackoff172 = $backoff171 <= 32 || !$doBackoff172;
                        }
                        $commit166 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$activated()) return;
                            tmp.refresh();
                        }
                        catch (final fabric.worker.RetryException $e168) {
                            $commit166 = false;
                            continue $label165;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e168) {
                            $commit166 = false;
                            fabric.common.TransactionID $currentTid169 =
                              $tm170.getCurrentTid();
                            if ($e168.tid.isDescendantOf($currentTid169))
                                continue $label165;
                            if ($currentTid169.parent != null) {
                                $retry167 = false;
                                throw $e168;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e168) {
                            $commit166 = false;
                            if ($tm170.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid169 =
                              $tm170.getCurrentTid();
                            if ($e168.tid.isDescendantOf($currentTid169)) {
                                $retry167 = true;
                            }
                            else if ($currentTid169.parent != null) {
                                $retry167 = false;
                                throw $e168;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e168) {
                            $commit166 = false;
                            if ($tm170.checkForStaleObjects())
                                continue $label165;
                            $retry167 = false;
                            throw new fabric.worker.AbortException($e168);
                        }
                        finally {
                            if ($commit166) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e168) {
                                    $commit166 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e168) {
                                    $commit166 = false;
                                    fabric.common.TransactionID $currentTid169 =
                                      $tm170.getCurrentTid();
                                    if ($currentTid169 != null) {
                                        if ($e168.tid.equals($currentTid169) ||
                                              !$e168.tid.isDescendantOf(
                                                           $currentTid169)) {
                                            throw $e168;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit166 && $retry167) {
                                {  }
                                continue $label165;
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
                    fabric.worker.transaction.TransactionManager $tm179 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled182 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff180 = 1;
                    boolean $doBackoff181 = true;
                    boolean $retry176 = true;
                    $label174: for (boolean $commit175 = false; !$commit175; ) {
                        if ($backoffEnabled182) {
                            if ($doBackoff181) {
                                if ($backoff180 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff180);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e177) {
                                            
                                        }
                                    }
                                }
                                if ($backoff180 < 5000) $backoff180 *= 2;
                            }
                            $doBackoff181 = $backoff180 <= 32 || !$doBackoff181;
                        }
                        $commit175 = true;
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
                        catch (final fabric.worker.RetryException $e177) {
                            $commit175 = false;
                            continue $label174;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e177) {
                            $commit175 = false;
                            fabric.common.TransactionID $currentTid178 =
                              $tm179.getCurrentTid();
                            if ($e177.tid.isDescendantOf($currentTid178))
                                continue $label174;
                            if ($currentTid178.parent != null) {
                                $retry176 = false;
                                throw $e177;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e177) {
                            $commit175 = false;
                            if ($tm179.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid178 =
                              $tm179.getCurrentTid();
                            if ($e177.tid.isDescendantOf($currentTid178)) {
                                $retry176 = true;
                            }
                            else if ($currentTid178.parent != null) {
                                $retry176 = false;
                                throw $e177;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e177) {
                            $commit175 = false;
                            if ($tm179.checkForStaleObjects())
                                continue $label174;
                            $retry176 = false;
                            throw new fabric.worker.AbortException($e177);
                        }
                        finally {
                            if ($commit175) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e177) {
                                    $commit175 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e177) {
                                    $commit175 = false;
                                    fabric.common.TransactionID $currentTid178 =
                                      $tm179.getCurrentTid();
                                    if ($currentTid178 != null) {
                                        if ($e177.tid.equals($currentTid178) ||
                                              !$e177.tid.isDescendantOf(
                                                           $currentTid178)) {
                                            throw $e177;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit175 && $retry176) {
                                {  }
                                continue $label174;
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
            double v = this.get$metric().velocity();
            double n = this.get$metric().noise();
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
            refresh();
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
                    fabric.worker.transaction.TransactionManager $tm188 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled191 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff189 = 1;
                    boolean $doBackoff190 = true;
                    boolean $retry185 = true;
                    $label183: for (boolean $commit184 = false; !$commit184; ) {
                        if ($backoffEnabled191) {
                            if ($doBackoff190) {
                                if ($backoff189 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff189);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e186) {
                                            
                                        }
                                    }
                                }
                                if ($backoff189 < 5000) $backoff189 *= 2;
                            }
                            $doBackoff190 = $backoff189 <= 32 || !$doBackoff190;
                        }
                        $commit184 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated()) tmp.activate();
                            tmp.get$metric().addObserver(mc);
                        }
                        catch (final fabric.worker.RetryException $e186) {
                            $commit184 = false;
                            continue $label183;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e186) {
                            $commit184 = false;
                            fabric.common.TransactionID $currentTid187 =
                              $tm188.getCurrentTid();
                            if ($e186.tid.isDescendantOf($currentTid187))
                                continue $label183;
                            if ($currentTid187.parent != null) {
                                $retry185 = false;
                                throw $e186;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e186) {
                            $commit184 = false;
                            if ($tm188.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid187 =
                              $tm188.getCurrentTid();
                            if ($e186.tid.isDescendantOf($currentTid187)) {
                                $retry185 = true;
                            }
                            else if ($currentTid187.parent != null) {
                                $retry185 = false;
                                throw $e186;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e186) {
                            $commit184 = false;
                            if ($tm188.checkForStaleObjects())
                                continue $label183;
                            $retry185 = false;
                            throw new fabric.worker.AbortException($e186);
                        }
                        finally {
                            if ($commit184) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e186) {
                                    $commit184 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e186) {
                                    $commit184 = false;
                                    fabric.common.TransactionID $currentTid187 =
                                      $tm188.getCurrentTid();
                                    if ($currentTid187 != null) {
                                        if ($e186.tid.equals($currentTid187) ||
                                              !$e186.tid.isDescendantOf(
                                                           $currentTid187)) {
                                            throw $e186;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit184 && $retry185) {
                                {  }
                                continue $label183;
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
                        fabric.worker.transaction.TransactionManager $tm197 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled200 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff198 = 1;
                        boolean $doBackoff199 = true;
                        boolean $retry194 = true;
                        $label192: for (boolean $commit193 = false; !$commit193;
                                        ) {
                            if ($backoffEnabled200) {
                                if ($doBackoff199) {
                                    if ($backoff198 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff198);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e195) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff198 < 5000) $backoff198 *= 2;
                                }
                                $doBackoff199 = $backoff198 <= 32 ||
                                                  !$doBackoff199;
                            }
                            $commit193 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e195) {
                                $commit193 = false;
                                continue $label192;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e195) {
                                $commit193 = false;
                                fabric.common.TransactionID $currentTid196 =
                                  $tm197.getCurrentTid();
                                if ($e195.tid.isDescendantOf($currentTid196))
                                    continue $label192;
                                if ($currentTid196.parent != null) {
                                    $retry194 = false;
                                    throw $e195;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e195) {
                                $commit193 = false;
                                if ($tm197.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid196 =
                                  $tm197.getCurrentTid();
                                if ($e195.tid.isDescendantOf($currentTid196)) {
                                    $retry194 = true;
                                }
                                else if ($currentTid196.parent != null) {
                                    $retry194 = false;
                                    throw $e195;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e195) {
                                $commit193 = false;
                                if ($tm197.checkForStaleObjects())
                                    continue $label192;
                                $retry194 = false;
                                throw new fabric.worker.AbortException($e195);
                            }
                            finally {
                                if ($commit193) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e195) {
                                        $commit193 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e195) {
                                        $commit193 = false;
                                        fabric.common.TransactionID
                                          $currentTid196 =
                                          $tm197.getCurrentTid();
                                        if ($currentTid196 != null) {
                                            if ($e195.tid.equals(
                                                            $currentTid196) ||
                                                  !$e195.tid.
                                                  isDescendantOf(
                                                    $currentTid196)) {
                                                throw $e195;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit193 && $retry194) {
                                    {  }
                                    continue $label192;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -121, 20, -63, 50, -79,
    38, -47, -45, 42, -74, -89, -120, -71, -12, 104, -63, -55, -37, -111, 62,
    47, -101, 99, 127, -60, -37, -66, -16, -56, -86, -4, 122 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1519938369000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
