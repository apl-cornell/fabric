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
package fabric.visit;

import fabric.types.SilenceableSolverGLB;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.TypeSystem;
import jif.ast.JifMethodDecl;
import jif.visit.LabelChecker;

public class FabricLabelChecker extends LabelChecker {
  public FabricLabelChecker(Job job, TypeSystem ts, NodeFactory nf, boolean solvePerClassBody, boolean solvePerMethod, boolean doLabelSubst) {
    super(job, ts, nf, solvePerClassBody, solvePerMethod, doLabelSubst);
  }
  
  @Override
  public JifMethodDecl leavingMethod(JifMethodDecl n) {
    if (solvePerMethod) {
        // solving by class. We need to solve the constraints
        JifMethodDecl result = (JifMethodDecl)solveConstraints(n);
        if (!((SilenceableSolverGLB)solver()).isSolved()) {
          return null;
        }
        return result;
    }
    return n;
  }
}
