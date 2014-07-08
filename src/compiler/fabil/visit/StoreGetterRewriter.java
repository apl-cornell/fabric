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

import java.util.Stack;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.visit.NodeVisitor;
import fabil.ast.Annotated;
import fabil.ast.StoreGetter;

public class StoreGetterRewriter extends NodeVisitor {

  Stack<Expr> locationStack;

  public StoreGetterRewriter() {
    locationStack = new Stack<Expr>();
  }

  @Override
  public NodeVisitor enter(Node n) {
    if (n instanceof Annotated) {
      Annotated an = (Annotated) n;
      locationStack.push(an.location());
    }
    return this;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof Annotated) {
      locationStack.pop();
    }
    if (n instanceof StoreGetter) {
      return locationStack.peek();
    }
    return super.leave(old, n, v);
  }
}
