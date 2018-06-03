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
    
    public static final byte[] $classHash = new byte[] { -44, -108, 49, -46, 53,
    112, -40, -113, 90, 8, 76, -95, -94, -105, 6, 116, -99, -80, -57, -108,
    -110, 103, -51, -113, -124, -79, -25, -22, 97, -88, 118, -23 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527882698000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
