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
package fabric.extension;

import jif.extension.JifExprExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.PathMap;
import jif.visit.LabelChecker;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import fabric.ast.Worker;
import fabric.types.FabricTypeSystem;

public class WorkerJifExt_c extends JifExprExt {
  public WorkerJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }

  /**
   * @throws SemanticException
   */
  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    // Basically the same as the label checking for literals.
    Worker c = (Worker) node();

    JifContext A = lc.jifContext();
    A = (JifContext) c.del().enterScope(A);

    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();

    PathMap X = ts.pathMap();
    X = X.N(A.pc());
    X = X.NV(A.pc());
    return updatePathMap(c, X);
  }
}
