package fabric.lang.arrays;

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

  public fabric.lang.arrays.internal._ObjectArray set$root(
      fabric.lang.arrays.internal._ObjectArray val);

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
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).get$CHUNK_SIZE();
    }

    public int set$CHUNK_SIZE(int val) {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch())
          .set$CHUNK_SIZE(val);
    }

    public int postInc$CHUNK_SIZE() {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch())
          .postInc$CHUNK_SIZE();
    }

    public int postDec$CHUNK_SIZE() {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch())
          .postDec$CHUNK_SIZE();
    }

    public int get$CHUNK_SIZE_LOG2() {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch())
          .get$CHUNK_SIZE_LOG2();
    }

    public int set$CHUNK_SIZE_LOG2(int val) {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch())
          .set$CHUNK_SIZE_LOG2(val);
    }

    public int postInc$CHUNK_SIZE_LOG2() {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch())
          .postInc$CHUNK_SIZE_LOG2();
    }

    public int postDec$CHUNK_SIZE_LOG2() {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch())
          .postDec$CHUNK_SIZE_LOG2();
    }

    public int get$height() {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).get$height();
    }

    public int set$height(int val) {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).set$height(val);
    }

    public int postInc$height() {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).postInc$height();
    }

    public int postDec$height() {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).postDec$height();
    }

    public int get$length() {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).get$length();
    }

    public int set$length(int val) {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).set$length(val);
    }

    public int postInc$length() {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).postInc$length();
    }

    public int postDec$length() {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).postDec$length();
    }

    public java.lang.Class get$proxyType() {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).get$proxyType();
    }

    public java.lang.Class set$proxyType(java.lang.Class val) {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch())
          .set$proxyType(val);
    }

    public fabric.lang.arrays.internal._ObjectArray get$root() {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).get$root();
    }

    public fabric.lang.arrays.internal._ObjectArray set$root(
        fabric.lang.arrays.internal._ObjectArray val) {
      return ((fabric.lang.arrays.ObjectArray._Impl) fetch()).set$root(val);
    }

    public native fabric.lang.arrays.ObjectArray fabric$lang$arrays$ObjectArray$(
        fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
        java.lang.Class arg3, int arg4);

    public native fabric.lang.arrays.ObjectArray fabric$lang$arrays$ObjectArray$(
        fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
        java.lang.Class arg3, int arg4, int arg5);

    public native int getLength();

    public native void setLength(int arg1);

    public native fabric.lang.Object get(int arg1);

    public native fabric.lang.Object set(int arg1, fabric.lang.Object arg2);

    public _Proxy(ObjectArray._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.arrays.ObjectArray {

    public int get$CHUNK_SIZE() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.CHUNK_SIZE;
    }

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

    /**
     * The number of elements in each little array. Dependent on the MTU?
     * Analogous to a block in a file system. Also directly determines the
     fanout.
     * We always need it to be a power of 2.
     */
    private int CHUNK_SIZE;

    public int get$CHUNK_SIZE_LOG2() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.CHUNK_SIZE_LOG2;
    }

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
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
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
     * (determining the branching factor) and the number of expected
     elements in
     * the bigger array
     */
    private int height;

    public int get$length() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
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
     * The number of expected elements in this big array Can be modified
     even
     * after an instance has been created
     */
    private int length;

    public java.lang.Class get$proxyType() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.proxyType;
    }

    public java.lang.Class set$proxyType(java.lang.Class val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.proxyType = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    /**
     * The class representing the proxy type for the array elements.
     */
    private java.lang.Class proxyType;

    public fabric.lang.arrays.internal._ObjectArray get$root() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
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
     * The root of the tree of little arrays. The runtime type of root is a
     Fabric
     * array of Fabric Objects. Each object in the array is either a further
     array
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
    public native fabric.lang.arrays.ObjectArray fabric$lang$arrays$ObjectArray$(
        fabric.lang.security.Label updateLabel,
        fabric.lang.security.ConfPolicy accessPolicy, java.lang.Class proxyType,
        int length);

    public native fabric.lang.arrays.ObjectArray fabric$lang$arrays$ObjectArray$(
        fabric.lang.security.Label updateLabel,
        fabric.lang.security.ConfPolicy accessPolicy, java.lang.Class proxyType,
        int length, int CHUNK_SIZE_LOG2);

    public native fabric.lang.Object $initLabels();

    public native int getLength();

    /**
     * Ceiling(log_{CHUNK_SIZE}(length)).  (Except returns 1 if length <=
     1.)
     * This basically returns the height of the tree for a given array
     length.
     */
    private native int getHeight(int length);

    private native void setZeroLength();

    public native void setLength(int newSize);

    public native fabric.lang.Object get(int i);

    public native fabric.lang.Object set(int i, fabric.lang.Object data);

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.arrays.ObjectArray._Proxy(this);
    }

    public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
        java.util.List intraStoreRefs, java.util.List interStoreRefs)
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
        fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, long expiry,
        fabric.worker.Store labelStore, long labelOnum,
        fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, associates, treaties, expiry, labelStore, labelOnum,
          accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
          interStoreRefs);
      this.CHUNK_SIZE = in.readInt();
      this.CHUNK_SIZE_LOG2 = in.readInt();
      this.height = in.readInt();
      this.length = in.readInt();
      this.proxyType = (java.lang.Class) in.readObject();
      this.root = (fabric.lang.arrays.internal._ObjectArray) $readRef(
          fabric.lang.arrays.internal._ObjectArray._Proxy.class,
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
        return ((fabric.lang.arrays.ObjectArray._Static._Impl) fetch())
            .get$DEFAULT_VALUE();
      }

      public fabric.lang.Object set$DEFAULT_VALUE(fabric.lang.Object val) {
        return ((fabric.lang.arrays.ObjectArray._Static._Impl) fetch())
            .set$DEFAULT_VALUE(val);
      }

      public _Proxy(fabric.lang.arrays.ObjectArray._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.arrays.ObjectArray._Static $instance;

      static {
        fabric.lang.arrays.ObjectArray._Static._Impl impl =
            (fabric.lang.arrays.ObjectArray._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.arrays.ObjectArray._Static._Impl.class);
        $instance = (fabric.lang.arrays.ObjectArray._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.arrays.ObjectArray._Static {

      public fabric.lang.Object get$DEFAULT_VALUE() {
        fabric.worker.transaction.TransactionManager.getInstance()
            .registerRead(this);
        return this.DEFAULT_VALUE;
      }

      public fabric.lang.Object set$DEFAULT_VALUE(fabric.lang.Object val) {
        fabric.worker.transaction.TransactionManager tm =
            fabric.worker.transaction.TransactionManager.getInstance();
        boolean transactionCreated = tm.registerWrite(this);
        this.DEFAULT_VALUE = val;
        if (transactionCreated) tm.commitTransaction();
        return val;
      }

      private fabric.lang.Object DEFAULT_VALUE;

      public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
          java.util.List intraStoreRefs, java.util.List interStoreRefs)
          throws java.io.IOException {
        super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
        $writeRef($getStore(), this.DEFAULT_VALUE, refTypes, out,
            intraStoreRefs, interStoreRefs);
      }

      public _Impl(fabric.worker.Store store, long onum, int version,
          fabric.worker.metrics.ImmutableObjectSet associates, fabric.worker.metrics.treaties.TreatySet treaties, long expiry,
          fabric.worker.Store labelStore, long labelOnum,
          fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
          java.io.ObjectInput in, java.util.Iterator refTypes,
          java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
          throws java.io.IOException, java.lang.ClassNotFoundException {
        super(store, onum, version, associates, treaties, expiry, labelStore, labelOnum,
            accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
            interStoreRefs);
        this.DEFAULT_VALUE =
            (fabric.lang.Object) $readRef(fabric.lang.Object._Proxy.class,
                (fabric.common.RefTypeEnum) refTypes.next(), in, store,
                intraStoreRefs, interStoreRefs);
      }

      public _Impl(fabric.worker.Store store) {
        super(store);
      }

      protected fabric.lang.Object._Proxy $makeProxy() {
        return new fabric.lang.arrays.ObjectArray._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
