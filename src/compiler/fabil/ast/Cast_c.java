/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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

import polyglot.ast.Expr;
import polyglot.ast.TypeNode;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;
import fabil.types.FabILTypeSystem;

public class Cast_c extends polyglot.ast.Cast_c {

  public Cast_c(Position pos, TypeNode castType, Expr expr) {
    super(pos, castType, expr);
  }

  @Override
  public Type childExpectedType(Expr child, AscriptionVisitor av) {
    if (child == expr) {
      FabILTypeSystem ts = (FabILTypeSystem) av.typeSystem();
      Type toType = castType.type();
      if (ts.isJavaInlineable(toType)) return ts.Object();
      if (ts.isFabricReference(toType)) return ts.FObject();
    }

    return super.childExpectedType(child, av);
  }

}
