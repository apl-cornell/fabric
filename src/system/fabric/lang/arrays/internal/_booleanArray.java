package fabric.lang.arrays.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.worker.Core;
import fabric.worker.transaction.TransactionManager;
import fabric.common.RefTypeEnum;
import fabric.common.util.Pair;
import fabric.lang.Object;
import jif.lang.Label;

public interface _booleanArray extends Object {
  int get$length();

  boolean set(int i, boolean value);

  boolean get(int i);

  public static class _Impl extends Object._Impl implements _booleanArray {
    private boolean[] value;

    /**
     * Creates a new boolean array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public _Impl(Core core, Label label, int length) {
      this(core, label, new boolean[length]);
    }

    /**
     * Creates a new boolean array at the given Core using the given backing
     * array.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param value
     *                The backing array to use.
     */
    public _Impl(Core core, Label label, boolean[] value) {
      super(core, label);
      this.value = value;
    }

    /**
     * Used for deserializing.
     */
    public _Impl(Core core, long onum, int version, long expiry, long label,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intracoreRefs) throws IOException,
        ClassNotFoundException {
      super(core, onum, version, expiry, label, in, refTypes, intracoreRefs);
      value = new boolean[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readBoolean();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.booleanArray#getLength()
     */
    public int get$length() {
      TransactionManager.getInstance().registerRead(this);
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.booleanArray#get(int)
     */
    public boolean get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.booleanArray#set(int, boolean)
     */
    public boolean set(int i, boolean value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      boolean result = this.value[i] = value;
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
      _booleanArray._Impl src = (_booleanArray._Impl) other;
      value = new boolean[src.value.length];
      System.arraycopy(src.value, 0, value, 0, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object._Impl#$makeProxy()
     */
    @Override
    protected _booleanArray._Proxy $makeProxy() {
      return new _booleanArray._Proxy(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object._Impl#$serialize(java.io.ObjectOutput)
     */
    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intracoreRefs, List<Pair<String, Long>> intercoreRefs)
        throws IOException {
      super.$serialize(out, refTypes, intracoreRefs, intercoreRefs);
      out.writeInt(value.length);
      for (int i = 0; i < value.length; i++)
        out.writeBoolean(value[i]);
    }
  }

  public static class _Proxy extends Object._Proxy implements _booleanArray {

    public _Proxy(Core core, long onum) {
      super(core, onum);
    }

    public _Proxy(_booleanArray._Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.booleanArray#getLength()
     */
    public int get$length() {
      return ((_booleanArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.booleanArray#get(int)
     */
    public boolean get(int i) {
      return ((_booleanArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.booleanArray#set(int, boolean)
     */
    public boolean set(int i, boolean value) {
      return ((_booleanArray) fetch()).set(i, value);
    }
  }
}
