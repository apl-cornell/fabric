package fabric.metrics;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Store;
import fabric.worker.metrics.StatsMap;
import fabric.worker.metrics.proxies.ProxyMap;
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
                fabric.worker.metrics.treaties.statements.EqualityStatement.
                    create(value));
            return fabric.worker.metrics.treaties.enforcement.WitnessPolicy.
              create(witnesses);
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
                fabric.worker.metrics.treaties.statements.ThresholdStatement.
                    create(rate, base));
            return fabric.worker.metrics.treaties.enforcement.WitnessPolicy.
              create(witnesses);
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
                    fabric.worker.metrics.treaties.statements.ThresholdStatement.
                        create(rate, base, time),
                    proactive);
            } else {
                createThresholdTreaty(rate, base, time);
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
    
    public static final byte[] $classHash = new byte[] { 41, 69, 22, 57, 33,
    -63, -112, -41, -43, 99, -103, 108, -58, 19, 36, 109, 3, 50, 70, -110, -10,
    -93, -44, 42, 106, -77, -40, 72, 104, -102, -75, -104 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549232391000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
