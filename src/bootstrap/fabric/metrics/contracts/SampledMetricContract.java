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
    
    public static final byte[] $classHash = new byte[] { 20, 22, -93, -100, -3,
    -24, 55, 15, 30, 125, 119, 34, 104, -124, 16, -27, 110, -87, -72, -97, 35,
    28, 91, -17, -105, -10, -41, 87, -110, -24, -87, 81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492294329000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
