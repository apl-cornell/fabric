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
    
    public static final byte[] $classHash = new byte[] { -123, -99, 39, -82,
    -61, -87, 9, -19, 53, 8, 40, -8, -5, 67, -112, 10, -76, -27, 69, 46, -36,
    -119, -59, -60, 27, 124, 119, 111, 27, 104, -88, -40 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492535467000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
