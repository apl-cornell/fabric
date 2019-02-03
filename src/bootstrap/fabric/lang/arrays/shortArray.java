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
    
    public static final byte[] $classHash = new byte[] { -88, -61, 31, 99, -8,
    -5, -56, 40, -57, 30, -59, 86, 102, -65, -57, -82, -100, -59, -93, 121, 92,
    63, 2, 86, -104, -13, 101, -28, -33, 7, 42, 49 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
