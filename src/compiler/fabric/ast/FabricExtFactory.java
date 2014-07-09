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

import jif.ast.JifExtFactory;
import polyglot.ast.Ext;

/**
 * Factory for extensions for all of the AST nodes in the Fabric language. Note
 * that the created extensions need not be <code>FabricExt</code>s.
 */
public interface FabricExtFactory extends JifExtFactory {
  Ext extAbortStmt();

  Ext extAtomic();

  Ext extAmbNewFabricArray();

  Ext extWorker();

  Ext extFabricArrayInit();

  Ext extFabricArrayTypeNode();

  Ext extNewFabricArray();

  Ext extRemoteWorkerGetter();

  Ext extRetryStmt();

  Ext extCodebaseNode();

  Ext extCodebaseDecl();

  Ext extStore();

  Ext extAccessPolicy();
}
