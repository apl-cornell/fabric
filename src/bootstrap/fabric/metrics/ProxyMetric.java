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
            this.set$$associates(this.get$$associates().add(primary));
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
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
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
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
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
    
    public static final byte[] $classHash = new byte[] { -70, 81, -78, -125, 10,
    17, 125, -89, -54, -96, 27, -8, -45, 118, 117, 28, 4, 5, 30, 4, 57, 46, -3,
    34, -1, 111, 46, -2, 60, 23, 93, 91 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260582000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
