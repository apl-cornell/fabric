package fabric.lang.arrays.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fabric.common.RefTypeEnum;
import fabric.common.exceptions.InternalError;
import fabric.common.util.Pair;
import fabric.lang.Object;
import fabric.lang.security.Label;
import fabric.worker.Store;
import fabric.worker.transaction.TransactionManager;

public interface _ObjectArray<T extends Object> extends Object {
  int get$length();

  T set(int i, T value);

  T get(int i);

  public static class _Impl<T extends Object> extends Object._Impl implements
      _ObjectArray<T>, _InternalArrayImpl {
    /**
     * The class representing the proxy type for the array elements.
     */
    private final Class<? extends Object._Proxy> proxyType;

    private Object[] value;

    /**
     * Creates a new object array at the given Store with the given length.
     * 
     * @param store
     *                The store on which to allocate the array.
     * @param length
     *                The length of the array.
     */
    public _Impl(Store store, Label label, Label accessLabel,
        Class<? extends Object._Proxy> proxyType, int length) {
      super(store, label, accessLabel);
      this.proxyType = getProxy(proxyType);
      value = new Object[length];
    }

    /**
     * Creates a new object array at the given Store using the given backing
     * array.
     * 
     * @param store
     *                The store on which to allocate the array.
     * @param value
     *                The backing array to use.
     */
    public _Impl(Store store, Label label, Label accessLabel,
        Class<? extends Object._Proxy> proxyType, T[] value) {
      super(store, label, accessLabel);
      this.proxyType = getProxy(proxyType);
      this.value = value;
    }

    /**
     * Used for deserializing.
     */
    @SuppressWarnings("unchecked")
    public _Impl(Store store, long onum, int version, long expiry, long label,
        long accessLabel, ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intraStoreRefs) throws IOException,
        ClassNotFoundException {
      super(store, onum, version, expiry, label, accessLabel, in, refTypes,
          intraStoreRefs);
      proxyType = (Class<? extends Object._Proxy>) Class.forName(in.readUTF());
      value = new Object[in.readInt()];
      for (int i = 0; i < value.length; i++) {
        value[i] =
            $readRef(proxyType, refTypes.next(), in, store, intraStoreRefs);
      }
    }
    
    private static final Map<Class<?>, Class<? extends fabric.lang.Object._Proxy>> proxyCache =
        Collections
            .synchronizedMap(new HashMap<Class<?>, Class<? extends fabric.lang.Object._Proxy>>());

    /**
     * Given a Fabric class, returns the corresponding _Proxy class. If the
     * given class is already a _Proxy class, it is returned back to the caller.
     * This is a hack -- we need a value for the <code>proxyType</code> field
     * (used during deserialization), but the array classes in
     * fabric.lang.arrays are implemented in Fabric, which isn't able to talk
     * about the _Proxy classes.
     */
    @SuppressWarnings("unchecked")
    private Class<? extends fabric.lang.Object._Proxy> getProxy(Class<?> c) {
      Class<? extends fabric.lang.Object._Proxy> result =
        proxyCache.get(c);
      if (result != null) return result;
      
      if (c.getSimpleName().equals("_Proxy")) {
        result = (Class<? extends fabric.lang.Object._Proxy>) c;
        proxyCache.put(c, result);
        return result;
      }

      Class<?>[] classes = c.getClasses();
      for (Class<?> c_ : classes) {
        if (c_.getSimpleName().equals("_Proxy")) {
          result = (Class<? extends fabric.lang.Object._Proxy>) c_;
          proxyCache.put(c, result);
          return result;
        }
      }

      throw new InternalError("Error finding _Proxy class in " + c);
    }

    @Override
    public int get$length() {
      TransactionManager.getInstance().registerRead(this);
      return value.length;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int i) {
      TransactionManager.getInstance().registerRead(this);
      return (T) value[i];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T set(int i, T value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      T result = (T) (this.value[i] = value);
      if (transactionCreated)
        TransactionManager.getInstance().commitTransaction();
      return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void $copyAppStateFrom(Object._Impl other) {
      super.$copyAppStateFrom(other);
      _ObjectArray._Impl<T> src = (_ObjectArray._Impl<T>) other;
      value = src.value;
    }

    @Override
    public void cloneValues() {
      value = value.clone();
    }

    @Override
    protected _ObjectArray._Proxy<T> $makeProxy() {
      return new _ObjectArray._Proxy<T>(this);
    }

    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intraStoreRefs, List<Pair<String, Long>> interStoreRefs)
        throws IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      out.writeUTF(proxyType.getName());
      out.writeInt(value.length);
      for (int i = 0; i < value.length; i++)
        $writeRef($getStore(), value[i], refTypes, out, intraStoreRefs,
            interStoreRefs);
    }
  }

  public static class _Proxy<T extends Object> extends Object._Proxy implements
      _ObjectArray<T> {

    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public _Proxy(_ObjectArray._Impl<T> impl) {
      super(impl);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int get$length() {
      return ((_ObjectArray<T>) fetch()).get$length();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int i) {
      return ((_ObjectArray<T>) fetch()).get(i);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T set(int i, T value) {
      return ((_ObjectArray<T>) fetch()).set(i, value);
    }
  }
}
