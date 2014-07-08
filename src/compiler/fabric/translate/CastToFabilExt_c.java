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
package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Cast;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import jif.translate.CastToJavaExt_c;
import jif.translate.JifToJavaRewriter;

public class CastToFabilExt_c extends CastToJavaExt_c {
  protected Type exprType;
  
  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
    Cast c = (Cast)this.node();
    exprType = c.expr().type();
    return super.toJavaEnter(rw);
  }
  
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Cast c = (Cast)node();
    
    FabricTypeSystem ts = (FabricTypeSystem)rw.jif_ts();
    FabILNodeFactory nf = (FabILNodeFactory)rw.java_nf();
    
    if (ts.isPrincipal(castType) 
     && (ts.typeEquals(ts.Worker(), exprType) 
      || ts.typeEquals(ts.RemoteWorker(), exprType))
      || ts.typeEquals(ts.Store(), exprType)) {
      return nf.Call(c.position(), c.expr(), nf.Id(Position.compilerGenerated(), "getPrincipal"));
    }
    
    return super.toJava(rw);
  }
}
