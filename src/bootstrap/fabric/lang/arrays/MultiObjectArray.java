package fabric.lang.arrays;

/**
 * <p>
 * Implements a multi-dimensional object array by representing it as an array
 * of arrays of arrays ... depending on the number of dimensions
 *
 * Uses ObjectArray to represent each individual array
 * </p>
 *
 * @author kvikram
 */
public interface MultiObjectArray extends fabric.lang.Object {

  public int get$dims();

  public int set$dims(int val);

  public int postInc$dims();

  public int postDec$dims();

  public fabric.lang.arrays.intArray get$lengths();

  public fabric.lang.arrays.intArray set$lengths(
      fabric.lang.arrays.intArray val);

  public fabric.lang.arrays.ObjectArray get$firstDim();

  public fabric.lang.arrays.ObjectArray set$firstDim(
      fabric.lang.arrays.ObjectArray val);

  public java.lang.Class get$proxyType();

  public java.lang.Class set$proxyType(java.lang.Class val);

  public int get$CHUNK_SIZE_LOG2();

  public int set$CHUNK_SIZE_LOG2(int val);

  public int postInc$CHUNK_SIZE_LOG2();

  public int postDec$CHUNK_SIZE_LOG2();

  public fabric.lang.arrays.MultiObjectArray fabric$lang$arrays$MultiObjectArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, java.lang.Class proxyType,
      int dims, fabric.lang.arrays.intArray lengths);

  public fabric.lang.arrays.MultiObjectArray fabric$lang$arrays$MultiObjectArray$(
      fabric.lang.security.Label updateLabel,
      fabric.lang.security.ConfPolicy accessPolicy, java.lang.Class proxyType,
      int dims, fabric.lang.arrays.intArray lengths, int CHUNK_SIZE_LOG2);

  public fabric.lang.Object $initLabels();

  public fabric.lang.arrays.intArray getLengths();

  public fabric.lang.Object get(fabric.lang.arrays.intArray index);

  public fabric.lang.Object set(fabric.lang.arrays.intArray index,
      fabric.lang.Object data);

  public static class _Proxy extends fabric.lang.Object._Proxy
      implements fabric.lang.arrays.MultiObjectArray {

    public int get$dims() {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch()).get$dims();
    }

    public int set$dims(int val) {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch())
          .set$dims(val);
    }

    public int postInc$dims() {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch())
          .postInc$dims();
    }

    public int postDec$dims() {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch())
          .postDec$dims();
    }

    public fabric.lang.arrays.intArray get$lengths() {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch())
          .get$lengths();
    }

    public fabric.lang.arrays.intArray set$lengths(
        fabric.lang.arrays.intArray val) {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch())
          .set$lengths(val);
    }

    public fabric.lang.arrays.ObjectArray get$firstDim() {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch())
          .get$firstDim();
    }

    public fabric.lang.arrays.ObjectArray set$firstDim(
        fabric.lang.arrays.ObjectArray val) {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch())
          .set$firstDim(val);
    }

    public java.lang.Class get$proxyType() {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch())
          .get$proxyType();
    }

    public java.lang.Class set$proxyType(java.lang.Class val) {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch())
          .set$proxyType(val);
    }

    public int get$CHUNK_SIZE_LOG2() {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch())
          .get$CHUNK_SIZE_LOG2();
    }

    public int set$CHUNK_SIZE_LOG2(int val) {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch())
          .set$CHUNK_SIZE_LOG2(val);
    }

    public int postInc$CHUNK_SIZE_LOG2() {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch())
          .postInc$CHUNK_SIZE_LOG2();
    }

    public int postDec$CHUNK_SIZE_LOG2() {
      return ((fabric.lang.arrays.MultiObjectArray._Impl) fetch())
          .postDec$CHUNK_SIZE_LOG2();
    }

    public native fabric.lang.arrays.MultiObjectArray fabric$lang$arrays$MultiObjectArray$(
        fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
        java.lang.Class arg3, int arg4, fabric.lang.arrays.intArray arg5);

    public native fabric.lang.arrays.MultiObjectArray fabric$lang$arrays$MultiObjectArray$(
        fabric.lang.security.Label arg1, fabric.lang.security.ConfPolicy arg2,
        java.lang.Class arg3, int arg4, fabric.lang.arrays.intArray arg5,
        int arg6);

    public native fabric.lang.arrays.intArray getLengths();

    public native fabric.lang.Object get(fabric.lang.arrays.intArray arg1);

    public native fabric.lang.Object set(fabric.lang.arrays.intArray arg1,
        fabric.lang.Object arg2);

    public _Proxy(MultiObjectArray._Impl impl) {
      super(impl);
    }

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

  public static final class _Impl extends fabric.lang.Object._Impl
      implements fabric.lang.arrays.MultiObjectArray {

    public int get$dims() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.dims;
    }

    public int set$dims(int val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.dims = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    public int postInc$dims() {
      int tmp = this.get$dims();
      this.set$dims((int) (tmp + 1));
      return tmp;
    }

    public int postDec$dims() {
      int tmp = this.get$dims();
      this.set$dims((int) (tmp - 1));
      return tmp;
    }

    private int dims;

    public fabric.lang.arrays.intArray get$lengths() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.lengths;
    }

    public fabric.lang.arrays.intArray set$lengths(
        fabric.lang.arrays.intArray val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.lengths = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.lang.arrays.intArray lengths;

    public fabric.lang.arrays.ObjectArray get$firstDim() {
      fabric.worker.transaction.TransactionManager.getInstance()
          .registerRead(this);
      return this.firstDim;
    }

    public fabric.lang.arrays.ObjectArray set$firstDim(
        fabric.lang.arrays.ObjectArray val) {
      fabric.worker.transaction.TransactionManager tm =
          fabric.worker.transaction.TransactionManager.getInstance();
      boolean transactionCreated = tm.registerWrite(this);
      this.firstDim = val;
      if (transactionCreated) tm.commitTransaction();
      return val;
    }

    private fabric.lang.arrays.ObjectArray firstDim;

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

    private java.lang.Class proxyType;

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

    public native fabric.lang.arrays.MultiObjectArray fabric$lang$arrays$MultiObjectArray$(
        fabric.lang.security.Label updateLabel,
        fabric.lang.security.ConfPolicy accessPolicy, java.lang.Class proxyType,
        int dims, fabric.lang.arrays.intArray lengths);

    public native fabric.lang.arrays.MultiObjectArray fabric$lang$arrays$MultiObjectArray$(
        fabric.lang.security.Label updateLabel,
        fabric.lang.security.ConfPolicy accessPolicy, java.lang.Class proxyType,
        int dims, fabric.lang.arrays.intArray lengths, int CHUNK_SIZE_LOG2);

    public native fabric.lang.Object $initLabels();

    public native fabric.lang.arrays.intArray getLengths();

    public native fabric.lang.Object get(fabric.lang.arrays.intArray index);

    public native fabric.lang.Object set(fabric.lang.arrays.intArray index,
        fabric.lang.Object data);

    public _Impl(fabric.worker.Store $location) {
      super($location);
    }

    protected fabric.lang.Object._Proxy $makeProxy() {
      return new fabric.lang.arrays.MultiObjectArray._Proxy(this);
    }

    public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
        java.util.List intraStoreRefs, java.util.List interStoreRefs)
        throws java.io.IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      out.writeInt(this.dims);
      $writeRef($getStore(), this.lengths, refTypes, out, intraStoreRefs,
          interStoreRefs);
      $writeRef($getStore(), this.firstDim, refTypes, out, intraStoreRefs,
          interStoreRefs);
      $writeInline(out, this.proxyType);
      out.writeInt(this.CHUNK_SIZE_LOG2);
    }

    public _Impl(fabric.worker.Store store, long onum, int version,
        fabric.worker.metrics.ImmutableObserverSet observers,
        fabric.worker.Store labelStore, long labelOnum,
        fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
        java.io.ObjectInput in, java.util.Iterator refTypes,
        java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
        throws java.io.IOException, java.lang.ClassNotFoundException {
      super(store, onum, version, observers, labelStore, labelOnum,
          accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
          interStoreRefs);
      this.dims = in.readInt();
      this.lengths = (fabric.lang.arrays.intArray) $readRef(
          fabric.lang.arrays.intArray._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
      this.firstDim = (fabric.lang.arrays.ObjectArray) $readRef(
          fabric.lang.arrays.ObjectArray._Proxy.class,
          (fabric.common.RefTypeEnum) refTypes.next(), in, store,
          intraStoreRefs, interStoreRefs);
      this.proxyType = (java.lang.Class) in.readObject();
      this.CHUNK_SIZE_LOG2 = in.readInt();
    }

    public void $copyAppStateFrom(fabric.lang.Object._Impl other) {
      super.$copyAppStateFrom(other);
      fabric.lang.arrays.MultiObjectArray._Impl src =
          (fabric.lang.arrays.MultiObjectArray._Impl) other;
      this.dims = src.dims;
      this.lengths = src.lengths;
      this.firstDim = src.firstDim;
      this.proxyType = src.proxyType;
      this.CHUNK_SIZE_LOG2 = src.CHUNK_SIZE_LOG2;
    }
  }

  interface _Static extends fabric.lang.Object, Cloneable {
    final class _Proxy extends fabric.lang.Object._Proxy
        implements fabric.lang.arrays.MultiObjectArray._Static {

      public _Proxy(fabric.lang.arrays.MultiObjectArray._Static._Impl impl) {
        super(impl);
      }

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }

      public static final fabric.lang.arrays.MultiObjectArray._Static $instance;

      static {
        fabric.lang.arrays.MultiObjectArray._Static._Impl impl =
            (fabric.lang.arrays.MultiObjectArray._Static._Impl) fabric.lang.Object._Static._Proxy
                .$makeStaticInstance(
                    fabric.lang.arrays.MultiObjectArray._Static._Impl.class);
        $instance =
            (fabric.lang.arrays.MultiObjectArray._Static) impl.$getProxy();
        impl.$init();
      }
    }

    class _Impl extends fabric.lang.Object._Impl
        implements fabric.lang.arrays.MultiObjectArray._Static {

      public void $serialize(java.io.ObjectOutput out, java.util.List refTypes,
          java.util.List intraStoreRefs, java.util.List interStoreRefs)
          throws java.io.IOException {
        super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      }

      public _Impl(fabric.worker.Store store, long onum, int version,
          fabric.worker.metrics.ImmutableObserverSet observers,
          fabric.worker.Store labelStore, long labelOnum,
          fabric.worker.Store accessPolicyStore, long accessPolicyOnum,
          java.io.ObjectInput in, java.util.Iterator refTypes,
          java.util.Iterator intraStoreRefs, java.util.Iterator interStoreRefs)
          throws java.io.IOException, java.lang.ClassNotFoundException {
        super(store, onum, version, observers, labelStore, labelOnum,
            accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
            interStoreRefs);
      }

      public _Impl(fabric.worker.Store store) {
        super(store);
      }

      protected fabric.lang.Object._Proxy $makeProxy() {
        return new fabric.lang.arrays.MultiObjectArray._Static._Proxy(this);
      }

      private void $init() {
      }
    }

  }

}
