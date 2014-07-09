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
package fabric.visit;

import jif.visit.JifExceptionChecker;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

public class FabricExceptionChecker extends JifExceptionChecker {
  protected boolean inRemoteWrapper = false;

  public FabricExceptionChecker(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }

  @Override
  protected NodeVisitor enterCall(Node n) throws SemanticException {
    FabricExceptionChecker v = (FabricExceptionChecker) super.enterCall(n);
    if (n instanceof MethodDecl) {
      // XXX v should have been copied already.
      MethodDecl md = (MethodDecl) n;
      if (md.name().endsWith("_remote")) {
        v.inRemoteWrapper = true;
      }
    }
    return v;
  }

  @Override
  public void throwsException(Type t, Position pos) throws SemanticException {
    if (!inRemoteWrapper || !ts.typeEquals(t, ts.NullPointerException())) {
      super.throwsException(t, pos);
    }
  }
}
