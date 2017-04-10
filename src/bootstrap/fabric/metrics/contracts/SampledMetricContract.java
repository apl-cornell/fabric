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
    
    public static final byte[] $classHash = new byte[] { -47, 49, 18, -9, -86,
    118, -51, -87, -52, 74, -74, -51, 106, -117, 53, -2, -39, -97, -103, -7, 11,
    44, 15, -114, -69, 83, -105, 123, -33, 94, -75, -72 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
