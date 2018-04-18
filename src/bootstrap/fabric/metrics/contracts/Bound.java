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
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
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
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.Bound._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -117, -17, 116, -114,
    -124, 8, 78, -121, -61, 98, -45, 65, 32, 42, -128, -95, 103, -93, -108, -30,
    -55, -56, 22, -2, -84, -55, 40, -92, -66, 7, 81, -30 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524081841000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
