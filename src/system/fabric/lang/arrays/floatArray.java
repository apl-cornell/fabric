package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;

public interface floatArray extends Object {
  int get$length();

  float set(int i, float value);

  float get(int i);

  public static class $Impl extends Object.$Impl implements
      floatArray {
    private float[] value;

    /**
     * Creates a new float array at the given Core with the given length.
     * 
     * @param core
     *          The core on which to allocate the array.
     * @param length
     *          The length of the array.
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
     *          The core on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    public $Impl(Core core, float[] value) {
      super(core);
      this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.floatArray#getLength()
     */
    public int get$length() {
      // TODO: mark as read
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.floatArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public float get(int i) {
      // TODO: mark as read
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.floatArray#set(int, float)
     */
    public float set(int i, float value) {
      // TODO: mark as written
      return this.value[i] = value;
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

  }

  public static class $Proxy extends Object.$Proxy implements
      floatArray {

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
