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
public interface ActsForProof extends fabric.lang.Object {
    public fabric.lang.security.Principal get$actor();
    
    public fabric.lang.security.Principal set$actor(
      fabric.lang.security.Principal val);
    
    public fabric.lang.security.Principal get$granter();
    
    public fabric.lang.security.Principal set$granter(
      fabric.lang.security.Principal val);
    
    public fabric.lang.security.ActsForProof fabric$lang$security$ActsForProof$(
      fabric.lang.security.Principal actor,
      fabric.lang.security.Principal granter);
    
    public fabric.lang.security.Principal getActor();
    
    public fabric.lang.security.Principal getGranter();
    
    public abstract void gatherDelegationDependencies(java.util.Set s);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.security.ActsForProof {
        public fabric.lang.security.Principal get$actor() {
            return ((fabric.lang.security.ActsForProof._Impl) fetch()).
              get$actor();
        }
        
        public fabric.lang.security.Principal set$actor(
          fabric.lang.security.Principal val) {
            return ((fabric.lang.security.ActsForProof._Impl) fetch()).
              set$actor(val);
        }
        
        public fabric.lang.security.Principal get$granter() {
            return ((fabric.lang.security.ActsForProof._Impl) fetch()).
              get$granter();
        }
        
        public fabric.lang.security.Principal set$granter(
          fabric.lang.security.Principal val) {
            return ((fabric.lang.security.ActsForProof._Impl) fetch()).
              set$granter(val);
        }
        
        public fabric.lang.security.ActsForProof
          fabric$lang$security$ActsForProof$(
          fabric.lang.security.Principal arg1,
          fabric.lang.security.Principal arg2) {
            return ((fabric.lang.security.ActsForProof) fetch()).
              fabric$lang$security$ActsForProof$(arg1, arg2);
        }
        
        public fabric.lang.security.Principal getActor() {
            return ((fabric.lang.security.ActsForProof) fetch()).getActor();
        }
        
        public fabric.lang.security.Principal getGranter() {
            return ((fabric.lang.security.ActsForProof) fetch()).getGranter();
        }
        
        public void gatherDelegationDependencies(java.util.Set arg1) {
            ((fabric.lang.security.ActsForProof) fetch()).
              gatherDelegationDependencies(arg1);
        }
        
        public _Proxy(ActsForProof._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.security.ActsForProof {
        public fabric.lang.security.Principal get$actor() { return this.actor; }
        
        public fabric.lang.security.Principal set$actor(
          fabric.lang.security.Principal val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.actor = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.Principal actor;
        
        public fabric.lang.security.Principal get$granter() {
            return this.granter;
        }
        
        public fabric.lang.security.Principal set$granter(
          fabric.lang.security.Principal val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.granter = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.security.Principal granter;
        
        public fabric.lang.security.ActsForProof
          fabric$lang$security$ActsForProof$(
          fabric.lang.security.Principal actor,
          fabric.lang.security.Principal granter) {
            this.set$actor(actor);
            this.set$granter(granter);
            fabric$lang$Object$();
            return (fabric.lang.security.ActsForProof) this.$getProxy();
        }
        
        public fabric.lang.security.Principal getActor() {
            return this.get$actor();
        }
        
        public fabric.lang.security.Principal getGranter() {
            return this.get$granter();
        }
        
        public abstract void gatherDelegationDependencies(java.util.Set s);
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.security.ActsForProof._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.actor, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.granter, refTypes, out, intraStoreRefs,
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
            this.actor = (fabric.lang.security.Principal)
                           $readRef(fabric.lang.security.Principal._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.granter =
              (fabric.lang.security.Principal)
                $readRef(fabric.lang.security.Principal._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.security.ActsForProof._Impl src =
              (fabric.lang.security.ActsForProof._Impl) other;
            this.actor = src.actor;
            this.granter = src.granter;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.security.ActsForProof._Static {
            public _Proxy(fabric.lang.security.ActsForProof._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.security.ActsForProof._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  security.
                  ActsForProof.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.security.ActsForProof._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.security.ActsForProof._Static._Impl.class);
                $instance = (fabric.lang.security.ActsForProof._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.security.ActsForProof._Static {
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
                return new fabric.lang.security.ActsForProof._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -58, -123, 115, 11, -6,
    -121, -108, -101, -55, -112, 91, -82, -3, -120, -115, -69, 34, 10, -120,
    -78, 43, 37, 37, 23, 109, -100, -89, -82, 31, -42, -10, -38 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
