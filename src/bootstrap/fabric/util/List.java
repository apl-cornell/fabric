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
package fabric.util;

public interface List extends fabric.util.Collection, fabric.lang.Object {

  void add(int index, fabric.lang.Object o);

  @Override
  boolean add(fabric.lang.Object o);

  boolean addAll(int index, fabric.util.Collection c);

  @Override
  boolean addAll(fabric.util.Collection c);

  @Override
  void clear();

  @Override
  boolean contains(fabric.lang.Object o);

  @Override
  boolean containsAll(fabric.util.Collection c);

  @Override
  boolean equals(fabric.lang.Object o);

  fabric.lang.Object get(int index);

  @Override
  int hashCode();

  int indexOf(fabric.lang.Object o);

  @Override
  boolean isEmpty();

  @Override
  fabric.util.Iterator iterator(fabric.worker.Store store);

  int lastIndexOf(fabric.lang.Object o);

  fabric.util.ListIterator listIterator(fabric.worker.Store store);

  fabric.util.ListIterator listIterator(fabric.worker.Store store, int index);

  fabric.lang.Object remove(int index);

  @Override
  boolean remove(fabric.lang.Object o);

  @Override
  boolean removeAll(fabric.util.Collection c);

  @Override
  boolean retainAll(fabric.util.Collection c);

  fabric.lang.Object set(int index, fabric.lang.Object o);

  @Override
  int size();

  fabric.util.List subList(int fromIndex, int toIndex);

  @Override
  fabric.lang.arrays.ObjectArray toArray();

  @Override
  fabric.lang.arrays.ObjectArray toArray(fabric.lang.arrays.ObjectArray a);

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.List {

    @Override
    native public void add(int arg1, fabric.lang.Object arg2);

    @Override
    native public boolean add(fabric.lang.Object arg1);

    @Override
    native public boolean addAll(int arg1, fabric.util.Collection arg2);

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
    native public fabric.lang.Object get(int arg1);

    @Override
    native public int hashCode();

    @Override
    native public int indexOf(fabric.lang.Object arg1);

    @Override
    native public boolean isEmpty();

    @Override
    native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

    @Override
    native public int lastIndexOf(fabric.lang.Object arg1);

    @Override
    native public fabric.util.ListIterator listIterator(fabric.worker.Store arg1);

    @Override
    native public fabric.util.ListIterator listIterator(
        fabric.worker.Store arg1, int arg2);

    @Override
    native public fabric.lang.Object remove(int arg1);

    @Override
    native public boolean remove(fabric.lang.Object arg1);

    @Override
    native public boolean removeAll(fabric.util.Collection arg1);

    @Override
    native public boolean retainAll(fabric.util.Collection arg1);

    @Override
    native public fabric.lang.Object set(int arg1, fabric.lang.Object arg2);

    @Override
    native public int size();

    @Override
    native public fabric.util.List subList(int arg1, int arg2);

    @Override
    native public fabric.lang.arrays.ObjectArray toArray();

    @Override
    native public fabric.lang.arrays.ObjectArray toArray(
        fabric.lang.arrays.ObjectArray arg1);

    @Override
    native public fabric.util.Iterator iterator();

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
