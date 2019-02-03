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
                        int $backoff47 = 1;
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
                                    if ($backoff47 < 5000) $backoff47 *= 2;
                                }
                                $doBackoff48 = $backoff47 <= 32 ||
                                                 !$doBackoff48;
                            }
                            $commit41 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.lang.arrays.doubleArray._Static.
                                      _Proxy.
                                      $instance.
                                      set$DEFAULT_VALUE((double) 0.0);
                                }
                                catch (final fabric.worker.
                                         RetryException $e44) {
                                    throw $e44;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e44) {
                                    throw $e44;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e44) {
                                    throw $e44;
                                }
                                catch (final Throwable $e44) {
                                    $tm46.getCurrentLog().checkRetrySignal();
                                    throw $e44;
                                }
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
                                if ($tm46.checkForStaleObjects()) {
                                    $retry42 = true;
                                    $keepReads43 = false;
                                    continue $label40;
                                }
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
                                if ($tm46.checkForStaleObjects())
                                    continue $label40;
                                $retry42 = false;
                                throw new fabric.worker.AbortException($e44);
                            }
                            finally {
                                if ($commit41) {
                                    fabric.common.TransactionID $currentTid45 =
                                      $tm46.getCurrentTid();
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
                                        if ($tm46.checkForStaleObjects()) {
                                            $retry42 = true;
                                            $keepReads43 = false;
                                            continue $label40;
                                        }
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
                                } else if ($keepReads43) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
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
    
    public static final byte[] $classHash = new byte[] { -7, -85, -6, -10, 104,
    -33, 127, -11, 17, -102, 107, 23, -59, 0, -20, -27, -91, 108, 68, 23, -118,
    -118, 87, -122, 56, -116, 1, -63, 120, -114, 25, 49 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
