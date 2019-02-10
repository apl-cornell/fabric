package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Set;

/**
 * This code is mostly copied from Jif.
 */
public interface ReflexiveProof extends fabric.lang.security.ActsForProof {
    /**
   * Either p == q or p and q are non null and p.equals(q) and q.equals(p)
   * 
   * @param p
   * @param q
   */
    public fabric.lang.security.ReflexiveProof
      fabric$lang$security$ReflexiveProof$(fabric.lang.security.Principal p,
                                           fabric.lang.security.Principal q);
    
    public void gatherDelegationDependencies(java.util.Set s);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.ReflexiveProof {
        public fabric.lang.security.ReflexiveProof
          fabric$lang$security$ReflexiveProof$(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2) {
            return ((fabric.lang.security.ReflexiveProof) fetch()).
              fabric$lang$security$ReflexiveProof$(arg1, arg2);
        }
        
        public _Proxy(ReflexiveProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl
    extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.ReflexiveProof {
        /**
   * Either p == q or p and q are non null and p.equals(q) and q.equals(p)
   * 
   * @param p
   * @param q
   */
        public fabric.lang.security.ReflexiveProof
          fabric$lang$security$ReflexiveProof$(
          fabric.lang.security.Principal p, fabric.lang.security.Principal q) {
            fabric$lang$security$ActsForProof$(p, q);
            return (fabric.lang.security.ReflexiveProof) this.$getProxy();
        }
        
        public void gatherDelegationDependencies(java.util.Set s) {  }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.ReflexiveProof) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.ReflexiveProof._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.ReflexiveProof._Static {
            public _Proxy(fabric.lang.security.ReflexiveProof._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.ReflexiveProof._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  ReflexiveProof.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.ReflexiveProof._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.ReflexiveProof._Static._Impl.class);
                $instance = (fabric.lang.security.ReflexiveProof._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.ReflexiveProof._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.security.ReflexiveProof._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -14, 3, -75, 68, 115,
    55, 74, -126, 118, -75, 44, 90, 99, -80, 46, -97, -45, 54, -112, 20, 46,
    120, 11, 67, -95, 105, -85, 122, 109, 86, -98, -47 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
