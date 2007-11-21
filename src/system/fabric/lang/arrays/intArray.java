package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;

public interface intArray extends Object {
  int get$length();

  int set(int i, int value);

  int get(int i);

  public static class $Impl extends Object.$Impl implements
      intArray {
    private int[] value;

    /**
     * Creates a new int array at the given Core with the given length.
     * 
     * @param core
     *          The core on which to allocate the array.
     * @param length
     *          The length of the array.
     */
    public $Impl(Core core, int length) {
      super(core);
      value = new int[length];
    }

    /**
     * Creates a new int array at the given Core using the given backing
     * array.
     * 
     * @param core
     *          The core on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    public $Impl(Core core, int[] value) {
      super(core);
      this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.intArray#getLength()
     */
    public int get$length() {
      // TODO: mark as read
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.intArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public int get(int i) {
      // TODO: mark as read
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.intArray#set(int, int)
     */
    public int set(int i, int value) {
      // TODO: mark as written
      return this.value[i] = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$makeProxy()
     */
    @Override
    protected intArray.$Proxy $makeProxy() {
      return new intArray.$Proxy(this);
    }

  }

  public static class $Proxy extends Object.$Proxy implements
      intArray {
    
    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(intArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.intArray#getLength()
     */
    public int get$length() {
      return ((intArray) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.intArray#get(int)
     */
    public int get(int i) {
      return ((intArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.intArray#set(int, int)
     */
    public int set(int i, int value) {
      return ((intArray) fetch()).set(i, value);
    }
  }
}
