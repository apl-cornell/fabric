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
    
    public static final byte[] $classHash = new byte[] { -104, -110, -102, 122,
    -15, -110, 39, -69, -68, 81, 88, -71, 78, -76, 22, 100, -89, -28, -111, -92,
    94, 114, 22, -29, 115, -106, -61, -10, 32, -118, -25, -36 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492109732000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
