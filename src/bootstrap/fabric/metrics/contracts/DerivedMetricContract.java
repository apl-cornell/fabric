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
    
    public static final byte[] $classHash = new byte[] { -104, -17, 96, 66, 18,
    -40, 25, 17, 41, 9, -79, -44, 17, 126, -113, 109, 70, -81, 88, 66, -64, 105,
    -125, -64, -38, 67, 76, -89, 84, -46, 22, 74 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492294329000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
