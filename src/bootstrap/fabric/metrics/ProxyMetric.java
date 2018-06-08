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
    
    public static final byte[] $classHash = new byte[] { 60, -48, -1, -24, 9,
    -107, -89, -104, -1, 122, -35, -16, -63, 11, -117, 109, 45, 106, 105, 0, 3,
    -84, -115, 111, -76, -45, 70, 12, -114, 107, -29, -82 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1528498815000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
