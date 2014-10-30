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
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Pair;
import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.worker.Store;
import fabric.worker.Worker;
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
     *          The store on which to allocate the array.
     * @param length
     *          The length of the array.
     */
    public _Impl(Store store, Label updateLabel, ConfPolicy accessPolicy,
        Class<? extends Object._Proxy> proxyType, int length) {
      super(store);
      this.proxyType = getProxy(proxyType);
      value = new Object[length];

      set$$updateLabel(updateLabel);
      set$$accessPolicy(accessPolicy);
    }

    /**
     * Creates a new object array at the given Store using the given backing
     * array.
     *
     * @param store
     *          The store on which to allocate the array.
     * @param value
     *          The backing array to use.
     */
    public _Impl(Store store, Label updateLabel, ConfPolicy accessPolicy,
        Class<? extends Object._Proxy> proxyType, T[] value) {
      super(store);
      this.proxyType = getProxy(proxyType);
      this.value = value;

      set$$updateLabel(updateLabel);
      set$$accessPolicy(accessPolicy);
    }

    /**
     * Used for deserializing.
     */
    public _Impl(Store store, long onum, int version, long expiry, long label,
        long accessLabel, ObjectInput in, Iterator<RefTypeEnum> refTypes,
        Iterator<Long> intraStoreRefs,
        Iterator<Pair<String, Long>> interStoreRefs) throws IOException,
        ClassNotFoundException {
      super(store, onum, version, expiry, label, accessLabel, in, refTypes,
          intraStoreRefs, interStoreRefs);
      proxyType =
          (Class<? extends Object._Proxy>) Worker.getWorker().getClassLoader()
              .loadClass(in.readUTF());
      value = new Object[in.readInt()];
      for (int i = 0; i < value.length; i++) {
        value[i] =
            $readRef(proxyType, refTypes.next(), in, store, intraStoreRefs,
                interStoreRefs);
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
    private Class<? extends fabric.lang.Object._Proxy> getProxy(Class<?> c) {
      Class<? extends fabric.lang.Object._Proxy> result = proxyCache.get(c);
      if (result != null) return result;

      if (c.getSimpleName().equals("_Proxy")) {
        @SuppressWarnings("unchecked")
        Class<? extends fabric.lang.Object._Proxy> proxyClass =
            (Class<? extends fabric.lang.Object._Proxy>) c;
        result = proxyClass;
        proxyCache.put(c, result);
        return result;
      }

      Class<?>[] classes = c.getClasses();
      for (Class<?> c_ : classes) {
        if (c_.getSimpleName().equals("_Proxy")) {
          @SuppressWarnings("unchecked")
          Class<? extends fabric.lang.Object._Proxy> proxyClass =
              (Class<? extends fabric.lang.Object._Proxy>) c_;
          result = proxyClass;
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
    public T get(int i) {
      TransactionManager.getInstance().registerRead(this);
      @SuppressWarnings("unchecked")
      T t = (T) value[i];
      return t;
    }

    @Override
    public T set(int i, T value) {
      boolean transactionCreated =
          TransactionManager.getInstance().registerWrite(this);
      this.value[i] = value;
      if (transactionCreated)
        TransactionManager.getInstance().commitTransaction();
      return value;
    }

    @Override
    public void $copyAppStateFrom(Object._Impl other) {
      super.$copyAppStateFrom(other);
      @SuppressWarnings("unchecked")
      _ObjectArray._Impl<T> src = (_ObjectArray._Impl<T>) other;
      value = src.value;
    }

    @Override
    public void cloneValues() {
      value = value.clone();
    }

    @Override
    protected _ObjectArray._Proxy<T> $makeProxy() {
      return new _ObjectArray._Proxy<>(this);
    }

    @Override
    public void $serialize(ObjectOutput out, List<RefTypeEnum> refTypes,
        List<Long> intraStoreRefs, List<Pair<String, Long>> interStoreRefs)
        throws IOException {
      super.$serialize(out, refTypes, intraStoreRefs, interStoreRefs);
      out.writeUTF(proxyType.getName());
      out.writeInt(value.length);
      for (Object element : value)
        $writeRef($getStore(), element, refTypes, out, intraStoreRefs,
            interStoreRefs);
    }

    @Override
    public Object $initLabels() {
      return $getProxy();
    }

    @Override
    public _ObjectArray<T> $makeSemiDeepCopy(LongSet oldSet,
        LongKeyMap<Object> oldToNew) {
      _ObjectArray._Impl<T> copy = null;
      if (oldToNew.containsKey(this.$getOnum())) {
        copy = (_ObjectArray._Impl<T>) oldToNew.get(this.$getOnum());
      } else {
        copy = (_ObjectArray._Impl<T>) this.$makeBlankCopy().fetch();
        oldToNew.put(this.$getOnum(), copy);
      }
      super.$makeSemiDeepCopy(oldSet, oldToNew);
      copy.value = new Object[this.value.length];
      for (int i = 0; i < this.get$length(); i++) {
        T ithItem = this.get(i);
        if (ithItem == null) {
          copy.set(i, null);
        } else if (oldSet.contains(ithItem.$getOnum())) {
          if (oldToNew.containsKey(ithItem.$getOnum())) {
            copy.set(i, (T) oldToNew.get(ithItem.$getOnum()));
          } else {
            copy.set(i, (T) ithItem.$makeSemiDeepCopy(oldSet, oldToNew));
          }
        } else {
          copy.set(i, this.get(i));
        }
      }
      return copy.$makeProxy();
    }

    @Override
    public _ObjectArray<T> $makeBlankCopy() {
      return new _ObjectArray._Impl<T>(this.$getStore(),
                                       this.get$$updateLabel(),
                                       this.get$$accessPolicy(),
                                       this.proxyType,
                                       this.value.length).$makeProxy();
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
    public int get$length() {
      return ((_ObjectArray<T>) fetch()).get$length();
    }

    @Override
    public T get(int i) {
      return ((_ObjectArray<T>) fetch()).get(i);
    }

    @Override
    public T set(int i, T value) {
      return ((_ObjectArray<T>) fetch()).set(i, value);
    }
  }
}
