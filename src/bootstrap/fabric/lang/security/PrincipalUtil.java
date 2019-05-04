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
        
        public static final byte[] $classHash = new byte[] { 38, 105, -8, 61,
        52, 78, 29, 57, 40, 44, -16, 112, -52, 39, 38, -108, -73, 124, -124, 63,
        -39, -106, -94, -26, -15, 122, 27, 93, -126, -86, -90, 115 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1536693771000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAALVYfWwcRxWfO39/JP5I7SRO7LjOYYjr3JEmFKWmQHKtk0svzSl2IuEocef25uyN93a3u3PJOTRVqIBE/SMIcN1WkCChUEJqEoEU5Q9k0T9oaNUK0QoBRYLmn34gE5EIAZXaUt6b2b3d2ztfVAks7cx45r2Z9/mbN7dwk9TZFhnI0rSqRfmsyezoKE0nkilq2SwT16htj8PspNJSm5h/78eZvjAJJ0mrQnVDVxWqTeo2JyuTR+kxGtMZjx3Ynxg5RJoUZNxN7WlOwod2FizSbxra7JRmcOeQsv2fvic298yR9p/XkLYJ0qbqY5xyVYkbOmcFPkFacyyXZpa9I5NhmQnSoTOWGWOWSjX1BBAa+gTptNUpnfK8xez9zDa0Y0jYaedNZokz3UkU3wCxrbzCDQvEb5fi57mqxZKqzUeSpD6rMi1jP0aeILVJUpfV6BQQdiddLWJix9gozgN5swpiWlmqMJeldkbVM5xsCHIUNY48DATA2pBjfNooHlWrU5ggnVIkjepTsTFuqfoUkNYZeTiFk55lNwWiRpMqM3SKTXKyJkiXkktA1STMgiycdAXJxE7gs56Az3zeuvnIF85+Vd+th0kIZM4wRUP5G4GpL8C0n2WZxXSFScbWoeQ87V48EyYEiLsCxJLm2uO3vzzc9+LLkmZdBZp96aNM4ZPKhfTK19fHN22vQTEaTcNWMRRKNBdeTTkrIwUTor27uCMuRt3FF/df/8qpS2wpTJoTpF4xtHwOoqpDMXKmqjFrF9OZRTnLJEgT0zNxsZ4gDTBOqjqTs/uyWZvxBKnVxFS9If4HE2VhCzRRA4xVPWu4Y5PyaTEumISQDvhIDXx/IqS3HfqfEbK6mZODsWkjx2JpLc+OQ3jH4GPUUqZjkLeWqmxWDHM2ZltKzMrrXAVKOS/jx2ZK3lL5bCwFcaSoJtUOoNogkfl/27mAOrUfD4XA3BsUI8PS1AbfOXG0M6VBquw2tAyzJhXt7GKCrFp8TsRSE8a/DTEsrBUC/68PIoefdy6/86HblydflXGIvI4xOdkqJY2ipFFX0miJpJGUZRjZMaEw4g0DaVsx8aIAZVGAsoVQIRo/n3hBxFe9LRKxeEArHHC/qVGeNaxcgYRCQtu7BL8ILAiLGYAbQJTWTWOH9zx6ZgBcWzCP14JjkTQSzC8PlRIwopA0k0rb6ff+dWX+pOFlGieRMgAo58QEHgiazjIUlgGA9LYf6qdXJxdPRsIIPk2Ai5xC5ALI9AXPKEnkERcU0Rp1SdKCNqAaLrlI1synLeO4NyNCYiU2nTI60FgBAQWePjBmnvvjb/66Vdw0LvS2+TB6jPERX7rjZm0isTs8249bjAHdn59Nfffpm6cPCcMDxcZKB0awjUOaU8hvw/rGy4+9+dZfLvwu7DmLkwbTUo9BhBSEMh0fw18Ivv/gh0mLE9gDdMcdwOgvIoaJRw96wgF2aIBfILsdOaDnjIyaVWlaYxgqH7Z9asvVv51tl/7WYEZazyLDd97Am1+7k5x69ci/+8Q2IQXvLs+AHpkExFXezjssi86iHIWvvdH73K/pOQh9gDNbPcEEQhFhECI8eK+wxWbRbgmsbcNmQFprfTHig5fDKN6yXjBOxBa+3xP/4pJEgmIw4h53V0CCg9SXJ/deyv0zPFD/Upg0TJB2ccFTnR+kAGwQBxNwRdtxZzJJVpSsl1638m4ZKSbb+mAi+I4NpoGHQDBGahw3y8iXgQOG6EYjxeG7SsiaIdmv/ghXV5nY3lUIETG4X7BsFO0gNpuEIcM4HOKkiVtUt1Wm80Jxb+GALmfPD51+ybc3sE0ZVAPxlRlwyDp/4bcHbCD8L6/hI5BOv539+7y8hoPFgI/w1sJbS2+s6L0sMKQWkV6oHayiyoukktpHWKm11Eoxx0rdlawkSLs4iVYE+jFnEKfKNIvsULg9algpqlqCbS3YAeFbM6CGLRQjOOREKv5/HzYPur7Ys4wvOKk382lNhQyry6o6lTffPTCtMX2KT1cIeriCcgBcx5yKiJ2Ze+rj6Nk5mfCybNxYVrn5eWTpKA5aIU4rwCl3VztFcIy+e+XkLy6ePC392VlaBD2k53M//f1Hr0WfvfFKhcu0BnyH/8Q9W4WlAVwvSAwRPohrhs4QjnBpl0sgL0bViBbLdpei3P6gTtmLZK+IFS/hbyz1bo/PvD0l1dkQUD9I/ZO9C6/sGlS+EyY1xcwuK85LmUZK87nZYvC20MdLsrpfBokwTeUICeFwr4yKKniZqbKWxeZRCDAFDevas90zuIQsaUtxtRaqIwdUMvi48mAjjGe59WaT7Ls/8CWbD8cJRlvvck8DEWkXnpw7n9n3oy1hR4FxONJ5v3n71Irx4aIMLbj3UfiuQcLPO/3n/QkvqwahGTbpUtRrdljuc/rPBsXfvFzo9lWvFF3AKK33cHJWtIUqrnsCGyhftskjInhExD0iUr0YjXj6elZqxa1PwbdIyNqLTr9tGStVjMWhwG3R4myy1emHl7dbjdilRpwg9PeM8M0qRjiDzan/oRFEqHwOvuuE9Nxy+uufLFSQ5SWn/+UdQyWg7beqaPttbJ7ipNGpX2xBlXKAGrsxKCfThqExuL4rKLYBvtcJWfeB07/7yRRDlnec/sbyivlF/l6VtXPYzHPSElF1lSdpmmm2mzid/sSRIFQ5WQoAV2VvLWeTgTu/05CyB6uVCi9J59cOJf4rduHth4e7lnlFrin7/cnhu3y+rXH1+QN/kMWL+0tGE7wvsnlN89dzvnG9abGsKuzTJKs7U3QX4ZqrpA5EgzsUaj8vyV8As/rIOdT70PkpLgNySgr874rwZE9p8wNB2JO38Je2hX+sfr++cfyGeLuAC/sH1fcf2PZI7/bPDN8yX/v04Ny1x7/+pTfnf/jO7RPrDj956Xn7v011a6YBFAAA";
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
        
        public static final byte[] $classHash = new byte[] { -32, 35, -22, -74,
        -27, 62, -66, -42, 38, 33, 25, -27, -78, -113, -48, -79, 92, -15, -128,
        41, 23, 47, 100, -113, -72, -52, -123, -74, 104, -115, 74, -53 };
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
                long $backoff28 = 1;
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
                            if ($backoff28 <
                                  fabric.worker.Worker.getWorker().config.
                                    maxBackoff)
                                $backoff28 =
                                  java.lang.Math.
                                    min(
                                      $backoff28 * 2,
                                      fabric.worker.Worker.getWorker().config.
                                        maxBackoff);
                        }
                        $doBackoff29 = $backoff28 <= 32 || !$doBackoff29;
                    }
                    $commit22 = true;
                    fabric.worker.transaction.TransactionManager.getInstance().
                      startTransaction();
                    try {
                        eq =
                          fabric.lang.security.PrincipalUtil._Impl.actsFor(p,
                                                                           q) &&
                            fabric.lang.security.PrincipalUtil._Impl.actsFor(q,
                                                                             p);
                    }
                    catch (final fabric.worker.RetryException $e25) {
                        $commit22 = false;
                        continue $label21;
                    }
                    catch (fabric.worker.TransactionAbortingException $e25) {
                        $commit22 = false;
                        $retry23 = false;
                        $keepReads24 = $e25.keepReads;
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
                    catch (final Throwable $e25) {
                        $commit22 = false;
                        $retry23 = false;
                        if ($tm27.inNestedTxn()) { $keepReads24 = true; }
                        throw $e25;
                    }
                    finally {
                        fabric.common.TransactionID $currentTid26 =
                          $tm27.getCurrentTid();
                        if ($commit22) {
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
                        } else {
                            if (!$tm27.inNestedTxn() &&
                                  $tm27.checkForStaleObjects()) {
                                $retry23 = true;
                                $keepReads24 = false;
                            }
                            if ($keepReads24) {
                                try {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                }
                                catch (final fabric.worker.TransactionRestartingException $e25) {
                                    $currentTid26 = $tm27.getCurrentTid();
                                    if ($currentTid26 !=
                                          null &&
                                          ($e25.tid.equals($currentTid26) ||
                                             !$e25.tid.isDescendantOf($currentTid26))) {
                                        throw $e25;
                                    } else {
                                        $retry23 = true;
                                    }
                                }
                            } else {
                                fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                            }
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
    
    public static final byte[] $classHash = new byte[] { 38, 105, -8, 61, 52,
    78, 29, 57, 40, 44, -16, 112, -52, 39, 38, -108, -73, 124, -124, 63, -39,
    -106, -94, -26, -15, 122, 27, 93, -126, -86, -90, 115 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1536693771000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
