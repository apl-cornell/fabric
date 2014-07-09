/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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

import fabric.worker.Store;

public class Oid {
  final Store store;
  final long onum;

  public Oid(Store store, long onum) {
    if (store == null) {
      throw new IllegalArgumentException("store cannot be null");
    }

    this.store = store;
    this.onum = onum;
  }

  public Oid(fabric.lang.Object o) {
    this(o.$getStore(), o.$getOnum());
  }

  @Override
  public int hashCode() {
    return store.hashCode() ^ (int) (onum ^ (onum >>> 32));
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Oid)) return false;
    Oid other = (Oid) obj;
    return onum == other.onum && store.equals(other.store);
  }

  @Override
  public String toString() {
    return "fab://" + store.name() + "/" + onum;
  }
}