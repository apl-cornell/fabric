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
    
    public static final byte[] $classHash = new byte[] { 59, 75, -90, 44, -32,
    -22, -111, 96, -15, 85, 38, 33, 33, 75, -40, 13, -52, -18, 25, -45, 111, 29,
    85, 62, -123, 9, -80, 114, 99, -21, 57, -57 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527094903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
