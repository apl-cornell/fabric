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
    
    public static final byte[] $classHash = new byte[] { 35, 50, -71, 99, 17,
    26, -7, -37, -111, -87, 34, -88, -122, -76, 29, 23, -117, -123, -28, -22,
    16, -101, -102, -56, -99, 29, 79, -116, -7, 0, -102, -55 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1YfWwUxxWfOxt/YbCB8BGDPzAXJL7uCq3agts09mHjKwd2sY0Uk3Ks9+bOG/Z2l905c6Ylpa0oKKVILYYkJKAoJQpQSqqotOoHKFKbBkqElDRKm1ZtqVTUtJQ/on4itU3fm5273dvbu+CqJ828vdn35v32zXu/md0Ld8gMyySdKWlMUcNs0qBWuE8ai8UHJdOiyagqWdYwjCbkmdWxE++8kGwLkmCcNMqSpmuKLKkJzWJkdvxRaUKKaJRFRrbFunaQehkN+yVrnJHgjp6cSToMXZ1MqzoTTkrmP74qMvXEzuaXqkjTKGlStCEmMUWO6hqjOTZKGjM0M0ZNqzuZpMlRMkejNDlETUVSlX2gqGujZK6lpDWJZU1qbaOWrk6g4lwra1CT+8wPInwdYJtZmekmwG+24WeZokbiisW64qQmpVA1ae0hj5HqOJmRUqU0KC6I558iwmeM9OE4qDcoANNMSTLNm1TvVrQkI+1ei8IThzaDApjWZigb1wuuqjUJBshcG5IqaenIEDMVLQ2qM/QseGGkpeykoFRnSPJuKU0TjCzy6g3at0CrnocFTRiZ71XjM8GatXjWzLVad7Z+7OhntH4tSAKAOUllFfHXgVGbx2gbTVGTajK1DRtXxk9ICy4fDhICyvM9yrbOdz/77kOr216+auss9tEZGHuUyiwhnxmb/fqS6Ir1VQijztAtBVOh6Mn5qg6KO105A7J9QWFGvBnO33x5208ePnCe3g6ShhipkXU1m4GsmiPrGUNRqbmJatSUGE3GSD3VklF+P0Zq4TquaNQeHUilLMpipFrlQzU6/w8hSsEUGKJauFa0lJ6/NiQ2zq9zBiFkFjQSgPZXQtqTIFsICX6dka2RcT1DI2Nqlu6F9I5Ao5Ipj0egbk1FXiPrxmTEMuWIZJrSpCWG7fQRQ5Ch3XgVBiTG/33GHD5D895AAMLbLutJOiZZsFYib3oGVSiNfl1NUjMhq0cvx8i8y0/x3KnHfLcgZ3l0ArDeS7xM4badyvb0vnsxcd3OO7QVwWNksQ0xjBDDNsRwHiKgasSCCgNFhYGiLgRy4ejp2Dd43tRYvMAKEzXCRBsMVWIp3czkSCDAn+o+bs8TBpZ7N9AIMEXjiqFPf3LX4c4qyFRjbzUuHqiGvHXjsE0MriQohoTcdOidv794Yr/uVBAjoZLCLrXEwuz0hsjUZZoE4nOmX9khXUpc3h8KIqnUA98xCTISyKPN66OoQLvyZIfRmBEnMzEGkoq38gzVwMZNfa8zwpd+NnZz7SzAYHkAcp78+JBx6hc3/vhBvoPkKbXJxb1DlHW5yhgna+IFO8eJ/bBJKej9+snBY8fvHNrBAw8ay/wchrCPQvlKULe6efDqnrd/+5szbwadxWKkxsiOqYqc488y5z34BaD9BxvWIg6gBEaOCh7oKBCBgZ6XO9iAElSgJYBuhUa0jJ5UUoo0plLMlH81PbD20p+PNtvLrcKIHTyTrH7/CZzx+3vIges7/9HGpwnIuCU58XPUbJ6b58zMqwBx5D7/RutTr0qnIPOBpSxlH+XEQ3g8CF/AdTwWa3i/1nPvQ9h12tFawsdrrFLO78PN08nF0ciFZ1qiD962C76QizjHUp+C3y65ymTd+czfgp01rwRJ7Shp5vu2pLHtEvAWpMEo7LxWVAzGyayi+8W7qL1ldBVqbYm3DlxuvVXgEA1cozZeN9iJbycOBGIhBmkNtMXA2b8T8jrenWdgf18uQPjFBm6yjPfLsVvBA1nFSK1hKhOQWQw5CY8/jNQrmUyWYRJwd6sYmbWxt697JD6c2N4dH+n1if6gqWSggCbEjksPTz3+XvjolJ159rFkWcnJwG1jH024v1ncaQ68LK3khVv0/eHF/T84u/+QvW3PLd5ke7Vs5ptv/fu18JM3r/mQdxUwtU0e2H+4ENRmDGo7tE4I0JtCvuIT1Jh/UIN4+SB2D+Xj1xDtH9m6OTEUG+3FkY1l3YagLQN3d4W85eN26z27bXLcJuIDm9aV9d2AvudDW0lI9TUhr/j4HvL3HeC+c4X5gjhfvZjnspDfcc0H2TZOlfQ4e39IHyBkxi4hR3wgPWxDwm57KQC0GhZySzEAlWpp+yBUHsACaB8BwjGETPoA2FkRAFrJQj5SBKDa1HXGDeYDbbhPEjZv8Fv3e88GHGquQgqsZEAkiiapznLwX6M42j0n5AkXGBe3Eiy81nKncF50Z74wdTo58PzaoCDoPqAMphtrVDpBVddU9VjCJW95W/i7h8O2N2+3ro/uvpW2S7jd49mrfW7LhWublstfC5KqAq2WvPAUG3UVk2mDSeF9TRsuotSOQqxwzTBdSQ8htT8F2Q0y7V52J1n862Clpw7qxCQpIXd5A+9se1U2K+eTosWdFBaVs6bCJsNxaUyEuSQ58obtvoawaaUGdTh5TJZPrY28n6ywHX8ROzhFLbF9hNBHyD77hvJn35ATI6M4sp+AFoOY3ADZD3J7mchiN1EaRzQZEXKgfByrOd5q/LsPu8cKVW4/3+MVnu8Idgf/1+drhQYvUY2PCNk/vedDk01Cdpd/PjfeYxXuHcfuKCMzAbnCnMSR/aAvgqaA3/NCPj096GhyUsipe4P+TIV7p7F7ApglDe+yDlN7gHOSho2S7IEX2a8IySoA92FoNLGEVMsDDzgVbifR8xXQv4Ddszb6fmej80PfBu1zhMw+K+TU9NCjyTEhj9xb2C9WuPct7M7Bic+Cky41dTv0XLUHu1U2x/TC9jWhK0m/PMIzzEFCmg4ISaeXR2iSFHLnNJbj+xWe6ofYXYLlsNzJ9JIfejxSX4Fi2CDk6umhR5NVQj4wDfQ/qoCenz2vwIEVkskvjTjupdDegJ39U0Kunx5uNPmokOvK4w465wwXnb5WAfwN7F4F8JYAn2OkLs+j+Ma42OfTjficKEd/TM/c2rx6fpnPNotKPvAKu4unm+oWnh75Of8SUfhUWA8v+qmsqrrfrFzXNYZJUwoHXW+/Zxlc/MxzOrP3AjhA2hf8qV+3Vd8CqnWpQpGgcGu8DXa2Bv77JQ9yS3Enc8WWrImfsS/8ZeE/a+qGb/IPCBDTjmXrvifPabn7q6+e6zz7pW+3Lvzywd//qfnpk1dPtQ4cuUtOXvsvm2DrBV4XAAA=";
}
