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
                        int $backoff7 = 1;
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
                                    if ($backoff7 < 5000) $backoff7 *= 2;
                                }
                                $doBackoff8 = $backoff7 <= 32 || !$doBackoff8;
                            }
                            $commit1 = true;
                            fabric.worker.transaction.TransactionManager.
                              getInstance().startTransaction();
                            try {
                                try {
                                    fabric.lang.arrays.ObjectArray._Static.
                                      _Proxy.
                                      $instance.
                                      set$DEFAULT_VALUE(null);
                                }
                                catch (final fabric.worker.RetryException $e4) {
                                    throw $e4;
                                }
                                catch (final fabric.worker.
                                         TransactionAbortingException $e4) {
                                    throw $e4;
                                }
                                catch (final fabric.worker.
                                         TransactionRestartingException $e4) {
                                    throw $e4;
                                }
                                catch (final Throwable $e4) {
                                    $tm6.getCurrentLog().checkRetrySignal();
                                    throw $e4;
                                }
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
                                if ($tm6.checkForStaleObjects()) {
                                    $retry2 = true;
                                    $keepReads3 = false;
                                    continue $label0;
                                }
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
                                if ($tm6.checkForStaleObjects())
                                    continue $label0;
                                $retry2 = false;
                                throw new fabric.worker.AbortException($e4);
                            }
                            finally {
                                if ($commit1) {
                                    fabric.common.TransactionID $currentTid5 =
                                      $tm6.getCurrentTid();
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
                                        if ($tm6.checkForStaleObjects()) {
                                            $retry2 = true;
                                            $keepReads3 = false;
                                            continue $label0;
                                        }
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
                                } else if ($keepReads3) {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransactionUpdates();
                                } else {
                                    fabric.worker.transaction.TransactionManager.getInstance().abortTransaction();
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
    
    public static final byte[] $classHash = new byte[] { 107, 120, 33, 123, -17,
    13, 74, -43, 94, -15, -36, -12, 36, -16, 5, -113, -64, -48, 17, -34, 72, 18,
    -62, -65, 17, 95, 117, 91, -6, 1, -85, -103 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
