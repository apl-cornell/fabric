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
package fabric.worker.transaction;

import fabric.common.exceptions.FabricRuntimeException;
import fabric.lang.Object._Impl;
import fabric.lang.Object._Proxy;

public final class VersionConflictException extends FabricRuntimeException {
  public _Proxy reference;

  public VersionConflictException(_Impl obj) {
    this(obj.$getProxy());
  }

  public VersionConflictException(_Proxy reference) {
    this.reference = reference;
  }
}
