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
                    fabric.worker.transaction.TransactionManager $tm493 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled496 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff494 = 1;
                    boolean $doBackoff495 = true;
                    boolean $retry490 = true;
                    $label488: for (boolean $commit489 = false; !$commit489; ) {
                        if ($backoffEnabled496) {
                            if ($doBackoff495) {
                                if ($backoff494 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff494);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e491) {
                                            
                                        }
                                    }
                                }
                                if ($backoff494 < 5000) $backoff494 *= 2;
                            }
                            $doBackoff495 = $backoff494 <= 32 || !$doBackoff495;
                        }
                        $commit489 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$activated()) return;
                            tmp.refresh();
                        }
                        catch (final fabric.worker.RetryException $e491) {
                            $commit489 = false;
                            continue $label488;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e491) {
                            $commit489 = false;
                            fabric.common.TransactionID $currentTid492 =
                              $tm493.getCurrentTid();
                            if ($e491.tid.isDescendantOf($currentTid492))
                                continue $label488;
                            if ($currentTid492.parent != null) {
                                $retry490 = false;
                                throw $e491;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e491) {
                            $commit489 = false;
                            if ($tm493.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid492 =
                              $tm493.getCurrentTid();
                            if ($e491.tid.isDescendantOf($currentTid492)) {
                                $retry490 = true;
                            }
                            else if ($currentTid492.parent != null) {
                                $retry490 = false;
                                throw $e491;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e491) {
                            $commit489 = false;
                            if ($tm493.checkForStaleObjects())
                                continue $label488;
                            $retry490 = false;
                            throw new fabric.worker.AbortException($e491);
                        }
                        finally {
                            if ($commit489) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e491) {
                                    $commit489 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e491) {
                                    $commit489 = false;
                                    fabric.common.TransactionID $currentTid492 =
                                      $tm493.getCurrentTid();
                                    if ($currentTid492 != null) {
                                        if ($e491.tid.equals($currentTid492) ||
                                              !$e491.tid.isDescendantOf(
                                                           $currentTid492)) {
                                            throw $e491;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit489 && $retry490) {
                                {  }
                                continue $label488;
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
                    fabric.worker.transaction.TransactionManager $tm502 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled505 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff503 = 1;
                    boolean $doBackoff504 = true;
                    boolean $retry499 = true;
                    $label497: for (boolean $commit498 = false; !$commit498; ) {
                        if ($backoffEnabled505) {
                            if ($doBackoff504) {
                                if ($backoff503 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff503);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e500) {
                                            
                                        }
                                    }
                                }
                                if ($backoff503 < 5000) $backoff503 *= 2;
                            }
                            $doBackoff504 = $backoff503 <= 32 || !$doBackoff504;
                        }
                        $commit498 = true;
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
                        catch (final fabric.worker.RetryException $e500) {
                            $commit498 = false;
                            continue $label497;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e500) {
                            $commit498 = false;
                            fabric.common.TransactionID $currentTid501 =
                              $tm502.getCurrentTid();
                            if ($e500.tid.isDescendantOf($currentTid501))
                                continue $label497;
                            if ($currentTid501.parent != null) {
                                $retry499 = false;
                                throw $e500;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e500) {
                            $commit498 = false;
                            if ($tm502.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid501 =
                              $tm502.getCurrentTid();
                            if ($e500.tid.isDescendantOf($currentTid501)) {
                                $retry499 = true;
                            }
                            else if ($currentTid501.parent != null) {
                                $retry499 = false;
                                throw $e500;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e500) {
                            $commit498 = false;
                            if ($tm502.checkForStaleObjects())
                                continue $label497;
                            $retry499 = false;
                            throw new fabric.worker.AbortException($e500);
                        }
                        finally {
                            if ($commit498) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e500) {
                                    $commit498 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e500) {
                                    $commit498 = false;
                                    fabric.common.TransactionID $currentTid501 =
                                      $tm502.getCurrentTid();
                                    if ($currentTid501 != null) {
                                        if ($e500.tid.equals($currentTid501) ||
                                              !$e500.tid.isDescendantOf(
                                                           $currentTid501)) {
                                            throw $e500;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit498 && $retry499) {
                                {  }
                                continue $label497;
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
                    fabric.worker.transaction.TransactionManager $tm511 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled514 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff512 = 1;
                    boolean $doBackoff513 = true;
                    boolean $retry508 = true;
                    $label506: for (boolean $commit507 = false; !$commit507; ) {
                        if ($backoffEnabled514) {
                            if ($doBackoff513) {
                                if ($backoff512 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff512);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e509) {
                                            
                                        }
                                    }
                                }
                                if ($backoff512 < 5000) $backoff512 *= 2;
                            }
                            $doBackoff513 = $backoff512 <= 32 || !$doBackoff513;
                        }
                        $commit507 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated()) tmp.activate();
                            tmp.get$metric().addObserver(mc);
                        }
                        catch (final fabric.worker.RetryException $e509) {
                            $commit507 = false;
                            continue $label506;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e509) {
                            $commit507 = false;
                            fabric.common.TransactionID $currentTid510 =
                              $tm511.getCurrentTid();
                            if ($e509.tid.isDescendantOf($currentTid510))
                                continue $label506;
                            if ($currentTid510.parent != null) {
                                $retry508 = false;
                                throw $e509;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e509) {
                            $commit507 = false;
                            if ($tm511.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid510 =
                              $tm511.getCurrentTid();
                            if ($e509.tid.isDescendantOf($currentTid510)) {
                                $retry508 = true;
                            }
                            else if ($currentTid510.parent != null) {
                                $retry508 = false;
                                throw $e509;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e509) {
                            $commit507 = false;
                            if ($tm511.checkForStaleObjects())
                                continue $label506;
                            $retry508 = false;
                            throw new fabric.worker.AbortException($e509);
                        }
                        finally {
                            if ($commit507) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e509) {
                                    $commit507 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e509) {
                                    $commit507 = false;
                                    fabric.common.TransactionID $currentTid510 =
                                      $tm511.getCurrentTid();
                                    if ($currentTid510 != null) {
                                        if ($e509.tid.equals($currentTid510) ||
                                              !$e509.tid.isDescendantOf(
                                                           $currentTid510)) {
                                            throw $e509;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit507 && $retry508) {
                                {  }
                                continue $label506;
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
                        fabric.worker.transaction.TransactionManager $tm520 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled523 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff521 = 1;
                        boolean $doBackoff522 = true;
                        boolean $retry517 = true;
                        $label515: for (boolean $commit516 = false; !$commit516;
                                        ) {
                            if ($backoffEnabled523) {
                                if ($doBackoff522) {
                                    if ($backoff521 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff521);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e518) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff521 < 5000) $backoff521 *= 2;
                                }
                                $doBackoff522 = $backoff521 <= 32 ||
                                                  !$doBackoff522;
                            }
                            $commit516 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e518) {
                                $commit516 = false;
                                continue $label515;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e518) {
                                $commit516 = false;
                                fabric.common.TransactionID $currentTid519 =
                                  $tm520.getCurrentTid();
                                if ($e518.tid.isDescendantOf($currentTid519))
                                    continue $label515;
                                if ($currentTid519.parent != null) {
                                    $retry517 = false;
                                    throw $e518;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e518) {
                                $commit516 = false;
                                if ($tm520.checkForStaleObjects()) continue;
                                fabric.common.TransactionID $currentTid519 =
                                  $tm520.getCurrentTid();
                                if ($e518.tid.isDescendantOf($currentTid519)) {
                                    $retry517 = true;
                                }
                                else if ($currentTid519.parent != null) {
                                    $retry517 = false;
                                    throw $e518;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e518) {
                                $commit516 = false;
                                if ($tm520.checkForStaleObjects())
                                    continue $label515;
                                $retry517 = false;
                                throw new fabric.worker.AbortException($e518);
                            }
                            finally {
                                if ($commit516) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e518) {
                                        $commit516 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e518) {
                                        $commit516 = false;
                                        fabric.common.TransactionID
                                          $currentTid519 =
                                          $tm520.getCurrentTid();
                                        if ($currentTid519 != null) {
                                            if ($e518.tid.equals(
                                                            $currentTid519) ||
                                                  !$e518.tid.
                                                  isDescendantOf(
                                                    $currentTid519)) {
                                                throw $e518;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit516 && $retry517) {
                                    {  }
                                    continue $label515;
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
    public static final long jlc$SourceLastModified$fabil = 1520096235000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
