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
            return null;
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
    
    public static final byte[] $classHash = new byte[] { 70, -103, -18, -30, 94,
    82, -94, -3, -95, 78, -38, 104, -54, -112, -24, -5, -63, 59, 5, 98, 37, 102,
    -112, 118, 8, 118, 75, 24, 97, 105, -40, -40 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527113233000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
