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
    
    public static final byte[] $classHash = new byte[] { 87, -124, -81, 19, 118,
    13, 115, 16, 25, -119, 41, 59, -47, 75, -113, -10, -65, 67, -11, 94, 81, 35,
    -36, 3, 27, -2, -19, 15, 102, 74, -20, 5 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527882698000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0YDWxbR/nsOD9usiZN1m5N0zRtvIp2rU03YBopbItZG69OG5J2QCoazu+dnbc8v/d675w4G0VlDLUbqGhbVjrEKk102g+hlYamCbFAQWisGgKKEBvSgAqYGNqqaUKMgdjG9907/+Q59mIJS3ffu7vvu/v+7zvPXyaNLieb0jRlmFEx6zA3uoumEskRyl2mx03quvthdkJrDSVOvva43hskwSRp06hlW4ZGzQnLFWRl8g46TWMWE7EDo4mBgySsIeEQdScFCR4czHPS59jmbMa0hTqkYv+Hro3NffNQx9MNpH2ctBvWmKDC0OK2JVhejJO2LMumGHdv0XWmj5NVFmP6GOMGNY07AdG2xkmna2QsKnKcuaPMtc1pROx0cw7j8szCJLJvA9s8pwmbA/sdHvs5YZixpOGKgSRpShvM1N3D5EsklCSNaZNmAHFNsiBFTO4Y24XzgL7CADZ5mmqsQBKaMixdkA1+iqLEkT2AAKTNWSYm7eJRIYvCBOn0WDKplYmNCW5YGUBttHNwiiDdVTcFpBaHalM0wyYEudqPN+ItAVZYqgVJBFntR5M7gc26fTYrs9blvTtP3GUNWUESAJ51ppnIfwsQ9fqIRlmacWZpzCNs25o8SdcsHA8SAsirfcgezrNffOvmbb3nX/Bw1i2Bsy91B9PEhHYmtfJiT3zLjQ3IRotjuwa6wiLJpVVH1MpA3gFvX1PcERejhcXzo89/7uhT7PUgWZEgTZpt5rLgVas0O+sYJuO7mcU4FUxPkDCz9LhcT5Bm+E4aFvNm96XTLhMJEjLlVJMtx6CiNGyBKmqGb8NK24Vvh4pJ+Z13CCHN0EgA2igh4QTAdhj+WpBEbNLOsljKzLEZcO8YNEa5NhmDuOWGFnO5FuM5SxiApKbAiwCAzbmdnx2Wgygw4fw/N8sj5x0zgQAodYNm6yxFXbCQ8pbBERMCYsg2dcYnNPPEQoJ0LTwsPSaMXu6Cp0qdBMDKPf78UE47lxu89a2zEy963oa0SmUQCx6HUcVhtIxDYKoNoygKeSkKeWk+kI/GTye+K52lyZVRVdynDfb5uGNSkbZ5Nk8CASnUlZJeegnYeApyB6SHti1jn7/tC8c3NYB7OjMhtBigRvzBUkoxCfiiEAETWvux194+d/KIXQobQSIV0VxJidG4ya8hbmtMh2xX2n5rH31mYuFIJIiZJAxJTlBwQ8gYvf4zFkXlQCHDoTYak6QVdUBNXCqkpRViktszpRlp+ZXYdXpOgMryMSiT4yfGnEde/uXfr5fXRiGPtpcl3DEmBspiFzdrl1G6qqT7/ZwxwPvDqZEHH7p87KBUPGD0L3VgBPs4xCyFYLX5V184/Ps//fHMb4MlYwnS5ORSpqHlpSyr3odfANp72DAAcQIhpOG4Cv6+YvQ7ePLmEm+QB0zIRcC6GzlgZW3dSBs0ZTL0lP+2X7PjmTdOdHjmNmHGUx4n2z54g9L82kFy9MVD/+qV2wQ0vIdK+iuhecmtq7TzLZzTWeQj/+XfrH/45/QR8HxITa5xJ5PZhkh9EGnA66Qutst+h2/tI9ht8rTVo+bloF/2m7Hb4ukWP7cqvRL1a1J57FcKXsDVLgf7Kxfvycn6aleOvC7P3D13Wt/32A7vYuhcnMZvtXLZ7/3u3V9ET126sESiCAvb2W6yaWaWndkKR26sqH2G5Y1cCqtLr6+/MT71asY7doOPRT/2k8PzF3Zv1h4IkoZijFeUAYuJBsqZhWDjDKoYC8XGmRXSCH1FpYZRWTdDg0FwQMFQmVJVREoLYXdDkTSIpC2KpMGDgXf99ih5QUClNxyvhkrBl2y9PIuL3fLM3TWcaA92g4Ks8/aIqD0iZQk7UuL7psXSboTWQ0gDU3C4PmmRJKngrmVL26WknbH5FOPRMcgmXvZb678uJAujNYT/LHbDgrRkmJAC43hoKUn7oPUDm28q+HJ9kiLJSwperC5pOW8TNdYoduNQ4GMRBMXnCNYKYtStLPNGuJGFBD6tyjx2fO6+96Mn5rxA9Grh/opytJzGq4flqVdgdy2mg421TpEUu/527sgPnzhyLKg4HoDsrtuQ3lk17X6IkNCQgjvq0y6SfFjBrcvTrlVjTRIbfu0O4mxV7rfD0Y8qeF993CPJvQp+ZXncz9RYkz7M/dzfXpP76+Do/yj4l/q4R5I/K/jK8rg/WmPtbuzu8nO/dynu25Doemg3EKhgFLypCvcVNyLcPA63BVzRTM8vFqtV7fVJBT+67NTUuzg1FfIxvpndYerUyFJfq6GT+7G7R+DzWOrkdgpPhKoa+Ri0nXC793uw8d817HmsUnAkeUfBNz9QcBx+Xe56qoYA38LuQagvCwIw09YMMVvTqoMgw18VXKhPBiR5TsHv1yHDozVk+A523y4ZYa9tuEsaQQbVODRIZs3PK5irL6iQRChoVRegQfLXILmQUmA3Jvd/qoYo89g9BjHGDueoCXYYsaHqni048c6lnVhwRoUByZ7BM5lrLMssAcVd8btsE3DxMLo4WJma+aX0k4G2F2Q9r2CqPv0gCVXwYHX9hKTQoaJ+/Ep6toaSfoDd0+Cz8MJi7iS8eD0BcfpsXpDWsgIJ6/t1S7yz1T8+Wvxn7Myre7atrvLGvrriPzhFd/Z0e8tVpw+8JN+NxX9zwvAsS+dMs6wyLa9SmxzO0oaUIew9Bx0JfgQGX1wnCvknF35JDTzn4f0EbmoPD0c/lYrtll3BQXp85eanGIerX/dXnd05jn8yzv/jqneaWvZfki89vG8+c8+5rukr3I61924ZuLjnG2//OP7PQ5/uf6Vh3XuX29O3vdH4P0yLPFr8FAAA";
}
