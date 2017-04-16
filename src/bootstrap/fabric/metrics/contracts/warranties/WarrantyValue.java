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
    
    public static final byte[] $classHash = new byte[] { -116, -84, 97, -97, 45,
    104, 87, -88, -47, -7, -113, 3, 117, 63, 81, -113, 16, 53, -122, 85, 104,
    76, -110, -32, -1, 73, -51, 43, 92, 39, -88, -117 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492294329000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
