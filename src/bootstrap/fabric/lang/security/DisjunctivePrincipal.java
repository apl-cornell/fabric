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
    
    public static final byte[] $classHash = new byte[] { -60, 70, -28, 8, 22,
    12, 9, -102, 11, -5, -16, -56, 20, 61, 59, 62, 83, 58, -76, 86, 0, -50, 48,
    -36, -94, -90, -121, -91, -93, -68, 104, 71 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
