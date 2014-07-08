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

public interface Enumeration extends fabric.lang.Object {

  boolean hasMoreElements();

  fabric.lang.Object nextElement();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.util.Enumeration {

    @Override
    native public boolean hasMoreElements();

    @Override
    native public fabric.lang.Object nextElement();

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
