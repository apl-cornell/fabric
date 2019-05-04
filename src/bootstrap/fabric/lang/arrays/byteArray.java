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
                        long $backoff27 = 1;
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
                                    if ($backoff27 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff27 =
                                          java.lang.Math.
                                            min(
                                              $backoff27 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff28 = $backoff27 <= 32 ||
                                                 !$doBackoff28;
                            }
                            $commit21 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.lang.arrays.byteArray._Static._Proxy.
                                  $instance.
                                  set$DEFAULT_VALUE((byte) 0);
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
                            catch (final Throwable $e24) {
                                $commit21 = false;
                                $retry22 = false;
                                if ($tm26.inNestedTxn()) {
                                    $keepReads23 = true;
                                }
                                throw $e24;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid25 =
                                  $tm26.getCurrentTid();
                                if ($commit21) {
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
                                } else {
                                    if (!$tm26.inNestedTxn() &&
                                          $tm26.checkForStaleObjects()) {
                                        $retry22 = true;
                                        $keepReads23 = false;
                                    }
                                    if ($keepReads23) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e24) {
                                            $currentTid25 = $tm26.getCurrentTid();
                                            if ($currentTid25 != null &&
                                                  ($e24.tid.equals($currentTid25) || !$e24.tid.isDescendantOf($currentTid25))) {
                                                throw $e24;
                                            } else {
                                                $retry22 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
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
    
    public static final byte[] $classHash = new byte[] { 66, -79, 2, 26, 60, 64,
    19, -49, 24, 112, -28, 75, -117, 66, -101, -35, -33, -21, 45, 9, -83, 106,
    -101, -101, 53, 58, -100, 122, -16, -72, -115, -32 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1YfYwTxxUf+3wfPg7ugPCRA+7I4SJBwC70S+HSFM7ccc45cNxxtBxtzHo99i2sdze748NHQxUatdBGoUpKCJSEqippKD0SpRVtpOia9CMJ+ShRoogWqTT80ai0FKlR1TZ/tEnfmx177fXa4apamnnrmfdmfvPmvd/M7uR1Um+ZpCstJRU1zCYMaoX7pGQsPiiZFk1FVcmytkFrQp4RiB29+mSqw0/8cdIiS5quKbKkJjSLkVnx3dK4FNEoi4wMxbp3kqCMhv2SNcaIf2dP3iRLDV2dyKg6E5NUjP/IrZEjj97d9uM60jpKWhVtmElMkaO6xmiejZKWLM0mqWltSKVoapTM1ihNDVNTkVRlHyjq2iiZYykZTWI5k1pD1NLVcVScY+UMavI5C40IXwfYZk5mugnw22z4Oaaokbhise44aUgrVE1Z95CvkECc1KdVKQOK8+OFVUT4iJE+bAf1ZgVgmmlJpgWTwB5FSzHS6bYorjg0AApg2pilbEwvThXQJGggc2xIqqRlIsPMVLQMqNbrOZiFkfaqg4JSkyHJe6QMTTCy0K03aHeBVpC7BU0YmedW4yPBnrW79qxkt65vvv3wl7V+zU98gDlFZRXxN4FRh8toiKapSTWZ2oYtK+NHpflTh/yEgPI8l7Kt87N731u/quOF87bOIg+dLcndVGYJ+VRy1puLoytuq0MYTYZuKRgKZSvnuzooerrzBkT7/OKI2BkudL4w9NKO+87Qa37SHCMNsq7mshBVs2U9aygqNTdRjZoSo6kYCVItFeX9MdIIz3FFo3brlnTaoixGAipvatD5f3BRGoZAFzXCs6Kl9cKzIbEx/pw3CCEzoRAflPcJ6TwOsp0Q/2lGtkTG9CyNJNUc3QvhHYFCJVMei0Demoq8WtaNiYhlyhHJNKUJSzTb4SOakhOMbsDHMEAx/v9D5nEVbXt9PnBwp6ynaFKyYLdE5PQMqpAc/bqaomZCVg9PxcjcqeM8eoIY8RZELfePD3Z8sZsrSm2P5Hp633sq8ZodeWgr3MfIYhtjGDGGbYzhIkaA1YI5FQaWCgNLTfry4ejJ2I946DRYPMeKI7XASOsMVWJp3czmic/Hl3UTt+cxAzu+B5gEyKJlxfCX7tx1qKsOgtXYG8D9A9WQO3UcwonBkwT5kJBbD17959NH9+tOEjESqsjtSkvMzS63j0xdpingPmf4lUulc4mp/SE/8koQKI9JEJTAHx3uOcpytLvAd+iN+jiZgT6QVOwqkFQzGzP1vU4L3/tZWM2xwwCd5QLIqfKzw8bjv7vw50/wQ6TAqq0l9DtMWXdJJuNgrTxnZzu+32ZSCnqXjw1++5HrB3dyx4PGMq8JQ1hHIYMlSF3d/Nr5ey6984dTb/udzWKkwcglVUXO87XM/hB+PigfYMF0xAaUQMpRQQVLi1xg4MzLHWzACiowE0C3QiNaVk8paUVKqhQj5d+tH1tz7q+H2+ztVqHFdp5JVn30AE77zT3kvtfu/lcHH8Yn46nk+M9Rs6lurjMyzwLEkT/w1pLjL0uPQ+QDUVnKPsq5h3B/EL6Ba7kvVvN6javvk1h12d5azNsbrEra78Pz04nF0cjkY+3RO67ZGV+MRRzjFo+M3y6VpMnaM9l/+LsaXvSTxlHSxo9uSWPbJWAuCINROHytqGiMk5ll/eUHqX1qdBdzbbE7D0qmdWeBwzTwjNr43GwHvh044IgF6KQwlEVA21eFfBN75xpY35T3Ef6wjpss4/VyrFZwR9Yx0miYyjhEFkNOwhsQI0Elm80xDAI+3a2MzNzY27dhJL4tsX1DfKTXw/uDppKFBBoXhy49dOSbH4YPH7Ejz76ZLKu4HJTa2LcTPt9MPmkeZrml1izcou9PT+9/7vT+g/bJPaf8nO3VctmzF//zevjYlVc82DuAVG2zB9afLnq1Db3aCaULPHRJyFc9vBrz9qofH+/Aan3Bgc3R/pHNA4nh2Ggv1+4Ra0TRy0gdXOeqIglBWQYIPhDyLx5Itt4wklYHSSK+ZdNabN7sNXczzj0PykpCAheE/JXH3Nu95/bxufPF8fw4XlCM80shnysZDyJwjCqZMfbRkD5OSD0VctQD0hdtSFjtqASAVjuEHCoHoFItY9+PqgOYD+UzQEI5IRUPAFJNAGg1JqRUBiBg6jrjBvOASkqvFzaX8K6b3fcFDjVfIwRWMiAXRZNUZzv4r0Xc+J4U8kQJmBK+JZiMS6pdznkinvrqkZOpLU+s8QvS7gMaYbqxWqXjVC0ZKohpXfHydxd/JXEY+Mq1JbdF97ybsdO60zWzW/uHd02+smm5/LCf1BWptuI9qNyou5xgm00Kr3HatjKaXVr0Fe4ZhivpIaTxRZAbQO4q3XYnWLzzYKUrD5rEIAkhd7gd7xyFdTZTF4KivTQoLCrnTIVNhONSUri5IjgKhp2ehnCQpQd1uI1MVA+tzby+t8YR/XWsxhlZYs8RwjlC9oU4VLwQhxwnWeWu/RyUGDjldZD9IIequBariUpHoslWIQeqOzLAAQfw736sDhTT3F7ggzUW+C2sDv3PC1wCBRK+5bCQ+6a3QDSZENKqvsBSwEdr9B3D6iFGZoQUTWFO6KS9oC+EkoV5Lwt5YXrQ0eQ3Qr58Y9C/W6Pve1h9B7glAy+5Dle7gHOahqOSAEXPfF7IH9QA7sHRaPKEkCerA/c5OW5H0eka6M9g9X0bfb9z1Hmh74ByPyGz3hLy59NDjyZTQv70xtz+TI2+n2A1CfdAC+6/1NRt13vdZALjupLyiiO8xXyDkNazQj48vThCk4eEfGAa2zFVY1XPY/UsbIdVGkznqmUB3FkWZoT8wvTQo8nnhdw6DfQv1UB/HqtfwN0RggkfN3rhxiS4CGf7ASEz08ONJmkhd1XH7XduGpxPN/Kh36gBnr+jvArgLQE+D9tQJFJ8kVzk8UlHfGiUo7+mp94dWDWvyuechRWffoXdUydbmxacHPkt/0BR/IgYhPf/dE5VS1+4Sp4bDJOmFY46aL9+GVxcdF3Q7NMA7pD2A1/227bqJeDaElXIEhSlGr8HO1sD/13mXm4vr9JcsT1n4gfuyb8veL+hadsV/l0BnLq05xl/++3r576x0PjjwAM9Jy6/c2118OzuEyc+te6xfX979sEr/wW3R2lGeBcAAA==";
}
