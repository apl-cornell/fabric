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
    
    public static final byte[] $classHash = new byte[] { -30, 23, 24, 23, -105,
    -104, -86, 8, 39, -49, -106, 34, 107, 113, 36, -9, -35, 64, -90, 79, -107,
    40, 106, 67, 86, 79, -3, 93, 89, -44, 44, 80 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1548260582000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
