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
    
    public static final byte[] $classHash = new byte[] { -10, 82, -111, -16,
    -11, -107, -80, 12, -94, 107, 22, 100, 111, 63, -114, -5, -97, -37, -19,
    103, -87, 104, -58, 105, -103, -51, -126, 108, 43, -5, -68, 4 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1528821850000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
