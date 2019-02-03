package fabric.lang.arrays;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Store;
import fabric.lang.arrays.internal._charArray;
import fabric.lang.arrays.internal._ObjectArray;

/**
 * <p>
 * This class implements a resizable array using
 * fabric.lang.arrays.internal._charArray as a primitive. The smaller array
 * pieces are arranged as a tree so as to support efficient indexing operations.
 * </p>
 * <p>
 * This is an array for chars, and is adapted from byteArray.
 * </p>
 * <p>
 * Optimizations:
 * <ol>
 * <li>Remove the call to getProxy in the generated Java file</li>
 * </ol>
 * </p>
 * XXX For simplicity the char arrays are also of CHUNK_SIZE size
 * 
 * @author kvikram
 */
public interface charArray extends fabric.lang.Object {
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
    public fabric.lang.arrays.charArray fabric$lang$arrays$charArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length);
    
    public fabric.lang.arrays.charArray fabric$lang$arrays$charArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, int length,
      int CHUNK_SIZE_LOG2);
    
    public fabric.lang.Object $initLabels();
    
    public int getLength();
    
    public void setLength(int newSize);
    
    public char get(int i);
    
    public char set(int i, char data);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.arrays.charArray {
        public int get$CHUNK_SIZE() {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).
              get$CHUNK_SIZE();
        }
        
        public int set$CHUNK_SIZE(int val) {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).
              set$CHUNK_SIZE(val);
        }
        
        public int postInc$CHUNK_SIZE() {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).
              postInc$CHUNK_SIZE();
        }
        
        public int postDec$CHUNK_SIZE() {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).
              postDec$CHUNK_SIZE();
        }
        
        public int get$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).
              get$CHUNK_SIZE_LOG2();
        }
        
        public int set$CHUNK_SIZE_LOG2(int val) {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).
              set$CHUNK_SIZE_LOG2(val);
        }
        
        public int postInc$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).
              postInc$CHUNK_SIZE_LOG2();
        }
        
        public int postDec$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).
              postDec$CHUNK_SIZE_LOG2();
        }
        
        public int get$height() {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).get$height();
        }
        
        public int set$height(int val) {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).set$height(
                                                                    val);
        }
        
        public int postInc$height() {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).
              postInc$height();
        }
        
        public int postDec$height() {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).
              postDec$height();
        }
        
        public int get$length() {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).get$length();
        }
        
        public int set$length(int val) {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).set$length(
                                                                    val);
        }
        
        public int postInc$length() {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).
              postInc$length();
        }
        
        public int postDec$length() {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).
              postDec$length();
        }
        
        public fabric.lang.Object get$root() {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).get$root();
        }
        
        public fabric.lang.Object set$root(fabric.lang.Object val) {
            return ((fabric.lang.arrays.charArray._Impl) fetch()).set$root(val);
        }
        
        public fabric.lang.arrays.charArray fabric$lang$arrays$charArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3) {
            return ((fabric.lang.arrays.charArray) fetch()).
              fabric$lang$arrays$charArray$(arg1, arg2, arg3);
        }
        
        public fabric.lang.arrays.charArray fabric$lang$arrays$charArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          int arg3, int arg4) {
            return ((fabric.lang.arrays.charArray) fetch()).
              fabric$lang$arrays$charArray$(arg1, arg2, arg3, arg4);
        }
        
        public int getLength() {
            return ((fabric.lang.arrays.charArray) fetch()).getLength();
        }
        
        public void setLength(int arg1) {
            ((fabric.lang.arrays.charArray) fetch()).setLength(arg1);
        }
        
        public char get(int arg1) {
            return ((fabric.lang.arrays.charArray) fetch()).get(arg1);
        }
        
        public char set(int arg1, char arg2) {
            return ((fabric.lang.arrays.charArray) fetch()).set(arg1, arg2);
        }
        
        public _Proxy(charArray._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.arrays.charArray {
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
        public fabric.lang.arrays.charArray fabric$lang$arrays$charArray$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy, int length) {
            fabric$lang$arrays$charArray$(updateLabel, accessPolicy, length, 8);
            return (fabric.lang.arrays.charArray) this.$getProxy();
        }
        
        public fabric.lang.arrays.charArray fabric$lang$arrays$charArray$(
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
                              ((fabric.lang.arrays.charArray._Impl)
                                 this.fetch()).getHeight(length));
            switch (this.get$height()) {
                case 1:
                    this.
                      set$root(
                        (fabric.lang.arrays.internal._charArray)
                          fabric.lang.Object._Proxy.
                          $getProxy(
                            ((fabric.lang.arrays.internal._charArray)
                               new fabric.lang.arrays.internal._charArray._Impl(
                                 this.$getStore()).$getProxy()).
                                fabric$lang$arrays$internal$_charArray$(
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
                                  fabric.lang.arrays.internal._charArray.class,
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
            return (fabric.lang.arrays.charArray) this.$getProxy();
        }
        
        public fabric.lang.Object $initLabels() {
            return (fabric.lang.arrays.charArray) this.$getProxy();
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
                (fabric.lang.arrays.internal._charArray)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.lang.arrays.internal._charArray)
                       new fabric.lang.arrays.internal._charArray._Impl(
                         this.$getStore()).$getProxy()).
                        fabric$lang$arrays$internal$_charArray$(
                          this.get$$updateLabel(), this.get$$accessPolicy(),
                          this.get$CHUNK_SIZE())));
        }
        
        public void setLength(int newSize) {
            if (newSize < 0) throw new java.lang.NegativeArraySizeException();
            if (newSize == 0)
                ((fabric.lang.arrays.charArray._Impl) this.fetch()).
                  setZeroLength();
            int newHeight = ((fabric.lang.arrays.charArray._Impl)
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
                                fabric.lang.arrays.internal._charArray.class,
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
                    fabric.lang.arrays.internal._charArray
                      rootArray =
                      (fabric.lang.arrays.internal._charArray)
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
        
        public char get(int i) {
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
                    return fabric.lang.arrays.charArray._Static._Proxy.$instance.
                      get$DEFAULT_VALUE();
                }
                i = i & (1 << counter) - 1;
                counter -= c;
                level--;
            }
            return ((fabric.lang.arrays.internal._charArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).get(i);
        }
        
        public char set(int i, char data) {
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
                                        fabric.lang.arrays.internal._charArray.class,
                                        this.get$CHUNK_SIZE()));
                            break;
                        case 2:
                            nextObject =
                              (fabric.lang.arrays.internal._charArray)
                                fabric.lang.Object._Proxy.
                                $getProxy(
                                  ((fabric.lang.arrays.internal._charArray)
                                     new fabric.lang.arrays.internal._charArray.
                                       _Impl(this.$getStore()).
                                     $getProxy()).
                                      fabric$lang$arrays$internal$_charArray$(
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
            return ((fabric.lang.arrays.internal._charArray)
                      fabric.lang.Object._Proxy.$getProxy(node)).set(i, data);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.arrays.charArray._Proxy(this);
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
            fabric.lang.arrays.charArray._Impl src =
              (fabric.lang.arrays.charArray._Impl) other;
            this.CHUNK_SIZE = src.CHUNK_SIZE;
            this.CHUNK_SIZE_LOG2 = src.CHUNK_SIZE_LOG2;
            this.height = src.height;
            this.length = src.length;
            this.root = src.root;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public char get$DEFAULT_VALUE();
        
        public char set$DEFAULT_VALUE(char val);
        
        public char postInc$DEFAULT_VALUE();
        
        public char postDec$DEFAULT_VALUE();
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.arrays.charArray._Static {
            public char get$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.charArray._Static._Impl) fetch()).
                  get$DEFAULT_VALUE();
            }
            
            public char set$DEFAULT_VALUE(char val) {
                return ((fabric.lang.arrays.charArray._Static._Impl) fetch()).
                  set$DEFAULT_VALUE(val);
            }
            
            public char postInc$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.charArray._Static._Impl) fetch()).
                  postInc$DEFAULT_VALUE();
            }
            
            public char postDec$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.charArray._Static._Impl) fetch()).
                  postDec$DEFAULT_VALUE();
            }
            
            public _Proxy(fabric.lang.arrays.charArray._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.arrays.charArray._Static $instance;
            
            static {
                fabric.
                  lang.
                  arrays.
                  charArray.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.arrays.charArray._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.arrays.charArray._Static._Impl.class);
                $instance = (fabric.lang.arrays.charArray._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.arrays.charArray._Static {
            public char get$DEFAULT_VALUE() { return this.DEFAULT_VALUE; }
            
            public char set$DEFAULT_VALUE(char val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DEFAULT_VALUE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            public char postInc$DEFAULT_VALUE() {
                char tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((char) (tmp + 1));
                return tmp;
            }
            
            public char postDec$DEFAULT_VALUE() {
                char tmp = this.get$DEFAULT_VALUE();
                this.set$DEFAULT_VALUE((char) (tmp - 1));
                return tmp;
            }
            
            private char DEFAULT_VALUE;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                out.writeChar(this.DEFAULT_VALUE);
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
                this.DEFAULT_VALUE = in.readChar();
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.arrays.charArray._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm36 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled39 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        int $backoff37 = 1;
                        boolean $doBackoff38 = true;
                        boolean $retry32 = true;
                        boolean $keepReads33 = false;
                        $label30: for (boolean $commit31 = false; !$commit31;
                                       ) {
                            if ($backoffEnabled39) {
                                if ($doBackoff38) {
                                    if ($backoff37 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff37));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e34) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff37 < 5000) $backoff37 *= 2;
                                }
                                $doBackoff38 = $backoff37 <= 32 ||
                                                 !$doBackoff38;
                            }
                            $commit31 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.lang.arrays.charArray._Static._Proxy.
                                      $instance.
                                      set$DEFAULT_VALUE((char) 0);
                                }
                                catch (final fabric.worker.
                                         RetryException $e34) {
                                    throw $e34;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e34) {
                                    throw $e34;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e34) {
                                    throw $e34;
                                }
                                catch (final Throwable $e34) {
                                    $tm36.getCurrentLog().checkRetrySignal();
                                    throw $e34;
                                }
                            }
                            catch (final fabric.worker.RetryException $e34) {
                                $commit31 = false;
                                continue $label30;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e34) {
                                $commit31 = false;
                                $retry32 = false;
                                $keepReads33 = $e34.keepReads;
                                if ($tm36.checkForStaleObjects()) {
                                    $retry32 = true;
                                    $keepReads33 = false;
                                    continue $label30;
                                }
                                fabric.common.TransactionID $currentTid35 =
                                  $tm36.getCurrentTid();
                                if ($e34.tid == null ||
                                      !$e34.tid.isDescendantOf($currentTid35)) {
                                    throw $e34;
                                }
                                throw new fabric.worker.UserAbortException(
                                        $e34);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e34) {
                                $commit31 = false;
                                fabric.common.TransactionID $currentTid35 =
                                  $tm36.getCurrentTid();
                                if ($e34.tid.isDescendantOf($currentTid35))
                                    continue $label30;
                                if ($currentTid35.parent != null) {
                                    $retry32 = false;
                                    throw $e34;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e34) {
                                $commit31 = false;
                                if ($tm36.checkForStaleObjects())
                                    continue $label30;
                                $retry32 = false;
                                throw new fabric.worker.AbortException($e34);
                            }
                            finally {
                                if ($commit31) {
                                    fabric.common.TransactionID $currentTid35 =
                                      $tm36.getCurrentTid();
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e34) {
                                        $commit31 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e34) {
                                        $commit31 = false;
                                        $retry32 = false;
                                        $keepReads33 = $e34.keepReads;
                                        if ($tm36.checkForStaleObjects()) {
                                            $retry32 = true;
                                            $keepReads33 = false;
                                            continue $label30;
                                        }
                                        if ($e34.tid ==
                                              null ||
                                              !$e34.tid.isDescendantOf(
                                                          $currentTid35))
                                            throw $e34;
                                        throw new fabric.worker.
                                                UserAbortException($e34);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e34) {
                                        $commit31 = false;
                                        $currentTid35 = $tm36.getCurrentTid();
                                        if ($currentTid35 != null) {
                                            if ($e34.tid.equals(
                                                           $currentTid35) ||
                                                  !$e34.tid.isDescendantOf(
                                                              $currentTid35)) {
                                                throw $e34;
                                            }
                                        }
                                    }
                                } else if ($keepReads33) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                }
                                if (!$commit31) {
                                    {  }
                                    if ($retry32) { continue $label30; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { -27, 103, 11, 69, -91,
    73, 5, 64, -86, 38, -126, -17, 53, -3, 40, 18, 83, -65, -73, -41, -59, -66,
    -37, 70, 60, -56, -108, 25, 66, -56, -77, -85 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
