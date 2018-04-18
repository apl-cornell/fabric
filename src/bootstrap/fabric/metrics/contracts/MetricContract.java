package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.metrics.DerivedMetric;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.contracts.enforcement.WitnessPolicy;
import fabric.metrics.util.Observer;
import fabric.metrics.util.Subject;
import fabric.common.TransactionID;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import java.util.logging.Level;
import fabric.common.Logging;

/**
 * A contract asserting that a {@link Metric}'s value is above or below a
 * vectorized boundary expression <code>r\u20d7 * (t - startTime) + b\u20d7</code> until
 * the associated expriation time.
 * <p>
 * This class follows the subject-observer pattern. An instance is an observer
 * of either a {@link Metric} or a set of {@link MetricContract}s and can be
 * observed by other {@link Contract}s.
 */
public interface MetricContract extends fabric.metrics.contracts.Contract {
    public fabric.metrics.Metric get$metric();
    
    public fabric.metrics.Metric set$metric(fabric.metrics.Metric val);
    
    public fabric.lang.arrays.ObjectArray get$leafMetrics();
    
    public fabric.lang.arrays.ObjectArray set$leafMetrics(
      fabric.lang.arrays.ObjectArray val);
    
    public double get$rate();
    
    public double set$rate(double val);
    
    public double postInc$rate();
    
    public double postDec$rate();
    
    public double get$base();
    
    public double set$base(double val);
    
    public double postInc$base();
    
    public double postDec$base();
    
    /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param rate
   *        the rate of the bound this {@link MetricContract} asserts on
   *        metric.
   * @param base
   *        the base of the bound this {@link MetricContract} asserts on
   *        metric.
   */
    public fabric.metrics.contracts.MetricContract
      fabric$metrics$contracts$MetricContract$(fabric.metrics.Metric metric,
                                               double rate, double base);
    
    /** @return the {@link Metric} that this contract observes. */
    public fabric.metrics.Metric getMetric();
    
    /**
   * @return the expected lifetime of this {@link MetricContract} given the
   *       associated {@link Metric}s current velocity.
   */
    public long getExpectedLifetime();
    
    public void activate();
    
    /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
    public boolean refresh(boolean asyncExtension);
    
    public boolean implies(fabric.metrics.Metric otherMetric, double otherRate,
                           double otherBase);
    
    public java.lang.String toString();
    
    public fabric.lang.arrays.ObjectArray getLeafSubjects();
    
    public static class _Proxy extends fabric.metrics.contracts.Contract._Proxy
      implements fabric.metrics.contracts.MetricContract {
        public fabric.metrics.Metric get$metric() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              get$metric();
        }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              set$metric(val);
        }
        
        public fabric.lang.arrays.ObjectArray get$leafMetrics() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              get$leafMetrics();
        }
        
        public fabric.lang.arrays.ObjectArray set$leafMetrics(
          fabric.lang.arrays.ObjectArray val) {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              set$leafMetrics(val);
        }
        
        public double get$rate() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              get$rate();
        }
        
        public double set$rate(double val) {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              set$rate(val);
        }
        
        public double postInc$rate() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              postInc$rate();
        }
        
        public double postDec$rate() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              postDec$rate();
        }
        
        public double get$base() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              get$base();
        }
        
        public double set$base(double val) {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              set$base(val);
        }
        
        public double postInc$base() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              postInc$base();
        }
        
        public double postDec$base() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              postDec$base();
        }
        
        public fabric.metrics.contracts.MetricContract
          fabric$metrics$contracts$MetricContract$(fabric.metrics.Metric arg1,
                                                   double arg2, double arg3) {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              fabric$metrics$contracts$MetricContract$(arg1, arg2, arg3);
        }
        
        public fabric.metrics.Metric getMetric() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              getMetric();
        }
        
        public long getExpectedLifetime() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              getExpectedLifetime();
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
            return ((fabric.metrics.contracts.MetricContract) fetch()).
              getLeafSubjects();
        }
        
        public _Proxy(MetricContract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.contracts.Contract._Impl
      implements fabric.metrics.contracts.MetricContract {
        public fabric.metrics.Metric get$metric() { return this.metric; }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.metric = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.Metric metric;
        
        public fabric.lang.arrays.ObjectArray get$leafMetrics() {
            return this.leafMetrics;
        }
        
        public fabric.lang.arrays.ObjectArray set$leafMetrics(
          fabric.lang.arrays.ObjectArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.leafMetrics = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.arrays.ObjectArray leafMetrics;
        
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
        
        /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param rate
   *        the rate of the bound this {@link MetricContract} asserts on
   *        metric.
   * @param base
   *        the base of the bound this {@link MetricContract} asserts on
   *        metric.
   */
        public fabric.metrics.contracts.MetricContract
          fabric$metrics$contracts$MetricContract$(fabric.metrics.Metric metric,
                                                   double rate, double base) {
            this.set$metric(metric);
            this.set$rate((double) rate);
            this.set$base((double) base);
            fabric.lang.security.Label lbl =
              fabric.lang.security.LabelUtil._Impl.noComponents();
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        metric)) instanceof fabric.metrics.SampledMetric) {
                this.
                  set$leafMetrics(
                    (fabric.lang.arrays.ObjectArray)
                      new fabric.lang.arrays.ObjectArray._Impl(
                        this.$getStore()).
                      fabric$lang$arrays$ObjectArray$(
                        lbl, lbl.confPolicy(),
                        fabric.metrics.SampledMetric._Proxy.class, 1).$getProxy(
                                                                        ));
                this.get$leafMetrics().set(
                                         0,
                                         (fabric.metrics.SampledMetric)
                                           fabric.lang.Object._Proxy.$getProxy(
                                                                       metric));
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy(
                         (java.lang.Object)
                           fabric.lang.WrappedJavaInlineable.
                           $unwrap(
                             metric)) instanceof fabric.metrics.DerivedMetric) {
                this.set$leafMetrics(
                       ((fabric.metrics.DerivedMetric)
                          fabric.lang.Object._Proxy.$getProxy(
                                                      metric)).getLeafSubjects(
                                                                 ));
            }
            else {
                throw new java.lang.IllegalStateException(
                        "All metrics should be either sampled or derived!");
            }
            fabric$metrics$contracts$Contract$();
            return (fabric.metrics.contracts.MetricContract) this.$getProxy();
        }
        
        /** @return the {@link Metric} that this contract observes. */
        public fabric.metrics.Metric getMetric() { return this.get$metric(); }
        
        /**
   * @return the expected lifetime of this {@link MetricContract} given the
   *       associated {@link Metric}s current velocity.
   */
        public long getExpectedLifetime() {
            long time = java.lang.System.currentTimeMillis();
            fabric.metrics.Metric m = getMetric();
            double adjustedRate = this.get$rate() - m.velocity();
            return (long)
                     (time +
                        (m.value() -
                           fabric.metrics.contracts.Bound._Impl.value(
                                                                  this.get$rate(
                                                                         ),
                                                                  this.get$base(
                                                                         ),
                                                                  time)) /
                        adjustedRate);
        }
        
        public void activate() {
            fabric.metrics.contracts.MetricContract._Impl.
              static_activate((fabric.metrics.contracts.MetricContract)
                                this.$getProxy());
        }
        
        private static void static_activate(
          fabric.metrics.contracts.MetricContract tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                if (tmp.isActivated()) return;
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm322 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled325 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff323 = 1;
                    boolean $doBackoff324 = true;
                    boolean $retry319 = true;
                    $label317: for (boolean $commit318 = false; !$commit318; ) {
                        if ($backoffEnabled325) {
                            if ($doBackoff324) {
                                if ($backoff323 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff323);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e320) {
                                            
                                        }
                                    }
                                }
                                if ($backoff323 < 5000) $backoff323 *= 2;
                            }
                            $doBackoff324 = $backoff323 <= 32 || !$doBackoff324;
                        }
                        $commit318 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try { if (tmp.isActivated()) return; }
                        catch (final fabric.worker.RetryException $e320) {
                            $commit318 = false;
                            continue $label317;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e320) {
                            $commit318 = false;
                            fabric.common.TransactionID $currentTid321 =
                              $tm322.getCurrentTid();
                            if ($e320.tid.isDescendantOf($currentTid321))
                                continue $label317;
                            if ($currentTid321.parent != null) {
                                $retry319 = false;
                                throw $e320;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e320) {
                            $commit318 = false;
                            if ($tm322.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid321 =
                              $tm322.getCurrentTid();
                            if ($e320.tid.isDescendantOf($currentTid321)) {
                                $retry319 = true;
                            }
                            else if ($currentTid321.parent != null) {
                                $retry319 = false;
                                throw $e320;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e320) {
                            $commit318 = false;
                            if ($tm322.checkForStaleObjects())
                                continue $label317;
                            $retry319 = false;
                            throw new fabric.worker.AbortException($e320);
                        }
                        finally {
                            if ($commit318) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e320) {
                                    $commit318 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e320) {
                                    $commit318 = false;
                                    fabric.common.TransactionID $currentTid321 =
                                      $tm322.getCurrentTid();
                                    if ($currentTid321 != null) {
                                        if ($e320.tid.equals($currentTid321) ||
                                              !$e320.tid.isDescendantOf(
                                                           $currentTid321)) {
                                            throw $e320;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit318 && $retry319) {
                                {  }
                                continue $label317;
                            }
                        }
                    }
                }
            }
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.set$currentPolicy(
                      tmp.get$metric().thresholdPolicy(tmp.get$rate(),
                                                       tmp.get$base(), false,
                                                       tmp.$getStore()));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm331 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled334 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff332 = 1;
                    boolean $doBackoff333 = true;
                    boolean $retry328 = true;
                    $label326: for (boolean $commit327 = false; !$commit327; ) {
                        if ($backoffEnabled334) {
                            if ($doBackoff333) {
                                if ($backoff332 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff332);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e329) {
                                            
                                        }
                                    }
                                }
                                if ($backoff332 < 5000) $backoff332 *= 2;
                            }
                            $doBackoff333 = $backoff332 <= 32 || !$doBackoff333;
                        }
                        $commit327 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().thresholdPolicy(
                                                     tmp.get$rate(),
                                                     tmp.get$base(), true,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e329) {
                            $commit327 = false;
                            continue $label326;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e329) {
                            $commit327 = false;
                            fabric.common.TransactionID $currentTid330 =
                              $tm331.getCurrentTid();
                            if ($e329.tid.isDescendantOf($currentTid330))
                                continue $label326;
                            if ($currentTid330.parent != null) {
                                $retry328 = false;
                                throw $e329;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e329) {
                            $commit327 = false;
                            if ($tm331.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid330 =
                              $tm331.getCurrentTid();
                            if ($e329.tid.isDescendantOf($currentTid330)) {
                                $retry328 = true;
                            }
                            else if ($currentTid330.parent != null) {
                                $retry328 = false;
                                throw $e329;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e329) {
                            $commit327 = false;
                            if ($tm331.checkForStaleObjects())
                                continue $label326;
                            $retry328 = false;
                            throw new fabric.worker.AbortException($e329);
                        }
                        finally {
                            if ($commit327) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e329) {
                                    $commit327 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e329) {
                                    $commit327 = false;
                                    fabric.common.TransactionID $currentTid330 =
                                      $tm331.getCurrentTid();
                                    if ($currentTid330 != null) {
                                        if ($e329.tid.equals($currentTid330) ||
                                              !$e329.tid.isDescendantOf(
                                                           $currentTid330)) {
                                            throw $e329;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit327 && $retry328) {
                                {  }
                                continue $label326;
                            }
                        }
                    }
                }
            }
            tmp.get$currentPolicy().activate();
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                fabric.metrics.contracts.Contract._Impl.static_activate(tmp);
                tmp.getMetric().addContract(tmp);
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm340 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled343 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff341 = 1;
                    boolean $doBackoff342 = true;
                    boolean $retry337 = true;
                    $label335: for (boolean $commit336 = false; !$commit336; ) {
                        if ($backoffEnabled343) {
                            if ($doBackoff342) {
                                if ($backoff341 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff341);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e338) {
                                            
                                        }
                                    }
                                }
                                if ($backoff341 < 5000) $backoff341 *= 2;
                            }
                            $doBackoff342 = $backoff341 <= 32 || !$doBackoff342;
                        }
                        $commit336 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e338) {
                            $commit336 = false;
                            continue $label335;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e338) {
                            $commit336 = false;
                            fabric.common.TransactionID $currentTid339 =
                              $tm340.getCurrentTid();
                            if ($e338.tid.isDescendantOf($currentTid339))
                                continue $label335;
                            if ($currentTid339.parent != null) {
                                $retry337 = false;
                                throw $e338;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e338) {
                            $commit336 = false;
                            if ($tm340.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid339 =
                              $tm340.getCurrentTid();
                            if ($e338.tid.isDescendantOf($currentTid339)) {
                                $retry337 = true;
                            }
                            else if ($currentTid339.parent != null) {
                                $retry337 = false;
                                throw $e338;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e338) {
                            $commit336 = false;
                            if ($tm340.checkForStaleObjects())
                                continue $label335;
                            $retry337 = false;
                            throw new fabric.worker.AbortException($e338);
                        }
                        finally {
                            if ($commit336) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e338) {
                                    $commit336 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e338) {
                                    $commit336 = false;
                                    fabric.common.TransactionID $currentTid339 =
                                      $tm340.getCurrentTid();
                                    if ($currentTid339 != null) {
                                        if ($e338.tid.equals($currentTid339) ||
                                              !$e338.tid.isDescendantOf(
                                                           $currentTid339)) {
                                            throw $e338;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit336 && $retry337) {
                                {  }
                                continue $label335;
                            }
                        }
                    }
                }
            }
        }
        
        /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
        public boolean refresh(boolean asyncExtension) {
            if (!isActivated()) { return false; }
            long currentTime = java.lang.System.currentTimeMillis();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                long curExpiry = this.get$currentPolicy().expiry();
                if (curExpiry >= currentTime) {
                    return update(curExpiry, asyncExtension);
                }
            }
            if (asyncExtension) return false;
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            acquireReconfigLocks();
            tm.markCoordination();
            fabric.metrics.contracts.enforcement.EnforcementPolicy oldPolicy =
              this.get$currentPolicy();
            fabric.metrics.contracts.enforcement.EnforcementPolicy newPolicy =
              this.get$metric().thresholdPolicy(this.get$rate(),
                                                this.get$base(), $getStore());
            if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null) &&
                  !oldPolicy.equals(newPolicy))
                oldPolicy.unapply((fabric.metrics.contracts.MetricContract)
                                    this.$getProxy());
            newPolicy.activate();
            long newExpiry = newPolicy.expiry();
            boolean result = update(newExpiry, asyncExtension);
            if (newExpiry >= currentTime) {
                this.set$currentPolicy(newPolicy);
                this.get$currentPolicy().
                  apply((fabric.metrics.contracts.MetricContract)
                          this.$getProxy());
            } else {
                this.set$currentPolicy(null);
            }
            return result;
        }
        
        public boolean implies(fabric.metrics.Metric otherMetric,
                               double otherRate, double otherBase) {
            if (!getMetric().equals(otherMetric) || this.get$rate() <
                  otherRate || this.get$base() < otherBase)
                return false;
            return valid();
        }
        
        public java.lang.String toString() {
            return ((java.lang.Comparable)
                      fabric.lang.WrappedJavaInlineable.$unwrap(getMetric())).
              toString() +
            " >= " +
            this.get$rate() +
            " * t + " +
            (this.get$base() +
               this.get$rate() * java.lang.System.currentTimeMillis()) +
            " until " +
            getExpiry();
        }
        
        public fabric.lang.arrays.ObjectArray getLeafSubjects() {
            return this.get$leafMetrics();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.MetricContract._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.metric, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.leafMetrics, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            out.writeDouble(this.rate);
            out.writeDouble(this.base);
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
            this.leafMetrics =
              (fabric.lang.arrays.ObjectArray)
                $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.rate = in.readDouble();
            this.base = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.MetricContract._Impl src =
              (fabric.metrics.contracts.MetricContract._Impl) other;
            this.metric = src.metric;
            this.leafMetrics = src.leafMetrics;
            this.rate = src.rate;
            this.base = src.base;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.MetricContract._Static {
            public _Proxy(fabric.metrics.contracts.MetricContract._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.MetricContract._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  MetricContract.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    MetricContract.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.MetricContract._Static.
                        _Impl.class);
                $instance = (fabric.metrics.contracts.MetricContract._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.MetricContract._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
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
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.MetricContract._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -63, 78, -90, -95, 53,
    45, -101, -32, 9, -118, -85, 125, -88, 76, -72, 122, 3, -51, -85, 125, 71,
    -71, -75, -35, 67, 28, 75, -122, 47, 5, -84, -78 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524080237000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
