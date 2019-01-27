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
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e54) {
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
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e54) {
                                $commit51 = false;
                                if ($tm56.checkForStaleObjects())
                                    continue $label50;
                                fabric.common.TransactionID $currentTid55 =
                                  $tm56.getCurrentTid();
                                if ($e54.tid.isDescendantOf($currentTid55)) {
                                    $retry52 = true;
                                }
                                else if ($currentTid55.parent != null) {
                                    $retry52 = false;
                                    throw $e54;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
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
    
    public static final byte[] $classHash = new byte[] { -51, -43, -101, -29,
    30, -53, 101, -102, -92, 78, 41, -122, 21, 53, 118, 71, 2, 104, -25, 6, 65,
    -47, -125, -126, 49, 29, -86, 51, -121, 1, 112, -56 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
