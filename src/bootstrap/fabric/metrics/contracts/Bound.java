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
    
    public static final byte[] $classHash = new byte[] { -75, 31, 62, 79, -64,
    115, -94, 80, 66, -47, 120, -115, 121, 109, 2, 87, -65, 14, 62, -24, 15,
    -83, 58, -64, 0, 76, 19, 81, -42, 71, 71, -126 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525097266000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
