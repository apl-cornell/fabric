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

public interface NodePrincipal extends AbstractPrincipal, fabric.lang.Object {
  public static class _Proxy extends AbstractPrincipal._Proxy implements
      NodePrincipal {

    public _Proxy(Store store, long onum) {
      super(store, onum);
    }

    @Override
    public native boolean delegatesTo(Principal p);

    @Override
    public native boolean equals(Principal p);

    @Override
    public native ActsForProof findProofDownto(Store store, Principal q,
        java.lang.Object searchState);

    @Override
    public native ActsForProof findProofUpto(Store store, Principal p,
        java.lang.Object searchState);

    @Override
    public native boolean isAuthorized(java.lang.Object authPrf,
        Closure closure, Label lb, boolean executeNow);

    @Override
    public native String name();
  }

  public static final class _Impl extends AbstractPrincipal._Impl implements
      NodePrincipal {
    public _Impl(fabric.worker.Store $location,
        fabric.lang.security.Label $label, java.lang.String name) {
      super($location, $label, name);
    }

    @Override
    public native boolean delegatesTo(Principal p);

    @Override
    public native boolean equals(Principal p);

    @Override
    public native ActsForProof findProofDownto(Store store, Principal q,
        java.lang.Object searchState);

    @Override
    public native ActsForProof findProofUpto(Store store, Principal p,
        java.lang.Object searchState);

    @Override
    public native boolean isAuthorized(java.lang.Object authPrf,
        Closure closure, Label lb, boolean executeNow);

    @Override
    public native String name();
  }
}
