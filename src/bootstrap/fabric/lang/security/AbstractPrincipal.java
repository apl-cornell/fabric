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

import fabric.worker.Store;

public interface AbstractPrincipal extends DelegatingPrincipal,
    fabric.lang.Object {
  public static class _Proxy extends DelegatingPrincipal._Proxy implements
      AbstractPrincipal {

    public _Proxy(AbstractPrincipal._Impl impl) {
      super(impl);
    }

    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    @Override
    public native void addDelegatesTo(Principal p);

    @Override
    public native void removeDelegatesTo(Principal p);
  }

  public abstract static class _Impl extends DelegatingPrincipal._Impl
      implements AbstractPrincipal {

    public _Impl(Store store, Label label, String name) {
      super(store, label);
    }

    @Override
    public native void addDelegatesTo(Principal p);

    @Override
    public native void removeDelegatesTo(Principal p);
  }
}
