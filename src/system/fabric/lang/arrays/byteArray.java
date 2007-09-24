package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;

public interface byteArray extends Object {
  int getLength();

  byte set(int i, byte value);

  byte get(int i);

  public static class $Impl extends Object.$Impl implements
      byteArray {
    private byte[] value;

    /**
     * Creates a new byte array at the given Core with the given length.
     * 
     * @param core
     *          The core on which to allocate the array.
     * @param length
     *          The length of the array.
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
     *          The core on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    public $Impl(Core core, byte[] value) {
      super(core);
      this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.byteArray#getLength()
     */
    public int getLength() {
      // TODO: mark as read
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.byteArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public byte get(int i) {
      // TODO: mark as read
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.byteArray#set(int, byte)
     */
    public byte set(int i, byte value) {
      // TODO: mark as written
      return this.value[i] = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$getProxy()
     */
    @Override
    public byteArray.$Proxy $getProxy() {
      return new byteArray.$Proxy(this);
    }

  }

  public static class $Proxy extends Object.$Proxy implements
      byteArray {

    public $Proxy(byteArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.byteArray#getLength()
     */
    public int getLength() {
      return ((byteArray) fetch()).getLength();
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
