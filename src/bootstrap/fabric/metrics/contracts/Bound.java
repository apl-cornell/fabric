package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.worker.Store;

/**
 * A linear time-varying bound that can be applied to a {@link Metric}'s value
 * and enforced by a {@link MetricContract} of
 * <code>&gt;= r * (t - startTime) + b</code>.
 */
public interface Bound extends fabric.lang.Object {
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.Bound {
        public static double[] createBound(double arg1, double arg2) {
            return fabric.metrics.contracts.Bound._Impl.createBound(arg1, arg2);
        }
        
        public static double[] createBound(double arg1, double arg2,
                                           long arg3) {
            return fabric.metrics.contracts.Bound._Impl.createBound(arg1, arg2,
                                                                    arg3);
        }
        
        public static double value(double arg1, double arg2, long arg3,
                                   long arg4) {
            return fabric.metrics.contracts.Bound._Impl.value(arg1, arg2, arg3,
                                                              arg4);
        }
        
        public static double value(double arg1, double arg2, long arg3) {
            return fabric.metrics.contracts.Bound._Impl.value(arg1, arg2, arg3);
        }
        
        public static long trueExpiry(double arg1, double arg2, double arg3,
                                      long arg4) {
            return fabric.metrics.contracts.Bound._Impl.trueExpiry(arg1, arg2,
                                                                   arg3, arg4);
        }
        
        public static boolean test(double arg1, double arg2, double arg3,
                                   long arg4) {
            return fabric.metrics.contracts.Bound._Impl.test(arg1, arg2, arg3,
                                                             arg4);
        }
        
        public _Proxy(Bound._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.contracts.Bound {
        /** Create a normalized bound. */
        public static double[] createBound(double rate, double base) {
            return fabric.metrics.contracts.Bound._Impl.createBound(rate, base,
                                                                    0);
        }
        
        /** Create a normalized bound. */
        public static double[] createBound(double rate, double base,
                                           long startTime) {
            if (java.lang.Double.isNaN(rate) || java.lang.Double.isNaN(base)) {
                throw new java.lang.RuntimeException("This shouldn\'t happen");
            }
            return new double[] { rate, base - rate * startTime };
        }
        
        /**
   * Compute a value given the rate, base, starting time (for base), and
   * current time.
   */
        public static double value(double rate, double base, long startTime,
                                   long curTime) {
            long dt = curTime - startTime;
            return rate * dt + base;
        }
        
        /**
   * Compute a value given the rate, base, and current time.
   */
        public static double value(double rate, double base, long time) {
            return fabric.metrics.contracts.Bound._Impl.value(rate, base, 0,
                                                              time);
        }
        
        /**
   * Compute the time at which this bound will expire given the current value
   * and time.
   */
        public static long trueExpiry(double rate, double base, double value,
                                      long time) {
            if (rate > 0) {
                return (long)
                         (time +
                            (value -
                               fabric.metrics.contracts.Bound._Impl.value(
                                                                      rate,
                                                                      base,
                                                                      time)) /
                            rate);
            } else if (value <
                         fabric.metrics.contracts.Bound._Impl.value(rate, base,
                                                                    time)) {
                return 0;
            }
            return java.lang.Long.MAX_VALUE;
        }
        
        /**
   * Determine if the given value x is above the bound at the given time.
   */
        public static boolean test(double rate, double base, double x,
                                   long time) {
            return x >=
              fabric.metrics.contracts.Bound._Impl.value(rate, base, time);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.Bound._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.Bound._Static {
            public _Proxy(fabric.metrics.contracts.Bound._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.Bound._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  Bound.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.contracts.Bound._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.Bound._Static._Impl.class);
                $instance = (fabric.metrics.contracts.Bound._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.Bound._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.Bound._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 68, 19, 96, -93, -63,
    29, -64, -43, 96, -4, -66, 76, -92, -84, 111, -97, -98, -38, -71, 113, -67,
    58, -83, 8, -55, 91, -61, -18, -101, -80, -62, 107 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526753776000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
