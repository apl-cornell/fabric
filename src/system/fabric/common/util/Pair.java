/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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

public class Pair<T1, T2> {
  public T1 first;
  public T2 second;

  public Pair(T1 first, T2 second) {
    this.first = first;
    this.second = second;
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
