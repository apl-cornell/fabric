package fabric.lang.arrays.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.common.RefTypeEnum;
import fabric.common.VersionWarranty;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;

public interface _shortArray extends Object {
  int get$length();

  short set(int i, short value);

  short get(int i);

  public static class _Impl extends Object._Impl implements _shortArray,
      _InternalArrayImpl {
    private short[] value;

    /**
     * Creates a new short array at the given Store with the given length.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param length
     *          The length of the array.
     */
    public _Impl(Store store, Label updateLabel, ConfPolicy accessPolicy,
        int length) {
      this(store, updateLabel, accessPolicy, new short[length]);
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
    public _Impl(Store store, Label updateLabel, ConfPolicy accessPolicy,
        short[] value) {
      super(store);
      this.value = value;

      set$$updateLabel(updateLabel);
      set$$accessPolicy(accessPolicy);
    }

    /**
     * Used for deserializing.
     */
    public _Impl(Store store, long onum, int version, VersionWarranty warranty,
        long label, long accessLabel, ObjectInput in,
        Iterator<RefTypeEnum> refTypes, Iterator<Long> intraStoreRefs,
        Iterator<Pair<String, Long>> interStoreRefs) throws IOException,
        ClassNotFoundException {
      super(store, onum, version, warranty, label, accessLabel, in, refTypes,
          intraStoreRefs, interStoreRefs);
      value = new short[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readShort();
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
      return $getProxy();
    }

    @Override
    public _shortArray $makeSemiDeepCopy(LongSet oldSet,
        LongKeyMap<Object> oldToNew) {
      _shortArray._Impl copy = null;
      if (oldToNew.containsKey(this.$getOnum())) {
        copy = (_shortArray._Impl) oldToNew.get(this.$getOnum());
      } else {
        copy = (_shortArray._Impl) this.$makeBlankCopy().fetch();
        oldToNew.put(this.$getOnum(), copy);
      }
      super.$makeSemiDeepCopy(oldSet, oldToNew);
      copy.value = new short[this.value.length];
      for (int i = 0; i < this.value.length; i++) {
        copy.value[i] = this.value[i];
      }
      return copy.$makeProxy();
    }

    @Override
    public _shortArray $makeBlankCopy() {
      return new _shortArray._Impl(this.$getStore(),
                                   this.get$$updateLabel(),
                                   this.get$$accessPolicy(),
                                   this.value.length).$makeProxy();
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
