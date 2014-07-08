/**
 * Copyright (C) 2010 Fabric project group, Cornell University
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

import java.security.PrivateKey;

import fabric.worker.Store;
import fabric.net.UnreachableNodeException;

public interface PrivateKeyObject extends fabric.lang.Object {
  PrivateKey getKey();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      PrivateKeyObject {
    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    public native PrivateKey getKey();
  }

  public static class _Impl extends fabric.lang.Object._Impl implements
      PrivateKeyObject {
    public _Impl(Store store, Label label, PrivateKey key)
        throws UnreachableNodeException {
      super(store, label);
    }

    public native PrivateKey getKey();
  }
}
