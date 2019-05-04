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
    
    public static final byte[] $classHash = new byte[] { -103, -116, -55, -13,
    13, -99, 81, 27, -42, -22, -112, -118, 23, -16, 54, 25, -63, 60, -15, -56,
    -49, -88, -53, 41, 73, 18, -7, -56, 96, 83, 89, 2 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
