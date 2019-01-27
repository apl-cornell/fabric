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
    
    public static final byte[] $classHash = new byte[] { 32, 44, -43, 2, 49,
    120, -6, -86, -75, -55, 108, 61, -24, 25, -11, -62, 111, 9, -97, -41, -27,
    96, 25, 31, 64, 61, -90, -59, 112, 85, 29, 101 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
