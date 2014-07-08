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
package fabric.util;

import fabric.lang.security.Label;
import fabric.worker.Store;
import fabric.lang.Object;

public interface HashMap extends Map, AbstractMap {
  public static class _Impl extends AbstractMap._Impl implements HashMap {
    public _Impl(Store store, Label label) {
      super(store, label);
    }
    
    public native Object get(Object key);
    
    public native Object put(Object key, Object value);
  }
}
