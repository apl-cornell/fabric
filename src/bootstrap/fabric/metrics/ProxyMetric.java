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
    
    public static final byte[] $classHash = new byte[] { 74, 119, -87, -85, 91,
    101, -22, 60, -13, 112, 3, 82, -97, 39, 92, 5, -128, 57, -104, 106, 22, -19,
    -48, 117, 0, -5, -52, -18, -23, -89, 108, 81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
