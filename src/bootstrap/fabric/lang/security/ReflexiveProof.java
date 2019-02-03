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
    
    public static final byte[] $classHash = new byte[] { -89, 82, 2, 20, 5, 52,
    107, -116, 102, -54, -94, 20, -120, 99, 11, -84, -54, 97, 102, -5, -75,
    -121, 35, -16, -94, -47, 85, 27, 0, 79, 10, 25 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
