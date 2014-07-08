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

import java.util.List;

import polyglot.types.Declaration;
import polyglot.types.Flags;
import polyglot.types.MemberInstance;
import polyglot.types.MethodInstance;
import polyglot.types.MethodInstance_c;
import polyglot.types.ProcedureInstance;
import polyglot.types.ReferenceType;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;

public class FabILMethodInstance_c extends MethodInstance_c {

  public FabILMethodInstance_c(TypeSystem ts, Position pos,
      ReferenceType container, Flags flags, Type returnType, String name,
      List<? extends Type> formalTypes, List<? extends Type> excTypes) {
    super(ts, pos, container, flags, returnType, name, formalTypes, excTypes);
  }

  @Override
  public boolean moreSpecificImpl(ProcedureInstance p) {
    // Augment default behaviour to support class-file wonkiness. Apparently,
    // modern class files can have entries for each method that have identical
    // argument types but different return types. e.g., java.lang.StringBuffer
    // in JRE 1.6.0_27 has both of the following.
    // public synchronized java.lang.StringBuffer append(java.lang.String)
    // public java.lang.AbstractStringBuilder append(java.lang.String)

    MethodInstance p1 = this;
    MethodInstance p2 = (MethodInstance) p;

    // Get the reference types that declare p1 and p2.
    ReferenceType t1 =
        ((MemberInstance) ((Declaration) p1).declaration()).container();
    ReferenceType t2 =
        ((MemberInstance) ((Declaration) p2).declaration()).container();

    if ((t1 == null ? t2 == null : t1.equals(t2))
        && p1.formalTypes().equals(p2.formalTypes())) {
      // Both procedures were declared in the same place with the same argument
      // types. p1 is not more specific if its return type is a supertype of
      // p2's return type.
      if (p2.returnType().isSubtype(p1.returnType())) return false;
    }

    return super.moreSpecificImpl(p);
  }
}
