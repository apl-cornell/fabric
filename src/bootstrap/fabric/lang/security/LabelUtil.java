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
import fabric.lang.Object;

public interface LabelUtil extends Object {
  
  public static class _Impl extends Object._Impl implements LabelUtil {

    protected _Impl(Store store, Label label) {
      super(store, label);
    }
    
    public static native boolean isReadableBy(Label lbl, Principal p);
    
    public static native boolean isWritableBy(Label lbl, Principal p);
    
    public static native ConfPolicy readerPolicy(Store store, Principal owner,
        Principal reader);
    
    public static native IntegPolicy writerPolicy(Store store, Principal owner,
        Principal writer);
    
    public static native Label toLabel(Store store, ConfPolicy confidPolicy,
        IntegPolicy integPolicy);
    
    public static native Label join(Store store, Label l1, Label l2);
    
    public static native boolean relabelsTo(Label from, Label to);
  }
}
