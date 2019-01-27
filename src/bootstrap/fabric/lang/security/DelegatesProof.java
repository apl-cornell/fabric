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
    
    public static final byte[] $classHash = new byte[] { 53, -91, -92, 17, -128,
    -116, -4, -51, -51, 27, -19, -108, 101, 13, 122, -87, -127, 74, -115, 96,
    -49, 102, -65, 59, -108, -96, -101, 66, -60, -77, -120, -2 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
