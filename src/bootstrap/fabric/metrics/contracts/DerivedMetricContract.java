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
    
    public static final byte[] $classHash = new byte[] { 13, 69, 91, 83, 13, 21,
    -8, 25, 102, -3, 80, 51, 81, -83, 96, 112, 1, -90, -93, -106, 68, 106, 41,
    -111, 63, -125, 6, -8, -76, -101, 77, -74 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492535467000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
