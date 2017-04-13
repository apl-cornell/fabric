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
    
    public static final byte[] $classHash = new byte[] { -73, -32, -49, -69, 62,
    -116, -53, -23, 106, -63, -34, 88, 36, 111, -65, 96, -106, 61, 84, 115, 102,
    -124, 110, 100, -86, 30, 8, 39, 18, 67, 60, -33 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492109732000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
