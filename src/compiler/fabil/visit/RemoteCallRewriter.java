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
package fabil.visit;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.qq.QQ;
import polyglot.visit.NodeVisitor;
import fabil.ExtensionInfo;
import fabil.extension.FabILExt;
import fabil.types.FabILTypeSystem;

public class RemoteCallRewriter extends NodeVisitor {
  protected QQ qq;
  protected NodeFactory nf;
  protected FabILTypeSystem ts;

  public RemoteCallRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
    this.nf = extInfo.nodeFactory();
    this.ts = extInfo.typeSystem();
  }

  protected FabILExt ext(Node n) {
    return (FabILExt) n.ext();
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    return ext(n).rewriteRemoteCalls(this);
  }

  /**
   * @return the qq
   */
  public QQ qq() {
    return qq;
  }

  public NodeFactory nodeFactory() {
    return nf;
  }

  public FabILTypeSystem typeSystem() {
    return ts;
  }
}
