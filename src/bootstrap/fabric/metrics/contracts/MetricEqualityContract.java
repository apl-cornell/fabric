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
import fabric.worker.metrics.StatsMap;
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
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy getNewPolicy(
      fabric.worker.metrics.StatsMap weakStats);
    
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
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          getNewPolicy(fabric.worker.metrics.StatsMap weakStats) {
            return this.get$metric().equalityPolicy(this.get$value(), weakStats,
                                                    this.$getStore());
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
    
    public static final byte[] $classHash = new byte[] { -91, -128, 25, -95, 88,
    127, 46, 116, 28, -25, -2, -106, -84, 109, -60, -62, 97, -79, -103, 50,
    -124, 123, 63, -24, -72, 115, -20, -34, 125, 85, -73, -26 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527094903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
