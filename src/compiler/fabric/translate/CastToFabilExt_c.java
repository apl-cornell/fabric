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
package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import jif.extension.JifCastDel;
import jif.translate.CastToJavaExt_c;
import jif.translate.ClassDeclToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import jif.types.JifPolyType;
import jif.types.JifSubst;
import jif.types.ParamInstance;
import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import fabil.ast.FabILNodeFactory;
import fabric.types.FabricClassType;
import fabric.types.FabricSubstType;
import fabric.types.FabricTypeSystem;
import fabric.visit.FabricToFabilRewriter;

public class CastToFabilExt_c extends CastToJavaExt_c {

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Cast c = (Cast) node();
    Type exprType = c.expr().type();
    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) rw;
    FabricTypeSystem ts = (FabricTypeSystem) ffrw.jif_ts();
    FabILNodeFactory nf = (FabILNodeFactory) ffrw.java_nf();
    if (ts.isPrincipal(castType)
        && (ts.typeEquals(ts.Worker(), exprType) || ts.typeEquals(
            ts.RemoteWorker(), exprType))
        || ts.typeEquals(ts.Store(), exprType)) {
      return nf.Call(c.position(), c.expr(),
          nf.Id(Position.compilerGenerated(), "getPrincipal"));
    }

    if (castType.isPrimitive() || !ts.isFabricClass(castType)) {
      return rw.java_nf().Cast(c.position(), c.castType(), c.expr());
    }

    List<Expr> args = new ArrayList<Expr>();
    if (((JifCastDel) c.del()).isToSubstJifClass()) {
      // add all the actual param expressions to args
      FabricSubstType t = (FabricSubstType) castType;
      JifSubst subst = (JifSubst) t.subst();
      JifPolyType base = (JifPolyType) t.base();
      for (ParamInstance pi : base.params()) {
        args.add(ffrw.paramToJava(subst.get(pi)));
      }
    }
    // add the actual expression being cast.
    args.add(c.expr());

    FabricClassType fct = (FabricClassType) castType;
    String jifImplClass = fct.name();
    if (fct.flags().isInterface()) {
      // use the full name for the interface, since the IMPL class
      // will not be in the import table.
      jifImplClass =
          ClassDeclToJavaExt_c.interfaceClassImplName(fct.fullName());
    }
    return ffrw.qq().parseExpr(jifImplClass + ".%s(%LE)",
        ClassDeclToJavaExt_c.castMethodName(fct), args);

  }
}
