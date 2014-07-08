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

import java.util.ArrayList;
import java.util.List;

import fabil.ast.FabILNodeFactory;
import polyglot.ast.*;
import polyglot.types.SemanticException;
import jif.translate.ConstructorDeclToJavaExt_c;
import jif.translate.JifToJavaRewriter;

public class ConstructorDeclToFabilExt_c extends ConstructorDeclToJavaExt_c {
  @SuppressWarnings("unchecked")
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Node n = super.toJava(rw);
    if (n instanceof MethodDecl) {
      // The constructor declaration has been rewritten to a method declaration.
      MethodDecl md = (MethodDecl)n;
      if (md.body() != null) {
        FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();
//        FabILTypeSystem ts = (FabILTypeSystem)rw.java_ts();

        List<Stmt> stmts = new ArrayList<Stmt>(md.body().statements().size() + 1);
        
//        TypeNode worker = nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Worker());
//        stmts.add(nf.LocalDecl(Position.compilerGenerated(), 
//                               Flags.FINAL, 
//                               worker, 
//                               nf.Id(Position.compilerGenerated(), 
//                                     "worker$"),
//                               nf.Call(Position.compilerGenerated(), 
//                                       worker, 
//                                       nf.Id(Position.compilerGenerated(), 
//                                             "getWorker"))));
        stmts.addAll(md.body().statements());
        
        return md.body(nf.Block(md.body().position(), stmts));
      }
    }
    return n;
  }
}
