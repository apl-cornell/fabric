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
    
    public static final byte[] $classHash = new byte[] { -33, -82, -122, 40, 32,
    -85, -67, -92, -45, 106, 98, -65, 33, -119, -113, 52, -110, 17, 57, 57, 46,
    14, 87, 24, 56, -42, -8, 85, -33, 110, 110, -1 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527094903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
