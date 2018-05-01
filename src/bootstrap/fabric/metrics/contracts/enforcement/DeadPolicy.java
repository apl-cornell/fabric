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
    
    public static final byte[] $classHash = new byte[] { 27, -46, 121, 60, -20,
    -7, 83, 124, 66, 64, 63, 65, 116, -86, 77, 52, 78, -96, 3, 70, -91, 101, 1,
    -59, 84, 110, -24, 13, 106, -4, 112, 71 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525097266000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
