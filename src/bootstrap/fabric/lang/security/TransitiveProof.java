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
    
    public static final byte[] $classHash = new byte[] { -34, 78, 116, -20, -79,
    126, -103, -54, -93, -104, -31, 65, -65, -90, 18, -14, 83, -57, -95, -44,
    -24, 40, 3, 78, 43, -4, -61, 20, -74, 96, 96, 109 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
