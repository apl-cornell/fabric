package fabric.util;

public interface Map extends fabric.lang.Object {

  void clear();

  boolean containsKey(fabric.lang.Object key);

  boolean containsValue(fabric.lang.Object value);

  fabric.util.Set entrySet();

  @Override
  boolean equals(fabric.lang.Object o);

  fabric.lang.Object get(fabric.lang.Object key);

  fabric.lang.Object put(fabric.lang.Object key, fabric.lang.Object value);

  @Override
  int hashCode();

  boolean isEmpty();

  fabric.util.Set keySet();

  void putAll(fabric.util.Map m);

  fabric.lang.Object remove(fabric.lang.Object key);

  int size();

  fabric.util.Collection values();

  public static interface Entry extends fabric.lang.Object {

    fabric.lang.Object getKey();

    fabric.lang.Object getValue();

    fabric.lang.Object setValue(fabric.lang.Object value);

    @Override
    int hashCode();

    @Override
    boolean equals(fabric.lang.Object o);

    public static class _Proxy extends fabric.lang.Object._Proxy implements
        fabric.util.Map.Entry {

      @Override
      native public fabric.lang.Object getKey();

      @Override
      native public fabric.lang.Object getValue();

      @Override
      native public fabric.lang.Object setValue(fabric.lang.Object arg1);

      @Override
      native public int hashCode();

      @Override
      native public boolean equals(fabric.lang.Object arg1);

      public _Proxy(fabric.worker.Store store, long onum) {
        super(store, onum);
      }
    }

  }

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.Map {

    @Override
    native public void clear();

    @Override
    native public boolean containsKey(fabric.lang.Object arg1);

    @Override
    native public boolean containsValue(fabric.lang.Object arg1);

    @Override
    native public fabric.util.Set entrySet();

    @Override
    native public boolean equals(fabric.lang.Object arg1);

    @Override
    native public fabric.lang.Object get(fabric.lang.Object arg1);

    @Override
    native public fabric.lang.Object put(fabric.lang.Object arg1,
        fabric.lang.Object arg2);

    @Override
    native public int hashCode();

    @Override
    native public boolean isEmpty();

    @Override
    native public fabric.util.Set keySet();

    @Override
    native public void putAll(fabric.util.Map arg1);

    @Override
    native public fabric.lang.Object remove(fabric.lang.Object arg1);

    @Override
    native public int size();

    @Override
    native public fabric.util.Collection values();

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
