package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;

public interface shortArray extends Object {
  int getLength();

  short set(int i, short value);

  short get(int i);

  public static class $Impl extends Object.$Impl implements
      shortArray {
    private short[] value;

    /**
     * Creates a new short array at the given Core with the given length.
     * 
     * @param core
     *          The core on which to allocate the array.
     * @param length
     *          The length of the array.
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
     *          The core on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    public $Impl(Core core, short[] value) {
      super(core);
      this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.shortArray#getLength()
     */
    public int getLength() {
      // TODO: mark as read
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.shortArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public short get(int i) {
      // TODO: mark as read
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.shortArray#set(int, short)
     */
    public short set(int i, short value) {
      // TODO: mark as written
      return this.value[i] = value;
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

  }

  public static class $Proxy extends Object.$Proxy implements
      shortArray {

    public $Proxy(shortArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.shortArray#getLength()
     */
    public int getLength() {
      return ((shortArray) fetch()).getLength();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.shortArray#get(int)
     */
    public short get(int i) {
      return ((shortArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.shortArray#set(int, short)
     */
    public short set(int i, short value) {
      return ((shortArray) fetch()).set(i, value);
    }
  }
}
