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
package fabric.lang.security;

public interface ConfPolicy extends fabric.lang.security.Policy,
    fabric.lang.Object {

  fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p, java.util.Set dependencies);

  fabric.lang.security.ConfPolicy join(fabric.lang.security.ConfPolicy p,
      java.util.Set dependencies);

  fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p, java.util.Set dependencies);

  fabric.lang.security.ConfPolicy meet(fabric.lang.security.ConfPolicy p,
      java.util.Set dependencies);

  fabric.lang.security.ConfPolicy join(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p);

  fabric.lang.security.ConfPolicy join(fabric.lang.security.ConfPolicy p);

  fabric.lang.security.ConfPolicy meet(fabric.worker.Store store,
      fabric.lang.security.ConfPolicy p);

  fabric.lang.security.ConfPolicy meet(fabric.lang.security.ConfPolicy p);

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.ConfPolicy {

    @Override
    native public fabric.lang.security.ConfPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
        java.util.Set arg3);

    @Override
    native public fabric.lang.security.ConfPolicy join(
        fabric.lang.security.ConfPolicy arg1, java.util.Set arg2);

    @Override
    native public fabric.lang.security.ConfPolicy meet(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2,
        java.util.Set arg3);

    @Override
    native public fabric.lang.security.ConfPolicy meet(
        fabric.lang.security.ConfPolicy arg1, java.util.Set arg2);

    @Override
    native public fabric.lang.security.ConfPolicy join(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2);

    @Override
    native public fabric.lang.security.ConfPolicy join(
        fabric.lang.security.ConfPolicy arg1);

    @Override
    native public fabric.lang.security.ConfPolicy meet(
        fabric.worker.Store arg1, fabric.lang.security.ConfPolicy arg2);

    @Override
    native public fabric.lang.security.ConfPolicy meet(
        fabric.lang.security.ConfPolicy arg1);

    @Override
    native public boolean relabelsTo(fabric.lang.security.Policy arg1,
        java.util.Set arg2);

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
