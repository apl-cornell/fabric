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
    
    public static final byte[] $classHash = new byte[] { -99, -61, 39, -124,
    -113, -3, -79, -3, 56, -95, 85, 16, -121, -91, 39, -24, 87, 1, 14, -119, 81,
    -11, -21, 45, -84, -128, -124, 116, 109, 48, -106, 58 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492106131000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
