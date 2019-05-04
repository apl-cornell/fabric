package fabric.lang.arrays;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Store;
import fabric.lang.arrays.internal._doubleArray;
import fabric.lang.arrays.internal._ObjectArray;

/**
 * <p>
 * This class implements a resizable array using
 * fabric.lang.arrays.internal._doubleArray as a primitive. The smaller array
 * pieces are arranged as a tree so as to support efficient indexing operations.
 * </p>
 * <p>
 * This is an array for doubles, and is adapted from byteArray.
 * </p>
 * <p>
 * Optimizations:
 * <ol>
 * <li>Remove the call to getProxy in the generated Java file</li>
 * </ol>
 * </p>
 * XXX For simplicity the double arrays are also of CHUNK_SIZE size
 * 
 * @author kvikram
 */
public interface doubleArray extends fabric.lang.Object {
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
    public fabric.lang.arrays.doubleArray fabric$lang$arrays$doubleArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length);
    
    public fabric.lang.arrays.doubleArray fabric$lang$arrays$doubleArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length,
      int CHUNK_SIZE_LOG2);
    
    public fabric.lang.Object $initLabels();
    
    public int getLength();
    
    public void setLength(int newSize);
    
    public double get(int i);
    
    public double set(int i, double data);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.arrays.doubleArray {
        public int get$CHUNK_SIZE() {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).
              get$CHUNK_SIZE();
        }
        
        public int set$CHUNK_SIZE(int val) {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).
              set$CHUNK_SIZE(val);
        }
        
        public int postInc$CHUNK_SIZE() {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).
              postInc$CHUNK_SIZE();
        }
        
        public int postDec$CHUNK_SIZE() {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).
              postDec$CHUNK_SIZE();
        }
        
        public int get$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).
              get$CHUNK_SIZE_LOG2();
        }
        
        public int set$CHUNK_SIZE_LOG2(int val) {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).
              set$CHUNK_SIZE_LOG2(val);
        }
        
        public int postInc$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).
              postInc$CHUNK_SIZE_LOG2();
        }
        
        public int postDec$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).
              postDec$CHUNK_SIZE_LOG2();
        }
        
        public int get$height() {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).get$height(
                                                                      );
        }
        
        public int set$height(int val) {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).set$height(
                                                                      val);
        }
        
        public int postInc$height() {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).
              postInc$height();
        }
        
        public int postDec$height() {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).
              postDec$height();
        }
        
        public int get$length() {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).get$length(
                                                                      );
        }
        
        public int set$length(int val) {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).set$length(
                                                                      val);
        }
        
        public int postInc$length() {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).
              postInc$length();
        }
        
        public int postDec$length() {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).
              postDec$length();
        }
        
        public fabric.lang.Object get$root() {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).get$root();
        }
        
        public fabric.lang.Object set$root(fabric.lang.Object val) {
            return ((fabric.lang.arrays.doubleArray._Impl) fetch()).set$root(
                                                                      val);
        }
        
        public fabric.lang.arrays.doubleArray fabric$lang$arrays$doubleArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3) {
            return ((fabric.lang.arrays.doubleArray) fetch()).
              fabric$lang$arrays$doubleArray$(arg1, arg2, arg3);
        }
        
        public fabric.lang.arrays.doubleArray fabric$lang$arrays$doubleArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3, int arg4) {
            return ((fabric.lang.arrays.doubleArray) fetch()).
              fabric$lang$arrays$doubleArray$(arg1, arg2, arg3, arg4);
        }
        
        public int getLength() {
            return ((fabric.lang.arrays.doubleArray) fetch()).getLength();
        }
        
        public void setLength(int arg1) {
            ((fabric.lang.arrays.doubleArray) fetch()).setLength(arg1);
        }
        
        public double get(int arg1) {
            return ((fabric.lang.arrays.doubleArray) fetch()).get(arg1);
        }
        
        public double set(int arg1, double arg2) {
            return ((fabric.lang.arrays.doubleArray) fetch()).set(arg1, arg2);
        }
        
        public _Proxy(doubleArray._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.arrays.doubleArray {
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
        public fabric.lang.arrays.doubleArray fabric$lang$arrays$doubleArray$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy, int length) {
            fabric$lang$arrays$doubleArray$(updateLabel, accessPolicy, length,
                                            8);
            return (fabric.lang.arrays.doubleArray) this.$getProxy();
        }
        
        public fabric.lang.arrays.doubleArray fabric$lang$arrays$doubleArray$(
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
                              ((fabric.lang.arrays.doubleArray._Impl)
                                 this.fetch()).getHeight(length));
            switch (this.get$height()) {
                case 1:
                    this.
                      set$root(
                        (fabric.lang.arrays.internal._doubleArray)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.lang.arrays.internal._doubleArray)
                               new fabric.lang.arrays.internal._doubleArray.
                                 _Impl(this.$getStore()).
                               $getProxy()).
                                fabric$lang$arrays$internal$_doubleArray$(
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
                                  fabric.lang.arrays.internal._doubleArray.class,
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
            return (fabric.lang.arrays.doubleArray) this.$getProxy();
        }
        
        public fabric.lang.Object $initLabels() {
            return (fabric.lang.arrays.doubleArray) this.$getProxy();
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
                (fabric.lang.arrays.internal._doubleArray)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.lang.arrays.internal._doubleArray)
                       new fabric.lang.arrays.internal._doubleArray._Impl(
                         this.$getStore()).$getProxy()).
                        fabric$lang$arrays$internal$_doubleArray$(
                          this.get$$updateLabel(), this.get$$accessPolicy(),
                          this.get$CHUNK_SIZE())));
        }
        
        public void setLength(int newSize) {
            if (newSize < 0) throw new java.lang.NegativeArraySizeException();
            if (newSize == 0)
                ((fabric.lang.arrays.doubleArray._Impl) this.fetch()).
                  setZeroLength();
            int newHeight = ((fabric.lang.arrays.doubleArray._Impl)
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
                                fabric.lang.arrays.internal._doubleArray.class,
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
                    fabric.lang.arrays.internal._doubleArray
                      rootArray =
                      (fabric.lang.arrays.internal._doubleArray)
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
        
        public double get(int i) {
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
                    return fabric.lang.arrays.doubleArray._Static._Proxy.$instance.
                      get$DEFAULT_VALUE();
                }
                i = i & (1 << counter) - 1;
                counter -= c;
                level--;
            }
            return ((fabric.lang.arrays.internal._doubleArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).get(i);
        }
        
        public double set(int i, double data) {
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
                                        fabric.lang.arrays.internal._doubleArray.class,
                                        this.get$CHUNK_SIZE()));
                            break;
                        case 2:
                            nextObject =
                              (fabric.lang.arrays.internal._doubleArray)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  ((fabric.lang.arrays.internal._doubleArray)
                                     new fabric.lang.arrays.internal.
                                       _doubleArray._Impl(this.$getStore()).
                                     $getProxy()).
                                      fabric$lang$arrays$internal$_doubleArray$(
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
            return ((fabric.lang.arrays.internal._doubleArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).set(i, data);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.arrays.doubleArray._Proxy(this);
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
            fabric.lang.arrays.doubleArray._Impl src =
              (fabric.lang.arrays.doubleArray._Impl) other;
            this.CHUNK_SIZE = src.CHUNK_SIZE;
            this.CHUNK_SIZE_LOG2 = src.CHUNK_SIZE_LOG2;
            this.height = src.height;
            this.length = src.length;
            this.root = src.root;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public double get$DEFAULT_VALUE();
        
        public double set$DEFAULT_VALUE(double val);
        
        public double postInc$DEFAULT_VALUE();
        
        public double postDec$DEFAULT_VALUE();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.arrays.doubleArray._Static {
            public double get$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.doubleArray._Static._Impl) fetch()).
                  get$DEFAULT_VALUE();
            }
            
            public double set$DEFAULT_VALUE(double val) {
                return ((fabric.lang.arrays.doubleArray._Static._Impl) fetch()).
                  set$DEFAULT_VALUE(val);
            }
            
            public double postInc$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.doubleArray._Static._Impl) fetch()).
                  postInc$DEFAULT_VALUE();
            }
            
            public double postDec$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.doubleArray._Static._Impl) fetch()).
                  postDec$DEFAULT_VALUE();
            }
            
            public _Proxy(fabric.lang.arrays.doubleArray._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.arrays.doubleArray._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  arrays.
                  doubleArray.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.arrays.doubleArray._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.arrays.doubleArray._Static._Impl.class);
                $instance = (fabric.lang.arrays.doubleArray._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.arrays.doubleArray._Static {
            public double get$DEFAULT_VALUE() { return this.DEFAULT_VALUE; }
            
            public double set$DEFAULT_VALUE(double val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DEFAULT_VALUE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public double postInc$DEFAULT_VALUE() {
                double tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((double) (tmp + 1));
                return tmp;
            }
            
            public double postDec$DEFAULT_VALUE() {
                double tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((double) (tmp - 1));
                return tmp;
            }
            
            private double DEFAULT_VALUE;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeDouble(this.DEFAULT_VALUE);
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
                this.DEFAULT_VALUE = in.readDouble();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.arrays.doubleArray._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm46 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled49 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff47 = 1;
                        boolean $doBackoff48 = true;
                        boolean $retry42 = true;
                        boolean $keepReads43 = false;
                        $label40: for (boolean $commit41 = false; !$commit41;
                                       ) {
                            if ($backoffEnabled49) {
                                if ($doBackoff48) {
                                    if ($backoff47 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff47));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e44) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff47 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff47 =
                                          java.lang.Math.
                                            min(
                                              $backoff47 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff48 = $backoff47 <= 32 ||
                                                 !$doBackoff48;
                            }
                            $commit41 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.lang.arrays.doubleArray._Static._Proxy.
                                  $instance.
                                  set$DEFAULT_VALUE((double) 0.0);
                            }
                            catch (final fabric.worker.RetryException $e44) {
                                $commit41 = false;
                                continue $label40;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e44) {
                                $commit41 = false;
                                $retry42 = false;
                                $keepReads43 = $e44.keepReads;
                                fabric.common.TransactionID $currentTid45 =
                                  $tm46.getCurrentTid();
                                if ($e44.tid == null ||
                                      !$e44.tid.isDescendantOf($currentTid45)) {
                                    throw $e44;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e44);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e44) {
                                $commit41 = false;
                                fabric.common.TransactionID $currentTid45 =
                                  $tm46.getCurrentTid();
                                if ($e44.tid.isDescendantOf($currentTid45))
                                    continue $label40;
                                if ($currentTid45.parent != null) {
                                    $retry42 = false;
                                    throw $e44;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e44) {
                                $commit41 = false;
                                $retry42 = false;
                                if ($tm46.inNestedTxn()) {
                                    $keepReads43 = true;
                                }
                                throw $e44;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid45 =
                                  $tm46.getCurrentTid();
                                if ($commit41) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e44) {
                                        $commit41 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e44) {
                                        $commit41 = false;
                                        $retry42 = false;
                                        $keepReads43 = $e44.keepReads;
                                        if ($e44.tid ==
                                              null ||
                                              !$e44.tid.isDescendantOf(
                                                          $currentTid45))
                                            throw $e44;
                                        throw new fabric.worker.
                                                UserAbortException($e44);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e44) {
                                        $commit41 = false;
                                        $currentTid45 = $tm46.getCurrentTid();
                                        if ($currentTid45 != null) {
                                            if ($e44.tid.equals(
                                                           $currentTid45) ||
                                                  !$e44.tid.isDescendantOf(
                                                              $currentTid45)) {
                                                throw $e44;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm46.inNestedTxn() &&
                                          $tm46.checkForStaleObjects()) {
                                        $retry42 = true;
                                        $keepReads43 = false;
                                    }
                                    if ($keepReads43) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e44) {
                                            $currentTid45 = $tm46.getCurrentTid();
                                            if ($currentTid45 != null &&
                                                  ($e44.tid.equals($currentTid45) || !$e44.tid.isDescendantOf($currentTid45))) {
                                                throw $e44;
                                            } else {
                                                $retry42 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit41) {
                                    {  }
                                    if ($retry42) { continue $label40; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 16, 109, -98, -26, 24,
    100, -51, 19, -111, -14, -53, -102, -24, 46, 58, 48, 70, -111, 84, -103,
    -93, -19, -46, 9, -128, -76, 80, -118, 126, -105, 49, 91 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
