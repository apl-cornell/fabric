/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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
package fabric.util;

public interface SortedSet extends fabric.util.Set, fabric.lang.Object {

  fabric.util.Comparator comparator();

  fabric.lang.Object first();

  fabric.util.SortedSet headSet(fabric.lang.Object toElement);

  fabric.lang.Object last();

  fabric.util.SortedSet subSet(fabric.lang.Object fromElement,
      fabric.lang.Object toElement);

  fabric.util.SortedSet tailSet(fabric.lang.Object fromElement);

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.SortedSet {

    @Override
    native public fabric.util.Comparator comparator();

    @Override
    native public fabric.lang.Object first();

    @Override
    native public fabric.util.SortedSet headSet(fabric.lang.Object arg1);

    @Override
    native public fabric.lang.Object last();

    @Override
    native public fabric.util.SortedSet subSet(fabric.lang.Object arg1,
        fabric.lang.Object arg2);

    @Override
    native public fabric.util.SortedSet tailSet(fabric.lang.Object arg1);

    @Override
    native public boolean add(fabric.lang.Object arg1);

    @Override
    native public boolean addAll(fabric.util.Collection arg1);

    @Override
    native public void clear();

    @Override
    native public boolean contains(fabric.lang.Object arg1);

    @Override
    native public boolean containsAll(fabric.util.Collection arg1);

    @Override
    native public boolean equals(fabric.lang.Object arg1);

    @Override
    native public int hashCode();

    @Override
    native public boolean isEmpty();

    @Override
    native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

    @Override
    native public fabric.util.Iterator iterator();

    @Override
    native public boolean remove(fabric.lang.Object arg1);

    @Override
    native public boolean removeAll(fabric.util.Collection arg1);

    @Override
    native public boolean retainAll(fabric.util.Collection arg1);

    @Override
    native public int size();

    @Override
    native public fabric.lang.arrays.ObjectArray toArray();

    @Override
    native public fabric.lang.arrays.ObjectArray toArray(
        fabric.lang.arrays.ObjectArray arg1);

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
