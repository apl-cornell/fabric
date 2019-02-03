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
public interface DelegatesProof extends fabric.lang.security.ActsForProof {
    public fabric.lang.security.DelegatesProof
      fabric$lang$security$DelegatesProof$(
      fabric.lang.security.Principal actor,
      fabric.lang.security.Principal granter);
    
    public void gatherDelegationDependencies(java.util.Set s);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.DelegatesProof {
        public fabric.lang.security.DelegatesProof
          fabric$lang$security$DelegatesProof$(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2) {
            return ((fabric.lang.security.DelegatesProof) fetch()).
              fabric$lang$security$DelegatesProof$(arg1, arg2);
        }
        
        public _Proxy(DelegatesProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl
    extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.DelegatesProof {
        public fabric.lang.security.DelegatesProof
          fabric$lang$security$DelegatesProof$(
          fabric.lang.security.Principal actor,
          fabric.lang.security.Principal granter) {
            fabric$lang$security$ActsForProof$(actor, granter);
            return (fabric.lang.security.DelegatesProof) this.$getProxy();
        }
        
        public void gatherDelegationDependencies(java.util.Set s) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        getGranter(
                          ))) instanceof fabric.lang.security.DisjunctivePrincipal ||
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        getGranter(
                          ))) instanceof fabric.lang.security.ConjunctivePrincipal) {
                return;
            }
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        getActor(
                          ))) instanceof fabric.lang.security.ConjunctivePrincipal &&
                  ((fabric.lang.security.ConjunctivePrincipal)
                     fabric.lang.Object._Proxy.$getProxy(getActor())).
                  get$conjuncts().contains(getGranter())) {
                return;
            }
            s.add(
                new fabric.lang.security.SecurityCache.DelegationPair(
                  getActor(), getGranter()));
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.DelegatesProof) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.DelegatesProof._Proxy(this);
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
          implements fabric.lang.security.DelegatesProof._Static {
            public _Proxy(fabric.lang.security.DelegatesProof._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.DelegatesProof._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  DelegatesProof.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.DelegatesProof._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.DelegatesProof._Static._Impl.class);
                $instance = (fabric.lang.security.DelegatesProof._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.DelegatesProof._Static {
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
                return new fabric.lang.security.DelegatesProof._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -98, -124, 87, -93, 44,
    -59, -78, -23, 46, -73, 18, 126, 53, 55, 85, 81, 102, 69, 39, -117, 101,
    -12, 46, 112, 48, 102, -57, -3, 115, -90, 11, -119 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
