package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Iterator;
import fabric.util.Map;
import java.util.Set;

/**
 * This code is mostly copied from Jif.
 */
public interface ToConjunctProof extends fabric.lang.security.ActsForProof {
    public fabric.util.Map get$conjunctProofs();
    
    public fabric.util.Map set$conjunctProofs(fabric.util.Map val);
    
    public fabric.lang.security.ToConjunctProof
      fabric$lang$security$ToConjunctProof$(
      fabric.lang.security.Principal actor,
      fabric.lang.security.ConjunctivePrincipal granter,
      fabric.util.Map conjunctProofs);
    
    public fabric.util.Map getConjunctProofs();
    
    public void gatherDelegationDependencies(java.util.Set s);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.ToConjunctProof {
        public fabric.util.Map get$conjunctProofs() {
            return ((fabric.lang.security.ToConjunctProof._Impl) fetch()).
              get$conjunctProofs();
        }
        
        public fabric.util.Map set$conjunctProofs(fabric.util.Map val) {
            return ((fabric.lang.security.ToConjunctProof._Impl) fetch()).
              set$conjunctProofs(val);
        }
        
        public fabric.lang.security.ToConjunctProof
          fabric$lang$security$ToConjunctProof$(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.ConjunctivePrincipal arg2,
          fabric.util.Map arg3) {
            return ((fabric.lang.security.ToConjunctProof) fetch()).
              fabric$lang$security$ToConjunctProof$(arg1, arg2, arg3);
        }
        
        public fabric.util.Map getConjunctProofs() {
            return ((fabric.lang.security.ToConjunctProof) fetch()).
              getConjunctProofs();
        }
        
        public _Proxy(ToConjunctProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl
    extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.ToConjunctProof {
        public fabric.util.Map get$conjunctProofs() {
            return this.conjunctProofs;
        }
        
        public fabric.util.Map set$conjunctProofs(fabric.util.Map val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.conjunctProofs = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Map conjunctProofs;
        
        public fabric.lang.security.ToConjunctProof
          fabric$lang$security$ToConjunctProof$(
          fabric.lang.security.Principal actor,
          fabric.lang.security.ConjunctivePrincipal granter,
          fabric.util.Map conjunctProofs) {
            this.set$conjunctProofs(conjunctProofs);
            fabric$lang$security$ActsForProof$(actor, granter);
            return (fabric.lang.security.ToConjunctProof) this.$getProxy();
        }
        
        public fabric.util.Map getConjunctProofs() {
            return this.get$conjunctProofs();
        }
        
        public void gatherDelegationDependencies(java.util.Set s) {
            fabric.lang.security.ConjunctivePrincipal cp =
              (fabric.lang.security.ConjunctivePrincipal)
                fabric.lang.Object._Proxy.$getProxy(getGranter());
            for (fabric.util.Iterator iter = cp.get$conjuncts().iterator();
                 iter.hasNext(); ) {
                fabric.lang.security.Principal conjunct =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(iter.next());
                fabric.lang.security.ActsForProof pr =
                  (fabric.lang.security.ActsForProof)
                    fabric.lang.Object._Proxy.$getProxy(
                                                this.getConjunctProofs(
                                                       ).get(conjunct));
                pr.gatherDelegationDependencies(s);
            }
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.ToConjunctProof) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.ToConjunctProof._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.conjunctProofs, refTypes, out,
                      intraStoreRefs, interStoreRefs);
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
            this.conjunctProofs = (fabric.util.Map)
                                    $readRef(fabric.util.Map._Proxy.class,
                                             (fabric.common.RefTypeEnum)
                                               refTypes.next(), in, store,
                                             intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.ToConjunctProof._Impl src =
              (fabric.lang.security.ToConjunctProof._Impl) other;
            this.conjunctProofs = src.conjunctProofs;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.ToConjunctProof._Static {
            public _Proxy(fabric.lang.security.ToConjunctProof._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.ToConjunctProof._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  ToConjunctProof.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.ToConjunctProof._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.ToConjunctProof._Static._Impl.class);
                $instance = (fabric.lang.security.ToConjunctProof._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.ToConjunctProof._Static {
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
                return new fabric.lang.security.ToConjunctProof._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 108, -56, 118, 54, -38,
    122, -125, -2, -66, -124, -43, -115, -58, -124, -10, -103, 82, 13, -28, 34,
    -121, 17, -37, 0, -32, 108, 35, 49, -76, -64, 60, 116 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
