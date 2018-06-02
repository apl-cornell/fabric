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
    
    public static final byte[] $classHash = new byte[] { 33, -29, -62, 43, 14,
    39, 80, -16, 77, 15, 123, 106, -13, -117, 102, 1, -16, 54, -16, 49, -5, -80,
    -69, -48, 25, -54, -51, 33, 89, -49, -112, 99 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527882698000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
