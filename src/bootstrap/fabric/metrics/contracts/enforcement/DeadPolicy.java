package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.Contract;
import fabric.worker.metrics.StatsMap;

/**
 * A policy indicating there is no way to enforcing a {@link Contract}.
 */
public interface DeadPolicy
  extends fabric.metrics.contracts.enforcement.EnforcementPolicy,
          fabric.lang.Object
{
    /**
   * @param metric
   *            the Metric the associated contract is bounding.
   * @param rate
   *            the rate of the bound enforced by the associated contract.
   * @param base
   *            the base of the bound enforced by the associated contract.
   */
    public fabric.metrics.contracts.enforcement.DeadPolicy
      fabric$metrics$contracts$enforcement$DeadPolicy$();
    
    /**
   * @return the exipration time of this {@link EnforcementPolicy}.
   */
    public long expiry(fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * Update book-keeping to use this {@link EnforcementPolicy} for the given
   * {@link Contract}. This will add the given {@link Contract} as
   * an {@link metrics.util.Observer Observer} of the necessary
   * {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link Contract} to apply this policy to.
   */
    public void apply(fabric.metrics.contracts.Contract mc);
    
    /**
   * Update book-keeping to stop using this {@link EnforcementPolicy} for the
   * given {@link Contract}. This will remove the given
   * {@link Contract} as an {@link metrics.util.Observer Observer} of
   * the necessary {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link Contract} to stop applying this policy to.
   */
    public void unapply(fabric.metrics.contracts.Contract mc);
    
    /**
   * Activate this policy, activating witnesses and setting the expiry.
   */
    public void activate(fabric.worker.metrics.StatsMap weakStats);
    
    /**
   * Acquire reconfig locks for the evidence used for this policy.
   */
    public void acquireReconfigLocks();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.enforcement.DeadPolicy {
        public fabric.metrics.contracts.enforcement.DeadPolicy
          fabric$metrics$contracts$enforcement$DeadPolicy$() {
            return ((fabric.metrics.contracts.enforcement.DeadPolicy) fetch()).
              fabric$metrics$contracts$enforcement$DeadPolicy$();
        }
        
        public long expiry(fabric.worker.metrics.StatsMap arg1) {
            return ((fabric.metrics.contracts.enforcement.DeadPolicy) fetch()).
              expiry(arg1);
        }
        
        public void apply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.DeadPolicy) fetch()).apply(
                                                                          arg1);
        }
        
        public void unapply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.DeadPolicy) fetch()).unapply(
                                                                          arg1);
        }
        
        public void activate(fabric.worker.metrics.StatsMap arg1) {
            ((fabric.metrics.contracts.enforcement.DeadPolicy) fetch()).
              activate(arg1);
        }
        
        public void acquireReconfigLocks() {
            ((fabric.metrics.contracts.enforcement.DeadPolicy) fetch()).
              acquireReconfigLocks();
        }
        
        public _Proxy(DeadPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.contracts.enforcement.DeadPolicy {
        /**
   * @param metric
   *            the Metric the associated contract is bounding.
   * @param rate
   *            the rate of the bound enforced by the associated contract.
   * @param base
   *            the base of the bound enforced by the associated contract.
   */
        public fabric.metrics.contracts.enforcement.DeadPolicy
          fabric$metrics$contracts$enforcement$DeadPolicy$() {
            fabric$lang$Object$();
            return (fabric.metrics.contracts.enforcement.DeadPolicy)
                     this.$getProxy();
        }
        
        /**
   * @return the exipration time of this {@link EnforcementPolicy}.
   */
        public long expiry(fabric.worker.metrics.StatsMap weakStats) { return 0; }
        
        /**
   * Update book-keeping to use this {@link EnforcementPolicy} for the given
   * {@link Contract}. This will add the given {@link Contract} as
   * an {@link metrics.util.Observer Observer} of the necessary
   * {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link Contract} to apply this policy to.
   */
        public void apply(fabric.metrics.contracts.Contract mc) {  }
        
        /**
   * Update book-keeping to stop using this {@link EnforcementPolicy} for the
   * given {@link Contract}. This will remove the given
   * {@link Contract} as an {@link metrics.util.Observer Observer} of
   * the necessary {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link Contract} to stop applying this policy to.
   */
        public void unapply(fabric.metrics.contracts.Contract mc) {  }
        
        /**
   * Activate this policy, activating witnesses and setting the expiry.
   */
        public void activate(fabric.worker.metrics.StatsMap weakStats) {  }
        
        /**
   * Acquire reconfig locks for the evidence used for this policy.
   */
        public void acquireReconfigLocks() {  }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.enforcement.DeadPolicy._Proxy(
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
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.enforcement.DeadPolicy._Static {
            public _Proxy(fabric.metrics.contracts.enforcement.DeadPolicy.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.enforcement.DeadPolicy.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  enforcement.
                  DeadPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    enforcement.
                    DeadPolicy.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.enforcement.DeadPolicy._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.enforcement.DeadPolicy._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.enforcement.DeadPolicy._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.enforcement.DeadPolicy.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -127, 0, -35, -49, -82,
    0, 93, 126, -57, -65, -114, -21, 16, -81, -4, -1, -34, 18, 89, 122, 44, 14,
    70, 50, -76, 30, 25, -59, -30, 83, -31, -128 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527094903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
