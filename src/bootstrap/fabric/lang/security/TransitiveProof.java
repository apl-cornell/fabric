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
    
    public static final byte[] $classHash = new byte[] { 2, -83, 21, 126, -106,
    -124, -86, -29, -119, 87, -85, -6, 105, -48, -100, -3, 84, 67, -119, -5, 0,
    -71, 55, -16, 74, 2, -76, 63, 35, 7, 113, -60 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
