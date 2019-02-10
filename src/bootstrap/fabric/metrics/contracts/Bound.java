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
 * and enforced by a treaty of
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
                return new fabric.metrics.contracts.Bound._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 20, -95, 19, -106, 53,
    12, 32, -93, -24, 117, 5, -87, -82, -11, 88, 13, -123, -55, -58, -87, 94,
    25, -111, -66, -16, 127, 8, -23, 22, 17, -38, 16 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260582000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
