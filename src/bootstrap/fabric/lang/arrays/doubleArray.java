/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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
package fabric.lang.arrays;

import fabric.lang.Object;
import fabric.lang.security.ConfPolicy;
import fabric.lang.security.Label;
import fabric.worker.Store;

public interface doubleArray extends Object {
  double get(int i);

  double set(int i, double value);

  public static class _Impl extends Object._Impl implements doubleArray {
    public _Impl(Store store, Label label, ConfPolicy accessPolicy, int length) {
      super(store);
    }

    @Override
    public native double get(int i);

    @Override
    public native double set(int i, double value);
  }
}
