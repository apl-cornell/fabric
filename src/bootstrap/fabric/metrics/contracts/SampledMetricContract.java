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
    
    public static final byte[] $classHash = new byte[] { -76, 71, 11, -43, 0,
    -106, -70, -61, 84, 38, -13, -101, -110, -108, -2, -31, -121, 18, 117, -12,
    105, 89, 124, 110, 41, -109, -85, -78, -100, 70, -62, 28 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492294329000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
