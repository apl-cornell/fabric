package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.Collections;
import fabric.util.Set;
import fabric.metrics.SampledMetric;
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
    
    public fabric.util.Set getLeafSubjects();
    
    public void deactivate();
    
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
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.contracts.warranties.Warranty) fetch()).
              getLeafSubjects();
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
        
        public fabric.util.Set getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(), null))
                return this.get$witness().getLeafSubjects();
            else
                return fabric.util.Collections._Static._Proxy.$instance.
                  get$EMPTY_SET();
        }
        
        public void deactivate() {
            if (!isObserved()) {
                if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(),
                                                        null))
                    this.get$witness().
                      removeObserver(
                        (fabric.metrics.contracts.warranties.Warranty)
                          this.$getProxy());
            }
            super.deactivate();
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
    
    public static final byte[] $classHash = new byte[] { -39, 110, -86, 83, -18,
    -88, -90, 105, -117, -25, -10, -28, -95, -112, 84, 80, -123, 78, -33, -114,
    111, -63, 59, -71, -12, 93, 38, 49, 41, -23, -50, -125 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491927419000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
