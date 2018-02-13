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
    
    public static final byte[] $classHash = new byte[] { 67, -127, 47, 65, -56,
    121, 68, 13, 28, 11, -16, -61, 65, -116, -117, -61, 25, 13, 80, 22, -6,
    -101, 30, 117, 4, 40, 78, -118, 124, -126, 2, 49 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1518538122000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
