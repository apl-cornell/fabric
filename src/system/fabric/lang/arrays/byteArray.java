package fabric.lang.arrays;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.client.Core;
import fabric.client.TransactionManager;
import fabric.common.Pair;
import fabric.core.SerializedObject.RefTypeEnum;
import fabric.lang.Object;

public interface byteArray extends Object {
  int get$length();

  byte set(int i, byte value);

  byte get(int i);

  public static class $Impl extends Object.$Impl implements byteArray {
    private byte[] value;

    /**
     * Creates a new byte array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public $Impl(Core core, int length) {
      super(core);
      value = new byte[length];
    }

    /**
     * Creates a new byte array at the given Core using the given backing
     * array.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param value
     *                The backing array to use.
     */
    public $Impl(Core core, byte[] value) {
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
      value = new byte[in.readInt()];
      for (int i = 0; i < value.length; i++)
        value[i] = in.readByte();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.byteArray#getLength()
     */
    public int get$length() {
      TransactionManager.INSTANCE.registerRead(this);
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.byteArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public byte get(int i) {
      TransactionManager.INSTANCE.registerRead(this);
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.byteArray#set(int, byte)
     */
    public byte set(int i, byte value) {
      boolean transactionCreated =
          TransactionManager.INSTANCE.registerWrite(this);
      byte result = this.value[i] = value;
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
      byteArray.$Impl src = (byteArray.$Impl) other;
      value = new byte[src.value.length];
      System.arraycopy(src.value, 0, value, 0, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$makeProxy()
     */
    @Override
    protected byteArray.$Proxy $makeProxy() {
      return new byteArray.$Proxy(this);
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
        out.writeByte(value[i]);
    }
  }

  public static class $Proxy extends Object.$Proxy implements byteArray {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(byteArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.byteArray#getLength()
     */
    public int get$length() {
      return ((byteArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.byteArray#get(int)
     */
    public byte get(int i) {
      return ((byteArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.byteArray#set(int, byte)
     */
    public byte set(int i, byte value) {
      return ((byteArray) fetch()).set(i, value);
    }
  }
}
