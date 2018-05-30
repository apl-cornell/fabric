package fabric.lang;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.MapMaker;

import fabric.common.exceptions.InternalError;
import fabric.lang.arrays.ObjectArray;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.worker.Store;
import fabric.worker.metrics.ImmutableObserverSet;
import fabric.worker.metrics.ImmutableSet;
import fabric.worker.metrics.treaties.TreatySet;

public class WrappedJavaInlineable<T> implements JavaInlineable {

  public final T obj;

  private WrappedJavaInlineable(T obj) {
    this.obj = obj;
  }

  /**
   * Maps ordinary Java objects to their JavaInlineable wrappers.
   */
  private static final ConcurrentMap<java.lang.Object, WrappedJavaInlineable<?>> $wrappingMap =
      new MapMaker().concurrencyLevel(16).weakKeys().weakValues().makeMap();

  /**
   * Given an object that, in the Fabric type system, implements
   * fabric.lang.JavaInlineable, returns a wrapped version of that object. If
   * the given object is already wrapped, it is returned unmodified.
   */
  public static final Object $wrap(java.lang.Object obj) {
    if (obj == null || obj instanceof Object) return (Object) obj;

    WrappedJavaInlineable<java.lang.Object> newWrapped =
        new WrappedJavaInlineable<>(obj);
    WrappedJavaInlineable<?> existing =
        $wrappingMap.putIfAbsent(obj, newWrapped);
    if (existing != null) return existing;
    return newWrapped;
  }

  /**
   * Given a Fabric object, returns an unwrapped version of that object. If the
   * given object is already unwrapped, it is returned unmodified.
   */
  public static final java.lang.Object $unwrap(Object obj) {
    if (obj == null) return null;
    return obj.$unwrap();
  }

  /**
   * Creates a Fabric version of the given array. All elements of the given
   * array should be instances of fabric.lang.Object from the Fabric type
   * system's point of view.
   */
  public static ObjectArray $wrap(Store store, Label label,
      ConfPolicy accessPolicy, java.lang.Object[] array) {
    ObjectArray result =
        new ObjectArray._Impl(store).fabric$lang$arrays$ObjectArray$(label,
            accessPolicy, Object._Proxy.class, array.length);
    for (int i = 0; i < array.length; i++)
      result.set(i, $wrap(array[i]));
    return result;
  }

  @Override
  public Store $getStore() {
    throw new InternalError("WrappedJavaInlineables don't have stores.");
  }

  @Override
  public long $getOnum() {
    throw new InternalError("WrappedJavaInlineables don't have onums.");
  }

  @Override
  public Label get$$updateLabel() {
    throw new InternalError("WrappedJavaInlineables don't have labels.");
  }

  @Override
  public Label set$$updateLabel(Label val) {
    throw new InternalError("WrappedJavaInlineables don't have labels.");
  }

  @Override
  public TreatySet get$$treaties() {
    throw new InternalError("WrappedJavaInlineables don't have treaties.");
  }

  @Override
  public TreatySet set$$treaties(TreatySet val) {
    throw new InternalError("WrappedJavaInlineables don't have treaties.");
  }

  @Override
  public ImmutableObserverSet get$$observers() {
    throw new InternalError("WrappedJavaInlineables don't have observers.");
  }

  @Override
  public ImmutableObserverSet set$$observers(ImmutableObserverSet val) {
    throw new InternalError("WrappedJavaInlineables don't have observers.");
  }

  @Override
  public ImmutableSet get$$associated() {
    throw new InternalError("WrappedJavaInlineables don't have associated.");
  }

  @Override
  public ImmutableSet set$$associated(ImmutableSet val) {
    throw new InternalError("WrappedJavaInlineables don't have associated.");
  }

  @Override
  public ConfPolicy get$$accessPolicy() {
    throw new InternalError(
        "WrappedJavaInlineables don't have access policies.");
  }

  @Override
  public ConfPolicy set$$accessPolicy(ConfPolicy val) {
    throw new InternalError(
        "WrappedJavaInlineables don't have access policies.");
  }

  @Override
  public Object $initLabels() {
    return this;
  }

  @Override
  public void $initPartitions() {
  }

  @Override
  public Object fabric$lang$Object$() {
    return this;
  }

  @Override
  public boolean idEquals(fabric.lang.Object other) {
    return obj == other;
  }

  @Override
  public int oidHashCode() {
    throw new InternalError("WrappedJavaInlineables don't have oids.");
  }

  @Override
  public fabric.lang.Object._Proxy $getProxy() {
    throw new InternalError("WrappedJavaInlineables don't have proxies.");
  }

  @Override
  public Object fetch() {
    return this;
  }

  @Override
  public T $unwrap() {
    return obj;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof WrappedJavaInlineable<?>)) return false;
    java.lang.Object obj = ((WrappedJavaInlineable<?>) o).obj;
    if (obj == null) return this.obj == null;
    return obj.equals(this.obj);
  }

  @Override
  public boolean equals(java.lang.Object obj) {
    if (obj instanceof WrappedJavaInlineable<?>)
      return equals((WrappedJavaInlineable<?>) obj);
    if (obj == null) return this.obj == null;
    return obj.equals(this.obj);
  }

  @Override
  public int hashCode() {
    return obj == null ? 0 : obj.hashCode();
  }

  @Override
  public String toString() {
    return obj == null ? "null" : obj.toString();
  }

  @Override
  public void $forceRenumber(long onum) {
    throw new InternalError("Unsupported operation");
  }
}
