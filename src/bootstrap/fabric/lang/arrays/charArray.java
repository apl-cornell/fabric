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
    
    public static final byte[] $classHash = new byte[] { 65, -6, -83, 59, 114,
    -52, -38, 123, 7, -83, 85, -100, -118, -4, 33, 2, -108, 57, -122, 43, 115,
    -62, -84, -83, 86, -64, -114, -64, -88, 34, -128, -122 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
