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

public interface _byteArray extends Object {
  _byteArray fabric$lang$arrays$internal$_byteArray$(Label updateLabel,
      ConfPolicy accessPolicy, int length);

  _byteArray fabric$lang$arrays$internal$_byteArray$(Label updateLabel,
      ConfPolicy accessPolicy, byte[] value);

  int get$length();

  byte set(int i, byte value);

  byte get(int i);

  public static class _Impl extends Object._Impl
      implements _byteArray, _InternalArrayImpl {
    private byte[] value;

    public _Impl(Store store) {
      super(store);
    }

    /**
     * Used for deserializing.
     */
    public _Impl(Store store, long onum, int version, Store labelStore,
        long labelOnum, Store accessPolicyStore, long accessPolicyOnum,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intraStoreRefs,
        Iterator<Pair<String, Long>> interStoreRefs)
        throws IOException, ClassNotFoundException {
      super(store, onum, version, labelStore, labelOnum, accessPolicyStore,
          accessPolicyOnum, in, refTypes, intraStoreRefs, interStoreRefs);
      value = new byte[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readByte();
    }

    /**
     * Creates a new byte array at the given Store with the given length.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param length
     *          The length of the array.
     */
    @Override
    public _byteArray fabric$lang$arrays$internal$_byteArray$(Label updateLabel,
        ConfPolicy accessPolicy, int length) {
      fabric$lang$arrays$internal$_byteArray$(updateLabel, accessPolicy,
          new byte[length]);
      return this;
    }

    /**
     * Creates a new byte array at the given Store using the given backing
     * array.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    @Override
    public _byteArray fabric$lang$arrays$internal$_byteArray$(Label updateLabel,
        ConfPolicy accessPolicy, byte[] value) {
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
    public byte get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return this.value[i];
    }

    @Override
    public byte set(int i, byte value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      byte result = this.value[i] = value;
      if (transactionCreated)
        TransactionManager.getInstance().commitTransaction();
      return result;
    }

    @Override
    public void $copyAppStateFrom(Object._Impl other) {
      super.$copyAppStateFrom(other);
      _byteArray._Impl src = (_byteArray._Impl) other;
      value = src.value;
    }

    @Override
    public void cloneValues() {
      value = value.clone();
    }

    @Override
    protected _byteArray._Proxy $makeProxy() {
      return new _byteArray._Proxy(this);
    }

    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intraStoreRefs, List<Pair<String, Long>> interStoreRefs)
        throws IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      out.writeInt(value.length);
      for (byte element : value)
        out.writeByte(element);
    }

    @Override
    public Object $initLabels() {
      // Handled by initializers.
      return $getProxy();
    }
  }

  public static class _Proxy extends Object._Proxy implements _byteArray {

    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public _Proxy(_byteArray._Impl impl) {
      super(impl);
    }

    @Override
    public _byteArray fabric$lang$arrays$internal$_byteArray$(Label updateLabel,
        ConfPolicy accessPolicy, int length) {
      return ((_byteArray) fetch()).fabric$lang$arrays$internal$_byteArray$(
          updateLabel, accessPolicy, length);
    }

    @Override
    public _byteArray fabric$lang$arrays$internal$_byteArray$(Label updateLabel,
        ConfPolicy accessPolicy, byte[] value) {
      return ((_byteArray) fetch()).fabric$lang$arrays$internal$_byteArray$(
          updateLabel, accessPolicy, value);
    }

    @Override
    public int get$length() {
      return ((_byteArray) fetch()).get$length();
    }

    @Override
    public byte get(int i) {
      return ((_byteArray) fetch()).get(i);
    }

    @Override
    public byte set(int i, byte value) {
      return ((_byteArray) fetch()).set(i, value);
    }
  }
}
