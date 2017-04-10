package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.Contract;
import fabric.metrics.contracts.MetricContract;

/**
 * A Warranty (also known as a General Contract) is a time-limited assertion of
 * the form <code>f(...) = y</code> and is enforced by an associated metric
 * contract.
 */
public interface Warranty extends fabric.metrics.contracts.Contract {
    public fabric.metrics.contracts.warranties.WarrantyComp get$computation();
    
    public fabric.metrics.contracts.warranties.WarrantyComp set$computation(
      fabric.metrics.contracts.warranties.WarrantyComp val);
    
    public fabric.lang.Object get$value();
    
    public fabric.lang.Object set$value(fabric.lang.Object val);
    
    public fabric.metrics.contracts.MetricContract get$witness();
    
    public fabric.metrics.contracts.MetricContract set$witness(
      fabric.metrics.contracts.MetricContract val);
    
    /**
   * @param computation
   *        the computation being warrantied.
   */
    public fabric.metrics.contracts.warranties.Warranty
      fabric$metrics$contracts$warranties$Warranty$(
      fabric.metrics.contracts.warranties.WarrantyComp computation);
    
    public void refresh();
    
    /**
   * @return the current value of the computation this enforces (assuming the
   *       {@link Warranty} is currently valid).
   */
    public fabric.lang.Object value();
    
    public java.lang.String toString();
    
    public static class _Proxy extends fabric.metrics.contracts.Contract._Proxy
      implements fabric.metrics.contracts.warranties.Warranty {
        public fabric.metrics.contracts.warranties.WarrantyComp get$computation(
          ) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).get$computation();
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp set$computation(
          fabric.metrics.contracts.warranties.WarrantyComp val) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).set$computation(val);
        }
        
        public fabric.lang.Object get$value() {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).get$value();
        }
        
        public fabric.lang.Object set$value(fabric.lang.Object val) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).set$value(val);
        }
        
        public fabric.metrics.contracts.MetricContract get$witness() {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).get$witness();
        }
        
        public fabric.metrics.contracts.MetricContract set$witness(
          fabric.metrics.contracts.MetricContract val) {
            return ((fabric.metrics.contracts.warranties.Warranty._Impl)
                      fetch()).set$witness(val);
        }
        
        public fabric.metrics.contracts.warranties.Warranty
          fabric$metrics$contracts$warranties$Warranty$(
          fabric.metrics.contracts.warranties.WarrantyComp arg1) {
            return ((fabric.metrics.contracts.warranties.Warranty) fetch()).
              fabric$metrics$contracts$warranties$Warranty$(arg1);
        }
        
        public fabric.lang.Object value() {
            return ((fabric.metrics.contracts.warranties.Warranty) fetch()).
              value();
        }
        
        public _Proxy(Warranty._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.metrics.contracts.Contract._Impl
      implements fabric.metrics.contracts.warranties.Warranty {
        public fabric.metrics.contracts.warranties.WarrantyComp get$computation(
          ) {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.computation;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp set$computation(
          fabric.metrics.contracts.warranties.WarrantyComp val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.computation = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp computation;
        
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
        
        protected fabric.lang.Object value;
        
        public fabric.metrics.contracts.MetricContract get$witness() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.witness;
        }
        
        public fabric.metrics.contracts.MetricContract set$witness(
          fabric.metrics.contracts.MetricContract val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.witness = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        protected fabric.metrics.contracts.MetricContract witness;
        
        /**
   * @param computation
   *        the computation being warrantied.
   */
        public fabric.metrics.contracts.warranties.Warranty
          fabric$metrics$contracts$warranties$Warranty$(
          fabric.metrics.contracts.warranties.WarrantyComp computation) {
            this.set$computation(computation);
            fabric$metrics$contracts$Contract$();
            return (fabric.metrics.contracts.warranties.Warranty)
                     this.$getProxy();
        }
        
        public void refresh() {
            long currentTime = java.lang.System.currentTimeMillis();
            if (fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) ||
                  !this.get$witness().valid(currentTime)) {
                if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(),
                                                        null))
                    this.get$witness().
                      removeObserver(
                        (fabric.metrics.contracts.warranties.Warranty)
                          this.$getProxy());
                fabric.metrics.contracts.warranties.WarrantyValue curVal =
                  this.get$computation().apply(currentTime);
                if (!curVal.get$value().equals(this.get$value())) {
                    this.markModified();
                    this.set$value(curVal.get$value());
                }
                this.set$witness(curVal.get$contract());
                this.get$witness().activate();
                this.get$witness().
                  addObserver((fabric.metrics.contracts.warranties.Warranty)
                                this.$getProxy());
            }
            update(this.get$witness().getExpiry());
        }
        
        /**
   * @return the current value of the computation this enforces (assuming the
   *       {@link Warranty} is currently valid).
   */
        public fabric.lang.Object value() { return this.get$value(); }
        
        public java.lang.String toString() {
            return "Warranty " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(
                                                    this.get$computation())) +
            " = " +
            java.lang.String.
              valueOf(
                fabric.lang.WrappedJavaInlineable.$unwrap(this.get$value())) +
            " until " +
            getExpiry();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.warranties.Warranty._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.computation, refTypes, out,
                      intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.value, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.witness, refTypes, out, intraStoreRefs,
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
            this.computation =
              (fabric.metrics.contracts.warranties.WarrantyComp)
                $readRef(
                  fabric.metrics.contracts.warranties.WarrantyComp._Proxy.class,
                  (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                  intraStoreRefs, interStoreRefs);
            this.value = (fabric.lang.Object)
                           $readRef(fabric.lang.Object._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
            this.witness =
              (fabric.metrics.contracts.MetricContract)
                $readRef(fabric.metrics.contracts.MetricContract._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.Warranty._Impl src =
              (fabric.metrics.contracts.warranties.Warranty._Impl) other;
            this.computation = src.computation;
            this.value = src.value;
            this.witness = src.witness;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.warranties.Warranty._Static {
            public _Proxy(fabric.metrics.contracts.warranties.Warranty._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.warranties.Warranty.
              _Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  warranties.
                  Warranty.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    warranties.
                    Warranty.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.warranties.Warranty._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.warranties.Warranty._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.warranties.Warranty._Static {
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
                return new fabric.metrics.contracts.warranties.Warranty._Static.
                         _Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 125, 103, -84, 105, 47,
    -47, 92, -115, -80, 101, -91, -127, 66, 113, -84, 38, -65, 57, 6, -15, 91,
    28, 122, 99, 4, 8, 23, 27, -96, 88, -112, -56 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
