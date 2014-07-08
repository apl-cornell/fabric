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
package fabric.ast;

import polyglot.ast.JL;
import jif.ast.JifDelFactory;

/** Factory for delegates for all of the AST nodes in the Fabric language. */
public interface FabricDelFactory extends JifDelFactory {
  JL delAbortStmt();
  JL delAtomic();
  JL delAmbNewFabricArray();
  JL delWorker();
  JL delFabricArrayInit();
  JL delFabricArrayTypeNode();
  JL delNewFabricArray();
  JL delRemoteWorkerGetter();
  JL delRetryStmt();
}
