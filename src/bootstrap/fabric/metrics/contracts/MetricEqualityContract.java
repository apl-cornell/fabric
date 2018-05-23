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
    
    public static final byte[] $classHash = new byte[] { -47, 14, -12, 74, 123,
    -76, 85, 61, -119, 124, 78, 80, -64, 4, 84, 54, -80, -57, -42, -17, 2, 67,
    14, 9, -120, 35, 48, -93, -122, -55, 77, -107 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527094903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
