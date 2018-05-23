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
    
    public static final byte[] $classHash = new byte[] { -51, 121, -48, 91,
    -100, 126, 118, -9, -125, 52, 11, 79, -62, -101, -105, 20, -75, -15, -5,
    103, 108, 1, -122, -1, 73, -67, -40, 19, 39, 99, -56, 112 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1527094903000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
