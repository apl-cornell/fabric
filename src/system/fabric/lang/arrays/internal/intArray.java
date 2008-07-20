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

public interface intArray extends Object {
  int get$length();

  int set(int i, int value);

  int get(int i);

  public static class $Impl extends Object.$Impl implements intArray {
    private int[] value;

    /**
     * Creates a new int array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public $Impl(Core core, int length) {
      super(core);
      value = new int[length];
    }

    /**
     * Creates a new int array at the given Core using the given backing
     * array.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param value
     *                The backing array to use.
     */
    public $Impl(Core core, int[] value) {
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
    @SuppressWarnings("unchecked")
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
     * @see fabric.lang.Object.$Impl#$copyStateFrom(fabric.lang.Object.$Impl)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void $copyStateFrom(Object.$Impl other) {
      super.$copyStateFrom(other);
      intArray.$Impl src = (intArray.$Impl) other;
      value = new int[src.value.length];
      System.arraycopy(src.value, 0, value, 0, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$makeProxy()
     */
    @Override
    protected intArray.$Proxy $makeProxy() {
      return new intArray.$Proxy(this);
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
        out.writeInt(value[i]);
    }
  }

  public static class $Proxy extends Object.$Proxy implements intArray {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(intArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.intArray#getLength()
     */
    public int get$length() {
      return ((intArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.intArray#get(int)
     */
    public int get(int i) {
      return ((intArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.intArray#set(int, int)
     */
    public int set(int i, int value) {
      return ((intArray) fetch()).set(i, value);
    }
  }
}
