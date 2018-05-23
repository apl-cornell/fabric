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
    
    public static final byte[] $classHash = new byte[] { 31, 106, -125, 57, 70,
    53, -3, -113, 27, 65, 95, -18, -14, 115, -67, 90, 81, -91, -14, -60, 50, 49,
    -54, -122, -102, 25, 108, 98, 101, 105, -73, -6 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527113233000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
