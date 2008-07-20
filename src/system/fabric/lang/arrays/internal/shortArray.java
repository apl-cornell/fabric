package fabric.lang.arrays.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.client.Core;
import fabric.client.transaction.TransactionManager;
import fabric.common.Pair;
import fabric.common.RefTypeEnum;
import fabric.lang.Object;

public interface shortArray extends Object {
  int get$length();

  short set(int i, short value);

  short get(int i);

  public static class $Impl extends Object.$Impl implements shortArray {
    private short[] value;

    /**
     * Creates a new short array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public $Impl(Core core, int length) {
      super(core);
      value = new short[length];
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
    public $Impl(Core core, short[] value) {
      super(core);
      this.value = value;
    }

    /**
     * Used for deserializing.
     */
    public $Impl(Core core, long onum, int version, long label,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intracoreRefs) throws IOException,
        ClassNotFoundException {
      super(core, onum, version, label, in, refTypes, intracoreRefs);
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
    @SuppressWarnings("unchecked")
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
     * @see fabric.lang.Object.$Impl#$copyStateFrom(fabric.lang.Object.$Impl)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void $copyStateFrom(Object.$Impl other) {
      super.$copyStateFrom(other);
      shortArray.$Impl src = (shortArray.$Impl) other;
      value = new short[src.value.length];
      System.arraycopy(src.value, 0, value, 0, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$makeProxy()
     */
    @Override
    protected shortArray.$Proxy $makeProxy() {
      return new shortArray.$Proxy(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$serialize(java.io.ObjectOutput)
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

  public static class $Proxy extends Object.$Proxy implements shortArray {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(shortArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.shortArray#getLength()
     */
    public int get$length() {
      return ((shortArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.shortArray#get(int)
     */
    public short get(int i) {
      return ((shortArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.shortArray#set(int, short)
     */
    public short set(int i, short value) {
      return ((shortArray) fetch()).set(i, value);
    }
  }
}
