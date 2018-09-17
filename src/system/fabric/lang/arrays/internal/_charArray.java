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

public interface _charArray extends Object {
  _charArray fabric$lang$arrays$internal$_charArray$(Label updateLabel,
      ConfPolicy accessPolicy, int length);

  _charArray fabric$lang$arrays$internal$_charArray$(Label updateLabel,
      ConfPolicy accessPolicy, char[] value);

  int get$length();

  char set(int i, char value);

  char get(int i);

  public static class _Impl extends Object._Impl
      implements _charArray, _InternalArrayImpl {
    private char[] value;

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
      value = new char[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readChar();
    }

    /**
     * Creates a new char array at the given Store with the given length.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param length
     *          The length of the array.
     */
    @Override
    public _charArray fabric$lang$arrays$internal$_charArray$(Label updateLabel,
        ConfPolicy accessPolicy, int length) {
      fabric$lang$arrays$internal$_charArray$(updateLabel, accessPolicy,
          new char[length]);
      return this;
    }

    /**
     * Creates a new char array at the given Store using the given backing
     * array.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    @Override
    public _charArray fabric$lang$arrays$internal$_charArray$(Label updateLabel,
        ConfPolicy accessPolicy, char[] value) {
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
    public char get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return this.value[i];
    }

    @Override
    public char set(int i, char value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      char result = this.value[i] = value;
      if (transactionCreated)
        TransactionManager.getInstance().commitTransaction();
      return result;
    }

    @Override
    public void $copyAppStateFrom(Object._Impl other) {
      super.$copyAppStateFrom(other);
      _charArray._Impl src = (_charArray._Impl) other;
      value = src.value;
    }

    @Override
    public void cloneValues() {
      value = value.clone();
    }

    @Override
    protected _charArray._Proxy $makeProxy() {
      return new _charArray._Proxy(this);
    }

    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intraStoreRefs, List<Pair<String, Long>> interStoreRefs)
        throws IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      out.writeInt(value.length);
      for (char element : value)
        out.writeChar(element);
    }

    @Override
    public Object $initLabels() {
      // Handled by initializers.
      return $getProxy();
    }
  }

  public static class _Proxy extends Object._Proxy implements _charArray {

    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public _Proxy(_charArray._Impl impl) {
      super(impl);
    }

    @Override
    public _charArray fabric$lang$arrays$internal$_charArray$(Label updateLabel,
        ConfPolicy accessPolicy, int length) {
      return ((_charArray) fetch()).fabric$lang$arrays$internal$_charArray$(
          updateLabel, accessPolicy, length);
    }

    @Override
    public _charArray fabric$lang$arrays$internal$_charArray$(Label updateLabel,
        ConfPolicy accessPolicy, char[] value) {
      return ((_charArray) fetch()).fabric$lang$arrays$internal$_charArray$(
          updateLabel, accessPolicy, value);
    }

    @Override
    public int get$length() {
      return ((_charArray) fetch()).get$length();
    }

    @Override
    public char get(int i) {
      return ((_charArray) fetch()).get(i);
    }

    @Override
    public char set(int i, char value) {
      return ((_charArray) fetch()).set(i, value);
    }
  }
}
