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

import polyglot.ast.Block;
import polyglot.ast.ConstructorDecl;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.qq.QQ;
import polyglot.types.Flags;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import fabil.ExtensionInfo;
import fabil.ast.FabILNodeFactory;
import fabil.types.FabILFlags;

/**
 * Removes atomic keyword from method flags and makes body an atomic block.
 */
public class AtomicMethodRewriter extends NodeVisitor {

  protected QQ qq;
  protected FabILNodeFactory nf;

  public AtomicMethodRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
    this.nf = extInfo.nodeFactory();
  }

  @Override
  public Node override(Node parent, Node n) {
    if (n instanceof MethodDecl) {
      MethodDecl md = (MethodDecl) n;
      Flags f = md.flags();

      if (f.contains(FabILFlags.ATOMIC)) {
        f = f.clear(FabILFlags.ATOMIC);
        md = md.flags(f);
        md.methodInstance().setFlags(f);
        Block b =
            nf.Atomic(Position.compilerGenerated(), md.body().statements());
        md = (MethodDecl) md.body(b);

        return visitEdgeNoOverride(parent, md);
      }
    }

    if (n instanceof ConstructorDecl) {
      ConstructorDecl cd = (ConstructorDecl) n;
      Flags f = cd.flags();

      if (f.contains(FabILFlags.ATOMIC)) {
        f = f.clear(FabILFlags.ATOMIC);
        cd = cd.flags(f);
        cd.constructorInstance().setFlags(f);
        Block b =
            nf.Atomic(Position.compilerGenerated(), cd.body().statements());
        cd = (ConstructorDecl) cd.body(b);

        return visitEdgeNoOverride(parent, cd);
      }
    }

    return null;
  }

}
