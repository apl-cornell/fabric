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
package fabil.ast;

import java.util.List;

import polyglot.ast.Block;
import polyglot.types.LocalInstance;

/**
 * An <code>Atomic</code> represents an <code>atomic</code> block.
 */
public interface Atomic extends Block {
  List<LocalInstance> updatedLocals();
  Atomic updatedLocals(List<LocalInstance> s);
  boolean mayAbort();
  Atomic mayAbort(boolean b);
}
