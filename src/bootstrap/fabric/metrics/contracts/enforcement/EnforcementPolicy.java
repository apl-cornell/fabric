package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.MetricContract;

/**
 * A policy for enforcing a {@link MetricContract}.
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
   * Refresh this policy, updating the expiry time to account for expiry
   * changes in any metric contracts used.
   */
    public abstract void refresh();
    
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
        
        public void refresh() {
            ((fabric.metrics.contracts.enforcement.EnforcementPolicy) fetch()).
              refresh();
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
   * Refresh this policy, updating the expiry time to account for expiry
   * changes in any metric contracts used.
   */
        public abstract void refresh();
        
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
    
    public static final byte[] $classHash = new byte[] { -114, -7, -36, -86,
    -53, 57, -35, -16, 97, -126, -48, -117, -17, -8, 32, 48, 53, -126, 89, -24,
    -115, 59, -70, 19, 116, -19, 52, 87, 47, -96, 127, 87 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1493216501000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
