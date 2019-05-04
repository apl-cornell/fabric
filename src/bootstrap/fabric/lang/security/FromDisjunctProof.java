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
public interface FromDisjunctProof extends fabric.lang.security.ActsForProof {
    public fabric.util.Map get$disjunctProofs();
    
    public fabric.util.Map set$disjunctProofs(fabric.util.Map val);
    
    public fabric.lang.security.FromDisjunctProof
      fabric$lang$security$FromDisjunctProof$(
      fabric.lang.security.DisjunctivePrincipal actor,
      fabric.lang.security.Principal granter, fabric.util.Map disjunctProofs);
    
    public fabric.util.Map getDisjunctProofs();
    
    public void gatherDelegationDependencies(java.util.Set s);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.FromDisjunctProof {
        public fabric.util.Map get$disjunctProofs() {
            return ((fabric.lang.security.FromDisjunctProof._Impl) fetch()).
              get$disjunctProofs();
        }
        
        public fabric.util.Map set$disjunctProofs(fabric.util.Map val) {
            return ((fabric.lang.security.FromDisjunctProof._Impl) fetch()).
              set$disjunctProofs(val);
        }
        
        public fabric.lang.security.FromDisjunctProof
          fabric$lang$security$FromDisjunctProof$(
          fabric.lang.security.DisjunctivePrincipal arg1,
          fabric.lang.security.Principal arg2, fabric.util.Map arg3) {
            return ((fabric.lang.security.FromDisjunctProof) fetch()).
              fabric$lang$security$FromDisjunctProof$(arg1, arg2, arg3);
        }
        
        public fabric.util.Map getDisjunctProofs() {
            return ((fabric.lang.security.FromDisjunctProof) fetch()).
              getDisjunctProofs();
        }
        
        public _Proxy(FromDisjunctProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl
    extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.FromDisjunctProof {
        public fabric.util.Map get$disjunctProofs() {
            return this.disjunctProofs;
        }
        
        public fabric.util.Map set$disjunctProofs(fabric.util.Map val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.disjunctProofs = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.util.Map disjunctProofs;
        
        public fabric.lang.security.FromDisjunctProof
          fabric$lang$security$FromDisjunctProof$(
          fabric.lang.security.DisjunctivePrincipal actor,
          fabric.lang.security.Principal granter,
          fabric.util.Map disjunctProofs) {
            fabric$lang$security$ActsForProof$(actor, granter);
            this.set$disjunctProofs(disjunctProofs);
            return (fabric.lang.security.FromDisjunctProof) this.$getProxy();
        }
        
        public fabric.util.Map getDisjunctProofs() {
            return this.get$disjunctProofs();
        }
        
        public void gatherDelegationDependencies(java.util.Set s) {
            fabric.lang.security.DisjunctivePrincipal dp =
              (fabric.lang.security.DisjunctivePrincipal)
                fabric.lang.Object._Proxy.$getProxy(getActor());
            for (fabric.util.Iterator iter = dp.get$disjuncts().iterator();
                 iter.hasNext(); ) {
                fabric.lang.security.Principal disjunct =
                  (fabric.lang.security.Principal)
                    fabric.lang.Object._Proxy.$getProxy(iter.next());
                fabric.lang.security.ActsForProof pr =
                  (fabric.lang.security.ActsForProof)
                    fabric.lang.Object._Proxy.$getProxy(
                                                this.getDisjunctProofs(
                                                       ).get(disjunct));
                pr.gatherDelegationDependencies(s);
            }
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.FromDisjunctProof) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.FromDisjunctProof._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.disjunctProofs, refTypes, out,
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
            this.disjunctProofs = (fabric.util.Map)
                                    $readRef(fabric.util.Map._Proxy.class,
                                             (fabric.common.RefTypeEnum)
                                               refTypes.next(), in, store,
                                             intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.FromDisjunctProof._Impl src =
              (fabric.lang.security.FromDisjunctProof._Impl) other;
            this.disjunctProofs = src.disjunctProofs;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.FromDisjunctProof._Static {
            public _Proxy(fabric.lang.security.FromDisjunctProof._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.FromDisjunctProof._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  FromDisjunctProof.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    lang.
                    security.
                    FromDisjunctProof.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.FromDisjunctProof._Static.
                        _Impl.class);
                $instance = (fabric.lang.security.FromDisjunctProof._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.FromDisjunctProof._Static {
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
                return new fabric.lang.security.FromDisjunctProof._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -105, -51, -13, -101,
    -76, 22, 19, -88, -104, -124, -120, 105, 119, -122, -108, 82, 58, 42, -119,
    -101, -91, -65, 77, -98, 118, 117, -104, 29, -68, -116, 113, -94 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
