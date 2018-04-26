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
                return new fabric.metrics.contracts.warranties.WarrantyValue.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 94, -91, 124, -54, -37,
    72, -4, 59, -71, 103, -8, 114, -3, -89, -123, 100, -86, 76, 61, 96, -26,
    -58, -48, 29, 57, 39, 43, 39, -79, -83, -5, 123 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524675608000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
