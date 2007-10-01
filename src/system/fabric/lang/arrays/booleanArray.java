package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;

public interface booleanArray extends Object {
  int get$length();

  boolean set(int i, boolean value);

  boolean get(int i);

  public static class $Impl extends Object.$Impl implements
      booleanArray {
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

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.booleanArray#getLength()
     */
    public int get$length() {
      // TODO: mark as read
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.booleanArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public boolean get(int i) {
      // TODO: mark as read
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.booleanArray#set(int, boolean)
     */
    public boolean set(int i, boolean value) {
      // TODO: mark as written
      return this.value[i] = value;
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

  }

  public static class $Proxy extends Object.$Proxy implements
      booleanArray {

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
