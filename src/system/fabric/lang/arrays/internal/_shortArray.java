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
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.treaties.TreatySet;
import fabric.worker.transaction.TransactionManager;

public interface _shortArray extends Object {
  _shortArray fabric$lang$arrays$internal$_shortArray$(Label updateLabel,
      ConfPolicy accessPolicy, int length);

  _shortArray fabric$lang$arrays$internal$_shortArray$(Label updateLabel,
      ConfPolicy accessPolicy, short[] value);

  int get$length();

  short set(int i, short value);

  short get(int i);

  public static class _Impl extends Object._Impl
      implements _shortArray, _InternalArrayImpl {
    private short[] value;

    public _Impl(Store store) {
      super(store);
    }

    /**
     * Used for deserializing.
     */
    public _Impl(Store store, long onum, int version,
        ImmutableObjectSet associates, ImmutableObserverSet observers,
        TreatySet treaties, Store labelStore, long labelOnum,
        Store accessPolicyStore, long accessPolicyOnum, ObjectInput in,
        Iterator<RefTypeEnum> refTypes, Iterator<Long> intraStoreRefs,
        Iterator<Pair<String, Long>> interStoreRefs)
        throws IOException, ClassNotFoundException {
      super(store, onum, version, associates, observers, treaties, labelStore,
          labelOnum, accessPolicyStore, accessPolicyOnum, in, refTypes,
          intraStoreRefs, interStoreRefs);
      value = new short[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readShort();
    }

    /**
     * Creates a new short array at the given Store with the given length.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param length
     *          The length of the array.
     */
    @Override
    public _shortArray fabric$lang$arrays$internal$_shortArray$(
        Label updateLabel, ConfPolicy accessPolicy, int length) {
      fabric$lang$arrays$internal$_shortArray$(updateLabel, accessPolicy,
          new short[length]);
      return this;
    }

    /**
     * Creates a new short array at the given Store using the given backing
     * array.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    @Override
    public _shortArray fabric$lang$arrays$internal$_shortArray$(
        Label updateLabel, ConfPolicy accessPolicy, short[] value) {
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
    public short get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return this.value[i];
    }

    @Override
    public short set(int i, short value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      short result = this.value[i] = value;
      if (transactionCreated)
        TransactionManager.getInstance().commitTransaction();
      return result;
    }

    @Override
    public void $copyAppStateFrom(Object._Impl other) {
      super.$copyAppStateFrom(other);
      _shortArray._Impl src = (_shortArray._Impl) other;
      value = src.value;
    }

    @Override
    public void cloneValues() {
      value = value.clone();
    }

    @Override
    protected _shortArray._Proxy $makeProxy() {
      return new _shortArray._Proxy(this);
    }

    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intraStoreRefs, List<Pair<String, Long>> interStoreRefs)
        throws IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      out.writeInt(value.length);
      for (short element : value)
        out.writeShort(element);
    }

    @Override
    public Object $initLabels() {
      // Handled by initializers.
      return $getProxy();
    }
  }

  public static class _Proxy extends Object._Proxy implements _shortArray {

    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public _Proxy(_shortArray._Impl impl) {
      super(impl);
    }

    @Override
    public _shortArray fabric$lang$arrays$internal$_shortArray$(
        Label updateLabel, ConfPolicy accessPolicy, int length) {
      return ((_shortArray) fetch()).fabric$lang$arrays$internal$_shortArray$(
          updateLabel, accessPolicy, length);
    }

    @Override
    public _shortArray fabric$lang$arrays$internal$_shortArray$(
        Label updateLabel, ConfPolicy accessPolicy, short[] value) {
      return ((_shortArray) fetch()).fabric$lang$arrays$internal$_shortArray$(
          updateLabel, accessPolicy, value);
    }

    @Override
    public int get$length() {
      return ((_shortArray) fetch()).get$length();
    }

    @Override
    public short get(int i) {
      return ((_shortArray) fetch()).get(i);
    }

    @Override
    public short set(int i, short value) {
      return ((_shortArray) fetch()).set(i, value);
    }
  }
}
