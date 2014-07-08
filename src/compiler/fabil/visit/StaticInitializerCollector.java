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
package fabil.visit;

import polyglot.ast.Node;
import polyglot.visit.NodeVisitor;
import fabil.ast.FabILNodeFactory;
import fabil.extension.FabILExt;
import fabil.types.FabILTypeSystem;

/**
 * A pass that collects initializers for static fields and moves them into an
 * atomic static initializer.
 */
public class StaticInitializerCollector extends NodeVisitor {
  
  protected FabILNodeFactory nf;
  protected FabILTypeSystem ts;

  public StaticInitializerCollector(FabILNodeFactory nf, FabILTypeSystem ts) {
    this.nf = nf;
    this.ts = ts;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    return ext(n).collectStaticInitializers(this);
  }

  private FabILExt ext(Node n) {
    return (FabILExt) n.ext();
  }

  public FabILNodeFactory nodeFactory() {
    return nf;
  }

  public FabILTypeSystem typeSystem() {
    return ts;
  }
}
