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
    
    public static final byte[] $classHash = new byte[] { -48, 21, 8, 53, -60,
    121, -92, 106, -27, 38, -35, -57, 120, -47, -28, 90, 47, 93, 38, 30, -8,
    -123, -52, 97, -118, 13, 82, -47, 111, 23, -122, 108 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
