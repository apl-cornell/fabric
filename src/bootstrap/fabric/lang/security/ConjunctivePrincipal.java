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
    
    public static final byte[] $classHash = new byte[] { -45, -21, 4, 14, -5,
    -98, 22, -105, -103, 115, -42, -120, 29, -100, 118, 105, -61, -50, 31, -17,
    111, 69, -71, 17, -77, -97, -126, -104, -23, 93, -33, 90 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
