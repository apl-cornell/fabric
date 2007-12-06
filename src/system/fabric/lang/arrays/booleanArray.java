package fabric.lang.arrays;

import java.io.IOException;
import java.io.ObjectOutput;
import java.util.Arrays;

import fabric.client.Core;
import fabric.client.TransactionManager;
import fabric.common.Policy;
import fabric.core.SerializedObject.ObjectInput;
import fabric.lang.Object;

public interface booleanArray extends Object {
  int get$length();

  boolean set(int i, boolean value);

  boolean get(int i);

  public static class $Impl extends Object.$Impl implements booleanArray {
    private boolean[] value;

    /**
     * Creates a new boolean array at the given Core with the given length.
     * 
     * @param core
     *          The core on which to allocate the array.
     * @param length
     *          The length of the array.
     */
    public $Impl(Core core, int length) {
      super(core);
      value = new boolean[length];
    }

    /**
     * Creates a new boolean array at the given Core using the given backing
     * array.
     * 
     * @param core
     *          The core on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    public $Impl(Core core, boolean[] value) {
      super(core);
      this.value = value;
    }

    /**
     * Used for deserializing.
     */
    public $Impl(Core core, long onum, int version, Policy policy,
        ObjectInput in) throws IOException, ClassNotFoundException {
      super(core, onum, version, policy, in);
      value = new boolean[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readBoolean();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.booleanArray#getLength()
     */
    public int get$length() {
      TransactionManager.INSTANCE.registerRead(this);
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.booleanArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public boolean get(int i) {
      TransactionManager.INSTANCE.registerRead(this);
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.booleanArray#set(int, boolean)
     */
    public boolean set(int i, boolean value) {
      boolean transactionCreated =
          TransactionManager.INSTANCE.registerWrite(this);
      boolean result = this.value[i] = value;
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
      booleanArray.$Impl src = (booleanArray.$Impl) other;
      value = Arrays.copyOf(src.value, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$makeProxy()
     */
    @Override
    protected booleanArray.$Proxy $makeProxy() {
      return new booleanArray.$Proxy(this);
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
        out.writeBoolean(value[i]);
    }
  }

  public static class $Proxy extends Object.$Proxy implements booleanArray {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(booleanArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.booleanArray#getLength()
     */
    public int get$length() {
      return ((booleanArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.booleanArray#get(int)
     */
    public boolean get(int i) {
      return ((booleanArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.booleanArray#set(int, boolean)
     */
    public boolean set(int i, boolean value) {
      return ((booleanArray) fetch()).set(i, value);
    }
  }
}
