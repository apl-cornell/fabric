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
    
    public static final byte[] $classHash = new byte[] { -22, 14, -10, 35, 114,
    -23, -65, 49, 52, -105, 48, 14, -62, -92, 35, 6, -82, -25, -65, -111, -35,
    -101, -39, 93, -45, -93, 74, 125, 90, 28, 113, 124 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260582000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
