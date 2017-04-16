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
        public abstract void unapply(
          fabric.metrics.contracts.MetricContract mc);
        
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
    
    public static final byte[] $classHash = new byte[] { 97, 50, 14, -126, -97,
    -5, -10, -80, 122, 4, 36, 1, -17, 111, -87, -93, -77, 54, 94, 33, 111, 119,
    -26, -77, -90, 58, 113, -48, 106, 124, -72, 77 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492294329000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
