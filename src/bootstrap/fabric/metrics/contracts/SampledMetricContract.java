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
    
    public static final byte[] $classHash = new byte[] { 24, -81, -87, -1, -76,
    86, -30, 36, 99, 1, -116, 111, 40, -110, 85, 114, -101, 98, 68, -89, -100,
    44, 17, 66, 32, -52, -33, -27, 115, -35, 53, 36 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492632369000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
