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
package fabric.util;

public interface Iterable extends fabric.lang.Object {

  fabric.util.Iterator iterator(fabric.worker.Store store);

  fabric.util.Iterator iterator();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.Iterable {

    @Override
    native public fabric.util.Iterator iterator(fabric.worker.Store arg1);

    @Override
    native public fabric.util.Iterator iterator();

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
