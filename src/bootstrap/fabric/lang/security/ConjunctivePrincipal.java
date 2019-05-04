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
 * A conjunction of two or more (non-null) principals. This code is mostly
 * copied from Jif.
 */
public interface ConjunctivePrincipal extends fabric.lang.security.Principal {
    public fabric.util.Set get$conjuncts();
    
    public fabric.util.Set set$conjuncts(fabric.util.Set val);
    
    public java.lang.Integer get$hashCode();
    
    public java.lang.Integer set$hashCode(java.lang.Integer val);
    
    public fabric.lang.security.ConjunctivePrincipal
      fabric$lang$security$ConjunctivePrincipal$(fabric.util.Set conjuncts);
    
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
      implements fabric.lang.security.ConjunctivePrincipal {
        public fabric.util.Set get$conjuncts() {
            return ((fabric.lang.security.ConjunctivePrincipal._Impl) fetch()).
              get$conjuncts();
        }
        
        public fabric.util.Set set$conjuncts(fabric.util.Set val) {
            return ((fabric.lang.security.ConjunctivePrincipal._Impl) fetch()).
              set$conjuncts(val);
        }
        
        public java.lang.Integer get$hashCode() {
            return ((fabric.lang.security.ConjunctivePrincipal._Impl) fetch()).
              get$hashCode();
        }
        
        public java.lang.Integer set$hashCode(java.lang.Integer val) {
            return ((fabric.lang.security.ConjunctivePrincipal._Impl) fetch()).
              set$hashCode(val);
        }
        
        public fabric.lang.security.ConjunctivePrincipal
          fabric$lang$security$ConjunctivePrincipal$(fabric.util.Set arg1) {
            return ((fabric.lang.security.ConjunctivePrincipal) fetch()).
              fabric$lang$security$ConjunctivePrincipal$(arg1);
        }
        
        public _Proxy(ConjunctivePrincipal._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.security.Principal._Impl
      implements fabric.lang.security.ConjunctivePrincipal {
        public fabric.util.Set get$conjuncts() { return this.conjuncts; }
        
        public fabric.util.Set set$conjuncts(fabric.util.Set val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.conjuncts = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        fabric.util.Set conjuncts;
        
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
        
        private java.lang.Integer hashCode;
        
        public fabric.lang.security.ConjunctivePrincipal
          fabric$lang$security$ConjunctivePrincipal$(
          fabric.util.Set conjuncts) {
            this.set$conjuncts(conjuncts);
            fabric$lang$security$Principal$();
            return (fabric.lang.security.ConjunctivePrincipal) this.$getProxy();
        }
        
        public java.lang.String name() {
            java.lang.StringBuffer sb = new java.lang.StringBuffer();
            for (fabric.util.Iterator iter = this.get$conjuncts().iterator();
                 iter.hasNext(); ) {
                fabric.lang.security.Principal p =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(iter.next());
                sb.append(fabric.lang.security.PrincipalUtil._Impl.toString(p));
                if (iter.hasNext()) sb.append("&");
            }
            return sb.toString();
        }
        
        public boolean delegatesTo(fabric.lang.security.Principal p) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p)) instanceof fabric.lang.security.ConjunctivePrincipal) {
                fabric.lang.security.ConjunctivePrincipal cp =
                  (fabric.lang.security.ConjunctivePrincipal)
                    fabric.lang.Object._Proxy.$getProxy(p);
                return cp.get$conjuncts().containsAll(this.get$conjuncts());
            }
            for (fabric.util.Iterator iter = this.get$conjuncts().iterator();
                 iter.hasNext(); ) {
                fabric.lang.security.Principal q =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(iter.next());
                if (!fabric.lang.security.PrincipalUtil._Impl.delegatesTo(q, p))
                    return false;
            }
            return true;
        }
        
        public int hashCode() {
            if (fabric.lang.Object._Proxy.idEquals(this.get$hashCode(), null)) {
                this.set$hashCode(
                       java.lang.Integer.valueOf(
                                           this.get$conjuncts().hashCode()));
            }
            return this.get$hashCode().intValue();
        }
        
        public boolean equals(fabric.lang.security.Principal p) {
            if (fabric.lang.Object._Proxy.
                  $getProxy(
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap(
                        p)) instanceof fabric.lang.security.ConjunctivePrincipal) {
                fabric.lang.security.ConjunctivePrincipal that =
                  (fabric.lang.security.ConjunctivePrincipal)
                    fabric.lang.Object._Proxy.$getProxy(p);
                return this.hashCode() == that.hashCode() &&
                  this.get$conjuncts().equals(that.get$conjuncts()) &&
                  that.get$conjuncts().equals(this.get$conjuncts());
            }
            return false;
        }
        
        public boolean isAuthorized(java.lang.Object authPrf,
                                    fabric.lang.security.Closure closure,
                                    fabric.lang.security.Label lb,
                                    boolean executeNow) {
            for (fabric.util.Iterator iter = this.get$conjuncts().iterator();
                 iter.hasNext(); ) {
                fabric.lang.security.Principal p =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(iter.next());
                if (!p.isAuthorized(authPrf, closure, lb, executeNow))
                    return false;
            }
            return true;
        }
        
        public fabric.lang.security.ActsForProof findProofUpto(
          fabric.worker.Store store, fabric.lang.security.Principal p,
          java.lang.Object searchState) {
            fabric.util.Map
              proofs =
              (fabric.util.HashMap)
                fabric.lang.Object._Proxy.
                $getProxy(
                  ((fabric.util.HashMap)
                     new fabric.util.HashMap._Impl(store).$getProxy()).
                      fabric$util$HashMap$());
            for (fabric.util.Iterator iter = this.get$conjuncts().iterator();
                 iter.hasNext(); ) {
                fabric.lang.security.Principal q =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(iter.next());
                fabric.lang.security.ActsForProof prf =
                  fabric.lang.security.PrincipalUtil._Impl.findActsForProof(
                                                             store, p, q,
                                                             searchState);
                if (fabric.lang.Object._Proxy.idEquals(prf, null)) return null;
                proofs.put(q, prf);
            }
            return (fabric.lang.security.ToConjunctProof)
                     fabric.lang.Object._Proxy.
                     $getProxy(
                       ((fabric.lang.security.ToConjunctProof)
                          new fabric.lang.security.ToConjunctProof._Impl(store).
                          $getProxy()).
                           fabric$lang$security$ToConjunctProof$(
                             p,
                             (fabric.lang.security.ConjunctivePrincipal)
                               this.$getProxy(), proofs));
        }
        
        public fabric.lang.security.ActsForProof findProofDownto(
          fabric.worker.Store store, fabric.lang.security.Principal q,
          java.lang.Object searchState) {
            for (fabric.util.Iterator iter = this.get$conjuncts().iterator();
                 iter.hasNext(); ) {
                fabric.lang.security.Principal witness =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(iter.next());
                fabric.lang.security.ActsForProof prf =
                  fabric.lang.security.PrincipalUtil._Impl.findActsForProof(
                                                             store, witness, q,
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
                                (fabric.lang.security.ConjunctivePrincipal)
                                  this.$getProxy(), witness));
                    return (fabric.lang.security.TransitiveProof)
                             fabric.lang.Object._Proxy.
                             $getProxy(
                               ((fabric.lang.security.TransitiveProof)
                                  new fabric.lang.security.TransitiveProof.
                                    _Impl(store).
                                  $getProxy()).
                                   fabric$lang$security$TransitiveProof$(
                                     step, witness, prf));
                }
            }
            return null;
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.ConjunctivePrincipal) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.ConjunctivePrincipal._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.conjuncts, refTypes, out,
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
            this.conjuncts = (fabric.util.Set)
                               $readRef(fabric.util.Set._Proxy.class,
                                        (fabric.common.RefTypeEnum)
                                          refTypes.next(), in, store,
                                        intraStoreRefs, interStoreRefs);
            this.hashCode = (java.lang.Integer) in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.ConjunctivePrincipal._Impl src =
              (fabric.lang.security.ConjunctivePrincipal._Impl) other;
            this.conjuncts = src.conjuncts;
            this.hashCode = src.hashCode;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.ConjunctivePrincipal._Static {
            public _Proxy(fabric.lang.security.ConjunctivePrincipal._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.ConjunctivePrincipal.
              _Static $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  ConjunctivePrincipal.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    lang.
                    security.
                    ConjunctivePrincipal.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.ConjunctivePrincipal._Static.
                        _Impl.class);
                $instance = (fabric.lang.security.ConjunctivePrincipal._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.ConjunctivePrincipal._Static {
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
                return new fabric.lang.security.ConjunctivePrincipal._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 54, -48, -110, 110,
    -65, 63, -72, -8, -86, -16, -56, -52, 88, -78, -82, -85, 113, -68, 3, 83,
    106, 75, 59, 36, 3, 72, -22, -78, -9, 99, -51, -111 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
