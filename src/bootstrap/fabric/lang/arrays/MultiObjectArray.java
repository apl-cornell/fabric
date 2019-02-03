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
    
    public static final byte[] $classHash = new byte[] { 127, -76, 70, -93, 118,
    -128, 70, 84, 30, 41, 89, 114, 124, -40, -62, 8, -77, 79, -98, 82, 44, -48,
    79, -48, -37, -2, 96, -55, -77, 83, 90, 65 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
