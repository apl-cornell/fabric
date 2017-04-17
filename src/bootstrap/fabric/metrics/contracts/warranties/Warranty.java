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
import java.util.logging.Level;

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
    
    public void attemptExtension_remote(fabric.lang.security.Principal caller);
    
    public void attemptExtension();
    
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
            if (!fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) &&
                  !this.get$witness().valid(currentTime)) {
                fabric.common.Logging.METRICS_LOGGER.
                  log(java.util.logging.Level.INFO,
                      "REFRESHING WARRANTY WITNESS");
                this.get$witness().handleUpdates();
            }
            if (fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) ||
                  !this.get$witness().valid(currentTime)) {
                fabric.metrics.contracts.warranties.WarrantyValue curVal =
                  this.get$computation().apply(currentTime);
                if (!curVal.get$value().equals(this.get$value())) {
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
        
        public void attemptExtension_remote(
          fabric.lang.security.Principal caller) {
            
        }
        
        public void attemptExtension() {  }
        
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
    
    public static final byte[] $classHash = new byte[] { 70, 3, -22, 58, -73,
    -64, 127, -36, 29, -3, 32, 121, 82, -122, -35, -98, -38, 92, 109, -76, -69,
    3, 84, -53, -67, -73, 86, -41, -47, -76, -35, -12 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492446405000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
