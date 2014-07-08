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

import polyglot.types.Type;
import polyglot.util.Position;
import jif.types.ConstArrayType_c;

/** The only ArrayType class created by the FabricTypeSystem.
 * @see FabricTypeSystem for further description
 * @author mdgeorge
 *
 */
public class FabricArrayType_c
     extends ConstArrayType_c
  implements FabricArrayType {
  
  protected boolean isNative;
  
  public FabricArrayType_c(FabricTypeSystem ts, Position pos, Type base,
                           boolean isConst, boolean isNonConst,
                           boolean isNative) {
    super(ts, pos, base, isConst, isNonConst);
    this.isNative = isNative;
  }

  public boolean isNative() {
    return isNative;
  }
  
}
