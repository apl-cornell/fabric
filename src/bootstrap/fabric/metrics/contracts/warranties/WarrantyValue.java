package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.MetricContract;

/**
 * A utility class for tracking {@link WarrantyComp} results and associated
 * {@link MetricContract}s implying their validity.
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
      fabric.lang.Object value, fabric.metrics.contracts.MetricContract contract);
    
    /**
   * @return the time this result is expected to last (can be used to compare
   *         potential results so the {@link WarrantyComp} can return the
   *         longest lasting choice).
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
        
        /** The result value. */
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
        
        /**
   * A {@link MetricContract} which, when valid, implies the value is current.
   */
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
   * @return the time this result is expected to last (can be used to compare
   *         potential results so the {@link WarrantyComp} can return the
   *         longest lasting choice).
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
    
    public static final byte[] $classHash = new byte[] { 36, 99, -42, 95, 71,
    100, 42, 38, -80, -124, 126, 117, -59, 68, 82, 23, -24, -26, -25, -30, -127,
    -60, 113, 109, -118, -95, 91, 46, -41, 93, -22, 46 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1506451157000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
