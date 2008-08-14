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
import fabric.lang.auth.Label;

public interface _floatArray extends Object {
  int get$length();

  float set(int i, float value);

  float get(int i);

  public static class $Impl extends Object.$Impl implements _floatArray {
    private float[] value;

    /**
     * Creates a new float array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public $Impl(Core core, Label label, int length) {
      this(core, label, new float[length]);
    }

    /**
     * Creates a new float array at the given Core using the given backing
     * array.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param value
     *                The backing array to use.
     */
    public $Impl(Core core, Label label, float[] value) {
      super(core, label);
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
      value = new float[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readFloat();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.floatArray#getLength()
     */
    public int get$length() {
      TransactionManager.getInstance().registerRead(this);
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.floatArray#get(int)
     */
    public float get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.floatArray#set(int, float)
     */
    public float set(int i, float value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      float result = this.value[i] = value;
      if (transactionCreated) TransactionManager.getInstance().commitTransaction();
      return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$copyStateFrom(fabric.lang.Object.$Impl)
     */
    @Override
    public void $copyStateFrom(Object.$Impl other) {
      super.$copyStateFrom(other);
      _floatArray.$Impl src = (_floatArray.$Impl) other;
      value = new float[src.value.length];
      System.arraycopy(src.value, 0, value, 0, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$makeProxy()
     */
    @Override
    protected _floatArray.$Proxy $makeProxy() {
      return new _floatArray.$Proxy(this);
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
        out.writeFloat(value[i]);
    }
  }

  public static class $Proxy extends Object.$Proxy implements _floatArray {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(_floatArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.floatArray#getLength()
     */
    public int get$length() {
      return ((_floatArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.floatArray#get(int)
     */
    public float get(int i) {
      return ((_floatArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.floatArray#set(int, float)
     */
    public float set(int i, float value) {
      return ((_floatArray) fetch()).set(i, value);
    }
  }
}
