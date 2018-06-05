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
    
    public static final byte[] $classHash = new byte[] { -32, 29, 94, -109, 70,
    -121, -1, 84, 87, -87, 10, -12, 11, -126, -122, -12, -4, -38, -31, 82, -1,
    56, -91, 96, -119, -117, -86, 6, 93, -46, -89, 72 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1528225149000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
