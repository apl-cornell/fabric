package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.MetricContract;

/**
 * A utility class for warrantiable computation results, bundling the result
 * with a {@link MetricContract}.
 */
public interface WarrantyValue extends fabric.lang.Object {
    public fabric.lang.Object get$value();
    
    public fabric.lang.Object set$value(fabric.lang.Object val);
    
    public fabric.metrics.contracts.MetricContract get$contract();
    
    public fabric.metrics.contracts.MetricContract set$contract(fabric.metrics.contracts.MetricContract val);
    
    /**
   * @param value
   *        the return value we're bundling in this {@link WarrantyValue}
   * @param contract
   *        the value's associated {@link MetricContract}
   */
    public fabric.metrics.contracts.warranties.WarrantyValue
      fabric$metrics$contracts$warranties$WarrantyValue$(
      fabric.lang.Object value,
      fabric.metrics.contracts.MetricContract contract);
    
    /**
   * @return the time this result is expected to last.
   */
    public long expectedLifetime();
    
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
        
        public fabric.metrics.contracts.MetricContract get$contract() {
            return ((fabric.metrics.contracts.warranties.WarrantyValue._Impl)
                      fetch()).get$contract();
        }
        
        public fabric.metrics.contracts.MetricContract set$contract(
          fabric.metrics.contracts.MetricContract val) {
            return ((fabric.metrics.contracts.warranties.WarrantyValue._Impl)
                      fetch()).set$contract(val);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue
          fabric$metrics$contracts$warranties$WarrantyValue$(
          fabric.lang.Object arg1,
          fabric.metrics.contracts.MetricContract arg2) {
            return ((fabric.metrics.contracts.warranties.WarrantyValue)
                      fetch()).
              fabric$metrics$contracts$warranties$WarrantyValue$(arg1, arg2);
        }
        
        public long expectedLifetime() {
            return ((fabric.metrics.contracts.warranties.WarrantyValue)
                      fetch()).expectedLifetime();
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
        
        public fabric.lang.Object value;
        
        public fabric.metrics.contracts.MetricContract get$contract() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.contract;
        }
        
        public fabric.metrics.contracts.MetricContract set$contract(
          fabric.metrics.contracts.MetricContract val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.contract = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.contracts.MetricContract contract;
        
        /**
   * @param value
   *        the return value we're bundling in this {@link WarrantyValue}
   * @param contract
   *        the value's associated {@link MetricContract}
   */
        public fabric.metrics.contracts.warranties.WarrantyValue
          fabric$metrics$contracts$warranties$WarrantyValue$(
          fabric.lang.Object value,
          fabric.metrics.contracts.MetricContract contract) {
            this.set$value(value);
            this.set$contract(contract);
            fabric$lang$Object$();
            return (fabric.metrics.contracts.warranties.WarrantyValue)
                     this.$getProxy();
        }
        
        /**
   * @return the time this result is expected to last.
   */
        public long expectedLifetime() {
            return this.get$contract().getExpectedLifetime();
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
              (fabric.metrics.contracts.MetricContract)
                $readRef(fabric.metrics.contracts.MetricContract._Proxy.class,
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
    
    public static final byte[] $classHash = new byte[] { -118, 58, 115, 121,
    -100, 105, -74, 68, 9, -5, 59, 26, 29, -16, -9, 96, -37, 99, 19, 93, -70,
    98, 4, -123, 72, 76, -26, 124, 60, 100, -91, -91 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500320595000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
