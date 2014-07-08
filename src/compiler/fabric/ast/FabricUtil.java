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
package fabric.ast;

import polyglot.ast.Ext;
import polyglot.ast.Node;
import fabric.extension.FabricExt;

public class FabricUtil {
  /**
   * Gets the Fabric extension. 
   * Note that Fabric extensions are NOT subclasses of Jif extensions.
   * @param n
   */
  public static FabricExt fabricExt(Node n) {
    Ext ext = n.ext();
    while (ext != null && !(ext instanceof FabricExt)) {
      ext = ext.ext();
    }
    return (FabricExt)ext;
  }
  
  public static Node updateFabricExt(Node n, FabricExt fab) {
    return n.ext(updateFabricExt(n.ext(), fab));
  }

  private static Ext updateFabricExt(Ext e, FabricExt fab) {
    if (e instanceof FabricExt) return fab;
    if (e == null) return e;
    return e.ext(updateFabricExt(e.ext(), fab));
  }
}
