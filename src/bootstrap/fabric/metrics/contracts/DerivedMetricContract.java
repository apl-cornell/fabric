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
    
    public static final byte[] $classHash = new byte[] { 56, -97, -72, -84, -23,
    -84, -82, 106, -21, -91, 100, 104, 47, 106, -36, 119, 75, -90, -42, 116,
    -119, -6, -96, -54, 29, -114, 3, 100, -82, 35, -69, -80 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491929446000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
