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
package fabil.types;

import java.util.List;

import polyglot.types.ParsedClassType;
import codebases.types.CodebaseClassType;

public interface FabILParsedClassType extends CodebaseClassType,
    ParsedClassType {
  public byte[] getClassHash();

  @Override
  public List<FabILParsedClassType> interfaces();
}
