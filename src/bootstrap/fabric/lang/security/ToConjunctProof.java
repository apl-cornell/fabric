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
    
    public static final byte[] $classHash = new byte[] { 36, 120, 83, 93, 51,
    -45, -66, 2, 101, -93, 46, 47, 81, -101, -45, -65, 74, 38, 5, -96, -116, -7,
    22, -54, -124, -124, -5, 57, 74, -55, 58, -58 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
