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
package fabil.ast;

import java.util.List;

import polyglot.ast.*;
import polyglot.types.ArrayType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.util.TypedList;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

public class NewFabricArray_c extends NewArray_c implements NewFabricArray,
    Annotated {

  protected Expr label;
  protected Expr location;

  public NewFabricArray_c(Position pos, TypeNode baseType, List<Expr> dims,
      int addDims, FabricArrayInit init, Expr label, Expr location) {
    super(pos, baseType, dims, addDims, init);

    this.location = location;
    this.label = label;
  }

  @Override
  public FabricArrayInit init() {
    return (FabricArrayInit) super.init();
  }

  @Override
  public NewFabricArray_c init(polyglot.ast.ArrayInit init) {
    return (NewFabricArray_c) super.init(init);
  }

  public Expr label() {
    return label;
  }

  public NewFabricArray_c label(Expr label) {
    NewFabricArray_c n = (NewFabricArray_c) copy();
    n.label = label;
    return n;
  }

  public Expr location() {
    return location;
  }

  public NewFabricArray_c location(Expr location) {
    NewFabricArray_c n = (NewFabricArray_c) copy();
    n.location = location;
    return n;
  }

  /**
   * Reconstructs the expression.
   */
  protected NewFabricArray_c reconstruct(TypeNode baseType, List<Expr> dims,
      FabricArrayInit init, Expr location, Expr label) {
    if (baseType != this.baseType || !CollectionUtil.equals(dims, this.dims)
        || init != this.init || location != this.location
        || label != this.label) {
      NewFabricArray_c n = (NewFabricArray_c) copy();
      n.baseType = baseType;
      n.dims = TypedList.copyAndCheck(dims, Expr.class, true);
      n.init = init;
      n.location = location;
      n.label = label;
      return n;
    }

    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node visitChildren(NodeVisitor v) {
    TypeNode baseType = (TypeNode) visitChild(this.baseType, v);
    List<Expr> dims = visitList(this.dims, v);
    FabricArrayInit init = (FabricArrayInit) visitChild(this.init, v);
    Expr location = (Expr) visitChild(this.location, v);
    Expr label = (Expr) visitChild(this.label, v);
    return reconstruct(baseType, dims, init, location, label);
  }

  @Override
  protected ArrayType arrayOf(TypeSystem ts, Type baseType, int dims) {
    return ((FabILTypeSystem) ts).fabricArrayOf(baseType, dims);
  }

  @Override
  public NewFabricArray_c typeCheck(TypeChecker tc) throws SemanticException {
    FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
    NewFabricArray_c result = (NewFabricArray_c) super.typeCheck(tc);

    if (!ts.isFabricType(result.baseType)) {
      throw new SemanticException(
          "Non-Fabric objects cannot be stored in Fabric arrays.", node()
              .position());
    }

    if (location != null) {
      if (!ts.isImplicitCastValid(location.type(), ts.Store())) {
        throw new SemanticException("Array location must be a store.", location
            .position());
      }
    }

    if (label != null) {
      if (!ts.isImplicitCastValid(label.type(), ts.Label())) {
        throw new SemanticException("Invalid array label.", label.position());
      }
    }

    return result;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    if (init != null) {
      v.visitCFG(baseType, listChild(dims, init), ENTRY);
      v.visitCFGList(dims, init, ENTRY);

      Term last = init;
      if (label != null) {
        v.visitCFG(last, label, ENTRY);
        last = label;
      }

      if (location != null) {
        v.visitCFG(last, location, ENTRY);
        last = location;
      }

      v.visitCFG(last, this, EXIT);
    } else {
      v.visitCFG(baseType, listChild(dims, null), ENTRY);
      Term last = null;

      if (label != null) {
        v.visitCFGList(dims, label, ENTRY);
        last = label;
      }

      if (location != null) {
        if (last == null) {
          v.visitCFGList(dims, location, ENTRY);
        } else {
          v.visitCFG(last, location, ENTRY);
        }
        last = location;
      }

      if (last == null)
        v.visitCFGList(dims, this, EXIT);
      else v.visitCFG(last, this, EXIT);
    }

    return succs;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node copy(NodeFactory nf) {
    FabILNodeFactory filNf = (FabILNodeFactory) nf;
    return filNf.NewFabricArray(this.position, this.baseType, this.label,
        this.location, this.dims, this.addDims, (FabricArrayInit) this.init);
  }

}
