package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.Contract;

/**
 * Purely to allow for indirection for the policy a contract is using.
 */
public interface PolicyBox extends fabric.lang.Object {
    public fabric.metrics.contracts.enforcement.EnforcementPolicy get$value();
    
    public fabric.metrics.contracts.enforcement.EnforcementPolicy set$value(
      fabric.metrics.contracts.enforcement.EnforcementPolicy val);
    
    public fabric.metrics.contracts.enforcement.PolicyBox
      fabric$metrics$contracts$enforcement$PolicyBox$();
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.Contract mc);
    
    public void unapply(fabric.metrics.contracts.Contract mc);
    
    public void activate();
    
    public void acquireReconfigLocks();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.enforcement.PolicyBox {
        public fabric.metrics.contracts.enforcement.EnforcementPolicy get$value(
          ) {
            return ((fabric.metrics.contracts.enforcement.PolicyBox._Impl)
                      fetch()).get$value();
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy set$value(
          fabric.metrics.contracts.enforcement.EnforcementPolicy val) {
            return ((fabric.metrics.contracts.enforcement.PolicyBox._Impl)
                      fetch()).set$value(val);
        }
        
        public fabric.metrics.contracts.enforcement.PolicyBox
          fabric$metrics$contracts$enforcement$PolicyBox$() {
            return ((fabric.metrics.contracts.enforcement.PolicyBox) fetch()).
              fabric$metrics$contracts$enforcement$PolicyBox$();
        }
        
        public long expiry() {
            return ((fabric.metrics.contracts.enforcement.PolicyBox) fetch()).
              expiry();
        }
        
        public void apply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.PolicyBox) fetch()).apply(
                                                                         arg1);
        }
        
        public void unapply(fabric.metrics.contracts.Contract arg1) {
            ((fabric.metrics.contracts.enforcement.PolicyBox) fetch()).unapply(
                                                                         arg1);
        }
        
        public void activate() {
            ((fabric.metrics.contracts.enforcement.PolicyBox) fetch()).activate(
                                                                         );
        }
        
        public void acquireReconfigLocks() {
            ((fabric.metrics.contracts.enforcement.PolicyBox) fetch()).
              acquireReconfigLocks();
        }
        
        public _Proxy(PolicyBox._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.contracts.enforcement.PolicyBox {
        public fabric.metrics.contracts.enforcement.EnforcementPolicy get$value(
          ) {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.value;
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy set$value(
          fabric.metrics.contracts.enforcement.EnforcementPolicy val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.contracts.enforcement.EnforcementPolicy value;
        
        public fabric.metrics.contracts.enforcement.PolicyBox
          fabric$metrics$contracts$enforcement$PolicyBox$() {
            fabric$lang$Object$();
            return (fabric.metrics.contracts.enforcement.PolicyBox)
                     this.$getProxy();
        }
        
        public long expiry() { return this.get$value().expiry(); }
        
        public void apply(fabric.metrics.contracts.Contract mc) {
            this.get$value().apply(mc);
        }
        
        public void unapply(fabric.metrics.contracts.Contract mc) {
            this.get$value().unapply(mc);
        }
        
        public void activate() { this.get$value().activate(); }
        
        public void acquireReconfigLocks() {
            this.get$value().acquireReconfigLocks();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.enforcement.PolicyBox._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.value, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.value =
              (fabric.
                metrics.
                contracts.
                enforcement.
                EnforcementPolicy)
                $readRef(
                  fabric.metrics.contracts.enforcement.EnforcementPolicy.
                    _Proxy.class, (fabric.common.RefTypeEnum) refTypes.next(),
                  in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.enforcement.PolicyBox._Impl src =
              (fabric.metrics.contracts.enforcement.PolicyBox._Impl) other;
            this.value = src.value;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.enforcement.PolicyBox._Static {
            public _Proxy(fabric.metrics.contracts.enforcement.PolicyBox.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.enforcement.PolicyBox.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  enforcement.
                  PolicyBox.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    enforcement.
                    PolicyBox.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.enforcement.PolicyBox._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.enforcement.PolicyBox._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.enforcement.PolicyBox._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.enforcement.PolicyBox.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 13, 99, -48, 99, 1,
    123, 89, 42, -60, -43, -36, -42, 99, 60, 95, -94, 76, -118, -39, 60, -27,
    -88, 12, -84, -81, 71, -64, 56, 120, -16, 21, -18 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525110923000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
