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
    
    public static final byte[] $classHash = new byte[] { 25, -6, -93, 21, -8,
    77, -30, 11, 81, 34, 57, 2, -89, -125, -121, 76, 75, 6, 2, 101, 82, 101, 31,
    70, 62, -128, 41, -33, -17, 55, 68, 80 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1551212262000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
