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
        
        public static final byte[] $classHash = new byte[] { 11, -56, -18, 59,
        53, -30, -14, 29, 105, 28, 84, -34, 6, -92, 6, 67, -63, -109, 70, -79,
        87, -29, 89, 16, -68, 72, 95, 7, 94, 72, 87, 51 };
        public static final java.lang.String jlc$CompilerVersion$fabil =
          "0.3.0";
        public static final long jlc$SourceLastModified$fabil = 1536693771000L;
        public static final java.lang.String jlc$ClassType$fabil =
          "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";
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
        
        public static final byte[] $classHash = new byte[] { 107, -2, 74, 113,
        0, -5, -86, -113, -64, 17, -119, 72, 24, 37, 90, -123, -9, -45, -88,
        -78, -66, 86, -121, -86, -72, 41, 20, 46, 99, 56, -126, -101 };
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
    
    public static final byte[] $classHash = new byte[] { 11, -56, -18, 59, 53,
    -30, -14, 29, 105, 28, 84, -34, 6, -92, 6, 67, -63, -109, 70, -79, 87, -29,
    89, 16, -68, 72, 95, 7, 94, 72, 87, 51 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1536693771000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
