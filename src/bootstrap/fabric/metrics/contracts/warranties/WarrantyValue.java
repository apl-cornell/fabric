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
            fabric$lang$Object$();
            this.set$value(value);
            this.set$contract(contract);
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
    
    public static final byte[] $classHash = new byte[] { -21, -75, 79, 27, -33,
    9, -79, 127, -89, 71, -70, -110, 117, -27, -12, -15, -78, -108, -25, -111,
    23, -39, 11, 33, -85, -70, 123, 62, -10, 32, 0, 109 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1508274494000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
