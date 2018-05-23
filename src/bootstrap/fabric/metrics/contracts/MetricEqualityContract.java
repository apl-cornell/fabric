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
    
    public static final byte[] $classHash = new byte[] { -102, -96, 53, -85, 82,
    -4, 111, 74, 63, 106, -35, -50, 39, -33, -104, -122, 9, 1, -8, 106, 52, 9,
    4, 67, 94, -53, -64, -53, 46, -89, -62, 0 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527113233000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
