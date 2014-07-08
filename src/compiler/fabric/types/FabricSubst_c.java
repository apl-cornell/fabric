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
package fabric.types;

import java.util.Map;

import jif.types.JifClassType;
import jif.types.JifSubst_c;
import jif.types.JifTypeSystem;
import jif.types.Param;
import jif.types.ParamInstance;
import polyglot.types.ClassType;

public class FabricSubst_c extends JifSubst_c {
  public FabricSubst_c(JifTypeSystem ts,
      Map<ParamInstance, ? extends Param> subst) {
    super(ts, subst);
  }

  @Override
  public ClassType substClassType(ClassType t) {
    // Don't bother trying to substitute into a non-Jif class.
    if (!(t instanceof JifClassType)) {
      return t;
    }

    return new FabricSubstClassType_c((JifTypeSystem) ts, t.position(),
        (JifClassType) t, this);
  }
}
