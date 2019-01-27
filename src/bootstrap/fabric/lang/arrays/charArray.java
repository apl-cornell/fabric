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
                                catch (final fabric.worker.metrics.
                                         LockConflictException $e34) {
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
                            catch (final fabric.worker.metrics.
                                     LockConflictException $e34) {
                                $commit31 = false;
                                if ($tm36.checkForStaleObjects())
                                    continue $label30;
                                fabric.common.TransactionID $currentTid35 =
                                  $tm36.getCurrentTid();
                                if ($e34.tid.isDescendantOf($currentTid35)) {
                                    $retry32 = true;
                                }
                                else if ($currentTid35.parent != null) {
                                    $retry32 = false;
                                    throw $e34;
                                }
                                else {
                                    throw new InternalError(
                                            "Something is broken with transaction " +
                                                "management. Got a signal for a lock conflict in a different " +
                                                "transaction than the one being managed.");
                                }
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
    
    public static final byte[] $classHash = new byte[] { 83, 32, -76, 27, 62,
    122, 109, 121, 13, -26, -3, -9, 30, -23, 26, 64, 40, 91, 118, -40, -18, -76,
    -12, -42, -27, -83, 33, -59, 54, -50, 80, -104 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
