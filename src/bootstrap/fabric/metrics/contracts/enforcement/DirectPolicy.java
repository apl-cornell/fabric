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
                    fabric.worker.transaction.TransactionManager $tm127 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled130 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff128 = 1;
                    boolean $doBackoff129 = true;
                    boolean $retry124 = true;
                    $label122: for (boolean $commit123 = false; !$commit123; ) {
                        if ($backoffEnabled130) {
                            if ($doBackoff129) {
                                if ($backoff128 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff128);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e125) {
                                            
                                        }
                                    }
                                }
                                if ($backoff128 < 5000) $backoff128 *= 2;
                            }
                            $doBackoff129 = $backoff128 <= 32 || !$doBackoff129;
                        }
                        $commit123 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$activated()) return;
                            tmp.refresh();
                        }
                        catch (final fabric.worker.RetryException $e125) {
                            $commit123 = false;
                            continue $label122;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e125) {
                            $commit123 = false;
                            fabric.common.TransactionID $currentTid126 =
                              $tm127.getCurrentTid();
                            if ($e125.tid.isDescendantOf($currentTid126))
                                continue $label122;
                            if ($currentTid126.parent != null) {
                                $retry124 = false;
                                throw $e125;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e125) {
                            $commit123 = false;
                            if ($tm127.checkForStaleObjects())
                                continue $label122;
                            $retry124 = false;
                            throw new fabric.worker.AbortException($e125);
                        }
                        finally {
                            if ($commit123) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e125) {
                                    $commit123 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e125) {
                                    $commit123 = false;
                                    fabric.common.TransactionID $currentTid126 =
                                      $tm127.getCurrentTid();
                                    if ($currentTid126 != null) {
                                        if ($e125.tid.equals($currentTid126) ||
                                              !$e125.tid.isDescendantOf(
                                                           $currentTid126)) {
                                            throw $e125;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit123 && $retry124) {
                                {  }
                                continue $label122;
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
                    fabric.worker.transaction.TransactionManager $tm136 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled139 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff137 = 1;
                    boolean $doBackoff138 = true;
                    boolean $retry133 = true;
                    $label131: for (boolean $commit132 = false; !$commit132; ) {
                        if ($backoffEnabled139) {
                            if ($doBackoff138) {
                                if ($backoff137 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff137);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e134) {
                                            
                                        }
                                    }
                                }
                                if ($backoff137 < 5000) $backoff137 *= 2;
                            }
                            $doBackoff138 = $backoff137 <= 32 || !$doBackoff138;
                        }
                        $commit132 = true;
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
                        catch (final fabric.worker.RetryException $e134) {
                            $commit132 = false;
                            continue $label131;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e134) {
                            $commit132 = false;
                            fabric.common.TransactionID $currentTid135 =
                              $tm136.getCurrentTid();
                            if ($e134.tid.isDescendantOf($currentTid135))
                                continue $label131;
                            if ($currentTid135.parent != null) {
                                $retry133 = false;
                                throw $e134;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e134) {
                            $commit132 = false;
                            if ($tm136.checkForStaleObjects())
                                continue $label131;
                            $retry133 = false;
                            throw new fabric.worker.AbortException($e134);
                        }
                        finally {
                            if ($commit132) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e134) {
                                    $commit132 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e134) {
                                    $commit132 = false;
                                    fabric.common.TransactionID $currentTid135 =
                                      $tm136.getCurrentTid();
                                    if ($currentTid135 != null) {
                                        if ($e134.tid.equals($currentTid135) ||
                                              !$e134.tid.isDescendantOf(
                                                           $currentTid135)) {
                                            throw $e134;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit132 && $retry133) {
                                {  }
                                continue $label131;
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
                    fabric.worker.transaction.TransactionManager $tm145 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled148 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff146 = 1;
                    boolean $doBackoff147 = true;
                    boolean $retry142 = true;
                    $label140: for (boolean $commit141 = false; !$commit141; ) {
                        if ($backoffEnabled148) {
                            if ($doBackoff147) {
                                if ($backoff146 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff146);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e143) {
                                            
                                        }
                                    }
                                }
                                if ($backoff146 < 5000) $backoff146 *= 2;
                            }
                            $doBackoff147 = $backoff146 <= 32 || !$doBackoff147;
                        }
                        $commit141 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated()) tmp.activate();
                            tmp.get$metric().addObserver(mc);
                        }
                        catch (final fabric.worker.RetryException $e143) {
                            $commit141 = false;
                            continue $label140;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e143) {
                            $commit141 = false;
                            fabric.common.TransactionID $currentTid144 =
                              $tm145.getCurrentTid();
                            if ($e143.tid.isDescendantOf($currentTid144))
                                continue $label140;
                            if ($currentTid144.parent != null) {
                                $retry142 = false;
                                throw $e143;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e143) {
                            $commit141 = false;
                            if ($tm145.checkForStaleObjects())
                                continue $label140;
                            $retry142 = false;
                            throw new fabric.worker.AbortException($e143);
                        }
                        finally {
                            if ($commit141) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e143) {
                                    $commit141 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e143) {
                                    $commit141 = false;
                                    fabric.common.TransactionID $currentTid144 =
                                      $tm145.getCurrentTid();
                                    if ($currentTid144 != null) {
                                        if ($e143.tid.equals($currentTid144) ||
                                              !$e143.tid.isDescendantOf(
                                                           $currentTid144)) {
                                            throw $e143;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit141 && $retry142) {
                                {  }
                                continue $label140;
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
                        fabric.worker.transaction.TransactionManager $tm154 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled157 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff155 = 1;
                        boolean $doBackoff156 = true;
                        boolean $retry151 = true;
                        $label149: for (boolean $commit150 = false; !$commit150;
                                        ) {
                            if ($backoffEnabled157) {
                                if ($doBackoff156) {
                                    if ($backoff155 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff155);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e152) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff155 < 5000) $backoff155 *= 2;
                                }
                                $doBackoff156 = $backoff155 <= 32 ||
                                                  !$doBackoff156;
                            }
                            $commit150 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e152) {
                                $commit150 = false;
                                continue $label149;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e152) {
                                $commit150 = false;
                                fabric.common.TransactionID $currentTid153 =
                                  $tm154.getCurrentTid();
                                if ($e152.tid.isDescendantOf($currentTid153))
                                    continue $label149;
                                if ($currentTid153.parent != null) {
                                    $retry151 = false;
                                    throw $e152;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e152) {
                                $commit150 = false;
                                if ($tm154.checkForStaleObjects())
                                    continue $label149;
                                $retry151 = false;
                                throw new fabric.worker.AbortException($e152);
                            }
                            finally {
                                if ($commit150) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e152) {
                                        $commit150 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e152) {
                                        $commit150 = false;
                                        fabric.common.TransactionID
                                          $currentTid153 =
                                          $tm154.getCurrentTid();
                                        if ($currentTid153 != null) {
                                            if ($e152.tid.equals(
                                                            $currentTid153) ||
                                                  !$e152.tid.
                                                  isDescendantOf(
                                                    $currentTid153)) {
                                                throw $e152;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit150 && $retry151) {
                                    {  }
                                    continue $label149;
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
