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
                        long $backoff57 = 1;
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
                                    if ($backoff57 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff57 =
                                          java.lang.Math.
                                            min(
                                              $backoff57 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff58 = $backoff57 <= 32 ||
                                                 !$doBackoff58;
                            }
                            $commit51 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.lang.arrays.floatArray._Static._Proxy.
                                  $instance.
                                  set$DEFAULT_VALUE((float) 0.0F);
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
                                $retry52 = false;
                                if ($tm56.inNestedTxn()) {
                                    $keepReads53 = true;
                                }
                                throw $e54;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid55 =
                                  $tm56.getCurrentTid();
                                if ($commit51) {
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
                                } else {
                                    if (!$tm56.inNestedTxn() &&
                                          $tm56.checkForStaleObjects()) {
                                        $retry52 = true;
                                        $keepReads53 = false;
                                    }
                                    if ($keepReads53) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e54) {
                                            $currentTid55 = $tm56.getCurrentTid();
                                            if ($currentTid55 != null &&
                                                  ($e54.tid.equals($currentTid55) || !$e54.tid.isDescendantOf($currentTid55))) {
                                                throw $e54;
                                            } else {
                                                $retry52 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
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
    
    public static final byte[] $classHash = new byte[] { -10, 52, -92, -4, -124,
    -7, -27, -113, 65, -31, -92, -78, -43, 41, 34, 78, -84, 9, 45, 4, 96, 91,
    -10, 34, -44, -85, -61, 56, 41, -41, 47, -30 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
