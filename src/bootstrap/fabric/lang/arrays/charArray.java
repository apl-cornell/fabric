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
                        long $backoff37 = 1;
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
                                    if ($backoff37 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff37 =
                                          java.lang.Math.
                                            min(
                                              $backoff37 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff38 = $backoff37 <= 32 ||
                                                 !$doBackoff38;
                            }
                            $commit31 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.lang.arrays.charArray._Static._Proxy.
                                  $instance.
                                  set$DEFAULT_VALUE((char) 0);
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
                                $retry32 = false;
                                if ($tm36.inNestedTxn()) {
                                    $keepReads33 = true;
                                }
                                throw $e34;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid35 =
                                  $tm36.getCurrentTid();
                                if ($commit31) {
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
                                } else {
                                    if (!$tm36.inNestedTxn() &&
                                          $tm36.checkForStaleObjects()) {
                                        $retry32 = true;
                                        $keepReads33 = false;
                                    }
                                    if ($keepReads33) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e34) {
                                            $currentTid35 = $tm36.getCurrentTid();
                                            if ($currentTid35 != null &&
                                                  ($e34.tid.equals($currentTid35) || !$e34.tid.isDescendantOf($currentTid35))) {
                                                throw $e34;
                                            } else {
                                                $retry32 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
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
    
    public static final byte[] $classHash = new byte[] { -59, 27, 103, 115, -60,
    -24, -42, 126, 123, 62, -92, 89, 74, -31, -13, -61, -37, -117, -114, -82,
    -69, -52, 96, 38, 85, -67, -127, 84, 32, -87, -6, -113 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
