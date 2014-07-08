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

import java.util.HashMap;
import java.util.Map;

public class TwoKeyHashMap {
  public static <T, U, V> boolean containsKey(Map<T, HashMap<U, V>> map, T t,
      U u) {
    HashMap<U, V> inner = map.get(t);
    return inner != null && inner.containsKey(u);
  }

  public static <T, V> V get(Map<T, LongKeyHashMap<V>> map, T t, long u) {
    LongKeyHashMap<V> inner = map.get(t);
    return inner == null ? null : inner.get(u);
  }

  public static <T, V> V put(Map<T, LongKeyHashMap<V>> map, T t, long u, V v) {
    LongKeyHashMap<V> inner = map.get(t);
    if (inner == null) {
      inner = new LongKeyHashMap<V>();
      map.put(t, inner);
    }

    return inner.put(u, v);
  }
}
