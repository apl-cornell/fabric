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
    
    public static final byte[] $classHash = new byte[] { -125, -3, -33, -64, 64,
    84, 75, 113, 45, -74, 14, -61, -116, 69, -94, 90, -114, -12, -11, -28, -25,
    86, -42, -75, -121, 90, 115, 1, -47, -42, -47, 34 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492294329000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
