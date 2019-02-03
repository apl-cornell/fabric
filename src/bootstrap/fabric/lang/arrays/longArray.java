package fabric.lang.arrays;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Store;
import fabric.lang.arrays.internal._longArray;
import fabric.lang.arrays.internal._ObjectArray;

/**
 * <p>
 * This class implements a resizable array using
 * fabric.lang.arrays.internal._longArray as a primitive. The smaller array
 * pieces are arranged as a tree so as to support efficient indexing operations.
 * </p>
 * <p>
 * This is an array for longs, and is adapted from byteArray.
 * </p>
 * <p>
 * Optimizations:
 * <ol>
 * <li>Remove the call to getProxy in the generated Java file</li>
 * </ol>
 * </p>
 * XXX For simplicity the long arrays are also of CHUNK_SIZE size
 * 
 * @author kvikram
 */
public interface longArray extends fabric.lang.Object {
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
    public fabric.lang.arrays.longArray fabric$lang$arrays$longArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length);
    
    public fabric.lang.arrays.longArray fabric$lang$arrays$longArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length,
      int CHUNK_SIZE_LOG2);
    
    public fabric.lang.Object $initLabels();
    
    public int getLength();
    
    public void setLength(int newSize);
    
    public long get(int i);
    
    public long set(int i, long data);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.arrays.longArray {
        public int get$CHUNK_SIZE() {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).
              get$CHUNK_SIZE();
        }
        
        public int set$CHUNK_SIZE(int val) {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).
              set$CHUNK_SIZE(val);
        }
        
        public int postInc$CHUNK_SIZE() {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).
              postInc$CHUNK_SIZE();
        }
        
        public int postDec$CHUNK_SIZE() {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).
              postDec$CHUNK_SIZE();
        }
        
        public int get$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).
              get$CHUNK_SIZE_LOG2();
        }
        
        public int set$CHUNK_SIZE_LOG2(int val) {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).
              set$CHUNK_SIZE_LOG2(val);
        }
        
        public int postInc$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).
              postInc$CHUNK_SIZE_LOG2();
        }
        
        public int postDec$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).
              postDec$CHUNK_SIZE_LOG2();
        }
        
        public int get$height() {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).get$height();
        }
        
        public int set$height(int val) {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).set$height(
                                                                    val);
        }
        
        public int postInc$height() {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).
              postInc$height();
        }
        
        public int postDec$height() {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).
              postDec$height();
        }
        
        public int get$length() {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).get$length();
        }
        
        public int set$length(int val) {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).set$length(
                                                                    val);
        }
        
        public int postInc$length() {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).
              postInc$length();
        }
        
        public int postDec$length() {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).
              postDec$length();
        }
        
        public fabric.lang.Object get$root() {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).get$root();
        }
        
        public fabric.lang.Object set$root(fabric.lang.Object val) {
            return ((fabric.lang.arrays.longArray._Impl) fetch()).set$root(val);
        }
        
        public fabric.lang.arrays.longArray fabric$lang$arrays$longArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3) {
            return ((fabric.lang.arrays.longArray) fetch()).
              fabric$lang$arrays$longArray$(arg1, arg2, arg3);
        }
        
        public fabric.lang.arrays.longArray fabric$lang$arrays$longArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3, int arg4) {
            return ((fabric.lang.arrays.longArray) fetch()).
              fabric$lang$arrays$longArray$(arg1, arg2, arg3, arg4);
        }
        
        public int getLength() {
            return ((fabric.lang.arrays.longArray) fetch()).getLength();
        }
        
        public void setLength(int arg1) {
            ((fabric.lang.arrays.longArray) fetch()).setLength(arg1);
        }
        
        public long get(int arg1) {
            return ((fabric.lang.arrays.longArray) fetch()).get(arg1);
        }
        
        public long set(int arg1, long arg2) {
            return ((fabric.lang.arrays.longArray) fetch()).set(arg1, arg2);
        }
        
        public _Proxy(longArray._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.arrays.longArray {
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
        public fabric.lang.arrays.longArray fabric$lang$arrays$longArray$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy, int length) {
            fabric$lang$arrays$longArray$(updateLabel, accessPolicy, length, 8);
            return (fabric.lang.arrays.longArray) this.$getProxy();
        }
        
        public fabric.lang.arrays.longArray fabric$lang$arrays$longArray$(
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
                              ((fabric.lang.arrays.longArray._Impl)
                                 this.fetch()).getHeight(length));
            switch (this.get$height()) {
                case 1:
                    this.
                      set$root(
                        (fabric.lang.arrays.internal._longArray)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.lang.arrays.internal._longArray)
                               new fabric.lang.arrays.internal._longArray._Impl(
                                 this.$getStore()).$getProxy()).
                                fabric$lang$arrays$internal$_longArray$(
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
                                  fabric.lang.arrays.internal._longArray.class,
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
            return (fabric.lang.arrays.longArray) this.$getProxy();
        }
        
        public fabric.lang.Object $initLabels() {
            return (fabric.lang.arrays.longArray) this.$getProxy();
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
                (fabric.lang.arrays.internal._longArray)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.lang.arrays.internal._longArray)
                       new fabric.lang.arrays.internal._longArray._Impl(
                         this.$getStore()).$getProxy()).
                        fabric$lang$arrays$internal$_longArray$(
                          this.get$$updateLabel(), this.get$$accessPolicy(),
                          this.get$CHUNK_SIZE())));
        }
        
        public void setLength(int newSize) {
            if (newSize < 0) throw new java.lang.NegativeArraySizeException();
            if (newSize == 0)
                ((fabric.lang.arrays.longArray._Impl) this.fetch()).
                  setZeroLength();
            int newHeight = ((fabric.lang.arrays.longArray._Impl)
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
                                fabric.lang.arrays.internal._longArray.class,
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
                    fabric.lang.arrays.internal._longArray
                      rootArray =
                      (fabric.lang.arrays.internal._longArray)
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
        
        public long get(int i) {
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
                    return fabric.lang.arrays.longArray._Static._Proxy.$instance.
                      get$DEFAULT_VALUE();
                }
                i = i & (1 << counter) - 1;
                counter -= c;
                level--;
            }
            return ((fabric.lang.arrays.internal._longArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).get(i);
        }
        
        public long set(int i, long data) {
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
                                        fabric.lang.arrays.internal._longArray.class,
                                        this.get$CHUNK_SIZE()));
                            break;
                        case 2:
                            nextObject =
                              (fabric.lang.arrays.internal._longArray)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  ((fabric.lang.arrays.internal._longArray)
                                     new fabric.lang.arrays.internal._longArray.
                                       _Impl(this.$getStore()).
                                     $getProxy()).
                                      fabric$lang$arrays$internal$_longArray$(
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
            return ((fabric.lang.arrays.internal._longArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).set(i, data);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.arrays.longArray._Proxy(this);
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
            fabric.lang.arrays.longArray._Impl src =
              (fabric.lang.arrays.longArray._Impl) other;
            this.CHUNK_SIZE = src.CHUNK_SIZE;
            this.CHUNK_SIZE_LOG2 = src.CHUNK_SIZE_LOG2;
            this.height = src.height;
            this.length = src.length;
            this.root = src.root;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public long get$DEFAULT_VALUE();
        
        public long set$DEFAULT_VALUE(long val);
        
        public long postInc$DEFAULT_VALUE();
        
        public long postDec$DEFAULT_VALUE();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.arrays.longArray._Static {
            public long get$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.longArray._Static._Impl) fetch()).
                  get$DEFAULT_VALUE();
            }
            
            public long set$DEFAULT_VALUE(long val) {
                return ((fabric.lang.arrays.longArray._Static._Impl) fetch()).
                  set$DEFAULT_VALUE(val);
            }
            
            public long postInc$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.longArray._Static._Impl) fetch()).
                  postInc$DEFAULT_VALUE();
            }
            
            public long postDec$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.longArray._Static._Impl) fetch()).
                  postDec$DEFAULT_VALUE();
            }
            
            public _Proxy(fabric.lang.arrays.longArray._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.arrays.longArray._Static $instance;
            
            static {
                fabric.
                  lang.
                  arrays.
                  longArray.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.arrays.longArray._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.arrays.longArray._Static._Impl.class);
                $instance = (fabric.lang.arrays.longArray._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.arrays.longArray._Static {
            public long get$DEFAULT_VALUE() { return this.DEFAULT_VALUE; }
            
            public long set$DEFAULT_VALUE(long val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DEFAULT_VALUE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public long postInc$DEFAULT_VALUE() {
                long tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((long) (tmp + 1));
                return tmp;
            }
            
            public long postDec$DEFAULT_VALUE() {
                long tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((long) (tmp - 1));
                return tmp;
            }
            
            private long DEFAULT_VALUE;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeLong(this.DEFAULT_VALUE);
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
                this.DEFAULT_VALUE = in.readLong();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.arrays.longArray._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm76 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled79 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff77 = 1;
                        boolean $doBackoff78 = true;
                        boolean $retry72 = true;
                        boolean $keepReads73 = false;
                        $label70: for (boolean $commit71 = false; !$commit71;
                                       ) {
                            if ($backoffEnabled79) {
                                if ($doBackoff78) {
                                    if ($backoff77 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff77));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e74) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff77 < 5000) $backoff77 *= 2;
                                }
                                $doBackoff78 = $backoff77 <= 32 ||
                                                 !$doBackoff78;
                            }
                            $commit71 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.lang.arrays.longArray._Static._Proxy.
                                      $instance.
                                      set$DEFAULT_VALUE((long) 0L);
                                }
                                catch (final fabric.worker.
                                         RetryException $e74) {
                                    throw $e74;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e74) {
                                    throw $e74;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e74) {
                                    throw $e74;
                                }
                                catch (final Throwable $e74) {
                                    $tm76.getCurrentLog().checkRetrySignal();
                                    throw $e74;
                                }
                            }
                            catch (final fabric.worker.RetryException $e74) {
                                $commit71 = false;
                                continue $label70;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e74) {
                                $commit71 = false;
                                $retry72 = false;
                                $keepReads73 = $e74.keepReads;
                                if ($tm76.checkForStaleObjects()) {
                                    $retry72 = true;
                                    $keepReads73 = false;
                                    continue $label70;
                                }
                                fabric.common.TransactionID $currentTid75 =
                                  $tm76.getCurrentTid();
                                if ($e74.tid == null ||
                                      !$e74.tid.isDescendantOf($currentTid75)) {
                                    throw $e74;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e74);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e74) {
                                $commit71 = false;
                                fabric.common.TransactionID $currentTid75 =
                                  $tm76.getCurrentTid();
                                if ($e74.tid.isDescendantOf($currentTid75))
                                    continue $label70;
                                if ($currentTid75.parent != null) {
                                    $retry72 = false;
                                    throw $e74;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e74) {
                                $commit71 = false;
                                if ($tm76.checkForStaleObjects())
                                    continue $label70;
                                $retry72 = false;
                                throw new fabric.worker.AbortException($e74);
                            }
                            finally {
                                if ($commit71) {
                                    fabric.common.TransactionID $currentTid75 =
                                      $tm76.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e74) {
                                        $commit71 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e74) {
                                        $commit71 = false;
                                        $retry72 = false;
                                        $keepReads73 = $e74.keepReads;
                                        if ($tm76.checkForStaleObjects()) {
                                            $retry72 = true;
                                            $keepReads73 = false;
                                            continue $label70;
                                        }
                                        if ($e74.tid ==
                                              null ||
                                              !$e74.tid.isDescendantOf(
                                                          $currentTid75))
                                            throw $e74;
                                        throw new fabric.worker.
                                                UserAbortException($e74);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e74) {
                                        $commit71 = false;
                                        $currentTid75 = $tm76.getCurrentTid();
                                        if ($currentTid75 != null) {
                                            if ($e74.tid.equals(
                                                           $currentTid75) ||
                                                  !$e74.tid.isDescendantOf(
                                                              $currentTid75)) {
                                                throw $e74;
                                            }
                                        }
                                    }
                                } else if ($keepReads73) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit71) {
                                    {  }
                                    if ($retry72) { continue $label70; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -88, 28, -54, -78, 127,
    96, -88, 95, -24, -107, 23, -76, -35, 109, -34, 52, -85, -43, 54, -16, 124,
    -113, 72, 50, 119, -19, -54, 23, 65, 89, 38, 90 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
