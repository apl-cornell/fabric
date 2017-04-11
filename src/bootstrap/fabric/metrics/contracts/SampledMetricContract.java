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
    
    public static final byte[] $classHash = new byte[] { -44, 35, -44, 115, -64,
    111, -80, -77, -39, 92, -34, -77, 113, 124, 46, 112, -73, 119, -74, -61, 9,
    30, -89, 4, -121, -100, -103, -102, -117, 56, -30, -18 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
