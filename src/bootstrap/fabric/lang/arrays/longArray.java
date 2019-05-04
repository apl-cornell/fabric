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
                        long $backoff77 = 1;
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
                                    if ($backoff77 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff77 =
                                          java.lang.Math.
                                            min(
                                              $backoff77 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff78 = $backoff77 <= 32 ||
                                                 !$doBackoff78;
                            }
                            $commit71 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.lang.arrays.longArray._Static._Proxy.
                                  $instance.
                                  set$DEFAULT_VALUE((long) 0L);
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
                                $retry72 = false;
                                if ($tm76.inNestedTxn()) {
                                    $keepReads73 = true;
                                }
                                throw $e74;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid75 =
                                  $tm76.getCurrentTid();
                                if ($commit71) {
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
                                } else {
                                    if (!$tm76.inNestedTxn() &&
                                          $tm76.checkForStaleObjects()) {
                                        $retry72 = true;
                                        $keepReads73 = false;
                                    }
                                    if ($keepReads73) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e74) {
                                            $currentTid75 = $tm76.getCurrentTid();
                                            if ($currentTid75 != null &&
                                                  ($e74.tid.equals($currentTid75) || !$e74.tid.isDescendantOf($currentTid75))) {
                                                throw $e74;
                                            } else {
                                                $retry72 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
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
    
    public static final byte[] $classHash = new byte[] { 55, -76, -106, 83, -22,
    102, 71, 95, -26, 18, -125, -59, -123, -94, -4, 110, -27, 4, 56, -29, 41,
    15, 114, 105, 24, 18, -125, -96, 42, 33, 112, 81 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
