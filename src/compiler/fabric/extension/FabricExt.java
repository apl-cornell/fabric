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

import jif.ast.JifExt;
import polyglot.ast.Ext;

/**
 * This interface tags all fabric extension objects. All FabricExt objects
 * should be the second extension of an ast node, i.e. if <code>n</code> is a
 * node, then <code>n.ext.ext</code> refers to the FabricExt object (
 * <code>n.ext</code> should be a {@link JifExt}).
 * 
 * @author mdgeorge
 */
public interface FabricExt extends Ext {

}
