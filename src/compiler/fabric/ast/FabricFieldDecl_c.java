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
package fabric.ast;

import jif.ast.LabelNode;
import polyglot.ast.Expr;
import polyglot.ast.FieldDecl_c;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

public class FabricFieldDecl_c extends FieldDecl_c implements FabricFieldDecl {

  protected LabelNode accessLabel;

  public FabricFieldDecl_c(Position pos, Flags flags, TypeNode type,
      LabelNode accessLabel, Id name, Expr init) {
    super(pos, flags, type, name, init);
    this.accessLabel = accessLabel;
  }

  @Override
  public LabelNode accessLabel() {
    return this.accessLabel;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    TypeNode type = (TypeNode) visitChild(this.type, v);
    LabelNode accessLabel = (LabelNode) visitChild(this.accessLabel, v);
    Id name = (Id) visitChild(this.name, v);
    Expr init = (Expr) visitChild(this.init, v);
    return reconstruct(type, accessLabel, name, init);
  }

  protected FabricFieldDecl_c reconstruct(TypeNode type, LabelNode accessLabel,
      Id name, Expr init) {
    if (this.type != type || this.accessLabel != accessLabel
        || this.name != name || this.init != init) {
      FabricFieldDecl_c n = (FabricFieldDecl_c) copy();
      n.type = type;
      n.accessLabel = accessLabel;
      n.name = name;
      n.init = init;
      return n;
    }

    return this;
  }

}
