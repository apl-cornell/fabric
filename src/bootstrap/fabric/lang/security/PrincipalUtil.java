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
        
        public static final byte[] $classHash = new byte[] { -57, -50, 33, 65,
        28, -88, 26, -83, -20, -75, 40, -95, 33, -101, -19, 75, 97, -20, 19,
        -122, -22, 71, 1, 58, 78, 33, 10, 112, -86, -117, 59, 52 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1536693771000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "H4sIAAAAAAAAALVYe2wUxxmfO78f4AexAWMb21xRceCuBJoquK0K1xgOjnDCBqlGwZnbmzsv3tvd7M7BOS0RrdSAEomqreMmSqGqRFtKHFArofxRWc0fDUmUKmqiqi+pDf+kSWtQE1V9SEmaft/M7u3e3vlQpNbSzoxnvm/me/7mm1u8TRpsi4xkaVrVonzOZHZ0nKYTyRS1bJaJa9S2J2F2WmmrTyy886PMYJiEk6RdobqhqwrVpnWbk9XJE/QkjemMx44cTowdIy0KMu6j9gwn4WN7ihYZMg1tLqcZ3DmkYv8n747Nf+d450/rSMcU6VD1CU65qsQNnbMinyLteZZPM8vencmwzBTp0hnLTDBLpZr6CBAa+hTpttWcTnnBYvZhZhvaSSTstgsms8SZ7iSKb4DYVkHhhgXid0rxC1zVYknV5mNJ0phVmZaxHyaPkvokachqNAeEvUlXi5jYMTaO80DeqoKYVpYqzGWpn1X1DCcbgxwljSMHgABYm/KMzxilo+p1ChOkW4qkUT0Xm+CWqueAtMEowCmc9K24KRA1m1SZpTk2zcm6IF1KLgFVizALsnDSEyQTO4HP+gI+83nr9gOfPf9lfZ8eJiGQOcMUDeVvBqbBANNhlmUW0xUmGdtHkwu0d+lcmBAg7gkQS5rnv/LeF7YOvvCypNlQheZQ+gRT+LRyKb369f74lvvqUIxm07BVDIUyzYVXU87KWNGEaO8t7YiLUXfxhcM3vnTmClsOk9YEaVQMrZCHqOpSjLypaszay3RmUc4yCdLC9ExcrCdIE4yTqs7k7KFs1mY8Qeo1MdVoiP/BRFnYAk3UBGNVzxru2KR8RoyLJiGkCz5SB98fCBnohP4nhKxt5eRobMbIs1haK7BTEN4x+Bi1lJkY5K2lKtsUw5yL2ZYSswo6V4FSzsv4sZlSsFQ+F0tBHCmqSbUjqDZIZP7fdi6iTp2nQiEw90bFyLA0tcF3ThztSWmQKvsMLcOsaUU7v5Qga5aeFrHUgvFvQwwLa4XA//1B5PDzzhf23P/e1elXZRwir2NMTnZISaMoadSVNFomaSRlGUZ2QiiMeMNA2nZMvChAWRSgbDFUjMYvJp4V8dVoi0QsHdAOB+wyNcqzhpUvklBIaHuX4BeBBWExC3ADiNK+ZeLB/Q+dGwHXFs1T9eBYJI0E88tDpQSMKCTNtNJx9p1/Xls4bXiZxkmkAgAqOTGBR4KmswyFZQAgve1Hh+j16aXTkTCCTwvgIqcQuQAyg8EzyhJ5zAVFtEZDkrShDaiGSy6StfIZyzjlzYiQWI1Nt4wONFZAQIGnn5swL/zutb/sEDeNC70dPoyeYHzMl+64WYdI7C7P9pMWY0D3x6dS337y9tljwvBAsanagRFs45DmFPLbsL7+8sO/f/NPl34d9pzFSZNpqSchQopCma6P4C8E33/ww6TFCewBuuMOYAyVEMPEozd7wgF2aIBfILsdOaLnjYyaVWlaYxgqH3R8Yvv1W+c7pb81mJHWs8jWO2/gza/fQ868evxfg2KbkIJ3l2dAj0wC4hpv592WRedQjuJX3xh4+iV6AUIf4MxWH2ECoYgwCBEevEfYYptotwfWdmIzIq3VX4r44OUwjresF4xTscXv9sU/vyyRoBSMuMdwFSQ4Sn15cs+V/D/CI40vhknTFOkUFzzV+VEKwAZxMAVXtB13JpNkVdl6+XUr75axUrL1BxPBd2wwDTwEgjFS47hVRr4MHDBELxopDt91QtaNyn7th7i6xsT2rmKIiMEuwbJJtJux2SIMGcbhKCct3KK6rTKdF0t7Cwf0OHt+4PTLvr2BLWdQDcRXZsEhG/yF336wgfC/vIaPQzr9au5vC/IaDhYDPsJ3F99cfmPVwFWBIfWI9ELtYBVVWSSV1T7CSu3lVoo5VuqtZiVB2sNJtCrQTziDOFVmWGS3wu1xw0pR1RJs68EOCN+aATVssRTBISdS8f97sfmi64v9K/iCk0azkNZUyLCGrKpTefPdDdMa03N8pkrQwxWUB+A66VRE7Nz84x9Fz8/LhJdl46aKys3PI0tHcdAqcVoRThmudYrgGH/72umfXT59Vvqzu7wIul8v5J/7zYe/jD5185Uql2kd+A7/iXu2CksDuF6QGCJ8ENcMnSEc4dJel0BejKoRLZXtLkWl/UGdihfJQRErXsLfXB64Lz77Vk6qszGgfpD6xwcXX9m7WflWmNSVMruiOC9nGivP51aLwdtCnyzL6iEZJMI01SMkhMODMipq4GWmxloWm4cgwBQ0rGvPTs/gErKkLcXVWqyNHFDJ4OPKg40wnuXWmy2y733fl2w+HCcYbQMrPQ1EpF362vzFzKEfbA87CkzCkc77zdunXowfLMnQhnufgO95SPgFp/+MP+Fl1SA0wyZdjnqtDsu9Tv+poPjbVgrdwdqVogsY5fUeTs6JtljDdY9iA+XLTnlEBI+IuEdEahejEU9fz0rtuPUZ+JYIWX/Z6XeuYKWqsTgauC3anE12OP3Wle1WJ3apEycI/T0jPFbDCOewOfM/NIIIlU/Dd4OQvned/sbHCxVkedHpf37HUAlo+40a2n4Tm8c5aXbqF1tQpRygxm4Cysm0YWgMru8qim2E73VCNrzv9G9/PMWQ5c9Of3NlxfwiP1Nj7QI2C5y0RVRd5UmaZprtJk63P3EkCFVPliLAVcVby9lk5M7vNKTsw2qlykvS+bVDif+CXXrrwNaeFV6R6yp+f3L4rl7saF578chvZfHi/pLRAu+LbEHT/PWcb9xoWiyrCvu0yOrOFN1luOaqqQPR4A6F2j+U5M+CWX3kHOp96PwUVwE5JQX+d014sq+8+Z4g7CtY+Evb4t/X/ruxefKmeLuAC4deem14d//lvuduXf/k94efuX2A3lrz2F/3hnY9MNxqXnlibOd/ARJIBDkBFAAA";
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
        
        public static final byte[] $classHash = new byte[] { -75, -109, -63,
        -101, 37, -11, 103, 62, -103, -87, -99, 72, 50, -27, 38, 68, -77, -22,
        101, -74, 53, 86, 1, 101, 28, 95, -110, 51, 56, 6, 24, 99 };
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
    
    public static final byte[] $classHash = new byte[] { -57, -50, 33, 65, 28,
    -88, 26, -83, -20, -75, 40, -95, 33, -101, -19, 75, 97, -20, 19, -122, -22,
    71, 1, 58, 78, 33, 10, 112, -86, -117, 59, 52 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1536693771000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
