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

public interface ListIterator extends fabric.util.Iterator, fabric.lang.Object {

  @Override
  boolean hasNext();

  boolean hasPrevious();

  @Override
  fabric.lang.Object next();

  fabric.lang.Object previous();

  int nextIndex();

  int previousIndex();

  void add(fabric.lang.Object o);

  @Override
  void remove();

  void set(fabric.lang.Object o);

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.ListIterator {

    @Override
    native public boolean hasNext();

    @Override
    native public boolean hasPrevious();

    @Override
    native public fabric.lang.Object next();

    @Override
    native public fabric.lang.Object previous();

    @Override
    native public int nextIndex();

    @Override
    native public int previousIndex();

    @Override
    native public void add(fabric.lang.Object arg1);

    @Override
    native public void remove();

    @Override
    native public void set(fabric.lang.Object arg1);

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
