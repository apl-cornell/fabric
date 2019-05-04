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
    
    public double computePresetNTerm();
    
    public double computeValue(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeVelocity(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoise(fabric.worker.metrics.StatsMap weakStats);
    
    public double computeNoiseTerm(fabric.worker.metrics.StatsMap weakStats);
    
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      equalityPolicy(double value, long time,
                     fabric.worker.metrics.StatsMap weakStats,
                     final fabric.worker.Store s);
    
    public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
      thresholdPolicy(double rate, double base, long time,
                      fabric.worker.metrics.StatsMap weakStats,
                      final fabric.worker.Store s);
    
    public java.lang.String toString();
    
    public void createAndActivateTreaty(
      fabric.worker.metrics.treaties.statements.TreatyStatement stmt,
      boolean proactive);
    
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
        
        public double computePresetNTerm() { return term(0).getPresetNTerm(); }
        
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
        
        public double computeNoiseTerm(
          fabric.worker.metrics.StatsMap weakStats) {
            if (weakStats.containsKey((fabric.metrics.ProxyMetric)
                                        this.$getProxy()))
                return weakStats.getNoiseTerm((fabric.metrics.ProxyMetric)
                                                this.$getProxy());
            return this.term(0).noiseTerm(weakStats);
        }
        
        public fabric.worker.metrics.treaties.enforcement.EnforcementPolicy
          equalityPolicy(double value, long time,
                         fabric.worker.metrics.StatsMap weakStats,
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
          thresholdPolicy(double rate, double base, long time,
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
        
        public void createAndActivateTreaty(
          fabric.worker.metrics.treaties.statements.TreatyStatement stmt,
          boolean proactive) {
            if (proactive) {
                term(0).createAndActivateTreaty(stmt, proactive);
            } else {
                super.createAndActivateTreaty(stmt, proactive);
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
    
    public static final byte[] $classHash = new byte[] { -47, -45, 75, 11, -1,
    -2, 87, 101, -124, 126, 22, 110, 98, 17, 76, -70, 120, 22, 53, 24, -11, -38,
    -68, 43, 105, 33, -25, -5, 91, 35, -3, -67 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556814932000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
