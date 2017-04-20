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
   * Refresh this policy, updating the expiry.
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
   * Refresh this policy, updating the expiry.
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
    
    public static final byte[] $classHash = new byte[] { 1, 22, 6, 81, 29, -88,
    125, 90, 25, -95, -69, 81, -13, -98, 70, -83, 114, 34, 22, -19, 80, 18, 80,
    -8, 70, -18, 126, -95, -109, -57, -113, 6 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492660216000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
