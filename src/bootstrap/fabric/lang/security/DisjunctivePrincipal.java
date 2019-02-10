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
    
    public static final byte[] $classHash = new byte[] { 94, 65, -30, 7, 98,
    -42, 47, -76, -128, 12, -37, 10, -99, 1, 91, 83, 112, -76, -25, 14, -123, 2,
    104, -48, 87, -9, -21, 24, -120, 121, -91, -23 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
