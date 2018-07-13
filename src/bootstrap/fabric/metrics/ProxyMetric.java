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
    
    public void createAndActivateThresholdTreaty(double rate, double base,
                                                 long time, boolean proactive);
    
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
        
        public void createAndActivateThresholdTreaty(double rate, double base,
                                                     long time,
                                                     boolean proactive) {
            if (proactive) {
                term(0).createAndActivateThresholdTreaty(rate, base, time,
                                                         proactive);
            }
            else {
                createThresholdTreaty(
                  rate, base, time).update(
                                      false,
                                      fabric.worker.metrics.StatsMap.emptyStats(
                                                                       ));
            }
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
    
    public static final byte[] $classHash = new byte[] { 77, -82, -120, -31, 68,
    -49, -70, 108, 14, -40, -79, 65, 37, -37, -90, 6, 70, -122, -4, 98, -126,
    -124, -7, -38, 117, -95, -53, 99, 20, -46, -61, 74 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1531507184000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
