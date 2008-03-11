package fabric.lang.arrays;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import fabric.client.Core;
import fabric.client.TransactionManager;
import fabric.common.Pair;
import fabric.common.Policy;
import fabric.core.SerializedObject.RefTypeEnum;
import fabric.lang.Object;

public interface longArray extends Object {
  int get$length();

  long set(int i, long value);

  long get(int i);

  public static class $Impl extends Object.$Impl implements longArray {
    private long[] value;

    /**
     * Creates a new long array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public $Impl(Core core, int length) {
      super(core);
      value = new long[length];
    }

    /**
     * Creates a new long array at the given Core using the given backing
     * array.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param value
     *                The backing array to use.
     */
    public $Impl(Core core, long[] value) {
      super(core);
      this.value = value;
    }

    /**
     * Used for deserializing.
     */
    public $Impl(Core core, long onum, int version, Policy policy,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intracoreRefs) throws IOException,
        ClassNotFoundException {
      super(core, onum, version, policy, in, refTypes, intracoreRefs);
      value = new long[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readLong();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.longArray#getLength()
     */
    public int get$length() {
      TransactionManager.INSTANCE.registerRead(this);
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.longArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public long get(int i) {
      TransactionManager.INSTANCE.registerRead(this);
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.longArray#set(int, long)
     */
    public long set(int i, long value) {
      boolean transactionCreated =
          TransactionManager.INSTANCE.registerWrite(this);
      long result = this.value[i] = value;
      if (transactionCreated) TransactionManager.INSTANCE.commitTransaction();
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
      longArray.$Impl src = (longArray.$Impl) other;
      value = new long[src.value.length];
      System.arraycopy(src.value, 0, value, 0, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$makeProxy()
     */
    @Override
    protected longArray.$Proxy $makeProxy() {
      return new longArray.$Proxy(this);
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
        out.writeLong(value[i]);
    }
  }

  public static class $Proxy extends Object.$Proxy implements longArray {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(longArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.longArray#getLength()
     */
    public int get$length() {
      return ((longArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.longArray#get(int)
     */
    public long get(int i) {
      return ((longArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.longArray#set(int, long)
     */
    public long set(int i, long value) {
      return ((longArray) fetch()).set(i, value);
    }
  }
}
