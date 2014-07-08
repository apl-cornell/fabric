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
package fabric.visit;

import java.util.ArrayList;
import java.util.List;

import jif.translate.JifToJavaRewriter;
import jif.types.Param;
import jif.types.label.Label;
import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.TypeNode;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import fabil.FabILOptions;
import fabil.ast.FabILNodeFactory;
import fabil.types.FabILTypeSystem;
import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;

public class FabricToFabilRewriter extends JifToJavaRewriter {
  protected boolean principalExpected = false;

  public FabricToFabilRewriter(Job job, FabricTypeSystem fab_ts,
      FabricNodeFactory fab_nf, fabil.ExtensionInfo fabil_ext) {
    super(job, fab_ts, fab_nf, fabil_ext);
  }

  @Override
  public String runtimeLabelUtil() {
    return jif_ts().LabelUtilClassName();
  }

  @Override
  public TypeNode typeToJava(Type t, Position pos) throws SemanticException {
    FabILNodeFactory fabil_nf  = (FabILNodeFactory) java_nf();
    FabILTypeSystem  fabil_ts  = (FabILTypeSystem)  java_ts();
    FabricTypeSystem fabric_ts = (FabricTypeSystem) jif_ts();

    if (fabric_ts.typeEquals(t, fabric_ts.Worker())) {
      return canonical(fabil_nf, fabil_ts.Worker(), pos);
    }

    if (fabric_ts.typeEquals(t, fabric_ts.RemoteWorker())) {
      return canonical(fabil_nf, fabil_ts.RemoteWorker(), pos);
    }
    
    if (fabric_ts.isFabricArray(t)) {
      return fabil_nf.FabricArrayTypeNode(pos, typeToJava(t.toArray().base(), pos));
    }

    return super.typeToJava(t, pos);
  }

  public boolean inSignatureMode() {
    FabILOptions opts =
        (FabILOptions) ((fabil.ExtensionInfo) java_ext).getOptions();
    return opts.signatureMode();
  }
  
//private Expr loc;
//private Call addLoc(Call c) {
//  List<Expr> args = new ArrayList<Expr>(c.arguments().size() + 1);
//  args.add(loc);
//  for(Expr expr : (List<Expr>)c.arguments()) {
//    Expr addExpr = expr;
//    if(expr instanceof Call) {
//      Call subcall = (Call) expr;
//      addExpr = addLoc(subcall);
//    }
//    args.add(addExpr);
//  }
//  c = (Call)c.arguments(args);
//  return c;
//  
//}

  public Expr updateLabelLocation(Expr labelExpr, Expr locExpr) {
    if (labelExpr instanceof Call && locExpr != null) {
      Call c = (Call)labelExpr;
      
//      if(!c.target().toString().contains("LabelUtil") && !c.target().toString().contains("PrincipalUtil")) {
//        System.out.println("Big problem in the compiler. Calling " + c.target().toString() + "." + c.name());
//        return labelExpr;
//      }
      // XXX Hack
      // Several special cases, for which no change should be made.
      if (c.name().equals("getPrincipal") && c.arguments().size() == 0) {
        return c;
      }
      
      List<Expr> args = new ArrayList<Expr>(c.arguments().size() + 1);
      args.add(locExpr);
      for (Expr expr : (List<Expr>)c.arguments()) {
        if (expr instanceof Call) {
          args.add(updateLabelLocation(expr, locExpr));
        }
        else {
          args.add(expr);
        }
      }
      c = (Call)c.arguments(args);
      return c;
    }
    
    return labelExpr;
  }
  
  public Expr paramToJava(Param param, Expr locExpr) throws SemanticException {
    if (param instanceof Label) {
      Expr labelExpr = labelToJava((Label)param);
      return updateLabelLocation(labelExpr, locExpr);
    }
    
    return super.paramToJava(param);
  }
}
