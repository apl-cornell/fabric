package fabric.common.util;

import java.io.Serializable;

public class Pair<T1, T2> implements Serializable {
  public T1 first;
  public T2 second;

  public Pair(T1 first, T2 second) {
    this.first = first;
    this.second = second;
  }

  // Deserialization constructor.
  @SuppressWarnings("unused")
  private Pair() {
  }

  @Override
  public String toString() {
    return "(" + first + "," + second + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Pair<?, ?>)) return false;

    Pair<?, ?> p = (Pair<?, ?>) o;
    return equals(first, p.first) && equals(second, p.second);
  }

  private boolean equals(Object o1, Object o2) {
    if (o1 == o2) return true;
    if (o1 == null || o2 == null) return false;
    return o1.equals(o2);
  }

  @Override
  public int hashCode() {
    // This hash code implementation could probably be improved.
    return (first == null ? 0 : first.hashCode())
        ^ (second == null ? 0 : second.hashCode());
  }
}
