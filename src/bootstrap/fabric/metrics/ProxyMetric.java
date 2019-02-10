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
    
    public static final byte[] $classHash = new byte[] { -54, 39, -41, -20, 61,
    -34, 62, -123, 84, -117, 109, 21, -127, -57, 121, 46, -86, 127, -35, -87,
    96, -100, 30, 49, -127, 111, -32, -7, -2, -20, 125, 45 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1549748453000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK0YC2wcR3XufP6c7caOEyetkziOY4Ly8Z1CEVDcQONrErs5O5btRK2jxl3vzp033tvdzM7Z5xZDUlElomAJ6oa0SiIhUkFTk4pIVYWiiFAhmlBAAgEFVSlGamhRE1CFKFSChvdm5z7e+8QncdK8tzfz3sz7z2f+Fql0GGmPKWO6EeLTNnVCu5Wx3uiAwhyqRQzFcYahd1StC/SefO97Wquf+KOkXlVMy9RVxRg1HU6WRQ8rk0rYpDy8f7C36yAJqsjYozjjnPgPdqcYabMtYzpuWFwukjf/s1vDc98+1HixgjSMkAbdHOIK19WIZXKa4iOkPkETY5Q5OzWNaiNkuUmpNkSZrhj640BomSOkydHjpsKTjDqD1LGMSSRscpI2ZWLNdCeKb4HYLKlyi4H4ja74Sa4b4aju8K4oqYrp1NCcI+TLJBAllTFDiQPhqmhai7CYMbwb+4G8VgcxWUxRaZolMKGbGifrvRwZjTv2AgGwVicoH7cySwVMBTpIkyuSoZjx8BBnuhkH0korCatw0lJ0UiCqsRV1QonTUU7u9tINuENAFRRmQRZOmr1kYibwWYvHZzneutV//+wTZo/pJz6QWaOqgfLXAFOrh2mQxiijpkpdxvot0ZPKqssn/IQAcbOH2KV59UsfPLCt9cpVl2ZNAZp9Y4epykfVc2PLfr02svm+ChSjxrYcHUNhkebCqwNypCtlQ7SvysyIg6H04JXBnz1y9Dx9309qe0mVahnJBETVctVK2LpB2R5qUqZwqvWSIDW1iBjvJdXwHdVN6vbui8UcyntJwBBdVZb4DyaKwRRoomr41s2Ylf62FT4uvlM2IaQaGvFBO0xI/TuAVxLif42T/vC4laDhMSNJpyC8w9CowtTxMOQt09VO1bKnww5Twyxpch0o3f4whBIgcDyzUtN94k8IJLH/7zOmUIfGKZ8PzLtetTQ6pjjgKxk33QMGpEaPZWiUjarG7OVesuLycyJ2ghjvDsSssI4P/L3WWylyeeeS3bs+uDD6hht3yCuNB1nhShiSEoZyJASh6jGfQlChQlCh5n2pUORs70sibKockV+Zeephns/bhsJjFkukiM8nlFop+EW8gLcnoIpAoajfPPToQ4+daK+AQLWnAug7IO3wpk222PTClwK5MKo2HH/vw5dPzljZBOKkIy+v8zkxL9u9FmKWSjWoe9npt7Qpr4xenunwY00JQrnjCgQk1I5W7xqL8rMrXevQGpVRUoc2UAwcSheoWj7OrKlsj/D8MgRNbhCgsTwCijK5Y8g+84df/fVesYGkK2pDTukdorwrJ4txsgaRr8uzth9mlALd9VMDzzx76/hBYXig2FhowQ6EEcheBdLWYk9dPfLHP7197rf+rLM4qbKTY4aupoQuy2/DzwftY2yYitiBGApyRJaBtkwdsHHlTVnZoCIYUJVAdKdjv5mwND2mK2MGxUj5T8Mntr9yc7bRdbcBPa7xGNl25wmy/fd0k6NvHPpXq5jGp+KOlLVflswtcyuyM+9kTJlGOVLHfrPuudeVMxD5UKQc/XEq6g4R9iDCgZ8StugUcLtn7NMI2l1rrZX94s9GATch2OzaFj+3SLsS+auSFe0nEl/C0RU2wpWL52RkXbHNR2yc556cO6vte2G7u0U0LS7ou8xk4ge//+8vQqcWrhUoFEFu2Z0GnaRGzpp3wZIb8k5BfWJvzqbVwvvr7otM3Ii7y673iOilfrFv/tqeTeq3/KQik+N5B4LFTF25wkKyMQrnGRPVxp5a4YS2jFGDaKwHoK0ipKLTxf4Pc4wqM1J4CMFnM6x+ZK2RLP+U+O9ef2SjwCfLG/5vhjODp9i6dRYHW8Sae0oE0V4E3ZyscefokHN05BTsjqzcX1ys7QZo7YQEviFxsjxtkYVLbC5Z2xVS2ymLTVAWGoJq4la/e7zbhRBhsITyDyPo46QmTrlQGP/3FNK0DdpmQipXS0zK0xRYArcl/qi4prmyjZYYUxCMwFEfj0NwDB3AAwMfdPIPfANMT0ABn5QHPnpi7mu3Q7NzbiK6p+KNeQfTXB73ZCxWvQvBViwHG0qtIjh2v/vyzKXvzxz3S4m7oLprFpR3Wsy6IbCqLfEj5VkXWR6WeHBp1jVLjAlm3WvdbuwtKv29sPQ1iS+WJz2y/FDil5Ym/VSJMRHDzCv9gZLSfw52g3aJ68qTHllqJQ4sTfqjJcaeRPCEV/r+QtLXE9doZAcsPStxvIj0eTsi7Dw2szhs0VRLLVarTs4Vk/jRJZem1sWlKV2P8fbs9Cl2iSr1dAmbfBPBVzlelIVNDihwTyhqkc9A64bLy16JN5Tw5/F8xZGlTeLVd1Qc/35dzHqqhALPI3gGzpdpBahhqTqfLunVHoivehdX3yhPB2R5R+K3ytDhOyV0+C6C01kn9Fu6U9AJIqlGoPWDAn+T+Ex5SYUspyU+WVyBCiFfhZBCaIFgSMx/voQq8whegByjR5KKAX4YsODUPZ0O4vsLBzFnVOE6FHsKF2am0gQ1ORzuMt85k+SFeCETxaEdIKR2l4uD75ZnImT5i8QLxU0UEHoHMiby2unVEnb6EYKLELZwyaLOOFx6XR2x+0IhlVqgHYIQ3C/xg+WphCwRiXcsrZReKTH2GoJLcLjhlvtolXZwo7iK4GtWKGfgTk4TialDU+F7k4vrrpWXmMhyVeIfl+E0MSIOtFtdsWIc7m2WlNwzUD1mWQZVTCHPL0sY6HcIrnLSpmJo052mthMubJPwOZx2+DCOTBdcftLStRQndTkHZbznrSnw3iLfANXIT+m5G3u3NRd5a7k771VW8l0421Cz+uz+N8X7QeZ9LwjX81jSMHJuKLm3lSqb0ZguFA26zwK2QNch8RffF7h49sQvodxbLt0CnNhcOvz3Z+GoFgHScbTWc+14kDI4Amre20dLkuGz8/w/Vv+7qmZ4Qdz4wQVtP//kmzd3vP2Fp4afTjQfe306dP4r11987HTr9mPWwkcf35zp/B9FQ1ZXDhcAAA==";
}
