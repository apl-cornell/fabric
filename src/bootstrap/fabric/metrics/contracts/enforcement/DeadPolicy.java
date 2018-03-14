package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.Contract;

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
    public long expiry();
    
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
    public void activate();
    
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
        
        public long expiry() {
            return ((fabric.metrics.contracts.enforcement.DeadPolicy) fetch()).
              expiry();
        }
        
        public void apply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.DeadPolicy) fetch()).apply(
                                                                          arg1);
        }
        
        public void unapply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.DeadPolicy) fetch()).unapply(
                                                                          arg1);
        }
        
        public void activate() {
            ((fabric.metrics.contracts.enforcement.DeadPolicy) fetch()).
              activate();
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
        public long expiry() { return 0; }
        
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
        public void activate() {  }
        
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
                return new fabric.metrics.contracts.enforcement.DeadPolicy.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -64, 32, 65, 65, -78,
    -108, -31, -62, -88, 45, 39, 105, 118, 53, 10, 12, 78, 91, 110, -23, -58,
    -78, 110, 8, 90, -26, 110, -34, 117, 56, -60, 47 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1520977993000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
