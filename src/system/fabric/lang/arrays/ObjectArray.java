package fabric.lang.arrays;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.client.Core;
import fabric.client.TransactionManager;
import fabric.common.Pair;
import fabric.common.RefTypeEnum;
import fabric.common.Util;
import fabric.lang.Object;

public interface ObjectArray<T extends Object> extends Object {
  int get$length();

  T set(int i, T value);

  T get(int i);

  public static class $Impl<T extends Object> extends Object.$Impl implements
      ObjectArray<T> {
    /**
     * The class representing the proxy type for the array elements.
     */
    private /*final*/ Class<? extends Object.$Proxy> proxyType;

    private Object[] value;

    /**
     * Creates a new object array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    @SuppressWarnings("unchecked")
    public $Impl(Core core, Class<? extends Object.$Proxy> proxyType, int length) {
      super(core);
      this.proxyType = Util.getProxy(proxyType);
      value = new Object[length];
    }

    /**
     * Creates a new object array at the given Core using the given backing
     * array.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param value
     *                The backing array to use.
     */
    public $Impl(Core core, Class<? extends Object.$Proxy> proxyType, T[] value) {
      super(core);
      this.proxyType = Util.getProxy(proxyType);
      this.value = value;
    }

    /**
     * Used for deserializing.
     */
    @SuppressWarnings("unchecked")
    public $Impl(Core core, long onum, int version, long label,
        ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intracoreRefs) throws IOException,
        ClassNotFoundException {
      super(core, onum, version, label, in, refTypes, intracoreRefs);
      proxyType = (Class<? extends Object.$Proxy>) Class.forName(in.readUTF());
      value = new Object[in.readInt()];
      for (int i = 0; i < value.length; i++) {
        value[i] =
            $readRef(proxyType, refTypes.next(), in, core, intracoreRefs);
      }
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#getLength()
     */
    public int get$length() {
      TransactionManager.INSTANCE.registerRead(this);
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public T get(int i) {
      TransactionManager.INSTANCE.registerRead(this);
      return (T) value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#set(int, fabric.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public T set(int i, T value) {
      boolean transactionCreated =
          TransactionManager.INSTANCE.registerWrite(this);
      T result = (T) (this.value[i] = value);
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
      ObjectArray.$Impl<T> src = (ObjectArray.$Impl<T>) other;
      value = new Object[src.value.length];
      System.arraycopy(src.value, 0, value, 0, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$makeProxy()
     */
    @Override
    protected ObjectArray.$Proxy<T> $makeProxy() {
      return new ObjectArray.$Proxy<T>(this);
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
      out.writeUTF(proxyType.getName());
      out.writeInt(value.length);
      for (int i = 0; i < value.length; i++)
        $writeRef($getCore(), value[i], refTypes, out, intracoreRefs,
            intercoreRefs);
    }
  }

  public static class $Proxy<T extends Object> extends Object.$Proxy implements
      ObjectArray<T> {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(ObjectArray.$Impl<T> impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#getLength()
     */
    @SuppressWarnings("unchecked")
    public int get$length() {
      return ((ObjectArray<T>) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public T get(int i) {
      return ((ObjectArray<T>) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.ObjectArray#set(int, fabric.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public T set(int i, T value) {
      return ((ObjectArray<T>) fetch()).set(i, value);
    }
  }
}
