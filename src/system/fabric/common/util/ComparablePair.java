package fabric.common.util;

public class ComparablePair<T1 extends Comparable<T1>, T2 extends Comparable<T2>>
extends Pair<T1, T2> implements Comparable<ComparablePair<T1, T2>> {
  public ComparablePair(T1 first, T2 second) {
    super(first, second);
  }

  @Override
  public int compareTo(ComparablePair<T1, T2> other) {
    int compare = 0;
    if (first != other.first) {
      if (first != null)
        compare = first.compareTo(other.first);
      else compare = -other.first.compareTo(first);
    }

    if (compare != 0) return compare;

    if (second == other.second) return 0;
    if (second != null) return second.compareTo(other.second);
    return -other.second.compareTo(second);
  }
}
