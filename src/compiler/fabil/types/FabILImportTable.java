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
package fabil.types;

import polyglot.types.*;
import polyglot.types.Package;

public class FabILImportTable extends ImportTable {

  public FabILImportTable(TypeSystem ts, Package pkg, String src) {
    super(ts, pkg, src);
  }

  public FabILImportTable(TypeSystem ts, Package pkg) {
    super(ts, pkg);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.types.ImportTable#findInPkg(java.lang.String,
   *      java.lang.String)
   */
  @Override
  protected Named findInPkg(String name, String pkgName)
      throws SemanticException {
    // HACK Ignore java.lang.Object so that fabric.lang.Object takes priority.
    if ("Object".equals(name) && "java.lang".equals(pkgName)) return null;
    
    return super.findInPkg(name, pkgName);
  }

}
