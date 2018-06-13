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
    
    public static final byte[] $classHash = new byte[] { -15, -57, 35, 18, -23,
    -118, -1, -70, 23, -20, 43, -58, -6, 59, 67, -110, 25, 38, 38, -71, -56, 68,
    -13, 47, -68, 7, 15, -38, 25, 59, -39, 53 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1528903876000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0YC2wcxXXufP5c4saOQxLiOI4Tu1bzu2ugFNFLC/gg8TXnxLIT2joq7tzunL14b3czOxefoalSSpU0rSIBJoSqREINakvdIFEhRMFSVFUhUSooVVWg6idqiwqCCEVILUWl6Xuzcx/vffBJXWnm7c68N/P+82bnrpBGl5ONaZoyzIiYcZgb2UlTieQw5S7T4yZ13X0wOq4tDSVOvvUjvTtIgknSqlHLtgyNmuOWK8iy5D30EI1aTET3jyRiB0hYQ8JB6k4KEjwwkOOkx7HNmQnTFmqTsvUf2RKdffTu9mcaSNsYaTOsUUGFocVtS7CcGCOtGZZJMe7erutMHyPLLcb0UcYNahr3AqJtjZEO15iwqMhy5o4w1zYPIWKHm3UYl3vmB5F9G9jmWU3YHNhv99jPCsOMJg1XxJKkKW0wU3cPkm+QUJI0pk06AYirknkponLF6E4cB/QlBrDJ01RjeZLQlGHpgqz3UxQk7tsNCEDanGFi0i5sFbIoDJAOjyWTWhPRUcENawJQG+0s7CJIZ9VFAanFodoUnWDjglzvxxv2pgArLNWCJIKs9KPJlcBmnT6blVjryp4dJ+6zBq0gCQDPOtNM5L8FiLp9RCMszTizNOYRtm5OnqSr5o8FCQHklT5kD+e5r1+9bWv3uQseztoKOHtT9zBNjGtnUste7YpvuqUB2WhxbNdAV1ggubTqsJqJ5Rzw9lWFFXEykp88N3L+K0eeYu8EyZIEadJsM5sBr1qu2RnHMBnfxSzGqWB6goSZpcflfII0w3vSsJg3ujeddplIkJAph5ps+Q0qSsMSqKJmeDestJ1/d6iYlO85hxDSDI0EoH2JkPAlgG3w+RtBEtFJO8OiKTPLpsG9o9AY5dpkFOKWG1rU5VqUZy1hAJIaAi8CADbndm5mSH5EgAnn/7lYDjlvnw4EQKnrNVtnKeqChZS3DAybEBCDtqkzPq6ZJ+YTZMX8Y9JjwujlLniq1EkArNzlzw+ltLPZgTuvnh2/5Hkb0iqVQSx4HEYUh5ESDoGpVoyiCOSlCOSluUAuEj+d+Kl0liZXRlVhnVZY53OOSUXa5pkcCQSkUNdJeuklYOMpyB2QHlo3jX71i187trEB3NOZDqHFALXPHyzFFJOANwoRMK61HX3rn0+fPGwXw0aQvrJoLqfEaNzo1xC3NaZDtisuv7mHPjs+f7gviJkkDElOUHBDyBjd/j0WRGUsn+FQG41JshR1QE2cyqelJWKS29PFEWn5Zdh1eE6AyvIxKJPj50edx19/+e0b5bGRz6NtJQl3lIlYSeziYm0ySpcXdb+PMwZ4fzo1/PAjV44ekIoHjN5KG/ZhH4eYpRCsNv/2hYNv/OXPZ34XLBpLkCYnmzINLSdlWX4NngC0/2LDAMQBhJCG4yr4ewrR7+DO/UXeIA+YkIuAdbdvv5WxdSNt0JTJ0FP+0/bJ7c++e6LdM7cJI57yONn68QsUx9cMkCOX7v5Xt1wmoOE5VNRfEc1LbiuKK9/OOZ1BPnLf/O26x16ij4PnQ2pyjXuZzDZE6oNIA94gdbFN9tt9c5/BbqOnrS41Lj96Zd+P3SZPt/i6WemVqKdJ5bFXFLyIsysc7K9buCYn66odOfK4PHP/7Gl975PbvYOhY2Eav9PKZn72+49+HTl1+WKFRBEWtrPNZIeYWbJnK2y5oaz2GZIncjGsLr+z7pb41JsT3rbrfSz6sX8yNHdxV7/2UJA0FGK8rAxYSBQrZRaCjTOoYiwUG0eWSCP0FJQaRmXdBg0+gjEFQyVKVREpLYTdzQXSIJK2KJIGDwY+8tuj6AUBld7weyVUCr5k6+VZnOyUe+6q4US7sRsQZK23Rp9ao68kYfcV+b51obQboHUR0sAUHKpPWiRJKrhz0dKuUNJO23yK8cgoZBMv+63xHxeShZEawn8ZuyFBWiaYkALj92AlSXug9QKb7yn4en2SIslrCr5aXdJS3sZrzFHsxqDAxyIIis9hrBXEiFte5g1zIwMJ/JAq89ix2ePXIidmvUD0auHesnK0lMarh+Wun8BuC6aDDbV2kRQ7//H04Rd+fPhoUHEcg+yu25DeWTXtfoqQ0KCC2+vTLpJ8WsHNi9OuVWNOEht+7Q7gaFXut8HWTyh4vD7ukeQ7Cn5rcdxP15iTPsz93N9Vk/sbYOsPFfxbfdwjyV8V/OPiuD9SY+5+7O7zc7+nEvetSHQjtJsJVDAK3lqF+7ITEU4eh9sCjmim5xaKtVSt9QUFb1p0aupemJry+RjvzO4QdWpkqe/W0MmD2D0g8HosdXIXhStCVY18FtoOON17Pdj47xr2PFouOJJ8oOB7Hys4fn5PrnqqhgDfx+5hqC/zAjDT1gwxU9OqAyDD3xWcr08GJHlRwZ/XIcMTNWT4IXY/KBphj224FY0gg2oMGiSz5vMKZusLKiQRClrVBWiQ/DVILqQU2I3K9Z+qIcocdk9CjLGDWWqCHYZtqLpn8k68o7ITC86oMCDZM7gmc41lmCWguCu8lywCLh5GFwcrUzNXST8T0PaArOcUTNWnHyShCh6orp+QFDpU0I9fSc/VUNLz2D0DPgs3LOZOwo3XExCHz1YSqRPaPnh3FaxTJCShCtYQqZTFczXmfondC1DZCNv7T5W3bru8h+APrEjJRHlSEmRpSQmIN5i1Ff4kqH9aWvxX7Mybu7eurPIX4fqyv4yK7uzptpbVp/e/Jm/Ghf9VYbh4prOmWVJ7l9bhTQ5naUOKGfYuvI4El8ClF1bCQv7Gwzcp5UUP72WoRTw8/HpF6rlTdnkldfkK6jsYh+JG99fVnVmOv1Hn3l/9QVPLvsvyLosn6tWXejvePn7tF6vf3XL+w1j8oTX9/c9fuOP96IvNbX9YE3vjpv8BZQ06ad4VAAA=";
}
