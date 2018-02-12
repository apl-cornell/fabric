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
                    fabric.worker.transaction.TransactionManager $tm439 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled442 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff440 = 1;
                    boolean $doBackoff441 = true;
                    boolean $retry436 = true;
                    $label434: for (boolean $commit435 = false; !$commit435; ) {
                        if ($backoffEnabled442) {
                            if ($doBackoff441) {
                                if ($backoff440 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff440);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e437) {
                                            
                                        }
                                    }
                                }
                                if ($backoff440 < 5000) $backoff440 *= 2;
                            }
                            $doBackoff441 = $backoff440 <= 32 || !$doBackoff441;
                        }
                        $commit435 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$activated()) return;
                            tmp.refresh();
                        }
                        catch (final fabric.worker.RetryException $e437) {
                            $commit435 = false;
                            continue $label434;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e437) {
                            $commit435 = false;
                            fabric.common.TransactionID $currentTid438 =
                              $tm439.getCurrentTid();
                            if ($e437.tid.isDescendantOf($currentTid438))
                                continue $label434;
                            if ($currentTid438.parent != null) {
                                $retry436 = false;
                                throw $e437;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e437) {
                            $commit435 = false;
                            if ($tm439.checkForStaleObjects())
                                continue $label434;
                            $retry436 = false;
                            throw new fabric.worker.AbortException($e437);
                        }
                        finally {
                            if ($commit435) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e437) {
                                    $commit435 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e437) {
                                    $commit435 = false;
                                    fabric.common.TransactionID $currentTid438 =
                                      $tm439.getCurrentTid();
                                    if ($currentTid438 != null) {
                                        if ($e437.tid.equals($currentTid438) ||
                                              !$e437.tid.isDescendantOf(
                                                           $currentTid438)) {
                                            throw $e437;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit435 && $retry436) {
                                {  }
                                continue $label434;
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
                    fabric.worker.transaction.TransactionManager $tm448 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled451 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff449 = 1;
                    boolean $doBackoff450 = true;
                    boolean $retry445 = true;
                    $label443: for (boolean $commit444 = false; !$commit444; ) {
                        if ($backoffEnabled451) {
                            if ($doBackoff450) {
                                if ($backoff449 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff449);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e446) {
                                            
                                        }
                                    }
                                }
                                if ($backoff449 < 5000) $backoff449 *= 2;
                            }
                            $doBackoff450 = $backoff449 <= 32 || !$doBackoff450;
                        }
                        $commit444 = true;
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
                        catch (final fabric.worker.RetryException $e446) {
                            $commit444 = false;
                            continue $label443;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e446) {
                            $commit444 = false;
                            fabric.common.TransactionID $currentTid447 =
                              $tm448.getCurrentTid();
                            if ($e446.tid.isDescendantOf($currentTid447))
                                continue $label443;
                            if ($currentTid447.parent != null) {
                                $retry445 = false;
                                throw $e446;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e446) {
                            $commit444 = false;
                            if ($tm448.checkForStaleObjects())
                                continue $label443;
                            $retry445 = false;
                            throw new fabric.worker.AbortException($e446);
                        }
                        finally {
                            if ($commit444) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e446) {
                                    $commit444 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e446) {
                                    $commit444 = false;
                                    fabric.common.TransactionID $currentTid447 =
                                      $tm448.getCurrentTid();
                                    if ($currentTid447 != null) {
                                        if ($e446.tid.equals($currentTid447) ||
                                              !$e446.tid.isDescendantOf(
                                                           $currentTid447)) {
                                            throw $e446;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit444 && $retry445) {
                                {  }
                                continue $label443;
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
                    fabric.worker.transaction.TransactionManager $tm457 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled460 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff458 = 1;
                    boolean $doBackoff459 = true;
                    boolean $retry454 = true;
                    $label452: for (boolean $commit453 = false; !$commit453; ) {
                        if ($backoffEnabled460) {
                            if ($doBackoff459) {
                                if ($backoff458 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff458);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e455) {
                                            
                                        }
                                    }
                                }
                                if ($backoff458 < 5000) $backoff458 *= 2;
                            }
                            $doBackoff459 = $backoff458 <= 32 || !$doBackoff459;
                        }
                        $commit453 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated()) tmp.activate();
                            tmp.get$metric().addObserver(mc);
                        }
                        catch (final fabric.worker.RetryException $e455) {
                            $commit453 = false;
                            continue $label452;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e455) {
                            $commit453 = false;
                            fabric.common.TransactionID $currentTid456 =
                              $tm457.getCurrentTid();
                            if ($e455.tid.isDescendantOf($currentTid456))
                                continue $label452;
                            if ($currentTid456.parent != null) {
                                $retry454 = false;
                                throw $e455;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e455) {
                            $commit453 = false;
                            if ($tm457.checkForStaleObjects())
                                continue $label452;
                            $retry454 = false;
                            throw new fabric.worker.AbortException($e455);
                        }
                        finally {
                            if ($commit453) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e455) {
                                    $commit453 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e455) {
                                    $commit453 = false;
                                    fabric.common.TransactionID $currentTid456 =
                                      $tm457.getCurrentTid();
                                    if ($currentTid456 != null) {
                                        if ($e455.tid.equals($currentTid456) ||
                                              !$e455.tid.isDescendantOf(
                                                           $currentTid456)) {
                                            throw $e455;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit453 && $retry454) {
                                {  }
                                continue $label452;
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
                        fabric.worker.transaction.TransactionManager $tm466 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled469 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff467 = 1;
                        boolean $doBackoff468 = true;
                        boolean $retry463 = true;
                        $label461: for (boolean $commit462 = false; !$commit462;
                                        ) {
                            if ($backoffEnabled469) {
                                if ($doBackoff468) {
                                    if ($backoff467 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff467);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e464) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff467 < 5000) $backoff467 *= 2;
                                }
                                $doBackoff468 = $backoff467 <= 32 ||
                                                  !$doBackoff468;
                            }
                            $commit462 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e464) {
                                $commit462 = false;
                                continue $label461;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e464) {
                                $commit462 = false;
                                fabric.common.TransactionID $currentTid465 =
                                  $tm466.getCurrentTid();
                                if ($e464.tid.isDescendantOf($currentTid465))
                                    continue $label461;
                                if ($currentTid465.parent != null) {
                                    $retry463 = false;
                                    throw $e464;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e464) {
                                $commit462 = false;
                                if ($tm466.checkForStaleObjects())
                                    continue $label461;
                                $retry463 = false;
                                throw new fabric.worker.AbortException($e464);
                            }
                            finally {
                                if ($commit462) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e464) {
                                        $commit462 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e464) {
                                        $commit462 = false;
                                        fabric.common.TransactionID
                                          $currentTid465 =
                                          $tm466.getCurrentTid();
                                        if ($currentTid465 != null) {
                                            if ($e464.tid.equals(
                                                            $currentTid465) ||
                                                  !$e464.tid.
                                                  isDescendantOf(
                                                    $currentTid465)) {
                                                throw $e464;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit462 && $retry463) {
                                    {  }
                                    continue $label461;
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -76, -119, -6, -46,
    -53, -24, 22, 39, -44, -102, -105, 64, -94, 35, -6, 61, -75, -105, 49, 20,
    14, -80, -117, -15, 48, -66, -32, 81, -99, -20, -89, -96 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518448064000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
