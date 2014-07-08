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
package fabil.extension;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.ConstructorDecl;
import polyglot.ast.Formal;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.types.Flags;
import polyglot.types.Type;
import polyglot.util.Position;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class ConstructorDeclExt_c extends ClassMemberExt_c {

  @Override
  public List<ClassMember> implMember(ProxyRewriter pr, ClassDecl parent) {
    // TODO add Store parameters?
    ConstructorDecl node = (ConstructorDecl) node();
    node = node.name("_Impl");
    return Collections.singletonList((ClassMember) node);
  }

  @Override
  public Node rewriteProxies(ProxyRewriter pr) {
    // Need to add a $location argument to the constructor declaration if the
    // containing class is a Fabric class.
    ConstructorDecl decl = (ConstructorDecl) node();
    FabILTypeSystem ts = pr.typeSystem();

    // Ensure that we're translating a Fabric type.
    Type containerType = decl.constructorInstance().container();
    if (!ts.isFabricClass(containerType)) return decl;

    NodeFactory nf = pr.nodeFactory();
    Position pos = Position.compilerGenerated();
    List<Formal> formals = new LinkedList<Formal>(decl.formals());
    formals.add(
        0,
        nf.Formal(pos, Flags.NONE,
            nf.TypeNodeFromQualifiedName(pos, "fabric.worker.Store"),
            nf.Id(pos, "$location")));
    return decl.formals(formals);
  }

}
