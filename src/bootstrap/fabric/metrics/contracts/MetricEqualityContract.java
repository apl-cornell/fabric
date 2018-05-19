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
    
    /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
    public boolean refresh(boolean asyncExtension,
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
        
        /**
   * Update this contract's expiration time to stay valid in response to a
   * change in the value of the {@link Subject}s used for enforcing this
   * {@link Contract}. Revokes, extends, and updates the enforcement strategy
   * as needed.
   */
        public boolean refresh(boolean asyncExtension,
                               fabric.worker.metrics.StatsMap weakStats) {
            long currentTime = java.lang.System.currentTimeMillis();
            if (!fabric.lang.Object._Proxy.idEquals(this.get$currentPolicy(),
                                                    null)) {
                long curExpiry = this.get$currentPolicy().expiry(weakStats);
                if (curExpiry >= currentTime) {
                    this.get$currentPolicy().
                      apply((fabric.metrics.contracts.MetricEqualityContract)
                              this.$getProxy());
                    return update(curExpiry, asyncExtension);
                }
            }
            if (asyncExtension) return false;
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            tm.markCoordination();
            fabric.metrics.contracts.enforcement.EnforcementPolicy oldPolicy =
              this.get$currentPolicy();
            fabric.metrics.contracts.enforcement.EnforcementPolicy newPolicy =
              this.get$metric().equalityPolicy(this.get$value(), weakStats,
                                               $getStore());
            newPolicy.activate(weakStats);
            long newExpiry = newPolicy.expiry(weakStats);
            if (!fabric.lang.Object._Proxy.idEquals(oldPolicy, null))
                oldPolicy.unapply(
                            (fabric.metrics.contracts.MetricEqualityContract)
                              this.$getProxy());
            boolean result = update(newExpiry, asyncExtension);
            if (newExpiry >= currentTime) {
                this.set$currentPolicy(newPolicy);
                this.
                  set$$associated(
                    fabric.worker.metrics.ImmutableSet.emptySet().
                        add(this.get$currentPolicy()));
                this.get$currentPolicy().
                  apply((fabric.metrics.contracts.MetricEqualityContract)
                          this.$getProxy());
            } else {
                this.set$currentPolicy(null);
                this.set$$associated(null);
            }
            return result;
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
    
    public static final byte[] $classHash = new byte[] { 63, -87, 46, -86, -32,
    -104, -68, 125, 66, -117, 61, -36, -111, 97, 58, 50, 55, 77, 75, -75, 54,
    107, -22, -8, -7, -32, -103, 49, -4, -24, -109, 108 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526755971000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
