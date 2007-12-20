package fabric.lang.arrays;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fabric.client.Core;
import fabric.client.TransactionManager;
import fabric.common.Pair;
import fabric.common.Policy;
import fabric.core.SerializedObject.RefTypeEnum;
import fabric.lang.Object;

public interface floatArray extends Object {
  int get$length();

  float set(int i, float value);

  float get(int i);

  public static class $Impl extends Object.$Impl implements floatArray {
    private float[] value;

    /**
     * Creates a new float array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public $Impl(Core core, int length) {
      super(core);
      value = new float[length];
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
    public $Impl(Core core, float[] value) {
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
      value = new float[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readFloat();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.floatArray#getLength()
     */
    public int get$length() {
      TransactionManager.INSTANCE.registerRead(this);
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.floatArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public float get(int i) {
      TransactionManager.INSTANCE.registerRead(this);
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.floatArray#set(int, float)
     */
    public float set(int i, float value) {
      boolean transactionCreated =
          TransactionManager.INSTANCE.registerWrite(this);
      float result = this.value[i] = value;
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
      floatArray.$Impl src = (floatArray.$Impl) other;
      value = Arrays.copyOf(src.value, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$makeProxy()
     */
    @Override
    protected floatArray.$Proxy $makeProxy() {
      return new floatArray.$Proxy(this);
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

  public static class $Proxy extends Object.$Proxy implements floatArray {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(floatArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.floatArray#getLength()
     */
    public int get$length() {
      return ((floatArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.floatArray#get(int)
     */
    public float get(int i) {
      return ((floatArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.floatArray#set(int, float)
     */
    public float set(int i, float value) {
      return ((floatArray) fetch()).set(i, value);
    }
  }
}
