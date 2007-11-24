package fabric.lang;

import fabric.client.Core;
import fabric.common.InternalError;

public class WrappedJavaInlineable<T> implements JavaInlineable {
  public final T obj;

  public WrappedJavaInlineable(T obj) {
    this.obj = obj;
  }

  /**
   * Returns an array containing the elements of the given array, individually
   * wrapped.
   */
  @SuppressWarnings("unchecked")
  public static <T> WrappedJavaInlineable<T>[] wrap(T[] array) {
    WrappedJavaInlineable<T>[] result =
        new WrappedJavaInlineable[array.length];
    for (int i = 0; i < array.length; i++)
      result[i] = new WrappedJavaInlineable<T>(array[i]);
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

  /*
   * (non-Javadoc)
   * 
   * @see fabric.lang.Object#$getProxy()
   */
  public fabric.lang.Object.$Proxy $getProxy() {
    throw new InternalError("WrappedJavaInlineables don't have proxies.");
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.lang.Object#equals(fabric.lang.Object)
   */
  public boolean equals(Object o) {
    if (!(o instanceof WrappedJavaInlineable)) return false;
    java.lang.Object obj = ((WrappedJavaInlineable) o).obj;
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
      return equals((WrappedJavaInlineable) obj);
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
}
