package fabric.lang.arrays;

import fabric.client.Core;
import fabric.lang.Object;

public interface charArray extends Object {
  int getLength();

  char set(int i, char value);

  char get(int i);

  public static class $Impl extends Object.$Impl implements
      charArray {
    private char[] value;

    /**
     * Creates a new char array at the given Core with the given length.
     * 
     * @param core
     *          The core on which to allocate the array.
     * @param length
     *          The length of the array.
     */
    public $Impl(Core core, int length) {
      super(core);
      value = new char[length];
    }

    /**
     * Creates a new char array at the given Core using the given backing
     * array.
     * 
     * @param core
     *          The core on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    public $Impl(Core core, char[] value) {
      super(core);
      this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.charArray#getLength()
     */
    public int getLength() {
      // TODO: mark as read
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.charArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public char get(int i) {
      // TODO: mark as read
      return this.value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.charArray#set(int, char)
     */
    public char set(int i, char value) {
      // TODO: mark as written
      return this.value[i] = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$getProxy()
     */
    @Override
    public charArray.$Proxy $getProxy() {
      return new charArray.$Proxy(this);
    }

  }

  public static class $Proxy extends Object.$Proxy implements
      charArray {

    public $Proxy(charArray.$Impl impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.charArray#getLength()
     */
    public int getLength() {
      return ((charArray) fetch()).getLength();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.charArray#get(int)
     */
    public char get(int i) {
      return ((charArray) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.charArray#set(int, char)
     */
    public char set(int i, char value) {
      return ((charArray) fetch()).set(i, value);
    }
  }
}
