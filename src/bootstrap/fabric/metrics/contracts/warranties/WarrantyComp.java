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
    
    public fabric.metrics.contracts.warranties.WarrantyComp
      fabric$metrics$contracts$warranties$WarrantyComp$();
    
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
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              fabric$metrics$contracts$warranties$WarrantyComp$();
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
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            fabric$lang$Object$();
            return (fabric.metrics.contracts.warranties.WarrantyComp)
                     this.$getProxy();
        }
        
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
    
    public static final byte[] $classHash = new byte[] { 87, 0, -72, -104, -19,
    110, -33, 69, 76, -39, 85, 10, 15, -100, -114, 52, 60, -50, 89, -110, 9,
    -124, 84, -119, -122, 69, -67, 78, -103, 77, -71, 109 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1496931714000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
