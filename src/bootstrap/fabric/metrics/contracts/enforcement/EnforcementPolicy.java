package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.MetricContract;

/**
 * A policy for enforcing a {@link MetricContract}. This class is responsible
 * for ensuring a {@link MetricContract} (and the API implementation) is
 * monitoring evidence of the {@link MetricContract}'s validity and updating the
 * expiration time correctly. It effectively acts as a bundle of the currently
 * monitored information for enforcing a {@link MetricContract}.
 */
public interface EnforcementPolicy extends fabric.lang.Object {
    public fabric.metrics.contracts.enforcement.EnforcementPolicy
      fabric$metrics$contracts$enforcement$EnforcementPolicy$();
    
    /**
   * @return the exipration time of this {@link EnforcementPolicy}.
   */
    public abstract long expiry();
    
    /**
   * Update book-keeping to use this {@link EnforcementPolicy} for the given
   * {@link MetricContract}. This will add the given {@link MetricContract} as
   * an {@link metrics.util.Observer Observer} of the necessary
   * {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link MetricContract} to apply this policy to.
   */
    public abstract void apply(fabric.metrics.contracts.MetricContract mc);
    
    /**
   * Update book-keeping to stop using this {@link EnforcementPolicy} for the
   * given {@link MetricContract}. This will remove the given
   * {@link MetricContract} as an {@link metrics.util.Observer Observer} of
   * the necessary {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link MetricContract} to stop applying this policy to.
   */
    public abstract void unapply(fabric.metrics.contracts.MetricContract mc);
    
    /**
   * Activate this policy, activating witnesses and setting the expiry.
   */
    public abstract void activate();
    
    /**
   * Acquire reconfig locks for the evidence used for this policy.
   */
    public abstract void acquireReconfigLocks();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.enforcement.EnforcementPolicy {
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          fabric$metrics$contracts$enforcement$EnforcementPolicy$() {
            return ((fabric.metrics.contracts.enforcement.EnforcementPolicy)
                      fetch()).
              fabric$metrics$contracts$enforcement$EnforcementPolicy$();
        }
        
        public long expiry() {
            return ((fabric.metrics.contracts.enforcement.EnforcementPolicy)
                      fetch()).expiry();
        }
        
        public void apply(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              apply(arg1);
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract arg1) {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              unapply(arg1);
        }
        
        public void activate() {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              activate();
        }
        
        public void acquireReconfigLocks() {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              acquireReconfigLocks();
        }
        
        public _Proxy(EnforcementPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.contracts.enforcement.EnforcementPolicy {
        public fabric.metrics.contracts.enforcement.EnforcementPolicy
          fabric$metrics$contracts$enforcement$EnforcementPolicy$() {
            fabric$lang$Object$();
            return (fabric.metrics.contracts.enforcement.EnforcementPolicy)
                     this.$getProxy();
        }
        
        /**
   * @return the exipration time of this {@link EnforcementPolicy}.
   */
        public abstract long expiry();
        
        /**
   * Update book-keeping to use this {@link EnforcementPolicy} for the given
   * {@link MetricContract}. This will add the given {@link MetricContract} as
   * an {@link metrics.util.Observer Observer} of the necessary
   * {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link MetricContract} to apply this policy to.
   */
        public abstract void apply(fabric.metrics.contracts.MetricContract mc);
        
        /**
   * Update book-keeping to stop using this {@link EnforcementPolicy} for the
   * given {@link MetricContract}. This will remove the given
   * {@link MetricContract} as an {@link metrics.util.Observer Observer} of
   * the necessary {@link metrics.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link MetricContract} to stop applying this policy to.
   */
        public abstract void unapply(fabric.metrics.contracts.MetricContract mc);
        
        /**
   * Activate this policy, activating witnesses and setting the expiry.
   */
        public abstract void activate();
        
        /**
   * Acquire reconfig locks for the evidence used for this policy.
   */
        public abstract void acquireReconfigLocks();
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.enforcement.EnforcementPolicy.
                     _Proxy(this);
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
        final class _Proxy
        extends fabric.
          lang.
          Object.
          _Proxy
          implements fabric.metrics.contracts.enforcement.EnforcementPolicy.
                       _Static
        {
            public _Proxy(fabric.metrics.contracts.enforcement.
                            EnforcementPolicy._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.enforcement.
              EnforcementPolicy._Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  enforcement.
                  EnforcementPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    enforcement.
                    EnforcementPolicy.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.enforcement.EnforcementPolicy.
                        _Static._Impl.class);
                $instance =
                  (fabric.metrics.contracts.enforcement.EnforcementPolicy.
                    _Static) impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl
        extends fabric.
          lang.
          Object.
          _Impl
          implements fabric.metrics.contracts.enforcement.EnforcementPolicy.
                       _Static
        {
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
                return new fabric.metrics.contracts.enforcement.
                         EnforcementPolicy._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -2, 58, -122, 118, 11,
    116, -93, -44, -84, -105, 39, -55, -90, -107, -100, -74, -39, 11, 6, 34,
    -95, 55, 121, -15, 16, 68, 102, 27, -118, 120, -19, 64 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507234475000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
