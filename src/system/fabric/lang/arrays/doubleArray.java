package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;

public interface doubleArray extends Object {
  int getLength();

  double set(int i, double value);

  double get(int i);

  public static class $Impl extends Object.$Impl implements
      doubleArray {
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

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.doubleArray#getLength()
     */
    public int getLength() {
      // TODO: mark as read
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.doubleArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public double get(int i) {
      // TODO: mark as read
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.doubleArray#set(int, double)
     */
    public double set(int i, double value) {
      // TODO: mark as written
      return this.value[i] = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$getProxy()
     */
    @Override
    public doubleArray.$Proxy $getProxy() {
      return new doubleArray.$Proxy(this);
    }

  }

  public static class $Proxy extends Object.$Proxy implements
      doubleArray {

    public $Proxy(doubleArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.doubleArray#getLength()
     */
    public int getLength() {
      return ((doubleArray) fetch()).getLength();
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
