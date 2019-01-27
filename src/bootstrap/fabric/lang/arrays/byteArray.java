package fabric.lang.arrays;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Store;
import fabric.lang.arrays.internal._byteArray;
import fabric.lang.arrays.internal._ObjectArray;

/**
 * <p>
 * This class implements a resizable array using
 * fabric.lang.arrays.internal._byteArray as a primitive. The smaller array
 * pieces are arranged as a tree so as to support efficient indexing operations.
 * </p>
 * <p>
 * This is an array for bytes, and is adapted from ObjectArray
 * </p>
 * <p>
 * Optimizations:
 * <ol>
 * <li>Remove the call to getProxy in the generated Java file</li>
 * </ol>
 * </p>
 * XXX For simplicity the byte arrays are also of CHUNK_SIZE size
 * 
 * @author kvikram
 */
public interface byteArray extends fabric.lang.Object {
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
   *          The store on which to allocate the array.
   * @param length
   *          The length of the array.
   */
    public fabric.lang.arrays.byteArray fabric$lang$arrays$byteArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length);
    
    public fabric.lang.arrays.byteArray fabric$lang$arrays$byteArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length,
      int CHUNK_SIZE_LOG2);
    
    public fabric.lang.Object $initLabels();
    
    public int getLength();
    
    public void setLength(int newSize);
    
    public byte get(int i);
    
    public byte set(int i, byte data);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.arrays.byteArray {
        public int get$CHUNK_SIZE() {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).
              get$CHUNK_SIZE();
        }
        
        public int set$CHUNK_SIZE(int val) {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).
              set$CHUNK_SIZE(val);
        }
        
        public int postInc$CHUNK_SIZE() {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).
              postInc$CHUNK_SIZE();
        }
        
        public int postDec$CHUNK_SIZE() {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).
              postDec$CHUNK_SIZE();
        }
        
        public int get$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).
              get$CHUNK_SIZE_LOG2();
        }
        
        public int set$CHUNK_SIZE_LOG2(int val) {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).
              set$CHUNK_SIZE_LOG2(val);
        }
        
        public int postInc$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).
              postInc$CHUNK_SIZE_LOG2();
        }
        
        public int postDec$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).
              postDec$CHUNK_SIZE_LOG2();
        }
        
        public int get$height() {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).get$height();
        }
        
        public int set$height(int val) {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).set$height(
                                                                    val);
        }
        
        public int postInc$height() {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).
              postInc$height();
        }
        
        public int postDec$height() {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).
              postDec$height();
        }
        
        public int get$length() {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).get$length();
        }
        
        public int set$length(int val) {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).set$length(
                                                                    val);
        }
        
        public int postInc$length() {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).
              postInc$length();
        }
        
        public int postDec$length() {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).
              postDec$length();
        }
        
        public fabric.lang.Object get$root() {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).get$root();
        }
        
        public fabric.lang.Object set$root(fabric.lang.Object val) {
            return ((fabric.lang.arrays.byteArray._Impl) fetch()).set$root(val);
        }
        
        public fabric.lang.arrays.byteArray fabric$lang$arrays$byteArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3) {
            return ((fabric.lang.arrays.byteArray) fetch()).
              fabric$lang$arrays$byteArray$(arg1, arg2, arg3);
        }
        
        public fabric.lang.arrays.byteArray fabric$lang$arrays$byteArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3, int arg4) {
            return ((fabric.lang.arrays.byteArray) fetch()).
              fabric$lang$arrays$byteArray$(arg1, arg2, arg3, arg4);
        }
        
        public int getLength() {
            return ((fabric.lang.arrays.byteArray) fetch()).getLength();
        }
        
        public void setLength(int arg1) {
            ((fabric.lang.arrays.byteArray) fetch()).setLength(arg1);
        }
        
        public byte get(int arg1) {
            return ((fabric.lang.arrays.byteArray) fetch()).get(arg1);
        }
        
        public byte set(int arg1, byte arg2) {
            return ((fabric.lang.arrays.byteArray) fetch()).set(arg1, arg2);
        }
        
        public _Proxy(byteArray._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.arrays.byteArray {
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
   *          The store on which to allocate the array.
   * @param length
   *          The length of the array.
   */
        public fabric.lang.arrays.byteArray fabric$lang$arrays$byteArray$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy, int length) {
            fabric$lang$arrays$byteArray$(updateLabel, accessPolicy, length, 8);
            return (fabric.lang.arrays.byteArray) this.$getProxy();
        }
        
        public fabric.lang.arrays.byteArray fabric$lang$arrays$byteArray$(
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
                              ((fabric.lang.arrays.byteArray._Impl)
                                 this.fetch()).getHeight(length));
            switch (this.get$height()) {
                case 1:
                    this.
                      set$root(
                        (fabric.lang.arrays.internal._byteArray)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.lang.arrays.internal._byteArray)
                               new fabric.lang.arrays.internal._byteArray._Impl(
                                 this.$getStore()).$getProxy()).
                                fabric$lang$arrays$internal$_byteArray$(
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
                                  fabric.lang.arrays.internal._byteArray.class,
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
            return (fabric.lang.arrays.byteArray) this.$getProxy();
        }
        
        public fabric.lang.Object $initLabels() {
            return (fabric.lang.arrays.byteArray) this.$getProxy();
        }
        
        public int getLength() { return this.get$length(); }
        
        /**
   * Ceiling(log_{CHUNK_SIZE}(length)). (Except returns 1 if length <= 1.) This
   * basically returns the height of the tree for a given array length.
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
                (fabric.lang.arrays.internal._byteArray)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.lang.arrays.internal._byteArray)
                       new fabric.lang.arrays.internal._byteArray._Impl(
                         this.$getStore()).$getProxy()).
                        fabric$lang$arrays$internal$_byteArray$(
                          this.get$$updateLabel(), this.get$$accessPolicy(),
                          this.get$CHUNK_SIZE())));
        }
        
        public void setLength(int newSize) {
            if (newSize < 0) throw new java.lang.NegativeArraySizeException();
            if (newSize == 0)
                ((fabric.lang.arrays.byteArray._Impl) this.fetch()).
                  setZeroLength();
            int newHeight = ((fabric.lang.arrays.byteArray._Impl)
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
                                fabric.lang.arrays.internal._byteArray.class,
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
                    fabric.lang.arrays.internal._byteArray
                      rootArray =
                      (fabric.lang.arrays.internal._byteArray)
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
        
        public byte get(int i) {
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
                    return fabric.lang.arrays.byteArray._Static._Proxy.$instance.
                      get$DEFAULT_VALUE();
                }
                i = i & (1 << counter) - 1;
                counter -= c;
                level--;
            }
            return ((fabric.lang.arrays.internal._byteArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).get(i);
        }
        
        public byte set(int i, byte data) {
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
                                        fabric.lang.arrays.internal._byteArray.class,
                                        this.get$CHUNK_SIZE()));
                            break;
                        case 2:
                            nextObject =
                              (fabric.lang.arrays.internal._byteArray)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  ((fabric.lang.arrays.internal._byteArray)
                                     new fabric.lang.arrays.internal._byteArray.
                                       _Impl(this.$getStore()).
                                     $getProxy()).
                                      fabric$lang$arrays$internal$_byteArray$(
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
            return ((fabric.lang.arrays.internal._byteArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).set(i, data);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.arrays.byteArray._Proxy(this);
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
            fabric.lang.arrays.byteArray._Impl src =
              (fabric.lang.arrays.byteArray._Impl) other;
            this.CHUNK_SIZE = src.CHUNK_SIZE;
            this.CHUNK_SIZE_LOG2 = src.CHUNK_SIZE_LOG2;
            this.height = src.height;
            this.length = src.length;
            this.root = src.root;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public byte get$DEFAULT_VALUE();
        
        public byte set$DEFAULT_VALUE(byte val);
        
        public byte postInc$DEFAULT_VALUE();
        
        public byte postDec$DEFAULT_VALUE();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.arrays.byteArray._Static {
            public byte get$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.byteArray._Static._Impl) fetch()).
                  get$DEFAULT_VALUE();
            }
            
            public byte set$DEFAULT_VALUE(byte val) {
                return ((fabric.lang.arrays.byteArray._Static._Impl) fetch()).
                  set$DEFAULT_VALUE(val);
            }
            
            public byte postInc$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.byteArray._Static._Impl) fetch()).
                  postInc$DEFAULT_VALUE();
            }
            
            public byte postDec$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.byteArray._Static._Impl) fetch()).
                  postDec$DEFAULT_VALUE();
            }
            
            public _Proxy(fabric.lang.arrays.byteArray._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.arrays.byteArray._Static $instance;
            
            static {
                fabric.
                  lang.
                  arrays.
                  byteArray.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.arrays.byteArray._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.arrays.byteArray._Static._Impl.class);
                $instance = (fabric.lang.arrays.byteArray._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.arrays.byteArray._Static {
            public byte get$DEFAULT_VALUE() { return this.DEFAULT_VALUE; }
            
            public byte set$DEFAULT_VALUE(byte val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DEFAULT_VALUE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public byte postInc$DEFAULT_VALUE() {
                byte tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((byte) (tmp + 1));
                return tmp;
            }
            
            public byte postDec$DEFAULT_VALUE() {
                byte tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((byte) (tmp - 1));
                return tmp;
            }
            
            private byte DEFAULT_VALUE;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeByte(this.DEFAULT_VALUE);
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
                this.DEFAULT_VALUE = in.readByte();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.arrays.byteArray._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm26 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled29 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff27 = 1;
                        boolean $doBackoff28 = true;
                        boolean $retry22 = true;
                        boolean $keepReads23 = false;
                        $label20: for (boolean $commit21 = false; !$commit21;
                                       ) {
                            if ($backoffEnabled29) {
                                if ($doBackoff28) {
                                    if ($backoff27 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff27));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e24) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff27 < 5000) $backoff27 *= 2;
                                }
                                $doBackoff28 = $backoff27 <= 32 ||
                                                 !$doBackoff28;
                            }
                            $commit21 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.lang.arrays.byteArray._Static._Proxy.
                                      $instance.
                                      set$DEFAULT_VALUE((byte) 0);
                                }
                                catch (final fabric.worker.
                                         RetryException $e24) {
                                    throw $e24;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e24) {
                                    throw $e24;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e24) {
                                    throw $e24;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e24) {
                                    throw $e24;
                                }
                                catch (final Throwable $e24) {
                                    $tm26.getCurrentLog().checkRetrySignal();
                                    throw $e24;
                                }
                            }
                            catch (final fabric.worker.RetryException $e24) {
                                $commit21 = false;
                                continue $label20;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e24) {
                                $commit21 = false;
                                $retry22 = false;
                                $keepReads23 = $e24.keepReads;
                                if ($tm26.checkForStaleObjects()) {
                                    $retry22 = true;
                                    $keepReads23 = false;
                                    continue $label20;
                                }
                                fabric.common.TransactionID $currentTid25 =
                                  $tm26.getCurrentTid();
                                if ($e24.tid == null ||
                                      !$e24.tid.isDescendantOf($currentTid25)) {
                                    throw $e24;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e24);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e24) {
                                $commit21 = false;
                                fabric.common.TransactionID $currentTid25 =
                                  $tm26.getCurrentTid();
                                if ($e24.tid.isDescendantOf($currentTid25))
                                    continue $label20;
                                if ($currentTid25.parent != null) {
                                    $retry22 = false;
                                    throw $e24;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e24) {
                                $commit21 = false;
                                if ($tm26.checkForStaleObjects())
                                    continue $label20;
                                fabric.common.TransactionID $currentTid25 =
                                  $tm26.getCurrentTid();
                                if ($e24.tid.isDescendantOf($currentTid25)) {
                                    $retry22 = true;
                                }
                                else if ($currentTid25.parent != null) {
                                    $retry22 = false;
                                    throw $e24;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e24) {
                                $commit21 = false;
                                if ($tm26.checkForStaleObjects())
                                    continue $label20;
                                $retry22 = false;
                                throw new fabric.worker.AbortException($e24);
                            }
                            finally {
                                if ($commit21) {
                                    fabric.common.TransactionID $currentTid25 =
                                      $tm26.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e24) {
                                        $commit21 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e24) {
                                        $commit21 = false;
                                        $retry22 = false;
                                        $keepReads23 = $e24.keepReads;
                                        if ($tm26.checkForStaleObjects()) {
                                            $retry22 = true;
                                            $keepReads23 = false;
                                            continue $label20;
                                        }
                                        if ($e24.tid ==
                                              null ||
                                              !$e24.tid.isDescendantOf(
                                                          $currentTid25))
                                            throw $e24;
                                        throw new fabric.worker.
                                                UserAbortException($e24);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e24) {
                                        $commit21 = false;
                                        $currentTid25 = $tm26.getCurrentTid();
                                        if ($currentTid25 != null) {
                                            if ($e24.tid.equals(
                                                           $currentTid25) ||
                                                  !$e24.tid.isDescendantOf(
                                                              $currentTid25)) {
                                                throw $e24;
                                            }
                                        }
                                    }
                                } else if ($keepReads23) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit21) {
                                    {  }
                                    if ($retry22) { continue $label20; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 85, 94, -70, 92, -9,
    -31, -64, 65, 14, 82, 69, 4, -116, 97, -100, -56, -102, 35, -26, -69, -30,
    23, 32, 95, 31, -114, -58, 76, -30, 47, -125, 125 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
