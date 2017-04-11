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
import fabric.worker.transaction.TransactionManager;

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
                                                        null)) {
                    this.get$witness().
                      removeObserver(
                        (fabric.metrics.contracts.warranties.Warranty)
                          this.$getProxy());
                    this.get$witness().deactivate();
                }
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
        public fabric.lang.Object value() {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            return this.get$value();
        }
        
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
                                                        null)) {
                    this.get$witness().
                      removeObserver(
                        (fabric.metrics.contracts.warranties.Warranty)
                          this.$getProxy());
                    this.get$witness().deactivate();
                }
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
    
    public static final byte[] $classHash = new byte[] { 43, -34, 51, 116, -28,
    -47, -32, -78, 95, 24, -125, -95, 75, 89, -72, 39, 82, -58, -45, 106, -74,
    -45, 50, -36, -73, -111, 32, 67, 25, -51, 43, -116 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491933253000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
