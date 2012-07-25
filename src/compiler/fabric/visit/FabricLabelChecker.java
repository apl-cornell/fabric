package fabric.visit;

import jif.ast.JifMethodDecl;
import jif.visit.LabelChecker;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.TypeSystem;
import fabric.types.SilenceableSolverGLB;

public class FabricLabelChecker extends LabelChecker {
  public FabricLabelChecker(Job job, TypeSystem ts, NodeFactory nf,
      boolean solvePerClassBody, boolean solvePerMethod, boolean doLabelSubst) {
    super(job, ts, nf, solvePerClassBody, solvePerMethod, doLabelSubst);
  }

  @Override
  public JifMethodDecl leavingMethod(JifMethodDecl n) {
    if (solvePerMethod) {
      // solving by class. We need to solve the constraints
      JifMethodDecl result = (JifMethodDecl) solveConstraints(n);
      if (!((SilenceableSolverGLB) solver()).isSolved()) {
        return null;
      }
      return result;
    }
    return n;
  }
}
