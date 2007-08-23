package OO7.util;

public class Arrays {
  public Object[] copyOf(Object[] original, int newLength) {
    Object[] result = new Object[newLength];
    int i = 0;
    while (i < newLength && i < original.length) {
      result[i] = original[i];
      i++;
    }
    while (i < newLength) result[i++] = null;
    return result;
  }
}