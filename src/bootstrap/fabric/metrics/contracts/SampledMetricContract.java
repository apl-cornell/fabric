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
    
    public static final byte[] $classHash = new byte[] { -20, -76, 60, 75, 86,
    -51, 3, 40, -40, 96, -50, 50, 33, -108, -64, -60, -3, 28, -59, 36, -76, 58,
    -88, 53, 115, -52, 116, 90, 29, 95, -38, -80 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492294329000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
