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
package fabil.ast;

import polyglot.ast.ArrayAccess;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;
import fabil.types.FabILTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class ArrayAccessAssign_c extends polyglot.ast.ArrayAccessAssign_c {

  @Deprecated
  public ArrayAccessAssign_c(Position pos, ArrayAccess left, Operator op,
      Expr right) {
    this(pos, left, op, right, null);
  }

  public ArrayAccessAssign_c(Position pos, ArrayAccess left, Operator op,
      Expr right, Ext ext) {
    super(pos, left, op, right, ext);
  }

  @Override
  public Type childExpectedType(Expr child, AscriptionVisitor av) {
    // fabric arrays of java inlineables expect fabric objects, not java objects
    if (child == right) {
      FabILTypeSystem ts = (FabILTypeSystem) av.typeSystem();
      Type array = ((ArrayAccess) left).array().type();
      Type base = array.toArray().ultimateBase();
      if (ts.isJavaInlineable(base) && ts.isFabricArray(array))
        return ts.FObject();
    }
    return super.childExpectedType(child, av);
  }

}
