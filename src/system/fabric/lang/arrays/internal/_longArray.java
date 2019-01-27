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
import fabric.worker.metrics.ImmutableObjectSet;
import fabric.worker.transaction.TransactionManager;

public interface _longArray extends Object {
  _longArray fabric$lang$arrays$internal$_longArray$(Label updateLabel,
      ConfPolicy accessPolicy, int length);

  _longArray fabric$lang$arrays$internal$_longArray$(Label updateLabel,
      ConfPolicy accessPolicy, long[] value);

  int get$length();

  long set(int i, long value);

  long get(int i);

  public static class _Impl extends Object._Impl
      implements _longArray, _InternalArrayImpl {
    private long[] value;

    public _Impl(Store store) {
      super(store);
    }

    /**
     * Used for deserializing.
     */
    public _Impl(Store store, long onum, int version,
        ImmutableObjectSet associates, long expiry, Store labelStore,
        long labelOnum, Store accessPolicyStore, long accessPolicyOnum,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intraStoreRefs,
        Iterator<Pair<String, Long>> interStoreRefs)
        throws IOException, ClassNotFoundException {
      super(store, onum, version, associates, expiry, labelStore, labelOnum,
          accessPolicyStore, accessPolicyOnum, in, refTypes, intraStoreRefs,
          interStoreRefs);
      value = new long[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readLong();
    }

    /**
     * Creates a new long array at the given Store with the given length.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param length
     *          The length of the array.
     */
    @Override
    public _longArray fabric$lang$arrays$internal$_longArray$(Label updateLabel,
        ConfPolicy accessPolicy, int length) {
      fabric$lang$arrays$internal$_longArray$(updateLabel, accessPolicy,
          new long[length]);
      return this;
    }

    /**
     * Creates a new long array at the given Store using the given backing
     * array.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    @Override
    public _longArray fabric$lang$arrays$internal$_longArray$(Label updateLabel,
        ConfPolicy accessPolicy, long[] value) {
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
    public long get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return this.value[i];
    }

    @Override
    public long set(int i, long value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      long result = this.value[i] = value;
      if (transactionCreated)
        TransactionManager.getInstance().commitTransaction();
      return result;
    }

    @Override
    public void $copyAppStateFrom(Object._Impl other) {
      super.$copyAppStateFrom(other);
      _longArray._Impl src = (_longArray._Impl) other;
      value = src.value;
    }

    @Override
    public void cloneValues() {
      value = value.clone();
    }

    @Override
    protected _longArray._Proxy $makeProxy() {
      return new _longArray._Proxy(this);
    }

    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intraStoreRefs, List<Pair<String, Long>> interStoreRefs)
        throws IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      out.writeInt(value.length);
      for (long element : value)
        out.writeLong(element);
    }

    @Override
    public Object $initLabels() {
      // Handled by initializers.
      return $getProxy();
    }
  }

  public static class _Proxy extends Object._Proxy implements _longArray {

    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public _Proxy(_longArray._Impl impl) {
      super(impl);
    }

    @Override
    public _longArray fabric$lang$arrays$internal$_longArray$(Label updateLabel,
        ConfPolicy accessPolicy, int length) {
      return ((_longArray) fetch()).fabric$lang$arrays$internal$_longArray$(
          updateLabel, accessPolicy, length);
    }

    @Override
    public _longArray fabric$lang$arrays$internal$_longArray$(Label updateLabel,
        ConfPolicy accessPolicy, long[] value) {
      return ((_longArray) fetch()).fabric$lang$arrays$internal$_longArray$(
          updateLabel, accessPolicy, value);
    }

    @Override
    public int get$length() {
      return ((_longArray) fetch()).get$length();
    }

    @Override
    public long get(int i) {
      return ((_longArray) fetch()).get(i);
    }

    @Override
    public long set(int i, long value) {
      return ((_longArray) fetch()).set(i, value);
    }
  }
}
