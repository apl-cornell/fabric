package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import fabric.lang.security.Principal;
import fabric.util.ArrayList;
import fabric.util.Collection;
import fabric.util.Map;
import fabric.util.Set;
import fabric.util.HashMap;
import fabric.util.Iterator;
import fabric.util.LinkedHashSet;
import java.util.HashSet;

/**
 * Utility methods for principals. This code is mostly copied from Jif.
 */
public interface PrincipalUtil extends fabric.lang.Object {
    public static interface ProofSearchState extends fabric.lang.Object {
        public fabric.lang.security.SecurityCache.ActsForPair[] get$goalstack();
        
        public fabric.lang.security.SecurityCache.ActsForPair[] set$goalstack(
          fabric.lang.security.SecurityCache.ActsForPair[] val);
        
        public ProofSearchState
          fabric$lang$security$PrincipalUtil$ProofSearchState$(
          fabric.lang.security.Principal p, fabric.lang.security.Principal q);
        
        public boolean contains(fabric.lang.security.Principal p,
                                fabric.lang.security.Principal q);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.Object._Proxy
          implements ProofSearchState {
            public fabric.lang.security.SecurityCache.ActsForPair[]
              get$goalstack() {
                return ((fabric.lang.security.PrincipalUtil.ProofSearchState.
                          _Impl) fetch()).get$goalstack();
            }
            
            public fabric.lang.security.SecurityCache.ActsForPair[]
              set$goalstack(
              fabric.lang.security.SecurityCache.ActsForPair[] val) {
                return ((fabric.lang.security.PrincipalUtil.ProofSearchState.
                          _Impl) fetch()).set$goalstack(val);
            }
            
            public fabric.lang.security.PrincipalUtil.ProofSearchState
              fabric$lang$security$PrincipalUtil$ProofSearchState$(
              fabric.lang.security.Principal arg1,
              fabric.lang.security.Principal arg2) {
                return ((fabric.lang.security.PrincipalUtil.ProofSearchState)
                          fetch()).
                  fabric$lang$security$PrincipalUtil$ProofSearchState$(arg1,
                                                                       arg2);
            }
            
            public boolean contains(fabric.lang.security.Principal arg1,
                                    fabric.lang.security.Principal arg2) {
                return ((fabric.lang.security.PrincipalUtil.ProofSearchState)
                          fetch()).contains(arg1, arg2);
            }
            
            public _Proxy(ProofSearchState._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static class _Impl extends fabric.lang.Object._Impl
          implements ProofSearchState {
            public fabric.lang.security.SecurityCache.ActsForPair[]
              get$goalstack() {
                fabric.worker.transaction.TransactionManager.getInstance().
                  registerRead(this);
                return this.goalstack;
            }
            
            public fabric.lang.security.SecurityCache.ActsForPair[]
              set$goalstack(
              fabric.lang.security.SecurityCache.ActsForPair[] val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.goalstack = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private transient fabric.lang.security.SecurityCache.ActsForPair[]
              goalstack;
            
            public ProofSearchState
              fabric$lang$security$PrincipalUtil$ProofSearchState$(
              fabric.lang.security.Principal p,
              fabric.lang.security.Principal q) {
                this.set$goalstack(
                       new fabric.lang.security.SecurityCache.ActsForPair[1]);
                this.get$goalstack()[0] =
                  new fabric.lang.security.SecurityCache.ActsForPair(p, q);
                return (ProofSearchState) this.$getProxy();
            }
            
            private ProofSearchState
              fabric$lang$security$PrincipalUtil$ProofSearchState$(
              ProofSearchState ss, fabric.lang.security.Principal p,
              fabric.lang.security.Principal q) {
                int len = ss.get$goalstack().length + 1;
                this.set$goalstack(
                       new fabric.lang.security.SecurityCache.ActsForPair[len]);
                for (int i = 0; i < len - 1; i++)
                    this.get$goalstack()[i] = ss.get$goalstack()[i];
                this.get$goalstack()[len - 1] =
                  new fabric.lang.security.SecurityCache.ActsForPair(p, q);
                return (ProofSearchState) this.$getProxy();
            }
            
            public boolean contains(fabric.lang.security.Principal p,
                                    fabric.lang.security.Principal q) {
                for (int i = 0; i < this.get$goalstack().length; i++) {
                    if (!fabric.lang.Object._Proxy.idEquals(
                                                     this.get$goalstack()[i],
                                                     null)) {
                        if (fabric.lang.security.PrincipalUtil._Impl.
                              eq(this.get$goalstack()[i].p, p) &&
                              fabric.lang.security.PrincipalUtil._Impl.
                              eq(this.get$goalstack()[i].q, q)) {
                            return true;
                        }
                    }
                }
                return false;
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (ProofSearchState) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.security.PrincipalUtil.ProofSearchState.
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
            
            public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
                super.$copyAppStateFrom(other);
                fabric.lang.security.PrincipalUtil.ProofSearchState._Impl src =
                  (fabric.lang.security.PrincipalUtil.ProofSearchState._Impl)
                    other;
                this.goalstack = src.goalstack;
            }
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy
            extends fabric.
              lang.
              Object.
              _Proxy
              implements fabric.lang.security.PrincipalUtil.ProofSearchState.
                           _Static
            {
                public _Proxy(fabric.lang.security.PrincipalUtil.
                                ProofSearchState._Static._Impl impl) {
                    super(impl);
                }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.lang.security.PrincipalUtil.
                  ProofSearchState._Static $instance;
                
                static {
                    fabric.
                      lang.
                      security.
                      PrincipalUtil.
                      ProofSearchState.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        lang.
                        security.
                        PrincipalUtil.
                        ProofSearchState.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.lang.security.PrincipalUtil.ProofSearchState.
                            _Static._Impl.class);
                    $instance =
                      (fabric.lang.security.PrincipalUtil.ProofSearchState.
                        _Static) impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl
            extends fabric.
              lang.
              Object.
              _Impl
              implements fabric.lang.security.PrincipalUtil.ProofSearchState.
                           _Static
            {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.lang.security.PrincipalUtil.
                             ProofSearchState._Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { -10, -28, 63, 23,
        89, -128, 32, 106, 96, -46, -79, -10, -22, 107, 27, -110, -4, -23, 19,
        44, -1, -112, -32, 2, 103, -113, 107, 41, 124, 61, -126, 116 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1536693771000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAALVYbWwUxxmeO39/gD+IbTDYOOaKhGPuSqCpEjdt4RrDkaNY2CDFKJi5vbnz4r3dze4cnNMQuZFaUH6QNHXcRAn0D21p4oJSCeVHZTU/GpooVdWgql9SG/9omqQUNVFVWimk9H1ndm/39s6HIrWWdmY8874z7+cz79zidVJnW2QwQ1OqFuWzJrOjozSVSI5Ry2bpuEZtewJmp5SW2sTC+z9I94dJOElaFaobuqpQbUq3OVmdPEaP05jOeOzggcTIYdKkIOMeak9zEj68q2CRAdPQZrOawZ1DyvZ/9q7Y/HeOtP+4hrRNkjZVH+eUq0rc0Dkr8EnSmmO5FLPsnek0S0+SDp2x9DizVKqpjwKhoU+STlvN6pTnLWYfYLahHUfCTjtvMkuc6U6i+AaIbeUVblggfrsUP89VLZZUbT6SJPUZlWlp+xHyOKlNkrqMRrNA2J10tYiJHWOjOA/kzSqIaWWowlyW2hlVT3OyMchR1DjyIBAAa0OO8WmjeFStTmGCdEqRNKpnY+PcUvUskNYZeTiFk94VNwWiRpMqMzTLpjhZG6Qbk0tA1STMgiycdAXJxE7gs96Az3zeuv7VL5z5mr5HD5MQyJxmiobyNwJTf4DpAMswi+kKk4ytQ8kF2r10OkwIEHcFiCXNq4999OXh/tfekDTrK9DsTx1jCp9SzqdWv70hvuXeGhSj0TRsFUOhRHPh1TFnZaRgQrR3F3fExai7+NqBKw/NvcSuhUlzgtQrhpbPQVR1KEbOVDVm7WY6syhn6QRpYno6LtYTpAHGSVVncnZ/JmMzniC1mpiqN8T/YKIMbIEmaoCxqmcMd2xSPi3GBZMQ0gEfqYHvD4T0tUP/CiE9zZwcik0bORZLaXl2AsI7Bh+jljIdg7y1VGWrYpizMdtSYlZe5ypQynkZPzZT8pbKZ2NjEEeKalLtIKoNEpn/t50LqFP7iVAIzL1RMdIsRW3wnRNHu8Y0SJU9hpZm1pSinVlKkDVLz4tYasL4tyGGhbVC4P8NQeTw887ndz3w0cWpt2QcIq9jTE62S0mjKGnUlTRaImlkzDKMzLhQGPGGgbStmHhRgLIoQNliqBCNn0u8LOKr3haJWDygFQ64z9QozxhWrkBCIaHtHYJfBBaExQzADSBK65bxh/cePT0Iri2YJ2rBsUgaCeaXh0oJGFFImiml7dT7Ny4tnDS8TOMkUgYA5ZyYwINB01mGwtIAkN72QwP08tTSyUgYwacJcJFTiFwAmf7gGSWJPOKCIlqjLkla0AZUwyUXyZr5tGWc8GZESKzGplNGBxorIKDA0/vHzbO/++UH28VN40Jvmw+jxxkf8aU7btYmErvDs/2ExRjQ/fG5sW8/e/3UYWF4oNhU6cAItnFIcwr5bVjfeOOR37/zp/O/DnvO4qTBtNTjECEFoUzHLfgLwfcf/DBpcQJ7gO64AxgDRcQw8ejNnnCAHRrgF8huRw7qOSOtZlSa0hiGys22z2y7/Lcz7dLfGsxI61lk+PYbePPrdpG5t478q19sE1Lw7vIM6JFJQFzj7bzTsugsylH4+tW+539Oz0LoA5zZ6qNMIBQRBiHCg3cLW2wV7bbA2g5sBqW1NhQjPng5jOIt6wXjZGzxxd74F69JJCgGI+5xZwUkOER9eXL3S7l/hgfrXw+ThknSLi54qvNDFIAN4mASrmg77kwmyaqS9dLrVt4tI8Vk2xBMBN+xwTTwEAjGSI3jZhn5MnDAEN1opDh8lwlZOyT7nk9wdY2J7R2FEBGD+wTLJtFuxmaLMGQYh0OcNHGL6rbKdF4o7i0c0OXsedPpr/n2BrasQTUQX5kBh6z3F357wQbC//IaPgLp9KvZvy/IazhYDPgIP1x859rVVX0XBYbUItILtYNVVHmRVFL7CCu1llop5lipu5KVBGkXJ9GKQD/uDOJUmWaRnQq3Rw1rjKqWYFsHdkD41gyoYQvFCA45kYr/34PNV1xf7F3BF5zUm/mUpkKG1WVUncqb7y6Y1pie5dMVgh6uoBwA13GnImKn55+8FT0zLxNelo2byio3P48sHcVBq8RpBTjlzmqnCI7R9y6d/MmFk6ekPztLi6AH9HzuR7/55BfR55bfrHCZ1oDv8J+4Z6uwNIDrBYkhwgdxzdAZwhEu7XYJ5MWoGtFi2e5SlNsf1Cl7kewTseIl/PK1vnvjM+9mpTobA+oHqX+4b/HN3ZuVZ8KkppjZZcV5KdNIaT43WwzeFvpESVYPyCARpqkcISEc7pNRUQUv01XWMtgchQBT0LCuPds9g0vIkrYUV2uhOnJAJYOPKw82wniWW282yb77Y1+y+XCcYLT1rfQ0EJF2/on5c+n939sWdhSYgCOd95u3T60YP1yUoQX3Pgbfq5DwC07/eX/Cy6pBaIZNqhT1mh2We5z+s0Hxt64Uuv3VK0UXMErrPZycFW2hiusexwbKlx3yiAgeEXGPiFQvRiOevp6VWnHrOfiWCFl3wel3rGClirE4FLgtWpxNtjv98Mp2qxG71IgThP6eEb5ZxQinsZn7HxpBhMrn4LtCSO+HTn/l04UKsrzu9D+9bagEtH2qirbfwuZJThqd+sUWVGMOUGM3DuVkyjA0Btd3BcU2wvc2Ies/dvr3Pp1iyPIXp19eWTG/yC9UWTuLzQInLRFVV3mSpphmu4nT6U8cCUKVk6UAcFX21nI2Gbz9Ow0pe7FaqfCSdH7tUOI/Y+fffXC4a4VX5Nqy358cvovn2hp7zh38rSxe3F8ymuB9kclrmr+e843rTYtlVGGfJlndmaK7ANdcJXUgGtyhUPv7kvxlMKuPnEO9D52f4iIgp6TA/y4JT/aWNt8VhL15C39pW/xHz7/rGyeWxdsFXDhw489f6nlobuDY0auv3PjrzPpnbn6wZvjW08vh7FMzWx67/wn+X7OKdjQBFAAA";
    }
    
    public static interface TopPrincipal extends fabric.lang.security.Principal
    {
        public TopPrincipal fabric$lang$security$PrincipalUtil$TopPrincipal$();
        
        public java.lang.String name();
        
        public boolean delegatesTo(fabric.lang.security.Principal p);
        
        public boolean equals(fabric.lang.security.Principal p);
        
        public boolean isAuthorized(java.lang.Object authPrf,
                                    fabric.lang.security.Closure closure,
                                    fabric.lang.security.Label lb,
                                    boolean executeNow);
        
        public fabric.lang.security.ActsForProof findProofUpto(
          fabric.worker.Store store, fabric.lang.security.Principal p,
          java.lang.Object searchState);
        
        public fabric.lang.security.ActsForProof findProofDownto(
          fabric.worker.Store store, fabric.lang.security.Principal q,
          java.lang.Object searchState);
        
        public fabric.lang.Object $initLabels();
        
        public static class _Proxy extends fabric.lang.security.Principal._Proxy
          implements TopPrincipal {
            public fabric.lang.security.PrincipalUtil.TopPrincipal
              fabric$lang$security$PrincipalUtil$TopPrincipal$() {
                return ((fabric.lang.security.PrincipalUtil.TopPrincipal)
                          fetch()).
                  fabric$lang$security$PrincipalUtil$TopPrincipal$();
            }
            
            public _Proxy(TopPrincipal._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
        }
        
        public static final class _Impl
        extends fabric.lang.security.Principal._Impl implements TopPrincipal {
            public TopPrincipal
              fabric$lang$security$PrincipalUtil$TopPrincipal$() {
                fabric$lang$security$Principal$();
                return (TopPrincipal) this.$getProxy();
            }
            
            public java.lang.String name() { return "*"; }
            
            public boolean delegatesTo(fabric.lang.security.Principal p) {
                return false;
            }
            
            public boolean equals(fabric.lang.security.Principal p) {
                return fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(p)) instanceof TopPrincipal;
            }
            
            public boolean isAuthorized(java.lang.Object authPrf,
                                        fabric.lang.security.Closure closure,
                                        fabric.lang.security.Label lb,
                                        boolean executeNow) { return false; }
            
            public fabric.lang.security.ActsForProof findProofUpto(
              fabric.worker.Store store, fabric.lang.security.Principal p,
              java.lang.Object searchState) {
                return null;
            }
            
            public fabric.lang.security.ActsForProof findProofDownto(
              fabric.worker.Store store, fabric.lang.security.Principal q,
              java.lang.Object searchState) {
                return null;
            }
            
            public fabric.lang.Object $initLabels() {
                this.set$$updateLabel(
                       fabric.lang.security.LabelUtil._Impl.noComponents());
                this.set$$accessPolicy(
                       fabric.lang.security.LabelUtil._Impl.bottomConf());
                return (TopPrincipal) this.$getProxy();
            }
            
            public _Impl(fabric.worker.Store $location) { super($location); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.security.PrincipalUtil.TopPrincipal.
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
        }
        
        interface _Static extends fabric.lang.Object, Cloneable {
            final class _Proxy extends fabric.lang.Object._Proxy
              implements fabric.lang.security.PrincipalUtil.TopPrincipal._Static
            {
                public _Proxy(fabric.lang.security.PrincipalUtil.TopPrincipal.
                                _Static._Impl impl) { super(impl); }
                
                public _Proxy(fabric.worker.Store store, long onum) {
                    super(store, onum);
                }
                
                public static final fabric.lang.security.PrincipalUtil.
                  TopPrincipal._Static $instance;
                
                static {
                    fabric.
                      lang.
                      security.
                      PrincipalUtil.
                      TopPrincipal.
                      _Static.
                      _Impl
                      impl =
                      (fabric.
                        lang.
                        security.
                        PrincipalUtil.
                        TopPrincipal.
                        _Static.
                        _Impl)
                        fabric.lang.Object._Static._Proxy.
                        $makeStaticInstance(
                          fabric.lang.security.PrincipalUtil.TopPrincipal.
                            _Static._Impl.class);
                    $instance =
                      (fabric.lang.security.PrincipalUtil.TopPrincipal._Static)
                        impl.$getProxy();
                    impl.$init();
                }
            }
            
            class _Impl extends fabric.lang.Object._Impl
              implements fabric.lang.security.PrincipalUtil.TopPrincipal._Static
            {
                public void $serialize(java.io.ObjectOutput out,
                                       java.util.List refTypes,
                                       java.util.List intraStoreRefs,
                                       java.util.List interStoreRefs)
                      throws java.io.IOException {
                    super.$serialize(out, refTypes, intraStoreRefs,
                                     interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store,
                             long onum,
                             int version,
                             fabric.worker.metrics.
                               ImmutableObjectSet associates, long expiry,
                             fabric.worker.Store labelStore, long labelOnum,
                             fabric.worker.Store accessPolicyStore,
                             long accessPolicyOnum, java.io.ObjectInput in,
                             java.util.Iterator refTypes,
                             java.util.Iterator intraStoreRefs,
                             java.util.Iterator interStoreRefs)
                      throws java.
                  io.
                  IOException,
                    java.
                  lang.
                  ClassNotFoundException {
                    super(store, onum, version, associates, expiry, labelStore,
                          labelOnum, accessPolicyStore, accessPolicyOnum, in,
                          refTypes, intraStoreRefs, interStoreRefs);
                }
                
                public _Impl(fabric.worker.Store store) { super(store); }
                
                protected fabric.lang.Object._Proxy $makeProxy() {
                    return new fabric.lang.security.PrincipalUtil.TopPrincipal.
                             _Static._Proxy(this);
                }
                
                private void $init() {  }
            }
            
        }
        
        public static final byte[] $classHash = new byte[] { 82, 60, -85, -31,
        58, -11, -22, 77, -70, 37, 57, -117, -93, -52, -37, -71, 105, 45, 14,
        93, 9, -64, -84, -24, -18, -16, 97, 43, 125, 83, 77, 121 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1536693771000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
    }
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.PrincipalUtil {
        public static boolean acts_for(fabric.lang.security.Principal arg1,
                                       fabric.lang.security.Principal arg2) {
            return fabric.lang.security.PrincipalUtil._Impl.acts_for(arg1,
                                                                     arg2);
        }
        
        public static boolean actsFor(fabric.lang.security.Principal arg1,
                                      fabric.lang.security.Principal arg2) {
            return fabric.lang.security.PrincipalUtil._Impl.actsFor(arg1, arg2);
        }
        
        public static fabric.lang.security.ActsForProof actsForProof(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.Principal arg3) {
            return fabric.lang.security.PrincipalUtil._Impl.actsForProof(arg1,
                                                                         arg2,
                                                                         arg3);
        }
        
        public static void notifyNewDelegation(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2) {
            fabric.lang.security.PrincipalUtil._Impl.notifyNewDelegation(arg1,
                                                                         arg2);
        }
        
        public static void notifyRevokeDelegation(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2) {
            fabric.lang.security.PrincipalUtil._Impl.notifyRevokeDelegation(
                                                       arg1, arg2);
        }
        
        public static fabric.lang.security.ActsForProof findActsForProof(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.Principal arg3, java.lang.Object arg4) {
            return fabric.lang.security.PrincipalUtil._Impl.findActsForProof(
                                                              arg1, arg2, arg3,
                                                              arg4);
        }
        
        public static boolean verifyProof(
          fabric.lang.security.ActsForProof arg1,
          fabric.lang.security.Principal arg2,
          fabric.lang.security.Principal arg3) {
            return fabric.lang.security.PrincipalUtil._Impl.verifyProof(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public static boolean delegatesTo(fabric.lang.security.Principal arg1,
                                          fabric.lang.security.Principal arg2) {
            return fabric.lang.security.PrincipalUtil._Impl.delegatesTo(arg1,
                                                                        arg2);
        }
        
        public static boolean equivalentTo(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2) {
            return fabric.lang.security.PrincipalUtil._Impl.equivalentTo(arg1,
                                                                         arg2);
        }
        
        public static boolean equals(fabric.lang.security.Principal arg1,
                                     fabric.lang.security.Principal arg2) {
            return fabric.lang.security.PrincipalUtil._Impl.equals(arg1, arg2);
        }
        
        public static java.lang.Object execute(
          fabric.lang.security.Principal arg1, java.lang.Object arg2,
          fabric.lang.security.Closure arg3, fabric.lang.security.Label arg4) {
            return fabric.lang.security.PrincipalUtil._Impl.execute(arg1, arg2,
                                                                    arg3, arg4);
        }
        
        public static fabric.lang.security.Capability authorize(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          java.lang.Object arg3, fabric.lang.security.Closure arg4,
          fabric.lang.security.Label arg5) {
            return fabric.lang.security.PrincipalUtil._Impl.authorize(arg1,
                                                                      arg2,
                                                                      arg3,
                                                                      arg4,
                                                                      arg5);
        }
        
        public static fabric.lang.security.Capability authorize(
          fabric.lang.security.Principal arg1, java.lang.Object arg2,
          fabric.lang.security.Closure arg3, fabric.lang.security.Label arg4) {
            return fabric.lang.security.PrincipalUtil._Impl.authorize(arg1,
                                                                      arg2,
                                                                      arg3,
                                                                      arg4);
        }
        
        public static fabric.lang.security.Principal nullPrincipal() {
            return fabric.lang.security.PrincipalUtil._Impl.nullPrincipal();
        }
        
        public static fabric.lang.security.Principal bottomPrincipal() {
            return fabric.lang.security.PrincipalUtil._Impl.bottomPrincipal();
        }
        
        public static fabric.lang.security.Principal topPrincipal() {
            return fabric.lang.security.PrincipalUtil._Impl.topPrincipal();
        }
        
        public static boolean isTopPrincipal(
          fabric.lang.security.Principal arg1) {
            return fabric.lang.security.PrincipalUtil._Impl.isTopPrincipal(
                                                              arg1);
        }
        
        public static fabric.lang.security.ConfPolicy readableByPrinPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2) {
            return fabric.lang.security.PrincipalUtil._Impl.
              readableByPrinPolicy(arg1, arg2);
        }
        
        public static fabric.lang.security.IntegPolicy writableByPrinPolicy(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2) {
            return fabric.lang.security.PrincipalUtil._Impl.
              writableByPrinPolicy(arg1, arg2);
        }
        
        public static fabric.lang.security.Principal disjunction(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.Principal arg3) {
            return fabric.lang.security.PrincipalUtil._Impl.disjunction(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public static fabric.lang.security.Principal conjunction(
          fabric.worker.Store arg1, fabric.lang.security.Principal arg2,
          fabric.lang.security.Principal arg3) {
            return fabric.lang.security.PrincipalUtil._Impl.conjunction(arg1,
                                                                        arg2,
                                                                        arg3);
        }
        
        public static fabric.lang.security.Principal disjunction(
          fabric.worker.Store arg1, fabric.util.Collection arg2) {
            return fabric.lang.security.PrincipalUtil._Impl.disjunction(arg1,
                                                                        arg2);
        }
        
        public static fabric.lang.security.Principal conjunction(
          fabric.worker.Store arg1, fabric.util.Collection arg2) {
            return fabric.lang.security.PrincipalUtil._Impl.conjunction(arg1,
                                                                        arg2);
        }
        
        public static java.lang.String toString(
          fabric.lang.security.Principal arg1) {
            return fabric.lang.security.PrincipalUtil._Impl.toString(arg1);
        }
        
        public static java.lang.String stringValue(
          fabric.lang.security.Principal arg1) {
            return fabric.lang.security.PrincipalUtil._Impl.stringValue(arg1);
        }
        
        public _Proxy(PrincipalUtil._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.PrincipalUtil {
        /**
   * Returns true if and only if the principal p acts for the principal q. A
   * synonym for the <code>actsFor</code> method.
   */
        public static boolean acts_for(fabric.lang.security.Principal p,
                                       fabric.lang.security.Principal q) {
            return fabric.lang.security.PrincipalUtil._Impl.actsFor(p, q);
        }
        
        /**
   * Returns true if and only if the principal p acts for the principal q.
   */
        public static boolean actsFor(fabric.lang.security.Principal p,
                                      fabric.lang.security.Principal q) {
            final fabric.worker.Store localStore =
              fabric.worker.Worker.getWorker().getLocalStore();
            return !fabric.lang.Object._Proxy.
              idEquals(
                fabric.lang.security.PrincipalUtil._Impl.actsForProof(
                                                           localStore, p, q),
                null);
        }
        
        /**
   * Returns an actsfor proof if and only if the principal p acts for the
   * principal q.
   */
        public static fabric.lang.security.ActsForProof actsForProof(
          fabric.worker.Store store, fabric.lang.security.Principal p,
          fabric.lang.security.Principal q) {
            final fabric.worker.Store localStore =
              fabric.worker.Worker.getWorker().getLocalStore();
            final fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            fabric.lang.security.SecurityCache.ActsForPair pair =
              new fabric.lang.security.SecurityCache.ActsForPair(p, q);
            if (cache.containsActsFor(pair)) { return cache.getActsFor(pair); }
            if (cache.containsNotActsFor(pair)) return null;
            if (fabric.lang.security.PrincipalUtil._Impl.delegatesTo(q, p))
                return (fabric.lang.security.DelegatesProof)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.lang.security.DelegatesProof)
                              new fabric.lang.security.DelegatesProof._Impl(
                                store).$getProxy()).
                               fabric$lang$security$DelegatesProof$(p, q));
            if (fabric.lang.security.PrincipalUtil._Impl.eq(p, q))
                return (fabric.lang.security.ReflexiveProof)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.lang.security.ReflexiveProof)
                              new fabric.lang.security.ReflexiveProof._Impl(
                                store).$getProxy()).
                               fabric$lang$security$ReflexiveProof$(p, q));
            fabric.lang.security.ActsForProof prf =
              fabric.lang.security.PrincipalUtil._Impl.findActsForProof(store,
                                                                        p, q,
                                                                        null);
            if (!fabric.lang.Object._Proxy.idEquals(prf, null) &&
                  fabric.lang.security.PrincipalUtil._Impl.verifyProof(prf, p,
                                                                       q)) {
                cache.putActsFor(pair, prf);
                java.util.Set s = new java.util.HashSet();
                prf.gatherDelegationDependencies(s);
                for (java.util.Iterator iter = s.iterator(); iter.hasNext(); ) {
                    fabric.lang.security.SecurityCache.DelegationPair del =
                      (fabric.lang.security.SecurityCache.DelegationPair)
                        iter.next();
                    cache.addActsForDependency(del, pair);
                }
                return prf;
            }
            cache.addNotActsFor(pair);
            return null;
        }
        
        /**
   * Notification that a new delegation has been created.
   */
        public static void notifyNewDelegation(
          fabric.lang.security.Principal granter,
          fabric.lang.security.Principal superior) {
            if (!fabric.lang.security.PrincipalUtil._Impl.delegatesTo(granter,
                                                                      superior))
                return;
            fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache().clearNotActsFor();
            fabric.lang.security.LabelUtil._Impl.notifyNewDelegation(granter,
                                                                     superior);
        }
        
        /**
   * Notification that an existing delegation has been revoked.
   */
        public static void notifyRevokeDelegation(
          fabric.lang.security.Principal granter,
          fabric.lang.security.Principal superior) {
            fabric.lang.security.SecurityCache cache =
              fabric.worker.transaction.TransactionManager.getInstance().
              getSecurityCache();
            fabric.lang.security.SecurityCache.DelegationPair del =
              new fabric.lang.security.SecurityCache.DelegationPair(superior,
                                                                    granter);
            cache.notifyRevokedDelegation(del);
        }
        
        /**
   * Search for an ActsForProof between p and q. An ActsForProof between p and q
   * is a a checkable proof object.
   *
   * @param p
   * @param q
   * @param searchState
   *          records the goals that we are in the middle of attempting
   * @return An ActsForPoorf between p and q, or null if none can be found.
   */
        public static fabric.lang.security.ActsForProof findActsForProof(
          fabric.worker.Store store, fabric.lang.security.Principal p,
          fabric.lang.security.Principal q, java.lang.Object searchState) {
            final fabric.worker.Store localStore =
              fabric.worker.Worker.getWorker().getLocalStore();
            if (fabric.lang.Object._Proxy.idEquals(q, null)) {
                return (fabric.lang.security.DelegatesProof)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.lang.security.DelegatesProof)
                              new fabric.lang.security.DelegatesProof._Impl(
                                store).$getProxy()).
                               fabric$lang$security$DelegatesProof$(p, q));
            }
            if (fabric.lang.security.PrincipalUtil._Impl.eq(p, q)) {
                return (fabric.lang.security.ReflexiveProof)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.lang.security.ReflexiveProof)
                              new fabric.lang.security.ReflexiveProof._Impl(
                                store).$getProxy()).
                               fabric$lang$security$ReflexiveProof$(p, q));
            }
            ProofSearchState newss;
            if (fabric.lang.Object._Proxy.
                  $getProxy(searchState) instanceof ProofSearchState) {
                ProofSearchState
                  ss =
                  (ProofSearchState)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      fabric.lang.WrappedJavaInlineable.$wrap(searchState));
                if (ss.contains(p, q)) { return null; }
                newss =
                  (ProofSearchState)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      ((fabric.lang.security.PrincipalUtil.ProofSearchState.
                         _Impl)
                         ((fabric.lang.security.PrincipalUtil.ProofSearchState)
                            new fabric.lang.security.PrincipalUtil.
                              ProofSearchState._Impl(localStore).
                            $getProxy()).fetch()).
                          fabric$lang$security$PrincipalUtil$ProofSearchState$(
                            ss, p, q));
            }
            else {
                newss =
                  (ProofSearchState)
                    fabric.lang.Object._Proxy.
                    $getProxy(
                      ((fabric.lang.security.PrincipalUtil.ProofSearchState)
                         new fabric.lang.security.PrincipalUtil.
                           ProofSearchState._Impl(localStore).
                         $getProxy()).
                          fabric$lang$security$PrincipalUtil$ProofSearchState$(
                            p, q));
            }
            fabric.lang.security.ActsForProof prf;
            boolean doneDownTo = false;
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p)) instanceof fabric.lang.security.ConjunctivePrincipal ||
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p)) instanceof fabric.lang.security.DisjunctivePrincipal) {
                prf =
                  p.findProofDownto(
                      store,
                      q,
                      (java.lang.Object)
                        fabric.lang.WrappedJavaInlineable.$unwrap(newss));
                if (!fabric.lang.Object._Proxy.idEquals(prf, null)) return prf;
                doneDownTo = true;
            }
            prf =
              q.findProofUpto(
                  store,
                  p,
                  (java.lang.Object)
                    fabric.lang.WrappedJavaInlineable.$unwrap(newss));
            if (!fabric.lang.Object._Proxy.idEquals(prf, null)) return prf;
            if (!doneDownTo && !fabric.lang.Object._Proxy.idEquals(p, null)) {
                prf =
                  p.findProofDownto(
                      store,
                      q,
                      (java.lang.Object)
                        fabric.lang.WrappedJavaInlineable.$unwrap(newss));
                if (!fabric.lang.Object._Proxy.idEquals(prf, null)) return prf;
            }
            return null;
        }
        
        /**
   * Return whether principals p and q are equal. p and q must either be
   * references to the same object, both be null, or agree that they are equal
   * to the other.
   */
        private static boolean eq(fabric.lang.security.Principal p,
                                  fabric.lang.security.Principal q) {
            return fabric.lang.Object._Proxy.idEquals(p, q) ||
              !fabric.lang.Object._Proxy.idEquals(p, null) &&
              !fabric.lang.Object._Proxy.idEquals(q, null) && p.equals(q) &&
              q.equals(p);
        }
        
        /**
   * Verify that the chain is a valid delegates-chain between p and q. That is,
   * q == chain[n], chain[n] delegates to chain[n-1], ..., chain[0] == p, i.e.,
   * p acts for q.
   */
        public static boolean verifyProof(
          fabric.lang.security.ActsForProof prf,
          fabric.lang.security.Principal actor,
          fabric.lang.security.Principal granter) {
            if (fabric.lang.Object._Proxy.idEquals(prf, null)) return false;
            if (!fabric.lang.Object._Proxy.idEquals(prf.getActor(), actor) ||
                  !fabric.lang.Object._Proxy.idEquals(prf.getGranter(),
                                                      granter))
                return false;
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        prf)) instanceof fabric.lang.security.DelegatesProof) {
                return fabric.lang.security.PrincipalUtil._Impl.delegatesTo(
                                                                  granter,
                                                                  actor);
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy((java.lang.Object)
                                   fabric.lang.WrappedJavaInlineable.$unwrap(prf)) instanceof fabric.lang.security.ReflexiveProof) {
                return fabric.lang.security.PrincipalUtil._Impl.eq(actor,
                                                                   granter);
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy((java.lang.Object)
                                   fabric.lang.WrappedJavaInlineable.$unwrap(prf)) instanceof fabric.lang.security.TransitiveProof) {
                fabric.lang.security.TransitiveProof proof =
                  (fabric.lang.security.TransitiveProof)
                    fabric.lang.Object._Proxy.$getProxy(prf);
                return fabric.lang.security.PrincipalUtil._Impl.
                  verifyProof(proof.getActorToP(), proof.getActor(),
                              proof.getP()) &&
                  fabric.lang.security.PrincipalUtil._Impl.
                  verifyProof(proof.getPToGranter(), proof.getP(),
                              proof.getGranter());
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy((java.lang.Object)
                                   fabric.lang.WrappedJavaInlineable.$unwrap(prf)) instanceof fabric.lang.security.FromDisjunctProof) {
                fabric.lang.security.FromDisjunctProof proof =
                  (fabric.lang.security.FromDisjunctProof)
                    fabric.lang.Object._Proxy.$getProxy(prf);
                if (fabric.lang.Object._Proxy.
                      $getProxy((java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(actor)) instanceof fabric.lang.security.DisjunctivePrincipal) {
                    fabric.lang.security.DisjunctivePrincipal dp =
                      (fabric.lang.security.DisjunctivePrincipal)
                        fabric.lang.Object._Proxy.$getProxy(actor);
                    for (fabric.util.Iterator iter =
                           dp.get$disjuncts().iterator();
                         iter.hasNext();
                         ) {
                        fabric.lang.security.Principal disjunct =
                          (fabric.lang.security.Principal)
                            fabric.lang.Object._Proxy.$getProxy(iter.next());
                        fabric.lang.security.ActsForProof pr =
                          (fabric.lang.security.ActsForProof)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        proof.getDisjunctProofs(
                                                                ).get(
                                                                    disjunct));
                        if (!fabric.lang.security.PrincipalUtil._Impl.
                              verifyProof(pr, disjunct, granter))
                            return false;
                    }
                    return true;
                }
            }
            else if (fabric.lang.Object._Proxy.
                       $getProxy((java.lang.Object)
                                   fabric.lang.WrappedJavaInlineable.$unwrap(prf)) instanceof fabric.lang.security.ToConjunctProof) {
                fabric.lang.security.ToConjunctProof proof =
                  (fabric.lang.security.ToConjunctProof)
                    fabric.lang.Object._Proxy.$getProxy(prf);
                if (fabric.lang.Object._Proxy.
                      $getProxy((java.lang.Object)
                                  fabric.lang.WrappedJavaInlineable.$unwrap(granter)) instanceof fabric.lang.security.ConjunctivePrincipal) {
                    fabric.lang.security.ConjunctivePrincipal cp =
                      (fabric.lang.security.ConjunctivePrincipal)
                        fabric.lang.Object._Proxy.$getProxy(granter);
                    for (fabric.util.Iterator iter =
                           cp.get$conjuncts().iterator();
                         iter.hasNext();
                         ) {
                        fabric.lang.security.Principal conjunct =
                          (fabric.lang.security.Principal)
                            fabric.lang.Object._Proxy.$getProxy(iter.next());
                        fabric.lang.security.ActsForProof pr =
                          (fabric.lang.security.ActsForProof)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        proof.getConjunctProofs(
                                                                ).get(
                                                                    conjunct));
                        if (!fabric.lang.security.PrincipalUtil._Impl.
                              verifyProof(pr, actor, conjunct))
                            return false;
                    }
                    return true;
                }
            }
            return false;
        }
        
        public static boolean delegatesTo(
          fabric.lang.security.Principal granter,
          fabric.lang.security.Principal superior) {
            if (fabric.lang.Object._Proxy.idEquals(granter, null)) return true;
            if (fabric.lang.security.PrincipalUtil._Impl.isTopPrincipal(
                                                           superior))
                return true;
            if (fabric.lang.Object._Proxy.
                  $getProxy((java.lang.Object)
                              fabric.lang.WrappedJavaInlineable.$unwrap(superior)) instanceof fabric.lang.security.ConjunctivePrincipal) {
                fabric.lang.security.ConjunctivePrincipal cp =
                  (fabric.lang.security.ConjunctivePrincipal)
                    fabric.lang.Object._Proxy.$getProxy(superior);
                for (fabric.util.Iterator iter = cp.get$conjuncts().iterator();
                     iter.hasNext(); ) {
                    fabric.lang.security.Principal conjunct =
                      (fabric.lang.security.Principal)
                        fabric.lang.Object._Proxy.$getProxy(iter.next());
                    if (fabric.lang.security.PrincipalUtil._Impl.equals(
                                                                   conjunct,
                                                                   granter))
                        return true;
                }
            }
            return granter.delegatesTo(superior);
        }
        
        public static boolean equivalentTo(fabric.lang.security.Principal p,
                                           fabric.lang.security.Principal q) {
            boolean eq = false;
            {
                boolean eq$var20 = eq;
                fabric.worker.transaction.TransactionManager $tm27 =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean $backoffEnabled30 =
                  fabric.worker.Worker.getWorker().config.txRetryBackoff;
                int $backoff28 = 1;
                boolean $doBackoff29 = true;
                boolean $retry23 = true;
                boolean $keepReads24 = false;
                $label21: for (boolean $commit22 = false; !$commit22; ) {
                    if ($backoffEnabled30) {
                        if ($doBackoff29) {
                            if ($backoff28 > 32) {
                                while (true) {
                                    try {
                                        java.lang.Thread.
                                          sleep(
                                            java.lang.Math.
                                                round(java.lang.Math.random() *
                                                          $backoff28));
                                        break;
                                    }
                                    catch (java.lang.
                                             InterruptedException $e25) {
                                        
                                    }
                                }
                            }
                            if ($backoff28 < 5000) $backoff28 *= 2;
                        }
                        $doBackoff29 = $backoff28 <= 32 || !$doBackoff29;
                    }
                    $commit22 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        try {
                            eq =
                              fabric.lang.security.PrincipalUtil._Impl.actsFor(
                                                                         p,
                                                                         q) &&
                                fabric.lang.security.PrincipalUtil._Impl.
                                actsFor(q, p);
                        }
                        catch (final fabric.worker.RetryException $e25) {
                            throw $e25;
                        }
                        catch (final fabric.worker.
                                 TransactionAbortingException $e25) {
                            throw $e25;
                        }
                        catch (final fabric.worker.
                                 TransactionRestartingException $e25) {
                            throw $e25;
                        }
                        catch (final fabric.worker.metrics.
                                 LockConflictException $e25) {
                            throw $e25;
                        }
                        catch (final Throwable $e25) {
                            $tm27.getCurrentLog().checkRetrySignal();
                            throw $e25;
                        }
                    }
                    catch (final fabric.worker.RetryException $e25) {
                        $commit22 = false;
                        continue $label21;
                    }
                    catch (fabric.worker.TransactionAbortingException $e25) {
                        $commit22 = false;
                        $retry23 = false;
                        $keepReads24 = $e25.keepReads;
                        if ($tm27.checkForStaleObjects()) {
                            $retry23 = true;
                            $keepReads24 = false;
                            continue $label21;
                        }
                        fabric.common.TransactionID $currentTid26 =
                          $tm27.getCurrentTid();
                        if ($e25.tid == null ||
                              !$e25.tid.isDescendantOf($currentTid26)) {
                            throw $e25;
                        }
                        throw new fabric.worker.UserAbortException($e25);
                    }
                    catch (final fabric.worker.
                             TransactionRestartingException $e25) {
                        $commit22 = false;
                        fabric.common.TransactionID $currentTid26 =
                          $tm27.getCurrentTid();
                        if ($e25.tid.isDescendantOf($currentTid26))
                            continue $label21;
                        if ($currentTid26.parent != null) {
                            $retry23 = false;
                            throw $e25;
                        }
                        throw new InternalError(
                                "Something is broken with " +
                                    "transaction management. Got a signal to restart a " +
                                    "different transaction than the one being managed.");
                    }
                    catch (final fabric.worker.metrics.
                             LockConflictException $e25) {
                        $commit22 = false;
                        if ($tm27.checkForStaleObjects()) continue $label21;
                        fabric.common.TransactionID $currentTid26 =
                          $tm27.getCurrentTid();
                        if ($e25.tid.isDescendantOf($currentTid26)) {
                            $retry23 = true;
                        }
                        else if ($currentTid26.parent != null) {
                            $retry23 = false;
                            throw $e25;
                        }
                        else {
                            throw new InternalError(
                                    "Something is broken with transaction " +
                                        "management. Got a signal for a lock conflict in a different " +
                                        "transaction than the one being managed.");
                        }
                    }
                    catch (final Throwable $e25) {
                        $commit22 = false;
                        if ($tm27.checkForStaleObjects()) continue $label21;
                        $retry23 = false;
                        throw new fabric.worker.AbortException($e25);
                    }
                    finally {
                        if ($commit22) {
                            fabric.common.TransactionID $currentTid26 =
                              $tm27.getCurrentTid();
                            try {
                                fabric.worker.transaction.TransactionManager.
                                  getInstance().commitTransaction();
                            }
                            catch (final fabric.worker.AbortException $e25) {
                                $commit22 = false;
                            }
                            catch (final fabric.worker.
                                     TransactionAbortingException $e25) {
                                $commit22 = false;
                                $retry23 = false;
                                $keepReads24 = $e25.keepReads;
                                if ($tm27.checkForStaleObjects()) {
                                    $retry23 = true;
                                    $keepReads24 = false;
                                    continue $label21;
                                }
                                if ($e25.tid == null ||
                                      !$e25.tid.isDescendantOf($currentTid26))
                                    throw $e25;
                                throw new fabric.worker.UserAbortException(
                                        $e25);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e25) {
                                $commit22 = false;
                                $currentTid26 = $tm27.getCurrentTid();
                                if ($currentTid26 != null) {
                                    if ($e25.tid.equals($currentTid26) ||
                                          !$e25.tid.isDescendantOf(
                                                      $currentTid26)) {
                                        throw $e25;
                                    }
                                }
                            }
                        }
                        else if ($keepReads24) {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransactionUpdates();
                        }
                        else {
                            fabric.worker.transaction.TransactionManager.
                              getInstance().abortTransaction();
                        }
                        if (!$commit22) {
                            { eq = eq$var20; }
                            if ($retry23) { continue $label21; }
                        }
                    }
                }
            }
            return eq;
        }
        
        public static boolean equals(fabric.lang.security.Principal p,
                                     fabric.lang.security.Principal q) {
            return fabric.lang.security.PrincipalUtil._Impl.eq(p, q);
        }
        
        /**
   * Execute the given closure, if the principal agrees.
   */
        public static java.lang.Object execute(fabric.lang.security.Principal p,
                                               java.lang.Object authPrf,
                                               fabric.lang.security.Closure c,
                                               fabric.lang.security.Label lb) {
            final fabric.worker.Store localStore =
              fabric.worker.Worker.getWorker().getLocalStore();
            fabric.lang.security.Capability cap =
              fabric.lang.security.PrincipalUtil._Impl.authorize(localStore, p,
                                                                 authPrf, c, lb,
                                                                 true);
            if (!fabric.lang.Object._Proxy.idEquals(cap, null)) {
                return cap.invoke();
            }
            return null;
        }
        
        /**
   * Obtain a Capability for the given principal and closure.
   */
        public static fabric.lang.security.Capability authorize(
          fabric.worker.Store store, fabric.lang.security.Principal p,
          java.lang.Object authPrf, fabric.lang.security.Closure c,
          fabric.lang.security.Label lb) {
            return fabric.lang.security.PrincipalUtil._Impl.authorize(store, p,
                                                                      authPrf,
                                                                      c, lb,
                                                                      false);
        }
        
        public static fabric.lang.security.Capability authorize(
          fabric.lang.security.Principal p, java.lang.Object authPrf,
          fabric.lang.security.Closure c, fabric.lang.security.Label lb) {
            final fabric.worker.Store localStore =
              fabric.worker.Worker.getWorker().getLocalStore();
            return fabric.lang.security.PrincipalUtil._Impl.authorize(
                                                              localStore, p,
                                                              authPrf, c, lb,
                                                              false);
        }
        
        private static fabric.lang.security.Capability authorize(
          fabric.worker.Store store, fabric.lang.security.Principal p,
          java.lang.Object authPrf, fabric.lang.security.Closure c,
          fabric.lang.security.Label lb, boolean executeNow) {
            fabric.lang.security.Principal closureP =
              c.jif$getfabric_lang_security_Closure_P();
            fabric.lang.security.Label closureL =
              c.jif$getfabric_lang_security_Closure_L();
            if (fabric.lang.Object._Proxy.idEquals(closureP, p) ||
                  !fabric.lang.Object._Proxy.idEquals(p, null) &&
                  !fabric.lang.Object._Proxy.idEquals(closureP, null) &&
                  p.equals(closureP) && closureP.equals(p)) {
                if (fabric.lang.security.LabelUtil._Impl.equivalentTo(closureL,
                                                                      lb)) {
                    if (fabric.lang.Object._Proxy.idEquals(p, null) ||
                          p.isAuthorized(authPrf, c, lb, executeNow)) {
                        return (fabric.lang.security.Capability)
                                 fabric.lang.Object._Proxy.
                                 $getProxy(
                                   ((fabric.lang.security.Capability)
                                      new fabric.lang.security.Capability._Impl(
                                        store).$getProxy()).
                                       fabric$lang$security$Capability$(
                                         closureP, closureL, c));
                    }
                }
            }
            return null;
        }
        
        /**
   * returns the null principal, the principal that every other principal can
   * act for.
   */
        public static fabric.lang.security.Principal nullPrincipal() {
            return null;
        }
        
        public static fabric.lang.security.Principal bottomPrincipal() {
            return fabric.lang.security.PrincipalUtil._Impl.nullPrincipal();
        }
        
        public static fabric.lang.security.Principal topPrincipal() {
            final fabric.worker.LocalStore localStore =
              fabric.worker.Worker.getWorker().getLocalStore();
            return localStore.getTopPrincipal();
        }
        
        public static boolean isTopPrincipal(fabric.lang.security.Principal p) {
            return fabric.lang.Object._Proxy.
              $getProxy(
                (java.lang.Object)
                  fabric.lang.WrappedJavaInlineable.
                  $unwrap(p)) instanceof TopPrincipal;
        }
        
        public static fabric.lang.security.ConfPolicy readableByPrinPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal p) {
            return (fabric.lang.security.ReaderPolicy)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.lang.security.ReaderPolicy)
                          new fabric.lang.security.ReaderPolicy._Impl(store).
                          $getProxy()).
                           fabric$lang$security$ReaderPolicy$(
                             fabric.lang.security.PrincipalUtil._Impl.
                                 topPrincipal(),
                             p));
        }
        
        public static fabric.lang.security.IntegPolicy writableByPrinPolicy(
          fabric.worker.Store store, fabric.lang.security.Principal p) {
            return (fabric.lang.security.WriterPolicy)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.lang.security.WriterPolicy)
                          new fabric.lang.security.WriterPolicy._Impl(store).
                          $getProxy()).
                           fabric$lang$security$WriterPolicy$(
                             fabric.lang.security.PrincipalUtil._Impl.
                                 topPrincipal(),
                             p));
        }
        
        public static fabric.lang.security.Principal disjunction(
          fabric.worker.Store store, fabric.lang.security.Principal left,
          fabric.lang.security.Principal right) {
            if (fabric.lang.Object._Proxy.idEquals(left, null) ||
                  fabric.lang.Object._Proxy.idEquals(right, null))
                return null;
            if (fabric.lang.security.PrincipalUtil._Impl.actsFor(left, right))
                return right;
            if (fabric.lang.security.PrincipalUtil._Impl.actsFor(right, left))
                return left;
            fabric.util.Collection
              c =
              (fabric.util.ArrayList)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.ArrayList)
                     new fabric.util.ArrayList._Impl(store).$getProxy()).
                      fabric$util$ArrayList$(2));
            c.add(left);
            c.add(right);
            return fabric.lang.security.PrincipalUtil._Impl.disjunction(store,
                                                                        c);
        }
        
        public static fabric.lang.security.Principal conjunction(
          fabric.worker.Store store, fabric.lang.security.Principal left,
          fabric.lang.security.Principal right) {
            if (fabric.lang.Object._Proxy.idEquals(left, null)) return right;
            if (fabric.lang.Object._Proxy.idEquals(right, null)) return left;
            if (fabric.lang.security.PrincipalUtil._Impl.actsFor(left, right))
                return left;
            if (fabric.lang.security.PrincipalUtil._Impl.actsFor(right, left))
                return right;
            fabric.util.Collection
              c =
              (fabric.util.ArrayList)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.ArrayList)
                     new fabric.util.ArrayList._Impl(store).$getProxy()).
                      fabric$util$ArrayList$(2));
            c.add(left);
            c.add(right);
            return fabric.lang.security.PrincipalUtil._Impl.conjunction(store,
                                                                        c);
        }
        
        public static fabric.lang.security.Principal disjunction(
          fabric.worker.Store store, fabric.util.Collection principals) {
            if (fabric.lang.Object._Proxy.idEquals(principals, null) ||
                  principals.isEmpty()) {
                return fabric.lang.security.PrincipalUtil._Impl.topPrincipal();
            }
            final fabric.worker.Store localStore =
              fabric.worker.Worker.getWorker().getLocalStore();
            if (principals.size() == 1) {
                fabric.lang.Object o = principals.iterator(localStore).next();
                if (fabric.lang.Object._Proxy.
                      idEquals(o, null) ||
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(o)) instanceof fabric.lang.security.Principal)
                    return (fabric.lang.security.Principal)
                             fabric.lang.Object._Proxy.$getProxy(o);
                return fabric.lang.security.PrincipalUtil._Impl.topPrincipal();
            }
            fabric.util.Set
              needed =
              (fabric.util.LinkedHashSet)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.LinkedHashSet)
                     new fabric.util.LinkedHashSet._Impl(store).$getProxy()).
                      fabric$util$LinkedHashSet$());
            for (fabric.util.Iterator iter = principals.iterator(localStore);
                 iter.hasNext(); ) {
                fabric.lang.Object o = iter.next();
                fabric.lang.security.Principal p = null;
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(o)) instanceof fabric.lang.security.Principal)
                    p = (fabric.lang.security.Principal)
                          fabric.lang.Object._Proxy.$getProxy(o);
                if (fabric.lang.Object._Proxy.idEquals(p, null)) return p;
                if (fabric.lang.security.PrincipalUtil._Impl.isTopPrincipal(p))
                    continue;
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(
                            p)) instanceof fabric.lang.security.DisjunctivePrincipal) {
                    needed.addAll(
                             ((fabric.lang.security.DisjunctivePrincipal)
                                fabric.lang.Object._Proxy.$getProxy(
                                                            p)).get$disjuncts(
                                                                  ));
                }
                else {
                    needed.add(p);
                }
            }
            return (fabric.lang.security.DisjunctivePrincipal)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.lang.security.DisjunctivePrincipal)
                          new fabric.lang.security.DisjunctivePrincipal._Impl(
                            store).$getProxy()).
                           fabric$lang$security$DisjunctivePrincipal$(needed));
        }
        
        public static fabric.lang.security.Principal conjunction(
          fabric.worker.Store store, fabric.util.Collection principals) {
            if (fabric.lang.Object._Proxy.idEquals(principals, null) ||
                  principals.isEmpty()) {
                return fabric.lang.security.PrincipalUtil._Impl.bottomPrincipal(
                                                                  );
            }
            final fabric.worker.Store localStore =
              fabric.worker.Worker.getWorker().getLocalStore();
            if (principals.size() == 1) {
                fabric.lang.Object o = principals.iterator(localStore).next();
                if (fabric.lang.Object._Proxy.
                      idEquals(o, null) ||
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(o)) instanceof fabric.lang.security.Principal)
                    return (fabric.lang.security.Principal)
                             fabric.lang.Object._Proxy.$getProxy(o);
                return fabric.lang.security.PrincipalUtil._Impl.bottomPrincipal(
                                                                  );
            }
            fabric.util.Set
              needed =
              (fabric.util.LinkedHashSet)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.LinkedHashSet)
                     new fabric.util.LinkedHashSet._Impl(store).$getProxy()).
                      fabric$util$LinkedHashSet$());
            for (fabric.util.Iterator iter = principals.iterator(localStore);
                 iter.hasNext(); ) {
                fabric.lang.Object o = iter.next();
                fabric.lang.security.Principal p = null;
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(o)) instanceof fabric.lang.security.Principal)
                    p = (fabric.lang.security.Principal)
                          fabric.lang.Object._Proxy.$getProxy(o);
                if (fabric.lang.Object._Proxy.idEquals(p, null)) continue;
                if (fabric.lang.security.PrincipalUtil._Impl.isTopPrincipal(p))
                    return p;
                if (fabric.lang.Object._Proxy.
                      $getProxy(
                        (java.lang.Object)
                          fabric.lang.WrappedJavaInlineable.
                          $unwrap(
                            p)) instanceof fabric.lang.security.ConjunctivePrincipal) {
                    needed.addAll(
                             ((fabric.lang.security.ConjunctivePrincipal)
                                fabric.lang.Object._Proxy.$getProxy(
                                                            p)).get$conjuncts(
                                                                  ));
                }
                else {
                    needed.add(p);
                }
            }
            return (fabric.lang.security.ConjunctivePrincipal)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.lang.security.ConjunctivePrincipal)
                          new fabric.lang.security.ConjunctivePrincipal._Impl(
                            store).$getProxy()).
                           fabric$lang$security$ConjunctivePrincipal$(needed));
        }
        
        public static java.lang.String toString(
          fabric.lang.security.Principal p) {
            return fabric.lang.Object._Proxy.idEquals(p, null) ? "_" : p.name();
        }
        
        public static java.lang.String stringValue(
          fabric.lang.security.Principal p) {
            return fabric.lang.security.PrincipalUtil._Impl.toString(p);
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.PrincipalUtil) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.PrincipalUtil._Proxy(this);
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
          implements fabric.lang.security.PrincipalUtil._Static {
            public _Proxy(fabric.lang.security.PrincipalUtil._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.PrincipalUtil._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  PrincipalUtil.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.PrincipalUtil._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.PrincipalUtil._Static._Impl.class);
                $instance = (fabric.lang.security.PrincipalUtil._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.PrincipalUtil._Static {
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
                return new fabric.lang.security.PrincipalUtil._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -10, -28, 63, 23, 89,
    -128, 32, 106, 96, -46, -79, -10, -22, 107, 27, -110, -4, -23, 19, 44, -1,
    -112, -32, 2, 103, -113, 107, 41, 124, 61, -126, 116 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1536693771000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
