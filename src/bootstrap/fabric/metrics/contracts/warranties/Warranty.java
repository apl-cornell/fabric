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
                  log(
                    java.util.logging.Level.INFO,
                    "REFRESHING WITNESS FOR {0}",
                    (java.lang.Object)
                      fabric.lang.WrappedJavaInlineable.
                      $unwrap((fabric.metrics.contracts.warranties.Warranty)
                                this.$getProxy()));
                this.get$witness().handleUpdates();
            }
            if (fabric.lang.Object._Proxy.idEquals(this.get$witness(), null) ||
                  !this.get$witness().valid(currentTime)) {
                fabric.metrics.contracts.warranties.WarrantyValue curVal =
                  this.get$computation().apply(currentTime);
                if (!curVal.get$value().equals(this.get$value())) {
                    fabric.common.Logging.METRICS_LOGGER.
                      log(
                        java.util.logging.Level.
                          FINE, "UPDATING {0} TO {1}", new fabric.lang.Object[] { (fabric.metrics.contracts.warranties.Warranty) this.$getProxy(), curVal.get$value() });
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
    
    public static final byte[] $classHash = new byte[] { -58, -76, 2, -128, 88,
    12, -89, -43, 117, 6, -46, 90, 113, -63, -11, -50, -29, 110, 3, 104, 0, 66,
    11, 56, 61, 58, -114, 4, 4, -123, 53, 110 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492373488000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
