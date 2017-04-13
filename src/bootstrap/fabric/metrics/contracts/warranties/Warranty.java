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
            update(this.get$witness().getExpiryLower());
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
            java.lang.String.valueOf(
                               fabric.lang.WrappedJavaInlineable.$unwrap(
                                                                   getExpiry(
                                                                     )));
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
                this.set$value(null);
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
    
    public static final byte[] $classHash = new byte[] { -113, -16, 108, 19, 43,
    -61, -42, 64, 0, -62, 127, 4, 30, -85, 51, -47, -25, 33, 113, 76, 72, -24,
    113, -49, -125, 80, 8, 65, -126, -128, -94, 48 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492109732000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
