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
    
    public static final byte[] $classHash = new byte[] { 27, 25, -7, -92, -63,
    -101, -102, -9, 58, 65, 11, -80, -20, 27, 55, 66, 34, 30, 28, 56, 70, -79,
    -54, 72, -117, -105, 81, -51, -57, 0, 27, 122 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
