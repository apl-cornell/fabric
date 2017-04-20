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
    
    public static final byte[] $classHash = new byte[] { -40, 43, 112, 54, 19,
    -41, 122, -33, 100, -124, 121, 67, -71, 81, -95, 8, 104, -83, 124, -30, -31,
    -83, -77, 83, -105, -20, -43, 71, 8, -24, 120, -109 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492632369000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
