package fabric.lang.arrays;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Store;
import fabric.lang.arrays.internal._intArray;
import fabric.lang.arrays.internal._ObjectArray;

/**
 * <p>
 * This class implements a resizable array using
 * fabric.lang.arrays.internal._intArray as a primitive. The smaller array
 * pieces are arranged as a tree so as to support efficient indexing operations.
 * </p>
 * <p>
 * This is an array for ints, and is adapted from byteArray.
 * </p>
 * <p>
 * Optimizations:
 * <ol>
 * <li>Remove the call to getProxy in the generated Java file</li>
 * </ol>
 * </p>
 * XXX For simplicity the int arrays are also of CHUNK_SIZE size
 * 
 * @author kvikram
 */
public interface intArray extends fabric.lang.Object {
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
    public fabric.lang.arrays.intArray fabric$lang$arrays$intArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length);
    
    public fabric.lang.arrays.intArray fabric$lang$arrays$intArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length,
      int CHUNK_SIZE_LOG2);
    
    public fabric.lang.Object $initLabels();
    
    public int getLength();
    
    public void setLength(int newSize);
    
    public int get(int i);
    
    public int set(int i, int data);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.arrays.intArray {
        public int get$CHUNK_SIZE() {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).get$CHUNK_SIZE(
                                                                   );
        }
        
        public int set$CHUNK_SIZE(int val) {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).set$CHUNK_SIZE(
                                                                   val);
        }
        
        public int postInc$CHUNK_SIZE() {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).
              postInc$CHUNK_SIZE();
        }
        
        public int postDec$CHUNK_SIZE() {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).
              postDec$CHUNK_SIZE();
        }
        
        public int get$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).
              get$CHUNK_SIZE_LOG2();
        }
        
        public int set$CHUNK_SIZE_LOG2(int val) {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).
              set$CHUNK_SIZE_LOG2(val);
        }
        
        public int postInc$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).
              postInc$CHUNK_SIZE_LOG2();
        }
        
        public int postDec$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).
              postDec$CHUNK_SIZE_LOG2();
        }
        
        public int get$height() {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).get$height();
        }
        
        public int set$height(int val) {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).set$height(
                                                                   val);
        }
        
        public int postInc$height() {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).postInc$height(
                                                                   );
        }
        
        public int postDec$height() {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).postDec$height(
                                                                   );
        }
        
        public int get$length() {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).get$length();
        }
        
        public int set$length(int val) {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).set$length(
                                                                   val);
        }
        
        public int postInc$length() {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).postInc$length(
                                                                   );
        }
        
        public int postDec$length() {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).postDec$length(
                                                                   );
        }
        
        public fabric.lang.Object get$root() {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).get$root();
        }
        
        public fabric.lang.Object set$root(fabric.lang.Object val) {
            return ((fabric.lang.arrays.intArray._Impl) fetch()).set$root(val);
        }
        
        public fabric.lang.arrays.intArray fabric$lang$arrays$intArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3) {
            return ((fabric.lang.arrays.intArray) fetch()).
              fabric$lang$arrays$intArray$(arg1, arg2, arg3);
        }
        
        public fabric.lang.arrays.intArray fabric$lang$arrays$intArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3, int arg4) {
            return ((fabric.lang.arrays.intArray) fetch()).
              fabric$lang$arrays$intArray$(arg1, arg2, arg3, arg4);
        }
        
        public int getLength() {
            return ((fabric.lang.arrays.intArray) fetch()).getLength();
        }
        
        public void setLength(int arg1) {
            ((fabric.lang.arrays.intArray) fetch()).setLength(arg1);
        }
        
        public int get(int arg1) {
            return ((fabric.lang.arrays.intArray) fetch()).get(arg1);
        }
        
        public int set(int arg1, int arg2) {
            return ((fabric.lang.arrays.intArray) fetch()).set(arg1, arg2);
        }
        
        public _Proxy(intArray._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.arrays.intArray {
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
        public fabric.lang.arrays.intArray fabric$lang$arrays$intArray$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy, int length) {
            fabric$lang$arrays$intArray$(updateLabel, accessPolicy, length, 8);
            return (fabric.lang.arrays.intArray) this.$getProxy();
        }
        
        public fabric.lang.arrays.intArray fabric$lang$arrays$intArray$(
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
                              ((fabric.lang.arrays.intArray._Impl)
                                 this.fetch()).getHeight(length));
            switch (this.get$height()) {
                case 1:
                    this.
                      set$root(
                        (fabric.lang.arrays.internal._intArray)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.lang.arrays.internal._intArray)
                               new fabric.lang.arrays.internal._intArray._Impl(
                                 this.$getStore()).$getProxy()).
                                fabric$lang$arrays$internal$_intArray$(
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
                                  fabric.lang.arrays.internal._intArray.class,
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
            return (fabric.lang.arrays.intArray) this.$getProxy();
        }
        
        public fabric.lang.Object $initLabels() {
            return (fabric.lang.arrays.intArray) this.$getProxy();
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
                (fabric.lang.arrays.internal._intArray)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.lang.arrays.internal._intArray)
                       new fabric.lang.arrays.internal._intArray._Impl(
                         this.$getStore()).$getProxy()).
                        fabric$lang$arrays$internal$_intArray$(
                          this.get$$updateLabel(), this.get$$accessPolicy(),
                          this.get$CHUNK_SIZE())));
        }
        
        public void setLength(int newSize) {
            if (newSize < 0) throw new java.lang.NegativeArraySizeException();
            if (newSize == 0)
                ((fabric.lang.arrays.intArray._Impl) this.fetch()).
                  setZeroLength();
            int newHeight = ((fabric.lang.arrays.intArray._Impl)
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
                                fabric.lang.arrays.internal._intArray.class,
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
                    fabric.lang.arrays.internal._intArray
                      rootArray =
                      (fabric.lang.arrays.internal._intArray)
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
        
        public int get(int i) {
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
                    return fabric.lang.arrays.intArray._Static._Proxy.$instance.
                      get$DEFAULT_VALUE();
                }
                i = i & (1 << counter) - 1;
                counter -= c;
                level--;
            }
            return ((fabric.lang.arrays.internal._intArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).get(i);
        }
        
        public int set(int i, int data) {
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
                                        fabric.lang.arrays.internal._intArray.class,
                                        this.get$CHUNK_SIZE()));
                            break;
                        case 2:
                            nextObject =
                              (fabric.lang.arrays.internal._intArray)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  ((fabric.lang.arrays.internal._intArray)
                                     new fabric.lang.arrays.internal._intArray.
                                       _Impl(this.$getStore()).
                                     $getProxy()).
                                      fabric$lang$arrays$internal$_intArray$(
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
            return ((fabric.lang.arrays.internal._intArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).set(i, data);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.arrays.intArray._Proxy(this);
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
            fabric.lang.arrays.intArray._Impl src =
              (fabric.lang.arrays.intArray._Impl) other;
            this.CHUNK_SIZE = src.CHUNK_SIZE;
            this.CHUNK_SIZE_LOG2 = src.CHUNK_SIZE_LOG2;
            this.height = src.height;
            this.length = src.length;
            this.root = src.root;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public int get$DEFAULT_VALUE();
        
        public int set$DEFAULT_VALUE(int val);
        
        public int postInc$DEFAULT_VALUE();
        
        public int postDec$DEFAULT_VALUE();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.arrays.intArray._Static {
            public int get$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.intArray._Static._Impl) fetch()).
                  get$DEFAULT_VALUE();
            }
            
            public int set$DEFAULT_VALUE(int val) {
                return ((fabric.lang.arrays.intArray._Static._Impl) fetch()).
                  set$DEFAULT_VALUE(val);
            }
            
            public int postInc$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.intArray._Static._Impl) fetch()).
                  postInc$DEFAULT_VALUE();
            }
            
            public int postDec$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.intArray._Static._Impl) fetch()).
                  postDec$DEFAULT_VALUE();
            }
            
            public _Proxy(fabric.lang.arrays.intArray._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.arrays.intArray._Static $instance;
            
            static {
                fabric.
                  lang.
                  arrays.
                  intArray.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.arrays.intArray._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.arrays.intArray._Static._Impl.class);
                $instance = (fabric.lang.arrays.intArray._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.arrays.intArray._Static {
            public int get$DEFAULT_VALUE() { return this.DEFAULT_VALUE; }
            
            public int set$DEFAULT_VALUE(int val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DEFAULT_VALUE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public int postInc$DEFAULT_VALUE() {
                int tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((int) (tmp + 1));
                return tmp;
            }
            
            public int postDec$DEFAULT_VALUE() {
                int tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((int) (tmp - 1));
                return tmp;
            }
            
            private int DEFAULT_VALUE;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeInt(this.DEFAULT_VALUE);
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
                this.DEFAULT_VALUE = in.readInt();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.arrays.intArray._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm66 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled69 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff67 = 1;
                        boolean $doBackoff68 = true;
                        boolean $retry62 = true;
                        boolean $keepReads63 = false;
                        $label60: for (boolean $commit61 = false; !$commit61;
                                       ) {
                            if ($backoffEnabled69) {
                                if ($doBackoff68) {
                                    if ($backoff67 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff67));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e64) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff67 < 5000) $backoff67 *= 2;
                                }
                                $doBackoff68 = $backoff67 <= 32 ||
                                                 !$doBackoff68;
                            }
                            $commit61 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.lang.arrays.intArray._Static._Proxy.
                                      $instance.
                                      set$DEFAULT_VALUE((int) 0);
                                }
                                catch (final fabric.worker.
                                         RetryException $e64) {
                                    throw $e64;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e64) {
                                    throw $e64;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e64) {
                                    throw $e64;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e64) {
                                    throw $e64;
                                }
                                catch (final Throwable $e64) {
                                    $tm66.getCurrentLog().checkRetrySignal();
                                    throw $e64;
                                }
                            }
                            catch (final fabric.worker.RetryException $e64) {
                                $commit61 = false;
                                continue $label60;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e64) {
                                $commit61 = false;
                                $retry62 = false;
                                $keepReads63 = $e64.keepReads;
                                if ($tm66.checkForStaleObjects()) {
                                    $retry62 = true;
                                    $keepReads63 = false;
                                    continue $label60;
                                }
                                fabric.common.TransactionID $currentTid65 =
                                  $tm66.getCurrentTid();
                                if ($e64.tid == null ||
                                      !$e64.tid.isDescendantOf($currentTid65)) {
                                    throw $e64;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e64);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e64) {
                                $commit61 = false;
                                fabric.common.TransactionID $currentTid65 =
                                  $tm66.getCurrentTid();
                                if ($e64.tid.isDescendantOf($currentTid65))
                                    continue $label60;
                                if ($currentTid65.parent != null) {
                                    $retry62 = false;
                                    throw $e64;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e64) {
                                $commit61 = false;
                                if ($tm66.checkForStaleObjects())
                                    continue $label60;
                                fabric.common.TransactionID $currentTid65 =
                                  $tm66.getCurrentTid();
                                if ($e64.tid.isDescendantOf($currentTid65)) {
                                    $retry62 = true;
                                }
                                else if ($currentTid65.parent != null) {
                                    $retry62 = false;
                                    throw $e64;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e64) {
                                $commit61 = false;
                                if ($tm66.checkForStaleObjects())
                                    continue $label60;
                                $retry62 = false;
                                throw new fabric.worker.AbortException($e64);
                            }
                            finally {
                                if ($commit61) {
                                    fabric.common.TransactionID $currentTid65 =
                                      $tm66.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e64) {
                                        $commit61 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e64) {
                                        $commit61 = false;
                                        $retry62 = false;
                                        $keepReads63 = $e64.keepReads;
                                        if ($tm66.checkForStaleObjects()) {
                                            $retry62 = true;
                                            $keepReads63 = false;
                                            continue $label60;
                                        }
                                        if ($e64.tid ==
                                              null ||
                                              !$e64.tid.isDescendantOf(
                                                          $currentTid65))
                                            throw $e64;
                                        throw new fabric.worker.
                                                UserAbortException($e64);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e64) {
                                        $commit61 = false;
                                        $currentTid65 = $tm66.getCurrentTid();
                                        if ($currentTid65 != null) {
                                            if ($e64.tid.equals(
                                                           $currentTid65) ||
                                                  !$e64.tid.isDescendantOf(
                                                              $currentTid65)) {
                                                throw $e64;
                                            }
                                        }
                                    }
                                } else if ($keepReads63) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit61) {
                                    {  }
                                    if ($retry62) { continue $label60; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 2, 105, -122, 94, 66,
    -69, -80, -84, 73, -47, -60, -29, -50, -3, 2, 50, 10, -81, -98, -103, 30,
    123, 98, 81, 33, 79, -35, -113, -14, 27, -22, -34 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
