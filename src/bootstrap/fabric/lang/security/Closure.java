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

public interface Closure extends fabric.lang.Object {

  java.lang.Object invoke();

  fabric.lang.security.Principal jif$getfabric_lang_security_Closure_P();

  fabric.lang.security.Label jif$getfabric_lang_security_Closure_L();

  public static class _Proxy extends fabric.lang.Object._Proxy implements
      fabric.lang.security.Closure {

    @Override
    native public java.lang.Object invoke();

    @Override
    native public fabric.lang.security.Principal jif$getfabric_lang_security_Closure_P();

    @Override
    native public fabric.lang.security.Label jif$getfabric_lang_security_Closure_L();

    public _Proxy(fabric.worker.Store store, long onum) {
      super(store, onum);
    }
  }

}
