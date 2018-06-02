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
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.ProxyMetric._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -84, 108, 95, -49, -85,
    -106, 70, 4, 120, -84, -55, 53, -117, 56, -73, 6, -40, -56, 59, 124, -18,
    -78, 65, -14, -104, -111, 79, -8, -72, -12, -53, 69 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527882698000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0YDWxbR/nsOE7chKZN125L0zRtTEW71qYbY4wUtiY0janTRkk7QSpmzu+dk7c8v/d679w424rKALVsqNJYVjrEKk10ArbQSUVTQSyoIDRaFTGGECsSPxUwMTTKNEBQEGN8373z33PsxRKW7r53d9939/3fd56/RppdTjZmaNowY2LWYW5siKYTyVHKXaYPmtR198NsSmsLJU6+9jW9J0iCSdKuUcu2DI2aKcsVZHnyPnqYxi0m4gfGEv0HSURDwmHqTgkSPDiQ56TXsc3ZSdMW6pCq/R+/JT73pXtXnGsiHROkw7DGBRWGNmhbguXFBGnPsmyacXenrjN9gqy0GNPHGTeoadwPiLY1QTpdY9KiIseZO8Zc2zyMiJ1uzmFcnlmYRPZtYJvnNGFzYH+Fx35OGGY8abiiP0nCGYOZunuIfIqEkqQ5Y9JJQFyTLEgRlzvGh3Ae0JcZwCbPUI0VSELThqULst5PUZQ4ugcQgLQly8SUXTwqZFGYIJ0eSya1JuPjghvWJKA22zk4RZCumpsCUqtDtWk6yVKC3OTHG/WWACsi1YIkgqz2o8mdwGZdPpuVWeva3h0nHrCGrSAJAM8600zkvxWIenxEYyzDOLM05hG2b0mepGsWjgcJAeTVPmQP5/yDb969tefCRQ9n7SI4+9L3MU2ktDPp5S93D26+swnZaHVs10BXqJBcWnVUrfTnHfD2NcUdcTFWWLww9uLHjz7DXg+SZQkS1mwzlwWvWqnZWccwGd/NLMapYHqCRJilD8r1BGmB76RhMW92XybjMpEgIVNOhW05BhVlYAtUUQt8G1bGLnw7VEzJ77xDCGmBRgLQxgiJJAB2wPCngiTiU3aWxdNmjs2Ae8ehMcq1qTjELTe0uMu1OM9ZwgAkNQVeBABszu387IgcxIAJ5/+5WR45XzETCIBS12u2ztLUBQspbxkYNSEghm1TZzylmScWEmTVwhPSYyLo5S54qtRJAKzc7c8P5bRzuYFdb55NXfa8DWmVyiAWPA5jisNYGYfAVDtGUQzyUgzy0nwgHxs8nXhWOkvYlVFV3Kcd9vmgY1KRsXk2TwIBKdQNkl56Cdh4GnIHpIf2zeOf+Ognj29sAvd0ZkJoMUCN+oOllGIS8EUhAlJax7HX/vHcySN2KWwEiVZFczUlRuNGv4a4rTEdsl1p+y299PnUwpFoEDNJBJKcoOCGkDF6/GdURGV/IcOhNpqTpA11QE1cKqSlZWKK2zOlGWn55dh1ek6AyvIxKJPjh8adJ6/85E+3yWujkEc7yhLuOBP9ZbGLm3XIKF1Z0v1+zhjg/frU6GOPXzt2UCoeMPoWOzCK/SDELIVgtfnnLh765W9/c+bnwZKxBAk7ubRpaHkpy8q34ReA9l9sGIA4gRDS8KAK/t5i9Dt48qYSb5AHTMhFwLobPWBlbd3IGDRtMvSU/3S8e/vzfz6xwjO3CTOe8jjZ+s4blOZvHiBHL9/7zx65TUDDe6ikvxKal9xWlXbeyTmdRT7yn/7Zuid+RJ8Ez4fU5Br3M5ltiNQHkQa8Vepim+y3+9beh91GT1vdal4O+mS/CbvNnm7xc4vSK1G/sMpjLyl4CVdXOdjfULknJ+tqXTnyujzz0Nxpfd/T272LobMyje+yctlv/uKtH8dOXb20SKKICNvZZrLDzCw7sw2O3FBV+4zIG7kUVldfX3fn4PSrk96x630s+rG/MTJ/afcm7YtB0lSM8aoyoJKov5xZCDbOoIqxUGycWSaN0FtUagSVdTc0GAT7FQyVKVVFpLQQdncUSYNI2qpImjwYeMtvj5IXBFR6w/FqqBR8ydbLs7jYJc/cXceJ9mA3IMhab4+o2iNalrCjJb7vqpR2A7RuQpqYgiONSYskSQWHliztKiXtjM2nGY+NQzbxst/N/utCsjBWR/iPYTciSOskE1JgHA8vJmkvtD5g8w0FrzQmKZK8ouDLtSUt5y1VZ41iNwEFPhZBUHyOYq0gxtzqMm+UG1lI4IdVmceOzz38duzEnBeIXi3cV1WOltN49bA89V3Y3YLpYEO9UyTF0B+fO/Ldrx85FlQc90N2121I76yWdt9DSGhYwe2NaRdJ3qvglqVp16qzJokNv3YHcLYm99vg6KcUfLgx7pHk8wp+Zmncz9RZkz7M/dzfU5f7W+Hofyv4+8a4R5LfKfirpXF/tM7aQ9g94Od+72LctyPRbdDuIFDBKHhXDe6rbkS4eRxuC7iimZ6vFKtN7fVhBW9fcmrqqUxNhXyMb2Z3hDp1stQjdXTyKHafFfg8ljq5h8IToaZG3g9tB9zufR5s/lcdex6rFhxJriv4xjsKjsMvyF1P1RHgy9g9BvVlQQBm2pohZutadQBk+IOCC43JgCQvKPitBmR4qo4MX8XuKyUj7LUNd1EjyKCagAbJrOVFBXONBRWSCAWt2gI0Sf6aJBdSCuzG5f7P1BFlHrunIcbYoRw1wQ6jNlTdswUn3rG4EwvOqDAg2TN4JnONZZkloLgrfpdtAi4eQRcHK1Mzv5h+JqHtBVkvKJhuTD9IQhU8WFs/ISl0qKgfv5LO11HSd7A7Bz4LLyzmTsGL1xMQp8/mBWkrK5Cwvl+7yDtb/eOjDf6QnXl1z9bVNd7YN1X9B6fozp7uaL3x9IFX5Lux+G9OBJ5lmZxpllWm5VVq2OEsY0gZIt5z0JHge2DwyjpRyD+58Etq4AUP7/twU3t4OPqBVGyX7AoO0u0rNz/COFz9ur/q7Mpx/JNx/m83Xg+37r8qX3p438ybqZeePTkUys9fuv2RD5wPX7nY/+Bfzu3866lH913/9t8v7/ofXHqn2vwUAAA=";
}
