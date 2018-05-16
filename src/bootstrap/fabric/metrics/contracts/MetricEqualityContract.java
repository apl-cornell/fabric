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
import fabric.worker.metrics.ImmutableSet;
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
                tmp.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(tmp.get$currentPolicy()));
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
                            tmp.
                              set$$associated(
                                fabric.worker.metrics.ImmutableSet.emptySet().
                                    add(tmp.get$currentPolicy()));
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
                this.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(this.get$currentPolicy()));
                this.get$currentPolicy().
                  apply((fabric.metrics.contracts.MetricEqualityContract)
                          this.$getProxy());
            } else {
                this.set$currentPolicy(null);
                this.set$$associated(null);
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
    
    public static final byte[] $classHash = new byte[] { 119, 70, -22, 61, 108,
    103, 29, -94, -17, -112, -100, 65, -116, -127, 126, -124, 92, -119, -34,
    -30, 30, 76, 120, 90, 48, 30, -36, -9, -125, -64, -26, 68 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526344812000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
