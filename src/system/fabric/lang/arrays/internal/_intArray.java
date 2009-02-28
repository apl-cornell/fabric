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
import jif.lang.Label;

public interface _intArray extends Object {
  int get$length();

  int set(int i, int value);

  int get(int i);

  public static class $Impl extends Object.$Impl implements _intArray {
    private int[] value;

    /**
     * Creates a new int array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public $Impl(Core core, Label label, int length) {
      this(core, label, new int[length]);
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
    public $Impl(Core core, Label label, int[] value) {
      super(core, label);
      this.value = value;
    }

    /**
     * Used for deserializing.
     */
    public $Impl(Core core, long onum, int version, long expiry, long label,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intracoreRefs) throws IOException,
        ClassNotFoundException {
      super(core, onum, version, expiry, label, in, refTypes, intracoreRefs);
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
     * @see fabric.lang.Object.$Impl#$copyAppStateFrom(fabric.lang.Object.$Impl)
     */
    @Override
    public void $copyAppStateFrom(Object.$Impl other) {
      super.$copyAppStateFrom(other);
      _intArray.$Impl src = (_intArray.$Impl) other;
      value = new int[src.value.length];
      System.arraycopy(src.value, 0, value, 0, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$makeProxy()
     */
    @Override
    protected _intArray.$Proxy $makeProxy() {
      return new _intArray.$Proxy(this);
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

  public static class $Proxy extends Object.$Proxy implements _intArray {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(_intArray.$Impl impl) {
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
