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
package fabil.visit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import polyglot.ast.Block;
import polyglot.ast.For;
import polyglot.ast.ForInit;
import polyglot.ast.Formal;
import polyglot.ast.Local;
import polyglot.ast.LocalAssign;
import polyglot.ast.LocalDecl;
import polyglot.ast.Node;
import polyglot.ast.ProcedureDecl;
import polyglot.ast.Stmt;
import polyglot.types.LocalInstance;
import polyglot.visit.NodeVisitor;
import fabil.ast.Atomic;

public class UpdatedVariableFinder extends NodeVisitor {
  protected Set<LocalInstance> declared = new HashSet<LocalInstance>();
  protected Set<LocalInstance> updated = new HashSet<LocalInstance>();

  @Override
  public NodeVisitor enter(Node n) {
    if (n instanceof ProcedureDecl) {
      UpdatedVariableFinder v = (UpdatedVariableFinder) this.copy();
      v.declared = new HashSet<LocalInstance>();
      v.updated = new HashSet<LocalInstance>();

      ProcedureDecl pd = (ProcedureDecl) n;
      for (Formal f : pd.formals()) {
        v.declared.add(f.localInstance());
      }

      return v;
    } else if (n instanceof Block) {
      UpdatedVariableFinder v = (UpdatedVariableFinder) this.copy();
      v.declared = new HashSet<LocalInstance>();
      v.declared.addAll(declared);

      Block b = (Block) n;
      for (Stmt s : b.statements()) {
        if (s instanceof LocalDecl) {
          LocalDecl ld = (LocalDecl) s;
          v.declared.add(ld.localInstance());
        }
      }

      return v;
    } else if (n instanceof For) {
      UpdatedVariableFinder v = (UpdatedVariableFinder) this.copy();
      v.declared = new HashSet<LocalInstance>();
      v.declared.addAll(declared);

      For f = (For) n;
      for (ForInit s : f.inits()) {
        if (s instanceof LocalDecl) {
          LocalDecl ld = (LocalDecl) s;
          v.declared.add(ld.localInstance());
        }
      }

      return v;
    }

    return this;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof LocalAssign) {
      Local l = (Local) ((LocalAssign) n).left();
      updated.add(l.localInstance());
    } else if (n instanceof Atomic) {
      Atomic a = (Atomic) n;
      List<LocalInstance> updatedLocals = new ArrayList<LocalInstance>();
      for (LocalInstance li : updated) {
        if (declared.contains(li)) {
          updatedLocals.add(li);
        }
      }
      return a.updatedLocals(updatedLocals);
    }

    return n;
  }
}
