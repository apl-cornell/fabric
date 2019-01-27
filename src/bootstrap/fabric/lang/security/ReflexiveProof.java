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
    
    public static final byte[] $classHash = new byte[] { 40, -107, 47, -33, 18,
    70, 75, -72, 112, 26, -10, 11, -117, -86, 31, -95, -41, 108, 95, 9, 93, 46,
    97, -10, -15, -90, -34, -89, -120, 51, -27, -50 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
