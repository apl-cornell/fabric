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
                        long $backoff87 = 1;
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
                                    if ($backoff87 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff87 =
                                          java.lang.Math.
                                            min(
                                              $backoff87 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff88 = $backoff87 <= 32 ||
                                                 !$doBackoff88;
                            }
                            $commit81 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.lang.arrays.shortArray._Static._Proxy.
                                  $instance.
                                  set$DEFAULT_VALUE((short) 0);
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
                            catch (final Throwable $e84) {
                                $commit81 = false;
                                $retry82 = false;
                                if ($tm86.inNestedTxn()) {
                                    $keepReads83 = true;
                                }
                                throw $e84;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid85 =
                                  $tm86.getCurrentTid();
                                if ($commit81) {
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
                                } else {
                                    if (!$tm86.inNestedTxn() &&
                                          $tm86.checkForStaleObjects()) {
                                        $retry82 = true;
                                        $keepReads83 = false;
                                    }
                                    if ($keepReads83) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e84) {
                                            $currentTid85 = $tm86.getCurrentTid();
                                            if ($currentTid85 != null &&
                                                  ($e84.tid.equals($currentTid85) || !$e84.tid.isDescendantOf($currentTid85))) {
                                                throw $e84;
                                            } else {
                                                $retry82 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
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
    
    public static final byte[] $classHash = new byte[] { -118, 12, -125, 45, 14,
    -12, -80, -30, -118, 58, -59, -128, -83, -8, -50, 24, -125, 2, -2, 64, 15,
    -117, -38, -1, -43, 15, 126, -8, -122, -83, -31, -90 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
