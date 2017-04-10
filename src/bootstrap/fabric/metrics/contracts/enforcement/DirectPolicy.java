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
    
    public static final byte[] $classHash = new byte[] { -40, 14, -73, 31, -123,
    49, -41, 121, -44, 46, -37, -124, 5, 89, 95, 80, 67, 55, -60, -42, -97, 11,
    -95, 38, 82, -46, 64, 45, -128, 33, 79, 109 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1491836575000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
