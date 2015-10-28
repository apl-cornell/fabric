package fabric.lang.arrays.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.common.RefTypeEnum;
import fabric.common.util.Pair;
import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;

public interface _floatArray extends Object {
  _floatArray fabric$lang$arrays$internal$_floatArray$(Label updateLabel,
      ConfPolicy accessPolicy, int length);

  _floatArray fabric$lang$arrays$internal$_floatArray$(Label updateLabel,
      ConfPolicy accessPolicy, float[] value);

  int get$length();

  float set(int i, float value);

  float get(int i);

  public static class _Impl extends Object._Impl
      implements _floatArray, _InternalArrayImpl {
    private float[] value;

    public _Impl(Store store) {
      super(store);
    }

    /**
     * Used for deserializing.
     */
    public _Impl(Store store, long onum, int version, long expiry,
        Store labelStore, long labelOnum, Store accessPolicyStore,
        long accessPolicyOnum, ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intraStoreRefs,
        Iterator<Pair<String, Long>> interStoreRefs)
            throws IOException, ClassNotFoundException {
      super(store, onum, version, expiry, labelStore, labelOnum,
          accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
          interStoreRefs);
      value = new float[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readFloat();
    }

    /**
     * Creates a new float array at the given Store with the given length.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param length
     *          The length of the array.
     */
    @Override
    public _floatArray fabric$lang$arrays$internal$_floatArray$(Label updateLabel,
        ConfPolicy accessPolicy, int length) {
      fabric$lang$arrays$internal$_floatArray$(updateLabel, accessPolicy,
          new float[length]);
      return this;
    }

    /**
     * Creates a new float array at the given Store using the given backing
     * array.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    @Override
    public _floatArray fabric$lang$arrays$internal$_floatArray$(Label updateLabel,
        ConfPolicy accessPolicy, float[] value) {
      set$$updateLabel(updateLabel);
      set$$accessPolicy(accessPolicy);
      fabric$lang$Object$();

      this.value = value;
      return this;
    }

    @Override
    public int get$length() {
      TransactionManager.getInstance().registerRead(this);
      return value.length;
    }

    @Override
    public float get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return this.value[i];
    }

    @Override
    public float set(int i, float value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      float result = this.value[i] = value;
      if (transactionCreated)
        TransactionManager.getInstance().commitTransaction();
      return result;
    }

    @Override
    public void $copyAppStateFrom(Object._Impl other) {
      super.$copyAppStateFrom(other);
      _floatArray._Impl src = (_floatArray._Impl) other;
      value = src.value;
    }

    @Override
    public void cloneValues() {
      value = value.clone();
    }

    @Override
    protected _floatArray._Proxy $makeProxy() {
      return new _floatArray._Proxy(this);
    }

    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intraStoreRefs, List<Pair<String, Long>> interStoreRefs)
            throws IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      out.writeInt(value.length);
      for (float element : value)
        out.writeFloat(element);
    }

    @Override
    public Object $initLabels() {
      // Handled by initializers.
      return $getProxy();
    }
  }

  public static class _Proxy extends Object._Proxy implements _floatArray {

    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public _Proxy(_floatArray._Impl impl) {
      super(impl);
    }

    @Override
    public _floatArray fabric$lang$arrays$internal$_floatArray$(Label updateLabel,
        ConfPolicy accessPolicy, int length) {
      return ((_floatArray) fetch()).fabric$lang$arrays$internal$_floatArray$(
          updateLabel, accessPolicy, length);
    }

    @Override
    public _floatArray fabric$lang$arrays$internal$_floatArray$(Label updateLabel,
        ConfPolicy accessPolicy, float[] value) {
      return ((_floatArray) fetch()).fabric$lang$arrays$internal$_floatArray$(
          updateLabel, accessPolicy, value);
    }

    @Override
    public int get$length() {
      return ((_floatArray) fetch()).get$length();
    }

    @Override
    public float get(int i) {
      return ((_floatArray) fetch()).get(i);
    }

    @Override
    public float set(int i, float value) {
      return ((_floatArray) fetch()).set(i, value);
    }
  }
}
