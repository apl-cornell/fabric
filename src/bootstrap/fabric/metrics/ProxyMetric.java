package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Store;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.proxies.ProxyMap;
import fabric.worker.metrics.treaties.MetricTreaty;
import fabric.worker.metrics.treaties.enforcement.EnforcementPolicy;
import fabric.worker.metrics.treaties.enforcement.WitnessPolicy;
import fabric.common.exceptions.InternalError;

/**
 * A {@link DerivedMetric} that exists purely to proxy for another metric.
 */
public interface ProxyMetric extends fabric.metrics.DerivedMetric {
    public fabric.metrics.ProxyMetric fabric$metrics$ProxyMetric$(
      fabric.metrics.Metric primary);
    
    public fabric.metrics.Metric getProxy(fabric.worker.Store s);
    
    public double computePresetR();
    
    public double computePresetB();
    
    public double computePresetV();
    
    public double computePresetN();
    
    public double computeValue(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoise(fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      equalityPolicy(double value, fabric.worker.metrics.StatsMap weakStats,
                     final fabric.worker.Store s);
    
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base,
                      fabric.worker.metrics.StatsMap weakStats,
                      final fabric.worker.Store s);
    
    public java.lang.String toString();
    
    public static class _Proxy extends fabric.metrics.DerivedMetric._Proxy
      implements fabric.metrics.ProxyMetric {
        public fabric.metrics.ProxyMetric fabric$metrics$ProxyMetric$(
          fabric.metrics.Metric arg1) {
            return ((fabric.metrics.ProxyMetric) fetch()).
              fabric$metrics$ProxyMetric$(arg1);
        }
        
        public _Proxy(ProxyMetric._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.DerivedMetric._Impl
      implements fabric.metrics.ProxyMetric {
        public fabric.metrics.ProxyMetric fabric$metrics$ProxyMetric$(
          fabric.metrics.Metric primary) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(primary)) instanceof fabric.metrics.ProxyMetric) {
                primary =
                  ((fabric.metrics.ProxyMetric)
                     fabric.lang.Object._Proxy.$getProxy(primary)).term(0);
            }
            fabric$metrics$DerivedMetric$(
              new fabric.metrics.Metric[] { primary });
            initialize();
            return (fabric.metrics.ProxyMetric) this.$getProxy();
        }
        
        public fabric.metrics.Metric getProxy(fabric.worker.Store s) {
            if ($getStore().equals(s))
                return (fabric.metrics.ProxyMetric) this.$getProxy();
            return term(0).getProxy(s);
        }
        
        public double computePresetR() { return term(0).getPresetR(); }
        
        public double computePresetB() { return term(0).getPresetB(); }
        
        public double computePresetV() { return term(0).getPresetV(); }
        
        public double computePresetN() { return term(0).getPresetN(); }
        
        public double computeValue(fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey((fabric.metrics.ProxyMetric)
                                        this.$getProxy()))
                return weakStats.getValue((fabric.metrics.ProxyMetric)
                                            this.$getProxy());
            return this.term(0).value(weakStats);
        }
        
        public double computeVelocity(
          fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey((fabric.metrics.ProxyMetric)
                                        this.$getProxy()))
                return weakStats.getVelocity((fabric.metrics.ProxyMetric)
                                               this.$getProxy());
            return this.term(0).velocity(weakStats);
        }
        
        public double computeNoise(fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey((fabric.metrics.ProxyMetric)
                                        this.$getProxy()))
                return weakStats.getNoise((fabric.metrics.ProxyMetric)
                                            this.$getProxy());
            return this.term(0).noise(weakStats);
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          equalityPolicy(double value, fabric.worker.metrics.StatsMap weakStats,
                         final fabric.worker.Store s) {
            return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                     new fabric.worker.metrics.treaties.MetricTreaty[] { term(
                                                                           0).
                         getEqualityTreaty(value) });
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store s) {
            return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                     new fabric.worker.metrics.treaties.MetricTreaty[] { term(
                                                                           0).
                         getThresholdTreaty(rate, base) });
        }
        
        public java.lang.String toString() {
            return "Proxy at " +
            $getStore().toString() +
            " for " +
            ((java.lang.Comparable)
               fabric.lang.WrappedJavaInlineable.$unwrap(term(0))).toString();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.ProxyMetric._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.metrics.treaties.TreatySet treaties,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, treaties, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.ProxyMetric._Static {
            public _Proxy(fabric.metrics.ProxyMetric._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.ProxyMetric._Static $instance;
            
            static {
                fabric.
                  metrics.
                  ProxyMetric.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.ProxyMetric._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.ProxyMetric._Static._Impl.class);
                $instance = (fabric.metrics.ProxyMetric._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.ProxyMetric._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, treaties, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.ProxyMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 118, 105, -70, -48, 59,
    109, 76, 90, 84, 100, -68, -28, -41, -94, -31, 90, 22, -125, 99, 40, 106,
    -44, -11, -79, 64, 87, 100, -125, 53, 117, -127, -114 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1529351168000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
