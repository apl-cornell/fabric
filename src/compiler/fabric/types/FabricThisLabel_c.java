/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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

import jif.types.JifTypeSystem;
import jif.types.label.ThisLabel;
import jif.types.label.ThisLabel_c;
import polyglot.types.ReferenceType;
import polyglot.util.Position;
import fabric.translate.FabricThisLabelToFabilExpr_c;

/**
 * 
 */
public class FabricThisLabel_c extends ThisLabel_c implements ThisLabel {

  /**
   * @param ts
   * @param ct
   * @param pos
   */
  public FabricThisLabel_c(JifTypeSystem ts, ReferenceType ct, Position pos) {
    super(ts, ct, pos);
    this.toJava = new FabricThisLabelToFabilExpr_c();
  }

}
