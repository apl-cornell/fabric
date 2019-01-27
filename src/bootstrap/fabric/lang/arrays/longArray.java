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
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e74) {
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
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e74) {
                                $commit71 = false;
                                if ($tm76.checkForStaleObjects())
                                    continue $label70;
                                fabric.common.TransactionID $currentTid75 =
                                  $tm76.getCurrentTid();
                                if ($e74.tid.isDescendantOf($currentTid75)) {
                                    $retry72 = true;
                                }
                                else if ($currentTid75.parent != null) {
                                    $retry72 = false;
                                    throw $e74;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
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
    
    public static final byte[] $classHash = new byte[] { -61, 113, -120, -6,
    -92, -6, -72, 88, 63, -9, -59, 61, 104, 1, -43, 110, -33, 86, -128, 6, 86,
    34, -103, -50, -66, 51, 55, -92, -78, -60, 94, 120 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
