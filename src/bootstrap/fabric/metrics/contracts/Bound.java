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
    
    public static final byte[] $classHash = new byte[] { 5, 49, -55, -121, 48,
    87, 17, -1, 116, 55, -126, 11, -116, -88, -92, 79, 41, 124, 79, -10, 61, -7,
    -115, -69, -96, -5, 60, -78, -105, 86, -35, -96 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527874708000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
