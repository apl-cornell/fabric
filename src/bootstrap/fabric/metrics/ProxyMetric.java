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
    
    public static final byte[] $classHash = new byte[] { -6, 34, 59, -123, 61,
    -48, -60, -99, -35, -78, 44, 84, 15, 40, 21, -62, 22, 90, -85, 88, -128, 7,
    -68, -16, -28, -44, -48, 15, -5, 50, -109, -14 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1532368898000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
