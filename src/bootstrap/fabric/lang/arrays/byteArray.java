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
    
    public static final byte[] $classHash = new byte[] { 30, -12, -126, -94,
    -55, -36, -40, -92, -95, -97, 126, 17, 117, 78, 62, -26, -30, 51, 31, 11,
    -25, 61, 50, -8, -66, 13, -1, 4, -46, 36, 62, -105 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "H4sIAAAAAAAAAK1YfXATxxVfyfInNjYQPmLAJkZlBgJSIZ12Ejc0WNhYRTHGxrSYNuJ0WskXTneXu5WRaciEdFpoO6WTlBBoG2fSkoZSJ5m2Q9uZjJP0IwkkKZ0wGRpmQst0Jg0t5Y9Mv/ijDX1vb6WTTicFd6qZ3XfafW/3t2/f++3eTV0ltZZJulJSQlFDbMKgVqhPSkRjg5Jp0WRElSxrG7TG5VmB6JHLTyc7/MQfI82ypOmaIktqXLMYmR27VxqXwhpl4ZGhaPdO0iijYb9kjTHi39mTM8kyQ1cn0qrOxCRl4z96a/jwY/e0/biGtI6SVkUbZhJT5IiuMZpjo6Q5QzMJalobkkmaHCVzNEqTw9RUJFXZC4q6NkrmWkpak1jWpNYQtXR1HBXnWlmDmnzOfCPC1wG2mZWZbgL8Nht+lilqOKZYrDtG6lIKVZPWfeQBEoiR2pQqpUFxQSy/ijAfMdyH7aDepABMMyXJNG8S2K1oSUY63RaFFQc3gwKY1mcoG9MLUwU0CRrIXBuSKmnp8DAzFS0NqrV6FmZhpL3ioKDUYEjybilN44wscusN2l2g1cjdgiaMzHer8ZFgz9pde1a0W1cHPnnoC1q/5ic+wJyksor4G8Cow2U0RFPUpJpMbcPmVbEj0oLpg35CQHm+S9nW+dn979+1uuOl07bOYg+dLYl7qczi8vHE7DeXRFbeXoMwGgzdUjAUSlbOd3VQ9HTnDIj2BYURsTOU73xp6JUdD56kV/ykKUrqZF3NZiCq5sh6xlBUam6iGjUlRpNR0ki1ZIT3R0k9PMcUjdqtW1Ipi7IoCai8qU7n/8FFKRgCXVQPz4qW0vPPhsTG+HPOIIS0QCE+KNcI6TwGsp0Q/wlGtoTH9AwNJ9Qs3QPhHYZCJVMeC0Pemoq8RtaNibBlymHJNKUJSzTb4SOaEhOMbsDHEEAx/v9D5nAVbXt8PnBwp6wnaUKyYLdE5PQMqpAc/bqapGZcVg9NR8m86WM8ehox4i2IWu4fH+z4EjdXFNsezvb0vv9s/HU78tBWuI+RJTbGEGIM2RhDBYwAqxlzKgQsFQKWmvLlQpHJ6A956NRZPMcKIzXDSHcYqsRSupnJEZ+PL+smbs9jBnZ8NzAJkEXzyuHPf3rXwa4aCFZjTwD3D1SD7tRxCCcKTxLkQ1xuPXD5n88d2ac7ScRIsCy3yy0xN7vcPjJ1mSaB+5zhVy2TTsWn9wX9yCuNQHlMgqAE/uhwz1GSo915vkNv1MbILPSBpGJXnqSa2Jip73Fa+N7PxmquHQboLBdATpV3DhuPv332z7fxQyTPqq1F9DtMWXdRJuNgrTxn5zi+32ZSCnoXjw5+89GrB3Zyx4PGcq8Jg1hHIIMlSF3d/NLp+y784ffH3/I7m8VInZFNqIqc42uZcx1+PigfYMF0xAaUQMoRQQXLClxg4MwrHGzACiowE0C3giNaRk8qKUVKqBQj5d+tH1l76q+H2uztVqHFdp5JVn/4AE77zT3kwdfv+VcHH8Yn46nk+M9Rs6lunjMyzwLEkdt/bumxV6XHIfKBqCxlL+XcQ7g/CN/AddwXa3i91tX3May6bG8t4e11Vjnt9+H56cTiaHjqO+2R9VfsjC/EIo5xi0fGb5eK0mTdycw//F11L/tJ/Shp40e3pLHtEjAXhMEoHL5WRDTGSEtJf+lBap8a3YVcW+LOg6Jp3VngMA08ozY+N9mBbwcOOGIhOikEZTHQ9mUh38TeeQbWN+V8hD/cwU2W83oFViu5I2sYqTdMZRwiiyEn4Q2IkUYlk8kyDAI+3a2MtGzs7dswEtsW374hNtLr4f1BU8lAAo2LQ5cePPzV66FDh+3Is28my8suB8U29u2Ez9fCJ83BLLdUm4Vb9L333L7nT+w7YJ/cc0vP2V4tm3nm/H/eCB29dMaDvQNI1TZ7YP3xglfb0KudULrAQxeEfM3Dq1Fvr/rxcT1Wd+Ud2BTpHxnYHB+OjvZy7R6xRhS9jNTAda4ikiCU5YDgAyH/4oFk6w0jaXWQxGNbNq3D5gGvuZtw7vlQVhESOCvkrzzm3u49t4/PnSuM58fxGsU4vxTy+aLxIALHqJIeYx8O6aOE1FIhRz0gfc6GhNWOcgBotUPIoVIAKtXS9v2oMoAFUD4BJJQVUvEAIFUFgFZjQkolAAKmrjNuMB+opPh6YXMJ77rZfV/gUHNVQmAVA3JRNEl1toP/msWN72khv10EpohvCSbj0kqXc56Ixx86PJnc8tRavyDtPqARphtrVDpO1aKhGjGty17+7uavJA4DX7qy9PbI7nfTdlp3umZ2a//g7qkzm1bIj/hJTYFqy96DSo26Swm2yaTwGqdtK6HZZQVf4Z5huJIeQupfBrkB5K7ibXeCxTsPVrnyoEEMEhdyh9vxzlFYYzN1Pijai4PConLWVNhEKCYlhJvLgiNv2OlpCAdZalCH28hE5dAa4PX9VY7oL2M1zshSe44gzhG0L8TBwoU46DjJKnXtp6BEwSlvgOwHOVTBtVhNlDsSTbYKubmyIwMccAD/7sNqfyHN7QV+vcoCv4HVwf95gUuhQMI3HxJy78wWiCYTQlqVF1gM+EiVvqNYPczIrKCiKcwJnZQX9EVQMjDvRSHPzgw6mvxGyFdvDPoTVfqexOpbwC1peMl1uNoFnNM0HJUEKLrlRSG/XwW4B0ejyVNCTlYG7nNy3I6iE1XQn8Tqezb6fueo80LfAeWLhMw+J+QLM0OPJtNC/vTG3P6jKn0/wWoK7oEW3H+pqduu97rJBMZ1JekVR3iL+Qohrc8I+cjM4ghNHhbyazPYjukqq3oRq5/DdljFwXSqUhbAnWVRWsjPzgw9mnxGyK0zQP9KFfSnsfoF3B0hmPBxoxduTILzcLbvFzI9M9xokhJyV2Xcfuemwfl0Ix/6t1XA83eU1wC8JcDnYBsKRIovkos9PumID41y5Nf0+LubV8+v8DlnUdmnX2H37GRrw8LJkd/xDxSFj4iN8P6fyqpq8QtX0XOdYdKUwlE32q9fBhfnXRc0+zSAO6T9wJf9lq16Abi2SBWyBEWxxjtgZ2vgv4vcy+2lVYortmdN/MA99beF1+oatl3i3xXAqcs6/v7Qd8+88/bxJ594YE52YP2f/nhb56z37lx37YWW64FzwfWP/RfcPEdneBcAAA==";
}
