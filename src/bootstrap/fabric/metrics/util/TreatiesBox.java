package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;

/**
 * Utility to make treaties exist outside of metrics for locking.
 */
public interface TreatiesBox extends fabric.lang.Object {
    public fabric.metrics.Metric get$owner();
    
    public fabric.metrics.Metric set$owner(fabric.metrics.Metric val);
    
    public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
      fabric.metrics.Metric m);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.TreatiesBox {
        public fabric.metrics.Metric get$owner() {
            return ((fabric.metrics.util.TreatiesBox._Impl) fetch()).get$owner(
                                                                       );
        }
        
        public fabric.metrics.Metric set$owner(fabric.metrics.Metric val) {
            return ((fabric.metrics.util.TreatiesBox._Impl) fetch()).set$owner(
                                                                       val);
        }
        
        public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
          fabric.metrics.Metric arg1) {
            return ((fabric.metrics.util.TreatiesBox) fetch()).
              fabric$metrics$util$TreatiesBox$(arg1);
        }
        
        public _Proxy(TreatiesBox._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.TreatiesBox {
        public fabric.metrics.Metric get$owner() { return this.owner; }
        
        public fabric.metrics.Metric set$owner(fabric.metrics.Metric val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.owner = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.metrics.Metric owner;
        
        public fabric.metrics.util.TreatiesBox fabric$metrics$util$TreatiesBox$(
          fabric.metrics.Metric m) {
            this.set$owner(m);
            fabric$lang$Object$();
            return (fabric.metrics.util.TreatiesBox) this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.TreatiesBox._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeRef($getStore(), this.owner, refTypes, out, intraStoreRefs,
                      interStoreRefs);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObserverSet observers,
                     fabric.worker.metrics.treaties.TreatySet treaties,
                     fabric.worker.Store labelStore, long labelOnum,
                     fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, observers, treaties, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.owner = (fabric.metrics.Metric)
                           $readRef(fabric.metrics.Metric._Proxy.class,
                                    (fabric.common.RefTypeEnum) refTypes.next(),
                                    in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.TreatiesBox._Impl src =
              (fabric.metrics.util.TreatiesBox._Impl) other;
            this.owner = src.owner;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.TreatiesBox._Static {
            public _Proxy(fabric.metrics.util.TreatiesBox._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.TreatiesBox._Static
              $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  TreatiesBox.
                  _Static.
                  _Impl
                  impl =
                  (fabric.metrics.util.TreatiesBox._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.TreatiesBox._Static._Impl.class);
                $instance = (fabric.metrics.util.TreatiesBox._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.TreatiesBox._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObserverSet observers,
                         fabric.worker.metrics.treaties.TreatySet treaties,
                         fabric.worker.Store labelStore, long labelOnum,
                         fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, observers, treaties, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.TreatiesBox._Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 32, -14, 32, -68, 71,
    -80, -9, -99, 39, -19, 40, -104, 30, 24, 60, -38, -14, -73, 52, 53, -102,
    29, 1, 109, 90, -30, -20, 43, -74, 43, 45, -103 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1532719017000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
