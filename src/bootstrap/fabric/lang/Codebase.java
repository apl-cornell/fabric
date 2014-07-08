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
package fabric.lang;

public interface Codebase extends fabric.lang.Object {

  public fabric.util.Map get$classes();

  public fabric.util.Map set$classes(fabric.util.Map val);

  public fabric.lang.FClass resolveClassName(java.lang.String name);

  public java.lang.Class<?> toJavaClass(java.lang.String name);

  public fabric.lang.FabricClassLoader getClassLoader();

  public fabric.util.Map getClasses();

  public fabric.lang.FClass getClass(java.lang.String s);

}
