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
package fabric.ast;

import java.util.List;

import jif.types.label.Label;

import fabric.ExtensionInfo;
import fabric.FabricOptions;
import fabric.types.FabricParsedClassType;
import fabric.types.FabricTypeSystem;

import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.FieldDecl;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.main.Report;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.TypeChecker;

public class ClassDecl_c extends jif.ast.JifClassDecl_c {

  @SuppressWarnings("unchecked")
  public ClassDecl_c(Position pos, Flags flags, Id name, List params,
                     TypeNode superClass, List interfaces, List authority,
                     ClassBody body) {
    super(pos, flags, name, params, superClass, interfaces, authority, body);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.ClassDecl_c#setSuperClass(polyglot.visit.AmbiguityRemover,
   *      polyglot.ast.TypeNode)
   */
  @Override
  protected void setSuperClass(AmbiguityRemover ar, TypeNode superClass)
      throws SemanticException {
    // XXX: I think this is taken care of by the ExplicitSuperclassAdder now
    FabricTypeSystem ts = (FabricTypeSystem) ar.typeSystem();
    String fullName = type.fullName();

    if (superClass != null || type.equals(ts.Object())
        || type.equals(ts.FObject()) || fullName.equals(ts.Object().fullName())
        || fullName.equals(ts.FObject().fullName())) {
      super.setSuperClass(ar, superClass);
    } else {
      // Compiling a Fabric class with an unspecified superclass, and the type
      // is not the same as ts.Object() nor ts.FObject(). As such, the default
      // superclass is ts.FObject().
      if (Report.should_report(Report.types, 3))
        Report.report(3, "setting superclass of " + type + " to "
            + ts.FObject());
      type.superType(ts.FObject());
    }
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    ClassDecl cd = (ClassDecl)super.typeCheck(tc);
    FabricParsedClassType pct = (FabricParsedClassType)cd.type();
    Label defaultFieldLabel = pct.defaultFieldLabel();
    
    FabricTypeSystem ts = (FabricTypeSystem)tc.typeSystem();
    
    if (!ts.isFabricClass(pct)) {
      // No need to check Java classes implemented in Fabric.
      return cd;
    }
    
    for (ClassMember cm : (List<ClassMember>)cd.body().members()) {
      if (cm instanceof FieldDecl) {
        FieldDecl fd = (FieldDecl)cm;

        // Skip static fields.
        if (fd.flags().isStatic()) continue;
        
        // If this is a signature then dont' apply the default field restriction
        FabricOptions opts = (FabricOptions) ((ExtensionInfo) tc.job().extensionInfo()).getOptions();
        boolean sigMode = opts.signatureMode();
        
        Type ft = fd.type().type();
        Label fl = ts.labelOfType(ft);
        // TODO: Enable this for fabric signatures for fabil classes
        // Disable for non fabric classes
        if (ts.isFabricClass(ft) && !ts.equals(defaultFieldLabel, fl) && !sigMode) {
          throw new SemanticException("The field " + fd.fieldInstance() + " has a different label than " +
          		              "the default field label " + defaultFieldLabel + 
          		              "of the class " + pct + ".",
                                      fd.position());
        }
      }
    }
    
    return cd;
  }
}
