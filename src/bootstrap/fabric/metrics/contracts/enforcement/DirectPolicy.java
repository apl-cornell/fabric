package fabric.metrics.contracts.enforcement;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.MetricContract;

/**
 * An enforcement policy for enforcing a {@link MetricContract}s by checking
 * every update to the associated metric.
 */
public interface DirectPolicy
  extends fabric.metrics.contracts.enforcement.EnforcementPolicy {
    public long get$expiry();
    
    public long set$expiry(long val);
    
    public long postInc$expiry();
    
    public long postDec$expiry();
    
    /**
   * @param expiry
   *        how long to enforce the {@link MetricContract} this is applied
   *        to using direct checks on updates to the metric.
   */
    public fabric.metrics.contracts.enforcement.DirectPolicy
      fabric$metrics$contracts$enforcement$DirectPolicy$(long expiry);
    
    public long expiry();
    
    public void apply(fabric.metrics.contracts.MetricContract mc);
    
    public void unapply(fabric.metrics.contracts.MetricContract mc);
    
    public java.lang.String toString();
    
    public boolean equals(fabric.lang.Object other);
    
    public static class _Proxy
    extends fabric.metrics.contracts.enforcement.EnforcementPolicy._Proxy
      implements fabric.metrics.contracts.enforcement.DirectPolicy {
        public long get$expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).get$expiry();
        }
        
        public long set$expiry(long val) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).set$expiry(val);
        }
        
        public long postInc$expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).postInc$expiry();
        }
        
        public long postDec$expiry() {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy._Impl)
                      fetch()).postDec$expiry();
        }
        
        public fabric.metrics.contracts.enforcement.DirectPolicy
          fabric$metrics$contracts$enforcement$DirectPolicy$(long arg1) {
            return ((fabric.metrics.contracts.enforcement.DirectPolicy)
                      fetch()).
              fabric$metrics$contracts$enforcement$DirectPolicy$(arg1);
        }
        
        public _Proxy(DirectPolicy._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl
    extends fabric.metrics.contracts.enforcement.EnforcementPolicy._Impl
      implements fabric.metrics.contracts.enforcement.DirectPolicy {
        public long get$expiry() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.expiry;
        }
        
        public long set$expiry(long val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.expiry = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public long postInc$expiry() {
            long tmp = this.get$expiry();
            this.set$expiry((long) (tmp + 1));
            return tmp;
        }
        
        public long postDec$expiry() {
            long tmp = this.get$expiry();
            this.set$expiry((long) (tmp - 1));
            return tmp;
        }
        
        /**
   * How long to enforce the {@link MetricContract} this is applied to using
   * direct checks on updates to the metric.
   */
        public long expiry;
        
        /**
   * @param expiry
   *        how long to enforce the {@link MetricContract} this is applied
   *        to using direct checks on updates to the metric.
   */
        public fabric.metrics.contracts.enforcement.DirectPolicy
          fabric$metrics$contracts$enforcement$DirectPolicy$(long expiry) {
            this.set$expiry((long) expiry);
            fabric$metrics$contracts$enforcement$EnforcementPolicy$();
            return (fabric.metrics.contracts.enforcement.DirectPolicy)
                     this.$getProxy();
        }
        
        public long expiry() { return this.get$expiry(); }
        
        public void apply(fabric.metrics.contracts.MetricContract mc) {
            mc.getMetric().startTracking(mc);
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract mc) {
            mc.getMetric().stopTracking(mc);
        }
        
        public java.lang.String toString() {
            return "Direct Until " + this.get$expiry();
        }
        
        public boolean equals(fabric.lang.Object other) {
            if (!(fabric.lang.Object._Proxy.
                    $getProxy((java.lang.Object)
                                fabric.lang.WrappedJavaInlineable.$unwrap(other)) instanceof fabric.metrics.contracts.enforcement.DirectPolicy))
                return false;
            fabric.metrics.contracts.enforcement.DirectPolicy that =
              (fabric.metrics.contracts.enforcement.DirectPolicy)
                fabric.lang.Object._Proxy.$getProxy(other);
            return this.get$expiry() == that.get$expiry();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.contracts.enforcement.DirectPolicy._Proxy(
                     this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeLong(this.expiry);
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
            this.expiry = in.readLong();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.contracts.enforcement.DirectPolicy._Impl src =
              (fabric.metrics.contracts.enforcement.DirectPolicy._Impl) other;
            this.expiry = src.expiry;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.contracts.enforcement.DirectPolicy._Static {
            public _Proxy(fabric.metrics.contracts.enforcement.DirectPolicy.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.contracts.enforcement.
              DirectPolicy._Static $instance;
            
            static {
                fabric.
                  metrics.
                  contracts.
                  enforcement.
                  DirectPolicy.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    contracts.
                    enforcement.
                    DirectPolicy.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.contracts.enforcement.DirectPolicy._Static.
                        _Impl.class);
                $instance =
                  (fabric.metrics.contracts.enforcement.DirectPolicy._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.contracts.enforcement.DirectPolicy._Static {
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
                return new fabric.metrics.contracts.enforcement.DirectPolicy.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -75, 22, 16, 78, 2,
    -73, -51, 96, 91, 108, -107, 101, -6, -41, 68, -95, 22, 88, -51, -52, -92,
    -11, -42, 71, 33, -86, -12, -39, -33, -118, -45, -1 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492522047000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
