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

import polyglot.ast.ClassDecl;
import polyglot.ast.Node;
import polyglot.types.ParsedClassType;
import polyglot.visit.NodeVisitor;
import fabil.types.FabILParsedClassType_c;

/**
 * Ensures hashes in FabILParsedClassType_c instances are computed when
 * compiling FabIL and Fabric signatures. This way, the hashes become part of
 * the Polyglot serialized type information.
 */
public class SignatureHashGenerator extends NodeVisitor {

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof ClassDecl) {
      ClassDecl decl = (ClassDecl) n;
      ParsedClassType ct = decl.type();
      if (ct instanceof FabILParsedClassType_c) {
        ((FabILParsedClassType_c) ct).getClassHash();
      }
    }

    return super.leave(old, n, v);
  }

}
