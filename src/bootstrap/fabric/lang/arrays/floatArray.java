package fabric.lang.arrays;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Store;
import fabric.lang.arrays.internal._floatArray;
import fabric.lang.arrays.internal._ObjectArray;

/**
 * <p>
 * This class implements a resizable array using
 * fabric.lang.arrays.internal._floatArray as a primitive. The smaller array
 * pieces are arranged as a tree so as to support efficient indexing operations.
 * </p>
 * <p>
 * This is an array for floats, and is adapted from byteArray.
 * </p>
 * <p>
 * Optimizations:
 * <ol>
 * <li>Remove the call to getProxy in the generated Java file</li>
 * </ol>
 * </p>
 * XXX For simplicity the float arrays are also of CHUNK_SIZE size
 * 
 * @author kvikram
 */
public interface floatArray extends fabric.lang.Object {
    public int get$CHUNK_SIZE();
    
    public int set$CHUNK_SIZE(int val);
    
    public int postInc$CHUNK_SIZE();
    
    public int postDec$CHUNK_SIZE();
    
    public int get$CHUNK_SIZE_LOG2();
    
    public int set$CHUNK_SIZE_LOG2(int val);
    
    public int postInc$CHUNK_SIZE_LOG2();
    
    public int postDec$CHUNK_SIZE_LOG2();
    
    public int get$height();
    
    public int set$height(int val);
    
    public int postInc$height();
    
    public int postDec$height();
    
    public int get$length();
    
    public int set$length(int val);
    
    public int postInc$length();
    
    public int postDec$length();
    
    public fabric.lang.Object get$root();
    
    public fabric.lang.Object set$root(fabric.lang.Object val);
    
    /**
   * Creates a new object array at the given Store with the given length.
   * 
   * @param store
   *                The store on which to allocate the array.
   * @param length
   *                The length of the array.
   */
    public fabric.lang.arrays.floatArray fabric$lang$arrays$floatArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length);
    
    public fabric.lang.arrays.floatArray fabric$lang$arrays$floatArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length,
      int CHUNK_SIZE_LOG2);
    
    public fabric.lang.Object $initLabels();
    
    public int getLength();
    
    public void setLength(int newSize);
    
    public float get(int i);
    
    public float set(int i, float data);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.arrays.floatArray {
        public int get$CHUNK_SIZE() {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).
              get$CHUNK_SIZE();
        }
        
        public int set$CHUNK_SIZE(int val) {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).
              set$CHUNK_SIZE(val);
        }
        
        public int postInc$CHUNK_SIZE() {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).
              postInc$CHUNK_SIZE();
        }
        
        public int postDec$CHUNK_SIZE() {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).
              postDec$CHUNK_SIZE();
        }
        
        public int get$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).
              get$CHUNK_SIZE_LOG2();
        }
        
        public int set$CHUNK_SIZE_LOG2(int val) {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).
              set$CHUNK_SIZE_LOG2(val);
        }
        
        public int postInc$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).
              postInc$CHUNK_SIZE_LOG2();
        }
        
        public int postDec$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).
              postDec$CHUNK_SIZE_LOG2();
        }
        
        public int get$height() {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).get$height();
        }
        
        public int set$height(int val) {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).set$height(
                                                                     val);
        }
        
        public int postInc$height() {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).
              postInc$height();
        }
        
        public int postDec$height() {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).
              postDec$height();
        }
        
        public int get$length() {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).get$length();
        }
        
        public int set$length(int val) {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).set$length(
                                                                     val);
        }
        
        public int postInc$length() {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).
              postInc$length();
        }
        
        public int postDec$length() {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).
              postDec$length();
        }
        
        public fabric.lang.Object get$root() {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).get$root();
        }
        
        public fabric.lang.Object set$root(fabric.lang.Object val) {
            return ((fabric.lang.arrays.floatArray._Impl) fetch()).set$root(
                                                                     val);
        }
        
        public fabric.lang.arrays.floatArray fabric$lang$arrays$floatArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3) {
            return ((fabric.lang.arrays.floatArray) fetch()).
              fabric$lang$arrays$floatArray$(arg1, arg2, arg3);
        }
        
        public fabric.lang.arrays.floatArray fabric$lang$arrays$floatArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3, int arg4) {
            return ((fabric.lang.arrays.floatArray) fetch()).
              fabric$lang$arrays$floatArray$(arg1, arg2, arg3, arg4);
        }
        
        public int getLength() {
            return ((fabric.lang.arrays.floatArray) fetch()).getLength();
        }
        
        public void setLength(int arg1) {
            ((fabric.lang.arrays.floatArray) fetch()).setLength(arg1);
        }
        
        public float get(int arg1) {
            return ((fabric.lang.arrays.floatArray) fetch()).get(arg1);
        }
        
        public float set(int arg1, float arg2) {
            return ((fabric.lang.arrays.floatArray) fetch()).set(arg1, arg2);
        }
        
        public _Proxy(floatArray._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.arrays.floatArray {
        public int get$CHUNK_SIZE() { return this.CHUNK_SIZE; }
        
        public int set$CHUNK_SIZE(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.CHUNK_SIZE = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$CHUNK_SIZE() {
            int tmp = this.get$CHUNK_SIZE();
            this.set$CHUNK_SIZE((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$CHUNK_SIZE() {
            int tmp = this.get$CHUNK_SIZE();
            this.set$CHUNK_SIZE((int) (tmp - 1));
            return tmp;
        }
        
        private int CHUNK_SIZE;
        
        public int get$CHUNK_SIZE_LOG2() { return this.CHUNK_SIZE_LOG2; }
        
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
        
        public int get$height() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.height;
        }
        
        public int set$height(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.height = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$height() {
            int tmp = this.get$height();
            this.set$height((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$height() {
            int tmp = this.get$height();
            this.set$height((int) (tmp - 1));
            return tmp;
        }
        
        /**
   * The height of the tree of little arrays. Depends on the chunk size
   * (determining the branching factor) and the number of expected elements in
   * the bigger array
   */
        private int height;
        
        public int get$length() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.length;
        }
        
        public int set$length(int val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.length = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        public int postInc$length() {
            int tmp = this.get$length();
            this.set$length((int) (tmp + 1));
            return tmp;
        }
        
        public int postDec$length() {
            int tmp = this.get$length();
            this.set$length((int) (tmp - 1));
            return tmp;
        }
        
        /**
   * The number of expected elements in this big array Can be modified even
   * after an instance has been created
   */
        private int length;
        
        public fabric.lang.Object get$root() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.root;
        }
        
        public fabric.lang.Object set$root(fabric.lang.Object val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.root = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        /**
   * The root of the tree of little arrays. The runtime type of root is a Fabric
   * array of Fabric Objects. Each object in the array is either a further array
   * of objects or is an array element if this array is at the leaf level
   */
        private fabric.lang.Object root;
        
        /**
   * Creates a new object array at the given Store with the given length.
   * 
   * @param store
   *                The store on which to allocate the array.
   * @param length
   *                The length of the array.
   */
        public fabric.lang.arrays.floatArray fabric$lang$arrays$floatArray$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy, int length) {
            fabric$lang$arrays$floatArray$(updateLabel, accessPolicy, length,
                                           8);
            return (fabric.lang.arrays.floatArray) this.$getProxy();
        }
        
        public fabric.lang.arrays.floatArray fabric$lang$arrays$floatArray$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy, int length,
          int CHUNK_SIZE_LOG2) {
            this.set$CHUNK_SIZE_LOG2((int) CHUNK_SIZE_LOG2);
            this.set$CHUNK_SIZE((int) (1 << CHUNK_SIZE_LOG2));
            this.set$$updateLabel(updateLabel);
            this.set$$accessPolicy(accessPolicy);
            fabric$lang$Object$();
            this.set$length((int) length);
            this.set$height((int)
                              ((fabric.lang.arrays.floatArray._Impl)
                                 this.fetch()).getHeight(length));
            switch (this.get$height()) {
                case 1:
                    this.
                      set$root(
                        (fabric.lang.arrays.internal._floatArray)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.lang.arrays.internal._floatArray)
                               new fabric.lang.arrays.internal._floatArray.
                                 _Impl(this.$getStore()).
                               $getProxy()).
                                fabric$lang$arrays$internal$_floatArray$(
                                  this.get$$updateLabel(),
                                  this.get$$accessPolicy(),
                                  this.get$CHUNK_SIZE())));
                    break;
                case 2:
                    this.
                      set$root(
                        (fabric.lang.arrays.internal._ObjectArray)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.lang.arrays.internal._ObjectArray)
                               new fabric.lang.arrays.internal._ObjectArray.
                                 _Impl(this.$getStore()).
                               $getProxy()).
                                fabric$lang$arrays$internal$_ObjectArray$(
                                  this.get$$updateLabel(),
                                  this.get$$accessPolicy(),
                                  fabric.lang.arrays.internal._floatArray.class,
                                  this.get$CHUNK_SIZE())));
                    break;
                default:
                    this.
                      set$root(
                        (fabric.lang.arrays.internal._ObjectArray)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.lang.arrays.internal._ObjectArray)
                               new fabric.lang.arrays.internal._ObjectArray.
                                 _Impl(this.$getStore()).
                               $getProxy()).
                                fabric$lang$arrays$internal$_ObjectArray$(
                                  this.get$$updateLabel(),
                                  this.get$$accessPolicy(),
                                  fabric.lang.arrays.internal._ObjectArray.class,
                                  this.get$CHUNK_SIZE())));
            }
            return (fabric.lang.arrays.floatArray) this.$getProxy();
        }
        
        public fabric.lang.Object $initLabels() {
            return (fabric.lang.arrays.floatArray) this.$getProxy();
        }
        
        public int getLength() { return this.get$length(); }
        
        /**
   * Ceiling(log_{CHUNK_SIZE}(length)).  (Except returns 1 if length <= 1.)
   * This basically returns the height of the tree for a given array length.
   */
        private int getHeight(int length) {
            if (length <= 1) return 1;
            int result = 0;
            int tmp = 1;
            while (tmp < length) {
                tmp <<= this.get$CHUNK_SIZE_LOG2();
                result++;
            }
            return result;
        }
        
        private void setZeroLength() {
            this.set$length((int) 0);
            this.set$height((int) 1);
            this.
              set$root(
                (fabric.lang.arrays.internal._floatArray)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.lang.arrays.internal._floatArray)
                       new fabric.lang.arrays.internal._floatArray._Impl(
                         this.$getStore()).$getProxy()).
                        fabric$lang$arrays$internal$_floatArray$(
                          this.get$$updateLabel(), this.get$$accessPolicy(),
                          this.get$CHUNK_SIZE())));
        }
        
        public void setLength(int newSize) {
            if (newSize < 0) throw new java.lang.NegativeArraySizeException();
            if (newSize == 0)
                ((fabric.lang.arrays.floatArray._Impl) this.fetch()).
                  setZeroLength();
            int newHeight = ((fabric.lang.arrays.floatArray._Impl)
                               this.fetch()).getHeight(newSize);
            int oldHeight = this.get$height();
            int difference = newHeight - oldHeight;
            this.set$height((int) newHeight);
            this.set$length((int) newSize);
            if (difference > 0) {
                if (oldHeight == 1) {
                    fabric.lang.arrays.internal._ObjectArray
                      newRoot =
                      (fabric.lang.arrays.internal._ObjectArray)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          ((fabric.lang.arrays.internal._ObjectArray)
                             new fabric.lang.arrays.internal._ObjectArray._Impl(
                               this.$getStore()).$getProxy()).
                              fabric$lang$arrays$internal$_ObjectArray$(
                                this.get$$updateLabel(),
                                this.get$$accessPolicy(),
                                fabric.lang.arrays.internal._floatArray.class,
                                this.get$CHUNK_SIZE()));
                    newRoot.set(0, this.get$root());
                    this.set$root(newRoot);
                    difference--;
                }
                for (int count = 0; count < difference; count++) {
                    fabric.lang.arrays.internal._ObjectArray
                      newRoot =
                      (fabric.lang.arrays.internal._ObjectArray)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          ((fabric.lang.arrays.internal._ObjectArray)
                             new fabric.lang.arrays.internal._ObjectArray._Impl(
                               this.$getStore()).$getProxy()).
                              fabric$lang$arrays$internal$_ObjectArray$(
                                this.get$$updateLabel(),
                                this.get$$accessPolicy(),
                                fabric.lang.arrays.internal._ObjectArray.class,
                                this.get$CHUNK_SIZE()));
                    newRoot.set(0, this.get$root());
                    this.set$root(newRoot);
                }
            }
            else if (difference < 0) {
                if (newHeight == 1) difference++;
                for (int count = 0;
                     count < -difference; count++) {
                    fabric.lang.arrays.internal._ObjectArray
                      rootArray =
                      (fabric.lang.arrays.internal._ObjectArray)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          ((fabric.lang.arrays.internal._ObjectArray)
                             fabric.lang.Object._Proxy.$getProxy(
                                                         this.get$root())).
                              get(0));
                    this.set$root(rootArray);
                }
                if (newHeight == 1) {
                    fabric.lang.arrays.internal._floatArray
                      rootArray =
                      (fabric.lang.arrays.internal._floatArray)
                        fabric.lang.Object._Proxy.
                        $getProxy(
                          ((fabric.lang.arrays.internal._ObjectArray)
                             fabric.lang.Object._Proxy.$getProxy(
                                                         this.get$root())).
                              get(0));
                    this.set$root(rootArray);
                }
                if (newHeight != 1) {
                    fabric.lang.arrays.internal._ObjectArray curNode =
                      (fabric.lang.arrays.internal._ObjectArray)
                        fabric.lang.Object._Proxy.$getProxy(this.get$root());
                    int curIndex = newSize - 1;
                    int curHeight = newHeight;
                    int counter = newHeight - 1;
                    int firstDigit;
                    while (curHeight >
                             1 &&
                             !fabric.lang.Object._Proxy.idEquals(curNode,
                                                                 null)) {
                        firstDigit = curIndex >> counter *
                                       this.get$CHUNK_SIZE_LOG2();
                        for (int count = firstDigit + 1;
                             count < this.get$CHUNK_SIZE(); count++) {
                            curNode.set(count, null);
                        }
                        curNode =
                          (fabric.lang.arrays.internal._ObjectArray)
                            fabric.lang.Object._Proxy.$getProxy(
                                                        curNode.get(
                                                                  firstDigit));
                        curIndex = curIndex &
                                     (1 << counter *
                                        this.get$CHUNK_SIZE_LOG2()) -
                                     1;
                        counter -= this.get$CHUNK_SIZE_LOG2();
                        curHeight--;
                    }
                }
            }
        }
        
        public float get(int i) {
            if (i >= this.get$length())
                throw new java.lang.ArrayIndexOutOfBoundsException();
            fabric.lang.Object node = this.get$root();
            int level = this.get$height();
            int c = this.get$CHUNK_SIZE_LOG2();
            int counter = (level - 1) * c;
            int firstDigit;
            while (level > 1) {
                firstDigit = i >> counter;
                node =
                  ((fabric.lang.arrays.internal._ObjectArray)
                     fabric.lang.Object._Proxy.$getProxy(node)).get(firstDigit);
                if (fabric.lang.Object._Proxy.idEquals(node, null)) {
                    return fabric.lang.arrays.floatArray._Static._Proxy.$instance.
                      get$DEFAULT_VALUE();
                }
                i = i & (1 << counter) - 1;
                counter -= c;
                level--;
            }
            return ((fabric.lang.arrays.internal._floatArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).get(i);
        }
        
        public float set(int i, float data) {
            if (i >= this.get$length())
                throw new java.lang.ArrayIndexOutOfBoundsException();
            fabric.lang.Object node = this.get$root();
            int level = this.get$height();
            int c = this.get$CHUNK_SIZE_LOG2();
            int counter = (level - 1) * c;
            int firstDigit;
            fabric.lang.Object nextObject;
            fabric.lang.arrays.internal._ObjectArray oArray;
            while (level > 1) {
                firstDigit = i >> counter;
                oArray = (fabric.lang.arrays.internal._ObjectArray)
                           fabric.lang.Object._Proxy.$getProxy(node);
                nextObject = oArray.get(firstDigit);
                if (fabric.lang.Object._Proxy.idEquals(nextObject, null)) {
                    switch (level) {
                        case 3:
                            nextObject =
                              (fabric.lang.arrays.internal._ObjectArray)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  ((fabric.lang.arrays.internal._ObjectArray)
                                     new fabric.lang.arrays.internal.
                                       _ObjectArray._Impl(this.$getStore()).
                                     $getProxy()).
                                      fabric$lang$arrays$internal$_ObjectArray$(
                                        this.get$$updateLabel(),
                                        this.get$$accessPolicy(),
                                        fabric.lang.arrays.internal._floatArray.class,
                                        this.get$CHUNK_SIZE()));
                            break;
                        case 2:
                            nextObject =
                              (fabric.lang.arrays.internal._floatArray)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  ((fabric.lang.arrays.internal._floatArray)
                                     new fabric.lang.arrays.internal.
                                       _floatArray._Impl(this.$getStore()).
                                     $getProxy()).
                                      fabric$lang$arrays$internal$_floatArray$(
                                        this.get$$updateLabel(),
                                        this.get$$accessPolicy(),
                                        this.get$CHUNK_SIZE()));
                            break;
                        default:
                            nextObject =
                              (fabric.lang.arrays.internal._ObjectArray)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  ((fabric.lang.arrays.internal._ObjectArray)
                                     new fabric.lang.arrays.internal.
                                       _ObjectArray._Impl(this.$getStore()).
                                     $getProxy()).
                                      fabric$lang$arrays$internal$_ObjectArray$(
                                        this.get$$updateLabel(),
                                        this.get$$accessPolicy(),
                                        fabric.lang.arrays.internal._ObjectArray.class,
                                        this.get$CHUNK_SIZE()));
                    }
                    oArray.set(firstDigit, nextObject);
                }
                node = nextObject;
                i = i & (1 << counter) - 1;
                counter -= c;
                level--;
            }
            return ((fabric.lang.arrays.internal._floatArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).set(i, data);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.arrays.floatArray._Proxy(this);
        }
        
        public void $serialize(java.io.ObjectOutput out,
                               java.util.List refTypes,
                               java.util.List intraStoreRefs,
                               java.util.List interStoreRefs)
              throws java.io.IOException {
            super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
            out.writeInt(this.CHUNK_SIZE);
            out.writeInt(this.CHUNK_SIZE_LOG2);
            out.writeInt(this.height);
            out.writeInt(this.length);
            $writeRef($getStore(), this.root, refTypes, out, intraStoreRefs,
                      interStoreRefs);
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
            this.CHUNK_SIZE = in.readInt();
            this.CHUNK_SIZE_LOG2 = in.readInt();
            this.height = in.readInt();
            this.length = in.readInt();
            this.root = (fabric.lang.Object)
                          $readRef(fabric.lang.Object._Proxy.class,
                                   (fabric.common.RefTypeEnum) refTypes.next(),
                                   in, store, intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.arrays.floatArray._Impl src =
              (fabric.lang.arrays.floatArray._Impl) other;
            this.CHUNK_SIZE = src.CHUNK_SIZE;
            this.CHUNK_SIZE_LOG2 = src.CHUNK_SIZE_LOG2;
            this.height = src.height;
            this.length = src.length;
            this.root = src.root;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public float get$DEFAULT_VALUE();
        
        public float set$DEFAULT_VALUE(float val);
        
        public float postInc$DEFAULT_VALUE();
        
        public float postDec$DEFAULT_VALUE();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.arrays.floatArray._Static {
            public float get$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.floatArray._Static._Impl) fetch()).
                  get$DEFAULT_VALUE();
            }
            
            public float set$DEFAULT_VALUE(float val) {
                return ((fabric.lang.arrays.floatArray._Static._Impl) fetch()).
                  set$DEFAULT_VALUE(val);
            }
            
            public float postInc$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.floatArray._Static._Impl) fetch()).
                  postInc$DEFAULT_VALUE();
            }
            
            public float postDec$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.floatArray._Static._Impl) fetch()).
                  postDec$DEFAULT_VALUE();
            }
            
            public _Proxy(fabric.lang.arrays.floatArray._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.arrays.floatArray._Static $instance;
            
            static {
                fabric.
                  lang.
                  arrays.
                  floatArray.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.arrays.floatArray._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.arrays.floatArray._Static._Impl.class);
                $instance = (fabric.lang.arrays.floatArray._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.arrays.floatArray._Static {
            public float get$DEFAULT_VALUE() { return this.DEFAULT_VALUE; }
            
            public float set$DEFAULT_VALUE(float val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DEFAULT_VALUE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public float postInc$DEFAULT_VALUE() {
                float tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((float) (tmp + 1));
                return tmp;
            }
            
            public float postDec$DEFAULT_VALUE() {
                float tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((float) (tmp - 1));
                return tmp;
            }
            
            private float DEFAULT_VALUE;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeFloat(this.DEFAULT_VALUE);
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
                this.DEFAULT_VALUE = in.readFloat();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.arrays.floatArray._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm56 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled59 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff57 = 1;
                        boolean $doBackoff58 = true;
                        boolean $retry52 = true;
                        boolean $keepReads53 = false;
                        $label50: for (boolean $commit51 = false; !$commit51;
                                       ) {
                            if ($backoffEnabled59) {
                                if ($doBackoff58) {
                                    if ($backoff57 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff57));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e54) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff57 < 5000) $backoff57 *= 2;
                                }
                                $doBackoff58 = $backoff57 <= 32 ||
                                                 !$doBackoff58;
                            }
                            $commit51 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.lang.arrays.floatArray._Static.
                                      _Proxy.
                                      $instance.
                                      set$DEFAULT_VALUE((float) 0.0F);
                                }
                                catch (final fabric.worker.
                                         RetryException $e54) {
                                    throw $e54;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e54) {
                                    throw $e54;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e54) {
                                    throw $e54;
                                }
                                catch (final Throwable $e54) {
                                    $tm56.getCurrentLog().checkRetrySignal();
                                    throw $e54;
                                }
                            }
                            catch (final fabric.worker.RetryException $e54) {
                                $commit51 = false;
                                continue $label50;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e54) {
                                $commit51 = false;
                                $retry52 = false;
                                $keepReads53 = $e54.keepReads;
                                if ($tm56.checkForStaleObjects()) {
                                    $retry52 = true;
                                    $keepReads53 = false;
                                    continue $label50;
                                }
                                fabric.common.TransactionID $currentTid55 =
                                  $tm56.getCurrentTid();
                                if ($e54.tid == null ||
                                      !$e54.tid.isDescendantOf($currentTid55)) {
                                    throw $e54;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e54);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e54) {
                                $commit51 = false;
                                fabric.common.TransactionID $currentTid55 =
                                  $tm56.getCurrentTid();
                                if ($e54.tid.isDescendantOf($currentTid55))
                                    continue $label50;
                                if ($currentTid55.parent != null) {
                                    $retry52 = false;
                                    throw $e54;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e54) {
                                $commit51 = false;
                                if ($tm56.checkForStaleObjects())
                                    continue $label50;
                                $retry52 = false;
                                throw new fabric.worker.AbortException($e54);
                            }
                            finally {
                                if ($commit51) {
                                    fabric.common.TransactionID $currentTid55 =
                                      $tm56.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e54) {
                                        $commit51 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e54) {
                                        $commit51 = false;
                                        $retry52 = false;
                                        $keepReads53 = $e54.keepReads;
                                        if ($tm56.checkForStaleObjects()) {
                                            $retry52 = true;
                                            $keepReads53 = false;
                                            continue $label50;
                                        }
                                        if ($e54.tid ==
                                              null ||
                                              !$e54.tid.isDescendantOf(
                                                          $currentTid55))
                                            throw $e54;
                                        throw new fabric.worker.
                                                UserAbortException($e54);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e54) {
                                        $commit51 = false;
                                        $currentTid55 = $tm56.getCurrentTid();
                                        if ($currentTid55 != null) {
                                            if ($e54.tid.equals(
                                                           $currentTid55) ||
                                                  !$e54.tid.isDescendantOf(
                                                              $currentTid55)) {
                                                throw $e54;
                                            }
                                        }
                                    }
                                } else if ($keepReads53) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit51) {
                                    {  }
                                    if ($retry52) { continue $label50; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -30, 79, -23, 94, 56,
    27, -55, -112, 76, -28, 16, -39, 31, 57, -107, -32, 91, -13, -84, -44, 122,
    18, 63, 22, 16, -1, -114, -94, -58, 60, -30, 25 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
