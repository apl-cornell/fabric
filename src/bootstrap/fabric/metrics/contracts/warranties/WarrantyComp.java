package fabric.metrics.contracts.warranties;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.util.HashSet;
import fabric.util.Set;
import fabric.metrics.Metric;
import fabric.metrics.SampledMetric;
import fabric.metrics.util.Observer;

/**
 * A warrantiable computation. Returns values wrapped in a {@link WarrantyValue}
 * along with an appropriate {@link Metric} for the result.
 *
 * Currently, WarrantyComp acts as an observer of the currently associated
 * treaty. This helps to ensure that treaties don't get deactivated prematurely
 * when used by the computation. TODO: this seems a bit like a hack? Is there a
 * better way to handle the issue this addresses?
 */
public interface WarrantyComp
  extends fabric.metrics.util.Observer, fabric.lang.Object {
    public fabric.metrics.contracts.warranties.WarrantyValue get$curVal();
    
    public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(
      fabric.metrics.contracts.warranties.WarrantyValue val);
    
    /**
   * Create a new updated result paired with a contract that would enforce it
   * after this call.
   */
    public abstract fabric.metrics.contracts.warranties.WarrantyValue
      updatedVal(long time);
    
    /**
   * Run this warranty computation at the given time.
   */
    public fabric.lang.Object apply(long time);
    
    public fabric.util.Set getLeafSubjects();
    
    public boolean handleUpdates();
    
    public fabric.metrics.contracts.warranties.WarrantyComp
      fabric$metrics$contracts$warranties$WarrantyComp$();
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.metrics.contracts.warranties.WarrantyValue get$curVal() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).get$curVal();
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(
          fabric.metrics.contracts.warranties.WarrantyValue val) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp._Impl)
                      fetch()).set$curVal(val);
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue updatedVal(
          long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              updatedVal(arg1);
        }
        
        public fabric.lang.Object apply(long arg1) {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              apply(arg1);
        }
        
        public fabric.util.Set getLeafSubjects() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              getLeafSubjects();
        }
        
        public boolean handleUpdates() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              handleUpdates();
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            return ((fabric.metrics.contracts.warranties.WarrantyComp) fetch()).
              fabric$metrics$contracts$warranties$WarrantyComp$();
        }
        
        public _Proxy(WarrantyComp._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public abstract static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.contracts.warranties.WarrantyComp {
        public fabric.metrics.contracts.warranties.WarrantyValue get$curVal() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.curVal;
        }
        
        public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(
          fabric.metrics.contracts.warranties.WarrantyValue val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.curVal = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.metrics.contracts.warranties.WarrantyValue curVal;
        
        /**
   * Create a new updated result paired with a contract that would enforce it
   * after this call.
   */
        public abstract fabric.metrics.contracts.warranties.WarrantyValue
          updatedVal(long time);
        
        /**
   * Run this warranty computation at the given time.
   */
        public fabric.lang.Object apply(long time) {
            if (fabric.lang.Object._Proxy.idEquals(this.get$curVal(), null) ||
                  !this.get$curVal().get$contract().isActivated() ||
                  this.get$curVal().get$contract().stale(time)) {
                if (!fabric.lang.Object._Proxy.idEquals(this.get$curVal(),
                                                        null)) {
                    this.get$curVal().get$contract().
                      removeObserver(
                        (fabric.metrics.contracts.warranties.WarrantyComp)
                          this.$getProxy());
                }
                this.set$curVal(updatedVal(time));
                this.get$curVal().get$contract().activate();
                this.get$curVal().get$contract().
                  addObserver((fabric.metrics.contracts.warranties.WarrantyComp)
                                this.$getProxy());
            }
            return this.get$curVal().get$value();
        }
        
        public fabric.util.Set getLeafSubjects() {
            if (!fabric.lang.Object._Proxy.idEquals(this.get$curVal(), null))
                return this.get$curVal().get$contract().getLeafSubjects();
            return ((fabric.util.HashSet)
                      new fabric.util.HashSet._Impl(this.$getStore()).$getProxy(
                                                                        )).
              fabric$util$HashSet$();
        }
        
        public boolean handleUpdates() {
            long time = java.lang.System.currentTimeMillis();
            return !fabric.lang.Object._Proxy.idEquals(this.get$curVal(),
                                                       null) &&
              this.get$curVal().get$contract().valid(time) &&
              !fabric.lang.Object._Proxy.idEquals(this.get$curVal().get$value(),
                                                  apply(time));
        }
        
        public fabric.metrics.contracts.warranties.WarrantyComp
          fabric$metrics$contracts$warranties$WarrantyComp$() {
            fabric$lang$Object$();
            return (fabric.metrics.contracts.warranties.WarrantyComp)
                     this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.warranties.WarrantyComp._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.curVal, refTypes, out, intraStoreRefs,
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
            this.curVal =
              (fabric.
                metrics.
                contracts.
                warranties.
                WarrantyValue)
                $readRef(
                  fabric.metrics.contracts.warranties.WarrantyValue.
                    _Proxy.class, (fabric.common.RefTypeEnum) refTypes.next(),
                  in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.warranties.WarrantyComp._Impl src =
              (fabric.metrics.contracts.warranties.WarrantyComp._Impl) other;
            this.curVal = src.curVal;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.warranties.WarrantyComp._Static {
            public _Proxy(fabric.metrics.contracts.warranties.WarrantyComp.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.warranties.
              WarrantyComp._Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  warranties.
                  WarrantyComp.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    warranties.
                    WarrantyComp.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.warranties.WarrantyComp._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.warranties.WarrantyComp._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.warranties.WarrantyComp._Static {
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
                return new fabric.metrics.contracts.warranties.WarrantyComp.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 63, 1, 59, -18, -51,
    -15, -82, -80, 71, -32, -87, 17, 105, -60, -108, 50, 95, -10, 91, -73, 116,
    -5, -106, 122, -119, 10, 41, 9, 24, 50, 94, -39 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1500327047000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
