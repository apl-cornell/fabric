package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.Contract;

/**
 * A utility class for tracking {@link WarrantyComp} results and associated
 * {@link Contract}s implying their validity.
 */
public interface WarrantyValue extends fabric.lang.Object {
    public fabric.lang.Object get$value();
    
    public fabric.lang.Object set$value(fabric.lang.Object val);
    
    public fabric.metrics.contracts.Contract get$contract();
    
    public fabric.metrics.contracts.Contract set$contract(fabric.metrics.contracts.Contract val);
    
    /**
   * @param value
   *        the return value we're bundling in this {@link WarrantyValue}
   * @param contract
   *        the value's associated {@link Contract}
   */
    public fabric.metrics.contracts.warranties.WarrantyValue
      fabric$metrics$contracts$warranties$WarrantyValue$(
      fabric.lang.Object value, fabric.metrics.contracts.Contract contract);
    
    public java.lang.String toString();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.warranties.WarrantyValue {
        public fabric.lang.Object get$value() {
            return ((fabric.metrics.contracts.warranties.WarrantyValue._Impl)
                      fetch()).get$value();
        }
        
        public fabric.lang.Object set$value(fabric.lang.Object val) {
            return ((fabric.metrics.contracts.warranties.WarrantyValue._Impl)
                      fetch()).set$value(val);
        }
        
        public fabric.metrics.contracts.Contract get$contract() {
            return ((fabric.metrics.contracts.warranties.WarrantyValue._Impl)
                      fetch()).get$contract();
        }
        
        public fabric.metrics.contracts.Contract set$contract(
          fabric.metrics.contracts.Contract val) {
            return ((fabric.metrics.contracts.warranties.WarrantyValue._Impl)
                      fetch()).set$contract(val);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue
          fabric$metrics$contracts$warranties$WarrantyValue$(
          fabric.lang.Object arg1, fabric.metrics.contracts.Contract arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyValue)
                      fetch()).
              fabric$metrics$contracts$warranties$WarrantyValue$(arg1, arg2);
        }
        
        public _Proxy(WarrantyValue._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.contracts.warranties.WarrantyValue {
        public fabric.lang.Object get$value() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.value;
        }
        
        public fabric.lang.Object set$value(fabric.lang.Object val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /** The result value. */
        public fabric.lang.Object value;
        
        public fabric.metrics.contracts.Contract get$contract() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.contract;
        }
        
        public fabric.metrics.contracts.Contract set$contract(
          fabric.metrics.contracts.Contract val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.contract = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
   * A {@link Contract} which, when valid, implies the value is current.
   */
        public fabric.metrics.contracts.Contract contract;
        
        /**
   * @param value
   *        the return value we're bundling in this {@link WarrantyValue}
   * @param contract
   *        the value's associated {@link Contract}
   */
        public fabric.metrics.contracts.warranties.WarrantyValue
          fabric$metrics$contracts$warranties$WarrantyValue$(
          fabric.lang.Object value,
          fabric.metrics.contracts.Contract contract) {
            fabric$lang$Object$();
            this.set$value(value);
            this.set$contract(contract);
            return (fabric.metrics.contracts.warranties.WarrantyValue)
                     this.$getProxy();
        }
        
        public java.lang.String toString() {
            return "WarrantyVal(" +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(this.get$value())) +
            ", " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(
                                                    this.get$contract())) +
            ")";
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.warranties.WarrantyValue._Proxy(
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
            $writeRef($getStore(), this.contract, refTypes, out, intraStoreRefs,
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
            this.value = (fabric.lang.Object)
                           $readRef(fabric.lang.Object._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.contract =
              (fabric.metrics.contracts.Contract)
                $readRef(fabric.metrics.contracts.Contract._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.WarrantyValue._Impl src =
              (fabric.metrics.contracts.warranties.WarrantyValue._Impl) other;
            this.value = src.value;
            this.contract = src.contract;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.warranties.WarrantyValue._Static {
            public _Proxy(fabric.metrics.contracts.warranties.WarrantyValue.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.warranties.
              WarrantyValue._Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  warranties.
                  WarrantyValue.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    warranties.
                    WarrantyValue.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.warranties.WarrantyValue._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.warranties.WarrantyValue._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.warranties.WarrantyValue._Static {
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
                return new fabric.metrics.contracts.warranties.WarrantyValue.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -4, 103, 4, 62, 37,
    -121, -128, -69, 106, 80, 116, -61, 34, 66, -83, -97, -72, 36, -46, -70, 35,
    -118, -112, -70, 5, -91, 106, 91, 120, -59, 2, -61 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524505527000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
