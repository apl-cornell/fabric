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
            return null;
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
    
    public static final byte[] $classHash = new byte[] { -36, 97, 41, -119, 71,
    -41, -19, -104, 125, -54, -4, 58, -103, 117, -128, -117, -109, -4, -17, 85,
    -87, 22, -23, 79, -57, -117, -65, 58, -52, -10, -11, 100 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527113233000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
