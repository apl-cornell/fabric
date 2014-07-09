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
package fabric.ast;

import java.util.List;

import polyglot.ast.ArrayInit;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.NewArray_c;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

//XXX Should be replaced with extension
@Deprecated
public class NewFabricArray_c extends NewArray_c implements NewFabricArray {
  @Deprecated
  public NewFabricArray_c(Position pos, TypeNode baseType, List<Expr> dims,
      int addDims, ArrayInit init) {
    this(pos, baseType, dims, addDims, init, null);
  }

  public NewFabricArray_c(Position pos, TypeNode baseType, List<Expr> dims,
      int addDims, ArrayInit init, Ext ext) {
    super(pos, baseType, dims, addDims, init, ext);
  }
}
