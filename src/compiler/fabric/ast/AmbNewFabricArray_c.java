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

import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.TypeNode;
import polyglot.util.Position;
import jif.ast.AmbNewArray_c;

public class AmbNewFabricArray_c
     extends AmbNewArray_c
  implements AmbNewFabricArray {

  protected Expr loc;
  
  public AmbNewFabricArray_c(Position pos, TypeNode baseType, Expr loc, Object expr, List dims, int addDims) {
    super(pos, baseType, expr, dims, addDims);
    this.loc = loc;
  }

  
  public Expr location() {
    return this.loc;
  }
  
  public AmbNewFabricArray location(Expr loc) {
    AmbNewFabricArray_c result = (AmbNewFabricArray_c) copy();
    result.loc = loc;
    return result;
  }
}
