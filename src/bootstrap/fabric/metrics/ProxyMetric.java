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
                return new fabric.metrics.ProxyMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 7, -5, -2, 15, 65, 16,
    -31, -6, 30, -34, 81, -10, -69, -48, 96, -38, 19, -1, -50, -67, 54, -93,
    -107, 29, -35, -73, 97, 83, 126, -17, -39, 9 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527284577000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
