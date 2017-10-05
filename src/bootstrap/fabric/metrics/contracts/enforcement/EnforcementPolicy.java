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
    
    public static final byte[] $classHash = new byte[] { 24, 95, 67, 12, -65,
    49, 32, -93, -127, 112, -38, -116, 59, 64, 85, 125, 23, -118, -72, 116, 93,
    46, 80, 5, -96, 44, -59, -61, 114, 83, 14, 71 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1507228495000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
