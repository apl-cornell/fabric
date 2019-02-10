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
    
    public static final byte[] $classHash = new byte[] { 42, -119, 61, -127, 32,
    -80, -119, 6, 73, 57, 71, 59, -66, -127, -80, -80, -97, 28, -48, 122, 15,
    103, 118, -112, -38, -83, 114, -49, 111, -7, 119, -102 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
