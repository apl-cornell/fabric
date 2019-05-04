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
    
    public static final byte[] $classHash = new byte[] { -120, 57, 110, -123,
    20, 20, -32, 95, -21, -50, 54, 59, -43, -30, -77, -11, -117, -103, -118,
    -51, 76, -69, -10, -1, -74, 25, -21, 46, -103, -123, -35, -78 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
