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
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e44) {
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
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e44) {
                                $commit41 = false;
                                if ($tm46.checkForStaleObjects())
                                    continue $label40;
                                fabric.common.TransactionID $currentTid45 =
                                  $tm46.getCurrentTid();
                                if ($e44.tid.isDescendantOf($currentTid45)) {
                                    $retry42 = true;
                                }
                                else if ($currentTid45.parent != null) {
                                    $retry42 = false;
                                    throw $e44;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
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
    
    public static final byte[] $classHash = new byte[] { -86, -100, 91, -57,
    -46, 102, 88, 104, 81, 71, -75, -52, -92, -29, -9, -47, 17, -126, 100, -79,
    -7, 14, -12, 56, 24, -67, 45, -123, -93, 38, -48, -117 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
