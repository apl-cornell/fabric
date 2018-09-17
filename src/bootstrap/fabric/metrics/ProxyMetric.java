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
        
        public void createAndActivateThresholdTreaty(double arg1, double arg2,
                                                     long arg3, boolean arg4) {
            ((fabric.metrics.ProxyMetric) fetch()).
              createAndActivateThresholdTreaty(arg1, arg2, arg3, arg4);
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
                term(0).
                  createAndActivateTreaty(
                    new fabric.worker.metrics.treaties.statements.ThresholdStatement(
                      rate, base, time), proactive);
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
                     fabric.worker.metrics.ImmutableObjectSet associates,
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
            super(store, onum, version, associates, observers, treaties,
                  labelStore, labelOnum, accessPolicyStore, accessPolicyOnum,
                  in, refTypes, intraStoreRefs, interStoreRefs);
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
                         fabric.worker.metrics.ImmutableObjectSet associates,
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
                super(store, onum, version, associates, observers, treaties,
                      labelStore, labelOnum, accessPolicyStore,
                      accessPolicyOnum, in, refTypes, intraStoreRefs,
                      interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.ProxyMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -8, 40, -47, 69, 65,
    -100, -102, -21, 19, -33, -101, -18, 103, -23, -95, -96, 35, 126, 17, -18,
    68, -1, 44, 30, -21, 87, -53, -75, -115, -67, -35, 30 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1537039040000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
