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
package fabric.types;

import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import jif.types.JifContext_c;
import jif.types.JifTypeSystem;

public class FabricContext_c extends JifContext_c {
  protected FabricContext_c(JifTypeSystem ts, TypeSystem jlts) {
    super(ts, jlts);
  }
  
  @Override
  public LocalInstance findLocal(String name) throws SemanticException {
    if (name.equals("worker$") || name.equals("worker$'")) {
      return ((FabricTypeSystem)typeSystem()).workerLocalInstance();
    }
    else if (name.endsWith("'")) {
      // XXX HACK!
      return super.findLocal(name.substring(0, name.length() - 1));
    }
    return super.findLocal(name);
  }
}
