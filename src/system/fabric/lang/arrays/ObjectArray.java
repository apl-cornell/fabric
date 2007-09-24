package fabric.lang.arrays;

import fabric.client.Core;

public interface ObjectArray<T> {
  int getLength();

  T set(int i, T value);

  T get(int i);

  public static class $Impl<T> extends fabric.lang.Object.$Impl implements
      ObjectArray<T> {
    private Object[] value;

    /**
     * Creates a new object array at the given Core with the given length.
     * 
     * @param core
     *          The core on which to allocate the array.
     * @param length
     *          The length of the array.
     */
    public $Impl(Core core, int length) {
      super(core);
      value = new Object[length];
    }

    /**
     * Creates a new object array at the given Core using the given backing
     * array.
     * 
     * @param core
     *          The core on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    public $Impl(Core core, T[] value) {
      super(core);
      this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#getLength()
     */
    public int getLength() {
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public T get(int i) {
      return (T) value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#set(int, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public T set(int i, T value) {
      return (T) (this.value[i] = value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$getProxy()
     */
    @Override
    public ObjectArray.$Proxy<T> $getProxy() {
      return new ObjectArray.$Proxy<T>(this);
    }
  }

  public static class $Proxy<T> extends fabric.lang.Object.$Proxy implements
      ObjectArray<T> {

    public $Proxy(ObjectArray.$Impl<T> impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#getLength()
     */
    public int getLength() {
      return ((ObjectArray<T>) fetch()).getLength();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#get(int)
     */
    public T get(int i) {
      return ((ObjectArray<T>) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#set(int, java.lang.Object)
     */
    public T set(int i, T value) {
      return ((ObjectArray<T>) fetch()).set(i, value);
    }
  }
}
