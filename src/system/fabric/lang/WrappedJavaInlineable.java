package fabric.lang;

import java.util.Map;
import java.util.WeakHashMap;

import fabric.client.Core;
import fabric.common.exceptions.InternalError;
import fabric.lang.arrays.ObjectArray;
import jif.lang.Label;

public class WrappedJavaInlineable<T> implements JavaInlineable {
  
  public final T obj;

  private WrappedJavaInlineable(T obj) {
    this.obj = obj;
  }

  /**
   * Maps ordinary Java objects to their JavaInlineable wrappers.
   */
  private static final Map<java.lang.Object, WrappedJavaInlineable<?>> $wrappingMap =
      new WeakHashMap<java.lang.Object, WrappedJavaInlineable<?>>();

  /**
   * Given an object that, in the Fabric type system, implements
   * fabric.lang.JavaInlineable, returns a wrapped version of that object. If
   * the given object is already wrapped, it is returned unmodified.
   */
  public static final Object $wrap(java.lang.Object obj) {
    if (obj == null || obj instanceof Object) return (Object) obj;

    if ($wrappingMap.containsKey(obj)) return $wrappingMap.get(obj);

    WrappedJavaInlineable<?> result =
        new WrappedJavaInlineable<java.lang.Object>(obj);
    $wrappingMap.put(obj, result);
    return result;
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
  public static ObjectArray $wrap(Core core, Label label, java.lang.Object[] array) {
    ObjectArray result = new ObjectArray._Impl(core, label, Object._Proxy.class,
        array.length);
    for (int i = 0; i < array.length; i++)
      result.set(i, $wrap(array[i]));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.lang.Object#$getCore()
   */
  public Core $getCore() {
    throw new InternalError("WrappedJavaInlineables don't have cores.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.lang.Object#$getOnum()
   */
  public long $getOnum() {
    throw new InternalError("WrappedJavaInlineables don't have onums.");
  }

  public Label get$label() {
    throw new InternalError("WrappedJavaInlineables don't have labels.");
  }

  public Label set$label(Label label) {
    throw new InternalError("WrappedJavaInlineables don't have labels.");
  }
  
  public boolean idEquals(fabric.lang.Object other) {
    return obj == other;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.lang.Object#$getProxy()
   */
  public fabric.lang.Object._Proxy $getProxy() {
    throw new InternalError("WrappedJavaInlineables don't have proxies.");
  }

  public Object fetch() {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.lang.Object#$unwrap()
   */
  public T $unwrap() {
    return obj;
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.lang.Object#equals(fabric.lang.Object)
   */
  public boolean equals(Object o) {
    if (!(o instanceof WrappedJavaInlineable)) return false;
    java.lang.Object obj = ((WrappedJavaInlineable<?>) o).obj;
    if (obj == null) return this.obj == null;
    return obj.equals(this.obj);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(java.lang.Object obj) {
    if (obj instanceof WrappedJavaInlineable)
      return equals((WrappedJavaInlineable<?>) obj);
    if (obj == null) return this.obj == null;
    return obj.equals(this.obj);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return obj == null ? 0 : obj.hashCode();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return obj == null ? "null" : obj.toString();
  }

  public void $forceRenumber(long onum) {
    throw new InternalError("Unsupported operation");
  }
  
  public Statistics createStatistics() {
    return DefaultStatistics.instance;
  }
}
