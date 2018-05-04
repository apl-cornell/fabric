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
 * of either a {@link Metric} or a set of {@link MetricEqualityContract}s and can be
 * observed by other {@link Contract}s.
 */
public interface MetricEqualityContract
  extends fabric.metrics.contracts.Contract {
    public fabric.metrics.Metric get$metric();
    
    public fabric.metrics.Metric set$metric(fabric.metrics.Metric val);
    
    public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics();
    
    public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
      fabric.worker.metrics.ImmutableMetricsVector val);
    
    public double get$value();
    
    public double set$value(double val);
    
    public double postInc$value();
    
    public double postDec$value();
    
    /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param value
   *        the value of the equality
   * @param base
   *        the base of the bound this {@link MetricEqualityContract} asserts on
   *        metric.
   */
    public fabric.metrics.contracts.MetricEqualityContract
      fabric$metrics$contracts$MetricEqualityContract$(
      fabric.metrics.Metric metric, double value);
    
    /** @return the {@link Metric} that this contract observes. */
    public fabric.metrics.Metric getMetric();
    
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
      implements fabric.metrics.contracts.MetricEqualityContract {
        public fabric.metrics.Metric get$metric() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).get$metric();
        }
        
        public fabric.metrics.Metric set$metric(fabric.metrics.Metric val) {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).set$metric(val);
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector get$leafMetrics() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).get$leafMetrics();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$leafMetrics(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).set$leafMetrics(val);
        }
        
        public double get$value() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).get$value();
        }
        
        public double set$value(double val) {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).set$value(val);
        }
        
        public double postInc$value() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).postInc$value();
        }
        
        public double postDec$value() {
            return ((fabric.metrics.contracts.MetricEqualityContract._Impl)
                      fetch()).postDec$value();
        }
        
        public fabric.metrics.contracts.MetricEqualityContract
          fabric$metrics$contracts$MetricEqualityContract$(
          fabric.metrics.Metric arg1, double arg2) {
            return ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              fabric$metrics$contracts$MetricEqualityContract$(arg1, arg2);
        }
        
        public fabric.metrics.Metric getMetric() {
            return ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              getMetric();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return ((fabric.metrics.contracts.MetricEqualityContract) fetch()).
              getLeafSubjects();
        }
        
        public _Proxy(MetricEqualityContract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.contracts.Contract._Impl
      implements fabric.metrics.contracts.MetricEqualityContract {
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
        
        public double get$value() { return this.value; }
        
        public double set$value(double val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public double postInc$value() {
            double tmp = this.get$value();
            this.set$value((double) (tmp + 1));
            return tmp;
        }
        
        public double postDec$value() {
            double tmp = this.get$value();
            this.set$value((double) (tmp - 1));
            return tmp;
        }
        
        public double value;
        
        /**
   * @param metric
   *        the {@link Metric} this contract asserts a bound on
   * @param value
   *        the value of the equality
   * @param base
   *        the base of the bound this {@link MetricEqualityContract} asserts on
   *        metric.
   */
        public fabric.metrics.contracts.MetricEqualityContract
          fabric$metrics$contracts$MetricEqualityContract$(
          fabric.metrics.Metric metric, double value) {
            this.set$metric(metric);
            this.set$value((double) value);
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
            return (fabric.metrics.contracts.MetricEqualityContract)
                     this.$getProxy();
        }
        
        /** @return the {@link Metric} that this contract observes. */
        public fabric.metrics.Metric getMetric() { return this.get$metric(); }
        
        public void activate() { refresh(false); }
        
        private static void static_activate(
          fabric.metrics.contracts.MetricEqualityContract tmp) {
            if (fabric.worker.transaction.TransactionManager.getInstance().
                  inTxn()) {
                tmp.set$currentPolicy(
                      tmp.get$metric().equalityPolicy(tmp.get$value(), false,
                                                      tmp.$getStore()));
            }
            else {
                {
                    fabric.worker.transaction.TransactionManager $tm524 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled527 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff525 = 1;
                    boolean $doBackoff526 = true;
                    boolean $retry521 = true;
                    $label519: for (boolean $commit520 = false; !$commit520; ) {
                        if ($backoffEnabled527) {
                            if ($doBackoff526) {
                                if ($backoff525 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff525);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e522) {
                                            
                                        }
                                    }
                                }
                                if ($backoff525 < 5000) $backoff525 *= 2;
                            }
                            $doBackoff526 = $backoff525 <= 32 || !$doBackoff526;
                        }
                        $commit520 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            tmp.set$currentPolicy(
                                  tmp.get$metric().equalityPolicy(
                                                     tmp.get$value(), false,
                                                     tmp.$getStore()));
                        }
                        catch (final fabric.worker.RetryException $e522) {
                            $commit520 = false;
                            continue $label519;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e522) {
                            $commit520 = false;
                            fabric.common.TransactionID $currentTid523 =
                              $tm524.getCurrentTid();
                            if ($e522.tid.isDescendantOf($currentTid523))
                                continue $label519;
                            if ($currentTid523.parent != null) {
                                $retry521 = false;
                                throw $e522;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e522) {
                            $commit520 = false;
                            if ($tm524.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid523 =
                              $tm524.getCurrentTid();
                            if ($e522.tid.isDescendantOf($currentTid523)) {
                                $retry521 = true;
                            }
                            else if ($currentTid523.parent != null) {
                                $retry521 = false;
                                throw $e522;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e522) {
                            $commit520 = false;
                            if ($tm524.checkForStaleObjects())
                                continue $label519;
                            $retry521 = false;
                            throw new fabric.worker.AbortException($e522);
                        }
                        finally {
                            if ($commit520) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e522) {
                                    $commit520 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e522) {
                                    $commit520 = false;
                                    fabric.common.TransactionID $currentTid523 =
                                      $tm524.getCurrentTid();
                                    if ($currentTid523 != null) {
                                        if ($e522.tid.equals($currentTid523) ||
                                              !$e522.tid.isDescendantOf(
                                                           $currentTid523)) {
                                            throw $e522;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit520 && $retry521) {
                                {  }
                                continue $label519;
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
                    fabric.worker.transaction.TransactionManager $tm533 =
                      fabric.worker.transaction.TransactionManager.getInstance(
                                                                     );
                    boolean $backoffEnabled536 =
                      fabric.worker.Worker.getWorker().config.txRetryBackoff;
                    int $backoff534 = 1;
                    boolean $doBackoff535 = true;
                    boolean $retry530 = true;
                    $label528: for (boolean $commit529 = false; !$commit529; ) {
                        if ($backoffEnabled536) {
                            if ($doBackoff535) {
                                if ($backoff534 > 32) {
                                    while (true) {
                                        try {
                                            java.lang.Thread.sleep($backoff534);
                                            break;
                                        }
                                        catch (java.lang.
                                                 InterruptedException $e531) {
                                            
                                        }
                                    }
                                }
                                if ($backoff534 < 5000) $backoff534 *= 2;
                            }
                            $doBackoff535 = $backoff534 <= 32 || !$doBackoff535;
                        }
                        $commit529 = true;
                        fabric.worker.transaction.TransactionManager.
                          getInstance().startTransaction();
                        try {
                            fabric.metrics.contracts.Contract._Impl.
                              static_activate(tmp);
                            tmp.getMetric().addContract(tmp);
                        }
                        catch (final fabric.worker.RetryException $e531) {
                            $commit529 = false;
                            continue $label528;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e531) {
                            $commit529 = false;
                            fabric.common.TransactionID $currentTid532 =
                              $tm533.getCurrentTid();
                            if ($e531.tid.isDescendantOf($currentTid532))
                                continue $label528;
                            if ($currentTid532.parent != null) {
                                $retry530 = false;
                                throw $e531;
                            }
                            throw new InternalError(
                                    "Something is broken with " +
                                        "transaction management. Got a signal to restart a " +
                                        "different transaction than the one being managed.");
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e531) {
                            $commit529 = false;
                            if ($tm533.checkForStaleObjects()) continue;
                            fabric.common.TransactionID $currentTid532 =
                              $tm533.getCurrentTid();
                            if ($e531.tid.isDescendantOf($currentTid532)) {
                                $retry530 = true;
                            }
                            else if ($currentTid532.parent != null) {
                                $retry530 = false;
                                throw $e531;
                            }
                            else {
                                throw new InternalError(
                                        "Something is broken with transaction " +
                                            "management. Got a signal for a lock conflict in a different " +
                                            "transaction than the one being managed.");
                            }
                        }
                        catch (final Throwable $e531) {
                            $commit529 = false;
                            if ($tm533.checkForStaleObjects())
                                continue $label528;
                            $retry530 = false;
                            throw new fabric.worker.AbortException($e531);
                        }
                        finally {
                            if ($commit529) {
                                try {
                                    fabric.worker.transaction.TransactionManager.
                                      getInstance().commitTransaction();
                                }
                                catch (final fabric.worker.
                                         AbortException $e531) {
                                    $commit529 = false;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e531) {
                                    $commit529 = false;
                                    fabric.common.TransactionID $currentTid532 =
                                      $tm533.getCurrentTid();
                                    if ($currentTid532 != null) {
                                        if ($e531.tid.equals($currentTid532) ||
                                              !$e531.tid.isDescendantOf(
                                                           $currentTid532)) {
                                            throw $e531;
                                        }
                                    }
                                }
                            }
                            else {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().abortTransaction();
                            }
                            if (!$commit529 && $retry530) {
                                {  }
                                continue $label528;
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
            long currentTime = java.lang.System.currentTimeMillis();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                long curExpiry = this.get$currentPolicy().expiry();
                if (curExpiry >= currentTime) {
                    this.get$currentPolicy().
                      apply((fabric.metrics.contracts.MetricEqualityContract)
                              this.$getProxy());
                    return update(curExpiry, asyncExtension);
                }
            }
            if (asyncExtension) return false;
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            tm.markCoordination();
            fabric.metrics.contracts.enforcement.EnforcementPolicy oldPolicy =
              this.get$currentPolicy();
            fabric.metrics.contracts.enforcement.EnforcementPolicy newPolicy =
              this.get$metric().equalityPolicy(this.get$value(), $getStore());
            newPolicy.activate();
            long newExpiry = newPolicy.expiry();
            if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null))
                oldPolicy.unapply(
                            (fabric.metrics.contracts.MetricEqualityContract)
                              this.$getProxy());
            boolean result = update(newExpiry, asyncExtension);
            if (newExpiry >= currentTime) {
                this.set$currentPolicy(newPolicy);
                this.get$currentPolicy().
                  apply((fabric.metrics.contracts.MetricEqualityContract)
                          this.$getProxy());
            } else {
                this.set$currentPolicy(null);
            }
            return result;
        }
        
        public boolean implies(fabric.metrics.Metric otherMetric,
                               double otherRate, double otherBase) {
            if (!getMetric().equals(otherMetric) ||
                  !fabric.metrics.contracts.Bound._Impl.
                  test(otherRate, otherBase, this.get$value(),
                       java.lang.System.currentTimeMillis()))
                return false;
            return valid();
        }
        
        public java.lang.String toString() {
            return ((java.lang.Comparable)
                      fabric.lang.WrappedJavaInlineable.$unwrap(getMetric())).
              toString() + " == " + this.get$value() + " until " + getExpiry();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector getLeafSubjects() {
            return this.get$leafMetrics();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.MetricEqualityContract._Proxy(
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
            $writeInline(out, this.leafMetrics);
            out.writeDouble(this.value);
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
            this.leafMetrics = (fabric.worker.metrics.ImmutableMetricsVector)
                                 in.readObject();
            this.value = in.readDouble();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.MetricEqualityContract._Impl src =
              (fabric.metrics.contracts.MetricEqualityContract._Impl) other;
            this.metric = src.metric;
            this.leafMetrics = src.leafMetrics;
            this.value = src.value;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.MetricEqualityContract._Static {
            public _Proxy(fabric.metrics.contracts.MetricEqualityContract.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.MetricEqualityContract.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  MetricEqualityContract.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    MetricEqualityContract.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.MetricEqualityContract._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.MetricEqualityContract._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.MetricEqualityContract._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
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
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.MetricEqualityContract.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -42, 120, -87, 103,
    -103, -1, 108, 38, 5, -23, -33, -16, -22, 125, -81, 70, -26, -127, 28, -48,
    -88, 116, -75, -47, 5, 38, 52, -88, -83, -81, 116, 127 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525364618000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
