package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.Metric;
import fabric.worker.metrics.ImmutableMetricsVector;

/**
 * Purely here to provide indirection for the inlined value.
 */
public interface ImmutableMetricsVectorFinalBox extends fabric.lang.Object {
    public fabric.worker.metrics.ImmutableMetricsVector get$value();
    
    public fabric.worker.metrics.ImmutableMetricsVector set$value(
      fabric.worker.metrics.ImmutableMetricsVector val);
    
    public int length();
    
    public fabric.metrics.Metric get(int i);
    
    public fabric.metrics.Metric[] array();
    
    public fabric.metrics.util.ImmutableMetricsVectorFinalBox
      fabric$metrics$util$ImmutableMetricsVectorFinalBox$(
      fabric.metrics.Metric[] value);
    
    public fabric.metrics.util.ImmutableMetricsVectorFinalBox
      fabric$metrics$util$ImmutableMetricsVectorFinalBox$(
      fabric.worker.metrics.ImmutableMetricsVector value);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.ImmutableMetricsVectorFinalBox {
        public fabric.worker.metrics.ImmutableMetricsVector get$value() {
            return ((fabric.metrics.util.ImmutableMetricsVectorFinalBox._Impl)
                      fetch()).get$value();
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$value(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            return ((fabric.metrics.util.ImmutableMetricsVectorFinalBox._Impl)
                      fetch()).set$value(val);
        }
        
        public int length() {
            return ((fabric.metrics.util.ImmutableMetricsVectorFinalBox)
                      fetch()).length();
        }
        
        public fabric.metrics.Metric get(int arg1) {
            return ((fabric.metrics.util.ImmutableMetricsVectorFinalBox)
                      fetch()).get(arg1);
        }
        
        public fabric.metrics.Metric[] array() {
            return ((fabric.metrics.util.ImmutableMetricsVectorFinalBox)
                      fetch()).array();
        }
        
        public fabric.metrics.util.ImmutableMetricsVectorFinalBox
          fabric$metrics$util$ImmutableMetricsVectorFinalBox$(
          fabric.metrics.Metric[] arg1) {
            return ((fabric.metrics.util.ImmutableMetricsVectorFinalBox)
                      fetch()).
              fabric$metrics$util$ImmutableMetricsVectorFinalBox$(arg1);
        }
        
        public fabric.metrics.util.ImmutableMetricsVectorFinalBox
          fabric$metrics$util$ImmutableMetricsVectorFinalBox$(
          fabric.worker.metrics.ImmutableMetricsVector arg1) {
            return ((fabric.metrics.util.ImmutableMetricsVectorFinalBox)
                      fetch()).
              fabric$metrics$util$ImmutableMetricsVectorFinalBox$(arg1);
        }
        
        public _Proxy(ImmutableMetricsVectorFinalBox._Impl impl) {
            super(impl);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.ImmutableMetricsVectorFinalBox {
        public fabric.worker.metrics.ImmutableMetricsVector get$value() {
            return this.value;
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector set$value(
          fabric.worker.metrics.ImmutableMetricsVector val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.worker.metrics.ImmutableMetricsVector value;
        
        public int length() { return this.get$value().length(); }
        
        public fabric.metrics.Metric get(int i) {
            return this.get$value().get(i);
        }
        
        public fabric.metrics.Metric[] array() {
            return this.get$value().array();
        }
        
        public fabric.metrics.util.ImmutableMetricsVectorFinalBox
          fabric$metrics$util$ImmutableMetricsVectorFinalBox$(
          fabric.metrics.Metric[] value) {
            this.set$value(
                   fabric.worker.metrics.ImmutableMetricsVector.createVector(
                                                                  value));
            fabric$lang$Object$();
            return (fabric.metrics.util.ImmutableMetricsVectorFinalBox)
                     this.$getProxy();
        }
        
        public fabric.metrics.util.ImmutableMetricsVectorFinalBox
          fabric$metrics$util$ImmutableMetricsVectorFinalBox$(
          fabric.worker.metrics.ImmutableMetricsVector value) {
            this.set$value(value);
            fabric$lang$Object$();
            return (fabric.metrics.util.ImmutableMetricsVectorFinalBox)
                     this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.ImmutableMetricsVectorFinalBox.
                     _Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            $writeInline(out, this.value);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     long expiry, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, expiry, observers, labelStore, labelOnum,
                  accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.value = (fabric.worker.metrics.ImmutableMetricsVector)
                           in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.ImmutableMetricsVectorFinalBox._Impl src =
              (fabric.metrics.util.ImmutableMetricsVectorFinalBox._Impl) other;
            this.value = src.value;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.metrics.util.ImmutableMetricsVectorFinalBox._Static
        {
            public _Proxy(fabric.metrics.util.ImmutableMetricsVectorFinalBox.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.
              ImmutableMetricsVectorFinalBox._Static $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  ImmutableMetricsVectorFinalBox.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    util.
                    ImmutableMetricsVectorFinalBox.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.ImmutableMetricsVectorFinalBox.
                        _Static._Impl.class);
                $instance =
                  (fabric.metrics.util.ImmutableMetricsVectorFinalBox._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.metrics.util.ImmutableMetricsVectorFinalBox._Static
        {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         long expiry, fabric.worker.metrics.ImmutableObserverSet observers, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, expiry, observers, labelStore, labelOnum,
                      accessPolicyStore, accessPolicyOnum, in, refTypes,
                      intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.metrics.util.ImmutableMetricsVectorFinalBox.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 86, 111, -123, -18,
    -45, 66, -42, 36, 88, 82, -80, 101, 10, -89, -117, 14, -51, -28, 30, -72,
    -116, -18, -51, 99, -9, 54, -85, -2, 22, -35, 36, -78 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524345697000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
