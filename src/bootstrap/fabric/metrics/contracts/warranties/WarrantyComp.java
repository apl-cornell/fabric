package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;

/**
 * A warrantiable computation. Returns values wrapped in a {@link WarrantyValue}
 * along with an appropriate {@link Metric} for the result.
 */
public interface WarrantyComp extends fabric.lang.Object {
    public fabric.metrics.contracts.warranties.WarrantyValue get$curVal();
    
    public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(
      fabric.metrics.contracts.warranties.WarrantyValue val);
    
    /**
   * Create a new updated result paired with a contract that would enforce it
   * after this call.
   */
    public abstract fabric.metrics.contracts.warranties.WarrantyValue
      updatedVal(long time);
    
    /**
   * Run this warranty computation at the given time.
   */
    public fabric.lang.Object apply(long time);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.metrics.contracts.warranties.WarrantyValue get$curVal() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$curVal();
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(
          fabric.metrics.contracts.warranties.WarrantyValue val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$curVal(val);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue updatedVal(
          long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              updatedVal(arg1);
        }
        
        public fabric.lang.Object apply(long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              apply(arg1);
        }
        
        public _Proxy(WarrantyComp._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.metrics.contracts.warranties.WarrantyValue get$curVal() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.curVal;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(
          fabric.metrics.contracts.warranties.WarrantyValue val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.curVal = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.metrics.contracts.warranties.WarrantyValue curVal;
        
        /**
   * Create a new updated result paired with a contract that would enforce it
   * after this call.
   */
        public abstract fabric.metrics.contracts.warranties.WarrantyValue
          updatedVal(long time);
        
        /**
   * Run this warranty computation at the given time.
   */
        public fabric.lang.Object apply(long time) {
            if (fabric.lang.Object._Proxy.idEquals(this.get$curVal(), null) ||
                  !this.get$curVal().get$contract().isActive() ||
                  !this.get$curVal().get$contract().valid(time)) {
                this.set$curVal(updatedVal(time));
                this.get$curVal().get$contract().activate();
            }
            return this.get$curVal().get$value();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.warranties.WarrantyComp._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.curVal, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.curVal =
              (fabric.
                metrics.
                contracts.
                warranties.
                WarrantyValue)
                $readRef(
                  fabric.metrics.contracts.warranties.WarrantyValue.
                    _Proxy.class, (fabric.common.RefTypeEnum) refTypes.next(),
                  in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.WarrantyComp._Impl src =
              (fabric.metrics.contracts.warranties.WarrantyComp._Impl) other;
            this.curVal = src.curVal;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.warranties.WarrantyComp._Static {
            public _Proxy(fabric.metrics.contracts.warranties.WarrantyComp.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.warranties.
              WarrantyComp._Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  warranties.
                  WarrantyComp.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    warranties.
                    WarrantyComp.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.warranties.WarrantyComp._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.warranties.WarrantyComp._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.warranties.WarrantyComp._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.contracts.warranties.WarrantyComp.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 47, -91, -33, -12, -21,
    -65, 90, 47, 47, 111, -72, 5, 51, -28, -109, -117, 66, -16, -77, 67, 109,
    -69, -16, -5, 62, 60, 83, -9, 42, -121, 78, 60 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1496929423000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
