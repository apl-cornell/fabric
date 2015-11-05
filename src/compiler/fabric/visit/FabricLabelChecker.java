package fabric.visit;

import java.util.List;

import fabric.extension.FabricCallHelper;
import fabric.types.FabricPath;
import fabric.types.SilenceableSolverGLB;

import jif.ast.JifMethodDecl;
import jif.extension.CallHelper;
import jif.types.JifProcedureInstance;
import jif.types.Path;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.ast.Receiver;
import polyglot.frontend.Job;
import polyglot.types.ReferenceType;
import polyglot.types.TypeSystem;
import polyglot.util.Position;

public class FabricLabelChecker extends LabelChecker {
  public FabricLabelChecker(Job job, TypeSystem ts, NodeFactory nf,
      boolean warningsEnabled, boolean solvePerClassBody,
      boolean solvePerMethod, boolean doLabelSubst) {
    super(job, ts, nf, warningsEnabled, solvePerClassBody, solvePerMethod,
        doLabelSubst);
  }

  @Override
  public JifMethodDecl leavingMethod(JifMethodDecl n) {
    if (solvePerMethod) {
      // solving by method. We need to solve the constraints
      JifMethodDecl result = (JifMethodDecl) solveConstraints(n);
      if (!((SilenceableSolverGLB) solver()).isSolved()) {
        return null;
      }
      return result;
    }
    return n;
  }

  //XXX: CallHelper has some really bad design patterns.
  //     Lots of static calls make it very difficult to extend.
  @Override
  public CallHelper createCallHelper(Label receiverLabel, Receiver receiver,
      ReferenceType calleeContainer, JifProcedureInstance pi,
      List<Expr> actualArgs, Position position) {
    return new FabricCallHelper(receiverLabel, receiver, calleeContainer, pi,
        actualArgs, position);
  }

  @Override
  public CallHelper createCallHelper(Label receiverLabel,
      ReferenceType calleeContainer, JifProcedureInstance pi,
      List<Expr> actualArgs, Position position) {
    return createCallHelper(receiverLabel, null, calleeContainer, pi,
        actualArgs, position);
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    // TODO Auto-generated method stub
    return super.clone();
  }

  /* Ignore additional AC path. */
  @Override
  public boolean ignoredForSinglePathRule(Path p) {
    return super.ignoredForSinglePathRule(p) || p.equals(FabricPath.A);
  }

}
