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
package fabric.translate;

import jif.translate.JifToJavaRewriter;
import jif.translate.NewLabelToJavaExt_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;
import fabric.ast.FabricUtil;
import fabric.extension.LocatedExt_c;
import fabric.extension.NewLabelExt_c;
import fabric.visit.FabricToFabilRewriter;

public class NewLabelToFabilExt_c extends NewLabelToJavaExt_c {

  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) super.toJavaEnter(rw);
    LocatedExt_c ext = (LocatedExt_c) FabricUtil.fabricExt(node());
    return ffrw.pushLocation(ext.location());
  }

  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    // toJava is called explicitly on label types, without actually visiting
    // so we need to use this ugly hack to keep track of the proper location
    // to assign to new label objects.
    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) rw;
    NewLabelExt_c ext = (NewLabelExt_c) FabricUtil.fabricExt(node());
    rw = ffrw.pushLocation(ext.location());
    Node n = super.toJava(rw);
    return n;
  }
}
