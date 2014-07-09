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

public interface Label extends fabric.lang.Object {

  boolean relabelsTo(fabric.lang.security.Label l, java.util.Set s);

  fabric.lang.security.Label join(fabric.worker.Store store,
      fabric.lang.security.Label l);

  fabric.lang.security.Label meet(fabric.worker.Store store,
      fabric.lang.security.Label l);

  fabric.lang.security.ConfPolicy confPolicy();

  fabric.lang.security.IntegPolicy integPolicy();

  fabric.lang.security.SecretKeyObject keyObject();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.Label {

    @Override
    native public boolean relabelsTo(fabric.lang.security.Label arg1,
        java.util.Set arg2);

    @Override
    native public fabric.lang.security.Label join(fabric.worker.Store arg1,
        fabric.lang.security.Label arg2);

    @Override
    native public fabric.lang.security.Label meet(fabric.worker.Store arg1,
        fabric.lang.security.Label arg2);

    @Override
    native public fabric.lang.security.ConfPolicy confPolicy();

    @Override
    native public fabric.lang.security.IntegPolicy integPolicy();

    @Override
    native public fabric.lang.security.SecretKeyObject keyObject();

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
