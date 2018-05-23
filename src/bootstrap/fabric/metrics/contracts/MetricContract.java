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
    
    /**
   * @return a new policy to enforce this treaty.
   */
    public fabric.metrics.contracts.enforcement.EnforcementPolicy getNewPolicy(
      fabric.worker.metrics.StatsMap weakStats);
    
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
        
        /**
   * @return a new policy to enforce this treaty.
   */
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          getNewPolicy(fabric.worker.metrics.StatsMap weakStats) {
            return this.get$metric().thresholdPolicy(this.get$rate(),
                                                     this.get$base(), weakStats,
                                                     this.$getStore());
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
                return new fabric.metrics.contracts.MetricContract._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -5, 71, -94, -98, -71,
    -11, -46, 106, 5, -38, -42, 38, 88, 64, -28, 52, 5, -125, 106, -98, -26, 11,
    43, 7, 0, 109, -96, 72, -100, -68, -35, 85 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527094903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
