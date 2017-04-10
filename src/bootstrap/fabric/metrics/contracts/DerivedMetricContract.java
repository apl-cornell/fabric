package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.DerivedMetric;

/**
 * A {@link MetricContract} for enforcement of {@link Bound}s on
 * {@link DerivedMetric}s using the {@link DerivedMetric}s implementation of
 * {@link DerivedMetric#policyFor(Bound)}
 */
public interface DerivedMetricContract
  extends fabric.metrics.contracts.MetricContract {
    /**
   * @param metric
   *        the {@link DerivedMetric} this contract asserts a bound on
   * @param bound
   *        the {@link Bound} this {@link MetricContract} asserts on
   *        metric.
   */
    public fabric.metrics.contracts.DerivedMetricContract
      fabric$metrics$contracts$DerivedMetricContract$(
      fabric.metrics.DerivedMetric metric,
      fabric.metrics.contracts.Bound bound);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      enforcementStrategy();
    
    public static class _Proxy
    extends fabric.metrics.contracts.MetricContract._Proxy
      implements fabric.metrics.contracts.DerivedMetricContract {
        public fabric.metrics.contracts.DerivedMetricContract
          fabric$metrics$contracts$DerivedMetricContract$(
          fabric.metrics.DerivedMetric arg1,
          fabric.metrics.contracts.Bound arg2) {
            return ((fabric.metrics.contracts.DerivedMetricContract) fetch()).
              fabric$metrics$contracts$DerivedMetricContract$(arg1, arg2);
        }
        
        public _Proxy(DerivedMetricContract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl
    extends fabric.metrics.contracts.MetricContract._Impl
      implements fabric.metrics.contracts.DerivedMetricContract {
        /**
   * @param metric
   *        the {@link DerivedMetric} this contract asserts a bound on
   * @param bound
   *        the {@link Bound} this {@link MetricContract} asserts on
   *        metric.
   */
        public fabric.metrics.contracts.DerivedMetricContract
          fabric$metrics$contracts$DerivedMetricContract$(
          fabric.metrics.DerivedMetric metric,
          fabric.metrics.contracts.Bound bound) {
            fabric$metrics$contracts$MetricContract$(metric, bound);
            return (fabric.metrics.contracts.DerivedMetricContract)
                     this.$getProxy();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          enforcementStrategy() {
            return ((fabric.metrics.DerivedMetric)
                      fabric.lang.Object._Proxy.$getProxy(getMetric())).
              policyFor(getBound());
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.DerivedMetricContract._Proxy(
                     this);
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
          implements fabric.metrics.contracts.DerivedMetricContract._Static {
            public _Proxy(fabric.metrics.contracts.DerivedMetricContract.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.DerivedMetricContract.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  DerivedMetricContract.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    DerivedMetricContract.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.DerivedMetricContract._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.DerivedMetricContract._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.DerivedMetricContract._Static {
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
                return new fabric.metrics.contracts.DerivedMetricContract.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -73, 22, 63, 93, 49,
    116, -41, -28, -52, 58, -4, 89, -72, 123, -4, -32, 121, -41, -48, 23, -51,
    35, 36, -70, 89, 80, -102, 77, -47, 20, -91, 43 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAALVXXWxURRSe3bbbbtvQ/4KllFpWDH97UzEqVIN0BbqypU0LRkrKMnvv7PbSu/de5s7SLYhBE4NPPCggmNCnEv8qJiYEY9KEByMQ0Igxog9qHyTBIA/ERH3AnzNz7+7dve3ik5vMz50558yZb875ZnbmLqqwKOpK4oSqhdmkSazwVpyIxgYxtYgS0bBl7YTRuFxTHj11+x2lw4/8MVQrY93QVRlrcd1iaFFsPz6IJZ0waddQtGcPCspcsQ9bYwz59/RmKeo0DW0ypRnMWWSe/ZNrpBNv7a3/uAzVjaA6VR9mmKlyxNAZybIRVJsm6QSh1mZFIcoIatAJUYYJVbGmHgJBQx9BjZaa0jHLUGINEcvQDnLBRitjEirWzA1y9w1wm2ZkZlBwv952P8NUTYqpFuuJoUBSJZpiHUAvo/IYqkhqOAWCrbHcLiRhUdrKx0G8WgU3aRLLJKdSPq7qCkPLvRr5HYe2gwCoVqYJGzPyS5XrGAZQo+2ShvWUNMyoqqdAtMLIwCoMtZU0CkJVJpbHcYrEGVrilRu0p0AqKGDhKgy1eMWEJTizNs+ZFZzW3R1PHz+s9+l+5AOfFSJr3P8qUOrwKA2RJKFEl4mtWLs6dgq3zr7uRwiEWzzCtszFl+49u7bj0hVbZukCMgOJ/URmcXk6sehGe2TVhjLuRpVpWCoPhaKdi1MddGZ6siZEe2veIp8M5yYvDX2+++j75I4fVUdRQDa0TBqiqkE20qaqEbqN6IRiRpQoChJdiYj5KKqEfkzViT06kExahEVRuSaGAob4BoiSYIJDVAl9VU8aub6J2ZjoZ02EUCUU5IOyDKGyd6Gtgc8BhkalMSNNpISWIRMQ3hIUgqk8JkHeUlWWLCpLNKMzFYScIYgiaCwJQp1RLDNLeg7SBcK/X0xEnOEwOGb+3wtk+Q7rJ3w+AH+5bCgkgS04SSeqegc1SJw+Q1MIjcva8dkoapo9IyIryLPBgogW2PkgGtq9PFKoeyLTu+Xe+fg1Oyq5rgMtQ2Hb67DjdTjvdXhBr8HRWp6BYeC0MHDajC8bjkxFPxCBFrBERuZt14LtjaaGWdKg6Szy+cRGm4W+iDCIj3HgHaCW2lXDo8/ve72rDELbnCjnpw2iIW+iufQUhR6G7InLdcdu//7RqSOGm3IMheYxwXxNnsldXtSoIRMFmNI1v7oTX4jPHgn5OQsFOTwYQhjYpsO7RlFG9+TYkaNREUM1HAOs8akcpVWzMWpMuCMiGhbxqtEODA6Wx0FBrM8Mm2e/+/KX9eLKyXFwXQFZDxPWU5D33FidyPAGF/udlBCQ++H04Jsn7x7bI4AHiRULLRjidQTyHUOiG/S1Kwe+/+nH6W/87mExFDAzCU2Vs2IvDf/Azwflb1548vIB3gKFRxzi6Mwzh8lXXun6BhyiAY+B61Zol542FDWp4oRGeKTcr3uk+8Kvx+vt49ZgxAaPorX/bcAdf6gXHb22948OYcYn8zvMxc8Vs4mxybW8mVI8yf3IvvL1sjOX8VmIfKA1Sz1EBFMhgQcSB/iYwGKdqLs9c4/zqstGq90ZFx8rRL2SV6tsbHl3tYMrcn4BhwN3OG0fn20yed1cbJOiZaWuK3HVTr96YkoZONdtXyqNxVfAFj2T/vDbv66HT89dXYA8gsww12nkINEK1vTDkg/Pezf1i9vcTau5O8s2RMZvpexll3tc9Eq/1z9zddtK+Q0/Ksvn+LwnRLFST6GzkGyUwAtI59vmI9XiEDrzoAY5WAegNMMGLjttvABUJyPFCfHqybyqn6tWOSp7nfZF73m4UeB3UOLfLQy1ewi4iHa5TFtOsqMkVfcaGV0RssLNbQ+Iu35e9TLk3FYhx1gobyy0IO+H3O1vyu+8lltdD6UNbuV9TruhBGjzwhrCx6QGgzwjSrYYzRrH1lNOK5VGs3BvLzxgThwIvBiaCDwyqEzSRGfwgAT2SU3mAH6iJMAFSpAT+f6gAYw3aQMP78UFkeP8sXSBu915jcqRz8j0re1rW0rc60vm/T9w9M5P1VUtntp1U9xL+ZdmEGg/mdG0gsgvzIKASUlSFYAE7evGFA1/FJfaPLNvPdEXSI3aOgn4f1Osw8SjnfcK5eAlHbDl+FdSnGKbW+XAf7Qk+MV4umHelqH8T9TMb4v/DFTtnBO3ERx358XWTaPd7ObP1zfe3/3J4ftzkze/WvzFitCnuwff7r/RfG7NvwVibQXcDQAA";
}
