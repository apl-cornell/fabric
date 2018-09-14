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
    
    public static final byte[] $classHash = new byte[] { 110, -106, 114, 83,
    -48, -74, -91, 80, 54, -71, 122, -67, -121, 35, -100, 65, -111, 19, -35, 12,
    -96, 13, -106, 85, 24, 21, 58, -92, 118, -25, 13, 1 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1536940425000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
