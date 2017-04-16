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
    
    public boolean refresh();
    
    public long getExpiry();
    
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
        
        public boolean refresh() {
            long currentTime = java.lang.System.currentTimeMillis();
            if (fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) ||
                  !this.get$witness().valid(currentTime)) {
                fabric.metrics.contracts.warranties.WarrantyValue curVal =
                  this.get$computation().apply(currentTime);
                if (!curVal.get$value().equals(this.get$value())) {
                    fabric.common.Logging.METRICS_LOGGER.
                      info(
                        "UPDATING " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.
                                  $unwrap(
                                    (fabric.metrics.contracts.warranties.Warranty)
                                      this.$getProxy())) +
                          " VALUE TO " +
                          java.lang.String.
                            valueOf(
                              fabric.lang.WrappedJavaInlineable.
                                  $unwrap(curVal.get$value())));
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
                return true;
            }
            return false;
        }
        
        public long getExpiry() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) &&
                  isActive())
                return this.get$witness().getExpiry();
            return -1;
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
    
    public static final byte[] $classHash = new byte[] { 38, 22, -74, -52, 70,
    115, -56, -113, 127, -49, 111, 10, 110, 97, -23, -31, 6, 3, -57, 79, -46,
    83, -28, -65, -40, 91, 51, -100, 85, 109, -66, -91 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492364387000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
