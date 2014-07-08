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
package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.ast.RetryStmt;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt_c;

public class RetryToFabilExt_c extends ToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    RetryStmt retry = (RetryStmt)node();
    return ((FabILNodeFactory) rw.java_nf()).RetryStmt(retry.position());
  }
}
