package fabric.lang.security;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import java.util.Set;

/**
 * This code is mostly copied from Jif.
 */
public interface TransitiveProof extends fabric.lang.security.ActsForProof {
    public fabric.lang.security.ActsForProof get$actorToP();
    
    public fabric.lang.security.ActsForProof set$actorToP(
      fabric.lang.security.ActsForProof val);
    
    public fabric.lang.security.ActsForProof get$pToGranter();
    
    public fabric.lang.security.ActsForProof set$pToGranter(
      fabric.lang.security.ActsForProof val);
    
    public fabric.lang.security.Principal get$p();
    
    public fabric.lang.security.Principal set$p(
      fabric.lang.security.Principal val);
    
    public fabric.lang.security.TransitiveProof
      fabric$lang$security$TransitiveProof$(
      fabric.lang.security.ActsForProof actorToP,
      fabric.lang.security.Principal p,
      fabric.lang.security.ActsForProof pToGranter);
    
    public fabric.lang.security.ActsForProof getActorToP();
    
    public fabric.lang.security.ActsForProof getPToGranter();
    
    public fabric.lang.security.Principal getP();
    
    public void gatherDelegationDependencies(java.util.Set s);
    
    public fabric.lang.Object $initLabels();
    
    public static class _Proxy extends fabric.lang.security.ActsForProof._Proxy
      implements fabric.lang.security.TransitiveProof {
        public fabric.lang.security.ActsForProof get$actorToP() {
            return ((fabric.lang.security.TransitiveProof._Impl) fetch()).
              get$actorToP();
        }
        
        public fabric.lang.security.ActsForProof set$actorToP(
          fabric.lang.security.ActsForProof val) {
            return ((fabric.lang.security.TransitiveProof._Impl) fetch()).
              set$actorToP(val);
        }
        
        public fabric.lang.security.ActsForProof get$pToGranter() {
            return ((fabric.lang.security.TransitiveProof._Impl) fetch()).
              get$pToGranter();
        }
        
        public fabric.lang.security.ActsForProof set$pToGranter(
          fabric.lang.security.ActsForProof val) {
            return ((fabric.lang.security.TransitiveProof._Impl) fetch()).
              set$pToGranter(val);
        }
        
        public fabric.lang.security.Principal get$p() {
            return ((fabric.lang.security.TransitiveProof._Impl) fetch()).get$p(
                                                                            );
        }
        
        public fabric.lang.security.Principal set$p(
          fabric.lang.security.Principal val) {
            return ((fabric.lang.security.TransitiveProof._Impl) fetch()).
              set$p(val);
        }
        
        public fabric.lang.security.TransitiveProof
          fabric$lang$security$TransitiveProof$(
          fabric.lang.security.ActsForProof arg1,
          fabric.lang.security.Principal arg2,
          fabric.lang.security.ActsForProof arg3) {
            return ((fabric.lang.security.TransitiveProof) fetch()).
              fabric$lang$security$TransitiveProof$(arg1, arg2, arg3);
        }
        
        public fabric.lang.security.ActsForProof getActorToP() {
            return ((fabric.lang.security.TransitiveProof) fetch()).getActorToP(
                                                                      );
        }
        
        public fabric.lang.security.ActsForProof getPToGranter() {
            return ((fabric.lang.security.TransitiveProof) fetch()).
              getPToGranter();
        }
        
        public fabric.lang.security.Principal getP() {
            return ((fabric.lang.security.TransitiveProof) fetch()).getP();
        }
        
        public _Proxy(TransitiveProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl
    extends fabric.lang.security.ActsForProof._Impl
      implements fabric.lang.security.TransitiveProof {
        public fabric.lang.security.ActsForProof get$actorToP() {
            return this.actorToP;
        }
        
        public fabric.lang.security.ActsForProof set$actorToP(
          fabric.lang.security.ActsForProof val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.actorToP = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.ActsForProof actorToP;
        
        public fabric.lang.security.ActsForProof get$pToGranter() {
            return this.pToGranter;
        }
        
        public fabric.lang.security.ActsForProof set$pToGranter(
          fabric.lang.security.ActsForProof val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.pToGranter = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.ActsForProof pToGranter;
        
        public fabric.lang.security.Principal get$p() { return this.p; }
        
        public fabric.lang.security.Principal set$p(
          fabric.lang.security.Principal val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.p = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.Principal p;
        
        public fabric.lang.security.TransitiveProof
          fabric$lang$security$TransitiveProof$(
          fabric.lang.security.ActsForProof actorToP,
          fabric.lang.security.Principal p,
          fabric.lang.security.ActsForProof pToGranter) {
            this.set$actorToP(actorToP);
            this.set$pToGranter(pToGranter);
            this.set$p(p);
            fabric$lang$security$ActsForProof$(
              !fabric.lang.Object._Proxy.idEquals(actorToP, null)
                  ? actorToP.getActor()
                  : null,
              !fabric.lang.Object._Proxy.idEquals(pToGranter, null)
                  ? pToGranter.getGranter()
                  : null);
            return (fabric.lang.security.TransitiveProof) this.$getProxy();
        }
        
        public fabric.lang.security.ActsForProof getActorToP() {
            return this.get$actorToP();
        }
        
        public fabric.lang.security.ActsForProof getPToGranter() {
            return this.get$pToGranter();
        }
        
        public fabric.lang.security.Principal getP() { return this.get$p(); }
        
        public void gatherDelegationDependencies(java.util.Set s) {
            this.get$actorToP().gatherDelegationDependencies(s);
            this.get$pToGranter().gatherDelegationDependencies(s);
        }
        
        public fabric.lang.Object $initLabels() {
            this.set$$updateLabel(
                   fabric.lang.security.LabelUtil._Impl.noComponents());
            this.set$$accessPolicy(
                   fabric.lang.security.LabelUtil._Impl.bottomConf());
            return (fabric.lang.security.TransitiveProof) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.TransitiveProof._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.actorToP, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.pToGranter, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.p, refTypes, out, intraStoreRefs,
                      interStoreRefs);
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
            this.actorToP =
              (fabric.lang.security.ActsForProof)
                $readRef(fabric.lang.security.ActsForProof._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.pToGranter =
              (fabric.lang.security.ActsForProof)
                $readRef(fabric.lang.security.ActsForProof._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.p = (fabric.lang.security.Principal)
                       $readRef(fabric.lang.security.Principal._Proxy.class,
                                (fabric.common.RefTypeEnum) refTypes.next(), in,
                                store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.TransitiveProof._Impl src =
              (fabric.lang.security.TransitiveProof._Impl) other;
            this.actorToP = src.actorToP;
            this.pToGranter = src.pToGranter;
            this.p = src.p;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.TransitiveProof._Static {
            public _Proxy(fabric.lang.security.TransitiveProof._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.TransitiveProof._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  TransitiveProof.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.TransitiveProof._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.TransitiveProof._Static._Impl.class);
                $instance = (fabric.lang.security.TransitiveProof._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.TransitiveProof._Static {
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
                return new fabric.lang.security.TransitiveProof._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 42, 100, -22, -36, 74,
    -68, -56, 81, -29, -78, 94, 60, 4, -16, -118, 49, 42, -35, 58, -54, 43, -17,
    -124, -24, -89, 103, -39, 9, -65, 26, 86, 94 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
