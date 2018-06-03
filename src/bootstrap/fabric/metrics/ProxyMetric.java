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
    
    public static final byte[] $classHash = new byte[] { 127, 9, -78, -68, 87,
    -3, -113, 111, -99, 5, -35, 80, -106, -52, -19, 57, 51, 97, -58, -125, 106,
    54, 66, 18, -127, -9, 46, 99, 20, 57, 43, -96 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527882698000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
