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

import jif.translate.JifToJavaRewriter;
import jif.translate.ToJavaExt_c;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabil.ast.FabILNodeFactory;
import fabric.ast.RetryStmt;

public class RetryToFabilExt_c extends ToJavaExt_c {
  /**
   * @throws SemanticException
   */
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    RetryStmt retry = (RetryStmt) node();
    return ((FabILNodeFactory) rw.java_nf()).RetryStmt(retry.position());
  }
}
