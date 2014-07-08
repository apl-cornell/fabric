/**
 * Copyright (C) 2010 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.common.util;

public class ComparablePair<T1 extends Comparable<T1>, T2 extends Comparable<T2>>
    extends Pair<T1, T2> implements Comparable<ComparablePair<T1, T2>> {
  public ComparablePair(T1 first, T2 second) {
    super(first, second);
  }

  public int compareTo(ComparablePair<T1, T2> other) {
    int compare = 0;
    if (first != other.first) {
      if (first != null) compare = first.compareTo(other.first);
      else compare = -other.first.compareTo(first);
    }
    
    if (compare != 0) return compare;
    
    if (second == other.second) return 0;
    if (second != null) return second.compareTo(other.second);
    return -other.second.compareTo(second);
  }
}
