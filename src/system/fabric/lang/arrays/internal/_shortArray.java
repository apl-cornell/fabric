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

public interface _shortArray extends Object {
  int get$length();

  short set(int i, short value);

  short get(int i);

  public static class _Impl extends Object._Impl implements _shortArray {
    private short[] value;

    /**
     * Creates a new short array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public _Impl(Core core, Label label, int length) {
      this(core, label, new short[length]);
    }

    /**
     * Creates a new short array at the given Core using the given backing
     * array.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param value
     *                The backing array to use.
     */
    public _Impl(Core core, Label label, short[] value) {
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
      value = new short[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readShort();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.shortArray#getLength()
     */
    public int get$length() {
      TransactionManager.getInstance().registerRead(this);
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.shortArray#get(int)
     */
    public short get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.shortArray#set(int, short)
     */
    public short set(int i, short value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      short result = this.value[i] = value;
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
      _shortArray._Impl src = (_shortArray._Impl) other;
      value = new short[src.value.length];
      System.arraycopy(src.value, 0, value, 0, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object._Impl#$makeProxy()
     */
    @Override
    protected _shortArray._Proxy $makeProxy() {
      return new _shortArray._Proxy(this);
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
        out.writeShort(value[i]);
    }
  }

  public static class _Proxy extends Object._Proxy implements _shortArray {

    public _Proxy(Core core, long onum) {
      super(core, onum);
    }

    public _Proxy(_shortArray._Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.shortArray#getLength()
     */
    public int get$length() {
      return ((_shortArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.shortArray#get(int)
     */
    public short get(int i) {
      return ((_shortArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.shortArray#set(int, short)
     */
    public short set(int i, short value) {
      return ((_shortArray) fetch()).set(i, value);
    }
  }
}
