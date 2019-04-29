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
    
    public static final byte[] $classHash = new byte[] { 44, -72, 77, 98, 103,
    96, 86, 100, -55, 63, -77, 9, 87, -65, -97, -48, 112, 44, -28, -89, 112, 54,
    99, -49, -124, 21, 110, -35, 105, -91, 46, -82 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1556553199000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0YC2wUx3XufP6c7WBjME4MGNu4SIC5E02VljpRgt2Ar5yN5QMajIKztztnL97bXWbn7HNSKhqlArWppTYOJVEgqgpKGygoqVCrpjTpP4iqTav+ojQNlUBNRVFFq6aN1Ja+Nzv38d4Hn9ST5r29mfdm3n8+Z2+QaoeR7oQS140Qn7WpE9qmxCPREYU5VBswFMfZBb3jakMgcuzdF7QOP/FHSaOqmJapq4oxbjqcLIkeUKaVsEl5ePdopG8fCarIOKg4k5z49/WnGem0LWN2wrC4XKRg/qc3hue/tL/55SrSNEaadDPGFa6rA5bJaZqPkcYkTcYpc7ZqGtXGyFKTUi1Gma4Y+qNAaJljpMXRJ0yFpxh1RqljGdNI2OKkbMrEmplOFN8CsVlK5RYD8Ztd8VNcN8JR3eF9UVKT0KmhOQfJp0ggSqoThjIBhCuiGS3CYsbwNuwH8nodxGQJRaUZlsCUbmqcrPFyZDXu2QEEwFqbpHzSyi4VMBXoIC2uSIZiToRjnOnmBJBWWylYhZP2kpMCUZ2tqFPKBB3n5E4v3Yg7BFRBYRZk4aTVSyZmAp+1e3yW560bw/fOPWYOmn7iA5k1qhoofx0wdXiYRmmCMmqq1GVs3BA9pqy4eNRPCBC3eohdmm9+8uYDvR2vve7SrCxCszN+gKp8XD0VX/KLVQPrt1ShGHW25egYCgs0F14dkSN9aRuifUV2RhwMZQZfG/3x3sMv0ut+Uh8hNaplpJIQVUtVK2nrBmXbqUmZwqkWIUFqagNiPEJq4Tuqm9Tt3ZlIOJRHSMAQXTWW+A8mSsAUaKJa+NbNhJX5thU+Kb7TNiGkFhrxQTtASOOrgJcT4v8+J8PhSStJw3EjRWcgvMPQqMLUyTDkLdPVTaplz4YdpoZZyuQ6ULr9YQglQOB4ZqVnh8SfEEhi/99nTKMOzTM+H5h3jWppNK444CsZN/0jBqTGoGVolI2rxtzFCFl28RkRO0GMdwdiVljHB/5e5a0U+bzzqf4Hb54bv+zGHfJK40FWuBKGpIShPAlBqEbMpxBUqBBUqLO+dGjgZOSMCJsaR+RXdp5GmOejtqHwhMWSaeLzCaWWC34RL+DtKagiUCga18ce/vgjR7urIFDtmQD6Dkh7vGmTKzYR+FIgF8bVpiPvvnf+2CErl0Cc9BTkdSEn5mW310LMUqkGdS83/YZO5cL4xUM9fqwpQSh3XIGAhNrR4V1jQX72ZWodWqM6ShrQBoqBQ5kCVc8nmTWT6xGeX4KgxQ0CNJZHQFEm74vZJ373sz/fLTaQTEVtyiu9Mcr78rIYJ2sS+bo0Z/tdjFKge/v4yFNP3ziyTxgeKNYWW7AH4QBkrwJpa7HPvH7wzXf+cOpX/pyzOKmxU3FDV9NCl6W34OeD9l9smIrYgRgK8oAsA53ZOmDjyutyskFFMKAqgehOz24zaWl6QlfiBsVI+XfTBzZf+Mtcs+tuA3pc4zHSe/sJcv139ZPDl/f/s0NM41NxR8rZL0fmlrlluZm3MqbMohzpT/9y9TM/UU5A5EORcvRHqag7RNiDCAd+UNhik4CbPWMfQtDtWmuV7Bd/1gq4DsF617b4uUHalchfjaxo35P4FRxdZiNcvnBORlaX2nzExnnq8fmT2s7Tm90tomVhQX/QTCW//pv//DR0/MqlIoUiyC17k0GnqZG35h2wZFfBKWhI7M25tLpyffWWgalrE+6yazwieqm/NnT20vZ16hf9pCqb4wUHgoVMffnCQrIxCucZE9XGnnrhhM6sUYNorAegrSCkapOL/e/lGVVmpPAQgg9nWf3IWidZ/iHxX73+yEWBT5Y3/N8KZwZPsXXrLA62izW3lwmiHQj6OVnpztEj5+jJK9g9ObnvX6htF7RuQgKflzhVmbbIwiU2F63tMqntjMWmKAvFoJq41e8u73YhRBgto/xDCIY4qZugXCiM/weLadoJbT0h1W0Sk8o0BZbALYnfL61pvmzjZcYUBGNw1MfjEBxDR/DAwEedwgPfCNOTUMCn5YGPHp3/7K3Q3LybiO6peG3BwTSfxz0Zi1XvQLARy0FXuVUEx7Y/nT/0ylcPHfFLifugumsWlHdayrohsKot8d7KrIssD0k8ujjrmmXGBLPutW4/9paU/m5Y+pLEL1cmPbK8JPGZxUk/U2ZMxDDzSr+nrPQfgd2gW+KGyqRHlnqJA4uT/nCZsccRPOaVfriY9I3ENRq5D5aek3iihPQFOyLsPDazOGzRVEsvVKtBzpWQ+OFFl6aOhaUpU4/x9uwMKXaZKvW5Mjb5AoInOF6UhU32KHBPKGmRe6D1w+Vlh8RdZfx5pFBxZOmUuO22iuPfJ8Wsx8so8CyCp+B8mVGAGpaq89myXh2E+Gp0ce21ynRAlqsSv1WBDl8uo8NXEDyXc8KwpTtFnSCSikIbBgXel/hEZUmFLM9JfKy0AgEhXyAnhdjzN7rhnOBwtLXcq9STCGJi4RfL6HgewWlIPnowpRjgoBELjuOzmei+t3h0c0YVrsMuQOEmzVSapCaHU1/2O2+SgtgvZjsGbQ8h9ftcHPxbZbZDlpsSXy9tu2qhd7WwXRac8Rjr22WM9R0EFyCo4QpGnUm4EruKYvdLxfRqh7YfAnRK4rHK9EKWvRLHFldof1Bm7EcIvgtHH265T1oZLzeLiwq+dYXyBhblOQhXokIKd7m44feVaYgsb0n869Ia+oUW/oy8W24TlXCU5yIOHbyvKnw2lu0orZgnkWrjlmVQxRRKvFHGqm8iuMxJm4pL0a2mthXugNPw6a5dNEunLV1Lc9KQd+TGG+PKIi838jVRHfghPXVtR29riVebOwvedyXfuZNNdW0nd/9WvERkXwqDcNFPpAwj766Tf++psRlN6EK/oPvAYAv0R6gUC28eXDyg4pdQ7h2X7iqc/Vw6/HdNOLVdgIwPV3kuMB+jDA6Tmvce055i+IB99u9t/6qp23VFvB2A5Tt7vzUUn3hkj3bp/m8EP/Hq82/YvVdfsO9Rf/5Eq/m2fjp07n/v1a37WBcAAA==";
}
