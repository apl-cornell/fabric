package fabric.lang.arrays;

import java.io.IOException;
import java.io.ObjectOutput;
import java.util.Arrays;

import fabric.client.Core;
import fabric.client.TransactionManager;
import fabric.common.Policy;
import fabric.core.SerializedObject.ObjectInput;
import fabric.lang.Object;

public interface doubleArray extends Object {
  int get$length();

  double set(int i, double value);

  double get(int i);

  public static class $Impl extends Object.$Impl implements doubleArray {
    private double[] value;

    /**
     * Creates a new double array at the given Core with the given length.
     * 
     * @param core
     *          The core on which to allocate the array.
     * @param length
     *          The length of the array.
     */
    public $Impl(Core core, int length) {
      super(core);
      value = new double[length];
    }

    /**
     * Creates a new double array at the given Core using the given backing
     * array.
     * 
     * @param core
     *          The core on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    public $Impl(Core core, double[] value) {
      super(core);
      this.value = value;
    }

    /**
     * Used for deserializing.
     */
    public $Impl(Core core, long onum, int version, Policy policy,
        ObjectInput in) throws IOException, ClassNotFoundException {
      super(core, onum, version, policy, in);
      value = new double[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readDouble();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.doubleArray#getLength()
     */
    public int get$length() {
      TransactionManager.INSTANCE.registerRead(this);
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.doubleArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public double get(int i) {
      TransactionManager.INSTANCE.registerRead(this);
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.doubleArray#set(int, double)
     */
    public double set(int i, double value) {
      boolean transactionCreated =
          TransactionManager.INSTANCE.registerWrite(this);
      double result = this.value[i] = value;
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
      doubleArray.$Impl src = (doubleArray.$Impl) other;
      value = Arrays.copyOf(src.value, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$makeProxy()
     */
    @Override
    protected doubleArray.$Proxy $makeProxy() {
      return new doubleArray.$Proxy(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$serialize(java.io.ObjectOutput)
     */
    @Override
    public void $serialize(ObjectOutput out) throws IOException {
      super.$serialize(out);
      out.writeInt(value.length);
      for (int i = 0; i < value.length; i++)
        out.writeDouble(value[i]);
    }
  }

  public static class $Proxy extends Object.$Proxy implements doubleArray {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(doubleArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.doubleArray#getLength()
     */
    public int get$length() {
      return ((doubleArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.doubleArray#get(int)
     */
    public double get(int i) {
      return ((doubleArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.doubleArray#set(int, double)
     */
    public double set(int i, double value) {
      return ((doubleArray) fetch()).set(i, value);
    }
  }
}
