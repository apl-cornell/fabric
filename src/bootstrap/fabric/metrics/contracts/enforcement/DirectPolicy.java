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
                    fabric.worker.transaction.TransactionManager $tm149 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled152 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff150 = 1;
                    boolean $doBackoff151 = true;
                    boolean $retry146 = true;
                    $label144: for (boolean $commit145 = false; !$commit145; ) {
                        if ($backoffEnabled152) {
                            if ($doBackoff151) {
                                if ($backoff150 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff150);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e147) {
                                            
                                        }
                                    }
                                }
                                if ($backoff150 < 5000) $backoff150 *= 2;
                            }
                            $doBackoff151 = $backoff150 <= 32 || !$doBackoff151;
                        }
                        $commit145 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (tmp.get$activated()) return;
                            tmp.refresh();
                        }
                        catch (final fabric.worker.RetryException $e147) {
                            $commit145 = false;
                            continue $label144;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e147) {
                            $commit145 = false;
                            fabric.common.TransactionID $currentTid148 =
                              $tm149.getCurrentTid();
                            if ($e147.tid.isDescendantOf($currentTid148))
                                continue $label144;
                            if ($currentTid148.parent != null) {
                                $retry146 = false;
                                throw $e147;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e147) {
                            $commit145 = false;
                            if ($tm149.checkForStaleObjects())
                                continue $label144;
                            $retry146 = false;
                            throw new fabric.worker.AbortException($e147);
                        }
                        finally {
                            if ($commit145) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e147) {
                                    $commit145 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e147) {
                                    $commit145 = false;
                                    fabric.common.TransactionID $currentTid148 =
                                      $tm149.getCurrentTid();
                                    if ($currentTid148 != null) {
                                        if ($e147.tid.equals($currentTid148) ||
                                              !$e147.tid.isDescendantOf(
                                                           $currentTid148)) {
                                            throw $e147;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit145 && $retry146) {
                                {  }
                                continue $label144;
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
                    fabric.worker.transaction.TransactionManager $tm158 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled161 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff159 = 1;
                    boolean $doBackoff160 = true;
                    boolean $retry155 = true;
                    $label153: for (boolean $commit154 = false; !$commit154; ) {
                        if ($backoffEnabled161) {
                            if ($doBackoff160) {
                                if ($backoff159 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff159);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e156) {
                                            
                                        }
                                    }
                                }
                                if ($backoff159 < 5000) $backoff159 *= 2;
                            }
                            $doBackoff160 = $backoff159 <= 32 || !$doBackoff160;
                        }
                        $commit154 = true;
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
                        catch (final fabric.worker.RetryException $e156) {
                            $commit154 = false;
                            continue $label153;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e156) {
                            $commit154 = false;
                            fabric.common.TransactionID $currentTid157 =
                              $tm158.getCurrentTid();
                            if ($e156.tid.isDescendantOf($currentTid157))
                                continue $label153;
                            if ($currentTid157.parent != null) {
                                $retry155 = false;
                                throw $e156;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e156) {
                            $commit154 = false;
                            if ($tm158.checkForStaleObjects())
                                continue $label153;
                            $retry155 = false;
                            throw new fabric.worker.AbortException($e156);
                        }
                        finally {
                            if ($commit154) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e156) {
                                    $commit154 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e156) {
                                    $commit154 = false;
                                    fabric.common.TransactionID $currentTid157 =
                                      $tm158.getCurrentTid();
                                    if ($currentTid157 != null) {
                                        if ($e156.tid.equals($currentTid157) ||
                                              !$e156.tid.isDescendantOf(
                                                           $currentTid157)) {
                                            throw $e156;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit154 && $retry155) {
                                {  }
                                continue $label153;
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
                    fabric.worker.transaction.TransactionManager $tm167 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled170 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff168 = 1;
                    boolean $doBackoff169 = true;
                    boolean $retry164 = true;
                    $label162: for (boolean $commit163 = false; !$commit163; ) {
                        if ($backoffEnabled170) {
                            if ($doBackoff169) {
                                if ($backoff168 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff168);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e165) {
                                            
                                        }
                                    }
                                }
                                if ($backoff168 < 5000) $backoff168 *= 2;
                            }
                            $doBackoff169 = $backoff168 <= 32 || !$doBackoff169;
                        }
                        $commit163 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            if (!tmp.get$activated()) tmp.activate();
                            tmp.get$metric().addObserver(mc);
                        }
                        catch (final fabric.worker.RetryException $e165) {
                            $commit163 = false;
                            continue $label162;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e165) {
                            $commit163 = false;
                            fabric.common.TransactionID $currentTid166 =
                              $tm167.getCurrentTid();
                            if ($e165.tid.isDescendantOf($currentTid166))
                                continue $label162;
                            if ($currentTid166.parent != null) {
                                $retry164 = false;
                                throw $e165;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final Throwable $e165) {
                            $commit163 = false;
                            if ($tm167.checkForStaleObjects())
                                continue $label162;
                            $retry164 = false;
                            throw new fabric.worker.AbortException($e165);
                        }
                        finally {
                            if ($commit163) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e165) {
                                    $commit163 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e165) {
                                    $commit163 = false;
                                    fabric.common.TransactionID $currentTid166 =
                                      $tm167.getCurrentTid();
                                    if ($currentTid166 != null) {
                                        if ($e165.tid.equals($currentTid166) ||
                                              !$e165.tid.isDescendantOf(
                                                           $currentTid166)) {
                                            throw $e165;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit163 && $retry164) {
                                {  }
                                continue $label162;
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
                        fabric.worker.transaction.TransactionManager $tm176 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled179 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff177 = 1;
                        boolean $doBackoff178 = true;
                        boolean $retry173 = true;
                        $label171: for (boolean $commit172 = false; !$commit172;
                                        ) {
                            if ($backoffEnabled179) {
                                if ($doBackoff178) {
                                    if ($backoff177 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.sleep(
                                                                   $backoff177);
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e174) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff177 < 5000) $backoff177 *= 2;
                                }
                                $doBackoff178 = $backoff177 <= 32 ||
                                                  !$doBackoff178;
                            }
                            $commit172 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.metrics.contracts.enforcement.DirectPolicy.
                                  _Static.
                                  _Proxy.
                                  $instance.
                                  set$HEDGE_FACTOR((double) 3);
                            }
                            catch (final fabric.worker.RetryException $e174) {
                                $commit172 = false;
                                continue $label171;
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e174) {
                                $commit172 = false;
                                fabric.common.TransactionID $currentTid175 =
                                  $tm176.getCurrentTid();
                                if ($e174.tid.isDescendantOf($currentTid175))
                                    continue $label171;
                                if ($currentTid175.parent != null) {
                                    $retry173 = false;
                                    throw $e174;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e174) {
                                $commit172 = false;
                                if ($tm176.checkForStaleObjects())
                                    continue $label171;
                                $retry173 = false;
                                throw new fabric.worker.AbortException($e174);
                            }
                            finally {
                                if ($commit172) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e174) {
                                        $commit172 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e174) {
                                        $commit172 = false;
                                        fabric.common.TransactionID
                                          $currentTid175 =
                                          $tm176.getCurrentTid();
                                        if ($currentTid175 != null) {
                                            if ($e174.tid.equals(
                                                            $currentTid175) ||
                                                  !$e174.tid.
                                                  isDescendantOf(
                                                    $currentTid175)) {
                                                throw $e174;
                                            }
                                        }
                                    }
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit172 && $retry173) {
                                    {  }
                                    continue $label171;
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
    public static final long jlc$SourceLastModified$fabil = 1519057317000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
