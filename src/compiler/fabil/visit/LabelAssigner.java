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
package fabil.visit;

import polyglot.ast.Node;
import polyglot.frontend.Job;
import polyglot.qq.QQ;
import polyglot.types.SemanticException;
import polyglot.visit.ContextVisitor;
import fabil.ExtensionInfo;
import fabil.ast.FabILNodeFactory;
import fabil.extension.FabILExt;
import fabil.types.FabILTypeSystem;

/**
 * Assigns object locations to all <code>new</code> expressions.
 */
public class LabelAssigner extends ContextVisitor {

  private QQ qq;

  public LabelAssigner(Job job, ExtensionInfo extInfo) {
    super(job, extInfo.typeSystem(), extInfo.nodeFactory());
    this.qq = new QQ(extInfo);
  }

  public QQ qq() {
    return qq;
  }

  @Override
  public FabILNodeFactory nodeFactory() {
    return (FabILNodeFactory) super.nodeFactory();
  }

  @Override
  public FabILTypeSystem typeSystem() {
    return (FabILTypeSystem) super.typeSystem();
  }

  @Override
  protected Node leaveCall(Node n) throws SemanticException {
    return ext(n).assignLabels(this);
  }

  private FabILExt ext(Node n) {
    return (FabILExt) n.ext();
  }

}
