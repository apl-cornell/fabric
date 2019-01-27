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
    
    public static final byte[] $classHash = new byte[] { -80, -49, 23, -81, 66,
    -98, 48, 89, 41, -66, 46, -113, 126, 71, 84, -120, -117, -73, -104, 90, 123,
    -110, -98, -18, 4, 29, 127, 82, -97, -95, -101, 104 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
