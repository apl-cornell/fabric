/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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

import java.util.List;

import polyglot.ast.ArrayInit_c;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.CollectionUtil;
import polyglot.util.ListUtil;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import fabric.types.FabricTypeSystem;

public class FabricArrayInit_c extends ArrayInit_c implements FabricArrayInit {
  // XXX: this code copied from fabil.FabricArrayInit_c

  protected Expr location;
  protected Expr label;

  public FabricArrayInit_c(Position pos, List<Expr> elements, Expr label,
      Expr location) {
    super(pos, elements);

    this.location = location;
    this.label = label;
  }

  @Override
  public FabricArrayInit elements(List<Expr> elements) {
    return (FabricArrayInit) super.elements(elements);
  }

  public Expr location() {
    return location;
  }

  @Override
  public FabricArrayInit_c location(Expr location) {
    FabricArrayInit_c n = (FabricArrayInit_c) copy();
    n.location = location;
    return n;
  }

  public Expr label() {
    return label;
  }

  @Override
  public FabricArrayInit_c label(Expr label) {
    FabricArrayInit_c n = (FabricArrayInit_c) copy();
    n.label = label;
    return n;
  }

  /**
   * Reconstructs the initializer.
   */
  protected FabricArrayInit_c reconstruct(List<Expr> elements, Expr location,
      Expr label) {
    if (!CollectionUtil.equals(elements, this.elements)
        || location != this.location || label != this.label) {
      FabricArrayInit_c n = (FabricArrayInit_c) copy();
      n.elements = ListUtil.copy(elements, true);
      n.location = location;
      n.label = label;
      return n;
    }

    return this;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    List<Expr> elements = visitList(this.elements, v);
    Expr location = (Expr) visitChild(this.location, v);
    Expr label = (Expr) visitChild(this.label, v);
    return reconstruct(elements, location, label);
  }

  @Override
  protected Type arrayOf(TypeSystem ts, Type baseType) {
    return ((FabricTypeSystem) ts).fabricArrayOf(position(), baseType);
  }

  @Override
  public Node copy(NodeFactory nf) {
    FabricNodeFactory fabNF = (FabricNodeFactory) nf;
    return fabNF.FabricArrayInit(this.position, this.label, this.location,
        this.elements);
  }

}
