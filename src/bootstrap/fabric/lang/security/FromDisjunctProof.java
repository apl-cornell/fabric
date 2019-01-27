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
    
    public static final byte[] $classHash = new byte[] { -119, 74, -8, 78, 42,
    25, 31, -110, 95, -86, -38, 73, -90, 108, 96, 53, 28, -84, -14, 124, 61,
    122, -111, 15, 94, 110, 28, -19, -36, 81, 80, -36 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
