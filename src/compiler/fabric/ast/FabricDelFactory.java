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
package fabric.ast;

import jif.ast.JifDelFactory;
import polyglot.ast.JLDel;

/** Factory for delegates for all of the AST nodes in the Fabric language. */
public interface FabricDelFactory extends JifDelFactory {
  JLDel delAbortStmt();

  JLDel delAtomic();

  JLDel delAmbNewFabricArray();

  JLDel delWorker();

  JLDel delFabricArrayInit();

  JLDel delFabricArrayTypeNode();

  JLDel delNewFabricArray();

  JLDel delRemoteWorkerGetter();

  JLDel delRetryStmt();

  JLDel delCodebaseNode();

  JLDel delCodebaseDecl();

  JLDel delPrincipalExpr();

  /**
   * @return
   */
  JLDel delStore();

  /**
   * @return
   */
  JLDel delAccessPolicy();

}
