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
    
    public static final byte[] $classHash = new byte[] { -83, 124, -72, 36, -14,
    70, 102, 116, 40, -7, 25, -25, 43, -18, 17, -91, 75, -70, 101, -22, -79, 5,
    15, -36, 101, 87, 46, 13, 95, -50, -86, 23 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
