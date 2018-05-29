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
    
    public static final byte[] $classHash = new byte[] { 110, -39, -90, 5, 88,
    -51, 114, 12, 69, 107, -45, -127, 6, -20, 102, 43, 47, -41, 79, 64, -82, 21,
    -67, -30, 126, 28, 40, -109, -121, -85, 113, 119 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527629388000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
