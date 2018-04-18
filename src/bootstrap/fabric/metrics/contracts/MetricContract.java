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
    public static final long jlc$SourceLastModified$fabil = 1524081841000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALUZC2wUx3XufP6cMdiYQMDYxpgrKQTuRNJUIm7T4iufi49gYT6NaXD39ubshb3dZXfOHFCjpFIDIhJVGkJIkyBFoQ0FB1RU1FSRVVI1JCk0EiT9qmlQpShBQFXUpqVS2/S9mbnbu/XdxY5aSzNvbua9mfefN+vRG6TasUlnSkloepjtsqgTXqUkYvFexXZoMqorjrMBZgfUKYHY4Q9fTLb7iT9OGlTFMA1NVfQBw2FkWnybMqxEDMoiG9fHuraQoIqEaxRniBH/lu6sTTosU981qJtMHjJu/yfvjBx6amvTmSrS2E8aNaOPKUxTo6bBaJb1k4Y0TSeo7axIJmmyn0w3KE32UVtTdG03IJpGP2l2tEFDYRmbOuupY+rDiNjsZCxq8zNzk8i+CWzbGZWZNrDfJNjPME2PxDWHdcVJTUqjetLZQfaSQJxUp3RlEBBnxXNSRPiOkVU4D+j1GrBppxSV5kgC2zUjycg8L0Ve4lAPIABpbZqyITN/VMBQYII0C5Z0xRiM9DFbMwYBtdrMwCmMtJTdFJDqLEXdrgzSAUZme/F6xRJgBblakISRmV40vhPYrMVjswJr3XjgCwf3GGsMP/EBz0mq6sh/HRC1e4jW0xS1qaFSQdiwOH5YmTW2308IIM/0IAucH3/j5peXtJ97Q+DMLYGzLrGNqmxAPZaYdqk1umh5FbJRZ5mOhq5QJDm3aq9c6cpa4O2z8jviYji3eG79+QcfPkGv+Ul9jNSopp5Jg1dNV820penUXk0NaiuMJmMkSI1klK/HSC2M45pBxey6VMqhLEYCOp+qMflvUFEKtkAV1cJYM1JmbmwpbIiPsxYhpBYa8UF7h5C5RwC2E1L1NiObIkNmmkYSeobuBPeOQKOKrQ5FIG5tTY04thqxMwbTAElOgRcBcCLg6sxWVOZE1vKZqPwdBo6s/9vOWZSpaafPB+qep5pJmlAcsJ30o+5eHUJljaknqT2g6gfHYmTG2NPcl4Lo/w74MNeWD+zf6s0chbSHMt0rb54auCD8EGmlMhm5Q7AbluyG8+yGi9kFDhsw2MKQvsKQvkZ92XD0aOwk96kahwdfftMG2PReS1dYyrTTWeLzcQlv4/TcmcAVtkOKgSzSsKjvofu/vr+zCrzY2hlAwwJqyBtTbiaKwUiBQBlQG/d9+PfTh0dMN7oYCY0L+vGUGLSdXnXZpkqTkBTd7Rd3KGcHxkZCfkw4QdSLAt4KiaXde0ZR8HblEiFqozpOpqAOFB2Xctmrng3Z5k53hrvBNOyahUegsjwM8hz6xT7rud++dfVufrvk0m1jQV7uo6yrIMRxs0YezNNd3W+wKQW8d4/0PvHkjX1buOIBY0GpA0PYRyG0FYhp0/7WGzt+994fj73jd43FSI2VSeiamuWyTP8Y/nzQ/oMN4xQnEEK2jsoc0ZFPEhaevNDlDdKFDikLWHdCG420mdRSmpLQKXrKvxo/s+zs9YNNwtw6zAjl2WTJJ2/gzs/pJg9f2PqPdr6NT8XrytWfiyZy4Ax35xW2rexCPrKPXG57+nXlOfB8yGCOtpvypES4Pgg34F1cF0t5v8yz9jnsOoW2Wvl8wBl/H6zCi9X1xf7I6LMt0fuuieDP+yLuMb9E8G9SCsLkrhPpj/ydNa/5SW0/aeJ3umKwTQrkMnCDfriVnaicjJOpRevFN6y4TrrysdbqjYOCY71R4CYdGCM2juuF4wvHAUU0o5I6RAu0SsjjYoaF/W1ZH+GDeznJAt4vxG4RV6SfkaBlmwy4pFBVBLV0OsPQ+vycO8FVZZbDnzPhSvfkPpHxcLFFhCH2n8+z14TsLYE2H9iKSbi0BHvRsuzVWrY2DI6Pk1/KcTVFp0pKnO2AK7R5qkyY5r4nbv23Xrw1Zyx09Za49b21RwHiX0bfu3Z5atspnr4CeLFwlXuLtvE1WVGpxXlsKFZBG7R7CKn5p4TXGen59PfjV6A8hXJTyC+v2//ldtmctVs91u5T0paew+RGz8erT15E3P7YxdG0np846CtjaRwuZhACmqHoee/TqTHIhkpEe6+tpSFhD8vqj+4/dODj8MFDItOJEnnBuCq1kEaUyfygqfy0LJwyv9IpnGLVB6dHXjk+sk84U3NxwbfSyKRf+vW/L4aPXHmzROFQBY6DP3pKq8DHVSBEx24zdv2cIJvXs19oK2chkW0x10ARbRoUQ5evzYFgxpJCN+EtlTeoqCc0M5x/4SRE9ZjMjrMkaGPc420t93M3UV651rY8uv39QaGNeR7tebF/sHb0zdUL1e/4SVU+I457xxQTdRXnwXqbwjPM2FCUDTuEf01QsxXuGavCGr84gN9qFdWc02eTq36R6oUuS6XCRtxqLrROSIFUwgdLpMLhSgFSlAUDWBBwvK9KD0awBcImaWakYcuysQCOf0TCTAk29k6cjVym3MMPFFwswM7IH8v/auS747KEFwuOLbjcCUZiW7knIo/CY988dDS57nvL/NI6XwNvZ6a1VKfDVC/YKsjHWp6NIG6vQYsAN3cIWP16ofSuzjwS+JG0TpKcl/CcVwLXeao4A1X4c0VeOXv4/t+u4GePY3eAkc+K1BuSqTeUf2SEih8ZIZdhj5ho5G5g+YSERyYnJpI8JeHj5cUs5P1IhbXvYvcEmGmQMvf+WFGKcfBIcj+MsxJunRzjSPKQhJsnxvjzFdZewO5ZyLTA+MqsxQuluJaieJOWir0A5IfBUmLNhtZHSP1eCc3JiYUkhoRDExPrpQprp7E7zkgd+BCvr0rKMmxqSY8sPJqXQ9sMjFyV8NUyspTMHqsZvn7xI1y2WMwmuds5Cc+UF9Pn5vUmfuArFWQdw+5H8OwTpw7kRMbpH3rEa0Cqu6E9BqKeknDfBMXj/NznkWqK3ORRCUc+UapSpqhNmCYUvQY//rUKwv4Cu58CgU1TUARyVzlfyh97of2MkDkvS7hjcv6IJJaE2z5VGrxUQYq3sbsIUmhQcWrCOUtK0QLtAoC9EqYnJwWS6BKmJhZVv6+w9gfsfgVRxUzxcbVEoVCwMMf73aeUhIuhXYJ0npVw8+QkRJJNEvZOTMIPKqxdxe5PEEuQDuPwCOvL8LqH4/ZkGZlWfD3hF4G5JT7TyU/JavTn9Nj7PUtmlvlEN3vcx31Jd+poY93tRzf+RjzVcp+Jg3FSl8roeuHLuWBcY0FMaFyMoHhHWxz8mZHZ5b7pMfHtgI+5kNcFzU0QtZiG8Wcgf5EW4P0Nkp3Aw18fcd23uF3OP+aX/aaY0yRH51u2ZGz838foX2+/VVO34Qr/sgTm6Xj1ge8/f8/SZ64ED5wcOR5/eXfVL0+OrP7J2XejrT2PRqpHz/wXQ8evzJMZAAA=";
}
