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
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.ProxyMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -93, -67, -39, 104,
    -110, 38, 28, 60, 106, 99, 76, -52, -120, 72, -113, 67, 22, 124, -60, -78,
    -92, 0, 71, 32, -128, 96, -115, 111, 17, 58, 60, 32 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527874708000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
