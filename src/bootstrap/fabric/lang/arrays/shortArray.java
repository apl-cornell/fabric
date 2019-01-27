package fabric.lang.arrays;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Store;
import fabric.lang.arrays.internal._shortArray;
import fabric.lang.arrays.internal._ObjectArray;

/**
 * <p>
 * This class implements a resizable array using
 * fabric.lang.arrays.internal._shortArray as a primitive. The smaller array
 * pieces are arranged as a tree so as to support efficient indexing operations.
 * </p>
 * <p>
 * This is an array for shorts, and is adapted from byteArray.
 * </p>
 * <p>
 * Optimizations:
 * <ol>
 * <li>Remove the call to getProxy in the generated Java file</li>
 * </ol>
 * </p>
 * XXX For simplicity the short arrays are also of CHUNK_SIZE size
 * 
 * @author kvikram
 */
public interface shortArray extends fabric.lang.Object {
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
    public fabric.lang.arrays.shortArray fabric$lang$arrays$shortArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length);
    
    public fabric.lang.arrays.shortArray fabric$lang$arrays$shortArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length,
      int CHUNK_SIZE_LOG2);
    
    public fabric.lang.Object $initLabels();
    
    public int getLength();
    
    public void setLength(int newSize);
    
    public short get(int i);
    
    public short set(int i, short data);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.arrays.shortArray {
        public int get$CHUNK_SIZE() {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).
              get$CHUNK_SIZE();
        }
        
        public int set$CHUNK_SIZE(int val) {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).
              set$CHUNK_SIZE(val);
        }
        
        public int postInc$CHUNK_SIZE() {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).
              postInc$CHUNK_SIZE();
        }
        
        public int postDec$CHUNK_SIZE() {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).
              postDec$CHUNK_SIZE();
        }
        
        public int get$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).
              get$CHUNK_SIZE_LOG2();
        }
        
        public int set$CHUNK_SIZE_LOG2(int val) {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).
              set$CHUNK_SIZE_LOG2(val);
        }
        
        public int postInc$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).
              postInc$CHUNK_SIZE_LOG2();
        }
        
        public int postDec$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).
              postDec$CHUNK_SIZE_LOG2();
        }
        
        public int get$height() {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).get$height();
        }
        
        public int set$height(int val) {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).set$height(
                                                                     val);
        }
        
        public int postInc$height() {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).
              postInc$height();
        }
        
        public int postDec$height() {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).
              postDec$height();
        }
        
        public int get$length() {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).get$length();
        }
        
        public int set$length(int val) {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).set$length(
                                                                     val);
        }
        
        public int postInc$length() {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).
              postInc$length();
        }
        
        public int postDec$length() {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).
              postDec$length();
        }
        
        public fabric.lang.Object get$root() {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).get$root();
        }
        
        public fabric.lang.Object set$root(fabric.lang.Object val) {
            return ((fabric.lang.arrays.shortArray._Impl) fetch()).set$root(
                                                                     val);
        }
        
        public fabric.lang.arrays.shortArray fabric$lang$arrays$shortArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3) {
            return ((fabric.lang.arrays.shortArray) fetch()).
              fabric$lang$arrays$shortArray$(arg1, arg2, arg3);
        }
        
        public fabric.lang.arrays.shortArray fabric$lang$arrays$shortArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3, int arg4) {
            return ((fabric.lang.arrays.shortArray) fetch()).
              fabric$lang$arrays$shortArray$(arg1, arg2, arg3, arg4);
        }
        
        public int getLength() {
            return ((fabric.lang.arrays.shortArray) fetch()).getLength();
        }
        
        public void setLength(int arg1) {
            ((fabric.lang.arrays.shortArray) fetch()).setLength(arg1);
        }
        
        public short get(int arg1) {
            return ((fabric.lang.arrays.shortArray) fetch()).get(arg1);
        }
        
        public short set(int arg1, short arg2) {
            return ((fabric.lang.arrays.shortArray) fetch()).set(arg1, arg2);
        }
        
        public _Proxy(shortArray._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.arrays.shortArray {
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
        public fabric.lang.arrays.shortArray fabric$lang$arrays$shortArray$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy, int length) {
            fabric$lang$arrays$shortArray$(updateLabel, accessPolicy, length,
                                           8);
            return (fabric.lang.arrays.shortArray) this.$getProxy();
        }
        
        public fabric.lang.arrays.shortArray fabric$lang$arrays$shortArray$(
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
                              ((fabric.lang.arrays.shortArray._Impl)
                                 this.fetch()).getHeight(length));
            switch (this.get$height()) {
                case 1:
                    this.
                      set$root(
                        (fabric.lang.arrays.internal._shortArray)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.lang.arrays.internal._shortArray)
                               new fabric.lang.arrays.internal._shortArray.
                                 _Impl(this.$getStore()).
                               $getProxy()).
                                fabric$lang$arrays$internal$_shortArray$(
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
                                  fabric.lang.arrays.internal._shortArray.class,
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
            return (fabric.lang.arrays.shortArray) this.$getProxy();
        }
        
        public fabric.lang.Object $initLabels() {
            return (fabric.lang.arrays.shortArray) this.$getProxy();
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
                (fabric.lang.arrays.internal._shortArray)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.lang.arrays.internal._shortArray)
                       new fabric.lang.arrays.internal._shortArray._Impl(
                         this.$getStore()).$getProxy()).
                        fabric$lang$arrays$internal$_shortArray$(
                          this.get$$updateLabel(), this.get$$accessPolicy(),
                          this.get$CHUNK_SIZE())));
        }
        
        public void setLength(int newSize) {
            if (newSize < 0) throw new java.lang.NegativeArraySizeException();
            if (newSize == 0)
                ((fabric.lang.arrays.shortArray._Impl) this.fetch()).
                  setZeroLength();
            int newHeight = ((fabric.lang.arrays.shortArray._Impl)
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
                                fabric.lang.arrays.internal._shortArray.class,
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
                    fabric.lang.arrays.internal._shortArray
                      rootArray =
                      (fabric.lang.arrays.internal._shortArray)
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
        
        public short get(int i) {
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
                    return fabric.lang.arrays.shortArray._Static._Proxy.$instance.
                      get$DEFAULT_VALUE();
                }
                i = i & (1 << counter) - 1;
                counter -= c;
                level--;
            }
            return ((fabric.lang.arrays.internal._shortArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).get(i);
        }
        
        public short set(int i, short data) {
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
                                        fabric.lang.arrays.internal._shortArray.class,
                                        this.get$CHUNK_SIZE()));
                            break;
                        case 2:
                            nextObject =
                              (fabric.lang.arrays.internal._shortArray)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  ((fabric.lang.arrays.internal._shortArray)
                                     new fabric.lang.arrays.internal.
                                       _shortArray._Impl(this.$getStore()).
                                     $getProxy()).
                                      fabric$lang$arrays$internal$_shortArray$(
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
            return ((fabric.lang.arrays.internal._shortArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).set(i, data);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.arrays.shortArray._Proxy(this);
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
            fabric.lang.arrays.shortArray._Impl src =
              (fabric.lang.arrays.shortArray._Impl) other;
            this.CHUNK_SIZE = src.CHUNK_SIZE;
            this.CHUNK_SIZE_LOG2 = src.CHUNK_SIZE_LOG2;
            this.height = src.height;
            this.length = src.length;
            this.root = src.root;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public short get$DEFAULT_VALUE();
        
        public short set$DEFAULT_VALUE(short val);
        
        public short postInc$DEFAULT_VALUE();
        
        public short postDec$DEFAULT_VALUE();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.arrays.shortArray._Static {
            public short get$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.shortArray._Static._Impl) fetch()).
                  get$DEFAULT_VALUE();
            }
            
            public short set$DEFAULT_VALUE(short val) {
                return ((fabric.lang.arrays.shortArray._Static._Impl) fetch()).
                  set$DEFAULT_VALUE(val);
            }
            
            public short postInc$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.shortArray._Static._Impl) fetch()).
                  postInc$DEFAULT_VALUE();
            }
            
            public short postDec$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.shortArray._Static._Impl) fetch()).
                  postDec$DEFAULT_VALUE();
            }
            
            public _Proxy(fabric.lang.arrays.shortArray._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.arrays.shortArray._Static $instance;
            
            static {
                fabric.
                  lang.
                  arrays.
                  shortArray.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.arrays.shortArray._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.arrays.shortArray._Static._Impl.class);
                $instance = (fabric.lang.arrays.shortArray._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.arrays.shortArray._Static {
            public short get$DEFAULT_VALUE() { return this.DEFAULT_VALUE; }
            
            public short set$DEFAULT_VALUE(short val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DEFAULT_VALUE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public short postInc$DEFAULT_VALUE() {
                short tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((short) (tmp + 1));
                return tmp;
            }
            
            public short postDec$DEFAULT_VALUE() {
                short tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((short) (tmp - 1));
                return tmp;
            }
            
            private short DEFAULT_VALUE;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeShort(this.DEFAULT_VALUE);
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
                this.DEFAULT_VALUE = in.readShort();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.arrays.shortArray._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm86 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled89 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff87 = 1;
                        boolean $doBackoff88 = true;
                        boolean $retry82 = true;
                        boolean $keepReads83 = false;
                        $label80: for (boolean $commit81 = false; !$commit81;
                                       ) {
                            if ($backoffEnabled89) {
                                if ($doBackoff88) {
                                    if ($backoff87 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff87));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e84) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff87 < 5000) $backoff87 *= 2;
                                }
                                $doBackoff88 = $backoff87 <= 32 ||
                                                 !$doBackoff88;
                            }
                            $commit81 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.lang.arrays.shortArray._Static.
                                      _Proxy.
                                      $instance.
                                      set$DEFAULT_VALUE((short) 0);
                                }
                                catch (final fabric.worker.
                                         RetryException $e84) {
                                    throw $e84;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e84) {
                                    throw $e84;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e84) {
                                    throw $e84;
                                }
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e84) {
                                    throw $e84;
                                }
                                catch (final Throwable $e84) {
                                    $tm86.getCurrentLog().checkRetrySignal();
                                    throw $e84;
                                }
                            }
                            catch (final fabric.worker.RetryException $e84) {
                                $commit81 = false;
                                continue $label80;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e84) {
                                $commit81 = false;
                                $retry82 = false;
                                $keepReads83 = $e84.keepReads;
                                if ($tm86.checkForStaleObjects()) {
                                    $retry82 = true;
                                    $keepReads83 = false;
                                    continue $label80;
                                }
                                fabric.common.TransactionID $currentTid85 =
                                  $tm86.getCurrentTid();
                                if ($e84.tid == null ||
                                      !$e84.tid.isDescendantOf($currentTid85)) {
                                    throw $e84;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e84);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e84) {
                                $commit81 = false;
                                fabric.common.TransactionID $currentTid85 =
                                  $tm86.getCurrentTid();
                                if ($e84.tid.isDescendantOf($currentTid85))
                                    continue $label80;
                                if ($currentTid85.parent != null) {
                                    $retry82 = false;
                                    throw $e84;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e84) {
                                $commit81 = false;
                                if ($tm86.checkForStaleObjects())
                                    continue $label80;
                                fabric.common.TransactionID $currentTid85 =
                                  $tm86.getCurrentTid();
                                if ($e84.tid.isDescendantOf($currentTid85)) {
                                    $retry82 = true;
                                }
                                else if ($currentTid85.parent != null) {
                                    $retry82 = false;
                                    throw $e84;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
                            }
                            catch (final Throwable $e84) {
                                $commit81 = false;
                                if ($tm86.checkForStaleObjects())
                                    continue $label80;
                                $retry82 = false;
                                throw new fabric.worker.AbortException($e84);
                            }
                            finally {
                                if ($commit81) {
                                    fabric.common.TransactionID $currentTid85 =
                                      $tm86.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e84) {
                                        $commit81 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e84) {
                                        $commit81 = false;
                                        $retry82 = false;
                                        $keepReads83 = $e84.keepReads;
                                        if ($tm86.checkForStaleObjects()) {
                                            $retry82 = true;
                                            $keepReads83 = false;
                                            continue $label80;
                                        }
                                        if ($e84.tid ==
                                              null ||
                                              !$e84.tid.isDescendantOf(
                                                          $currentTid85))
                                            throw $e84;
                                        throw new fabric.worker.
                                                UserAbortException($e84);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e84) {
                                        $commit81 = false;
                                        $currentTid85 = $tm86.getCurrentTid();
                                        if ($currentTid85 != null) {
                                            if ($e84.tid.equals(
                                                           $currentTid85) ||
                                                  !$e84.tid.isDescendantOf(
                                                              $currentTid85)) {
                                                throw $e84;
                                            }
                                        }
                                    }
                                } else if ($keepReads83) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit81) {
                                    {  }
                                    if ($retry82) { continue $label80; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 49, 54, -85, -75, 20,
    -128, -94, -14, -58, -32, 8, 126, 58, 36, -48, -51, -25, -101, 32, -123,
    -96, -14, 36, -109, -9, -35, -53, -38, 62, -127, 124, 49 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
