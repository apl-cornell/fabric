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
                return new fabric.metrics.contracts.Bound._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 29, -4, 48, 31, -10,
    -43, 121, -94, 62, 124, 84, 10, -74, -25, 88, -42, -66, 3, 59, -40, 10, 125,
    15, 120, 91, -70, -123, -80, -73, 79, 24, 127 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1529598589000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
