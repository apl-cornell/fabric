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
    
    public static final byte[] $classHash = new byte[] { -83, -10, -56, -113,
    13, 10, -66, 119, 56, -67, 99, -116, 75, 110, 94, 55, -79, -70, 126, 1,
    -122, -64, 10, 102, -9, 44, 102, 30, 2, -24, 8, -104 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492294329000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
