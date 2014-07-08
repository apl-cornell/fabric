/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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
package fabric.lang;

public class FabricClassLoader extends java.lang.ClassLoader {

  @Override
  native public java.lang.Class findClass(java.lang.String name)
      throws java.lang.ClassNotFoundException;

  @Override
  native public java.lang.Class loadClass(java.lang.String name, boolean resolve)
      throws java.lang.ClassNotFoundException;

  native java.lang.Class getJavaClass(java.lang.String cls);

  public FabricClassLoader(java.lang.ClassLoader parent) {
    super(parent);
  }

  native public static fabric.lang.FabricClassLoader getClassLoader(
      fabric.lang.Codebase codebase);

  @Override
  native public java.io.InputStream getResourceAsStream(java.lang.String name);

}
