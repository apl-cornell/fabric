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
import polyglot.ast.If;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.translate.JifToJavaRewriter;
import jif.translate.MethodDeclToJavaExt_c;

public class MethodDeclToFabilExt_c extends MethodDeclToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    MethodDecl md = (MethodDecl)super.toJava(rw);
    
    if (md.body() == null) {
      // abstract method
      return md;
    }
    
    FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();
//    FabILTypeSystem ts = (FabILTypeSystem)rw.java_ts();
    
    if (md.name().endsWith("_remote")) {
      // Fabric wrapper
      // Rewrite the else block to throw an exception
//      Try tryStmt = (Try)md.body().statements().get(0);
//      Eval npecall = (Eval) tryStmt.tryBlock().statements().get(0);
//      If ifStmt = (If)tryStmt.tryBlock().statements().get(1);
      If ifStmt = (If)md.body().statements().get(0);
      ifStmt = ifStmt.alternative(rw.qq().parseStmt("throw new fabric.worker.remote.RemoteCallLabelCheckFailedException();"));
//      ifStmt = ifStmt.alternative(nf.Throw(Position.compilerGenerated(), nf.New(Position.compilerGenerated(), nf.CanonicalTypeNode(Position.compilerGenerated(), ts.InternalError()), Collections.EMPTY_LIST)));
//      tryStmt = tryStmt.tryBlock(nf.Block(Position.compilerGenerated(), npecall, ifStmt));
      return md.body(nf.Block(Position.compilerGenerated(), ifStmt));
    }
    
    return md;
//    List<Stmt> stmts = new ArrayList<Stmt>(md.body().statements().size() + 1);
    
//    TypeNode worker = nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Worker());
//    stmts.add(nf.LocalDecl(Position.compilerGenerated(), 
//                           Flags.FINAL, 
//                           worker, 
//                           nf.Id(Position.compilerGenerated(), 
//                                 "worker$"),
//                           nf.Call(Position.compilerGenerated(), 
//                                   worker, 
//                                   nf.Id(Position.compilerGenerated(), 
//                                         "getWorker"))));
//    stmts.addAll(md.body().statements());
    
//    return md.body(nf.Block(md.body().position(), stmts));
  }
}
