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
    
    public static final byte[] $classHash = new byte[] { 94, -81, 119, 75, -30,
    -83, -57, 116, 38, 9, -26, -85, -99, -87, -103, -78, -51, -59, 22, -47, -39,
    -16, -44, -88, -19, 104, 6, 126, 80, 7, -14, -80 };
    public static final java.lang.String jlc$CompilerVersion$fabil = "0.3.0";
    public static final long jlc$SourceLastModified$fabil = 1525719795000L;
    public static final java.lang.String jlc$ClassType$fabil =
      "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";
}
