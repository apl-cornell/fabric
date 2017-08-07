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
 * A computation that uses {@link Contract}s to cache and reuse results.
 * <p>
 * Acts as an {@link Observer} of the currently associated {@link Contract}.
 * This helps to ensure that the {@link Contract} implying the currently cached
 * result is correct doesn't get deactivated prematurely by the API
 * implementation.
 */
public interface WarrantyComp
  extends fabric.metrics.util.Observer, fabric.lang.Object {
    public fabric.metrics.contracts.warranties.WarrantyValue get$curVal();
    
    public fabric.metrics.contracts.warranties.WarrantyValue set$curVal(fabric.metrics.contracts.warranties.WarrantyValue val);
    
    /**
   * Create a new updated result paired with a contract that would enforce it
   * after this call.
   *
   * @param time
   *            the current time we're running a new update at.
   */
    public abstract fabric.metrics.contracts.warranties.WarrantyValue
      updatedVal(long time);
    
    /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
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
        
        /** The currently cached result. */
        private fabric.metrics.contracts.warranties.WarrantyValue curVal;
        
        /**
   * Create a new updated result paired with a contract that would enforce it
   * after this call.
   *
   * @param time
   *            the current time we're running a new update at.
   */
        public abstract fabric.metrics.contracts.warranties.WarrantyValue
          updatedVal(long time);
        
        /**
   * Run this warranty computation at the given time.
   *
   * @param time
   *            the current time we're applying this computation at
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
    
    public static final byte[] $classHash = new byte[] { -27, -82, 62, -1, 4,
    53, 83, 43, -21, -1, 116, 36, 80, 77, 38, -28, 27, -40, 57, -83, 110, -105,
    22, 25, -78, 88, 45, -80, -24, 80, 59, -52 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1502134860000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
