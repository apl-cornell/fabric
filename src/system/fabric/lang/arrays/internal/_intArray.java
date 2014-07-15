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

public interface _intArray extends Object {
  int get$length();

  int set(int i, int value);

  int get(int i);

  public static class _Impl extends Object._Impl implements _intArray,
  _InternalArrayImpl {
    private int[] value;

    /**
     * Creates a new int array at the given Store with the given length.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param length
     *          The length of the array.
     */
    public _Impl(Store store, Label label, ConfPolicy accessPolicy, int length) {
      this(store, label, accessPolicy, new int[length]);
    }

    /**
     * Creates a new int array at the given Store using the given backing array.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    public _Impl(Store store, Label updateLabel, ConfPolicy accessPolicy,
        int[] value) {
      super(store);
      this.value = value;

      set$$updateLabel(updateLabel);
      set$$accessPolicy(accessPolicy);
    }

    /**
     * Used for deserializing.
     */
    public _Impl(Store store, long onum, int version, long expiry, long label,
        long accessLabel, ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intraStoreRefs,
        Iterator<Pair<String, Long>> interStoreRefs) throws IOException,
        ClassNotFoundException {
      super(store, onum, version, expiry, label, accessLabel, in, refTypes,
          intraStoreRefs, interStoreRefs);
      value = new int[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readInt();
    }

    @Override
    public int get$length() {
      TransactionManager.getInstance().registerRead(this);
      return value.length;
    }

    @Override
    public int get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return this.value[i];
    }

    @Override
    public int set(int i, int value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      int result = this.value[i] = value;
      if (transactionCreated)
        TransactionManager.getInstance().commitTransaction();
      return result;
    }

    @Override
    public void $copyAppStateFrom(Object._Impl other) {
      super.$copyAppStateFrom(other);
      _intArray._Impl src = (_intArray._Impl) other;
      value = src.value;
    }

    @Override
    public void cloneValues() {
      value = value.clone();
    }

    @Override
    protected _intArray._Proxy $makeProxy() {
      return new _intArray._Proxy(this);
    }

    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intraStoreRefs, List<Pair<String, Long>> interStoreRefs)
            throws IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      out.writeInt(value.length);
      for (int element : value)
        out.writeInt(element);
    }

    @Override
    public Object $initLabels() {
      return $getProxy();
    }
  }

  public static class _Proxy extends Object._Proxy implements _intArray {

    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public _Proxy(_intArray._Impl impl) {
      super(impl);
    }

    @Override
    public int get$length() {
      return ((_intArray) fetch()).get$length();
    }

    @Override
    public int get(int i) {
      return ((_intArray) fetch()).get(i);
    }

    @Override
    public int set(int i, int value) {
      return ((_intArray) fetch()).set(i, value);
    }
  }
}
