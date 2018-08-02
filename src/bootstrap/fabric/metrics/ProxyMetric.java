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
import fabric.worker.metrics.treaties.statements.EqualityStatement;
import fabric.worker.metrics.treaties.statements.TreatyStatement;
import fabric.worker.metrics.treaties.statements.ThresholdStatement;
import com.google.common.collect.Multimap;
import com.google.common.collect.HashMultimap;
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
            com.google.common.collect.Multimap witnesses =
              com.google.common.collect.HashMultimap.create();
            witnesses.
              put(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(term(0)),
                new fabric.worker.metrics.treaties.statements.EqualityStatement(
                  value));
            return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                     witnesses);
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          thresholdPolicy(double rate, double base,
                          fabric.worker.metrics.StatsMap weakStats,
                          final fabric.worker.Store s) {
            com.google.common.collect.Multimap witnesses =
              com.google.common.collect.HashMultimap.create();
            witnesses.
              put(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.$unwrap(term(0)),
                new fabric.worker.metrics.treaties.statements.ThresholdStatement(
                  rate, base));
            return new fabric.worker.metrics.treaties.enforcement.WitnessPolicy(
                     witnesses);
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
    
    public static final byte[] $classHash = new byte[] { -65, 96, -77, -21, 81,
    -41, 63, 35, 58, -33, 44, 45, 6, 52, -46, -74, 107, 35, 64, -123, -104, 33,
    55, 120, 90, -3, 89, 106, 72, -21, 84, 109 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1533241119000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
