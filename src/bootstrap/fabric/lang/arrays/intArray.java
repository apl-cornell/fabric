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
                        long $backoff67 = 1;
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
                                    if ($backoff67 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff67 =
                                          java.lang.Math.
                                            min(
                                              $backoff67 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff68 = $backoff67 <= 32 ||
                                                 !$doBackoff68;
                            }
                            $commit61 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.lang.arrays.intArray._Static._Proxy.
                                  $instance.
                                  set$DEFAULT_VALUE((int) 0);
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
                            catch (final Throwable $e64) {
                                $commit61 = false;
                                $retry62 = false;
                                if ($tm66.inNestedTxn()) {
                                    $keepReads63 = true;
                                }
                                throw $e64;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid65 =
                                  $tm66.getCurrentTid();
                                if ($commit61) {
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
                                } else {
                                    if (!$tm66.inNestedTxn() &&
                                          $tm66.checkForStaleObjects()) {
                                        $retry62 = true;
                                        $keepReads63 = false;
                                    }
                                    if ($keepReads63) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e64) {
                                            $currentTid65 = $tm66.getCurrentTid();
                                            if ($currentTid65 != null &&
                                                  ($e64.tid.equals($currentTid65) || !$e64.tid.isDescendantOf($currentTid65))) {
                                                throw $e64;
                                            } else {
                                                $retry62 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
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
    
    public static final byte[] $classHash = new byte[] { -35, 91, 98, -103, -72,
    -61, 8, -110, 113, -41, -64, 61, 97, 125, 119, 27, 37, 69, 16, -35, 12, 58,
    -19, -72, 78, -91, 43, 29, 97, -86, 108, -8 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
