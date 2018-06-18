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
    
    public static final byte[] $classHash = new byte[] { -26, 94, -33, 75, 0,
    -88, 71, -48, -67, 124, -62, 44, 125, -54, -2, 58, -36, 79, -108, 12, -48,
    -78, -23, 114, 103, -59, 75, -33, 22, 107, -4, -99 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1529349674000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
