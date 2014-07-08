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
import fabric.net.UnreachableNodeException;

public interface PairLabel extends Label {
  public static class _Impl extends fabric.lang.Object._Impl implements
      PairLabel {
    public _Impl(Store store, Label label, ConfPolicy conf, IntegPolicy integ)
        throws UnreachableNodeException {
      super(store, label);
    }

    public native ConfPolicy confPolicy();

    public native IntegPolicy integPolicy();

    public native SecretKeyObject keyObject();
  }
}
