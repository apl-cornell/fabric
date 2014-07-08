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
package fabric.extension;

import fabric.ast.RemoteWorkerGetter;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import jif.extension.JifExprExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.visit.LabelChecker;

public class RemoteWorkerGetterJifExt_c extends JifExprExt {
  public RemoteWorkerGetterJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }
  
  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    RemoteWorkerGetter rcg = (RemoteWorkerGetter)node();
    
    JifContext A = lc.jifContext();
    A = (JifContext)rcg.del().enterScope(A);
    
    Expr rcName = rcg.remoteWorkerName();
    rcName = (Expr)lc.context(A).labelCheck(rcName);
    
    return updatePathMap(rcg.remoteWorkerName(rcName), getPathMap(rcName));
  }
}
