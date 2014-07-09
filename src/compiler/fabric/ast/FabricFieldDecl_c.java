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
package fabric.ast;

import jif.ast.LabelNode;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.FieldDecl_c;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

// XXX Should be replaced with extension
@Deprecated
public class FabricFieldDecl_c extends FieldDecl_c implements FabricFieldDecl {

  protected LabelNode accessPolicy;

  @Deprecated
  public FabricFieldDecl_c(Position pos, Flags flags, TypeNode type,
      LabelNode accessPolicy, Id name, Expr init) {
    this(pos, flags, type, accessPolicy, name, init, null);
  }

  public FabricFieldDecl_c(Position pos, Flags flags, TypeNode type,
      LabelNode accessPolicy, Id name, Expr init, Ext ext) {
    super(pos, flags, type, name, init, ext);
    this.accessPolicy = accessPolicy;
  }

  @Override
  public LabelNode accessPolicy() {
    return this.accessPolicy;
  }

  @Override
  public FabricFieldDecl accessPolicy(LabelNode accessPolicy) {
    return accessPolicy(this, accessPolicy);
  }

  protected <N extends FabricFieldDecl_c> N accessPolicy(N n,
      LabelNode accessPolicy) {
    if (n.accessPolicy == accessPolicy) return n;
    n = copyIfNeeded(n);
    n.accessPolicy = accessPolicy;
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    TypeNode type = visitChild(this.type, v);
    LabelNode accessPolicy = visitChild(this.accessPolicy, v);
    Id name = visitChild(this.name, v);
    Expr init = visitChild(this.init, v);
    return reconstruct(this, type, accessPolicy, name, init);
  }

  protected <N extends FabricFieldDecl_c> N reconstruct(N n, TypeNode type,
      LabelNode accessPolicy, Id name, Expr init) {
    n = super.reconstruct(n, type, name, init);
    n = accessPolicy(n, accessPolicy);
    return n;
  }

}
