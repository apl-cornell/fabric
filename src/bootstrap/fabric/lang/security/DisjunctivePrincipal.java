package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Worker;
import fabric.worker.Store;
import fabric.util.*;

/**
 * A disjunction of two (non-null) principals. This code is mostly copied from
 * Jif.
 */
public interface DisjunctivePrincipal extends fabric.lang.security.Principal {
    public fabric.util.Set get$disjuncts();
    
    public fabric.util.Set set$disjuncts(fabric.util.Set val);
    
    public java.lang.Integer get$hashCode();
    
    public java.lang.Integer set$hashCode(java.lang.Integer val);
    
    public fabric.lang.security.DisjunctivePrincipal
      fabric$lang$security$DisjunctivePrincipal$(fabric.util.Set disjuncts);
    
    public java.lang.String name();
    
    public boolean delegatesTo(fabric.lang.security.Principal p);
    
    public int hashCode();
    
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
      implements fabric.lang.security.DisjunctivePrincipal {
        public fabric.util.Set get$disjuncts() {
            return ((fabric.lang.security.DisjunctivePrincipal._Impl) fetch()).
              get$disjuncts();
        }
        
        public fabric.util.Set set$disjuncts(fabric.util.Set val) {
            return ((fabric.lang.security.DisjunctivePrincipal._Impl) fetch()).
              set$disjuncts(val);
        }
        
        public java.lang.Integer get$hashCode() {
            return ((fabric.lang.security.DisjunctivePrincipal._Impl) fetch()).
              get$hashCode();
        }
        
        public java.lang.Integer set$hashCode(java.lang.Integer val) {
            return ((fabric.lang.security.DisjunctivePrincipal._Impl) fetch()).
              set$hashCode(val);
        }
        
        public fabric.lang.security.DisjunctivePrincipal
          fabric$lang$security$DisjunctivePrincipal$(fabric.util.Set arg1) {
            return ((fabric.lang.security.DisjunctivePrincipal) fetch()).
              fabric$lang$security$DisjunctivePrincipal$(arg1);
        }
        
        public _Proxy(DisjunctivePrincipal._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.security.Principal._Impl
      implements fabric.lang.security.DisjunctivePrincipal {
        public fabric.util.Set get$disjuncts() { return this.disjuncts; }
        
        public fabric.util.Set set$disjuncts(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.disjuncts = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        fabric.util.Set disjuncts;
        
        public java.lang.Integer get$hashCode() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.hashCode;
        }
        
        public java.lang.Integer set$hashCode(java.lang.Integer val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.hashCode = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private java.lang.Integer hashCode = null;
        
        public fabric.lang.security.DisjunctivePrincipal
          fabric$lang$security$DisjunctivePrincipal$(
          fabric.util.Set disjuncts) {
            this.set$disjuncts(disjuncts);
            fabric$lang$security$Principal$();
            return (fabric.lang.security.DisjunctivePrincipal) this.$getProxy();
        }
        
        public java.lang.String name() {
            java.lang.StringBuffer sb = new java.lang.StringBuffer();
            for (fabric.util.Iterator iter = this.get$disjuncts().iterator();
                 iter.hasNext(); ) {
                fabric.lang.security.Principal p =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(iter.next());
                sb.append(fabric.lang.security.PrincipalUtil._Impl.toString(p));
                if (iter.hasNext()) sb.append(",");
            }
            return sb.toString();
        }
        
        public boolean delegatesTo(fabric.lang.security.Principal p) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p)) instanceof fabric.lang.security.DisjunctivePrincipal) {
                fabric.lang.security.DisjunctivePrincipal dp =
                  (fabric.lang.security.DisjunctivePrincipal)
                    fabric.lang.Object._Proxy.$getProxy(p);
                return this.get$disjuncts().containsAll(dp.get$disjuncts());
            }
            for (fabric.util.Iterator iter = this.get$disjuncts().iterator();
                 iter.hasNext(); ) {
                fabric.lang.security.Principal q =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(iter.next());
                if (fabric.lang.security.PrincipalUtil._Impl.equals(q, p))
                    return true;
            }
            return false;
        }
        
        public int hashCode() {
            if (fabric.lang.Object._Proxy.idEquals(this.get$hashCode(), null)) {
                this.set$hashCode(
                       java.lang.Integer.valueOf(
                                           this.get$disjuncts().hashCode()));
            }
            return this.get$hashCode().intValue();
        }
        
        public boolean equals(fabric.lang.security.Principal p) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p)) instanceof fabric.lang.security.DisjunctivePrincipal) {
                fabric.lang.security.DisjunctivePrincipal that =
                  (fabric.lang.security.DisjunctivePrincipal)
                    fabric.lang.Object._Proxy.$getProxy(p);
                return this.hashCode() == that.hashCode() &&
                  this.get$disjuncts().equals(that.get$disjuncts()) &&
                  that.get$disjuncts().equals(this.get$disjuncts());
            }
            return false;
        }
        
        public boolean isAuthorized(java.lang.Object authPrf,
                                    fabric.lang.security.Closure closure,
                                    fabric.lang.security.Label lb,
                                    boolean executeNow) {
            for (fabric.util.Iterator iter = this.get$disjuncts().iterator();
                 iter.hasNext(); ) {
                fabric.lang.security.Principal p =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(iter.next());
                if (p.isAuthorized(authPrf, closure, lb, executeNow))
                    return true;
            }
            return false;
        }
        
        public fabric.lang.security.ActsForProof findProofUpto(
          fabric.worker.Store store, fabric.lang.security.Principal p,
          java.lang.Object searchState) {
            if (delegatesTo(p)) {
                return (fabric.lang.security.DelegatesProof)
                         fabric.lang.Object._Proxy.
                         $getProxy(
                           ((fabric.lang.security.DelegatesProof)
                              new fabric.lang.security.DelegatesProof._Impl(
                                store).$getProxy()).
                               fabric$lang$security$DelegatesProof$(
                                 p,
                                 (fabric.lang.security.DisjunctivePrincipal)
                                   this.$getProxy()));
            }
            for (fabric.util.Iterator iter = this.get$disjuncts().iterator();
                 iter.hasNext(); ) {
                fabric.lang.security.Principal witness =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(iter.next());
                fabric.lang.security.ActsForProof prf =
                  fabric.lang.security.PrincipalUtil._Impl.findActsForProof(
                                                             store, p, witness,
                                                             searchState);
                if (!fabric.lang.Object._Proxy.idEquals(prf, null)) {
                    fabric.lang.security.DelegatesProof
                      step =
                      (fabric.lang.security.DelegatesProof)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          ((fabric.lang.security.DelegatesProof)
                             new fabric.lang.security.DelegatesProof._Impl(
                               store).$getProxy()).
                              fabric$lang$security$DelegatesProof$(
                                witness,
                                (fabric.lang.security.DisjunctivePrincipal)
                                  this.$getProxy()));
                    return (fabric.lang.security.TransitiveProof)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               ((fabric.lang.security.TransitiveProof)
                                  new fabric.lang.security.TransitiveProof.
                                    _Impl(store).
                                  $getProxy()).
                                   fabric$lang$security$TransitiveProof$(
                                     prf, witness, step));
                }
            }
            return null;
        }
        
        public fabric.lang.security.ActsForProof findProofDownto(
          fabric.worker.Store store, fabric.lang.security.Principal q,
          java.lang.Object searchState) {
            fabric.util.Map
              proofs =
              (fabric.util.HashMap)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.HashMap)
                     new fabric.util.HashMap._Impl(store).$getProxy()).
                      fabric$util$HashMap$());
            for (fabric.util.Iterator iter = this.get$disjuncts().iterator();
                 iter.hasNext(); ) {
                fabric.lang.security.Principal p =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(iter.next());
                fabric.lang.security.ActsForProof prf =
                  fabric.lang.security.PrincipalUtil._Impl.findActsForProof(
                                                             store, p, q,
                                                             searchState);
                if (fabric.lang.Object._Proxy.idEquals(prf, null)) return null;
                proofs.put(p, prf);
            }
            return (fabric.lang.security.FromDisjunctProof)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.lang.security.FromDisjunctProof)
                          new fabric.lang.security.FromDisjunctProof._Impl(
                            store).$getProxy()).
                           fabric$lang$security$FromDisjunctProof$(
                             (fabric.lang.security.DisjunctivePrincipal)
                               this.$getProxy(), q, proofs));
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.DisjunctivePrincipal) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.DisjunctivePrincipal._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.disjuncts, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeInline(out, this.hashCode);
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
            this.disjuncts = (fabric.util.Set)
                               $readRef(fabric.util.Set._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
                                        intraStoreRefs, interStoreRefs);
            this.hashCode = (java.lang.Integer) in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.DisjunctivePrincipal._Impl src =
              (fabric.lang.security.DisjunctivePrincipal._Impl) other;
            this.disjuncts = src.disjuncts;
            this.hashCode = src.hashCode;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.DisjunctivePrincipal._Static {
            public _Proxy(fabric.lang.security.DisjunctivePrincipal._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.DisjunctivePrincipal.
              _Static $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  DisjunctivePrincipal.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    lang.
                    security.
                    DisjunctivePrincipal.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.DisjunctivePrincipal._Static.
                        _Impl.class);
                $instance = (fabric.lang.security.DisjunctivePrincipal._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.DisjunctivePrincipal._Static {
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
                return new fabric.lang.security.DisjunctivePrincipal._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 7, 38, 53, 94, 55,
    -122, -16, -81, 5, -117, 62, 41, 25, -119, -117, -100, -8, -82, -33, -89,
    -43, 22, 59, 24, -117, -27, 27, 125, 78, 18, 116, -42 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
