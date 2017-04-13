package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.SampledMetric;

/**
 * A specialized form of {@link MetricContract} for automating the enforcement
 * strategy for {@link SampledMetric}s.
 */
public interface SampledMetricContract
  extends fabric.metrics.contracts.MetricContract {
    /**
   * @param metric
   *        the {@link SampledMetric} this contract asserts a bound on
   * @param bound
   *        the {@link Bound} this {@link MetricContract} asserts on
   *        metric.
   */
    public fabric.metrics.contracts.SampledMetricContract
      fabric$metrics$contracts$SampledMetricContract$(
      fabric.metrics.SampledMetric metric,
      fabric.metrics.contracts.Bound bound);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      enforcementStrategy();
    
    public static class _Proxy
    extends fabric.metrics.contracts.MetricContract._Proxy
      implements fabric.metrics.contracts.SampledMetricContract {
        public fabric.metrics.contracts.SampledMetricContract
          fabric$metrics$contracts$SampledMetricContract$(
          fabric.metrics.SampledMetric arg1,
          fabric.metrics.contracts.Bound arg2) {
            return ((fabric.metrics.contracts.SampledMetricContract) fetch()).
              fabric$metrics$contracts$SampledMetricContract$(arg1, arg2);
        }
        
        public _Proxy(SampledMetricContract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl
    extends fabric.metrics.contracts.MetricContract._Impl
      implements fabric.metrics.contracts.SampledMetricContract {
        /**
   * @param metric
   *        the {@link SampledMetric} this contract asserts a bound on
   * @param bound
   *        the {@link Bound} this {@link MetricContract} asserts on
   *        metric.
   */
        public fabric.metrics.contracts.SampledMetricContract
          fabric$metrics$contracts$SampledMetricContract$(
          fabric.metrics.SampledMetric metric,
          fabric.metrics.contracts.Bound bound) {
            fabric$metrics$contracts$MetricContract$(metric, bound);
            return (fabric.metrics.contracts.SampledMetricContract)
                     this.$getProxy();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          enforcementStrategy() {
            return directStrategy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.SampledMetricContract._Proxy(
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
          implements fabric.metrics.contracts.SampledMetricContract._Static {
            public _Proxy(fabric.metrics.contracts.SampledMetricContract.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.SampledMetricContract.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  SampledMetricContract.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    SampledMetricContract.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.SampledMetricContract._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.SampledMetricContract._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.SampledMetricContract._Static {
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
                return new fabric.metrics.contracts.SampledMetricContract.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 121, -87, -71, 2, -122,
    -123, 18, -53, -29, 6, 58, 76, 39, -31, 19, 99, -16, 121, 36, 88, 14, 19,
    -75, -99, -92, 107, 76, -71, 46, 102, -30, 71 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492106515000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
