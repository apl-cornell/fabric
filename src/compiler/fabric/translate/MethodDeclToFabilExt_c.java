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

import jif.translate.JifToJavaRewriter;
import jif.translate.MethodDeclToJavaExt_c;
import polyglot.ast.Block;
import polyglot.ast.If;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import fabil.ast.FabILNodeFactory;

public class MethodDeclToFabilExt_c extends MethodDeclToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    MethodDecl md = (MethodDecl) super.toJava(rw);

    if (md.body() == null) {
      // abstract method
      return md;
    }

    FabILNodeFactory nf = (FabILNodeFactory) rw.nodeFactory();

    if (md.name().endsWith("_remote")) {
      // Fabric wrapper
      // Rewrite the else block to throw an exception
      If ifStmt = (If) md.body().statements().get(0);
      ifStmt =
          ifStmt
              .alternative(rw
                  .qq()
                  .parseStmt(
                      "throw new fabric.worker.remote.RemoteCallLabelCheckFailedException();"));
      return md.body(nf.Block(Position.compilerGenerated(), ifStmt));
    }

    return md;
  }

  @Override
  protected Block guardWithConstraints(JifToJavaRewriter rw, Block b)
      throws SemanticException {
    boolean shouldGuard = false;
    if (shouldGuard) {
      b = super.guardWithConstraints(rw, b);
    }
    return ((FabILNodeFactory) rw.java_nf()).Atomic(b.position(),
        b.statements());
  }
}
