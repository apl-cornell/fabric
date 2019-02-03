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
    
    public static final byte[] $classHash = new byte[] { -103, -48, -42, -62,
    86, -126, 104, -73, -2, 99, 97, -30, -64, 63, 94, 53, 101, -4, -25, -31,
    -29, 42, 80, 83, 106, -79, -43, 93, -52, 52, 58, -68 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
