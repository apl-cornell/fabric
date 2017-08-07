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
    
    public static final byte[] $classHash = new byte[] { 66, 65, -123, -43, -3,
    57, 96, -102, -49, 73, -83, -60, 101, -13, -15, 80, 75, 82, -100, -110, 126,
    -27, -71, -115, -128, 0, 99, -8, 51, 121, -104, -84 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1501602696000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
