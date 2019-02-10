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
                        int $backoff17 = 1;
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
                                    if ($backoff17 < 5000) $backoff17 *= 2;
                                }
                                $doBackoff18 = $backoff17 <= 32 ||
                                                 !$doBackoff18;
                            }
                            $commit11 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.lang.arrays.booleanArray._Static.
                                      _Proxy.
                                      $instance.
                                      set$DEFAULT_VALUE(false);
                                }
                                catch (final fabric.worker.
                                         RetryException $e14) {
                                    throw $e14;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e14) {
                                    throw $e14;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e14) {
                                    throw $e14;
                                }
                                catch (final Throwable $e14) {
                                    $tm16.getCurrentLog().checkRetrySignal();
                                    throw $e14;
                                }
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
                                if ($tm16.checkForStaleObjects()) {
                                    $retry12 = true;
                                    $keepReads13 = false;
                                    continue $label10;
                                }
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
                                if ($tm16.checkForStaleObjects())
                                    continue $label10;
                                $retry12 = false;
                                throw new fabric.worker.AbortException($e14);
                            }
                            finally {
                                if ($commit11) {
                                    fabric.common.TransactionID $currentTid15 =
                                      $tm16.getCurrentTid();
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
                                        if ($tm16.checkForStaleObjects()) {
                                            $retry12 = true;
                                            $keepReads13 = false;
                                            continue $label10;
                                        }
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
                                } else if ($keepReads13) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
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
    
    public static final byte[] $classHash = new byte[] { 76, -54, 112, 18, -12,
    -25, 3, -48, -4, -65, 60, 91, -14, 124, 40, 41, 47, 50, -90, 110, -29, -86,
    108, -21, -65, 39, -90, -56, -122, 82, 125, 117 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
