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
    
    public static final byte[] $classHash = new byte[] { -63, -17, 88, -5, 56,
    89, -65, -89, 46, 112, 19, 96, -29, 30, -119, -109, 117, -117, -19, -63, 52,
    69, -15, 90, -88, -2, -34, -6, -2, 15, 39, 126 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491929446000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
