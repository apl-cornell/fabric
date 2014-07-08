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
package fabil.ast;

import java.util.List;

import polyglot.ast.ArrayInit_c;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Term;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.CollectionUtil;
import polyglot.util.ListUtil;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

public class FabricArrayInit_c extends ArrayInit_c implements FabricArrayInit,
    Annotated {

  protected Expr location;
  protected Expr label;
  protected Expr accessPolicy;

  public FabricArrayInit_c(Position pos, List<Expr> elements, Expr label,
      Expr accessLabel, Expr location) {
    super(pos, elements);

    this.location = location;
    this.label = label;
    this.accessPolicy = accessLabel;
  }

  @Override
  public FabricArrayInit elements(List<Expr> elements) {
    return (FabricArrayInit) super.elements(elements);
  }

  @Override
  public Expr location() {
    return location;
  }

  @Override
  public FabricArrayInit_c location(Expr location) {
    FabricArrayInit_c n = (FabricArrayInit_c) copy();
    n.location = location;
    return n;
  }

  @Override
  public Expr updateLabel() {
    return label;
  }

  @Override
  public FabricArrayInit_c updateLabel(Expr label) {
    FabricArrayInit_c n = (FabricArrayInit_c) copy();
    n.label = label;
    return n;
  }

  @Override
  public Expr accessPolicy() {
    return accessPolicy;
  }

  @Override
  public FabricArrayInit_c accessPolicy(Expr accessLabel) {
    FabricArrayInit_c n = (FabricArrayInit_c) copy();
    n.accessPolicy = accessLabel;
    return n;
  }

  /**
   * Reconstructs the initializer.
   */
  protected FabricArrayInit_c reconstruct(List<Expr> elements, Expr location,
      Expr label, Expr accessLabel) {
    if (!CollectionUtil.equals(elements, this.elements)
        || location != this.location || label != this.label
        || accessLabel != this.accessPolicy) {
      FabricArrayInit_c n = (FabricArrayInit_c) copy();
      n.elements = ListUtil.copy(elements, true);
      n.location = location;
      n.label = label;
      n.accessPolicy = accessLabel;
      return n;
    }

    return this;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    List<Expr> elements = visitList(this.elements, v);
    Expr location = (Expr) visitChild(this.location, v);
    Expr label = (Expr) visitChild(this.label, v);
    Expr accessLabel = (Expr) visitChild(this.accessPolicy, v);
    return reconstruct(elements, location, label, accessLabel);
  }

  @Override
  public FabricArrayInit_c typeCheck(TypeChecker tc) throws SemanticException {
    FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
    FabricArrayInit_c result = (FabricArrayInit_c) super.typeCheck(tc);

    if (location != null) {
      if (!ts.isImplicitCastValid(location.type(), ts.Store())) {
        throw new SemanticException("Array location must be a store.",
            location.position());
      }
    }

    if (label != null) {
      if (!ts.isImplicitCastValid(label.type(), ts.Label())) {
        throw new SemanticException("Invalid array label.", label.position());
      }
    }

    if (accessPolicy != null) {
      if (!ts.isImplicitCastValid(accessPolicy.type(), ts.ConfPolicy())) {
        throw new SemanticException("Invalid access policy.",
            accessPolicy.position());
      }
    }

    return result;
  }

  @Override
  protected Type arrayOf(TypeSystem ts, Type baseType) {
    return ((FabILTypeSystem) ts).fabricArrayOf(baseType);
  }

  @Override
  public Type childExpectedType(Expr child, AscriptionVisitor av) {
    FabILTypeSystem ts = (FabILTypeSystem) av.typeSystem();

    if (child == location) return ts.Store();
    if (child == label) return ts.Label();
    if (child == accessPolicy) return ts.ConfPolicy();

    Type t = av.toType();
    Type baseType = t.toArray().base();
    if (ts.isJavaInlineable(baseType)) return ts.FObject();

    return super.childExpectedType(child, av);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    Term last = null;

    if (accessPolicy != null) {
      v.visitCFGList(elements, accessPolicy, ENTRY);
      last = accessPolicy;
    }

    if (label != null) {
      if (last == null) {
        v.visitCFGList(elements, label, ENTRY);
      } else {
        v.visitCFG(last, label, ENTRY);
      }
      last = label;
    }

    if (location != null) {
      if (last == null) {
        v.visitCFGList(elements, location, ENTRY);
      } else {
        v.visitCFG(last, location, ENTRY);
      }
      last = location;
    }

    if (last == null) {
      v.visitCFGList(elements, this, EXIT);
    } else {
      v.visitCFG(last, this, EXIT);
    }

    return succs;
  }

  @Override
  public Node copy(NodeFactory nf) {
    FabILNodeFactory filNf = (FabILNodeFactory) nf;
    return filNf.FabricArrayInit(this.position, this.label, this.accessPolicy,
        this.location, this.elements);
  }

}
