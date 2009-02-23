package fabric.lang.arrays.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.List;

import fabric.client.Core;
import fabric.client.transaction.TransactionManager;
import fabric.common.InternalError;
import fabric.common.Pair;
import fabric.common.RefTypeEnum;
import fabric.lang.Object;
import jif.lang.Label;

public interface _ObjectArray<T extends Object> extends Object {
  int get$length();

  T set(int i, T value);

  T get(int i);

  public static class $Impl<T extends Object> extends Object.$Impl implements
      _ObjectArray<T> {
    /**
     * The class representing the proxy type for the array elements.
     */
    private final Class<? extends Object.$Proxy> proxyType;

    private Object[] value;

    /**
     * Creates a new object array at the given Core with the given length.
     * 
     * @param core
     *                The core on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public $Impl(Core core, Label label,
        Class<? extends Object.$Proxy> proxyType, int length) {
      super(core, label);
      this.proxyType = getProxy(proxyType);
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
    public $Impl(Core core, Label label,
        Class<? extends Object.$Proxy> proxyType, T[] value) {
      super(core, label);
      this.proxyType = getProxy(proxyType);
      this.value = value;
    }

    /**
     * Used for deserializing.
     */
    @SuppressWarnings("unchecked")
    public $Impl(Core core, long onum, int version, long label, ObjectInput in,
        Iterator<RefTypeEnum> refTypes, Iterator<Long> intracoreRefs)
        throws IOException, ClassNotFoundException {
      super(core, onum, version, label, in, refTypes, intracoreRefs);
      proxyType = (Class<? extends Object.$Proxy>) Class.forName(in.readUTF());
      value = new Object[in.readInt()];
      for (int i = 0; i < value.length; i++) {
        value[i] =
            $readRef(proxyType, refTypes.next(), in, core, intracoreRefs);
      }
    }

    /**
     * Given a Fabric class, returns the corresponding $Proxy class. If the
     * given class is already a $Proxy class, it is returned back to the caller.
     * This is a hack -- we need a value for the <code>proxyType</code> field
     * (used during deserialization), but the array classes in
     * fabric.lang.arrays are implemented in Fabric, which isn't able to talk
     * about the $Proxy classes.
     */
    @SuppressWarnings("unchecked")
    private Class<? extends fabric.lang.Object.$Proxy> getProxy(Class<?> c) {
      if (c.getSimpleName().equals("$Proxy"))
        return (Class<? extends fabric.lang.Object.$Proxy>) c;

      Class<?>[] classes = c.getClasses();
      for (Class<?> c_ : classes) {
        if (c_.getSimpleName().equals("$Proxy"))
          return (Class<? extends fabric.lang.Object.$Proxy>) c_;
      }

      throw new InternalError("Error finding $Proxy class in " + c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.ObjectArray#getLength()
     */
    public int get$length() {
      TransactionManager.getInstance().registerRead(this);
      return value.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.ObjectArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public T get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return (T) value[i];
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.ObjectArray#set(int, fabric.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public T set(int i, T value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      T result = (T) (this.value[i] = value);
      if (transactionCreated)
        TransactionManager.getInstance().commitTransaction();
      return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$copyAppStateFrom(fabric.lang.Object.$Impl)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void $copyAppStateFrom(Object.$Impl other) {
      super.$copyAppStateFrom(other);
      _ObjectArray.$Impl<T> src = (_ObjectArray.$Impl<T>) other;
      value = new Object[src.value.length];
      System.arraycopy(src.value, 0, value, 0, src.value.length);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.Object.$Impl#$makeProxy()
     */
    @Override
    protected _ObjectArray.$Proxy<T> $makeProxy() {
      return new _ObjectArray.$Proxy<T>(this);
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
      _ObjectArray<T> {

    public $Proxy(Core core, long onum) {
      super(core, onum);
    }

    public $Proxy(_ObjectArray.$Impl<T> impl) {
      super(impl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.ObjectArray#getLength()
     */
    @SuppressWarnings("unchecked")
    public int get$length() {
      return ((_ObjectArray<T>) fetch()).get$length();
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.ObjectArray#get(int)
     */
    @SuppressWarnings("unchecked")
    public T get(int i) {
      return ((_ObjectArray<T>) fetch()).get(i);
    }

    /*
     * (non-Javadoc)
     * 
     * @see fabric.lang.arrays.internal.ObjectArray#set(int, fabric.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public T set(int i, T value) {
      return ((_ObjectArray<T>) fetch()).set(i, value);
    }
  }
}
