package fabric.common.util;

public class Triple<T1, T2, T3> {
  public T1 first;
  public T2 second;
  public T3 third;

  public Triple(T1 first, T2 second, T3 third) {
    this.first = first;
    this.second = second;
    this.third = third;
  }

  @Override
  public String toString() {
    return "(" + first + "," + second + "," + third + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Triple<?, ?, ?>)) return false;

    Triple<?, ?, ?> p = (Triple<?, ?, ?>) o;
    return equals(first, p.first) && equals(second, p.second)
        && equals(third, p.third);
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
        ^ (second == null ? 0 : second.hashCode())
        ^ (third == null ? 0 : third.hashCode());
  }
}
