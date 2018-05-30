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
    
    public static final byte[] $classHash = new byte[] { 88, -36, 63, -34, 3,
    35, -79, -66, -83, 22, 86, -122, -70, -38, 55, 106, 89, -68, 76, 54, -34,
    30, -114, 11, -28, -18, -61, -81, 34, -42, 111, 96 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527629388000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0YC2wcR3XufP5cbOLEadLWcRzHPiKSJnekhUbFgbQ+mvjIObHsJGod0evc7py98d7uZnYuPrcEhUJJKMgS1A0popEqUgHFTURRVQFxFT4qjYqAItQWVEIErSgqoaqQoCBKeW927rfnu9oSJ828nZn3Zt5/3tzcVdLoctKboWnDjIpph7nRXTSdSA5T7jI9blLX3Q+zKa01lDj1+jf17iAJJkmbRi3bMjRqpixXkOXJw/QojVlMxA6MJPoPkbCGhIPUnRAkeGggz0mPY5vT46Yt1CFV+z90Q2z2q3eteLKBtI+RdsMaFVQYWty2BMuLMdKWZdk04+5tus70MbLSYkwfZdygpnEPINrWGOlwjXGLihxn7ghzbfMoIna4OYdxeWZhEtm3gW2e04TNgf0VHvs5YZixpOGK/iRpyhjM1N0j5NMklCSNGZOOA+KaZEGKmNwxtgvnAX2ZAWzyDNVYgSQ0aVi6IOv9FEWJI3sAAUibs0xM2MWjQhaFCdLhsWRSazw2KrhhjQNqo52DUwTprLkpILU4VJuk4ywlyHV+vGFvCbDCUi1IIshqP5rcCWzW6bNZmbWu7t0xc681aAVJAHjWmWYi/y1A1O0jGmEZxpmlMY+wbXPyFF0zfzJICCCv9iF7OE9/6q1bt3RffM7DWbsAzr70YaaJlHY2vfyFrvimWxqQjRbHdg10hQrJpVWH1Up/3gFvX1PcERejhcWLI8/eefxx9kaQLEuQJs02c1nwqpWanXUMk/HdzGKcCqYnSJhZelyuJ0gzfCcNi3mz+zIZl4kECZlyqsmWY1BRBrZAFTXDt2Fl7MK3Q8WE/M47hJBmaCQAbYSQcAJgOwx/JUgiNmFnWSxt5tgUuHcMGqNcm4hB3HJDi7lci/GcJQxAUlPgRQDA5tzOTw/JQRSYcP6fm+WR8xVTgQAodb1m6yxNXbCQ8paBYRMCYtA2dcZTmjkznyCr5h+WHhNGL3fBU6VOAmDlLn9+KKedzQ3c/ta51POetyGtUhnEgsdhVHEYLeMQmGrDKIpCXopCXpoL5KPxM4nvSGdpcmVUFfdpg30+4phUZGyezZNAQAp1jaSXXgI2noTcAemhbdPoJz9x98neBnBPZyqEFgPUiD9YSikmAV8UIiCltZ94/R/nTx2zS2EjSKQqmqspMRp7/RritsZ0yHal7Tf30KdS88ciQcwkYUhygoIbQsbo9p9REZX9hQyH2mhMklbUATVxqZCWlokJbk+VZqTll2PX4TkBKsvHoEyOHx11Hnn5F3+5SV4bhTzaXpZwR5noL4td3KxdRunKku73c8YA7/enhx986OqJQ1LxgNG30IER7OMQsxSC1eb3P3fkt3+4fPY3wZKxBGlycmnT0PJSlpXvwi8A7b/YMABxAiGk4bgK/p5i9Dt48sYSb5AHTMhFwLobOWBlbd3IGDRtMvSU/7S/f9tTf51Z4ZnbhBlPeZxsee8NSvPXD5Djz9/1z265TUDDe6ikvxKal9xWlXa+jXM6jXzkP/PrdQ//jD4Cng+pyTXuYTLbEKkPIg14o9TFVtlv8619CLteT1tdal4O+mS/EbtNnm7xc7PSK1G/JpXHfqngJVxd5WB/TeWenKyrdeXI6/LsfbNn9H2PbfMuho7KNH67lcs+8eI7P4+evnJpgUQRFraz1WRHmVl2ZiscuaGq9hmSN3IprK68se6W+ORr496x630s+rG/PTR3afdG7StB0lCM8aoyoJKov5xZCDbOoIqxUGycWSaN0FNUahiVdSs0GAT7FQyVKVVFpLQQdtuLpEEkbVEkDR4MvOO3R8kLAiq94Xg1VAq+ZOvlWVzslGfuruNEe7AbEGStt0dE7REpS9iREt87K6XdAK2LkAam4NDSpEWSpIK7Fi3tKiXtlM0nGY+OQjbxst/1/utCsjBSR/g7sBsSpGWcCSkwjgcXkrQHWh+w+aaCLy9NUiR5ScEXaktazluqzhrFbgwKfCyCoPgcxlpBjLjVZd4wN7KQwI+qMo+dnH3g3ejMrBeIXi3cV1WOltN49bA89X3Y3YDpYEO9UyTFrj+fP/bDbx07EVQc90N2121I76yWdj9ASGhQwW1L0y6SfFDBzYvTrlVnTRIbfu0O4GxN7rfC0Y8q+MDSuEeSLyj42cVxP1VnTfow93N/sC73N8LR/1bwT0vjHkn+qOAri+P+eJ21+7C718/93oW4b0Oim6BtJ1DBKLizBvdVNyLcPA63BVzRTM9XitWq9vqYgh9edGrqrkxNhXyMb2Z3iDp1stQX6+jky9h9TuDzWOrkIIUnQk2N3AxtB9zufR5s/Fcde56oFhxJ3lbwzfcUHIdfkrueriPA17B7EOrLggDMtDVDTNe16gDI8KqC80uTAUkuKPi9JcjwaB0ZvoHd10tG2Gsb7oJGkEE1Bg2SWfOzCuaWFlRIIhS0agvQIPlrkFxIKbAblfs/XkeUOewegxhjR3LUBDsM21B1TxeceMfCTiw4o8KAZM/gmcw1lmWWgOKu+F22Cbh4GF0crEzN/EL6GYe2F2S9qGB6afpBEqrgodr6CUmhQ0X9+JX0dB0lfR+7J8Fn4YXF3Al48XoC4vS5vCCtZQUS1vdrF3hnq398tPhP2dnX9mxZXeONfV3Vf3CK7tyZ9pZrzxx4Sb4bi//mhOFZlsmZZlllWl6lNjmcZQwpQ9h7DjoSPAMGr6wThfyTC7+kBi54eD+Cm9rDw9GPpWI7ZVdwkC5fuflxxuHq1/1VZ2eO45+Mc3+/9u2mlv1X5EsP75s7Xtl5uaHvu888sebg53/wu+2H77yQvPly90zrq3/7yfneF+27/wd15Jxc/BQAAA==";
}
