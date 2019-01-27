package fabric.lang.arrays;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Store;
import fabric.lang.arrays.ObjectArray;
import fabric.lang.arrays.internal._ObjectArray;

/**
 * <p>
 * Implements a multi-dimensional object array by representing it as an array
 * of arrays of arrays ... depending on the number of dimensions
 * 
 * Uses ObjectArray to represent each individual array
 * </p>
 * 
 * @author kvikram
 */
public interface MultiObjectArray extends fabric.lang.Object {
    public int get$dims();
    
    public int set$dims(int val);
    
    public int postInc$dims();
    
    public int postDec$dims();
    
    public fabric.lang.arrays.intArray get$lengths();
    
    public fabric.lang.arrays.intArray set$lengths(
      fabric.lang.arrays.intArray val);
    
    public fabric.lang.arrays.ObjectArray get$firstDim();
    
    public fabric.lang.arrays.ObjectArray set$firstDim(
      fabric.lang.arrays.ObjectArray val);
    
    public java.lang.Class get$proxyType();
    
    public java.lang.Class set$proxyType(java.lang.Class val);
    
    public int get$CHUNK_SIZE_LOG2();
    
    public int set$CHUNK_SIZE_LOG2(int val);
    
    public int postInc$CHUNK_SIZE_LOG2();
    
    public int postDec$CHUNK_SIZE_LOG2();
    
    public fabric.lang.arrays.MultiObjectArray
      fabric$lang$arrays$MultiObjectArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, java.lang.Class proxyType,
      int dims, fabric.lang.arrays.intArray lengths);
    
    public fabric.lang.arrays.MultiObjectArray
      fabric$lang$arrays$MultiObjectArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, java.lang.Class proxyType,
      int dims, fabric.lang.arrays.intArray lengths, int CHUNK_SIZE_LOG2);
    
    public fabric.lang.Object $initLabels();
    
    public fabric.lang.arrays.intArray getLengths();
    
    public fabric.lang.Object get(fabric.lang.arrays.intArray index);
    
    public fabric.lang.Object set(fabric.lang.arrays.intArray index,
                                  fabric.lang.Object data);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.arrays.MultiObjectArray {
        public int get$dims() {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              get$dims();
        }
        
        public int set$dims(int val) {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              set$dims(val);
        }
        
        public int postInc$dims() {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              postInc$dims();
        }
        
        public int postDec$dims() {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              postDec$dims();
        }
        
        public fabric.lang.arrays.intArray get$lengths() {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              get$lengths();
        }
        
        public fabric.lang.arrays.intArray set$lengths(
          fabric.lang.arrays.intArray val) {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              set$lengths(val);
        }
        
        public fabric.lang.arrays.ObjectArray get$firstDim() {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              get$firstDim();
        }
        
        public fabric.lang.arrays.ObjectArray set$firstDim(
          fabric.lang.arrays.ObjectArray val) {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              set$firstDim(val);
        }
        
        public java.lang.Class get$proxyType() {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              get$proxyType();
        }
        
        public java.lang.Class set$proxyType(java.lang.Class val) {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              set$proxyType(val);
        }
        
        public int get$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              get$CHUNK_SIZE_LOG2();
        }
        
        public int set$CHUNK_SIZE_LOG2(int val) {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              set$CHUNK_SIZE_LOG2(val);
        }
        
        public int postInc$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              postInc$CHUNK_SIZE_LOG2();
        }
        
        public int postDec$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).
              postDec$CHUNK_SIZE_LOG2();
        }
        
        public fabric.lang.arrays.MultiObjectArray
          fabric$lang$arrays$MultiObjectArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          java.lang.Class arg3, int arg4, fabric.lang.arrays.intArray arg5) {
            return ((fabric.lang.arrays.MultiObjectArray) fetch()).
              fabric$lang$arrays$MultiObjectArray$(arg1, arg2, arg3, arg4,
                                                   arg5);
        }
        
        public fabric.lang.arrays.MultiObjectArray
          fabric$lang$arrays$MultiObjectArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          java.lang.Class arg3, int arg4, fabric.lang.arrays.intArray arg5,
          int arg6) {
            return ((fabric.lang.arrays.MultiObjectArray) fetch()).
              fabric$lang$arrays$MultiObjectArray$(arg1, arg2, arg3, arg4, arg5,
                                                   arg6);
        }
        
        public fabric.lang.arrays.intArray getLengths() {
            return ((fabric.lang.arrays.MultiObjectArray) fetch()).getLengths();
        }
        
        public fabric.lang.Object get(fabric.lang.arrays.intArray arg1) {
            return ((fabric.lang.arrays.MultiObjectArray) fetch()).get(arg1);
        }
        
        public fabric.lang.Object set(fabric.lang.arrays.intArray arg1,
                                      fabric.lang.Object arg2) {
            return ((fabric.lang.arrays.MultiObjectArray) fetch()).set(arg1,
                                                                       arg2);
        }
        
        public _Proxy(MultiObjectArray._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.arrays.MultiObjectArray {
        public int get$dims() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.dims;
        }
        
        public int set$dims(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.dims = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$dims() {
            int tmp = this.get$dims();
            this.set$dims((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$dims() {
            int tmp = this.get$dims();
            this.set$dims((int) (tmp - 1));
            return tmp;
        }
        
        private int dims;
        
        public fabric.lang.arrays.intArray get$lengths() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.lengths;
        }
        
        public fabric.lang.arrays.intArray set$lengths(
          fabric.lang.arrays.intArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.lengths = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.arrays.intArray lengths;
        
        public fabric.lang.arrays.ObjectArray get$firstDim() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.firstDim;
        }
        
        public fabric.lang.arrays.ObjectArray set$firstDim(
          fabric.lang.arrays.ObjectArray val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.firstDim = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private fabric.lang.arrays.ObjectArray firstDim;
        
        public java.lang.Class get$proxyType() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.proxyType;
        }
        
        public java.lang.Class set$proxyType(java.lang.Class val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.proxyType = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private java.lang.Class proxyType;
        
        public int get$CHUNK_SIZE_LOG2() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.CHUNK_SIZE_LOG2;
        }
        
        public int set$CHUNK_SIZE_LOG2(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.CHUNK_SIZE_LOG2 = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$CHUNK_SIZE_LOG2() {
            int tmp = this.get$CHUNK_SIZE_LOG2();
            this.set$CHUNK_SIZE_LOG2((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$CHUNK_SIZE_LOG2() {
            int tmp = this.get$CHUNK_SIZE_LOG2();
            this.set$CHUNK_SIZE_LOG2((int) (tmp - 1));
            return tmp;
        }
        
        private int CHUNK_SIZE_LOG2;
        
        public fabric.lang.arrays.MultiObjectArray
          fabric$lang$arrays$MultiObjectArray$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy,
          java.lang.Class proxyType, int dims,
          fabric.lang.arrays.intArray lengths) {
            fabric$lang$arrays$MultiObjectArray$(updateLabel, accessPolicy,
                                                 proxyType, dims, lengths, 8);
            return (fabric.lang.arrays.MultiObjectArray) this.$getProxy();
        }
        
        public fabric.lang.arrays.MultiObjectArray
          fabric$lang$arrays$MultiObjectArray$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy,
          java.lang.Class proxyType, int dims,
          fabric.lang.arrays.intArray lengths, int CHUNK_SIZE_LOG2) {
            this.set$$updateLabel(updateLabel);
            this.set$$accessPolicy(accessPolicy);
            fabric$lang$Object$();
            this.set$proxyType(proxyType);
            this.set$CHUNK_SIZE_LOG2((int) CHUNK_SIZE_LOG2);
            this.set$dims((int) dims);
            this.set$lengths(lengths);
            if (lengths.get$length() < 1) {
                throw new java.lang.RuntimeException(
                        "Missing array dimension. NewFabricArrayExt_c should not have let this happen.");
            }
            this.set$firstDim(
                   ((fabric.lang.arrays.ObjectArray)
                      new fabric.lang.arrays.ObjectArray._Impl(
                        this.$getStore()).$getProxy(
                                            )).fabric$lang$arrays$ObjectArray$(
                                                 updateLabel, accessPolicy,
                                                 proxyType,
                                                 (int) lengths.get(0),
                                                 CHUNK_SIZE_LOG2));
            for (int depth = 0; depth < lengths.get$length() - 1; depth++) {
                int range = 1;
                for (int i = 0; i < depth; i++) {
                    range = range * (int) lengths.get(i);
                }
                fabric.lang.arrays.ObjectArray traverse;
                for (int j = 0; j < range; j++) {
                    int nextInd = 0;
                    int divisor = range;
                    traverse = this.get$firstDim();
                    for (int i = 0; i < depth; i++) {
                        divisor = divisor / (int) lengths.get(i);
                        nextInd = j / divisor;
                        traverse =
                          (fabric.lang.arrays.ObjectArray)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        traverse.get(nextInd));
                    }
                    for (int k = 0; k < (int) lengths.get(depth); k++) {
                        traverse.
                          set(
                            k,
                            ((fabric.lang.arrays.ObjectArray)
                               new fabric.lang.arrays.ObjectArray._Impl(
                                 this.$getStore()).$getProxy()).
                                fabric$lang$arrays$ObjectArray$(
                                  this.get$$updateLabel(),
                                  this.get$$accessPolicy(), proxyType,
                                  (int) lengths.get(depth + 1),
                                  this.get$CHUNK_SIZE_LOG2()));
                    }
                }
            }
            return (fabric.lang.arrays.MultiObjectArray) this.$getProxy();
        }
        
        public fabric.lang.Object $initLabels() {
            return (fabric.lang.arrays.MultiObjectArray) this.$getProxy();
        }
        
        public fabric.lang.arrays.intArray getLengths() {
            return this.get$lengths();
        }
        
        public fabric.lang.Object get(fabric.lang.arrays.intArray index) {
            fabric.lang.arrays.ObjectArray traverse = this.get$firstDim();
            for (int i = 0; i < index.get$length() - 1; i++) {
                traverse =
                  (fabric.lang.arrays.ObjectArray)
                    fabric.lang.Object._Proxy.$getProxy(
                                                traverse.get((int)
                                                               index.get(i)));
            }
            return traverse.get((int) index.get(index.get$length() - 1));
        }
        
        public fabric.lang.Object set(fabric.lang.arrays.intArray index,
                                      fabric.lang.Object data) {
            fabric.lang.arrays.ObjectArray traverse = this.get$firstDim();
            for (int i = 0; i < index.get$length() - 1; i++) {
                traverse =
                  (fabric.lang.arrays.ObjectArray)
                    fabric.lang.Object._Proxy.$getProxy(
                                                traverse.get((int)
                                                               index.get(i)));
            }
            return traverse.set((int) index.get(index.get$length() - 1), data);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.arrays.MultiObjectArray._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeInt(this.dims);
            $writeRef($getStore(), this.lengths, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeRef($getStore(), this.firstDim, refTypes, out, intraStoreRefs,
                      interStoreRefs);
            $writeInline(out, this.proxyType);
            out.writeInt(this.CHUNK_SIZE_LOG2);
        }
        
        public _Impl(fabric.worker.Store store, long onum, int version,
                     fabric.worker.metrics.ImmutableObjectSet associates,
                     long expiry, fabric.worker.Store labelStore,
                     long labelOnum, fabric.worker.Store accessPolicyStore,
                     long accessPolicyOnum, java.io.ObjectInput in,
                     java.util.Iterator refTypes,
                     java.util.Iterator intraStoreRefs,
                     java.util.Iterator interStoreRefs)
              throws java.io.IOException,
            java.lang.ClassNotFoundException {
            super(store, onum, version, associates, expiry, labelStore,
                  labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
                  intraStoreRefs, interStoreRefs);
            this.dims = in.readInt();
            this.lengths = (fabric.lang.arrays.intArray)
                             $readRef(fabric.lang.arrays.intArray._Proxy.class,
                                      (fabric.common.RefTypeEnum)
                                        refTypes.next(), in, store,
                                      intraStoreRefs, interStoreRefs);
            this.firstDim =
              (fabric.lang.arrays.ObjectArray)
                $readRef(fabric.lang.arrays.ObjectArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
            this.proxyType = (java.lang.Class) in.readObject();
            this.CHUNK_SIZE_LOG2 = in.readInt();
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.arrays.MultiObjectArray._Impl src =
              (fabric.lang.arrays.MultiObjectArray._Impl) other;
            this.dims = src.dims;
            this.lengths = src.lengths;
            this.firstDim = src.firstDim;
            this.proxyType = src.proxyType;
            this.CHUNK_SIZE_LOG2 = src.CHUNK_SIZE_LOG2;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.arrays.MultiObjectArray._Static {
            public _Proxy(fabric.lang.arrays.MultiObjectArray._Static.
                            _Impl impl) { super(impl); }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.arrays.MultiObjectArray._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  arrays.
                  MultiObjectArray.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.arrays.MultiObjectArray._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.arrays.MultiObjectArray._Static._Impl.class);
                $instance = (fabric.lang.arrays.MultiObjectArray._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.arrays.MultiObjectArray._Static {
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store, long onum, int version,
                         fabric.worker.metrics.ImmutableObjectSet associates,
                         long expiry, fabric.worker.Store labelStore,
                         long labelOnum, fabric.worker.Store accessPolicyStore,
                         long accessPolicyOnum, java.io.ObjectInput in,
                         java.util.Iterator refTypes,
                         java.util.Iterator intraStoreRefs,
                         java.util.Iterator interStoreRefs)
                  throws java.io.IOException,
                java.lang.ClassNotFoundException {
                super(store, onum, version, associates, expiry, labelStore,
                      labelOnum, accessPolicyStore, accessPolicyOnum, in,
                      refTypes, intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.arrays.MultiObjectArray._Static._Proxy(
                         this);
            }
            
            private void $init() {  }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 32, -120, 86, 57, -105,
    -69, 67, 37, 0, 56, -92, -17, 91, -118, 18, 16, 115, -78, -28, 24, 126, 52,
    49, 1, 44, 35, 38, 3, -114, -25, 75, 30 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
