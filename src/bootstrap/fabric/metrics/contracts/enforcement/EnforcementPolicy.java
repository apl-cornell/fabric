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
   * an {@link contracts.util.Observer Observer} of the necessary
   * {@link contracts.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link MetricContract} to apply this policy to.
   */
    public abstract void apply(fabric.metrics.contracts.MetricContract mc);
    
    /**
   * Update book-keeping to stop using this {@link EnforcementPolicy} for the
   * given {@link MetricContract}. This will remove the given
   * {@link MetricContract} as an {@link contracts.util.Observer Observer} of
   * the necessary {@link contracts.util.Subject Subject}s to use the policy.
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
   * an {@link contracts.util.Observer Observer} of the necessary
   * {@link contracts.util.Subject Subject}s to use the policy.
   *
   * @param mc
   *        the {@link MetricContract} to apply this policy to.
   */
        public abstract void apply(fabric.metrics.contracts.MetricContract mc);
        
        /**
   * Update book-keeping to stop using this {@link EnforcementPolicy} for the
   * given {@link MetricContract}. This will remove the given
   * {@link MetricContract} as an {@link contracts.util.Observer Observer} of
   * the necessary {@link contracts.util.Subject Subject}s to use the policy.
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
    
    public static final byte[] $classHash = new byte[] { -75, -116, 79, -12, 71,
    -127, -77, -119, -95, 117, 119, 124, -31, -89, 5, -76, -112, 54, 111, -124,
    -79, 83, 24, 49, -25, 7, 126, 107, 96, 28, 50, 94 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491929446000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
