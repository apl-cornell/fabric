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
    
    public static final byte[] $classHash = new byte[] { -19, 91, -127, 123,
    -117, 36, -13, 116, -58, 24, 20, 38, 84, 83, -105, 27, 118, -110, -79, -19,
    121, -114, -53, -35, 124, -49, -127, 80, -108, 6, -89, 7 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
