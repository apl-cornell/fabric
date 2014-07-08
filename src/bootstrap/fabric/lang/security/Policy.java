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
package fabric.lang.security;

public interface Policy extends fabric.lang.Object {

  boolean relabelsTo(fabric.lang.security.Policy p, java.util.Set s);

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.Policy {

    @Override
    native public boolean relabelsTo(fabric.lang.security.Policy arg1,
        java.util.Set arg2);

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
