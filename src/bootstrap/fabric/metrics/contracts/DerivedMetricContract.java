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
    
    public static final byte[] $classHash = new byte[] { 36, -113, -75, -97, 95,
    -88, 69, -23, -47, 38, 18, 66, 23, -34, 99, -55, 88, -15, 45, 2, -14, -99,
    -50, 26, -27, 91, -6, 76, 37, -127, -58, 60 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492294329000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
