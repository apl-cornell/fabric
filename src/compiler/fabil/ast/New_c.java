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
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.util.TypedList;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

public class New_c extends polyglot.ast.New_c implements New, Annotated {

  protected Expr label;
  protected Expr location;

  public New_c(Position pos, Expr qualifier, TypeNode tn, List<Expr> arguments,
      ClassBody body, Expr label, Expr location) {
    super(pos, qualifier, tn, arguments, body);

    this.location = location;
    this.label = label;
  }

  @Override
  public New objectType(TypeNode tn) {
    return (New) super.objectType(tn);
  }

  public Expr label() {
    return label;
  }

  public New_c label(Expr label) {
    New_c n = (New_c) copy();
    n.label = label;
    return n;
  }

  public Expr location() {
    return location;
  }

  public New_c location(Expr location) {
    New_c n = (New_c) copy();
    n.location = location;
    return n;
  }

  /**
   * Reconstructs the expression.
   */
  protected New_c reconstruct(Expr qualifier, TypeNode tn,
      List<Expr> arguments, ClassBody body, Expr location, Expr label) {
    if (qualifier != this.qualifier || tn != this.tn
        || !CollectionUtil.equals(arguments, this.arguments)
        || body != this.body || location != this.location
        || label != this.label) {
      New_c n = (New_c) copy();
      n.tn = tn;
      n.qualifier = qualifier;
      n.arguments = TypedList.copyAndCheck(arguments, Expr.class, true);
      n.body = body;
      n.location = location;
      n.label = label;
      return n;
    }

    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public New_c visitChildren(NodeVisitor v) {
    Expr qualifier = (Expr) visitChild(this.qualifier, v);
    TypeNode tn = (TypeNode) visitChild(this.tn, v);
    List<Expr> arguments = visitList(this.arguments, v);
    ClassBody body = (ClassBody) visitChild(this.body, v);
    Expr location = (Expr) visitChild(this.location, v);
    Expr label = (Expr) visitChild(this.label, v);
    return reconstruct(qualifier, tn, arguments, body, location, label);
  }

  @Override
  public New_c typeCheck(TypeChecker tc) throws SemanticException {
    FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
    New_c result = (New_c) super.typeCheck(tc);

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
    if (qualifier != null) {
      v.visitCFG(qualifier, tn, ENTRY);
    }

    Term last = tn;
    if (label != null) {
      v.visitCFG(last, label, ENTRY);
      last = label;
    }

    if (location != null) {
      v.visitCFG(last, location, ENTRY);
      last = location;
    }

    if (body() != null) {
      v.visitCFG(last, listChild(arguments, body()), ENTRY);
      v.visitCFGList(arguments, body(), ENTRY);
      v.visitCFG(body(), this, EXIT);
    } else {
      if (!arguments.isEmpty()) {
        v.visitCFG(last, listChild(arguments, null), ENTRY);
        v.visitCFGList(arguments, this, EXIT);
      } else {
        v.visitCFG(last, this, EXIT);
      }
    }

    return succs;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    // TODO Auto-generated method stub
    return super.clone();
  }

  /*
   * (non-Javadoc)
   * @see polyglot.ast.New_c#disambiguateOverride(polyglot.ast.Node,
   * polyglot.visit.AmbiguityRemover)
   */
  @Override
  public Node disambiguateOverride(Node parent, AmbiguityRemover ar)
      throws SemanticException {
    New_c nn = (New_c) super.disambiguateOverride(parent, ar);

    // Disambiguate location and label.
    if (nn.label != null) {
      nn = nn.label((Expr) nn.visitChild(nn.label, ar));
    }

    if (nn.location != null) {
      nn = nn.location((Expr) nn.visitChild(nn.location, ar));
    }

    // If we have an anonymous class implementing an interface, make its
    // supertype fabric.lang.Object.
    if (nn.body() != null && nn.objectType().isDisambiguated()
        && nn.objectType().type().toClass().flags().isInterface()) {
      ParsedClassType anonType = nn.anonType();
      anonType.superType(((FabILTypeSystem) ar.typeSystem()).FObject());
    }

    return nn;
  }

  @Override
  public Node typeCheckOverride(Node parent, TypeChecker tc)
      throws SemanticException {
    New_c n = (New_c) super.typeCheckOverride(parent, tc);
    if (n == null) return null;
    
    if (n.label != null) {
      n = n.label((Expr) n.visitChild(n.label, tc));
    }
    
    if (n.location != null) {
      n = n.location((Expr) n.visitChild(n.location, tc));
    }
    
    return n;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node copy(NodeFactory nf) {
    FabILNodeFactory filNf = (FabILNodeFactory) nf;
    return filNf.New(this.position, this.qualifier, this.tn, this.label,
        this.location, this.arguments, this.body);
  }
}
