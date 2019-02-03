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
    
    public static final byte[] $classHash = new byte[] { 69, -26, 23, -60, -123,
    -41, 39, 124, -94, -20, -6, 61, -10, -41, -98, -22, 81, -52, 35, 8, -111,
    -49, -4, -126, 106, 126, 109, -125, -74, -120, 79, -2 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
