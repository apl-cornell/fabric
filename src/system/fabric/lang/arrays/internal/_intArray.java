package fabric.lang.arrays.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;
import fabric.common.RefTypeEnum;
import fabric.common.util.Pair;
import fabric.lang.Object;
import fabric.lang.security.Label;

public interface _intArray extends Object {
  int get$length();

  int set(int i, int value);

  int get(int i);

  public static class _Impl extends Object._Impl implements _intArray {
    private int[] value;

    /**
     * Creates a new int array at the given Store with the given length.
     * 
     * @param store
     *                The store on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public _Impl(Store store, Label label, int length) {
      this(store, label, new int[length]);
    }

    /**
     * Creates a new int array at the given Store using the given backing
     * array.
     * 
     * @param store
     *                The store on which to allocate the array.
     * @param value
     *                The backing array to use.
     */
    public _Impl(Store store, Label label, int[] value) {
      super(store, label);
      this.value = value;
    }

    /**
     * Used for deserializing.
     */
    public _Impl(Store store, long onum, int version, long expiry, long label,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intraStoreRefs) throws IOException,
        ClassNotFoundException {
      super(store, onum, version, expiry, label, in, refTypes, intraStoreRefs);
      value = new int[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readInt();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.intArray#getLength()
     */
    public int get$length() {
      TransactionManager.getInstance().registerRead(this);
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.intArray#get(int)
     */
    public int get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.intArray#set(int, int)
     */
    public int set(int i, int value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      int result = this.value[i] = value;
      if (transactionCreated) TransactionManager.getInstance().commitTransaction();
      return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object._Impl#$copyAppStateFrom(fabric.lang.Object._Impl)
     */
    @Override
    public void $copyAppStateFrom(Object._Impl other) {
      super.$copyAppStateFrom(other);
      _intArray._Impl src = (_intArray._Impl) other;
      value = new int[src.value.length];
      System.arraycopy(src.value, 0, value, 0, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object._Impl#$makeProxy()
     */
    @Override
    protected _intArray._Proxy $makeProxy() {
      return new _intArray._Proxy(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object._Impl#$serialize(java.io.ObjectOutput)
     */
    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intraStoreRefs, List<Pair<String, Long>> interStoreRefs)
        throws IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      out.writeInt(value.length);
      for (int i = 0; i < value.length; i++)
        out.writeInt(value[i]);
    }
  }

  public static class _Proxy extends Object._Proxy implements _intArray {

    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public _Proxy(_intArray._Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.intArray#getLength()
     */
    public int get$length() {
      return ((_intArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.intArray#get(int)
     */
    public int get(int i) {
      return ((_intArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.intArray#set(int, int)
     */
    public int set(int i, int value) {
      return ((_intArray) fetch()).set(i, value);
    }
  }
}
