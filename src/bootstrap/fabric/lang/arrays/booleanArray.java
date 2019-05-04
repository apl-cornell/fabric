package fabric.lang.arrays;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Store;
import fabric.lang.arrays.internal._booleanArray;
import fabric.lang.arrays.internal._ObjectArray;

/**
 * <p>
 * This class implements a resizable array using
 * fabric.lang.arrays.internal._booleanArray as a primitive. The smaller array
 * pieces are arranged as a tree so as to support efficient indexing operations.
 * </p>
 * <p>
 * This is an array for booleans, and is adapted from byteArray.
 * </p>
 * <p>
 * Optimizations:
 * <ol>
 * <li>Remove the call to getProxy in the generated Java file</li>
 * </ol>
 * </p>
 * XXX For simplicity the boolean arrays are also of CHUNK_SIZE size
 * 
 * @author kvikram
 */
public interface booleanArray extends fabric.lang.Object {
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
    public fabric.lang.arrays.booleanArray fabric$lang$arrays$booleanArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length);
    
    public fabric.lang.arrays.booleanArray fabric$lang$arrays$booleanArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length,
      int CHUNK_SIZE_LOG2);
    
    public fabric.lang.Object $initLabels();
    
    public int getLength();
    
    public void setLength(int newSize);
    
    public boolean get(int i);
    
    public boolean set(int i, boolean data);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.arrays.booleanArray {
        public int get$CHUNK_SIZE() {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).
              get$CHUNK_SIZE();
        }
        
        public int set$CHUNK_SIZE(int val) {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).
              set$CHUNK_SIZE(val);
        }
        
        public int postInc$CHUNK_SIZE() {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).
              postInc$CHUNK_SIZE();
        }
        
        public int postDec$CHUNK_SIZE() {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).
              postDec$CHUNK_SIZE();
        }
        
        public int get$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).
              get$CHUNK_SIZE_LOG2();
        }
        
        public int set$CHUNK_SIZE_LOG2(int val) {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).
              set$CHUNK_SIZE_LOG2(val);
        }
        
        public int postInc$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).
              postInc$CHUNK_SIZE_LOG2();
        }
        
        public int postDec$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).
              postDec$CHUNK_SIZE_LOG2();
        }
        
        public int get$height() {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).get$height(
                                                                       );
        }
        
        public int set$height(int val) {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).set$height(
                                                                       val);
        }
        
        public int postInc$height() {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).
              postInc$height();
        }
        
        public int postDec$height() {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).
              postDec$height();
        }
        
        public int get$length() {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).get$length(
                                                                       );
        }
        
        public int set$length(int val) {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).set$length(
                                                                       val);
        }
        
        public int postInc$length() {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).
              postInc$length();
        }
        
        public int postDec$length() {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).
              postDec$length();
        }
        
        public fabric.lang.Object get$root() {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).get$root();
        }
        
        public fabric.lang.Object set$root(fabric.lang.Object val) {
            return ((fabric.lang.arrays.booleanArray._Impl) fetch()).set$root(
                                                                       val);
        }
        
        public fabric.lang.arrays.booleanArray fabric$lang$arrays$booleanArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3) {
            return ((fabric.lang.arrays.booleanArray) fetch()).
              fabric$lang$arrays$booleanArray$(arg1, arg2, arg3);
        }
        
        public fabric.lang.arrays.booleanArray fabric$lang$arrays$booleanArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3, int arg4) {
            return ((fabric.lang.arrays.booleanArray) fetch()).
              fabric$lang$arrays$booleanArray$(arg1, arg2, arg3, arg4);
        }
        
        public int getLength() {
            return ((fabric.lang.arrays.booleanArray) fetch()).getLength();
        }
        
        public void setLength(int arg1) {
            ((fabric.lang.arrays.booleanArray) fetch()).setLength(arg1);
        }
        
        public boolean get(int arg1) {
            return ((fabric.lang.arrays.booleanArray) fetch()).get(arg1);
        }
        
        public boolean set(int arg1, boolean arg2) {
            return ((fabric.lang.arrays.booleanArray) fetch()).set(arg1, arg2);
        }
        
        public _Proxy(booleanArray._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.arrays.booleanArray {
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
        public fabric.lang.arrays.booleanArray fabric$lang$arrays$booleanArray$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy, int length) {
            fabric$lang$arrays$booleanArray$(updateLabel, accessPolicy, length,
                                             8);
            return (fabric.lang.arrays.booleanArray) this.$getProxy();
        }
        
        public fabric.lang.arrays.booleanArray fabric$lang$arrays$booleanArray$(
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
                              ((fabric.lang.arrays.booleanArray._Impl)
                                 this.fetch()).getHeight(length));
            switch (this.get$height()) {
                case 1:
                    this.
                      set$root(
                        (fabric.lang.arrays.internal._booleanArray)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.lang.arrays.internal._booleanArray)
                               new fabric.lang.arrays.internal._booleanArray.
                                 _Impl(this.$getStore()).
                               $getProxy()).
                                fabric$lang$arrays$internal$_booleanArray$(
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
                                  fabric.lang.arrays.internal._booleanArray.class,
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
            return (fabric.lang.arrays.booleanArray) this.$getProxy();
        }
        
        public fabric.lang.Object $initLabels() {
            return (fabric.lang.arrays.booleanArray) this.$getProxy();
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
                (fabric.lang.arrays.internal._booleanArray)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.lang.arrays.internal._booleanArray)
                       new fabric.lang.arrays.internal._booleanArray._Impl(
                         this.$getStore()).$getProxy()).
                        fabric$lang$arrays$internal$_booleanArray$(
                          this.get$$updateLabel(), this.get$$accessPolicy(),
                          this.get$CHUNK_SIZE())));
        }
        
        public void setLength(int newSize) {
            if (newSize < 0) throw new java.lang.NegativeArraySizeException();
            if (newSize == 0)
                ((fabric.lang.arrays.booleanArray._Impl) this.fetch()).
                  setZeroLength();
            int newHeight = ((fabric.lang.arrays.booleanArray._Impl)
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
                                fabric.lang.arrays.internal._booleanArray.class,
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
                    fabric.lang.arrays.internal._booleanArray
                      rootArray =
                      (fabric.lang.arrays.internal._booleanArray)
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
        
        public boolean get(int i) {
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
                    return fabric.lang.arrays.booleanArray._Static._Proxy.$instance.
                      get$DEFAULT_VALUE();
                }
                i = i & (1 << counter) - 1;
                counter -= c;
                level--;
            }
            return ((fabric.lang.arrays.internal._booleanArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).get(i);
        }
        
        public boolean set(int i, boolean data) {
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
                                        fabric.lang.arrays.internal._booleanArray.class,
                                        this.get$CHUNK_SIZE()));
                            break;
                        case 2:
                            nextObject =
                              (fabric.lang.arrays.internal._booleanArray)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  ((fabric.lang.arrays.internal._booleanArray)
                                     new fabric.lang.arrays.internal.
                                       _booleanArray._Impl(this.$getStore()).
                                     $getProxy()).
                                      fabric$lang$arrays$internal$_booleanArray$(
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
            return ((fabric.lang.arrays.internal._booleanArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).set(i, data);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.arrays.booleanArray._Proxy(this);
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
            fabric.lang.arrays.booleanArray._Impl src =
              (fabric.lang.arrays.booleanArray._Impl) other;
            this.CHUNK_SIZE = src.CHUNK_SIZE;
            this.CHUNK_SIZE_LOG2 = src.CHUNK_SIZE_LOG2;
            this.height = src.height;
            this.length = src.length;
            this.root = src.root;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public boolean get$DEFAULT_VALUE();
        
        public boolean set$DEFAULT_VALUE(boolean val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.arrays.booleanArray._Static {
            public boolean get$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.booleanArray._Static._Impl)
                          fetch()).get$DEFAULT_VALUE();
            }
            
            public boolean set$DEFAULT_VALUE(boolean val) {
                return ((fabric.lang.arrays.booleanArray._Static._Impl)
                          fetch()).set$DEFAULT_VALUE(val);
            }
            
            public _Proxy(fabric.lang.arrays.booleanArray._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.arrays.booleanArray._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  arrays.
                  booleanArray.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.arrays.booleanArray._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.arrays.booleanArray._Static._Impl.class);
                $instance = (fabric.lang.arrays.booleanArray._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.arrays.booleanArray._Static {
            public boolean get$DEFAULT_VALUE() { return this.DEFAULT_VALUE; }
            
            public boolean set$DEFAULT_VALUE(boolean val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DEFAULT_VALUE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private boolean DEFAULT_VALUE;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeBoolean(this.DEFAULT_VALUE);
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
                this.DEFAULT_VALUE = in.readBoolean();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.arrays.booleanArray._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm16 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled19 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff17 = 1;
                        boolean $doBackoff18 = true;
                        boolean $retry12 = true;
                        boolean $keepReads13 = false;
                        $label10: for (boolean $commit11 = false; !$commit11;
                                       ) {
                            if ($backoffEnabled19) {
                                if ($doBackoff18) {
                                    if ($backoff17 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff17));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e14) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff17 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff17 =
                                          java.lang.Math.
                                            min(
                                              $backoff17 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff18 = $backoff17 <= 32 ||
                                                 !$doBackoff18;
                            }
                            $commit11 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.lang.arrays.booleanArray._Static._Proxy.
                                  $instance.
                                  set$DEFAULT_VALUE(false);
                            }
                            catch (final fabric.worker.RetryException $e14) {
                                $commit11 = false;
                                continue $label10;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e14) {
                                $commit11 = false;
                                $retry12 = false;
                                $keepReads13 = $e14.keepReads;
                                fabric.common.TransactionID $currentTid15 =
                                  $tm16.getCurrentTid();
                                if ($e14.tid == null ||
                                      !$e14.tid.isDescendantOf($currentTid15)) {
                                    throw $e14;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e14);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e14) {
                                $commit11 = false;
                                fabric.common.TransactionID $currentTid15 =
                                  $tm16.getCurrentTid();
                                if ($e14.tid.isDescendantOf($currentTid15))
                                    continue $label10;
                                if ($currentTid15.parent != null) {
                                    $retry12 = false;
                                    throw $e14;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e14) {
                                $commit11 = false;
                                $retry12 = false;
                                if ($tm16.inNestedTxn()) {
                                    $keepReads13 = true;
                                }
                                throw $e14;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid15 =
                                  $tm16.getCurrentTid();
                                if ($commit11) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e14) {
                                        $commit11 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e14) {
                                        $commit11 = false;
                                        $retry12 = false;
                                        $keepReads13 = $e14.keepReads;
                                        if ($e14.tid ==
                                              null ||
                                              !$e14.tid.isDescendantOf(
                                                          $currentTid15))
                                            throw $e14;
                                        throw new fabric.worker.
                                                UserAbortException($e14);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e14) {
                                        $commit11 = false;
                                        $currentTid15 = $tm16.getCurrentTid();
                                        if ($currentTid15 != null) {
                                            if ($e14.tid.equals(
                                                           $currentTid15) ||
                                                  !$e14.tid.isDescendantOf(
                                                              $currentTid15)) {
                                                throw $e14;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm16.inNestedTxn() &&
                                          $tm16.checkForStaleObjects()) {
                                        $retry12 = true;
                                        $keepReads13 = false;
                                    }
                                    if ($keepReads13) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e14) {
                                            $currentTid15 = $tm16.getCurrentTid();
                                            if ($currentTid15 != null &&
                                                  ($e14.tid.equals($currentTid15) || !$e14.tid.isDescendantOf($currentTid15))) {
                                                throw $e14;
                                            } else {
                                                $retry12 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit11) {
                                    {  }
                                    if ($retry12) { continue $label10; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -75, -96, -77, 3, 74,
    89, -85, -77, 67, -28, -104, 101, 64, -56, 43, -29, -45, 72, 124, -105, -57,
    65, -60, 4, 66, 103, 33, -80, 33, 97, -49, 126 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
