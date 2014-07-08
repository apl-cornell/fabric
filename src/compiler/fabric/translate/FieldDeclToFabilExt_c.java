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
package fabric.translate;

import jif.translate.FieldDeclToJavaExt_c;
import jif.translate.JifToJavaRewriter;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;
import fabric.ast.FabricFieldDecl;

public class FieldDeclToFabilExt_c extends FieldDeclToJavaExt_c {
  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
    rw = (JifToJavaRewriter) super.toJavaEnter(rw);

    // XXX: the serialization pass doesn't use the Fabric NodeFactory *sigh*
    // skip fields used for polyglot metadata
    if (node() instanceof FabricFieldDecl) {
      FabricFieldDecl f = (FabricFieldDecl) node();
      return rw.bypass(f.accessLabel());
    } else return rw;
  }

}
