package fabric.metrics.util;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.metrics.contracts.Contract;
import fabric.worker.metrics.ImmutableContractsVector;

/**
 * Purely here to provide indirection for the inlined value.
 */
public interface ImmutableContractsVectorFinalBox extends fabric.lang.Object {
    public fabric.worker.metrics.ImmutableContractsVector get$value();
    
    public fabric.worker.metrics.ImmutableContractsVector set$value(
      fabric.worker.metrics.ImmutableContractsVector val);
    
    public int length();
    
    public fabric.metrics.contracts.Contract get(int i);
    
    public fabric.metrics.contracts.Contract[] array();
    
    public fabric.metrics.util.ImmutableContractsVectorFinalBox
      fabric$metrics$util$ImmutableContractsVectorFinalBox$(
      fabric.metrics.contracts.Contract[] value);
    
    public fabric.metrics.util.ImmutableContractsVectorFinalBox
      fabric$metrics$util$ImmutableContractsVectorFinalBox$(
      fabric.worker.metrics.ImmutableContractsVector value);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.metrics.util.ImmutableContractsVectorFinalBox {
        public fabric.worker.metrics.ImmutableContractsVector get$value() {
            return ((fabric.metrics.util.ImmutableContractsVectorFinalBox._Impl)
                      fetch()).get$value();
        }
        
        public fabric.worker.metrics.ImmutableContractsVector set$value(
          fabric.worker.metrics.ImmutableContractsVector val) {
            return ((fabric.metrics.util.ImmutableContractsVectorFinalBox._Impl)
                      fetch()).set$value(val);
        }
        
        public int length() {
            return ((fabric.metrics.util.ImmutableContractsVectorFinalBox)
                      fetch()).length();
        }
        
        public fabric.metrics.contracts.Contract get(int arg1) {
            return ((fabric.metrics.util.ImmutableContractsVectorFinalBox)
                      fetch()).get(arg1);
        }
        
        public fabric.metrics.contracts.Contract[] array() {
            return ((fabric.metrics.util.ImmutableContractsVectorFinalBox)
                      fetch()).array();
        }
        
        public fabric.metrics.util.ImmutableContractsVectorFinalBox
          fabric$metrics$util$ImmutableContractsVectorFinalBox$(
          fabric.metrics.contracts.Contract[] arg1) {
            return ((fabric.metrics.util.ImmutableContractsVectorFinalBox)
                      fetch()).
              fabric$metrics$util$ImmutableContractsVectorFinalBox$(arg1);
        }
        
        public fabric.metrics.util.ImmutableContractsVectorFinalBox
          fabric$metrics$util$ImmutableContractsVectorFinalBox$(
          fabric.worker.metrics.ImmutableContractsVector arg1) {
            return ((fabric.metrics.util.ImmutableContractsVectorFinalBox)
                      fetch()).
              fabric$metrics$util$ImmutableContractsVectorFinalBox$(arg1);
        }
        
        public _Proxy(ImmutableContractsVectorFinalBox._Impl impl) {
            super(impl);
        }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static class _Impl extends fabric.lang.Object._Impl
      implements fabric.metrics.util.ImmutableContractsVectorFinalBox {
        public fabric.worker.metrics.ImmutableContractsVector get$value() {
            return this.value;
        }
        
        public fabric.worker.metrics.ImmutableContractsVector set$value(
          fabric.worker.metrics.ImmutableContractsVector val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.value = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public fabric.worker.metrics.ImmutableContractsVector value;
        
        public int length() { return this.get$value().length(); }
        
        public fabric.metrics.contracts.Contract get(int i) {
            return this.get$value().get(i);
        }
        
        public fabric.metrics.contracts.Contract[] array() {
            return this.get$value().array();
        }
        
        public fabric.metrics.util.ImmutableContractsVectorFinalBox
          fabric$metrics$util$ImmutableContractsVectorFinalBox$(
          fabric.metrics.contracts.Contract[] value) {
            this.set$value(
                   fabric.worker.metrics.ImmutableContractsVector.createVector(
                                                                    value));
            fabric$lang$Object$();
            return (fabric.metrics.util.ImmutableContractsVectorFinalBox)
                     this.$getProxy();
        }
        
        public fabric.metrics.util.ImmutableContractsVectorFinalBox
          fabric$metrics$util$ImmutableContractsVectorFinalBox$(
          fabric.worker.metrics.ImmutableContractsVector value) {
            this.set$value(value);
            fabric$lang$Object$();
            return (fabric.metrics.util.ImmutableContractsVectorFinalBox)
                     this.$getProxy();
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.metrics.util.ImmutableContractsVectorFinalBox.
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
            this.value = (fabric.worker.metrics.ImmutableContractsVector)
                           in.readObject();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.metrics.util.ImmutableContractsVectorFinalBox._Impl src =
              (fabric.metrics.util.ImmutableContractsVectorFinalBox._Impl)
                other;
            this.value = src.value;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy
        extends fabric.
          lang.
          Object.
          _Proxy
          implements fabric.metrics.util.ImmutableContractsVectorFinalBox.
                       _Static
        {
            public _Proxy(fabric.metrics.util.ImmutableContractsVectorFinalBox.
                            _Static._Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.metrics.util.
              ImmutableContractsVectorFinalBox._Static $instance;
            
            static {
                fabric.
                  metrics.
                  util.
                  ImmutableContractsVectorFinalBox.
                  _Static.
                  _Impl
                  impl =
                  (fabric.
                    metrics.
                    util.
                    ImmutableContractsVectorFinalBox.
                    _Static.
                    _Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.metrics.util.ImmutableContractsVectorFinalBox.
                        _Static._Impl.class);
                $instance =
                  (fabric.metrics.util.ImmutableContractsVectorFinalBox._Static)
                    impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl
        extends fabric.
          lang.
          Object.
          _Impl
          implements fabric.metrics.util.ImmutableContractsVectorFinalBox.
                       _Static
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
                return new fabric.metrics.util.ImmutableContractsVectorFinalBox.
                         _Static._Proxy(this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 98, 89, -25, -25, 112,
    -9, 61, 81, -108, -27, 17, -1, 70, 80, 95, -4, -43, -24, 106, -82, -63,
    -114, 91, 125, 20, -1, 106, 103, -119, -59, -10, -95 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1524345593000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
