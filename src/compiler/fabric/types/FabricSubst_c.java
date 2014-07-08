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

import java.util.Map;

import polyglot.types.ClassType;

import jif.types.JifClassType;
import jif.types.JifSubst_c;
import jif.types.JifTypeSystem;

public class FabricSubst_c extends JifSubst_c {
  public FabricSubst_c(JifTypeSystem ts, Map subst, Map cache) {
    super(ts, subst, cache);
  }
  
  @Override
  public ClassType substClassType(ClassType t) {
    // Don't bother trying to substitute into a non-Jif class.
    if (! (t instanceof JifClassType)) {
        return t;
    }

    return new FabricSubstClassType_c((JifTypeSystem) ts, t.position(),
                                      (JifClassType) t, this);
  }
}
