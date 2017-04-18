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
            if (!valid(java.lang.System.currentTimeMillis())) refresh();
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
    
    public static final byte[] $classHash = new byte[] { 109, -102, -57, -112,
    11, 47, -39, 41, 27, 30, -21, 24, -28, -29, -13, -127, -24, -126, 121, 33,
    61, -100, 119, -71, -122, 41, -35, 31, -73, 42, -28, 55 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492539056000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
