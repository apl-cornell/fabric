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
            mc.getMetric().addObserver(mc);
        }
        
        public void unapply(fabric.metrics.contracts.MetricContract mc) {
            mc.getMetric().removeObserver(mc);
        }
        
        public java.lang.String toString() {
            return "Direct Until " + this.get$expiry();
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
    
    public static final byte[] $classHash = new byte[] { -106, 36, -5, -111,
    -108, 68, 57, -49, 32, -46, 47, 126, -118, 11, -97, -79, -37, -97, 115, 75,
    -40, 78, 75, -13, -89, 11, -35, -16, -46, 22, 36, 104 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1492106580000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
