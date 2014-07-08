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
package fabric.types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jif.types.JifParsedPolyType_c;
import jif.types.label.Label;
import polyglot.frontend.Source;
import polyglot.types.*;

public class FabricParsedClassType_c extends JifParsedPolyType_c implements FabricParsedClassType {
  private transient Label defaultFieldLabel = null;
  private transient boolean fieldLabelFound = false;
  
  public FabricParsedClassType_c() {
    super();
  }

  public FabricParsedClassType_c(FabricTypeSystem ts, LazyClassInitializer init,
      Source fromSource) {
    super(ts, init, fromSource);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.types.ClassType_c#descendsFromImpl(polyglot.types.Type)
   */
  @Override
  public boolean descendsFromImpl(Type ancestor) {
    FabricTypeSystem ts = (FabricTypeSystem) typeSystem();

    if (flags().contains(FabricFlags.NONFABRIC) && ts.typeEquals(ancestor, ts.FObject())) {
      // XXX nonfabric interfaces do not descend from fabric.lang.Object.
      return false;
    }
    
    // All Fabric interface types descend from fabric.lang.Object.
    if (ancestor.isCanonical() && !ancestor.isNull()
        && !ts.typeEquals(this, ancestor) && ancestor.isReference()
        && ts.typeEquals(ancestor, ts.FObject()) && flags().isInterface()) {
      // Determine whether we have a Fabric interface.
      // XXX Assume any class loaded from the DeserializedClassInitializer was
      // compiled with loom.
      if (job() != null
          || initializer() instanceof DeserializedClassInitializer)
        return true;
    }

    return super.descendsFromImpl(ancestor);
  }
  
  @SuppressWarnings("unchecked")
  public Label defaultFieldLabel() {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    if (!fieldLabelFound) {
      if (ts.isFabricClass(this)) {
        FabricClassType superType = (FabricClassType)superType();
        if (superType != null && superType.defaultFieldLabel() != null) {
          defaultFieldLabel = superType.defaultFieldLabel();
        }
        else {
          for (FieldInstance fi : (List<FieldInstance>)fields()) {
            if (fi.flags().isStatic()) continue;
            Type t = fi.type();
            if (ts.isLabeled(t)) {
              defaultFieldLabel = ts.labelOfType(t);
              break;
            }
          }
        }
      }
      fieldLabelFound = true;
    }
    return defaultFieldLabel;
  }
  
  public Label defaultFabilFieldLabel() {
    // Type checking has been done, so all field labels are guaranteed to
    // be the same
    // ThisLabelChecker has already run and checked that 'this' is
    // being used correctly.
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();

    // If the default field label has not been computed yet
    // or if this is a DelegatingPrincipal (XXX Principal instead?)
    // then compute defaultFieldLabel
    if (!fieldLabelFound || isSubtype(ts.DelegatingPrincipal())) {
      FabricClassType superType = (FabricClassType)superType();
      if (superType != null && superType.defaultFabilFieldLabel() != null) {
        defaultFieldLabel = superType.defaultFabilFieldLabel();
      }
      else {
        for (FieldInstance fi : (List<FieldInstance>)fields()) {
          if (fi.flags().isStatic()) continue;
          Type t = fi.type();
          if (ts.isLabeled(t)) {
            defaultFieldLabel = ts.labelOfType(t);
            break;
          }
        }
      }
      fieldLabelFound = true;
    }
    return defaultFieldLabel;
  }
  
  
  public void removeMethod(MethodInstance mi) {
    for (Iterator<MethodInstance> it = methods.iterator(); it.hasNext(); ) {
      if (it.next() == mi) {
        it.remove();
      }
    }
  }
}
