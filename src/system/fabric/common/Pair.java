package fabric.common;

public class Pair<T1,T2> {
  public T1 first;
  public T2 second;

  public Pair(T1 first, T2 second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Pair)) return false;
    Pair<?,?> p = (Pair)o;
    if (first == null) return p.first == null;
    else if (!first.equals(p.first)) return false;
    return second == null ? p.second == null : second.equals(p.second);
  }

  @Override
  public int hashCode() {
    // TODO Auto-generated method stub
    return first.hashCode() ^ second.hashCode();
  }
}

