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
    
    public static final byte[] $classHash = new byte[] { -120, 76, 106, 14, -24,
    94, -118, -63, -127, 125, -100, -6, -111, -84, -102, -94, 66, 76, -128, 108,
    46, 119, 40, -25, 24, 51, -80, 89, -58, 31, 60, -74 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1526592698000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
