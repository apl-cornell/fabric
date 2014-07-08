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

import polyglot.types.ClassType;
import polyglot.util.Position;
import jif.types.JifSubst;
import jif.types.JifSubstClassType_c;
import jif.types.JifTypeSystem;
import jif.types.label.Label;

public class FabricSubstClassType_c extends JifSubstClassType_c implements FabricSubstType {
  public FabricSubstClassType_c(JifTypeSystem ts, Position pos, ClassType base, JifSubst subst) {
    super(ts, pos, base, subst);
  }
  
  public Label defaultFieldLabel() {
    FabricParsedClassType base = (FabricParsedClassType)base();
    Label l = base.defaultFieldLabel();
    if (l == null) return null;
    
    JifSubst subst = (JifSubst)subst();
    return subst.substLabel(base.defaultFieldLabel());
  }

  public Label defaultFabilFieldLabel() {
    // TODO Auto-generated method stub
    return defaultFieldLabel();
  }
}
