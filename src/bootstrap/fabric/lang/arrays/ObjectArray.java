package fabric.lang.arrays;

import fabric.lang.*;
import fabric.lang.security.*;
import fabric.worker.*;
import fabric.worker.remote.*;
import java.lang.*;
import fabric.worker.Store;
import fabric.lang.arrays.internal._ObjectArray;

/**
 * <p>
 * This class implements a resizable array using
 * fabric.lang.arrays.internal._ObjectArray as a primitive. The smaller array
 * pieces are arranged as a tree so as to support efficient indexing operations.
 * This version of ResizableArray assumes elements are Object instances.
 * </p>
 * <p>
 * Possible optimizations:
 * <ol>
 * <li>Convert the length to a base 128 representation will make it easier to
 * take logs then.</li>
 * </ol>
 * </p>
 * <p>
 * Bugs:
 * <ol>
 * <li>If the array is indeed of _ObjectArray instance, then things could get
 * messed up. We're assuming for now that this case won't arise.</li>
 * </ol>
 * </p>
 * 
 * @author kvikram
 */
public interface ObjectArray extends fabric.lang.Object {
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
    
    public java.lang.Class get$proxyType();
    
    public java.lang.Class set$proxyType(java.lang.Class val);
    
    public fabric.lang.arrays.internal._ObjectArray get$root();
    
    public fabric.lang.arrays.internal._ObjectArray set$root(fabric.lang.arrays.internal._ObjectArray val);
    
    /**
   * Creates a new object array at the given Store with the given length.
   * 
   * @param store
   *                The store on which to allocate the array.
   * @param length
   *                The length of the array.
   */
    public fabric.lang.arrays.ObjectArray fabric$lang$arrays$ObjectArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, java.lang.Class proxyType,
      int length);
    
    public fabric.lang.arrays.ObjectArray fabric$lang$arrays$ObjectArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, java.lang.Class proxyType,
      int length, int CHUNK_SIZE_LOG2);
    
    public fabric.lang.Object $initLabels();
    
    public int getLength();
    
    public void setLength(int newSize);
    
    public fabric.lang.Object get(int i);
    
    public fabric.lang.Object set(int i, fabric.lang.Object data);
    
    public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.arrays.ObjectArray {
        public int get$CHUNK_SIZE() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              get$CHUNK_SIZE();
        }
        
        public int set$CHUNK_SIZE(int val) {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              set$CHUNK_SIZE(val);
        }
        
        public int postInc$CHUNK_SIZE() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              postInc$CHUNK_SIZE();
        }
        
        public int postDec$CHUNK_SIZE() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              postDec$CHUNK_SIZE();
        }
        
        public int get$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              get$CHUNK_SIZE_LOG2();
        }
        
        public int set$CHUNK_SIZE_LOG2(int val) {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              set$CHUNK_SIZE_LOG2(val);
        }
        
        public int postInc$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              postInc$CHUNK_SIZE_LOG2();
        }
        
        public int postDec$CHUNK_SIZE_LOG2() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              postDec$CHUNK_SIZE_LOG2();
        }
        
        public int get$height() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).get$height(
                                                                      );
        }
        
        public int set$height(int val) {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).set$height(
                                                                      val);
        }
        
        public int postInc$height() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              postInc$height();
        }
        
        public int postDec$height() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              postDec$height();
        }
        
        public int get$length() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).get$length(
                                                                      );
        }
        
        public int set$length(int val) {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).set$length(
                                                                      val);
        }
        
        public int postInc$length() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              postInc$length();
        }
        
        public int postDec$length() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              postDec$length();
        }
        
        public java.lang.Class get$proxyType() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              get$proxyType();
        }
        
        public java.lang.Class set$proxyType(java.lang.Class val) {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).
              set$proxyType(val);
        }
        
        public fabric.lang.arrays.internal._ObjectArray get$root() {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).get$root();
        }
        
        public fabric.lang.arrays.internal._ObjectArray set$root(
          fabric.lang.arrays.internal._ObjectArray val) {
            return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).set$root(
                                                                      val);
        }
        
        public fabric.lang.arrays.ObjectArray fabric$lang$arrays$ObjectArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          java.lang.Class arg3, int arg4) {
            return ((fabric.lang.arrays.ObjectArray) fetch()).
              fabric$lang$arrays$ObjectArray$(arg1, arg2, arg3, arg4);
        }
        
        public fabric.lang.arrays.ObjectArray fabric$lang$arrays$ObjectArray$(
          fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
          java.lang.Class arg3, int arg4, int arg5) {
            return ((fabric.lang.arrays.ObjectArray) fetch()).
              fabric$lang$arrays$ObjectArray$(arg1, arg2, arg3, arg4, arg5);
        }
        
        public int getLength() {
            return ((fabric.lang.arrays.ObjectArray) fetch()).getLength();
        }
        
        public void setLength(int arg1) {
            ((fabric.lang.arrays.ObjectArray) fetch()).setLength(arg1);
        }
        
        public fabric.lang.Object get(int arg1) {
            return ((fabric.lang.arrays.ObjectArray) fetch()).get(arg1);
        }
        
        public fabric.lang.Object set(int arg1, fabric.lang.Object arg2) {
            return ((fabric.lang.arrays.ObjectArray) fetch()).set(arg1, arg2);
        }
        
        public _Proxy(ObjectArray._Impl impl) { super(impl); }
        
        public _Proxy(fabric.worker.Store store, long onum) {
            super(store, onum);
        }
    }
    
    public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.arrays.ObjectArray {
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
        
        public java.lang.Class get$proxyType() { return this.proxyType; }
        
        public java.lang.Class set$proxyType(java.lang.Class val) {
            fabric.worker.transaction.TransactionManager tm =
              fabric.worker.transaction.TransactionManager.getInstance();
            boolean transactionCreated = tm.registerWrite(this);
            this.proxyType = val;
            if (transactionCreated) tm.commitTransaction();
            return val;
        }
        
        private java.lang.Class proxyType;
        
        public fabric.lang.arrays.internal._ObjectArray get$root() {
            fabric.worker.transaction.TransactionManager.getInstance().
              registerRead(this);
            return this.root;
        }
        
        public fabric.lang.arrays.internal._ObjectArray set$root(
          fabric.lang.arrays.internal._ObjectArray val) {
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
        private fabric.lang.arrays.internal._ObjectArray root;
        
        /**
   * Creates a new object array at the given Store with the given length.
   * 
   * @param store
   *                The store on which to allocate the array.
   * @param length
   *                The length of the array.
   */
        public fabric.lang.arrays.ObjectArray fabric$lang$arrays$ObjectArray$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy,
          java.lang.Class proxyType, int length) {
            fabric$lang$arrays$ObjectArray$(updateLabel, accessPolicy,
                                            proxyType, length, 8);
            return (fabric.lang.arrays.ObjectArray) this.$getProxy();
        }
        
        public fabric.lang.arrays.ObjectArray fabric$lang$arrays$ObjectArray$(
          fabric.lang.security.Label updateLabel,
          fabric.lang.security.ConfPolicy accessPolicy,
          java.lang.Class proxyType, int length, int CHUNK_SIZE_LOG2) {
            this.set$CHUNK_SIZE_LOG2((int) CHUNK_SIZE_LOG2);
            this.set$CHUNK_SIZE((int) (1 << CHUNK_SIZE_LOG2));
            this.set$proxyType(proxyType);
            this.set$$updateLabel(updateLabel);
            this.set$$accessPolicy(accessPolicy);
            fabric$lang$Object$();
            this.set$length((int) length);
            this.set$height((int)
                              ((fabric.lang.arrays.ObjectArray._Impl)
                                 this.fetch()).getHeight(length));
            if (this.get$height() == 1)
                this.
                  set$root(
                    (fabric.lang.arrays.internal._ObjectArray)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        ((fabric.lang.arrays.internal._ObjectArray)
                           new fabric.lang.arrays.internal._ObjectArray._Impl(
                             this.$getStore()).$getProxy()).
                            fabric$lang$arrays$internal$_ObjectArray$(
                              this.get$$updateLabel(), this.get$$accessPolicy(),
                              proxyType, this.get$CHUNK_SIZE())));
            else
                this.
                  set$root(
                    (fabric.lang.arrays.internal._ObjectArray)
                      fabric.lang.Object._Proxy.
                      $getProxy(
                        ((fabric.lang.arrays.internal._ObjectArray)
                           new fabric.lang.arrays.internal._ObjectArray._Impl(
                             this.$getStore()).$getProxy()).
                            fabric$lang$arrays$internal$_ObjectArray$(
                              this.get$$updateLabel(), this.get$$accessPolicy(),
                              fabric.lang.arrays.internal._ObjectArray.class,
                              this.get$CHUNK_SIZE())));
            return (fabric.lang.arrays.ObjectArray) this.$getProxy();
        }
        
        public fabric.lang.Object $initLabels() {
            return (fabric.lang.arrays.ObjectArray) this.$getProxy();
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
                (fabric.lang.arrays.internal._ObjectArray)
                  fabric.lang.Object._Proxy.
                  $getProxy(
                    ((fabric.lang.arrays.internal._ObjectArray)
                       new fabric.lang.arrays.internal._ObjectArray._Impl(
                         this.$getStore()).$getProxy()).
                        fabric$lang$arrays$internal$_ObjectArray$(
                          this.get$$updateLabel(), this.get$$accessPolicy(),
                          this.get$proxyType(), this.get$CHUNK_SIZE())));
        }
        
        public void setLength(int newSize) {
            if (newSize < 0) throw new java.lang.NegativeArraySizeException();
            if (newSize == 0)
                ((fabric.lang.arrays.ObjectArray._Impl) this.fetch()).
                  setZeroLength();
            int newHeight = ((fabric.lang.arrays.ObjectArray._Impl)
                               this.fetch()).getHeight(newSize);
            int difference = newHeight - this.get$height();
            this.set$height((int) newHeight);
            this.set$length((int) newSize);
            if (difference > 0) {
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
                for (int count = 0;
                     count < -difference; count++) {
                    fabric.lang.arrays.internal._ObjectArray rootArray =
                      (fabric.lang.arrays.internal._ObjectArray)
                        fabric.lang.Object._Proxy.$getProxy(
                                                    this.get$root().get(0));
                    this.set$root(rootArray);
                }
                fabric.lang.arrays.internal._ObjectArray curNode =
                  this.get$root();
                int curIndex = newSize - 1;
                int curHeight = newHeight;
                int counter = newHeight - 1;
                int firstDigit;
                while (curHeight >= 1 &&
                         !fabric.lang.Object._Proxy.idEquals(curNode, null)) {
                    firstDigit = curIndex >> counter *
                                   this.get$CHUNK_SIZE_LOG2();
                    for (int count = firstDigit + 1;
                         count < this.get$CHUNK_SIZE(); count++) {
                        curNode.set(count, null);
                    }
                    curNode =
                      (fabric.lang.arrays.internal._ObjectArray)
                        fabric.lang.Object._Proxy.$getProxy(
                                                    curNode.get(firstDigit));
                    curIndex = curIndex &
                                 (1 << counter * this.get$CHUNK_SIZE_LOG2()) -
                                 1;
                    counter -= this.get$CHUNK_SIZE_LOG2();
                    curHeight--;
                }
            }
        }
        
        public fabric.lang.Object get(int i) {
            if (i >= this.get$length())
                throw new java.lang.ArrayIndexOutOfBoundsException();
            fabric.lang.arrays.internal._ObjectArray node = this.get$root();
            int level = this.get$height();
            int c = this.get$CHUNK_SIZE_LOG2();
            int counter = (level - 1) * c;
            int firstDigit;
            while (level > 1) {
                firstDigit = i >> counter;
                node =
                  (fabric.lang.arrays.internal._ObjectArray)
                    fabric.lang.Object._Proxy.$getProxy(node.get(firstDigit));
                if (fabric.lang.Object._Proxy.idEquals(node, null)) {
                    return fabric.lang.arrays.ObjectArray._Static._Proxy.$instance.
                      get$DEFAULT_VALUE();
                }
                i = i & (1 << counter) - 1;
                counter -= c;
                level--;
            }
            return node.get(i);
        }
        
        public fabric.lang.Object set(int i, fabric.lang.Object data) {
            if (i >= this.get$length())
                throw new java.lang.ArrayIndexOutOfBoundsException();
            fabric.lang.arrays.internal._ObjectArray node = this.get$root();
            int level = this.get$height();
            int c = this.get$CHUNK_SIZE_LOG2();
            int counter = (level - 1) * c;
            int firstDigit;
            fabric.lang.Object nextObject;
            while (level > 1) {
                firstDigit = i >> counter;
                nextObject = node.get(firstDigit);
                if (fabric.lang.Object._Proxy.idEquals(nextObject, null)) {
                    if (level == 2)
                        nextObject =
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
                                    this.get$proxyType(),
                                    this.get$CHUNK_SIZE()));
                    else
                        nextObject =
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
                                    this.get$CHUNK_SIZE()));
                    node.set(firstDigit, nextObject);
                }
                node = (fabric.lang.arrays.internal._ObjectArray)
                         fabric.lang.Object._Proxy.$getProxy(nextObject);
                i = i & (1 << counter) - 1;
                counter -= c;
                level--;
            }
            return node.set(i, data);
        }
        
        public _Impl(fabric.worker.Store $location) { super($location); }
        
        protected fabric.lang.Object._Proxy $makeProxy() {
            return new fabric.lang.arrays.ObjectArray._Proxy(this);
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
            $writeInline(out, this.proxyType);
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
            this.proxyType = (java.lang.Class) in.readObject();
            this.root =
              (fabric.lang.arrays.internal._ObjectArray)
                $readRef(fabric.lang.arrays.internal._ObjectArray._Proxy.class,
                         (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                         intraStoreRefs, interStoreRefs);
        }
        
        public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
            super.$copyAppStateFrom(other);
            fabric.lang.arrays.ObjectArray._Impl src =
              (fabric.lang.arrays.ObjectArray._Impl) other;
            this.CHUNK_SIZE = src.CHUNK_SIZE;
            this.CHUNK_SIZE_LOG2 = src.CHUNK_SIZE_LOG2;
            this.height = src.height;
            this.length = src.length;
            this.proxyType = src.proxyType;
            this.root = src.root;
        }
    }
    
    interface _Static extends fabric.lang.Object, Cloneable {
        public fabric.lang.Object get$DEFAULT_VALUE();
        
        public fabric.lang.Object set$DEFAULT_VALUE(fabric.lang.Object val);
        
        final class _Proxy extends fabric.lang.Object._Proxy
          implements fabric.lang.arrays.ObjectArray._Static {
            public fabric.lang.Object get$DEFAULT_VALUE() {
                return ((fabric.lang.arrays.ObjectArray._Static._Impl) fetch()).
                  get$DEFAULT_VALUE();
            }
            
            public fabric.lang.Object set$DEFAULT_VALUE(
              fabric.lang.Object val) {
                return ((fabric.lang.arrays.ObjectArray._Static._Impl) fetch()).
                  set$DEFAULT_VALUE(val);
            }
            
            public _Proxy(fabric.lang.arrays.ObjectArray._Static._Impl impl) {
                super(impl);
            }
            
            public _Proxy(fabric.worker.Store store, long onum) {
                super(store, onum);
            }
            
            public static final fabric.lang.arrays.ObjectArray._Static
              $instance;
            
            static {
                fabric.
                  lang.
                  arrays.
                  ObjectArray.
                  _Static.
                  _Impl
                  impl =
                  (fabric.lang.arrays.ObjectArray._Static._Impl)
                    fabric.lang.Object._Static._Proxy.
                    $makeStaticInstance(
                      fabric.lang.arrays.ObjectArray._Static._Impl.class);
                $instance = (fabric.lang.arrays.ObjectArray._Static)
                              impl.$getProxy();
                impl.$init();
            }
        }
        
        class _Impl extends fabric.lang.Object._Impl
          implements fabric.lang.arrays.ObjectArray._Static {
            public fabric.lang.Object get$DEFAULT_VALUE() {
                return this.DEFAULT_VALUE;
            }
            
            public fabric.lang.Object set$DEFAULT_VALUE(
              fabric.lang.Object val) {
                fabric.worker.transaction.TransactionManager tm =
                  fabric.worker.transaction.TransactionManager.getInstance();
                boolean transactionCreated = tm.registerWrite(this);
                this.DEFAULT_VALUE = val;
                if (transactionCreated) tm.commitTransaction();
                return val;
            }
            
            private fabric.lang.Object DEFAULT_VALUE;
            
            public void $serialize(java.io.ObjectOutput out,
                                   java.util.List refTypes,
                                   java.util.List intraStoreRefs,
                                   java.util.List interStoreRefs)
                  throws java.io.IOException {
                super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
                $writeRef($getStore(), this.DEFAULT_VALUE, refTypes, out,
                          intraStoreRefs, interStoreRefs);
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
                this.DEFAULT_VALUE = (fabric.lang.Object)
                                       $readRef(fabric.lang.Object._Proxy.class,
                                                (fabric.common.RefTypeEnum)
                                                  refTypes.next(), in, store,
                                                intraStoreRefs, interStoreRefs);
            }
            
            public _Impl(fabric.worker.Store store) { super(store); }
            
            protected fabric.lang.Object._Proxy $makeProxy() {
                return new fabric.lang.arrays.ObjectArray._Static._Proxy(this);
            }
            
            private void $init() {
                {
                    {
                        fabric.worker.transaction.TransactionManager $tm6 =
                          fabric.worker.transaction.TransactionManager.
                          getInstance();
                        boolean $backoffEnabled9 =
                          fabric.worker.Worker.getWorker(
                                                 ).config.txRetryBackoff;
                        long $backoff7 = 1;
                        boolean $doBackoff8 = true;
                        boolean $retry2 = true;
                        boolean $keepReads3 = false;
                        $label0: for (boolean $commit1 = false; !$commit1; ) {
                            if ($backoffEnabled9) {
                                if ($doBackoff8) {
                                    if ($backoff7 > 32) {
                                        while (true) {
                                            try {
                                                java.lang.Thread.
                                                  sleep(
                                                    java.lang.Math.
                                                        round(
                                                          java.lang.Math.random(
                                                                           ) *
                                                              $backoff7));
                                                break;
                                            }
                                            catch (java.lang.
                                                     InterruptedException $e4) {
                                                
                                            }
                                        }
                                    }
                                    if ($backoff7 <
                                          fabric.worker.Worker.getWorker().
                                            config.
                                            maxBackoff)
                                        $backoff7 =
                                          java.lang.Math.
                                            min(
                                              $backoff7 * 2,
                                              fabric.worker.Worker.getWorker().
                                                config.
                                                maxBackoff);
                                }
                                $doBackoff8 = $backoff7 <= 32 || !$doBackoff8;
                            }
                            $commit1 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                fabric.lang.arrays.ObjectArray._Static._Proxy.
                                  $instance.
                                  set$DEFAULT_VALUE(null);
                            }
                            catch (final fabric.worker.RetryException $e4) {
                                $commit1 = false;
                                continue $label0;
                            }
                            catch (fabric.worker.
                                     TransactionAbortingException $e4) {
                                $commit1 = false;
                                $retry2 = false;
                                $keepReads3 = $e4.keepReads;
                                fabric.common.TransactionID $currentTid5 =
                                  $tm6.getCurrentTid();
                                if ($e4.tid == null ||
                                      !$e4.tid.isDescendantOf($currentTid5)) {
                                    throw $e4;
                                }
                                throw new fabric.worker.UserAbortException($e4);
                            }
                            catch (final fabric.worker.
                                     TransactionRestartingException $e4) {
                                $commit1 = false;
                                fabric.common.TransactionID $currentTid5 =
                                  $tm6.getCurrentTid();
                                if ($e4.tid.isDescendantOf($currentTid5))
                                    continue $label0;
                                if ($currentTid5.parent != null) {
                                    $retry2 = false;
                                    throw $e4;
                                }
                                throw new InternalError(
                                        "Something is broken with " +
                                            "transaction management. Got a signal to restart a " +
                                            "different transaction than the one being managed.");
                            }
                            catch (final Throwable $e4) {
                                $commit1 = false;
                                $retry2 = false;
                                if ($tm6.inNestedTxn()) { $keepReads3 = true; }
                                throw $e4;
                            }
                            finally {
                                fabric.common.TransactionID $currentTid5 =
                                  $tm6.getCurrentTid();
                                if ($commit1) {
                                    try {
                                        fabric.worker.transaction.TransactionManager.
                                          getInstance().commitTransaction();
                                    }
                                    catch (final fabric.worker.
                                             AbortException $e4) {
                                        $commit1 = false;
                                    }
                                    catch (final fabric.worker.
                                             TransactionAbortingException $e4) {
                                        $commit1 = false;
                                        $retry2 = false;
                                        $keepReads3 = $e4.keepReads;
                                        if ($e4.tid ==
                                              null ||
                                              !$e4.tid.isDescendantOf(
                                                         $currentTid5))
                                            throw $e4;
                                        throw new fabric.worker.
                                                UserAbortException($e4);
                                    }
                                    catch (final fabric.worker.
                                             TransactionRestartingException $e4) {
                                        $commit1 = false;
                                        $currentTid5 = $tm6.getCurrentTid();
                                        if ($currentTid5 != null) {
                                            if ($e4.tid.equals($currentTid5) ||
                                                  !$e4.tid.isDescendantOf(
                                                             $currentTid5)) {
                                                throw $e4;
                                            }
                                        }
                                    }
                                } else {
                                    if (!$tm6.inNestedTxn() &&
                                          $tm6.checkForStaleObjects()) {
                                        $retry2 = true;
                                        $keepReads3 = false;
                                    }
                                    if ($keepReads3) {
                                        try {
                                            fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                        }
                                        catch (final fabric.worker.TransactionRestartingException $e4) {
                                            $currentTid5 = $tm6.getCurrentTid();
                                            if ($currentTid5 != null &&
                                                  ($e4.tid.equals($currentTid5) || !$e4.tid.isDescendantOf($currentTid5))) {
                                                throw $e4;
                                            } else {
                                                $retry2 = true;
                                            }
                                        }
                                    } else {
                                        fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
                                    }
                                }
                                if (!$commit1) {
                                    {  }
                                    if ($retry2) { continue $label0; }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public static final byte[] $classHash = new byte[] { 88, 113, -30, 21, 114,
    118, -78, 119, -77, -1, -10, 115, -99, 47, -13, -59, 76, 49, -45, 10, 98,
    -81, -17, 27, -111, 39, -61, -74, 87, -86, -115, -127 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
