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
    
    public static final byte[] $classHash = new byte[] { -123, 110, 1, -107,
    -47, 21, 99, -77, -5, -120, 19, 39, -86, 101, 99, 75, 100, 37, 116, 19, -78,
    45, 54, -80, -82, -45, -31, -54, -113, -103, -94, -50 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
