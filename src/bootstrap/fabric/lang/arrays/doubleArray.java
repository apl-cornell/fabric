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
    
    public static final byte[] $classHash = new byte[] { -9, 30, 14, -38, 62,
    18, -37, -67, 41, -75, 84, -71, 74, -9, 103, -65, -3, -76, 1, -44, -59, 34,
    -85, -115, 114, 82, 58, -84, -75, 11, -107, 6 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
