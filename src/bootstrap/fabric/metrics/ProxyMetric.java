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
    
    public static final byte[] $classHash = new byte[] { -41, -62, -98, 10, 45,
    -64, -127, 49, 60, 115, 85, -109, -123, -119, 76, 46, 119, 85, -93, 118,
    -120, -110, -23, 59, 47, 108, -33, 11, 41, 78, -71, 26 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1528821850000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
