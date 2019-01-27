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
    
    public static final byte[] $classHash = new byte[] { 29, 107, -116, 42, -90,
    -116, 111, -94, -6, 27, 36, -15, 54, -86, 36, 21, -23, -90, -70, -73, 30, 7,
    84, 4, -92, -9, 97, 69, -90, 67, 70, -58 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
