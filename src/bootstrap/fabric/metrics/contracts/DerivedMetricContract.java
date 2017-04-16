package fabric.metrics.contracts;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.enforcement.EnforcementPolicy;
import fabric.metrics.DerivedMetric;

/**
 * A {@link MetricContract} for enforcement of {@link Bound}s on
 * {@link DerivedMetric}s using the {@link DerivedMetric}s implementation of
 * {@link DerivedMetric#policyFor(Bound)}
 */
public interface DerivedMetricContract
  extends fabric.metrics.contracts.MetricContract {
    /**
   * @param metric
   *        the {@link DerivedMetric} this contract asserts a bound on
   * @param bound
   *        the {@link Bound} this {@link MetricContract} asserts on
   *        metric.
   */
    public fabric.metrics.contracts.DerivedMetricContract
      fabric$metrics$contracts$DerivedMetricContract$(
      fabric.metrics.DerivedMetric metric,
      fabric.metrics.contracts.Bound bound);
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      enforcementStrategy();
    
    public static class _Proxy
    extends fabric.metrics.contracts.MetricContract._Proxy
      implements fabric.metrics.contracts.DerivedMetricContract {
        public fabric.metrics.contracts.DerivedMetricContract
          fabric$metrics$contracts$DerivedMetricContract$(
          fabric.metrics.DerivedMetric arg1,
          fabric.metrics.contracts.Bound arg2) {
            return ((fabric.metrics.contracts.DerivedMetricContract) fetch()).
              fabric$metrics$contracts$DerivedMetricContract$(arg1, arg2);
        }
        
        public _Proxy(DerivedMetricContract._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl
    extends fabric.metrics.contracts.MetricContract._Impl
      implements fabric.metrics.contracts.DerivedMetricContract {
        /**
   * @param metric
   *        the {@link DerivedMetric} this contract asserts a bound on
   * @param bound
   *        the {@link Bound} this {@link MetricContract} asserts on
   *        metric.
   */
        public fabric.metrics.contracts.DerivedMetricContract
          fabric$metrics$contracts$DerivedMetricContract$(
          fabric.metrics.DerivedMetric metric,
          fabric.metrics.contracts.Bound bound) {
            fabric$metrics$contracts$MetricContract$(metric, bound);
            return (fabric.metrics.contracts.DerivedMetricContract)
                     this.$getProxy();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          enforcementStrategy() {
            return ((fabric.metrics.DerivedMetric)
                      fabric.lang.Object._Proxy.$getProxy(getMetric())).
              policyFor(getBound());
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.DerivedMetricContract._Proxy(
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
          implements fabric.metrics.contracts.DerivedMetricContract._Static {
            public _Proxy(fabric.metrics.contracts.DerivedMetricContract.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.DerivedMetricContract.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  DerivedMetricContract.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    DerivedMetricContract.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.DerivedMetricContract._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.DerivedMetricContract._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.DerivedMetricContract._Static {
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
                return new fabric.metrics.contracts.DerivedMetricContract.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 92, -33, 8, -109, 124,
    -50, -63, -40, -8, 87, 86, 37, 22, -96, -62, -116, 86, 66, 90, 45, -63,
    -127, 122, 107, 15, 57, -74, -47, 15, 68, 103, -1 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492294329000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
