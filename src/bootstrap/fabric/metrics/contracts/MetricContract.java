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
import fabric.worker.metrics.ImmutableMetricsVector;
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
    
    public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics();
    
    public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
      fabric.worker.metrics.ImmutableMetricsVector val);
    
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
    
    public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects();
    
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
        
        public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics() {
            return ((fabric.metrics.contracts.MetricContract._Impl) fetch()).
              get$leafMetrics();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
          fabric.worker.metrics.ImmutableMetricsVector val) {
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
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
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
        
        public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics() {
            return this.leafMetrics;
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.leafMetrics = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.worker.metrics.ImmutableMetricsVector leafMetrics;
        
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
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        metric)) instanceof fabric.metrics.SampledMetric) {
                this.
                  set$leafMetrics(
                    fabric.worker.metrics.ImmutableMetricsVector.
                        createVector(
                          new fabric.metrics.SampledMetric[] { (fabric.metrics.SampledMetric)
                                                                 fabric.lang.Object._Proxy.
                                                                 $getProxy(
                                                                   metric) }));
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
                        try { if (tmp.isActivated()) return; }
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
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.set$currentPolicy(
                      tmp.get$metric().thresholdPolicy(tmp.get$rate(),
                                                       tmp.get$base(), false,
                                                       tmp.$getStore()));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm536 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled539 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff537 = 1;
                    boolean $doBackoff538 = true;
                    boolean $retry533 = true;
                    $label531: for (boolean $commit532 = false; !$commit532; ) {
                        if ($backoffEnabled539) {
                            if ($doBackoff538) {
                                if ($backoff537 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff537);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e534) {
                                            
                                        }
                                    }
                                }
                                if ($backoff537 < 5000) $backoff537 *= 2;
                            }
                            $doBackoff538 = $backoff537 <= 32 || !$doBackoff538;
                        }
                        $commit532 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().thresholdPolicy(
                                                     tmp.get$rate(),
                                                     tmp.get$base(), true,
                                                     tmp.$getStore()));
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
                                    fabric.common.TransactionID $currentTid535 =
                                      $tm536.getCurrentTid();
                                    if ($currentTid535 != null) {
                                        if ($e534.tid.equals($currentTid535) ||
                                              !$e534.tid.isDescendantOf(
                                                           $currentTid535)) {
                                            throw $e534;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit532 && $retry533) {
                                {  }
                                continue $label531;
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
                    fabric.worker.transaction.TransactionManager $tm545 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled548 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff546 = 1;
                    boolean $doBackoff547 = true;
                    boolean $retry542 = true;
                    $label540: for (boolean $commit541 = false; !$commit541; ) {
                        if ($backoffEnabled548) {
                            if ($doBackoff547) {
                                if ($backoff546 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff546);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e543) {
                                            
                                        }
                                    }
                                }
                                if ($backoff546 < 5000) $backoff546 *= 2;
                            }
                            $doBackoff547 = $backoff546 <= 32 || !$doBackoff547;
                        }
                        $commit541 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e543) {
                            $commit541 = false;
                            continue $label540;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e543) {
                            $commit541 = false;
                            fabric.common.TransactionID $currentTid544 =
                              $tm545.getCurrentTid();
                            if ($e543.tid.isDescendantOf($currentTid544))
                                continue $label540;
                            if ($currentTid544.parent != null) {
                                $retry542 = false;
                                throw $e543;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e543) {
                            $commit541 = false;
                            if ($tm545.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid544 =
                              $tm545.getCurrentTid();
                            if ($e543.tid.isDescendantOf($currentTid544)) {
                                $retry542 = true;
                            }
                            else if ($currentTid544.parent != null) {
                                $retry542 = false;
                                throw $e543;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e543) {
                            $commit541 = false;
                            if ($tm545.checkForStaleObjects())
                                continue $label540;
                            $retry542 = false;
                            throw new fabric.worker.AbortException($e543);
                        }
                        finally {
                            if ($commit541) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e543) {
                                    $commit541 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e543) {
                                    $commit541 = false;
                                    fabric.common.TransactionID $currentTid544 =
                                      $tm545.getCurrentTid();
                                    if ($currentTid544 != null) {
                                        if ($e543.tid.equals($currentTid544) ||
                                              !$e543.tid.isDescendantOf(
                                                           $currentTid544)) {
                                            throw $e543;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit541 && $retry542) {
                                {  }
                                continue $label540;
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
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
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
            $writeInline(out, this.leafMetrics);
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
            this.leafMetrics = (fabric.worker.metrics.ImmutableMetricsVector)
                                 in.readObject();
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
    
    public static final byte[] $classHash = new byte[] { 61, -59, -113, -13,
    -25, 57, 60, -55, -79, -118, 30, -110, 41, -101, -52, -39, -25, 117, 61, 50,
    -88, -40, -100, 0, 78, -2, 47, 101, 89, 81, 96, 59 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524349478000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
