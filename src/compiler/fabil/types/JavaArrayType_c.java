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

import polyglot.types.ArrayType_c;
import polyglot.types.Type;
import polyglot.types.TypeObject;
import polyglot.util.CodeWriter;
import polyglot.util.Position;

public class JavaArrayType_c extends ArrayType_c implements JavaArrayType {

  /** Used for deserializing types. */
  protected JavaArrayType_c() {
  }

  public JavaArrayType_c(FabILTypeSystem ts, Position pos, Type type) {
    super(ts, pos, type);
  }

  @Override
  public boolean equalsImpl(TypeObject t) {
    return (t instanceof JavaArrayType) && super.equalsImpl(t);
  }

  @Override
  public boolean typeEqualsImpl(Type t) {
    return (t instanceof JavaArrayType) && super.typeEqualsImpl(t);
  }

  @Override
  public boolean isImplicitCastValidImpl(Type toType) {
    if (toType instanceof FabricArrayType) return false;

    return super.isImplicitCastValidImpl(toType);
  }

  @Override
  public boolean isCastValidImpl(Type toType) {
    if (toType instanceof FabricArrayType) return false;

    return super.isCastValidImpl(toType);
  }

  @Override
  public String toString() {
      return base().toString() + " native[]";
  }

  @Override
  public void print(CodeWriter w) {
      base().print(w);
      w.write("native[]");
  }

}
