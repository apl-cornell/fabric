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
package fabil.types;

import polyglot.types.ArrayType_c;
import polyglot.types.FieldInstance;
import polyglot.types.Type;
import polyglot.types.TypeObject;
import polyglot.types.TypeSystem;
import polyglot.util.Position;

public class FabricArrayType_c extends ArrayType_c implements FabricArrayType {

  /** Used for deserializing types. */
  protected FabricArrayType_c() {
  }

  public FabricArrayType_c(TypeSystem ts, Position pos, Type base) {
    super(ts, pos, base);
  }

  @Override
  protected void init() {
    boolean fixField = fields == null;

    super.init();

    if (fixField) {
      // Make the length field non-final.
      FieldInstance lengthField = lengthField();
      lengthField = lengthField.flags(lengthField.flags().clearFinal());
      fields.set(0, lengthField);
    }
  }

  @Override
  public boolean descendsFromImpl(Type ancestor) {
    // Fabric arrays are subtypes of fabric.lang.Object.
    FabILTypeSystem ts = (FabILTypeSystem) this.ts;
    if (ancestor.isCanonical() && !ancestor.isNull()
        && !ts.typeEquals(this, ancestor) && ancestor.isReference()
        && ts.typeEquals(ancestor, ts.FObject()) && ts.isFabricArray(this))
      return true;
    return super.descendsFromImpl(ancestor);
  }

  @Override
  public boolean equalsImpl(TypeObject t) {
    return (t instanceof FabricArrayType) && super.equalsImpl(t);
  }

  @Override
  public boolean typeEqualsImpl(Type t) {
    return (t instanceof FabricArrayType) && super.typeEqualsImpl(t);
  }

  @Override
  public boolean isImplicitCastValidImpl(Type toType) {
    if (toType instanceof JavaArrayType) return false;

    return super.isImplicitCastValidImpl(toType);
  }

  @Override
  public boolean isCastValidImpl(Type toType) {
    if (toType instanceof JavaArrayType) return false;

    return super.isCastValidImpl(toType);
  }

}
