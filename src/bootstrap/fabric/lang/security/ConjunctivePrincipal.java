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
    
    public static final byte[] $classHash = new byte[] { 100, -114, 65, 104,
    -66, -111, -118, -105, -107, 48, -77, -99, 75, 100, -109, 96, 81, -90, 34,
    58, 82, -72, 42, -123, 64, -89, -118, 107, 15, 15, -122, 118 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
