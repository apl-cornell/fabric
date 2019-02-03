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
    
    public static final byte[] $classHash = new byte[] { -125, -53, 35, -67,
    127, -76, 66, 19, -121, 10, 26, 64, -1, -56, -19, 95, -34, -83, 111, 120,
    -7, 98, 66, -72, -80, 127, 22, -16, 3, 98, 121, 73 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
