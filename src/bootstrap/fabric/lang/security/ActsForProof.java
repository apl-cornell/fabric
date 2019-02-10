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
    
    public static final byte[] $classHash = new byte[] { -63, 111, -90, -9, -21,
    113, 1, 119, -10, -25, 51, -116, -21, -50, -44, 102, 117, 21, 30, -55, 30,
    -39, -26, 6, -68, 103, 2, -16, -122, 105, 85, -128 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
