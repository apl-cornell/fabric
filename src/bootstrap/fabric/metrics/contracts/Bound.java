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
                return new fabric.metrics.contracts.Bound._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -14, -49, 40, -96, -1,
    76, 47, -54, 92, -104, -55, 60, -10, -33, 52, -68, -41, -65, 83, -71, -46,
    27, 32, -75, -106, 92, 8, 115, 106, -7, -13, -6 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527882698000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
