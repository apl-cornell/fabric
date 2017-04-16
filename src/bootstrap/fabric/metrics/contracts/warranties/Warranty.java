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
    
    public void activate();
    
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
                fabric.metrics.contracts.warranties.WarrantyValue curVal =
                  this.get$computation().apply(currentTime);
                if (!curVal.get$value().equals(this.get$value())) {
                    this.markModified();
                    this.set$value(curVal.get$value());
                }
                if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(),
                                                        null) &&
                      !fabric.lang.Object._Proxy.idEquals(
                                                   this.get$witness(),
                                                   curVal.get$contract())) {
                    this.get$witness().
                      removeObserver(
                        (fabric.metrics.contracts.warranties.Warranty)
                          this.$getProxy());
                    this.get$witness().deactivate();
                }
                this.set$witness(curVal.get$contract());
                this.get$witness().activate();
                this.get$witness().
                  addObserver((fabric.metrics.contracts.warranties.Warranty)
                                this.$getProxy());
                fabric.common.Logging.METRICS_LOGGER.
                  info(
                    "DEFENDING " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.
                              $unwrap(
                                (fabric.metrics.contracts.warranties.Warranty)
                                  this.$getProxy())) +
                      " WITH " +
                      java.lang.String.
                        valueOf(
                          fabric.lang.WrappedJavaInlineable.$unwrap(
                                                              this.get$witness(
                                                                     ))));
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
        
        public void activate() {
            fabric.worker.transaction.TransactionManager.getInstance().
              resolveObservations();
            super.activate();
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
    
    public static final byte[] $classHash = new byte[] { 31, -60, 52, -16, 45,
    46, 116, 68, -105, -122, 108, 58, -36, -23, -25, -41, 17, -125, -1, 116,
    -99, -70, 73, -93, 4, 37, 30, 93, -89, -52, -7, 75 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492299499000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
