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
    
    public static final byte[] $classHash = new byte[] { 73, 77, 82, 67, -100,
    -32, -108, -106, 100, 13, 106, 49, 94, -102, 56, 104, -21, 49, -12, 113,
    -16, 7, 125, 103, 103, 36, -119, -102, 18, -49, 124, 72 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
